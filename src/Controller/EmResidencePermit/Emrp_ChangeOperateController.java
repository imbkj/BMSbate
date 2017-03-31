package Controller.EmResidencePermit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmResidencePermitChangeModel;
import Util.UserInfo;
import bll.EmResidencePermit.Emrp_ChangeBll;
import bll.EmResidencePermit.Emrp_ListBll;

public class Emrp_ChangeOperateController {
	private EmResidencePermitChangeModel m = new EmResidencePermitChangeModel();
	private Integer daid = 0;
	private String timestr = "";
	private Integer state = 0;
	private Date statetime = new Date();

	public Emrp_ChangeOperateController() {
		try {
			Emrp_ListBll bll = new Emrp_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			setM(bll.getErpcList(" and erpc_id=" + daid).get(0));

			if (m.getErpc_state().equals(4) || m.getErpc_state().equals(5)) {
				state = m.getErpc_laststate();
			} else {
				state = m.getErpc_state();
			}
			timestr();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//弹出退回页面
	@Command
	public void back(@BindingParam("win") Window win)
	{
		Map map=new HashMap<>();
		m.setErpc_tapr_id(daid);
		map.put("model", m);
		map.put("flag", "0");
		Window window=(Window)Executions.createComponents("/EmResidencePermit/Emrp_ChangeBack.zul", null, map);
		window.doModal();
		if(map.get("flag")=="1")
		{
			win.detach();
		}
	}

	public void timestr() {
		switch (state) {
		case 1:
			setTimestr("操作转换日期");
			break;
		case 2:
			setTimestr("转换完成日期");
			break;
		default:
			break;
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @param gd
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		if (statetime == null) {
			Messagebox.show("请输入日期!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {
			try {
				m.setErpc_state(state + 1);
				m.setEpcr_addname(UserInfo.getUsername());
				m.setEpcr_statetime(statetime);

				String[] str = new Emrp_ChangeBll().updatestate(m);

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
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

	public String getTimestr() {
		return timestr;
	}

	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}

	public EmResidencePermitChangeModel getM() {
		return m;
	}

	public void setM(EmResidencePermitChangeModel m) {
		this.m = m;
	}

	public final Integer getDaid() {
		return daid;
	}

	public final Integer getState() {
		return state;
	}

	public final Date getStatetime() {
		return statetime;
	}

	public final void setDaid(Integer daid) {
		this.daid = daid;
	}

	public final void setState(Integer state) {
		this.state = state;
	}

	public final void setStatetime(Date statetime) {
		this.statetime = statetime;
	}
}
