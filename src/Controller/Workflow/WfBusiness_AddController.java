package Controller.Workflow;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Util.UserInfo;
import bll.Workflow.WfNode_AddBll;

public class WfBusiness_AddController {

	@Command("addBusiness")
	public void addBusiness(@BindingParam("name") String name,
			@BindingParam("remark") String remark,
			@BindingParam("win") Window win) {
		String bremark = "";
		WfNode_AddBll bll = new WfNode_AddBll();
		if (remark != null && !"".equals(remark)) {
			bremark = remark;
		}
		if (name != null && !"".equals(name)) {
			try {
				int i = bll.AddBusiness(name, bremark, UserInfo.getUsername());
				if (i == 1) {
					Messagebox.show("业务新增成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else if (i == 2) {
					Messagebox.show("已存在相应业务，无法新增。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					Messagebox.show("业务新增失败，无法新增。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("业务新增出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请输入业务名称。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}

	}
}
