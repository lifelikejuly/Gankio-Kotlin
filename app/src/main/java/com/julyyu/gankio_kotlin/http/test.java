package com.julyyu.gankio_kotlin.http;

import java.util.List;

/**
 * Created by JulyYu on 2017/4/26.
 */

public class test {



    public boolean error;
    public List<String> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
