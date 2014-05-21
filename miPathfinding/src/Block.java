import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.text.Position;


/** CLASE BLOCK, CREA LOS CUADRADITOS 
 *  - sirve para ir creando la cuadricula
 *  - cada cuadrado tiene un mouse listener
 *  
 * @author JS
 */
class Block extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	//logica del algoritmo
	public String name;
	public String getName() {
		return name;
	}


	private int row;
    private int col;
    private Block refPadre = null;
    private int Heuristic = 0;
    private int Costo = 0;
    
    
    private int Fcost = 0; // la formula es [ Fcost = Gcost + Hcost ]
    private int Gcost = 0; //Aqui guardo el coste de moverme de este bloque al siquiente (coste 10 para movimientos horizonta-vertical y coste 14 para movimientos diagonales)
    private int Hcost = 0; //Heuristica: es un costo aproximado lo calcula mirando a cuantas casillas de distancia de este bloque esta el bloque FIN y luego lo multiplico x10 
    
    
    
    
    public int getFcost() {
		return Fcost;
	}
	public void setFcost(int fcost) {
		Fcost = fcost;
	}
	public int getGcost() {
		return Gcost;
	}
	public void setGcost(int gcost) {
		Gcost = gcost;
	}
	public int getHcost() {
		return Hcost;
	}
	public void setHcost(int hcost) {
		Hcost = hcost;
	}


	private boolean casillaInicio = false;
    private boolean casillaFin = false;
    
    public boolean isCasillaInicio() {
		return casillaInicio;
	}
	public void setCasillaInicio(boolean casillaInicio) {
		this.casillaInicio = casillaInicio;
		setTipoColor();
	}
	public boolean isCasillaFin() {
		return casillaFin;
	}
	public void setCasillaFin(boolean casillaFin) {
		this.casillaFin = casillaFin;
		setTipoColor();
	}


	private int tipo; //el tipo de casilla puede ser (0)TRANSITABLE / NO TRANSITABLE(1)
    public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
		setTipoColor();
	}


	private boolean activado = false;
    
    
	
    //block
	private final int SIZE = 35;
    private final Color COLOR_TRANSITABLE = Color.BLACK;
    private final Color COLOR_NO_TRANSITABLE = Color.RED;
    private final Color COLOR_INICIO = Color.GREEN;
    private final Color COLOR_FIN = Color.orange;
    
    //bordes
    private final int BORDER_SIZE = 1;
    private final int BORDER_SIZE_CHECK = 3;
    private final Color BORDER_COLOR = Color.gray;
    private final Color BORDER_COLOR_CHECK = Color.cyan;
    private boolean checked = false; //VAR q sirve para activar y desactivar el resaltado del borde. 
    
   
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
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
    
        
    private void setTipoColor()
    {       
    	if(Block.this.isCasillaInicio() || Block.this.isCasillaFin()){
    		
    		if(Block.this.isCasillaInicio()==true && Block.this.isCasillaFin()==true){
    			System.out.println("ERROR. NO PUEDEN SER CASILLA DE INICIO Y DE FIN A LA VEZ!!");
    		}else{
    			if(Block.this.isCasillaInicio() == true)
    				setBackground(COLOR_INICIO);
    			else
    				setBackground(COLOR_FIN);
    		}
    	}else if(Block.this.tipo == camino.TRANSITABLE.valor)
    			setBackground(COLOR_TRANSITABLE);
    	else if(Block.this.tipo == camino.NO_TRANSITABLE.valor)
    			setBackground(COLOR_NO_TRANSITABLE);
    	else{
    		System.out.println("Error. setTipoColor no coincide con ninguna tipo");
    		setBackground(COLOR_NO_TRANSITABLE);
    	}
    		
    }
    
    public void setCaminoEncontradoColor(){
    	setBackground(Color.WHITE);
    }
    
    
    //SOLO SIRVE PARA CAMBIAR EL COLOR Y EL TIPO DE LA CASILLA CON EL RATON
    private void nextTipo()
    {       
        if(Block.this.tipo == camino.TRANSITABLE.valor)
        	Block.this.tipo = camino.NO_TRANSITABLE.valor;
    	else if(Block.this.tipo == camino.NO_TRANSITABLE.valor)
    		Block.this.tipo = camino.TRANSITABLE.valor;
    	else if (Block.this.tipo == camino.INICIO.valor)
    		Block.this.tipo = camino.TRANSITABLE.valor;
    	else
    		Block.this.tipo = camino.TRANSITABLE.valor;
    }

    public void setBorderChecked(){
    	checked = !checked;
    	if(checked)
    		this.setBorder(new LineBorder(BORDER_COLOR_CHECK,BORDER_SIZE_CHECK));
    	else
    		this.setBorder(new LineBorder(BORDER_COLOR,BORDER_SIZE));
    }
    
    
    
    /**
     * 
     * @param row
     * @param col
     * @param tipo  le paso un numero para luego pintarlo de ese color
     */
    public Block(int row, int col, int tipo) {
        this.row = row;
        this.col = col;
        this.tipo = tipo;
        this.setBorder(new LineBorder(BORDER_COLOR,BORDER_SIZE));
        name = "["+row+","+col+"]";
        setTipoColor();
    		
        this.addMouseListener(new MouseListener() 
        {
			@Override
            public void mouseClicked(MouseEvent e) 
			{  
			   if(SwingUtilities.isLeftMouseButton(e)){
				   nextTipo();
				   setTipoColor();
				   //System.out.printf("\n[%d][%d]     [isActivado]: %b",Block.this.col,Block.this.row, Block.this.activado);
			   }else if(SwingUtilities.isRightMouseButton(e)){
				   setBorderChecked();
			   }
			   
	        }
			
            @Override
            public void mousePressed(MouseEvent e) {
            	
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	 if(SwingUtilities.isLeftMouseButton(e)){
    				   Block.this.tipo = camino.NO_TRANSITABLE.valor;
    				   setTipoColor();
    			   }else if(SwingUtilities.isRightMouseButton(e)){
    				 Block.this.tipo = camino.TRANSITABLE.valor;
  				  setTipoColor();
    			   }
            }

            @Override
            public void mouseExited(MouseEvent e) {                	
           }
        });
    
    }
	public int getCosto() {
		return Costo;
	}
	public void setCosto(int costo) {
		Costo = costo;
	}
	public Block getRefPadre() {
		return refPadre;
	}
	public void setRefPadre(Block refPadre) {
		this.refPadre = refPadre;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}

}
