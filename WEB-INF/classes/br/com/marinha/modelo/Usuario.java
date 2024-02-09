package br.com.marinha.modelo;

import com.ibm.workplace.wcm.api.DocumentId;

public class Usuario {
 
	private String nome;
	private boolean aprovador;
	private String omUsuario;
	private DocumentId omIdUsuario;
	private String omTituloUsuario;
	private String omDescricaoUsuario;
	private String dn;
	private boolean access;
	
	
	public boolean getAccess() {
		return access;
	}
	public void setAccess(boolean access) {
		this.access = access;
	}
	
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getOmDescricaoUsuario() {
		return omDescricaoUsuario;
	}
	public void setOmDescricaoUsuario(String omDescricaoUsuario) {
		this.omDescricaoUsuario = omDescricaoUsuario;
	}
	public String getOmTituloUsuario() {
		return omTituloUsuario;
	}
	public void setOmTituloUsuario(String omTituloUsuario) {
		this.omTituloUsuario = omTituloUsuario;
	}
	public DocumentId getOmIdUsuario() {
		return omIdUsuario;
	}
	public void setOmIdUsuario(DocumentId omIdUsuario) {
		this.omIdUsuario = omIdUsuario;
	}
	public String getOmUsuario() {
		return omUsuario;
	}
	public void setOmUsuario(String omUsuario) {
		this.omUsuario = omUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean getAprovador() {
		return aprovador;
	}
	public void setAprovador(boolean aprovador) {
		this.aprovador = aprovador;
	}
	
}
