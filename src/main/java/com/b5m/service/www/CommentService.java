package com.b5m.service.www;

import java.util.List;
import java.util.Map;

import com.b5m.bean.entity.Comment;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-6-17
 * @email echo.weng@b5m.com
 */
public interface CommentService {

	/**
	 * 添加数据
	 * 
	 * @param comment
	 */
	void addComment(Comment comment);

    
    /**
     * 更新comment全部数据
     * @param comment
     */
    void updateComment(Comment comment);
    
    /**
     * 根据id 获取数据
     * @param id
     * @return
     */
    Comment getComment(Long id);
    
    /**
     * @param suppliserId
     * @return
     */
    int queryCommentCount(Long suppliserId, Integer type);
    
    /**
     * @description
     * 多个 supplierId 查询 
     * @param supplierIds
     * @param type
     * @return
     * @author echo
     * @since 2013-7-25
     * @email echo.weng@b5m.com
     */
    Map<Long, Integer> queryCommentCount(List<Long> supplierIds, Integer type);
    
    /**
     * @description
     * 根据条件进行查询评论Id
     * @param params
     * @return
     * @return List<Long>
     * @date 2013-6-23
     * @author xiuqing.weng
     */
    List<Long> queryIds(Cnd cnd);
    
    /**
     * 根据id 列表 到获取数据
     * @param ids
     * @return
     */
    List<Comment> getComments(List<Long> ids);
    
    /**
     * @param comment
     */
    void deleteComment(Comment comment);

    /**
     * @param cnd
     * @param pageCnd
     * @return
     */
    PageView<Comment> queryPage(Cnd cnd, PageCnd pageCnd);

	/**
	 * @param currentPage
	 * @param pageSize
	 * @param suppliserId
	 * @param type
	 * @return
	 */
    PageView<Comment> queryPassedPage(int currentPage, int pageSize, Long suppliserId, Integer type);

	Map<String, Integer> queryCountByType(Long suppliserId);
}
