/**
 * 历史价格曲线插件 特性： 1、支持多个商家历史价格 2、支持样式定制
 * 
 * garrettyzhu@gmail.com 2013-08-09
 */
;
(function($) {
	function getData(docid, url, callback, container) {
		container.addClass('ajax_loading');
		$.ajax({
			dataType : 'jsonp',
			jsonp : 'jsonCallback',
			url : url,
			data : "docId=" + docid,
			success : function(d) {
				$(container).removeClass('ajax_loading');
				if (typeof callback === 'function') {
					callback(d);
				}
			}
		});
	}
	function parseSeries(pa) {
		// pa is array.
		var i, j, l_1, l_2, t_i, t_j, ps = [], ss = [];
		for (i = 0, l_1 = pa.length; i < l_1; i++) {
			t_i = pa[i];
			ps = t_i.prices;
			var pp = [];
			for (j = 0, l_2 = ps.length; j < l_2; j++) {
				t_j = ps[j];
				pp.push([ t_j.time, parseFloat(t_j.price, 10) ]);
			}
			ss.push({
				"name" : t_i.site,
				"data" : pp
			});
		}// loop end.
		return ss;
	}
	;
	function createJson(p) {
		var price = p.price;
		var site = p.site;
		var rs = {
			averiageType : 0,
			nowPrice : {
				price : price
			},
			averiage : [ {
				prices : createPrices(price),
				site : site
			} ]
		};
		return rs;
	}

	function createPrices(price) {
		var s = [];
		var tody = new Date().getTime() - 89 * 86400000;
		for ( var i = 0; i < 90; i++) {
			s.push({
				"price" : price,
				"time" : tody
			});
			tody = tody + 86400000;
		}
		return s;
	}
	Highcharts.setOptions({
		global : {
			useUTC : false
		}
	});
	$.fn.b5mtrend = function(docid, url, options) {
		var defaults = {
			chart : {
				type : "spline",
				style : {
					overflow : 'visible'
				}
			},
			colors : [ '#E97D01', "#D24A7D", "#64AD10", "#0C7F1A", "#1C98C5", "#4F90B5", "#EECFA1", "#EE82EE", "#DB7093", "#BFEFFF" ],
			title : {
				text : "",
				align : "left",
				style : {
					color : '#3E576F',
					fontSize : '14px'
				}
			},
			tooltip : {
				xDateFormat : "%Y-%m-%d",
				shared : true,
				valuePrefix : '￥',
				backgroundColor : "#ffffff",
				borderWidth : 1,
				borderColor : "#E97D01",
				crosshairs : [ {
					color : "#ff1919",
					width : 1
				}, {
					color : "#ff1919",
					width : 1
				} ],
				style : {
					color : '#333333',
					fontSize : '12px',
					padding : '8px'
				}
			},
			xAxis : {
				type : "datetime",
				dateTimeLabelFormats : {
					day : "%m-%d",
					week : "%m-%d",
					month : "%m-%d"
				},
				alternateGridColor : '#eeeeee',
				minorGridLineColor : "#dfdfdf",
				minorTickInterval : "auto"
			},
			yAxis : {
				title : {
					text : ""
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : "#808080"
				} ],
				gridLineColor : "#c0d0e0",
				minorGridLineColor : "#dfdfdf",
				minorTickInterval : "auto"
			},
			plotOptions : {
				spline : {
					lineWidth : 2,
					states : {
						hover : {
							lineWidth : 2
						}
					},
					marker : {
						enabled : false
					}
				}
			},
			legend : {
				layout : "horizontal",
				align : "center",
				verticalAlign : "bottom",
				borderWidth : 0,
				margin : 10
			},
			credits : {
				enabled : false,
				href : "http://www.b5m.com",
				position : {
					align : 'right',
					x : -10,
					verticalAlign : 'bottom',
					y : -15
				},
				text : "www.b5m.com"
			},
			series : []
		};

		/*
		 * 
		 */
		var opts = $.extend(true, {}, defaults);
		if (options.height) {
			opts.chart.height = options.height;
		}
		if (options.width) {
			opts.chart.width = options.width;
		}
		if (options.titleText) {
			opts.title.text = options.titleText;
		}
		if (options.titleAlign) {
			opts.title.align = options.titleAlign;
		}
		if (options.colors) {
			opts.chart.colors = options.colors;
		}
		opts.tooltip.crosshairs = opts.tooltip.crosshairs || [ {}, {} ];
		if (!options.crosshairsEnableds) {
			options.crosshairsEnableds = [ true, true ];
		}

		if (options.crosshairsEnableds && options.crosshairsEnableds.length >= 2) {
			if (options.crosshairsEnableds[0] === false) {
				opts.tooltip.crosshairs[0] = null;
			} else {
				opts.tooltip.crosshairs[0].color = options.crosshairsColor[0];
				opts.tooltip.crosshairs[0].width = 1;
			}
			if (options.crosshairsEnableds[1] === false) {
				opts.tooltip.crosshairs[1] = null;
			} else {
				opts.tooltip.crosshairs[1].color = options.crosshairsColor[1];
				opts.tooltip.crosshairs[1].width = 1;
			}
		}
		if (options.legendEnabled != null) {
			opts.legend = {
				enabled : options.legendEnabled
			};
		}
		var _this = this;
		getData(docid, url, function(d) {
			var pa;
			if (!d.val) {
				if (options.price && options.site) {
					if (!pa || pa[0].prices.length == 0) {
						d = {};
						d.val = createJson(options);
					}
				}
			}
			if (options.handler && (typeof options.handler === 'function')) {
				pa = options.handler(d);
			} else if (d) {
				pa = d.val;
			}
			if (pa) {
				var series = parseSeries(pa);
				opts.series = series.slice(0, 10);
				$(_this).highcharts(opts);
				var chart = $(_this).highcharts();
				if (chart && chart.series) {
					var ser = chart.series;
					for ( var i = 1; i < ser.length; i++) {
						ser[i].hide();
					}
					ser[0].show();
				}
				$('.highcharts-legend-item text,path').on('click', function() {
					if ($('g.highcharts-legend-item').find('path[stroke!="#CCC"]').length == 1 && $(this).parent().find('path').attr('stroke') != '#CCC') {
						return false;
					}
				});
				// var svg = _this.find('svg');
				// svg.attr('width', parseInt(svg.attr('width')) + 10);
			}
		}, _this);
	};
}(jQuery));
