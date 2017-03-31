package Controller.CoCompact.CoQuotation;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.CoQuotation.CoQuotation_List1Bll;

import Conn.dbconn;
import Model.CoLatencyClientModel;
import Util.UserInfo;

public class CoQuotation_ListController {

	private String companyName = "";
	private String follower = "";
	private String level = "";
	String str = " and (colanum>0 or colanum is null) and coba_LTS<=0";
	List<CoLatencyClientModel> listmodel = new ListModelList<CoLatencyClientModel>();
	private List<String> followerlist = new ListModelList<String>();
	private CoQuotation_List1Bll bll = new CoQuotation_List1Bll();

	private Window winL;

	@Init
	public void init() throws Exception {
		// if(!isManager())
		// {
		// str = " and cola_ownpeople='"+UserInfo.getUsername()+"'";
		// }

		setListmodel(bll.getCoLatencyClientAllInfo(str,"top 100"));

		setFollowerlist(bll.getFollowerList());
	}

	@Command
	public void gridPage(@BindingParam("a") Window w) {
		winL = w;
	}

	@Command("searchmenu")
	@NotifyChange("listmodel")
	public void searchmenu() {
		CoQuotation_List1Bll bll = new CoQuotation_List1Bll();
		String levelstr = "";
		str = " and (colanum>0 or colanum is null) and coba_LTS<=0";
		if (!companyName.isEmpty()) {
			str += " and cola_company like '%" + companyName + "%'";
		}
		if (!follower.isEmpty()) {
			str += " and cola_follower='" + follower + "'";
		}
		if (!level.isEmpty()) {
			if (level.equals("较低")) {
				levelstr = "(1,2)";
			} else if (level.equals("一般")) {
				levelstr = "(3)";
			} else {
				levelstr = "(4,5)";
			}
			str += " and cola_successlevel in " + levelstr;
		}

		setListmodel(bll.getCoLatencyClientAllInfo(str,""));


	}

	@Command("quotationadd")
	@NotifyChange("listmodel")
	public void quotationadd(@BindingParam("model") CoLatencyClientModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("colaid", model.getCola_id());
		map.put("colacompany", model.getCola_company());
		Window window = (Window) Executions.createComponents(
				"Coquotation_Add.zul", null, map);
		window.doModal();
		searchmenu();

		listmodel = bll.getCoLatencyClientAllInfo(str,"");

	}

	// 弹出修改潜在客户事件
	@Command
	@NotifyChange("listmodel")
	public void updateColaClient(
			@BindingParam("clientpdate") CoLatencyClientModel model) {
		Map colaMap = new HashMap();
		colaMap.put("cola", model);
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/CoLatencyClientUpdate.zul", null, colaMap);
		window.doModal();
		Grid gd = (Grid) winL.getFellow("gd");
		Integer acPage = gd.getActivePage();
		searchmenu();
		gd.setActivePage(acPage);
		// listmodel =
		// bll.getCoLatencyClientAllInfo(" and cola_id="+model.getCola_id());
	}

	// 弹出联系人管理页面
	@Command
	@NotifyChange("listmodel")
	public void openlink(@BindingParam("model") final CoLatencyClientModel model) {
		Map colaMap = new HashMap();
		colaMap.put("model", model);
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/Cola_LinkManInfo.zul", null, colaMap);
		window.doModal();
	}

	// 打开潜在客户新增页面
	@Command
	@NotifyChange("listmodel")
	public void add() {
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/CoLatencyClient_Add.zul", null, null);
		window.doModal();

		listmodel = bll.getCoLatencyClientAllInfo("",null);

	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFollower() {
		return follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<CoLatencyClientModel> getListmodel() {
		return listmodel;
	}

	public void setListmodel(List<CoLatencyClientModel> listmodel) {
		this.listmodel = listmodel;
	}

	public List<String> getFollowerlist() {
		return followerlist;
	}

	public void setFollowerlist(List<String> followerlist) {
		this.followerlist = followerlist;
	}

	// 查询是否是系统管理员
	private boolean isManager() {
		String sql = "select * from role a inner join logingroup b on a.rol_id=b.rol_id "
				+ "inner join Login c on b.log_id=c.log_id "
				+ "where log_name='"
				+ UserInfo.getUsername()
				+ "' and rol_name='系统管理员'";
		boolean flag = false;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}
}
