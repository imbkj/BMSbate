package Controller.EmBenefit;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import bll.EmBenefit.EmActy_addProductBll;
import Model.EmActyCompactModel;
import Model.EmActySupProductInfoModel;
import Util.UserInfo;

public class EmActy_addProductController {
	private List<EmActySupProductInfoModel> list = new ListModelList<>();
	private List<EmActyCompactModel> cList = new ListModelList<>();
	private EmActySupProductInfoModel eam = new EmActySupProductInfoModel();
	private EmActy_addProductBll bll = new EmActy_addProductBll();
	private Window win = (Window) Path.getComponent("/winproduct");
	private String username = UserInfo.getUsername();

	private Integer ID = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	
	public EmActy_addProductController() {
		cList= bll.getcompactList(ID);
		if (cList.size()>0) {
			if (cList.get(0).getEaco_supp_id()!=null) {
				eam.setProd_supid(cList.get(0).getEaco_supp_id());
				eam.setEaco_id(ID);
				eam.setBstate(false);
				list = bll.getList(eam);
				if (list.size()>0) {
					eam.setSupp_name(list.get(0).getSupp_name());
				}else {
					Messagebox.show("当前没有可添加的产品,请在供应商信息管理中添加产品报价!", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
	}
	
	@Command
	public void winC(@BindingParam("a") Window w){
		win=w;
		if (list.size()==0) {
			win.detach();
		}
	}

	@Command("update")
	@NotifyChange("eam")
	public void update(@BindingParam("a") Comboitem ci) {
		List<EmActySupProductInfoModel> pList = new ListModelList<>();
		if (ci != null && !ci.getValue().equals("")) {
			eam.setProd_id(Integer.valueOf(ci.getValue().toString()));
			pList = bll.getList(eam);

			eam.setProd_price(pList.get(0).getProd_price());
			eam.setProd_discountprice(pList.get(0).getProd_discountprice());
		} else {
			eam.setProd_id(0);
		}

	}

	@Command("submit")
	public void submit() {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Combobox cb = (Combobox) win.getFellow("pd");

							if (cb.getSelectedItem() != null) {
								eam.setProd_id(Integer.valueOf(cb
										.getSelectedItem().getValue()
										.toString()));
								eam.setProd_modname(username);
								eam.setProd_eaco_id(ID);
								// 产品关联合同
								if (bll.add(eam) > 0) {
									Messagebox.show("操作成功", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									win.detach();
								} else {
									Messagebox.show("操作失败", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
									eam.setProd_eaco_id(null);
								}

							} else {
								Messagebox.show("请选择产品", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public EmActySupProductInfoModel getEam() {
		return eam;
	}

	public void setEam(EmActySupProductInfoModel eam) {
		this.eam = eam;
	}

	public List<EmActySupProductInfoModel> getList() {
		return list;
	}

	public void setList(List<EmActySupProductInfoModel> list) {
		this.list = list;
	}

}
