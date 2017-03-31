package Controller.BusinessIntelligence;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.github.abel533.echarts.Polar;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Radar;

import bll.BusinessIntelligence.CoFinanceBll;

import Model.CoFinanceMonthlyBillSortAccountModel;

public class CoFinanceSortAccountChartController {
	private String ownmonth;
	private String type;
	private List<CoFinanceMonthlyBillSortAccountModel> list;
	private Object[] nameList;
	private Object[] receivableList;
	private Object[] paidInList;
	private CoFinanceBll bll;

	public CoFinanceSortAccountChartController() {
		bll = new CoFinanceBll();
	}

	@Command("search")
	public void search() {
		try {
			if (checkPage()) {
				getData();
				switch (type) {
				case "柱状图":
					barChart();
					break;
				case "饼图":
					pieChart();
					break;
				case "雷达图":
					radarChart();
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取数据
	private void getData() {
		try {
			list = bll.getMonthlySortAccount(Integer.parseInt(ownmonth));
			if (list.size() > 0) {
				int size = list.size();
				nameList = new Object[size];
				receivableList = new Object[size];
				paidInList = new Object[size];
				switch (type) {
				case "柱状图":
					for (int i = 0; i < size; i++) {
						nameList[i] = list.get(i).getCfsa_cpac_name();
						receivableList[i] = list.get(i).getCfsa_Receivable();
						paidInList[i] = list.get(i).getCfsa_PaidIn();
					}
					break;
				case "饼图":
					for (int i = 0; i < size; i++) {
						nameList[i] = list.get(i).getCfsa_cpac_name();
						receivableList[i] = new PieData(list.get(i)
								.getCfsa_cpac_name(), list.get(i)
								.getCfsa_Receivable());
					}
					break;
				case "雷达图":
					for (int i = 0; i < size; i++) {
						nameList[i] = new Data().text(list.get(i)
								.getCfsa_cpac_name());
						receivableList[i] = list.get(i).getCfsa_Receivable();
						paidInList[i] = list.get(i).getCfsa_PaidIn();
					}
					break;
				default:
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 检查页面值
	private boolean checkPage() {
		try {
			if (ownmonth == null || "".equals(ownmonth)) {
				Messagebox.show("请选择所属月份。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				return false;
			} else if (type == null || "".equals(type)) {
				Messagebox.show("请选择报表类型。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 柱状图
	private void barChart() {
		// 创建Option对象
		GsonOption option = new GsonOption();

		// 设置图表标题
		option.title().text("财务科目统计图").x(X.left);
		option.title().subtext(ownmonth).x(X.left);

		option.tooltip().trigger(Trigger.axis);

		option.legend("应收款", "实收款");
		option.toolbox()
				.show(true)
				.feature(Tool.mark, Tool.dataView,
						new MagicType(Magic.line, Magic.bar).show(true),
						Tool.restore, Tool.saveAsImage);
		option.calculable(true);
		option.xAxis(new CategoryAxis().data(nameList));
		option.yAxis(new ValueAxis());

		Bar bar = new Bar("应收款");
		bar.data(receivableList);
		bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"),
				new PointData().type(MarkType.min).name("最小值"));
		bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));

		Bar bar2 = new Bar("实收款");
		bar2.data(paidInList);
		bar2.markPoint().data(new PointData().type(MarkType.max).name("最大值"),
				new PointData().type(MarkType.min).name("最小值"));
		bar2.markLine()
				.data(new PointData().type(MarkType.average).name("平均值"));

		option.series(bar, bar2);
		String js = "var op=" + option.toString() + ";";
		String id = "var comId='" + Path.getComponent("/win/div").getUuid()
				+ "';";
		Clients.evalJavaScript(id + js + "getChart(comId,op);");
	}

	// 饼图
	private void pieChart() {
		// 创建Option对象
		GsonOption option = new GsonOption();

		// 设置图表标题，并且居中显示
		option.title().text("财务科目应收统计图").x(X.left);
		option.title().subtext(ownmonth).x(X.left);
		option.tooltip().trigger(Trigger.item)
				.formatter("{a} <br/>{b} : {c} ({d}%)");
		option.legend().orient(Orient.horizontal).x(X.center).y(Y.bottom)
				.data(nameList);
		option.toolbox()
				.show(true)
				.feature(Tool.mark, Tool.dataView,
						new MagicType(Magic.pie, Magic.funnel).show(true),
						Tool.restore, Tool.saveAsImage);
		option.series(new Pie().name("财务科目应收统计图（" + ownmonth + "）").data(
				receivableList));
		String js = "var op=" + option.toString() + ";";
		String id = "var comId='" + Path.getComponent("/win/div").getUuid()
				+ "';";
		Clients.evalJavaScript(id + js + "getChart(comId,op);");
	}

	// 雷达图
	private void radarChart() {
		// 创建Option对象
		GsonOption option = new GsonOption();

		option.title().text("财务科目统计图").x(X.left);
		option.title().subtext(ownmonth).x(X.left);
		option.tooltip(Trigger.axis);
		option.legend().orient(Orient.vertical).x(X.left).y(Y.bottom)
				.data("应收款", "实收款");
		option.toolbox()
				.show(true)
				.feature(Tool.mark, Tool.dataView, Tool.restore,
						Tool.saveAsImage);
		option.calculable(true);

		Polar polar = new Polar();
		polar.indicator(nameList);
		option.polar(polar);

		Radar radar = new Radar("财务科目统计图");
		radar.data(new Data().name("应收款").value(receivableList), new Data()
				.name("实收款").value(paidInList));
		option.series(radar);
		String js = "var op=" + option.toString() + ";";
		String id = "var comId='" + Path.getComponent("/win/div").getUuid()
				+ "';";
		Clients.evalJavaScript(id + js + "getChart(comId,op);");
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
