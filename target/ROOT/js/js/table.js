var dragsort = ToolMan.dragsort();
var junkdrawer = ToolMan.junkdrawer();
var before;
var _this;
var st;
var length_1;
$(function() {
	var left = "";
	$.getJSON(_basePath + "js/config/category.json", function(result) {
		length_1 = result.length;
		// 初始化dom
		$.each(result, function(i, item) {
			var name = item.name;
			left += "<li name='pbox-a' id='" + i + "' class='edit'>" + name + "</li>";
			var right = "<ul id='r_boxes_" + i + "' class='boxes'>";
			$.each(item.subCategoryLinkDtoList, function(j, items) {
				var name = items.name;
				right += "<li name='a' class='second'><span class='edit'>" + name + "</span><ul class='third' name='bbb'>";
				$.each(items.subCategoryLinkDtoList, function(z, itemss) {
					var names = itemss.name;
					right = right + "<li class='box edit' name='b'>" + names + "</li>";
				});
				right += "</ul><button class='add3'>+</button></li>";
			});
			right += "</ul>";
			$("#r_treebox").append(right);
			// 右边树的拖拽初始化
			dragsort.makeListSortable($(right)[0], "a", saveOrder);
			$(right, ".third").each(function(i, item) {
				dragsort.makeListSortable(item, "b", saveOrder);
			});
		});
		$("#pbox").append(left);
		// 绑定事件，会动态生成的用on

		// 左边树的悬浮，点击事件
		$("#pbox").on({
			click : function() {
				pbox_click($(this));
			}
		}, "li[name='pbox-a']");

		// 左边树的拖拽初始化
		dragsort.makeListSortable(document.getElementById("pbox"), "pbox-a", saveOrder);

		// 双击编辑事件
		$("body").on({
			dblclick : function() {
				var clickObj = $(this);
				if (clickObj.find('input').length > 0)
					return false;
				changeToEdit(clickObj, clickObj.text());
			}
		}, '.edit');

		$('#r_treebox').on({
			click : function() {
				add3(this);
			}
		}, '.add3');
	});
	$('.context').on({
		mouseenter : function() {
			var dm = $("<s class='ico_close'><img src='" + _basePath + "images/close.png'></img></s>");
			$(this).append(dm);
			var _dom = $(this);
			dm.click(function() {
				removeDOM(_dom);
			});
		},
		mouseleave : function() {
			$('s.ico_close', this).remove();
		}
	}, 'li');

});

function pbox_click(li) {
	if (before) {
		before.removeClass("cur");
		$('#r_boxes_' + before.attr('id')).css('display', 'none');
	}
	li.addClass("cur");
	before = li;
	var id = li.attr("id");
	$('#r_boxes_' + id).css('display', 'block');
}

function removeDOM(item) {
	if (item) {
		$(item).remove();
		if ($(item).attr('name') == 'pbox-a')
			$("#r_boxes_" + $(item).attr('id')).remove();
	}
}

function get(id, name) {
	var value = junkdrawer.valueJson($("#" + id), name);
	return value;
}

function add() {
	var html = $("<li name='pbox-a' id='" + length_1 + "' class='edit'>新增类目" + length_1 + "</li>");
	$("#pbox").append(html);
	var son = "<ul id='r_boxes_" + length_1 + "' class='boxes'><li name='a' class='third'><span class='edit'>新增二级类目</span><ul class='third' name='bbb'><li class='box edit' name='b'>新增三级类目</li></ul><button class='add3'>+</button></li></ul>";
	$("#r_treebox").append($(son));
	length_1++;
	// 左边树的拖拽初始化
	dragsort.makeListSortable(document.getElementById("pbox"), "pbox-a", saveOrder);
	// 右边树的拖拽初始化
	dragsort.makeListSortable($(son), "a", saveOrder);
	$(son, ".third").each(function(i, item) {
		dragsort.makeListSortable(item, "b", saveOrder);
	});
}

function add2() {
	if (before) {
		var r_box = $('#r_boxes_' + before.attr('id'));
		var li = $("<li name='a' class='second'><span class='edit'>新增二级类目</span><ul class='third' name='bbb'></ul><button class='add3'>+</button></li>");
		r_box.append(li);
		// 右边树的拖拽初始化
		dragsort.makeListSortable(r_box[0], "a", saveOrder);
	}
}

function add3(target) {
	var html = $("<li class='box edit' name='b'>新增三级类目</li>");
	$(target).prev().append(html);
	dragsort.makeListSortable(html, "b", saveOrder);
}

function stopPropagation(e) {
	e = e || window.event;
	if (e.stopPropagation) { // W3C阻止冒泡方法
		e.stopPropagation();
	} else {
		e.cancelBubble = true; // IE阻止冒泡方法
	}
}

function randomColor() {
	return '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).slice(-6);
}

function verticalOnly(item) {
	item.toolManDragGroup.verticalOnly();
}

function speak(id, what) {
	var element = document.getElementById(id);
	element.innerHTML = 'Clicked ' + what;
}

function saveOrder(item) {
	var group = item.toolManDragGroup;
	var list = group.element.parentNode;
	var id = list.getAttribute("id");
	if (id == null) {
		return group.register('dragend', function() {
			ToolMan.cookies().set("list-" + id, junkdrawer.serializeList(list), 365);
		});
	}
}

function changeToEdit(node, content) {
	node.empty();
	var inputObj = $("<input type='text'/>");
	inputObj.attr("class", node.attr("class"));
	inputObj.css({
		"border" : "0",
		"background-color" : node.css("background-color"),
		"font-size" : node.css("font-size"),
		"height" : "20px",
		"width" : "97%",
		"text-align" : "center",
		"margin" : " 2px 0 0 -15px"
	}).val(content).appendTo(node).get(0).select();
	inputObj.dblclick(function() {
		return false;
	}).keyup(function(event) {
		var keyvalue = event.which;
		if (keyvalue == 13) {
			if ($.trim(node.children("input").val()) == '') {
				alert('内容不可为空');
				node.html(content);
				return;
			}
			node.html(node.children("input").val());
		}
		if (keyvalue == 27) {
			node.html(content);
		}
	}).blur(function() {
		if ($.trim(node.children("input").val()) == '') {
			alert('内容不可为空');
			node.html(content);
			return;
		}
		if (node.children("input").val() != content) {
			if (confirm("是否保存修改的内容？", "Yes", "No")) {
				node.html(node.children("input").val());
			} else {
				node.html(content);
			}
		} else {
			node.html(content);
		}
	});

}