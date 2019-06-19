package dados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import util.Funcoes;
import util.MapObject;

public class Horario implements Comparable<Horario>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2867114607619064482L;
	public static final int KEY_ID = 0, KEY_DISCIPLINAS_PRESENTE = 1, KEY_DISCIPLINAS_POSSIVEIS = 2, KEY_TIMESLOTS = 3, KEY_OBJ = 4;
	//public static final String KEY_DISCIPLINAS_PRESENTE = "presente", KEY_DISCIPLINAS_POSSIVEIS = "possiveis", KEY_TIMESLOTS = "timeslots";
	private ArrayList<MapObject> listaEstudante, listaDocente, listaDisciplina; //estudantes e docentes nessas listas são apenas os que estão disponíveis 
	private ArrayList<SalaTipo> listaSalaTipo;
	private HashMap<SalaTipo, ArrayList<MapObject>> matrizSala; //tabela da lista de salas por tipo
	private Turma[] listaTurma; // lista de todas as turmas existentes, o tamanho do vetor é o número de disciplinas, pode conter null
	private int pontuacao; // aptidão do indivíduo
	
	public Horario(Container container){
		MapObject map;
		listaSalaTipo = container.listaSalaTipo;
		boolean[] timeslots;
		listaDisciplina = new ArrayList<>(container.listaDisciplina.size());
		listaEstudante = new ArrayList<>(container.listaEstudante.size());
		listaDocente = new ArrayList<>(container.listaDocente.size());
		matrizSala = new HashMap<>(container.listaSalaTipo.size());
		
		for(SalaTipo st : container.listaSalaTipo){
			matrizSala.put(st, new ArrayList<>());
		}
		
		for(Sala s : container.listaSala){
			map = new MapObject(); //sala: contem os timeslots disponíveis e objecto sala correspondente
			timeslots = new boolean[Funcoes.NUMERO_SLOTS];
			setVetorDisponivel(timeslots, s.getTimeslotsPossiveis());
			map.put(KEY_TIMESLOTS, timeslots);
			map.put(KEY_OBJ, s);
			matrizSala.get(listaSalaTipo.get(Collections.binarySearch(listaSalaTipo, new SalaTipo(s.getTipo(), "")))).add(map);
		}
		for(Estudante e : container.listaEstudante){
			map = new MapObject(); //estudante: contem id, timeslots, disciplinas presente e possiveis
			map.put(KEY_ID, e.getId());
			
			timeslots = new boolean[Funcoes.NUMERO_SLOTS];
			Arrays.fill(timeslots, true);
			map.put(KEY_TIMESLOTS, timeslots);
			map.put(KEY_DISCIPLINAS_PRESENTE, 0);
			map.put(KEY_DISCIPLINAS_POSSIVEIS, e.getDisciplinas());
			listaEstudante.add(map);
		}
		
		for(Docente d: container.listaDocente){
			map = new MapObject(); //docente: igual estudante
			map.put(KEY_ID, d.getId());
			
			timeslots = new boolean[Funcoes.NUMERO_SLOTS];
			setVetorDisponivel(timeslots, d.getTimeslotsPossiveis());
			map.put(KEY_TIMESLOTS, timeslots);
			map.put(KEY_DISCIPLINAS_PRESENTE, 0);
			map.put(KEY_DISCIPLINAS_POSSIVEIS, d.getDisciplinas());
			listaDocente.add(map);
		}
		for(Disciplina d : container.listaDisciplina){
			map = new MapObject(); //disciplina: timeslots disponiveis e disciplina correspondente
			timeslots = new boolean[Funcoes.NUMERO_SLOTS];
			setVetorDisponivel(timeslots, d.getTimeslotsPossiveis());
			map.put(KEY_TIMESLOTS, timeslots);
			
			map.put(KEY_OBJ, d);
			listaDisciplina.add(map);
		}
		pontuacao = 0;
	}
	
	public int getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	
	public ArrayList<MapObject> getListaEstudante() {
		return listaEstudante;
	}

	public ArrayList<MapObject> getListaDocente() {
		return listaDocente;
	}

	public ArrayList<MapObject> getListaDisciplina() {
		return listaDisciplina;
	}

	private void setVetorDisponivel(boolean[] timeslots, int[] posicoes){
		Funcoes.desocuparTimeslots(timeslots, posicoes);
	}
	
	public ArrayList<MapObject> getListaSalaPorTipo(int tipo){
		return matrizSala.get(listaSalaTipo.get(Collections.binarySearch(listaSalaTipo, new SalaTipo(tipo, ""))));
	}

	public Turma[] getListaTurma() {
		return listaTurma;
	}

	public void setListaTurma(Turma[] listaTurma) {
		this.listaTurma = listaTurma;
	}

	@Override
	public int compareTo(Horario o) {
		return Integer.compare(this.pontuacao, o.getPontuacao());
	}
	public void limpar(){
/*		listaDisciplina = listaEstudante = listaDocente = null;
		listaSalaTipo = null;
		matrizSala = null;*/
	}
}
