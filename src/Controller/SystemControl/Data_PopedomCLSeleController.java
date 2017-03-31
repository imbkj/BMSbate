package Controller.SystemControl;

import impl.SystemControl.Data_PopedomIpml;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Messagebox;

import service.DataPopedomService;
import Model.CoLatencyClientModel;
import Model.DataPopedomModel;
import Model.LoginModel;

/**
 * @author zmj
 *
 */
public class Data_PopedomCLSeleController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private Map map=new HashMap();
	private String log_name="";
	private String cocid="";
	private String cola_id="";
	
	private String cola_company="";
	private String cola_follower="";
	private Boolean sel;
	private Boolean edi;
	private Boolean del;
	private String cola_addname="";
	public List<LoginModel> loginlist = new ArrayList<LoginModel>();
	public List<CoLatencyClientModel> dataPopedomlist = new ArrayList<CoLatencyClientModel>();
	
	DataPopedomService Datap =new Data_PopedomIpml("0",cocid,log_name,cola_company,cola_follower,sel,edi,del,cola_addname);
	

	
	DataPopedomService d =new  Data_PopedomIpml("7","","","","","","");
	
	public Data_PopedomCLSeleController() throws SQLException{
		
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
			DataPopedomService Datap =new Data_PopedomIpml("0",cocid,log_name,cola_company,cola_follower,sel,edi,del,cola_addname);
			dataPopedomlist=Datap.getPopedomCllist();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		}
		else
		{
			 Messagebox.show("请先选择查询对象!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

 	}
	public String getCola_id() {
		return cola_id;
	}

	public void setCola_id(String cola_id) {
		this.cola_id = cola_id;
	}
	
	public String  getCocid() {
		return cocid;
	}

	public void setCocid(String cocid) {
		this.cocid = cocid;
	}
	public String getCola_company() {
		return cola_company;
	}

	public void setCola_company(String cola_company) {
		this.cola_company = cola_company;
	}

	public String getCola_follower() {
		return cola_follower;
	}

	public void setCola_follower(String cola_follower) {
		this.cola_follower = cola_follower;
	}

	public String getCola_addname() {
		return cola_addname;
	}

	public void setCola_addname(String cola_addname) {
		this.cola_addname = cola_addname;
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

	public List<LoginModel> getLoginlist() {

		return loginlist;
	}
	
	public List<CoLatencyClientModel> getDataPopedomlist() {
		return dataPopedomlist;
	}

	public void setDataPopedomlist(List<CoLatencyClientModel> dataPopedomlist) {
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
