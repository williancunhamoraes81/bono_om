package br.com.marinha.logica;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import br.com.marinha.dao.OmDAO;
import br.com.marinha.modelo.OrganizacaoMilitar;


public class Adiciona implements Logica{

	public String executa(PortletRequest request, PortletResponse response)throws Exception{
		
		String codigo = (String)request.getParameter("codigo");
		String descricao = (String)request.getParameter("descricao");
		String sigla = (String)request.getParameter("sigla");
		String indicativo = (String)request.getParameter("indicativo");
		String posto = (String)request.getParameter("posto");
		String genero = (String)request.getParameter("genero");
		String sequencia = (String)request.getParameter("sequencia");
		String distrito = (String)request.getParameter("distrito");
						
		OrganizacaoMilitar om = new OrganizacaoMilitar();
		om.setCodigo(codigo);
		om.setDescricao(descricao);
		om.setSigla(sigla);
		om.setIndicativo(indicativo);
		om.setPosto(posto);
		om.setGenero(genero);
		om.setSequencia(sequencia);	
		om.setDistrito(distrito);	
						
		OmDAO omDao = new OmDAO();
		omDao.cria(request, om);
								
		request.setAttribute("om",om);
		
		return "formulario-edicao";
	}
	
}
