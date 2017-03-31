package Controller.EmBaseCompact;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Model.Allinone_ListModel;
import Model.EmBaseCompactListModel;
import bll.EmBaseCompact.EmBaseCompact_BaseBll;

public class EmBaseCompact_BaseController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	
	EmBaseCompact_BaseBll bll = new EmBaseCompact_BaseBll();

	private ListModelList<EmBaseCompactListModel> emcompact;// 劳动合同未制作列表
	
	private ListModelList<Allinone_ListModel> emcompact_baselist;// 显示劳动合同主表数据
	
	private ListModelList<Allinone_ListModel> emcompact_list;// 显示劳动合同数据
	
	private ListModelList<Allinone_ListModel> emcompact_chlist;// 显示劳动合同变更数据
	

	public EmBaseCompact_BaseController() throws SQLException {
		String ebco_id = "0";
		try {
			ebco_id = Executions.getCurrent().getArg().get("ebco_id")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		
		emcompact = new ListModelList<EmBaseCompactListModel>(
				bll.getemcompactlist(gid));// 劳动合同未制作列表
		
		emcompact_list = new ListModelList<Allinone_ListModel>(
				bll.getemcompact_list(gid));// 显示劳动合同数据
		
		emcompact_baselist = new ListModelList<Allinone_ListModel>(
				bll.getemcompact_baselist(gid));// 显示劳动合同数据
		
		emcompact_chlist = new ListModelList<Allinone_ListModel>(
				bll.getemcompact_chlist(ebco_id));// 显示劳动合同数据
	}
	
	
	
	// 查看劳动合同合同
		@Command("emcompact_check")
		public void emcompact_check(@BindingParam("emco") EmBaseCompactListModel emco) {
			Map arg = new HashMap();
			arg.put("daid", emco.getEbco_id());
			arg.put("id", emco.getEbco_tapr_id());
			arg.put("gid", gid);
			System.out.println("xxx-xxx");
			System.out.println(emco.getEbco_id());
			System.out.println(emco.getEbco_tapr_id());
			Window wnd = (Window) Executions.createComponents(
					"../EmBaseCompact/EmBaseCompact_CheckDoc.zul", null, arg);
			wnd.doModal();
		}

	public ListModelList<EmBaseCompactListModel> getEmcompact() {
		return emcompact;
	}

	public void setEmcompact(
			ListModelList<EmBaseCompactListModel> emcompact) {
		this.emcompact = emcompact;
	}



	public ListModelList<Allinone_ListModel> getEmcompact_list() {
		return emcompact_list;
	}



	public void setEmcompact_list(ListModelList<Allinone_ListModel> emcompact_list) {
		this.emcompact_list = emcompact_list;
	}



	public ListModelList<Allinone_ListModel> getEmcompact_baselist() {
		return emcompact_baselist;
	}



	public void setEmcompact_baselist(
			ListModelList<Allinone_ListModel> emcompact_baselist) {
		this.emcompact_baselist = emcompact_baselist;
	}



	public ListModelList<Allinone_ListModel> getEmcompact_chlist() {
		return emcompact_chlist;
	}



	public void setEmcompact_chlist(
			ListModelList<Allinone_ListModel> emcompact_chlist) {
		this.emcompact_chlist = emcompact_chlist;
	}
	
	
}
