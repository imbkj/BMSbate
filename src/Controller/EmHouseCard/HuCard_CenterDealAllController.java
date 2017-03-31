package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;

import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;

public class HuCard_CenterDealAllController {
	private List<EmHouseTakeCardInfoModel> list = (List<EmHouseTakeCardInfoModel>) Executions
			.getCurrent().getArg().get("list");
	private EmHouseTakeCardInfoModel model = new EmHouseTakeCardInfoModel();
	private boolean isvisible = false;
	private String statename, takecardname, username = UserInfo.getUsername();
	private Date centerdate, ydate;
	private String taketype;

	public HuCard_CenterDealAllController() {
		if (list.size() > 0) {
			model = list.get(0);
			takecardname = model.getUsername();
		}
	}

	@Command
	@NotifyChange("isvisible")
	public void changestate() {
		if (statename != null && statename.equals("员工已领")) {
			isvisible = true;
		} else {
			isvisible = false;
		}
	}

	@Command
	public void summit(@BindingParam("win") Window win) {
		Integer k = 0;
		String sql = getSql();
		if (sql != null && !sql.equals("error")) {
			for (EmHouseTakeCardInfoModel m : list) {
				String[] str = new String[5];
				try {
					EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
					if (m.getRe_taprid() != null) {
						str = obll.updateTakeCardInfo(m, sql, 1);
					}
					else
					{
						k=obll.updateTakeCardInfo(m.getRe_id(), sql);
					}
					if (str[0] == "1") {
						k = k + 1;
					}
				} catch (Exception e) {

				}
			}
			if (k > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	private String getSql() {
		String sql = "";
		Integer k = 0;
		if (model.getState_Name().equals("福利领卡")) {
			if (centerdate == null) {
				Messagebox.show("请输入领卡时间", "提示", Messagebox.OK,
						Messagebox.ERROR);
				sql = "error";
			} else {
				sql = ",Pla_ReceiveTime='" + datechange(centerdate)
						+ "',Pla_ReceiveName='" + UserInfo.getUsername()
						+ "',re_flsendname='" + username + "',Re_State=12";
			}
		} else {
			if (statename != null && !statename.equals("")) {
				if (statename.equals("员工已领")) {
					if (ydate == null) {
						Messagebox.show("请输入领卡时间", "提示", Messagebox.OK,
								Messagebox.ERROR);
						sql = "error";
					} else if (taketype == null || taketype.equals("")) {
						Messagebox.show("请选择领卡方式", "提示", Messagebox.OK,
								Messagebox.ERROR);
						sql = "error";
					} else {
						sql = ",Re_State=14,re_name='" + takecardname
								+ "',pla_taketype='" + taketype + "',re_time='"
								+ datechange(ydate) + "'";
					}
				} else {
					sql = ",Re_State=25";
				}
			} else {
				Messagebox.show("请输选择下一步状态", "提示", Messagebox.OK,
						Messagebox.ERROR);
				sql = "error";
			}
		}
		return sql;
	}

	public EmHouseTakeCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmHouseTakeCardInfoModel model) {
		this.model = model;
	}

	public boolean isIsvisible() {
		return isvisible;
	}

	public void setIsvisible(boolean isvisible) {
		this.isvisible = isvisible;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getTakecardname() {
		return takecardname;
	}

	public void setTakecardname(String takecardname) {
		this.takecardname = takecardname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCenterdate() {
		return centerdate;
	}

	public void setCenterdate(Date centerdate) {
		this.centerdate = centerdate;
	}

	public Date getYdate() {
		return ydate;
	}

	public void setYdate(Date ydate) {
		this.ydate = ydate;
	}

	public String getTaketype() {
		return taketype;
	}

	public void setTaketype(String taketype) {
		this.taketype = taketype;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (d != null && !d.equals("")) {
			date = time.format(d);
		}
		return date;
	}
}
