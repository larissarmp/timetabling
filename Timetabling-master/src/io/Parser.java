package io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import dados.Container;
import dados.Curso;
import dados.Disciplina;
import dados.Docente;
import dados.Estudante;
import dados.Sala;
import dados.SalaTipo;
import dados.Timeslot;
import util.Funcoes;

public class Parser {
	public static Container parseEntrada(File arquivoInformacoes, File arquivoRestricoes) throws IOException{
		Scanner in = new Scanner(arquivoInformacoes);
		String temp[];
		HashMap<String, Integer> tempMap;
		Pattern p = Pattern.compile("^//.*");
		
		Container container = new Container();
		//String[] identificador = {"TIMESLOT", "CURSO", "TIPO DE SALA", "SALA", "DISCILINA"}
		String linha = in.nextLine();
		
		while(!linha.equals("TIMESLOT")) linha = in.nextLine();
		linha = in.nextLine();
		while(!linha.equals("CURSO")){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			tempMap = new HashMap<>();
			tempMap.put("domingo", 1);
			tempMap.put("segunda-feira", 2);
			tempMap.put("terça-feira", 3);
			tempMap.put("quarta-feira", 4);
			tempMap.put("quinta-feira", 5);
			tempMap.put("sexta-feira", 6);
			tempMap.put("sábado", 7);
			container.listaTimeslot.add(new Timeslot(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), 
					Integer.parseInt(temp[2].substring(0, 2))));
			linha = in.nextLine();
		}
		linha = in.nextLine();
		while(!linha.equals("TIPO DE SALA")){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			container.listaCurso.add(new Curso(Integer.parseInt(temp[0]), temp[1], temp[2], Integer.parseInt(temp[3]), Integer.parseInt(temp[4])));
			linha = in.nextLine();
		}
		
		linha = in.nextLine();
		
		while(!linha.equals("SALA")){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			container.listaSalaTipo.add(new SalaTipo(Integer.parseInt(temp[0]), temp[1]));
			linha = in.nextLine();
		}
		linha = in.nextLine();
		
		while(!linha.equals("DISCIPLINA")){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			container.listaSala.add(new Sala(Integer.parseInt(temp[0]), temp[1], temp[2], Integer.parseInt(temp[3]), Integer.parseInt(temp[4])));
			linha = in.nextLine();
		}
		linha = in.nextLine();
		
		while(!linha.equals("ESTUDANTE")){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			container.listaDisciplina.add(new Disciplina(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]),
					temp[3], temp[4], Integer.parseInt(temp[5]), Integer.parseInt(temp[6]), Integer.parseInt(temp[7]), Integer.parseInt(temp[8])));
			linha = in.nextLine();
		}
		linha = in.nextLine();
		
		while(!linha.equals("DOCENTE")){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			container.listaEstudante.add(new Estudante(Integer.parseInt(temp[0]), temp[1], String.join(",", Arrays.copyOfRange(temp, 2, temp.length))));
			linha = in.nextLine();
		}
		linha = in.nextLine();
		
		while(in.hasNextLine()){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			container.listaDocente.add(new Docente(Integer.parseInt(temp[0]), temp[1], temp[2], parseIntArray(Arrays.copyOfRange(temp, 3, temp.length))));
			linha = in.nextLine();
		}
		for(Disciplina d : container.listaDisciplina){
			d.setTimeslotsPossiveis(fillTimeslots());
		}
		for(Docente d : container.listaDocente){
			d.setTimeslotsPossiveis(fillTimeslots());
		}
		for(Sala d : container.listaSala){
			d.setTimeslotsPossiveis(fillTimeslots());
		}
		//FIM ARQUIVO INFORMAÇÕES
		in.close();
		container.ordenar();
		if(arquivoRestricoes == null) return container;
		in = new Scanner(arquivoRestricoes);
		linha = in.nextLine();
		while(p.matcher(linha).matches()){
			linha = in.nextLine();
			continue;
		}
		linha = in.nextLine();
/*		Disciplina tempDisciplina;
		int[] restricoes;
		while(!linha.equals("DOCENTE")){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			tempDisciplina = container.getDisciplina(Integer.parseInt(temp[0]));
			
			restricoes = Arrays.copyOf(tempDisciplina.getTimeslotsPossiveis(), tempDisciplina.getTimeslotsPossiveis().length + temp.length -1);
			for(int i = tempDisciplina.getTimeslotsPossiveis().length; i < restricoes.length; i++){
				restricoes[i] = Integer.parseInt(temp[i - tempDisciplina.getTimeslotsPossiveis().length + 1]);
			}
			Arrays.sort(restricoes);
			tempDisciplina.setTimeslotsPossiveis(restricoes);
			linha = in.nextLine();
		}
		linha = in.nextLine();
		
		Docente tempDocente;
		while(!linha.equals("SALA")){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			tempDocente = container.getDocente(Integer.parseInt(temp[0].trim()));
			
			restricoes = Arrays.copyOf(tempDocente.getTimeslotsPossiveis(), tempDocente.getTimeslotsPossiveis().length + temp.length -1);
			for(int i = tempDocente.getTimeslotsPossiveis().length; i < restricoes.length; i++){
				restricoes[i] = Integer.parseInt(temp[i - tempDocente.getTimeslotsPossiveis().length + 1]);
			}
			Arrays.sort(restricoes);
			
			tempDocente.setTimeslotsPossiveis(restricoes);
			linha = in.nextLine();
		}
		
		linha = in.nextLine();
		Sala tempSala;
		while(true){
			if(p.matcher(linha).matches()){
				linha = in.nextLine();
				continue;
			}
			temp = trimSplit(linha);
			tempSala = container.getSala(Integer.parseInt(temp[0].trim()));
			
			restricoes = Arrays.copyOf(tempSala.getTimeslotsPossiveis(), tempSala.getTimeslotsPossiveis().length + temp.length -1);
			for(int i = tempSala.getTimeslotsPossiveis().length; i < restricoes.length; i++){
				restricoes[i] = Integer.parseInt(temp[i - tempSala.getTimeslotsPossiveis().length + 1].trim());
			}
			Arrays.sort(restricoes);
			tempSala.setTimeslotsPossiveis(restricoes);
			if(in.hasNextLine()) linha = in.nextLine();
			else break;
		}*/
		in.close();
		return container;
	}
	
	private static String[] trimSplit(String linha){
		String[] arr = linha.split(",");
		for(int i = 0; i < arr.length; i++){
			arr[i] = arr[i].trim();
		}
		return arr;
	}
	
	private static int[] parseIntArray(String[] s){
		int arr[] = new int[s.length];
		for(int i = 0; i < s.length; i++){
			arr[i] = Integer.parseInt(s[i]);
		}
		return arr;
	}
	
	private static int[] fillTimeslots(){
		int[] temp = new int[Funcoes.NUMERO_SLOTS];
		Arrays.parallelSetAll(temp, i -> i);
		return temp;
	}
}
