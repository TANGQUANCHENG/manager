window.onload=function (ev) {
    $("body").animate({opacity: 1});
};
/**
 * 扩展树表格级联勾选方法：
 * @param {Object} container
 * @param {Object} options
 * @return {TypeName}
 */
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
        //级联选择父节点
        selectParent(target[0], param.id, idField, status);
        selectChildren(target[0], param.id, idField, param.deepCascade, status);
        /**
         * 级联选择父节点
         * @param {Object} target
         * @param {Object} id 节点ID
         * @param {Object} status 节点状态，true:勾选，false:未勾选
         * @return {TypeName}
         */
        function selectParent(target, id, idField, status) {
            var parent = $(target).treegrid('getParent', id);
            var allStatus = false;
            if (parent) {
                var rows = $('#easyTable').datagrid('getSelections');//获取所有选中行
                var parentId = parent[idField];
                if (status)
                    $(target).treegrid('select', parentId);
                else {
                    var children = $(target).treegrid('getChildren', parentId);
                    for (var i = 0; i < children.length; i++) {
                        //判断是否在选中的行中
                        for (var j = 0; j < rows.length; j++) {
                            if (children[i].id == rows[j].id) {
                                allStatus = true;
                            }
                        }
                    }
                    /*如果取消选择的节点的父节点还存在选中的节点，该父节点不会被取消选中*/
                    if (allStatus) {
                    } else {
                        $(target).treegrid('unselect', parentId);
                    }
                }

                selectParent(target, parentId, idField, status);
            }
        }

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

    $('#easyTable').treegrid({
        height: $(window).height() - 100,
        url: '/bind/getData/' + $('#roleId').val(),
        idField: 'id',
        treeField: 'functionName',
        fitColumns: true,
        singleSelect: false,//需设置
        emptyMsg: '<span class="no-record"></span>',
        columns: [[
            {title: '菜单名称', field: 'functionName', width: 180}
        ]],
        toolbar: toolbar,
        onClickRow: function (row) {
            //级联选择
            $(this).treegrid('cascadeCheck', {
                id: row.id, //节点ID
                deepCascade: true //深度级联
            });
        },
        onLoadSuccess: function (rows, data) {
            var list = data.rows;
            for (var i=0;i< list.length;i++) {
                if (list[i].selected) {
                    $('#easyTable').treegrid('select', list[i].id);
                }
            }
        }
    });
});
var toolbar = ['-', {
    text: '绑定',
    handler: function () {
        handleBind();
    }
}, '-'];

function handleBind() {
    var sourceRow = $('#easyTable').datagrid('getSelections');
    if (sourceRow == null || sourceRow === "") {
        layer.msg("请至少选择一种功能!");
        return;
    }
    var ids=[];
    for(var i=0;i<sourceRow.length;i++){
        ids.push(sourceRow[i].id);
    }
    $.confirm({
        title: '请确认!',
        content: '此操作将重置该角色的权限配置，是否继续？',
        confirm: function(){
            $.ajax({
                type: "GET",
                url: "/bind/addDutyNavBinding",
                data: {
                    navs:""+ids,
                    roles:$('#roleId').val()
                },
                dataType: "json",
                success: function (data) {
                    if(data.errorCode===200){
                        layer.msg("操作成功!");
                        $("#tt").treegrid("reload");
                    }else {
                        layer.msg("操作失败!");
                    }
                },
                beforeSend: function () {
                }
            });
        },
        cancel: function(){
        }
    });
}