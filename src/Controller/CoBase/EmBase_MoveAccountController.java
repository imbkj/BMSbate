package Controller.CoBase;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;

import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoBase.EmbaseMoveAccountBll;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoglistModel;

import Util.DateUtil;

public class EmBase_MoveAccountController {
	private List<CoCompactModel> compactList1 = new ListModelList<>();
	private List<CoCompactModel> compactList2 = new ListModelList<>();
	private List<CoOfferModel> cooffer1 = new ListModelList<>();
	private List<CoOfferModel> cooffer2 = new ListModelList<>();
	private List<CoOfferListModel> itemList1 = new ListModelList<>();
	private List<CoOfferListModel> itemList2 = new ListModelList<>();
	private List<CoglistModel> empList = new ListModelList<>();

	private EmbaseMoveAccountBll bll = new EmbaseMoveAccountBll();

	private CoBaseModel cbm;
	private String type;
	private String title;

	// 下拉框参数
	private String cp; // 合同名称
	private String cf1; // 报价单名称1
	private String cf2; // 报价单名称2
	private String cl1; // 报价单项目名称1
	private String cl2; // 报价单项目名称2
	private String cu1; // 报价单项目单位1
	private String cu2; // 报价单项目单位2
	private String account1;// 账户类型
	private String account2;// 账户类型

	private Window winC;

	public EmBase_MoveAccountController() {
		cbm = (CoBaseModel) Executions.getCurrent().getArg().get("model");

		title = Executions.getCurrent().getAttribute("name").toString();

		type = title.equals("社保账户转移") ? "社会保险服务" : "住房公积金服务";

		if (!bll.judgeDate(cbm.getCid(), type)) {

		} else {
			setCompactList1(cbm.getCid(), type);
		}

	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		winC = w;
		if (!bll.judgeDate(cbm.getCid(), type)) {
			Messagebox.show("当前不允许提交变更数据!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			w.detach();
		}
	}

	@Command
	@NotifyChange({ "cooffer1", "itemList1", "empList", "compactList2",
			"cooffer2", "itemList1", "itemList2", "cp", "cf1", "cf2", "cl1",
			"cl2", "cu1", "cu2","account1","account2" })
	public void changeInfo(@BindingParam("a") Object o,
			@BindingParam("b") Integer j) {
		// 转换页面传递下拉框的值
		if (o.getClass().equals(Comboitem.class)) {
			changeInfo(((Comboitem) o).getValue(), j);
		} else {

			// 合同模版
			if (o.getClass().equals(CoCompactModel.class)) {
				CoCompactModel cm = (CoCompactModel) o;
				
				// 第一个下拉框
				if (j.equals(1)) {
					account1=type.equals("社会保险服务")?cm.getCoco_shebao():cm.getCoco_house();
					// 更新报价单列表
					setCooffer1(cm.getCoco_id(), type);
					cf1 = cooffer1.get(0).getCoof_name();
					changeInfo(cooffer1.get(0), j);
					
					changeInfo(cm, 0);

				} else {
					// 更新合同下拉框2
					
					if (type.equals("社会保险服务")) {
						setCompactList2(cbm.getCid(), type, cm.getCoco_shebao(),false);
						
						if (compactList2.size() > 0) {
							cp = compactList2.get(0).getCoco_compactid();
							account2=type.equals("社会保险服务")?compactList2.get(0).getCoco_shebao():compactList2.get(0).getCoco_house();
							setCooffer2(compactList2.get(0).getCoco_id(),
									type, cm.getCoco_shebao(),false);
							
							if (cooffer2.size() > 0) {
								cf2 = cooffer2.get(0).getCoof_name();
								changeInfo(cooffer2.get(0), 0);
							}
						}
					} else {
						setCompactList2(cbm.getCid(), type, cm.getCoco_house() ,false);
						if (compactList2.size() > 0) {
							cp = compactList2.get(0).getCoco_compactid();
							setCooffer2(compactList2.get(0).getCoco_id(),
									type, cm.getCoco_house(),false);
							
							cf2 = cooffer2.get(0).getCoof_name();
							changeInfo(cooffer2.get(0), 0);
						}

					}
					
				}
				// 报价单模版
			} else if (o.getClass().equals(CoOfferModel.class)) {
				CoOfferModel cm = (CoOfferModel) o;
				// 第一个下拉框
				if (j.equals(1)) {
					// 更新报价单项目
					setItemList1(cm.getCoof_id(), type);
					if (itemList1.size() > 0) {
						cl1 = itemList1.get(0).getColi_name();
						cu1 = itemList1.get(0).getColi_cpfc_name();
						changeInfo(itemList1.get(0), 1);
					}
				} else {
					// 更新报价单项目2
					if (type.equals("社会保险服务")) {
						setItemList2(cm.getCoof_id(), type, cm.getCoco_shebao(),true);
						
					} else {
						setItemList2(cm.getCoof_id(), type, cm.getCoco_house(),true);
						
					}

					if (itemList2.size() > 0) {
						cl2 = itemList2.get(0).getColi_name();
						cu2 = itemList2.get(0).getColi_cpfc_name();
					}
				}
			} else if (o.getClass().equals(CoOfferListModel.class)) {
				if (j.equals(1)) {
					CoOfferListModel cm = (CoOfferListModel) o;
					empList = bll.getembaseList(cm.getColi_id());
					Date d = new Date();
					String s1 = DateUtil.dateAdd(d, "M", -1);
					String s2 = DateUtil.dateAdd(d, "M", 0);
					for (int i = 0; i < empList.size(); i++) {
						empList.get(i).setCgli_stopdate(Integer.valueOf(s1));
						empList.get(i).setCgli_startdate2(Integer.valueOf(s2));
						empList.get(i).setCgli_startdate(Integer.valueOf(s2));
					}
				}
			}
		}
	}

	@Command
	public void checkall(@BindingParam("a") Checkbox cka) {
		Button btn = (Button) winC.getFellow("btn");
		btn.setVisible(false);

		for (CoglistModel m : empList) {
			if (cka.isChecked()) {
				if (bll.judgeDate(m.getCid(), type)) {
					if (!bll.aduitData(type, m.getGid(), m.getCgli_startdate())) {
						m.setChecked(false);
						BindUtils.postNotifyChange(null, null, m, "checked");
					} else {
						m.setChecked(true);
						btn.setVisible(true);
						BindUtils.postNotifyChange(null, null, m, "checked");
					}
				} else {
					m.setChecked(false);
					BindUtils.postNotifyChange(null, null, m, "checked");
				}
			} else {
				m.setChecked(false);
				btn.setVisible(false);
				BindUtils.postNotifyChange(null, null, m, "checked");
			}
		}

	}

	@Command
	public void aduit(@BindingParam("a") CoglistModel cm,
			@BindingParam("b") Checkbox ck) {
		Button btn = (Button) winC.getFellow("btn");
		btn.setVisible(false);
		if (bll.judgeDate(cm.getCid(), type)) {

			if (!bll.aduitData(type, cm.getGid(), cm.getCgli_startdate())) {
				cm.setChecked(false);
				ck.setChecked(false);

				Messagebox.show("无在册数据或当前有未完成申报数据,不允许操作.", "操作提示",
						Messagebox.OK, Messagebox.ERROR);
			} else {
				btn.setVisible(true);
			}
		} else {
			cm.setChecked(false);
			ck.setChecked(false);

			Messagebox.show("当月禁止操作数据变更.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	@Command
	@NotifyChange("empList")
	public void submit() {
		boolean b = false;
		Grid gd = (Grid) winC.getFellow("gd");

		if (cl1 == null || cl1.equals("")) {
			Messagebox.show("请选择历史报价单项目.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if (cl2 == null || cl2.equals("")) {
			Messagebox.show("请选择新报价单项目.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		for (CoglistModel m : empList) {
			if (m.isChecked()) {
				b = true;
				break;
			}
		}
		if (!b) {
			Messagebox.show("请选择需要转账户人员!", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer j = 0;
							Integer k = 0;
							String str = "";
							Combobox cb1 = (Combobox) winC.getFellow("item1");
							Combobox cb2 = (Combobox) winC.getFellow("item2");
							CoOfferListModel cm1 = cb1.getSelectedItem()
									.getValue();
							CoOfferListModel cm2 = cb2.getSelectedItem()
									.getValue();

							for (CoglistModel m : empList) {
								if (m.isChecked()) {
									j = bll.changeAccount(type, m.getCid(),
											m.getGid(), m.getEmba_name(),
											m.getCgli_stopdate(),
											m.getCgli_startdate(),
											cm1.getColi_id(), cm2.getColi_id(),
											cm2.getColi_group_id(),
											cm2.getColi_group_count());
									if (j > 0) {
										k++;
									} else {
										str = str + "," + m.getEmba_name();
									}
								}
							}
							if (!str.equals("")) {
								str = str.substring(1);
								Messagebox.show(str + ",数据提交出错.", "操作提示",
										Messagebox.OK, Messagebox.INFORMATION);
							} else {

								Messagebox.show(k + "人操作成功!", "操作提示",
										Messagebox.OK, Messagebox.INFORMATION);
								empList = bll.getembaseList(cm1.getColi_id());
								Date d = new Date();
								String s1 = DateUtil.dateAdd(d, "M", -1);
								String s2 = DateUtil.dateAdd(d, "M", 0);
								for (int i = 0; i < empList.size(); i++) {
									empList.get(i).setCgli_stopdate(
											Integer.valueOf(s1));
									empList.get(i).setCgli_startdate2(
											Integer.valueOf(s2));
									empList.get(i).setCgli_startdate(
											Integer.valueOf(s2));
								}
							}
						}
					}
				});

	}

	public List<CoCompactModel> getCompactList1() {
		return compactList1;
	}

	public void setCompactList1(Integer cid, String name) {
		this.compactList1 = bll.getcompactList(cid, name, null, true);
	}

	public List<CoCompactModel> getCompactList2() {
		return compactList2;
	}

	public void setCompactList2(Integer cid, String name, String single,
			boolean b) {
		this.compactList2 = bll.getcompactList(cid, name, single, b);
	}

	public List<CoOfferModel> getCooffer1() {
		return cooffer1;
	}

	public void setCooffer1(Integer coId, String name) {
		this.cooffer1 = bll.getcoofferList(coId, name, null, true);
	}

	public List<CoOfferModel> getCooffer2() {
		return cooffer2;
	}

	public void setCooffer2(Integer coId, String name, String single, boolean b) {
		this.cooffer2 = bll.getcoofferList(coId, name, single, b);
	}

	public List<CoOfferListModel> getItemList1() {
		return itemList1;
	}

	public void setItemList1(Integer coofId, String name) {
		this.itemList1 = bll.getitemList(coofId, name, null, true);
	}

	public List<CoOfferListModel> getItemList2() {
		return itemList2;
	}

	public void setItemList2(Integer coofId, String name, String single,
			boolean b) {
		this.itemList2 = bll.getitemList(coofId, name, single, b);
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getCf1() {
		return cf1;
	}

	public void setCf1(String cf1) {
		this.cf1 = cf1;
	}

	public String getCf2() {
		return cf2;
	}

	public void setCf2(String cf2) {
		this.cf2 = cf2;
	}

	public String getCl1() {
		return cl1;
	}

	public void setCl1(String cl1) {
		this.cl1 = cl1;
	}

	public String getCl2() {
		return cl2;
	}

	public void setCl2(String cl2) {
		this.cl2 = cl2;
	}

	public String getCu1() {
		return cu1;
	}

	public void setCu1(String cu1) {
		this.cu1 = cu1;
	}

	public String getCu2() {
		return cu2;
	}

	public void setCu2(String cu2) {
		this.cu2 = cu2;
	}

	public List<CoglistModel> getEmpList() {
		return empList;
	}

	public void setEmpList(List<CoglistModel> empList) {
		this.empList = empList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAccount1() {
		return account1;
	}

	public void setAccount1(String account1) {
		this.account1 = account1;
	}

	public String getAccount2() {
		return account2;
	}

	public void setAccount2(String account2) {
		this.account2 = account2;
	}

}
