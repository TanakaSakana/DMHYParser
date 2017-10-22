package com.dmhyparser.thread;

import com.dmhyparser.context.BANGUMI;
import com.dmhyparser.info.dmhy.BangumiInfo;
import com.dmhyparser.parser.wiki.CombinedParser;

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

