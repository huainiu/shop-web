<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<div class="bdg_mask"></div>
<div class="bdg_goods bdg_goods_none" style="display:none;">
	<div class="bdg_goodsposrel">
		<h4><a class="closeall" data-attr="101602"></a>提示：</h4>
		<div class="bdg-none">
			<img class="s-img" src="http://y.b5mcdn.com/images/search/bdg-none-s-img.png" alt="Sorry">
			<h5>你想查看的商品已下架，<br />请换一个试试！</h5>
			你可以：<br />
			1、检查刚才的输入<br />
			2、返回 <a href="http://www.b5m.com/" class="go-home">帮5买首页</a>
		</div>
	</div>
</div>
<div class="bdg_goods bdg_goods_have">
	<div class="bdg_goodsposrel">
		<h4><a class="closeall" data-attr="101602"></a>帮我买代购单</h4>
		<input id="_goodsPriceAvg" type="hidden">
		<input id="_goodsPrice" type="hidden">
		<input id="_goodsUrl" type="hidden">
		<input id="_goodsImgUrl" type="hidden">
		<input id="_goodsName" type="hidden">
		<input id="_goodsSource" type="hidden">
		<input id="_docId" type="hidden">
		<div class="goods_information">
			<div class="show_pic goods_informationlr">
				<div class="imgbox">
					<img src="" width="144" height="144" />
				</div>
				<div class="show_text">
					<p class="goodsname">HTC&nbsp;Desire&nbsp;816w 3G(WCDMA/GMS)手机&nbsp;轻盈白&nbsp;双卡双待</p>
				</div>
				<div class="pos_bdg"></div>
			</div>
			<div class="bdg_guige_box">
				<div class="goods_informationlr">
					<div class="bdg_progress_bar">
						<div class="progress_barcor"><img src="http://y.b5mcdn.com/images/search/daigou/5-121204194026.gif" /></div>
						<p>商品信息正在玩命加载中...</p>
					</div>
				</div>
				<div class="guigeboxcurbox">
					<div class="guigebox">
						<div class="guige_refresh">
							<!-- <dl>
								<dt>颜色</dt>
								<dd>
									<ul class="ggxz gg_color">
										<li><a href="javascript:void(0)">红色<b></b></a></li>
										<li><a href="javascript:void(0)">蓝色<b></b></a></li>
										<li><a href="javascript:void(0)">白色<b></b></a></li>
										<li><a href="javascript:void(0)">灰色<b></b></a></li>
										<li><a href="javascript:void(0)">灰色<b></b></a></li>
									</ul>
								</dd>
								<div class="clear"></div>
							</dl>
							<dl>
								<dt>规格</dt>
								<dd>
									<ul class="ggxz gg_size">
										<li><a href="javascript:void(0)">S<b></b></a></li>
										<li><a href="javascript:void(0)">XS<b></b></a></li>
										<li><a href="javascript:void(0)">M<b></b></a></li>
										<li><a href="javascript:void(0)">L<b></b></a></li>
										<li><a href="javascript:void(0)">XL<b></b></a></li>
									</ul>
								</dd>
								<div class="clear"></div>
							</dl> -->
						</div>
						<dl>
							<dt>数量</dt>
							<dd>
								<div class="bdg_goodsnumber">
									<p class="numbox">
										<span class="p-stock">
				            				<a href="javascript:void(0);" hidefocus="" class="p-reduce p-reducel p-reduceno">-</a>
											<input type="text" class="p-text" value="1" id="_goodsNum" maxlength="8" title="请输入购买量">
											<a href="javascript:void(0);" hidefocus="" class="p-reduce p-reducer">+</a>
				        				</span>
									</p>
								</div>
							</dd>
							<div class="clear"></div>
						</dl>
					</div>
					<div class="gg_error_message">
						<span></span>请选择你要的商品信息
					</div>
					<a class="guige_close" style="display: none;">&times;</a>
					<!--<div class="bdg_goodsnumber">
						<p class="numbox">
							<span class="p-stock">
								<b>数量</b><a href="#" hidefocus="" class="p-reduce p-reducel p-reduceno">-</a>
								<input type="text" class="p-text" value="1" maxlength="8" title="请输入购买量">
								<a href="#" hidefocus="" class="p-reduce p-reducer">+</a>
							</span>
						</p>
					</div>-->
				</div>
			</div>
				
			<div class="goods_informationlr">
				<!-- <div class="show_pic">
					<div class="imgbox">
						<img src="" width="207" height="207"/>
					</div>
					<div class="pos_bdg"></div>
				</div> -->
				<!-- <div class="show_text">
					<p class="goodsname">HTC&nbsp;Desire&nbsp;816w 3G(WCDMA/GMS)手机&nbsp;轻盈白&nbsp;双卡双待</p>
					<p class="goodsprice"><span>全网均价：</span>¥3999</p>
				</div> -->
				<div class="bdg_price">
					<div class="bdg_pricel">
						<p class="pricetitle">全网均价</p>
						<p class="pricenum pricenum_graycorlor" id="averageprice"><span>¥</span>3999</p>
					</div>
					<div class="bdg_pricec">
						<p class="pricetitle">帮5买代购单价</p>
						<p class="pricenum" id="daigouprice"><span>¥</span>3799</p>
					</div>
					<div class="bdg_pricer">
						<p class="pricetitle">节省</p>
						<p class="pricenum" id="remainprice"><span>¥</span>188</p>
					</div>
					<div class="clear"></div>
				</div>
				<div class="price_list">
					<div class="price_listbg">
						<!-- <div class="price_listy">
							<span><a class="problemicon"></a>¥12</span>帮我买保价费
							<div class="b5mcommitment_posbg">
								<div class="b5mcommitment_pos">
									<h6>B5M承诺：</h6>
									<p>1.代购商品降价时，必<b>足额返利</b>或<b>返等价值帮钻</b>（可用于优惠购买全网任意商品）；</p>
									<p>2.代购商品价格上涨时，必<b>全额退款</b>；</p>
									<p>3.20元以内不再加收额外邮费；</p>
									<div class="pos_icon"></div>
								</div>
							</div>
						</div> -->
						<div class="price_listz"><span>¥3811</span>总价</div>
					</div>
				</div>
				<a class="buynowbtn zftbtnbuynow" data-attr="1016" >立即购买</a>
				<a class="zfbbtn zftbtnsuccess"><b></b>加入购物车</a>
				<p class="bdg_b5mbdg_instructions"><a href="http://cdn.bang5mai.com/upload/web/cmsphp/html/bwmstatement.htm" target="_blank">帮我买代购申明&gt;</a></p>
			</div>
		</div>
		
		<!-- <div class="bdg_posadress">
			<div class="add_useradress">
				<div class="useradd_adress"></div>
				<p class="add_adress" data-attr="101601">请添加收货地址</p>
				<div class="bdg_tishi" style="display: none;"><p><a class="closetishi"></a></p></div>
			</div>
			<div class="adresslistbox">
				<div class="adresslist">
					
				</div>
				<div class="bdgselest_tishi"><p><a class="closesel_ts"></a></p></div>
			</div>
			<div class="bdg_addadressbg"></div>
		</div> -->
		
		<!--购物车-->
		<div class="shopping_cart">
			<h4>您的购物车</h4>
			<div class="shoppingimg_box">
 				<div class="shoppingimg"></div>
			</div>
			<a class="btnpayment" href="javascript:void(0)" target="_blank">立即付款</a>
		</div>
		
		<!--点击添加新地址弹窗-->
		<!-- 
		<div class="add_newsadressbg">
			<div class="add_newsadress">
				<h5><a class="close_addadress"></a><span id="close_addadress_text">添加新地址</span></h5>
				<div class="adress_banner">
					<input id="txtid" type="hidden">
					<div class="form_adress">
						<b><a>*&nbsp;</a>所在地区：</b>
						<p>
							<select id="seachprov" name="seachprov" class="seladrass"></select>
							<select id="seachcity" name="seachcity" class="seladrass"></select>
							<select id="seachdistrict" name="seachdistrict" class="seladrass"></select>
						</p>
						<div class="clear"></div>	
					</div>
					<div class="form_adress">
						<b><a>*&nbsp;</a>街道地址：</b>
						<p><textarea name="txtaddress" id="txtaddress" cols="" rows="" maxlength="120" placeholder="不需要重复填写省市区，必须大于5个字符，小于120个字符"  class="taxtarea"></textarea></p>
						<div class="clear"></div>	
					</div>
					<div class="form_adress">
						<b><a>*&nbsp;</a>邮政编码：</b>
						<p><input name="txtzipcode" id="txtzipcode" maxlength="6" type="text" class="inptext inpwidm" /><font color="red">必须6位</font></p>
						<div class="clear"></div>	
					</div>
					<div class="form_adress">
						<b><a>*&nbsp;</a>收货人姓名：</b>
						<p><input name="txtreceivername" id="txtreceivername" maxlength="25" type="text" class="inptext" /></p>
						<div class="clear"></div>	
					</div>
					<div class="form_adress">
						<b>QQ：</b>
						<p><input name="txtqq" type="text" maxlength="12" class="inptext"></p>
						<div class="clear"></div>	
					</div>
					<div class="form_adress">
						<b>手机号码：</b>
						<p><input name="txtmobile" id="txtmobile" type="text" maxlength="11" class="inptext" />&nbsp;<span class="show">手机和座机必须填写至少一项</span></p>
						<div class="clear"></div>	
					</div>
					<div class="form_adress">
						<b>座机号码：</b>
						<p>
							<input name="txtareacode" id="txtareacode" maxlength="5" type="text" placeholder="区号" class="inptext inpwid" />&nbsp;-&nbsp;<input name="txttel" id="txttel" type="text" maxlength="10" placeholder="电话" class="inptext inpwidm" />&nbsp;-&nbsp;<input name="txtextension" id="txtextension" type="text" maxlength="5" placeholder="分机号" class="inptext inpwid" />
						</p>
						<div class="clear"></div>	
					</div>
					<div class="formadress_btn form_adress">
						<b></b>
						<p><a class="btn confirm" style="cursor:pointer" onclick="but_submit()">确&nbsp;定</a><a class="btn cancel" style="cursor:pointer" onclick="but_cancel()">取&nbsp;消</a></p>
					</div>
				</div>
			</div>
		</div> -->
		
		<!--点击支付宝弹窗-->
		<div class="bdg_zfbbg">
			<div class="bdg_zfb">
				<h5><a class="close_zfbbox"></a></h5>
				<div class="zfbwin_banner">
					<p class="zfbwin_text">请问您刚才...</p>
					<div class="zfbpay_status">
						<a class="paysuccess" href="${paysuccessurl}" target="_blank" data-attr="101801">支付成功</a>
						<a class="payfail" data-attr="101802">支付失败</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>