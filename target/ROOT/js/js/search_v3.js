;(function($,window,document){

    $(function(){
        //有奖调查
        searchFed.indexFun();
        //调用弹出框
        $('.popup-trigger').b5m_popup();
        $('.feed-awards').b5m_popup({fixed:true,cName:'arrow-right'});

        $('#J_filter').b5m_toggleItem();

        MiniFilter.init();
        
        setTimeout(function(){
                $('.side-l').b5m_fixTop({
                    placeWrap:false,
                    className:'fixed',
                    offsetBottom:200
                });
        },1000);

        //左侧展开收起
        $('#J_category_nav').b5m_cateNav();

        //历史记录滚动条
        setTimeout(function(){
            var hisList = $('.scroll-history .grid-view').find('li'),
                len = hisList.length;
            if(len > 2){
                $('.scroll-history').css('height',400);
                scrollBar();
            }
        },1000);

        // $('#J_goods_list').find('.grid-ls').click(function() {

            
        //     setTimeout(function(){
            
        //         var hisList = $('.scroll-history .grid-view').find('li'),
        //             len = hisList.length ;
        //         if(len > 2){
        //                 $('.scroll-history').css('height',400);
        //                 scrollBar();
        //         }
        //     },1000)

        // });
    });

    function scrollBar(){
         seajs.use(['$','modules/widgets/jscrollpane/2.0.19/jscrollpane'],function($,Jscroll) {
            jscroll = new Jscroll({
                container:$('.scroll-history'),
            });
         });   
    }
    var searchFed = window.searchFed || {};

    searchFed = {
        indexInit:function(){
            $('.gotop').before('<div class="feed-awards" data-target="feed-popup"></div>');

            this.setPos();
        },
        //调整返回顶部按钮位置
        setPos:function(){
            var posLeft = parseInt($('.wp').width()/2 + 55);
            $('.gotop,.gotofushi,.feed-awards').css({
                'marginLeft':posLeft
            });
        },
        indexFun:function(){
            this.indexInit();
        }
    };

    $(window).resize(function(){
        var flag = null;
        clearTimeout(flag);

        flag = setTimeout(function(){
            searchFed.setPos();
        },400);
    });

	$.b5m = $.b5m || {};
	/**
	 * 展开收起更多属性值
	 */
	$.b5m.toggleItem = function(el,options){
		var base = this;
		base.$el = $(el);
		base.el = el;

		$(window).resize(function(){
			clearTimeout(flag);
			var flag = setTimeout(function(){
				base.winResize();
			},600);
		});

		base.init = function(){
			base.options = $.extend({},$.b5m.toggleItem.defaultOptions,options);
			$box = base.$el.find('.' + base.options.box);
			classFilter = base.options.classFilter;
			classMultiple = base.options.classMultiple;
			classOpen = base.options.classOpen;
			num = base.options.num;

			base.bindEvent();
			base.bindMore();
			base.winResize();
		}

		//绑定事件
		base.bindEvent = function(){

			//绑定展开收起事件
			$box.find('.show-more').on('click.showmore',function(){
				var $this = $(this);
					$parent = $this.parent();

				$this.toggleClass(classOpen)
					 .parent().toggleClass(classFilter);

				if($this.hasClass('open')){
                    $this.html('<span>收起</span>');
			    }else{

			    	//收起时，去掉多选
					$this
						.parent().removeClass(classMultiple);
			        $this.html('<span>展开</span>');
			    }
			});

			//绑定多选事件
			$box.find('.btn-multiple').on('click',function(e){
				$(this)
					.parents('.filter-item').addClass(classMultiple + ' ' + classFilter);
					$(this).parent('.filter-act').siblings('.show-more').addClass("open").text("收起");
				e.preventDefault();
			});

			//取消多选事件
			$box.find('.btn-cancle').on('click',function(e){
				var $this = $(this);
				$this
					.parents('.' + base.options.box).removeClass(classMultiple)
					.find('.show-more').trigger('click.showmore');
				e.preventDefault();
			});

			//确定多选事件
			$box.find('.btn-sure').on('click',function(e){
				var $this = $(this),
					$parent = $this.parents('.filter-item'),
					$filter = $parent.find('.filter-lists').find('.cur'),
					len = $filter.length,
					title = $parent.find('dt').attr('title'),
					data = '';

				$filter.each(function(index, el) {
					if(index == len - 1){
						data += title + ':' + $(el).text();
					}else{
						data += title + ':' + $(el).text() + ',';
					}
				});

				//提交数据
				searchMulti(data);
				e.preventDefault();

			});

			//每个条件添加事件
			$box.find('.filter-lists a').not('.not-filter').on('click',function(e){
				var $this = $(this);
				$this.toggleClass('cur');

				//多选情况下，阻止a的默认事件
				if($this.parents('.filter-item').hasClass('filter-multiply')){
					e.preventDefault();
				}
			});
		}

		/**
		* 展开收起其余属性值
		*/
		base.bindMore = function(){
			var $this = $('#J_more'),
                $toggbleBar = $this.parent('.filter-more'),
				items_len = $box.length;

            //筛选条件小于5个则删除展开更多按钮
            var lenFlag = items_len < 5;
            
            if(lenFlag){
                $toggbleBar.remove();
            }

            //标识最后一个是否为 ‘价格’
            var flag = $box.last().find('dt').text().slice(0,2) == '价格';

			$this.on('click',function(e){

                if(flag){
                    $box.filter(function(index){
        					return (index > (num - 2)) && index != (items_len - 1);
       				}).toggle();
                }else{
                    $box.filter(function(index){
                        return (index > (num - 2));
                    }).toggle();
                }

				$(this).toggleClass('open');

				//展开和收起在初次加载和window resize时显隐切换
				base.winResize();
				e.preventDefault();
			});
		},

		//展开和收起在初次加载和window resize时显隐切换
		base.winResize = function(){
			$box.each(function(){
				var $this = $(this),
				    $filtersBox = $this.find('.filter-lists'),
					box_width = $filtersBox.width(),
					$filters = $filtersBox.find('a'),
					filters_width = $filters.length * $filters.outerWidth(true);

				if(filters_width > box_width){
					$this.find('.show-more').show();
				}else{
					$this.find('.show-more').hide();
				}
			});
		}
	};

	$.b5m.toggleItem.defaultOptions = {
		box:'filter-item',
		classFilter:'filter-open',
		classMultiple:'filter-multiply',
		classOpen:'open',
		num:5
	};

    //插件入口
	$.fn.b5m_toggleItem = function(options){
		return this.each(function(){
			new $.b5m.toggleItem(this,options).init();
		});
	}

    // /**
    //  * 历史记录更多
    //  */
    // $('#J_show_sub').mouseover(function(event) {
    //     /* Act on the event */
    //     var items = $(this).find('.goods-history-sub li'),
    //         title_height = $(this).find('.more-c-title').outerHeight(),
    //         items_len = items.length,
    //         items_width = items.outerWidth()*Math.min(5,items_len),
    //         items_height = items.outerHeight()*Math.ceil(items_len/5),
    //         offsetTop = items_height - title_height + 2;

    //     $(this).find('.goods-history-sub').css({
    //         width:items_width,
    //         height:items_height,
    //         marginTop:-offsetTop
    //     });

    // });
	/**
	 * 小屏幕下面顶部分类展开、收起
	 */
	var MiniFilter = {

		init:function(){
			var $box = $('#J_filter_cate'),
				$trigger = $box.find('.show-more');
			this.bindEvent.apply($trigger,arguments);
			this.winResize($trigger);
		},

		bindEvent:function(){

			//这里的this已经是$trigger jquery对象
			var $this = this;
			$this.on('click.showmore',function(){
				var $this = $(this);
				$this.toggleClass(classOpen)
					 .parent().find('ul,dl').toggleClass('filter-open');
				if($this.hasClass('open')){
                    $this.html('<span>收起</span>');
			    }else{
			        $this.html('<span>展开</span>');
			    }
			});
		},

		//展开和收起在初次加载和window resize时显隐切换
		winResize:function(obj){
			obj.each(function(){
				var $this = $(this),
				    $filtersBox = $this.parent().find('ul,dl'),
					box_width = $filtersBox.width(),
					$filters = $filtersBox.find('dd,li'),
					filters_width = $filters.length * $filters.outerWidth(true);

				if(filters_width > box_width){
					$filtersBox
						.parent()
						.find('.show-more').show();
				}else{
					$filtersBox
						.parent()
						.find('.show-more').hide();
				}
			});
		}
	}


	$(window).resize(function(){
		var $box = $('#J_filter_cate'),
			$trigger = $box.find('.show-more');
		MiniFilter.winResize($trigger);
	})

	/**
	 * 置顶/置底效果
	 */
    $.b5m.fixTop = function(el,options){

        var base = this;

        base.el = el;
        base.$el = $(el);

        base.init = function(){
            options = $.extend({}, $.b5m.fixTop.Defaults,options || {});

            base.placeWrap = options.placeWrap;
            base.className = options.className;
            base.offsetBottom = options.offsetBottom;

            base.bindEvent();
        }

        /**
         * 获得元素的信息：宽、高和距离屏幕左、上的距离
         * @returns {{size: {width: *, height: *}, position: {left: *, top: *}}}
         */
        base.getEleInfo = function(){
            return {
                size:{
                    width:base.$el.width(),
                    height:base.$el.outerHeight(true)
                },
                position:{
                    left:base.$el.offset().left,
                    top:base.$el.offset().top
                }
            };
        }

        /**
         * 判断元素是否在可视区域内
         * @returns {boolean}
         */
        base.isInWin = function(){
            var isShowTop = (base.getEleInfo().position.top + base.getEleInfo().size.height) > $(window).scrollTop(),
                isShowBottom = base.getEleInfo().position.top < ($(window).scrollTop() + $(window).height());
            return isShowTop && isShowBottom;
        }

        base.bindEvent = function(){
            var $win = $(window),
                winH = $(window).height(),
                defaultPos = base.$el.css('position'),
                defaultTop = base.getEleInfo().position.top;
                zIndex = base.$el.css('zIndex');

            $win.scroll(function(){
                var scrollTop = $(window).scrollTop(),
                    elemH = base.$el.outerHeight();

                //当元素的高度大于浏览器高度
                if(elemH > winH){

                    if((defaultTop + elemH) <= (scrollTop + winH)){

                        base.$el.css({
                            position:'fixed',
                            bottom:0,
                            zIndex:70,
                            width:base.getEleInfo().size.width
                        }).addClass(base.className);

                        //当footer进入页面时，左侧位置不在吸底
                        if($('.footer').offset().top <= scrollTop + winH){
                            base.$el.css({
                                position:'fixed',
                                bottom:scrollTop + winH - $('.footer').offset().top,
                                top:'auto',
                                zIndex:70,
                                width:base.getEleInfo().size.width
                            })
                        }

                    }else{

                        base.$el.css({
                            position:defaultPos,
                            bottom:'auto',
                            top:'auto',
                            zIndex:zIndex
                        }).removeClass(base.className);

                    }

                }else{
                    if(defaultTop <= scrollTop){
                        base.$el.css({
                            position:'fixed',
                            top:0,
                            zIndex:70,
                            width:base.getEleInfo().size.width
                        }).addClass(base.className);

                        //判断父类是否有占位标签
                        if(!base.$el.next('div').hasClass('placeWrap') && base.placeWrap){
                            base.$el.after('<div class="placeWrap" style="height:' + base.getEleInfo().size.height + 'px"></div>');
                        }
                    }else{
                        base.$el.css({
                            position:defaultPos,
                            top:'auto',
                            bottom:'auto',
                            zIndex:zIndex,
                            width:base.getEleInfo().size.width
                        }).removeClass(base.className);

                        if(base.$el.next('div').hasClass('placeWrap') && base.placeWrap){
                            base.$el.next('.placeWrap').remove();
                        }

                    }

                }
            })
        }
    }


    $.b5m.fixTop.Defaults = {
        placeWrap:true,
        className:"",
        offsetBottom:0
    }

	$.fn.b5m_fixTop = function(options){
        return this.each(function(){
            new $.b5m.fixTop(this,options).init();
        })
	}



    /**
     * @param el
     * @param options
     */
    $.b5m.cateNav = function(el,options){
        var base = this;
        base.el = el;
        base.$el = $(el);

        base.init = function(){
            base.options = $.extend({}, $.b5m.cateNav.Defaults,options || {}),
            base.className =  base.options.className;
            base.nodeElement = base.options.nodeElement;
            base.showMore = base.options.showMore;
            base.moreOpen = base.options.moreOpen;
            base.num = base.options.num;

            base.bindEvent();
        }

        base.bindEvent = function(){

            var $items = base.$el.find(base.nodeElement),
            	$itemLinks = $items.find('a'),
                itemLen = $items.length;


            //展开点击元素，收起其余兄弟节点。
            $items.on('click',function(){
                var _this = $(this);

                //当此元素展开时，再次点击就收起。
                _this.parent().toggleClass(base.className)
                    .siblings().removeClass(base.className);
                $(window).trigger('scroll');
            });

            //阻止冒泡
            $itemLinks.on('click',function(e){
            	e.stopPropagation();
            });

            if(itemLen <= base.num){
                base.$el.find('.' + base.showMore).hide();
                return false;
            }

            var $targetItem = $items.parent().filter(function(index){
                return index > (base.num - 1);
            });

            base.$el.find('.' + base.showMore).on('click',function(){
                var _this = $(this);

                if(_this.hasClass(base.moreOpen)){
                    _this.removeClass(base.moreOpen);
                    $targetItem.hide().removeClass(base.className);
                }else{
                    _this.addClass(base.moreOpen);
                    $targetItem.show().removeClass(base.className);
                }

                //显隐历史记录、用户还点击了模块
                $('.goods-history').toggle();
            });
        }
    }

    $.b5m.cateNav.Defaults = {
        className:'cur',
        nodeElement:'dl dt',
        showMore:'more-category',
        moreOpen:'more-category-open',
        num:5
    }


    $.fn.b5m_cateNav = function(options){
        return this.each(function(){
            new $.b5m.cateNav(this,options).init();
        });
    }

    /**
     * 弹窗
     * @param el
     * @param options
     */
	$.b5m.popup = function(el,options){
        var base = this;
        base.el = el,
        base.$el = $(el);

        base.init = function(){
            var opts = $.extend({}, $.b5m.popup.defaultOptions,options || {});
            var $target = $('.' + base.$el.data('target')).length ? $('.' + base.$el.data('target')):'';

            base.$target = $target || $('.' + opts.target);
            base.$close = $('.' + opts.close);
            base.$arrow = $('.' + opts.arrow);
            base.fixed = opts.fixed;
            base.cName = opts.cName;

            this.bindEvent();
        };

        base.bindEvent = function(){

            base.$el.on('click',function(){

//                if(base.$target.is(':hidden')){
                    base.show();

                    base.$target.on('click',function(e){
                        e.stopPropagation();
                    });

                    $(document).on('click',function(){
                        base.hide();
                    });

                    base.$close.on('click',function(){
                        base.hide();
                    })

                    $(window).resize(function(){
                        var flag = null;
                        clearTimeout(flag);

                        setTimeout(function(){
                            base.setPos();
                        },1000);
                    });
//                }else{
//                    base.hide();
//                }
                return false;
            });
        };

        base.getPos = function(){
            var coord = null,
                x = parseInt(base.$el.offset().left),
                y = parseInt(base.$el.offset().top);

            return coord = {
                x:x,
                y:y
            };
        };

        base.setPos = function(){
            var coord = base.getPos();

            base.$target.css({
                'position':'absolute',
                'left':coord.x - parseInt(base.$target.width() - base.$el.width()),
                'top':coord.y + 40,
                'bottom':'auto'
            })

            if(base.fixed){
                base.$target.css({
                    'position':'fixed',
                    'left':coord.x - parseInt(base.$target.width()) - 30,
                    'top':'auto',
                    'bottom':parseInt(base.$el.css('bottom'))
                })
            }
        };

        base.show = function(){
            base.$target.css({
                'display':'block'
            });

            base.$arrow.removeClass().addClass('arrow ' + base.cName);

            base.setPos();
        };

        base.hide = function(){
            base.$target.css({'display':'none'});
        };

	}

    $.b5m.popup.defaultOptions = {
        target:'feed-popup',
        close:'close',
        fixed:false,
        arrow:'arrow',
        cName:'arrow-top'
    };

    $.fn.b5m_popup = function(options){
        return this.each(function(){
            new $.b5m.popup(this,options).init();
        });
    };

    /*帮钻加分提示s*/
    $.fn.b5m_bangdouTips = function(){
        var tips;
        function init(num){
            return $('<div class="bangdou-tips" style="color:red"><div class="bangdou-tips__in">' + "+" + num + '</div></div>').appendTo($('.feed-popup').find('.btn-box'));
        }
        return {
            getTips:function(num){
                return tips || (tips = init(num));
            }
        }
    }();

    $.fn.b5m_addTips=function (num){
        $.fn.b5m_bangdouTips.getTips(num).stop(true,true).animate({opacity:1},400).delay(500).animate({opacity:0},800,function(){
            $('.popup-in').html('<div class="tips"><h5>您的意见已提交成功!</h5><p>谢谢参与，感谢您长久对帮5买的支持~</p></div>');
        });
    };
    /*帮钻加分提示e*/

    var validate = {
        check:function(element,url){
            var $target = $('.' + element),
                $items = $target.find('.required'),
                $submit_btn = $('#J_submit');

            $items.each(function(){
                var $this = $(this),
                    value = $.trim($this.val());

                if(!value){
                    $submit_btn.addClass('btn-disable').removeClass('btn-enable');
                    return false;
                }

                $submit_btn.addClass('btn-enable').removeClass('btn-disable');
            });

            $submit_btn.off().on('click',function(e){
                e.preventDefault();
                validate.submit($target,url);
            })
        },
        submit:function(obj,url){
            var data = obj.serialize();
                url = url + "?" + data;

            $.ajax({
                url: url,
                data: {},
                success:function(data){
                    var codes=data['code'];
                    if (Cookies.get('login') != "true") {
                        $('.popup-in').html('<div class="tips"><h5>您的意见已提交成功!</h5><p>谢谢参与，感谢您长久对帮5买的支持~</p></div>');
                    }else{
                        if(codes == 1){
                            $.fn.b5m_addTips('6');
                        };
                    }
                }
            })
            .always(function() {
                   /* if (Cookies.get('login') != "true") {
                        $('.popup-in').html('<div class="tips"><h5>您的意见已提交成功!</h5><p>谢谢参与，感谢您长久对帮5买的支持~</p></div>');
                    }else{
                        $.fn.b5m_addTips('6');
                    }*/
               // $('.popup-in').html('<div class="tips"><h5>您的意见已提交成功!</h5><p>谢谢参与，感谢您长久对帮5买的支持~</p></div>');
                var flag = setTimeout(function(){
                    $('.feed-popup').hide();
                    clearTimeout(flag);
                },4000);
            })

        }
    }

    $('.feed-form').find('.required').keyup(function(event) {

        if(typeof _basePath !== 'undefined'){
            validate.check('feed-form',_basePath + "searchRecommend.htm");
//            validate.check('feed-form',"searchRecommend.htm");
        }
    });
})(jQuery,window,document);


;(function($,window,document,undefined){

    $.fn.prodDetails = function(options){
        return this.each(function(){
            new ProdDetail(this,options).init();
        });
    }
    /**
     * 商品的详细信息构造函数
     * @param {[type]} elem    [description]
     * @param {[type]} options [description]
     */
    function ProdDetail(elem,options){
        this.elem = elem;
        this.$elem = $(elem);
        this.$parent = this.$elem.parent();
        this.docId = this.$elem.attr('docId');
        this.iscompare =this.$elem.attr('iscompare');
        this.id = this.$elem.attr('id');
        this.posLeft = this.$elem.data('posLeft');
        this.offetLeft = this.$elem.offset().left;
        this.offsetWidth = this.$elem.outerWidth();

        this.options = options;
    }

    ProdDetail.prototype.defaults = {
        /*baseUrl:'http://s.b5m.com',*/
    	baseUrl:_basePath,
        url:_basePath + "goodsDetail/detailInfo.htm",
        adSize:5,
        closeTrigger:'pop-close'
    }

    ProdDetail.prototype.init = function(){
        this.config = $.extend({},this.defaults,this.options);
        this.url = this.config.url;
        this.adSize = this.config.adSize;
        this.close = this.config.closeTrigger;
        this.docIds = this.config.docIds;
        this.baseUrl = this.config.baseUrl;
        //绑定事件
        this.bindEvent();
    }

    // ProdDetail.prototype.getDocId = function(str){
    //     var jsonStr = str;

    //     if(jsonStr == null){
    //         return null;
    //     }

    //     //这里需要处理一下
    //     jsonStr = '{"' + jsonStr.replace(/,/g,'":"').replace(/;/g,'","') + '"}';

    //     //非IE
    //     if(window.JSON && window.JSON.parse){
    //         return window.JSON.parse(jsonStr)
    //     }else{
    //         return (new Function('return ' + jsonStr))();
    //     }
    // }


    ProdDetail.prototype.bindEvent = function(){
        var base = this,
            timeFlag = '';

        this.$elem.on('mouseenter',function(){
            clearTimeout(timeFlag);

            var $this = $(this),
                flag = $this.data('flag'),
                isOpen = $this.data('isOpen'),
                //点击产品的下标
                prodIndex = $('.grid-ls').index($(this)) + 1,
                //插入的位置
                posIndex = Math.ceil(prodIndex / 4);

            timeFlag = setTimeout(function(){
                $('.pop-ls').hide();
                $this.addClass('grid-ls-on').siblings('.grid-ls').data('flag','').data('isOpen','').removeClass('grid-ls-on');
                //flag标识是否为第一次请求，空为第一次，
                // if(!flag){
                    // $this.data('flag','true');
                    // $this.data('isOpen','true');
                    base.getDetail(posIndex);
                // }else{
                //     if(isOpen == 'true'){
                //         base.hidePop(base.docId,$this);
                //     }else{
                //         //显示对应的弹出框（根据docId来判断对应关系）
                //         base.showPop(base.docId,$this);
                //     }
                // }
                // window.location.hash = $this.attr('id');
            },800);
        });


       this.$elem.on('mouseleave',function(){
           var $this = $(this),
                outFlag = '';

            outFlag = setTimeout(function(){

                var popProd = $('#' + base.docId + '_prod');

                if(!popProd.data('show')){
                    clearTimeout(timeFlag);
                    clearTimeout(outFlag);
                    base.hidePop(base.docId,$this);
                }
            },200)
       });

//        this.$elem.on('click',function() {
//
//            var $this = $(this),
//                flag = $this.data('flag'),
//                isOpen = $this.data('isOpen'),
//            //点击产品的下标
//                prodIndex = $('.grid-ls').index($(this)) + 1,
//            //插入的位置
//                posIndex = Math.ceil(prodIndex / 4);
//
//            $('.pop-ls').hide();
//            $this.addClass('grid-ls-on').siblings('.grid-ls').data('flag', '').data('isOpen', '').removeClass('grid-ls-on');
//
//            //flag标识是否为第一次请求，空为第一次，
//            if (!flag) {
//                $this.data('flag', 'true');
//                $this.data('isOpen', 'true');
//                base.getDetail(posIndex);
//            } else {
//                if (isOpen == 'true') {
//                    base.hidePop(base.docId, $this);
//                } else {
//                    //显示对应的弹出框（根据docId来判断对应关系）
//                    base.showPop(base.docId, $this);
//                }
//            }
//            window.location.hash = $this.attr('id');
//        });
    }
    /**
     * 获取产品的详细信息
     * @param  {[Number]} index [弹出框需要插入第几行]
     */
    ProdDetail.prototype.getDetail = function(index){
        var base = this,
            url = base.url,
            iscompare = base.iscompare,
            docId = base.docId,
            $parent = base.$parent,
            adSize = base.adSize,
            index = index,
            compareBaseUrl = base.baseUrl + 'compare/',
            BaseUrl = base.baseUrl + 'item/',
            txtLen = 56;
        $.ajax({
            url:url,
            dataType:'jsonp',
            data:{"docId":docId,"isCompare":iscompare},
            jsonp:'jsonCallback',
            success:function(data){

                var prodTitle = data.val.Title,
                    //绘制图表需要的ID
                    price_ChartID = data.val.DetailDocId || data.val.DocId;

                /**
                 * 优先获取产品详情里面的‘isLowPrice’
                 * 若isLowPrice不为1，再获取trend里面的值
                 * 标识-1,0,-1表示降、平、升
                 */
                if(data.val.isLowPrice == '1'){
                    price_trend = 'price-lowest';
                }else if(data.val.trend == '-1'){
                    price_trend = 'price-down';
                }else if(data.val.trend == '0'){
                    price_trend = 'price-flat';
                }else if(data.val.trend == '1'){
                    price_trend = 'price-up';
                }
                //比价商品
                if(iscompare == 'true'){
                    var html = '<li class="pop-ls cf" id="' + base.docId + '_prod">';
                        html += '<div class="prod-pop cf">';
                        html += '<div class="prod-details cf"> <div class="prod-details-pic l"> <div class="main-slider"> <div class="main-slider-pic">';
                        html += '<img src="' + data.val.Picture + '"></div>';
                        //小图标
                        if(data.val.OriginalPicture && data.val.OriginalPicture.split(',').length > 0){
                            html += '<div class="slider-bar cf mini-slider"><ul class="cf">';
                            for(var i = 0; i < data.val.OriginalPicture.split(',').length; i++){
                                html += '<li><span><img src="' + data.val.OriginalPicture.split(',')[i] + '"></span></li>';
                            }
                            html += '</ul>';
                            if(data.val.OriginalPicture.split(',').length > 5){
                                html += '<a href="" class="slider-trigger slider-left arrow-left-disable"></a><a href="" class="slider-trigger slider-right"></a>';
                            }
                            html += '</div>';
                        }
                        html += '</div></div>';
                        html += '<div class="prod-details-txt">';
                        if(prodTitle.length > txtLen){
                            prodTitle = prodTitle.slice(0, txtLen);
                        }
                        html += '<h3 class="prod-details-title"><a target="_blank" data-attr="100801" href="' + compareBaseUrl + docId + '.html">' + prodTitle + "</a></h3>";
                        if(data.val.Score){
                            html += '<p><span class="star-wrap"><span class="star-out"><span class="star-in" style="width:'+ data.val.Score/5*100 +'%"></span></span><span class="prod-score">' + data.val.Score + '</span></span>';
                        }else{
                            html += '<p><span class="star-wrap cl-5555">该商品暂无评分 </span>';
                        }
                        html += '<span class="cl-5555"> 销量（月）<span class="cl-eb7e">' + data.val.SalesAmount + '</span> 件</span>';
                        html += '</p><p class="fw mt20 cl-5555">商品推荐：</p>';
                        html += '<ul class="h-list rec-prod-list cf">';
                        // html += '<li><a href="#"><img src="'+ url +'" alt=""></a><span>'+ price '</span></li>';
                        html += '</ul><ul class="prod-detail-price h-list mt20 cf">';
                        html += '<li class="item-odd"><p class="goods-mall">商城：<span class="cl-eb7e">'+ data.val.SubDocs[0].Source +'</span></p><a href="javascript:void(0);" class="btn-bevel btn-bevel-1">&yen;' + data.val.SubDocs[0].Price + '</a>';
                        if(data.val.isDaigou){
                        	$(base.elem).find("a").attr("href", "javascript:void(0);");
                        	html += '<a target="_blank" data-attr="100803" href="javascript:void(0);" class="btn-bevel btn-bevel-2 bodyopen" docid="'+data.val.daigouSource.DOCID+'" url="'+data.val.daigouSource.Url+'">正品低价抢</a></li>';
                        }else{
                        	html += '<a target="_blank" data-attr="100803" href="' + data.val.SubDocs[0].Url + '" class="btn-bevel btn-bevel-2">直接去购买</a></li>';
                        }
                        html += '<li class="item-even"><div class="pro-malls">';
                        if(data.val.SubDocs && data.val.SubDocs.length > 0){
                            var subDocLen = Math.min(4,data.val.SubDocs.length);
                            for(var i = 1; i < subDocLen; i++){

                                var mallName = data.val.SubDocs[i].Source;

                                if(mallName == '淘宝网'){
                                    mallName = '淘宝网<span class="taobaoPerson">个人卖家</span>';
                                }
                                html += '<p><span class="pro-mall-item"><span class="cl-5555 fs14">&yen;' + data.val.SubDocs[i].Price + '</span><a target="_blank" data-attr="100804" href="' + data.val.SubDocs[i].Url + '" class="ml20 cl-eb7e">' + mallName + '</a></span></p>';
                            }
                        }
                        html += '<p class="more-mall cf"><a target="_blank" href="' + compareBaseUrl + docId + '.html" class="l cl-eb7e fw">查看更多商家</a></p></div></li></ul>';
                        html += '<div class="prod-price-trend"><div class="icon-price-trend J_price_trend '+ price_trend +'"></div>';
                        html += '<div class="prod-price-chart" id="chart_'+ price_ChartID +'"></div></div>';
                        html += '</div></div><span class="pop-icon pop-arrow"></span><span class="pop-icon pop-close" data-closeid="'+ docId +'"></span>';
                        html += '</div></li>';                   
                }else{
                //非比价商品
                    var html = '<li class="pop-ls cf" id="' + base.docId + '_prod">';
                        html += '<div class="prod-pop cf">';
                        html += '<div class="prod-details cf"> <div class="prod-details-pic l"> <div class="main-slider"> <div class="main-slider-pic">';
                        html += '<img src="' + data.val.Picture + '"></div>';
                        //小图标
                        if(data.val.OriginalPicture && data.val.OriginalPicture.split(',').length > 0){
                            html += '<div class="slider-bar cf mini-slider"><ul class="cf">';
                            for(var i = 0; i < data.val.OriginalPicture.split(',').length; i++){
                                html += '<li><span><img src="' + data.val.OriginalPicture.split(',')[i] + '"></span></li>';
                            }
                            html += '</ul>';
                            if(data.val.OriginalPicture.split(',').length > 5){
                                html += '<a href="" class="slider-trigger slider-left arrow-left-disable"></a><a href="" class="slider-trigger slider-right"></a>';
                            }
                            html += '</div>'
                        }
                        html += '</div></div>';
                        html += '<div class="prod-details-txt">';
                        if(prodTitle.length > txtLen){
                            prodTitle = prodTitle.slice(0, txtLen);
                        }
                        html += '<h3 class="prod-details-title"><a target="_blank" href="' + BaseUrl + docId +'.html">' + prodTitle + "</a></h3>";
                        if(data.val.Score){
                            html += '<p><span class="star-wrap"><span class="star-out"><span class="star-in" style="width:'+ data.val.Score/5*100 +'%"></span></span><span class="prod-score">' + data.val.Score + '</span></span>';
                        }else{
                            html += '<p><span class="star-wrap cl-5555">该商品暂无评分 </span>';
                        }
                        // html += '<p><span class="star-wrap"><span class="star-out"><span class="star-in"></span></span></span>';
                        html += '<span class="cl-5555">销量（月）<span class="cl-eb7e">' + data.val.SalesAmount + '</span> 件</span>';

                        var numComment = data.val.CommentSize ? data.val.CommentSize : '0';
                        
                        html += '<span class="ml20 cl-5555">评论（<a  target="_blank" data-attr="100802" href="' + BaseUrl + docId +'.html" class="cl-eb7e">' + numComment + '</a>）</span></p><p class="fw mt20">商品推荐：</p>';
                        html += '<ul class="h-list rec-prod-list cf">';
                        // html += '<li><a href="#"><img src="'+ url +'" alt=""></a><span>'+ price '</span></li>';
                        html += '</ul><ul class="prod-detail-price h-list mt20 cf">';
                        html += '<li class="item-odd"><p class="goods-mall">商城：<span class="cl-eb7e">'+ data.val.Source +'</span></p><a href="javascript:void(0);" class="btn-bevel btn-bevel-1">&yen;' + data.val.Price + '</a>';
                        html += '<a target="_blank" data-attr="100803" href="' + data.val.Url + '" class="btn-bevel btn-bevel-2">直接去购买</a></li>';

                        html += '<li class="item-even"><div class="pro-malls">';

                        if(data.val.carriage && data.val.carriage.split('&').length > 0){
                            var carriage_item = data.val.carriage.split('&'),
                                carriage_len = carriage_item.length;

                            for(var i = 0,len = Math.min(2,carriage_len); i < len; i++){
                                html += '<p><span class="prod-carriage"><span class="cl-5555 fs14">' + carriage_item[i] + '</span></span></p>';
                            }
                        }
                        html += '<p class="more-mall cf"><a  target="_blank" data-attr="100808" href="' + BaseUrl + docId + '.html" class="prod-detail cl-eb7e r">查看商品详细信息</a></p>';
                        html += '</div></li></ul>';
                        html += '<div class="prod-price-trend"><div class="icon-price-trend J_price_trend '+ price_trend +'"></div>';
                        html += '<div class="prod-price-chart" id="chart_'+ price_ChartID +'"></div></div>';
                        html += '</div></div><span class="pop-icon pop-arrow"></span><span class="pop-icon pop-close" data-closeid="'+ docId +'"></span>';
                        html += '</div></li>';   
                }

                var $targetElem = $parent.find('.grid-ls').eq(4*index - 1),
                    $html = $(html);
                
                $html.find(".bodyopen").click(function(){
                	Daigou.show(data, $(this));
                });
                //指定位置插入弹出框
                if($targetElem.length <= 0){
                    $html.appendTo($parent).css({display:'block'}).stop(true,true).animate({
                        height:'397'
                    }, 300);
                }else{
                    $html.insertAfter($targetElem).css({display:'block'}).stop(true,true).animate({
                        height:'397'
                    }, 300);
                }

                base.showProductPriceTrendByDocid(price_ChartID, data.val.Source)
                base.setPos(docId);

                $('#' + docId + '_prod').find('.pop-close').on('click',function(){
                    var id = $(this).data('closeid');
                    base.hidePop(id,$('#'+id));
                })

                $('.mini-slider').b5m_miniSlider({
                    trigger:'slider-trigger'
                }); 

                base.getAds(prodTitle,'','',adSize,false,base.docId);

                //显隐价格趋势图
                $html.find('.J_price_trend').hover(function() {
                    base.showChart(price_ChartID);
                }, function() {
                    base.hideChart(price_ChartID);
                });

                var prod = $html.attr('id').replace('_prod','');
                //
                $html.hover(function(){
                    $(this).data('show',true);
                },function(){
                    base.hidePop(base.docId,$('#' + prod));
                });
            }
        })
        
    }

    ProdDetail.prototype.getAds = function(keywords, cookId, recordUrl, adSize, needShowAd,Id){
        var base = this,
            docId = base.docId;

        $.ajax({
            url: 'http://click.simba.taobao.b5m.com/s/data/' + adSize + '_0_V.html',
            type: 'GET',
            data: {
                keywords : keywords,
                // keywords : '春装',
                cid : cookId
            },
            dataType : "jsonp",
            jsonp : 'jsoncallback',
            success:function(data){
                var ads = data.val,
                    ads_len = ads.length,
                    //需要请求的推荐商品的数据量
                    diff_val = adSize - ads_len,
                    html = '';

                if (ads_len > 0) {
                    var len = Math.min(ads_len,adSize);

                    for(var i = 0; i < len; i++){
                        var jumpUrl = ads[i].Url;
                        var picUrl = ads[i].Picture;
                        var title = ads[i].Title;
                        var price = ads[i].Price;
                        html += '<li><a target="_blank"  href="' + jumpUrl + '"><img src="' + picUrl + '"></a><span>&yen;' + price + '</span></li>';
                    }

                }else if(diff_val > 0){
                    $.ajax({
                        url: base.baseUrl + 'goodsDetail/recommandProduces.htm?t=' + new Date().getTime(),
                        type: 'POST',
                        data: {
                            docId:docId,
                            title:keywords
                        },
                        success:function(data){
                            for(var j = 0; j < diff_val; j++){
                                if(data[j]){
                                    var item = data[j].shopList[0];
                                    html += '<li><a target="_blank" href="' + item.Url + '"><img src="' + item.Picture + '"></a><span>&yen;' + item.Price + '</span></li>';
                                }
                            }
                            $('#' + Id + '_prod').find('.rec-prod-list').append(html);
                        }
                    });
                }

                $('#' + Id + '_prod').find('.rec-prod-list').append(html);
            }
        });
    }

    ProdDetail.prototype.showPop = function(id,obj){
        $('#' + id + '_prod').css({display:'block'}).animate({
            height:'397'
        }, 300,'linear');
        obj.data('isOpen','true').addClass('grid-ls-on').siblings('.grid-ls').removeClass('grid-ls-on');
    }

    ProdDetail.prototype.hidePop = function(id,obj){
        $('#' + id + '_prod').animate({
            height:0
        },300,'linear',function(){
            $(this).hide();
        });
        obj.data('isOpen','false').removeClass('grid-ls-on');
    }

    ProdDetail.prototype.setPos = function(id){
        var base = this,
            popLeft = $('#' + id + '_prod').offset().left;

        $('#' + id + '_prod').find('.pop-arrow').css({
            left:(base.offetLeft - popLeft) + parseInt(base.offsetWidth/2) - 9
        })   
    }

    ProdDetail.prototype.showChart = function(chartId){
        var $chart = $('#chart_' + chartId);
        $chart.fadeIn();
    }

    ProdDetail.prototype.hideChart = function(chartId){
        var $chart = $('#chart_' + chartId);
        $chart.fadeOut();
    }
    /**
     * 绘制价格趋势
     * @param  {[string]} docid  [产品id]
     * @param  {[string]} source [来源商家]
     */
    ProdDetail.prototype.showProductPriceTrendByDocid = function(docid,source){
        var options = {
            height : "120",
            width : "275",
            titleAlign : "left",
            crosshairsColor : [ "#ff1919", "#ff1919" ],
            legendEnabled : false,
            handler : function(result) {
                    return result.val.averiage;
            }
        };
        //图标初始化位置
        var $topPriceHistroyDiv = $("#chart_"+docid),
            url = this.baseUrl + "/pricehistory/goodsDetail.htm?fill=true&source=" + source;

        $topPriceHistroyDiv.b5mtrend(docid, url, options);
    }    

    /**
     * 弹出框调用
     */
    $('.goods-list').find('.grid-ls').prodDetails({});

    /**
     * 弹出框内图片滚动
     */
    $.b5m.miniSlider = function(el,options){
        var base = this;
        base.$el = $(el);
        base.el = el;

        base.init = function(){
            var opts = $.extend({},$.b5m.miniSlider.defaultOptions,options || {});
            base.trigger = opts.trigger;

            base.bindEvent();
        }

        base.bindEvent = function(){
            var $container = base.$el,
                $slider = $container.find('ul'),
                $slider_item = $slider.find('li'),
                $slider_a = $slider.find('a'),
                $img = $slider_item.find('img'),
                item_w = $slider_item.outerWidth(true),
                step = $slider_item.length - 5,
                n = 0,
                $trigger = $container.find('.' + base.trigger),
                $targetImg = $container.siblings('.main-slider-pic').find('img'),

                $slider = $container.find('ul>li').eq(0);

            $img.on('mouseover',function(e){
            // $(document).on('mouseover',$img,function(e){
                var src = this.src;
                console.log(src)
                $targetImg[0].src = src;
                $slider_a.removeClass('active');
                $(this).parent('a').addClass('active');
                e.preventDefault();
            });

            //若缩略图小于4个就隐藏左右箭头
            if(step <= 0){
                $trigger.hide();
                return false;
            }

            $trigger.eq(0).addClass('arrow-left-disable');

            $trigger.on('click',function(e){
                if($(this).hasClass('slider-right') && (n < step)){
                    n++;
                    $slider.animate({marginLeft:-item_w*n});
                }else if($(this).hasClass('slider-left') && (n >= 1)){
                    n--;
                    $slider.animate({marginLeft:-item_w*n});
                }

                if(n > 0 && n < step ){
                    $trigger.removeClass('arrow-left-disable arrow-right-disable');
                }else if( n == 0){
                    $trigger.eq(0).addClass('arrow-left-disable');
                    $trigger.eq(1).removeClass('arrow-right-disable')
                }else if( n == step){
                    $trigger.eq(1).addClass('arrow-right-disable');
                    $trigger.eq(0).removeClass('arrow-left-disable')
                }
                e.preventDefault();
            });
        }
    }


    $.b5m.miniSlider.defaultOptions = {
        trigger:'arrow'
    }

    $.fn.b5m_miniSlider = function(options){
        return this.each(function(){
            new $.b5m.miniSlider(this,options).init();
        })
    }

    $('.mini-slider').b5m_miniSlider({
        trigger:'slider-trigger'
    }); 

})(jQuery,window,document);