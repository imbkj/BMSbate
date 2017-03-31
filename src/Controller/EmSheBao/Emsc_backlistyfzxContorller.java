package Controller.EmSheBao;

 

	import impl.SystemControl.Data_PopedomIpml;

	import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

	import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

	import service.DataPopedomService;

	import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

	import Model.CoAgencyLinkmanModel;
import Model.EmSheBaoChangeModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.embaseyfModel;
import Util.UserInfo;
import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.EmSheBao.Emsc_backlistyfzxBll;
import bll.Embase.EmbaseListBll;
import Util.DateStringChange;

	public class Emsc_backlistyfzxContorller {
		private List<Integer> countdate;
		private String strwhere = "";
		private String ownmonth="";
		private String[] ownmonthList;
		public List<EmSheBaoChangeModel> embaselist = new ArrayList<EmSheBaoChangeModel>();
		EmbaseListBll embasebll = new EmbaseListBll();
		private	DataPopedomService DataPopedomd =new  Data_PopedomIpml("3","");
		public List<LoginModel> sqrlist = new ArrayList<LoginModel>();
		private String sqrname="";
		private String cobasestring="";
		private String statestring="";
		private String lxstring="";
		private String lxstatestring="";
		private String clstatestring="";
		private String lxwebstatestr="";
		private Emsc_backlistyfzxBll emscbll= new Emsc_backlistyfzxBll();
		
		public Emsc_backlistyfzxContorller() {

			Date d = new Date();
		
			embaselist = embasebll.searchembaseedityflist(sqrname,cobasestring,statestring,
					lxstring,lxstatestring,lxwebstatestr,clstatestring,strwhere);
			
			ownmonthList=embasebll.getLastOwnmonthByNow(true);
			ownmonth=DateStringChange.getOwnmonth();
			sqrlist= DataPopedomd.getLoginlist();

		}

		public List<Integer> getCountdate() {
			return countdate;
		}

		public void setCountdate(List<Integer> countdate) {
			this.countdate = countdate;
		}

		public String getStrwhere() {
			return strwhere;
		}

		public void setStrwhere(String strwhere) {
			this.strwhere = strwhere;
		}

		public List<embaseyfModel> getEmbaselist() {
			return embaselist;
		}

		public void setEmbaselist(List<embaseyfModel> embaselist) {
			this.embaselist = embaselist;
		}

		// 查询客服数据权限列表
		@Command
		@NotifyChange({ "embaselist", "countdate" })
		public void search() throws SQLException {

			embaselist = embasebll.searchembaseedityflist(sqrname,cobasestring,statestring,
					lxstring,lxstatestring,lxwebstatestr,clstatestring,strwhere);
		
		}
		
		
		
		// 打开业务中心
		@Command
		@NotifyChange("embaselist")
		public void changestate(@BindingParam("type") String type,@BindingParam("id") String gid,@BindingParam("state") Combobox statestr) {
		 
			 
				embasebll.changelxstate(Integer.parseInt(gid), statestr.getValue(), Integer.parseInt(type));
		  
				embaselist = embasebll.searchembaseedityflist(sqrname,cobasestring,statestring,
						lxstring,lxstatestring,lxwebstatestr,clstatestring,strwhere);
			
			}

		
		 
		@Command
		public void embaseinonly(@BindingParam("a") embaseyfModel em) {
		EmbaseModel emmodel =embasebll.getEmbaseInfo(" and a.gid="+em.getGid());
			
			Map map = new HashMap<>();
			
			map.put("gid", emmodel.getGid());
			//map.put("id", emmodel.getEmba_tapr_id());
			Window window;
			 
				window = (Window) Executions.createComponents(
						"../CIICNET/Createuser.zul", null, map);

				window.doModal();
				embaselist = embasebll.searchembaseedityflist(sqrname,cobasestring,statestring,
						lxstring,lxstatestring,lxwebstatestr,clstatestring,strwhere);
			}

		//导入员工信息
		
		@Command
		public void embaseindr(@BindingParam("a") embaseyfModel em) {
		EmbaseModel emmodel =embasebll.getEmbaseInfo(" and a.gid="+em.getGid());
			
			Map map = new HashMap<>();
			
			map.put("gid", emmodel.getGid());
			map.put("emba_id", emmodel.getEmba_id());
			Window window;
			 
				window = (Window) Executions.createComponents(
						"../CIICNET/NetCicc_main.zul", null, map);

				window.doModal();
				embaselist = embasebll.searchembaseedityflist(sqrname,cobasestring,statestring,
						lxstring,lxstatestring,lxwebstatestr,clstatestring,strwhere);
			}
		

		// 弹出联系人详细信息页面
		@Command
		public void linkinfo(@BindingParam("cid") String cid,@BindingParam("val") String val) {
			if (val.contains("联系人")) {
				String a[] = val.split("—");
				if (a.length > 1) {
					Integer cali_id = 0;
					CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
					List<CoAgencyLinkmanModel> linklist = lmBll
							.getLinkmanByCid(Integer.parseInt(cid),1);
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

		 

		// 打开业务中心
		@Command
		@NotifyChange("embaselist")
		public void embasein(@BindingParam("a") embaseyfModel em) {
			
			EmbaseModel emmodel =embasebll.getEmbaseInfo(" and a.gid="+em.getGid());
			
			Map map = new HashMap<>();
			
//			map.put("gid", emmodel.getGid());
//			//map.put("id", emmodel.getEmba_tapr_id());
//			Window window;
//			 
//				window = (Window) Executions.createComponents(
//						"../CIICNET/Createuser.zul", null, map);
				
				map.put("daid", emmodel.getEmba_id());
				map.put("id", emmodel.getEmba_tapr_id());
				Window window;
				 
					window = (Window) Executions.createComponents(
							"../Taskflow/EmBaseMenulists.zul", null, map);
				 
				
				
				window.doModal();
				embaselist = embasebll.searchembaseedityflist(sqrname,cobasestring,statestring,
						lxstring,lxstatestring,lxwebstatestr,clstatestring,strwhere);
			}

		 
		// 打开短信页面
		@Command("openmobile")
		public void openmobile(@BindingParam("a") EmbaseModel em) {
			Map map = new HashMap<>();
			map.put("mobile", em.getEmba_mobile());
			map.put("gid", em.getGid());
			Window window;
			window = (Window) Executions.createComponents("SMS_Add.zul", null, map);
			window.doModal();
		}
		
		// 打开详情页面
		@Command("contentinfo")
		public void contentinfo(@BindingParam("a") embaseyfModel em) {
			Map map = new HashMap<>();
			map.put("gid", em.getGid());
			Window window;
			window = (Window) Executions.createComponents("Embase_yfzxinfo.zul", null,
					map);
			window.doModal();
		}
		

		 
		public String getOwnmonth() {
			return ownmonth;
		}

		public void setOwnmonth(String ownmonth) {
			this.ownmonth = ownmonth;
		}

		public String[] getOwnmonthList() {
			return ownmonthList;
		}

		public void setOwnmonthList(String[] ownmonthList) {
			this.ownmonthList = ownmonthList;
		}

		public List<LoginModel> getSqrlist() {
			return sqrlist;
		}

		public void setSqrlist(List<LoginModel> sqrlist) {
			this.sqrlist = sqrlist;
		}

		public String getSqrname() {
			return sqrname;
		}

		public void setSqrname(String sqrname) {
			this.sqrname = sqrname;
		}

		public String getCobasestring() {
			return cobasestring;
		}

		public void setCobasestring(String cobasestring) {
			this.cobasestring = cobasestring;
		}

		public String getStatestring() {
			return statestring;
		}

		public void setStatestring(String statestring) {
			this.statestring = statestring;
		}

		public String getLxstring() {
			return lxstring;
		}

		public void setLxstring(String lxstring) {
			this.lxstring = lxstring;
		}

		public String getLxstatestring() {
			return lxstatestring;
		}

		public void setLxstatestring(String lxstatestring) {
			this.lxstatestring = lxstatestring;
		}

		public String getClstatestring() {
			return clstatestring;
		}

		public void setClstatestring(String clstatestring) {
			this.clstatestring = clstatestring;
		}

		public String getLxwebstatestr() {
			return lxwebstatestr;
		}

		public void setLxwebstatestr(String lxwebstatestr) {
			this.lxwebstatestr = lxwebstatestr;
		}
		
		
		
	}
