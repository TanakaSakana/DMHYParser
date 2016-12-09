package com.dmhyparser.workerthreads;

import com.dmhyparser.info.BANGUMI;
import com.dmhyparser.info.BangumiInfo;
import com.dmhyparser.utilparser.CombinedParser;

import java.util.concurrent.Callable;

public class DescriptionParserThread implements Callable<Integer> {
    BangumiInfo bangumiInfo;

    public DescriptionParserThread(BangumiInfo bangumiInfo) {
        this.bangumiInfo = bangumiInfo;
    }

    @Override
    public Integer call() throws Exception {
        String desc = new CombinedParser().parse(bangumiInfo.getName());
        bangumiInfo.setDescription(desc);
        BANGUMI.getBangumiInfoList().put(bangumiInfo.getName(), bangumiInfo);
        return null;
    }
}

