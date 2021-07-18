<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<c:out value = "コメント"/>
<table>
        <tr>
            <th>氏名</th>
            <td><c:out value="${sessionScope.login_employee.name}" /></td>
        </tr>
        <tr>
            <th>コメント内容</th>
            <td><pre><c:forEach var="comments" items="${comments}" varStatus="status"><c:out value="${comments.content}" /></pre>
            </td></tr></c:forEach>

</table>
