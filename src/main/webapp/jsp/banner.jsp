<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">轮播图管理</a>
        </div>
    </div>
</nav>
<ul class="nav nav-tabs">
    <li class="active">
        <a href="#">轮播图信息</a>
    </li>
</ul>
<script>
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/banner/showAllBanner",
                datatype: "json",
                height: 450,
                colNames: ['ID', '标题', '图片', '超链接', '创建时间', '描述', '状态'],
                colModel: [
                    {name: 'id', hidden: true},
                    {name: 'title', align: "center", editable: true, editrules: {required: true}},
                    {
                        name: 'url', align: "center", formatter: function (data) {
                            return "<img style='width: 200px'  src='" + data + "'/>"
                        }, editable: true, edittype: "file", editoptions: {enctype: "multipart/from-data"}
                    },
                    {name: 'href', align: "center", editable: true, editrules: {required: true}},
                    {name: 'create_date', align: "center"},
                    {name: 'intro', align: "center", editable: true, editrules: {required: true}},
                    {
                        name: 'status', align: "center", formatter: function (data) {
                            if (data == "1") {
                                return "展示";
                            } else return "冻结";
                        }, editable: true, edittype: "select", editoptions: {value: "1:展示;2:冻结"}
                    }
                ],
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#bannerPage',
                mtype: "post",
                viewrecords: true,
                styleUI: "Bootstrap",
                autowidth: true,
                multiselect: true,
                editurl: "${pageContext.request.contextPath}/banner/edit"
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage',
            {
                edit: true, add: true, del: true,
                edittext: "修改", addtext: "添加", deltext: "删除"
            },
            {
                closeAfterEdit:true,
                beforeShowForm: function (frm) {
                    frm.find("#url").attr("disabled", true)
                }
            }, {
                closeAfterAdd:true,
                afterSubmit: function (response,postData) {
                    var bannerID = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        datatype: "json",
                        type: "post",
                        data: {bannerId: bannerID},
                        fileElementId: "url",
                        success: function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            }, {
                closeAfterDel: true,
            });
    });
</script>
<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">轮播图列表</a></li>
        <li role="presentation" ><a href="${pageContext.request.contextPath}/banner/exportBanner">导出轮播图信息</a></li>
        <li><a onclick="importBannerExcel()">导入轮播图信息</a></li>
        <%--<li role="presentation"><a href="#profile" onclick="outBanner()" aria-controls="profile" role="tab" data-toggle="tab">导出轮播图信息</a></li>--%>
    </ul>

</div>
<div>
    <table id="bannerTable"></table>
    <div id="bannerPage" style="height: 50px"></div>
</div>
<script>
    // 点击导入BannerExcel时触发事件
    function importBannerExcel() {
        $("#bannerModal").modal("show");
    }
    function bannerSub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/banner/importBanner",
            type: "post",
            data: {
            },
            datatype: "json",
            fileElementId: "bannerFile",
            success: function (data) {
                //$("#articleList").trigger("reloadGrid");
                $("#bannerModal").modal("hide");
            }
        })

    }
</script>