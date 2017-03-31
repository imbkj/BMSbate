package chart.bar;

import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;

/*
 * you are able to do many advanced chart customization by extending ChartEngine
 */
public class BarChartEngine extends JFreeChartEngine {

	public boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {
		jfchart.setTitle("客户规模统计");		
		CategoryPlot plot = jfchart.getCategoryPlot();		
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        //Rotation 
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0)
        );

        renderer.setSeriesPaint(0, new Color(0x5489cf));
		renderer.setSeriesPaint(1, new Color(0xFAB043));
		renderer.setSeriesPaint(2, new Color(0x80a41c));
		renderer.setSeriesPaint(3, new Color(0xffdb08));	
		renderer.setSeriesPaint(4, new Color(0xe62171));	
		renderer.setSeriesPaint(5, new Color(0x6d9d30));	
		renderer.setSeriesPaint(6, new Color(0xd4e024));	
		renderer.setSeriesPaint(7, new Color(0xd6e123));	
		return false;
	}
}