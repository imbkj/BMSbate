package Controller.CoBase;

import java.util.List;
import java.util.Set;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoBase.CoBaseLinkMan_OperateBll;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseLinkFamilyModel;
import Util.UserInfo;
import bll.CoBase.CoBaseLinkMan_SelectBll;

public class CoBaseLinkMan_UpdateController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private final int cali_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cali_id").toString());
	private CoAgencyLinkmanModel m;
	private List<CoBaseLinkFamilyModel> familyList;
	private ListModelList<String> dutyList;

	public CoBaseLinkMan_UpdateController() {
		CoBaseLinkMan_SelectBll bll = new CoBaseLinkMan_SelectBll();
		m = bll.getLinkModel(cali_id);
		familyList = bll.getLinkFamilyModel(cali_id);
		AddDutyList();

	}

	// 更新
	@Command("upLinkman")
	public void upLinkman(@BindingParam("win") Window win,
			@BindingParam("cbDuty") Chosenbox cbDuty) {
		CoBaseLinkMan_OperateBll bll = new CoBaseLinkMan_OperateBll();
		try {
			if (checkPage(cbDuty.getSelectedObjects())) {
				// 调用修改方法
				String[] message = bll.UpCoBaseLinkman(m, familyList, cali_id);
				if (message[0].equals("1")) {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();

				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			Messagebox
					.show("新增联系人出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 处理特殊字段,并检验
	private boolean checkPage(Set<Object> dutySet) {
		try {
			if (m.getCali_name() == null || "".equals(m.getCali_name())) {
				Messagebox.show("请输入联系人姓名。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				return false;
			} else {
				if (dutySet.contains("发票联系人")) {
					if (m.getCali_address() == null
							|| "".equals(m.getCali_address())) {
						Messagebox.show("当前联系人是“发票联系人”，需填写联系地址。", "操作提示",
								Messagebox.OK, Messagebox.INFORMATION);
						return false;
					} else if ((m.getCali_tel() == null || "".equals(m
							.getCali_tel()))
							&& (m.getCali_tel1() == null || "".equals(m
									.getCali_tel1()))
							&& (m.getCali_mobile() == null || "".equals(m
									.getCali_mobile()))) {
						Messagebox.show("当前联系人是“发票联系人”，需填写至少一个联系电话。", "操作提示",
								Messagebox.OK, Messagebox.INFORMATION);
						return false;
					}
				}
				m.setCali_addname(UserInfo.getUsername());
				m.setCali_duty(dutySet.size() == 0 ? "" : dutySet.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("字段格式检验，出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return false;
		}
		return true;
	}

	// 添加家庭成员
	@Command("addFamily")
	@NotifyChange("familyList")
	public void addFamily() {
		familyList.add(new CoBaseLinkFamilyModel());
	}

	// 删除家庭成员
	@Command("delFamily")
	public void delFamily(@BindingParam("m") CoBaseLinkFamilyModel m) {
		familyList.remove(m);
		BindUtils.postNotifyChange(null, null, this, "familyList");
	}

	// 新增职责选项
	@Command("addDuty")
	public void addDuty(@BindingParam("contact") String contact) {
		dutyList.add(contact);
		dutyList.addToSelection(contact);
	}

	// 生成职责选择列表
	private void AddDutyList() {
		dutyList = new ListModelList<String>();
		dutyList.add("付款通知联系人");
		dutyList.add("发票联系人");
		dutyList.add("活动福利联系人");
		dutyList.add("证件联系人");
		dutyList.add("日常事务联系人");
		dutyList.add("政策变动联系人");
		if (m.getCali_duty() != null && !"".equals(m.getCali_duty())) {
			String dutyChar = FuckTheStr(m.getCali_duty());
			try {
				String[] dutyCon = dutyChar.split(",");
				dutyList.setMultiple(true);
				for (String str : dutyCon) {
					if (!dutyList.contains(str)) {
						dutyList.add(str);
					}
					dutyList.addToSelection(str.trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String FuckTheStr(String str) {
		str = str.replace("[", "");
		str = str.replace("]", "");
		str = str.replace(" ", "");
		return str;
	}

	public CoAgencyLinkmanModel getM() {
		return m;
	}

	public void setM(CoAgencyLinkmanModel m) {
		this.m = m;
	}

	public List<CoBaseLinkFamilyModel> getFamilyList() {
		return familyList;
	}

	public void setFamilyList(List<CoBaseLinkFamilyModel> familyList) {
		this.familyList = familyList;
	}

	public ListModelList<String> getDutyList() {
		return dutyList;
	}

}
