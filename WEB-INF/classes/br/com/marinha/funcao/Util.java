package br.com.marinha.funcao;

import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import br.com.marinha.modelo.Usuario;
import br.com.marinha.usuario.DadosUsuario;

import com.ibm.workplace.wcm.api.Repository;
import com.ibm.workplace.wcm.api.WebContentService;
import com.ibm.workplace.wcm.api.Workspace;

public class Util {
		
	public static String completeToLeft(String value, String c, int size) {  
        String result = value;   
        while (result.length() < size) {  
            result = c + result;  
        }
        return result;  
    }
	
	public static Usuario getUsuario(PortletSession session){	
		Usuario	usuario = new DadosUsuario().criaUsuario();
		session.setAttribute("usuario", usuario, PortletSession.APPLICATION_SCOPE);	
		return usuario;		
	}
	
	public static void setSessionTimeCache(PortletRequest request, String nomeVariavel){
		
		PortletSession session 	= request.getPortletSession();
		Date dataCacheCombo		= new Date();
		session.setAttribute(nomeVariavel, dataCacheCombo, PortletSession.APPLICATION_SCOPE);
		
	}
	
	public static void closeWorkspace(Workspace wk){
		
		if(wk != null){
			wk.logout();
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

	public static Date getSessionTimeCache(PortletRequest request, String nomeVariavel){
		
		Date dateSession;
		
		try{
			PortletSession session 	= request.getPortletSession();
			dateSession = (Date)session.getAttribute(nomeVariavel, PortletSession.APPLICATION_SCOPE);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		return dateSession;
		
	}

}
