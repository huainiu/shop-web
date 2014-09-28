package com.b5m.service.www.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.b5m.base.common.utils.DateTools;
import com.b5m.bean.entity.Comment;
import com.b5m.cached.CachedConfigure;
import com.b5m.cached.ICachedProxy;
import com.b5m.cached.exception.CachedException;
import com.b5m.common.env.GlobalInfo;
import com.b5m.dao.domain.page.PageView;
import com.b5m.dao.domain.page.QueryResult;
import com.b5m.service.www.CommentService;
import com.b5m.service.www.utils.CacheUtils;
import com.b5m.service.www.utils.JsonUtils;

public class CommentCacheServiceImplApp {
	private CommentCacheServiceImpl cacheServiceImpl;
	private ICachedProxy proxy;
	private CachedConfigure configure;
	private CommentService commentService;
	
	@Before
	public void setUp() throws IOException{
		proxy = EasyMock.createMock(ICachedProxy.class);
		configure = new CachedConfigure();
		commentService = EasyMock.createMock(CommentService.class);
		cacheServiceImpl = new CommentCacheServiceImpl(proxy, configure);
		cacheServiceImpl.setCommentService(commentService);
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sysConfig.properties");
		GlobalInfo.load(inputStream);
	}
	
	@Test
	public void testGetComment() throws CachedException{
		Comment expected = new Comment();
		expected.setId(1L);
		String commentJson = JsonUtils.comment2Json(expected);
		EasyMock.expect(proxy.get(CacheUtils.getCommentKey(1L), String.class)).andReturn(commentJson);
		
		EasyMock.replay(proxy);
		
		Comment actual = cacheServiceImpl.getComment(1L);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetComments() throws CachedException{
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		ids.add(2L);
		Comment expected1 = new Comment();
		expected1.setId(1L);
		expected1.setCreateTime(new Date());
		
		Comment expected2 = new Comment();
		expected2.setId(2L);
		expected2.setCreateTime(DateTools.addDay(new Date(), 1));
		List<String> keyCollections = new ArrayList<String>();
		keyCollections.add(CacheUtils.getCommentKey(1L));
		keyCollections.add(CacheUtils.getCommentKey(2L));
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(CacheUtils.getCommentKey(1L), JsonUtils.comment2Json(expected1));
		resultMap.put(CacheUtils.getCommentKey(2L), JsonUtils.comment2Json(expected2));
		EasyMock.expect(proxy.gets(keyCollections, String.class)).andReturn(resultMap);
		
		EasyMock.replay(proxy);
		List<Comment> expected = cacheServiceImpl.getComments(ids);
		List<Comment> actual = new ArrayList<Comment>();
		actual.add(expected2);
		actual.add(expected1);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetCommentsFromdb() throws CachedException{
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		
		Comment[] comments = getTestGetCommentsFromdbData();
		List<String> list = new ArrayList<String>();
		list.add(CacheUtils.getCommentKey(1L));
		list.add(CacheUtils.getCommentKey(2L));
		list.add(CacheUtils.getCommentKey(3L));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CacheUtils.getCommentKey(1L), JsonUtils.comment2Json(comments[0]));
		map.put(CacheUtils.getCommentKey(2L), JsonUtils.comment2Json(comments[1]));
		map.put(CacheUtils.getCommentKey(3L), JsonUtils.comment2Json(comments[2]));
		EasyMock.expect(proxy.gets(list)).andReturn(map);
		proxy.put(CacheUtils.getCommentKey(3l), JsonUtils.comment2Json(comments[2]), configure.getExpiredTime());
		EasyMock.expectLastCall();
		
		List<Long> fromDbIds = new ArrayList<Long>();
		fromDbIds.add(3L);
		List<Comment> value = new ArrayList<Comment>();
		value.add(comments[2]);
		EasyMock.expect(commentService.getComments(fromDbIds)).andReturn(value);
		
		EasyMock.replay(proxy, commentService);
		List<Comment> expected = cacheServiceImpl.getComments(ids);
		List<Comment> actual = new ArrayList<Comment>();
		actual.add(comments[2]);
		actual.add(comments[1]);
		actual.add(comments[0]);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateComment() throws CachedException{
		Comment needUpdateComment = new Comment();
		needUpdateComment.setId(1l);
		needUpdateComment.setSupplierId(1l);
		needUpdateComment.setType(1);
		needUpdateComment.setOper(GlobalInfo.OPER_PASS);
		
		Comment commentdb = new Comment();
		commentdb.setId(1l);
		needUpdateComment.setType(1);
		commentdb.setOper(GlobalInfo.OPER_UN_REVIEW);
		commentdb.setSupplierId(1l);
		EasyMock.expect(commentService.getComment(1L)).andReturn(commentdb);		
		commentService.updateComment(needUpdateComment);
		
		proxy.put(CacheUtils.getCommentKey(1l), JsonUtils.comment2Json(needUpdateComment), configure.getExpiredTime());
		EasyMock.expectLastCall();
		
		List<Long> ids = new ArrayList<Long>();
		ids.add(1l);
		ids.add(2l);
		EasyMock.expect(commentService.queryIds(cacheServiceImpl.getAllPassCnd(1l))).andReturn(ids);
		proxy.put(CacheUtils.getAllCommentListKey(1l), JsonUtils.idList2Json(ids), GlobalInfo.getCommImpreExpired());
		EasyMock.expectLastCall();
		
		EasyMock.expect(commentService.queryIds(cacheServiceImpl.getAllPassTypeCnd(1l, 1))).andReturn(ids);
		proxy.put(CacheUtils.getCommentListKey(1l, 1), JsonUtils.idList2Json(ids), GlobalInfo.getCommImpreExpired());
		EasyMock.expectLastCall();
		
		EasyMock.replay(proxy, commentService);
		cacheServiceImpl.updateComment(needUpdateComment);
	}
	
	@Test
	public void testQueryPassedPage() throws CachedException{
		List<Long> ids = getPassedPageIdsData();
		String json = JsonUtils.idList2Json(ids);
		EasyMock.expect(proxy.getMaybeNull(CacheUtils.getCommentListKey(1l, 1), String.class)).andReturn(json);
		
		List<Comment> comments = getPassedPageCommentData();
		for(long i = 0; i < 10; i++){
			EasyMock.expect(proxy.getMaybeNull(CacheUtils.getCommentKey(i+1), String.class)).andReturn(JsonUtils.comment2Json(comments.get((int)i)));
		}
		
		EasyMock.replay(proxy);
		PageView<Comment> actual = cacheServiceImpl.queryPassedPage(1, 10, 1l, 1);
		PageView<Comment> expected = new PageView<Comment>(10, 1);
		expected.setQueryResult(new QueryResult<Comment>(comments, ids.size()));
		
		Assert.assertEquals(expected, actual);
	}
	
	private List<Long> getPassedPageIdsData(){
		List<Long> ids = new ArrayList<Long>(18);
		ids.add(1l);
		ids.add(2l);
		ids.add(3l);
		ids.add(4l);
		ids.add(5l);
		ids.add(6l);
		ids.add(7l);
		ids.add(8l);
		ids.add(9l);
		ids.add(10l);
		ids.add(11l);
		ids.add(12l);
		ids.add(13l);
		ids.add(14l);
		ids.add(15l);
		ids.add(16l);
		ids.add(17l);
		return ids;
	}
	
	private List<Comment> getPassedPageCommentData(){
		List<Comment> comments = new ArrayList<Comment>();
		for(long i = 0; i < 10; i++){
			comments.add(newComment(i));
		}
		return comments;
	}
	
	private Comment newComment(Long id){
		Comment comment = new Comment();
		comment.setId(id);
		comment.setCreateTime(new Date());
		return comment;
	}
	
	private Comment[] getTestGetCommentsFromdbData(){
		Comment[] comments = new Comment[3];
		Comment expected1 = new Comment();
		expected1.setId(1L);
		expected1.setCreateTime(new Date());
		
		Comment expected2 = new Comment();
		expected2.setId(2L);
		expected2.setCreateTime(DateTools.addDay(new Date(), 1));
		
		Comment expected3 = new Comment();
		expected3.setId(3L);
		expected3.setCreateTime(DateTools.addDay(new Date(), 2));
		
		comments[0] = expected1;
		comments[1] = expected1;
		comments[2] = expected1;
		
		return comments;
	}
	
	@Test
	public void testCnd(){
		Assert.assertEquals(cacheServiceImpl.getAllPassCnd(1l), cacheServiceImpl.getAllPassCnd(1l));
	}
	
	@After
	public void tearDown(){
		cacheServiceImpl = null;
		proxy = null;
		configure = null;
	}
}
