package com.dmhyparser.utilparser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.dmhyparser.utilparser.WikiParser.*;

public class CombinedParser {
    public static String getDescriptionWithWIKI_COMBINED(final String query) throws Exception {
        return getWithExecutorServices(query);
    }

    private String getWithSerial(String query) throws Exception {
        String Description;
        if ((Description = getDescriptionWithWIKI_ZH(query)) == null)
            if ((Description = getDescriptionWithWIKI_EN(TranslaterParser.translate(query, "zh", "en"))) == null)
                if ((Description = getDescriptionWithWIKI_JA(TranslaterParser.translate(query, "zh", "ja"))) == null)
                    return null;
        return Description;
    }

    private String getWithFutureTask(final String query) throws ExecutionException, InterruptedException {
        String Description;
        if ((Description = getDescriptionWithWIKI_ZH(query)) == null) {
            FutureTask<String> future_en = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return getDescriptionWithWIKI_EN(TranslaterParser.translate(query, "zh", "en"));
                }
            });
            FutureTask<String> future_ja = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return getDescriptionWithWIKI_JA(TranslaterParser.translate(query, "zh", "ja").substring(0, 5));
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

    private static String getWithExecutorServices(final String query) throws ExecutionException, InterruptedException {
        String Description;
        List<Future<String>> results = new ArrayList<>();
        ExecutorService service = Executors.newCachedThreadPool();
        if ((Description = getDescriptionWithWIKI_ZH(query)) == null) {
            results.add(service.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return getDescriptionWithWIKI_EN(TranslaterParser.translate(query, "zh", "en"));
                }
            }));
            results.add(service.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return getDescriptionWithWIKI_JA(TranslaterParser.translate(query, "zh", "ja").substring(0, 5));
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
}