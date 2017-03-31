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
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmPay.EmPay_OperateBll;

public class EmActy_GiftPayInfosController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private EmActySuppilerGiftInfoModel model = new EmActySuppilerGiftInfoModel();
	private EmActy_GiftInfoSelectBll bll = new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> list = bll
			.getEmActyGiftInfo(" and gift_id=" + id);
	private String sortid;
	private BigDecimal allpri = new BigDecimal(0.0);
	private BigDecimal haspay = new BigDecimal(0.0);
	private BigDecimal nowpay = new BigDecimal(0.0);
	private BigDecimal evpay = new BigDecimal(0.0);
	private BigDecimal evhaspay = new BigDecimal(0.0);

	EmBenefit_comitListBll bllss = new EmBenefit_comitListBll();
	List<EmWelfareModel> listss = new ListModelList<>();
	private Integer num = 0,  totalnum = 0, usernum = 0,
			gift_usernum = 0;

	public EmActy_GiftPayInfosController() {

		if (list.size() > 0) {
			model = list.get(0);
			haspay = model.getGift_prepay();
			sortid = model.getGift_sortid();
			totalnum = model.getGift_totalnum();
			usernum = model.getGift_userhousenum();
			gift_usernum = usernum;
			listss = bllss
					.getLists(" and emwf_state in(6,7,8,9,10,12,13) and emwf_sortid='"
							+ sortid + "'");
			if (listss.size() > 0) {
				for (int i = 0; i < listss.size(); i++) {
					if (listss.get(i).getEmwf_amount() != null
							&& listss.get(i).getProd_discountprice() != null) {
						int ni = listss.get(i).getEmwf_amount();
						int numn = ni;
						for (int m = 0; m < numn; m++) {
							if (usernum > 0) {
								ni = ni - 1;
								usernum = usernum - 1;
							} else {
								break;
							}
						}
						BigDecimal num = new BigDecimal(ni);
						allpri = allpri.add(listss.get(i)
								.getProd_discountprice().multiply(num));
					}
				}
			}
		}
		if (haspay == null) {
			haspay = BigDecimal.ZERO;
		}
		if (allpri == null) {
			allpri = BigDecimal.ZERO;
		}
		nowpay = allpri.subtract(haspay);
		getNumSize();// 获取总份数num，表示总份数
		//if (num == 0) {
		//	num = 1;
		//}
		BigDecimal b2 = new BigDecimal(num);
		evpay = nowpay.divide(b2, 2, BigDecimal.ROUND_HALF_UP);// evpay表示每份需要的金额
		evhaspay = haspay.divide(b2, 2, BigDecimal.ROUND_HALF_UP);// evhaspay表示每份已经付了的金额

	}

	// 生成支付通知
	@Command
	public void addpayinfo(@BindingParam("win") Window win) {
		if (listss.size() > 0) {
			EmBenefit_comitListBll mlbll = new EmBenefit_comitListBll();
			String sql = "";
			sql = sql + ",gift_state=7";
			EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
			EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
			m.setGift_id(id);
			m.setGift_tarpid(tapr_id);
			m.setGift_remark("生成支付通知");
			String[] str = obll.updateEmActy_GiftInfo(m, sql, "2");
			if (str[0] == "1") {
				Integer znum = model.getGift_userhousenum(), firstnum = 0;
				int znums = model.getGift_totalnum()
						- model.getGift_userhousenum();
				BigDecimal znn = new BigDecimal(znums);// 份数类型转换
				Integer fg = znn.compareTo(BigDecimal.ZERO);
				if (fg == 1) {
					evpay = nowpay.divide(znn, 2, BigDecimal.ROUND_HALF_UP);// 计算每一份礼品预支付多少钱
				}
				if (listss.size() > 0) {
					SimpleDateFormat form = new SimpleDateFormat("yyyyMM");
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyyMMddhhmmss");
					Date date = new Date();
					String nowtime = formatter.format(date);
					String paynum = "FL" + nowtime; // 支付单号，
					String ownmonth = form.format(date);
					String type = "福利费";
					int add_message = 0;
					EmPay_OperateBll payBll = new EmPay_OperateBll();
					for (int ij = 0; ij < listss.size(); ij++) {
						EmWelfareModel wm = listss.get(ij);
						if (znum > 0) {
							znum = znum - wm.getEmwf_amount();
						}
						else
						{
							firstnum=firstnum+1;
						}
						if (znum <= 0) {
							if (wm.getProd_discountprice() != null
									&& wm.getEmwf_amount() != null) {
								BigDecimal monum = new BigDecimal(
										wm.getEmwf_amount());
								BigDecimal pr = BigDecimal.ZERO;// pr表示每个员工需要支付的总金额

								BigDecimal zpr = BigDecimal.ZERO;// pr表示每个员工需要支付的总金额

								if (znum < 0)// 第一种情况表示这次循环的员工的份数部分是使用库存，部分需要购买
								{
									BigDecimal ddd = new BigDecimal(-znum);
									pr = evpay.multiply(ddd);// 计算每个人需要预支付多少钱
									znum = 0;// 把需要使用库存数清空
								} else if (znum == 0 && firstnum > 0) {
									pr = evpay.multiply(monum);// 计算每个人需要预支付多少钱
								}
								if (wm.getEmwf_truecharge() != null) {
									zpr = pr.add(wm.getEmwf_truecharge());
								}
								else
								{
									zpr = pr;
								}
								String sqlp = ",emwf_truecharge='" + zpr
										+ "',emwf_state=10,emwf_paynum='"
										+ paynum + "'";
								// 更新福利信息表的费用
								obll.updateEmWelfareInfo(sqlp, wm.getEmwf_id());
								// 添加支付明细
								try {
									BigDecimal siz = BigDecimal.ZERO;
									Integer nk = nowpay.compareTo(siz);
									Integer pnk = pr.compareTo(siz);
									Integer pnevpay = evpay.compareTo(siz);
									if (nk == 1 && pnk == 1 && pnevpay == 1) {
										add_message = add_message
												+ payBll.add_pay(wm.getGid()
														.toString(), wm
														.getCid().toString(),
														paynum, ownmonth, type,
														pr.toString(), wm
																.getEmwf_id()
																.toString());
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					if (add_message > 0) {
						try {
							String[] message = payBll.up_pay(paynum, ownmonth,
									type);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("没有数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 获取总总份数
	private void getNumSize() {
		for (EmWelfareModel m : listss) {
			num = num + m.getEmwf_amount();
		}
	}

	public BigDecimal getAllpri() {
		return allpri;
	}

	public void setAllpri(BigDecimal allpri) {
		this.allpri = allpri;
	}

	public List<EmWelfareModel> getListss() {
		return listss;
	}

	public void setListss(List<EmWelfareModel> listss) {
		this.listss = listss;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getHaspay() {
		return haspay;
	}

	public void setHaspay(BigDecimal haspay) {
		this.haspay = haspay;
	}

	public BigDecimal getNowpay() {
		return nowpay;
	}

	public void setNowpay(BigDecimal nowpay) {
		this.nowpay = nowpay;
	}

	public Integer getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}

	public Integer getUsernum() {
		return usernum;
	}

	public void setUsernum(Integer usernum) {
		this.usernum = usernum;
	}

	public Integer getGift_usernum() {
		return gift_usernum;
	}

	public void setGift_usernum(Integer gift_usernum) {
		this.gift_usernum = gift_usernum;
	}

}
