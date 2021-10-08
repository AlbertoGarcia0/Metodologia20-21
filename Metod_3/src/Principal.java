/*************************************************************************************
*
* Class Name: Principal
* Author/s name: Alberto García Aparicio, Laura Morales Caro
* Release/Creation date: 10/04/2021
* Class version: 1.0
* Class description: La clase Principal se encargará de generar los diferentes contenedores
* y Eopies cada uno con su volumen correspondiente. En esta clase también tendremos un algoritmo
* voraz para la asignación de los contenedores a los Eopies para así repartir la mayor cantidad
* de agua entre la población. Además tendremos dos métodos para ver cuales han sido los contenedores
* que no se han podido repartir y los eopies que no han podido realizar ningún transporte
*
**************************************************************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Principal {
	static Scanner teclado = new Scanner(System.in);
	
	/*************************************************************************************
	*
	* Method Name: main
	* Method description: En el método main pediremos que se introduzcan la cantidad de 
	* contenedores y eopies que vamos a tener. Después se llamará al metodoPrincipal que 
	* será el que hará el algoritmo voraz y se irá imprimiendo un informe día a dia y por 
	* último se imprimirá un informe con la cantidad de litros repartidos en total.
	*
	**************************************************************************************/

	public static void main(String[] args) {
		int cantidadContenedores = 0;
		int cantidadEopie =0;
		double  cantidadTotal=0;
		
		
		System.out.println("Introduce la cantidad de contenedores:");
		cantidadContenedores = teclado.nextInt();

		do{
			System.out.println("Introduce la cantidad de Eopies:");
			cantidadEopie = teclado.nextInt();
		}while(cantidadContenedores<=cantidadEopie);
		
		for(int i=1; i<8;i++) {
			
			System.out.println("~~~~~~~~~~~~~~~~ DIA "+i+" ~~~~~~~~~~~~~~~~");
			cantidadTotal+=metodoPrincipal(cantidadContenedores, cantidadEopie,i);
		
		}
		System.out.println("~~~~~~~~~~~~~~~~ Informe ~~~~~~~~~~~~~~~~");
		System.out.println("La cantidad total de litros repartidos es: "+cantidadTotal+" litros");
		
		
		
	  }
	
	/*************************************************************************************
	*
	* Method Name: metodoPrincipal
	* Method description: En  el metodoPrincipal lo primero que hacemos es llamar al método 
	* Crear_Contenedores y Crear_Eopies a los que le pasamos la cantidad de cada uno pedida 
	* anteriormente y un arrayList, este método nos devolverá una cadena con la información 
	* correspondiente a cada uno y se imprimirá por pantalla. Después ordenaremos las listas 
	* en base a su capacidad en el caso de los contenedores y a la carga máxima en los eopies. 
	* Le pasaremos las listas ordenadas al método Transporte y se imprimirá los litros 
	* transportados en ese día y los eopies y contenedores que no han podido ser transportados 
	* en ese día.
	* 
	**************************************************************************************/

	public static double metodoPrincipal(int cantidadContenedores, int cantidadEopie, int dia) {
		double cantidadTransportada;
		String infoEopies;
		String infoContenedores, cadenaContenedoresNoTransportado, cadenaEopiesNoTransportado;
		ArrayList<Contenedor> arrayContenedores = new ArrayList<Contenedor>();
		ArrayList<Eopie> arrayEopies = new ArrayList<Eopie>();
		
		
		infoContenedores=Crear_Contenedores(cantidadContenedores, arrayContenedores);
		infoEopies=Crear_Eopies(cantidadEopie, arrayEopies);
		
		System.out.println(infoContenedores);
		System.out.println(infoEopies);		
		
		Collections.sort(arrayContenedores,(a, b) -> Double.compare(b.capacidad, a.capacidad));
		Collections.sort(arrayEopies,(a, b) -> Double.compare(b.cargaMaxima, a.cargaMaxima));
		long tiempoInicio = System.nanoTime();
		cantidadTransportada=Tranporte(arrayContenedores, arrayEopies);
		long tiempo = System.nanoTime() - tiempoInicio;
		System.out.println("El tiempo empleado en el algoritmo voraz es:" + tiempo);
		System.out.println("\nCantidad transportada el dia "+dia+" es de "+cantidadTransportada+" litros\n");
		cadenaEopiesNoTransportado=EopiesNoTransportes(arrayEopies);
		System.out.println(cadenaEopiesNoTransportado);
		cadenaContenedoresNoTransportado=ContenedoresNoTransportados(arrayContenedores);
		System.out.println(cadenaContenedoresNoTransportado);
		
		
		return cantidadTransportada;
	}
	
	/*************************************************************************************
	*
	* Method Name: Crear_Contenedores
	* Method description: En este método nos encargaremos de generar la cantidad de litros 
	* que va en el contenedor (entre 1 y 50) y le pasaremos tambien el valor false al atributo
	* transporteRealizado.
	*
	**************************************************************************************/
	
	public static  String Crear_Contenedores(int cantidadContenedores, ArrayList<Contenedor> arrayContenedores) {
		String cadena = "";
		for (int i = 0; i < cantidadContenedores; i++) {
			int litros = new Random().nextInt(51);
			Double litrosDouble = new Random().nextDouble();
			Contenedor contenedor = new Contenedor(litros+litrosDouble, false);
			cadena+="\nContenedor "+i+" con un volumen de: "+contenedor.capacidad;
			arrayContenedores.add(contenedor);
		}
		return cadena;
	}
	
	/*************************************************************************************
	*
	* Method Name: Crear_Eopies
	* Method description: En este método nos encargaremos de generar la cantidad de litros 
	* que va a poder soportar el eopie (entre 1 y 50) y le pasaremos tambien el valor false 
	* al atributo transporteRealizado.
	*
	**************************************************************************************/

	public static String Crear_Eopies(int cantidadEopie, ArrayList<Eopie> arrayEopies) {
		String cadena = "";
		for (int i = 0; i < cantidadEopie; i++) {
			int capacidadLitros = new Random().nextInt(51);
			Double capacidadLitrosDouble = new Random().nextDouble();
			Eopie eopies = new Eopie(capacidadLitros+capacidadLitrosDouble, false);
			cadena+="\nEopie "+i+" con un volumen de: "+eopies.cargaMaxima;
			arrayEopies.add(eopies);
		}
		return cadena;
	}
	
	
	/*************************************************************************************
	*
	* Method Name: Tranporte
	* Method description: Este método contendrá la función solución de nuestro algoritmo 
	* voraz. Recorreremos los arrays eopie y contenedores con el for y veremos si la carga
	* del eopie es mayor o igual al contenedor y todavia no se ha realizado el transporte 
	* de ese contenedor entonces ese eopie se encargará de transportar ese contenedor y le 
	* daremos el valor true al atributo Transporte realizado en los dos. Tambien iremos 
	* guardando la cantidad transportada de todos los eopies en la variable cantidadTransportada 
	*
	**************************************************************************************/

	public static double Tranporte(ArrayList<Contenedor> arrayContenedores,ArrayList<Eopie> arrayEopies) {
		double cantidadTransportada=0;
		for(int i = 0; i<arrayEopies.size();i++) {
			for(int j = 0; j<arrayContenedores.size();j++) {
				if(arrayEopies.get(i).getCargaMaxima()>=arrayContenedores.get(j).getCapacidad() && arrayContenedores.get(j).isTransporteRealizado()==false ) {
					arrayEopies.get(i).setTransporteRealizado(true);
					arrayContenedores.get(j).setTransporteRealizado(true);
					cantidadTransportada+=arrayContenedores.get(j).getCapacidad();
					break;
				}	
			}
		}

		return cantidadTransportada;
	}

	/*************************************************************************************
	*
	* Method Name: ContenedoresNoTransportados
	* Method description: Una vez Una vez realizado el transporte del agua de cada noche, 
	* veremos que contenedores no han sido transportados viendo si el atributo TransporteRealizado
	* está a false.
	*
	**************************************************************************************/
	
	public static String ContenedoresNoTransportados(ArrayList<Contenedor> arrayContenedores) {
		String cadenaContenedoresNoTransportado="";
		
		for(int i=0; i<arrayContenedores.size();i++) {
			if(arrayContenedores.get(i).isTransporteRealizado()==false)
				cadenaContenedoresNoTransportado+="El contenedor "+i+" con un volumen de: "+arrayContenedores.get(i).getCapacidad()+" no ha podido ser transportado\n";
		}
		return cadenaContenedoresNoTransportado;
	}

	/*************************************************************************************
	*
	* Method Name: EopiesNoTransportes
	* Method description: Una vez Una vez realizado el transporte del agua de cada noche, 
	* veremos que eopies no han transportado ningun contenedor viendo si el atributo 
	* TransporteRealizado está a false.
	*
	**************************************************************************************/

	public static String EopiesNoTransportes( ArrayList<Eopie> arrayEopies) {
		String cadenaEopiesNoTransportado="";
		
		for(int i=0; i<arrayEopies.size();i++) {
			if(arrayEopies.get(i).isTransporteRealizado()==false)
				cadenaEopiesNoTransportado+="El Eopie "+i+" con una capacidad de: "+arrayEopies.get(i).getCargaMaxima()+" no ha podido realizar ningun transporte\n";
		}
		return cadenaEopiesNoTransportado;
	}
	
}