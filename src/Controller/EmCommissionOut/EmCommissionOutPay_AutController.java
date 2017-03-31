package Controller.EmCommissionOut;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;
import bll.EmCommissionOut.EmCommissionOutPayBll;
import bll.EmCommissionOut.EmCommissionOutPayDifBll;
import Model.EmCommissionOutPayDifModel;
import Model.EmCommissionOutPayModel;
import Util.plyUtil;

public class EmCommissionOutPay_AutController {
	EmCommissionOutPayBll bll = new EmCommissionOutPayBll();
	EmCommissionOutPayDifBll difbll =new EmCommissionOutPayDifBll();
	private ListModelList<EmCommissionOutPayModel> ownmonthlist;// 显示帐单所属月份
	private ListModelList<EmCommissionOutPayModel> citylist;// 委托城市
	private ListModelList<EmCommissionOutPayModel> depcompanylist;// 委托机构
	private ListModelList<EmCommissionOutPayModel> autpaylist;// 显示帐单月份明细
	private ListModelList<EmCommissionOutPayModel> wt_paylist;// 显示帐单明细

	@Init
	public void init() throws SQLException {
		//autpaylist = new ListModelList<EmCommissionOutPayModel>(
		//		bll.getautpaylist("", ""));// 显示帐单月份明细
		
		citylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getyfcitylist());// 显示应付委托城市

		depcompanylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getdepcompanylist(""));// 显示应付委托机构

		ownmonthlist = new ListModelList<EmCommissionOutPayModel>(
				bll.getownmonthlist());// 显示系统帐单月份明细
	}

	// 审核搜索
	@Command("aut_search")
	@NotifyChange("autpaylist")
	public void aut_search(@BindingParam("wt_ownmonth") Combobox wt_ownmonth,
			@BindingParam("wt_depcompany") Combobox wt_depcompany,@BindingParam("wt_city") Combobox wt_city)
			throws SQLException {
		autpaylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getautpaylist(wt_ownmonth.getValue(),
						wt_depcompany.getValue(),wt_city.getValue()));// 显示所有帐单数
	}

	// 提交帐单
	@Command("zd_pass")
	public void zd_pass(@BindingParam("emco") EmCommissionOutPayModel emco)
			throws Exception {

		wt_paylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getwt_paypass(emco.getEcop_gs_yf(), emco.getOwnmonth()));// 显示帐单费用明细

		Messagebox.show("提交成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		Executions.sendRedirect("EmCommissionOutPay_AutList.zul");
	}
	
	// 委托机构查询
	@Command("cityChange")
	@NotifyChange("depcompanylist")
	public void cityChange(@BindingParam("wt_city") Combobox wt_city)
			throws SQLException {
		depcompanylist = new ListModelList<EmCommissionOutPayModel>(
				bll.getdepcompanylist(wt_city.getValue()));// 显示所有帐单数
	}

	// 委托公司实付明细
	@Command("copay_base")
	public void copay_base(@BindingParam("emco") EmCommissionOutPayModel emco) {

		Map arg = new HashMap();
		arg.put("cabc_id", emco.getEcop_cabc_id());
		arg.put("ownmonth", emco.getOwnmonth());
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPay_CoBaseList.zul", null, arg);
		wnd.doModal();
	}
	
	// 委托公司实付明细
		@Command("wt_cpay")
		public void wt_cpay(@BindingParam("emco") EmCommissionOutPayModel emco) {

			Map arg = new HashMap();
			arg.put("cabc_id", emco.getEcop_cabc_id());
			arg.put("ownmonth", emco.getOwnmonth());
			Window wnd = (Window) Executions.createComponents(
					"EmCommissionOutPay_WtUnAutList.zul", null, arg);
			wnd.doModal();
		}
	
	// 本地帐单对比外地帐单
		@Command("yf_sf")
		public void yf_sf(@BindingParam("emco") EmCommissionOutPayModel emco) {

			Map arg = new HashMap();
			arg.put("cabc_id", emco.getEcop_cabc_id());
			arg.put("ownmonth", emco.getOwnmonth());
			arg.put("er_1", "本地");
			arg.put("er_2", "外地");
			arg.put("er_st", 1);
			Window wnd = (Window) Executions.createComponents(
					"EmCommissionOutPay_ErrList.zul", null, arg);
			wnd.doModal();
		}

		// 外地帐单对比本地帐单
		@Command("sf_yf")
		public void sf_yf(@BindingParam("emco") EmCommissionOutPayModel emco) {

			Map arg = new HashMap();
			arg.put("cabc_id", emco.getEcop_cabc_id());
			arg.put("ownmonth", emco.getOwnmonth());
			arg.put("er_2", "本地");
			arg.put("er_1", "外地");
			arg.put("er_st", 2);
			Window wnd = (Window) Executions.createComponents(
					"EmCommissionOutPay_ErrList.zul", null, arg);
			wnd.doModal();
		}
	
	//导出未办反馈所有账单
	@Command("exportAll")
	public void exportAll(@BindingParam("exdate") EmCommissionOutPayModel exdate,@BindingParam("wt_ownmonth") Combobox wt_ownmonth,
			@BindingParam("wt_depcompany") Combobox wt_depcompany) throws SQLException, Exception{
		
		difbll.addEmCommissionOutPayDifInfo("","");
		
		List<EmCommissionOutPayDifModel> listEmCommissionOutPayDif=difbll.getExportList(wt_ownmonth.getValue(),"",wt_depcompany.getValue()); 
		plyUtil p= new plyUtil();
		String path = "/../../EmCommissionOut/File/Download/";// 导出保存路径
		//创建当前日期
		Date date =new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "委托对帐差额记录表" + sdf.format(date) + ".xls";// 定义导出文件名称
		// 获取绝对路径
		path = p.getAbsolutePath(path, filename, this);
		// 创建文件
		File file = new File(path);
		file.createNewFile();
		String sheetName = "委托对帐差额记录表";// Excel表格名
		// 定义表头
		String[] title = { "序号", "城市", "委托机构", "员工编号", "姓名",
				"身份证","所属月份",
				"养老差额" ,"失业差额","工伤差额","生育差额",
				"医疗差额", "住房公积金差额", "其它差额", "服务费差额",
				"档案费差额","信息反馈"};
		// 使用自己写的Excel导出实现类把数据写入Excel
		ExcelService exl = new EmCommissionOutPayDifOutExcelImpl(file, sheetName, title,listEmCommissionOutPayDif);
	    exl.writeExcel();
	    Filedownload.save(file, "xls");
	}
	
	//导出未办反馈账单
	@Command("export")
	public void export(@BindingParam("exdate") EmCommissionOutPayModel exdate) throws SQLException, Exception{
		difbll.addEmCommissionOutPayDifInfo(exdate.getOwnmonth(),exdate.getEcop_cabc_id());
		System.out.println(exdate.getOwnmonth());
		System.out.println(exdate.getEcop_cabc_id());
		List<EmCommissionOutPayDifModel> listEmCommissionOutPayDif=difbll.getExportList(exdate.getOwnmonth(),exdate.getEcop_cabc_id(),""); 
		plyUtil p= new plyUtil();
		String path = "/../../EmCommissionOut/File/Download/";// 导出保存路径
		//创建当前日期
		Date date =new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "委托对帐差额记录表" + sdf.format(date) + ".xls";// 定义导出文件名称
		// 获取绝对路径
		path = p.getAbsolutePath(path, filename, this);
		// 创建文件
		File file = new File(path);
		file.createNewFile();
		String sheetName = "委托对帐差额记录表";// Excel表格名
		// 定义表头
		String[] title = { "序号", "城市","委托机构", "公司编号","公司名称","员工编号", "姓名",
				"身份证","所属月份",
				"养老差额" ,"失业差额","工伤差额","生育差额",
				"医疗差额", "住房公积金差额", "其它差额", "服务费差额",
				"档案费差额","总差额","信息反馈","","养老结转月" ,"失业结转月","工伤结转月","生育结转月",
				"医疗结转月", "住房公积金结转月", "其它结转月", "服务费结转月",
				"档案费结转月"};
		// 使用自己写的Excel导出实现类把数据写入Excel
		ExcelService exl = new EmCommissionOutPayDifOutExcelImpl(file, sheetName, title,listEmCommissionOutPayDif);
	    exl.writeExcel();
	    Filedownload.save(file, "xls");
	}
	//导入未办反馈账单信息反馈
	@Command("imporex")
	public  void importex(@BindingParam("imdate") EmCommissionOutPayModel imdate){
		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOutPayDif_ImportExcel.zul", null, null);
		wnd.doModal();
		
	}
	
	@Command("importExcel")
	@NotifyChange({ "fieldList", "spuList", "cityList", "coabList" })
	public void importExcel() {
		Window window = (Window) Executions.createComponents(
				"/EmCommissionOut/EmCommissionOutPayUpdate_ImportExcel.zul",
				null, null);
		window.doModal();
	}
	
	public ListModelList<EmCommissionOutPayModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(
			ListModelList<EmCommissionOutPayModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public ListModelList<EmCommissionOutPayModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(ListModelList<EmCommissionOutPayModel> citylist) {
		this.citylist = citylist;
	}

	public ListModelList<EmCommissionOutPayModel> getDepcompanylist() {
		return depcompanylist;
	}

	public void setDepcompanylist(
			ListModelList<EmCommissionOutPayModel> depcompanylist) {
		this.depcompanylist = depcompanylist;
	}

	public ListModelList<EmCommissionOutPayModel> getAutpaylist() {
		return autpaylist;
	}

	public void setAutpaylist(ListModelList<EmCommissionOutPayModel> autpaylist) {
		this.autpaylist = autpaylist;
	}

	public ListModelList<EmCommissionOutPayModel> getWt_paylist() {
		return wt_paylist;
	}

	public void setWt_paylist(ListModelList<EmCommissionOutPayModel> wt_paylist) {
		this.wt_paylist = wt_paylist;
	}
    
}
