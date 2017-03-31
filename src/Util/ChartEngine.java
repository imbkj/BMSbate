package Util;

import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;
import java.awt.Font;

public class ChartEngine extends JFreeChartEngine {
	private static final long serialVersionUID = 1L;

	public boolean prepareJFreeChart(org.jfree.chart.JFreeChart jfchart,
			Chart chart) {
		Font titleFont = new Font("黑体", Font.BOLD, 18);
		Font legendFont = new Font("黑体", Font.PLAIN, 12);
		Font axisFont = new Font("黑体", Font.PLAIN, 12);

		jfchart.getTitle().setFont(titleFont);

		jfchart.getLegend().setItemFont(legendFont);
		jfchart.getLegend().setVisible(true);

		jfchart.getCategoryPlot().getDomainAxis().setTickLabelFont(axisFont);
		jfchart.getCategoryPlot().getRangeAxis().setTickLabelFont(axisFont);
		jfchart.getCategoryPlot().getDomainAxis().setLabelFont(axisFont);
		jfchart.getCategoryPlot().getRangeAxis().setLabelFont(axisFont);
		return false;
	}
}
