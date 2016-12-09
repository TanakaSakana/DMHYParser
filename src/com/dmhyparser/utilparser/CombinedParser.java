package com.dmhyparser.utilparser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CombinedParser implements BaseWikiParser {
    BaseWikiParser JAParser, ENParser, ZHParser;

    private static String getWithExecutorServices(final String query) throws Exception {
        String Description;
        BaseWikiParser JAParser, ENParser, ZHParser;
        JAParser = new JAWikiParser();
        ENParser = new ENWikiParser();
        ZHParser = new ZHWikiParser();

        List<Future<String>> results = new ArrayList<>();
        ExecutorService service = Executors.newCachedThreadPool();

        if ((Description = ZHParser.parse(query)) == null) {
            results.add(service.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return ENParser.parse(TranslaterParser.translate(query, "zh", "en"));
                }
            }));
            results.add(service.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return JAParser.parse(TranslaterParser.translate(query, "zh", "ja").substring(0, 5));
                }
            }));
            for (Future<String> result : results) {
                String res = result.get();
                if (res != null) return res;
            }
            return null;
        }
        return Description;
    }

    /**
     * 1. Serial
     * 2.  Future Task
     * 3. Executor Services
     */
    @Override
    public String parse(String query) throws Exception {
        return getWithExecutorServices(query);
    }

    private String getWithSerial(String query) throws Exception {
        String Description;

        JAParser = new JAWikiParser();
        ENParser = new ENWikiParser();
        ZHParser = new ZHWikiParser();

        if ((Description = ZHParser.parse(query)) == null)
            if ((Description = ENParser.parse(TranslaterParser.translate(query, "zh", "en"))) == null)
                if ((Description = JAParser.parse(TranslaterParser.translate(query, "zh", "ja"))) == null)
                    return null;
        return Description;
    }

    private String getWithFutureTask(final String query) throws Exception {
        String Description;
        JAParser = new JAWikiParser();
        ENParser = new ENWikiParser();
        ZHParser = new ZHWikiParser();

        if ((Description = ZHParser.parse(query)) == null) {
            FutureTask<String> future_en = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return ENParser.parse(TranslaterParser.translate(query, "zh", "en"));
                }
            });
            FutureTask<String> future_ja = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return JAParser.parse(TranslaterParser.translate(query, "zh", "ja").substring(0, 5));
                }
            });
            Thread t1 = new Thread(future_en);
            Thread t2 = new Thread(future_ja);
            t1.start();
            t2.start();
            String tx_en = future_en.get();
            String tx_ja = future_ja.get();

            if ((Description = tx_en) == null)
                if ((Description = tx_ja) == null)
                    return null;
        }
        return Description;
    }
}