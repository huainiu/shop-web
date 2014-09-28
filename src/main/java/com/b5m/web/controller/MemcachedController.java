package com.b5m.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.base.common.utils.DateTools;
import com.b5m.common.utils.UcMemCachedUtils;
import com.b5m.web.controller.base.AbstractBaseController;

@Controller
@RequestMapping("/memcache")
public class MemcachedController extends AbstractBaseController {
	
	@RequestMapping("/update")
	@ResponseBody
	public void update() throws Exception {
		String time = DateTools.formate(DateTools.now(), "yyyyMMddHHmmss");
		UcMemCachedUtils.setCache("v_version", time, 60 * 60 * 24 * 30);
		output(time);
	}
}
