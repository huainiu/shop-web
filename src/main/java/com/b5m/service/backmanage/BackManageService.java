package com.b5m.service.backmanage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.b5m.bean.dto.dianping.BackManageCnd;
import com.b5m.bean.dto.dianping.OperationDto;
import com.b5m.bean.entity.Comment;
import com.b5m.bean.entity.Impress;
import com.b5m.bean.entity.Info;
import com.b5m.bean.entity.Key;
import com.b5m.bean.entity.Link;
import com.b5m.bean.entity.Meta;
import com.b5m.bean.entity.SearchHotKey;
import com.b5m.bean.entity.SearchRecommend;
import com.b5m.bean.entity.Suppliser;
import com.b5m.bean.entity.Word;
import com.b5m.dao.domain.page.PageView;
import com.b5m.goods.promotions.dto.SuppliserDto;
/**
 * @description
 * 供后台点评管理调用
 * @author echo
 * @since 2013-06-22
 */
public interface BackManageService {
	String login(HttpServletRequest request, ModelMap model, String userName, String password, String ip);
	
	String listSuppliser(ModelMap model, Integer currentPageNum);
	
	String addSuppliser(Suppliser suppliser);
	
	String editSuppliser(Suppliser suppliser);
	
	String removeSuppliser(Long id);
	
	List<SuppliserDto> searchSupplier(String keyword);
	
	/**
	 * 商家审核页面
	 * @description
	 *
	 * @param model
	 * @param suppliserId
	 * @param currentPage
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @return void
	 * @date 2013-6-22
	 * @author xiuqing.weng
	 */
	void listImpress(ModelMap model, BackManageCnd backManageCnd);
	
	Map<String, Object> impressDel(ModelMap model, Long id);
	
	void impressDetail(ModelMap model, Long id, Long supplierId);
	
	Map<String, Object> impressEdit(ModelMap model, Impress impress);
	
	Map<String, Object> impressAudit(ModelMap model, String id, boolean isPassed);
	
	void listComment(ModelMap model, BackManageCnd backManageCnd);
	
	void commentDetail(ModelMap model, Long id, Long supplierId);
	
	Map<String, Object> commentDel(ModelMap model, Long id);
	
	Map<String, Object> commentEdit(ModelMap model, Comment comment);
	
	Map<String, Object> commentAudit(ModelMap model, String id, boolean isPassed);
	
	void supplierDetail(ModelMap model, Long id);
	
	Map<String, Object> supplierDel(ModelMap model, Long id);
	
	Map<String, Object> supplierEdit(ModelMap model, Suppliser supplier);
	
	OperationDto uploadFile(MultipartFile file);
	
	OperationDto cutImageAndSave(String fileName, Integer x, Integer y, Integer width, Integer height);
	
	OperationDto deleteImage(String fileName);

	Map<String, Object> wordEdit(Word word);

	Map<String, Object> wordRemove(Long id);

	void wordList(ModelMap model,Integer currentPage, String word);

	void wordDetail(ModelMap model, Long id);

	boolean saveCategory(String content);

	void initCategory();

	PageView<SearchRecommend> getRecommends(String startDate, String endDate, Integer currentPage);

	void exportRecommend(HttpServletResponse response, String startDate, String endDate, String fileName) throws Exception;

	int getConfig() throws Exception;

	Boolean saveConfig(Integer value) throws Exception;

	List<SearchHotKey> getPageConfig() throws Exception;

	Boolean savePageConfig(String value);

	Boolean deletePageConfig(String key);

	Info getInfo(Long id);

	Long saveInfo(String title, String content, Long id);

	Boolean deleteInfo(Long id);

	PageView<Info> queryInfos(Integer currentPage, Integer pageSize, String keyword);

	PageView<Key> queryKeys(Integer currentPage, Integer pageSize, String keyword);

	Key getKey(Long id);

	Long saveKey(String name, Long id);

	Boolean deleteKey(Long id);

	PageView<Link> queryLinks(Integer currentPage, Integer pageSize, String keyword);

	Link getLink(Long id);

	Long saveLink(String name, String link, Long id);

	PageView<Meta> queryMetas(Integer currentPage, Integer pageSize);

	Meta getMeta(Long id);

	Long saveMeta(String pageName, String title, String des, String keyword, Long id);

}
