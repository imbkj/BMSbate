package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;

import Model.EmRegistrationInfoModel;
import Util.UserInfo;

public class EmReg_OperatesController {

	List<EmRegistrationInfoModel> list = new ListModelList<>();

	private String names = "";
	private String timestr = "";
	private Date statetime = new Date();
	Integer row = 0;

	@SuppressWarnings("unchecked")
	public EmReg_OperatesController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			list = (List<EmRegistrationInfoModel>) Executions.getCurrent()
					.getArg().get("list");

			String daids = "";
			for (EmRegistrationInfoModel m : list) {
				names += "," + m.getEmba_name();
				daids += "," + m.getErin_id();
			}
			names = names.substring(1);
			daids = daids.substring(1);

			list.clear();
			list = bll.getEmRegInfoList(daids, "");
			row = list.size();
			timestr();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void timestr() {
		switch (list.get(0).getErin_state()) {
		case 3:
			setTimestr("人事网上申报日期");
			break;
		case 5:
			setTimestr("人事签收已盖章表格日期");
			break;
		case 6:
			setTimestr("政府部门办理完成日期");
			break;
		case 7:
			setTimestr("员工领取就业登记证明日期");
			break;
		default:
			break;
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			Integer row1 = 0;
			for (EmRegistrationInfoModel erm : list) {
				erm.setErin_state(erm.getErin_state() + 1);
				erm.setErsr_addname(UserInfo.getUsername());
				erm.setErsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
						.format(statetime));

				if (erm.getErin_state() == 7) {
					erm.setErin_reg_state(1);
				}

				String[] str = new EmReg_OperateBll().UpdateState(erm,
						new Grid());
				if (str[0].equals("1")) {
					row1++;
				}
			}
			if (row1 == row) {
				Messagebox.show("成功提交了" + row1 + "条数据!", "INFORMATION",
						Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			} else if (row1 < row) {
				Messagebox.show("成功提交了" + row1 + "条数据!", "INFORMATION",
						Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("全部数据提交失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getTimestr() {
		return timestr;
	}

	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}

	public Date getStatetime() {
		return statetime;
	}

	public void setStatetime(Date statetime) {
		this.statetime = statetime;
	}
}
