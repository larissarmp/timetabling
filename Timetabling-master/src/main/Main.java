package main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import dados.Container;
import dados.Horario;
import gen.Populacao;
import io.Parser;

public class Main {

	public static void main(String[] args) {
		Container container = null;
		try {
			container = Parser.parseEntrada(new File("entrada" + File.separator + "ag-informacoes.csv"), new File("entrada" + File.separator + "ag-restricoes.csv"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
/*		try {
			PrintStream out = new PrintStream("saida/tabela.html");
			Tabela.escreverTabela(out, Populacao.criarHorario(container).getListaTurma(), "disciplina", "docente", "docente", "listaEstudantes", "listatimeslotT",
					"listatimeslotP");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		Horario[] hs = Populacao.gerarPopulacao(10, container);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(hs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(baos.toByteArray().length);
		
	}

}
