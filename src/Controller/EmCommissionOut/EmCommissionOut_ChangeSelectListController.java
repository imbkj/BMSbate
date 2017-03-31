package Controller.EmCommissionOut;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;

import Controller.CoBase.EmBaseOutExcelImpl;
import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutModel;
import Model.PubStateModel;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_ChangeSelectListController {
	private List<EmCommissionOutChangeModel> ecocList;
	private List<EmCommissionOutChangeModel> secocList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> cityList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> coabList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> clientList = new ListModelList<>();
	private List<PubStateModel> stateList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> countList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> addtime_ylist = new ListModelList<>();
	private List<EmCommissionOutChangeModel> addtime_mlist = new ListModelList<>();
	private List<EmCommissionOutChangeModel> addtime_dlist = new ListModelList<>();

	String username = UserInfo.getUsername();
	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String gid = "";
	private String name = "";
	private String idcard = "";
	private String city = "";
	private String coabname = "";
	// private String client = username;
	private String client = "";
	private String sbownmonth = "";
	private String gjjownmonth = "";
	private String addname = "";
	private String addtime_y = "";
	private String addtime_m = "";
	private String addtime_d = "";

	// private String client = "杨志泽";

	private String statename = "";
	private String addtype = "";
	StringBuilder strwhere = new StringBuilder();
	EmCommissionOutListBll bll = new EmCommissionOutListBll();

	public EmCommissionOut_ChangeSelectListController() {
		try {
			bind();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化绑定
	 * 
	 */
	public void bind() {

		try {
			search();
			// ecocList=bll.getemcommoutchangelist(strwhere.toString());

			// ecocList=bll.getemcommoutchangelist(" 1=1 ");

			// setEcocList(bll.getEmCommOutList(""));
			setCityList(bll.getCityList("", ""));
			cityList.add(0, null);
			setCoabList(bll.getCoagencyList());
			coabList.add(0, null);
			setClientList(bll.getClientList());
			clientList.add(0, null);
			setCountList(bll.getCountList());
			setAddtime_ylist(bll.getAddtimeyList());
			addtime_ylist.add(0, null);
			setAddtime_mlist(bll.getAddtimemList());
			addtime_mlist.add(0, null);
			setAddtime_dlist(bll.getAddtimedList());
			addtime_dlist.add(0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	// 导出excel
	@Command("excelout")
	@NotifyChange({ "secocList" })
	public void excelout(HttpServletResponse response) throws Exception {
		plyUtil ply = new plyUtil();
		String path = "/../../EmCommissionOut/File/Templet/";
		String paths = "/EmCommissionOut/File/Download/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "wt_changeselect.xls";

		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = sdf.format(date) + UserInfo.getUsername() + ".xls";
		// 获取绝对路径
		String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
		// 创建文件
		// File file = new File(path);
		// file.createNewFile();
		try {
			File f = new File(paths + newfilename);
			if (f.isFile()) {
				f.delete();
			}
			ExcelService exl = new wtchangeOutExcelImpl(solpath, absolutePath
					+ paths + newfilename, secocList);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		FileOperate.download(paths + newfilename);
	}

	/**
	 * 列表检索
	 * 
	 */
	@Command("search")
	@NotifyChange({ "secocList" })
	public void search() {
		StringBuilder strwhere = new StringBuilder();
		strwhere.append(" ");
		

		if (!cid.isEmpty()) {
			strwhere.append(" and cid=" + cid);
		}
		if (!shortname.isEmpty()) {

			strwhere.append(" and Coab_company like '%" + cid + "%' ");
		}
		if (!gid.isEmpty()) {
			strwhere.append(" and gid=" + cid);
		}
		if (!name.isEmpty()) {

			strwhere.append(" and emba_name like '%" + name + "%' ");
		}
		if (!idcard.isEmpty()) {

			strwhere.append(" and Ecoc_idcard like '%" + idcard + "%' ");
		}
		if (!city.isEmpty()) {

			strwhere.append(" and City like '%" + city + "%' ");
		}
		if (!coabname.isEmpty()) {

			strwhere.append(" and Coab_name like '%" + coabname + "%' ");
		}
		if (!client.isEmpty()) {

			strwhere.append(" and coba_client = '" + client + "' ");
		}
		if (!statename.isEmpty()) {

			strwhere.append(" and Ecoc_statestr like '%" + statename + "%' ");
		}
		if (!sbownmonth.isEmpty()) {

			strwhere.append(" and Sbownmonth =" + sbownmonth);
		}
		if (!gjjownmonth.isEmpty()) {

			strwhere.append(" and Gjjownmonth =" + gjjownmonth);
		}
		if (!addtype.isEmpty()) {

			strwhere.append(" and Ecoc_addtype = '" + addtype + "' ");
		}
		if (!addname.isEmpty()) {

			strwhere.append(" and ecoc_addname = '" + addname + "' ");
		}
		if (!addtime_y.isEmpty()) {

			strwhere.append(" and year(ecoc_addtime) = '" + addtime_y + "' ");
		}
		if (!addtime_m.isEmpty()) {

			strwhere.append(" and month(ecoc_addtime) = '" + addtime_m + "' ");
		}
		if (!addtime_d.isEmpty()) {

			strwhere.append(" and day(ecoc_addtime) = '" + addtime_d + "' ");
		}
		

		secocList = bll.getemcommoutchangelist(strwhere.toString());
		
	}

	/**
	 * 弹窗
	 * 
	 * @param m
	 * @param url
	 */
	@Command("openwin")
	public void detail(@BindingParam("each") EmCommissionOutChangeModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getEcoc_id());

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public List<EmCommissionOutChangeModel> getEcocList() {
		return ecocList;
	}

	public void setEcocList(List<EmCommissionOutChangeModel> ecocList) {
		this.ecocList = ecocList;
	}

	public List<EmCommissionOutChangeModel> getSecocList() {
		return secocList;
	}

	public void setSecocList(List<EmCommissionOutChangeModel> secocList) {
		this.secocList = secocList;
	}

	public final List<CoAgencyBaseCityRelViewModel> getCityList() {
		return cityList;
	}

	public final List<CoAgencyBaseCityRelViewModel> getCoabList() {
		return coabList;
	}

	public final List<EmCommissionOutChangeModel> getClientList() {
		return clientList;
	}

	public final List<PubStateModel> getStateList() {
		return stateList;
	}

	public final List<EmCommissionOutChangeModel> getCountList() {
		return countList;
	}

	public final String getCid() {
		return cid;
	}

	public final String getShortname() {
		return shortname;
	}

	public final String getGid() {
		return gid;
	}

	public final String getName() {
		return name;
	}

	public final String getIdcard() {
		return idcard;
	}

	public final String getCity() {
		return city;
	}

	public final String getCoabname() {
		return coabname;
	}

	public final String getClient() {
		return client;
	}

	public final String getStatename() {
		return statename;
	}

	public final void setCityList(List<CoAgencyBaseCityRelViewModel> cityList) {
		this.cityList = cityList;
	}

	public final void setCoabList(List<CoAgencyBaseCityRelViewModel> coabList) {
		this.coabList = coabList;
	}

	public final void setClientList(List<EmCommissionOutChangeModel> clientList) {
		this.clientList = clientList;
	}

	public final void setStateList(List<PubStateModel> stateList) {
		this.stateList = stateList;
	}

	public final void setCountList(List<EmCommissionOutChangeModel> countList) {
		this.countList = countList;
	}

	public final void setCid(String cid) {
		this.cid = cid;
	}

	public final void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public final void setGid(String gid) {
		this.gid = gid;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public final void setCity(String city) {
		this.city = city;
	}

	public final void setCoabname(String coabname) {
		this.coabname = coabname;
	}

	public final void setClient(String client) {
		this.client = client;
	}

	public final void setStatename(String statename) {
		this.statename = statename;
	}

	public String getSbownmonth() {
		return sbownmonth;
	}

	public void setSbownmonth(String sbownmonth) {
		this.sbownmonth = sbownmonth;
	}

	public String getGjjownmonth() {
		return gjjownmonth;
	}

	public void setGjjownmonth(String gjjownmonth) {
		this.gjjownmonth = gjjownmonth;
	}

	public List<EmCommissionOutChangeModel> getAddtime_ylist() {
		return addtime_ylist;
	}

	public void setAddtime_ylist(List<EmCommissionOutChangeModel> addtime_ylist) {
		this.addtime_ylist = addtime_ylist;
	}

	public List<EmCommissionOutChangeModel> getAddtime_mlist() {
		return addtime_mlist;
	}

	public void setAddtime_mlist(List<EmCommissionOutChangeModel> addtime_mlist) {
		this.addtime_mlist = addtime_mlist;
	}

	public List<EmCommissionOutChangeModel> getAddtime_dlist() {
		return addtime_dlist;
	}

	public void setAddtime_dlist(List<EmCommissionOutChangeModel> addtime_dlist) {
		this.addtime_dlist = addtime_dlist;
	}

	public String getAddtime_y() {
		return addtime_y;
	}

	public void setAddtime_y(String addtime_y) {
		this.addtime_y = addtime_y;
	}

	public String getAddtime_m() {
		return addtime_m;
	}

	public void setAddtime_m(String addtime_m) {
		this.addtime_m = addtime_m;
	}

	public String getAddtime_d() {
		return addtime_d;
	}

	public void setAddtime_d(String addtime_d) {
		this.addtime_d = addtime_d;
	}

}
