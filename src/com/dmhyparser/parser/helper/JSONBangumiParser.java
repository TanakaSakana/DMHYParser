package com.dmhyparser.parser.helper;

import com.dmhyparser.info.dmhy.BangumiInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class JSONBangumiParser {
    // Input JSON output Season list
    public static HashMap<String, BangumiInfo> parse(String jsonarraystring, String yr, String season) throws UnsupportedEncodingException {
        HashMap<String, BangumiInfo> SeasonAnimeList = new HashMap<>();
        JSONObject bangumi_json = new JSONObject(jsonarraystring);

        for (Object weekday : bangumi_json.keySet()) {
            JSONArray arr = (JSONArray) bangumi_json.get(weekday.toString());
            for (int i = 0; i < arr.length(); i++) {
                // Get and Create BANGUMI Instance
                JSONObject obj = (JSONObject) arr.get(i);
                BangumiInfo item = new BangumiInfo(obj, yr, season);
                SeasonAnimeList.put(item.getName(), item);
            }
        }
        return SeasonAnimeList;
    }

    public static String readFromHTTP(String link) throws Exception {
        String jsonres = BaseParser.readFromHTTP(link);
        return jsonres.substring(jsonres.indexOf("{"));
    }
}
