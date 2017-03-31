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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmActy_SupplierSelectBll;
import bll.EmBenefit.EmBenefit_comitEmListBll;

import Model.EmActySupProductInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActySupplierInfoModel;
import Model.EmWelfareModel;
import Util.UserInfo;

public class EmActy_GiftAddController {
	private List<EmWelfareModel> list = (List<EmWelfareModel>) Executions
			.getCurrent().getArg().get("list");
	private String gifttype = (String) Executions.getCurrent().getArg()
			.get("gifttype");
	private String con = (String) Executions.getCurrent().getArg().get("con");
	private EmActy_GiftInfoSelectBll bll = new EmActy_GiftInfoSelectBll();
	private EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
	private Double pri = 0.00;
	private Integer num = 0;
	private String idstr = "";
	// 统计字符串
	private String tjstr = "";
	private List<EmWelfareModel> countlist = new ArrayList<EmWelfareModel>();
	private EmBenefit_comitEmListBll countbll=new EmBenefit_comitEmListBll();
	public EmActy_GiftAddController() {
		for (int i = 0; i < list.size(); i++) {
			num = num + list.get(i).getEmwf_amount();
			if (i == 0) {
				idstr = list.get(0).getEmwf_id() + "";
			} else {
				idstr = idstr + "," + list.get(i).getEmwf_id();
			}
		}
		if (idstr != null && !idstr.equals("")) {
			//countlist = bll.getWfCount(" and emwf_id in(" + idstr + ")");
			countlist = countbll.getEmWelfareCount(" and emwf_id in(" + idstr + ") ");
		}
		getWfCount();
	}

	// 获取统计数据
	private void getWfCount() {
		tjstr = "";
		for (EmWelfareModel countm : countlist) {
			if (countm.getEmwf_producefo() != null
					&& !countm.getEmwf_producefo().equals(""))
				tjstr = tjstr + countm.getEmwf_producefo() + "　　";
		}
	}
	
	//打开使用库存页面
	@Command
	public void usehouse()
	{
		
	}

	// 供应商下来列表事件（根据选择的供应商查询报价报价信息）
	@Command
	@NotifyChange("prolist")
	public void updatelist(@BindingParam("val") String val) {
		String sql = " and prod_supid in(select supp_id from EmActySupplierInfo where supp_name='"
				+ val + "')";
		// prolist=probll.getEmActySupProductInfo(sql);
	}

	// 采购总价的计算事件
	@Command
	@NotifyChange("pri")
	public void getPrice(@BindingParam("innum") Integer innum,
			@BindingParam("nowprice") Double nowprice) {
		if (innum != null && nowprice != null && innum > 0) {
			pri = innum * nowprice;
		} else {
			pri = 0.00;
		}
	}

	// 福利申请新增提交事件
	@Command
	public void summit(@BindingParam("addwin") Window win,
			@BindingParam("ownmonth") Date ownmonth) {
		if (ownmonth == null || ownmonth.equals("")) {
			Messagebox.show("请选择所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Integer lcID = 67;
			String sortid = changedate(), idstr = "", sortname = "", sortstr = "";
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					idstr = list.get(0).getEmwf_id() + "";
					sortname = list.get(0).getEmbf_name();
				} else {
					idstr = idstr + "," + list.get(i).getEmwf_id() + "";
				}
			}
			sortstr = sortname + sortid;
			// m.setGift_tarpid(tapr_id);
			m.setGift_sortid(sortstr);
			m.setGift_addname(UserInfo.getUsername());
			m.setGift_totalnum(num);
			// m.setGift_price(promodel.getProd_price());
			// m.setGift_nowprice(promodel.getProd_discountprice());
			m.setGift_type(gifttype);
			m.setGift_projectname(con);
			if (gifttype.equals("礼品")) {
				lcID = 100;
			}
			String mon = "";
			if (pri != null && !pri.equals("")) {
				BigDecimal p = new BigDecimal(pri);
				m.setGift_totalprice(p);
			}
			// m.setGift_inaddress(supmodel.getSupp_address());
			if (ownmonth != null && !ownmonth.equals("")) {
				mon = changedate(ownmonth);
				if (mon != null && !mon.equals("") && mon != "") {
					m.setOwnmonth(Integer.parseInt(mon));
				}
			}
			EmActy_GiftInfoOperateBll bl = new EmActy_GiftInfoOperateBll();
			String str[] = bl.EmActy_GiftInfoAdd(m, "1", con, lcID);
			if (str[0] == "1") {
				if (idstr != null && !idstr.equals("") && idstr != "") {
					Integer tapid = bll.getTarpId(" and gift_id=" + str[3]);
					bl.updateEmWelfare(sortstr, idstr, 4);
					bl.updateEmWelfareTarpid(idstr, tapid);
					// 更新所属月份
					bl.updateEmWelfare(idstr, Integer.parseInt(mon));
				}
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}

	public Double getPri() {
		return pri;
	}

	public void setPri(Double pri) {
		this.pri = pri;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public EmActySuppilerGiftInfoModel getM() {
		return m;
	}

	public void setM(EmActySuppilerGiftInfoModel m) {
		this.m = m;
	}

	public String getTjstr() {
		return tjstr;
	}

	public void setTjstr(String tjstr) {
		this.tjstr = tjstr;
	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}

	// 根据时间生成批量号
	private String changedate() {
		String dateString = "";
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		dateString = formatter.format(now);
		return dateString;
	}

}
