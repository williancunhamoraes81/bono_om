<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="formlabels"> 
	<label style="padding: 3px 0 0 0">Descri��o:&nbsp;</label>
	<label style="padding: 3px 0 0 0">Sigla:&nbsp;</label>
	<label style="padding: 3px 0 0 0">C�digo:</label>
	<label style="padding: 3px 0 0 0">Ind. Naval: </label>
	<label>Posto Titular:</label>
	<label style="padding: 3px 0 0 0">Distrito: </label>
	<div id="ajuda">Utilizado no momento da visualiza��o do BONO, identificando se deve ser utilizado "do" ou "da" antes no nome da OM referida.</div>
	<label>G�nero: <img onMouseOver=abreAjuda('ajuda',$(this).offset().top,$(this).offset().left) onMouseOut=fechaAjuda('ajuda') width="14px" src='<c:url value="/resources/img/ajuda.png"/>' /></label>
	<label>Sequ�ncia:</label>
</div> 