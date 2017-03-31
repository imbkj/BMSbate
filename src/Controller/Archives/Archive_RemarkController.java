package Controller.Archives;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.Archives.Archive_FeeManagerBll;

public class Archive_RemarkController {
	private Integer remarkType = (Integer) Executions.getCurrent().getArg()
			.get("rt");
	private Integer id = (Integer) Executions.getCurrent().getArg().get("id");
	private Integer gid = (Integer) Executions.getCurrent().getArg().get("gid");

	private String content;
	
	private Archive_FeeManagerBll bll = new Archive_FeeManagerBll();

	private Window win;
	public Archive_RemarkController() {
		
	}
	
	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		win = winD;
	}

	@Command
	public void submit() {
		Integer i=bll.addRemark(remarkType, id, content,gid);
		if (i>0) {
			Messagebox.show("添加成功", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		}else {
			Messagebox.show("添加失败", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
