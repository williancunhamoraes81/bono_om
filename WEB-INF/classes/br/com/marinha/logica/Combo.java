package br.com.marinha.logica;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import org.w3c.dom.Document;

import br.com.marinha.dao.CategoriaDAO;
import br.com.marinha.modelo.Categoria;


public class Combo implements Logica{
	
	private Document doc;
		
	public String executa(PortletRequest request, PortletResponse response){
		
		PortletSession session 				= request.getPortletSession();
		Date dataAgora						= new Date();
		List<Categoria> listaCategoriaDN	= new CategoriaDAO().lista(request, "DN");
						
		//Atribui lista de Categoria na sessão
		session.setAttribute("listaCategoriaDN", listaCategoriaDN, PortletSession.APPLICATION_SCOPE);
		
		return "";
						
	}
		
}