package com.example.thomas.weather_thuis_2;

import android.net.Uri;

import java.net.URI;

/**
 * Created by thomas on 17/01/15.
 */
public class Weather {
    public Uri icon;
    public String title;
    public Weather(){
        super();
    }

    public Weather(Uri icon, String title) {
        super();
        this.icon = icon;
        this.title = title;
    }

    public Uri getIcon() {
        return icon;
    }

    public void setIcon(Uri icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
