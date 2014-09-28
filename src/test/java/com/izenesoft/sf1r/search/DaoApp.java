package com.izenesoft.sf1r.search;

import org.junit.Before;
import org.junit.Test;

import com.b5m.bean.entity.SwitchKeywords;
import com.b5m.dao.Dao;
import com.b5m.dao.impl.DaoImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DaoApp {
	private Dao dao;
	
	@Before
	public void setUp() throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://172.16.11.218:3306/cms?autoReconnect=true&useUnicode=true&characterEncoding=UTF8&mysqlEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		dataSource.setMinPoolSize(5);
		dataSource.setMaxPoolSize(20);
		dataSource.setMaxIdleTime(1800);
		dataSource.setInitialPoolSize(5);
		dataSource.setIdleConnectionTestPeriod(1200);
		dataSource.setUser("root");
		dataSource.setPassword("izene123");
		dao = new DaoImpl(dataSource);
	}
	
	@Test
	public void testCreate(){
//		dao.create(SwitchKeywords.class, true);
		dao.createEntity("cms_yiyuan_product", "com.b5m.bean.entity", "D:\\workspace\\newwww\\src\\main\\java\\com\\b5m\\bean\\entity");
	}
	
}
