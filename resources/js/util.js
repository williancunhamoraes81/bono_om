function removeAncoraPortlet(action){
	var i = action.indexOf("#");
	if(i > -1){
		var valorAction = action.split("#");
		action = valorAction[0];
	}
	return action;
}

function geraCombo(url){
	try{
		$.ajax({
			type: "GET",
			url: url + "?logica=Combo",
			async: true,
			dataType: "xml",
			success: function (xml) {
				$('.botao-criar').fadeIn(800);				
			},
			error: function(){			
			}
		});
	}catch(err){
		alternaCoresTabela();		
	}	
}

function buscaOM(action){
	
	var searchValue = $("#searchField").val();
    if(searchValue == ""){jAlert('Favor preencher o campo de busca.', 'Aten��o'); return parent.focus(); }
    
    action = removeAncoraPortlet(action);
    
    $('#main').attr('action',action + "?logica=Lista&valorBusca=" + searchValue);
	$('#main').submit();
   
}

function limpaBuscaOM(action){  
  
	action = removeAncoraPortlet(action);
	$('#main').attr('action',action + "?logica=Lista&paginaIni=0&paginaFim=19&pagina=1");
	$('#main').submit();
}

function toTitleCase(toTransform) {
	  return toTransform.replace(/\b([a-z])/g, function (_, initial) {
	      return initial.toUpperCase();
	  });
}

function alternaCoresTabela(){
	try{
		$('#tabelaOM').find('tr').each(function(index){
			 if(index % 2 == 0){
			   $(this).css('background-color','#ccc');
			 }else{
			    $(this).css('background-color','#fff');
			 }	  
		});
	}catch(err){}
	$('#loader').css('display','none');
	$('#resultadoOM').fadeIn(800);	
}

function salva(action,url){
	
	$.ajax({
		type: "GET",
		url: url + "?logica=ExisteSigla&id=" + $("#sigla").val(),
		async: true,
		dataType: "html",
		success: function (html) { 
			validaCampos(action,html);
		},
		error: function(){	
			jAlert("N�o foi poss�vel validar o formul�rio.", 'Aten��o');			
		}
	});
}

function sair(action){	
	jConfirm('Deseja realmente sair?', 'Aten��o', function(r) {
		if (r == true){	
			$('#register').attr('action',action);
			$('#register').submit();
		}
	});
	
}

function adiciona(){
	$('#main').submit();
}

function edita(action){
	$('#main').attr('action',action);
	$('#main').submit();
}

function paginacao(action){	
	action = removeAncoraPortlet(action);
	$('#main').attr('action',action);
	$('#main').submit();
}

function remove(url,id){
	
	jConfirm('Deseja realmente excluir a OM ' + id + '?', 'Aten��o', function(r) {
		if (r == true){	
			 $.ajax({
					type: "GET",
					url: url + "?logica=Remove&id=" + id,
					async: true,
					dataType: "html",
					success: function (html) { 
						$('#' + id).fadeOut('800',function(){
							 $('#' + id).remove();
							 alternaCoresTabela();
						});
					},
					error: function(){
						jAlert("N�o foi poss�vel remover a categoria selecionada.","Aten��o");
					}
			 });
		};
	});
	
}

function validaCampos(action,qtdeSigla){
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Par�metros
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//action 	- utilizado para direcionar a��o do portlet
	//qtdeSigla - utilizado para verifica��o se j� existe sigla cadastrada.
	//			- =0; n�o existe sigla;
	//			- >0; existe sigla;
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	if($("#descricao").val() == ""){		
		jAlert('Favor preencher o campo "Descri��o".', 'Aten��o');	
		$("#descricao").focus();	
	}else if($("#sigla").val() == ""){
		jAlert("Favor preencher o campo 'Sigla'.", 'Aten��o');
		$("#sigla").focus();
	}else if(qtdeSigla > 0){
		jAlert("J� existe um OM com a sigla " + $("#sigla").val().toUpperCase() + ".", 'Aten��o');
		$("#sigla").focus();
	}else if($("#codigo").val() == ""){
		jAlert("Favor preencher o campo 'C�digo'.", 'Aten��o');
		$("#codigo").focus();	
	}else if($("#indicativo").val() == ""){
		jAlert("Favor preencher o campo 'Naval'.", 'Aten��o');
		$("#indicativo").focus();
	}else if($("#indicativo").val().length != 6){
		jAlert("Favor preencher o campo 'Ind. Naval' com 6 caracteres.", 'Aten��o');
		$("#indicativo").focus();	
	}else if(jQuery("input[name=genero]:checked").val() == undefined){
		jAlert("Favor preencher o campo 'G�nero'.", 'Aten��o');		
	}else if($("#sequencia").val() == ""){
		jAlert("Favor preencher o campo 'Sequ�ncia'.", 'Aten��o');
		$("#sequencia").focus();
	}else if( $.isNumeric( $('#sequencia').val()) == false ){
		jAlert("Favor preencher o campo 'Sequ�ncia' com valores num�ricos.", 'Aten��o');
		$("#sequencia").focus();
	}else if(validaCampoSequencia()){
		$("#sequencia").focus();
	}else{
		
		jConfirm('Deseja realmente salvar o documento?', 'Aten��o', function(r) {
			if (r == true){	
				$('#register').attr('action',action);
				$('#register').submit();
			}
		});
		
	}		
}

function validaCampoSequencia(){
	
	var campo = $('#sequencia').val();
	var x = campo.indexOf('.');
	
	if(x > -1){
		var elems = campo.split('.');
		if(elems[0].length != 2){
			jAlert('Favor preencher o campo "Sequ�ncia" no formato "00.000".', 'Aten��o');		
			return true;
		}else if(elems[1].length != 3){
			jAlert('Favor preencher o campo "Sequ�ncia" no formato "00.000" utilizando duas casas decimais.', 'Aten��o');
			return true;
		}
	}else{
		jAlert('Favor preencher o campo "Sequ�ncia" no formato "00.000" utilizando pontua��o.', 'Aten��o');			
		return true;
	}
	return false;
}

function abreAjuda(valor,top,left){	
	$("#" + valor).css('width','350px');
	$("#" + valor).css('top',(top-20) + 'px');
	$("#" + valor).css('left',(left + 15) + 'px');
	$("#" + valor).css('display','block');
}

function fechaAjuda(valor){	
	$("#" + valor).css('display','none');
}