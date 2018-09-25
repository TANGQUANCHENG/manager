window.onload=function (ev) {
    topbar.hide();
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

            if (parent != null) {

            }

            //var p2=$(target).treegrid('getParent', parent.id);

            var allStatus = false;
            if (parent) {
                var rows = $('#tt').datagrid('getSelections');//获取所有选中行
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

var toolbar = ['-',{
    text: '绑定资源',
    handler: function () {
        checkSelect();
    }
},'-'];


$(document).ready(function () {
    $('#tt').treegrid({
        height:document.body.clientHeight - 140,
        url:"bind/allNav",
        idField: 'id',
        treeField: 'functionName',
        emptyMsg: '<span class="no-record"></span>',
        fitColumns:true,
        singleSelect: false,//需设置
        columns: [[
            {title: '菜单名称', field: 'functionName', width: 180}
        ]],
        onClickRow: function (row) {
            //级联选择
            $(this).treegrid('cascadeCheck', {
                id: row.id, //节点ID
                deepCascade: true //深度级联
            });
        }
    });


    $('#dg').datagrid({
        url:'bind/allRole',
        fitColumns:true,
        rownumbers:true,
        emptyMsg: '<span class="no-record"></span>',
        checkOnSelect:true,
        columns:[[
            {field:'name',title:'名称',width:100},
            // {field:'code',title:'代码',width:100},
            // {field:'status',title:'状态',width:100},
            {field:'createTime',title:'创建时间',width:100}
        ]],
        toolbar:toolbar
    });
    $("#toBind").click(function () {
        $.ajax({
            type: "GET",
            url: "/bind/addDutyNavBinding",
            data: {
                navs:$("#navArr").val(),
                roles:$("#roleArr").val()
            },
            dataType: "json",
            success: function (data) {
                if(data.errorCode===200){
                    layer.msg("操作成功");
                    $("#menuModal").modal("hide");
                }else {
                    layer.msg("操作失败");
                }
            },
            beforeSend: function () {
            }
        });
    });
});


function checkSelect() {

    var sourceStr = [];//提交后台的资源id字符串
    var dutyStr = [];//提交后台的职务id字符串
    var sourceNames = '';//窗口显示需要绑定的名称
    var dutyNames = '';//窗口显示的职务名称字符串
    var sourceRow = $('#tt').datagrid('getSelections');
    var dutyRow = $('#dg').datagrid('getSelections');
    if (sourceRow == null || sourceRow == "") {
        layer.msg("请至少选择一种功能!");
        return;
    }
    if (dutyRow == null || dutyRow === "") {
        layer.msg("请至少选择一种角色!");
        return;
    }
    for (var i = 0; i < sourceRow.length; i++) {
        var row = sourceRow[i];
        sourceStr.push(row.id)
        sourceNames += row.functionName + ",<br/>";
    }
    for (var i = 0; i < dutyRow.length; i++) {
        var row = dutyRow[i];
        dutyStr.push(row.id)
        dutyNames +=row.name+",<br/>";
    }
    sourceNames = sourceNames.substring(0, sourceNames.length - 6);
    dutyNames = dutyNames.substring(0, dutyNames.length - 6);

    $("#navContent").html(sourceNames);
    $("#roleContent").html(dutyNames);
    $("#navArr").val(sourceStr);
    $("#roleArr").val(dutyStr);

    $("#menuModal").modal("show");
}

