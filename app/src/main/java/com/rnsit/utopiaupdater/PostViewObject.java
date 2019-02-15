package com.rnsit.utopiaupdater;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PostViewObject implements Serializable{
    String postDetail;
    String imagePostURL;
    String timeStamp;
    public PostViewObject (String timeStamp, String postDetail, String imagePostURL){
        this.timeStamp = timeStamp;
        this.postDetail = postDetail;
        this.imagePostURL = imagePostURL;
    }
    public PostViewObject(){}

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(String postDetail) {
        this.postDetail = postDetail;
    }

    public String getImagePostURL() {
        return imagePostURL;
    }

    public void setImagePostURL(String imagePostURL) {
        this.imagePostURL = imagePostURL;
    }
}
