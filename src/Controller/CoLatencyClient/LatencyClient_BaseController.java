package Controller.CoLatencyClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoLatencyClientModel;
import Model.CoServiceRequestModel;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.CoQuotation.CoQuotation_List1Bll;

public class LatencyClient_BaseController {
	private String companyName = "";
	private String statename = "";
	private String addname = "";
	private String level = "";
	private String follower = "";
	private String str = " and colanum>0 ";
	private String strs = " ";
	private String strs2 = " and colanum>0";
	private CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();
	private List<CoLatencyClientModel> listmodel = bll
			.getCoLatencyClientAllInfoedit(str);
	private List<CoLatencyClientModel> addnamelist = bll.getAddName();
	private List<String> personList =bll.getFollower(); // 执行人

	private Window winL;
	
	public List<CoLatencyClientModel> getListmodel() {
		return listmodel;
	}

	public List<CoLatencyClientModel> getAddnamelist() {
		return addnamelist;
	}
	

	@Command
	public void gridPage(@BindingParam("a") Window w) {
		winL = w;
	}

	// 弹出修改潜在客户事件
	@Command
	@NotifyChange("listmodel")
	public void updateColaClient(
			@BindingParam("clientpdate") final CoLatencyClientModel model) {
		Map colaMap = new HashMap();
		colaMap.put("cola", model);
		Window window = (Window) Executions.createComponents(
				"CoLatencyClientUpdate.zul", null, colaMap);
		window.doModal();
		listmodel = bll.getCoLatencyClientAllInfoedit(strs2);
	}

	// 添加联系人
	@Command
	@NotifyChange("listmodel")
	public void addLinkMan(
			@BindingParam("linkm") final CoLatencyClientModel model) {
		Map map = new HashMap();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"ColaClientLinkManAdd.zul", null, map);
		window.doModal();
		listmodel = bll.getCoLatencyClientAllInfoedit(strs2);
	}

	// 潜在客户详细页面
	@Command
	public void colainfo(
			@BindingParam("colainfo") final CoLatencyClientModel model) {
		Map colaMap = new HashMap();
		colaMap.put("cola", model);
		Window window = (Window) Executions.createComponents(
				"CoLatencyClientInfo.zul", null, colaMap);
		window.doModal();
	}

	// 服务要求详细页面
	@Command
	public void questinfo(
			@BindingParam("model") final CoLatencyClientModel model) {
		Map map = new HashMap();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"ColaQuestInfo.zul", null, map);
		window.doModal();
	}

	// 转潜在客户
	@Command
	@NotifyChange("listmodel")
	public void changeColaClient(
			@BindingParam("colainfo") final CoLatencyClientModel model) {
		Map colaMap = new HashMap();
		colaMap.put("cola", model);
		Window window = (Window) Executions.createComponents(
				"CoLatencyClientChange.zul", null, colaMap);
		window.doModal();
		//listmodel = bll.getCoLatencyClientAllInfoedit(strs2);
		
		Grid gd = (Grid) winL.getFellow("gd");
		Integer acPage = gd.getActivePage();
		searchmenu();
		gd.setActivePage(acPage);
	}
	
	@Command("searchmenu")
	@NotifyChange("listmodel")
	public void searchmenu() {
		CoQuotation_List1Bll bll = new CoQuotation_List1Bll();
		String levelstr = "";
		str = " and colanum>0 ";
		if (!companyName.isEmpty()) {
			str += " and cola_company like '%" + companyName + "%'";
		}
		if (!addname.isEmpty()) {
			str += " and cola_addname='" + addname + "'";
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
		setListmodel(bll.getCoLatencyClientAllInfo(str));

	}

	// 服务要求信息新增
	@Command
	public void request(@BindingParam("model") CoLatencyClientModel model,
			@BindingParam("url") String url, @BindingParam("a") Integer a) {
		String strinfo = "";
		strinfo = ifok(a, model.getCola_id());
		if (strinfo.equals("")) {
			Map map = new HashMap();
			map.put("model", model);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
		} else {
			Messagebox.show(strinfo, "提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	
	// 弹出公司详细信息页面
	@Command
	public void cobasedetials(@BindingParam("colainfo") CoLatencyClientModel cml) {
			Map cidmap = new HashMap();
			cidmap.put("cid", cml.getCid()+"");
			Window window = (Window) Executions.createComponents(
					"../CoBase/CoBase_DetailInfoshow.zul", null, cidmap);
			window.doModal();
	}
	
	private String ifok(Integer a, Integer cola_id) {
		String str = "";
		Integer k = 0;
		CoServiceRequestSelectBll bll = new CoServiceRequestSelectBll();
		List<CoServiceRequestModel> list = bll
				.getRequestInfoList(" and csqe_cola_id=" + cola_id);
		if (a == 1) {
			if (list.size() > 0) {
				CoServiceRequestModel ml = list.get(0);
				k = ifEmploy(ml);
			}
		} else if (a == 3) {
			if (list.size() > 0) {
				CoServiceRequestModel ml = list.get(0);
				k = ifSalaryOk(ml);
			}
		}

		if (k == 1) {
			str = "该公司已有服务信息，不能新增";
		}
		return str;
	}

	// 快速查询
	@Command
	@NotifyChange("listmodel")
	public void searchColaInfo() {
		str = "";
		strs2 = " and colanum>0";
		if (companyName != "" && !companyName.equals("")) {
			str = str + " and cola_company like '%" + companyName + "%'";
		}
		if (level != "" && !level.equals("") && level != "0"
				&& !level.equals("0")) {
			str = str + " and cola_successlevel=" + level;
		}
		if (addname != "" && !addname.equals("")) {
			str = str + " and cola_addname like '%" + addname + "%'";
		}
		if (follower != "" && !follower.equals("")) {
			str = str + " and cola_follower='" + follower + "'";
		}
		if(statename!=null&&!statename.equals(""))
		{
			if(statename.equals("未转"))
			{
				str=str+" and coba_LTS=0";
			}
			else if(statename.equals("已转"))
			{
				str=str+" and coba_LTS>0";
			}
		}
		strs2 =strs2+ str + strs+" ";
		listmodel = bll.getCoLatencyClientAllInfoedit(strs2);
	}

	// 数据查询
	@Command("search")
	@NotifyChange("listmodel")
	public void search(@BindingParam("lname") String lname,
			@BindingParam("mo") String mo) throws Exception {
		strs = "";
		strs2 = "";
		if (lname != "" && !lname.equals("")) {
			strs = strs
					+ " and cola_id in(select coca_colaid from colaColiLinkRel a,CoAgencyLinkman b ";
			strs = strs
					+ " where a.coca_caliid=b.cali_id and cali_name like '%"
					+ lname + "%')";
		}

		if (mo != "" && !mo.equals("")) {
			strs = strs
					+ " and cola_id in(select coca_colaid from colaColiLinkRel a,CoAgencyLinkman b ";
			strs = strs + " where a.coca_caliid=b.cali_id and (cali_tel='" + mo
					+ "' or cali_mobile='" + mo + "' ";
			strs = strs + " or cali_email='" + mo + "' or cali_tel1='" + mo
					+ "' or cali_email1='" + mo + "'))";
		}
		strs2 = str + strs+" ";
		listmodel = bll.getCoLatencyClientAllInfoedit(strs2);
	}

	// 检查输入的人事服务要求是否为空
	private Integer ifEmploy(CoServiceRequestModel servicemodel) {
		Integer k = 0;
		if (servicemodel.getIsenddate() != null
				&& !servicemodel.getIsenddate().equals("")) {
			k = 1;
		}
		if (servicemodel.getSbtype() != null
				&& !servicemodel.getSbtype().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_house() != null
				&& !servicemodel.getCsqe_house().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_regist() != null
				&& !servicemodel.getCsqe_regist().equals("")) {
			k = 1;
		}
		if (servicemodel.getCardpay() != null
				&& !servicemodel.getCardpay().equals("")) {
			k = 1;
		}
		if (servicemodel.getDtdservice() != null
				&& !servicemodel.getDtdservice().equals("")) {
			k = 1;
		}
		if (servicemodel.getWt() != null && !servicemodel.getWt().equals("")) {
			k = 1;
		}
		if (servicemodel.getFservice() != null
				&& !servicemodel.getFservice().equals("")) {
			k = 1;
		}
		if (servicemodel.getEmfi_backdate() != null
				&& !servicemodel.getEmfi_backdate().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_other() != null
				&& !servicemodel.getCsqe_other().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_ispa() != null
				&& !servicemodel.getCsqe_ispa().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_ws() != null
				&& !servicemodel.getCsqe_ws().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_todo() != null
				&& !servicemodel.getCsqe_todo().equals("")) {
			k = 1;
		}
		return k;
	}

	// 检查输入的薪酬服务信息
	private Integer ifSalaryOk(CoServiceRequestModel servicemodel) {
		Integer k = 0;
		if (servicemodel.getEmfics_backdate() != null
				&& !servicemodel.getEmfics_backdate().equals("")) {
			k = 1;
		}
		if (servicemodel.getPaydate() != null
				&& !servicemodel.getPaydate().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_sbhousetype() != null
				&& !servicemodel.getCsqe_sbhousetype().equals("")) {
			k = 1;
		}
		if (servicemodel.getCsqe_sbhouse_trans() != null
				&& !servicemodel.getCsqe_sbhouse_trans().equals("")) {
			k = 1;
		}
		if (servicemodel.getHouseover() != null
				&& !servicemodel.getHouseover().equals("")) {
			k = 1;
		}
		if (servicemodel.getActype() != null
				&& !servicemodel.getActype().equals("")) {
			k = 1;
		}
		if (servicemodel.getReport() != null
				&& !servicemodel.getReport().equals("")) {
			k = 1;
		}
		if (servicemodel.getTaxctype() != null
				&& !servicemodel.getTaxctype().equals("")) {
			k = 1;
		}
		if (servicemodel.getTaxdetype() != null
				&& !servicemodel.getTaxdetype().equals("")) {
			k = 1;
		}
		if (servicemodel.getTaxpay() != null
				&& !servicemodel.getTaxpay().equals("")) {
			k = 1;
		}
		if (servicemodel.getTaxwt() != null
				&& !servicemodel.getTaxwt().equals("")) {
			k = 1;
		}
		if (servicemodel.getTaxde_date() != null
				&& !servicemodel.getTaxde_date().equals("")) {
			k = 1;
		}
		if (servicemodel.getGzpayroll_type() != null
				&& !servicemodel.getGzpayroll_type().equals("")) {
			k = 1;
		}
		if (servicemodel.getGzpayroll_b() != null
				&& !servicemodel.getGzpayroll_b().equals("")) {
			k = 1;
		}
		return k;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setListmodel(List<CoLatencyClientModel> listmodel) {
		this.listmodel = listmodel;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getFollower() {
		return follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

	public List<String> getPersonList() {
		return personList;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}
}
