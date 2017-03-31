package Controller.EmSheBao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmSheBaoFinanceModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsc_SZSI_FinanceController {
	MonthListUtil mlu = new MonthListUtil();
	private DateStringChange dsc = new DateStringChange();
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private List<EmSheBaoFinanceModel> sbDataList;

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	// 所属月份下拉框
	private String[] s_ownmonth = mlu.getMonthList(true, nowmonth, 2, 9);
	// 获取用户名
	String username = UserInfo.getUsername();
	// 统计数据的数组
	private String[] count = new String[4];

	// 独立户数据
	int szsi_1_cou = 0;// 参保人数

	// 所有数据
	int szsi_a_cou = 0;// 所有参保人数

	// 中智大户数据
	int szsi_0_cou = 0;// 参保人数
	int sb_0_cou = 0;// 系统人数
	int ce_0_cou = 0;// 差额
	BigDecimal szsi_0_cost;// 参保人数
	BigDecimal sb_0_cost;// 系统人数
	BigDecimal ce_0_cost;// 差额

	// 中智大户(委托)数据
	int szsi_2_cou = 0;// 参保人数
	int sb_2_cou = 0;// 系统人数
	int ce_2_cou = 0;// 差额
	BigDecimal szsi_2_cost;// 参保人数
	BigDecimal sb_2_cost;// 系统人数
	BigDecimal ce_2_cost;// 差额

	public Emsc_SZSI_FinanceController() {
		select(nowmonth);
	}

	// 查询公司合同基本信息
	@Command("search")
	@NotifyChange({ "sbDataList", "szsi_0_cou", "szsi_1_cou", "szsi_2_cou",
			"szsi_a_cou", "sb_0_cou", "sb_2_cou", "ce_0_cou", "ce_2_cou",
			"szsi_0_cost", "sb_0_cost", "ce_0_cost", "szsi_2_cost",
			"sb_2_cost", "ce_2_cost" })
	public void search(@BindingParam("s_ownmonth") Combobox s_ownmonth) {
		if (s_ownmonth.getSelectedItem() != null) {
			String ownmonth = s_ownmonth.getSelectedItem().getValue();

			select(ownmonth);
		}
	}

	// 删除
	@Command
	@NotifyChange({ "sbDataList", "szsi_0_cou", "szsi_1_cou", "szsi_2_cou",
			"szsi_a_cou", "sb_0_cou", "sb_2_cou", "ce_0_cou", "ce_2_cou",
			"szsi_0_cost", "sb_0_cost", "ce_0_cost", "szsi_2_cost",
			"sb_2_cost", "ce_2_cost" })
	public void delete(@BindingParam("sbid") final String sbid,
			@BindingParam("s_ownmonth") final Combobox s_ownmonth) {
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.OK.equals(event.getButton())) {
					if (sbid != null && s_ownmonth.getSelectedItem() != null) {
						String ownmonth = s_ownmonth.getSelectedItem()
								.getValue();
						boolean flag = dobll.deleteSZSIFinance(Integer
								.valueOf(sbid));
						if (flag) {
							Messagebox.show("删除成功", "操作提示", Messagebox.OK,
									Messagebox.NONE);
							// 刷新
							select(ownmonth);
						} else {
							Messagebox.show("删除失败", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				}
			}
		};
		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION, clickListener);

	}

	// 查询数据
	public void select(String ownmonth) {
		// 获取台账前数据
		EmSheBaoFinanceModel m = new EmSheBaoFinanceModel();
		m = (EmSheBaoFinanceModel) dsbll.getSZSIFinance(ownmonth).get(0);
		// szsi_1_cou = m.getSzsi_1_cou();
		szsi_0_cou = m.getSzsi_0_cou();
		szsi_2_cou = m.getSzsi_2_cou();
		// szsi_a_cou = m.getShebao_count();
		szsi_0_cost = m.getSzsi_0_cost();
		szsi_2_cost = m.getSzsi_2_cost();

		sb_0_cou = m.getSb_0_cou();
		sb_2_cou = m.getSb_2_cou();
		sb_0_cost = m.getSb_0_cost();
		sb_2_cost = m.getSb_2_cost();

		ce_0_cou = m.getCe_0_cou();
		ce_2_cou = m.getCe_2_cou();
		ce_0_cost = m.getCe_0_cost();
		ce_2_cost = m.getCe_2_cost();

		// 独立户数据
		sbDataList = dsbll.getSZSIFinanceSingle(ownmonth);

		// 在册和台后统计数据
		count = dsbll.getSZSICount(nowmonth);
	}

	// 清空台账
	@Command("clean")
	@NotifyChange({ "sbDataList", "szsi_0_cou", "szsi_1_cou", "szsi_2_cou",
			"szsi_a_cou", "sb_0_cou", "sb_2_cou", "ce_0_cou", "ce_2_cou",
			"szsi_0_cost", "sb_0_cost", "ce_0_cost", "szsi_2_cost",
			"sb_2_cost", "ce_2_cost" })
	public void clean(@BindingParam("s_ownmonth") Combobox s_ownmonth) {
		String message[] = dobll.deleteSZSI();

		if (message[0].equals("1")) {
			// 弹出提示
			Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.NONE);
			// 刷新
			if (s_ownmonth.getSelectedItem() != null) {
				String ownmonth = s_ownmonth.getSelectedItem().getValue();

				select(ownmonth);
			}
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 启用逻辑检查
	@Command("checkErr")
	public void checkErr() {
		String message[] = dobll.chkErrSZSI(username);

		if (message[0].equals("1")) {
			// 弹出提示
			Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.NONE);

		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 上传文件
	@Command("upload")
	@NotifyChange({ "sbDataList", "szsi_0_cou", "szsi_1_cou", "szsi_2_cou",
			"szsi_a_cou", "sb_0_cou", "sb_2_cou", "ce_0_cou", "ce_2_cou",
			"szsi_0_cost", "sb_0_cost", "ce_0_cost", "szsi_2_cost",
			"sb_2_cost", "ce_2_cost" })
	public void upload(@BindingParam("url") String url,
			@BindingParam("s_ownmonth") Combobox s_ownmonth) {
		Window window = (Window) Executions.createComponents(url, null, null);
		window.doModal();
		// 刷新
		if (s_ownmonth.getSelectedItem() != null) {
			String ownmonth = s_ownmonth.getSelectedItem().getValue();

			select(ownmonth);
		}
	}

	public List<EmSheBaoFinanceModel> getSbDataList() {
		return sbDataList;
	}

	public void setSbDataList(List<EmSheBaoFinanceModel> sbDataList) {
		this.sbDataList = sbDataList;
	}

	public int getSzsi_1_cou() {
		return szsi_1_cou;
	}

	public void setSzsi_1_cou(int szsi_1_cou) {
		this.szsi_1_cou = szsi_1_cou;
	}

	public int getSzsi_a_cou() {
		return szsi_a_cou;
	}

	public void setSzsi_a_cou(int szsi_a_cou) {
		this.szsi_a_cou = szsi_a_cou;
	}

	public int getSzsi_0_cou() {
		return szsi_0_cou;
	}

	public void setSzsi_0_cou(int szsi_0_cou) {
		this.szsi_0_cou = szsi_0_cou;
	}

	public int getSb_0_cou() {
		return sb_0_cou;
	}

	public void setSb_0_cou(int sb_0_cou) {
		this.sb_0_cou = sb_0_cou;
	}

	public int getCe_0_cou() {
		return ce_0_cou;
	}

	public void setCe_0_cou(int ce_0_cou) {
		this.ce_0_cou = ce_0_cou;
	}

	public BigDecimal getSzsi_0_cost() {
		return szsi_0_cost;
	}

	public void setSzsi_0_cost(BigDecimal szsi_0_cost) {
		this.szsi_0_cost = szsi_0_cost;
	}

	public BigDecimal getSb_0_cost() {
		return sb_0_cost;
	}

	public void setSb_0_cost(BigDecimal sb_0_cost) {
		this.sb_0_cost = sb_0_cost;
	}

	public BigDecimal getCe_0_cost() {
		return ce_0_cost;
	}

	public void setCe_0_cost(BigDecimal ce_0_cost) {
		this.ce_0_cost = ce_0_cost;
	}

	public int getSzsi_2_cou() {
		return szsi_2_cou;
	}

	public void setSzsi_2_cou(int szsi_2_cou) {
		this.szsi_2_cou = szsi_2_cou;
	}

	public int getSb_2_cou() {
		return sb_2_cou;
	}

	public void setSb_2_cou(int sb_2_cou) {
		this.sb_2_cou = sb_2_cou;
	}

	public int getCe_2_cou() {
		return ce_2_cou;
	}

	public void setCe_2_cou(int ce_2_cou) {
		this.ce_2_cou = ce_2_cou;
	}

	public BigDecimal getSzsi_2_cost() {
		return szsi_2_cost;
	}

	public void setSzsi_2_cost(BigDecimal szsi_2_cost) {
		this.szsi_2_cost = szsi_2_cost;
	}

	public BigDecimal getSb_2_cost() {
		return sb_2_cost;
	}

	public void setSb_2_cost(BigDecimal sb_2_cost) {
		this.sb_2_cost = sb_2_cost;
	}

	public BigDecimal getCe_2_cost() {
		return ce_2_cost;
	}

	public void setCe_2_cost(BigDecimal ce_2_cost) {
		this.ce_2_cost = ce_2_cost;
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

	public String[] getCount() {
		return count;
	}

	public void setCount(String[] count) {
		this.count = count;
	}

}
