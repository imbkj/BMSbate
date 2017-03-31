package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Model.TaskProcessViewModel;
import bll.Archives.EmArchive_SelectBll;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmPay.EmPay_OperateBll;

public class EmActy_prepayController {
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
	private BigDecimal allpris = new BigDecimal(0.0);
	private BigDecimal prepay = new BigDecimal(0.0);// 预支付金额
	private BigDecimal evpay = new BigDecimal(0.0);// 每一份礼品需要支付金额
	private BigDecimal evepay = new BigDecimal(0.0);// 每一份礼品单价
	private Integer zn = 0;// 总份数
	private EmBenefit_comitListBll bllss = new EmBenefit_comitListBll();
	private List<EmWelfareModel> listss = new ListModelList<>();
	private int housenum = 0, nowhousenum = 0, ware_id = 0, hsnt_beforenum = 0;

	private EmBenefit_comitListBll wbll = new EmBenefit_comitListBll();
	private List<EmActyWarehouseModel> wlist = new ArrayList<EmActyWarehouseModel>();
	private String wase_name = "";

	public EmActy_prepayController() {
		if (list.size() > 0) {
			model = list.get(0);
			sortid = model.getGift_sortid();
			listss = bllss.getLists(" and emwf_state=5 and emwf_sortid='"
					+ sortid + "'");
			if (listss.size() > 0) {
				evepay = listss.get(0).getProd_discountprice();
				wase_name = listss.get(0).getProductName();
				for (int i = 0; i < listss.size(); i++) {
					if (listss.get(i).getEmwf_amount() != null
							&& listss.get(i).getProd_discountprice() != null) {
						BigDecimal num = new BigDecimal(listss.get(i)
								.getEmwf_amount());
						allpri = allpri.add(listss.get(i)
								.getEmwf_price());
						zn = zn + listss.get(i).getEmwf_amount();
					}
				}
			}
		}
		allpris = allpri;
		wlist = wbll.getEmActyWarehouse(" and wase_name='" + wase_name + "'");
		if (wlist.size() > 0) {
			EmActyWarehouseModel waremodel = wlist.get(0);
			ware_id = waremodel.getWase_id();
			hsnt_beforenum = waremodel.getWase_nownum();
			nowhousenum = waremodel.getWase_nownum();
		}

	}

	// 礼品预付款生成支付通知
	@Command
	public void addpayinfo(@BindingParam("win") Window win) {
		Integer pare = prepay.compareTo(allpri);
		if (pare == 1) {
			Messagebox
					.show("预付款不能大于总金额", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (housenum > nowhousenum) {
			Messagebox.show("使用库存不能大于现有库存", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			if (listss.size() > 0) {
				String sql = "";
				sql = sql + ",gift_userhousenum=" + housenum;
				EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
				EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
				m.setGift_id(id);
				m.setGift_tarpid(tapr_id);
				m.setGift_remark("生成支付通知");
				String[] str = new String[5];
				if (zn == housenum && model.getGift_type().equals("礼品"))// 如果总数等于使用库存数则跳过采购礼品步骤
				{
					str = obll.updateEmActy_GiftInfos(m, sql + ",gift_state=4",
							"1");
				} else {
					str = obll.updateEmActy_GiftInfo(m, sql, "2");
				}
				if (str[0] == "1") {
					if (housenum > 0)// 使用库存大于0时就添加使用库存的记录
					{
						Integer hsnt_num = zn - housenum;
						int changenum = 0 - housenum;
						String remark = "采购" + hsnt_num + "库存" + changenum;
						EmArchive_SelectBll abll = new EmArchive_SelectBll();
						List<TaskProcessViewModel> tlist = abll
								.getLastId(tapr_id.toString());
						String tali_name = "";
						if (tlist.size() > 0) {
							tali_name = tlist.get(0).getTali_name();
						}
						obll.insertHouse(ware_id, hsnt_num, zn, changenum,
								"采购", remark, "", tali_name);
					}
					BigDecimal pr = new BigDecimal(0);
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
					// 计算每一份需要预支付多少金额
					if (housenum > 0) {
						zn = zn - housenum;
					}
					BigDecimal znn = new BigDecimal(zn);// 份数类型转换
					Integer fg = znn.compareTo(BigDecimal.ZERO);
					if (fg == 1) {
						evpay = prepay.divide(znn, 2, BigDecimal.ROUND_HALF_UP);// 计算每一份礼品预支付多少钱
					}
					int knum = housenum, firstnum = 0;
					for (int ij = 0; ij < listss.size(); ij++) {
						EmWelfareModel wm = listss.get(ij);
						if (knum > 0) {
							knum = knum - wm.getEmwf_amount();
						} else {
							firstnum = firstnum + 1;
						}
						if (wm.getProd_discountprice() != null
								&& wm.getEmwf_amount() != null) {
							BigDecimal monum = new BigDecimal(
									wm.getEmwf_amount());
							if (knum <= 0)// 表示库存的数据已经使用
							{
								if (knum < 0)// 第一种情况表示这次循环的员工的份数部分是使用库存，部分需要购买
								{
									BigDecimal ddd = new BigDecimal(-knum);
									pr = evpay.multiply(ddd);// 计算每个人需要预支付多少钱
									knum = 0;// 把需要使用库存数清空
								} else if (knum == 0 && firstnum > 0) {
									pr = evpay.multiply(monum);// 计算每个人需要预支付多少钱
								}
							}
							int sttaeid = 10;
							if (zn == 0 && model.getGift_type().equals("礼品"))// 如果总数等于使用库存数则跳过采购礼品步骤
							{
								sttaeid = 6;
							}
							String sqlp = "";
							sqlp = ",emwf_truecharge='" + pr + "',emwf_state="
									+ sttaeid + ",emwf_paynum='" + paynum + "'";
							// 更新福利信息表的费用
							obll.updateEmWelfareInfo(sqlp, wm.getEmwf_id());
							// 添加支付明细
							try {
								BigDecimal siz = BigDecimal.ZERO;
								Integer nk = prepay.compareTo(siz);
								Integer pnk = pr.compareTo(siz);
								Integer pnevpay = evpay.compareTo(siz);
								if (nk == 1 && pnk == 1 && pnevpay == 1) {
									add_message = add_message
											+ payBll.add_pay(wm.getGid()
													.toString(), wm.getCid()
													.toString(), paynum,
													ownmonth, type, pr
															.toString(), wm
															.getEmwf_id()
															.toString());
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
				// 把预付款金额更新到礼品表
				obll.updateGiftInfo(",gift_prepay=" + prepay, m.getGift_id());
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				win.detach();
				Messagebox.show("发放失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 填写库存数
	@Command
	@NotifyChange("allpri")
	public void housechange() {
		if (housenum >= 0) {
			if (housenum <= nowhousenum) {
				BigDecimal num = new BigDecimal(housenum);
				allpri = allpris.subtract(evepay.multiply(num));
			} else {
				housenum = 0;
				allpri = allpris;
				Messagebox.show("使用库存不能大于现有库存", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 填写预付金额时候限制不能大于总金额
	@Command
	public void paychange() {
		Integer pare = prepay.compareTo(allpri);
		if (pare == 1) {
			prepay = allpri;
			Messagebox
					.show("预付款不能大于总金额", "提示", Messagebox.OK, Messagebox.ERROR);
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

	public BigDecimal getPrepay() {
		return prepay;
	}

	public void setPrepay(BigDecimal prepay) {
		this.prepay = prepay;
	}

	public int getHousenum() {
		return housenum;
	}

	public void setHousenum(int housenum) {
		this.housenum = housenum;
	}

	public int getNowhousenum() {
		return nowhousenum;
	}

	public void setNowhousenum(int nowhousenum) {
		this.nowhousenum = nowhousenum;
	}

	public EmActySuppilerGiftInfoModel getModel() {
		return model;
	}

	public void setModel(EmActySuppilerGiftInfoModel model) {
		this.model = model;
	}

}
