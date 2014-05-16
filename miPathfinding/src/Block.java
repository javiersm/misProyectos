import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;


/** CLASE BLOCK, CREA LOS CUADRADITOS 
 *  - sirve para ir creando la cuadricula
 *  - cada cuadrado tiene un mouse listener
 *  
 * @author JS
 */
class Block extends JPanel 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int SIZE = 35;
    private final Color ACTIVE_COLOR = Color.BLACK;
    private final Color INACTIVE_COLOR = Color.RED;
    private final int BORDER_SIZE = 1;
    private final Color BORDER_COLOR = Color.gray;
    private int row;
    private int col;
    private boolean activado = false; //activar o desactivar una casilla

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
    }
    
    //constructor. le paso un numero de fila y de columna
    public Block(int row, int col, boolean isActivado) {
        this.row = row;
        this.col = col;
        this.activado = isActivado;
        this.setBorder(new LineBorder(BORDER_COLOR,BORDER_SIZE));
        System.out.println("r:"+row+"  col: "+col+ "   active: "+Block.this.activado);
        
        if(Block.this.activado)
    		setBackground(ACTIVE_COLOR);
    	else setBackground(INACTIVE_COLOR);
    	
        this.addMouseListener(new MouseListener() 
        {
			@Override
            public void mouseClicked(MouseEvent e) {
			   Block.this.activado = !Block.this.activado;
               System.out.printf("\n[%d][%d]     [isActivado]: %b",Block.this.col,Block.this.row, Block.this.activado);
                if(Block.this.activado){
                	setBackground(ACTIVE_COLOR);
                	CuadriculaPanel.setValor(Block.this.row,Block.this.col,0);
                }else{
                	setBackground(INACTIVE_COLOR);
                	CuadriculaPanel.setValor(Block.this.row,Block.this.col,1);
                }
                CuadriculaPanel.imprimeMapa();	 
            }
			
            @Override
            public void mousePressed(MouseEvent e) {
            	
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	//System.out.println("GRID_ACTIVE:" + GridPanel.this.activado);
            	if(Block.this.activado){
            		setBackground(ACTIVE_COLOR);
            	}else{
            		setBackground(INACTIVE_COLOR);
            	}
			
            }

            @Override
            public void mouseExited(MouseEvent e) {                	
           }
        });
    }


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
