package Controller.EmBenefit;

import java.math.BigDecimal;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActyProduceModel;
import Model.EmActyProducetypeModel;
import Model.EmActySupplierInfoModel;
import Util.UserInfo;
import bll.EmBenefit.EmActy_NewOperateBll;

public class EmActy_ProductAddController {
	private EmActySupplierInfoModel model = (EmActySupplierInfoModel) Executions
			.getCurrent().getArg().get("model");
	private EmActyProduceModel m = new EmActyProduceModel();
	private String suboption = "";
	private String suboptions = "";
	private boolean vis = false;

	@Command
	@NotifyChange("vis")
	public void selectItems() {
		if (suboption != null && suboption.equals("有")) {
			vis = true;
		} else {
			vis = false;
		}
	}

	@Command
	public void submit(@BindingParam("win") Window win) {
		if (m.getProd_name() == null || m.getProd_name().equals("")) {
			Messagebox.show("请输入产品名称", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (suboption != null && suboption.equals("有")) {
			if (suboptions == null || suboptions.equals("")) {
				Messagebox.show("请输入分项", "提示", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		if (m.getProd_pricetype() == null || m.getProd_pricetype().equals("")) {
			Messagebox.show("请选择计算方式", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (m.getProd_discount() == null) {
			Messagebox.show("请输入折扣/价格", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (m.getProd_pricetype().equals("折扣")) {
			if (m.getProd_discount() != null
					&& m.getProd_discount().compareTo(BigDecimal.ZERO) < 0
					|| m.getProd_discount().compareTo(new BigDecimal(1)) > 0) {
				m.setProd_discount(null);
				Messagebox.show("折扣范围只能在0~1之间!", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		m.setProd_supp_id(model.getSupp_id());
		m.setProd_addname(UserInfo.getUsername());
		m.setProd_unit("元");
		if (m.getProd_pricetype().equals("金额")) {
			m.setProd_unit("张");
		}
		EmActy_NewOperateBll bll = new EmActy_NewOperateBll();
		int k = bll.AddProduce(m);
		if (k > 0) {
			String[] arr = suboptions.trim().split("，");
			EmActyProducetypeModel tm = new EmActyProducetypeModel();
			tm.setPrty_addname(UserInfo.getUsername());
			tm.setPrty_discount(m.getProd_discount());
			tm.setPrty_prod_id(k);
			tm.setPrty_unit(m.getProd_unit());
			for (String prty_name : arr) {
				tm.setPrty_name(prty_name);
				bll.AddProduceType(tm);
			}
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmActyProduceModel getM() {
		return m;
	}

	public void setM(EmActyProduceModel m) {
		this.m = m;
	}

	public String getSuboption() {
		return suboption;
	}

	public void setSuboption(String suboption) {
		this.suboption = suboption;
	}

	public String getSuboptions() {
		return suboptions;
	}

	public void setSuboptions(String suboptions) {
		this.suboptions = suboptions;
	}

	public boolean isVis() {
		return vis;
	}

	public void setVis(boolean vis) {
		this.vis = vis;
	}

}
