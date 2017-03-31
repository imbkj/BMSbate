package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.sun.org.apache.xpath.internal.operations.And;

import bll.EmBenefit.EmActy_compactAddBll;

import Model.EmActyCompactModel;
import Model.EmActySupplierInfoModel;
import Util.UserInfo;

public class EmActy_compactAddController {
	private EmActyCompactModel eacm = new EmActyCompactModel();
	private List<EmActySupplierInfoModel> companyList = new ListModelList<>();
	private List<EmActyCompactModel> compactlist = new ListModelList<>();
	private EmActy_compactAddBll bll = new EmActy_compactAddBll();
	private String username = UserInfo.getUsername();
	private Window win = (Window) Path.getComponent("/wcadd");

	public EmActy_compactAddController() {
		setCompanyList("", true);
		setCompactlist("", true);

		eacm.setEaco_addname(username);
	}

	@Command("updatecompany")
	@NotifyChange("companyList")
	public void updatecompany(@BindingParam("a") String name,
			@BindingParam("b") Comboitem cb) {
		if (cb != null) {
			eacm.setEaco_supp_id(Integer.valueOf(cb.getValue().toString()));
		}
		setCompanyList(name, true);
	}

	@Command("updatecompact")
	@NotifyChange("compactlist")
	public void updatecompact(@BindingParam("a") String name) {
		setCompactlist(name, true);
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
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");
							Combobox cbsup = (Combobox) win.getFellow("supp");
							Datebox db1 = (Datebox) win.getFellow("signdate");// 签订日期
							Datebox db2 = (Datebox) win.getFellow("inuredate");// 生效日期
							Datebox db3 = (Datebox) win.getFellow("stopdate");// 终止日期

							if (db1.getValue() != null) {
								eacm.setEaco_signdate(sdf.format(db1.getValue()));
							}
							if (db2.getValue() != null) {
								eacm.setEaco_inuredate(sdf.format(db2
										.getValue()));
							}
							if (db3.getValue() != null) {
								eacm.setEaco_stopdate(sdf.format(db3.getValue()));
							}
							if (eacm.getEaco_auto2() != null) {
								eacm.setEaco_auto(eacm.getEaco_auto2().equals(
										"是") ? 1 : 0);
							}

							if (bll.getNameList(eacm.getEaco_name(), false)
									.size() > 0) {

								if (bll.getCompactList(
										eacm.getEaco_compactid(), false).size() > 0) {
									Messagebox.show("该供应商合同已存在", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								} else {
									if (eacm.getEaco_compactid() != null
											&& !eacm.getEaco_compactid()
													.equals("")) {

										Integer i = bll.add(eacm);
							
										if (i > 0) {
											Messagebox.show("添加成功", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
											win.detach();
											Window window = (Window) Executions
													.createComponents(
															"EmActy_compactList.zul",
															null, null);
											window.doModal();
										} else {
											Messagebox.show("添加失败", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									} else {
										Messagebox
												.show("请输入合同号", "操作提示",
														Messagebox.OK,
														Messagebox.ERROR);
									}
								}
							} else {
								Messagebox.show("请选择已有的供应商", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});
	}

	public EmActyCompactModel getEacm() {
		return eacm;
	}

	public void setEacm(EmActyCompactModel eacm) {
		this.eacm = eacm;
	}

	public List<EmActySupplierInfoModel> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(String name, boolean type) {
		this.companyList = bll.getNameList(name, type);
	}

	public List<EmActyCompactModel> getCompactlist() {
		return compactlist;
	}

	public void setCompactlist(String name, boolean type) {
		this.compactlist = bll.getCompactList(name, type);
	}

}
