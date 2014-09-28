package com.b5m.service.www;

import java.util.List;
import java.util.Map;

import com.b5m.bean.entity.Impress;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-7-25
 * @email echo.weng@b5m.com
 */
public interface ImpressService{
    
    /**
	 * 添加数据
	 * @param impress
	 */
    void addImpress(Impress impress);
    
    /**
     * 更新Impress数据
     * @param impress
     */
    void updateImpress(Impress impress);
    
    /**
     * 点击印象
     */
    void clickImpress(Long id);
    
    /**
     * 根据id 获取数据
     * @param id
     * @return
     */
    Impress getImpress(Long id);
    
    /**
     * 根据id 列表 到获取数据
     * @param ids
     * @return
     */
    List<Impress> getImpresss(List<Long> ids);
    
    /**
     * @param suppliserId
     * @return
     */
    int queryImpressCount(Long suppliserId);
    
    /**
     * @description
     * 多个supplierId进行获取
     * @param supplierIds
     * @return
     * @author echo
     * @since 2013-7-25
     * @email echo.weng@b5m.com
     */
    Map<Long, Integer> queryImpressCountMap(List<Long> supplierIds);

    /**
     * @param startIndex
     * @param suppliserId
     * @param params
     * @param orderBies
     * @return
     */
    List<Impress> queryImpress(int startIndex, int size, Long suppliserId);
    
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
     * @param currentPage
     * @param suppliserId
     * @param content
     * @return
     */
    PageView<Impress> queryPage(Cnd cnd, PageCnd pageCnd);
    
    /**
     * 查询通过的
     * @param startIndex
     * @param suppliserId
     * @return
     */
    List<Impress> queryPassedImpress(int startIndex, int size, Long suppliserId);
    
    void deleteImpress(Impress impress);
    
    Impress getImpressFromDb(Long id);

	List<Impress> queryImpressLessLength(int size, Long suppliserId);
}