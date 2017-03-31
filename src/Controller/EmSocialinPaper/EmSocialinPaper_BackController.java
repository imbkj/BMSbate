package Controller.EmSocialinPaper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSocialinPaper.EmSocialinPaperOperateBll;

import Model.EmSocialinPaperModel;
import Util.UserInfo;

public class EmSocialinPaper_BackController {

	private List<EmSocialinPaperModel> list = new ListModelList<>();
	private String names = "";
	private Date time = new Date();
	private String reason = "";
	private String addname = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	@SuppressWarnings("unchecked")
	public EmSocialinPaper_BackController() {
		try {
			setList((List<EmSocialinPaperModel>) Executions.getCurrent()
					.getArg().get("list"));

			for (EmSocialinPaperModel m : list) {
				names += m.getName() + "、";
			}
			names = names.substring(0, names.length() - 2);
			setAddname(UserInfo.getUsername());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {

		if (reason.isEmpty()) {
			Messagebox.show("请输入退回原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			try {
				EmSocialinPaperOperateBll bll = new EmSocialinPaperOperateBll();
				int i = 0;
				for (EmSocialinPaperModel m : list) {

					m.setEspa_state(m.getEspa_state() - 1);
					m.setEspa_backreason(reason);
					m.setEspa_backname(addname);

					String[] str = bll.back(m);
					if (str[0].equals("1")) {
						i++;
					}
				}

				if (i > 0) {
					Messagebox.show("成功退回了" + i + "条办卡数据!", "INFORMATION",
							Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("成功退回了0条数据!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("退回出错,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				System.out.println(e.toString());
			}
		}
	}

	public List<EmSocialinPaperModel> getList() {
		return list;
	}

	public void setList(List<EmSocialinPaperModel> list) {
		this.list = list;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

}
