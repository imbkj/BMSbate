package Controller.EmHouseCard;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmHouseCardFailInfoModel;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

public class HuCard_TakeCardRemarkListController {
	private Integer re_id = (Integer)Executions.getCurrent().getArg().get("re_id");
	private EmHouse_TakeCardInfoSelectBll bll=new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseCardFailInfoModel> list=bll.getEmhouseRemarkCardInfo(" and fail_reid="+re_id);
	public List<EmHouseCardFailInfoModel> getList() {
		return list;
	}
	public void setList(List<EmHouseCardFailInfoModel> list) {
		this.list = list;
	}
	
	

}
