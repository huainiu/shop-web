package com.b5m.service.www.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b5m.base.common.utils.CollectionTools;
import com.b5m.bean.entity.Comment;
import com.b5m.cached.CachedBase;
import com.b5m.cached.CachedConfigure;
import com.b5m.cached.ICachedProxy;
import com.b5m.cached.exception.CachedException;
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.log.LogUtils;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.dao.domain.page.QueryResult;
import com.b5m.service.www.CommentService;
import com.b5m.service.www.utils.CacheUtils;
import com.b5m.service.www.utils.JsonUtils;

/**
 * @Company B5M.com
 * @description 评论缓存服务类
 * @author echo
 * @since 2013-6-18
 * @email echo.weng@b5m.com
 */
public class CommentCacheServiceImpl extends CachedBase implements CommentService {

	@Autowired
	@Qualifier("commentService")
	private CommentService commentService;

	public CommentCacheServiceImpl(ICachedProxy proxy, CachedConfigure configure) {
		super(proxy, configure);
	}

	@Override
    public Comment getComment(Long id){
        try{
           String json = proxy.get(CacheUtils.getCommentKey(id), String.class);
           if(StringUtils.isEmpty(json)){
               return commentService.getComment(id);
           }
           return JsonUtils.json2Comment(json);
        }catch (CachedException e){
            LogUtils.error(this.getClass(), e);
            return commentService.getComment(id);
        }
    }
    
    @Override
    public List<Comment> getComments(List<Long> ids){
    	List<Comment> comments = new ArrayList<Comment>();
    	List<Long> fromDbIds = new ArrayList<Long>(ids.size());
    	try{
    		List<String> keyCollections = new ArrayList<String>();
    		for(Long id : ids){
    			keyCollections.add(CacheUtils.getCommentKey(id));
    		}
    		Map<String, String> resultMap = proxy.gets(keyCollections, String.class);
    		for(Long id : ids){
    			String json = resultMap.get(CacheUtils.getCommentKey(id));
    			if(JsonUtils.isEmpty(json)){
        			fromDbIds.add(id);
        			continue;
        		}
    			comments.add(JsonUtils.json2Comment(json));
    		}
    	}catch (CachedException e){
            LogUtils.error(this.getClass(), e);
            List<Comment> commentList = commentService.getComments(ids);
            updateCaches(commentList);
            return commentList;
        }
    	if(!fromDbIds.isEmpty()){
    		List<Comment> commentdbList = commentService.getComments(fromDbIds);
    		updateCaches(commentdbList);
    		comments.addAll(commentdbList);
    	}
    	Collections.sort(comments, new Comparator<Comment>() {

			@Override
			public int compare(Comment c1, Comment c2) {
				long v = c2.getCreateTime().getTime() - c1.getCreateTime().getTime();
				if(v < 0) return -1;
				if(v == 0) return 0;
				else return 1;
			}
			
		});
    	return comments;
    }

    @Override
    public void updateComment(Comment comment){
    	Comment commentdb = commentService.getComment(comment.getId());
    	commentService.updateComment(comment);
    	if((commentdb.getOper() == GlobalInfo.OPER_UN_REVIEW || commentdb.getOper() == GlobalInfo.OPER_UN_PASS) && comment.getOper() == GlobalInfo.OPER_PASS){
    		addCache(comment);
    	}
    	if(commentdb.getOper() == GlobalInfo.OPER_PASS && comment.getOper() == GlobalInfo.OPER_UN_PASS){
    		removeCache(comment);
    	}
    	if(commentdb.getOper() == GlobalInfo.OPER_PASS && comment.getOper() == GlobalInfo.OPER_PASS){
    		updateCache(commentdb, comment);
    	}
    }
    
	public boolean load(Object... args) throws CachedException {
		return false;
	}

	@Override
	public boolean unload(Object... args) throws CachedException {
		return false;
	}

	@Override
	public void addComment(Comment comment) {
		commentService.addComment(comment);
		if (comment.getOper() == GlobalInfo.OPER_PASS) {
			addCache(comment);
		}
	}
	
	@Override
	public void deleteComment(Comment comment) {
		commentService.deleteComment(comment);
		if (comment.getOper() == 1) {// 表示通过
			removeCache(comment);
		}
	}

	@Override
	public List<Long> queryIds(Cnd cnd) {
		return commentService.queryIds(cnd);
	}

	@Override
	public PageView<Comment> queryPage(Cnd cnd, PageCnd pageCnd) {
		return commentService.queryPage(cnd, pageCnd);
	}

	@Override
	public PageView<Comment> queryPassedPage(int currentPage, int pageSize, Long suppliserId, Integer type) {
		if (currentPage < 1) currentPage = 1;
		if (type != null && type == -1) type = null;
		try {
			String json = null;
			if (type == null) {// -1表示全部
				json = proxy.getMaybeNull(CacheUtils.getAllCommentListKey(suppliserId), String.class);
			} else {
				json = proxy.getMaybeNull(CacheUtils.getCommentListKey(suppliserId, type), String.class);
			}
			if (!JsonUtils.isEmpty(json)) {
				List<Long> ids = JsonUtils.json2IdList(json);
				List<Long> displayIds = CollectionTools.subPage(ids, pageSize, currentPage);

				List<Comment> comments = getComments(displayIds);
				PageView<Comment> pageView = new PageView<Comment>(pageSize, currentPage);
				pageView.setPageCode(3);
				pageView.setQueryResult(new QueryResult<Comment>(comments, ids.size()));
				return pageView;
			} else {
				// 如果查询不到数据 memcache又没有报错，则表明memcache中没有数据 需要重新load一下
				Long expired = GlobalInfo.getCommImpreExpired();
				if (type == null) {
					List<Long> ids = commentService.queryIds(getAllPassCnd(suppliserId));
					proxy.put(CacheUtils.getAllCommentListKey(suppliserId), JsonUtils.idList2Json(ids), expired);
				} else {
					List<Long> ids = commentService.queryIds(getAllPassTypeCnd(suppliserId, type));
					proxy.put(CacheUtils.getCommentListKey(suppliserId, type), JsonUtils.idList2Json(ids), expired);
				}
				PageView<Comment> pageView = commentService.queryPassedPage(currentPage, pageSize, suppliserId, type);
				pageView.setPageCode(3);
				pageView.setTotalPage(pageView.getTotalPage());
				return pageView;
			}
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
			PageView<Comment> pageView = commentService.queryPassedPage(currentPage, pageSize, suppliserId, type);
			pageView.setPageCode(3);
			pageView.setTotalPage(pageView.getTotalPage());
			return pageView;
		}
	}
	
	@Override
	public int queryCommentCount(Long supplierId, Integer type) {
		if(type != null && type == -1) type = null;
		try{
			String json = null;
			if(type == null){//-1表示全部
				json = proxy.getMaybeNull(CacheUtils.getAllCommentListKey(supplierId), String.class);
			}else{
				json = proxy.getMaybeNull(CacheUtils.getCommentListKey(supplierId, type), String.class);
			}
			if(!JsonUtils.isEmpty(json)){
				List<Long> ids = JsonUtils.json2IdList(json);
				return ids.size();
			}
			Cnd cnd = Cnd.where("supplierId", Op.EQ, supplierId).add("type", Op.EQ, type).add("oper", Op.EQ, 1).desc("createTime");
			List<Long> ids = commentService.queryIds(cnd);
			String key = null;
			if(type == null){
				key = CacheUtils.getAllCommentListKey(supplierId);
			}else{
				key = CacheUtils.getCommentListKey(supplierId, type);
			}
			proxy.put(key, JsonUtils.idList2Json(ids), GlobalInfo.getCommImpreExpired());
			return commentService.queryCommentCount(supplierId, type);
		}catch (CachedException e) {
			return commentService.queryCommentCount(supplierId, type);
		}
	}
	
	@Override
	public Map<Long, Integer> queryCommentCount(List<Long> supplierIds, Integer type){
		if(type != null && type == -1) type = null;
		List<String> keyCollections = new ArrayList<String>();
		for(Long supplierId : supplierIds){
			if(type == null){
				keyCollections.add(CacheUtils.getAllCommentListKey(supplierId));
			}else{
				keyCollections.add(CacheUtils.getCommentListKey(supplierId, type));
			}
		}
		try {
			Map<Long, Integer> commentCountMap = new HashMap<Long, Integer>();
			Map<String, String> map = proxy.gets(keyCollections, String.class);
			for(Long supplierId : supplierIds){
				String key = null;
				if(type != null){
					key = CacheUtils.getCommentListKey(supplierId, type);
				}else{
					key = CacheUtils.getAllCommentListKey(supplierId);
				}
				String json = map.get(key);
				if(!JsonUtils.isEmpty(json)){
					List<Long> ids = JsonUtils.json2IdList(json);
					commentCountMap.put(supplierId, ids.size());
				}else{
					Cnd cnd = Cnd.where("supplierId", Op.EQ, supplierId).add("type", Op.EQ, type).add("oper", Op.EQ, 1).desc("createTime");
					List<Long> ids = commentService.queryIds(cnd);
					proxy.put(key, JsonUtils.idList2Json(ids), GlobalInfo.getCommImpreExpired());
					commentCountMap.put(supplierId, commentService.queryCommentCount(supplierId, type));
				}
			}
			return commentCountMap;
		} catch (CachedException e) {
			return commentService.queryCommentCount(supplierIds, type);
		}
	}

	@Override
	public Map<String, Integer> queryCountByType(Long suppliserId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int[] typeNo = new int[] { 0, 1, 2 };
		int count = 0;
		try {
			for (int i = 0; i < typeNo.length; i++) {
				map.put("comment_" + i, 0);
				String json = null;
				json = proxy.getMaybeNull(CacheUtils.getCommentListKey(suppliserId, i), String.class);
				if (!StringUtils.isEmpty(json)) {
					List<Long> ids = JsonUtils.json2IdList(json);
					map.put("comment_" + i, ids.size());
					count = count + ids.size();
				} else {
					// 如果查询不到数据 memcache又没有报错，则表明memcache中没有数据 需要重新load一下
					Long expired = GlobalInfo.getCommImpreExpired();
					List<Long> ids = commentService.queryIds(getAllPassTypeCnd(suppliserId, i));
					proxy.put(CacheUtils.getCommentListKey(suppliserId, i), JsonUtils.idList2Json(ids), expired);
					map.put("comment_" + i, ids.size());
					count = count + ids.size();
				}
			}
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
		}
		map.put("comment_" + -1, count);
		return map;
	}
	
	protected void addCache(Comment comment) {
		try {
			proxy.put(CacheUtils.getCommentKey(comment.getId()), JsonUtils.comment2Json(comment), configure.getExpiredTime());
			reload(comment.getSupplierId(), null);
			reload(comment.getSupplierId(), comment.getType());
		} catch (Exception e) {
			LogUtils.error(this.getClass(), e);
		}
	}

	protected void updateCache(Comment commentdb, Comment comment) {
		try {
			if(commentdb.getType() != comment.getType()){
				removeCache(commentdb);
				addCache(comment);
			}else{
				proxy.put(CacheUtils.getCommentKey(comment.getId()), JsonUtils.comment2Json(comment), configure.getExpiredTime());
			}
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
		}
	}

	protected void removeCache(Comment comment) {
		try {
			proxy.remove(CacheUtils.getCommentKey(comment.getId()));
		} catch (Exception e) {
			LogUtils.error(this.getClass(), e);
		}
		reload(comment.getSupplierId(), null);
		reload(comment.getSupplierId(), comment.getType());
	}
	
	protected void reload(Long supplierId, Integer type){
		if(type == null){
			try {
				List<Long> ids = commentService.queryIds(getAllPassCnd(supplierId));
				proxy.put(CacheUtils.getAllCommentListKey(supplierId), JsonUtils.idList2Json(ids), GlobalInfo.getCommImpreExpired());
			} catch (CachedException e) {
				LogUtils.error(this.getClass(), e);
			}
		}else{
			try {
				List<Long> typeIds = commentService.queryIds(getAllPassTypeCnd(supplierId, type));
				proxy.put(CacheUtils.getCommentListKey(supplierId, type), JsonUtils.idList2Json(typeIds), GlobalInfo.getCommImpreExpired());
			} catch (CachedException e) {
				LogUtils.error(this.getClass(), e);
			}
		}
	}

	protected void updateCaches(List<Comment> commentList){
		try {
			for(Comment comment : commentList){
				proxy.put(CacheUtils.getCommentKey(comment.getId()), JsonUtils.comment2Json(comment), configure.getExpiredTime());
			}
		} catch (CachedException e) {
			LogUtils.error(this.getClass(), e);
		}
	}
	
	protected Cnd getAllPassCnd(Long supplierId){
		Cnd cnd = Cnd.where("supplierId", Op.EQ, supplierId);
		cnd.add("oper", Op.EQ, 1).desc("createTime");
		return cnd;
	}
	
	protected void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	protected Cnd getAllPassTypeCnd(Long supplierId, int type){
		Cnd cnd = getAllPassCnd(supplierId);
		cnd.add("type", Op.EQ, type);
		return cnd;
	}
	
}
