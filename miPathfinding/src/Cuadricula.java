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
	public int numComprobaciones = 0;
	
	
	// esto es para ejecutar paso a paso
	public static boolean sigPaso = false;
	public static boolean modePasoAPaso=false;
	

	
	private void addToOpenList(Block node){
		if(!openList.contains(node)){
			System.out.println("\tIntroduciendo en openList" + node.getName());
			openList.add(node);
		}
	}
	
	private void deleteFromOpenList(Block currentNode){
		if(openList.contains(currentNode)){
			System.out.println("Borrando currentNode");
			openList.remove(currentNode);
		}
	}
	
	private void addToClosedList(Block node){
		if(!closedList.contains(node)){
			System.out.println("addTo closeList");
			closedList.add(node);
		}
	}
	
	//IMPRIME LA OPENLIST Y CLOSE LIST PARA VER COMO VA.....
	private void printOpenList(){
		System.out.println("\n_________ OPEN LIST ______________");
		for(int i=0; i<openList.size();i++){
			System.out.printf("  %s coste:%d",openList.get(i).getName(),openList.get(i).getFcost());
			if(i%10== 0 && i>1)
				System.out.println("");
		}
		System.out.println("\n___________________________________");
	}
	

	public void start(){
		openList.ensureCapacity(60);
		closedList.ensureCapacity(100); 
		openList.add(NODO_INICIO);
		sigPaso = true;
		System.out.println("mapaRows: " + mapaRows + "  mapaCols: " + mapaCols);
		
		
		while(openList.size()>0 && !encontrado && sigPaso==true)
		{	
			if(modePasoAPaso)
				sigPaso = false;
			
			numComprobaciones++;
			//Elijo el nodo que menos costeF (total) tenga para ser procesado
			currentNode = openList.get(0);
			for(int i=0; i<openList.size();i++){
				if(currentNode.getFcost() > openList.get(i).getFcost())
					currentNode = openList.get(i);
			}
			
			currentNode.setBorderChecked();
			System.out.println("\n******* checkCurrentBlock: "+currentNode.getName() +" *********************");
			addToClosedList(currentNode);
			deleteFromOpenList(currentNode);
			checkCurrentBlock(currentNode);
			printOpenList();
			
			if(encontrado){
				System.out.println("\n\n[[ BIENNN!!  ENCONTRADO EL BLOQUE DEE FIN!!! ]]");
				pintaCaminoRegreso(currentNode);
				return;
			}
		}	

		if(!encontrado)
			System.out.println("PROGRAMA TERMINADO NO SE HA ENCONTRADO EL FIN");
	}
	


	private void pintaCaminoRegreso(Block nodo){
		int distanciaNodos = 1;
		System.out.println("\n ____________________ pintando camino de vuelta _______________________");
		System.out.println(NODO_FIN.getName());
		do{
			++distanciaNodos;
			System.out.println(nodo.getName() + "   ");
			nodo.setCaminoEncontradoColor();
			nodo = nodo.getRefPadre();
		}while(nodo.getRefPadre() != null);
		System.out.println(NODO_INICIO.getName());
		System.out.println("_______________________________________________________________________ ");
		System.out.println("Distancia: "+distanciaNodos +" cuadros");
		System.out.println("NumComprobaciones: " + numComprobaciones);
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
			System.out.printf("mirando en NORTH...   [%d][%d]",row,col);
			if(mapa[row][col].getTipo() == camino.TRANSITABLE.valor && !closedList.contains(mapa[row][col]))
			{
				if(mapa[row][col].equals(NODO_FIN)){
					encontrado = true;
				}else{
					mapa[row][col].setRefPadre(currentNode);
					mapa[row][col].setGcost(currentNode.getGcost() + 10);
					mapa[row][col].setHcost(currentNode, NODO_FIN);
					addToOpenList(mapa[row][col]); //sino es el que estoy buscando meto el nodo en la lista abierta
				}
			}else{
				System.out.println("\t camino no transitable");
			}
		}else{
			System.out.println("mirando en North: FUERA RANGO "+"["+row+"]["+col+"]");
		}
	}
	
	private void checkSouth(int row, int col)
	{
		++row;
		if((col>=0 && col<mapaCols) && (row>=0 && row<mapaRows))
		{
			System.out.printf("mirando en SOUTH...   [%d][%d]",row,col);
			if(mapa[row][col].getTipo() == camino.TRANSITABLE.valor && !closedList.contains(mapa[row][col]))
			{
				if(mapa[row][col].equals(NODO_FIN)){
					encontrado = true;
				}else{
					mapa[row][col].setRefPadre(currentNode);
					mapa[row][col].setGcost(currentNode.getGcost() + 10);
					mapa[row][col].setHcost(currentNode, NODO_FIN);
					addToOpenList(mapa[row][col]); //sino es el que estoy buscando meto el nodo en la lista abierta
				}
			}else{
				System.out.println("\tCamino no transitable");
			}		
		}else{
			System.out.println("mirando en South: FUERA RANGO " +"["+row+"]["+col+"]");
		}
	}
	
	private void checkEast(int row, int col){
		++col;
		if((col>=0 && col<mapaCols) && (row>=0 && row<mapaRows))
		{
			System.out.printf("mirando en EAST...   [%d][%d]",row,col);
			if(mapa[row][col].getTipo() == camino.TRANSITABLE.valor && !closedList.contains(mapa[row][col]))
			{
				if(mapa[row][col].equals(NODO_FIN)){
					encontrado = true;
				}else{
					mapa[row][col].setRefPadre(currentNode);
					mapa[row][col].setGcost(currentNode.getGcost() + 10);
					mapa[row][col].setHcost(currentNode, NODO_FIN);
					addToOpenList(mapa[row][col]); //sino es el que estoy buscando meto el nodo en la lista abierta
				}
			}else{
				System.out.println("\tCamino no transitable");
			}		
		}else{
			System.out.println("mirando en EAST: FUERA RANGO "+"["+row+"]["+col+"]");
		}
	}
	
	private void checkWest(int row, int col){
		--col;
		if((col>=0 && col<mapaCols) && (row>=0 && row<mapaRows))
		{
			System.out.printf("mirando en WEST...   [%d][%d]",row,col);
			if(mapa[row][col].getTipo() == camino.TRANSITABLE.valor && !closedList.contains(mapa[row][col]))
			{
				if(mapa[row][col].equals(NODO_FIN)){
					encontrado = true;
				}else{
					mapa[row][col].setRefPadre(currentNode);
					mapa[row][col].setGcost(currentNode.getGcost() + 10);
					mapa[row][col].setHcost(currentNode, NODO_FIN);
					addToOpenList(mapa[row][col]); //sino es el que estoy buscando meto el nodo en la lista abierta
				}
			}else{
				System.out.println("\t Camino no transitable");
			}		
		}else{
			System.out.println("mirando en WEST: FUERA RANGO "+"["+row+"]["+col+"]");
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
		NODO_INICIO.setCasillaInicio(true);
		NODO_FIN = mapa[8][1];
		NODO_FIN.setCasillaFin(true);
	}
	
	
	public Cuadricula(int[][] map, int tamaño) {
		mapa = generaMapaCompletoConTamaño(map, tamaño); //genera un mapa cuadrado 
		if (mapa != null)
		{
			cuadriculaPanel.setLayout(new GridLayout(mapaRows, mapaCols, 1, 1));
			for (int i = 0; i < mapaRows; i++) {
				for (int j = 0; j < mapaCols; j++) {
						cuadriculaPanel.add(mapa[i][j]);
				}
			}
			NODO_INICIO = mapa[03][4];
			NODO_INICIO.setCasillaInicio(true);
			NODO_FIN = mapa[20][35];
			NODO_FIN.setCasillaFin(true);
		}

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
	
	private Block[][] generaMapaCompletoConTamaño(int[][]map, int tamaño)
	{
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
		if (tamaño>mapaCols && tamaño>mapaRows)
		{
			mapaCols = tamaño;
			mapaRows = tamaño;
			mapa = new Block[tamaño][tamaño]; //creo un array[][] de Bloques y lo inicializo
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
			System.out.printf("Generado MAPA de  [row:%d | col:%d] \n", tamaño, tamaño);
			return mapa;
		}else{
			System.out.println("ERROR EL TAMAÑO DEBE SER MAYOR QUE EL TAMAÑO DEL ARRAY");
			return null;
		}
	}

}
