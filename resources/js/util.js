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
    if(searchValue == ""){jAlert('Favor preencher o campo de busca.', 'Atenção'); return parent.focus(); }
    
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
			jAlert("Não foi possível validar o formulário.", 'Atenção');			
		}
	});
}

function sair(action){	
	jConfirm('Deseja realmente sair?', 'Atenção', function(r) {
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
	
	jConfirm('Deseja realmente excluir a OM ' + id + '?', 'Atenção', function(r) {
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
						jAlert("Não foi possível remover a categoria selecionada.","Atenção");
					}
			 });
		};
	});
	
}

function validaCampos(action,qtdeSigla){
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Parâmetros
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//action 	- utilizado para direcionar ação do portlet
	//qtdeSigla - utilizado para verificação se já existe sigla cadastrada.
	//			- =0; não existe sigla;
	//			- >0; existe sigla;
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	if($("#descricao").val() == ""){		
		jAlert('Favor preencher o campo "Descrição".', 'Atenção');	
		$("#descricao").focus();	
	}else if($("#sigla").val() == ""){
		jAlert("Favor preencher o campo 'Sigla'.", 'Atenção');
		$("#sigla").focus();
	}else if(qtdeSigla > 0){
		jAlert("Já existe um OM com a sigla " + $("#sigla").val().toUpperCase() + ".", 'Atenção');
		$("#sigla").focus();
	}else if($("#codigo").val() == ""){
		jAlert("Favor preencher o campo 'Código'.", 'Atenção');
		$("#codigo").focus();	
	}else if($("#indicativo").val() == ""){
		jAlert("Favor preencher o campo 'Naval'.", 'Atenção');
		$("#indicativo").focus();
	}else if($("#indicativo").val().length != 6){
		jAlert("Favor preencher o campo 'Ind. Naval' com 6 caracteres.", 'Atenção');
		$("#indicativo").focus();	
	}else if(jQuery("input[name=genero]:checked").val() == undefined){
		jAlert("Favor preencher o campo 'Gênero'.", 'Atenção');		
	}else if($("#sequencia").val() == ""){
		jAlert("Favor preencher o campo 'Sequência'.", 'Atenção');
		$("#sequencia").focus();
	}else if( $.isNumeric( $('#sequencia').val()) == false ){
		jAlert("Favor preencher o campo 'Sequência' com valores numéricos.", 'Atenção');
		$("#sequencia").focus();
	}else if(validaCampoSequencia()){
		$("#sequencia").focus();
	}else{
		
		jConfirm('Deseja realmente salvar o documento?', 'Atenção', function(r) {
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
			jAlert('Favor preencher o campo "Sequência" no formato "00.000".', 'Atenção');		
			return true;
		}else if(elems[1].length != 3){
			jAlert('Favor preencher o campo "Sequência" no formato "00.000" utilizando duas casas decimais.', 'Atenção');
			return true;
		}
	}else{
		jAlert('Favor preencher o campo "Sequência" no formato "00.000" utilizando pontuação.', 'Atenção');			
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