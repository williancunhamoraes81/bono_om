package br.com.marinha.usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.marinha.funcao.Funcao;
import br.com.marinha.funcao.Util;
import br.com.marinha.modelo.Usuario;

import com.ibm.workplace.wcm.api.Category;
import com.ibm.workplace.wcm.api.Content;
import com.ibm.workplace.wcm.api.DocumentId;
import com.ibm.workplace.wcm.api.DocumentIdIterator;
import com.ibm.workplace.wcm.api.DocumentTypes;
import com.ibm.workplace.wcm.api.UserProfile;
import com.ibm.workplace.wcm.api.Workspace;

public class DadosUsuario {

public static String isMemberDN(){
		 
		String retorno = "";
		
		Workspace wk				= Util.getWorkspace();
		DocumentIdIterator itCat	= wk.findByName(DocumentTypes.Category, "DN");
		DocumentId idCat			= (DocumentId)itCat.next();		
		Category categoria;
		Category categoriaChild;
		DocumentIdIterator childCateg;
		DocumentId idCatChild;
		
		try {
			categoria 	= (Category)wk.getById(idCat);			
			childCateg	= categoria.getChildren();
			
			while(childCateg.hasNext()){
				idCatChild = (DocumentId)childCateg.next();
				categoriaChild = (Category)wk.getById(idCatChild);
				String grupo = categoriaChild.getDescription();
				
				if(!grupo.equals("")){
					if(isMemberOfGroup(grupo)){						
						return categoriaChild.getName();							
					}					
				}
				
			}
			
		}catch(Exception e){
			System.out.println("Problemas com isMemberDN");
		}		
		return retorno;
	}
	
	public static boolean isMemberOfGroup(String grupo){
		
		Workspace wk = Util.getWorkspace();
		
		String grupoRepositorio = Funcao.getHTMLComponentValue(grupo)[0];				
		List<String> listUsers = Arrays.asList(grupoRepositorio.split(";"));
		
		String[] userList = wk.getUserProfile().getDistinguishedName().toUpperCase().split(",");			
		String[] userListValue = userList[1].split("=");
		String findUser = wk.getUserProfile().getCommonName().toUpperCase() + "#" + userListValue[1];
									
		if(listUsers.indexOf(findUser) > -1){				
			return true;
		}
		
		return false;
		
	}

	public static Usuario criaUsuario(){
							
		Workspace wk = Util.getWorkspace();
		
		String nomeUsuario	= wk.getUserProfile().getCommonName();
		Usuario usuario = new Usuario();
		usuario.setNome(nomeUsuario);			
		usuario.setAprovador(DadosUsuario.isApprover(wk));		
		DadosUsuario.getOMAuthor(usuario);		
		usuario.setDn(DadosUsuario.isMemberDN());
		usuario.setAccess(getAccess(wk));
				
		Util.closeWorkspace(wk);
		
		return usuario;
	}
	
	
	public static boolean getAccess(Workspace wk){
		
		try{
			
			List<String> grupos = new ArrayList();
			grupos.add("HTML_BONO_APROVADOR");
			grupos.add("HTML_BONO_EDITOR");
			
			for (String grupo : grupos) {
				String grupoRepositorio = Funcao.getHTMLComponentValue(grupo)[0];				
				List<String> listUsers = Arrays.asList(grupoRepositorio.split(";"));
				
				String[] userList = wk.getUserProfile().getDistinguishedName().toUpperCase().split(",");			
				String[] userListValue = userList[1].split("=");
				String findUser = wk.getUserProfile().getCommonName().toUpperCase() + "#" + userListValue[1];
											
				if(listUsers.indexOf(findUser) > -1){				
					return true;
				}
			}
								
		}catch(Exception e){
			System.out.println("Verificando getAccess erro");
			throw new RuntimeException(e);	
		}		
		return false;		
	}
	
	
	public static void getOMAuthor(Usuario usuario){
		
		Workspace wk 			= Util.getWorkspace();
		UserProfile user 		= wk.getUserProfile(); 
		String userIndNaval		= "";
		String omTituloUsuario	= "";
		String omDescUsuario 	= "";
		DocumentId idCateg		= null;
		
		try{
			//Exemplo: "cn=02967459798,ou=cetinf,ou=1DN,dc=mb"
			String[] userList 		= user.getDistinguishedName().split(",");			
			String[] userListValue	= userList[1].split("=");			
			userIndNaval			= userListValue[1].toUpperCase();
						
			DocumentIdIterator itCateg	= wk.findByName(DocumentTypes.Category, userIndNaval);
			
			idCateg						= (DocumentId)itCateg.next();
			Category category 			= (Category)wk.getById(idCateg);			
			omTituloUsuario				= category.getTitle();
			omDescUsuario				= category.getDescription();
			
		}catch(Exception e){
			
		}
			
		usuario.setOmUsuario(userIndNaval);
		usuario.setOmIdUsuario(idCateg);
		usuario.setOmTituloUsuario(omTituloUsuario);
		usuario.setOmDescricaoUsuario(omDescUsuario);
				
	}
	
	public static boolean isAuthor(Content content, String Usuario){		
		boolean isAuthor = false;		
		try{
			String[] listAuthors = content.getAuthors();			
			for(int i=0;i < listAuthors.length;i++){				
				if(Usuario.toUpperCase().equals(listAuthors[i].toUpperCase())){
					isAuthor = true;
					break;
				}
			}
		}catch(Exception e){			
			throw new RuntimeException(e);		
		}	
		return isAuthor;		
	}
	
	public static boolean isApprover(Workspace wk){		
		
		try{	
			String grupoRepositorio = Funcao.getHTMLComponentValue("HTML_BONO_APROVADOR")[0];				
			List<String> listApprovers = Arrays.asList(grupoRepositorio.split(";"));
			
			String[] userList 		= wk.getUserProfile().getDistinguishedName().toUpperCase().split(",");			
			String[] userListValue	= userList[1].split("=");
			String findUser = wk.getUserProfile().getCommonName().toUpperCase() + "#" + userListValue[1];
										
			if(listApprovers.indexOf(findUser) > -1){				
				return true;
			}
					
		}catch(Exception e){
			System.out.println("Verificando Is Approver erro");
			throw new RuntimeException(e);	
		}		
		return false;		
	}
	
}
