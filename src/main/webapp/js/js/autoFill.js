
Cookies = {};
Cookies.set = function(name, value) {
    var argv = arguments;
    var argc = arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    var path = (argc > 3) ? argv[3] : '/';
    var domain = (argc > 4) ? argv[4] : null;
    var secure = (argc > 5) ? argv[5] : false;
    document.cookie = name + "=" + escape(value) +
        ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
        ((path == null) ? "" : ("; path=" + path)) +
        ((domain == null) ? "" : ("; domain=" + domain)) +
        ((secure == true) ? "; secure" : "");
};
Cookies.get = function(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    var j = 0;
    while (i < clen) {
        j = i + alen;
        if (document.cookie.substring(i, j) == arg)
            return Cookies.getCookieVal(j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0)
            break;
    }
    return null;
};
Cookies.clear = function(name) {
    if (Cookies.get(name)) {
        var expdate = new Date();
        expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
        Cookies.set(name, "", expdate);
    }
};
Cookies.getCookieVal = function(offset) {
    var endstr = document.cookie.indexOf(";", offset);
    if (endstr == -1) {
        endstr = document.cookie.length;
    }
    return unescape(document.cookie.substring(offset, endstr));
};


;(function($) {

    $.fn.autoFill = function(url, site, callback) {


        function Autofill(obj, url, site, callback) {
            this.input = $(obj);
            if (this.input.data('initted')) {
                return false;
            }

            this.input.data('initted', 'true');
            this.input.attr('autocomplete', 'off');
            this.site = site || 'www';
            this.ddindex = -1;
            this.url = url || 'http://search.b5m.com/allAutoFill.htm'; //'http://search.stage.bang5mai.com/allAutoFill.htm';
            this.callback = callback || '';
            this.vesion = '1.2';

            this.init();
        }

        Autofill.prototype.init = function() {

            this.creatDom();

            if (!Cookies.get('firstlead') && this.input.is(':visible')) {
                this.createlead();
                Cookies.set('firstlead', 'true', new Date("December 31, 2020"), '', '.b5m.com');
            }
            this.bindEvent();
            this.setStyle();
        };

        Autofill.prototype.createlead = function() {

            var _this = this;
            this.lead = $('<div class="autofill-firstlead"><em class="at-arrow"></em><em class="at-close"></em><div class="at-con"></div><div class="at-sbm"><a class="at-btn"></a></div></div>');
            if (!$('.autofill-firstlead').length) {
                $(document.body).append(this.lead);
            }
            this.lead.find('.at-close,.at-btn').click(function() {
                _this.lead.hide();
            });

            $(document).on('click.af', function() {
                _this.lead.hide();
            });

            $(this.lead).on('click.af', function() {
                return false;
            });

            $(window).on('resize.af', function() {

                _this.resetleadPosition();
            });

            this.lead.css({
                position: 'absolute',
                left: this.input.offset().left + 'px',
                top: this.input.offset().top + this.input.outerHeight() + 10 + 'px',
                'zIndex': 9998,
                background: '#fff',
                display: 'none'
            });

            this.lead.show();
        };
        Autofill.prototype.resetleadPosition = function() {
            this.lead.css({
                left: this.input.offset().left + 'px',
                top: this.input.offset().top + this.input.outerHeight() + 10 + 'px'
            });
        };

        Autofill.prototype.getCity = function() {
            var city = '';
            if (Cookies.get('tcity_sn')) {
                city = decodeURIComponent(decodeURIComponent(Cookies.get('tcity_sn')));
            } else {
                $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function() {
                    city = remote_ip_info.city;
                });
            }
            return city;
        };

        Autofill.prototype.bindEvent = function() {

            var _this = this;
            this.time = null;
            var obj = this.input,
                con = this.con;

            obj.on('keyup', function(e) {

                var params = "keyWord=" + _this.getJsParams($.trim(obj.val())) + '&city=' + _this.getCity();

                if (!$.trim(obj.val()).length) {
                    _this.hide();
                    return;
                }
                if (e.keyCode == 40 || e.keyCode == 38 || e.keyCode == 37 || e.keyCode == 39) {
                    return;
                }
                if (_this.time != null) {
                    clearTimeout(_this.time);
                }

                _this.time = setTimeout(function() {
                    _this.hide();
                    _this.getData(_this.url, params);
                }, 100);

                return false;

            });

            obj.on('focus', function() {
                _this.setStyle();
                obj.addClass('act');

            });

            con.off('click.af').on('click.af', function(e) {
                e.stopPropagation();
            });

            $(document).on('click.af', function() {
                _this.input.removeClass('af-cur');
                _this.hide();
            });

            $(window).on('resize.af', function() {
                if (_this.con.is(':visible')) {
                    _this.setPosition();
                }
            });

            con.delegate('.autofill-more span', 'click', function(e) {
                var prt = $(this).parent('.autofill-more');
                var index = _this.con.find('dl').index($(this).parents('dl'));

                if ($(this).parents('dl').hasClass('open')) {
                    if (index) {
                        $(this).parents('dl').find('dd:gt(1)').not(':last').hide();
                        $(this).parents('dl').removeClass('open');
                    } else {
                        $(this).parents('dl').find('dd:gt(3)').not(':last').hide();
                        $(this).parents('dl').removeClass('open');
                    }

                } else {
                    prt.siblings('dd').show();
                    prt.parent('dl').siblings('dl').removeClass('open').end().addClass('open');
                }

                _this.moreHideHandle();

            });

            con.delegate('dd:not(.autofill-more)', 'click.at', function(e) {

                if (obj.hasClass('act')) {
                    obj.val($.trim($(this).find('.txt').text())).removeClass('act');
                }

                var url = _this.locationTO($(this).find('span')[0]);

                _this.hide();
            });

            obj.on('keydown', function(e) {

                var dd = con.find('dd'),
                    ddSize = dd.filter(':visible').not('.autofill-more').size();
                if (e.keyCode == 13) {
                    _this.enterHandler();
                }

                if (!_this.input.val().length) {
                    _this.hide();
                    return;
                }

                if (e.keyCode == 40 && con.is(':visible')) {

                    _this.disableInputDft();

                    if (_this.ddindex < ddSize - 1) {
                        _this.ddindex++;

                    } else {
                        _this.ddindex = 0;
                    }
                    dd.removeClass('curr').filter(':visible').not('.autofill-more').eq(_this.ddindex).addClass('curr');

                    obj.val(dd.filter(':visible').not('.autofill-more').eq(_this.ddindex).find('.txt').text());

                    return false;

                } else {
                    if (e.keyCode == 38 && con.is(':visible')) {
                        //按向下键时移动光标
                        _this.disableInputDft();
                        if (_this.ddindex > 0) {
                            _this.ddindex--;
                        } else {
                            _this.ddindex = ddSize - 1;
                        }
                        dd.removeClass('curr').filter(':visible').not('.autofill-more').eq(_this.ddindex).addClass('curr');
                        obj.val(dd.filter(':visible').not('.autofill-more').eq(_this.ddindex).find('.txt').text());

                        return false;
                    }
                }
            });
        };

        Autofill.prototype.moreHideHandle = function() {
            this.con.find('dl:not(.open)').each(function(i) {
                if (i == 0) {
                    if ($(this).find('dd').length > 4) {
                        $(this).find('dd:gt(3)').not(':last').hide();

                    }
                } else {
                    if ($(this).find('dd').length > 2) {

                        $(this).find('dd:gt(1)').not(':last').hide();

                    }
                }
            });
        };

        Autofill.prototype.hide = function() {

            this.con.hide().html('');
            this.enableInputDft();
            this.ddindex = -1;
            $(this.input).trigger('listhide');
        };

        Autofill.prototype.show = function() {
            if (this.input.val().length) {
                this.con.show();
                this.setPosition();
                $(this.input).trigger('listshow');
            }
        };

        Autofill.prototype.enterHandler = function() {
            if (this.con.find('dd.curr').size()) {
                this.con.find('dd.curr').trigger('click');
                this.input.off('keyup');
                this.input.unbind('keyup');
                return false;
            }
        };

        Autofill.prototype.disableInputDft = function() {
            this.input.off('keydown.enter').on('keydown.enter', function(e) {
                if (e.keyCode == 13) {
                    e.preventDefault();
                    return false;
                }
            });
        };

        Autofill.prototype.enableInputDft = function() {

            this.input.off('keydown.enter');
        };

        Autofill.prototype.creatDom = function(arr) {

            this.con = $('<div class="autofill"></div>');

            $(document.body).append(this.con);

            this.input.addClass('af-input');
        };

        Autofill.prototype.setStyle = function() {

            var _this = this;
            this.con.css({
                position: 'absolute',
                left: this.input.offset().left + 'px',
                top: this.input.offset().top + this.input.outerHeight() + 'px',
                'zIndex': 9999,
                background: '#fff',
                width: this.input.outerWidth() - 2 + 'px',
                display: 'none'
            });

        };

        Autofill.prototype.setPosition = function() {

            this.con.css({
                left: this.input.offset().left + 'px',
                top: this.input.offset().top + this.input.outerHeight() + 'px'
            });

        };

        Autofill.prototype.htmlSet = function(html) {

            var con = this.con;
            var _this = this;

            con.html(html);

            var dl = con.find('dl'),
                dd = dl.find('dd'),
                dv = con.find('div'),
                maxsize = 15,
                n,
                otad,
                adl;

            con.find('dt').each(function() {

                $(this).parent('dl').addClass($.trim($(this).text()));
                $(this).parent('dl').find('dd').attr('ct', $.trim($(this).text()));
                $(this).text(_this.getCTitle($.trim($(this).text())));

            });

            n = dl.index(dl.filter(function(inx) {
                return $(this).hasClass(_this.site);
            }));

            if (!$.trim(con.find('dd').text()).length) {
            	if(dv){
            		con.empty().append(dv);
            		_this.show();
            	}else{
            		_this.hide();
            	}
                return;
            }


            //首页类型处理
            if (n == -1) {
                dl.each(function(i) {
                    $(this).find(dd).find('em').after('<span class="st">' + $(this).find('dt').text() + '</span>');
                });
                con.empty().html('<dl></dl>');

                dd.sort(function(a, b) {
                    return parseInt($(b).find('em').text(), 10) - parseInt($(a).find('em').text(), 10);
                });

                $(dd).each(function() {
                    if (parseInt($(this).find('em').text()) > 9999) {
                        $(this).find('em').text('9999+');
                    }
                });

                dd = Array.prototype.slice.apply(dd, [0, 10]);

                con.find('dl').html(dd);

                this.show();

            } else {
                $(dd).each(function() {
                    if (parseInt($(this).find('em').text()) > 9999) {
                        $(this).find('em').text('9999+');
                    }
                });
                if(dv){
                	dl.eq(n).insertAfter(dv);
                }else{
                	dl.eq(n).prependTo(con);
                }

                dl = con.find('dl'); //update dl

                dl.each(function(i) {
                    if (i == 0) {
                        if ($(this).find('dd').length > 4) {
                            $(this).find('dd:gt(3)').hide();
                            $(this).append('<dd class="autofill-more"><span>+</span></dd>');
                        } else if ($(this).find('dd').length == 0) {
                            $(this).remove();
                        }
                    } else {
                        if ($(this).find('dd').length > 2) {
                            $(this).find('dd:gt(1)').hide();
                            $(this).append('<dd class="autofill-more"><span>+</span></dd>');
                        } else if ($(this).find('dd').length == 0) {
                            $(this).remove();
                        }
                    }
                });

                adl = con.find('dl'); //update dl

                if (adl.length > maxsize) {

                    con.empty();

                    if (adl.filter('.' + this.site).length) {
                        otad = adl.not(':first').sort(function(a, b) {
                            return $(b).find('dd').length - $(a).find('dd').length;
                        });

                        con.html(Array.prototype.slice.apply(otad, [0, maxsize - 1])).prepend(dl.first());

                    } else {
                        otad = adl.sort(function(a, b) {
                            return $(b).find('dd').length - $(a).find('dd').length;
                        });

                        con.html(Array.prototype.slice.apply(otad, [0, maxsize]));
                    }
                }
                this.show();
            }

            this.input.addClass('af-cur');

        };

        Autofill.prototype.getData = function(url, params) {

            var _this = this;
            $.ajax({
                url: url,
                async: false,
                data: params,
                dataType: "jsonp",
                timeout: 200,
                jsonp: 'jsoncallback',
                success: function(data) {
                    _this.createList(data);
                }
            });
        },

            Autofill.prototype.createList = function(data) {
                var head = '';
                var html = '';
                for (var i in data) {
                	if("correction" == i){
                		head = '<div style="border-top:1px solid #ccc;padding:5px 0 5px 5px"><font style="color:red">' + data[i][0].value + '</font></div>';
                	}else{
                		var html_content = '';
                		$(data[i]).each(function(j) {
                			html_content += '<dd data-attr="1001" data-keyword="' + data[i][j].value + '"><em>' + data[i][j].count + '</em><span  class="txt">' + data[i][j].value + '</span></dd>';
                		});
                		html += '<dl><dt>' + i + '</dt>' + html_content + '</dl>';
                	}
                }

                this.htmlSet(head + html);
            };
        Autofill.prototype.getJsParams = function(str) {

            var reg = /[']/g;
            var str = str.replace(reg, "\\'");

            str = str.replace(/\&/g, "&amp;");

            var reg = /["]/g;
            var str = str.replace(reg, "&quot;");

            return str;

        };
        Autofill.prototype.getCTitle = function(text) {
            switch (text) {
                case 'b5mo':
                    return '商品';
                    break;
                case 'tejia':
                    return '特价';
                    break;
                case 'hotel':
                    return '酒店';
                    break;
                case 'ticketp':
                    return '票务';
                    break;
                case 'tourguide':
                    return '攻略';
                    break;
                case 'tourp':
                    return '旅游';
                    break;
                case 'tuanm':
                    return '团购';
                    break;
                case 'zdm':
                    return '值得买';
                    break;
                case 'she':
                    return '帮社区';
                    break;
                case 'korea':
                    return '韩国馆';
                    break;
                case 'guang':
                    return '逛';
                    break;
                default:
                    return '';
            }
        };


        Autofill.prototype.getUrl = function(txt,value) {

            var url = '';
            txt = $.trim(txt.replace(/open/g, ''));

            switch (txt) {
                case 'b5mo':
                    url = 'http://s.b5m.com/search/s/___image________________' + value + '.html';
                    break;
                case 'hotel':
                    url = 'http://you.b5m.com/taoPage_-1_hotelSearchresult_' + value + '_1_search';
                    break;
                case 'tourguide':
                    url = 'http://you.b5m.com/taoPage_-1_noteSearchresult_' + value + '_1_hotNotes_search';
                    break;
                case 'ticketp':
                    url = 'http://piao.b5m.com/search_' + value + '.html';
                    break;
                case 'tourp':
                    url = 'http://you.b5m.com/taoPage_-1_searchresult_' + value + '_1_search';
                    break;
                case 'tejia':
                    url = 'http://tejia.b5m.com/taoPage_index_showIndex_' + value;
                    break;
                case 'tuanm':
                    url = 'http://tuan.b5m.com/__' + value;
                    break;
                case 'zdm':
                    url = 'http://zdm.b5m.com/keyword_' + value;
                    break;
                case 'she':
                    url = 'http://shang.b5m.com/portal.php?mod=list&catid=1&keyword=' + value;
                    break;
                case 'korea':
                    url = 'http://haiwai.b5m.com/goods/'+encodeURIComponent(value)+'.html';
                    break;

                case 'guang':
                    url = 'http://guangsearch.b5m.com/keyword_' + value;
                    break;

                default:
                    url = 'http://s.b5m.com/search/s/___image________________' + value + '.html';
            }

            return url;

        };


        Autofill.prototype.locationTO = function(target,spm) {

            var txt = (this.site !='www' ? $(target).parents('dl').attr('class') : $.trim($(target).parent().attr('ct'))) || '',
            value = this.input.val();

            var _url = this.getUrl(txt,value);

            var urlConcat = _url.indexOf('?') !=-1 ? '&' : '?';
            var _spm = typeof Stat === 'object' ? (urlConcat+'spm='+Stat.spm()) : '';

            var url = _url + _spm;

            if(spm) {
                return url;
            }

            if (this.callback && this.site != 'www' && txt == this.site) {
                return this.callback();
            }

            location.href = url;

        };

        return this.each(function() {
            this.autofill = new Autofill(this, url, site, callback);
        });

    };



}(jQuery));


function getAutoFillDataUrl(target) {
    var _af = jQuery('.af-cur')[0].autofill;
    return _af.locationTO.call(_af,target,true);
}