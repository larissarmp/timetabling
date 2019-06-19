package dados;

import java.io.Serializable;
import java.util.LinkedList;

import util.MapObject;

public class Turma implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3198637515402239057L;
	private int pontuacao;
	private LinkedList<Integer> listatimeslotT, listatimeslotP, listaTimeslotTotal;
	MapObject disciplina, docente, salaT, salaP;
	private LinkedList<MapObject> listaEstudantes;
	
	
	
	public Turma(LinkedList<Integer> listatimeslotT, LinkedList<Integer> listatimeslotP, MapObject disciplina, MapObject docente, MapObject salaT, MapObject salaP, 
			LinkedList<MapObject> listaEstudantes) {
		this.pontuacao = 0;
		this.listatimeslotT = listatimeslotT;
		this.listatimeslotP = listatimeslotP;
		this.disciplina = disciplina;
		this.docente = docente;
		this.salaT = salaT;
		this.salaP = salaP;
		this.listaEstudantes = listaEstudantes;
		listaTimeslotTotal = new LinkedList<>();
		listaTimeslotTotal.addAll(listatimeslotT);
		listaTimeslotTotal.addAll(listatimeslotP);
	}
	public int getPontuacao() {
		return pontuacao;
	}
	
	public LinkedList<MapObject> getListaEstudantes() {
		return listaEstudantes;
	}
	public LinkedList<Integer> getListatimeslotT() {
		return listatimeslotT;
	}
	public LinkedList<Integer> getListatimeslotP() {
		return listatimeslotP;
	}
	public MapObject getDisciplina() {
		return disciplina;
	}
	public MapObject getDocente() {
		return docente;
	}
	public MapObject getSalaT() {
		return salaT;
	}
	public MapObject getSalaP() {
		return salaP;
	}
	public LinkedList<Integer> getListaTimeslotTotal() {
		return listaTimeslotTotal;
	}
	@Override
	public String toString() {
		return String.format(
				"Turma [pontuacao=%s, listatimeslotT=%s, listatimeslotP=%s, disciplina=%s, docente=%s, salaT=%s, salaP=%s, listaEstudantes=%s]",
				pontuacao, listatimeslotT, listatimeslotP, disciplina, docente, salaT, salaP, listaEstudantes);
	}
	
	
}
