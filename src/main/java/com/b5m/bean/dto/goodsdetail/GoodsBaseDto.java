package com.b5m.bean.dto.goodsdetail;

import java.io.Serializable;

/**
 * 商品基本信息基类
 * @author yuxiaolong
 *
 */
public class GoodsBaseDto implements Serializable{

	private static final long serialVersionUID = 1L;

	//商品的docId
    protected String docId;
    
    //商家值 不知道有什么用，名称可以修改
    protected String sourceValue;
    //关键字
    protected String keywords;

    public String getDocId()
    {
        return docId;
    }

    public void setDocId(String docId)
    {
        this.docId = docId;
    }

    public String getSourceValue()
    {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue)
    {
        this.sourceValue = sourceValue;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }
    
}
