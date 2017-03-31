package chart.line;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;

import chart.ChartColors;

/*
 * you are able to do many advanced chart customization by extending ChartEngine
 */
public class LineChartEngine extends JFreeChartEngine {

	public int width = 2;
	public boolean showLine = true;
	public boolean showShape = true;

	public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
		Font titleFont = new Font("宋体", Font.PLAIN, 18);
		Font legendFont = new Font("宋体", Font.PLAIN, 12);
		Font axisFont = new Font("宋体", Font.PLAIN, 12);
		
		jfchart.getTitle().setFont(titleFont);
		jfchart.getLegend().setItemFont(legendFont);
		jfchart.getLegend().setVisible(true);
		
		jfchart.getCategoryPlot().getDomainAxis().setTickLabelFont(axisFont);
		jfchart.getCategoryPlot().getRangeAxis().setTickLabelFont(axisFont);
		jfchart.getCategoryPlot().getDomainAxis().setLabelFont(axisFont);
		jfchart.getCategoryPlot().getRangeAxis().setLabelFont(axisFont);
		
		LineAndShapeRenderer lrenderer = (LineAndShapeRenderer) ((CategoryPlot) jfchart.getPlot()).getRenderer();
		lrenderer.setSeriesStroke(0, new BasicStroke(width));
		lrenderer.setSeriesStroke(1, new BasicStroke(width));
		lrenderer.setSeriesStroke(2, new BasicStroke(width));

		lrenderer.setSeriesLinesVisible(0, showLine);
		lrenderer.setSeriesLinesVisible(1, showLine);
		lrenderer.setSeriesLinesVisible(2, showLine);

		lrenderer.setSeriesShapesVisible(0, showShape);
		lrenderer.setSeriesShapesVisible(1, showShape);
		lrenderer.setSeriesShapesVisible(2, showShape);
		
		lrenderer.setSeriesPaint(0, new Color(0x508bcf));
		lrenderer.setSeriesPaint(1, new Color(0xf6b243));
		lrenderer.setSeriesPaint(2, new Color(0x7ECEFD));
		return false;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setShowLine(boolean showLine) {
		this.showLine = showLine;
	}

	public void setShowShape(boolean showShape) {
		this.showShape = showShape;
	}
}