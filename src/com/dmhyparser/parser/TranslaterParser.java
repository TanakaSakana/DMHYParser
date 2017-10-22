package com.dmhyparser.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslaterParser {
    public static String readFromHTTP(String link) throws Exception {
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-agent", "Mozilla/5.0 (Linux; Android 4.2.1; Nexus 7 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String rawData = "";
        while ((rawData += in.readLine()) != null && in.ready()) ;
        rawData = rawData.replace("\t", "");
        return rawData;
    }

    public static String getOutput(String jsonstr) {
        String output = new JSONObject(jsonstr).get("text").toString();
        output = new JSONArray(output).getString(0);
        return output;
    }

    public static String translate(String str, String srclang, String tarlang) throws Exception {
        String Template = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                "key=%s" +
                "&text=%s" +
                "&lang=%s-%s" +
                "&format=plain";
        String api = "trnsl.1.1.20161101T062645Z.00a7c86ab7e458e0.a0af297e6397f526dc5774c18f003589d65cca27";
        String SourceLang = srclang;
        String TargetLang = tarlang;

        String obj = str;
        obj = URLEncoder.encode(obj);
        String query = String.format(Template, api, obj, SourceLang, TargetLang);
        String ans = readFromHTTP(query);
        return (getOutput(ans) != "") ? getOutput(ans) : null;
    }
}
