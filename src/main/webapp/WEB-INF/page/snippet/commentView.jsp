<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage="" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<div class="b_title_price">
	<h2>点评(${commentPageDto.countRecNumber})</h2>
</div>
<table cellpadding="0" cellspacing="0" width="100%" class="box_wm">
	<tbody>
		<tr>
			<td width="200"><b class="num">${dataSet.rankDto.rank}</b><b>分</b></td>
			<td width="200">
				<div class="result_start">综合评分（满分5.0）</div>
				<div class="result_start">共评论(${commentPageDto.countRecNumber})条，有效评分(${dataSet.rankDto.hasRankCount})条</div>
			</td>
			<td width="200">
				<ul>
					<li><span>5分：</span> <span> <img
							src="sui/images/star10.gif" /> <img src="sui/images/star10.gif" />
							<img src="sui/images/star10.gif" /> <img
							src="sui/images/star10.gif" /> <img src="sui/images/star10.gif" />
					</span></li>
					<li><span>4分：</span> <span> <img
							src="sui/images/star10.gif" /> <img src="sui/images/star10.gif" />
							<img src="sui/images/star10.gif" /> <img
							src="sui/images/star10.gif" />
					</span></li>
					<li><span>3分：</span> <span> <img
							src="sui/images/star10.gif" /> <img src="sui/images/star10.gif" />
							<img src="sui/images/star10.gif" />
					</span></li>
					<li><span>2分：</span> <span> <img
							src="sui/images/star10.gif" /> <img src="sui/images/star10.gif" />
					</span></li>
					<li><span>1分：</span> <span> <img
							src="sui/images/star10.gif" />
					</span></li>
				</ul>
			</td>
			<td>
				<ul>
					<li>${dataSet.rankDto.rank5Per}%的人认为非常棒</li>
					<li>${dataSet.rankDto.rank4Per}%的人推荐</li>
					<li>${dataSet.rankDto.rank3Per}%的人认为一般</li>
					<li>${dataSet.rankDto.rank2Per}%的人认为不好</li>
					<li>${dataSet.rankDto.rank1Per}%的人认为很差</li>
				</ul>
			</td>

		</tr>
	</tbody>
</table>
<!--星E-->

<!--ComS-->
<s:if
	test="#dataSet.commentMap.advantage!=null||#dataSet.commentMap.disadvantage!=null">
	<div class="commentFilterDiv">
		<div class="comment_t">
			<div class="b_title_price">
				<h2>大家都怎么说</h2>
			</div>
			<div class="comment_box">
				<s:if test="#dataSet.commentMap.advantage!=null">
					<dl class="clear-fix">
						<dt>
							<span class="red">优点</span>
						</dt>
						<dd>
							<s:iterator var="item" value="#dataSet.commentMap.advantage"
								status="status">
								<s:if test="#status.index<4">
									<s:if test="commentValue==#item[0]">
										<span class="active" style="cursor: pointer;"
											onclick="changeComentValue('')" title="${item[0]}">${item[0]}<b>&times;</b></span>
									</s:if>
									<s:else>
										<span style="cursor: pointer;"
											onclick="changeComentValue('${item[0]}')" title="${item[0]}">${item[0]}</span>
									</s:else>
								</s:if>
							</s:iterator>
						</dd>
					</dl>
				</s:if>
				<s:if test="#dataSet.commentMap.disadvantage!=null">
					<dl class="clear-fix">
						<dt>
							<span class="blue">缺点</span>
						</dt>
						<dd>
							<s:iterator var="item" value="#dataSet.commentMap.disadvantage"
								status="status">
								<s:if test="#status.index<4">
									<s:if test="commentValue==#item[0]">
										<span class="active" style="cursor: pointer;"
											onclick="changeComentValue('')" title="${item[0]}">${item[0]}<b>&times;</b></span>
									</s:if>
									<s:else>
										<span style="cursor: pointer;"
											onclick="changeComentValue('${item[0]}')" title="${item[0]}">${item[0]}</span>
									</s:else>
								</s:if>
							</s:iterator>
						</dd>
					</dl>
				</s:if>
			</div>
		</div>
	</div>
</s:if>