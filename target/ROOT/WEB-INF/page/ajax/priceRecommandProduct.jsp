<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="b5m" uri="http://www.b5m.com/tags"%>  

<c:if test="${fn:length(dataSet.imageInfos) > 0}">
	<div class="pk_cankao">
	   	<ul>
	       <li class="k0">商家</li>
	          <li class="k1">商品</li>
	          <li class="k2">价格</li>
	          <li class="k3">服务保障</li>
	          <li class="k4">操作</li>
	      </ul>
	</div>
	<table id="recommandProductTable" width="100%" cellspacing="0" cellpadding="0">
		<tbody>
			<c:forEach var="item" items="${dataSet.imageInfos}" varStatus="status">
			    <c:choose>
			    	<c:when test="${status.count > 4}">
			    		<tr class="hideNode">
			    	</c:when>
			    	<c:otherwise>
			    		<tr>
			    	</c:otherwise>
			    </c:choose>
				    	    <c:set var="splInfo" value="${dataSet.splInfoMap[item.Source]}"/>
						  	<td class="t0">
						  		<c:set value="${fn:split(item.Source, ',')}" var="sourceList"/>
						  		<c:set value="${fn:length(sourceList)}" var="sourceCount"/>
						  		<c:choose>
						  			<c:when test="${sourceCount > 1}">
						  				共${sourceCount}家商家报价
						  			</c:when>
						  			<c:otherwise>
						  				<c:choose>
						  					<c:when test="${splInfo.logo != null && splInfo.logo != ''}">
						  						<div class="price_shangjia">
							  						<a href="${splInfo.url}">
							            				<img src="${splInfo.logo}"/>
							            			</a>
						            			</div>
						  					</c:when>
						  					<c:otherwise>
						  						${item.Source}
						  					</c:otherwise>
						  				</c:choose>
						  			</c:otherwise>
						  		</c:choose>
		            		</td>
		                	<td class="t1"><div class="com_pic_120"><a href="${b5m:gotoCps(item.Source,item.Url)}" target="_blank"></a></div></td>
		                    <td class="t2">
		                        <div class="text_over"><a href="${b5m:gotoCps(item.Source,item.Url)}" target="_blank">${item.Title}</a></div>
		                      	<c:if test="${item.itemCount == 1} ">
			                       	<div class="result_start">
				                        <a href="javascript:void(0)" onclick="suiAddPdcToCollection('${b5ma}','${item.DocId}',this)" class="B_B addzj"></a>
			                        </div>
		                        </c:if>
		                    </td>
		                    <td class="t3">
		                        <div class="meny">
		                        	￥${item.price}
		                        	<%-- <span style="display: none;" class="J_J " docid="${item.DocId}" id="priceHistoryIcon${item.DocId}"></span><input type="hidden" id="priceHistoryData${item.DocId}" value="" /> --%>
		                        </div>
		                        <c:if test="${item.kaviSpl!=null && item.kaviSpl != '' } ">
	                        		<a href="korea" class="hanguo">韩国购</a>
	                        	</c:if>
	                        	<c:if test="${splInfo.fan!=null&&#splInfo.fan!=''}">
		                    		<div class="result_start"><span class="org">返</span>${splInfo.fan}</div>
		                    	</c:if>
		                    </td>
		                    <td class="t4">
		                    	<ul>
		                    		<c:if test="${splInfo.splService!=null && splInfo.splService!=''}">
		                    			<c:forTokens items="${splInfo.splService}" delims="," varStatus="status2" var="item2">
		                    				<c:if test="${status2.count < 4}">
			                    				<li class="i1"><b></b>${item2}</li>
		                    				</c:if>
		                    			</c:forTokens>
		                    		</c:if>
		                        </ul>
		                    </td>
		                    <td class="t6">
		                    	<c:choose>
		                    		<c:when test="${item.itemCount > 1}">
		                    		 	<c:set var="priceUrl" value=""/>
			                            <a href="${priceUrl}" target="_blank" class="btn_price frame">帮5比</a>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<a href="${b5m:gotoCps(item.Source,item.Url)}" target="_blank" class="btn_price frame">去购买</a>
		                    		</c:otherwise>
		                    	</c:choose>
		                    </td>
	         			</tr>
			</c:forEach>
		 </tbody>
	</table>
	<script>
		function showMoreRecommandProduct(){
			var trHideArray = $("#recommandProductTable .hideNode");
			if(trHideArray[0]!=null){
				for(var i=0,j=0;i<trHideArray.length&&j<4;i++,j++){
					trHideArray.eq(i).removeClass("hideNode");
				}
			}
			trHideArray  = $("#recommandProductTable .hideNode");
			var length = $("#recommandProductTable tr").length;
			if(trHideArray[0]==null){
				$("#showMoreRecommandProductSpan").hide();
				$("#hideMoreRecommandProductSpan").show();
				$("#showMoreRecommandProductSpan").html("展开更多(4,"+length+")");
			}else{
				var hideLength = trHideArray.length;
				var showLength = length - hideLength;
				$("#showMoreRecommandProductSpan").html("展开更多("+showLength+","+length+")");
			}
		}
		function hideMoreRecommandProduct(){
			var trArray = $("#recommandProductTable tr");
			for(var i=0;i<trArray.length;i++){
				if(i>=4){
					trArray.eq(i).addClass("hideNode");
				}
			}
			$("#showMoreRecommandProductSpan").show();
			$("#hideMoreRecommandProductSpan").hide();
			goTo("recommandProductTable");
		}
	</script>
	<c:if test="${fn:length(dataSet.imageInfos) > 0}">
		<div class="overflow_text">
		   	<span class="shang" style="cursor: pointer;" id="showMoreRecommandProductSpan" onclick="showMoreRecommandProduct()">展开更多(4/${fn:length(dataSet.imageInfos)})</span>
		    <span class="xia" style="cursor: pointer;display:none" id="hideMoreRecommandProductSpan" onclick="hideMoreRecommandProduct()">收起全部</span>	
		</div>
	</c:if>
</c:if>