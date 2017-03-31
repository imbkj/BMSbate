package Controller.EmBaseCompact;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import Model.EmbaseContractDetailModel;
import Util.FileOperate;
import bll.EmBaseCompact.EmbaseContractDetail_ShowBll;

public class EmbaseContractDetail_ShowController {
	
	private EmbaseContractDetail_ShowBll bll =new EmbaseContractDetail_ShowBll();
	
	private String coba_company="";
	private String emba_name="";
	private String emba_idcard="";
	private String coba_client="";
	private String puof_addname="";
	
	private List<String> companyList= null;
	private List<String> nameList= null;
	private List<String> idcardList= null;
	private List<String> clientList= null;
	private List<String> addnameList= null;
	
	private List<EmbaseContractDetailModel> list = new ArrayList<EmbaseContractDetailModel>();
	
	public EmbaseContractDetail_ShowController() {
		 companyList= bll.getCompanyList();
		 nameList= bll.getNameList();
		 idcardList= bll.getIdcardList();
		 clientList= bll.getClientList();
		 addnameList= bll.getAddnameList();
		 serch();
	}
	@Command
	@NotifyChange("list")
	public void serch(){
		StringBuilder strsql= new StringBuilder();
		if(!coba_company.equals("")){
			strsql.append(" and co.coba_company like '%"+coba_company+"%'" );
		}
		if(!emba_name.equals("")){
			strsql.append(" and em.emba_name like '%"+emba_name+"%'" );
		}
		if(!emba_idcard.equals("")){
			strsql.append(" and em.emba_idcard='"+emba_idcard+"'" );
		}
		if(!coba_client.equals("")){
			strsql.append(" and co.coba_client='"+coba_client+"'" );
		}
		if(!puof_addname.equals("")){
			strsql.append(" and a.puof_addname='"+puof_addname+"'" );
		}
		strsql.append(" ");
		try {
			list = bll.getEmbaseContractDetail(strsql.toString());//查询
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//详情
	@Command("detail")
	public void detail(@BindingParam("m") EmbaseContractDetailModel m){
		if(!m.getPuof_url().equals("")&&m.getPuof_url()!=null){
			String fileName= m.getPuof_url();
			String fileType= fileName.substring(fileName.indexOf(".")+1, fileName.length());
			System.out.println(fileType);
			boolean b= FileOperate.existFile("OfficeFile/DownLoad/EmBaseCompact/",  fileName);//检验文件目录下是否存在该文件
			if(b){
				String absoultPath= FileOperate.getAbsolutePath();//获取根目录
				String path= absoultPath+"OfficeFile/DownLoad/EmBaseCompact/"+fileName;
				File file =new File(path);
				try {
					Filedownload.save(file, fileType);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}else{
				Messagebox.show("该文件目录下不存在该文件！", "操作提示", Messagebox.OK,Messagebox.ERROR);
				return;
			}
			
		}else{
			Messagebox.show("文档不存在！", "操作提示", Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
	}
	//审核
	@Command("approve")
	public void approve(@BindingParam("m") EmbaseContractDetailModel m){
		int a= bll.appCommont(m.getPuof_id());
		if(a>0){
			Messagebox.show("审核成功！", "操作提示", Messagebox.OK,Messagebox.NONE);
			chengSerch();
			return;
		}else{
			Messagebox.show("审核失败！", "操作提示", Messagebox.OK,Messagebox.ERROR);
			return;
		}
	}
	
	public void chengSerch(){
		StringBuilder strsql= new StringBuilder();
		if(!coba_company.equals("")){
			strsql.append(" and co.coba_company like '%"+coba_company+"%'" );
		}
		if(!emba_name.equals("")){
			strsql.append(" and em.emba_name like '%"+emba_name+"%'" );
		}
		if(!emba_idcard.equals("")){
			strsql.append(" and em.emba_idcard='"+emba_idcard+"'" );
		}
		if(!coba_client.equals("")){
			strsql.append(" and co.coba_client='"+coba_client+"'" );
		}
		if(!puof_addname.equals("")){
			strsql.append(" and a.puof_addname='"+puof_addname+"'" );
		}
		strsql.append(" ");
		try {
			list.clear();
			list.addAll(bll.getEmbaseContractDetail(strsql.toString()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getCoba_company() {
		return coba_company;
	}
	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}
	public String getEmba_name() {
		return emba_name;
	}
	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}
	public String getEmba_idcard() {
		return emba_idcard;
	}
	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}
	public String getCoba_client() {
		return coba_client;
	}
	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}
	public String getPuof_addname() {
		return puof_addname;
	}
	public void setPuof_addname(String puof_addname) {
		this.puof_addname = puof_addname;
	}
	public List<String> getCompanyList() {
		return companyList;
	}
	public void setCompanyList(List<String> companyList) {
		this.companyList = companyList;
	}
	public List<String> getNameList() {
		return nameList;
	}
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}
	public List<String> getIdcardList() {
		return idcardList;
	}
	public void setIdcardList(List<String> idcardList) {
		this.idcardList = idcardList;
	}
	public List<String> getClientList() {
		return clientList;
	}
	public void setClientList(List<String> clientList) {
		this.clientList = clientList;
	}
	public List<String> getAddnameList() {
		return addnameList;
	}
	public void setAddnameList(List<String> addnameList) {
		this.addnameList = addnameList;
	}
	public List<EmbaseContractDetailModel> getList() {
		return list;
	}
	public void setList(List<EmbaseContractDetailModel> list) {
		this.list = list;
	}
	
	
}
