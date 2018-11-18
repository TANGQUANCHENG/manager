/**
 * Created by dxkj on 2016/11/2.
 */
function addTab(str,index,title){

    var o =str, m = index, l = title, k = true;
    if (o == undefined || $.trim(o).length == 0) {
        return false
    }
    $(".J_menuTab",parent.document).each(function () {
        if ($(this).data("id") == o) {
            if (!$(this).hasClass("active")) {
                $(this).addClass("active").siblings(".J_menuTab",parent.document).removeClass("active");
                g(this);
                $(".J_mainContent .J_iframe",parent.document).each(function () {
                    if ($(this).data("id") == o) {
                        $(this).show().siblings(".J_iframe").hide();
                        return false
                    }
                })
            }
            k = false;
            return false
        }
    });
    if (k) {
        var p = '<a href="javascript:;" class="active J_menuTab" data-id="' + o + '">' + l + ' <i class="fa fa-times-circle"></i></a>';
        $(".J_menuTab",parent.document).removeClass("active");
        var n = '<iframe class="J_iframe" name="iframe' + m + '" width="100%" height="'+getClientHeight()+'" src="' + o + '" frameborder="0" data-id="' + o + '" seamless></iframe>';
        $(".J_mainContent",parent.document).find("iframe.J_iframe").hide().parents(".J_mainContent").append(n);
        $(".J_menuTabs .page-tabs-content",parent.document).append(p);
        g($(".J_menuTab.active",parent.document))
    }
    return false
}
function g(n) {
    var o = f($(n).prevAll()), q = f($(n).nextAll());
    var l = f($(".content-tabs",parent.document).children().not(".J_menuTabs",parent.document));
    var k = $(".content-tabs",parent.document).outerWidth(true) - l;
    var p = 0;
    if ($(".page-tabs-content",parent.document).outerWidth() < k) {
        p = 0
    } else {
        if (q <= (k - $(n).outerWidth(true) - $(n).next().outerWidth(true))) {
            if ((k - $(n).next().outerWidth(true)) > q) {
                p = o;
                var m = n;
                while ((p - $(m).outerWidth()) > ($(".page-tabs-content",parent.document).outerWidth() - k)) {
                    p -= $(m).prev().outerWidth();
                    m = $(m).prev()
                }
            }
        } else {
            if (o > (k - $(n).outerWidth(true) - $(n).prev().outerWidth(true))) {
                p = o - $(n).prev().outerWidth(true)
            }
        }
    }
    $(".page-tabs-content",parent.document).animate({marginLeft: 0 - p + "px"}, "fast")
}

function f(l) {
    var k = 0;
    $(l).each(function () {
        k += $(this).outerWidth(true)
    });
    return k
}

function closeActive() {
    $(".J_menuTabs .J_menuTab",parent.document).each(function () {
        if ($(this).hasClass("active")) {
            $(this).find(".fa-times-circle").click();
        }
    });
}
function getClientHeight() {
    var clientHeight = 0;
    if (document.body.clientHeight && document.documentElement.clientHeight) {
        var clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
    }
    else {
        var clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
    }
    return clientHeight-90;
}