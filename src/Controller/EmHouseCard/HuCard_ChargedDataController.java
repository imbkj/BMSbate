package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmExpressContactInfoModel;
import Model.EmExpressInfoModel;
import Model.EmHouseCardBackInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;
import bll.EmExpress.EmExpressInfoSelectBll;
import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

public class HuCard_ChargedDataController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private EmHouse_TakeCardInfoSelectBll bll = new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseTakeCardInfoModel> list = new ArrayList<EmHouseTakeCardInfoModel>();
	private EmHouseTakeCardInfoModel model = new EmHouseTakeCardInfoModel();
	private String takebank = "", zhtype = "", nextstate = "";
	private boolean vistip = false;

	public HuCard_ChargedDataController() {
		list = bll.getEmhouseTakeCardInfo(" and re_id=" + id);
		if (list.size() > 0) {
			model = list.get(0);
			takebank = model.getCohf_banklk();
			if (takebank == null || takebank == "") {
				takebank = model.getRe_band();
			}
		}
		EmHouse_MakeCardInfoSelectBll mbll = new EmHouse_MakeCardInfoSelectBll();
		zhtype = mbll.getZhType(model.getGid());
	}

	// 领卡信息更新(客服助理核收_服务中心核收)
	@Command
	public void summitzl(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("clientuptime") Date clientuptime) {
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		m.setRe_id(model.getRe_id());
		m.setRe_taprid(model.getRe_taprid());
		m.setState_Name(model.getState_Name());
		m.setCid(model.getCid());

		// Re_State=8表示是客服助理核收
		String sqlstr = "";
		if (nextstate.equals("中心核收")) {
			if (clientuptime == null || clientuptime.equals("")) {
				Messagebox.show("请输入提交资料时间", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				sqlstr = sqlstr + ",Pla_ClientAssistantTime='"
						+ datechange(clientuptime) + "',Pla_ClientAssistant='"
						+ UserInfo.getUsername() + "',Re_State=8";
			}
		} else {
			sqlstr = ",Re_State=26";
		}
		String[] str = new String[5];
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		try {
			if (m.getRe_taprid() != null) {
				str = obll.updateTakeCardInfo(m, sqlstr, 0);
			} else {
				Integer lk = obll.updateTakeCardInfo(m.getRe_id(), sqlstr);
				if (lk > 0) {
					str[0] = "1";
				} else {
					str[0] = "0";
					str[1] = "提交失败";
				}
			}
			if (str[0] == "1") {
				docOC.AddsubmitDoc(gd, id);
				if (nextstate.equals("中心核收")) {//跳过下一步
					if(str[2]!=null&&!str[2].equals(""))
					{
						obll.EmHuTakeCardSkip(m,Integer.parseInt(str[2]));
					}
				}
				Messagebox.show("提交成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@Command
	@NotifyChange("vistip")
	public void changestate() {
		if (nextstate.equals("中心核收")) {
			vistip = true;
		} else {
			vistip = false;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EmHouseTakeCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmHouseTakeCardInfoModel model) {
		this.model = model;
	}

	public String getZhtype() {
		return zhtype;
	}

	public void setZhtype(String zhtype) {
		this.zhtype = zhtype;
	}

	public String getNextstate() {
		return nextstate;
	}

	public void setNextstate(String nextstate) {
		this.nextstate = nextstate;
	}

	public boolean isVistip() {
		return vistip;
	}

	public void setVistip(boolean vistip) {
		this.vistip = vistip;
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
