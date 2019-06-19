package dados;

import java.io.Serializable;

public class Timeslot implements Comparable<Timeslot>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7060217193594395701L;
	private int id;
	private int diaSemana;
	private int horario;
	
	private Turno turno;
	
	private boolean disponivel;
	
	protected Timeslot(int id){
		this.id = id;
	}
	
	public Timeslot(int id, int diaSemana, int horario) {
		this.id = id;
		this.diaSemana = diaSemana;
		this.horario = horario;
		this.disponivel = true;
		if(horario >= 7){
			if(horario < 12) turno = Turno.MATUTINO;
			else if(horario >= 13 && horario < 18) turno = Turno.VESPERTINO;
			else if(horario >= 18 && horario < 22) turno = Turno.NOTURNO;
		}
	}
	public int getId() {
		return id;
	}
	public int getDiaSemana() {
		return diaSemana;
	}
	public int getHorario() {
		return horario;
	}
	public boolean isDisponivel() {
		return disponivel;
	}
	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	public Turno getTurno() {
		return turno;
	}

	@Override
	public int compareTo(Timeslot o) {
		return Integer.compare(id, o.getId());
	}

	@Override
	public String toString() {
		return String.format("Timeslot [id=%s, diaSemana=%s, horario=%s, turno=%s, disponivel=%s]", id, diaSemana,
				horario, turno, disponivel);
	}
}
