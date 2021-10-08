
/*********************************************************************************
*
* Class Name: Jugador
* Author/s name: Alberto García Aparicio, Laura Morales Caro
* Release/Creation date: 18/03/2020
* Class version: 1.0
* Class description: En esta clase tenemos los atributos de un jugador. Este va a 
* tener los atributos ira, experiencia y misiones. También tendremos los setters 
* y getters correspondientes de cada atributo.
*
**********************************************************************************/

public class Jugador extends Thread {

	int ira;
	int experiencia;
	int misiones;
	
	
	public Jugador(int ira, int experiencia, int misiones) {
		super();
		this.ira = ira;
		this.experiencia = experiencia;
		this.misiones = misiones;
	}
	
	public int getIra() {
		return ira;
	}
	public void setIra(int ira) {
		this.ira = ira;
	}
	public int getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}
	public int getMisiones() {
		return misiones;
	}
	public void setMisiones(int misiones) {
		this.misiones = misiones;
	}

	

	

}
