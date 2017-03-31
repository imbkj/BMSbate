package Controller.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoHousingFundPayAmountModel;
import bll.CoHousingFund.CoHousingFund_QueryBillsBll;

public class CoHousingFund_PaymentInfoController {
	private CoHousingFund_QueryBillsBll bll = new CoHousingFund_QueryBillsBll();
	private CoHousingFundPayAmountModel m = (CoHousingFundPayAmountModel) Executions
			.getCurrent().getArg().get("m");
	private int count = 0; // 是否更新缴纳金额
	private CoHousingFundPayAmountModel md;
	private String month;

	public CoHousingFund_PaymentInfoController() {
		month = new SimpleDateFormat("yyyyMM").format(new Date());
		m.setCofp_queryDate(new Date());
		md = bll.getPayAmount(m);

	}

	@Command
	public void init(@BindingParam("btn") Button btn,
			@BindingParam("lb") Label lb) {
		if (m.getCofp_isaccount().equals("已到帐")) {
			btn.setVisible(false);
			lb.setVisible(true);
		}
	}

	@Command
	public void ischange() {
		count++;
	}

	@Command
	public void save(@BindingParam("paymentWin") Window win) {
		// 保存补缴金额
		if (count > 0) { // 先判断有没有改动
			if (md.getCount() > 0) { // 再判断本月的缴存金额是否存在 ，如果存在就执行更新，不存在就执行新增
				m.setCfpa_id(md.getCfpa_id());
				bll.updatePaymentAmount(m);
			} else {

				bll.addPaymentAmount(m);
				md = bll.getPayAmount(m);
			}
		}

		// 保存到账信息
		if (md.getCfpa_id() != 0) {
			if (m.getPayment() != null) {
				if (m.getCofp_queryDate() != null) {
					m.setCfpa_id(md.getCfpa_id());
					int row = bll.addPayment(m);
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
				Messagebox.show("请填写补缴金额", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请填写缴交信息", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public CoHousingFundPayAmountModel getM() {
		return m;
	}

	public void setM(CoHousingFundPayAmountModel m) {
		this.m = m;
	}

}
