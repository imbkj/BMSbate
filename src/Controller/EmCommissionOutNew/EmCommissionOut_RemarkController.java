package Controller.EmCommissionOutNew;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmCommissionOutChangeModel;
import Util.UserInfo;
import bll.EmCommissionOutNew.EmCommissionOut_AddAutBll;
import bll.EmCommissionOutNew.EmCommissionOut_AllEcBll;
import bll.EmCommissionOutNew.EmCommissionOut_AutOperateBll;

public class EmCommissionOut_RemarkController {

	private List<EmCommissionOutChangeModel> gettaprid = new ListModelList<>();// 获取cid,taprid

	// 批量二次确认
	@Command("wt_remark")
	public void wt_remark(@BindingParam("ecoc_id") Label ecoc_id,
			@BindingParam("outcontent") Textbox outcontent,
			@BindingParam("email") Checkbox email,
			@BindingParam("win") Window win) throws Exception {

		int rsult = 1;
		int email_state=0;
		if(email.isChecked()){
			email_state=1;
		}

		EmCommissionOut_AddAutBll bll = new EmCommissionOut_AddAutBll();
		setGettaprid(bll.gettaprid(ecoc_id.getValue()));// 社保费用明细
		
		rsult=bll.remark(ecoc_id.getValue(),outcontent.getValue(),UserInfo.getUsername(),email_state);

		Messagebox.show("添加成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		win.detach();
	}

	public List<EmCommissionOutChangeModel> getGettaprid() {
		return gettaprid;
	}

	public void setGettaprid(List<EmCommissionOutChangeModel> gettaprid) {
		this.gettaprid = gettaprid;
	}

}
