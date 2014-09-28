/*
 * henry.li@b5m.com
 * @param {cutFn} window
 * @param {cancelFn} $
 * @returns {jQuery}
 * 使用方法 $('myimage').cur({
 *              callback : fn
 *              cutting  : fn
 *              start    : fn
 *              minSize  : number
 *      })
 */
(function(window, $) {
    $.fn.cut = function(Options) {
        return this.each(function() {
            Options = $.extend({
                callback: function() {},
                cuting : function(){},
                start   : function(){},
                minSize: 48
            },Options || {});
            var $this = $(this);
            var self = this;
            $this.css({'position': 'absolute'});
            $this.parent().css({
                'user-select': 'none'
            });
            $this.parent().find('.loading').html('正在加载...').show();
            var $cutHelper, $dragHelp, $mask, cursor = $(document.body).css('cursor');
            var containerWidth = $this.parent().width();
            var setClip = function(init) {
                var position = init ? {left:120,top:120} : $dragHelp.position();
                var w = $dragHelp.width();
                var h = $dragHelp.height();
                $cutHelper.css({
                    clip: 'rect(' + position.top + 'px ' + (w + position.left) + 'px ' + (h + position.top) + 'px ' + position.left + 'px)'
                });
                Options.cuting.call($this,{
                    x: position.left - $this.position().left,
                    y: position.top - $this.position().top,
                    w: w,
                    h: h,
                    imgW: $this.width(),
                    imgH: $this.height(),
                    imgRealW: realSize[0],
                    imgRealH: realSize[1]
                });
            };
            var realSize = [];
            this.setImage = function(){
                $(this).hide();
                var image = $(new Image());
                image.load(function(){
                    var w = this.width;
                    var h = this.height;
                    realSize = [w,h];
                    var _w,_h
                    if(w == h && w > containerWidth){
                        _w = containerWidth;
                        _h = containerWidth;
                    }else if(w > h && w > containerWidth){
                        _w = containerWidth;
                        _h = Math.ceil(h * _w / w);
                    }else  if(h > w && h > containerWidth){
                        _h = containerWidth;
                        _w = Math.ceil(w * _h/h);
                    }else{
                    	if(w > h){
                    		_w = containerWidth;
                    		_h = _h = Math.ceil(h * _w / w);
                    	}else{
                    		_h = containerWidth;
                    		_w = _w = Math.ceil(w * _h / h);
                    	}
                    }
                    $this.css({
                        width : _w + 'px',
                        height: _h + 'px',
                        left  : (containerWidth - _w)/2 + 'px',
                        top   : (containerWidth-_h)/2 + 'px',
                        display:'block'
                    });
                    $this.parent().find('.loading').hide();
                    self.createUi().setEvents();
                }).error(function(){
                	alert('图片加载失败');
                }).attr('src',$this.attr('src'));
            };
            this.createUi = function() {
                $this.next('div.mask').remove();
                $this.next('.ui-cut-helper-image').remove();
                $this.next('.ui-cut-helper').remove();

                $this.after('<div class="mask" style="width:300px;height:300px;background:black;opacity:0.5;filter:alpha(opacity=50);position:absolute;left:0;top:0"></div>');
                $mask = $this.next('div.mask');
                $mask.after($('<div></div>').append($this.clone()).addClass('ui-cut-helper-image'));
                $cutHelper = $mask.next();
                $cutHelper.after('<div class="ui-cut-helper"><div class="bg"></div><div class="ui-cut-help-rb"></div></div>');
                $cutHelper.find('.bg').css({
                	width : '100%',
                	height: '100%',
                	backgroundColor : 'black',
                	opacity : '0.1'
                });
                $dragHelp = $cutHelper.next();
                setClip('init');
                return this;
            };
            this.setEvents = function() {
                $dragHelp.bind({
                    mousedown: function(e) {
                        e.preventDefault();
                        var initCur = [e.pageX, e.pageY];//开始时的鼠标坐标
                        var initPos = $dragHelp.position();//开始时的left，top值
                        var initSize = $dragHelp.width();//开始时的宽高
                        var imageSize = [$this.position().left,$this.position().top,$this.width(),$this.height()];
                        if ($(e.target).is('.ui-cut-help-rb')) {
                            $(document.body).css('cursor', 'nwse-resize');

                            var max_size = Math.min(containerWidth - initPos.left -imageSize[0] ,containerWidth - initPos.top - imageSize[1]);
                            $(document).bind('mousemove.cut', function(e) {
                                e.preventDefault();
                                var size =  Math.max(Math.min(max_size, Math.max(e.pageX - initCur[0] + initSize,e.pageY - initCur[1] + initSize)),Options.minSize);
                                    $dragHelp.css({
                                        width: size,
                                        height: size
                                    });
                                    setClip();
                            }).bind('mouseup.cut', function(e) {
                                e.preventDefault();
                                $(document).unbind('mouseup.cut').unbind('mousemove.cut');
                                $(document.body).css('cursor', cursor);
                                ;
                            });
                        } else {
                            $(document.body).css('cursor', 'move');
                            $(document).bind('mousemove.cut', function(e) {
                                e.preventDefault();
                                var _x = Math.min(e.pageX - initCur[0] + initPos.left,imageSize[2] - initSize + imageSize[0]);
                                _x = Math.max(_x,imageSize[0]);
                                var _y = Math.min(e.pageY - initCur[1] + initPos.top,imageSize[3] - initSize + imageSize[1]);
                                _y = Math.max(_y,imageSize[1]);
                                $dragHelp.css({
                                    left: _x,
                                    top: _y
                                });
                                setClip();
                            }).bind('mouseup.cut', function(e) {
                                $dragHelp.unbind('mouseup.cut.cut');
                                $(document).unbind('mouseup.cut').unbind('mousemove.cut');
                                $(document.body).css('cursor', cursor);
                            });
                        }
                        $(this).bind('mouseup.cut',function(){
                            $dragHelp.unbind('mouseup.cut.cut');
                            $(document).unbind('mouseup.cut').unbind('mousemove.cut');
                            $(document.body).css('cursor', cursor);
                        });
                        //$dragHelp
                    }
                    
                });
                Options.saveBtn.removeClass('saveBtnDis').bind('click',function(e){
                    e.preventDefault();
                    if($(this).hasClass('saveBtnDis')){
                        return false;
                    }
                    self.finishCut();
                    self.destory();
                    $(this).addClass('saveBtnDis');
                });
                Options.cancelCutBtn.length && Options.cancelCutBtn.removeClass('saveBtnDis').bind("click.cut",function(e){
                    e.preventDefault();
                    if($(this).hasClass('saveBtnDis')){
                        return false;
                    }
                    self.destory();
                });
                $("#J_reCut").addClass('saveBtnDis');
            };
            this.finishCut = function(){
                Options.callback.call($this.get(0),{
                    x : $dragHelp.position().left - $this.position().left,
                    y : $dragHelp.position().top - $this.position().top,
                    w : $dragHelp.width(),
                    h : $dragHelp.height(),
                    imgW : $this.width(),
                    imgH : $this.height(),
                    imgRealW : realSize[0],
                    imgRealH : realSize[1]
                });
            };
            this.destory = function(){
                $this.next('div.mask').unbind().remove();
                $this.next('.ui-cut-helper-image').unbind().remove();
                $this.next('.ui-cut-helper').unbind().remove();
                Options.cancelCutBtn.length && Options.cancelCutBtn.unbind("click.cut");
                Options.saveBtn.addClass('saveBtnDis');
            };
            this.setImage();//.bindFn();
        });
    };
})(window, jQuery);