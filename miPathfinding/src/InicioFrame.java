import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class InicioFrame extends JFrame {
		
	public static Cuadricula cuadricula;
	private static JFrame frame;
	private static PanelInferior panelInferior;
	private static int tamañoArray = 40;
	private static int[][] array= 	  { {  1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0},
										{  0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0 },
										{  0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1 }, 
										{  1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0 },
										{  0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0 },
										{  0, 1, 1, 0, 0, 1, 0, 0, 1 },
										{  0, 1, 1, 0, 0, 1, 0, 0, 1 },
										{  0, 1, 1, 0, 0, 1, 0, 0, 1 },
										{  0, 0, 1, 1, 0, 1, 0, 1, 1 } };
	
	
	
	public static void vaciarCuadricula(){
		
		frame.remove(cuadricula);
		
		
		cuadricula = new Cuadricula(array, tamañoArray);
		System.out.println("añadiendo nuevo jpanel al frame");
		frame.add(cuadricula);
		
		//frame.getContentPane().removeAll();
		frame.invalidate();
		//frame.getContentPane().add(cuadricula);
		frame.revalidate();
		frame.repaint();
		// Hiding all components (JPanels) added to a container (ex: another JPanel)
		for (Component component : frame.getComponents()) {
		      component.setVisible(false);
		}
		for (Component component : frame.getComponents()) {
		      component.setVisible(true);
		}
		
	}
	
	
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				frame = new JFrame("mi Pathfinding");
				cuadricula = new Cuadricula(array, tamañoArray);
				
				PanelInferior panelInferior = new PanelInferior();
				//PanelDerecho panelDerecho = new PanelDerecho();
				
				setDefaultLookAndFeelDecorated(true);
				frame.setLayout(new BorderLayout(5, 5));
				frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				frame.add(cuadricula.getCuadriculaPanel(), BorderLayout.CENTER);
				frame.add(panelInferior, BorderLayout.SOUTH);
				//frame.add(panelDerecho,BorderLayout.EAST);
				frame.pack();
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				frame.setVisible(true);
				
				// COMPROBAR LOS TIEMPOS QUE TARDA EN ENCONTRAR EL CAMINO
				// SYSTEM.NANOTIME()	
			}

		});
	}


}


class PanelInferior extends JPanel{

	public PanelInferior(){
		
		this.setLayout(new FlowLayout());
	    this.setBackground(Color.DARK_GRAY);
	        
        
		JButton btn_calcular = new JButton("Calcular");
		btn_calcular.setAlignmentX(Component.LEFT_ALIGNMENT); 
		btn_calcular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("CALCULANDO....");
				InicioFrame.cuadricula.start();
				Cuadricula.sigPaso = true;
			}
		});
		add(btn_calcular);
		
		
		
		JButton btn_pasoApaso = new JButton("paso A paso");
		btn_pasoApaso.setAlignmentX(Component.LEFT_ALIGNMENT); 
		btn_pasoApaso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!InicioFrame.cuadricula.modePasoAPaso){
					System.err.println("\nMODO: PASO A PASO ON");
					Cuadricula.modePasoAPaso = true;
					//Cuadricula.sigPaso = true;
					//InicioFrame.cuadricula.start();
				}else{
					//System.out.println("Siguiente paso");
					//Cuadricula.sigPaso = true;
				}

			}
		});
		add(btn_pasoApaso);
		
		
		JButton btn_vaciarCuadricula = new JButton("vacia Cuadricula");
		btn_vaciarCuadricula.setAlignmentX(Component.LEFT_ALIGNMENT); 
		btn_vaciarCuadricula.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("vaciando cuadricula....");	
				InicioFrame.vaciarCuadricula();
			}
		});
		add(btn_vaciarCuadricula);
		
		
		JButton btn_salir = new JButton("Salir");
		btn_salir.setAlignmentX(Component.LEFT_ALIGNMENT); 
		btn_salir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Hasta Pronto !!");
				System.exit(0);	
			}
		});
		add(btn_salir);
		
		JLabel label1 = new JLabel("etiqueta 1");
		//add(label1);
			
	}
}

class PanelDerecho extends JPanel{

	public PanelDerecho(){
		
		//this.setLayout(new FlowLayout());
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.DARK_GRAY);
	    boolean shouldFill = true;
	    
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
		                //natural height, maximum width
		                c.fill = GridBagConstraints.HORIZONTAL;
		}

		JButton button = new JButton("Button 1");
		boolean shouldWeightX = true;
		if (shouldWeightX ) {
		                   c.weightx = 0.5;
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(button, c);

		button = new JButton("Button 2");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		add(button, c);

		button = new JButton("Button 3");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		add(button, c);

		button = new JButton("Long-Named Button 4");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		add(button, c);

		button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		add(button, c);
		
		

		/*
		JLabel lbl_tiempoCalculo = new JLabel("TiempoCalculo: ");
		lbl_tiempoCalculo.setForeground(Color.WHITE);
		add(lbl_tiempoCalculo);
		
		JTextField txt_tiempoCalculo = new JTextField();
		txt_tiempoCalculo.setBounds(10, 80, 1500, 20);
		add(txt_tiempoCalculo);
		*/
	
		
		

		
	}
}

