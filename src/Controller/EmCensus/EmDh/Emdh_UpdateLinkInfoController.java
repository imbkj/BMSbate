package Controller.EmCensus.EmDh;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmCensusModel;
import Model.EmDhModel;
import Model.EmbaseModel;
import Model.SmsSendRecModel;
import Util.UserInfo;
import bll.EmCensus.EmCensus_SelectBll;
import bll.EmCensus.EmDh.EmDh_OperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;
import bll.SmsMessage.SmsMessageManagerBll;

public class Emdh_UpdateLinkInfoController {
	private String username = UserInfo.getUsername();
	String id = (String) Executions.getCurrent().getArg().get("daid");
	String tperid = (String) Executions.getCurrent().getArg().get("id");
	private SmsMessageManagerBll smsbll = new SmsMessageManagerBll();

	private EmDh_SelectBll bl = new EmDh_SelectBll();
	private List<EmDhModel> listemba = new ArrayList<EmDhModel>();
	private EmDhModel model = new EmDhModel();

	private List<SmsSendRecModel> list = new ArrayList<SmsSendRecModel>();

	public Emdh_UpdateLinkInfoController() {
		listemba = bl.getEmDhInfo(" and a.id=" + id);
		if (listemba.size() > 0) {
			list = smsbll.getSendrecList(listemba.get(0).getGid());
			model = listemba.get(0);
		}
	}

	@Command
	public void submit(@BindingParam("win") Window win,
			@BindingParam("client") String client,
			@BindingParam("linktime") Date linktime,
			@BindingParam("content") String content,
			@BindingParam("linktype") String linktype) {
		if (linktime == null || linktime.equals("")) {
			Messagebox.show("联系时间不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (content == null || content.equals("") || content == "") {
			Messagebox.show("联系内容不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			EmDh_OperateBll opbll = new EmDh_OperateBll();
			EmDhModel models = new EmDhModel();
			String sql = ",emdh_linktime='" + timechange(linktime)
					+ "',emdh_linktype='" + linktype + "'";
			sql = sql + ",emdh_linkname='" + client + "',emdh_linkcontent='"
					+ content + "'";
			if (tperid != null && !tperid.equals("") && tperid != "") {
				models.setEmdh_taprid(Integer.parseInt(tperid));
				models.setId(Integer.parseInt(id));
				String[] str = opbll.EmDhupdate(models, sql, 0);
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("任务单id为空", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 联系员工
	@Command
	@NotifyChange("list")
	public void sendmsg() {
		if (id != null && !id.equals("")) {

			if (listemba.size() > 0) {
				Integer gid = listemba.get(0).getGid();
				Map map = new HashMap<>();
				map.put("gid", gid);
				Window window = (Window) Executions.createComponents(
						"../SysMessage/SysMessage_AddInfo.zul", null, map);
				window.doModal();
				list = smsbll.getSendrecList(listemba.get(0).getGid());
			} else {
				Messagebox.show("该员工没有调户信息", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("数据出现错误，请联系客服人员帮忙解决", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 下一步
	@Command
	public void next(@BindingParam("win") Window win) {
		EmDh_OperateBll opbll = new EmDh_OperateBll();
		EmDhModel models = new EmDhModel();
		String sql = ",emdh_linktime='" + timechange(new Date()) + "'";
		if (tperid != null && !tperid.equals("") && tperid != "") {
			models.setEmdh_taprid(Integer.parseInt(tperid));
		}
		models.setStates("人事联系员工");
		models.setId(Integer.parseInt(id));
		String[] str = opbll.EmDhupdate(models, sql, 0);
		if (str[0].equals("1")) {
			Messagebox.show("提交成功", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出备注
	@Command
	public void addRemark() {
		Map map = new HashMap<>();
		map.put("daid", model.getId() + "");
		map.put("id", model.getEmdh_taprid() + "");
		map.put("model", model);
		map.put("gid", model.getGid());
		Window window = (Window) Executions.createComponents(
				"../EmCensus/Emdh_AddRemark.zul", null, map);
		window.doModal();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<SmsSendRecModel> getList() {
		return list;
	}

	public void setList(List<SmsSendRecModel> list) {
		this.list = list;
	}

	// 时间格式转换
	private java.sql.Date timechange(Date d) {
		java.sql.Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}

}
