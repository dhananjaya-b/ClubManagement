package com.example.clubmanagement;

public class Events {
    private String date;
    private String guest;
    private String title;
    private String venue;

    public Events(String date, String guest, String title, String venue) {
        this.date = date;
        this.guest = guest;
        this.title = title;
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}

