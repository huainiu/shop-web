<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="false"%>
<script id="productList" type="text/x-handlebars-template">
    <h3 class="recommend-prod">热门商品</h3>
    <ul class="grid-view cf">
        {{#each val}}
        <li class="grid-mod">
            <a href="{{url}}" class="pic">
                <img src="{{imageUrl}}" alt="商品图片">
            </a>
            <div class="grid-item">
                <a href="{{url}}" title="{{title}}" class="des">{{title}}</a>
            </div>
            <div class="grid-item">
                <a href="{{url}}" title="去购买" class="go-buy r">去看看</a>
                <strong class="price"><b>¥</b>{{price}}</strong>
            </div>
        </li>
        {{/each}}
    </ul>
</script>
