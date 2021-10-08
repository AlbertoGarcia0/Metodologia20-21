/*************************************************************************************
*
* Class Name: AmongUs
* Author/s name: Alberto García Aparicio, Laura Morales Caro
* Release/Creation date: 18/03/2020
* Class version: 1
* Class description: La clase AmongUs se encargará de generar los jugadores, seleccionar
*  a un jugador como impostor de manera aleatoria y encontrar a ese impostor de entre 
*  todos los tripulantes. Una vez tengamos al impostor comprobaremos el ganador final.
*
**************************************************************************************/
import java.util.*;

public class Amongus {
	private static Jugador vectorJugadores[] = null;
	static int contador = 0;
	static Scanner teclado = new Scanner(System.in);

	
	public static void main(String args[]) {
		int cantidadJugadores = generarJugadores();
		long tiempoInicio = System.nanoTime();
		int impostorUbi = recursividad(0, cantidadJugadores - 1);
		long tiempo = System.nanoTime() - tiempoInicio;
		// Ver contra quien se va a comparar el traidor
		int contrincante = posicionContrincante(impostorUbi);
		imprimirInformacionPosTareasExp(impostorUbi, contrincante, tiempo);
		ganador(impostorUbi, contrincante);
	}
	
	/*********************************************************************************
	 * Method Name: generarJugadores
	 * Method description: En este método lo que haremos será pedir la cantidad de jugadores
	 * que van a participar, a todos les daremos una experiencia de entre 0 y 5 y unas misiones
	 * de entre 0 y 8 y meteremos a todos los jugadores en nuestro vectorJugadores. Una vez
	 * tengamos todos nuestros jugadores cogeremos a uno aleatoriamente y le actualizaremos
	 * la ira a 2.
	 **********************************************************************************/
	
	public static int generarJugadores() {
		int cantidadJugadores;
		System.out.println("Introduce la cantidad de jugadores:");
		cantidadJugadores = teclado.nextInt();

		vectorJugadores = new Jugador[cantidadJugadores];

		for (int i = 0; i < vectorJugadores.length; i++) {
			int experiencia = new Random().nextInt(6);
			int misiones = new Random().nextInt(9);
			Jugador jugador = new Jugador(1, experiencia, misiones);
			vectorJugadores[i] = jugador;
		}
		int impostor = new Random().nextInt(cantidadJugadores);
		vectorJugadores[impostor].setIra(2);
		return cantidadJugadores;
	}

	/*********************************************************************************
	 * Method Name: recursividad
	 * Method description: El impostor sabemos que esta entre el rango 0 y n por lo
	 * que pondremos el inicio a 0 y el final a n para ir buscando al impostor mediante divisiones
	 * entre dos. Si la longitud de jugadores es impar se mide la izquierda y la derecha
	 * sin contar el del medio, en el caso de que la ira sea igual en los dos lados el impostor es
	 * el del medio, si no es el caso seleccionaremos la parte que tenga mas ira para seguir haciendo
	 * la recursividad. En caso de ser par se mide simplemente los dos lados y se va al que tenga mas ira
	 * para seguir la recursividad.
	 **********************************************************************************/
	public static int recursividad(int iz, int der) {
		int iraIz, iraDer, posicionTraidor = 0;
		System.out.println("Vamos a ver las posiciones siguientes: "+iz+" a "+der);
		//SOLO 1 POSICIÓN
		if (iz == der) {
			posicionTraidor = iz;
			System.out.println("Solo queda una posición donde puede estar el impostor");
		} else {
			int mitad = (iz + der) / 2;
			iraDer = medidorIra(mitad + 1, der);
			// PAR
			if (((der + 1) - iz) % 2 != 0) {
				iraIz = medidorIra(iz, mitad - 1);
				contador++;
				if (iraIz > iraDer)
					posicionTraidor = recursividad(iz, mitad - 1);
				else if (iraIz < iraDer)
					posicionTraidor = recursividad(mitad + 1, der);
				else {
					posicionTraidor = mitad;
					System.out.println("El impostor esta en el medio");
				}
			}
			// IMPAR
			else {
				iraIz = medidorIra(iz, mitad);
				contador++;
				if (iraIz > iraDer)
					posicionTraidor = recursividad(iz, mitad);
				else if (iraIz < iraDer)
					posicionTraidor = recursividad(mitad + 1, der);
			}
		}
		return posicionTraidor;
	}

	/*********************************************************************************
	 * Method Name: medidorIra
	 * Method description: En este método iremos sumando las iras de los jugadores y las
	 * guardaremos en el contadorIra, que es lo que pasaremos.
	 **********************************************************************************/
	
	public static int medidorIra(int iz, int der) {
		int contadorIra = 0;

		for (int i = iz; i <= der; i++) {
			contadorIra += vectorJugadores[i].getIra();
		}
		return contadorIra;
	}

	/*********************************************************************************
	 * Method Name: posicionContrincante
	 * Method description: En este método buscamos la posición del tripulante que será 
	 * con quien compararemos al impostor. Si el impostor está en la última posición 
	 * del array, el contrincante será el primer tripulante del array. En caso contrario, 
	 * el contrincante será el tripulante de la derecha del impostor.
	 **********************************************************************************/
	
	public static int posicionContrincante(int impostorUbi) {
		int contrincante;

		if (impostorUbi == vectorJugadores.length - 1) {
			contrincante = 0;
		} else {
			contrincante = impostorUbi + 1;
		}
		return contrincante;
	}

	
	public static void imprimirInformacionPosTareasExp(int impostorUbi, int contrincante, long tiempo) {
		System.out.println("\nPosición en el que se encuentra el impostor: " + impostorUbi);
		System.out.println("Numero de tareas realizadas por impostor: " + vectorJugadores[impostorUbi].getMisiones());
		System.out.println("Cantidad de experiencia del impostor: " + vectorJugadores[impostorUbi].getExperiencia() + "\n");
		System.out.println("Posición en el que se encuentra el tripulante: " + contrincante);
		System.out.println("Numero de tareas realizadas por tripulante: " + vectorJugadores[contrincante].getMisiones());
		System.out.println("Cantidad de experiencia del tripulante: " + vectorJugadores[contrincante].getExperiencia() + "\n");
		System.out.println("El número de veces que se ha utilizado el medidor de ira es: " + contador);
		System.out.println("Tiempo que se ha tardado en encontrar al impostor en nanosegundos: " + tiempo + "\n");
	}

	/*********************************************************************************
	 * Method Name: ganador
	 * Method description: A este método le pasamos la posición del impostor en el vector
	 * Jugadores y la posición del contrincante. Obtenemos las misiones del impostor con
	 * vectorJugadores[impostorUbi].getMisiones() y las del contrincante con
	 * vectorJugadores[contrincante].getMisiones(), si el impostor tiene mas misiones que
	 * el tripulante, ganará el impostor. En caso contrario, ganará la tripulación. Si el
	 * impostor y el tripulante tienen el mismo número de misiones se mirará la experiencia
	 * y se aplicara la misma regla, gana quien tiene mas experiencia. Si tienen la misma
	 * experiencia le daremos la victoria a la tripulación.
	 **********************************************************************************/
	
	public static void ganador(int impostorUbi, int contrincante) {
		if (vectorJugadores[impostorUbi].getMisiones() > vectorJugadores[contrincante].getMisiones()) {
			System.out.println("Gana el impostor");
		} else if (vectorJugadores[impostorUbi].getMisiones() < vectorJugadores[contrincante].getMisiones()) {
			System.out.println("Gana la tripulación");
		} else {
			if (vectorJugadores[impostorUbi].getExperiencia() > vectorJugadores[contrincante].getExperiencia()) {
				System.out.println("Gana el impostor");
			} else if (vectorJugadores[impostorUbi].getExperiencia() < vectorJugadores[contrincante].getExperiencia()) {
				System.out.println("Gana la tripulación");
			} else {
				System.out.println("Gana la tripulación");
			}
		}
	}

}