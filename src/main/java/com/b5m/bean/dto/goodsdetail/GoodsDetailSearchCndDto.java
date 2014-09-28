package com.b5m.bean.dto.goodsdetail;


/**
 * 商品详细信息查询
 * 
 * @author yuxiaolong
 * 
 */
public class GoodsDetailSearchCndDto extends GoodsBaseDto{
    /**
     * 升序
     */
    public static final String ASC = "ASC";

    /**
     * 降序
     */
    public static final String DESC = "DESC";

    private String sortField;

    private String sortType = ASC;

    public String getSortField()
    {
        return sortField;
    }

    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }

    public String getSortType()
    {
        return sortType;
    }

    public void setSortType(String sortType)
    {
        this.sortType = sortType;
    }

}
