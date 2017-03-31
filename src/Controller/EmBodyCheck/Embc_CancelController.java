package Controller.EmBodyCheck;

import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmBodyCheckModel;
import Model.EmBodycheckCancelModel;
import Util.UserInfo;
import bll.EmBodyCheck.EmBcInfo_OperateBll;

public class Embc_CancelController {
	private Integer tapr_id = null;
	private EmBodyCheckModel model = (EmBodyCheckModel) Executions.getCurrent()
			.getArg().get("model");
	private EmBodycheckCancelModel ml = new EmBodycheckCancelModel();
	private Map map = Executions.getCurrent().getArg();

	public Embc_CancelController()
	{
		if(Executions.getCurrent().getArg()
				.get("tarpid")!=null)
		{
		 tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("tarpid").toString());
		}
	}

	@Command
	public void cancel(@BindingParam("win") Window win) {

		if (ml.getEmca_content() != null && !ml.getEmca_content().equals("")) {
			EmBcInfo_OperateBll bll = new EmBcInfo_OperateBll();
			ml.setEbcl_id(model.getEbcl_id());
			ml.setEmca_ebcl_id(model.getEbcl_id());
			ml.setEmca_remark(ml.getEmca_content());
			ml.setEmca_addname(UserInfo.getUsername());
			ml.setEmca_hospital(model.getEbcs_hospital());
			ml.setEmca_embc_id(model.getEmbc_id());
			String[] str = bll.EmBodyCheckcancel(ml, tapr_id);
			if (str[0] == "1") {
				map.put("flag", "1");
				Messagebox.show("取消成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				map.put("flag", "0");
				Messagebox.show("取消失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			map.put("flag", "0");
			Messagebox.show("请输入取消理由", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmBodycheckCancelModel getMl() {
		return ml;
	}

	public void setMl(EmBodycheckCancelModel ml) {
		this.ml = ml;
	}

}
