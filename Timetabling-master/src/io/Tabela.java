package io;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Tabela {
	public static void escreverTabela(PrintStream out, List<? extends Object> lista, String...campos) {
		List<String> listCab = new LinkedList<>(Arrays.asList(campos));
		listCab.add(0, "indice");
		String cab[] = listCab.toArray(new String[0]);
		out.print("<html><head><title>Tabela</title><head>"
				+ "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u' crossorigin='anonymous'>"
				+ "<body>");
		out.print("<table class='table table-bordered'>"
					+ "<thead>"
						+ getRow(cab)
					+ "</thead>");
		out.print("<tbody>");
		for(int i = 0; i < lista.size(); i++){
			String[] linha = lerLinha(lista.get(i), i, campos);
			out.print(getRow(linha));
		}
		out.print("</tbody></table></body></html>");
	}
	public static void escreverTabela(PrintStream out, Object[] lista, String...campos) {
		List<String> listCab = new LinkedList<>(Arrays.asList(campos));
		listCab.add(0, "indice");
		String cab[] = listCab.toArray(new String[0]);
		out.print("<html><head><title>Tabela</title><head>"
				+ "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u' crossorigin='anonymous'>"
				+ "<body>");
		out.print("<table class='table table-bordered'>"
					+ "<thead>"
						+ getRow(cab)
					+ "</thead>");
		out.print("<tbody>");
		for(int i = 0; i < lista.length; i++){
			String[] linha = lerLinha(lista[i], i, campos);
			out.print(getRow(linha));
		}
		out.print("</tbody></table></body></html>");
	}
	public static String[] lerLinha(Object bean, int index, String... campos){
		String[] linha = new String[campos.length + 1];
		if(bean == null){
			Arrays.fill(linha, "");
			linha[0] = String.valueOf(index);
			return linha;
		}
		linha[0] = String.valueOf(index);
		for(int i = 1; i <= campos.length; i++){
			try {
				Field f = bean.getClass().getDeclaredField(campos[i - 1]);
				f.setAccessible(true);
				Object temp = f.get(bean);
				if(temp.getClass().isArray()){
					if(temp instanceof int[]) linha[i] = Arrays.toString((int[]) temp);
					else linha[i] = Arrays.toString((Object[]) temp);
				}
				else linha[i] = String.valueOf(temp);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
		return linha;
	}
	
	private static String getRow(String[] row){
		StringBuilder builder = new StringBuilder("<tr>");
		for(String col : row){
			builder.append("<td>").append(col).append("</td>");
		}
		return builder.append("</tr>").toString();
	}
}
