package Util;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;

public class ChartPieEngine extends JFreeChartEngine {
	private static final long serialVersionUID = 1L;
	private boolean explode = false;

	public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
		jfchart.setBackgroundPaint(Color.white);

		Font titleFont = new Font("黑体", Font.BOLD, 18);
		Font legendFont = new Font("黑体", Font.PLAIN, 12);
		Font axisFont = new Font("黑体", Font.PLAIN, 12);

		jfchart.getTitle().setFont(titleFont);

		jfchart.getLegend().setItemFont(legendFont);
		jfchart.getLegend().setVisible(true);

		PiePlot piePlot = (PiePlot) jfchart.getPlot();
		piePlot.setLabelFont(axisFont);
		piePlot.setShadowPaint(null);
		piePlot.setSectionOutlinesVisible(false);

		return false;
	}

	public void setExplode(boolean explode) {
		this.explode = explode;
	}

}