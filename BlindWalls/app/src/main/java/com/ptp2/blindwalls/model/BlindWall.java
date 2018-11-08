package com.ptp2.blindwalls.model;

import java.io.Serializable;
import java.util.List;

public class BlindWall implements Serializable {

    private final int id;
    private final int published;
    private final int date;
    private final int dateModified;
    private final int authorID;
    private final double latitude;
    private final double longitude;
    private final int numberOnMap;
    private final double rating;

    private final String address;
    private final String title;
    private final String videoUrl;
    private final String year;
    private final String photographer;
    private final String videoAuthor;
    private final String author;
    private final String descriptionEnglish;
    private final String descriptionDutch;
    private final String materialEnglish;
    private final String materialDutch;

    private final List<String> imagesUrls;

    public static class Builder {
        private int id;
        private int published;
        private int date;
        private int dateModified;
        private int authorID;
        private double latitude;
        private double longitude;
        private String adress;
        private int numberOnMap;
        private String videoUrl;
        private String year;
        private String photographer;
        private String videoAuthor;
        private String author;
        private double rating;
        private String descriptionEnglish;
        private String descriptionDutch;
        private String materialEnglish;
        private String materialDutch;
        private List<String> imagesUrls;
        private String title;



        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder published(int published) {
            this.published = published;
            return this;
        }

        public Builder date(int date) {
            this.date = id;
            return this;
        }

        public Builder dateModified(int dateModified) {
            this.dateModified = dateModified;
            return this;
        }

        public Builder authorID(int authorID) {
            this.authorID = authorID;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder adress(String adress) {
            this.adress = adress;
            return this;
        }

        public Builder numberOnMap(int numberOnMap) {
            this.numberOnMap = numberOnMap;
            return this;
        }

        public Builder videoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public Builder year(String year) {
            this.year = year;
            return this;
        }

        public Builder photographer(String photographer) {
            this.photographer = photographer;
            return this;
        }

        public Builder videoAuthor(String videoAuthor) {
            this.videoAuthor = videoAuthor;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder descriptionEnglish(String descriptionEnglish) {
            this.descriptionEnglish = descriptionEnglish;
            return this;
        }

        public Builder descriptionDutch(String descriptionDutch) {
            this.descriptionDutch = descriptionDutch;
            return this;
        }

        public Builder materialEnglish(String materialEnglish) {
            this.materialEnglish = materialEnglish;
            return this;
        }

        public Builder materialDutch(String materialDutch) {
            this.materialDutch = materialDutch;
            return this;
        }

        public Builder imagesUrls(List<String> imagesUrls) {
            this.imagesUrls = imagesUrls;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public BlindWall build() {
            return new BlindWall(this);
        }
    }


    private BlindWall(Builder builder) {
        this.id = builder.id;
        this.published = builder.published;
        this.date = builder.date;
        this.dateModified = builder.dateModified;
        this.authorID = builder.authorID;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.address = builder.adress;
        this.numberOnMap = builder.numberOnMap;
        this.videoUrl = builder.videoUrl;
        this.year = builder.year;
        this.photographer = builder.photographer;
        this.videoAuthor = builder.videoAuthor;
        this.author = builder.author;
        this.rating = builder.rating;
        this.descriptionEnglish = builder.descriptionEnglish;
        this.descriptionDutch = builder.descriptionDutch;
        this.materialEnglish = builder.materialEnglish;
        this.materialDutch = builder.materialDutch;
        this.imagesUrls = builder.imagesUrls;
        this.title = builder.title;
    }

    public int getId() {
        return id;
    }

    public int getPublished() {
        return published;
    }

    public int getDate() {
        return date;
    }

    public int getDateModified() {
        return dateModified;
    }

    public int getAuthorID() {
        return authorID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getNumberOnMap() {
        return numberOnMap;
    }

    public double getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getYear() {
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
