package dados;

import java.io.Serializable;

public class Sala implements Comparable<Sala>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 440793000239864716L;
	private int id;
	private String sigla;
	private String descricao;
	private int tipo;
	private int capacidade;
	
	private int[] timeslotsPossiveis;
	
	protected Sala(int id){
		this.id = id;
	}
	
	public Sala(int id, String sigla, String descricao, int tipo, int capacidade) {
		this.id = id;
		this.sigla = sigla;
		this.descricao = descricao;
		this.tipo = tipo;
		this.capacidade = capacidade;
		timeslotsPossiveis = new int[0];
	}

	public Sala clone(){
		try {
			return (Sala) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public int getId() {
		return id;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getTipo() {
		return tipo;
	}

	public int getCapacidade() {
		return capacidade;
	}
	
	public void setTimeslotsPossiveis(int[] timeslotsPossiveis) {
		this.timeslotsPossiveis = timeslotsPossiveis;
	}
	
	public int[] getTimeslotsPossiveis() {
		return timeslotsPossiveis;
	}

	@Override
	public int compareTo(Sala o) {
		return Integer.compare(id, o.getId());
	}

	@Override
	public String toString() {
		return String.format("Sala [id=%s, sigla=%s, descricao=%s, tipo=%s, capacidade=%s, timeslotsPossiveis=%s]", id,
				sigla, descricao, tipo, capacidade, ""/*Arrays.toString(timeslotsPossiveis)*/);
	}
	
}
