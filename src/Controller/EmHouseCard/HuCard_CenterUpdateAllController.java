package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;

import BI.B;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;

public class HuCard_CenterUpdateAllController {
	private List<EmHouseTakeCardInfoModel> list = (List<EmHouseTakeCardInfoModel>) Executions
			.getCurrent().getArg().get("list");

	private String statename = "";
	private final String nowstatename = "我司待领";
	private boolean visdatainfo = false;
	private Date date;
	private Integer dataId;// 材料ID

	public HuCard_CenterUpdateAllController() {
		if (list.size() > 0) {
			String takebank = list.get(0).getCohf_banklk();
			if (takebank == null || takebank == "") {
				takebank = list.get(0).getRe_band();
			}
			getType(takebank);
		}
	}

	@Command
	public void summit(@BindingParam("win") Window win,
			@BindingParam("gd") final Grid gd) {
		if (statename == null || statename.equals("")) {
			Messagebox.show("请选择下一步状态", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
			String[] str = new String[5];
			Integer k = 0;
			if (statename.equals("中心核收")) {
				if (date == null) {
					Messagebox.show("请填写核收资料时间", "提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				for (EmHouseTakeCardInfoModel m : list) {
					String sql = ",pla_clientassistanttime='"
							+ datechange(date) + "',pla_clientassistant='"
							+ UserInfo.getUsername() + "',re_state=8";

					if (m.getRe_taprid() != null) {
						str = obll.updateTakeCardInfo(m, sql, 0);
					} else {
						Integer lk = obll.updateTakeCardInfo(m.getRe_id(), sql);
						if (lk > 0) {
							str[0] = "1";
						}
					}
					if (str[0] == "1") {
						if (str[2] != null && !str[2].equals(""))// 跳过下一步
						{
							obll.EmHuTakeCardSkip(m, Integer.parseInt(str[2]));
						}
						k = k + 1;
						DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
						try {
							// String[] message = docOC.AddchkHaveTo(gd);
							// if (message[0].equals("1")) {
							String[] message = docOC.AddsubmitDoc(gd, m
									.getRe_id().toString());
							// }
						} catch (Exception e) {
						}
					}
				}
			} else {
				for (EmHouseTakeCardInfoModel m : list) {
					String sql = ",re_state=26";
					if (m.getRe_taprid() != null) {
						str = obll.updateTakeCardInfo(m, sql, 1);
					} else {
						Integer lk = obll.updateTakeCardInfo(m.getRe_id(), sql);
						if (lk > 0) {
							k = k + 1;
						}
					}
					if (str[0] == "1") {
						k = k + 1;
					}
				}
			}
			if (k > 0) {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			}
		}
	}

	@Command
	@NotifyChange("visdatainfo")
	public void changstate() {
		if (statename != null && statename.equals("中心核收")) {
			visdatainfo = true;
		} else {
			visdatainfo = false;
		}
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (d != null && !d.equals("")) {
			date = time.format(d);
		}
		return date;
	}

	// 判断领卡类型
	private void getType(String info) {
		if (info != null) {
			if (info.indexOf("建行") >= 0 || info.indexOf("建设") >= 0) {
				dataId = 41;
			} else if (info.indexOf("招行") >= 0 || info.indexOf("招行") >= 0) {
				dataId = 42;
			}
			// 否则就是默认为中国银行
			else {
				dataId = 43;
			}
		} else {
			dataId = 43;
		}
	}

	public String getNowstatename() {
		return nowstatename;
	}

	public boolean isVisdatainfo() {
		return visdatainfo;
	}

	public void setVisdatainfo(boolean visdatainfo) {
		this.visdatainfo = visdatainfo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

}
