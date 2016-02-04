package org.snake.android.instagramclient;

import android.content.Context;
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

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);

        tvCaption.setText(photo.caption);
        ivLikes.setText( "❤" + photo.likesCount);
        ivLastCommentUserName.setText(photo.lastComment.commentsUserName);
        ivLastComments.setText(":"+ photo.lastComment.commentsText);
        ivSecondLastComments.setText(":"+ photo.secondLastComment.commentsText);
        ivSecondLastCommentUserName.setText(photo.secondLastComment.commentsUserName);
        ivUserName.setText(photo.username);

        //insert image using piascco
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);

        //insert the profile image using roundimage and piascco
        ivProfilePhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.profileImageUrl).into(ivProfilePhoto);

            return convertView;

    }
}
