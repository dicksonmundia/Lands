package com.example.landsproject;

import java.io.Serializable;

public class Land implements Serializable {
    private String  description, status, location, ownerName, number, landId, imageUrl;
    private int price, ownerNumber;

    public Land() {
    }

    public Land(String description, String imageUrl, int ownerNumber, String status, String location, String ownerName, String number, int price, String landId) {
        this.description = description;
        this.status = status;
        this.location = location;
        this.ownerName = ownerName;
        this.number = number;
        this.price = price;
        this.landId = landId;
        this.imageUrl = imageUrl;
        this.ownerNumber = ownerNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(int ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLandId() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId = landId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
