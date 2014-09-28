package com.b5m.service.www;

import java.util.List;

import com.b5m.bean.entity.CategoryDaigou;
/**
 * @author echo
 */
public interface CategoryDaigouService {
    /**
     *<font style="font-weight:bold">Description: </font> <br/>
     * 分页查询
     * @author echo
     * @email wuming@b5m.cn
     * @since 2014年4月2日 下午2:03:18
     *
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
	List<CategoryDaigou> queryAllCategory();
}
