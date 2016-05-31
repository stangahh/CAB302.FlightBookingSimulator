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
import java.awt.ComponentOrientation;
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
	private static final int TEXT_TITLE_FONT_SIZE = 30;
	private static final int TEXT_FONT_SIZE = 20;
	private static final int TEXT_FIELD_LENGTH = 10;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	
	private JPanel 		container;
	
	//private JPanel 		buttonArea;
	private JPanel 		chartArea;
	private JPanel 		interactiveArea;

	private JButton 	runSimulation;
	private JButton 	swapCharts;
	
	private JTextArea 	titleSimulation;
	
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
//	 */
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
	    
	    //Main Container
	    container = createPanel(Color.WHITE);
	    
	    //Sub Containers
	    chartArea = createPanel(Color.RED);
	    interactiveArea = createPanel(Color.GREEN);
	    //buttonArea = createPanel(Color.BLUE);
	    
	    //Buttons
	    runSimulation = createButton("Run Simulation");
	    swapCharts = createButton("Swap Charts");
	        
	    //Text Areas
	    titleSimulation = createTitleArea("Simulation");
	    nameRNGSeed = createTextArea("RNG Seed");
	    nameDailyMean = createTextArea("Daily Mean");
	    nameQueueSize = createTextArea("Queue Size");
	    nameCancellation = createTextArea("Cancellation");
	    titleFareClasses = createTitleArea("Fare Classes");
	    nameFirst = createTextArea("First");
	    nameBusiness = createTextArea("Business");
	    namePremium = createTextArea("Premium");
	    nameEconomy = createTextArea("Economy");
	    
	    //Text Fields
	    // The "0" will be filled later with info from other classes
	    fieldRNGSeed = createTextField("0");
	    fieldDailyMean = createTextField("0");
	    fieldQueueSize = createTextField("0");
	    fieldCancellation = createTextField("0");
	    fieldFirst = createTextField("0");
	    fieldBusiness = createTextField("0");
	    fieldPremium = createTextField("0");
	    fieldEconomy = createTextField("0");
	    
	    
	    container.setLayout(new BorderLayout());
	    
	    container.add(chartArea, BorderLayout.CENTER);
	    layoutChartPanel();
	    
	    //container.add(buttonArea, BorderLayout.CENTER);
	    //layoutButtonPanel();
	    
	    container.add(interactiveArea, BorderLayout.SOUTH);
	    layoutInteractivePanel();
	    
	    this.getContentPane().add(container, BorderLayout.CENTER);
	    
	    repaint(); 
	    this.setVisible(true);
	}
	
	/* ------------------------------- JComponents Create Methods ------------------------ */
	private JPanel createPanel(Color c) {
		JPanel jp = new JPanel();
		jp.setBackground(c);
		return jp;
	}
	
	private JButton createButton(String str) {
		JButton jb = new JButton(str); 
		jb.setFont(new Font(FONT, Font.PLAIN, TEXT_FONT_SIZE));
		jb.addActionListener(this);
		return jb; 
	}
	
	private JTextArea createTextArea(String str) {
		JTextArea jta = new JTextArea(str); 
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setFont(new Font(FONT, Font.PLAIN, TEXT_FONT_SIZE));
		jta.setOpaque(false);
		//jta.setBorder(BorderFactory.createEtchedBorder());
		return jta;
	}
	private JTextArea createTitleArea(String str) {
		JTextArea jta = new JTextArea(str); 
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setFont(new Font(FONT, Font.BOLD, TEXT_TITLE_FONT_SIZE));
		jta.setOpaque(false);
		//jta.setBorder(BorderFactory.createEtchedBorder());
		return jta;
	}
	
	private JTextField createTextField(String str) {
		JTextField jtf = new JTextField(str, TEXT_FIELD_LENGTH); 
		jtf.setEditable(true);
		jtf.setFont(new Font(FONT, Font.PLAIN, TEXT_FONT_SIZE));
		jtf.setBorder(BorderFactory.createEtchedBorder());
		return jtf;
	}
	
	/* ------------------------------- Layout Methods ----------------------------------- */
	private void layoutChartPanel() {
		//some way of drawing a chart
		GridBagLayout layout = new GridBagLayout();
		chartArea.setLayout(layout);
	    
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    constraints.weightx = 0.5;
	    constraints.weighty = 1;
	    
	    constraints.fill = GridBagConstraints.BOTH;
	    
	    JButton chartTemp = createButton("This is where the chart goes.");
	    
	    addToPanel(chartArea, chartTemp, constraints, 0,0,1,1); 
	}
	
//	private void layoutButtonPanel() {
//		GridBagLayout layout = new GridBagLayout();
//		buttonArea.setLayout(layout);
//		
//	    GridBagConstraints constraints = new GridBagConstraints(); 
//	    
//	    //Buttons
//	    constraints.fill = GridBagConstraints.HORIZONTAL;
//	    constraints.weightx = 1;
//	    constraints.weighty = 1;
//	    
//	    addToPanel(buttonArea, runSimulation, constraints, 0,0,1,1); 
//	    addToPanel(buttonArea, swapCharts, constraints, 1,0,1,1);
//	}
	
	private void layoutInteractivePanel() {
		GridBagLayout layout = new GridBagLayout();
		interactiveArea.setLayout(layout);
	    
	    GridBagConstraints constraints = new GridBagConstraints(); 

	    //Titles
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
	    
	    //Buttons
	    addToPanel(interactiveArea, titleSimulation, constraints, 		0,0,6,2);
	    addToPanel(interactiveArea, titleFareClasses, constraints, 		6,0,6,2);
	    
	    //Names
	    //--------------------------------------------------------------x/y/w/h
	    addToPanel(interactiveArea, nameRNGSeed, constraints, 			0,2,1,1);
	    addToPanel(interactiveArea, nameDailyMean, constraints, 		0,3,1,1);
	    addToPanel(interactiveArea, nameQueueSize, constraints, 		0,4,1,1);
	    addToPanel(interactiveArea, nameCancellation, constraints, 		0,5,1,1);
	    
	    addToPanel(interactiveArea, nameFirst, constraints, 			6,2,3,1);
	    addToPanel(interactiveArea, nameBusiness, constraints, 			6,3,3,1);
	    addToPanel(interactiveArea, namePremium, constraints, 			6,4,3,1);
	    addToPanel(interactiveArea, nameEconomy, constraints, 			6,5,3,1);
	    
	    //Fields
	    constraints.anchor = GridBagConstraints.LINE_START;
	    
	    addToPanel(interactiveArea, fieldRNGSeed, constraints, 			3,2,3,1);
	    addToPanel(interactiveArea, fieldDailyMean, constraints, 		3,3,3,1);
	    addToPanel(interactiveArea, fieldQueueSize, constraints, 		3,4,3,1);
	    addToPanel(interactiveArea, fieldCancellation, constraints, 	3,5,3,1);

	    addToPanel(interactiveArea, fieldFirst, constraints, 			9,2,3,1);
	    addToPanel(interactiveArea, fieldBusiness, constraints, 		9,3,3,1);
	    addToPanel(interactiveArea, fieldPremium, constraints, 			9,4,3,1);
	    addToPanel(interactiveArea, fieldEconomy, constraints, 			9,5,3,1);
	    
	    addToPanel(interactiveArea, runSimulation, constraints, 		12,0,8,3); 
	    addToPanel(interactiveArea, swapCharts, constraints, 			12,3,8,3);
	    
	}
	
   private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
      constraints.gridx = x;
      constraints.gridy = y;
      constraints.gridwidth = w;
      constraints.gridheight = h;
      jp.add(c, constraints);
   }
	
}
