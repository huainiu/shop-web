package com.b5m.service.backmanage.impl;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.UrlTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.dto.dianping.BackManageCnd;
import com.b5m.bean.dto.dianping.OperationDto;
import com.b5m.bean.entity.BaseSupplierWrap;
import com.b5m.bean.entity.Category;
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
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.log.LogUtils;
import com.b5m.common.utils.ImageUtils;
import com.b5m.common.utils.MemCachedUtils;
import com.b5m.dao.Dao;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.dao.domain.page.QueryResult;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.ISuppliserService;
import com.b5m.service.backmanage.BackManageService;
import com.b5m.service.pricetrend.PriceTrendUtils;
import com.b5m.service.topic.impl.TopicSEOServiceImpl;
import com.b5m.service.www.CommentService;
import com.b5m.service.www.ImpressService;
import com.b5m.service.www.SearchRecommendService;
import com.b5m.service.www.SuppliserService;
import com.b5m.service.www.WordService;
import com.b5m.web.filter.CommInfoSetFilter;

/**
 * @author echo
 * @since 2013-06-22
 */
@Service("backManageService")
public class BackManageServiceImpl implements BackManageService {

	private static String UPLOAD_IMAGE_PATH = "/opt/cdn-image/upload/www/image/logo";

	private static String IMAGE_URL_PREFIX = "http://cdn.bang5mai.com/upload/web/www/image/logo";

	private String CATEGORY_URL_FORMAT = "search/s/{0}___image_____true___________.html";

	// 原来的supplier的service
	@Autowired
	@Qualifier("suppliserCachedService")
	private ISuppliserService supplierService;

	@Autowired
	@Qualifier("newSuppliserCacheService")
	private SuppliserService suppliserService;

	@Autowired
	@Qualifier("impressCacheService")
	private ImpressService impressService;

	@Autowired
	@Qualifier("commentCacheService")
	private CommentService commentService;

	@Autowired
	@Qualifier("wordServiceImpl")
	private WordService wordService;

	@Autowired
	@Qualifier("searchRecommendService")
	private SearchRecommendService searchRecommendService;

	@Autowired
	private Dao dao;

	@Override
	public String login(HttpServletRequest request, ModelMap model, String userName, String password, String ip) {
		// 登录成功，查看所有的评论
		return "redirect:/backmanager/impress/audit/page.htm";
	}

	@Override
	public String listSuppliser(ModelMap model, Integer currentPage) {
		if (currentPage == null)
			currentPage = 1;
		List<Suppliser> list = suppliserService.listSuppliser();
		List<Suppliser> subList = CollectionTools.subPage(list, GlobalInfo.getBackManageListPageSize(), currentPage);
		PageView<Suppliser> pageView = new PageView<Suppliser>(GlobalInfo.getBackManageListPageSize(), currentPage);
		pageView.setQueryResult(new QueryResult<Suppliser>(subList, list.size()));
		model.put("pageView", pageView);
		return "/backmanager/supplier/supplier";
	}

	@Override
	public List<SuppliserDto> searchSupplier(String keyword) {
		List<SuppliserDto> suppliserDtos = supplierService.querySupplier(keyword);
		return suppliserDtos;
	}

	@Override
	public String addSuppliser(Suppliser suppliser) {
		suppliserService.saveSuppliser(suppliser);
		return "redirect:suppliser.htm";
	}

	@Override
	public String editSuppliser(Suppliser suppliser) {
		suppliserService.saveSuppliser(suppliser);
		return "redirect:suppliser.htm";
	}

	@Override
	public String removeSuppliser(Long id) {
		suppliserService.removeSuppliser(id);
		return "redirect:suppliser.htm";
	}

	@Override
	public void listImpress(ModelMap model, BackManageCnd backManageCnd) {
		Integer currentPage = backManageCnd.getCurrentPage();
		String keyword = backManageCnd.getKeyword();
		Long supplierId = backManageCnd.getSupplierId();
		if (currentPage == null)
			currentPage = 1;
		keyword = UrlTools.urlDecode(keyword);
		backManageCnd.setKeyword(keyword);
		Cnd cnd = Cnd._new();
		if (supplierId != null && supplierId != -1L) {
			cnd.add("supplierId", Op.EQ, supplierId);
		}
		cnd.add("oper", Op.EQ, backManageCnd.getOper()).add("content", Op.LIKE, keyword).add("createTime", Op.GTE, backManageCnd.getStartDate());

		Date endDate = null;
		if (!StringUtils.isEmpty(backManageCnd.getEndDate())) {
			Date date = DateTools.parse(backManageCnd.getEndDate(), null);
			long endDatetime = date.getTime() + DateTools.ONE_DAY;
			endDate = new Date(endDatetime);
		}
		cnd.add("createTime", Op.LTE, endDate).desc("createTime");
		PageView pageView = impressService.queryPage(cnd, new PageCnd(currentPage, GlobalInfo.getBackManageListPageSize()));
		setSuppliser(pageView.getRecords(), backManageCnd.getSupplierId());
		model.put("pageView", pageView);
		setCnd2Model(model, backManageCnd);
	}

	@Override
	public Map<String, Object> impressDel(ModelMap model, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Impress impress = impressService.getImpressFromDb(id);
			impressService.deleteImpress(impress);
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public void impressDetail(ModelMap model, Long id, Long supplierId) {
		Impress impress = null;
		if (id != null) {
			impress = impressService.getImpressFromDb(id);
		} else {
			impress = new Impress();
			impress.setOper(1);
			impress.setSupplierId(supplierId);
		}
		model.put("suppliserList", suppliserService.listSuppliser());
		model.put("impress", impress);
	}

	@Override
	public Map<String, Object> impressEdit(ModelMap model, Impress impress) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long id = impress.getId();
		try {
			String content = UrlTools.urlDecode(impress.getContent());
			if (id != null) {
				Impress impressdb = impressService.getImpressFromDb(id);
				impressdb.setImpressCount(impress.getImpressCount());
				impressdb.setOper(GlobalInfo.OPER_PASS);
				impressdb.setUpdateTime(DateTools.now());
				impress.setContent(content);
				impressService.updateImpress(impressdb);
			} else {
				impress.setContent(content);
				impress.setOper(GlobalInfo.OPER_PASS);
				impress.setCreateTime(DateTools.now());
				impress.setUpdateTime(DateTools.now());
				impressService.addImpress(impress);
			}
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> impressAudit(ModelMap model, String id, boolean isPassed) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Long> ids = parseIds(id);
			for (Long lId : ids) {
				Impress impress = impressService.getImpress(lId);
				if (isPassed) {
					impress.setOper(GlobalInfo.OPER_PASS);
				} else {
					impress.setOper(GlobalInfo.OPER_UN_PASS);
					impress.setIsDelete(1);
				}
				impressService.updateImpress(impress);
			}
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public void listComment(ModelMap model, BackManageCnd backManageCnd) {
		Integer currentPage = backManageCnd.getCurrentPage();
		String keyword = backManageCnd.getKeyword();
		Long supplierId = backManageCnd.getSupplierId();

		if (currentPage == null)
			currentPage = 1;
		keyword = UrlTools.urlDecode(keyword);
		backManageCnd.setKeyword(keyword);

		Cnd cnd = Cnd._new();
		if (supplierId != null && supplierId != -1L) {
			cnd.add("supplierId", Op.EQ, supplierId);
		}
		cnd.add("oper", Op.EQ, backManageCnd.getOper()).add("content", Op.LIKE, keyword);
		if ("-1".equals(backManageCnd.getType())) {
			backManageCnd.setType(null);
		}
		cnd.add("type", Op.EQ, backManageCnd.getType()).add("createTime", Op.GTE, backManageCnd.getStartDate());
		Date endDate = null;
		if (!StringUtils.isEmpty(backManageCnd.getEndDate())) {
			Date date = DateTools.parse(backManageCnd.getEndDate(), null);
			long endDatetime = date.getTime() + DateTools.ONE_DAY;
			endDate = new Date(endDatetime);
		}
		cnd.add("createTime", Op.LTE, endDate).desc("createTime");

		PageView<Comment> pageView = commentService.queryPage(cnd, PageCnd.newInstance(currentPage, GlobalInfo.getBackManageListPageSize()));
		List<Comment> comments = pageView.getRecords();
		Suppliser suppliser = null;
		if (supplierId != null) {
			suppliser = suppliserService.querySuppliserById(supplierId);
		}
		for (Comment comment : comments) {
			if (suppliser == null) {
				Suppliser _suppliser = suppliserService.querySuppliserById(comment.getSupplierId());
				comment.setSuppliser(_suppliser);
			} else {
				comment.setSuppliser(suppliser);
			}
		}
		model.put("pageView", pageView);
		setCnd2Model(model, backManageCnd);
	}

	@Override
	public Map<String, Object> commentAudit(ModelMap model, String id, boolean isPassed) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Long> ids = parseIds(id);
			for (Long lId : ids) {
				Comment comment = commentService.getComment(lId);
				if (isPassed) {
					comment.setOper(1);
				} else {
					// 不通过则删除
					comment.setOper(0);
					comment.setIsDelete(1);
				}
				commentService.updateComment(comment);
			}
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public void commentDetail(ModelMap model, Long id, Long supplierId) {
		Comment comment = null;
		if (id != null) {
			comment = commentService.getComment(id);
		} else {
			comment = new Comment();
			comment.setSupplierId(supplierId);
		}
		model.put("suppliserList", suppliserService.listSuppliser());
		model.put("comment", comment);
	}

	private List<Long> parseIds(String ids) {
		List<Long> idList = new ArrayList<Long>();
		if (ids.indexOf("_") > 0) {
			String[] array = ids.split("_");
			for (String id : array) {
				if (!StringUtils.isEmpty(id)) {
					idList.add(Long.parseLong(id));
				}
			}
		} else {
			idList.add(Long.parseLong(ids));
		}
		return idList;
	}

	@Override
	public Map<String, Object> commentDel(ModelMap model, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Comment comment = commentService.getComment(id);
			commentService.deleteComment(comment);
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> commentEdit(ModelMap model, Comment comment) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long id = comment.getId();
		try {
			if (id != null) {
				Comment commentdb = commentService.getComment(id);
				commentdb.setType(comment.getType());
				commentdb.setOper(GlobalInfo.OPER_PASS);
				commentdb.setContent(UrlTools.urlDecode(comment.getContent()));
				commentdb.setUpdateTime(DateTools.now());
				commentService.updateComment(commentdb);
			} else {
				comment.setContent(UrlTools.urlDecode(comment.getContent()));
				comment.setOper(GlobalInfo.OPER_PASS);
				comment.setCreateTime(DateTools.now());
				comment.setUpdateTime(DateTools.now());
				commentService.addComment(comment);
			}
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public void supplierDetail(ModelMap model, Long id) {
		if (id != null) {
			Suppliser supplier = suppliserService.querySuppliserById(id);
			model.put("supplier", supplier);
		}
	}

	@Override
	public Map<String, Object> supplierDel(ModelMap model, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			suppliserService.removeSuppliser(id);
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> supplierEdit(ModelMap model, Suppliser supplier) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (supplier.getId() != null) {
				Suppliser suppliserdb = suppliserService.querySuppliserById(supplier.getId());
				suppliserdb.setLogo(supplier.getLogo());
				suppliserdb.setUrl(supplier.getUrl());
				suppliserdb.setUpdateTime(DateTools.now());
				suppliserdb.setName(supplier.getName());
				suppliserService.updateSuppliser(suppliserdb);
			} else {
				supplier.setUpdateTime(DateTools.now());
				supplier.setCreateTime(DateTools.now());
				supplier.setCode(supplier.getName());
				suppliserService.saveSuppliser(supplier);
			}
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	public String updateComment(ModelMap model, Long id) {
		List<Suppliser> suppliserList = suppliserService.listSuppliser();
		model.put("comment", commentService.getComment(id));
		model.put("suppliserList", suppliserList);
		model.put("action", "/backmanager/comments/update.htm");
		model.put("method", "POST");
		return "/backmanager/commentsForm";
	}

	private void setSuppliser(List<BaseSupplierWrap> supplierWrap, Long suppliserId) {
		Suppliser suppliser = null;
		List<Suppliser> suppliserList = null;
		if (suppliserId != null && suppliserId != -1L) {
			suppliser = suppliserService.querySuppliserById(suppliserId);
		} else {
			suppliserList = suppliserService.listSuppliser();
		}
		for (BaseSupplierWrap baseSupplier : supplierWrap) {
			if (suppliser == null) {
				for (Suppliser su : suppliserList) {
					if (baseSupplier.getSupplierId() == su.getId()) {
						baseSupplier.setSuppliser(su);
					}
				}
			} else {
				baseSupplier.setSuppliser(suppliser);
			}
		}
	}

	private void setCnd2Model(ModelMap model, BackManageCnd cnd) {
		model.addAttribute("supplierId", cnd.getSupplierId());
		model.addAttribute("currentPage", cnd.getCurrentPage());
		model.addAttribute("keyword", cnd.getKeyword());
		model.addAttribute("startDate", cnd.getStartDate());
		model.addAttribute("endDate", cnd.getEndDate());
		model.addAttribute("type", cnd.getType());
		model.addAttribute("oper", cnd.getOper());
		model.put("suppliserList", suppliserService.listSuppliser());
	}

	@Override
	public OperationDto uploadFile(MultipartFile file) {
		OperationDto operationDto = new OperationDto(false, "文件格式错误");
		String fileType = ImageUtils.isImageFile(file);
		if (StringUtils.isNotBlank(fileType)) {
			String logoRealPathDir = UPLOAD_IMAGE_PATH;
			File dir = new File(logoRealPathDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String fileName = RandomStringUtils.randomAlphanumeric(8);
			File imageFile = new File(logoRealPathDir + "/" + fileName + "." + fileType);
			try {
				file.transferTo(imageFile);
				operationDto.setData(IMAGE_URL_PREFIX + "/" + fileName + "." + fileType);
				operationDto.setOper(true);
				operationDto.setMessage("文件上传成功");
			} catch (Exception e) {
				e.printStackTrace();
				operationDto.setMessage("文件上传失败");
			}
		}
		return operationDto;
	}

	@Override
	public OperationDto cutImageAndSave(String filePath, Integer x, Integer y, Integer width, Integer height) {
		OperationDto operationDto = new OperationDto(false, "裁剪失败");
		try {
			byte[] bytes = WebTools.getBytes(filePath);
			if (bytes != null) {
				Image cutImage = ImageUtils.abscut(new ByteArrayInputStream(bytes), x, y, width, height);
				ByteArrayOutputStream os1 = new ByteArrayOutputStream();

				ImageIO.write(ImageUtils.smallerImage(cutImage, 110, 55, 1f), "JPEG", os1);

				FileUtils.writeByteArrayToFile(new File(UPLOAD_IMAGE_PATH + "/" + getFileName(filePath)), os1.toByteArray());

				operationDto.setData(filePath);
				operationDto.setOper(true);
				operationDto.setMessage("裁剪成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return operationDto;
	}

	@Override
	public OperationDto deleteImage(String filePath) {
		OperationDto operationDto = new OperationDto(false, "删除失败");
		File file = new File(UPLOAD_IMAGE_PATH + "/" + getFileName(filePath));
		if (file.exists()) {
			file.delete();

			operationDto.setOper(true);
			operationDto.setMessage("删除成功");
		}
		return operationDto;
	}

	private static String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}

	@Override
	public void wordList(ModelMap model, Integer currentPage, String word) {
		if (word == null)
			word = "";
		if (currentPage == null)
			currentPage = 1;
		model.put("pageView", wordService.queryWordPage(word, new PageCnd(currentPage, GlobalInfo.DEFAULT_WORD_PAGE_SIZE)));
		model.put("keyword", word);
	}

	@Override
	public void wordDetail(ModelMap model, Long id) {
		Word word = wordService.getWord(id);
		model.addAttribute("word", word);
	}

	@Override
	public Map<String, Object> wordEdit(Word word) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (word.getId() != null)
				wordService.update(word);
			else
				wordService.addWord(word);
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> wordRemove(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			wordService.delete(id);
			map.put("flag", true);
		} catch (Exception e) {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public boolean saveCategory(String content) {
		JSONArray array = JSONArray.parseArray(content);
		for (int i = 0; i < array.size(); i++) {
			JSONObject json_1 = JSONObject.parseObject(array.get(i).toString());
			JSONArray json_1_list = json_1.getJSONArray("subCategoryLinkDtoList");
			setUrl(json_1, null, null);
			array.set(i, json_1);
			if (json_1_list != null) {
				for (int j = 0; j < json_1_list.size(); j++) {
					JSONObject json_2 = JSONObject.parseObject(json_1_list.get(j).toString());
					JSONArray json_2_list = json_2.getJSONArray("subCategoryLinkDtoList");
					setUrl(json_2, json_1.getString("name"), null);
					json_1_list.set(j, json_2);
					if (json_2_list != null) {
						for (int k = 0; k < json_2_list.size(); k++) {
							JSONObject json_3 = new JSONObject();
							json_3.put("name", json_2_list.get(k).toString());
							setUrl(json_3, json_1.getString("name"), json_2.getString("name"));
							json_2_list.set(k, json_3);
						}
					} else {
						LogUtils.info(getClass(), json_1.getString("name"));
					}
				}
			} else {
				LogUtils.info(getClass(), json_1.getString("name"));
			}
		}

		try {
			OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(System.getProperty("newwww.root") + "js/config/category.json"));
			os.write(array.toJSONString());
			os.close();

			// 保存数据库
			List<Category> categories = dao.queryAll(Category.class);
			if (CollectionTools.isEmpty(categories)) {
				Category ca = new Category();
				ca.setContext(array.toJSONString());
				dao.insert(ca);
			} else {
				Category ca = categories.get(0);
				ca.setContext(array.toJSONString());
				dao.update(ca);
			}
			return true;
		} catch (IOException e) {
			LogUtils.error(getClass(), e);
			return false;
		}
	}

	@Override
	public void initCategory() {
		List<Category> categories = dao.queryAll(Category.class);
		if (!CollectionTools.isEmpty(categories)) {
			Category ca = categories.get(0);

			OutputStreamWriter os;
			try {
				os = new OutputStreamWriter(new FileOutputStream(System.getProperty("newwww.root") + "js/config/category.json"));
				os.write(ca.getContext());
				os.close();
			} catch (Exception e) {
				LogUtils.error(getClass(), e);
			}
		}
	}

	@Override
	public PageView<SearchRecommend> getRecommends(String startDate, String endDate, Integer currentPage) {
		return searchRecommendService.getSearchRecommends(startDate, endDate, currentPage);
	}

	@Override
	public void exportRecommend(HttpServletResponse response, String startDate, String endDate, String fileNames) throws Exception {
		List<SearchRecommend> list = searchRecommendService.getSearchRecommendList(startDate, endDate);
		if (CollectionTools.isEmpty(list))
			return;
		String fileName = "recommends.xls";
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + "");
		WritableWorkbook work = Workbook.createWorkbook(response.getOutputStream());
		WritableSheet sheet = work.createSheet("aaa", 0);
		sheet.getSettings().setFitHeight(sheet.getSettings().getFitHeight());
		sheet.setColumnView(0, 30);
		sheet.setColumnView(1, 80);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);

		WritableCellFormat wcf = new WritableCellFormat(new DateFormat("yyyy-MM-dd HH:mm:ss"));
		wcf.setAlignment(Alignment.LEFT);
		wcf.setVerticalAlignment(VerticalAlignment.TOP);
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setAlignment(Alignment.LEFT);
		cellFormat.setVerticalAlignment(VerticalAlignment.TOP);
		cellFormat.setWrap(true);

		WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
		font.setColour(Colour.RED);
		WritableCellFormat titleFormat = new WritableCellFormat(font);
		titleFormat.setAlignment(Alignment.CENTRE);
		sheet.addCell(new Label(0, 0, "关键词", titleFormat));
		sheet.addCell(new Label(1, 0, "反馈内容", titleFormat));
		sheet.addCell(new Label(2, 0, "联系邮箱", titleFormat));
		sheet.addCell(new Label(3, 0, "IP", titleFormat));
		sheet.addCell(new Label(4, 0, "发布时间", titleFormat));
		for (int i = 1; i <= list.size(); i++) {
			SearchRecommend recommend = list.get(i - 1);
			sheet.addCell(new Label(0, i, recommend.getKeyword(), cellFormat));
			sheet.addCell(new Label(1, i, recommend.getContent(), cellFormat));
			sheet.addCell(new Label(2, i, recommend.getEmail(), cellFormat));
			sheet.addCell(new Label(3, i, recommend.getIp(), wcf));
			sheet.addCell(new DateTime(4, i, recommend.getCreateTime(), wcf));
		}
		work.write();
		work.close();
	}

	public final String PRICE_CONFIG_FILEPATH = "config.properties";

	public final String PROPERTY_PRICE_DATASOURCE = "price.dataSource";

	@Override
	public int getConfig() throws Exception {
		PropertiesConfiguration pros = new PropertiesConfiguration();
		InputStream input = new FileInputStream(getWebClassesPath() + PRICE_CONFIG_FILEPATH);
		pros.load(input);
		input.close();
		return pros.getInt(PROPERTY_PRICE_DATASOURCE);
	}

	@Override
	public Boolean saveConfig(Integer value) {
		PropertiesConfiguration pros = new PropertiesConfiguration();
		try {
			InputStream input = new FileInputStream(getWebClassesPath() + PRICE_CONFIG_FILEPATH);
			pros.load(input);
			input.close();
			pros.setProperty(PROPERTY_PRICE_DATASOURCE, value);
			OutputStream output = new FileOutputStream(getWebClassesPath() + PRICE_CONFIG_FILEPATH);
			pros.save(output, null);
			output.close();
			PriceTrendUtils.DATA_SOURCE = value;
			return true;
		} catch (Exception e) {
			LogUtils.error(getClass(), e);
			return false;
		}
	}

	@Override
	public List<SearchHotKey> getPageConfig() throws Exception {
		List<SearchHotKey> list = dao.queryAll(SearchHotKey.class);
		return list;
	}

	@Override
	public Boolean savePageConfig(String key) {
		if (CollectionTools.isEmpty(dao.query(SearchHotKey.class, Cnd.where("name", Op.EQ, key)))) {
			dao.insert(new SearchHotKey(key));
			MemCachedUtils.cleanCache(CommInfoSetFilter.HOTKEY);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean deletePageConfig(String key) {
		List<SearchHotKey> list = dao.query(SearchHotKey.class, Cnd.where("name", Op.EQ, key));
		if (CollectionTools.isEmpty(list)) {
			return false;
		}
		SearchHotKey bean = list.get(0);
		dao.delete(bean);
		MemCachedUtils.cleanCache(CommInfoSetFilter.HOTKEY);
		return true;
	}
	
	/*---------------------------------------资讯管理-----------------------------------------------*/
	
	@Override
	public PageView<Info> queryInfos(Integer currentPage, Integer pageSize, String keyword) {
		Cnd cnd = Cnd._new();
		if (!StringUtils.isEmpty(keyword)) {
			cnd.add("title", Op.LIKE, keyword);
			cnd.or("content", Op.LIKE, keyword);
		}
		PageView<Info> pageView = dao.queryPage(Info.class, cnd, PageCnd.newInstance(currentPage, pageSize));
		return pageView;
	}
	
	@Override
	public Info getInfo(Long id) {
		Info info = dao.get(Info.class, id);
		return info;
	}
	
	@Override
	public Long saveInfo(String title, String content, Long id) {
		Info info = new Info(title, content, DateTools.now());
		if (id == null) {
			info = dao.insert(info);
		} else {
			info.setId(id);
			info = dao.update(info);
		}
		return info.getId();
	}
	
	@Override
	public Boolean deleteInfo(Long id) {
		Info info = new Info();
		info.setId(id);
		dao.delete(info);
		return true;
	}
	
	/*---------------------------------------关键词管理-----------------------------------------------*/
	
	@Override
	public PageView<Key> queryKeys(Integer currentPage, Integer pageSize, String keyword) {
		Cnd cnd = Cnd._new();
		if (!StringUtils.isEmpty(keyword)) {
			cnd.add("name", Op.EQ, keyword);
		}
		PageView<Key> pageView = dao.queryPage(Key.class, cnd, PageCnd.newInstance(currentPage, pageSize));
		return pageView;
	}
	
	@Override
	public Key getKey(Long id) {
		Key key = dao.get(Key.class, id);
		return key;
	}
	
	@Override
	public Long saveKey(String name, Long id) {
		Key key = new Key(name);
		if (id == null) {
			key = dao.insert(key);
		} else {
			key.setId(id);
			key = dao.update(key);
		}
		return key.getId();
	}
	
	@Override
	public Boolean deleteKey(Long id) {
		Key key = new Key();
		key.setId(id);
		dao.delete(key);
		return true;
	}
	
	/*---------------------------------------topic管理-----------------------------------------------*/
	
	@Override
	public PageView<Link> queryLinks(Integer currentPage, Integer pageSize, String keyword) {
		Cnd cnd = Cnd._new();
		if (!StringUtils.isEmpty(keyword)) {
			cnd.add("name", Op.EQ, keyword);
			cnd.or("link", Op.EQ, keyword);
		}
		PageView<Link> pageView = dao.queryPage(Link.class, cnd, PageCnd.newInstance(currentPage, pageSize));
		return pageView;
	}
	
	@Override
	public Link getLink(Long id) {
		Link bean = dao.get(Link.class, id);
		return bean;
	}
	
	@Override
	public Long saveLink(String name, String link, Long id) {
		Link bean = new Link(link, name);
		if (id == null) {
			bean = dao.insert(bean);
		} else {
			bean.setId(id);
			bean = dao.update(bean);
		}
		Map<String, String> map = TopicSEOServiceImpl.map;
		for (Iterator<Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			String e_link = entry.getKey();
			String e_name = entry.getValue();
			if (e_name.equals(name)) {
				map.remove(e_link);
				map.put(link, name);
				break;
			}
		}
		return bean.getId();
	}
	
	/*---------------------------------------meta管理-----------------------------------------------*/
	
	@Override
	public PageView<Meta> queryMetas(Integer currentPage, Integer pageSize) {
		PageView<Meta> pageView = dao.queryPage(Meta.class, Cnd._new(), PageCnd.newInstance(currentPage, pageSize));
		return pageView;
	}
	
	@Override
	public Meta getMeta(Long id) {
		Meta bean = dao.get(Meta.class, id);
		return bean;
	}

	@Override
	public Long saveMeta(String pageName, String title, String des, String keyword, Long id) {
		Meta bean = new Meta(pageName, title, des, keyword);
		if (id == null) {
			bean = dao.insert(bean);
		} else {
			bean.setId(id);
			bean = dao.update(bean);
		}
		return bean.getId();
	}
	
	
	private String getWebClassesPath() {
		String path = getClass().getClassLoader().getResource("").getPath();
		return path;

	}

	private void setUrl(JSONObject json, String parent1, String parent2) {
		if (json != null && !StringUtils.isEmpty(json.getString("name"))) {
			String name = json.getString("name");
			String prefix = "";
			if (!StringUtils.isEmpty(parent1))
				prefix += parent1 + ">";
			if (!StringUtils.isEmpty(parent2))
				prefix += parent2 + ">";
			prefix += name;
			json.put("url", CATEGORY_URL_FORMAT.replace("{0}", prefix));
		}
	}
}
