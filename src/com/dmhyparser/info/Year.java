package com.dmhyparser.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Year {
    String Year;
    HashMap<String, String> Seasons;

    public Year(String year) {
        Year = year;
        Seasons = new HashMap<>();
    }

    public void putSeason(String text, String index) {
        Seasons.put(text, index);
    }

    public void getSeasonsNames() {
        for (String str : Seasons.keySet()) {
            System.out.println(str);
        }
    }

    public List<String> getSeasonsValues() {
        List<String> list = new ArrayList<>();
        list.addAll(Seasons.values());
        return list;
    }

    public String getYear() {
        return Year;
    }

    public int getSeasonSize() {
        return Seasons.keySet().size();
    }
}
