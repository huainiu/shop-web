package com.b5m.service.www.thread;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b5m.bean.entity.Impress;
import com.b5m.common.log.LogUtils;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.ImpressService;
/**
 * @Company B5M.com
 * @description
 * commentService 作为生产者，调用该service的方法 就会往队列里面添加
 * @author echo
 * @since 2013-6-17
 * @email echo.weng@b5m.com
 */
@Service("pImpressService")
public class PImpressServiceImpl implements ImpressService{
    public static final Log LOG = LogFactory.getLog(PCommentServiceImpl.class);
    @Autowired
    @Qualifier("impressCacheService")
    private ImpressService impressService;
    
    @Resource(name = "impressService")
    private ConsumerService<Impress> consumerService;
    
    @Override
    public void addImpress(Impress impression){
        try{
        	consumerService.addMessage(impression, MessageOp.INSERT);
        }catch (InterruptedException e){
        	LogUtils.error(this.getClass(), e);
        }
    }

    @Override
    public void updateImpress(Impress impression){
        try{
        	consumerService.addMessage(impression, MessageOp.UPDATE);
        }catch (InterruptedException e){
        	LogUtils.error(this.getClass(), e);
        }
    }
    
    @Override
	public void clickImpress(Long id) {
    	try{
    		Impress impress = new Impress();
    		impress.setId(id);
    		consumerService.addMessage(impress, MessageOp.CLICK);
        }catch (InterruptedException e){
        	LogUtils.error(this.getClass(), e);
        }
	}

	@Override
	public Impress getImpress(Long id) {
		return impressService.getImpress(id);
	}

	@Override
	public List<Impress> getImpresss(List<Long> ids) {
		return impressService.getImpresss(ids);
	}

	@Override
	public void deleteImpress(Impress impress) {
		impressService.deleteImpress(impress);
	}

	@Override
	public List<Long> queryIds(Cnd cnd) {
		return impressService.queryIds(cnd);
	}

	@Override
	public PageView<Impress> queryPage(Cnd cnd, PageCnd pageCnd) {
		return impressService.queryPage(cnd, pageCnd);
	}

	@Override
	public List<Impress> queryImpress(int startIndex, int size, Long suppliserId) {
		return impressService.queryImpress(startIndex, size, suppliserId);
	}

	@Override
	public List<Impress> queryPassedImpress(int startIndex, int size, Long suppliserId) {
		return impressService.queryPassedImpress(startIndex, size, suppliserId);
	}

	@Override
	public int queryImpressCount(Long suppliserId) {
		return impressService.queryImpressCount(suppliserId);
	}

	@Override
	public Impress getImpressFromDb(Long id) {
		return impressService.getImpressFromDb(id);
	}

	@Override
	public List<Impress> queryImpressLessLength(int size, Long suppliserId) {
		return impressService.queryImpressLessLength(size, suppliserId);
	}

	@Override
	public Map<Long, Integer> queryImpressCountMap(List<Long> supplierIds) {
		return impressService.queryImpressCountMap(supplierIds);
	}

}
