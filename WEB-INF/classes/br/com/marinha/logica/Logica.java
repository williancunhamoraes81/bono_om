package br.com.marinha.logica;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

public interface Logica {
	String executa(PortletRequest request, PortletResponse response) throws Exception;	
}
