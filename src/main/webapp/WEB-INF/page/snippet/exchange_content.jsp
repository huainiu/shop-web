<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>
<%@ page session="false"%>
<div class="wp super-bangdou mt20">
	<div class="bangdou-ad">
		<a href="http://www.b5m.com/bangdou"><img src="${basePath }images/banner.jpg" alt=""></a>
	</div>
	<div class="bangdou-detail mt15 cfx">
		<div class="bangdou-detail-pic bd-detail-newpic">
			<div class="pic bangdou-pic-big">
				<ul>
					<c:forEach items="${fn:split(produce.Picture, ',') }" var="pic" varStatus="status">
						<li><img src="${pic }"></li>
					</c:forEach>
				</ul>
			</div>
			<div class="bangdou-pic-qiehuan cfx">
				<c:if test="${fn:length(fn:split(produce.Picture, ','))>4 }">
					<a data-page="" class="bd-pic-l" href="javascript:void(0);"></a>
					<a data-page="" class="bd-pic-r" href="javascript:void(0);"></a>
				</c:if>
				<div class="bangdou-pic-qiehuan-box">
					<ul>
						<c:forEach items="${fn:split(produce.Picture, ',') }" var="pic" varStatus="status">
							<c:set var="c" value=""></c:set>
							<c:if test="${status.first }">
								<c:set var="c" value="border-red"></c:set>
							</c:if>
							<li data-page="${status.count }" class="${c }"><em></em><img src="${pic }"></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<div class="bangdou-new-title">
			<span class="bd-newtitl-name" id="title">${produce.Title }</span>
		</div>
		<fmt:formatNumber var="beans" value="${produce.Price * 100}" pattern="#" />
		<c:if test="${produce.Price >= 10.0 || refer == 'hot'}">
			<div style="display: block;" class="bangdou-new-list">
				<div class="list-cima" id="buy-count">
					<span class="cima-l">购买数量：</span>
					<p class="bd-input-buttom">
						<input type="text" value="1" class="count" id="count" disabled="disabled"> 
						<c:if test="${produce.referRule != '1' }">
							<span class="inp-but-a"> <a class="but-a-top" id="inc"></a> <a class="but-a-dow" id="dec"></a>	</span>
						</c:if>
					</p>
				</div>
				<span class="bangdou-you" id="remain-tip" style="display: none;">您的帮钻余额不足，无法继续此次交易！</span>
				<div class="bangdou-shop-price" data-mps="21001">
                    <p class="bd-shop-midd">
                        <span>价格(¥)：<b id="price">${produce.Price}</b>所需帮钻：<strong id="beans-pay">${beans }</strong>帮钻</span>
                    </p>
                    <p class="bd-shop-r" style="display:none" id="buzuP">
                        <span class="shop-price-prompt">帮钻余额：<b id="beans-balance">${userBeans}</b>帮钻</span>
                        <a class="shop-price-bz" href="http://www.b5m.com/bangdou" target="_blank" data-mps="1">赚取帮钻</a>
                        <a class="shop-price-buttom" id="buyBtn" href="javascript:void(0)" data-mps="2">立即购买</a>
                        <script>
                        $(function(){
                        	var id = "${produce.DocId}";
                        	var ref="";
                        	ref = "${produce.ref}";
                        	if(id.length==32){
                        		if(ref!=""){
                                	$('#buyBtn').attr("href","${basePath }daigoucart/center.htm?docId=${produce.DocId}&ref=${produce.ref}"); 
                        		}else{
                                	$('#buyBtn').attr("href","${basePath }daigoucart/center.htm?docId=${produce.DocId}"); 
                        		}
                        	}else{
                            	$('#buyBtn').attr("href","${basePath }daigoucart/center.htm?url="+encodeURIComponent("${produce.Url}")); 
                        	}                       	
                        });
                        </script>
                    </p>
                    <p class="bd-shop-r shop-yuez" style="display:none" id="zugouP">
                        <span class="shop-price-prompt">帮钻余额：<b>${userBeans}</b>帮钻</span>
                    </p>
                </div>
			</div>
		</c:if>
		<c:if test="${produce.Price < 10.0 && refer != 'hot' }">
			<div class="bangdou-shiyun" style="display: block">
				<span><dfn>抱歉！暂不支持10元以下商品兑换</dfn>建议您：<a href="${basePath }search/s/___image______10__________${produce.Title}.html">选择其他相关商品&gt;</a></span>
			</div>
		</c:if>
	</div>
	
	<div id="infoDiv" style="display:none">	
		<div class="bangdou-address mt15 new-mt14">
			<h3>
				<!-- <dfn>*</dfn> -->
				备注信息<label>为保证帮5买准确提供给您想要的礼品，请点击“<a href="${produce.Url }" target="_blank">查看参考商品</a>”填写详细颜色、尺寸、型号、质地等具体信息
				</label>
			</h3>
			<div class="new-address-list">
				<span class=""> <a href="javascript:void(0);" id="sel-sku"></a>
				</span>
			</div>
			<textarea class="textare" id="remark" placeholder="请输入颜色、尺寸、型号、质地等具体信息"></textarea>
		</div>
	
		<div class="bangdou-address mt20">
			<h3>收货地址</h3>
			<div class="bangdou-address-list">
				<c:forEach items="${address.data}" var="item" varStatus="status">
					<c:set var="cur" value=""></c:set>
					<c:set var="check" value=""></c:set>
					<c:if test="${status.index == 0 }">
						<c:set var="cur" value="current"></c:set>
						<c:set var="check" value="checked=\"checked\""></c:set>
					</c:if>
					<div class="address-item ${cur }" attr-id="${item.id }">
						<h6 class="cfx">
							<input type="radio" name="address" value="${item.id }" class="fl address-item-radio" ${check } /> <input type="hidden" value='${item }' class="itemJson" />
							<p class="fl address-item-spe">${item.provinceName } ${item.cityName } ${item.districtName } ${item.detailAddress }</p>
							<p class="fl address-item-user">
								（<span class="username">${item.userName }</span> 收）
							</p>
							<c:if test="${item.mobile != null && item.mobile != '' }">
								<p class="fl address-item-tel">${item.mobile}</p>
							</c:if>
							<c:if test="${item.mobile == null || item.mobile == '' }">
								<p class="fl address-item-tel">${item.phoneDistrict}-${item.phone}-${item.phoneExtension }</p>
							</c:if>
							<p class="fr address-item-fun">
								<a href="javascript:void(0);" class="update-address">修改</a> <em>|</em> <a href="javascript:void(0);" class="delete-address">删除</a>
							</p>
						</h6>
					</div>
				</c:forEach>
				<c:set value="current" var="cur"></c:set>
				<c:set value="display" var="display"></c:set>
				<c:set var="check" value="checked=\"checked\""></c:set>
				<c:if test="${fn:length(address.data) > 0 }">
					<c:set value="" var="cur"></c:set>
					<c:set value="none" var="display"></c:set>
					<c:set var="check" value=""></c:set>
				</c:if>
				<div class="address-item ${cur }">
					<h6 class="cfx">
						<input type="radio" name="address" value="new" class="fl address-item-radio" ${check } />
						<p class="fl address-item-add">使用新地址</p>
					</h6>
					<div class="adress-new" style="display: ${display}">
						<p class="new-field cfx">
							<label class="fl"><em class="star-m">*</em>省市区：</label> <select id="province" name="province">
							</select> <select id="city" name="city">
							</select> <select id="area" name="area">
							</select>
						</p>
						<p class="new-field cfx">
							<label class="fl"><em class="star-m">*</em>街道地址：</label>
							<textarea class="txt" id="detailAddress" placeholder="不需要重复填写省市区，请直接填写具体街道地址"></textarea>
						</p>
						<p class="new-field cfx">
							<label class="fl"><em class="star-m">*</em>邮编：</label> <input class="txt w100" id="zipcode" type="text" />
						</p>
						<p class="new-field cfx">
							<label class="fl"><em class="star-m">*</em>收件人姓名：</label> <input class="txt w160" id="username" type="text" />
						</p>
						<p class="new-field cfx">
							<label class="fl"><em class="star-m">*</em>手机：</label> <input class="fl txt w160" type="text" id="mobile" placeholder="手机和座机必须填写至少一项" /> <span class="fl other">或 座机：</span> <input class="fl txt w50" id="phoneDistrict" type="text" placeholder="区号" /> <i class="fl slice"></i> <input class="fl txt w90" id="phone" type="text" placeholder="电话" /> <i class="fl slice"></i> <input class="fl txt w50" id="phoneExtension" type="text" placeholder="分机号" />
						</p>
						<p class="new-field cfx">
							<label class="fl"><em class="star-m">*</em>QQ：</label> <input class="txt w160" id="qq" type="text" />
						</p>
						<p class="new-field cfx">
							<a href="javascript:void(0);" id="address-btn" class="confirm">确认收货地址</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="bangdou-address mt15 new-m15">
			<h3>
				<input type="checkbox" class="setAgree" name="setAgree" id="setAgree" checked="checked"><label>同意兑换协议</label>
			</h3>
			<div class="bangdou-protocol">
				<div class="bangdou-protocol-box">
					<p class="cfx">
						<em>（1）</em>目前帮5买每日限兑换<span class="c-yellow">100单</span>，之后的订单将会延后处理，订单状态请随时关注“我的兑换”和站内信。
					</p>
					<p class="cfx">
						<em>（2）</em>试运营期间全网兑换商品说明：<br> <span class="c-yellow">● </span>要求兑换订单金额在<span class="c-yellow">10元及10元以上商品</span>，低于10元商品将不安排兑换；<br> <span class="c-yellow">●</span> 暂时不支持兑换<span class="c-yellow">药品类</span>、<span class="c-yellow">部分食品类</span>及<span class="c-yellow">其他不适合兑换</span>的商品。
					</p>
					<p class="cfx">
						<em>（3）</em>因商品颜色、尺码、套餐不同，限时特卖等原因，可能会引起价格上有调整，具体帮钻扣除以最终购买价格为准（如扣除帮钻数低于实际购买价格需补齐差价），故实际兑换价格还需客服人员与您进一步确认。
					</p>
					<p class="cfx">
						<em>（4）</em>因“京东”等商城有部分“买一送一”的商品（送赠品），对于<span class="c-yellow">直接兑换赠品</span>的请求，帮5买不予兑换。
					</p>
					<p class="cfx">
						<em>（5）</em>运费问题：<br> <span class="c-yellow">● </span>系统将稍后扣除购买商品产生的运费，具体额度将与您亲自购买时产生的运费额度一致，如有疑问请咨询客服^^；<br> <span class="c-yellow">● </span>如帮钻余额不足可能会导致此次兑换请求失败，客服会与您取得联系，请求补齐差额，届时您有权撤销兑换订单等待再次兑换；如一定时间段内您未补齐差额，系统将自动<br>&nbsp;&nbsp;&nbsp;关闭订单。请求您的理解。
					</p>
					<p class="cfx">
						<em>（6）</em>为保证您的兑换订单及时处理，请认真填写收货信息，尤其手机号、QQ等需要保持畅通，因没有联系方式或联系方式出错导致无法取得联系，引发兑换订单延时处理的问题全权由用户承担，只要保证您的信息准确，帮5买将及时竭诚为您服务。
					</p>
					<p class="cfx">
						<em>（7）</em>如无其他客观原因，客服将在十个工作日内联系您处理订单，如无法联系或其他原因导致延迟，十个工作日后系统将自行关闭订单。
					</p>
					<p class="cfx">
						<em>（8）</em>本兑换相应规则的最终解释权在帮5买，有问题请联系客服（客服QQ 362251877、电话400-085-9285）。
					</p>
				</div>
			</div>
			<a class="view-more" href="javascript:void(0)">下拉查看更多<em class="dow"></em></a>
		</div>
		<div class="bangdou-buy mt15 new-m15">
			<a href="javascript:void(0);" class="fr determine determ-bg" id="confirm-btn" data-attr="102102">确定兑换</a> <span class="fr">应付帮钻：<em id="beans-pay-2">${beans }</em>个
			</span>
			<div class="select-address" style="display: none;">
				<i class="icon-tips icon-tips-error"></i><i id="error-tips">请先选择收货地址</i><i class="arr-top-outer"></i><i class="arr-top-inner"></i>
			</div>
		</div>
	</div>
	<div style="margin-top: 50px">
		<script type="text/javascript">
			var cpro_id = "u1583453";
		</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
	</div>
</div>
<div id="loading" style="height: 100%; width: 100%; margin: 0 auto; position: absolute; z-index: 10000; top: 0px; display: none;">
	<div style="width: 200px; height: 50px; margin: 0 auto; position: absolute; top: 65%; left: 45%; background: url(http://y.b5mcdn.com/images/search/ajax-loader.gif) no-repeat 50% 50%;"></div>
</div>
<input id="address-id" type="hidden" value='${fn:length(address.data) > 0 ? address.data[0].id : ""}' />
<input id="goods-url" type="hidden" value="${produce.Url }" />
<input id="beans-price" type="hidden" value="${beans }" />
<input id="refer" type="hidden" value="${refer }" />
<input id="referRule" type="hidden" value="${produce.referRule }" />
<input id="docId" type="hidden" value="${produce.docId }" />
<script type="text/javascript" src="http://y.b5mcdn.com/scripts/exchange/exchangeNewFed.js"></script>
<script src="http://y.b5mcdn.com/scripts/search/PCASClass.js?t=${today}"></script>
<script src="${basePath}js/js/exchange.js?t=${today}"></script>
