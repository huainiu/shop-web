package com.b5m.goods.search;

import java.io.IOException;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.mchange.v1.io.InputStreamUtils;

public class BigJsonApp
{
    private String jsonStr;

    public BigJsonApp() throws IOException
    {
        jsonStr = InputStreamUtils.getContentsAsString(this.getClass().getResourceAsStream("/com/b5m/goods/search/subdoc.json"));
    }

    @Test
    public void test()
    {
        JSONObject.parse(jsonStr);
    }

}
