package br.com.marinha.modelo;

import java.util.Comparator;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrganizacaoMilitar implements Comparator<OrganizacaoMilitar>{

	private String descricao;
	private String sigla;
	private String codigo;
	private String indicativo;
	private String posto;
	private String sequencia;
	private String genero;
	private Date data;
	private String distrito;
	private String autor;	
				
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		if(distrito == ""){
			this.distrito = "-";
		}else{
			this.distrito = distrito;
		}
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getAutor() {
		return autor;
	}
	public void setAutor(String[] autor) {
		this.autor = autor[0];
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {			
		this.descricao = descricao.toUpperCase();
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getIndicativo() {
		return indicativo;
	}
	public void setIndicativo(String indicativo) {
		this.indicativo = indicativo;
	}
	public String getPosto() {
		return posto;
	}
	public void setPosto(String posto) {
		this.posto = posto;
	}
	public String getSequencia() {
		return sequencia;
	}
	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}
	
	public boolean find(String valorBusca){
		boolean retorno = false;
		
		if(this.getDescricao().toUpperCase().contains(valorBusca.toUpperCase())){
			retorno = true;
		}else if(this.getSigla().toUpperCase().contains(valorBusca.toUpperCase())){
			retorno = true;
		}else if(this.getCodigo().toUpperCase().contains(valorBusca.toUpperCase())){
			retorno = true;
		}else if(this.getIndicativo().toUpperCase().contains(valorBusca.toUpperCase())){
			retorno = true;
		}else if(this.getPosto().toUpperCase().contains(valorBusca.toUpperCase())){
			retorno = true;
		}else if(this.getSequencia().toUpperCase().contains(valorBusca.toUpperCase())){
			retorno = true;
		}
				
		return retorno;	
	}
	
	@Override
	public int compare(OrganizacaoMilitar om1, OrganizacaoMilitar om2) {
		// Gera variáveis de comparação	
		String compara1 = om1.getSequencia() + om1.getCodigo();
		String compara2 = om2.getSequencia() + om2.getCodigo();
		
		int retorno = compara1.compareTo(compara2);
		if(retorno > 0){
			return 1; // transforma em descendente
		}else if (retorno < 0){
			return -1;
		}
		return 0;
	}
	
	
}
