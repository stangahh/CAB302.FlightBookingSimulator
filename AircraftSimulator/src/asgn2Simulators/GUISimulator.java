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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import asgn2Aircraft.AircraftException;
import asgn2Aircraft.Bookings;
import asgn2Passengers.PassengerException;

/**
 * @author Megan Hunter, Jesse Stanger, hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements ActionListener, Runnable {

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;

	private static final String CHART_TITLE = "Flight Bookings";
	private static final String CHART2_TITLE = "Flight Data";

	private static final String FONT = "Arial";
	private static final int TEXT_TITLE_FONT_SIZE = 30;
	private static final int TEXT_FONT_SIZE = 20;
	private static final int TEXT_FIELD_LENGTH = 10;

	private static Boolean textOutput = false;
	private static Boolean fieldError = false;
	private static Boolean resetFields = false;

	private static Boolean chartSwapped = false;
	private JFreeChart	chart1;
	private JFreeChart	chart2;
	private ChartPanel 	CP1;
	private ChartPanel	CP2;

	private JPanel 		container;

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

	private Simulator sim = null;
	private SimulationRunner SR = null;
	private Log log;

	/**
	 * @param arg0
	 * @throws HeadlessException
	 * @throws SimulationException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
		try {
			sim = new Simulator();
			log = new Log();
			SR = new SimulationRunner(this.sim, this.log);
			SR.runSimulation();
		} catch (SimulationException | IOException | AircraftException | PassengerException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		TimeSeriesCollection dataset;
		try {
			dataset = createTimeSeriesData();
			chart1 = createChart(dataset);
			CP1 = new ChartPanel(chart1);
			this.add(CP1, BorderLayout.CENTER);
		} catch (SimulationException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		switch (checkOutputVersion()) {
		case 0:
			textOutput = false;
			try {
				createGUI();
			} catch (SimulationException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			break;
		case 1:
			textOutput = true;
			try {
				createGUI();
				printLogOutput();
			} catch (SimulationException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			break;
		}
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
			if (resetFields) {
				fieldRNGSeed.setText(String.valueOf(Constants.DEFAULT_SEED));
				fieldDailyMean.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
				fieldQueueSize.setText(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
				fieldCancellation.setText(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
				fieldFirst.setText(String.valueOf(Constants.DEFAULT_FIRST_PROB));
				fieldBusiness.setText(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
				fieldPremium.setText(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
				fieldEconomy.setText(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));
				resetFields = false;
			}
			
			if (!fieldError) {
				try {
					sim = new Simulator(
					    Integer.parseInt(fieldRNGSeed.getText()),
					    Integer.parseInt(fieldQueueSize.getText()),
					    Double.parseDouble(fieldDailyMean.getText()),
					    0.33 * Double.parseDouble(fieldDailyMean.getText()),
					    Double.parseDouble(fieldFirst.getText()),
					    Double.parseDouble(fieldBusiness.getText()),
					    Double.parseDouble(fieldPremium.getText()),
					    Double.parseDouble(fieldEconomy.getText()),
					    Double.parseDouble(fieldCancellation.getText())
					);
					log = new Log();
					SR = new SimulationRunner(this.sim, this.log);
					SR.runSimulation();

					if (textOutput) {
						printLogOutput();
					} else if (!textOutput) {
						if (!chartSwapped) {
							chart1 = createChart(createTimeSeriesData());
							CP1 = new ChartPanel(chart1);
							container.add(CP1, BorderLayout.CENTER);
							container.validate();
						} else {
							chart2 = createBarChart(createDataset());
							CP2 = new ChartPanel(chart2);
							container.add(CP2, BorderLayout.CENTER);
							container.validate();
						}
					}
				} catch (SimulationException | AircraftException | PassengerException | IOException e1) {
					e1.printStackTrace();
					System.exit(-1);
				}
			}
		} else if (src == swapCharts) {
			if (!textOutput) {
				if (chartSwapped) {
					chartSwapped = false;
				} else {
					chartSwapped = true;
				}

				if (!chartSwapped) {
					try {
						container.remove(CP2);
						chart1 = createChart(createTimeSeriesData());
						CP1 = new ChartPanel(chart1);
						container.add(CP1, BorderLayout.CENTER);
						container.validate();
					} catch (SimulationException e1) {
						e1.printStackTrace();
					}
				} else {
					container.remove(CP1);
					chart2 = createBarChart(createDataset());
					CP2 = new ChartPanel(chart2);
					container.add(CP2, BorderLayout.CENTER);
					container.validate();
				}
			}
		}
	}

	private void printLogOutput() throws SimulationException {
		String logString = null;
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String capacities = sim.getFlights(Constants.FIRST_FLIGHT).initialState();

		logString = timeLog + ": Start of Simulation\n\n";

		logString += "Simulator [meanDailyBookings = " + fieldDailyMean.getText() + ", sdDailyBookings = " + 0.33 * Double.parseDouble(fieldDailyMean.getText())
		             + ", seed = " + fieldRNGSeed.getText() + ", firstProb = " + fieldFirst.getText() + ", businessProb = "
		             + fieldBusiness.getText() + ", premiumProb = " + fieldPremium.getText()
		             + ", economyProb = " + fieldEconomy.getText() + ", maxQueueSize = " + fieldQueueSize.getText()
		             + ", cancellationProb = " + fieldCancellation.getText() + "]\n";

		logString += capacities + "\n";

		for (int time = 0; time <= Constants.DURATION; time++) {
			logString += sim.getSummary(time, (time >= Constants.FIRST_FLIGHT));
		}

		logString += "\n" + timeLog + ": End of Simulation";

		logString += "\nFinal Totals: [F" + sim.getTotalFirst()
		             + ":J" + sim.getTotalBusiness()
		             + ":P" + sim.getTotalPremium()
		             + ":Y" + sim.getTotalEconomy()
		             + ":T" + sim.getTotalFlown()
		             + ":E" + sim.getTotalEmpty()
		             + ":R" + sim.numRefused() + "]\n";

		logText.setText(logString);
	}

	/**
	 * @throws SimulationException
	 */
	private void createGUI() throws SimulationException {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);

		//Main Panel
		container = createPanel(Color.WHITE);
		container.setLayout(new BorderLayout());

		//Sub Panels
		JFreeChart chart = createChart(createTimeSeriesData());

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

		container.add(interactiveArea, BorderLayout.SOUTH);

		layoutInteractivePanel();

		if (textOutput) {
			container.add(textArea, BorderLayout.CENTER);
			layoutTextPanel();
		} else {
			CP1 = new ChartPanel(chart);
			container.add(CP1, BorderLayout.CENTER);
			container.validate();
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

		addToPanel(textArea, textScroll, constraints, 					0, 0, 1, 1);
	}

	private void layoutInteractivePanel() {
		GridBagLayout layout = new GridBagLayout();
		interactiveArea.setLayout(layout);

		GridBagConstraints constraints = new GridBagConstraints();

		//Titles
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.5;

		addToPanel(interactiveArea, titleSimulation, constraints, 		0, 0, 6, 2);
		addToPanel(interactiveArea, titleFareClasses, constraints, 		6, 0, 6, 2);

		//Names
		addToPanel(interactiveArea, nameRNGSeed, constraints, 			0, 2, 1, 1);
		addToPanel(interactiveArea, nameDailyMean, constraints, 		0, 3, 1, 1);
		addToPanel(interactiveArea, nameQueueSize, constraints, 		0, 4, 1, 1);
		addToPanel(interactiveArea, nameCancellation, constraints, 		0, 5, 1, 1);

		addToPanel(interactiveArea, nameFirst, constraints, 			6, 2, 3, 1);
		addToPanel(interactiveArea, nameBusiness, constraints, 			6, 3, 3, 1);
		addToPanel(interactiveArea, namePremium, constraints, 			6, 4, 3, 1);
		addToPanel(interactiveArea, nameEconomy, constraints, 			6, 5, 3, 1);

		//Fields
		constraints.anchor = GridBagConstraints.LINE_START;

		addToPanel(interactiveArea, fieldRNGSeed, constraints, 			3, 2, 3, 1);
		addToPanel(interactiveArea, fieldDailyMean, constraints, 		3, 3, 3, 1);
		addToPanel(interactiveArea, fieldQueueSize, constraints, 		3, 4, 3, 1);
		addToPanel(interactiveArea, fieldCancellation, constraints, 	3, 5, 3, 1);

		addToPanel(interactiveArea, fieldFirst, constraints, 			9, 2, 3, 1);
		addToPanel(interactiveArea, fieldBusiness, constraints, 		9, 3, 3, 1);
		addToPanel(interactiveArea, fieldPremium, constraints, 			9, 4, 3, 1);
		addToPanel(interactiveArea, fieldEconomy, constraints, 			9, 5, 3, 1);

		addToPanel(interactiveArea, runSimulation, constraints, 		12, 1, 8, 3);
		addToPanel(interactiveArea, swapCharts, constraints, 			12, 4, 8, 3);

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
		

		if (!RNGSeedValue.matches("^[0-9]+$")) {
			errorMessage += "\nRNG Seed value";
			errorFound = true;
			fieldRNGSeed.setText(String.valueOf(Constants.DEFAULT_SEED));
		}
		if (!dailyMeanValue.matches("^[0-9]+([,.][0-9]+)?$")) {
			errorMessage += "\nDaily mean";
			errorFound = true;
			fieldDailyMean.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
		}
		if (!queueSizeValue.matches("^[0-9]+$")) {
			errorMessage += "\nQueue size";
			errorFound = true;
			fieldQueueSize.setText(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
		}
		if (!cancellationValue.matches("^[0-9]+([,.][0-9]+)?$")) {
			errorMessage += "\nCancellation value";
			errorFound = true;
			fieldCancellation.setText(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
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
			double totalClassProb = Double.parseDouble(firstValue)
			                        + Double.parseDouble(businessValue)
			                        + Double.parseDouble(premiumValue)
			                        + Double.parseDouble(economyValue);
			if (totalClassProb != 1.0) {
				if (errorFound) {
					errorMessage += "\nAnd total probabily of classes doesn't equal 1";
				} else {
					errorMessage += "\nThe total probability of classes doesn't equal 1";
					errorFound = true;
				}
			}
		}

		if (errorFound) {
			switch (checkErrorDialog(errorMessage)) {
			case 0:
				resetFields = false;
				break;
			case 1:
				resetFields = true;
				break;
			}
			fieldError = true;
		} else {
			fieldError = false;
		}

	}

	private int checkErrorDialog(String errorMessage) {
		Object[] options = {"OK", "Reset"};
		return JOptionPane.showOptionDialog(this, errorMessage, "Error", JOptionPane.YES_NO_OPTION, 
				JOptionPane.ERROR_MESSAGE, null, options, options[0]);
	}
	
	/* ------------------------------- JFreeChart & Associated Methods --------------------- */
	private TimeSeriesCollection createTimeSeriesData() throws SimulationException {
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		TimeSeries bookTotal = new TimeSeries("Total Bookings");
		TimeSeries econTotal = new TimeSeries("Economy");
		TimeSeries premTotal = new TimeSeries("Premium");
		TimeSeries busTotal = new TimeSeries("Business");
		TimeSeries firstTotal = new TimeSeries("First");

		Calendar cal = GregorianCalendar.getInstance();

		for (int i = 0; i <= Constants.DURATION; i++) {
			if (i >= Constants.FIRST_FLIGHT) {
				Flights flights = sim.getFlights(i);
				Bookings counts = flights.getCurrentCounts();

				int economy = 	counts.getNumEconomy();
				int premium = 	counts.getNumPremium();
				int business = 	counts.getNumBusiness();
				int first = 	counts.getNumFirst();

				cal.set(2016, 0, i, 6, 0);
				Date timePoint = cal.getTime();

				bookTotal.add(new Day(timePoint), economy + premium + business + first);
				econTotal.add(new Day(timePoint), economy);
				premTotal.add(new Day(timePoint), premium);
				busTotal.add(new Day(timePoint), business);
				firstTotal.add(new Day(timePoint), first);
			}
		}

		//Collection
		tsc.addSeries(bookTotal);
		tsc.addSeries(econTotal);
		tsc.addSeries(premTotal);
		tsc.addSeries(busTotal);
		tsc.addSeries(firstTotal);

		return tsc;
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
		                              CHART_TITLE, "Days", "Passengers", dataset, true, true, false);
		final XYPlot plot = result.getXYPlot();
		ValueAxis domain = plot.getDomainAxis();
		domain.setAutoRange(true);
		ValueAxis range = plot.getRangeAxis();
		range.setAutoRange(true);
		return result;
	}

	private JFreeChart createBarChart(final CategoryDataset dataset) {
		final JFreeChart result = ChartFactory.createBarChart(
		                              CHART2_TITLE, "Type", "Passengers", dataset);
		result.setBackgroundPaint(Color.WHITE);
		return result;
	}

	private CategoryDataset createDataset() {
		final String capacity = "Capacity";
		final String queue = "Queue";
		final String refused = "Refused";

		final String category1 = "";

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(sim.getTotalEmpty(), capacity, category1);
		dataset.addValue(sim.numInQueue(), queue, category1);
		dataset.addValue(sim.numRefused(), refused, category1);

		return dataset;
	}
}
