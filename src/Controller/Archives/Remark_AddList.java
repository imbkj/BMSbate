package Controller.Archives;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmArchiveRemarkModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatumRemarkBll;
import bll.Archives.EmArchive_SelectBll;

public class Remark_AddList {
	private String id = Executions.getCurrent().getArg().get("id").toString();
	private Integer gid =Integer.parseInt(Executions.getCurrent().getArg().get("gid").toString());

	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private String str = " and gid=" + gid
			+ " AND eare_state =1 "
			+ " order by eare_id desc ";
	private EmArchiveRemarkModel earm = new EmArchiveRemarkModel();
	private List<EmArchiveRemarkModel> list = bll.getEmArchiveRemarkInfo(str);
	private String remark = "";
	private EmArchiveDatumRemarkBll addbll = new EmArchiveDatumRemarkBll();
	public Remark_AddList()
	{
		if( Executions.getCurrent().getArg().get("typeid")!=null)
		{
			String typeid=(String) Executions.getCurrent().getArg().get("typeid");
			earm.setEare_tid(Integer.parseInt(typeid));
		}
		earm.setEare_trid(Integer.parseInt(id));
		earm.setEare_addname(UserInfo.getUsername());
		earm.setGid(gid);
	}
	@Command
	@NotifyChange({"list","remark"})
	public void submit(@BindingParam("win") Window win) {
		if (remark == null || remark.equals("")) {
			Messagebox.show("请输入备注信息!", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else {
			earm.setEare_content(remark);
			earm.setEare_remark(remark);
			Integer i = addbll.add(earm);
			if (i > 0) {
				list = bll.getEmArchiveRemarkInfo(str);
				remark="";
				Clients.showNotification("提交成功");
			} else {
				Messagebox.show("添加失败!", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	public List<EmArchiveRemarkModel> getList() {
		return list;
	}

	public void setList(List<EmArchiveRemarkModel> list) {
		this.list = list;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
