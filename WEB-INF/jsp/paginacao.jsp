<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatNumber var="atual" value="${sessionScope.paginaCorrente}" type="number"/>

<c:if test="${sessionScope.paginacao == true }">
<div class="pagination pagination-small pagination-centered">
<ul>
	
	<c:forEach var="i" begin="0" end="${sessionScope.qtdeTotalDocs }" varStatus="status">
	
		<c:choose>
			<c:when test="${i == atual}">
				<li class="active"><a href=javascript:paginacao("<portlet:actionURL><portlet:param name="paginaCorrente" value="${i }"/><portlet:param name="logica" value="Lista"/><portlet:param name="paginaIni" value="${i * 19}"/><portlet:param name="paginaFim" value="${i * 19 + 19}"/></portlet:actionURL>")><c:out value="${i + 1}"/></a></li>
			</c:when>
			<c:otherwise>				
				<li><a href=javascript:paginacao("<portlet:actionURL><portlet:param name="paginaCorrente" value="${i }"/><portlet:param name="logica" value="Lista"/><portlet:param name="paginaIni" value="${i * 20}"/><portlet:param name="paginaFim" value="${(i * 20) + 19}"/></portlet:actionURL>")><c:out value="${i + 1}"/></a></li>
			</c:otherwise>
		</c:choose>
		
	</c:forEach>
</ul>
</div>
</c:if>