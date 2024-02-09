package br.com.marinha.logica;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import br.com.marinha.dao.OmDAO;
import br.com.marinha.modelo.OrganizacaoMilitar;

public class Edita implements Logica{

	public String executa(PortletRequest request, PortletResponse response)throws Exception{
		
		String id = request.getParameter("sigla");
		OrganizacaoMilitar om = new OmDAO().edita(request, id);
		request.setAttribute("om",om);
		
		return "formulario-edicao";
		
	}
		
}
