<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Cadastro de OM</title>

<link rel="stylesheet" href="<%= response.encodeURL(request.getContextPath() + "/resources/css/modal/jquery.alerts.css")  %>" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<%= response.encodeURL(request.getContextPath() + "/resources/css/util.css")  %>" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<%= response.encodeURL(request.getContextPath() + "/resources/css/bootstrap.css")  %>" rel="stylesheet" type="text/css">


<script charset="iso-8859-1" language="javascript" type="text/javascript" src="<%= response.encodeURL(request.getContextPath()+ "/resources/js/bootstrap.js") %>"></script>
<script charset="iso-8859-1" language="javascript" type="text/javascript" src="<%= response.encodeURL(request.getContextPath()+ "/resources/js/jquery-3.6.0.min.js") %>"></script>
<script charset="iso-8859-1" language="javascript" type="text/javascript" src="<%= response.encodeURL(request.getContextPath()+ "/resources/js/util.js") %>"></script>
<script charset="iso-8859-1" language="javascript" type="text/javascript" src="<%= response.encodeURL(request.getContextPath()+ "/resources/js/modal/jquery.alerts.js") %>"></script> 
<script charset="iso-8859-1" language="javascript" type="text/javascript" src="<%= response.encodeURL(request.getContextPath()+ "/resources/js/modal/jquery.ui.draggable.js") %>"></script> 

<script>

	$(document).ready(function(){
		alternaCoresTabela();
		geraCombo('<%=request.getAttribute("nextURI") %>');	
	})

</script>

</head>
<body>

<form id="main" action="<portlet:actionURL><portlet:param name="logica" value="Cria"/></portlet:actionURL>" method="post">
<!-- TUDO-->
<div id="tudo">
<!-- GERENCIAMENTO-->

<div class="topoedicao">
<h1>Gerenciamento de OM -</h1>
<h3>cadastramento, edição e consulta de OM cadastradas</h3>
<div class="botao-criar"><a href=javascript:adiciona()>Cadastro Novo</a></div>
</div>


<div class="consultaOM">
Listagem das OM cadastradas

<div class="buscaOM">
	<c:if test="${sessionScope.paginacao == true }">
		<input type="text" value="" name="searchField" id="searchField">
		<a id="linkProcura" href="javascript:buscaOM('<portlet:actionURL></portlet:actionURL>')"><i class="icon-search"></i></a>
	</c:if>
	<c:if test="${sessionScope.paginacao == false }">
		<div onClick=limpaBuscaOM('<portlet:actionURL></portlet:actionURL>') class="limpa-resultado"><i class="icon-refresh"></i> Limpar resultado</div>
	</c:if>
</div>
</div>
</div>
<!-- LISTAGEM  MATERIAS-->

<c:import url="loading.jsp"></c:import>
<div id="texto-resultado-pesquisa"></div>

<div id="resultadoOM">

	<c:import url="paginacao.jsp"/>
	
	<!-- LISTAGEM DAS OM-->
	<table id="tabelaOM" width="100%" border="0" cellpadding="3" cellspacing="1">
	<thhead>
	<tr>
		<th class="titulo-tabela-om" id="tabela-titulo-posto">Posto Titular</th>
		<th class="titulo-tabela-om" id="tabela-titulo-codigo">Código</th>
		<th class="titulo-tabela-om" id="tabela-titulo-indicativo">Ind. Naval</th>
		<th class="titulo-tabela-om" id="tabela-titulo-sigla">Sigla</th>
		<th class="titulo-tabela-om" id="tabela-titulo-sequencia">Sequência</th>
		<th class="titulo-tabela-om" id="tabela-titulo-distrito">Distrito</th>
		<th class="titulo-tabela-om" id="tabela-titulo-descricao">Descrição</th>
		<th class="titulo-tabela-om" id="tabela-titulo-icone-editar"></th>
		<th class="titulo-tabela-om" id="tabela-titulo-icone-excluir"></th>
	</tr>
	</thhead>
	
	<tbody id="linhas-resultado">
	
		<c:forEach var="om" begin="${sessionScope.rangePaginaIni }" end="${sessionScope.rangePaginaFim }" items="${sessionScope.omLista}">
		
			<tr id="${om.sigla }">
				<td class="resultado-om" id="td-posto">${om.posto }</td>
				<td class="resultado-om" id="td-codigo">${om.codigo }</td>
				<td class="resultado-om" id="td-indicativo">${om.indicativo }</td>
				<td class="resultado-om" id="td-sigla">${om.sigla }</td>
				<td class="resultado-om" id="td-sequencia">${om.sequencia }</td>
				<td class="resultado-om" id="td-distrito">${om.distrito }</td>
				
				<c:set var="descricao" value="${om.descricao }"/>
				
				<c:choose>
				    <c:when test="${fn:length(descricao) > 50}">
				        <td class="resultado-om" id="td-descricao">${fn:substring(descricao,0,50) }...</td>        
				    </c:when>    
				    <c:otherwise>
				        <td class="resultado-om" id="td-descricao">${om.descricao }</td>  
				    </c:otherwise>
				</c:choose>	
				
				<td class="resultado-om" id="td-editar"><a href=javascript:edita("<portlet:actionURL><portlet:param name="logica" value="Edita"/><portlet:param name="sigla" value="${om.sigla }"/></portlet:actionURL>")><i class="icon-pencil"></i></a></td>
				<td class="resultado-om" id="td-excluir"><a href=javascript:remove("<%=request.getAttribute("nextURI") %>","${om.sigla }")><i class="icon-trash"></i></a></td>
			</tr>
		
		
		</c:forEach>
	
	</tbody>
	</table>
	<!-- FIM LISTAGEM DAS OM-->

	<c:import url="paginacao.jsp"/>

</div>

</form>
<!-- FIM TUDO-->

</div>

</body>

</html>

