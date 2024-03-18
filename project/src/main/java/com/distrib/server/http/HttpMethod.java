package com.distrib.server.http;

public enum HttpMethod {
    GET, HEAD;
    static final int MAX_LENGTH;

    static {
        int tempMaxLen = -1;
        for (HttpMethod method: values()){
            if(method.name().length() > tempMaxLen)
                tempMaxLen = method.name().length();
        }
        MAX_LENGTH = tempMaxLen;
    }

}
