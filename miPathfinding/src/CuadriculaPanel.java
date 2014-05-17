
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/** CLASE CUADRICULAPANEL
 *  crea una clase al que le paso un array[][] 
 *  el array NO tiene porque ser cuadrado (proporcional)
 *  
 *  Punto de salida es un '1'
 *  Puntos donde puede ir el algoritmo '0'
 *  Punto donde no puede pintar '-1
 *   
 * 
 * @author JS
 *
 */

class CuadriculaPanel extends JFrame {

    private final JFrame mainFrame = new JFrame();
    private final JPanel cuadriculaPanel = new JPanel();
    private static int[][] mapa; //guarda un array con rellenado con 0-1 para saber si la casilla esta activa o no
    
    Point INICIO = new Point(0,0);
    Point FIN = new Point(4,10);   //punto que quiero verifique si puede llegar
    
    public enum camino 
    { 
           TRANSITABLE(0),  
           NO_TRANSITABLE(-1),  
           INICIO(1),  
           FIN(1); 

          private int valor;

          private camino(int valor){
           this.valor = valor;
          }

        public int getvalor(){
          return this.valor;
         } 
    }
    
    
	public CuadriculaPanel(int[][] map) {
	
		this.mapa = map;
		//Calculo el tamaño maximo de la cuadricula y creo una cuadricula del tamaño maximo
		int col = 0;
		int row = 0;
		for(int i=0; i<mapa.length;i++){
			if(row < mapa.length) 
				row = mapa.length;
			for(int j=0;j<mapa[i].length;j++){
				if(col < mapa[i].length) 
					col = mapa[i].length;
			}
		}
		System.out.println("col: "+col);
		System.out.println("row: "+row);
		
	    
		//creo la cuadricula con el tamaño maximo
		GridLayout grid = new GridLayout();//new GridLayout();
		grid.setRows(row);
		grid.setColumns(col);
		grid.setHgap(2);
		grid.setVgap(2);
		cuadriculaPanel.setLayout(grid);
		
		//RELLENO LA CUADRICULA CON JPANEL
	   for(int i=0; i<row;i++){
		  System.out.println("i:");
			for(int j=0;j<col;j++){
				
				if(j<mapa[i].length)
				{
						if(mapa[i][j]==camino.TRANSITABLE.valor || mapa[i][j]==camino.INICIO.valor){
							System.out.print("X");
							Block gb = new Block(i, j, mapa[i][j]);
				            cuadriculaPanel.add(gb);
						}else if(mapa[i][j]==camino.NO_TRANSITABLE.valor){
							System.out.print("0");
							Block pv = new Block(i, j,mapa[i][j]);
				            cuadriculaPanel.add(pv);
						}
				}else{
					System.out.print("E");
					Block pv = new Block(i, j, -1);
		            cuadriculaPanel.add(pv);
				}
				
			}
			System.out.println();
		}
	   
        mainFrame.setLayout(new BorderLayout(5, 5));
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.add(cuadriculaPanel, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
	
	//cambio el array (mapa)
	public static void setValor(int row, int col, int valor){
		if(mapa[row].length > col){
			System.out.println(mapa[row].length + "   col: " + col);
			mapa[row][col]=valor;
		}
			
    }
	//devuelve un array con el mapa existente
	public int[][] getMapa(){
		return Arrays.copyOf(mapa,mapa.length);
	}
	
	public static void imprimeMapa(){
		System.out.println("\n********************");
		 for(int i=0; i<mapa.length;i++){
 			for(int j=0;j<mapa[i].length;j++){
 				System.out.print(mapa[i][j]);
 			}	
 				System.out.println();
 		}
	}
	
	

    
    
    //COMPROBAR LOS TIEMPOS QUE TARDA EN ENCONTRAR EL CAMINO
    //SYSTEM.NANOTIME()
    public static void main(String[] args) {

    	final int[][] array = {{ 1, 0,-1,-1,-1,-1, 0},
    						   { 0, 0,-1,-1,-1,-1,-1, -1, -1},
    						   {-1, 0, 0,-1, 0,-1,-1},
    						   { 0,-1, 0,-1, 0, 0, 0},
    						   {-1,-1, 0,-1, 0,-1,-1, 0, -1},
    						   {-1,-1, 0,-1, 0,-1,-1, 0,  0},
    						   {-1,-1, 0, 0, 0, 0,-1, -1,-1}};
    	 
    	
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                CuadriculaPanel cuadricula = new CuadriculaPanel(array);
                
               int[][] arrayfinal = cuadricula.getMapa();
               
               cuadricula.imprimeMapa();
        		   
            }
        });
    }
}




