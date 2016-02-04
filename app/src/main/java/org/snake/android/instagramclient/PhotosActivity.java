package org.snake.android.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;
    private AsyncHttpClient client;
    public static final String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        client = new AsyncHttpClient();
        // Send out API Request to Popular Photos
        photos = new ArrayList<>();
        //Creating the adapter and linking it to the source
        aPhotos = new InstagramPhotosAdapter(this,photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);
        //Fetch the popular photos
        fetchPopularPhotos();

    }


    public void fetchTimelineAsync() {

        client.get(url,null, new JsonHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, JSONObject response)  {
                 aPhotos.clear();
                 // ...the data has come back, add new items to your adapter...
                 fetchPopularPhotos();
                 //Now we call setRefreshing(false) to signal refresh has finished
                 swipeContainer.setRefreshing(false);
             }
             public void onFailure(Throwable e) {
                 Log.d("DEBUG", "Fetch timeline error: " + e.toString());
             }
         });
    }


    public void fetchPopularPhotos()
    {

        client.get(url, null, new JsonHttpResponseHandler(){
           //onSuccess method
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Expecting a JSON Object
//                https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
//                - {“Data” => [x] => “type”} (“image” or “video”)
//                - {“Data” => [x] => “images” ==> “Standard resolution” == “url}
//                - {“Data” => [x] => “user” ==> “username”}
//                 Iterate each of the photo items and decode the item into a java object

                JSONArray photosJSON = null;
                JSONObject photosCommentsJSONObject = null;
                JSONArray photosCommentsJSONArray = null;
                try {
                    photosJSON = response.getJSONArray("data");
                    //iterate array of posts
                    for (int i=0; i<photosJSON.length();i++)
                    {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        if (photoJSON.optJSONObject("caption") != null) {
                            //decode the attributes of the json into a data model
                            InstagramPhoto photo = new InstagramPhoto();
                            photo.username = photoJSON.getJSONObject("user").getString("username");
                            photo.caption = photoJSON.getJSONObject("caption").getString("text");
                            photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                            photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                            photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                            photo.profileImageUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                            //getting the 2 last comments
                            try {
                                photosCommentsJSONObject = photoJSON.getJSONObject("comments");
                                photosCommentsJSONArray = photosCommentsJSONObject.getJSONArray("data");
                                //  Log.d("DEBUG",photosCommentsJSONArray.toString());
                                JSONObject photoLastComment = photosCommentsJSONArray.getJSONObject(0);
                                JSONObject photoSecondLastComment = photosCommentsJSONArray.getJSONObject(1);
                                photo.lastComment.commentsText = photoLastComment.getString("text");
                                photo.lastComment.commentsUserName = photoLastComment.getJSONObject("from").getString("username");
                                photo.lastComment.commentsUserProfilePictureImageUrl = photoLastComment.getJSONObject("from").getString("profile_picture");
                                photo.secondLastComment.commentsText = photoSecondLastComment.getString("text");
                                photo.secondLastComment.commentsUserName = photoSecondLastComment.getJSONObject("from").getString("username");
                                photo.secondLastComment.commentsUserProfilePictureImageUrl = photoSecondLastComment.getJSONObject("from").getString("profile_picture");

                            }catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            //Add decoded object to arraylist photo
                            photos.add(photo);
                        }

                        }

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                //callback
                aPhotos.notifyDataSetChanged();
            }

            //onFailure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }
}
