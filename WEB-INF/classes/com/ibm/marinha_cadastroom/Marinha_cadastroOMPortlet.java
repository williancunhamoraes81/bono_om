package com.ibm.marinha_cadastroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import br.com.marinha.dao.OmDAO;
import br.com.marinha.funcao.Funcao;
import br.com.marinha.funcao.Util;
import br.com.marinha.logica.Logica;
import br.com.marinha.modelo.Categoria;
import br.com.marinha.modelo.OrganizacaoMilitar;
import br.com.marinha.modelo.Usuario;

import com.ibm.workplace.wcm.api.Workspace;

/**
 * A sample portlet
 */
public class Marinha_cadastroOMPortlet extends javax.portlet.GenericPortlet {
	
	private String navegacao;
	
	/**
	 * @see javax.portlet.Portlet#init()
	 */
	public void init() throws PortletException{
		super.init();
	}

		
	/**
	 * Serve up the <code>view</code> mode.
	 * 
	 * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		// Set the MIME type for the render response
		 
		response.setContentType(request.getResponseContentType());
		PortletSession session = request.getPortletSession();		
				
		// Atribui workspace na sessão do Portlet
		if(session.getAttribute("workspace") == null){			
			Workspace wk = Funcao.getWorkspace();
			session.setAttribute("workspace", wk);			
		}
				
		// Atribui valores na paginação inicial e não acessa mais esse trecho do cógido para sessão atual.
		if ( session.getAttribute("paginacaoInicial", PortletSession.APPLICATION_SCOPE) == null){			
			session.setAttribute("rangePaginaIni", "0", PortletSession.APPLICATION_SCOPE);	
			session.setAttribute("rangePaginaFim", "19", PortletSession.APPLICATION_SCOPE);
			session.setAttribute("paginacaoInicial", true, PortletSession.APPLICATION_SCOPE);
			session.setAttribute("paginaCorrente", "0", PortletSession.APPLICATION_SCOPE);
		}		
		
		if(navegacao == null || navegacao.equals("") || navegacao.equals("listagem")){			
			List<OrganizacaoMilitar> omLista = new OmDAO().lista(request, null);
			OrganizacaoMilitar compara = new OrganizacaoMilitar();
			Collections.sort(omLista,compara); 
			session.setAttribute("omLista",omLista, PortletSession.APPLICATION_SCOPE);
			navegacao = "listagem";			
		}
		
		//retorno sem passar pela listagem
		if(navegacao.equals("lista")){
			navegacao = "listagem";
		}
				
		Usuario	usuario = Util.getUsuario(session);	
		if(!usuario.getAccess() && !usuario.getNome().equals("wasadmin")){
			navegacao = "no_access";			
		}
		
		ResourceURL nextURI = response.createResourceURL();
		request.setAttribute("nextURI",nextURI);		
		PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher("/WEB-INF/jsp/" + navegacao + ".jsp");
		rd.include(request,response);
		
	}

	public void serveResource(ResourceRequest req, ResourceResponse resp)throws PortletException, IOException{
		
		String id		 		= req.getParameter("id");
		String paramLogica 		= req.getParameter("logica");
		String nomeDaClasse 	= "br.com.marinha.logica." + paramLogica;
		String retorno 			= "";	
				
		PortletSession session 	= req.getPortletSession();
						
		// Atribui workspace na sessão do Portlet
		if(session.getAttribute("workspace") == null){			
			Workspace wk = Funcao.getWorkspace();
			session.setAttribute("workspace", wk);			
		}
		
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		
		try{			
			Class classe 		= Class.forName(nomeDaClasse);				
			Logica logica 		= (Logica) classe.newInstance();
			retorno				= logica.executa(req, resp);
		}catch(Exception e){
			throw new RuntimeException(e);
		}				
		
		writer.print(retorno);
		writer.close();		
		
	}
	
	/**
	 * Serve up the <code>edit</code> mode.
	 * 
	 * @see javax.portlet.GenericPortlet#doEdit(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	public void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		// TODO: auto-generated method stub
	}

	/**
	 * Serve up the <code>help</code> mode.
	 * 
	 * @see javax.portlet.GenericPortlet#doHelp(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	protected void doHelp(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		// TODO: auto-generated method stub
	}

	/**
	 * Process an action request.
	 * 
	 * @see javax.portlet.Portlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
	 */
	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, java.io.IOException {
		// TODO: auto-generated method stub
		
		String paramLogica 		= request.getParameter("logica");
		String nomeDaClasse 	= "br.com.marinha.logica." + paramLogica;
		PortletSession session 	= request.getPortletSession();
		
		// Atribui workspace na sessão do Portlet
		if(session.getAttribute("workspace") == null){			
			Workspace wk = Funcao.getWorkspace();
			session.setAttribute("workspace", wk);			
		}
						
		// Atribui valores na paginação		
		Funcao.paginator(request);
		
		try{
			Class classe 	= Class.forName(nomeDaClasse);
			Logica logica 	= (Logica) classe.newInstance();
			navegacao		= logica.executa(request, response);			
		}catch(Exception e){
			throw new RuntimeException(e);
		}				
		
	}

}
