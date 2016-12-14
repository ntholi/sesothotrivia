package com.nalaneholdings.sesothotrivia.model.bean;

/**
 * Created by ntholi.nkhatho on 2016/08/11.
 */
public class Advert {

    public static final String NAME = "adverts";

    private String advertiserID;
    private String body;
    private Advertiser advertiser;

    public Advert(){
        // Default constructor required for calls to DataSnapshot.getValue(Player.class)
    }

    public Advert(String advertiserID, String body) {
        this.advertiserID = advertiserID;
        this.body = body;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAdvertiserID() {
        return advertiserID;
    }

    public void setAdvertiserID(String advertiserID) {
        this.advertiserID = advertiserID;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }
}
