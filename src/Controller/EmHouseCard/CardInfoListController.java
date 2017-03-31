package Controller.EmHouseCard;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;
import bll.EmResidencePermit.Emrp_ChangeSelectBll;
import bll.EmResidencePermit.Emrp_ListBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

import Model.EmHouseMakeCardInfoModel;
import Model.EmHouseTakeCardInfoModel;
import Model.EmResidencePermitChangeModel;
import Model.EmResidencePermitInfoModel;
import Model.EmShebaoCardInfoModel;

public class CardInfoListController {
	private String gid = Executions.getCurrent().getArg().get("gid").toString();
	private EmHouse_MakeCardInfoSelectBll makebll=new EmHouse_MakeCardInfoSelectBll();
	private List<EmHouseMakeCardInfoModel> makecardlist=makebll.getEmhouseMakeCardInfo("and a.gid="+gid);
	private EmHouse_TakeCardInfoSelectBll takebll = new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseTakeCardInfoModel> takecardlist = takebll.getEmhouseTakeCardInfo("and a.gid="+gid);
	private EmShebaoCardInfoSelectBll sbcdbll=new EmShebaoCardInfoSelectBll();
	private List<EmShebaoCardInfoModel> sbcdblllist=sbcdbll.getEmShebaoCardInfoList(" and a.gid="+gid);
	private Emrp_ListBll serpbll = new Emrp_ListBll();;
	private List<EmResidencePermitInfoModel> serplist =serpbll.getEmrpList("and type=1 and typename='前道状态' and a.gid="+gid);
	
	private Emrp_ChangeSelectBll chbll = new Emrp_ChangeSelectBll();
	private List<EmResidencePermitChangeModel> chlist = chbll.getChangeList(" and a.gid="+gid);
	
	public List<EmHouseMakeCardInfoModel> getMakecardlist() {
		return makecardlist;
	}
	public void setMakecardlist(List<EmHouseMakeCardInfoModel> makecardlist) {
		this.makecardlist = makecardlist;
	}
	public List<EmHouseTakeCardInfoModel> getTakecardlist() {
		return takecardlist;
	}
	public void setTakecardlist(List<EmHouseTakeCardInfoModel> takecardlist) {
		this.takecardlist = takecardlist;
	}
	public List<EmShebaoCardInfoModel> getSbcdblllist() {
		return sbcdblllist;
	}
	public void setSbcdblllist(List<EmShebaoCardInfoModel> sbcdblllist) {
		this.sbcdblllist = sbcdblllist;
	}
	public List<EmResidencePermitInfoModel> getSerplist() {
		return serplist;
	}
	public void setSerplist(List<EmResidencePermitInfoModel> serplist) {
		this.serplist = serplist;
	}
	public List<EmResidencePermitChangeModel> getChlist() {
		return chlist;
	}
	public void setChlist(List<EmResidencePermitChangeModel> chlist) {
		this.chlist = chlist;
	}
	
	
}
