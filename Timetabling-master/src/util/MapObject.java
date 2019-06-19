package util;

import java.io.Serializable;

public class MapObject implements Serializable{
	private static final long serialVersionUID = 6648503058329233399L;
	private Object[] mapa;
	
	public MapObject(){
		mapa = new Object[5];
	}

	public boolean equals(Object o) {
		return mapa.equals(o);
	}

	public int hashCode() {
		return mapa.hashCode();
	}

/*	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		mapa.forEach((Integer key, Object value) -> {
			builder.append(key).append("=");
			if(value.getClass().isArray()) builder.append("ARRAY");
			else builder.append(value);
			builder.append(", ");
		});
		return builder.delete(builder.length() - 2, builder.length()).append("}").toString();
	}*/

	public int size() {
		return mapa.length;
	}

	public boolean isEmpty() {
		return mapa.length == 0;
	}

	public Object get(Object key) {
		if(key instanceof Integer) return mapa[(int) key];
		return null;
	}

	public void put(Integer key, Object value) {
		if(key instanceof Integer) mapa[(int) key] = value;
	}

	public void remove(Object key) {
		if(key instanceof Integer) mapa[(int) key] = null;
	}

	public void replace(Integer key, Object value) {
		put(key, value);
	}
	
}
