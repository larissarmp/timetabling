package dados;

import java.io.Serializable;

public class SalaTipo implements Comparable<SalaTipo>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7481703173603471021L;
	int id;
	String descricao;
	
	public SalaTipo(int codigo, String descricao) {
		this.id = codigo;
		this.descricao = descricao;
	}
	
	public int getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}

	@Override
	public int compareTo(SalaTipo o) {
		return Integer.compare(id, o.getId());
	}

	@Override
	public String toString() {
		return String.format("SalaTipo [id=%s, descricao=%s]", id, descricao);
	}
	
	
}
