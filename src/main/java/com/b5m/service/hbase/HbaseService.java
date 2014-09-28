package com.b5m.service.hbase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.PHAPI.HBaseConnection;
import com.b5m.PHAPI.PHUtilities;
import com.b5m.PHAPI.PriceHistory;
import com.b5m.PHAPI.ProductPrice;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.DateTools;
import com.b5m.service.pricetrend.PricePerDay;
import com.b5m.service.pricetrend.PriceTrend;

public class HbaseService {

	private static String zookeeper;

	private static String zookeeperPort;

	private static String tableName;

	private static String columnFamily;

	private static HBaseConnection hbase = null;

	private final static Logger logger = Logger.getLogger(HbaseService.class);

	public static HBaseConnection getInstance() {
		if (hbase == null) {
			try {
				hbase = new HBaseConnection(zookeeper, zookeeperPort, tableName, columnFamily);
			} catch (Exception e) {
				logger.error("ERROR: Init Connection Failed, " + e.getMessage(), e);
			}
		}
		return hbase;
	}

	public static JSONObject gets(Date start, Date end, String... ids) {
		return buildJsonResult(getPriceHistorys(start, end, ids));
	}

	public static JSONObject gets(Date start, Date end, ArrayList<String> ids) {
		return buildJsonResult(getPriceHistorys(start, end, ids));
	}

	public static List<PriceTrend> getPriceHistorys(Date start, Date end, String... ids) {
		return getPriceHistorys(start, end, Arrays.asList(ids));
	}

	public static List<PriceTrend> getPriceHistorys(Date start, Date end, List<String> ids) {
		try {
			List<PriceHistory> list = HbaseService.getInstance().get(ids, start, end);
			if (CollectionUtils.isEmpty(list) && ids.size() == 1) {
				list = HbaseService.getInstance().get(ids, null, end);
				if (!CollectionUtils.isEmpty(list)) {
					ProductPrice pp = null;
					String id = list.get(list.size()-1).identifier();
					while(list.get(list.size()-1).next()) {
						pp = list.get(list.size()-1).get();
					}
					pp.setTimestamp(System.currentTimeMillis());
					list.clear();
					PriceHistory ph = new PriceHistory(id);
					ph.insert(pp);
					list.add(ph);
				}
			}
			return convert(list);
		} catch (Exception e) {
			logger.error("gets price trend exception from hbase, with exception is [" + e.getMessage() + "]", e);
			return new ArrayList<PriceTrend>();
		}
	}

	public static JSONObject get(Date start, Date end, String id) {
		return buildJsonResult(getPriceHistory(start, end, id));
	}

	public static PriceTrend getPriceHistory(Date start, Date end, String id) {
		try {
			return convert(HbaseService.getInstance().get(id, start, end));
		} catch (Exception e) {
			logger.error("get price trend exception from hbase, with exception is [" + e.getMessage() + "]", e);
			return null;
		}
	}

	public static JSONObject get(String id) {
		return get(null, null, id);
	}

	public static PriceTrend getPriceHistory(String id) {
		return getPriceHistory(null, null, id);
	}

	/**
	 * @description 转化成www搜索中的bean
	 * @param ph
	 * @return
	 * @author echo
	 * @since 2013-9-24
	 * @email echo.weng@b5m.com
	 */
	protected static PriceTrend convert(PriceHistory ph) {
		if (ph == null)
			return null;
		PriceTrend dto = new PriceTrend();
		if (ph.getProductId() != null) {
			String docId = PHUtilities.bytesToString(ph.getProductId());
			if (docId.length() == 31)
				docId = "0" + docId;
			dto.setDocId(docId);
		}
		Set<String> dates = new HashSet<String>();
		while (ph.next()) {
			ProductPrice price = ph.get();
			Date date = DateTools.toDate(price.getTimestamp());
			String dateStr = DateTools.formate(date);
			if (dates.contains(dateStr))
				continue;
			PricePerDay pricePerDay = new PricePerDay();
			pricePerDay.setDate(date);
			BigDecimal p = new BigDecimal(price.getPriceHigh()).setScale(2, RoundingMode.HALF_EVEN);
			pricePerDay.setPrice(p);
			dto.addPricePerDay(pricePerDay);
			dates.add(dateStr);
		}
		return dto;
	}

	protected static List<PriceTrend> convert(List<PriceHistory> priceHistories) {
		List<PriceTrend> list = new ArrayList<PriceTrend>();
		for (PriceHistory priceHistory : priceHistories) {
			if (priceHistory != null) {
				list.add(convert(priceHistory));
			}
		}
		return list;
	}

	public static JSONObject buildJsonResult(PriceTrend ph) {
		return buildJsonResult(CollectionTools.newList(ph));
	}

	public static JSONObject buildJsonResult(List<PriceTrend> list) {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put("resources", array);
		if (list.isEmpty())
			return json;
		for (PriceTrend ph : list) {
			array.add(buildJsonObject(ph));
		}
		return json;
	}

	public static JSONObject buildJsonObject(PriceTrend ph) {
		JSONObject o = new JSONObject();
		o.put("docid", ph.getDocId());
		JSONArray result = new JSONArray();
		o.put("price_history", result);
		for (PricePerDay pricePerDay : ph.getPricePerDays()) {
			JSONObject obj = new JSONObject();
			result.add(obj);
			JSONObject priceRange = new JSONObject();
			obj.put("price_range", priceRange);
			priceRange.put("price_low", pricePerDay.getPrice());
			priceRange.put("price_high", pricePerDay.getPrice());
			obj.put("timestamp", pricePerDay.getDate());
		}
		return o;
	}

	public void setZookeeper(String zookeeper) {
		HbaseService.zookeeper = zookeeper;
	}

	public void setZookeeperPort(String zookeeperPort) {
		HbaseService.zookeeperPort = zookeeperPort;
	}

	public void setTableName(String tableName) {
		HbaseService.tableName = tableName;
	}

	public void setColumnFamily(String columnFamily) {
		HbaseService.columnFamily = columnFamily;
	}

}
