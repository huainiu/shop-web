package com.b5m.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.b5m.bean.entity.Suppliser;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-7-19
 * @email echo.weng@b5m.com
 */
public class SelectTagTest {
	private SelectTag selectTag;
	private PageContext pageContext;
	private JspWriter jspWriter;
	
	@Before
	public void setUp(){
		selectTag = new SelectTag();
		selectTag.setName("supplierId");
		selectTag.setId("supplierId");
		selectTag.setNoEmpty(true);
		selectTag.setStyleClass("select");
		selectTag.setList(getSupplisers());
		selectTag.setValue("1");
		pageContext = EasyMock.createMock(PageContext.class);
		jspWriter = EasyMock.createMock(JspWriter.class);
		selectTag.setPageContext(pageContext);
	}
	
	@Test
	public void testDoStartTag() throws Exception{
		EasyMock.expect(pageContext.getOut()).andReturn(jspWriter);
		jspWriter.write(expectReturn());
		EasyMock.expectLastCall();
		EasyMock.replay(pageContext, jspWriter);
		selectTag.doStartTag();
	}
	
	@Test
	public void testGetValue(){
		Suppliser suppliser = createSuppliser(1L, "淘宝");
		Assert.assertEquals("1", selectTag.getValue(suppliser, "id", ""));
	}
	
	private List<Suppliser> getSupplisers(){
		List<Suppliser> supplisers = new ArrayList<Suppliser>();
		supplisers.add(createSuppliser(1L, "淘宝"));
		supplisers.add(createSuppliser(2L, "当当网"));
		supplisers.add(createSuppliser(3L, "京东"));
		return supplisers;
	}
	
	private Suppliser createSuppliser(Long id, String name){
		Suppliser suppliser = new Suppliser();
		suppliser.setId(id);
		suppliser.setName(name);
		return suppliser;
	}
	
	private String expectReturn(){
		StringBuffer select = new StringBuffer("<select name=\"supplierId\"");
		select.append(" id=\"supplierId\"");
		select.append("class=\"select\"");
		select.append(">");
		select.append("<option selected=\"selected\" value=\"1\">淘宝</option>");
		select.append("<option value=\"2\">当当网</option>");
		select.append("<option value=\"3\">京东</option>");
		select.append("</select>");
		return select.toString();
	}
	
	@After
	public void tearDown(){
		selectTag = null;
		pageContext = null;
		jspWriter = null;
	}
}
