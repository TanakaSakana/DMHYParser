package com.dmhyparser.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BANGUMI {
    private static HashMap<String, BangumiInfo> BangumiInfoList = new HashMap<>();
    private static List<Year> IndexList = new ArrayList<>();
    private static List<String> LinkList = new ArrayList<>();
    private static List<String> AnimeNameList = new ArrayList<>();


    public static HashMap<String, BangumiInfo> getBangumiInfoList() {
        return BangumiInfoList;
    }

    public static void setBangumiInfoList(HashMap<String, BangumiInfo> bangumiInfoList) {
        BangumiInfoList = bangumiInfoList;
    }

    public static List<Year> getIndexList() {
        return IndexList;
    }

    public static void setIndexList(List<Year> indexList) {
        IndexList = indexList;
    }

    public static List<String> getLinkList() {
        return LinkList;
    }

    public static void setLinkList(List<String> linkList) {
        LinkList = linkList;
    }

    public static void clearAll() {
        BangumiInfoList = new HashMap<>();
        IndexList = new ArrayList<>();
        LinkList = new ArrayList<>();
        AnimeNameList = new ArrayList<>();
    }

    public static void addtoLinkList(String r) {
        LinkList.add(r);
    }

    public static void addtoBangumiInfoList(HashMap<String, BangumiInfo> map) {
        BangumiInfoList.putAll(map);
    }

    public static void addtoBangumiInfoList(BangumiInfo item) {
        BangumiInfoList.put(item.getName(), item);
    }

    public static void addtoAnimeNameList(String s) {
        AnimeNameList.add(s);
    }
}
