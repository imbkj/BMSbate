package Controller.EmHouseCard;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.zhuozhengsoft.pageoffice.zoomseal.User;

import Model.EmHouseCardBackInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Util.UserInfo;
import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

public class HuCard_TakeCardSendBackController {
	String id = (String)Executions.getCurrent().getArg().get("daid");
	//String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmHouse_TakeCardInfoSelectBll bll=new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseTakeCardInfoModel> list=new ArrayList<EmHouseTakeCardInfoModel>();
	private EmHouseTakeCardInfoModel model= new EmHouseTakeCardInfoModel();
	private List<EmHouseCardBackInfoModel> backlist=bll.getEmhouseCardBackInfo(" and back_cardid="+id);
	
	public HuCard_TakeCardSendBackController()
	{
		list=bll.getEmhouseTakeCardInfo(" and re_id="+id);
		if(list.size()>0)
		{
			model=list.get(0);;
		}
	}
	
	//退回提交
	@Command
	public void backtbn(@BindingParam("win") Window win,@BindingParam("backcase") String backcase)
	{
		if(backcase!=null&&!backcase.equals("")&&backcase!="")
		{
			EmHouseTakeCardInfoModel m= new EmHouseTakeCardInfoModel();
			m.setRe_id(model.getRe_id());
			m.setRe_failcase(backcase);
			m.setRe_taprid(model.getRe_taprid());
			m.setAddname(UserInfo.getUsername());
			EmHouse_TakeCardInfoOperateBll obll=new EmHouse_TakeCardInfoOperateBll();
			String[] str =obll.backTakeCardInfo(m);
			if(str[0]=="1")
			{
				Messagebox.show("退回成功", "提示", Messagebox.OK,Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("退回失败", "提示", Messagebox.OK,Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("退回原因不能为空", "提示", Messagebox.OK,Messagebox.ERROR);
		}
	}

	public EmHouseTakeCardInfoModel getModel() {
		return model;
	}

	public void setModel(EmHouseTakeCardInfoModel model) {
		this.model = model;
	}

	public List<EmHouseCardBackInfoModel> getBacklist() {
		return backlist;
	}

	public void setBacklist(List<EmHouseCardBackInfoModel> backlist) {
		this.backlist = backlist;
	}
	
}
