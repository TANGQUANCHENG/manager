//通用状态
var shelfStatus = {
    "ON": '<span style="color: green">上架</span>',
    "OFF": '<span style="color: red">下架</span>'
}
var voucherStatus = {
    "UNSTARTED": '<span style="color: orangered">未开始</span>',
    "RUNNING": '<span style="color: green">进行中</span>',
    "FINISDED": '<span style="color: red">已结束</span>'
}
var ticketStatus={
    "TO_BE_AUDITED": '<span style="color: blue">待审核</span>',
    "ABNORMAL": '<span style="color: red">卡券异常</span>',
    "NORMAL": '<span style="color: green">正常</span>',
    "EXPIRED": '<span style="color: orange">已到期</span>',
    "USED": '<span style="color: grey">已使用</span>',
    "FREEZE": '<span style="color: #3f92d2">已冻结</span>',
    "COMPLETED": '<span style="background-color: #008500" class="label" >已完成</span>'
}
var ticketType = {
    "RECOVERY": '<span style="color: green">回收卡券</span>',
    "STOCK": '<span style="color: blue">自有卡券</span>'
}
var accountStatus = {
    "NORMAL": '<span style="color: green">正常</span>',
    "FREEZE": '<span style="color: red">冻结</span>'
}
var orderStatus= {
    "PENDING_PAYMENT": '<span style="color: purple">等待付款</span>',
    "SOLD": '<span style="color: blue">出售成功</span>',
    "SUCCESS": '<span style="color: green">交易成功</span>',
    "REFUNDING": '<span style="color: orange">退款中</span>',
    "REFUNDED": '<span style="color: red">已退款</span>'
}
var collectOrderStatus= {
    "TO_BE_AUDITED": '<span style="color: blue">待审核</span>',
    "FAILED": '<span style="color: red">失败</span>',
    "TO_BE_SOLD": '<span style="color: orange">待出售</span>',
    "SOLD": '<span style="color: purple">出售成功</span>',
    "SUCCESS": '<span style="color: green">成功</span>'
}
var transferStatus= {
    "TO_BE_TRANSFERRED": '<span style="color: blue">待转账</span>',
    "PROCESSING": '<span style="color: purple">处理中</span>',
    "ABNORMAL": '<span style="color: orange">转账异常</span>',
    "FAILED": '<span style="color: red">失败</span>',
    "SUCCESS": '<span style="color: green">转账成功</span>'
}
var refundStatus= {
    "REFUNDING": '<span style="color: blue">发起退款</span>',
    "PROCESSING": '<span style="color: purple">处理中</span>',
    "ABNORMAL": '<span style="color: orange">退款异常</span>',
    "SUCCESS": '<span style="color: green">退款成功</span>'
}
var sellerGrade={
    "ONE": '<span >一星</span>',
    "TWO": '<span >二星</span>',
    "THREE": '<span >三星</span>',
    "FOUR": '<span >四星</span>'
}
var sellerMagnitude= {
    "SMALL_SCALE": '<span style="color: blue">小型</span>',
    "MEDIUM_SIZED": '<span style="color: orange">中型</span>',
    "LARGE_SCALE": '<span style="color: red">大型</span>',
}
var pointLogType={
    "CONSUME": '<span style="color: blue">积分抵现</span>',
    "SUCCESSFUL_TRADE": '<span style="color: orange">交易成功</span>',
    "CONFIRM_RECEIPT": '<span style="color: red">确认收货</span>',
    "CONFIRM_FIRST_TIME": '<span style="color: red">首次确认收货</span>',
    "POINT_RETURN": '<span style="color: green">积分返还</span>',
    "GIFT_POINT": '<span style="color: purple">积分赠送</span>',
}
var buyerVoucherStatus={
    "AVAILABLE": '<span style="color: green">可用</span>',
    "USED": '<span style="color: orange">已使用</span>',
    "DISABLED": '<span style="color: red">不可用</span>',
}
var voucherType={
    "GRANT": '<span style="color: blue">发放</span>',
    "RECEIVE": '<span style="color: orange">领取</span>',
    "GIFT_BAG": '<span style="color: orange">新人大礼包</span>',
    "THANKSGIVING_GIFT": '<span style="color: red">感恩礼</span>'
}
function getTodyStart() {
    var day=new Date();
    var str=day.getFullYear()+"-"+(day.getMonth()+1)+"-"+day.getDate() +" 00:00:00";
    return str;
}
function getTodyEnd() {
    var day=new Date();
    var str=day.getFullYear()+"-"+(day.getMonth()+1)+"-"+day.getDate() +" 23:59:59";
    return str;
}

/**
 * 时间戳进行转换(两位年)
 * @param shijianchuo
 * @returns {*}
 */
function format(shijianchuo) {

    if (shijianchuo == null || shijianchuo == '') {
        return '未知时间';
    }
//shijianchuo是整数，否则要parseInt转换
    var time = new Date(shijianchuo);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}

function add0(m) {
    return m < 10 ? '0' + m : m
}