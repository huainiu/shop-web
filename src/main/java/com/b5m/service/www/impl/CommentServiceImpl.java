package com.b5m.service.www.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b5m.bean.entity.Comment;
import com.b5m.common.env.GlobalInfo;
import com.b5m.dao.Dao;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.CommentService;
import com.b5m.service.www.thread.AbstractConsumerService;
import com.b5m.service.www.thread.MessageWrap;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-6-20
 * @email echo.weng@b5m.com
 */
@Service("commentService")
public class CommentServiceImpl extends AbstractConsumerService<Comment> implements CommentService{
	
	@Autowired
    @Qualifier("dao")
	private Dao dao;
    
    @Override
    public void addComment(Comment comment){
    	comment.setCreateTime(new Date());
    	comment.setUpdateTime(new Date());
        dao.insert(comment);
    }
    
    @Override
    public void updateComment(Comment comment) {
    	comment.setUpdateTime(new Date());
    	dao.update(comment);
    }
    
    @Override
    public Comment getComment(Long id){
        return dao.get(Comment.class, id);
    }

	@Override
	public List<Comment> getComments(List<Long> ids) {
		if(ids.isEmpty()) return new ArrayList<Comment>(0);
		return dao.query(Comment.class, Cnd.where("id", Op.IN, ids));
	}
	
	@Override
	public List<Long> queryIds(Cnd cnd) {
		return dao.query(Comment.class, "queryId", cnd, Long.class);
	}

	@Override
	public PageView<Comment> queryPage(Cnd cnd, PageCnd pageCnd) {
		return dao.queryPage(Comment.class, cnd, pageCnd);
	}

	@Override
	public void deleteComment(Comment comment) {
		dao.delete(comment);
	}
	
	@Override
	public PageView<Comment> queryPassedPage(int currentPage, int pageSize, Long suppliserId, Integer type) {
		if(type == -1) type = null;
		Cnd cnd = Cnd.where("supplierId", Op.EQ, suppliserId).add("type", Op.EQ, type).add("oper", Op.EQ, 1).desc("createTime");
		return dao.queryPage(Comment.class, cnd, new PageCnd(currentPage, pageSize));
	}
	
	@Override
	public int queryCommentCount(Long suppliserId, Integer type) {
		Cnd cnd = Cnd.where("supplierId", Op.EQ, suppliserId).add("type", Op.EQ, type).add("oper", Op.EQ, 1);
		return dao.queryCount(Comment.class, cnd);
	}
	
	@Override
	public Map<String, Integer> queryCountByType(Long suppliserId) {
		return new HashMap<String, Integer>();
	}

	@Override
	public Map<Long, Integer> queryCommentCount(List<Long> supplierIds, Integer type) {
		Map<Long, Integer> commentCountMap = new HashMap<Long, Integer>();
		for(Long supplierId : supplierIds){
			commentCountMap.put(supplierId, queryCommentCount(supplierId, type));
		}
		return commentCountMap;
	}

	@Override
	public Integer getThreadPoolSize() {
		return getSize(GlobalInfo.KEY_COMMENT_CONSUMER_THREAD_SIZE);
	}

	@Override
	public Integer getBlockingQueueSize() {
		return getSize(GlobalInfo.KEY_COMMENT_BLOCKING_QUEUE_SIZE);
	}

	@Override
	public void dealWith(MessageWrap<Comment> messageWrap) {
		switch (messageWrap.getOp()) {
		case INSERT:
			addComment(messageWrap.getMessage());
			break;
        case UPDATE:
        	updateComment(messageWrap.getMessage());
			break;

		default:
			break;
		}
	}

}
