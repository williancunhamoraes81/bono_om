package br.com.marinha.dao;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

import br.com.marinha.funcao.Util;
import br.com.marinha.modelo.Categoria;

import com.ibm.workplace.wcm.api.Category;
import com.ibm.workplace.wcm.api.DocumentId;
import com.ibm.workplace.wcm.api.DocumentIdIterator;
import com.ibm.workplace.wcm.api.DocumentTypes;
import com.ibm.workplace.wcm.api.Workspace;

public class CategoriaDAO {

	public List<Categoria> lista(PortletRequest request, String categoriaPai){
		 
		Workspace wk 					= Util.getWorkspace();
		List<Categoria> listaCategoria	= new ArrayList<Categoria>();
			
		try{
			
			DocumentId idTaxonomia 		= (DocumentId)wk.findByName(DocumentTypes.Category, categoriaPai).next();
			Category categoria 			= (Category)wk.getById(wk.createDocumentId(idTaxonomia.toString()));
			DocumentIdIterator filhos	= categoria.getAllChildren();
									
			while(filhos.hasNext()){
				DocumentId idCategoria 	= (DocumentId)filhos.next();
				Category categoriaFilho	= (Category)wk.getById(wk.createDocumentId(idCategoria.toString()));
								
				Categoria objetoCategoria = new Categoria();
				objetoCategoria.setNomeCategoria(categoriaFilho.getName());
				objetoCategoria.setIdCategoria(categoriaFilho.getId());
				objetoCategoria.setTituloCategoria(categoriaFilho.getTitle());
				objetoCategoria.setDescricaoCategoria(categoriaFilho.getDescription());
						
				listaCategoria.add(objetoCategoria);	
												
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
					
		return listaCategoria;		
		
	}
	
	public List<Categoria> lista(PortletRequest request, String categoriaPai, String filtro){
		
		Workspace wk 					= Util.getWorkspace();
		List<Categoria> listaCategoria	= new ArrayList<Categoria>();
			
		try{
			
			DocumentId idTaxonomia 		= (DocumentId)wk.findByName(DocumentTypes.Category, categoriaPai).next();
			Category categoria 			= (Category)wk.getById(wk.createDocumentId(idTaxonomia.toString()));
			DocumentIdIterator filhos	= categoria.getAllChildren();
			
			while(filhos.hasNext()){
				DocumentId idCategoria 	= (DocumentId)filhos.next();
				Category categoriaFilho	= (Category)wk.getById(wk.createDocumentId(idCategoria.toString()));
				
				System.out.println("indexOf:"+categoriaFilho.getDescription().trim().indexOf("aprovador"));
				
				if(categoriaFilho.getDescription().trim().equals(filtro)){
					
					Categoria objetoCategoria = new Categoria();
					objetoCategoria.setNomeCategoria(categoriaFilho.getName());
					objetoCategoria.setIdCategoria(categoriaFilho.getId());
					objetoCategoria.setTituloCategoria(categoriaFilho.getTitle());
					objetoCategoria.setDescricaoCategoria(categoriaFilho.getDescription());
						
					listaCategoria.add(objetoCategoria);	
				}								
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		return listaCategoria;		
		
	}
	
}
