/**
 * @author Lee
 *
 */
package Controller.CoFinanceManage;

import impl.SystemControl.Data_PopedomIpml;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_SelBll;
import bll.CoFinanceManage.cfma_OperateBll;

import service.DataPopedomService;
import sun.misc.VM;

import Model.LoginModel;
import Util.CheckString;
import Util.DateStringChange;
import Util.MonthListUtil;

public class Cfma_SearchController {
	private List<String> ownmonthList; // 所属月份
	private List<LoginModel> clientList; // 客服
	private List<String> cwUserList;
	private String nowMonth;
	private int[] count;
	// 页面表单值
	private String ownmonth;
	private String client;
	private String cwUser;
	private String writeOffs;
	private String cocon;
	private String dept;
	private String billNo;
	private String personnelConfirm;
	private String financeConfirm;
	private boolean advance;
	private boolean perimbalance;
	private boolean finimbalance;
	private String ssdb;

	private String fownmonth;
	private String[] fownmonthList; // 未来台账所属月份
	private Integer fcid;

	public Cfma_SearchController() {
		DataPopedomService c_dps = new Data_PopedomIpml();
		clientList = c_dps.getdepLoginlist();
		nowMonth = DateStringChange.DatetoSting(new Date(), "yyyyMM");
		ownmonth = nowMonth;
		Cfma_SelBll bll = new Cfma_SelBll();
		count = bll.getOwnmonthCount(Integer.parseInt(nowMonth));
		ownmonthList = bll.getCoFinanceOwnmonth();
		cwUserList = bll.getCwLoginname();
		cwUser = "全部";
		client = "全部";
		writeOffs = "全部";
		dept = "全部";
		personnelConfirm = "全部";
		financeConfirm = "全部";
		advance = false;
		perimbalance = false;
		finimbalance = false;
		cocon = "";
		billNo = "";
		ssdb = "全部";

		fownmonthList = MonthListUtil.getMonthList(true, nowMonth, "f", 6);
	}

	@Command("search")
	public void search() {
		Map<String, String> map = new HashMap<String, String>();
		if (checkSearchBill()) {
			map.put("where", getSqlWhereByBill());
			Window window = (Window) Executions.createComponents(
					"Cfma_AllBillList.zul", null, map);
			window.doModal();
		} else if ("全部".equals(ownmonth)) {
			map.put("where", getSqlWhereByAll());
			Window window = (Window) Executions.createComponents(
					"Cfma_AllFinanceList.zul", null, map);
			window.doModal();
		} else {
			map.put("where", getSqlWhere());
			Window window = (Window) Executions.createComponents(
					"Cfma_OwnmonthFinanceList.zul", null, map);
			window.doModal();
		}
	}

	@Command
	public void update() {
		if (ownmonth != null && !ownmonth.equals("") && ownmonth.length() == 6
				&& CheckString.isNum(ownmonth)) {

		} else {
			Messagebox
					.show("请选择所属月份！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (cocon != null && !cocon.equals("") && cocon.length() == 4
				&& CheckString.isNum(cocon)) {

		} else {
			Messagebox
					.show("请输入公司编号！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		cfma_OperateBll bll = new cfma_OperateBll();
		bll.synchronousFinance(Integer.valueOf(cocon), Integer.valueOf(ownmonth));
	}

	// 按账单查询将条件转为查询语句
	private String getSqlWhereByBill() {
		StringBuilder sb = new StringBuilder();
		if (!"全部".equals(ownmonth)) {
			sb.append(" and cfmb.ownmonth=");
			sb.append(ownmonth);
		}
		if (!"全部".equals(client)) {
			sb.append(" and co.coba_client='");
			sb.append(client);
			sb.append("'");
		}
		if (!"全部".equals(dept)) {
			sb.append(" and co.coba_client in (select log_name from login where dep_id=(select dep_id from department where dep_name='");
			sb.append(dept);
			sb.append("'))");
		}
		if (cocon != null && !"".equals(cocon)) {
			if (CheckString.isNum(cocon)) {
				sb.append(" and co.CID=");
				sb.append(cocon);
			} else if (CheckString.isChinese(cocon)) {
				sb.append(" and co.coba_company like '%");
				sb.append(cocon);
				sb.append("%'");
			} else if (CheckString.isLetter(cocon)) {
				sb.append(" and (co.coba_company like '%");
				sb.append(cocon);
				sb.append("%' or co.coba_namespell like '%");
				sb.append(cocon);
				sb.append("%");
				sb.append("')");
			}
		}
		if (!"".equals(billNo) && billNo != null) {
			sb.append(" and cfmb_number like '%");
			sb.append(billNo);
			sb.append("%'");
		}
		if (!"全部".equals(writeOffs)) {
			switch (writeOffs) {
			case "未销账":
				sb.append(" and cfmb_WriteOffs=0");
				break;
			case "已销账":
				sb.append(" and cfmb_WriteOffs=1");
				break;
			case "已结转":
				sb.append(" and cfmb_WriteOffs=2");
				break;
			}
		}

		if (!"全部".equals(ssdb)) {
			switch (ssdb) {
			case "等于0":
				sb.append(" and cfmb_TotalPaidIn=0 ");
				break;
			case "小于应收":
				sb.append(" and cfma_PersonnelReceivable+cfma_FinanceReceivable>cfmb_TotalPaidIn  ");
				break;
			case "等于应收":
				sb.append(" and cfma_PersonnelReceivable+cfma_FinanceReceivable=cfma_totalPaidIn AND cfmb_TotalPaidIn>0 ");
				break;
			}
		}

		if (!"全部".equals(cwUser)) {
			sb.append(" and cfma_WriteOffsName='");
			sb.append(cwUser);
			sb.append("'");
		}
		if (!"全部".equals(personnelConfirm)) {
			switch (personnelConfirm) {
			case "已确认":
				sb.append(" and cfmb_PersonnelConfirm=1");
				break;
			case "未确认":
				sb.append(" and cfmb_PersonnelConfirm=0");
				break;
			}
		}
		if (!"全部".equals(financeConfirm)) {
			switch (financeConfirm) {
			case "已确认":
				sb.append(" and cfmb_FinanceConfirm=1");
				break;
			case "未确认":
				sb.append(" and cfmb_FinanceConfirm=0");
				break;
			}
		}
		return sb.toString();
	}

	// 按月份查询将条件转为查询语句
	private String getSqlWhere() {
		StringBuilder sb = new StringBuilder();
		if (!"全部".equals(ownmonth)) {
			sb.append(" and cfma.ownmonth=");
			sb.append(ownmonth);
		}
		if (!"全部".equals(client)) {
			sb.append(" and co.coba_client='");
			sb.append(client);
			sb.append("'");
		}
		if (!"全部".equals(dept)) {
			sb.append(" and co.coba_client in (select log_name from login where dep_id=(select dep_id from department where dep_name='");
			sb.append(dept);
			sb.append("'))");
		}

		if (!"全部".equals(ssdb)) {
			switch (ssdb) {
			case "等于0":
				sb.append(" and totalPaidIn=0 ");
				break;
			case "小于应收":
				sb.append(" and cfma_PersonnelReceivable+cfma_FinanceReceivable>totalPaidIn  ");
				break;
			case "等于应收":
				sb.append(" and cfma_PersonnelReceivable+cfma_FinanceReceivable=totalPaidIn AND totalPaidIn>0 ");
				break;
			}
		}

		if (cocon != null && !"".equals(cocon)) {
			if (CheckString.isNum(cocon)) {
				sb.append(" and co.CID=");
				sb.append(cocon);
			} else if (CheckString.isChinese(cocon)) {
				sb.append(" and co.coba_company like '%");
				sb.append(cocon);
				sb.append("%'");
			} else if (CheckString.isLetter(cocon)) {
				sb.append(" and (co.coba_company like '%");
				sb.append(cocon);
				sb.append("%' or co.coba_namespell like '%");
				sb.append(cocon);
				sb.append("%");
				sb.append("')");
			}
		}
		return sb.toString();
	}

	@Command("financeforf")
	public void financeforf() {
		if (fcid == null || fcid == 0) {
			Messagebox
					.show("请填写公司编号！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

		else if (fownmonth == null) {
			Messagebox
					.show("请选择所属月份！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show("账单生成成功，请查询！", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}

	}

	// 按全部月份查询将条件转为查询语句
	private String getSqlWhereByAll() {
		StringBuilder sb = new StringBuilder();
		if (!"全部".equals(client)) {
			sb.append(" and co.coba_client='");
			sb.append(client);
			sb.append("'");
		}

		if (!"全部".equals(dept)) {
			sb.append(" and co.coba_client in (select log_name from login where dep_id=(select dep_id from department where dep_name='");
			sb.append(dept);
			sb.append("'))");
		}
		if (cocon != null && !"".equals(cocon)) {
			if (CheckString.isNum(cocon)) {
				sb.append(" and co.CID=");
				sb.append(cocon);
			} else if (CheckString.isChinese(cocon)) {
				sb.append(" and co.coba_company like '%");
				sb.append(cocon);
				sb.append("%'");
			} else if (CheckString.isLetter(cocon)) {
				sb.append(" and (co.coba_company like '%");
				sb.append(cocon);
				sb.append("%' or co.coba_namespell like '%");
				sb.append(cocon);
				sb.append("')");
			}
		}
		return sb.toString();
	}

	// 判断是否检索账单
	private boolean checkSearchBill() {
		boolean bool = false;
		if (!"".equals(billNo) && billNo != null) {
			bool = true;
		} else if (!"全部".equals(writeOffs)) {
			bool = true;
		} else if (!"全部".equals(cwUser)) {
			bool = true;
		} else if (!"全部".equals(personnelConfirm)) {
			bool = true;
		} else if (!"全部".equals(financeConfirm)) {
			bool = true;
		}
		return bool;
	}

	public String getSsdb() {
		return ssdb;
	}

	public void setSsdb(String ssdb) {
		this.ssdb = ssdb;
	}

	public String getCwUser() {
		return cwUser;
	}

	public void setCwUser(String cwUser) {
		this.cwUser = cwUser;
	}

	public List<String> getCwUserList() {
		return cwUserList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getWriteOffs() {
		return writeOffs;
	}

	public void setWriteOffs(String writeOffs) {
		this.writeOffs = writeOffs;
	}

	public String getCocon() {
		return cocon;
	}

	public void setCocon(String cocon) {
		this.cocon = cocon;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPersonnelConfirm() {
		return personnelConfirm;
	}

	public void setPersonnelConfirm(String personnelConfirm) {
		this.personnelConfirm = personnelConfirm;
	}

	public String getFinanceConfirm() {
		return financeConfirm;
	}

	public void setFinanceConfirm(String financeConfirm) {
		this.financeConfirm = financeConfirm;
	}

	public boolean isAdvance() {
		return advance;
	}

	public void setAdvance(boolean advance) {
		this.advance = advance;
	}

	public boolean isPerimbalance() {
		return perimbalance;
	}

	public void setPerimbalance(boolean perimbalance) {
		this.perimbalance = perimbalance;
	}

	public boolean isFinimbalance() {
		return finimbalance;
	}

	public void setFinimbalance(boolean finimbalance) {
		this.finimbalance = finimbalance;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public List<LoginModel> getClientList() {
		return clientList;
	}

	public String getNowMonth() {
		return nowMonth;
	}

	public int[] getCount() {
		return count;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getFownmonth() {
		return fownmonth;
	}

	public void setFownmonth(String fownmonth) {
		this.fownmonth = fownmonth;
	}

	public String[] getFownmonthList() {
		return fownmonthList;
	}

	public void setFownmonthList(String[] fownmonthList) {
		this.fownmonthList = fownmonthList;
	}

	public Integer getFcid() {
		return fcid;
	}

	public void setFcid(Integer fcid) {
		this.fcid = fcid;
	}

}