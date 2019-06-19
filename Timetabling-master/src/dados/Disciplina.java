package dados;

import java.io.Serializable;

public class Disciplina implements Comparable<Disciplina>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2429758854214454062L;
	private int id;
	private int idCurso;
	private int periodo;
	private String sigla;
	private String descricao;
	private int cht;
	private int tipoT;
	private int chp;
	private int tipoP;
	
	private int[] timeslotsPossiveis;
	
	protected Disciplina(int id){
		this.id = id;
	}
	
	public Disciplina(int id, int idCurso, int periodo, String sigla, String descricao, int cht, int tipoT, int chp, int tipoP) {
		this.id = id;
		this.idCurso = idCurso;
		this.periodo = periodo;
		this.sigla = sigla;
		this.descricao = descricao;
		this.cht = cht;
		this.tipoT = tipoT;
		this.chp = chp;
		this.tipoP = tipoP;
		timeslotsPossiveis = new int[0];
	}
	public int getId() {
		return id;
	}
	public int getIdCurso() {
		return idCurso;
	}
	public int getPeriodo() {
		return periodo;
	}
	public String getSigla() {
		return sigla;
	}
	public String getDescricao() {
		return descricao;
	}
	public int getCht() {
		return cht;
	}
	public int getTipoT() {
		return tipoT;
	}
	public int getChp() {
		return chp;
	}
	public int getTipoP() {
		return tipoP;
	}
	public int[] getTimeslotsPossiveis() {
		return timeslotsPossiveis;
	}
	public void setTimeslotsPossiveis(int[] timeslotsPossiveis) {
		this.timeslotsPossiveis = timeslotsPossiveis;
	}
	
	@Override
	public int compareTo(Disciplina o) {
		return Integer.compare(id, o.getId());
	}

	@Override
	public String toString() {
		return String.format(
				"Disciplina [id=%s, idCurso=%s, periodo=%s, sigla=%s, descricao=%s, cht=%s, tipoT=%s, chp=%s, tipoP=%s, timeslotsPossiveis=%s]",
				id, idCurso, periodo, sigla, descricao, cht, tipoT, chp, tipoP, /*Arrays.toString(timeslotsPossiveis)*/"");
	}
}
