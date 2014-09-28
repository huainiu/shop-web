package com.b5m.sf1.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.b5m.raindrop.collector.Collector;
import com.b5m.raindrop.collector.LatencyCollecter;

public class MonitorUtils {
	private static Collector collector = Collector.NO_OP;
	private static final Log LOG = LogFactory.getLog(MonitorUtils.class);
	
	static {
		InputStream stream = MonitorUtils.class.getResourceAsStream("/monitor.properties");
		if (stream != null) {
			Properties properties = new Properties();
			try {
				properties.load(stream);
				String enable = properties.getProperty("monitor.sf1r.enable", "false");
				String metrics = properties.getProperty("monitor.sf1r.metrics", "sf1r");
				if ("true".equals(enable)) {
					collector = new LatencyCollecter(metrics);
				}
			} catch (IOException e) {
				LOG.error(e);
			} catch (SecurityException e) {
				LOG.error(e);
			} catch (IllegalArgumentException e) {
				LOG.error(e);
			}
		}
	}
	
	public static void latencyCollecterBegin() {
		collector.begin();
	}

	public static void latencyCollecterCommit() {
		collector.commit();
	}

}
