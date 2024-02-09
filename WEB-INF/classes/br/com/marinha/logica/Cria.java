package br.com.marinha.logica;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

public class Cria implements Logica{
	public String executa(PortletRequest request, PortletResponse response)throws Exception{		
		return "formulario-criacao";
	}
}
