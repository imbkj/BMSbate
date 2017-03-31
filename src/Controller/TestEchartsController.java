package Controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;

import Model.CoFinanceMonthlyBillModel;

import bll.BusinessIntelligence.CoFinanceBll;

import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Pie;

public class TestEchartsController {
	private Object[] nameList;
	private Object[] valueList;
	private List<CoFinanceMonthlyBillModel> l = new ArrayList<CoFinanceMonthlyBillModel>();
	private CoFinanceBll bll;

	public TestEchartsController() {
		bll = new CoFinanceBll();
		l = bll.getMonthBillTotle();
		nameList = new Object[l.size()];
		valueList = new Object[l.size()];
		for (int i = 0; i < l.size(); i++) {
			nameList[i] = l.get(i).getOwnmonth();
			valueList[i] = new PieData(String.valueOf(l.get(i).getOwnmonth()),
					l.get(i).getCfmb_FinanceReceivable());
		}

	}

	@Command
	public void init() {
//		// 创建饼图
//		// 创建option对象
//		Option op = new Option();
//		op.title().text("街边调查你幸福吗").x(X.left);
//		op.tooltip().trigger(Trigger.item);
//		op.tooltip().formatter("{a} <br/>{b} : {c} ({d}%)");
//		op.legend().orient(Orient.vertical);
//		op.legend().x(X.left);
//		op.legend().data(nameList);
//		op.toolbox().show(true);
//		op.toolbox().feature(Tool.mark, Tool.dataView,
//				new MagicType(Magic.pie, Magic.funnel).show(true),
//				Tool.saveAsImage, Tool.restore);
//		op.calculable(true);
//		Pie p = new Pie();
//		p.name("街边调查你幸福吗");
//		p.data(valueList);
//		op.series(p);
//		String js = "var op=" + op.toString() + ";";
//		String id = "var comId='" + Path.getComponent("/wins/div").getUuid()
//				+ "';";
//		Clients.evalJavaScript(id + js + "getChart(comId,op);");
		// 创建Option对象
		GsonOption option = new GsonOption();

		// 设置图表标题，并且居中显示
		option.title().text("台账每月应收统计").x(X.left);
		option.tooltip().trigger(Trigger.item)
				.formatter("{a} <br/>{b} : {c} ({d}%)");
		option.legend().orient(Orient.horizontal).x(X.center).y(Y.bottom)
				.data(nameList);
		option.toolbox()
				.show(true)
				.feature(Tool.mark, Tool.dataView,
						new MagicType(Magic.pie, Magic.funnel).show(true),
						Tool.restore, Tool.saveAsImage);
		option.series(new Pie().name("台账每月应收统计").data(valueList));
		String js = "var op=" + option.toString() + ";";
		String id = "var comId='" + Path.getComponent("/wins/div").getUuid()
				+ "';";
		Clients.evalJavaScript(id + js + "getChart(comId,op);");
	}

}
