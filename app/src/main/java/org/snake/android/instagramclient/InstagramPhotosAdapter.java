package org.snake.android.instagramclient;

import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rmukhedkar on 2/1/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            InstagramPhoto photo = getItem(position);
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView ivLikes = (TextView) convertView.findViewById(R.id.ivLikes);
        TextView ivLastCommentUserName = (TextView) convertView.findViewById(R.id.ivlastCommentUserName);
        TextView ivLastComments = (TextView) convertView.findViewById(R.id.ivLastComments);
        TextView ivSecondLastCommentUserName = (TextView) convertView.findViewById(R.id.ivSecondLastCommentUserName);
        TextView ivSecondLastComments = (TextView) convertView.findViewById(R.id.ivSecondLastComments);
        TextView ivUserName = (TextView) convertView.findViewById(R.id.ivUserName);
        TextView ivCreatedTime = (TextView) convertView.findViewById(R.id.ivCreatedTime);

        Typeface fonts = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        tvCaption.setTypeface(fonts);

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);

        tvCaption.setText(photo.caption);
        ivLikes.setText("❤" + photo.likesCount);
        ivLastCommentUserName.setText(photo.lastComment.commentsUserName);
        ivLastComments.setText(":" + photo.lastComment.commentsText);
        ivSecondLastComments.setText(":" + photo.secondLastComment.commentsText);
        ivSecondLastCommentUserName.setText(photo.secondLastComment.commentsUserName);
        ivUserName.setText(photo.username);
        ivCreatedTime.setText(DateUtils.getRelativeTimeSpanString(photo.createdTime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));


                //insert image using piascco
                ivPhoto.setImageResource(0);
             //   Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
             Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.defaultplaceholder).error(R.drawable.defaultplaceholder).into(ivPhoto);

        //insert the profile image using roundimage and piascco
        ivProfilePhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.profileImageUrl).into(ivProfilePhoto);

            return convertView;

    }
}
