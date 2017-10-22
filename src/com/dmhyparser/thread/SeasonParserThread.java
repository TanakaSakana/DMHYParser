package com.dmhyparser.thread;

import com.dmhyparser.context.BANGUMI;
import com.dmhyparser.info.dmhy.BangumiInfo;
import com.dmhyparser.parser.helper.JSONBangumiParser;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class SeasonParserThread implements Callable<Integer> {
    String lnk;
    String year;
    String season;

    public SeasonParserThread(String lnk, String year, String season) {
        this.lnk = lnk;
        this.year = year;
        this.season = season;
    }

    @Override
    public Integer call() throws Exception {
        // Get information for bangumi
        String rawData;
        HashMap<String, BangumiInfo> blist = new HashMap<>();
        try {
            rawData = JSONBangumiParser.readFromHTTP(lnk);
            blist = JSONBangumiParser.parse(rawData, year, season);
        } catch (Exception e) {
            System.out.println("parse JSON error");
        }
        // Get information for description
        BANGUMI.addtoBangumiInfoList(blist);
        return null;
    }
}
