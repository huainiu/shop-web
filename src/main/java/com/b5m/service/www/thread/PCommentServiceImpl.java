package com.b5m.service.www.thread;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b5m.bean.entity.Comment;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.CommentService;
/**
 * @Company B5M.com
 * @description
 * commentService 作为生产者，调用该service的方法 就会往队列里面添加
 * @author echo
 * @since 2013-6-17
 * @email echo.weng@b5m.com
 */
@Service("pCommentService")
public class PCommentServiceImpl implements CommentService{
    public static final Log LOG = LogFactory.getLog(PCommentServiceImpl.class);
    
    @Autowired
    @Qualifier("commentCacheService")
    private CommentService commentService;
    
    @Resource(name = "commentService")
    private ConsumerService<Comment> consumerService;
    
    @Override
    public void addComment(Comment comment){
        try{
        	consumerService.addMessage(comment, MessageOp.INSERT);
        }catch (InterruptedException e){
            LOG.error("exception type is " + e.getClass().getSimpleName() + ", error message:[" + e.getMessage() + "]");
        }
    }

	@Override
	public void updateComment(Comment comment) {
		commentService.updateComment(comment);
	}

	@Override
	public Comment getComment(Long id) {
		return commentService.getComment(id);
	}

	@Override
	public int queryCommentCount(Long suppliserId, Integer type) {
		return commentService.queryCommentCount(suppliserId, type);
	}

	@Override
	public Map<Long, Integer> queryCommentCount(List<Long> supplierIds,
			Integer type) {
		return commentService.queryCommentCount(supplierIds, type);
	}

	@Override
	public List<Long> queryIds(Cnd cnd) {
		return commentService.queryIds(cnd);
	}

	@Override
	public List<Comment> getComments(List<Long> ids) {
		return commentService.getComments(ids);
	}

	@Override
	public void deleteComment(Comment comment) {
		commentService.deleteComment(comment);
	}

	@Override
	public PageView<Comment> queryPage(Cnd cnd, PageCnd pageCnd) {
		return commentService.queryPage(cnd, pageCnd);
	}

	@Override
	public PageView<Comment> queryPassedPage(int currentPage, int pageSize,
			Long suppliserId, Integer type) {
		return commentService.queryPassedPage(currentPage, pageSize, suppliserId, type);
	}

	@Override
	public Map<String, Integer> queryCountByType(Long suppliserId) {
		return commentService.queryCountByType(suppliserId);
	}
    
}
