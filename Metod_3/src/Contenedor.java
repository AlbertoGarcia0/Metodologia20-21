/*********************************************************************************
*
* Class Name: Contenedor
* Author/s name: Alberto García Aparicio, Laura Morales Caro
* Release/Creation date: 10/04/2021
* Class version: 1.0
* Class description: En esta clase tenemos los atributos de un contenedor. Este va a 
* tener los atributos capacidad y transporteRealizado. También tendremos los setters 
* y getters correspondientes de cada atributo.
*
**********************************************************************************/

public class Contenedor {
	double capacidad;
	boolean transporteRealizado;

	
	public Contenedor(double capacidad, boolean transporteRealizado) {
		this.capacidad = capacidad;
		this.transporteRealizado = transporteRealizado;

	}

	public double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(double capacidad) {
		this.capacidad = capacidad;
	}
	
	public boolean isTransporteRealizado() {
		return transporteRealizado;
	}

	public void setTransporteRealizado(boolean transporteRealizado) {
		this.transporteRealizado = transporteRealizado;
	}
}
