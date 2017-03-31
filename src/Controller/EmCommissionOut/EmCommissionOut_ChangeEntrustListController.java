package Controller.EmCommissionOut;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import service.ExcelService;
import bll.EmCommissionOut.EmCommissionOut_ChangeEntrustBll;
import Model.EmCommissionOutChangeEntrustModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;
/**
 * 
 * @author 苏宏远
 * create 2016-06-20
 * 功能描述：年调上传数据查询
 */
public class EmCommissionOut_ChangeEntrustListController {
	//年调上传数据查询
	private List<EmCommissionOutChangeEntrustModel> entrustList = new ListModelList<>();
	//委托地
	private List<EmCommissionOutChangeEntrustModel> cityList =new ListModelList<>();
	//客服
	private List<EmCommissionOutChangeEntrustModel> clientList =new ListModelList<>();
	// 检索条件
	private String cid = "";
	private String gid= "";
	private String company = "";
	private String cpname= "";
	private String iscnum = "";
	private String city = "";
    private String coba_client="";
	private String ecyc_state="";
	//获取委托服务项目费用列表业务层
	EmCommissionOut_ChangeEntrustBll bll = new EmCommissionOut_ChangeEntrustBll();
	//年调上传数据查询页初始化数据
	public EmCommissionOut_ChangeEntrustListController(){
		try {
			bind();
		}catch(Exception e){
			e.printStackTrace();
			Messagebox.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}
	/**
	 * 初始化绑定
	 * 
	 */
	public void bind() {

		try {
			search();
			setCityList(bll.getCityList());
			cityList.add(0, null);
			setClientList(bll.getCobaClientList());
			clientList.add(0,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 列表检索
	 * @throws SQLException 
	 * 
	 */
	@Command("search")
	@NotifyChange({ "entrustList" })
	public void search() throws SQLException {
		StringBuilder strwhere = new StringBuilder();
		strwhere.append(" ");
		if (!cid.isEmpty()) {
			strwhere.append(" and a.cid =" + cid);
		}
		if (!company.isEmpty()) {

			strwhere.append(" and b.coba_company like '%" +company+ "%' ");
		}
		if (!cpname.isEmpty()) {
			strwhere.append(" and c.emba_name like '%" +cpname+ "%' ");
		}
		if (!gid.isEmpty()) {
			strwhere.append(" and a.gid =" + gid );
		}
		if (!iscnum.isEmpty()) {
			strwhere.append(" and c.emba_idcard ='"+iscnum+"'");
		}
		if(!ecyc_state.isEmpty()){
			strwhere.append(" and a.ecyc_state ="+ecyc_state);
		}
		if (!city.isEmpty()) {
			strwhere.append(" and e.city like '%" + city + "%' ");
		}
		if(!coba_client.isEmpty()){
			strwhere.append(" and b.coba_client like '%" + coba_client + "%' ");
		}
		entrustList = bll.getEmCommissionOut_ChangeEntrustList(strwhere.toString());
		
	}
	// 导出excel
	@Command("excelout")
	@NotifyChange({ "entrustList" })
	public void excelout(HttpServletResponse response) throws Exception {
		    plyUtil ply = new plyUtil();
			String path = "/../../EmCommissionOut/File/Templet/";
			String paths = "/EmCommissionOut/File/Download/";
			String absolutePath = FileOperate.getAbsolutePath();
			String filename = "out_ChangeEntrustList.xls";

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
				ExcelService exl = new changeEntrustListOutExcelImpl(solpath, absolutePath+ paths + newfilename, entrustList);
				exl.writeExcel();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			FileOperate.download(paths + newfilename);
	}
	public List<EmCommissionOutChangeEntrustModel> getEntrustList() {
		return entrustList;
	}
	public void setEntrustList(List<EmCommissionOutChangeEntrustModel> entrustList) {
		this.entrustList = entrustList;
	}
	
	public List<EmCommissionOutChangeEntrustModel> getCityList() {
		return cityList;
	}
	public void setCityList(List<EmCommissionOutChangeEntrustModel> cityList) {
		this.cityList = cityList;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCpname() {
		return cpname;
	}
	public void setCpname(String cpname) {
		this.cpname = cpname;
	}
	public String getIscnum() {
		return iscnum;
	}
	public void setIscnum(String iscnum) {
		this.iscnum = iscnum;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEcyc_state() {
		return ecyc_state;
	}
	public void setEcyc_state(String ecyc_state) {
		this.ecyc_state = ecyc_state;
	}
	public String getCoba_client() {
		return coba_client;
	}
	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}
	public List<EmCommissionOutChangeEntrustModel> getClientList() {
		return clientList;
	}
	public void setClientList(List<EmCommissionOutChangeEntrustModel> clientList) {
		this.clientList = clientList;
	}
	
}
