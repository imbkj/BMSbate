package Controller.EmHouseCard;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseMakeCardInfoModel;
import Model.emhouseMakeCardBackInfoModel;
import Util.UserInfo;
import bll.EmHouseCard.EmHouse_MakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;

public class HuCard_MakeCardInfoBackController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmHouse_MakeCardInfoSelectBll bll=new EmHouse_MakeCardInfoSelectBll();
	private EmHouseMakeCardInfoModel model=bll.getMakeCardInfo(id);
	public HuCard_MakeCardInfoBackController() {
		// TODO Auto-generated constructor stub
	}
	
	//退回提交
	@Command
	public void summit(@BindingParam("win") Window win,@BindingParam("gd") Grid gd,
			@BindingParam("backcase") String backcase)
	{
		if(backcase==null||backcase.equals(""))
		{
			Messagebox.show("请填写退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			String[] message=new String[5];
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				message = docOC.UpchkHaveTo(gd);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (message[0].equals("1")) {
				emhouseMakeCardBackInfoModel model=new emhouseMakeCardBackInfoModel();
				model.setBack_cardid(Integer.parseInt(id));
				model.setBack_case(backcase);
				EmHouse_MakeCardInfoOperateBll obll=new EmHouse_MakeCardInfoOperateBll();
				String[] str=obll.backMakeCardInfo(model, Integer.parseInt(tperid));
				if(str[0]=="1")
				{
					try {
							message = docOC.UpsubmitDoc(gd,id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Messagebox.show("提交成功", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
	}
	public EmHouseMakeCardInfoModel getModel() {
		return model;
	}
	public void setModel(EmHouseMakeCardInfoModel model) {
		this.model = model;
	}
	
}
