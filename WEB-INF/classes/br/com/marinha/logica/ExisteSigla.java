package br.com.marinha.logica;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import br.com.marinha.dao.OmDAO;
import br.com.marinha.modelo.OrganizacaoMilitar;

public class ExisteSigla implements Logica{

public String executa(PortletRequest request, PortletResponse response)throws Exception{
		
		String id = request.getParameter("id");	
		String retorno = new OmDAO().existeSigla(request, id);
		
		return retorno;
		
	}
	
}
