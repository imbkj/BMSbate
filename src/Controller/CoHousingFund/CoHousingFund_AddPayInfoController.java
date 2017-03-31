package Controller.CoHousingFund;

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

import Model.CoHousingFundPayAmountModel;
import bll.CoHousingFund.CoHousingFund_QueryBillsBll;

public class CoHousingFund_AddPayInfoController {
	private CoHousingFund_QueryBillsBll bll = new CoHousingFund_QueryBillsBll();
	private CoHousingFundPayAmountModel m = (CoHousingFundPayAmountModel) Executions
			.getCurrent().getArg().get("m");
	private int count = 0; // 是否更新缴纳金额
	private CoHousingFundPayAmountModel md;
	String d;

	public CoHousingFund_AddPayInfoController() {
		d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		md = bll.getPayAmount(m);
		m.setCqbc_queryDate(new Date());

	}

	@Command
	public void init(@BindingParam("btn") Button btn,
			@BindingParam("bc") Doublebox bc, @BindingParam("ac") Doublebox ac,
			@BindingParam("lb") Label lb) {
		if (m.getCqbc_isaccount().equals("已到帐")) {
			bc.setReadonly(true);
			ac.setReadonly(true);
			lb.setVisible(true);
			btn.setVisible(false);
		}
	}

	@Command
	public void isChange() {
		count++;
		System.out.println(count);
	}

	// 保存
	@Command
	public void save(@BindingParam("addPaywin") Window win) {

		if (count > 0) {
			// 保存缴纳金额
			if (md.getCount() > 0) { // 更新
				if (md.getCfpa_id() != 0) {
					m.setCfpa_id(md.getCfpa_id());
					bll.updatePayAmount(m);
				}
			} else { // 添加
				bll.addPayAmount(m);
				md = bll.getPayAmount(m);
			}
		}

		// 保存到账信息
		if (md.getCfpa_id() != 0) {
			if (m.getCqbc_queryDate() != null) {
				m.setCfpa_id(md.getCfpa_id());
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

	public CoHousingFundPayAmountModel getM() {
		return m;
	}

	public void setM(CoHousingFundPayAmountModel m) {
		this.m = m;
	}

}
