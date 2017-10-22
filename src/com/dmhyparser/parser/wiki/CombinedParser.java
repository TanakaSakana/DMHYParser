package com.dmhyparser.parser.wiki;

import com.dmhyparser.parser.BaseWikiParser;
import com.dmhyparser.parser.TranslaterParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CombinedParser {
    BaseWikiParser JAParser, ENParser, ZHParser;

    private static String getWithExecutorServices(final String query) throws Exception {
        final BaseWikiParser JAParser, ZHParser;
        JAParser = new JAWikiParser();
        ZHParser = new ZHWikiParser();

        List<Future<String>> results = new ArrayList<>();
        ExecutorService service = Executors.newCachedThreadPool();

        results.add(service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return ZHParser.parse(query);
            }
        }));

        results.add(service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String translate = TranslaterParser.translate(query, "zh", "ja");
                if (translate.length() > 5)
                    translate = translate.substring(0, 5);
                return JAParser.parse(translate);
            }
        }));
        results.add(service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return JAParser.parse(query);
            }
        }));
        service.shutdown();
        for (Future<String> result : results) {
            String res = result.get();
            if (res != null) return res;
        }
        return null;
    }

    /**
     * 1. Serial
     * 2.  Future Task
     * 3. Executor Services
     */
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