package dados;

import java.io.Serializable;

public class Curso implements Comparable<Curso>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8828250441486754130L;
	private int id;
	private String nome;
	private String sigla;
	private int numeroPeriodos;
	private Turno turno;
	
	protected Curso(int id){
		this.id = id;
	}
	
	public Curso(int id, String nome, String sigla, int numeroPeriodos, int turno) {
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
		this.numeroPeriodos = numeroPeriodos;
		this.turno = Turno.getTipo(turno);
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getSigla() {
		return sigla;
	}

	public int getNumeroPeriodos() {
		return numeroPeriodos;
	}

	public Turno getTurno() {
		return turno;
	}

	@Override
	public int compareTo(Curso o) {
		return Integer.compare(id, o.getId());
	}

	@Override
	public String toString() {
		return String.format("Curso [id=%s, nome=%s, sigla=%s, numeroPeriodos=%s, turno=%s]", id, nome, sigla,
				numeroPeriodos, turno);
	}
}
