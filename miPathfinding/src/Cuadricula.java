import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.*;
/**
 * CLASE CUADRICULA Le paso un array[][] y me crea un JPanel que contiene una
 * cuadricula(grid) lleno de JPanel(Bloques) pequeños el array NO tiene porque
 * ser cuadrado (proporcional)
 * 
 * Puntos donde puede ir el algoritmo '0' 
 * Punto ocupados en el mapa '1'
 * inicio= '-1
 * fin= '-2'
 * 
 * 
 * @author JS
 * 
 */

class Cuadricula extends JPanel 
{
	private final JPanel cuadriculaPanel = new JPanel();
	public JPanel getCuadriculaPanel() {
		return cuadriculaPanel;
	}

	private static int[][] mapa; // guarda un array con rellenado con 0-1 para saber si la casilla esta activa o no
	private int mapaRows;
	private int mapaCols;
	

	Point NODO_INICIO = new Point(0, 0);
	Point NODO_FIN = new Point(4, 10); // punto que quiero verifique si puede llegar
	
	
	//ARRAY LIST con los puntos que me faltan por verificar y los verificados
	ArrayList<Point> openList = new ArrayList<Point>();
	ArrayList<Point> closedList = new ArrayList<Point>();
	boolean encontrado = false;
	Block nodo;
	
	public void start(){
		openList.add(NODO_INICIO);
		do{
			for(int i=0; i<openList.size();i++){
				
				
				
				System.out.println("");
			}
			System.out.println("");
		}while(closedList.size()>0 || encontrado);
		
	}
	
	
	public void checkCurrentNode(){
		
	}
	
	
	
	
	
	
	/**
	 * Constructor
	 * @param map
	 */
	public Cuadricula(int[][] map) {

		mapa = generaMapaCompleto(map); //genera un mapa cuadrado 
		cuadriculaPanel.setLayout(new GridLayout(mapaRows, mapaCols, 1, 1));

		// RELLENO LA CUADRICULA CON JPANEL
		for (int i = 0; i < mapaRows; i++) {
			for (int j = 0; j < mapaCols; j++) {
				if (mapa[i][j] == camino.TRANSITABLE.valor || mapa[i][j] == camino.INICIO.valor || mapa[i][j] == camino.NO_TRANSITABLE.valor) {
					Block gb = new Block(i, j, mapa[i][j]);
					cuadriculaPanel.add(gb);
				}
			}
		}
	}

	
	
	// cambio el array (mapa)
	public static void setValorMapa(int row, int col, int valor) {
		if (mapa[row].length > col) {
			System.out.println(mapa[row].length + "   col: " + col);
			mapa[row][col] = valor;
		}
	}

	// devuelve un array con el mapa existente
	public int[][] getMapa() {
		return Arrays.copyOf(mapa, mapa.length);
	}

	public static void imprimeMapa() {
		System.out.println("\n******** MAP PRINT *************");
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[i].length; j++) {
				System.out.printf("%3s",mapa[i][j]);
			}
			System.out.println();
		}
	}


	public enum camino {
		TRANSITABLE(0), NO_TRANSITABLE(1), INICIO(-1), FIN(-2);

		private int valor;

		private camino(int valor) {
			this.valor = valor;
		}

		public int getvalor() {
			return this.valor;
		}
	}

	private int[][] generaMapaCompleto(int[][]map){
		int[][] mapa;
		//calcula el tamaño maximo de fila y columna
		for (int i = 0; i < map.length; i++) {
			if (mapaRows < map.length)
				mapaRows = map.length;
			for (int j = 0; j < map[i].length; j++) {
				if (mapaCols < map[i].length)
					mapaCols = map[i].length;
			}
		}
		mapa = new int[mapaRows][mapaCols]; //creo un array con el valor maximo de fila y columna
		for (int i = 0; i < mapa.length; i++) {
			Arrays.fill(mapa[i], camino.NO_TRANSITABLE.valor);
		}
		
		for (int i = 0; i < map.length; i++) {
			for(int j=0; j<map[i].length;j++){
				mapa[i][j] = map[i][j];
			}
		}
		System.out.printf("Generado MAPA de col:%d    row:%d ", mapaRows, mapaCols);
		return mapa;
	}

}
