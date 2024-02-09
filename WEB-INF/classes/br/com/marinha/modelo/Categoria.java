package br.com.marinha.modelo;

import com.ibm.workplace.wcm.api.DocumentId;

public class Categoria {

	private String nomeCategoria;
	private DocumentId idCategoria;
	private String tituloCategoria;
	private String descricaoCategoria;
			
	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}

	public String getTituloCategoria() {
		return tituloCategoria;
	}

	public void setTituloCategoria(String tituloCategoria) {
		this.tituloCategoria = tituloCategoria;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}
	
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	public DocumentId getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(DocumentId idCategoria) {
		this.idCategoria = idCategoria;
	}
	
}
