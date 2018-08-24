
$.extend($.fn.treegrid.methods, {
    /**
     * 级联选择
     * @param {Object} target
     * @param {Object} param
     *      param包括两个参数:
     *          id:勾选的节点ID
     *          deepCascade:是否深度级联
     * @return {TypeName}
     */
    cascadeCheck: function (target, param) {
        var opts = $.data(target[0], "treegrid").options;
        if (opts.singleSelect)
            return;
        var idField = opts.idField;//这里的idField其实就是API里方法的id参数
        var status = false;//用来标记当前节点的状态，true:勾选，false:未勾选
        var selectNodes = $(target).treegrid('getSelections');//获取当前选中项
        for (var i = 0; i < selectNodes.length; i++) {
            if (selectNodes[i][idField] == param.id)
                status = true;
        }

        selectChildren(target[0], param.id, idField, param.deepCascade, status);

        /**
         * 级联选择子节点
         * @param {Object} target
         * @param {Object} id 节点ID
         * @param {Object} deepCascade 是否深度级联
         * @param {Object} status 节点状态，true:勾选，false:未勾选
         * @return {TypeName}
         */
        function selectChildren(target, id, idField, deepCascade, status) {
            //深度级联时先展开节点
            if (!status && deepCascade)
                $(target).treegrid('expand', id);
            //根据ID获取下层孩子节点
            var children = $(target).treegrid('getChildren', id);
            for (var i = 0; i < children.length; i++) {
                var childId = children[i][idField];
                if (status)
                    $(target).treegrid('select', childId);
                else
                    $(target).treegrid('unselect', childId);
                selectChildren(target, childId, idField, deepCascade, status);//递归选择子节点
            }
        }
    }
});

$(document).ready(function () {

    $('#tt').treegrid({
        height:$(window).height()-100,
        url:'/navigation/role/'+$('#roleId').val(),
        idField: 'id',
        treeField: 'functionName',
        fitColumns:true,
        singleSelect: false,//需设置
        emptyMsg: '<span class="no-record"></span>',
        columns: [[
            {title: '菜单名称', field: 'functionName', width: 180}
        ]],
        toolbar:toolbar,
        onClickRow: function (row) {
            //级联选择
            $(this).treegrid('cascadeCheck', {
                id: row.id, //节点ID
                deepCascade: true //深度级联
            });
        }
    });


});



var toolbar = ['-',{
    text: '解除绑定',
    handler: function () {
        checkSelect();
    }
},'-',{
    text: '重新绑定',
    handler: function () {
        window.location.href="/bind/toBind/"+$('#roleId').val();
    }
},'-'];

function checkSelect() {
    var sourceRow = $('#tt').datagrid('getSelections');

    if(sourceRow==null||sourceRow.length===0){
        layer.msg("请选择需要操作的功能");
        return;
    }
    var msg="";
    var ids=[];
    for(var i=0;i<sourceRow.length;i++){
        ids.push(sourceRow[i].navId);
        msg+=sourceRow[i].functionName+',';
    }
    $.confirm({
        title: '请确认!',
        content: '解除以下功能绑定：<br/>'+msg,
        confirm: function(){
            $.ajax({
                type: "GET",
                url: "/bind/relieveBind",
                data: {
                    relationIds:""+ids
                },
                dataType: "json",
                success: function (data) {
                    if(data.errorCode===200){
                        layer.msg("操作成功");
                        $("#tt").treegrid("reload");
                    }else {
                        layer.msg("操作失败");
                    }
                },
                beforeSend: function () {
                }
            });
        },
        cancel: function(){
        }
    });

    console.log(sourceRow);
}
window.onload=function (ev) {
    $(".mask").fadeOut(300);
    $("body").animate({opacity: 1});
}