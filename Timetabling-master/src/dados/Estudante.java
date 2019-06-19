package dados;

import java.io.Serializable;
import java.util.Arrays;

public class Estudante  implements Comparable<Estudante>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8726457256528843586L;
	private int id;
	private String nome;
	private int[] disciplinas;
	
	protected Estudante(int id){
		this.id = id;
	}
	public Estudante(int id, String nome, String disciplinas) {
		this.id = id;
		this.nome = nome;
		this.disciplinas = Arrays.stream(disciplinas.split(",")).mapToInt(Integer::parseInt).toArray();
	}
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	public int[] getDisciplinas() {
		return disciplinas;
	}
	
	@Override
	public int compareTo(Estudante o) {
		return Integer.compare(id, o.getId());
	}
	@Override
	public String toString() {
		return String.format("Estudante [id=%s, nome=%s, disciplinas=%s]", id, nome, Arrays.toString(disciplinas));
	}
}
