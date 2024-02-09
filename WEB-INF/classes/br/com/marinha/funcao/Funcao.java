package br.com.marinha.funcao;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import com.ibm.workplace.wcm.api.Content;
import com.ibm.workplace.wcm.api.ContentComponentContainer;
import com.ibm.workplace.wcm.api.DocumentId;
import com.ibm.workplace.wcm.api.LibraryHTMLComponent;
import com.ibm.workplace.wcm.api.Repository;
import com.ibm.workplace.wcm.api.TextComponent;
import com.ibm.workplace.wcm.api.WebContentService;
import com.ibm.workplace.wcm.api.Workspace;
import com.ibm.workplace.wcm.api.exceptions.ComponentNotFoundException;
import com.ibm.workplace.wcm.api.exceptions.IllegalTypeChangeException;

public class Funcao {
	
	public static void paginator(PortletRequest request){
						
		PortletSession session = request.getPortletSession();
		
		//Se o parametro está atribuido diferente de nulo, ajusta paginação.
		if( (request.getParameter("paginaIni") != null) && (request.getParameter("paginaFim") != null) ){
				session.setAttribute("rangePaginaIni", request.getParameter("paginaIni"), PortletSession.APPLICATION_SCOPE);	
				session.setAttribute("rangePaginaFim", request.getParameter("paginaFim"), PortletSession.APPLICATION_SCOPE);
				session.setAttribute("paginaCorrente", request.getParameter("paginaCorrente"), PortletSession.APPLICATION_SCOPE);
		}
			
	}
				
	public static Workspace getWorkspace(){
		
		Workspace wk = null;
		try {	
			
			Repository repository = null;
			javax.naming.InitialContext ctx = new javax.naming.InitialContext(); 
			WebContentService webContentService = (WebContentService) ctx.lookup("portal:service/wcm/WebContentService"); 
			repository = webContentService.getRepository();				
			wk = repository.getWorkspace();	
			wk.setCurrentDocumentLibrary(wk.getDocumentLibrary("BONO"));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 		
		return wk;
	}
	
	public static String[] getHTMLComponentValue(String component){
		
		String retorno[]	= new String[2];		 	
		Workspace wk		= br.com.marinha.funcao.Util.getWorkspace();
		
		try{
			DocumentId idComponente 		= (DocumentId)wk.findComponentByName(component).next();
			LibraryHTMLComponent libHTML 	= null;
			
			libHTML 	= (LibraryHTMLComponent)wk.getById(wk.createDocumentId(idComponente.toString()));
			retorno[0] 	= libHTML.getHTML().trim();
			retorno[1]	= libHTML.getDescription().trim();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			br.com.marinha.funcao.Util.closeWorkspace(wk);
		}
		return retorno;
	 }

	public static String getHTMLComponentValue(String component, PortletRequest request){
				
		String valor				= "";
		PortletSession session 		= request.getPortletSession();				
		Workspace wk				= (Workspace)session.getAttribute("workspace");
		
		try{
			DocumentId id_component = (DocumentId)wk.findComponentByName(component).next();
			LibraryHTMLComponent lib_htmlcomponent = null;
					
			try {
				lib_htmlcomponent = (LibraryHTMLComponent)wk.getById(wk.createDocumentId(id_component.toString()));
				valor = lib_htmlcomponent.getHTML().trim();
				String newStr_1 = valor.toString().replaceAll("&lt;","<");
				String newStr_2 = newStr_1.replaceAll("&gt;",">");
				valor = newStr_2;
			} catch (Exception e) {
				
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
				
		return valor.trim();
	 }

	public static String getTextContentValue(Content content, String component){
		
	  	String valor 						= "";
		TextComponent componente 			= null;
		ContentComponentContainer container = null;
		
		try {
			componente = (TextComponent)content.getComponent(component);
				container = componente.getContainer();			
				if(componente.getText() != ""){
					valor = componente.getText().trim();
				}else{
					valor = "";
				}
		} catch (Exception e) {
				throw new RuntimeException(e);	
		}
	
		return valor.trim();
	 }

	public static void setTextContentValue(Content content,  String component, String texto){
		
		ContentComponentContainer container = null;
		TextComponent componente = null;
		
		if(texto != null){			
			try {
				
				componente = (TextComponent)content.getComponent(component);
				container = componente.getContainer();
				componente.setText(texto);				
				container.setComponent(component, componente);
				
			} catch (Exception e) {				
				throw new RuntimeException(e);
			}				
		}
	 }
	
}
