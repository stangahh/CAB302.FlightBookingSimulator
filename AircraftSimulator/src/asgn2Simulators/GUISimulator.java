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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;

/**
 * @author Megan Hunter, Jesse Stanger, hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {
	
	private static final String FONT = "Arial";
	private static final int TEXT_TITLE_FONT_SIZE = 30;
	private static final int TEXT_FONT_SIZE = 20;
	private static final int TEXT_FIELD_LENGTH = 10;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public static Boolean textOutput = false;
		
	private JPanel 		container;
	
	private JPanel 		chartArea;
	private JPanel 		interactiveArea;
	
	private JPanel		textArea;
	private JTextArea	logText;
	private JScrollPane	textScroll;

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
		switch (checkOutputVersion()) {
		case 0:
			textOutput = false;
			createGUI();
			//System.out.println("THIS WILL DO GUI STUFF");
			break;
		case 1:
			textOutput = true;
			createGUI();
			//System.out.println("THIS WILL DO TEXT OUTPUT STUFF");
			break;
		}
		//checkOutputVersion();
		//createGUI(); 
	}
	
	private int checkOutputVersion() {
		Object[] options = {"GUI output", "Text output"};
		return JOptionPane.showOptionDialog(null, "Which output version do you want to use?", "Output Version", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource(); 
			
		if (src == runSimulation) {
			//start simulation
			checkTextValues();
			if (textOutput) {
				String logString = null;
				try {
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(getLatestFilefromDir(System.getProperty("user.dir"))));
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					
					while (line != null) {
						sb.append(line);
						sb.append(System.lineSeparator());
						line = br.readLine();
					}
					logString = sb.toString();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				logText.setText(logString);
			}
			
		} else if (src == swapCharts) {
			//change chart
		}
	}
	
	//I GOT THIS FROM STACK OVERFLOW
	private File getLatestFilefromDir(String path){
	    File directory = new File(path);
	    File[] files = directory.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}
	
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		//Use this temporarily to help try out the simulator. 
//		//We will not be using this main as the final executable main method. 
//		//Instead the main we will use is a modified main() in SimulationRunner.java.
//		JFrame.setDefaultLookAndFeelDecorated(true);
//        SwingUtilities.invokeLater(new GUISimulator("BorderLayout"));
//	}

	private void createGUI() {
		setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	    setResizable(false);
	    
	    //Main Panel
	    container = createPanel(Color.WHITE);
	    container.setLayout(new BorderLayout());
	    
	    //Sub Panels
	    chartArea = createPanel(Color.LIGHT_GRAY);
	    chartArea.setLayout(new BorderLayout());
	    
	    interactiveArea = createPanel(Color.CYAN);
	    
	    textArea = createPanel(Color.WHITE);
	    logText = createTextArea("");
	    textScroll = new JScrollPane(logText);
	    textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    	    
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
	    fieldRNGSeed = createTextField(String.valueOf(Constants.DEFAULT_SEED));
	    fieldDailyMean = createTextField(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
	    fieldQueueSize = createTextField(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
	    fieldCancellation = createTextField(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
	    fieldFirst = createTextField(String.valueOf(Constants.DEFAULT_FIRST_PROB));
	    fieldBusiness = createTextField(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
	    fieldPremium = createTextField(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
	    fieldEconomy = createTextField(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));
	    
//	    container.add(new ChartPanel("Simulator"), BorderLayout.NORTH);
	    container.add(interactiveArea, BorderLayout.SOUTH);
	    	    
	    layoutInteractivePanel();
	    if (textOutput) {
	    	container.add(textArea, BorderLayout.CENTER);
	    	layoutTextPanel();
	    }
	   
	    
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
		return jta;
	}
	private JTextArea createTitleArea(String str) {
		JTextArea jta = new JTextArea(str); 
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setFont(new Font(FONT, Font.BOLD, TEXT_TITLE_FONT_SIZE));
		jta.setOpaque(false);
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
	
	private void layoutTextPanel() {
		GridBagLayout layout = new GridBagLayout();  
	    textArea.setLayout(layout);  
	      
	    GridBagConstraints constraints = new GridBagConstraints();    
	    constraints.fill = GridBagConstraints.BOTH;  
	    constraints.weightx = 1;  
	    constraints.weighty = 1;  
	        
	    addToPanel(textArea, textScroll, constraints, 0,0,1,1);  
	}
	
	private void layoutInteractivePanel() {
		GridBagLayout layout = new GridBagLayout();
		interactiveArea.setLayout(layout);
	    
	    GridBagConstraints constraints = new GridBagConstraints(); 

	    //Titles
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.weightx = 0.5;
	    
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
	    
	    addToPanel(interactiveArea, runSimulation, constraints, 		12,1,8,3);
	    addToPanel(interactiveArea, swapCharts, constraints, 			12,4,8,3);
	    
	}
	
   private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
      constraints.gridx = x;
      constraints.gridy = y;
      constraints.gridwidth = w;
      constraints.gridheight = h;
      jp.add(c, constraints);
   }
   
	private void checkTextValues() {
		String RNGSeedValue = fieldRNGSeed.getText();
		String dailyMeanValue = fieldDailyMean.getText();
		String queueSizeValue = fieldQueueSize.getText();
		String cancellationValue = fieldCancellation.getText();
		String firstValue = fieldFirst.getText();
		String businessValue = fieldBusiness.getText();
		String premiumValue = fieldPremium.getText();
		String economyValue = fieldEconomy.getText();
				
		String errorMessage = "There are errors in the following text fields: \n";
		Boolean errorFound = false;
		Boolean classError = false;
		
		if (!RNGSeedValue.matches("^[0-9]*$")) {
			errorMessage += "\nSeed value";
			errorFound = true;
		} 
		if (!dailyMeanValue.matches("^[0-9]+([,.][0-9]+)?$")) {
			errorMessage += "\nDaily mean";
			errorFound = true;
		} 
		if (!queueSizeValue.matches("^[0-9]*$")) {
			errorMessage += "\nQueue size";
			errorFound = true;
		} 
		if (!cancellationValue.matches("^[0-9]+([,.][0-9]+)?$")) {
			errorMessage += "\nCancellation value";
			errorFound = true;
		} 
		if (!firstValue.matches("^[0-9]+([,.][0-9]+)?$")) {
			errorMessage += "\nFirst value";
			classError = true;
			errorFound = true;
		} 
		if (!businessValue.matches("^[0-9]+([,.][0-9]+)?$")) {
			errorMessage += "\nBusiness value";
			classError = true;
			errorFound = true;
		}
		if (!premiumValue.matches("^[0-9]+([,.][0-9]+)?$")) {
			errorMessage += "\nPremium value";
			classError = true;
			errorFound = true;
		} 
		if (!economyValue.matches("^[0-9]+([,.][0-9]+)?$")) {
			errorMessage += "\nEconomy value";
			classError = true;
			errorFound = true;
		}
		if (!classError) {
			double totalClassProb = Double.parseDouble(firstValue) + Double.parseDouble(businessValue) + Double.parseDouble(premiumValue) + Double.parseDouble(economyValue);
			if (totalClassProb != 1.0) {
				if (errorFound) {
					errorMessage += "\nAnd total probabily of classes doesn't equal 1";
				} else {
					errorMessage += "\nThe total probability of classes doesn't equal 1";
				}
			}
		}

		if (errorFound) {
			JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
