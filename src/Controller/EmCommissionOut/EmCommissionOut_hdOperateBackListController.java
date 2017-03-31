package Controller.EmCommissionOut;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOut_OperateBll;

import Model.EmCommissionOutChangeModel;
import Util.UserInfo;

public class EmCommissionOut_hdOperateBackListController {
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	private Set<Listitem> s= (Set) Executions.getCurrent().getArg().get("set1");
	/**
	 * 批量退回
	 * 
	 * @param set
	 */
	@Command("all_back")
	public void all_back(@BindingParam("win") Window win,
			@BindingParam("msg") Checkbox msg,
			@BindingParam("remark") Textbox remark) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
		if (remark.getValue().equals("")) {
			Messagebox.show("请输入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			if (Messagebox.show("是否确认退回?!", "CONFIRM", Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				try {
					String[] str = null;
					for (Listitem l : s) {
						EmCommissionOutChangeModel m1 = l.getValue();
						m.setEcoc_addname(UserInfo.getUsername());
						m.setEcoc_state(4);
						m.setEcoc_remark(remark.getValue());
						m.setRemark(remark.getValue());
						m.setEcoc_id(m1.getEcoc_id());
						m.setEcoc_addtype("新增");
						m.setEcoc_tapr_id(m1.getEcoc_tapr_id());
						m.setEcoc_ecou_id(m1.getEcoc_ecou_id());
						m.setEcoc_type(m1.getEcoc_type());
						m.setGid(m1.getGid());
						m.setCid(m1.getCid());
						m.setCoba_shortname(m1.getCoba_shortname());
						m.setEmba_name(m1.getEmba_name());
						str = bll.back(m);
						
						if(msg.isChecked()){
							String[] sendstr = bll.sendSysMessage(m);
						}
					}

					if (str[0].equals("1")) {
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						Executions.sendRedirect("EmCommissionOut_hdOperateList.zul");
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	public EmCommissionOutChangeModel getM() {
		return m;
	}

	public void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}
}
