/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author Megan Hunter, Jesse Stanger, hogan
 *
 */
public class GUISimulator extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 6717132605785602783L;
	
	private static final String FONT = "Arial";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	
	private JPanel 		container;
	
	private JPanel 		buttonArea;
	private JPanel 		chartArea;
	private JPanel 		interactiveArea;

	private JButton 	runSimulation;
	private JButton 	swapCharts;
	
	private JTextArea 	titleSimuation;
	
	private JTextArea 	nameRNGSeed;
	private JTextField 	fieldRNGSeed;
	private JTextArea 	nameDailyMean;
	private JTextField 	fieldDailyMean;
	private JTextArea 	nameQueueSize;
	private JTextField 	fieldQueueSize;
	private JTextArea 	nameCancellation;
	private JTextField 	fieldCancellation;
	
	private JTextArea 	titleFareClasses;
	
	private JTextArea	nameFirst;
	private JTextField 	fieldFirst;
	private JTextArea	nameBusiness;
	private JTextField 	fieldBusiness;
	private JTextArea	namePremium;
	private JTextField 	fieldPremium;
	private JTextArea	nameEconomy;
	private JTextField 	fieldEconomy;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		createGUI(); 
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource(); 
		
		if (src == runSimulation) {
			//start simulation
		} else if (src == swapCharts) {
			//change chart
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Use this temporarily to help try out the simulator. 
		//We will not be using this main as the final executable main method. 
		//Instead the main we will use is a modified main() in SimulationRunner.java.
		JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new GUISimulator("BorderLayout"));
	}

	private void createGUI() {
		setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	    
	    container = createPanel(Color.WHITE);
	    
	    buttonArea = createPanel(Color.WHITE);
	    chartArea = createPanel(Color.WHITE);
	    interactiveArea = createPanel(Color.WHITE);
	    
	    runSimulation = createButton("Run Simulation");
	    swapCharts = createButton("Swap Charts");
	    
	    nameRNGSeed = createTextArea();
	    nameDailyMean = createTextArea();
	    nameQueueSize = createTextArea();
	    nameCancellation = createTextArea();
	    fieldRNGSeed = createTextField();
	    fieldDailyMean = createTextField();
	    fieldQueueSize = createTextField();
	    fieldCancellation = createTextField();
	    
	    nameFirst = createTextArea();
	    nameBusiness = createTextArea();
	    namePremium = createTextArea();
	    nameEconomy = createTextArea();
	    fieldFirst = createTextField();
	    fieldBusiness = createTextField();
	    fieldPremium = createTextField();
	    fieldEconomy = createTextField();
	    
	    container.setLayout(new BorderLayout());
	    
	    container.add(chartArea, BorderLayout.CENTER);
	    //chart stuff
	    
	    container.add(interactiveArea, BorderLayout.SOUTH);
	    layoutInteractivePanel();
	    
	    container.add(buttonArea, BorderLayout.NORTH);
	    layoutButtonPanel();
	    
	    this.getContentPane().add(container, BorderLayout.CENTER);
	    //more here maybe?
	    
	    repaint(); 
	    this.setVisible(true);
	}
	
	private JPanel createPanel(Color c) {
		JPanel jp = new JPanel();
		jp.setBackground(c);
		return jp;
	}
	
	private JButton createButton(String str) {
		JButton jb = new JButton(str); 
		jb.addActionListener(this);
		return jb; 
	}
	
	private JTextArea createTextArea() {
		JTextArea jta = new JTextArea(); 
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setFont(new Font(FONT, Font.BOLD, 24));
		jta.setBorder(BorderFactory.createEtchedBorder());
		return jta;
	}
	
	private JTextField createTextField() {
		JTextField jtf = new JTextField(); 
		jtf.setEditable(true);
		jtf.setFont(new Font(FONT, Font.BOLD, 24));
		jtf.setBorder(BorderFactory.createEtchedBorder());
		return jtf;
	}
	
	private void layoutButtonPanel() {
		GridBagLayout layout = new GridBagLayout();
	    container.setLayout(layout);
	    
	    //add components to grid
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    
	    //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 100;
	    constraints.weighty = 100;
	    
	    addToPanel(buttonArea, runSimulation,constraints,0,0,4,1); 
	    addToPanel(buttonArea, swapCharts,constraints,1,0,4,1);
	}
	
	private void layoutInteractivePanel() {
		GridBagLayout layout = new GridBagLayout();
	    container.setLayout(layout);
	    
	    //add components to grid
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    
	    //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 100;
	    constraints.weighty = 100;
	    
	    addToPanel(interactiveArea, titleSimuation, constraints, 1,0,4,1);
	    addToPanel(interactiveArea, nameRNGSeed, constraints, 1,1,4,1);
	    addToPanel(interactiveArea, fieldRNGSeed, constraints, 2,1,4,1);
	    addToPanel(interactiveArea, nameDailyMean, constraints, 1,2,4,1);
	    addToPanel(interactiveArea, fieldDailyMean, constraints, 2,2,4,1);
	    addToPanel(interactiveArea, nameQueueSize, constraints, 1,3,4,1);
	    addToPanel(interactiveArea, fieldQueueSize, constraints, 2,3,4,1);
	    addToPanel(interactiveArea, nameCancellation, constraints, 1,4,4,1);
	    addToPanel(interactiveArea, fieldCancellation, constraints, 2,4,4,1);
	    
	    addToPanel(interactiveArea, titleFareClasses, constraints, 4,0,4,1);
	    addToPanel(interactiveArea, nameFirst, constraints, 4,1,4,1);
	    addToPanel(interactiveArea, fieldFirst, constraints, 5,1,4,1);
	    addToPanel(interactiveArea, nameBusiness, constraints, 4,2,4,1);
	    addToPanel(interactiveArea, fieldBusiness, constraints, 5,2,4,1);
	    addToPanel(interactiveArea, namePremium, constraints, 4,3,4,1);
	    addToPanel(interactiveArea, fieldPremium, constraints, 5,3,4,1);
	    addToPanel(interactiveArea, nameEconomy, constraints, 4,4,4,1);
	    addToPanel(interactiveArea, fieldEconomy, constraints, 5,4,4,1);
	}
	
   private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
      constraints.gridx = x;
      constraints.gridy = y;
      constraints.gridwidth = w;
      constraints.gridheight = h;
      jp.add(c, constraints);
   }
	
}
