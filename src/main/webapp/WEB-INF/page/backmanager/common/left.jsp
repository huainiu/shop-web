<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="col-sub">
	<dl class="item">
		<dt class="item-name">商家印象</dt>
		<dd class="item-list"><a href="${basePath }backmanager/impress/audit/page.htm" id="impress-audit">商家印象审核</a></dd>
		<dd class="item-list"><a href="${basePath }backmanager/impress/manage/page.htm" id="impress-manager">商家印象管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">用户评价</dt>
		<dd class="item-list"><a href="${basePath }backmanager/comment/audit/page.htm" id="comment-audit">商家评价审核</a></dd>
		<dd class="item-list"><a href="${basePath }backmanager/comment/manage/page.htm" id="comment-manager">商家评价管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">商家管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/supplier/manage/page.htm" id="suppliser-manage">商家管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">敏感词管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/word/manage/page.htm" id="word-manage">敏感词列表</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">类目管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/category/page.htm" id="category-manage">类目管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">用户反馈管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/recommend/page.htm" id="recommend-manage">用户反馈管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">SEO管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/info/infos.htm" id="info-manage">资讯管理</a></dd>
		<dd class="item-list"><a href="${basePath }backmanager/key/keys.htm" id="key-manage">关键词管理</a></dd>
		<dd class="item-list"><a href="${basePath }backmanager/link/links.htm" id="link-manage">链接管理</a></dd>
		<dd class="item-list"><a href="${basePath }backmanager/meta/metas.htm" id="meta-manage">描述管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">权限管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/user.htm" id="user-manage">用户管理</a></dd>
		<dd class="item-list"><a href="${basePath }backmanager/role.htm" id="role-manage">角色管理</a></dd>
		<dd class="item-list"><a href="${basePath }backmanager/resource.htm" id="resource-manage">资源管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">数据源管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/config/get.htm" id="source-manage">价格趋势数据源管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">配置管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/pageConfig/get.htm" id="pageConfig-manage">page配置管理</a></dd>
	</dl>
	<dl class="item">
		<dt class="item-name">系统管理</dt>
		<dd class="item-list"><a href="${basePath }backmanager/cache/reload/page.htm" id="system-cache">缓存管理</a></dd>
		<dd class="item-list"><a href="${basePath }backmanager/user/changepassword/page.htm" id="system-passwordchange">密码修改</a></dd>
	</dl>
</div>
<script type="text/javascript">
$(function(){
	$("dl.item dt").on("click", function(){
		$(this).siblings().slideToggle();
	});
});
</script>