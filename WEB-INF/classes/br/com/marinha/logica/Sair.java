package br.com.marinha.logica;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

public class Sair implements Logica{

	public String executa(PortletRequest request, PortletResponse response)throws Exception{		
		return "listagem";
	}
	
}
