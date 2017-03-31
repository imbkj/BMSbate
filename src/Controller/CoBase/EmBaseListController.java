package Controller.CoBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.EmbaseModel;
import bll.Embase.EmbaseListBll;

public class EmBaseListController {
	
	private List<Integer> countdate;	//统计
	private List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	EmbaseListBll embasebll = new EmbaseListBll();
	private String cid =Executions.getCurrent().getArg()
			.get("cid").toString();
	private CoBaseModel model =(CoBaseModel) Executions.getCurrent().getArg()
			.get("model");
	private String strwhere = "";
	public EmBaseListController() {
		embaselist=embasebll.searchembaselist(cid);
		countdate=embasebll.datecount(cid);
	}

	
	// 查询客服数据权限列表
		@Command
		@NotifyChange({ "embaselist", "countdate" })
		public void search() throws SQLException {
			// System.out.print(log_name);

			try {
				if (strwhere.indexOf("|") != -1) {
					
					embaselist = embasebll.searchembaseeditlistcid(strwhere
							.split("\\|")[1].trim(),Integer.parseInt(cid));
					countdate = embasebll.datecount(strwhere.split("\\|")[1].trim());
					setStrwhere(strwhere.split("\\|")[2].trim());
				} else {
					embaselist = embasebll.searchembaseeditlistcid(strwhere.trim(),Integer.parseInt(cid));
					countdate = embasebll.datecount(strwhere.trim());
				}

			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

		// 打开短信页面
		@Command("openmobile")
		public void openmobile(@BindingParam("a") EmbaseModel em) {
			Map map = new HashMap<>();
			map.put("mobile", em.getEmba_mobile());
			map.put("gid", em.getGid());
			Window window;
			window = (Window) Executions.createComponents("../EmBase/SMS_Check.zul", null,
					map);
			window.doModal();
		}

		// 打开图片页面
		@Command("openempic")
		public void openempic(@BindingParam("a") EmbaseModel em) {
			Map map = new HashMap<>();
			map.put("gid", em.getGid());
			Window window;
			window = (Window) Executions.createComponents("../EmBase/EmPic_Check.zul", null,
					map);
			window.doModal();
		}
		
	
//	// 打开业务中心
//		@Command
//		public void openbucenter(@BindingParam("a") EmbaseModel em) {
//			Map map = new HashMap<>();
//			map.put("daid", em.getGid());
//			map.put("embaId", em.getEmba_id());
//			Window window;
//			if (em.getEmba_state().equals(2)) {
//				window = (Window) Executions.createComponents(
//						"../EmBase/EmBase_Add.zul", null, map);
//
//			} else {
//				window = (Window) Executions.createComponents(
//						"../SystemControl/EmBuCenterInfoList.zul", null, map);
//
//			}
//			window.doModal();
//		}

		// 打开离职页面
		@Command("openDimission")
		@NotifyChange({"embaselist","countdate"})
		public void openDimission(@BindingParam("a") EmbaseModel em) {
			Map map = new HashMap<>();
			map.put("daid", em.getGid());
			Window window;
			window = (Window) Executions.createComponents("../EmBase/Embase_Dimission.zul",
					null, map);
			window.doModal();
			embaselist = embasebll.searchembaselist(cid);
			countdate = embasebll.datecount(cid);
		}
		
		// 打开业务中心
		@Command
		@NotifyChange("embaselist")
		public void openbucenter(@BindingParam("a") EmbaseModel em) {
			Map map = new HashMap<>();
			
			map.put("embaId", em.getEmba_id());
		
			
			Window window;
			if (em.getEmba_state().equals(2)  ) {
				window = (Window) Executions.createComponents(
						"../EmBase/EmBase_Add.zul", null, map);
				window.doModal();
				embaselist = embasebll.searchembaseeditlist(em.getGid().toString());
			}else if (  em.getEmba_state().equals(0)) {
				map.put("cxrz", em.getCid());
				window = (Window) Executions.createComponents(
						"../EmBase/EmBase_Add.zul", null, map);
				window.doModal();
				embaselist = embasebll.searchembaseeditlist(em.getGid().toString());
			} 
			
			else if (em.getEmba_state().equals(5))
			{
				
				window = (Window) Executions.createComponents(
						"../EmBase/EmBase_Addselect.zul", null, map);
				window.doModal();
				embaselist = embasebll.searchembaseeditlist(em.getGid().toString());
			}
			else
			{
				map.put("daid", em.getGid());
				window = (Window) Executions.createComponents(
						"../SystemControl/EmBuCenterInfoList.zul", null, map);
				window.doModal();
			}

		}
		
	
	public String getStrwhere() {
			return strwhere;
		}


		public void setStrwhere(String strwhere) {
			this.strwhere = strwhere;
		}


	public List<EmbaseModel> getEmbaselist() {
		return embaselist;
	}

	public void setEmbaselist(List<EmbaseModel> embaselist) {
		this.embaselist = embaselist;
	}
	
	public List<Integer> getCountdate() {
		return countdate;
	}

	public void setCountdate(List<Integer> countdate) {
		this.countdate = countdate;
	}
}
