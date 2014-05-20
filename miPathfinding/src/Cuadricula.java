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

	private static Block[][] mapa; // guarda un array con rellenado con 0-1 para saber si la casilla esta activa o no
	private int mapaRows;
	private int mapaCols;
	

	Block NODO_INICIO;
	Block NODO_FIN; // punto que quiero verifique si puede llegar
	Block currentNode;
	
	//ARRAY LIST con los puntos que me faltan por verificar y los verificados
	ArrayList<Block> openList = new ArrayList<Block>();
	ArrayList<Block> closedList = new ArrayList<Block>();
	boolean encontrado = false;
	boolean continuar = false;
	
	
	
	

	
	private void addToOpenList(Block node){
		if(!openList.contains(node)){
			System.out.println("Introduciendo en openList!" + node.getName());
			openList.add(node);
		}
	}
	
	private void deleteFromOpenList(Block currentNode){
		if(openList.contains(currentNode)){
			System.out.println("encontrado ... borrando!");
			openList.remove(currentNode);
		}
	}
	
	private void addToClosedList(Block node){
		if(!closedList.contains(node)){
			System.out.println("Introduciendo en openList!");
			closedList.add(node);
		}
	}
	
	//IMPRIME LA OPENLIST Y CLOSE LIST PARA VER COMO VA.....
	private void printOpenList(){
		System.out.println("_________ OPEN LIST ______________");
		for(int i=0; i<openList.size();i++){
			System.out.printf("  %s",openList.get(i).getName());
			if(i%10== 0 && i>1)
				System.out.println("");
		}
		System.out.println("\n___________________________________");
	}
	

	public void start(){
		openList.add(NODO_INICIO);
		continuar = true;
		System.out.println("mapaRows: " + mapaRows + "  mapaCols: " + mapaCols);
		
		while(openList.size()>0 && !encontrado){
			for(int i=0; i<openList.size() && continuar==true;i++)
			{
				
				currentNode = openList.get(i);
				currentNode.setBorderChecked();
				System.out.println("\n******* checkCurrentBlock: "+openList.get(i).getName());
				
				checkCurrentBlock(currentNode);
				addToClosedList(currentNode);
				
				if(encontrado){
					System.out.println("BIENNN. ENCONTRADO EL BLOQUE DEE FIN !");
					pintaCaminoRegreso(currentNode);
					return;
				}
				printOpenList();
				
				deleteFromOpenList(currentNode);

			}
			System.out.println("");
		}
		
		if(!encontrado)
			System.out.println("PROGRAMA TERMINADO NO SE HA ENCONTRADO EL FIN");
	}
	
	private void pintaCaminoRegreso(Block nodo){
		System.out.println("\n ____________________ pintando camino de vuelta _______________________");
		System.out.println(NODO_FIN.getName());
		do{
			System.out.println(nodo.getName() + "   ");
			nodo.setCaminoEncontradoColor();
			nodo = nodo.getRefPadre();
		}while(nodo.getRefPadre() != null);
		System.out.println(NODO_INICIO.getName());
		System.out.println("_______________________________________________________________________");
	}
	
	
	public void checkCurrentBlock(Block nodo){
		
		checkNorth(nodo.getRow(), nodo.getCol());
		checkSouth(nodo.getRow(), nodo.getCol());
		checkEast(nodo.getRow(), nodo.getCol());
		checkWest(nodo.getRow(), nodo.getCol());
	}
	
	private void checkNorth(int row, int col)
	{	
		--row;
		if((col>=0 && col<mapaCols) && (row>=0 && row<mapaRows))
		{
			System.out.printf("\nmirando en NORTH......   [%d][%d]",row,col);
			if(mapa[row][col].getTipo() == camino.TRANSITABLE.valor && !closedList.contains(mapa[row][col]))
			{
				if(mapa[row][col].equals(NODO_FIN)){
					encontrado = true;
				}else{
					mapa[row][col].setRefPadre(currentNode);
					mapa[row][col].setCosto(currentNode.getCosto() + 10);
					addToOpenList(mapa[row][col]); //sino es el que estoy buscando meto el nodo en la lista abierta
				}
			}else{
				System.out.println("camino no transitable");
			}
		}else{
			System.out.println("North: esta fuera del rango!"+"["+row+"]["+col+"]");
		}
	}
	
	private void checkSouth(int row, int col)
	{
		++row;
		if((col>=0 && col<mapaCols) && (row>=0 && row<mapaRows))
		{
			System.out.printf("\nmirando en SOUTH......   [%d][%d]",row,col);
			if(mapa[row][col].getTipo() == camino.TRANSITABLE.valor && !closedList.contains(mapa[row][col]))
			{
				if(mapa[row][col].equals(NODO_FIN)){
					encontrado = true;
				}else{
					mapa[row][col].setRefPadre(currentNode);
					mapa[row][col].setCosto(currentNode.getCosto() + 10);
					addToOpenList(mapa[row][col]); //sino es el que estoy buscando meto el nodo en la lista abierta
				}
			}else{
				System.out.println("Camino no transitable");
			}		
		}else{
			System.out.println("South: esta fuera del rango!" +"["+row+"]["+col+"]");
		}
	}
	
	private void checkEast(int row, int col){
		++col;
		if((col>=0 && col<mapaCols) && (row>=0 && row<mapaRows))
		{
			System.out.printf("\nmirando en EAST......   [%d][%d]",row,col);
			if(mapa[row][col].getTipo() == camino.TRANSITABLE.valor && !closedList.contains(mapa[row][col]))
			{
				if(mapa[row][col].equals(NODO_FIN)){
					encontrado = true;
				}else{
					mapa[row][col].setRefPadre(currentNode);
					mapa[row][col].setCosto(currentNode.getCosto() + 10);
					addToOpenList(mapa[row][col]); //sino es el que estoy buscando meto el nodo en la lista abierta
				}
			}else{
				System.out.println("Camino no transitable");
			}		
		}else{
			System.out.println("EAST: esta fuera del rango!"+"["+row+"]["+col+"]");
		}
	}
	
	private void checkWest(int row, int col){
		--col;
		if((col>=0 && col<mapaCols) && (row>=0 && row<mapaRows))
		{
			System.out.printf("\nmirando en WEST......   [%d][%d]",row,col);
			if(mapa[row][col].getTipo() == camino.TRANSITABLE.valor && !closedList.contains(mapa[row][col]))
			{
				if(mapa[row][col].equals(NODO_FIN)){
					encontrado = true;
				}else{
					mapa[row][col].setRefPadre(currentNode);
					mapa[row][col].setCosto(currentNode.getCosto() + 10);
					addToOpenList(mapa[row][col]); //sino es el que estoy buscando meto el nodo en la lista abierta
				}
			}else{
				System.out.println("Camino no transitable");
			}		
		}else{
			System.out.println("WEST: esta fuera del rango!"+"["+row+"]["+col+"]");
		}
	}
	
	
	

	public Cuadricula(int[][] map) {

		mapa = generaMapaCompleto(map); //genera un mapa cuadrado 
		
		cuadriculaPanel.setLayout(new GridLayout(mapaRows, mapaCols, 1, 1));
		for (int i = 0; i < mapaRows; i++) {
			for (int j = 0; j < mapaCols; j++) {
					cuadriculaPanel.add(mapa[i][j]);
			}
		}
		
		NODO_INICIO = mapa[2][0];
		//NODO_INICIO = mapa[6][13];
		NODO_INICIO.setCasillaInicio(true);
		
		
		//NODO_FIN = mapa[0][11];
		NODO_FIN = mapa[8][1]; //no tiene fin
		//NODO_FIN = mapa[5][0];
		NODO_FIN.setCasillaFin(true);
		
		
		start();
	}

	
	
	
	/** Set valor en mapa[][] para que sea transitable o no.
	 * 
	 * @param row
	 * @param col
	 * @param tipo  pues pongo '0'si es transitable o  '1' si esta ocupado
	 */
	public static void setValorMapa(int row, int col, int tipo) 
	{
		if ((mapa[row].length >= row) &&  (row > 0) && (mapa[col].length >= col) && (col > 0)) {
			System.out.println(mapa[row].length + "   col: " + col);
			mapa[row][col].setTipo(tipo);
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

	private Block[][] generaMapaCompleto(int[][]map){
		Block[][] mapa;
		//calcula el tamaño maximo de fila y columna
		for (int i = 0; i < map.length; i++) {
			if (mapaRows < map.length)
				mapaRows = map.length;
			for (int j = 0; j < map[i].length; j++) {
				if (mapaCols < map[i].length)
					mapaCols = map[i].length;
			}
		}
		//creo un array[][] de Bloques y lo inicializo
		mapa = new Block[mapaRows][mapaCols]; 
		for (int i = 0; i < mapa.length; i++) {
			for(int j=0; j<mapa[i].length;j++){
				mapa[i][j] = new Block(i,j, camino.TRANSITABLE.valor); 
			}
		}
		
		//cargo los valores del array que pase por parametro en mi array de bloques
		for (int i = 0; i < map.length; i++) {
			for(int j=0; j<map[i].length;j++){
				mapa[i][j].setTipo(map[i][j]); 
			}
		}
		System.out.printf("Generado MAPA de  [row:%d | col:%d] \n", mapaRows, mapaCols);
		return mapa;
	}

}
