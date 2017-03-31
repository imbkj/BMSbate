package Controller.EmBodyCheck;

import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBodyCheck.EmBcInfo_OperateBll;

import Model.EmBodyCheckModel;
import Model.embodycheckoperlogModel;
import Util.UserInfo;

public class Embc_BackController {
	private EmBodyCheckModel model = (EmBodyCheckModel) Executions.getCurrent()
			.getArg().get("model");
	private String content,contents;
	private Map map = Executions.getCurrent().getArg();
	
	public Embc_BackController()
	{
		contents=model.getEbcl_backcase();
	}

	// 退回给服务中心
	@Command
	public void back(@BindingParam("win") Window win) {
		if (content != null && !content.equals("")) {
			EmBcInfo_OperateBll bll = new EmBcInfo_OperateBll();
			String sql = ",ebcl_state=7,ebcl_backcase='" + content
					+ "',ebcl_backname='" + UserInfo.getUsername()
					+ "',ebcl_backdate=getdate()";
			String[] str = bll.EmBodyCheckBack(model, sql);
			if (str[0] == "1") {
				embodycheckoperlogModel logm=new embodycheckoperlogModel();
				logm.setBclg_addname(UserInfo.getUsername());
				logm.setBclg_content("退回数据给客服");
				logm.setBclg_ebcl_id(model.getEbcl_id());
				map.put("flag", "1");
				Messagebox.show("退回成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("退回失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请填写退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 退回给客服
	@Command
	public void backclient(@BindingParam("win") Window win) {
		if (contents != null && !contents.equals("")) {
			EmBcInfo_OperateBll bll = new EmBcInfo_OperateBll();
			String sql = ",ebcl_state=15,ebcl_clientbackcase='" + contents
					+ "',ebcl_clientbackname='" + UserInfo.getUsername()
					+ "',ebcl_clientbacktime=getdate()";
			String[] str = bll.EmBodyCheckClientBack(model, sql);
			if (str[0] == "1") {
				map.put("flag", "1");
				Messagebox.show("退回成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("退回失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请填写退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
}
