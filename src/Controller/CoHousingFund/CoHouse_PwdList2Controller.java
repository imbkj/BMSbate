package Controller.CoHousingFund;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import dal.LoginDal;

import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbChangeModel;
import Model.LoginModel;
import Util.RegexUtil;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFund_PwdBll;

/**
 * 密钥后到控制
 * 
 * @author Administrator
 * 
 */
public class CoHouse_PwdList2Controller {

	private Window window;
	private List<CoHousingFundPwdChangeModel> chfmList;
	private List<CoHousingFundPwdChangeModel> schfmList = new ArrayList<CoHousingFundPwdChangeModel>();
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();

	private String cid = "";
	private String coName = "";
	private String cogjjid = "";
	private String addtype = "";

	public CoHouse_PwdList2Controller() {

		chfmList = cfpb.getPwdListChange();
		search(null);
		
	}

	/**
	 * 按条件查询申报信息
	 */
	@Command
	@NotifyChange("schfmList")
	public void search(@BindingParam("status") String status) {
		if (chfmList != null && chfmList.size() > 0) {
			schfmList.clear();
			for (CoHousingFundPwdChangeModel m : chfmList) {
				if (!cid.isEmpty()) {
					if (!RegexUtil.isExists(cid, m.getCid())) {
						continue;
					}
				}
				if (!cogjjid.isEmpty()) {
					if (!RegexUtil.isExists(cogjjid, m.getCfpc_cohf_houseid())) {
						continue;
					}
				}
				if (!coName.isEmpty()) {
					if (!RegexUtil.isExists(coName, m.getCfpc_cohf_company())) {
						continue;
					}
				}
				if (!addtype.isEmpty()) {
					if (!RegexUtil.isExists(addtype, m.getCfpc_addtype())) {
						continue;
					}
				}
				if (status != null) {
					if (!RegexUtil.isExists(status, m.getCfpc_statusString())) {
						continue;
					}
				}
				schfmList.add(m);
			}
		}

	}

	/**
	 * 退回操作
	 * 
	 * 在申报未完成之前都可以退回到前道 退回状态改为4
	 */
	@Command
	@NotifyChange("schfmList")
	public void back(@BindingParam("a") CoHousingFundPwdChangeModel m) {
		Map<String, CoHousingFundPwdChangeModel> map = new HashMap<String, CoHousingFundPwdChangeModel>();
		map.put("cfpc", m);
		window = (Window) Executions.createComponents(
				"/CoHousingFund/House_pwd_back.zul", null, map);
		window.doModal();
		schfmList = cfpb.getPwdListChange();
	}

	// 小信封
	@Command("msg")
	@NotifyChange("schfmList")
	public void msg(@BindingParam("id") int id,
			@BindingParam("addname") String addname) {
		LoginDal d = new LoginDal();

		Map map = new HashMap<>();
		map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
		map.put("id", id);// 业务表id
		map.put("tablename", "CoHousingFundPwdChange");// 业务表名
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(addname);
		m.setLog_id(d.getUserIDByname(addname));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		// 刷新
		schfmList = cfpb.getPwdListChange();
	}

	@Command
	@NotifyChange("schfmList")
	public void querycompelet(@BindingParam("m") CoHousingFundPwdChangeModel m) {
		Map<String, CoHousingFundPwdChangeModel> map = new HashMap<String, CoHousingFundPwdChangeModel>();
		map.put("cfpc", m);
		System.out.print(m.getNumberAndName());
		String url = "";
		if (!"".equals(m.getCfpc_addtype())
				&& m.getCfpc_addtype().equals("申请数字证书")) {
			url = "/CoHousingFund/Co_House_pwd_ApplyCheck.zul";
		} else if (!"".equals(m.getCfpc_addtype())
				&& m.getCfpc_addtype().equals("密钥专办员变更")) {
			url = "/CoHousingFund/Co_House_pwd_ChangeCheck.zul";
		} else if (!"".equals(m.getCfpc_addtype())
				&& m.getCfpc_addtype().equals("数字证书续期")) {
			url = "/CoHousingFund/Co_House_Pwd_Renewal.zul";
		}
		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		schfmList = cfpb.getPwdListChange();
	}

	/**
	 * combobox 选择弹出操作页面
	 * 
	 * @param contro
	 */
	@Command
	@NotifyChange("schfmList")
	public void contro(@BindingParam("m") CoHousingFundPwdChangeModel m) {

		Map<String, CoHousingFundPwdChangeModel> map = new HashMap<String, CoHousingFundPwdChangeModel>();
		map.put("cfpc", m);

		String url = "";
		if (!"".equals(m.getCfpc_addtype())
				&& m.getCfpc_addtype().equals("申请数字证书")) {
			url = "/CoHousingFund/Co_House_pwd_ApplyCheck.zul";
		} else if (!"".equals(m.getCfpc_addtype())
				&& m.getCfpc_addtype().equals("密钥专办员变更")) {
			url = "/CoHousingFund/Co_House_pwd_ChangeCheck.zul";
		} else if (!"".equals(m.getCfpc_addtype())
				&& m.getCfpc_addtype().equals("数字证书续期")) {
			url = "/CoHousingFund/Co_House_Pwd_Renewal.zul";
		}
		window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		schfmList = cfpb.getPwdListChange();

	}

	// /**
	// * 弹出前到录入信息页面
	// */
	// @Command
	// public void addPwdInfo() {
	// window = (Window) Executions.createComponents(
	// "House_Pwd_Add_Check.zul", null, null);
	// window.doModal();
	// }

	public List<CoHousingFundPwdChangeModel> getChfmList() {
		return chfmList;
	}

	public List<CoHousingFundPwdChangeModel> getSchfmList() {
		return schfmList;
	}

	public void setSchfmList(List<CoHousingFundPwdChangeModel> schfmList) {
		this.schfmList = schfmList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getCogjjid() {
		return cogjjid;
	}

	public void setCogjjid(String cogjjid) {
		this.cogjjid = cogjjid;
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public void setChfmList(List<CoHousingFundPwdChangeModel> chfmList) {
		this.chfmList = chfmList;
	}

}
