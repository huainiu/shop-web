import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.ColumnCountGetFilter;
import org.apache.hadoop.hbase.filter.ColumnPaginationFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.junit.Test;

import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.service.hbase.Connection;

public class HbaseApp {
	public static void main(String[] args) throws IOException {
		/*HBaseConnection hbaseConntion = new HBaseConnection("10.10.100.78","12181", "B5MO", "PriceHistory");
		byte[] pid = PHUtilities.md5ToUint128(new String("33066ed9fa11630437b538c1f77762c5"));
		PriceHistory priceHistory = hbaseConntion.get(pid, DateUtils.addDays(new Date(), -60).getTime(), new Date().getTime());
		while(priceHistory.next()){
			ProductPrice price = priceHistory.get();
			System.out.println(JSON.toJSONString(price));
		}*/
		
//		System.out.println(StringEscapeUtils.unescapeHtml("\u624b\u673a"));
//		System.out.println(StringEscapeUtils.unescapeHtml("\u3001\u6570"));
//		System.out.println(StringEscapeUtils.unescapeHtml("\u7801\u3001"));
//		System.out.println(StringEscapeUtils.unescapeHtml("\u914d\u4ef6"));
//	                                      http://item.taobao.com/item.htm?id=14117163618
//		System.out.println(MD5.MD5Encode("http://item.taobao.com/item.htm?id=14117163618"));
//		http://s.stage.bang5mai.com/item/b6a8efeda598e952086cc1ce838dd0cf.html
//		http://s.stage.bang5mai.com/item/++.html
		
//		b6a8efeda598e952086cc1ce838dd0cf
//		b6a8efeda598e952086cc1ce838dd0cf
		/*long start = System.nanoTime();
		for(int i = 0; i < 10000;i++){
			JSON.parseObject(IOUtils.toString(HbaseAppTest.class.getResourceAsStream("result.txt")));
		}
		System.out.println((System.nanoTime() - start)/1000000);
		start = System.nanoTime();
		for(int i = 0; i < 10000;i++){
			JSONObject.fromString(IOUtils.toString(HbaseAppTest.class.getResourceAsStream("result.txt")));
		}
		System.out.println((System.nanoTime() - start)/1000000);*/
		Process ps = Runtime.getRuntime().exec("ifconfig");
		List<String> list = IOUtils.readLines(ps.getInputStream(), "UTF-8");
		for(String str : list){
			System.out.println(str);
		}
		
//		String host = "172.16.1.30";
//		int timeOut = 100;
//		boolean status = InetAddress.getByName(host).isReachable(timeOut);
//		System.out.println(status);
	}
	
	@Test
	public void testDetail() throws Exception{
//		System.out.println(StringTools.MD5("http://item.jd.com/1006887.html"));
		Connection hBaseConnection = Connection.initConnection("HADOOP-MASTER", "12181");
		HTable hTable = hBaseConnection.createHTable("Product-Details", "cf");
		Get get = new Get("8d206758768d04b466123578c9c67404".getBytes("UTF-8"));
		get.addColumn("cf".getBytes("UTF-8"), "detail".getBytes("UTF-8"));
		
		Result result = hTable.get(get);
		if(result != null){
			byte[] detailBytes = result.getValue("cf".getBytes("UTF-8"), "detail".getBytes("UTF-8"));
			if(detailBytes == null){
				System.out.println("-----------------------");
			}else{
				String detail = new String(detailBytes, "UTF-8");
				System.out.println(detail);
			}
				
		}
	}
	
	@Test
	public void testComment() throws Exception{
		Connection hBaseConnection = Connection.initConnection("HADOOP-MASTER", "12181");
		HTable hTable = hBaseConnection.createHTable("COMMENT", "");
		Get get = new Get("059a1031afa0edd619f0fa4b17a10946".getBytes("UTF-8"));
		/*Filter filter = new ColumnPrefixFilter("中评".getBytes());
		FilterList filterList = new FilterList();
		filterList.addFilter(new ColumnPrefixFilter("中评".getBytes()));
		filterList.addFilter(filterList);
		Filter filter = new SingleColumnValueFilter("".getBytes(), "".getBytes(), CompareOp.EQUAL, "".getBytes());
		get.setFilter(filter);*/
		get.setFilter(new ColumnCountGetFilter(10));
		Result result = hTable.get(get);
		
		Scan scan = new Scan();
//		hTable.incrementColumnValue(row, family, qualifier, amount)
		if(result != null){
			NavigableMap<byte[],NavigableMap<byte[],byte[]>> resultMap = result.getNoVersionMap();
			if(resultMap != null)
			for(byte[] key : resultMap.keySet()){
				System.out.println(new String(key, "UTF-8") + "-->:");
				NavigableMap<byte[],byte[]> nav = resultMap.get(key);
				for(byte[] k : nav.keySet()){
					System.out.println(new String(k, "UTF-8") + ":" + new String(nav.get(k), "UTF-8"));
				}
			}
		}
	}
	
	@Test
	public void testCommentCount2() throws Exception{
		Connection hBaseConnection = Connection.initConnection("HADOOP-MASTER", "");
		HTable hTable = hBaseConnection.createHTable("COMMENT", null);
		long result = hTable.incrementColumnValue("059a1031afa0edd619f0fa4b17a10946".getBytes("UTF-8"), "COMM".getBytes(), "".getBytes(), 0);
		System.out.println(result);
	}
	
	@Test
	public void testCommentCount() throws Exception{
		Connection hBaseConnection = Connection.initConnection("HADOOP-MASTER", "");
		HTable hTable = hBaseConnection.createHTable("COMMENT", null);
		Get get = new Get("0fa5c67aadd8f7b909565ca562249f65".getBytes("UTF-8"));
		Filter filter = new ColumnPaginationFilter(20, 0);
		get.setFilter(filter);
		Result result = hTable.get(get);
		if(result != null){
			NavigableMap<byte[],NavigableMap<byte[],byte[]>> resultMap = result.getNoVersionMap();
			if(resultMap != null)
			for(byte[] key : resultMap.keySet()){
				System.out.println(new String(key, "UTF-8") + "-->:");
				NavigableMap<byte[],byte[]> nav = resultMap.get(key);
				for(byte[] k : nav.keySet()){
					System.out.println(new String(k, "UTF-8") + ":" + new String(nav.get(k), "UTF-8"));
				}
			}
		}
	}
	
	@Test
	public void testCommentCount1() throws Exception{
		Connection hBaseConnection = Connection.initConnection("HADOOP-MASTER", "12181");
		HTable hTable = hBaseConnection.createHTable("S_COMMENT", null);
		ResultScanner resultScanner = hTable.getScanner(new Scan());
		if(resultScanner != null){
			Iterator<Result> iterator = resultScanner.iterator();
			while(iterator.hasNext()){
				Result result = iterator.next();
				NavigableMap<byte[],NavigableMap<byte[],byte[]>> resultMap = result.getNoVersionMap();
				System.out.println(new String(result.getRow(), "UTF-8"));
				if(resultMap != null)
					for(byte[] key : resultMap.keySet()){
						System.out.println(new String(key, "UTF-8") + "-->:");
						NavigableMap<byte[],byte[]> nav = resultMap.get(key);
						for(byte[] k : nav.keySet()){
							System.out.println(new String(k, "UTF-8") + ":" + new String(nav.get(k), "UTF-8"));
						}
					}
			}
		}
	}
	
	@Test
	public void testTime(){
		System.out.println(StringTools.MD5("http://detail.tmall.com/item.htm?id=25083240692"));
//		System.out.println(DateTools.formate(DateTools.parse("20130924171600", "yyyyMMddHHmmss")));
	}
}
