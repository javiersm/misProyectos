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
 * Punto de salida es un '1' Puntos donde puede ir el algoritmo '0' Punto donde
 * no puede pintar '-1
 * 
 * 
 * @author JS
 * 
 */

class Cuadricula extends JPanel {

//	private final JFrame frame = new JFrame();
	private final JPanel cuadriculaPanel = new JPanel();
	public JPanel getCuadriculaPanel() {
		return cuadriculaPanel;
	}

	private static int[][] mapa; // guarda un array con rellenado con 0-1 para saber si la casilla esta activa o no
	private int mapaRows;
	private int mapaCols;
	

	Point INICIO = new Point(0, 0);
	Point FIN = new Point(4, 10); // punto que quiero verifique si puede llegar
	
	//ARRAY LIST con los puntos que me faltan por verificar y los verificados
	ArrayList<Point> verificados = new ArrayList<Point>();
	ArrayList<Point> noVerificados = new ArrayList<Point>();
	
	
	
	
	/**
	 * Constructor
	 * @param map
	 */
	public Cuadricula(int[][] map) {

		mapa = generaMapaCompleto(map); //genera un mapa cuadrado 
	
		

		// creo la cuadricula con el tamaño maximo
		GridLayout grid = new GridLayout();// new GridLayout();
		grid.setRows(mapaRows);
		grid.setColumns(mapaCols);
		grid.setHgap(2);
		grid.setVgap(2);
		cuadriculaPanel.setLayout(grid);

		// RELLENO LA CUADRICULA CON JPANEL
		for (int i = 0; i < mapaRows; i++) {
			System.out.println("i:");
			for (int j = 0; j < mapaCols; j++) {

				if (j < mapa[i].length) {
					if (mapa[i][j] == camino.TRANSITABLE.valor
							|| mapa[i][j] == camino.INICIO.valor) {
						System.out.print("X");
						Block gb = new Block(i, j, mapa[i][j]);
						cuadriculaPanel.add(gb);
					} else if (mapa[i][j] == camino.NO_TRANSITABLE.valor) {
						System.out.print("0");
						Block pv = new Block(i, j, mapa[i][j]);
						cuadriculaPanel.add(pv);
					}
				} else {
					System.out.print("E");
					Block pv = new Block(i, j, -1);
					cuadriculaPanel.add(pv);
				}

			}
			System.out.println();
		}

//		frame.setLayout(new BorderLayout(5, 5));
//		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		frame.add(cuadriculaPanel, BorderLayout.CENTER);
//		frame.pack();
//		frame.setVisible(true);
		//return cuadriculaPanel;
	}

	public JPanel getCuadricula() {
		return  cuadriculaPanel;
	}
	
	// cambio el array (mapa)
	public static void setValor(int row, int col, int valor) {
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
		TRANSITABLE(0), NO_TRANSITABLE(-1), INICIO(1), FIN(1);

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
