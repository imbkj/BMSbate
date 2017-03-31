package Controller.EmBenefit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.EmActyProduceModel;
import Model.EmActySupContactManInfoModel;
import Model.EmActySupProductInfoModel;
import Model.EmActySupplierInfoModel;
import bll.EmBenefit.EmActy_SupplierOperateBll;
import bll.EmBenefit.EmActy_SupplierSelectBll;
import bll.EmBenefit.EmBenefit_commitInfoBll;

public class EmActy_SupplierEditController {
	private EmActy_SupplierSelectBll bll = new EmActy_SupplierSelectBll();
	EmActy_SupplierOperateBll obll = new EmActy_SupplierOperateBll();
	private EmActySupplierInfoModel esm = (EmActySupplierInfoModel) Executions
			.getCurrent().getArg().get("model");
	private List<EmActyProduceModel> plist=new ArrayList<EmActyProduceModel>();
	private EmBenefit_commitInfoBll pbll=new EmBenefit_commitInfoBll();
	private Window win;

	public EmActy_SupplierEditController() {
		esm.setManList(bll
				.getEmActySupContactManInfo(" and coct_state=1 and Coct_supId="
						+ esm.getSupp_id()));
		//esm.setProductList(bll
		//		.getEmActySupProductInfo(" and prod_state=1 and prod_supId="
		//				+ esm.getSupp_id()));
		plist=pbll.getEmActyProduceList(esm.getSupp_id());
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
	
	@Command
	public void changeType(@BindingParam("m") EmActyProduceModel m)
	{
		if(m.getProd_pricetype()!=null&&!m.getProd_pricetype().equals(""))
		{
			if(m.getProd_pricetype().equals("折扣"))
			{
				if(m.getProd_discount()!=null&&m.getProd_discount().compareTo(BigDecimal.ZERO)<0||m.getProd_discount().compareTo(new BigDecimal(1))>0)
				{
					m.setProd_discount(null);
					Messagebox.show("折扣范围有误!", "提示", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}else
			{
				if(m.getProd_discount()!=null&&m.getProd_discount().compareTo(BigDecimal.ZERO)<0)
				{
					m.setProd_discount(null);
					Messagebox.show("价格不能小于0!", "提示", Messagebox.OK, Messagebox.ERROR);
					return;
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

		/*for (EmActySupProductInfoModel em : esm.getProductList()) {
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
		}*/
		for(EmActyProduceModel pm:plist)
		{
			if (pm.getProd_pricetype()==null||pm.getProd_pricetype().equals("")) {
				Messagebox.show("计算方式不能为空!", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			
			if (pm.getProd_discount()==null||pm.getProd_discount().equals("")) {
				Messagebox.show("扣价/价格不能为空!", "提示", Messagebox.OK,
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
							//for (EmActySupProductInfoModel em : esm
								//	.getProductList()) {
							//	n2 += obll.updateEmActySupProductInfo(em);
							//}
							
							for (EmActyProduceModel pm:plist) {
								n2 += obll.EmActyProduceEdit(pm);
							}

							if (k > 0
									&& (esm.getManList().size() == 0 || n1 > 0)
									&& (plist.size() == 0 || n2 > 0)) {
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
			@BindingParam("model") final EmActyProduceModel em) {
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

	public List<EmActyProduceModel> getPlist() {
		return plist;
	}

	public void setPlist(List<EmActyProduceModel> plist) {
		this.plist = plist;
	}
	
}
