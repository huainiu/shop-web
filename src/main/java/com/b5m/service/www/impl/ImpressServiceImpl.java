package com.b5m.service.www.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b5m.bean.entity.Impress;
import com.b5m.common.env.GlobalInfo;
import com.b5m.dao.Dao;
import com.b5m.dao.Trans;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.ImpressService;
import com.b5m.service.www.thread.AbstractConsumerService;
import com.b5m.service.www.thread.MessageWrap;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-6-17
 * @email echo.weng@b5m.com
 */
@Service("impressService")
public class ImpressServiceImpl extends AbstractConsumerService<Impress> implements ImpressService{
	@Autowired
	@Qualifier("dao")
	private Dao dao;

    @Override
    public void addImpress(Impress impress){
    	List<Impress> impressdbs = dao.query(Impress.class, Cnd.where("content", Op.EQ, impress.getContent()));
    	if(!impressdbs.isEmpty()){
    		Impress impressdb = impressdbs.get(0);
    		impressdb.setImpressCount(impressdb.getImpressCount() + 1);
    		impressdb.setIsDelete(0);
    		dao.update(impressdb);
    	}else{
    		dao.insert(impress);
    	}
    }

    @Override
    public void updateImpress(Impress impress){
    	dao.update(impress);
    }

	@Override
	public Impress getImpress(Long id) {
		return dao.get(Impress.class, id);
	}

	@Override
	public List<Impress> getImpresss(List<Long> ids) {
		if(ids.isEmpty()) return new ArrayList<Impress>(0);
		return dao.query(Impress.class, Cnd.where("id", Op.IN, ids).desc("impressCount"));
	}
	
	@Override
	public List<Long> queryIds(Cnd cnd) {
		return dao.query(Impress.class, "queryId", cnd, Long.class);
	}

	@Override
	public PageView<Impress> queryPage(Cnd cnd, PageCnd pageCnd){
		return dao.queryPage(Impress.class, cnd, pageCnd);
	}

	@Override
	public void deleteImpress(Impress impress) {
		dao.delete(impress);
	}

	@Override
	public List<Impress> queryImpress(int startIndex, int size, Long suppliserId) {
		Cnd cnd = Cnd.where("oper", Op.EQ, 1).add("supplierId", Op.EQ, suppliserId).desc("impressCount");
		return dao.query(Impress.class, startIndex, size, cnd);
	}

	@Override
	public void clickImpress(final Long id) {
		Trans._exe(new Runnable() {
			@Override
			public void run() {
				Impress impress = dao.get(Impress.class, id);
				impress.setImpressCount(impress.getImpressCount() + 1);
				dao.update(impress);
			}
		});
	}

	@Override
	public List<Impress> queryPassedImpress(int startIndex, int size, Long suppliserId) {
		Cnd cnd = Cnd.where("oper", Op.EQ, 1).add("supplierId", Op.EQ, suppliserId).desc("impressCount");
		return dao.query(Impress.class, startIndex, size, cnd);
	}

	@Override
	public int queryImpressCount(Long suppliserId) {
		return dao.queryCount(Impress.class, Cnd.where("supplierId", Op.EQ, suppliserId).add("oper", Op.EQ, 1));
	}

	@Override
	public Impress getImpressFromDb(Long id) {
		return dao.get(Impress.class, id);
	}

	@Override
	public List<Impress> queryImpressLessLength(int size, Long suppliserId) {
		return null;
	}

	@Override
	public Map<Long, Integer> queryImpressCountMap(List<Long> supplierIds) {
		Map<Long, Integer> impressCountMap = new HashMap<Long, Integer>(10);
		for(Long supplierId : supplierIds){
			impressCountMap.put(supplierId, queryImpressCount(supplierId));
		}
		return impressCountMap;
	}

	@Override
	public Integer getThreadPoolSize() {
		return getSize(GlobalInfo.KEY_IMPRESSION_CONSUMER_THREAD_SIZE);
	}

	@Override
	public Integer getBlockingQueueSize() {
		return getSize(GlobalInfo.KEY_IMPRESSION_BLOCKING_QUEUE_SIZE);
	}

	@Override
	public void dealWith(MessageWrap<Impress> messageWrap) {
		switch (messageWrap.getOp()) {
		case INSERT:
		    addImpress(messageWrap.getMessage());
			break;
			
        case UPDATE:
            updateImpress(messageWrap.getMessage());
			break;

        case CLICK:
        	clickImpress(messageWrap.getMessage().getId());
		default:
			break;
		}
	}
	
}
