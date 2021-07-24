<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
      <h2>コメント一覧　ページ</h2>
        <form method="POST"
            action="<c:url value='/comments/index' />">
            <c:import url="_formCommentsIndex.jsp" />
        </form>

        <p>
            <a href="<c:url value="/reports/index" />">日報詳細ページに戻る</a>
        </p>
    </c:param>
</c:import>