package Controller.EmZYT;

import java.util.Date;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import bll.EmZYT.EmZYT_OperateBll;

import Model.EmZYTModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmZYT_ConfirmDimissionController {
	private DateStringChange dsc = new DateStringChange();
	private String ylstop = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_ylstop();
	private String housestop = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_housestop();
	private EmZYT_OperateBll oBll=new EmZYT_OperateBll();
	private int emzt_id=((EmZYTModel) Executions.getCurrent().getArg().get("emztM"))
			.getId();
	private int emzt_tapr_id=((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_tapr_id();
	private Integer gid=((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getGid();
	private EmZYTModel emztM=(EmZYTModel) Executions.getCurrent().getArg().get("emztM");

	// 获取当前日期
	Date nowDate = new Date(); // 获取当前时间
	private String nowString = dsc.DatetoSting(nowDate, "yyyy") + "年"
			+ dsc.DatetoSting(nowDate, "MM") + "月"
			+ dsc.DatetoSting(nowDate, "d") + "日";
	private String ylstopString = dsc.DatetoSting(
			dsc.StringtoDate(ylstop, "yyyy-MM-dd"), "yyyy")
			+ "." + dsc.DatetoSting(dsc.StringtoDate(ylstop, "yyyy-MM-dd"), "MM");
	private String housestopString = dsc.DatetoSting(
			dsc.StringtoDate(housestop, "yyyy-MM-dd"), "yyyy")
			+ "." + dsc.DatetoSting(dsc.StringtoDate(housestop, "yyyy-MM-dd"), "MM");

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private Window winID;
	private Include ic;
	
	@Command("wininfo")
	public void wininfo(@BindingParam("a") Window winD) {
		winID = winD;
	}

	@Command("includeinfo")
	public void includeinfo(@BindingParam("ic") Include ic1) {
		ic = ic1;
	}
	
	@GlobalCommand
	public void winClose(@ContextParam(ContextType.VIEW) Component view) {
		Binder bind = (Binder) view.getParent().getAttribute("binder");
		bind.postCommand("refreshWin", null);
		winID.detach();
	}
	
	// 提交事件
	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {
		EmZYTModel m = new EmZYTModel();
		m.setId(emzt_id);
		m.setEmzt_state(2);
		m.setEmzt_declarename(username);
		m.setEmzt_tapr_id(emzt_tapr_id);
		
		//调用更新方法
		String[] message;
		message=oBll.notConfirmAdjust(m);
		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event)
						throws Exception {
					if (Messagebox.Button.OK.equals(event
							.getButton())) {
						//关闭页面
						win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox
					.show(message[1],
							"操作提示",
							new Messagebox.Button[] { Messagebox.Button.OK },
							Messagebox.INFORMATION,
							clickListener);
		} else {
			// 弹出提示
			Messagebox.show(message[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
		
	}

	public String getNowString() {
		return nowString;
	}

	public void setNowString(String nowString) {
		this.nowString = nowString;
	}

	public String getYlstopString() {
		return ylstopString;
	}

	public void setYlstopString(String ylstopString) {
		this.ylstopString = ylstopString;
	}

	public String getHousestopString() {
		return housestopString;
	}

	public void setHousestopString(String housestopString) {
		this.housestopString = housestopString;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public EmZYTModel getEmztM() {
		return emztM;
	}

	public void setEmztM(EmZYTModel emztM) {
		this.emztM = emztM;
	}
	
}
