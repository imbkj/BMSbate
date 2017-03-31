package chart.bar;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Label;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Window;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;

import sun.org.mozilla.javascript.internal.ObjArray;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Line;

import chart.line.LineChartEngine;
import dal.Statistics.Stat_SelectDal;

import Controller.EmResidencePermit.newExcelImpl;
import Model.StatisticsResultModel;
import Util.ChartEngine;
import Util.ChartPieEngine;

public class BarChartVM {

	private Object[] nameList;
	private Object[] receivableList;
	private Object[] paidInList;

	@Init
	public void init() {

		// int size = list.size();
	}

	@Command("pie_load")
	public void pie_load(@BindingParam("st_name") String st_name) {
		Stat_SelectDal dal = new Stat_SelectDal();
		List<StatisticsResultModel> list = dal.getStatResult(6, st_name, "");
		nameList = new Object[list.size()];
		receivableList = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getStre_content());

			System.out.println(list.get(i).getStre_content());
			nameList[i] = list.get(i).getStre_content();
			receivableList[i] = new PieData(list.get(i).getStre_content(),
					Float.valueOf(list.get(i).getStre_sum()));
		}

		pieChart(st_name);
	}

	@Command("bar_load")
	public void bar_load(@BindingParam("st_name") String st_name,
			@BindingParam("st_id") String st_id) {
		Stat_SelectDal dal = new Stat_SelectDal();
		List<StatisticsResultModel> list = dal.getStatResult(6, st_name, "");

		nameList = new Object[list.size()];
		receivableList = new Object[list.size()];
		paidInList = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {

			nameList[i] = list.get(i).getStre_smonth();
			receivableList[i] = list.get(i).getStre_content();
			paidInList[i] = list.get(i).getStre_sum();
		}

		if (list.size() != 0) {
			barChart(st_name, receivableList, st_id,list.get(0).getStre_content());
		}
	}

	@Command("line_load")
	public void line_load(@BindingParam("st_name") String st_name,
			@BindingParam("st_id") String st_id) {
		Stat_SelectDal dal = new Stat_SelectDal();
		List<StatisticsResultModel> list = dal.getStatResult(6, st_name, "");

		nameList = new Object[list.size()];
		receivableList = new Object[list.size()];
		paidInList = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {

			nameList[i] = list.get(i).getStre_smonth();
			receivableList[i] = list.get(i).getStre_content();
			paidInList[i] = list.get(i).getStre_sum();
		}

		if (list.size() != 0) {
			lineChart(st_name, receivableList, st_id,list.get(0).getStre_content());
		}
	}

	// 饼图
	private void pieChart(String name) {
		// 创建Option对象
		GsonOption option = new GsonOption();

		// 设置图表标题，并且居中显示
		option.title().text(name).x(X.left);
		option.tooltip().trigger(Trigger.item)
				.formatter("{a} <br/>{b} : {c} ({d}%)");
		option.legend().orient(Orient.horizontal).x(X.center).y(Y.bottom)
				.data(nameList);
		option.toolbox()
				.show(true)
				.feature(Tool.mark, Tool.dataView,
						new MagicType(Magic.pie, Magic.funnel).show(true),
						Tool.restore, Tool.saveAsImage);
		option.series(new Pie().name(name).data(receivableList));

		String js = "var op=" + option.toString() + ";";
		String id = "var comId='" + Path.getComponent("/win/div").getUuid()
				+ "';";
		Clients.evalJavaScript(id + js + "getChart(comId,op);");
	}

	// 柱状图
	private void barChart(String name, Object[] con_name, String st_id,String st_name) {
		// 创建Option对象
		GsonOption option = new GsonOption();

		// 设置图表标题
		option.title().text(name).x(X.left);

		option.tooltip().trigger(Trigger.axis);

		option.legend(st_name);
		option.toolbox()
				.show(true)
				.feature(Tool.mark, Tool.dataView,
						new MagicType(Magic.line, Magic.bar).show(true),
						Tool.restore, Tool.saveAsImage);
		option.calculable(true);
		option.xAxis(new CategoryAxis().data(nameList));
		option.yAxis(new ValueAxis());

		Bar bar2 = new Bar("数值");
		bar2.data(paidInList);

		option.series(bar2);
		String js = "var op=" + option.toString() + ";";
		String st_ida = "/win/" + st_id;
		String id = "var comId='" + Path.getComponent(st_ida).getUuid() + "';";
		Clients.evalJavaScript(id + js + "getChart(comId,op);");
	}

	// 折线图
	private void lineChart(String name, Object[] con_name, String st_id,String st_name) {
		// 创建Option对象
		GsonOption option = new GsonOption();

		// 设置图表标题
		option.title().text(name).x(X.left);

		option.tooltip().trigger(Trigger.axis);

		option.legend(st_name);
		option.toolbox()
				.show(true)
				.feature(Tool.mark, Tool.dataView,
						new MagicType(Magic.line, Magic.bar).show(true),
						Tool.restore, Tool.saveAsImage);
		option.calculable(true);
		option.xAxis(new CategoryAxis().data(nameList));
		option.yAxis(new ValueAxis());

		Line line = new Line("数值");
		line.data(paidInList);

		option.series(line);
		String js = "var op=" + option.toString() + ";";
		String st_ida = "/win/" + st_id;
		String id = "var comId='" + Path.getComponent(st_ida).getUuid() + "';";
		Clients.evalJavaScript(id + js + "getChart(comId,op);");
	}

	// 弹出发送新短信窗口
	@Command("sysmessageadd")
	public void sysmessageadd() {
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_Add.zul", null, null);
		window.doModal();
	}

	// 弹出草稿列表窗口
	@Command("draft")
	public void draft() {
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_DraftList.zul", null, null);
		window.doModal();
	}

	// 弹出已发送窗口
	@Command("sended")
	public void sended() {
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_SendList.zul", null, null);
		window.doModal();
	}

	// 弹出收件箱窗口
	@Command("reci")
	public void reci() {
		Window window = (Window) Executions.createComponents(
				"/SysMessage/SysMessage_ReciList.zul", null, null);
		window.doModal();
	}
}
