package com.dmhyparser.utilparser;

import org.json.JSONArray;

public class ENWikiParser implements BaseWikiParser {

    @Override
    public String parse(String query) {
        String jsonres = "";

        String temp = "https://en.wikipedia.org/w/api.php?action=opensearch&search=%s&limit=3&namespace=0&format=json";
        String opt = String.format(temp, query);
        try {
            jsonres = com.dmhyparser.jparser.BaseParser.readFromHTTP(opt);
        } catch (Exception e) {
            System.out.println("ENWikiParser response error");
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
                        if (arr.get(i).toString().length() > 50)
                            if (arr.get(i).toString().indexOf("[Aa]nimation") != -1 || arr.get(i).toString().indexOf("[Aa]nime") != -1 || arr.get(i).toString().indexOf("[Mm]anga") != -1)
                                return arr.get(i).toString();
                    }
                }
            }
        }
        return null;
    }
}
