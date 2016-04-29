package com.android.yaschenkodanil.yandexsummerapp.model;

import java.io.Serializable;

/**
 * Created by danil on 25.04.16.
 */
public class Cover implements Serializable {
    private String smallCoverImage = "";
    private String bigCoverImage = "";

    public Cover() {
    }

    public void setSmallCoverImage(String smallCoverImage) {
        this.smallCoverImage = smallCoverImage;
    }

    public void setBigCoverImage(String bigCoverImage) {
        this.bigCoverImage = bigCoverImage;
    }

    public String getSmallCoverImage() {
        return smallCoverImage;
    }

    public String getBigCoverImage() {
        return bigCoverImage;
    }
}
