package com.ptp2.blindwalls.model;

public class BlindWall {

    private final int id;
    private final int published;
    private final int date;
    private final int dateModified;
    private final int authorID;
    private final int latitude;
    private final int longitude;
    private final String address;
    private final int numberOnMap;
    private final String videoUrl;
    private final String year;
    private final String photographer;
    private final String videoAuthor;
    private final String author;
    private final double rating;


    public static class Builder {
        private int id;
        private int published;
        private int date;
        private int dateModified;
        private int authorID;
        private int latitude;
        private int longitude;
        private String adress;
        private int numberOnMap;
        private String videoUrl;
        private String year;
        private String photographer;
        private String videoAuthor;
        private String author;
        private double rating;


        public Builder id(int id) {this.id = id; return this;}
        public Builder published(int published) {this.published = published; return this;}
        public Builder date(int date) {this.date = id; return this;}
        public Builder dateModified(int dateModified) {this.dateModified = dateModified; return this;}
        public Builder authorID(int authorID) {this.authorID = authorID; return this;}
        public Builder latitude(int latitude) {this.latitude = latitude; return this;}
        public Builder longitude(int longitude) {this.longitude = longitude; return this;}
        public Builder adress(String adress) {this.adress = adress; return this;}
        public Builder numberOnMap(int numberOnMap) {this.numberOnMap = numberOnMap; return this;}
        public Builder videoUrl(String videoUrl) {this.videoUrl = videoUrl; return this;}
        public Builder year(String year) {this.year = year; return this;}
        public Builder photographer(String photographer) {this.photographer = photographer; return this;}
        public Builder videoAuthor(String videoAuthor) {this.videoAuthor = videoAuthor; return this;}
        public Builder author(String author) {this.author = author; return this;}
        public Builder rating(double rating) {this.rating = rating; return this;}

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

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
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

    public double getRating() {
        return rating;
    }


    @Override
    public String toString() {
        return "BlindWall{" +
                "id=" + id +
                ", published=" + published +
                ", date=" + date +
                ", dateModified=" + dateModified +
                ", authorID=" + authorID +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", numberOnMap=" + numberOnMap +
                ", videoUrl='" + videoUrl + '\'' +
                ", year='" + year + '\'' +
                ", photographer='" + photographer + '\'' +
                ", videoAuthor='" + videoAuthor + '\'' +
                ", author='" + author + '\'' +
                ", rating=" + rating +
                '}';
    }
}
