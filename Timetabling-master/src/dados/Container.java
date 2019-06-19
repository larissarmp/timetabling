package dados;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;

public class Container implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6743041274256457797L;
	private static Container instance; 
	public ArrayList<Curso> listaCurso;
	public ArrayList<Disciplina> listaDisciplina;
	public ArrayList<Docente> listaDocente;
	public ArrayList<Estudante> listaEstudante;
	public ArrayList<Sala> listaSala;
	public HashMap<SalaTipo, ArrayList<Sala>> matrizSala;
	public ArrayList<SalaTipo> listaSalaTipo;
	public ArrayList<Timeslot> listaTimeslot;
	
	
	public Container() {
		listaCurso = new ArrayList<>();
		listaDisciplina = new ArrayList<>();
		listaDocente = new ArrayList<>();
		listaEstudante = new ArrayList<>();
		listaSala = new ArrayList<>();
		listaSalaTipo = new ArrayList<>();
		listaTimeslot = new ArrayList<>();
		instance = this;
	}
	
	public void ordenar(){
		listaCurso.sort(Comparator.naturalOrder());
		listaDisciplina.sort(Comparator.naturalOrder());
		listaDocente.sort(Comparator.naturalOrder());
		listaEstudante.sort(Comparator.naturalOrder());
		listaSala.sort(Comparator.naturalOrder());
		listaTimeslot.sort(Comparator.naturalOrder());
	}
	
	public void finalizarInsercao(){
		for(Sala sala : listaSala){
			matrizSala.get(listaSalaTipo.get(sala.getTipo())).add(sala);
		}
	}
	
	public Disciplina getDisciplina(int id){
		return listaDisciplina.get(Collections.binarySearch(listaDisciplina, new Disciplina(id)));
	}
	public Estudante getEstudante(int id){
		return listaEstudante.get(Collections.binarySearch(listaEstudante, new Estudante(id)));
	}
	public Docente getDocente(int id){
		return listaDocente.get(Collections.binarySearch(listaDocente, new Docente(id)));
	}
	public Sala getSala(int id){
		return listaSala.get(Collections.binarySearch(listaSala, new Sala(id)));
	}
	public Timeslot getTimeslot(int id){
		return listaTimeslot.get(Collections.binarySearch(listaTimeslot, new Timeslot(id)));
	}
	public Curso getCurso(int id){
		return listaCurso.get(Collections.binarySearch(listaCurso, new Curso(id)));
	}
	
	public static Container getInstance(){
		return instance;
	}

	@Override
	public String toString() {
		return String.format(
				"Container {\n\tlistaCurso: %s, \n\tlistaDisciplina: %s, \n\tlistaDocente: %s, \n\tlistaEstudante: %s, \n\tlistaSala: %s, \n\tmatrizSala: %s, \n\tlistaSalaTipo: %s, \n\tlistaTimeslot: %s\n}",
				listaCurso, listaDisciplina, listaDocente, listaEstudante, listaSala, matrizSala, listaSalaTipo,
				listaTimeslot);
	}
	
}
