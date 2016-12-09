package com.dmhyparser.jparser;

import com.dmhyparser.info.Year;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONIndexParser {

    public static List<Year> parse(String str) {
        List<Year> yearslist = new ArrayList<>();
        JSONObject obj = new JSONObject(str);
        JSONArray arr = (JSONArray) obj.get("years");

        for (int i = 0; i < arr.length(); i++) {
            // Get Year
            JSONObject years = (JSONObject) arr.get(i);
            JSONArray seasons = (JSONArray) years.get("seasons");
            // Initialize Year object
            String Year = years.get("n").toString();
            Year yearItem = new Year(Year);
            for (int j = 0; j < seasons.length(); j++) {
                //Get Season
                JSONObject season = (JSONObject) seasons.get(j);
                String Text = season.get("text").toString();
                String Index = season.get("index").toString();
                yearItem.putSeason(Text, Index);
            }
            yearslist.add(yearItem);
        }
        return yearslist;
    }
}
