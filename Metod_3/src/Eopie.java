/*********************************************************************************
*
* Class Name: Eopie
* Author/s name: Alberto García Aparicio, Laura Morales Caro
* Release/Creation date: 10/04/2021
* Class version: 1.0
* Class description: En esta clase tenemos los atributos de un Eopie. Este va a 
* tener los atributos cargaMaxima y transporteRealizado. También tendremos los setters 
* y getters correspondientes de cada atributo.
*
**********************************************************************************/

public class Eopie {
	double cargaMaxima;	
	boolean transporteRealizado;
	
	

	public Eopie(double cargaMaxima,boolean transporteRealizado) {
		this.cargaMaxima = cargaMaxima;
		this.transporteRealizado = transporteRealizado;
	}
	
	public double getCargaMaxima() {
		return cargaMaxima;
	}

	public void setCargaMaxima(double cargaMaxima) {
		this.cargaMaxima = cargaMaxima;
	}
	
	public boolean isTransporteRealizado() {
		return transporteRealizado;
	}

	public void setTransporteRealizado(boolean transporteRealizado) {
		this.transporteRealizado = transporteRealizado;
	}
 
}
