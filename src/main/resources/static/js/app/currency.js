//通用状态
var genderEnum = {
    "MALE": '<span style="color: blue">男</span>',
    "FEMALE": '<span style="color: palevioletred">女</span>',
    "OTHER": '<span style="color: grey">其他</span>'
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
function convertObject(obj) {
    var str = "";
    for (var item in obj) {
        str += item + "=" + obj[item] + "&"
    }
    return str;
}