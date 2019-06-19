package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Funcoes {
	public static final int NUMERO_SLOTS = 168;
	
	private static Random random = new Random();
	
	/**
	 * Faz uma busca binária em um vetor. o vetor precisa obrigatoriamente estar ordenado
	 * @param lista vetor que pode conter o objeto
	 * @param objeto objeto a ser buscado
	 * @return true se o objeto esta no vetor ou false
	 */
	public static boolean contemNoVetor(Object[] lista, Object objeto){
		return Arrays.binarySearch(lista, objeto) > -1;
	}
	
	/**
	 * Faz uma busca binária em um vetor. o vetor precisa obrigatoriamente estar ordenado
	 * @param lista vetor que pode conter o objeto
	 * @param objeto objeto a ser buscado
	 * @return true se o objeto esta no vetor ou false
	 */
	public static boolean contemNoVetor(int[] lista, int objeto){
		return Arrays.binarySearch(lista, objeto) > -1;
	}
	
	/**
	 * Gera um inteiro aleatório no intervalo [0, limite)
	 * @param limite 
	 * @return int
	 */
	public static int getRandom(int limite){
		return random.nextInt(limite);
	}
	
	/**
	 * Gera um inteiro aleatório no intervalo [limiteInferior, limiteSuperior)
	 * @param limiteInferior
	 * @param limiteSuperior
	 * @return int
	 */
	public static int getRandom(int limiteInferior, int limiteSuperior){
		return limiteInferior + random.nextInt(limiteSuperior - limiteInferior);
	}
	
	
	/**
	 * Faz uma operação AND entre cada elemento do vetor e verifica se ele tem o minimo de trues necessários
	 * 
	 * @param v1
	 * @param v2
	 * @param minTrue mínimo de trues necessários para o vetor ser util
	 * @return boolean[] ou null se o numero de trues nao for atingido
	 */
	public static boolean[] andArray(boolean[] v1, boolean[] v2, int minTrue){
		int tam = Math.min(v1.length, v2.length), cont = 0;
		boolean ret[] = new boolean[tam];
		for(int i = 0; i < tam; i++){
			if(ret[i] = v1[i] && v2[i]) cont++;
		}
		return cont >= minTrue ? ret : null;
	}
	
	/**
	 * Faz uma operação OR entre cada elemento do vetor e verifica se ele tem o minimo de trues necessários
	 * 
	 * @param v1
	 * @param v2
	 * @param minTrue mínimo de trues necessários para o vetor ser util
	 * @return boolean[] ou null se o numero de trues nao for atingido
	 */
	public static boolean[] orArray(boolean[] v1, boolean[] v2, int minTrue){
		int tam = Math.min(v1.length, v2.length), cont = 0;
		boolean ret[] = new boolean[tam];
		for(int i = 0; i < tam; i++){
			if(ret[i] = v1[i] || v2[i]) cont++;
		}
		return cont >= minTrue ? ret : null;
	}
	
	public static List<Integer> getTrueIndexes(boolean[] v1, int max){
		ArrayList<Integer> lista = new ArrayList<>(max);
		PonteiroRandom p = new PonteiroRandom(v1.length);
		for(int i = 0, cont = 0; i < v1.length; i++){
			if(v1[p.getIndex(i)]){
				lista.add(p.getIndex(i));
				if(++cont == max) return lista;
			}
		}
		return null;
	}
	
	public static void ocuparTimeslots(boolean[] timeslots, int[] timeslotsOcupar){
		for(int t : timeslotsOcupar) timeslots[t] = false;
	}
	
	public static void ocuparTimeslots(boolean[] timeslots, List<Integer> timeslotsOcupar){
		for(int t : timeslotsOcupar) timeslots[t] = false;
	}
	
	public static void desocuparTimeslots(boolean[] timeslots, int[] timeslotsOcupar){
		for(int t : timeslotsOcupar) timeslots[t] = true;
	}
	
	public static void desocuparTimeslots(boolean[] timeslots, List<Integer> timeslotsOcupar){
		for(int t : timeslotsOcupar) timeslots[t] = true;
	}
	
	public static boolean testarTimeslots(boolean[] timeslots, List<Integer> timeslotsAlocar){
		for(int t : timeslotsAlocar) if(!timeslots[t]) return false;
		return true;
	}
}