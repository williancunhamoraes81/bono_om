<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<portlet:defineObjects/>
<c:import url="formulario-importa-cabecalho.jsp"></c:import>
 
<body>

<section id="formtodo">

<form id="register" name="main" method="post" action="">
<div class="formtitle">${om.descricao }</div>
<div class="formcredito">Criado por ${om.autor }<a href="#"></a></div>

<div class="botoes">
	<a href=javascript:validaCampos('<portlet:actionURL><portlet:param name="logica" value="Atualiza"/></portlet:actionURL>','0')><img src='<c:url value="/resources/img/botao_salvar.png"/>' /></a>
	<a href=javascript:sair('<portlet:actionURL><portlet:param name="logica" value="Sair"/></portlet:actionURL>')><img src='<c:url value="/resources/img/botao_fechar.png"/>' /></a>
</div>

  <fieldset> 
    <legend>Cadastro de OM</legend>
    <div class="status">Criado em: <fmt:formatDate value="${om.data}" pattern="dd/MM/yyyy hh:mm" /></div>
    <br>
     
      <c:import url="formulario-import-label.jsp"></c:import>
        
      <div>        
        <input required id="descricao" class="campoForm" name="descricao" type="text" required size="100" value="${om.descricao }">
      </div>
        
      <div>        
        <input readonly id="sigla" class="campoForm" name="sigla" type="text" required value="${om.sigla }">
      </div>
      
      <div> 
        <input required id="codigo" class="campoForm" name="codigo" type="text" required value="${om.codigo }">
      </div>
    
      <div> 
	    <input required id="indicativo" class="campoForm" name="indicativo" type="text" required value="${om.indicativo }">
      </div>
      
      <div>
        <input id="posto" class="campoForm" name="posto" type="text" required value="${om.posto }">   
      </div>
      
      <div>
         <select class="campoForm" name="distrito" id="distrito">
       		<option value="-">::::::::::::::::::selecione::::::::::::::::::</option>
       		
       		<c:forEach var="categoria" items="${sessionScope.listaCategoriaDN}">
	       		<c:if test="${om.distrito == categoria.nomeCategoria }">
	       			<option selected value="${categoria.nomeCategoria }">${categoria.nomeCategoria } - ${categoria.tituloCategoria }</option>
				</c:if>
	       		
				<c:if test="${om.distrito != categoria.nomeCategoria }">
	       			<option value="${categoria.nomeCategoria }">${categoria.nomeCategoria } - ${categoria.tituloCategoria }</option>
				</c:if>
       		</c:forEach>	
		</select>   
      </div>
      
      <div style="padding-top: 11px;">
        <c:set var="genero" value="${om.genero }"/>
        <input required type="radio" name="genero" value="M" <c:if test="${genero == 'M'}"> checked </c:if> >Masculino<input type="radio" name="genero" value="F" <c:if test="${genero == 'F'}"> checked </c:if> >Feminino
      </div>
      
      <div style="padding-top: 11px;">
        <input required id="sequencia" class="campoForm" name="sequencia" type="text" required value="${om.sequencia }" size="10">   
      </div>
    
    <div>
     
</fieldset>
</form> 

</section>

</body>
</html>
