package Controller.EmHouseCard;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;

import Model.EmHouseCardFailInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;

public class EmHouse_TakeCardRemarkAddController {
	private EmHouseTakeCardInfoModel model = (EmHouseTakeCardInfoModel)Executions.getCurrent().getArg().get("model");
	
	
	//添加备注
	@Command
	public void addremark(@BindingParam("win") Window win,@BindingParam("remark") String remark)
	{
		if(remark!=null&&!remark.equals("")&&remark!="")
		{
			EmHouse_TakeCardInfoOperateBll bll=new EmHouse_TakeCardInfoOperateBll();
			EmHouseCardFailInfoModel m=new EmHouseCardFailInfoModel();
			m.setFail_addname(UserInfo.getUsername());
			m.setFail_reid(model.getRe_id());
			m.setFail_content(remark);
			int k=bll.addremark(m);
			if(k>0)
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("备注内容不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

}
