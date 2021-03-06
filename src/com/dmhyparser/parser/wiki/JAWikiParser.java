package com.dmhyparser.parser.wiki;

import com.dmhyparser.parser.BaseWikiParser;
import org.json.JSONArray;

public class JAWikiParser implements BaseWikiParser {
    @Override
    public String parse(String query) {
        String jsonres = "";

        String temp = "https://ja.wikipedia.org/w/api.php?action=opensearch&search=%s&limit=10&namespace=0&format=json";
        String opt = String.format(temp, query);
        try {
            jsonres = com.dmhyparser.parser.helper.BaseParser.readFromHTTP(opt);
        } catch (Exception e) {
            System.out.println("parse HTTP response error");
        }
        if (jsonres.indexOf("[") != -1) {
            JSONArray input = new JSONArray(jsonres.substring(jsonres.indexOf("[")));
            // Total Array Length
            if (input.get(2).toString().length() > 20) {
                String response = input.get(2).toString();
                JSONArray arr = new JSONArray(response);
                if (arr.length() > 1) {
                    for (int i = 0; i < arr.length(); i++) {
                        // Query row length
                        if (arr.get(i).toString().length() > 10)
                            return arr.get(i).toString();
                    }
                }
            }
        }
        return null;
    }
}
