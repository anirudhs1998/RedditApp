package com.example.anirudhs.redditapp;

/**
 * Created by Anirudh S on 15-07-2017.
 */

public class Listitem {
    private int _id;
    String descr;
    String imgurl;
    int comm;

    public void set_id(int _id) {
        this._id = _id;
    }

    public Listitem(String descr, String url, int  comm) {
        this.descr = descr;
        this.imgurl = url;
        this.comm = comm;
    }

    public int getComm() {
        return comm;
    }

    public int get_id() {
        return _id;
    }

    public String getDescr() {
        return descr;
    }

    public String getImgurl() {
        return imgurl;
    }
}
