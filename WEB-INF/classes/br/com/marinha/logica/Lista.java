package br.com.marinha.logica;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import br.com.marinha.dao.OmDAO;
import br.com.marinha.modelo.OrganizacaoMilitar;
import br.com.marinha.xml.XML;

public class Lista implements Logica{
	
	public String executa(PortletRequest request, PortletResponse response)throws Exception{
				
		try{		
			//Gera Lista
			PortletSession session 	= request.getPortletSession();			
			List<OrganizacaoMilitar> omLista = new OmDAO().lista(request, request.getParameter("valorBusca"));	
			
			//Realiza Ordenação
			OrganizacaoMilitar compara = new OrganizacaoMilitar();
			Collections.sort(omLista,compara); 
									
			session.setAttribute("omLista", omLista, PortletSession.APPLICATION_SCOPE);
			
		}catch(Exception e){			
			throw new RuntimeException(e);
		}
		return "lista";
				
	}
	
}
