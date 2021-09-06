package com.example.innfystays;

public class Hostelsdetails {
    private String HstName,HstImage,HstAddress,HstDetails,HstRating,HstAvailability,HstOwnerId,HstId;

    public Hostelsdetails(String hstName, String hstImage, String hstAddress, String hstDetails, String hstRating, String hstAvailability, String hstOwnerId, String hstId) {
        HstName = hstName;
        HstImage = hstImage;
        HstAddress = hstAddress;
        HstDetails = hstDetails;
        HstRating = hstRating;
        HstAvailability = hstAvailability;
        HstOwnerId = hstOwnerId;
        HstId = hstId;
    }

    public String getHstId() {
        return HstId;
    }

    public void setHstId(String hstId) {
        HstId = hstId;
    }

    public Hostelsdetails(String hstName, String hstImage, String hstAddress, String hstDetails, String hstRating, String hstAvailability, String hstOwnerId) {
        HstName = hstName;
        HstImage = hstImage;
        HstAddress = hstAddress;
        HstDetails = hstDetails;
        HstRating = hstRating;
        HstAvailability = hstAvailability;
        HstOwnerId = hstOwnerId;
    }

    public String getHstOwnerId() {
        return HstOwnerId;
    }

    public void setHstOwnerId(String hstOwnerId) {
        HstOwnerId = hstOwnerId;
    }

    public Hostelsdetails() {
    }

    public Hostelsdetails(String hstName, String hstAddress, String hstDetails, String hstAvailability) {
        HstName = hstName;
        HstAddress = hstAddress;
        HstDetails = hstDetails;
        HstAvailability = hstAvailability;
    }

    public Hostelsdetails(String hstName, String hstImage, String hstAddress, String hstDetails, String hstRating, String hstAvailability) {
        HstName = hstName;
        HstImage = hstImage;
        HstAddress = hstAddress;
        HstDetails = hstDetails;
        HstRating = hstRating;
        HstAvailability = hstAvailability;
    }

    public String getHstName() {
        return HstName;
    }

    public void setHstName(String hstName) {
        HstName = hstName;
    }

    public String getHstImage() {
        return HstImage;
    }

    public void setHstImage(String hstImage) {
        HstImage = hstImage;
    }

    public String getHstAddress() {
        return HstAddress;
    }

    public void setHstAddress(String hstAddress) {
        HstAddress = hstAddress;
    }

    public String getHstDetails() {
        return HstDetails;
    }

    public void setHstDetails(String hstDetails) {
        HstDetails = hstDetails;
    }

    public String getHstRating() {
        return HstRating;
    }

    public void setHstRating(String hstRating) {
        HstRating = hstRating;
    }

    public String getHstAvailability() {
        return HstAvailability;
    }

    public void setHstAvailability(String hstAvailability) {
        HstAvailability = hstAvailability;
    }
}
