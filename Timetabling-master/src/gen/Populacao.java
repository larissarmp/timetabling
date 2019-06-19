package gen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dados.Container;
import dados.Curso;
import dados.Disciplina;
import dados.Horario;
import dados.Sala;
import dados.Turma;
import dados.Turno;
import util.Funcoes;
import util.MapObject;
import util.PonteiroRandom;

public class Populacao {
	//private static int DISCIPLINA_NAO_ALOCADA = 10, DOCENTE_DESOCUPADO = 10, ESTUDANTE_DESOCUPADO;
	
	public static Horario[] gerarPopulacao(int tamanho, Container cont){
		Horario[] listaHorario = new Horario[tamanho];
		for(int i = 0; i < tamanho; i++){
			System.out.println(i);
			listaHorario[i] = criarHorario(cont);
		}
		return listaHorario;
	}

	public static Horario criarHorario(Container container){
		//Turma[] horario = new Turma[container.listaDisciplina.size()];
		Horario horario = new Horario(container);
		Curso curso;
		LinkedList<Integer> listaTimeslotT, listaTimeslotP;
		List<MapObject> listaSalaT, listaSalaP, listaEstudante = horario.getListaEstudante(), listaDocente = horario.getListaDocente();
		Disciplina disciplina;
		int indiceTurma = -1;
		PonteiroRandom p1, p2, p3, ponteiroDisciplina;
		Turma[] listaTurma = new Turma[horario.getListaDisciplina().size()];
		LinkedList<MapObject> listaEstudanteTurma = null;
		boolean[] disciplinaTimeslot;
		ponteiroDisciplina = new PonteiroRandom(horario.getListaDisciplina().size());
		for(int h = 0; h < horario.getListaDisciplina().size(); h++){
			MapObject mapDisciplina = horario.getListaDisciplina().get(ponteiroDisciplina.getIndex(h));
			MapObject mapDocente, mapSalaT, mapSalaP;
			mapDocente = mapSalaT = mapSalaP = mapDocente = null;
			indiceTurma++;
			
			disciplina = (Disciplina) mapDisciplina.get(Horario.KEY_OBJ);
			disciplinaTimeslot = (boolean[]) mapDisciplina.get(Horario.KEY_TIMESLOTS);
			int cargaHoraria = disciplina.getChp() + disciplina.getCht();
			
			curso = container.getCurso(disciplina.getIdCurso());
			restringirTimeslotTurno(disciplinaTimeslot, curso.getTurno());

			listaTimeslotT = new LinkedList<>();
			listaTimeslotP = new LinkedList<>();
			p1 = new PonteiroRandom(listaDocente.size());
			
			listaSalaT = listaSalaP = null;
			if(disciplina.getCht() > 0)listaSalaT = horario.getListaSalaPorTipo(disciplina.getTipoT());
			if(disciplina.getChp() > 0) listaSalaP = horario.getListaSalaPorTipo(disciplina.getTipoP());
			List<Integer> listaTimeslotTotal = new ArrayList<>(cargaHoraria);
			listaEstudanteTurma = new LinkedList<>();
			for(int i = 0; i < listaDocente.size(); i++){
				mapDocente = listaDocente.get(p1.getIndex(i));
				if(Funcoes.contemNoVetor((int[]) mapDocente.get(Horario.KEY_DISCIPLINAS_POSSIVEIS), disciplina.getId())){
					boolean[] docenteTimeslot = (boolean[]) mapDocente.get(Horario.KEY_TIMESLOTS);
					boolean[] horasPossiveis = Funcoes.andArray(docenteTimeslot, disciplinaTimeslot, cargaHoraria);
					if(horasPossiveis != null){
						if(disciplina.getCht() > 0){
							p2 = new PonteiroRandom(listaSalaT.size());
							for(int j = 0; j < listaSalaT.size(); j++){
								mapSalaT = listaSalaT.get(p2.getIndex(j));
								boolean[] salaTTimeslot = (boolean[]) mapSalaT.get(Horario.KEY_TIMESLOTS);
//								tempListT = Funcoes.compararTimeslotsCompativeis(docenteTimeslot, (boolean[]) mapSalaT.get(Horario.KEY_TIMESLOTS), disciplina.getCht(),
//										curso.getTurno());
								boolean[] horasPossiveisSalaT = Funcoes.andArray(horasPossiveis, salaTTimeslot, disciplina.getCht());
								if(horasPossiveisSalaT == null) continue;
								if(disciplina.getChp() > 0){
									p3 = new PonteiroRandom(listaSalaP.size());
									for(int k = 0; k < listaSalaP.size(); k++){
										mapSalaP = listaSalaP.get(p3.getIndex(k));
										boolean[] salaPTimeslot = (boolean[]) mapSalaP.get(Horario.KEY_TIMESLOTS);
//										tempListP = Funcoes.compararTimeslotsCompativeis(docenteTimeslot, (boolean[]) mapSalaP.get(Horario.KEY_TIMESLOTS), disciplina.getChp(),
//												curso.getTurno());
										boolean[] horasPossiveisSalaP = Funcoes.andArray(horasPossiveis, salaPTimeslot, disciplina.getCht());
										if(horasPossiveisSalaP == null) continue;
										boolean[] horasFinal = Funcoes.orArray(horasPossiveisSalaT, horasPossiveisSalaP, cargaHoraria);
										
										if(horasFinal != null){
											listaTimeslotTotal = Funcoes.getTrueIndexes(horasFinal, cargaHoraria);
											listaEstudanteTurma = alocarAlunos(listaEstudante, mapSalaT, mapSalaP, disciplina, listaTimeslotTotal);
											if(!listaEstudanteTurma.isEmpty()){
												Funcoes.ocuparTimeslots(docenteTimeslot, listaTimeslotTotal);
												for(int t : listaTimeslotTotal){
													if(salaTTimeslot[t]){
														listaTimeslotT.add(t);
														salaTTimeslot[t] = false;
													}
													else if(salaPTimeslot[t]){
														listaTimeslotP.add(t);
														salaPTimeslot[t] = false;
													}
													else{
														System.err.println("Isso não deveria acontecer: timeslot insuficiente para as salas");
														System.exit(1);
													}
												}
												break;
											}else listaTimeslotTotal = null;
										}
									}
								}else{
									listaTimeslotTotal = Funcoes.getTrueIndexes(horasPossiveisSalaT, cargaHoraria);
									listaEstudanteTurma = alocarAlunos(listaEstudante, mapSalaT, mapSalaP, disciplina, listaTimeslotTotal);
									if(!listaEstudanteTurma.isEmpty()){
										Funcoes.ocuparTimeslots(docenteTimeslot, listaTimeslotTotal);
										for(int t : listaTimeslotTotal){
											listaTimeslotT.add(t);
											salaTTimeslot[t] = false;
										}										
									}else listaTimeslotTotal = null;
								}
								if(listaTimeslotTotal != null && !listaEstudanteTurma.isEmpty()) break;
							}
						}else if(disciplina.getChp() > 0){
							p2 = new PonteiroRandom(listaSalaP.size());
							for(int j = 0; j < listaSalaP.size(); j++){
								mapSalaP = listaSalaP.get(p2.getIndex(j));
								boolean[] salaPTimeslot = (boolean[]) mapSalaP.get(Horario.KEY_TIMESLOTS);
//								tempListT = Funcoes.compararTimeslotsCompativeis(docenteTimeslot, (boolean[]) mapSalaT.get(Horario.KEY_TIMESLOTS), disciplina.getCht(),
//										curso.getTurno());
								boolean[] horasPossiveisSalaP = Funcoes.andArray(horasPossiveis, salaPTimeslot, disciplina.getChp());
								if(horasPossiveisSalaP == null) continue;
								listaTimeslotTotal = Funcoes.getTrueIndexes(horasPossiveisSalaP, cargaHoraria);
								listaEstudanteTurma = alocarAlunos(listaEstudante, mapSalaT, mapSalaP, disciplina, listaTimeslotTotal);
								if(!listaEstudanteTurma.isEmpty()){
									Funcoes.ocuparTimeslots(docenteTimeslot, listaTimeslotTotal);
									for(int t : listaTimeslotTotal){
										listaTimeslotP.add(t);
										salaPTimeslot[t] = false;
									}
								}else listaTimeslotTotal = null;
							}
							if(listaTimeslotTotal != null && !listaEstudanteTurma.isEmpty())  break;
						}else{
							System.err.println("Isso não deveria acontecer: a disciplina não tem carga horaria?");
						}
					}
				}
			}
			if(listaTimeslotTotal == null || listaTimeslotTotal.size() == 0) continue;
			mapDocente.put(Horario.KEY_DISCIPLINAS_PRESENTE, ((int) mapDocente.get(Horario.KEY_DISCIPLINAS_PRESENTE))+1);
			
			if((int) mapDocente.get(Horario.KEY_DISCIPLINAS_PRESENTE) == 5) listaDocente.remove(mapDocente);
			
			listaTurma[indiceTurma] = new Turma(listaTimeslotT, listaTimeslotP, mapDisciplina, mapDocente, mapSalaT, mapSalaP, listaEstudanteTurma);
		}
		horario.setListaTurma(listaTurma);
		horario.setPontuacao(calcularPontuacao(horario));
		horario.limpar();
		return horario;
	}
	
	private static int calcularPontuacao(Horario horario){
		int pontuacao = 0;
		for(Turma t : horario.getListaTurma()) 
			if(t == null) pontuacao--;
/*		for(MapObject mapDocente : horario.getListaDocente()){
			if(mapDocente.getInt(Horario.KEY_DISCIPLINAS_PRESENTE) == 0) pontuacao-= Populacao.DOCENTE_DESOCUPADO;
		}
		for(MapObject mapEstudante : horario.getListaEstudante()){
			if(mapEstudante.getInt(Horario.KEY_DISCIPLINAS_PRESENTE) == 0) pontuacao-= Populacao.ESTUDANTE_DESOCUPADO;
		}
		for(Turma turma : horario.getListaTurma()) if(turma == null) pontuacao-= Populacao.DISCIPLINA_NAO_ALOCADA;*/
		/*for(MapObject est : horario.getListaEstudante()){
			if(est.getInt(Horario.KEY_DISCIPLINAS_POSSIVEIS) < est.getInt(Horario.KEY_DISCIPLINAS_PRESENTE)) pontuacao--;
			else if(est.getInt(Horario.KEY_DISCIPLINAS_POSSIVEIS) > est.getInt(Horario.KEY_DISCIPLINAS_PRESENTE)){
				System.out.println("Isso não deveria acontecer: o aluno está em uma disciplina que nao deve");
			}
		}*/
		return pontuacao;
	}
	
	
	private static void restringirTimeslotTurno(boolean[] timeslots, Turno turno){
		for(int i = 0; i < timeslots.length; i++){
			timeslots[i] = timeslots[i] && Turno.isCompativel(i, turno);
		}
	}
	
	public static LinkedList<MapObject> alocarAlunos(List<MapObject> listaEstudante, MapObject mapSalaT, MapObject mapSalaP,
			Disciplina disciplina, List<Integer> listaTimeslotTotal){
		LinkedList<MapObject> listaEstudanteTurma = new LinkedList<>();
		
		PonteiroRandom pEstudante = new PonteiroRandom(listaEstudante.size());
		Sala salaT = null, salaP = null;
		if(mapSalaT != null) salaT = (Sala) mapSalaT.get(Horario.KEY_OBJ);
		if(mapSalaP != null) salaP = (Sala) mapSalaP.get(Horario.KEY_OBJ);
		int capacidadeT = salaT != null ? salaT.getCapacidade() : Integer.MAX_VALUE;
		int capacidadeP = salaP != null ? salaP.getCapacidade() : Integer.MAX_VALUE;
		int tamanhoSala = Math.min(capacidadeT, capacidadeP);
		for(int i = 0; i < tamanhoSala && i < listaEstudante.size(); i++){
			MapObject mapEstudante = listaEstudante.get(pEstudante.getIndex(i));
			if(Funcoes.contemNoVetor((int[]) mapEstudante.get(Horario.KEY_DISCIPLINAS_POSSIVEIS), disciplina.getId())){
				if(Funcoes.testarTimeslots((boolean[]) mapEstudante.get(Horario.KEY_TIMESLOTS), listaTimeslotTotal)){
					listaEstudanteTurma.add(mapEstudante);
					Funcoes.ocuparTimeslots((boolean[]) mapEstudante.get(Horario.KEY_TIMESLOTS), listaTimeslotTotal);
					mapEstudante.put(Horario.KEY_DISCIPLINAS_PRESENTE, ((int) mapEstudante.get(Horario.KEY_DISCIPLINAS_PRESENTE))+1);
					if((int) mapEstudante.get(Horario.KEY_DISCIPLINAS_PRESENTE) == 10) listaEstudante.remove(mapEstudante);
				} 
			}
		}
		return listaEstudanteTurma;
	}
	
	public static boolean validarHorario(Horario h){
		for(Turma turma1 : h.getListaTurma()){
			if(turma1 == null) continue;
			for(Turma turma2 : h.getListaTurma()){
				if(turma2 == null) continue;
				LinkedList<Integer> listaTimeslotIgual = new LinkedList<>();
				for(int t1 : turma1.getListaTimeslotTotal())
					for(int t2 : turma2.getListaTimeslotTotal())
						if(t1 == t2) listaTimeslotIgual.add(t1);
				if(listaTimeslotIgual.isEmpty()) continue;
				
				if(turma1.getDocente().equals(turma2.getDocente()))
					return false;
				for(MapObject m1 : turma1.getListaEstudantes())
					for(MapObject m2 : turma2.getListaEstudantes())
						if(m1.equals(m2)) return false;
					
				if(turma1.getSalaT() != null && turma2.getSalaT() != null && turma1.getSalaT().equals(turma2.getSalaT()))
					return false;
				if(turma1.getSalaP() != null && turma2.getSalaP() != null && turma1.getSalaP().equals(turma2.getSalaP()))
					return false;
			}
		}
		return true;
	}
	
}