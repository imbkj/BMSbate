package Controller.EmHouseCard;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;

import Model.EmHouseTakeCardInfoModel;

public class EmHouse_TakeCardStateUpdateController {
	private EmHouseTakeCardInfoModel model = (EmHouseTakeCardInfoModel)Executions.getCurrent().getArg().get("model");
	
	private EmHouse_TakeCardInfoOperateBll bll=new EmHouse_TakeCardInfoOperateBll();
	
	@Command
	public void summit(@BindingParam("statename") Combobox statename,@BindingParam("win") Window win)
	{
		if(statename.getValue()!=null&&!statename.getValue().equals("")&&statename.getValue()!="")
		{
			String stateid=statename.getSelectedItem().getValue();
			
			Integer k=bll.UpdateTakeCardInfoState(model, stateid);
			if(k>0)
			{
				Messagebox.show("修改成功","提示",Messagebox.OK,Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("修改失败","提示",Messagebox.OK,Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请选择修改的状态","提示",Messagebox.OK,Messagebox.ERROR);
		}
	}

}
