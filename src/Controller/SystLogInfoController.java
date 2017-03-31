package Controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;

import Model.SystLogModel;
import bll.SystemControl.SystLogInfoBll;

public class SystLogInfoController {
	SystLogInfoBll bll=new SystLogInfoBll();
	private List<SystLogModel> logList= new ListModelList<SystLogModel>();
	// 检索条件
	private String cid = "";
	private String gid= "";
	private String tid = "";
	private String addname= "";
	private Date ebco_maturity_date;
	
	public SystLogInfoController() throws SQLException {
		     search();
	}
	
	/**
	 * 列表检索
	 * @throws SQLException 
	 * 
	 */
	@Command("search")
	@NotifyChange({"logList"})
	public void search() throws SQLException {
		StringBuilder strwhere = new StringBuilder();
		strwhere.append(" ");
		if (!cid.isEmpty()) {
			strwhere.append(" and cid =" + cid);
		}
		if (!gid.isEmpty()) {
			strwhere.append(" and gid =" + gid );
		}
		if (!tid.isEmpty()) {
			strwhere.append(" and tid ="+tid);
		}
		if(!addname.isEmpty()){
			strwhere.append(" and addname like '%"+addname+"%' ");
		}
		if (ebco_maturity_date!=null) {
			strwhere.append(" and convert(varchar(10),addtime ,120)=convert(varchar(10),'"+this.DatetoSting(ebco_maturity_date)+"',120)");
		}
		
		logList = bll.getSystLogList(strwhere.toString());
		
	}

	public List<SystLogModel> getLogList() {
		return logList;
	}

	public void setLogList(List<SystLogModel> logList) {
		this.logList = logList;
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

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public Date getEbco_maturity_date() {
		return ebco_maturity_date;
	}

	public void setEbco_maturity_date(Date ebco_maturity_date) {
		this.ebco_maturity_date = ebco_maturity_date;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String Date = sdf.format(d);
		return Date;
		}
}
