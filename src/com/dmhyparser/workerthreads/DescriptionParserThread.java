package com.dmhyparser.workerthreads;

import com.dmhyparser.utilparser.CombinedParser;
import com.dmhyparser.info.BANGUMI;
import com.dmhyparser.info.BangumiInfo;

import java.util.concurrent.Callable;

public class DescriptionParserThread implements Callable<Integer> {
    BangumiInfo bangumiInfo;

    public DescriptionParserThread(BangumiInfo bangumiInfo) {
        this.bangumiInfo = bangumiInfo;
    }

    @Override
    public Integer call() throws Exception {
        String desc = CombinedParser.getDescriptionWithWIKI_COMBINED(bangumiInfo.getName());
        bangumiInfo.setDescription(desc);
        BANGUMI.getBangumiInfoList().put(bangumiInfo.getName(), bangumiInfo);
        return null;
    }
}

