/*********************************************************************************
*
* Class Name: Principal
* Author/s name: Alberto Garc�a Aparicio, Laura Morales Caro
* Release/Creation date: 07/05/2021
* Class version: 1.0
* Class description: 
*
**********************************************************************************/

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class Principal {

	// ALMACENAR DATOS RESPECTO A LA SOLUCION

	private boolean solucion = false;
	ArrayList<String> arraySoluciones2 = new ArrayList<String>();
	ArrayList<String> arraySoluciones3 = new ArrayList<String>();
	ArrayList<String> arraySoluciones4 = new ArrayList<String>();
	ArrayList<String> arraySoluciones5 = new ArrayList<String>();
	ArrayList<String> arraySoluciones6 = new ArrayList<String>();

	// MOVIMIENTOS
	public static final int SUMA = 1;
	public static final int MULTIPLICACION = 2;
	public static final int RESTA = 3;
	public static final int DIVISION = 4;



	public boolean getSolucion() {
		return solucion;
	}

	/*********************************************************************************
	* Method Name: main
	* Method description: En el m�todo main pediremos que se selecciones la cantidad
	* de numeros grandes que el jugador va a desear. Despu�s imprimiremos el n�mero objetivo  
	* y llamaremos al m�todo GenerarNumerosParaJugar para, como su nombre indica, generar
	* los n�meros con los que el jugador va a hacer la solucion. Se llama al m�todo inicio
	* que ser� el que empice la simulaci�n de encontrar la soluci�n y por �ltimo se eliminaran
	* las soluciones duplicadas y se imprimiran los datos con el metodo ImprimirDatos.
	**********************************************************************************/	 
	
	public static void main(String[] args) {
		Principal partida = new Principal();
		Scanner teclado = new Scanner(System.in);
		int numerosGrandes;
		int[] conjuntoGrandes = { 25, 50, 75, 100 };
		int[] conjuntoPeque�os = { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10 };
		int[] numeroJugador = new int[6];
		int decrement = 0;

		int objetivo = (int) (Math.random() * (999 - 101)) + 101;

		do {
			System.out.println("Seleccione el n�mero de numeros grandes:");
			numerosGrandes = teclado.nextInt();
		} while (numerosGrandes < 0 || numerosGrandes > 4);
		System.out.println("\nEl n�mero objetivo es: " + objetivo);
		

		GenerarNumerosParaJugar(numerosGrandes, conjuntoGrandes, conjuntoPeque�os, numeroJugador);
		
			
		long tiempoInicio = System.nanoTime();

		partida.inicio(numeroJugador, objetivo);
		
		if (partida.getSolucion() == false) {
			noEncontrado(partida, numeroJugador, decrement, objetivo);
		}
		limpiarDuplicados(partida.arraySoluciones2, partida.arraySoluciones3, partida.arraySoluciones4, partida.arraySoluciones5, partida.arraySoluciones6);
		ImprimirDatos(partida, tiempoInicio);

		teclado.close();
	}
	
	/*********************************************************************************
	* Method Name: GenerarNumerosParaJugar
	* Method description: Este m�todo se encargar� de generar los n�meros aleatorios que 
	* el jugador tiene para crear la soluci�n. Primero veremos el n�mero de numeros grandes
	* que el jugador ha elegido y se generar� aleatoriamente eligiendo una posicion en el 
	* vector de conjuntos grandes. Despu�s, de igual forma, se generar�n los numeros peque�os.
	* Estos ser�n 6 menos el n�mero elegido de numeros grandes. Se mostrar� por pantalla
	* los numeros que se tiene para jugar.
	**********************************************************************************/	

	private static void GenerarNumerosParaJugar(int numerosGrandes, int[] conjuntoGrandes, int[] conjuntoPeque�os,int[] numeroJugador) {
		int posicion;
		int valor;
		String numeros = "";

		if (numerosGrandes != 0) {
			for (int i = 0; i < numerosGrandes; i++) {
				do {
					posicion = new Random().nextInt(4);
					valor = conjuntoGrandes[posicion];
					numeroJugador[i] = conjuntoGrandes[posicion];
					conjuntoGrandes[posicion] = 0;
				} while (valor == 0);
				numeros += valor + " ";
			}
		}

		for (int i = numerosGrandes; i < 6; i++) {
			do {
				posicion = new Random().nextInt(20);
				valor = conjuntoPeque�os[posicion];
				numeroJugador[i] = conjuntoPeque�os[posicion];
				conjuntoPeque�os[posicion] = 0;
			} while (valor == 0);
			numeros += valor + " ";
		}

		System.out.println("Los numeros con los que se va a jugar son : " + numeros);
	}

	
	public void inicio(int[] numeros, int objetivo) {
		solucion = false;
		buscarSubvectores(numeros, objetivo);
	}
	
	/*********************************************************************************
	* Method Name: buscarSubvectores
	* Method description: En el m�todo buscarSubvectores se generar�n 2^n subvectores 
	* disponibles. Se guardar�n todos los subvectores en el arrayList arraySubvectores 
	* en  binario. A continuaci�n, se crear� un vector del tama�o del arrayList y se 
	* almacenaran todos los valores del arrayList en el vector subVector. Una vez tengamos
	*  los valores  en el subvector llamaremos al m�todo permutarSubvectores pasando 
	*  el vector, el n�mero  objetivo y en la etapa en la que nos encontremos, en este caso 0.
	**********************************************************************************/	
	
    private void buscarSubvectores(int numerosJugador[], int objetivo){
        ArrayList<Integer> arraySubvectores = new ArrayList<Integer>();
        int[] subVector;
      
        for (int i = 0; i < Math.pow(2, numerosJugador.length); i++){
        	
            for (int j = 0; j < numerosJugador.length; j++) {
            	//Binario
                if ((i & (1 << j)) > 0) {
                    arraySubvectores.add(numerosJugador[j]);
                }
            }
            
            subVector = new int[arraySubvectores.size()];
            int contador=0;

            for (Integer s : arraySubvectores) {
            	subVector[contador]=s;
            	contador++;
            }
            
            if(subVector.length!=0)
            permutarSubvectores(subVector, objetivo, 0);
            
            arraySubvectores.clear();
        }
    } 

	/*********************************************************************************
	* Method Name: permutarSubvectores
	* Method description: Para poder calcular todas las soluciones posibles con los n�meros
	* que tenemos, lo haremos mediante permutaciones sin repetici�n. Esto se har� de forma
	* recursiva. Si hemos hecho todas las permutaciones llamaremos al m�todo operar. Por lo
	* contrario, si todavia nos queda por permutar pasaremos al else en el que en cada etapa  
	* k , colocamos en la posici�n  k  del vector soluci�n uno de los  N  elementos de entrada.
	**********************************************************************************/	
    
	private void permutarSubvectores(int[] subVector, int objetivo, int etapa) {
		int[] operaciones= new int [subVector.length - 1];
		int intercambio ,intercambio2;
		//Si se ha permutado tantas veces como numeros tiene el vector se realizan las operaciones
		if (etapa == subVector.length) {
			operar(subVector, operaciones, objetivo, 0);
		} 
		
		else {			
			for (int i = etapa; i < subVector.length; i++) {
				intercambio = subVector[i];
				intercambio2 = subVector[etapa];
				subVector[i] = intercambio2;
				subVector[etapa] = intercambio;
				permutarSubvectores(subVector, objetivo, etapa + 1);
			}
		}
		
	}
	
	/*********************************************************************************
	* Method Name: operar
	* Method description: En este m�todo asignaremos una posible operacion a realizar 
	* recursivamente para asignar todas las posibles operaciones al conjunto de numeros
	* sobre el que se esta trabajando, posicion + 1 para pasar a la siguiente cifra de 
	* los numeros y posicion - 1 para almacenar la operacion a realizar. Una vez hayamos 
	* asignado todas las operaciones, se ira realizando las operaciones, estas las realizaremos
	* mediante un switch en el que cada caso es una operacion. Si se encuentra una soluci�n que coincida
	* con el n�mero objetivo, se guardar� y se reconstruira los pasos que hemos seguido para obtenerla
	* guardandolo en pasosSolucion. Por ultimo, dependiendo del n�mero de elementos utilizados para
	* llegar a la soluci�n se guardara en arraySolucionx, siendo x el numero de elementos utilizados.
	**********************************************************************************/
	
	private void operar(int[] numeros, int[] operacionesARealizar, int numeroObjetivo, int posicion) {

		// comprobar que la posicion no esta en el ultimo numero del vector
		if (posicion != numeros.length) {
			// primera posicion 
			if (posicion == 0) {
				operar(numeros, operacionesARealizar, numeroObjetivo, posicion + 1);
			}
			// posiciones intermedias
			else {
				operacionesARealizar[posicion - 1] = SUMA;
				operar(numeros, operacionesARealizar, numeroObjetivo, posicion + 1);

				operacionesARealizar[posicion - 1] = MULTIPLICACION;
				operar(numeros, operacionesARealizar, numeroObjetivo, posicion + 1);

				operacionesARealizar[posicion - 1] = RESTA;
				operar(numeros, operacionesARealizar, numeroObjetivo, posicion + 1);

				operacionesARealizar[posicion - 1] = DIVISION;
				operar(numeros, operacionesARealizar, numeroObjetivo, posicion + 1);
			}

		}
		else {
			int total = numeros[0];
			for (int i = 1, j = 0; i < numeros.length; i++, j++) {

				switch (operacionesARealizar[j]) {

				case SUMA:
					total = total + numeros[i];
					break;

				case MULTIPLICACION:
					total *= numeros[i];
					break;

				case RESTA:
					if (total - numeros[i] < 0) {
						return;
					}
					total = total - numeros[i];
					break;

				case DIVISION:
					if (total % numeros[i] != 0) {
						return;
					}
					total /= numeros[i];
					break;

				}

			}

			// respuesta buscada econtrada == almacenar
			if (total == numeroObjetivo) {

				String pasosSolucion = "";
				solucion = true;
				
				for (int i = 0; i < numeros.length - 1; i++) {
					pasosSolucion=pasosSolucion+(numeros[i]);
					
					switch (operacionesARealizar[i]) {
					
					case SUMA:
						pasosSolucion=pasosSolucion+(" + ");
						break;
						
					case RESTA:
						pasosSolucion=pasosSolucion+(" - ");
						break;
						
					case MULTIPLICACION:
						pasosSolucion=pasosSolucion+(" * ");
						break;
						
					case DIVISION:
						pasosSolucion=pasosSolucion+(" / ");
						break;
						
					}
				}
				pasosSolucion=pasosSolucion+(numeros[numeros.length - 1]);
				
				if (numeros.length == 2)
					arraySoluciones2.add(pasosSolucion);
				if (numeros.length == 3)
					arraySoluciones3.add(pasosSolucion);
				if (numeros.length == 4)
					arraySoluciones4.add(pasosSolucion);
				if (numeros.length == 5)
					arraySoluciones5.add(pasosSolucion);
				if (numeros.length == 6)
					arraySoluciones6.add(pasosSolucion);
				
				
			}
			
		}
	}

	/*********************************************************************************
	* Method Name: noEncontrado
	* Method description: Si no se encuentra una soluci�n en la que se llegue al n�mero
	* objetivo, se buscara aquella que se acerque m�s a este. Mediante un bucle do
	* iremos buscando aquella solucion que se acerque m�s, el bucle acabar� una vez
	* se haya encontrado (partida.getSolucion = True).
	**********************************************************************************/

	private static void noEncontrado(Principal partida, int[] numeroJugador, int diferenciaRespectoObjetivo, int objetivo) {
		System.out.println();
		do {
			diferenciaRespectoObjetivo++;
			partida.inicio(numeroJugador, objetivo - diferenciaRespectoObjetivo);
			partida.inicio(numeroJugador, objetivo + diferenciaRespectoObjetivo);
		} while (partida.getSolucion() == false);
		System.out.println("El numero mas proximo al que he podido llegar tiene una diferencia de " + diferenciaRespectoObjetivo+ " respecto al objetivo");
	}

	/*********************************************************************************
	* Method Name: ImprimirDatos
	* Method description: En este m�todo nos encargaremos de imprimir las diferentes
	* soluciones encontradas. Primero imprimiremos el n� de soluciones encontradas, luego
	* las m�s eficientes, es decir, aquellas soluciones que tengan la menor cantidad de 
	* n�meros utilizados para calcular el n�mero objetivo y por �ltimo el resto de 
	* soluciones que lleguen a la soluci�n.
	**********************************************************************************/

	private static void ImprimirDatos(Principal partida, long tiempoInicio) {
		ArrayList<String> arraySoluciones2 = partida.arraySoluciones2;
		ArrayList<String> arraySoluciones3 = partida.arraySoluciones3;
		ArrayList<String> arraySoluciones4 = partida.arraySoluciones4;
		ArrayList<String> arraySoluciones5 = partida.arraySoluciones5;
		ArrayList<String> arraySoluciones6 = partida.arraySoluciones6;
		boolean eficiente = false, enviado = false;

		System.out.println("\nLa cantidad de soluciones entcontradas son: "+(arraySoluciones2.size()+arraySoluciones3.size()+arraySoluciones4.size()+arraySoluciones5.size()+arraySoluciones6.size()));
		System.out.println("\nDistintas formas de llegar al objetivo de la forma mas eficiente: ");
		//2 numeros
		for (String s : arraySoluciones2) {
            System.out.println(s);
            eficiente =true;
        }
		if(eficiente==true && enviado==false) {
			System.out.println("\nOtras formas menos eficientes:");
			enviado=true;
		}
		//3 numeros
		for (String s : arraySoluciones3) {
            System.out.println(s);
            eficiente =true;
        }
		if(eficiente==true && enviado==false) {
			System.out.println("\nOtras formas menos eficientes:");
			enviado=true;
		}
		//4 numeros
		for (String s : arraySoluciones4) {
            System.out.println(s);
            eficiente =true;
        }
		if(eficiente==true && enviado==false) {
			System.out.println("\nOtras formas menos eficientes:");
			enviado=true;
		}
		//5 numeros
		for (String s : arraySoluciones5) {
            System.out.println(s);
            eficiente =true;
        }
		if(eficiente==true && enviado==false) {
			System.out.println("\nOtras formas menos eficientes:");
			enviado=true;
		}
		//6 numeros
		for (String s : arraySoluciones6) {
            System.out.println(s);
            eficiente =true;
        }
		if(eficiente==true && enviado==false) {
			System.out.println("\nOtras formas menos eficientes:");
			enviado=true;
		}

		System.out.println("\nTiempo que se ha tardado en encontrar la soluci�n en nanosegundos: "+ (System.nanoTime() - tiempoInicio + "\n"));
	}

	/*********************************************************************************
	* Method Name: limpiarDuplicados
	* Method description: Como guardamos todas las formas posibles a las que se puede llegar
	* a la solucion, algunas veces nos encontraremos que tenemos formas iguales por lo que 
	* eliminaremos aquellas formas-soluciones repetidas que tengamos mediante este m�todo.
	**********************************************************************************/
	
	private static void limpiarDuplicados(ArrayList<String> arraySoluciones2, ArrayList<String> arraySoluciones3,ArrayList<String> arraySoluciones4, 
			ArrayList<String> arraySoluciones5, ArrayList<String> arraySoluciones6) {
		Set<String> hashSet2 = new HashSet<String>(arraySoluciones2);
		arraySoluciones2.clear();
		arraySoluciones2.addAll(hashSet2);
		
		Set<String> hashSet3 = new HashSet<String>(arraySoluciones3);
		arraySoluciones3.clear();
		arraySoluciones3.addAll(hashSet3);
		
		Set<String> hashSet4 = new HashSet<String>(arraySoluciones4);
		arraySoluciones4.clear();
		arraySoluciones4.addAll(hashSet4);
		
		Set<String> hashSet5 = new HashSet<String>(arraySoluciones5);
		arraySoluciones5.clear();
		arraySoluciones5.addAll(hashSet5);
		
		Set<String> hashSet6 = new HashSet<String>(arraySoluciones6);
		arraySoluciones6.clear();
		arraySoluciones6.addAll(hashSet6);
	}

	

}