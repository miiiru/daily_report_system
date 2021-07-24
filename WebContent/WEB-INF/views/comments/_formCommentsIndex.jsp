<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" />
            <br />
        </c:forEach>

    </div>
</c:if>
<table>
    <tbody>
        <tr>
            <th>氏名</th>
            <td><c:out value="${report.employee.name}" /></td>
        </tr>
        <tr>
            <th>日付</th>
            <td><fmt:formatDate value="${report.report_date}"
                    pattern="yyyy-MM-dd" /></td>
        </tr>
        <tr>
            <th>内容</th>
            <td><pre><c:out value="${report.content}" /></pre></td>
        </tr>
    </tbody>
</table>
<br>
<c:if test = "${fn:length(comments) gt 0}" >
    <c:out value="コメント" />
</c:if>
<c:forEach var="comment" items="${comments}" varStatus="status">
    <table id="comment_list">
        <tr>
            <th class="comment_name">氏名</th>
            <!-- 以下のcommentはvar="comment"にリンク -->
            <td><c:out value="${comment.employee.name}" /></td>
        </tr>
        <tr>
            <th class="comment_content">コメント内容</th>
            <td><pre><c:out value="${comment.content}" /></pre></td>
        </tr>
        <c:if test="${sessionScope.login_employee.id == comment.employee.id}">
            <tr>
                <th class="comment_action">更新</th>
                <td><a href="<c:url value="/comments/edit?id=${comment.id}" />">コメントを編集する</a>
                </td>
            </tr>
        </c:if>
    </table>
    <br>
</c:forEach>
