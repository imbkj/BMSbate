package Controller.CoProduct;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoPAccountModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Util.plyUtil;
import bll.CoProduct.cpd_ListBll;
import bll.CoProduct.cpd_addBll;

public class cpd_List2Controller {

	private ListModelList<CoProductModel> coproductlist;
	CoProductModel cop = new CoProductModel();
	plyUtil ply = new plyUtil();
	cpd_ListBll bll = new cpd_ListBll();
	cpd_addBll bll1 = new cpd_addBll();
	private ListModelList<CoPclassModel> classlist;
	private ListModelList<CoPAccountModel> accountlist;
	private List<CoProductModel> cityList = new ListModelList<>();
	private CoProductModel cityModel = new CoProductModel();
	private ListModelList<String> addnamelist;

	private ArrayList<String> statenamelist =new ArrayList<String>();
	private String copr_name;
	private String cpac_name;
	private String copc_name;
	private String copr_addname;
	private Date copr_addtime;
	private String statename;

	// 初始化
	@Init
	public void init() throws SQLException {
		this.setCoproductlist(new ListModelList<CoProductModel>(bll
				.getcoproduct(cop, "", "")));
		this.classlist = new ListModelList<CoPclassModel>(bll1.getclass());
		this.accountlist = new ListModelList<CoPAccountModel>(bll1.getaccount());
		this.addnamelist = new ListModelList<String>(bll.getaddnameList());
	//	statenamelist =  List<String>()[2];
		this.statenamelist.add(0,"");
		this.statenamelist.add(1,"已禁用");
		
		setCityList(bll.getCopCityList());
		cityList.add(0, new CoProductModel());
		copr_name = "";
		cpac_name = "";
		copc_name = "";
		copr_addname = "";
		statename="";
		copr_addtime = null;
	}

	// 表头级联查询
	@Command("search")
	@NotifyChange("coproductlist")
	public void search() throws SQLException {
		cop.setCopr_name(copr_name);
		cop.setCpac_name(cpac_name);
		cop.setCopc_name(copc_name);
		cop.setCopr_addname(copr_addname);
		cop.setCopr_addtime(copr_addtime);
		cop.setStatename(statename);
		cop.setId(cityModel.getId());
		this.coproductlist = new ListModelList<CoProductModel>(
				bll.getcoproduct(cop, "yyyy-MM-dd", ""));
	}

	// 弹出修改窗口
	@Command("mod")
	@NotifyChange("coproductlist")
	public void mod(@BindingParam("cop") CoProductModel cop)
			throws SQLException {
		Map copMap = new HashMap();
		copMap.put("cop", cop);
		Window window = (Window) Executions.createComponents(
				"/CoProduct/cpd_info.zul", null, copMap);
		window.doModal();
		search();
	}

	// 删除
	@Command("del")
	@NotifyChange("coproductlist")
	public void del(@BindingParam("id") int copr_id) throws SQLException {
		if (Messagebox.show("是否禁用?", "INFORMATION", Messagebox.YES
				| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
			int row = bll.delete(copr_id);
			if (row > 0) {
				Messagebox.show("禁用成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("禁用失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		search();
	}
	
	//启用
	@Command("redel")
	@NotifyChange("coproductlist")
	public void redel(@BindingParam("id") int copr_id) throws SQLException {
		if (Messagebox.show("是否启用?", "INFORMATION", Messagebox.YES
				| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
			int row = bll.redelete(copr_id);
			if (row > 0) {
				Messagebox.show("启用成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("启用失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		search();
	}

	

	
	
	public ArrayList<String> getStatenamelist() {
		return statenamelist;
	}

	public void setStatenamelist(ArrayList<String> statenamelist) {
		this.statenamelist = statenamelist;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public ListModelList<CoProductModel> getCoproductlist() {
		return coproductlist;
	}

	public void setCoproductlist(ListModelList<CoProductModel> coproductlist) {
		this.coproductlist = coproductlist;
	}

	public ListModelList<CoPclassModel> getClasslist() {
		return classlist;
	}

	public void setClasslist(ListModelList<CoPclassModel> classlist) {
		this.classlist = classlist;
	}

	public ListModelList<CoPAccountModel> getAccountlist() {
		return accountlist;
	}

	public void setAccountlist(ListModelList<CoPAccountModel> accountlist) {
		this.accountlist = accountlist;
	}

	public ListModelList<String> getAddnamelist() {
		return addnamelist;
	}

	public void setAddnamelist(ListModelList<String> addnamelist) {
		this.addnamelist = addnamelist;
	}

	public String getCopr_name() {
		return copr_name;
	}

	public void setCopr_name(String copr_name) {
		this.copr_name = copr_name;
	}

	public String getCpac_name() {
		return cpac_name;
	}

	public void setCpac_name(String cpac_name) {
		this.cpac_name = cpac_name;
	}

	public String getCopc_name() {
		return copc_name;
	}

	public void setCopc_name(String copc_name) {
		this.copc_name = copc_name;
	}

	public String getCopr_addname() {
		return copr_addname;
	}

	public void setCopr_addname(String copr_addname) {
		this.copr_addname = copr_addname;
	}

	public Date getCopr_addtime() {
		return copr_addtime;
	}

	public void setCopr_addtime(Date copr_addtime) {
		this.copr_addtime = copr_addtime;
	}

	public List<CoProductModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CoProductModel> cityList) {
		this.cityList = cityList;
	}

	public CoProductModel getCityModel() {
		return cityModel;
	}

	public void setCityModel(CoProductModel cityModel) {
		this.cityModel = cityModel;
	}
}
