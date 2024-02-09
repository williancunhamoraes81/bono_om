<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects/>
<c:import url="formulario-importa-cabecalho.jsp"></c:import>


<body>

<section id="formtodo">
<form id="register" name="main" method="post" action="">

<div class="botoes">
	<a href=javascript:salva('<portlet:actionURL><portlet:param name="logica" value="Adiciona"/></portlet:actionURL>','<%=request.getAttribute("nextURI") %>')><img src='<c:url value="/resources/img/botao_salvar.png"/>' /></a>
	<a href=javascript:sair('<portlet:actionURL><portlet:param name="logica" value="Sair"/></portlet:actionURL>')><img src='<c:url value="/resources/img/botao_fechar.png"/>' /></a>
</div>

  <fieldset>
    <legend>Cadastro de OM</legend>
    <br>
     
     <c:import url="formulario-import-label.jsp"></c:import>
        
      <div>        
        <input id="descricao" class="campoForm" name="descricao" type="text" required size="100" value="">
      </div>
        
      <div>        
        <input id="sigla" class="campoForm" name="sigla" type="text" required value="">
      </div>
      
      <div> 
        <input id="codigo" class="campoForm" name="codigo" type="text" required value="">
      </div>
    
      <div> 
	    <input id="indicativo" class="campoForm" name="indicativo" type="text" required value="">
      </div>
      
      <div>
        <input id="posto" class="campoForm" name="posto" type="text" required value="">   
      </div>
      
      <div>
       <select class="campoForm" name="distrito" id="distrito">
       		<option value="-">::::::::::::::::::selecione::::::::::::::::::</option>
			<c:forEach var="categoria" items="${sessionScope.listaCategoriaDN}">
				<option value="${categoria.nomeCategoria }">${categoria.nomeCategoria } - ${categoria.tituloCategoria }</option>
			</c:forEach>	
		</select>	  
      </div>
      
      <div style="padding-top: 11px;">
        <input required type="radio" name="genero" value="M" >Masculino<input type="radio" name="genero" value="F" >Feminino
      </div>
      
      <div style="padding-top: 11px;">
        <input required id="sequencia" class="campoForm" name="sequencia" type="text" required value="" size="10">   
      </div>
    
    <div>
     
</fieldset>
</section>

</body>
</html>
