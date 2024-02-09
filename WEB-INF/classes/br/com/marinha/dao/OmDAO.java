package br.com.marinha.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import br.com.marinha.funcao.Funcao;
import br.com.marinha.modelo.OrganizacaoMilitar;

import com.ibm.workplace.wcm.api.Category;
import com.ibm.workplace.wcm.api.Content;
import com.ibm.workplace.wcm.api.DocumentId;
import com.ibm.workplace.wcm.api.DocumentIdIterator;
import com.ibm.workplace.wcm.api.DocumentIterator;
import com.ibm.workplace.wcm.api.DocumentTypes;
import com.ibm.workplace.wcm.api.Workspace;
import com.ibm.workplace.wcm.api.exceptions.AuthorizationException;
import com.ibm.workplace.wcm.api.exceptions.DocumentRetrievalException;
import com.ibm.workplace.wcm.api.exceptions.DocumentSaveException;
import com.ibm.workplace.wcm.api.exceptions.DuplicateChildException;

public class OmDAO {
							
	public String existeSigla(PortletRequest request, String id){
		
		PortletSession session 	= request.getPortletSession();
		Workspace wk 			= (Workspace)session.getAttribute("workspace");
		String retorno			= "";
		
		DocumentIdIterator it_categ	= wk.findByName(DocumentTypes.Category, id);
		retorno += Integer.toString(it_categ.getCount());	
							
		return retorno;
	}
	
	public void remove(PortletRequest request, String id){
		
		PortletSession session 		= request.getPortletSession();
		Workspace wk 				= (Workspace)session.getAttribute("workspace");
		
		DocumentId id_categ 		= null;
		DocumentId id_categ_pai		= null;
		Category categoria 			= null;
		Category pai				= null;
		
		try{
			
			DocumentIdIterator it_categ = wk.findByName(DocumentTypes.Category, id);
			
			while(it_categ.hasNext()){
				id_categ		= (DocumentId)it_categ.next();				
				categoria		= (Category)wk.getById(id_categ);
				
				id_categ_pai 	= categoria.getParent();
				pai				= (Category)wk.getById(id_categ_pai);
				
				if(pai.getName().equals("OM")){					
					break;
				}
			}
			
			DocumentIdIterator it_cat 	= wk.findByName(DocumentTypes.Category, id);
			//DocumentId id_cat 			= (DocumentId)it_cat.nextId();
			wk.delete(id_categ);			
		}catch(Exception e){
			throw new RuntimeException(e);
		}				
	}
		
	public List<OrganizacaoMilitar> lista(PortletRequest request, String valorBusca){		
	
		List<OrganizacaoMilitar> omLista	= new ArrayList<OrganizacaoMilitar>();			
		PortletSession session 				= request.getPortletSession();
		Workspace wk 						= (Workspace)session.getAttribute("workspace");
		int count							= 0;
			
		DocumentId id_categPai			= (DocumentId)wk.findByName(DocumentTypes.Category, "OM").next();
		Category categoriaPai;
			try {
				categoriaPai = (Category)wk.getById(id_categPai);
				DocumentIterator categorias 	= wk.getByIds(categoriaPai.getChildren(), true);											
				
				while(categorias.hasNext()){				
					Category categoria = (Category)categorias.next();				
					
					
					try{
						
						String[] valores	= categoria.getDescription().split("#");
												
						OrganizacaoMilitar om = new OrganizacaoMilitar();
						om.setCodigo(valores[0]);					
						om.setDescricao(categoria.getTitle());
						om.setSigla(categoria.getName());
						om.setIndicativo(valores[1]);
						om.setPosto(valores[2]);
						om.setDistrito(valores[5]);
						om.setSequencia(valores[3]);
						om.setGenero(valores[4]);							
						om.setAutor(categoria.getAuthors());
						om.setData(categoria.getCreationDate());
						
						// Se existe valor para ser localizado entra na busca					
						if(valorBusca != null && !valorBusca.equals("")){
							if(om.find(valorBusca) == true){							
								omLista.add(om);
								count++;
							}
						}else{
							omLista.add(om);
							count++;
						}
																				
					}catch(ArrayIndexOutOfBoundsException e){
						System.out.println("Categoria com erro:" + categoria.getName());
					} 
					
				}	
				
				//Gera na sessão variavel para quantidade de páginas			
				if(valorBusca != null && !valorBusca.equals("")){
					session.setAttribute("rangePaginaIni", "0", PortletSession.APPLICATION_SCOPE);	
					session.setAttribute("rangePaginaFim", count, PortletSession.APPLICATION_SCOPE);
					session.setAttribute("paginaCorrente", "0", PortletSession.APPLICATION_SCOPE);
					session.setAttribute("paginacao", false, PortletSession.APPLICATION_SCOPE);
				}else{
					session.setAttribute("qtdeTotalDocs", Math.floor(count / 20), PortletSession.APPLICATION_SCOPE);
					session.setAttribute("paginacao", true, PortletSession.APPLICATION_SCOPE);
				}
				
			} catch (DocumentRetrievalException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			} catch (AuthorizationException e) {
				// TODO Bloco catch gerado automaticamente
				e.printStackTrace();
			}
				
		return omLista;
	}
	
	
	public void atualiza(PortletRequest request, OrganizacaoMilitar om){
		
		PortletSession session 		= request.getPortletSession();
		Workspace wk 				= (Workspace)session.getAttribute("workspace");
		DocumentId id_categ_pai		= null;
		Category pai				= null;
		Category categoria			= null;
		DocumentId id_categ			= null;
		
		try{
			DocumentIdIterator it_categ	= wk.findByName(DocumentTypes.Category, om.getSigla());
			
			while(it_categ.hasNext()){
				id_categ		= (DocumentId)it_categ.next();
				categoria		= (Category)wk.getById(id_categ);
				
				id_categ_pai 	= categoria.getParent();
				pai				= (Category)wk.getById(id_categ_pai);
				
				if(pai.getName().equals("OM")){					
					break;
				}
			}
			
			categoria.setDescription(om.getCodigo()+"#"+om.getIndicativo()+"#"+om.getPosto()+"#"+om.getSequencia()+"#"+om.getGenero()+"#"+om.getDistrito());
			categoria.setTitle(om.getDescricao());
			categoria.setName(om.getSigla());
			wk.save(categoria);			
			
			// PASSA PELOS DOCUMENTOS QUE FAZ REFERENCIA A OM SELECIONADA, ALTERANDO OS DADOS			
			DocumentId id_auth 			= wk.findByName(DocumentTypes.AuthoringTemplate, "at_bono").next();
			DocumentId[] categF 		= new DocumentId[1];				
			categF[0] 					= id_categ;
			String[] details			= categoria.getDescription().split("#");
			Content content 			= null;
			
			DocumentIdIterator contentCateg = wk.contentSearch(id_auth, null, categF, null, true, 1);
						
			if(contentCateg.getCount() > 0){		
				while(contentCateg.hasNext()){
					try{
						
						DocumentId id_area 	= (DocumentId)contentCateg.next();
						content 			= (Content)wk.getById(id_area);							
						Funcao.setTextContentValue(content, "DetalheOM", categoria.getDescription());			
						Funcao.setTextContentValue(content, "Posicao", details[3]);
						wk.save(content);
						
					}catch(Exception e){
						throw new RuntimeException(e);
					}
				}
			}
			
			om.setAutor(categoria.getAuthors());
			om.setData(categoria.getCreationDate());
						
		}catch(Exception e){
			throw new RuntimeException(e);
		}		
	}
	
	public OrganizacaoMilitar edita(PortletRequest request, String id){
		
		OrganizacaoMilitar om 		= new OrganizacaoMilitar();
		PortletSession session 		= request.getPortletSession();
		Workspace wk 				= (Workspace)session.getAttribute("workspace");
		DocumentId id_categ 		= null;
		DocumentId id_categ_pai		= null;
		Category categoria 			= null;
		Category pai				= null;
		
		try{
			DocumentIdIterator it_categ	= wk.findByName(DocumentTypes.Category, id);
			while(it_categ.hasNext()){
				id_categ		= (DocumentId)it_categ.next();
				categoria		= (Category)wk.getById(id_categ);
				
				id_categ_pai 	= categoria.getParent();
				pai				= (Category)wk.getById(id_categ_pai);
				
				if(pai.getName().equals("OM")){					
					break;
				}
				
			}
			System.out.println("saiu");
			String[] valores	= categoria.getDescription().split("#");			
			String codigo 		= valores[0];
			String descricao 	= categoria.getTitle();
			String sigla 		= categoria.getName();
			String indicativo 	= valores[1];
			String posto 		= valores[2];
			String sequencia 	= valores[3];
			String genero		= valores[4];
			String distrito		= valores[5];
			String[] autor		= categoria.getAuthors();
			Date data			= categoria.getCreationDate();
			
			om.setCodigo(codigo);					
			om.setDescricao(descricao);
			om.setSigla(sigla);
			om.setIndicativo(indicativo);
			om.setPosto(posto);
			om.setGenero(genero);
			om.setSequencia(sequencia);	
			om.setDistrito(distrito);	
			om.setAutor(autor);
			om.setData(data);
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		return om;
	}
	
	public void cria(PortletRequest request, OrganizacaoMilitar om){
			
		PortletSession session 		= request.getPortletSession();
		Workspace wk 				= (Workspace)session.getAttribute("workspace");
		DocumentIdIterator it_categ	= wk.findByName(DocumentTypes.Category, "OM");
		DocumentId id_categ			= (DocumentId)it_categ.next();
		Category categoria;
			
		try {
			
			//cria om
			categoria = wk.createCategory(id_categ);				
			categoria.setDescription(om.getCodigo()+"#"+om.getIndicativo()+"#"+om.getPosto()+"#"+om.getSequencia()+"#"+om.getGenero()+"#"+om.getDistrito());
			categoria.setName(om.getSigla());
			categoria.setTitle(om.getDescricao());
			wk.save(categoria);
			
			//atualiza dados da om criada no objeto
			om.setData(categoria.getCreationDate());
			om.setAutor(categoria.getAuthors());			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
}
