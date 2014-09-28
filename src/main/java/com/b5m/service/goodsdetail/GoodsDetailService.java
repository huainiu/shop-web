package com.b5m.service.goodsdetail;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.b5m.bean.dto.goodsdetail.GoodsDetailDataSetDto;
import com.b5m.bean.dto.goodsdetail.GoodsDetailSearchCndDto;
import com.b5m.bean.dto.goodsdetail.ShopInfoDto;
import com.b5m.bean.entity.ShopInfo;

public interface GoodsDetailService {
    
    /**
     * 获取商品的详细信息
     * @param docId
     */
    GoodsDetailDataSetDto getGoodsDetailInfo(String docId);
    
    /**
     * 获得SF1R产品数据
     */
    GoodsDetailDataSetDto getSF1RProduce(HttpServletRequest request, String docId, GoodsDetailSearchCndDto dto);
    
    /**
     * @description
     * 获取单个商家的商品信息
     * @param docId
     * @param cnd
     * @param source
     * @return
     * @author echo
     * @since 2013-10-17
     * @email echo.weng@b5m.com
     */
    List<Map<String, String>> getSF1RProduceSingleSource(HttpServletRequest request, String docId, GoodsDetailSearchCndDto cnd, String source);
    
    /**
     * description
     * 相关商品搜索
     * @param keyWord
     * @param docid
     * @param pageSize
     * @return
     * @author echo weng
     * @since 2013-6-4
     * @mail echo.weng@b5m.com
     */
    
    List<Map<String, String>> getGoodsDetailByDocId(String[] docIds);
    
    /**
     * 根据商家id查商品信息 
     * @description
     *
     * @param docId
     * @return
     * @author liuhaohuang
     * @since 2013-9-9
     * @email haohuang.liu@b5m.com
     */
	GoodsDetailDataSetDto getGoodsDetailInfoByDocId(HttpServletRequest request, String docId);
	
	/**
	 * @description
	 * 根据collection进行查询
	 * @param docId
	 * @param collection
	 * @return
	 * @author echo
	 * @since 2013-10-23
	 * @email echo.weng@b5m.com
	 */
	GoodsDetailDataSetDto getGoodsDetailInfoByDocId(HttpServletRequest request, String docId, String collection);
	/**
	 * @description
	 * 商品详情
	 * @param docid
	 * @return
	 * @author echo
	 * @since 2013-9-30
	 * @email echo.weng@b5m.com
	 */
	Map<String, String> getProduceBySf1r(String docid);
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 根据UUID获取图片
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年4月19日 下午2:29:58
	 *
	 * @param docId
	 * @param cnd
	 * @return
	 */
	List<Map<String, String>> getProduceImgs(String docId, GoodsDetailSearchCndDto cnd);
	
	/**
	 * 获取相关商品最低价格的20个商品信息
	 * @param docId
	 * @param cnd
	 * @param num
	 * @return
	 */
	List<Map<String, String>> getSF1RProduceLimit(HttpServletRequest request, String docId, GoodsDetailSearchCndDto cnd, Integer num);
	
	/**
	 * 获取相关商品
	 * @param docIdArray
	 * @param keywordArray
	 * @param count
	 * @return
	 */
	List<Map<String, String>> getRelateProduces(List<String> docIdArray, List<String> keywordArray, int count);
	
	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 多线程获取数据
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年5月9日 下午3:54:55
	 *
	 * @param isCompare
	 * @param docId
	 * @return
	 */
	Object[] queryDetailInfo(final String docId);

	ShopInfo getShopInfo(String name);

	/**
	 *<font style="font-weight:bold">Description: </font> <br/>
	 * 商家聚集
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年7月8日 下午4:48:30
	 *
	 * @param resources
	 * @param attr
	 * @return
	 */
	List<Map<String, String>> recommandProduces(String docid, String keyword, Integer pageSize, String attr, String fuzzy);


	List<ShopInfoDto> getProductGroupBySource(HttpServletRequest req, List<Map<String, String>> resources, String attr);

	List<Map<String, String>> recommandProduces(String docid, String keyword, Integer pageSize, String attr);

	List<ShopInfoDto> getRecommandProduces(HttpServletRequest req, String docid, String keyword, Integer pageSize, String attr);

	Map<String, String> getProduceBySf1r(String docid, String collection);

	Map<String, Object> getGoods(HttpServletRequest req, String docId, String col, Double scope);

}
