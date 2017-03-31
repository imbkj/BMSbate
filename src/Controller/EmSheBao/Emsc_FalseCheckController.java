package Controller.EmSheBao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmShebaoErrModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsc_FalseCheckController {
	MonthListUtil mlu = new MonthListUtil();
	private DateStringChange dsc = new DateStringChange();
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private List<EmShebaoErrModel> sbDataList;
	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	// 所属月份下拉框
	private String[] s_ownmonth = mlu.getMonthList(true, nowmonth, 2, 9);
	// 获取用户名
	String username = UserInfo.getUsername();

	public Emsc_FalseCheckController() {
		// 获取最新月份数据
		sbDataList = dsbll.getShebaoErr(nowmonth);
	}

	// 查询
	@Command("search")
	@NotifyChange("sbDataList")
	public void search(@BindingParam("s_ownmonth") Combobox s_ownmonth) {
		if (s_ownmonth.getSelectedItem() != null) {
			sbDataList = dsbll.getShebaoErr(s_ownmonth.getSelectedItem()
					.getLabel());
		}
	}

	// 处理
	@Command("edit")
	@NotifyChange("sbDataList")
	public void edit(@BindingParam("model") EmShebaoErrModel m,
			@BindingParam("s_ownmonth") Combobox s_ownmonth) {
		Map map = new HashMap();
		map.put("model", m);
		Window window = (Window) Executions.createComponents(
				"Emsc_FalseCheckMsg.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll
				.getShebaoErr(s_ownmonth.getSelectedItem().getLabel());
	}

	// 对比明细
	@Command("info")
	public void info(@BindingParam("model") EmShebaoErrModel m) {
		Map map = new HashMap();
		map.put("model", m);
		Window window = (Window) Executions.createComponents(
				"Emsc_FalseCheckInfo.zul", null, map);
		window.doModal();
	}

	// 删除
	@Command("clean")
	@NotifyChange("sbDataList")
	public void clean(@BindingParam("s_ownmonth") Combobox s_ownmonth) {
		if (s_ownmonth.getSelectedItem() != null) {
			// 删除台账后逻辑检查数据
			String message[] = dobll.delErrSZSI(s_ownmonth.getSelectedItem()
					.getLabel());
			if (message[0].equals("1")) {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);

				// 刷新
				sbDataList = dsbll.getShebaoErr(s_ownmonth.getSelectedItem()
						.getLabel());

			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
	}

	public String getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(String nowmonth) {
		this.nowmonth = nowmonth;
	}

	public String[] getS_ownmonth() {
		return s_ownmonth;
	}

	public void setS_ownmonth(String[] s_ownmonth) {
		this.s_ownmonth = s_ownmonth;
	}

	public List<EmShebaoErrModel> getSbDataList() {
		return sbDataList;
	}

	public void setSbDataList(List<EmShebaoErrModel> sbDataList) {
		this.sbDataList = sbDataList;
	}
}
