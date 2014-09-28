package com.b5m.service.hbase;

import java.util.List;

import com.b5m.dao.domain.page.PageView;
import com.b5m.service.hbase.bean.CommentType;
import com.b5m.service.hbase.bean.HComment;


/**
 * @author echo
 * 获取hbase数据接口
 */
public interface HbaseDataService {
	
	String getProductDetail(String docId) throws Exception;

	PageView<HComment> queryCommentPage(String docId, CommentType type, Integer pageSize, Integer pageCode) throws Exception;
	
	long getCommentSize(String docId, CommentType type) throws Exception;

	List<String> getTag(String docId) throws Exception;
}
