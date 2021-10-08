/*********************************************************************************
*
* Class Name: Practica1
* Author/s name: Alberto García Aparicio, Laura Morales Caro
* Release/Creation date: 19/02/2020
* Class version: 1.3
* Class description: En esta clase utilizamos 4 métodos diferentes para calcular la raiz 
* cuadrada de los números de la lista listaNumeros y luego vemos el tiempo empleado en cada 
* método en nano segundos para poder comprobar cual es el método más eficiente.
*
**********************************************************************************/
import java.util.*;

public class Practica1 {
	 
	static Scanner teclado=new Scanner(System.in);  
	 
	 public static void main(String args[]) { 
		 int [] listaNumeros = {1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225,256, 289, 324, 361, 400};

	     long tiempoMetodoUno=0, tiempoMetodoDos = 0, tiempoMetodoTres=0, tiempoMetodoCuatro=0;
	     
		 for (int i = 0; i<listaNumeros.length; i++) {
			 tiempoMetodoUno=tiempoMetodoUno+metodoMath(listaNumeros[i]);
			 tiempoMetodoDos=tiempoMetodoDos+metodoBabilonico(listaNumeros[i]);
			 tiempoMetodoTres=tiempoMetodoTres+metodoBinarySearch(listaNumeros[i]);
			 tiempoMetodoCuatro=tiempoMetodoCuatro+metodoBabilonicoRecursivo(listaNumeros[i]);
		 }
		 
		 System.out.println("Tiempo metodo Math: "+tiempoMetodoUno+ " nanosegundos");
		 System.out.println("Tiempo metodo Babilonico: "+tiempoMetodoDos+ " nanosegundos");
		 System.out.println("Tiempo metodo BinarySearch: "+tiempoMetodoTres+ " nanosegundos");
		 System.out.println("Tiempo metodo Babilonico recursivo: "+tiempoMetodoCuatro+ " nanosegundos");
		 
	 }
	 
	 
/*********************************************************************************
* Method Name: metodoMath
* Method description: En este método calculamos la raiz cuadrada de varios números
* utilizando la función sqrt de la librería Math. LLamamos a la función y le pasamos
* el número del que queremos calcular la raiz. Una vez calculada la raiz la mostaremos 
* por pantalla y devolveremos el tiempo empleado en el calculo de esta.
**********************************************************************************/	 
	 public static long metodoMath(int n) {
		 long tiempoInicio = System.nanoTime();
		 
		 double solucion = (float) Math.sqrt(n);

		 return (System.nanoTime()-tiempoInicio);		
	 }
	 
	 
	//Complejidad de O(log(n))
	 
 /*********************************************************************************
 * Method Name: metodoBabilonico
 * Method description: En este método implementaremos la funcion (a + x^2)/2x. Mientras
 * que "x" menos "y" sea mayor que el índice de error seguiremos caculando la raiz. "x"
 * sera igual a "x" mas "y" partido de dos e "y" será n partido de "x". Cuando "x" menos
 * "y" sea menor que "e" mostraremos por pantalla la raiz cuadrada calculada y devolveremos
 * el tiempo que hemos tardado.
 * **********************************************************************************/
	 public static long metodoBabilonico(int n){ 
		 long tiempoInicio = System.nanoTime();
		 double x = n; 
		 double y = 1; 
		 double e = 0.000001; /*indice de error*/
		 double solucion;
		        
		 while (x - y > e) { 
			x = (x + y) / 2; 
			y = n / x; 
		 }
		 solucion = x;
				
		 return (System.nanoTime()-tiempoInicio);		
	 } 
	 
	 
 /*********************************************************************************
 * Method Name: metodoBinarySearch
 * Method description: La raiz cuadrada sabemos que esta entre el rango 0 y n por lo
 * que pondremos el inicio a 0 y el final a n para ir buscando la raiz mediante divisiones
 * entre dos. Iremos comparando el medio con nuestra raiz integral. Si al multiplicar la
 * mitad por ella misma el resultado es mayor que la raiz integral supondra que el valor que
 * buscamos se encuentra a la izquiera de la mitad, con lo cual el valor de final pasa a ser
 * el que tenia la mitar y asi reducir la cantidad de numeros a la mitad, en el caso de que 
 * la mitad multiplicada por ella misma sea menor a n el valor del principio pasara a ser el
 * de la mitad por que eso significa al igual que antes que el resultado esta a la derecha
 * de la mitad, eso se hara sucesivamente hasta que el resultado de multiplicar la mitad por
 * ella misma menos la raiz integral sea menor que el error
 **********************************************************************************/	 
	 public static long metodoBinarySearch(int n) { 
		 long tiempoInicio = System.nanoTime();
		 double e = 0.000001; /*indice de error*/
		 double principio = 0, fin = n, mitad=n/2; 
		 double solucion;
		 	
	     while ((((mitad * mitad)-n)>e) || ((n-(mitad * mitad))>e)){    
	    	 if (mitad * mitad > n){ 
	            fin = mitad; 
	        } 

	        if (mitad * mitad < n) { 
	            principio = mitad;
	  	    } 
	        
	        mitad = (principio + fin) / 2; 
	        }
	     
		 solucion = mitad;
	     return (System.nanoTime()-tiempoInicio);		
	 }
	 
	 
 /*********************************************************************************
 * Method Name: metodoBabilonicoRecursivo
 * Method description: En este método declaramos las variables tiempoInicio, x e y 
 * y llamamos al método recursividad pasandole estas variables. Una vez hayamos 
 * terminado en el método recursividad mostraremos por pantalla el tiempo empleado.
 **********************************************************************************/	 
	 public static long metodoBabilonicoRecursivo(int n){ 
		 long tiempoInicio = System.nanoTime();
		 double x = n; 
		 double y = 1; 
	        
	     recursividad(n, x, y); 	  
	        
	     return (System.nanoTime()-tiempoInicio);		
	}

	 
 /*********************************************************************************
 * Method Name: recursividad
 * Method description: En este método calcularemos la raiz cuadrada de forma recursiva.
 * La variable "e" será el índice de error y miraremos que "x" menos "y" sea mayor que 
 * "e", si es asi "x" le pasaremos el valor de ((x+y)/2) e "y" le pasaremos el valor de 
 * (n/x) y luego llamaremos a este método pasandole los valores de "n", "x" e "y"
 **********************************************************************************/
	private static void recursividad(int n, double x, double y) {
        double e = 0.000001; /*indice de error*/
        double solucion = 0;
        
		if (x - y > e) { 
		    x = (x + y) / 2; 
		    y = n / x; 
		    recursividad(n, x, y); 
		}
		
		else solucion = x;
		
	} 
	 
	 
}
