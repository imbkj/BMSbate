package Controller.EmResidencePermit;

import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitInfoModel;
import Util.UserInfo;

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

import bll.EmResidencePermit.Emrp_OperateBll;

public class Emrp_OperatesController {

	private List<EmResidencePermitInfoModel> erpList = new ListModelList<>();
	private String names = "";
	Integer state = 0;
	private Date statetime = new Date();
	private String timestr = "";
	Integer row = 0;

	@SuppressWarnings("unchecked")
	public Emrp_OperatesController() {
		try {
			setErpList((List<EmResidencePermitInfoModel>) Executions
					.getCurrent().getArg().get("list"));

			for (EmResidencePermitInfoModel m : erpList) {
				names += "," + m.getEmba_name();
			}
			names = names.substring(1);
			state = erpList.get(0).getErpi_state();

			timestr();
			row = erpList.size();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void timestr() {
		switch (state) {
		case 3:
			setTimestr("上传日期");
			break;
		case 4:
			setTimestr("人事网上申报日期");
			break;
		case 6:
			setTimestr("人事收已盖章表格日期");
			break;
		case 7:
			setTimestr("政府部门办理完成日期");
			break;
		case 8:
			setTimestr("人事领取居住证日期");
			break;
		case 9:
			setTimestr("客服领取居住证日期");
			break;
		default:
			break;
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			Integer row1 = 0;
			for (EmResidencePermitInfoModel epm : erpList) {
				epm.setErpi_state(epm.getErpi_state() + 1);
				epm.setEpsr_addname(UserInfo.getUsername());
				epm.setEpsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
						.format(statetime));

				String[] str = new Emrp_OperateBll().UpdateState(epm,
						new EmRegistrationInfoModel(), new Grid());
				if (str[0].equals("1")) {
					row1++;
				}
			}
			if (row1 == row) {
				Messagebox.show("成功提交了" + row1 + "条数据!", "INFORMATION",
						Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			} else if (row1 < row) {
				row = row - row1;
				Messagebox.show("成功提交了" + row1 + "条数据\n" + row + "条数据提交失败!",
						"INFORMATION", Messagebox.OK, Messagebox.INFORMATION);
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

	public List<EmResidencePermitInfoModel> getErpList() {
		return erpList;
	}

	public void setErpList(List<EmResidencePermitInfoModel> erpList) {
		this.erpList = erpList;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public Date getStatetime() {
		return statetime;
	}

	public void setStatetime(Date statetime) {
		this.statetime = statetime;
	}

	public String getTimestr() {
		return timestr;
	}

	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}

}
