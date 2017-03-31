package Controller.EmBenefit;

import java.math.BigDecimal;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_SupplierOperateBll;

import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;
import Util.UserInfo;

public class EmActy_SupProductAddController {
	private EmActySupplierInfoModel model = (EmActySupplierInfoModel) Executions
			.getCurrent().getArg().get("model");

	// 产品新增
	@Command
	public void proadd(@BindingParam("proname") String proname,
			@BindingParam("price") Integer price,
			@BindingParam("disprice") Integer disprice,
			@BindingParam("discount") String discount,
			@BindingParam("pronum") Integer pronum,
			@BindingParam("win") Window win,
			@BindingParam("remark") String remark) {
		EmActySupProductInfoModel promodel = new EmActySupProductInfoModel();
		
		if (proname != null && !proname.equals("")) {
			promodel.setProd_name(proname);
		}else {
			Messagebox.show("请输入产品名称!", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		if (price != null && !price.equals("")) {
			BigDecimal sprice = new BigDecimal(price);
			promodel.setProd_price(sprice);
		} else {
			Messagebox.show("请输入产品原价!", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (disprice != null && !disprice.equals("")) {
			BigDecimal nowprice = new BigDecimal(disprice);
			promodel.setProd_discountprice(nowprice);
		} else {
			Messagebox.show("请输入产品折扣价!", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (discount != null && !discount.equals("") && discount != "") {
			promodel.setProd_discount(discount);
		}
		
		promodel.setProd_totalnum(0);
		
		promodel.setProd_supid(model.getSupp_id());
		promodel.setProd_addname(UserInfo.getUsername());
		EmActy_SupplierOperateBll obll = new EmActy_SupplierOperateBll();
		promodel.setProd_remark(remark);
		int k = obll.EmActySupProductInfoAdd(promodel);
		if (k > 0) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 联系人新增
	@Command
	public void linkadd(@BindingParam("name") String name,
			@BindingParam("phone") String phone,
			@BindingParam("mobile") String mobile,
			@BindingParam("email") String email,
			@BindingParam("address") String address,
			@BindingParam("win") Window win,
			@BindingParam("remark") String remark) {
		EmActySupContactManInfoModel linm = new EmActySupContactManInfoModel();
		if (name != null && !name.equals("") && name != "") {
			EmActy_SupplierOperateBll obll = new EmActy_SupplierOperateBll();
			// 供应商联系人信息
			linm.setCoct_supid(model.getSupp_id());
			linm.setCoct_name(name);
			linm.setCoct_mobile(mobile);
			linm.setCoct_phone(phone);
			linm.setCoct_address(address);
			linm.setCoct_addname(UserInfo.getUsername());
			linm.setCoct_Email(email);
			linm.setCoct_remark(remark);
			int k = obll.EmActySupContactManInfoAdd(linm);
			if (k > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			}
		} else {
			Messagebox.show("请输入联系人姓名", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

}
