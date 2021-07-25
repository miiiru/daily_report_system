<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <c:choose>
            <c:when test="${comment != null}">
                <h2>コメント 編集ページ</h2>
                <form method="POST" action="<c:url value='/comments/update' />">
                    <c:import url="_form.jsp" />
                </form>
                <p>
                    <a href="#" onclick="confirmDestroy();">このコメントを削除する</a>
                </p>
                <form method="POST" action="<c:url value='/comments/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                </form>
                <script>
                    function confirmDestroy() {
                        if (confirm("本当に削除してよろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p>
            <a href="<c:url value='/comments/index' />">コメント一覧に戻る</a>
        </p>
    </c:param>
</c:import>