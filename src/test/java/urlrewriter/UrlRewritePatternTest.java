package urlrewriter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

public class UrlRewritePatternTest {

	@Test
	public void test() throws UnsupportedEncodingException {
		/*final String patternStr = "^/search/s/all/([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)_([^_]*)/?\\.html$";
		Pattern p = Pattern.compile(patternStr);
		Assert.assertEquals(true, p.matcher("/search/s/all/________________ipod.html").matches());
	
		Assert.assertEquals(true, p.matcher("/search/s/all/________________\\.html").matches());*/
		String value = URLEncoder.encode("V+", "UTF-8");
		System.out.println(value);
	}

}
