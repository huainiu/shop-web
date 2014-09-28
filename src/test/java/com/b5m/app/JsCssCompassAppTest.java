package com.b5m.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class JsCssCompassAppTest {

	@Test
	public void testExecute(){
		try {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			path = path.substring(0, path.indexOf("newwww"));
			System.out.println(path);
			this.execute(path);
			this.executeCss(path);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void executeCss(String projectPath) throws Exception {
		String dir = projectPath + "newwww/src/main/webapp/css";
		InputStream in = new FileInputStream(projectPath + "newwww/src/test/java/com/b5m/app/csslist.txt");
		List<String> files = IOUtils.readLines(in);
		for (String file : files) {
			String fileName = dir + "/" + file + ".css";
			System.out.println(fileName);
			String js = cssCompressor(new FileInputStream(fileName));
			File minFile = new File(dir + "/" + file + ".min.css");
			if (minFile.exists())
				minFile.delete();
			IOUtils.write(js.getBytes("UTF-8"), new FileOutputStream(minFile));
		}
	}

	public void execute(String projectPath) throws Exception {
		String dir = projectPath + "newwww/src/main/webapp/js";
		InputStream in = new FileInputStream(projectPath + "newwww/src/test/java/com/b5m/app/jslist.txt");
		List<String> files = IOUtils.readLines(in);
		for (String file : files) {
			String fileName = dir + "/" + file + ".js";
			System.out.println(fileName);
			String js = javaScriptCompressor(new FileInputStream(fileName));
			File minFile = new File(dir + "/" + file + ".min.js");
			if (minFile.exists())
				minFile.delete();
			IOUtils.write(js.getBytes("UTF-8"), new FileOutputStream(minFile));
		}
	}

	public static String javaScriptCompressor(FileInputStream inputStream) throws Exception {
		int linebreakpos = -1;
		boolean munge = true;
		boolean verbose = false;
		boolean preserveAllSemiColons = true;
		boolean disableOptimizations = true;

		InputStreamReader reader = new InputStreamReader(inputStream);
		ErrorReporter reporter = new ErrorReporter() {

			public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
				if (line < 0) {
					System.err.println("\n[WARNING] " + message);
				} else {
					System.err.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
				}
			}

			public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
				if (line < 0) {
					System.err.println("\n[ERROR] " + message);
				} else {
					System.err.println("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
				}
			}

			public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
				error(message, sourceName, line, lineSource, lineOffset);
				return new EvaluatorException(message);
			}
		};
		JavaScriptCompressor compressor = new JavaScriptCompressor(reader, reporter);
		Writer out = new StringWriter();
		compressor.compress(out, linebreakpos, munge, verbose, preserveAllSemiColons, disableOptimizations);
		return out.toString();
	}
	
	public static String cssCompressor(FileInputStream inputStream) throws Exception {
		int linebreakpos = -1;
		InputStreamReader reader = new InputStreamReader(inputStream);
		CssCompressor compressor = new CssCompressor(reader);
		Writer out = new StringWriter();
		compressor.compress(out, linebreakpos);
		return out.toString();
	}
}
