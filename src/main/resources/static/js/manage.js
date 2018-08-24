$(document).ready(function () {
    $('#dataTable').datagrid({
        url:'role/list',
        fitColumns: true,
        view: detailview,
        detailFormatter: function (index, row) {
            return '<div class="easyui-panel" style="padding:5px 0;">'+index+'</div>';
        },
        onExpandRow: function (index, row) {
            console.log(1);
        },
        columns:[[
            {field:'name',title:'名称',width:100,align:'center'},
            {field:'code',title:'代码',width:100,align:'center'},
            {field:'available',title:'状态',width:100,align:'center'},
            {field:'creatorName',title:'创建人',width:100,align:'center'},
            {field:'createTime',title:'创建时间',width:100,align:'center'},
            {field:'v',title:'操作',width:100,align:'center'}
        ]]
    });

});
window.onload=function (ev) {
    topbar.hide();
    $("body").animate({opacity: 1});
};