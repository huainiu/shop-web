$(function() {
    // lazy loading images
    $('img').imglazyload({fadeIn:true});
	var S_Obj = {
		item_h : $('.filter-item').height(),
        init:function(){
            var $filter_item = $('.filter-item');
            $filter_item.each(function(){
                var $this = $(this),
                    $items_box = $this.find('dd'),
                    items_box_w = $items_box.width(),
                    $items = $items_box.find('a'),
                    items_len = $items.length,
                    items_w = $items.outerWidth(true),
                    items_all_w = items_len*items_w;
                // 判断当前类目的总宽度超过容器宽度并且当前类目显示则显示展开按钮，反之则隐藏
                if((items_all_w > items_box_w) && !$this.is(':hidden')){
                    $this.find('.show-more').show();
                }else{
                    $this.find('.show-more').hide();
                }
            })
        },
		toggleItem: function(elem){
			$('.' + elem).click(function(e) {
				var $this = $(this);
				e.preventDefault();
                // 切换类目高度，以此来达到显隐
				if($this.hasClass('open')){
                    $this.removeClass('open').html('<span>展开</span>').parents('.filter-item').css('height',S_Obj.item_h);
			    }else{
			        $this.addClass('open').html('<span>收起</span>').parents('.filter-item').css('height','auto');
			    }
			});
		},
		showItem: function(elem,className){
            // 获取第三、四项的搜索条件内容
            var $filter = $('#J_filter'),
                filter_len = $filter.find('dl').length;
            if(filter_len <= 8) return false;

            var str = $.trim($filter.find('dl:eq(7) dt').text()).slice(0,-1);
            //搜索条件大于4条时才取第3个搜索条件
            if(filter_len > 9){
                str = str + '、'+ $.trim($filter.find('dl:eq(8) dt').text()).slice(0,-1) + '等';
            }
			$('#' + elem).click(function(e){
                e.preventDefault();
				var $this = $(this)
                // 切换除去前两个和最后一个item的显隐
                $filter.find('dl:gt(6):not(:last)').slideToggle(200);
                // 切换更多按钮内容
				txt = $this.hasClass(className) ? '收起' : '其他属性<em>（' + str + '）</em>';
				$trigger = $this.find('span').html(txt);
				$this.toggleClass(className);
                // 调用初始化函数，检测是否应该显示展开、收起按钮
                S_Obj.init();
			})
		}
	};
    S_Obj.init();
	S_Obj.toggleItem('show-more');
	S_Obj.showItem('J_more','open');

    $(window).resize(function(event) {
            S_Obj.init();   
            resizeTop();
    });
	// menu
    var $menu = $('#J_menu'),
        $trigger_menu = $menu.find('li'),
        $subMenu = $menu.find('.sub_item'),
        timer;
    $menu.hover(function(){
        $subMenu.stop(true,true).animate({
            display:'block',
            width:552
        },{
            duration:300
        })
    },function(){        
        $subMenu.stop(true,true).animate({
            display:'none',
            width:0
        },{ 
            duration:200
        })
    });
    $trigger_menu.hover(function(){
        var $this = $(this),
            $subContent = $this.find('.sub-in'),
            $subMenuRel = $this.find('.sub_item');
        $subMenu.hide();
        $subContent.show();
        $subMenuRel.show();
        $this.addClass('open');
    },function(){
        var $this = $(this),
            $subMenuRel = $this.find('.sub_item'),
            $subContent = $this.find('.sub-in');
        $this.removeClass('open');  
        $subMenuRel.hide();      
        $subContent.hide();
    });

    $("#J_all_cate").hover(function(){
        $(this).find('h2').addClass('up');
    	$menu.show();
    },function(){
        $(this).find('h2').removeClass('up');
    	$menu.hide();
    });
    // 更多分类
    $('.more-category').hover(function(){
        $(this).addClass('open');
    },function(){
        $(this).removeClass('open');
    });

    $('#J_sub_nav').find('dd').on('click.nav',function(e){
        e.preventDefault();
        var $this = $(this);
        $this.siblings('dd').removeClass('cur').find('.sub-nav').slideUp().end().end().toggleClass('cur').find('.sub-nav').slideToggle();
    });

    function resizeTop(){
        $('.gotop').css('margin-left',($('.flex').width()/2 + 10));
    }
    resizeTop();

    // popup box
    var popup = {
        init:function(element){
            var $trigger = $('.'+ element),
                $targetPop = $('.'+ $trigger.data('target')),
                $closeTrigger = $targetPop.find('.close');
            $trigger.on('click',function(e){
                popup.showPop($targetPop);
                return false;
            });
            $closeTrigger.on('click',function(){
                popup.hidePop($targetPop);
            });
            $(document).on('click',function(){
                popup.hidePop($targetPop);
            });
            $targetPop.on('click',function(e){
                e.stopPropagation()
            })
        },
        showPop:function(obj){
            if(!obj) return;
            obj.fadeIn();
        },
        hidePop:function(obj){
            if(!obj) return;
            obj.fadeOut();
        }
    };
    popup.init('popup-trigger')

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
                    $submit_btn.off();
                    return false;
                }
                $submit_btn.addClass('btn-enable').removeClass('btn-disable');
                $submit_btn.on('click',function(e){
                    e.preventDefault();
                    validate.submit($target,url);
                })
            });
        },
        submit:function(obj,url){
            var data = obj.serialize();
                url = url + "?" + data;
            $.ajax({
                url: url,
                data: {}
            })
            .always(function() {
                $('.popup-in').html('<div class="tips"><h5>您的意见已提交成功!</h5><p>谢谢参与，感谢您长久对帮5买的支持~</p></div>');
                var flag = setTimeout(function(){
                    $('.feed-popup').hide();
                    clearTimeout(flag);
                },1000);
            })

        }
    }

    $('.feed-form').find('.required').keyup(function(event) {
        validate.check('feed-form',_basePath + "searchRecommend.htm");
    });
});