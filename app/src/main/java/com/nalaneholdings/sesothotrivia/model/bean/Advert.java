package com.nalaneholdings.sesothotrivia.model.bean;

/**
 * Created by ntholi.nkhatho on 2016/08/11.
 */
public class Advert {

    private String title;
    private String heading;
    private String body;

    public Advert(){
        // Default constructor required for calls to DataSnapshot.getValue(Player.class)
    }

    public Advert(String title, String heading, String body) {
        this.title = title;
        this.heading = heading;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
