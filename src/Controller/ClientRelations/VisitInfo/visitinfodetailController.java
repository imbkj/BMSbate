package Controller.ClientRelations.VisitInfo;

import impl.UserInfoImpl;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.ListModelList;

import bll.ClientRelations.VisitInfo.vit_backBll;
import bll.ClientRelations.VisitInfo.vit_backmodBll;

import service.UserInfoService;
import Model.VisitFollowModel;
import Model.VisitInfoModel;
import Util.plyUtil;

public class visitinfodetailController extends SelectorComposer<Component> {
	private List<VisitFollowModel> followList = new ListModelList<VisitFollowModel>();

	
	VisitInfoModel vim1 = new VisitInfoModel();
	
	int viin_id ;
	int viin_tapr_id;

	plyUtil ply = new plyUtil();

	VisitFollowModel vfm = new VisitFollowModel();

	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	
	
	public visitinfodetailController() throws SQLException
	{
	try{
		
		viin_tapr_id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());

		//			//表ID
		viin_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());
		
		
		}
		catch(Exception e)
		{
	
		}
	
		vim1 = vit_backmodBll.getvisitbackDetail(viin_id);

	// 根据viin_id获取跟进事项
	setFollowList(vit_backmodBll.getvisitfollows(viin_id));
	}
	
	
	public List<VisitFollowModel> getFollowList() {
		return followList;
	}

	public void setFollowList(List<VisitFollowModel> followList) {
		this.followList = followList;
	}

	public VisitInfoModel getVim1() {
		return vim1;
	}

	public void setVim1(VisitInfoModel vim1) {
		this.vim1 = vim1;
	}
}
