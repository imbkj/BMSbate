package Controller.CoSocialInsurance;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoShebaoPayAmountModel;
import bll.CoSocialInsurance.CoShebao_QueryBillsBll;

public class CoShebao_AddPayInfoController {
	private CoShebao_QueryBillsBll bll = new CoShebao_QueryBillsBll();

	private CoShebaoPayAmountModel m = (CoShebaoPayAmountModel) Executions
			.getCurrent().getArg().get("m");
	private int count = 0; // 是否更新缴纳金额
	private CoShebaoPayAmountModel md;
	String d;

	public CoShebao_AddPayInfoController() {
		d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		md = bll.getPayAmount(m);
		m.setQueryDate(new Date());
	}

	@Command
	public void init(@BindingParam("btn") Button btn,
			@BindingParam("bc") Doublebox bc, @BindingParam("ac") Doublebox ac,
			@BindingParam("lb") Label lb) {
		if (m.getIsaccount() != null && m.getIsaccount().equals("已到账")) {
			bc.setReadonly(true);
			ac.setReadonly(true);
			lb.setVisible(true);
			btn.setVisible(false);
		}
	}

	@Command
	public void isChange() {

		try {
			Integer.valueOf(m.getBodycount());
		} catch (Exception e) {
			Messagebox.show("缴交人数只能为数字!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}

		count++;
	}

	@Command
	public void save(@BindingParam("addPaywin") Window win) {
		if (count > 0) {
			// 保存缴纳金额
			if (md.getCount() > 0) { // 更新
				if (md.getCspa_id() != 0) {
					m.setCspa_id(md.getCspa_id());
				}
			} else { // 添加
				bll.addPayAmount(m);
				md = bll.getPayAmount(m);
			}
		}

		// 保存到账信息
		if (md.getCspa_id() != 0) {

			if (m.getQueryDate() != null) {
				m.setCspa_id(md.getCspa_id());
				int row = bll.addQueryBills(m);
				if (row > 0) {
					Messagebox.show("添加成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();

				} else {
					Messagebox.show("添加失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请填写查询日期", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请填写缴交信息", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public CoShebaoPayAmountModel getM() {
		return m;
	}

	public void setM(CoShebaoPayAmountModel m) {
		this.m = m;
	}

}
