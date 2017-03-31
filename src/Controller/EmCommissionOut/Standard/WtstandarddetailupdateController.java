package Controller.EmCommissionOut.Standard;

import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.WtServiceStandardrelationModel;
import Util.UserInfo;
import bll.EmCommissionOut.Standard.WtServiceStandardBll;

public class WtstandarddetailupdateController {
	private CoBaseModel com = new CoBaseModel();
	private WtServiceStandardrelationModel m =new WtServiceStandardrelationModel();
	
	
	
	// 社保、公积金
	private List<String> htxztypeList = new ListModelList<>();
	private List<String> ldhtqdtypeList = new ListModelList<>();
	private List<String> ldhtbbtypeList = new ListModelList<>();
	private List<String> ygtypeList = new ListModelList<>();
	private List<String> dafeetypeList = new ListModelList<>();
	private List<String> bcgjjtypeList = new ListModelList<>();
	private List<String> sybxfwtypeList = new ListModelList<>();
	private List<String> bcfltypeList = new ListModelList<>();
	private List<String> dfgztypeList = new ListModelList<>();
	private List<String> dbgstyptList = new ListModelList<>();
	private List<String> sbzhtypeList = new ListModelList<>();
	private List<String> sbfeetypeList = new ListModelList<>();
	private List<String> gjjzhtypeList = new ListModelList<>();
	private List<String> gjjfeetypeList = new ListModelList<>();
	
	Integer wtssid = 0;
	
	WtServiceStandardBll bll = new WtServiceStandardBll();
	
	public WtstandarddetailupdateController()
	{
		
		wtssid = Integer.parseInt(Executions.getCurrent().getArg().get("wtssid")
				.toString());
		//setCom(bll.getCobaInfo(cid));
		
		
	}
	
	
	

	public CoBaseModel getCom() {
		return com;
	}

	public void setCom(CoBaseModel com) {
		this.com = com;
	}

	public WtServiceStandardrelationModel getM() {
		return m;
	}

	public void setM(WtServiceStandardrelationModel m) {
		this.m = m;
	}





	public List<String> getHtxztypeList() {
		return htxztypeList;
	}





	public void setHtxztypeList(List<String> htxztypeList) {
		this.htxztypeList = htxztypeList;
	}





	public List<String> getLdhtqdtypeList() {
		return ldhtqdtypeList;
	}





	public void setLdhtqdtypeList(List<String> ldhtqdtypeList) {
		this.ldhtqdtypeList = ldhtqdtypeList;
	}





	public List<String> getLdhtbbtypeList() {
		return ldhtbbtypeList;
	}





	public void setLdhtbbtypeList(List<String> ldhtbbtypeList) {
		this.ldhtbbtypeList = ldhtbbtypeList;
	}





	public List<String> getYgtypeList() {
		return ygtypeList;
	}





	public void setYgtypeList(List<String> ygtypeList) {
		this.ygtypeList = ygtypeList;
	}





	public List<String> getDafeetypeList() {
		return dafeetypeList;
	}





	public void setDafeetypeList(List<String> dafeetypeList) {
		this.dafeetypeList = dafeetypeList;
	}





	public List<String> getBcgjjtypeList() {
		return bcgjjtypeList;
	}





	public void setBcgjjtypeList(List<String> bcgjjtypeList) {
		this.bcgjjtypeList = bcgjjtypeList;
	}





	public List<String> getSybxfwtypeList() {
		return sybxfwtypeList;
	}





	public void setSybxfwtypeList(List<String> sybxfwtypeList) {
		this.sybxfwtypeList = sybxfwtypeList;
	}





	public List<String> getBcfltypeList() {
		return bcfltypeList;
	}





	public void setBcfltypeList(List<String> bcfltypeList) {
		this.bcfltypeList = bcfltypeList;
	}





	public List<String> getDfgztypeList() {
		return dfgztypeList;
	}





	public void setDfgztypeList(List<String> dfgztypeList) {
		this.dfgztypeList = dfgztypeList;
	}





	public List<String> getDbgstyptList() {
		return dbgstyptList;
	}





	public void setDbgstyptList(List<String> dbgstyptList) {
		this.dbgstyptList = dbgstyptList;
	}





	public List<String> getSbzhtypeList() {
		return sbzhtypeList;
	}





	public void setSbzhtypeList(List<String> sbzhtypeList) {
		this.sbzhtypeList = sbzhtypeList;
	}





	public List<String> getSbfeetypeList() {
		return sbfeetypeList;
	}





	public void setSbfeetypeList(List<String> sbfeetypeList) {
		this.sbfeetypeList = sbfeetypeList;
	}





	public List<String> getGjjzhtypeList() {
		return gjjzhtypeList;
	}





	public void setGjjzhtypeList(List<String> gjjzhtypeList) {
		this.gjjzhtypeList = gjjzhtypeList;
	}





	public List<String> getGjjfeetypeList() {
		return gjjfeetypeList;
	}





	public void setGjjfeetypeList(List<String> gjjfeetypeList) {
		this.gjjfeetypeList = gjjfeetypeList;
	}
	
	
	
}
