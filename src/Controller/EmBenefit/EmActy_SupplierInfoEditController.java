package Controller.EmBenefit;

import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import bll.EmBenefit.EmActy_SupplierOperateBll;
import bll.EmBenefit.EmActy_SupplierSelectBll;
import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;

public class EmActy_SupplierInfoEditController {
	private EmActy_SupplierSelectBll bll = new EmActy_SupplierSelectBll();
	EmActy_SupplierOperateBll obll = new EmActy_SupplierOperateBll();
	private EmActySupplierInfoModel esm = (EmActySupplierInfoModel) Executions
			.getCurrent().getArg().get("model");

	private Window win;

	public EmActy_SupplierInfoEditController() {
		esm.setManList(bll
				.getEmActySupContactManInfo(" and coct_state=1 and Coct_supId="
						+ esm.getSupp_id()));
		esm.setProductList(bll
				.getEmActySupProductInfo(" and prod_state=1 and prod_supId="
						+ esm.getSupp_id()));

		ListModelList<String> list = new ListModelList<>();
		list.add("活动");
		list.add("礼品");
		esm.setItemList2(list);
	}

	@Command
	public void winC(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	public void selectedList(@BindingParam("a") Chosenbox cb) {
		if (esm.getSupp_type() != null) {
			String[] type = esm.getSupp_type().split(",");
			for (String s : type) {
				if (esm.getItemList2().contains(s)) {
					cb.addItemToSelection(s);
				}
			}
		}
	}

	// 修改信息的提交事件
	@Command
	public void edit() {
		Chosenbox cb = (Chosenbox) win.getFellow("items");
		Set<String> set = cb.getSelectedObjects();

		if (esm.getSupp_name() == null || esm.getSupp_name().equals("")) {
			Messagebox
					.show("供应商名称不能为空!", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (set != null && !set.toString().equals("")) {
			esm.setSupp_type(set.toString().replace("[", "").replace("]", "")
					.replace(" ", ""));
		} else {
			Messagebox.show("服务类型不能为空!", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		for (EmActySupContactManInfoModel em : esm.getManList()) {
			if (!(em.getCoct_name() != null && !em.getCoct_name().equals(""))) {
				Messagebox.show("联系人姓名不能为空!", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		for (EmActySupProductInfoModel em : esm.getProductList()) {
			if (!(em.getProd_name() != null && !em.getProd_name().equals(""))) {
				Messagebox.show("产品名称不能为空!", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (!(em.getProd_price() != null && !em.getProd_price().equals(""))) {
				Messagebox.show("产品原价不能为空!", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (!(em.getProd_discountprice() != null && !em
					.getProd_discountprice().equals(""))) {
				Messagebox.show("产品折扣价不能为空!", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		Messagebox.show("确认修改?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {

							Integer n1 = 0;
							Integer n2 = 0;
							int k = obll.updateEmActySupplierInfo(esm);

							for (EmActySupContactManInfoModel em : esm
									.getManList()) {
								n1 += obll.updateEmActySupContactManInfo(em);
							}
							for (EmActySupProductInfoModel em : esm
									.getProductList()) {
								n2 += obll.updateEmActySupProductInfo(em);
							}

							if (k > 0
									&& (esm.getManList().size() == 0 || n1 > 0)
									&& (esm.getProductList().size() == 0 || n2 > 0)) {
								Messagebox.show("修改成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("修改失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}

						}
					}
				});
	}

	// 删除联系人
	@Command
	@NotifyChange("esm")
	public void deletecoct(
			@BindingParam("model") final EmActySupContactManInfoModel em) {
		Messagebox.show("确认删除?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {

							EmActy_SupplierOperateBll obll = new EmActy_SupplierOperateBll();
							int k = obll.deletecoct(em.getCoct_id());
							if (k > 0) {
								Messagebox.show("删除成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								esm.setManList(bll
										.getEmActySupContactManInfo(" and coct_state=1 and Coct_supId="
												+ esm.getSupp_id()));
							} else {
								Messagebox.show("删除失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	// 删除报价信息
	@Command
	@NotifyChange("esm")
	public void deleteprod(
			@BindingParam("model") final EmActySupProductInfoModel em) {
		Messagebox.show("确认删除?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							EmActy_SupplierOperateBll obll = new EmActy_SupplierOperateBll();
							int k = obll.deleteprod(em.getProd_id());
							if (k > 0) {
								Messagebox.show("删除成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								esm.setProductList(bll
										.getEmActySupProductInfo(" and prod_state=1 and prod_supId="
												+ esm.getSupp_id()));
							} else {
								Messagebox.show("删除失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public EmActySupplierInfoModel getEsm() {
		return esm;
	}

	public void setEsm(EmActySupplierInfoModel esm) {
		this.esm = esm;
	}

}
