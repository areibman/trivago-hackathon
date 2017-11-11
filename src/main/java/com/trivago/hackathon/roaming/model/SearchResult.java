package com.trivago.hackathon.roaming.model;

public class SearchResult {

    String name;
    String location;
    String rating;
    String ratingText;
    String provider;
    String maxPrice;
    String minPrice;
    String linkElementId;

    public SearchResult(String name, String location, String rating, String ratingText, String provider, String maxPrice, String minPrice, String linkElementId) {
        this.name = name;
        this.location = location;
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
}
