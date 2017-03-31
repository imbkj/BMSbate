package Controller.CoProduct;

import impl.CpdExcelImpl;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.North;
import org.zkoss.zul.Window;

import service.ExcelService;

import bll.CoProduct.cpd_ListBll;
import bll.CoProduct.cpd_addBll;

import Model.CoAgencyBaseModel;
import Model.CoPAccountModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Util.plyUtil;

public class cpd_List1Controller {

	private ListModelList<CoProductModel> coproductlist;
	plyUtil ply = new plyUtil();
	cpd_ListBll bll = new cpd_ListBll();
	cpd_addBll bll1 = new cpd_addBll();
	private ListModelList<CoPclassModel> classlist;
	private ListModelList<CoPAccountModel> accountlist;
	private ListModelList<CoPclassModel> classlist1;
	private ListModelList<CoPAccountModel> accountlist1;
	private ListModelList<String> addnamelist;
	private List<CoAgencyBaseModel> wtlist = new ListModelList<>();
	private List<CoProductModel> cityList = new ListModelList<>();
	private CoProductModel cityModel = new CoProductModel();
	CoProductModel cop = new CoProductModel();
	private String copr_name = "";
	private String cpac_name = "";
	private String copc_name = "";
	private String copr_addname = "";
	private Date copr_addtime;
	private String copr_type = "";
	private String coab_name="";
	private String statename="";
	private ArrayList<String> statenamelist =new ArrayList<String>();

	// 初始化
	@Init
	public void init() throws SQLException {
		this.coproductlist = new ListModelList<CoProductModel>(
				bll.getcoproduct(cop, "", ""));
		this.classlist = new ListModelList<CoPclassModel>(bll1.getclass());
		classlist.add(0, null);
		this.accountlist = new ListModelList<CoPAccountModel>(bll1.getaccount());
		this.classlist1 = new ListModelList<CoPclassModel>(bll1.getclass());
		this.accountlist1 = new ListModelList<CoPAccountModel>(
				bll1.getaccount());
		this.addnamelist = new ListModelList<String>(bll.getaddnameList());
		setCityList(bll.getCopCityList());
		setWtlist(bll.getcopcoalist());
		cityList.add(0, new CoProductModel());
		this.statenamelist.add(0,"");
		this.statenamelist.add(1,"已禁用");
		
		
	}

	// 表头级联查询
	@Command("search")
	@NotifyChange("coproductlist")
	public void search() throws SQLException {
		try {
			cop.setCopr_name(copr_name);
			cop.setCpac_name(cpac_name);
			cop.setCopc_name(copc_name);
			cop.setCopr_addname(copr_addname);
			cop.setCopr_addtime(copr_addtime);
			cop.setCoab_name(coab_name);
			cop.setStatename(statename);
			cop.setId(cityModel.getId());
			this.coproductlist = new ListModelList<CoProductModel>(
					bll.getcoproduct(cop, "yyyy-MM-dd", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cop.setCopr_name(copr_name);
		cop.setCpac_name(cpac_name);
		cop.setCopc_name(copc_name);
		cop.setCopr_addname(copr_addname);
		cop.setCopr_addtime(copr_addtime);
		cop.setCoab_name(coab_name);
		cop.setStatename(statename);
		cop.setId(cityModel.getId());
		cop.setCopr_type(copr_type);
		this.coproductlist = new ListModelList<CoProductModel>(
				bll.getcoproduct(cop, "yyyy-MM-dd", ""));
	}

	// 特殊查询
	@Command("search1")
	@NotifyChange("coproductlist")
	public void search1(@BindingParam("a") Chosenbox chbcopc_name,
			@BindingParam("b") Chosenbox chbcpac_name,
			@BindingParam("c") Datebox dbcopr_addtime,
			@BindingParam("d") Combobox addtimetype,
			@BindingParam("e") North north) throws SQLException {
		copc_name = ply.SetToString(chbcopc_name.getSelectedObjects());
		cpac_name = ply.SetToString(chbcpac_name.getSelectedObjects());
		cop.setCopc_name(copc_name);
		cop.setCpac_name(cpac_name);
		this.coproductlist = new ListModelList<CoProductModel>(
				bll.getcoproduct(cop, "yyyy-MM", addtimetype.getSelectedItem()
						.getLabel()));
		north.setOpen(false);
	}

	@Command("openwin")
	@NotifyChange({ "coproductlist" })
	public void openwin(@BindingParam("each") CoProductModel m) {
		try {
			Map<String, Object> copMap = new HashMap<>();
			copMap.put("cop", m);
			Window window = (Window) Executions.createComponents(
					"/CoProduct/cpd_info.zul", null, copMap);
			window.doModal();
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 导出excel
	@Command("doExcel")
	public void doExcel(HttpServletResponse response) throws Exception {
		String path = "/../../CoProduct/file/";

		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "Product" + sdf.format(date) + ".xls";

		// 获取绝对路径
		path = ply.getAbsolutePath(path, filename, this);
		// System.out.println(path);

		// 创建文件
		File file = new File(path);
		file.createNewFile();

		String sheetName = "服务产品";
		String[] title = { "产品名称", "产品类型", "所属科目", "添加人", "添加时间" };

		try {
			ExcelService exl = new CpdExcelImpl(file, sheetName, title,
					coproductlist);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		Filedownload.save(file, "xlsx");
		file.delete();

	}

	@Command("upload")
	public void upload() {
		Fileupload.get();
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

	public String getCopr_name() {
		return copr_name;
	}

	public void setCopr_name(String copr_name) {
		this.copr_name = copr_name;
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

	public ListModelList<CoPclassModel> getClasslist1() {
		return classlist1;
	}

	public void setClasslist1(ListModelList<CoPclassModel> classlist1) {
		this.classlist1 = classlist1;
	}

	public ListModelList<CoPAccountModel> getAccountlist1() {
		return accountlist1;
	}

	public void setAccountlist1(ListModelList<CoPAccountModel> accountlist1) {
		this.accountlist1 = accountlist1;
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

	public String getCopr_type() {
		return copr_type;
	}

	public void setCopr_type(String copr_type) {
		this.copr_type = copr_type;
	}

	
	public List<CoAgencyBaseModel> getWtlist() {
		return wtlist;
	}

	public void setWtlist(List<CoAgencyBaseModel> wtlist) {
		this.wtlist = wtlist;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}

}
