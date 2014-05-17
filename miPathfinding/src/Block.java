import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;


/** CLASE BLOCK, CREA LOS CUADRADITOS 
 *  - sirve para ir creando la cuadricula
 *  - cada cuadrado tiene un mouse listener
 *  
 * @author JS
 */
class Block extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private final int SIZE = 35;
    private final Color ACTIVE_COLOR = Color.BLACK;
    private final Color INACTIVE_COLOR = Color.RED;
    private final Color START_COLOR = Color.GREEN;
    
    private final int BORDER_SIZE = 1;
    private final int BORDER_SIZE_CHECK = 3;
    private final Color BORDER_COLOR = Color.gray;
    private final Color BORDER_COLOR_CHECK = Color.cyan;
    private boolean checked = false; //VAR q sirve para activar y desactivar el resaltado del borde. 
    
    private int row;
    private int col;
    private int tipo = 0; //TIPO DE CASILLA
    private boolean activado = false;
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIZE, SIZE);
    }
    
    
    
    private void setTipoColor()
    {       
        if(Block.this.tipo == 0)
    		setBackground(ACTIVE_COLOR);
    	else if(Block.this.tipo == -1)
    		setBackground(INACTIVE_COLOR);
    	else if (Block.this.tipo == 1)
    		setBackground(START_COLOR);
    	else
    		setBackground(INACTIVE_COLOR);
    }
    
    private void nextTipo()
    {       
        if(Block.this.tipo == 0)
        	Block.this.tipo = -1;
    	else if(Block.this.tipo == -1)
    		Block.this.tipo =  1;
    	else if (Block.this.tipo == 1)
    		Block.this.tipo = 0;
    	else
    		Block.this.tipo = 0;
    }

    private void setBorderChecked(){
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
       // System.out.println("r:"+row+"  col: "+col+ "   tipo: "+Block.this.tipo);
        
        setTipoColor();
    		
        this.addMouseListener(new MouseListener() 
        {
			@Override
            public void mouseClicked(MouseEvent e) 
			{  
			   if(SwingUtilities.isLeftMouseButton(e)){
				   nextTipo();
				   setTipoColor();
				   System.out.printf("\n[%d][%d]     [isActivado]: %b",Block.this.col,Block.this.row, Block.this.activado);
		           Cuadricula.imprimeMapa();
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
            	//System.out.println("GRID_ACTIVE:" + GridPanel.this.activado);
            	setTipoColor();
            }

            @Override
            public void mouseExited(MouseEvent e) {                	
           }
        });
    
    }

}
