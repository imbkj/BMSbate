package Controller.EmBenefit;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmBenefit.EmActy_SupplierOperateBll;
import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;
import Util.UserInfo;

public class EmActy_SupplierInfoAddController {

	private List<String> itemList = new ListModelList<>();

	public EmActy_SupplierInfoAddController() {
		itemList.add("活动");
		itemList.add("礼品");
	}

	@Command
	public void add(@BindingParam("supname") String supname,
			@BindingParam("website") String website,
			@BindingParam("address") String address,
			@BindingParam("linkname") String linkname,
			@BindingParam("mobile") String mobile,
			@BindingParam("phone") String phone,
			@BindingParam("linkaddress") String linkaddress,
			@BindingParam("proname") String proname,
			@BindingParam("price") String price,
			@BindingParam("disprice") String disprice,
			@BindingParam("discount") String discount,
			@BindingParam("pronum") String pronum,
			@BindingParam("email") String email,
			@BindingParam("remark") String remark,
			@BindingParam("items") LinkedHashSet<String> list) {
		
		
		
		if (supname != null && !supname.equals("") && supname != "") {
			String addname = UserInfo.getUsername();
			EmActy_SupplierOperateBll obll = new EmActy_SupplierOperateBll();
			EmActySupplierInfoModel model = new EmActySupplierInfoModel();
			EmActySupProductInfoModel prom = new EmActySupProductInfoModel();
			EmActySupContactManInfoModel linm = new EmActySupContactManInfoModel();
			
			// 供应商基本信息
			model.setSupp_name(supname);
			model.setSupp_website(website);
			model.setSupp_address(address);
			model.setSupp_remark(remark);
			model.setSupp_addname(addname);
			
			if (list!=null && !list.toString().equals("[]")) {
				model.setSupp_type(list.toString().replace("[", "").replace("]", "").replace(" ", ""));
			}else {
				Messagebox.show("请选择提供服务类型.", "提示", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			int k = obll.EmActy_SupplierAdd(model);
			if (k > 0) {
				if (linkname != null && !linkname.equals("") && linkname != "") {
					// 供应商联系人信息
					linm.setCoct_supid(k);
					linm.setCoct_name(linkname);
					linm.setCoct_mobile(mobile);
					linm.setCoct_phone(phone);
					linm.setCoct_address(linkaddress);
					linm.setCoct_addname(addname);
					linm.setCoct_Email(email);
					int k2 = obll.EmActySupContactManInfoAdd(linm);
				}

				// 产品系信息
				if (proname != null && !proname.equals("") && proname != "") {
					prom.setProd_supid(k);
					prom.setProd_name(proname);
					if (price != null && !price.equals("") && price != "") {
						BigDecimal sprice = new BigDecimal(price);
						prom.setProd_price(sprice);
					}
					if (disprice != null && !disprice.equals("")
							&& disprice != "") {
						BigDecimal nowprice = new BigDecimal(disprice);
						prom.setProd_discountprice(nowprice);
					}
					if (discount != null && !discount.equals("")
							&& discount != "") {
						prom.setProd_discount(discount);
					}
					if (pronum != null && !pronum.equals("") && pronum != "") {
						prom.setProd_totalnum(Integer.parseInt(pronum));
					}
					prom.setProd_addname(addname);
					int k3 = obll.EmActySupProductInfoAdd(prom);
				}
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				Executions
						.sendRedirect("/EmBenefit/EmActy_SupplierInfoAdd.zul");
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请输入供应商名称", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<String> getItemList() {
		return itemList;
	}

	public void setItemList(List<String> itemList) {
		this.itemList = itemList;
	}

}
