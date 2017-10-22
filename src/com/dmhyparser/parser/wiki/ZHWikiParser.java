package com.dmhyparser.parser.wiki;

import com.dmhyparser.parser.BaseWikiParser;
import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZHWikiParser implements BaseWikiParser {
	
    @Override
    public String parse(String query) throws Exception {
		query = java.net.URLEncoder.encode(query,"UTF-8");
        String jsonres = "";

        String temp = "https://zh.wikipedia.org/w/api.php?action=opensearch&search=%s&limit=10&namespace=0&format=json";
        String opt = String.format(temp, query);
        try {
            jsonres = unicodeToString(com.dmhyparser.parser.helper.BaseParser.readFromHTTP(opt));
        } catch (Exception e) {
            System.out.println("parse HTTP response error");
        }
        if (jsonres.indexOf("[") != -1) {
            JSONArray input = new JSONArray(jsonres.substring(jsonres.indexOf("[")));
            // Total Array Length
            if (input.get(2).toString().length() > 20) {
                String response = input.get(2).toString();
                JSONArray arr = new JSONArray(response);
                if (arr.length() > 1) {
                    for (int i = 0; i < arr.length(); i++) {
                        // Query row length
                        if (arr.get(i).toString().length() > 10 && (arr.get(i).toString().indexOf("簡繁重定向")==-1)) 
                            //if (arr.get(i).toString().indexOf("動畫") != -1 || arr.get(i).toString().indexOf("漫畫") != -1 || arr.get(i).toString().indexOf("日语") != -1)
							return arr.get(i).toString();
                    }
                }
            }
        }
        return null;
    }
	

static String getUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
                 
            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
public static String unicodeToString(String str) {
 
    Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
    Matcher matcher = pattern.matcher(str);
    char ch;
    while (matcher.find()) {
        ch = (char) Integer.parseInt(matcher.group(2), 16);
        str = str.replace(matcher.group(1), ch + "");    
    }
    return str;
}
}
