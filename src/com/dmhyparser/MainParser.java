package com.dmhyparser;

import com.dmhyparser.info.BANGUMI;
import com.dmhyparser.info.Year;
import com.dmhyparser.jparser.JSONBangumiParser;
import com.dmhyparser.jparser.JSONIndexParser;
import com.dmhyparser.utilparser.CombinedParser;
import com.dmhyparser.workerthreads.SeasonParserThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainParser {
    /*  1. Read for index
    *   2. Reads bangumi for each year index*/
    public static boolean update() throws Exception {

        // Initialize
        BANGUMI.clearAll();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<SeasonParserThread> threads = new ArrayList<>();

        // Get the Index for scraping JSONs
        String rawData = JSONBangumiParser.readFromHTTP("http://share.dmhy.org/json/index.json?" + Math.random());
        BANGUMI.setIndexList(JSONIndexParser.parse(rawData));

        // Iterating JSON scrapping
        for (Year year : BANGUMI.getIndexList()) {
            for (final String season : year.getSeasonsValues()) {
                final String lnk = String.format("http://share.dmhy.org/json/%s.json?" + Math.random(), season);
                BANGUMI.addtoLinkList(lnk);
                final String currYear = year.getYear();
                // Start Scrapping from internet
                SeasonParserThread t1 = new SeasonParserThread(lnk, currYear, season);
                threads.add(t1);
            }
        }
        executorService.invokeAll(threads);
        // Get the Description of the History List
        /*List<DescriptionParserThread> runners = new ArrayList<>();
        for (final BangumiInfo bangumiInfo : BANGUMI.getBangumiInfoList().values()) {
            runners.add(new DescriptionParserThread(bangumiInfo));
        }
        executorService.invokeAll(runners);*/
        executorService.shutdown();
        return true;
    }

    /*public static void main(String[] args) throws Exception {
        MainParser.update();
        final ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future<String>> Descriptions = new ArrayList<>();

        BANGUMI.getBangumiInfoList().values().forEach(x -> {
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return new CombinedParser().parse(x.getName());
                }
            });
            Descriptions.add(future);
        });
        executorService.shutdown();

        for (Future<String> item : Descriptions) {
            System.out.println(item.get());
        }
    }*/
}