package com.android.rivchat.model;

/**
 * Created by shaimaaalkhamees on 25/03/2018 AD.
 */

public class imageUpload {
    public String name;
    public String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public imageUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public imageUpload(){}
}

