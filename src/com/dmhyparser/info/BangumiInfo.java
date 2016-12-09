package com.dmhyparser.info;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class BangumiInfo {
    String Name;
    String Image;
    String Keyword;
    String Homepage;
    String Year;
    String Season;
    String Description;

    public BangumiInfo(String name, String Image, String Keyword, String Homepage, String year, String season) {
        this.Name = name;
        this.Image = Image;
        this.Keyword = Keyword;
        this.Homepage = Homepage;
        this.Year = year;
        this.Season = season;
    }

    public BangumiInfo(JSONObject obj) throws UnsupportedEncodingException {
        this.Name = obj.getString("name");
        this.Image = obj.getString("img");
        this.Keyword = URLDecoder.decode(obj.getString("kw"), "utf-8");
        this.Homepage = obj.getString("hp");
        this.Description = "no description";
    }

    public BangumiInfo(JSONObject obj, String year, String season) throws UnsupportedEncodingException {
        this.Name = obj.getString("name");
        this.Image = obj.getString("img");
        this.Keyword = URLDecoder.decode(obj.getString("kw"), "utf-8");
        this.Homepage = obj.getString("hp");
        this.Year = year;
        this.Season = season;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        if (description != null) Description = description;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public void setSeason(String season) {
        this.Season = season;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getKeyword() {
        return Keyword;
    }

    public String getHomepage() {
        return Homepage;
    }

    @Override
    public String toString() {
        return "BangumiInfo{" +
                "Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", Keyword='" + Keyword + '\'' +
                ", Homepage='" + Homepage + '\'' +
                ", Year='" + Year + '\'' +
                ", Season='" + Season + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
