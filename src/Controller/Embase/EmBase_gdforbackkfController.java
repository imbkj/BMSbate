package Controller.Embase;

import impl.WorkflowCore.Core.WfFlowControlImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.Embase.EmBase_gdBll;
import bll.Embase.EmbaseListBll;
import bll.Taskflow.Task_controlBll;
import bll.LoginBll;
import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoBase.CoBase_SelectBll;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmHouseChangeModel;
import Model.EmbaseGDModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.TaskProcessModel;
import Model.loginroleModel;
import Util.UserInfo;

public class EmBase_gdforbackkfController {

	private List<EmbaseGDModel> gdlist = new ListModelList<>();
	//private List<EmbaseGDModel> rgdlist = new ListModelList<>();
	private List<EmbaseGDModel> ownmonthList = new ListModelList<>();
	private List<EmbaseGDModel> clientList = new ListModelList<>();
	private List<EmbaseGDModel> typeList = new ListModelList<>();
	private List<EmbaseGDModel> rtypeList = new ListModelList<>();
	private List<EmbaseGDModel> addnamelist = new ListModelList<>();
	 
	private EmbaseGDModel egm = new EmbaseGDModel();

	private EmBase_gdBll bll = new EmBase_gdBll();
	
	private Window win;

	public EmBase_gdforbackkfController() {
		//egm.setDeclareName("未确认");
		egm.setDeclareState(3);
		gdlist = bll.getListsbgjjkfback(egm);
		ownmonthList = bll.getownmonthList();
		clientList = bll.getclientListsbgjjkfback();
		rtypeList = bll.gettypeListsbgjjkfback();
		//addnamelist=bll.getaddnameListnojd();
		for (EmbaseGDModel m1 : rtypeList) {
			if (m1.getType().equals("养老补缴退回") || m1.getType().equals("医疗补缴退回") || m1.getType().equals("社保退回") 
					|| m1.getType().equals("外籍人退回") || m1.getType().equals("社保交单退回")
						 || m1.getType().equals("公积金补缴退回")
							|| m1.getType().equals("公积金退回")
							|| m1.getType().equals("公积金交单退回") 
					)
				
				if (m1.getTypeId().equals(4) || m1.getTypeId().equals(5) || m1.getTypeId().equals(6) 
						|| m1.getTypeId().equals(7)
						|| m1.getTypeId().equals(8) 
						|| m1.getTypeId().equals(9)
						|| m1.getTypeId().equals(10)
					 
						
						)
				{
					typeList.add(m1);
				}
				
			}
		
	}
	
	@Command
	@NotifyChange("clist")
	public void message(@BindingParam("a") EmbaseGDModel em) {
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		List<loginroleModel> msglist = new ListModelList<>();
		String title="";
		CoBaseModel cobamodel=new CoBaseModel();
		
		Map map = new HashMap<>();
		 
		LoginBll lbll =new LoginBll();
		LoginModel logmodel =new LoginModel();
		//int log_id=0;
		CoBase_SelectBll cobll = new CoBase_SelectBll();
		
		cobamodel=cobll.getCobaseByCid(em.getCid());
		
		 
		
		logmodel=lbll.loginList("and log_name='"+cobamodel.getCoba_client()+"'").get(0);
		 
		
		
		if (em.getTypeId().equals(5)) {
			
			title=cobamodel.getCoba_company()+","+em.getName()+"("+em.getGid()+"),社会保险被退回" ;
			
			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
		
				map.put("id", em.getDataId());// 业务表id
				map.put("tablename", "emshebaochange");// 业务表名
				
				msglist = bll.getuserlist("39,44,45");
			

		} else if (em.getTypeId().equals(6)){
			title=cobamodel.getCoba_company()+","+em.getName()+"("+em.getGid()+"),住房公积金被退回" ;
			
			map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emhousechange");// 业务表名
			msglist = bll.getuserlist("39,40,45");
		}
		else if (em.getTypeId().equals(8)){
			title=cobamodel.getCoba_company()+","+em.getName()+"("+em.getGid()+"),住房公积金被退回" ;
			
			map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emhousebj");// 业务表名
			msglist = bll.getuserlist("39,40,45");
		}
	else if (em.getTypeId().equals(4)){
		title=cobamodel.getCoba_company()+","+em.getName()+"("+em.getGid()+"),社会保险被退回" ;
			
			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emshebaobj");// 业务表名
			msglist = bll.getuserlist("39,40,45");
		}
	
		
		
//		for (loginroleModel m : msglist) {
//			LoginModel lm = new LoginModel();
//			lm.setLog_id(m.getLog_id());
//			lm.setLog_name(m.getLog_name());
//			mlist.add(lm);
//		}
		mlist.add(logmodel);
		// 收件人姓名和收件人id至少要填一个

		map.put("list", mlist);// 默认收件人list
		map.put("title", title);
		
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}
	
	//打开联系人信息
		@Command
		public void linkinfo(@BindingParam("a") EmbaseGDModel em){
			if (em.getCosp_sbhs_caliname().contains("联系人")) {
				String a[] = em.getCosp_sbhs_caliname().split("—");
				if (a.length > 1) {
					Integer cali_id = 0;
					CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
					List<CoAgencyLinkmanModel> linklist = lmBll
							.getLinkmanByCid(em.getCid(),1);
					for (int i = 0; i < linklist.size(); i++) {
						if (linklist.get(i).getCali_name().equals(a[1])) {
							cali_id = linklist.get(i).getCali_id();
						}
					}
					if (cali_id != 0) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("cali_id", String.valueOf(cali_id));
						Window window = (Window) Executions.createComponents(
								"../Cobase/CoBaseLinkMan_Sel.zul", null, map);
						window.doModal();
					}
				}
			}
		}
	
	

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	@NotifyChange("gdlist")
	public void Search() {
		gdlist = bll.getListsbgjjkfback(egm);
		
	 
	}

	// 打开短信页面
	@Command
	public void openmobile(@BindingParam("a") EmbaseGDModel em) {
		Map map = new HashMap<>();
		map.put("mobile", em.getEmba_mobile());
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("SMS_Add.zul", null, map);
		window.doModal();
	}
	
//	//退回客服权限
//	@Command
//	public void 

	// 打开信息详情
	@Command
	@NotifyChange("gdlist")
	public void checkInfo(@BindingParam("a") EmbaseGDModel em) {
		EmbaseListBll embasebll = new EmbaseListBll();
		EmbaseModel emmodel =new EmbaseModel();
		emmodel=embasebll.getEmbaseInfo(" and a.gid="+em.getGid());
		
		String URL="";
		Map map = new HashMap<>();
//		switch (em.getTypeId()) {
	
		 
			map.put("daid", emmodel.getGid());
			map.put("embaId", emmodel.getEmba_id());
			//map.put("id", em.getTaprId());
			URL="../SystemControl/EmBuCenterInfoList.zul";
			
		 
			 
			
//		case 6://公积金退回
//			map.put("daid", em.getDataId());
//		//	map.put("id", em.getTaprId());
//			URL="/EmHOuse/Emhouse_EditList.zul";	
//			break;
//		case 4://社保补交
//			map.put("gid", em.getGid());
//			//map.put("id", em.getTaprId());
//			URL="/EmSheBao/Emsi_DeleteBj_List.zul";
//			break;
//		 
//		case 7://公积金退回
//			
//			map.put("gid", em.getGid());
//		//	map.put("id", em.getTaprId());
//			URL="/EmHOuse/Emhouse_EditList.zul";	
//			break;
//			
//	case 8://劳动合同
//			map.put("daid", em.getDataId());
//			map.put("id", em.getTaprId());
//			URL="/EmBaseCompact/EmBaseCompact_PrintDoc.zul";	
//			break;
//	
//	case 9://社保新增
//		
//		map.put("gid", em.getGid());
//		//map.put("id", em.getTaprId());
//		URL="/EmSheBao/Emsi_DeleteChange_List.zul";
//		break;
//	case 10://公积金新增
//		
//		map.put("gid", em.getGid());
//	//	map.put("id", em.getTaprId());
//		URL="/EmHOuse/Emhouse_EditList.zul";	
//		break;
//		
//	case 11://社保补交
//		map.put("gid", em.getGid());
//		//map.put("id", em.getTaprId());
//		URL="/EmSheBao/Emsi_DeleteBj_List.zul";
//		break;
//		
//	case 12://公积金退回
//		map.put("daid", em.getDataId());
//	//	map.put("id", em.getTaprId());
//		URL="/EmHOuse/Emhouse_EditList.zul";	
//		break;
//		
//			
//		default:
//			break;
//		}
		Window window;
		window = (Window) Executions.createComponents(URL, null, map);
		window.doModal();
		Search();
	}
	
	
	
	//更新状态
	@Command
	@NotifyChange("gdlist")
	public void Stoptask(@BindingParam("a") EmbaseGDModel em){
		
		
			if (Messagebox.show("终止任务单后此条数据将不可操作！确定？？",
					"INFORMATION", Messagebox.YES | Messagebox.NO,
					Messagebox.INFORMATION) == Messagebox.YES) {
				
			 
				
				WfFlowControlImpl impl = new WfFlowControlImpl();
				 
				//i = impl.StopTask(nowModel.getTapr_id(), username, remark);
				
			if	(impl.StopTask(em.getTaprId(), "客服终止任务单",UserInfo.getUsername())==1)
			{
				//清空tapr_id
				
				
				Messagebox.show("结束成功！", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				bll.modinfo(em);
			}
			else
			{
				Messagebox.show("结束失败！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			}
			
			
			Search();
		}
	
	public List<EmbaseGDModel> getAddnamelist() {
		return addnamelist;
	}

	public void setAddnamelist(List<EmbaseGDModel> addnamelist) {
		this.addnamelist = addnamelist;
	}

	public List<EmbaseGDModel> getGdlist() {
		return gdlist;
	}

	public void setGdlist(List<EmbaseGDModel> gdlist) {
		this.gdlist = gdlist;
	}

	public EmbaseGDModel getEgm() {
		return egm;
	}

	public void setEgm(EmbaseGDModel egm) {
		this.egm = egm;
	}

	public List<EmbaseGDModel> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<EmbaseGDModel> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public List<EmbaseGDModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<EmbaseGDModel> clientList) {
		this.clientList = clientList;
	}

	public List<EmbaseGDModel> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<EmbaseGDModel> typeList) {
		this.typeList = typeList;
	}

}
