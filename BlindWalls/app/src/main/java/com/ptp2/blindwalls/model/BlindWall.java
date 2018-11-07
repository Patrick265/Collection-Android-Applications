package com.ptp2.blindwalls.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BlindWall {

    private int id;
    private double latitude;
    private double longitude;
    private String address;
    private int numberOnMap;
    private String videoUrl;
    private int year;
    private String photographer;
    private String videoAuthor;
    private String author;
    private String title;
    private String descriptionEnglish;
    private String descriptionDutch;
    private String materialEnglish;
    private String materialDutch;
    private List<String> imagesUrls;



    public BlindWall(JSONObject jsonWall)
    {
        try {
            // parse basic information:
            id = jsonWall.getInt("id");
            title = jsonWall.getJSONObject("title").getString("en").trim();
            author = jsonWall.getString("author").trim();
            year = jsonWall.getInt("year");

            // parse description information:
            descriptionEnglish = jsonWall.getJSONObject("description").getString("en");
            descriptionDutch = jsonWall.getJSONObject("description").getString("nl");
            materialEnglish = jsonWall.getJSONObject("material").getString("en");
            materialDutch = jsonWall.getJSONObject("material").getString("nl");

            // parse geographic information:
            numberOnMap = jsonWall.getInt("numberOnMap");
            latitude = jsonWall.getDouble("latitude");
            longitude = jsonWall.getDouble("longitude");
            address = jsonWall.getString("address");

            // parse image and video information:
            videoAuthor = jsonWall.getString("videoAuthor");
            videoUrl = jsonWall.getString("videoUrl");
            photographer = jsonWall.getString("photographer");
            // get all the images URLS:
            JSONArray imagesUrl = jsonWall.getJSONArray("images");
            List<String> imagesUrls = new ArrayList<>();
            for (int j = 0; j < imagesUrl.length(); j++) {
                String imageUrl = imagesUrl.getJSONObject(j).getString("url");
                imagesUrls.add(imageUrl);
            }
            this.imagesUrls = imagesUrls;
        } catch(JSONException e)
        {
            e.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public int getNumberOnMap() {
        return numberOnMap;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int getYear() {
        return year;
    }

    public String getPhotographer() {
        return photographer;
    }

    public String getVideoAuthor() {
        return videoAuthor;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public String getDescriptionDutch() {
        return descriptionDutch;
    }

    public String getMaterialEnglish() {
        return materialEnglish;
    }

    public String getMaterialDutch() {
        return materialDutch;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }
}
