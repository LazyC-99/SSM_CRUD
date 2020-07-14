<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/4
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工列表</title>
    <%
        request.setAttribute("path",request.getContextPath());
    %>
    <script type="text/javascript" src="${path}/static/js/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" type="text/css"  href="${path}/static/bootstrap-3.3.7-dist/css/bootstrap.css">
    <script TYPE="text/javascript" src="${path}/static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>
    <div class="container">
        <!--标题-->
        <div class="row">
                <div class="col-md-12">
                        <h1>SSM-CRUD</h1>
                </div>
        </div>
        <!--按钮 -->
        <div class="row">
                <div class="col-md-4 col-md-offset-8">
                    <button class="btn btn-primary">新增</button>
                    <button class="btn btn-danger">删除</button>
                </div>
        </div>
        <!--查询信息 -->
        <div class="row">
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="emp" >
                        <tr>
                            <td>${emp.empId}</td>
                            <td>${emp.empName}</td>
                            <td>${emp.gender}</td>
                            <td>${emp.email}</td>
                            <td>${emp.department.deptName}</td>
                            <td>
                                <button class="btn btn-primary btn-sm" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"/> 编辑</button>
                                <button class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"/>删除</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <!--分页信息 -->
        <div class="row">
            <!--分页文字信息 -->
            <div class="col-md-6">
                当前${pageInfo.pageNum}页,总${pageInfo.pages}页,总${pageInfo.total}条记录
            </div>
            <!--分页条信息 -->
            <div class="col-md-6">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li><a href="${path}/emp?pn=1">首页</a> </li>
                        <c:if test="${pageInfo.hasPreviousPage}">
                            <li>
                                <a href="${path}/emp?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>

                        <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                            <c:if test="${page_Num==pageInfo.pageNum}"><li class="active"><a href="${path}/emp?pn=${page_Num}">${page_Num}</a></li></c:if>
                            <c:if test="${page_Num!=pageInfo.pageNum}"><li><a href="${path}/emp?pn=${page_Num}">${page_Num}</a></li></c:if>
                        </c:forEach>

                        <c:if test="${pageInfo.hasNextPage}">
                            <li>
                                <a href="${path}/emp?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>
                        <li><a href="${path}/emp?pn=${pageInfo.pages}">末页</a> </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
