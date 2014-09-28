package com.b5m.bean.dto.dianping;

import com.b5m.common.env.Constants;
import com.b5m.common.utils.shoplist.PageSpliter;

/**
 * 评论请求 分页信息封装
 * @author yuxiaolong
 *
 */
public class CommentSearchDto
{
    private int pageSize = Constants.COMMENT_PAGE_SIZE;
    
    private int currPageNo;
    
    private String keyword;
    
    private String docId;

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getCurrPageNo()
    {
        return currPageNo;
    }

    public void setCurrPageNo(int currPageNo)
    {
        this.currPageNo = currPageNo;
    }

    public int getOffSet()
    {
        return PageSpliter.getOffSet(currPageNo, pageSize);
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getDocId()
    {
        return docId;
    }

    public void setDocId(String docId)
    {
        this.docId = docId;
    }
    
}
