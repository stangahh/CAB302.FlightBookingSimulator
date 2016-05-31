package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel.*;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import asgn2Simulators.Constants;

@SuppressWarnings("serial")
public class ChartPanel extends ApplicationFrame {
	
	private static final String CHART_TITLE = "Random Bookings";

	public ChartPanel(final String title) {
		super(title);
        final TimeSeriesCollection dataset = createTimeSeriesData(); 
        JFreeChart chart = createChart(dataset);
        this.add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ChartPanel chart = new ChartPanel(CHART_TITLE);
            	chart.pack();
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible(true);
            }
        });
	}
	
	private TimeSeriesCollection createTimeSeriesData() {
		TimeSeriesCollection tsc = new TimeSeriesCollection(); 
		TimeSeries bookTotal = new TimeSeries("Total Bookings");
		TimeSeries econTotal = new TimeSeries("Economy"); 
		TimeSeries premTotal = new TimeSeries("Premium");
		TimeSeries busTotal = new TimeSeries("Business");
		TimeSeries firstTotal = new TimeSeries("First");
		
		//Base time, data set up - the calendar is needed for the time points
		Calendar cal = GregorianCalendar.getInstance();
		Random rng = new Random(250); 
		
		int economy = 0;
		int premium = 1;
		int business = 2; 
		int first = 3; 
		
	    
		//These lines are important 
		for (int i = 0; i <= 10; i++) {
			cal.set(2016, 0, i, 6, 0);
			Date timePoint = cal.getTime();
			
			bookTotal.add(new Day(timePoint), economy+premium+business+first);
			econTotal.add(new Day(timePoint), economy);
			premTotal.add(new Day(timePoint), premium);
			busTotal.add(new Day(timePoint), business);
			firstTotal.add(new Day(timePoint), first);
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

}
