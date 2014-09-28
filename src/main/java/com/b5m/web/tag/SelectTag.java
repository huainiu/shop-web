package com.b5m.web.tag;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-7-19
 * @email echo.weng@b5m.com
 */
public class SelectTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5682162525936616860L;

	private List list;

	private String value;

	private String name;

	private String valueProperty;

	private String viewProperty;

	private boolean noEmpty = false;

	private String id;

	private String styleClass;

	private String style;

	@Override
	public int doStartTag() throws JspException {
		try {
			out(this.pageContext);
			return 0;
		} catch (Exception ex) {
			throw new JspException(ex.toString(), ex);
		}
	}

	protected void out(PageContext pageContext) throws Exception {
		JspWriter jspWriter = pageContext.getOut();
		StringBuffer select = new StringBuffer("<select name=\"" + this.name
				+ "\"");
		if (!StringUtils.isEmpty(this.id)) {
			select.append(" id=\"" + this.id + "\"");
		}
		if (!StringUtils.isEmpty(this.style)) {
			select.append("style=\"" + this.style + "\"");
		}
		if (!StringUtils.isEmpty(this.styleClass)) {
			select.append("class=\"" + this.styleClass + "\"");
		}
		select.append(">");
		if (!noEmpty)
			select.append("<option value=\"\">全部</option>");
		if (list == null || list.isEmpty()) {
			select.append("</select>");
			jspWriter.write(select.toString());
			return;
		}
		for (Object object : list) {
			String compValue = getValue(object, this.valueProperty, "id");
			String viewValue = getValue(object, this.viewProperty, "name");
			if (!StringUtils.isEmpty(this.value)
					&& compValue.equals(this.value)) {
				select.append("<option selected=\"selected\" value=\""
						+ compValue + "\">" + viewValue + "</option>");
			} else {
				select.append("<option value=\"" + compValue + "\">"
						+ viewValue + "</option>");
			}
		}
		select.append("</select>");
		jspWriter.write(select.toString());
	}

	protected String getValue(Object o, String property, String def) {
		if (o == null)
			return "";
		if (o instanceof String)
			return o.toString();
		if (StringUtils.isEmpty(property)) {
			property = def;
		}
		try {
			PropertyDescriptor descriptor = new PropertyDescriptor(property, o.getClass());
			Method method = descriptor.getReadMethod();
			Object v = method.invoke(o);
			if (v == null)
				return "";
			return v.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValueProperty() {
		return valueProperty;
	}

	public void setValueProperty(String valueProperty) {
		this.valueProperty = valueProperty;
	}

	public String getViewProperty() {
		return viewProperty;
	}

	public void setViewProperty(String viewProperty) {
		this.viewProperty = viewProperty;
	}

	public boolean isNoEmpty() {
		return noEmpty;
	}

	public void setNoEmpty(boolean noEmpty) {
		this.noEmpty = noEmpty;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
