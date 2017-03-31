package demo.chart.pie;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PiePlot;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;

import demo.chart.ChartColors;

/*
 * you are able to do many advanced chart customization by extending ChartEngine
 */
public class PieChartEngine extends JFreeChartEngine {
	
	private boolean explode = false;
	
	public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
		jfchart.setBackgroundPaint(Color.white);

		PiePlot piePlot = (PiePlot) jfchart.getPlot();
		piePlot.setLabelBackgroundPaint(ChartColors.COLOR_4);

		//override some default colors
		Paint[] colors = new Paint[] {ChartColors.COLOR_1, ChartColors.COLOR_2, ChartColors.COLOR_3, ChartColors.COLOR_4};
		DefaultDrawingSupplier defaults = new DefaultDrawingSupplier();
		piePlot.setDrawingSupplier(new DefaultDrawingSupplier(colors, new Paint[]{defaults.getNextFillPaint()}, new Paint[]{defaults.getNextOutlinePaint()}, 
				new Stroke[]{defaults.getNextStroke()}, new Stroke[] {defaults.getNextOutlineStroke()}, new Shape[] {defaults.getNextShape()}));
		
		piePlot.setShadowPaint(null);

		piePlot.setSectionOutlinesVisible(false);

		piePlot.setExplodePercent("Java", explode ? 0.2 : 0);

		return false;
	}

	public void setExplode(boolean explode) {
		this.explode = explode;
	}
}
