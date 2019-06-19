package util;

public class PonteiroRandom {
	private int ponteiro;
	private int limite;
	public PonteiroRandom(int limite){
		this.limite = limite;
		this.ponteiro = Funcoes.getRandom(limite);
	}
	
	public int getIndex(int i){
		return ponteiro + i >= limite ? ponteiro + i - limite : ponteiro + i; 
	}
}
