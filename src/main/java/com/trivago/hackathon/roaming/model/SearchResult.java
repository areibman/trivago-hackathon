package com.trivago.hackathon.roaming.model;

public class SearchResult {

    String name;
    String location;
    String stars;
    String rating;
    String ratingText;
    String provider;
    String maxPrice;
    String minPrice;
    String linkElementId;

    public SearchResult(String name, String location, String stars, String rating, String ratingText, String provider, String maxPrice, String minPrice, String linkElementId) {
        this.name = name;
        this.location = location;
        this.stars = stars;
        this.rating = rating;
        this.ratingText = ratingText;
        this.provider = provider;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.linkElementId = linkElementId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getStars() {
        return stars;
    }

    public String getRating() {
        return rating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public String getProvider() {
        return provider;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public String getLinkElementId() {
        return linkElementId;
    }

    public String toStringVerbose(){
        String returnStr = "Hotel Name: " + this.name + "\n"
                + "Location: " + this.name + "\n"
                + "Stars: " + this.stars + "\n"
                + "Rating: " + this.rating + " (" + this.ratingText + ")\n"
                + "Agency: " + this.provider + "\n"
                + "Price: " + this.minPrice + " (regularly: " + this.maxPrice + ")\n";
        return returnStr;
    }

    public String toStringConcise(){
        String returnStr = "Hotel Name: " + this.name +
                "\t" + "Location: " + this.name +
                "\t" + "Price: " + this.minPrice + " (regularly: " + this.maxPrice + ")";
        return returnStr;
    }
}
