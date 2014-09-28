package com.b5m.goods.priceHistory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.b5m.PHAPI.HBaseConnection;
import com.b5m.PHAPI.PHUtilities;
import com.b5m.PHAPI.PriceHistory;
import com.b5m.PHAPI.ProductPrice;

public class PriceHistoryApp {

	private static final String zookeeper = "HADOOP-MASTER";

	private static final String zookeeperPort = "12181";

	private static final String tableName = "B5MO_PRICE";

	private static final String columnFamily = "PH";

	public static void main(String[] args) {
		// test for time convert
		System.out.println(new Date().getTime());
		String strTime = "2013-08-14  13:19:21";
		Long time = PHUtilities.strTimeToLong(strTime);
		Date date = new Date(time);
		System.out.println(strTime + "=>" + date.toString() + " : " + time);

		// test for HBaseConnection
		HBaseConnection hbase = null;
		try {
			hbase = new HBaseConnection(zookeeper, zookeeperPort, tableName, columnFamily);
		} catch (Exception e) {
			System.out.println("ERROR: Init Connection Failed, " + e.getMessage());
			System.exit(0);
		}

		// test for commit.
		ArrayList<String> ids = new ArrayList<String>();
		String id = "d36c2de58443f96dfcafdaa29f16c428";

		ids.add(id);

		// test for get
		try {
			// Long timestamp =
			PriceHistory ph = hbase.get(id, null, null);
			System.out.println("***************" + JSON.toJSONString(ph) + "***************");
			if (null == ph) {
				System.out.println("ID: " + id + " not exist");
				System.exit(0);
			}
			while (ph.next()) {
				ProductPrice p = ph.get();
				System.out.println("Timestamp: " + PHUtilities.longToStrTime(p.getTimestamp()) + "  " + p.getTimestamp() + " Price: " + p.getPriceLow() + " - " + p.getPriceHigh());
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			System.exit(0);
		}

		// test for get list
		try {
			// Long timestamp =

			List<PriceHistory> phs = hbase.get(ids, null, null);
			if (null == phs) {
				System.exit(0);
			}
			for (int i = 0; i < phs.size(); i++) {
				PriceHistory ph = phs.get(i);
				System.out.println("ID: " + PHUtilities.bytesToString(ph.getProductId()));
				while (ph.next()) {
					ProductPrice p = ph.get();
					System.out.println("------------" + JSON.toJSONString(p) + "------------");
					System.out.println(new Timestamp(p.getTimestamp() / 1000));
					// System.out.println("Timestamp: " + PHUtilities.longToStrTime(p.getTimestamp()) + "  " + p.getTimestamp() + " Price: " + p.getPriceLow() + " - " + p.getPriceHigh());
				}
			}
			// System.out.println("------------" + JSON.toJSONString(phs) + "------------");
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			System.exit(0);
		}

	}
}
