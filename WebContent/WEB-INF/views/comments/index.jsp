<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        コメント一覧　ページ
        <p><a href="<c:url value="/comments/edit" />">コメントを編集する</a></p>
        <p><a href="<c:url value="/reports/index" />">日報詳細ページに戻る</a></p>
    </c:param>
</c:import>