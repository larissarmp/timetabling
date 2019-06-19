package dados;

public enum Turno {
	MATUTINO(1), VESPERTINO(2), MATUTINO_VESPERTINO(3), NOTURNO(4), MATUTINO_NOTURNO(5), VESPERTINO_NOTURNO(6),
		MATUTINO_VESPERTINO_NOTURNO(7);
	
	private int codigo;
	
	private Turno(int codigo){
		this.codigo = codigo;
	}
	
	public static Turno getTipo(int codigo){
		if(codigo > 0 && codigo < 7) return Turno.values()[codigo -1];
		else return null;
	}
	
	public int getCodigo(){
		return this.codigo;
	}

	public static boolean isCompativel(int slot, Turno turno) {
		if(turno == null) return false;
		int t = turnoPorSlot(slot);
		if(t > 0){
			String bin = Integer.toBinaryString(turno.getCodigo());
			if(t <= bin.length() && bin.charAt(bin.length() - t) == '1') return true;
		}
		return t == 7;
	}
	
	private static int turnoPorSlot(int slot){
		if(slot >= 145){
			if(slot >= 152 && slot <= 156)
				return 7;
		}else{
			slot = (slot - 1) % 24;
			if(slot >= 7){
				if(slot < 12) return 1;
				else if(slot >= 13){
					if(slot < 18 ) return 2;
					else if(slot < 22) return 3;
				}
			}
		}
		return 0;
	}
}
