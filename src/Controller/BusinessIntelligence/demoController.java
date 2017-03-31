package Controller.BusinessIntelligence;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.util.Clients;

import Model.AccessData;

import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.AxisLine;
import com.github.abel533.echarts.axis.AxisTick;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.SplitArea;
import com.github.abel533.echarts.axis.SplitLine;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SelectedMode;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Map;
import com.github.abel533.echarts.series.MapLocation;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Radar;
import com.github.abel533.echarts.style.LineStyle;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Polar;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.json.OptionUtil;

public class demoController {

	private String sy = "height:800px;width:800px";

	public demoController() {

	}

	/**
	 * 模拟从数据库获取数据
	 * 
	 * @return
	 */
	public List<AccessData> getWeekData() {
		List<AccessData> list = new ArrayList<AccessData>(7);
		list.add(new AccessData("09-16", 4));
		list.add(new AccessData("09-17", 7));
		list.add(new AccessData("09-18", 14));
		list.add(new AccessData("09-19", 304));
		list.add(new AccessData("09-20", 66));
		list.add(new AccessData("09-21", 16));
		list.add(new AccessData("09-22", 205));
		return list;
	}

	@Command("chart")
	public void chart() {
		List<AccessData> datas = getWeekData();

		
		// 创建Option对象
		GsonOption option = new GsonOption();

		  Map map = new Map("Map");
	        map.mapLocation(new MapLocation(X.left, Y.top, 500));
	        map.selectedMode(SelectedMode.multiple);
	        map.itemStyle().normal().borderWidth(2)
	                .borderColor("lightgreen").color("orange")
	                .label().show(true);

	        map.itemStyle().emphasis()
	                .borderWidth(2).borderColor("#fff").color("#32cd32")
	                .label().show(true)
	                .textStyle().color("#fff");
	        Data data = new Data("广东");
	        data.value(Math.round(Math.random() * 1000));
	        data.itemStyle().normal().color("#32cd32")
	                .label().show(true).textStyle().color("#fff").fontSize(15);
	        data.itemStyle().emphasis().borderColor("yellow").color("#cd5c5c")
	                .label().show(false).textStyle().color("blue");

	        map.data(data);
	        map.markPoint().itemStyle().normal().color("skyblue");
	        map.markPoint().data(new Data("天津", 350), new Data("上海", 103));

	        map.geoCoord("上海", "121.4648", "31.2891").geoCoord("天津", "117.4219", "39.4189");

	        option.series(map);
		String js = "var op=" + option.toString() + ";";
		Clients.evalJavaScript(js + "getdata(op);");
	}
	
	public Pie getPie(int idx) {
        return new Pie().name("浏览器（数据纯属虚构）").data(
                new PieData("Chrome", idx * 128 + 80),
                new PieData("Firefox", idx * 64 + 160),
                new PieData("Safari", idx * 32 + 320),
                new PieData("IE9+", idx * 16 + 640),
                new PieData("IE8-", idx * 8 + 1280));
    }

	public String getSy() {
		return sy;
	}

}
