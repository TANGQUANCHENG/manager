window.onload=function (ev) {
    topbar.hide();
    $("body").animate({opacity: 1});
};
$("#addForm").validate({
    rules: {
        functionName: {
            required: true,
            minlength: 2
        }
    }
});
$("#editForm").validate({
    rules: {
        functionName: {
            required: true,
            minlength: 2
        }
    }
});
$("#linkForm").validate({
    rules: {
        functionName: {
            required: true,
            minlength: 2
        }
    }
});
var navigationLayer = {
    "MENU": "<span class=\"label label-primary plain\">菜单</span>",
    "LINK": "<span class=\"label label-info plain\">功能</span>"
};

$('#dataTable').treegrid({
    height: $(window).height() - 100,
    url: "navigation/list",
    emptyMsg: '<span class="no-record"></span>',
    idField: 'id',
    treeField: 'functionName',
    fitColumns: true,
    columns: [[
        {title: '菜单名称', field: 'functionName', width: 60},
        {
            title: '类型', field: 'type',align:'center', width: 30, formatter: function (value, row) {
                return navigationLayer[value];
            }
        },
        {title: '地址', field: 'url', width: 80,align:'center'},
        {title: '排序', field: 'sort', width: 20,align:'center'},
        {
            title: '状态',align:'center', field: 'available', width: 30, formatter: function (value, row) {
                return value ? '<span class="label label-success" onclick="disableNav('+row.id+')">正常</span>' :
                    '<span class="label label-danger" onclick="enableNav('+row.id+')">停用</span>'
            }
        },
        {title: '创建时间', field: 'createTime', width: 50,align:'center'},
        {title: 'icon', field: 'icon', width: 40,align:'center'},
        {title: '说明', field: 'comment', width: 40,align:'center'},
        {
            title: '操作', field: 'comment2', width: 80, formatter: function (value, row) {

                var str='<a href="javascript:void(0)" class="label plain label-primary" onclick="toUpdate(' + row.id + ')">修改</a>&nbsp;' +
                    '<a href="javascript:void(0)"  class="label plain label-danger" onclick="deleteMenu(' + row.id + ')">删除</a>&nbsp;';
                if(row._parentId!=null && row.type=='MENU'){
                    str+='<a href="javascript:void(0)"  class="label plain label-success" onclick="toAddLink(' + row.id + ')">增加功能</a>'
                }
                return str;
            }
        }
    ]]
});


$("#toAddMenu").click(function () {

    $("#addForm").form("clear");
    $("#addMenuForm").val("MENU");
    $("#myModal").modal("show");
});

$("#addMenu").click(function () {

    var v = $("#addForm").valid();
    if (!v) {
        return;
    }

    $.ajax({
        type: "GET",
        url: "/navigation/add",
        data: $("#addForm").serialize(),
        dataType: "json",
        success: function (data) {
            if (data.errorCode === 200) {

                layer.msg("新增成功");
                $("#dataTable").treegrid("reload");
                reloadParents();
                $("#myModal").modal("hide");
            } else {
                layer.msg("新增失败");
            }
        },
        beforeSend: function () {
        }
    });
});
$("#updateMenu").click(function () {
    var v = $("#editForm").valid();
    if (!v) {
        return;
    }

    $.ajax({
        type: "GET",
        url: "/navigation/update",
        data: $("#editForm").serialize(),
        dataType: "json",
        success: function (data) {
            if (data.errorCode === 200) {
                layer.msg("修改成功");
                $("#dataTable").treegrid("reload");
                reloadParents();
                $("#myModal2").modal("hide");
            } else {
                layer.msg("修改失败");
            }
        },
        beforeSend: function () {
        }
    });
});

$("#addLink").click(function () {
    var v = $("#addForm").valid();
    if (!v) {
        return;
    }
    $.ajax({
        type: "GET",
        url: "/navigation/add",
        data: $("#linkForm").serialize(),
        dataType: "json",
        success: function (data) {
            if (data.errorCode === 200) {
                layer.msg("新增成功");
                $("#dataTable").treegrid("reload");
                $("#addLinkModal").modal("hide");
            } else {
                layer.msg("新增失败");
            }
        },
        beforeSend: function () {
        }
    });


});

function reloadParents() {
    $.ajax({
        type: "GET",
        url: "/navigation/queryParents",
        data: {},
        dataType: "json",
        success: function (data) {
            if(data.errorCode===200){
                data=data.data;
            }
            var str = " <option value=\"\">无</option>";
            var menus = data;
            for (var i = 0; i < menus.length; i++) {
                str += "<option value='" + menus[i].id + "'>" + menus[i].functionName + "</option>"
            }
            $("#addParentId").html(str);
        },
        beforeSend: function () {
        }
    });
}


function deleteMenu(id) {

    $.confirm({
        title: '确认!',
        content: '确定要删除这条数据吗？',
        confirm: function () {

            $.ajax({
                type: "GET",
                url: "/navigation/delete/" + id,
                data: {},
                dataType: "json",
                success: function (data) {
                    if (data.errorCode === 200) {
                        layer.msg("删除成功");
                        $("#dataTable").treegrid("reload");
                    } else {
                        layer.msg("删除失败");
                    }
                },
                beforeSend: function () {
                }
            });


        },
        cancel: function () {

        }
    });

}

function toUpdate(id) {

    var row = $("#dataTable").treegrid("find", id);
    console.log(row);
    $("#editForm").form("load", row);
    $("#myModal2").modal("show");
}
function toAddLink(id) {
    var row = $("#dataTable").treegrid("find", id);

    $("#linkForm").form("clear");

    $("#linkType").val("LINK");
    $("#linkParentId").val(row.id);
    $("#menuFunctionName").val(row.functionName);
    $("#addLinkModal").modal("show");
}
function disableNav(id) {
    $.confirm({
        title: '确认!',
        content: '确定要停用这个功能吗？',
        confirm: function () {
            $.ajax({
                type: "GET",
                url: "/navigation/updateStatus/" + id,
                data: {
                    available:false
                },
                dataType: "json",
                success: function (data) {
                    if (data.errorCode === 200) {
                        layer.msg("操作成功");
                        $("#dataTable").treegrid("reload");
                    } else {
                        layer.msg( '操作失败!'+data.errorMsg);
                    }
                },
                beforeSend: function () {
                }
            });
        },
        cancel: function () {

        }
    });
}
function enableNav(id) {
    $.confirm({
        title: '确认!',
        content: '确定要启用这个功能吗？',
        confirm: function () {

            $.ajax({
                type: "GET",
                url: "/navigation/updateStatus/" + id,
                data: {
                    available:true
                },
                dataType: "json",
                success: function (data) {
                    if (data.errorCode === 200) {
                        layer.msg("操作成功");
                        $("#dataTable").treegrid("reload");
                    } else {
                        layer.msg('操作失败!'+data.errorMsg);
                    }
                },
                beforeSend: function () {
                }
            });


        },
        cancel: function () {

        }
    });
}