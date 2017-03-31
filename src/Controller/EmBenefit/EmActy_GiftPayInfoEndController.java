package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_NewOperateFactory;
import bll.EmBenefit.EmActy_NewOperateFactoryImpl;
import bll.EmBenefit.EmActy_NewOperateService;
import bll.EmBenefit.EmActy_NewSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmPay.EmPay_OperateBll;

public class EmActy_GiftPayInfoEndController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private List<EmWelfareModel> list = new ListModelList<>();
	private EmActy_NewSelectBll bll = new EmActy_NewSelectBll();
	private EmActySuppilerGiftInfoModel model = new EmActySuppilerGiftInfoModel();
	private BigDecimal prePrice = new BigDecimal(0.0);
	private BigDecimal nowPayPrice = new BigDecimal(0.0);
	private BigDecimal totalPrice = new BigDecimal(0.0);
	private EmBenefit_comitListBll blls = new EmBenefit_comitListBll();

	public EmActy_GiftPayInfoEndController() {
		model = bll.getGiftInfo(id);
		if (model.getGift_prepay() != null) {
			prePrice = model.getGift_prepay();
		}
		if (model.getGift_totalprice() != null) {
			totalPrice = model.getGift_totalprice();
		}
		String sql = " and emwf_state in(6,7,8,9,10,12,13) and emwf_sortid='"
				+ model.getGift_sortid() + "'";
		list = blls.getLists(sql);
		initPrice();
	}

	private void initPrice() {
		nowPayPrice = totalPrice.subtract(prePrice);
	}

	// 生成支付通知
	@Command
	public void addpayinfo(@BindingParam("win") Window win) {
		if (list.size() > 0) {
			int totalNum = list.size();
			BigDecimal evpay = nowPayPrice.divide(new BigDecimal(totalNum), 2,
					BigDecimal.ROUND_HALF_UP);// 计算每个人预支付多少钱
			
			/************ 生成支付通知 ***************************/
			SimpleDateFormat form = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			Date date = new Date();
			String nowtime = formatter.format(date);
			String paynum = "FL" + nowtime; // 支付单号，
			String ownmonth = form.format(date);
			String type = "福利费";
			//if(model.getGift_projectname()!=null)
			//{
			//	type=model.getGift_projectname();
			//}
			int add_message = 0;
			EmPay_OperateBll payBll = new EmPay_OperateBll();
			EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
			String sql = ",emwf_truecharge='" + evpay
					+ "',emwf_state=10,emwf_paynum='" + paynum
					+ "',emwf_payprice=" + evpay;
			for (EmWelfareModel m : list) {
				int k = obll.updateEmWelfareInfo(sql, m.getEmwf_id());
				if (k > 0) {
					// 添加支付明细
					try {
						BigDecimal siz = BigDecimal.ZERO;
						add_message = add_message
								+ payBll.add_pay(m.getGid().toString(), m
										.getCid().toString(), paynum, ownmonth,
										type, evpay.toString(), m.getEmwf_id()
												.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (add_message > 0) {
				try {
					String[] message = payBll.up_pay(paynum, ownmonth, type);
					String gsql =",gift_state=7";
					obll.updateGiftInfo(gsql,id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/************** 生成支付通知End ***************/
			// 任务单处理
			EmActy_NewOperateFactory factory = new EmActy_NewOperateFactoryImpl();
			EmActy_NewOperateService service = factory
					.operateFactory("下一步");
			model.setGift_remark("生成支付通知");
			String[] str = service.edit(model, "");
			if (str[0] == "1") {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("发放失败", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else {
			Messagebox.show("没有数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public BigDecimal getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(BigDecimal prePrice) {
		this.prePrice = prePrice;
	}

	public BigDecimal getNowPayPrice() {
		return nowPayPrice;
	}

	public void setNowPayPrice(BigDecimal nowPayPrice) {
		this.nowPayPrice = nowPayPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}

}
