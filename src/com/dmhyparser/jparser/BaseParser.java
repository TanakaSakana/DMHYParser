package com.dmhyparser.jparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseParser {

    public static String readFromFile(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String rawData = "";
        while ((rawData += in.readLine()) != null && in.ready()) ;
        return rawData;
    }

    public static String readFromHTTP(String link) throws Exception {
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode() == 400) {
            return "";
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String rawData = "";
        while ((rawData += in.readLine()) != null && in.ready()) ;
        rawData = rawData.replace("\t", "");
        conn.getInputStream().close();
        return rawData;
    }
}
