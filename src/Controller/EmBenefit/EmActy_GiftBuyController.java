package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;

import Model.EmActyContactContentInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Util.UserInfo;

public class EmActy_GiftBuyController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private String username = UserInfo.getUsername();
	private EmActySuppilerGiftInfoModel model = new EmActySuppilerGiftInfoModel();
	private EmActy_GiftInfoSelectBll giftbll = new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> giftlist = giftbll
			.getEmActyGiftInfo(" and gift_id=" + id);
	private Integer gift_userhousenum=0;
	private String sortid;
	private List<EmWelfareModel> list = new ListModelList<>();
	private String idstr = "";
	// 获取统计数据
	private List<EmWelfareModel> countlist = new ArrayList<EmWelfareModel>();

	public EmActy_GiftBuyController() {
		if (giftlist.size() > 0) {
			EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
			model = giftlist.get(0);
			sortid = model.getGift_sortid();
			//buynum = model.getGift_nownum();
			gift_userhousenum=model.getGift_userhousenum();
			list = bll
					.getLists(" and (emwf_state=5 or emwf_state=10) and emwf_sortid='"
							+ sortid + "'");
			for (int i = 0; i < list.size(); i++) {
				//buynum = buynum + list.get(i).getEmwf_amount();
				if (i == 0) {
					idstr = list.get(0).getEmwf_id() + "";
				} else {
					idstr = idstr + "," + list.get(i).getEmwf_id();
				}
			}
			if (idstr != null && !idstr.equals("")) {
				countlist = bll.getWfCount(" and emwf_id in(" + idstr + ")");
				if(countlist.size()>0)
				{
					Integer mu=countlist.get(0).getNum();
					int kl=mu-gift_userhousenum;
					countlist.get(0).setNum(kl);
				}
			}
		}
	}

	// 更新采购申请信息并更新任务单
	@Command
	public void EditGift(@BindingParam("win") Window win,
			@BindingParam("dateval") Date dateval,
			@BindingParam("remark") String remark, @BindingParam("gd") Grid gd) {
		List<EmWelfareModel> lists = new ListModelList<>();
		EmBenefit_comitListBll sbll = new EmBenefit_comitListBll();
		String strsql = "", idstr = "";
		Integer stateId = null;
		EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
		stateId = 6;
		String sqls = "";
		if (model.getGift_paytype().equals("预付款")) {
			sqls = " and emwf_state=10 ";
		}
		lists = sbll.getLists(" and emwf_paytype is not null and emwf_sortid='"
				+ sortid + "'" + sqls);
		strsql = ",gift_buytime='" + datechange(dateval) + "',gift_buyname='"
				+ UserInfo.getUsername() + "',gift_state=4,gift_remark='"
				+ remark + "'";
		EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
		m.setGift_id(model.getGift_id());
		m.setGift_remark("采购礼品");
		m.setGift_tarpid(model.getGift_tarpid());
		String[] str = obll.updateEmActy_GiftInfo(m, strsql, "2");
		for (int j = 0; j < lists.size(); j++) {
			if (j == 0) {
				idstr = lists.get(j).getEmwf_id() + "";
			} else {
				idstr = idstr + "," + lists.get(j).getEmwf_id();
			}
		}
		if (str[0] == "1") {
			String content = "";
			if (content != null && !content.equals("") && content != "") {
				EmActyContactContentInfoModel cml = new EmActyContactContentInfoModel();
				cml.setCact_addname(username);
				cml.setCact_content(content);
				cml.setCact_gift_id(id);
				cml.setCact_remark(remark);
				obll.EmActyContactContentInfo(cml);
			}
			// 更新emwfare表的状态
			if (!idstr.equals("") && stateId != null) {
				obll.updateEmWelfare(sortid, idstr, stateId);
			}

			// 更新库存信息
			if (countlist.size() > 0) {
				for (int i = 0; i < countlist.size(); i++) {
					EmActyWarehouseModel wamodel = new EmActyWarehouseModel();
					String wasename = "";
					Integer buynums = 0;
					if (gd.getCell(i, 1) != null) {
						Label la = (Label) gd.getCell(i, 1).getChildren()
								.get(0);
						wasename = la.getValue().trim();
						wamodel.setWase_name(wasename);
						Label sp = (Label) gd.getCell(i, 5).getChildren()
								.get(0);
						if (sp.getValue() != null && !sp.getValue().equals("")) {
							buynums = Integer.parseInt(sp.getValue());
						}
						if (buynums == null) {
							buynums = 0;
						}
						wamodel.setWase_totalnum(buynums);
						wamodel.setWase_addname(username);
						//Integer hsry_wase_id = obll.EmActyWarehouse(wamodel);
						/*if (hsry_wase_id > 0) {
							for (int h = 0; h < list.size(); h++) {
								if (list.get(h).getEmwf_gift_id() == countlist
										.get(i).getProd_id()
										|| list.get(h)
												.getEmwf_gift_id()
												.equals(countlist.get(i)
														.getProd_id())) {
									obll.addEmActyWarehouseHistory(list.get(h)
											.getEmwf_id(), list.get(h)
											.getEmwf_amount(), list.get(h)
											.getProd_discountprice(),
											hsry_wase_id);
								}
							}
						}*/
					}
				}
			}
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//检测购买li

	private String datechange(Date d) {
		String date = "";
		if (d != null && !d.equals("")) {
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = time.format(d);
		}
		return date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public EmActySuppilerGiftInfoModel getModel() {
		return model;
	}

	public void setModel(EmActySuppilerGiftInfoModel model) {
		this.model = model;
	}

//	public Integer getBuynum() {
//		return buynum;
//	}
//
//	public void setBuynum(Integer buynum) {
//		this.buynum = buynum;
//	}

	public List<EmWelfareModel> getCountlist() {
		return countlist;
	}

	public void setCountlist(List<EmWelfareModel> countlist) {
		this.countlist = countlist;
	}

	public Integer getGift_userhousenum() {
		return gift_userhousenum;
	}

	public void setGift_userhousenum(Integer gift_userhousenum) {
		this.gift_userhousenum = gift_userhousenum;
	}
	
}
