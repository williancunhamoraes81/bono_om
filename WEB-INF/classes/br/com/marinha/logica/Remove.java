package br.com.marinha.logica;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

public class Remove implements Logica{
  
	public String executa(PortletRequest request, PortletResponse response)throws Exception {
		
		PortletSession session = request.getPortletSession();
		String id = request.getParameter("id");	
				
		try{			
			new br.com.marinha.dao.OmDAO().remove(request, id);
						
		}catch(Exception e){		
			throw new RuntimeException(e);
		}
		return ""; 
	}
	
}
