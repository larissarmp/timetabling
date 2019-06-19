package dados;

import java.io.Serializable;
import java.util.Arrays;

public class Docente implements Comparable<Docente>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8373190462691889040L;
	private int id;
	private String sigla;
	private String nome;
	private int[] disciplinas;
	
	private int[] timeslotsPossiveis;
	
	protected Docente(int id){
		this.id = id;
	}
	
	public Docente(int id, String sigla, String nome, int[] disciplinas) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.disciplinas = disciplinas;
		timeslotsPossiveis = new int[0];
	}

	public int getId() {
		return id;
	}

	public String getSigla() {
		return sigla;
	}

	public String getNome() {
		return nome;
	}

	public int[] getDisciplinas() {
		return disciplinas;
	}
	
	public int[] getTimeslotsPossiveis() {
		return timeslotsPossiveis;
	}
	public void setTimeslotsPossiveis(int[] timeslotsPossiveis) {
		this.timeslotsPossiveis = timeslotsPossiveis;
	}

	@Override
	public int compareTo(Docente o) {
		return Integer.compare(id, o.getId());
	}

	@Override
	public String toString() {
		return String.format("Docente [id=%s, sigla=%s, nome=%s, disciplinas=%s, timeslotsPossiveis=%s]", id, sigla,
				nome, Arrays.toString(disciplinas), /*Arrays.toString(timeslotsPossiveis)*/"");
	}
}
