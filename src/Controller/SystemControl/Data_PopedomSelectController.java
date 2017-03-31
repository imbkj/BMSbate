package Controller.SystemControl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Messagebox;

import impl.SystemControl.Data_PopedomIpml;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import service.DataPopedomService;
import Model.LoginModel;
import Model.DataPopedomModel;


public class Data_PopedomSelectController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map map=new HashMap();
	private String log_name="";
	private String cocid="";
	
	private String coshortname="";
	private String coclinet="";
	private Boolean sel;
	private Boolean edi;
	private Boolean del;
	private String coaddname="";
	public List<LoginModel> loginlist = new ArrayList<LoginModel>();
	public List<DataPopedomModel> dataPopedomlist = new ArrayList<DataPopedomModel>();
	
	DataPopedomService Datap =new Data_PopedomIpml("0",cocid,log_name,coshortname,coclinet,sel,edi,del,coaddname);
	

	
	DataPopedomService d =new  Data_PopedomIpml("112","法务");
	public Data_PopedomSelectController() throws SQLException{
		
		try{
			//获取注册用户列表
			loginlist=d.getLoginlist();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	//查询客服数据权限列表
	@Command
	@NotifyChange("dataPopedomlist")
	 public void search() throws SQLException {
		//System.out.print(log_name);
		if (!log_name.isEmpty())
		{
		try{
			DataPopedomService Datap =new Data_PopedomIpml("1",cocid,log_name,coshortname,coclinet,sel,edi,del,coaddname);
			dataPopedomlist=Datap.getPopedomlist();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		}
		else
		{
			 Messagebox.show("请先选择查询对象!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

 	}
	
	public String  getCocid() {
		return cocid;
	}

	public void setCocid(String cocid) {
		this.cocid = cocid;
	}

	public String getCoshortname() {
		return coshortname;
	}

	public void setCoshortname(String coshortname) {
		this.coshortname = coshortname;
	}

	public String getCoclinet() {
		return coclinet;
	}

	public void setCoclinet(String coclinet) {
		this.coclinet = coclinet;
	}


	public Boolean getSel() {
		return sel;
	}
	public void setSel(Boolean sel) {
		this.sel = sel;
	}
	public Boolean getEdi() {
		return edi;
	}
	public void setEdi(Boolean edi) {
		this.edi = edi;
	}
	public Boolean getDel() {
		return del;
	}
	public void setDel(Boolean del) {
		this.del = del;
	}
	public String getCoaddname() {
		return coaddname;
	}

	public void setCoaddname(String coaddname) {
		this.coaddname = coaddname;
	}

	public List<LoginModel> getLoginlist() {

		return loginlist;
	}
	
	public List<DataPopedomModel> getDataPopedomlist() {
		return dataPopedomlist;
	}

	public void setDataPopedomlist(List<DataPopedomModel> dataPopedomlist) {
		dataPopedomlist = dataPopedomlist;
	}
	public void setLoginlist(List<LoginModel> loginlist) {
		this.loginlist = loginlist;
	}
	public String getLog_name() {
		return log_name;
	}
	public void setLog_name(String log_name) {
		this.log_name = log_name;
	}
	
	
}
