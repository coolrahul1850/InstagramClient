package org.snake.android.instagramclient;

/**
 * Created by rmukhedkar on 2/1/16.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    public String profileImageUrl;

    //Capturing the last two comments
    InstagramPhotoComments lastComment = new InstagramPhotoComments();
    InstagramPhotoComments secondLastComment = new InstagramPhotoComments();
}
