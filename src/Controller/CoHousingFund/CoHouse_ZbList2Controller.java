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
import bll.CoHousingFund.CoHousingFundZbBll;
import Model.CoHousingFundZbChangeModel;
import Model.LoginModel;
import Util.RegexUtil;
import Util.SingleBllFactory;

/**
 * 专办员后到控制
 * 
 * @author Administrator
 * 
 */
public class CoHouse_ZbList2Controller {

	private Window window;

	private String queryCondition; // 查询条件
	private List<CoHousingFundZbChangeModel> chfmList;
	private List<CoHousingFundZbChangeModel> schfmList = new ArrayList<CoHousingFundZbChangeModel>();
	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private String cid = "";
	private String coName = "";
	private String cogjjid = "";
	private String addtype = "";

	public CoHouse_ZbList2Controller() {
		chfmList = cfzb.getChfzListChange();
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
			for (CoHousingFundZbChangeModel m : chfmList) {
				if (!cid.isEmpty()) {
					if (!RegexUtil.isExists(cid, m.getCid())) {
						continue;
					}
				}
				if (!coName.isEmpty()) {
					if (!RegexUtil.isExists(coName, m.getCfzc_cohf_company())) {
						continue;
					}
				}
				if (!cogjjid.isEmpty()) {
					if (!RegexUtil.isExists(cogjjid, m.getCfzc_cohf_houseid())) {
						continue;
					}
				}
				if (!addtype.isEmpty()) {
					if (!RegexUtil.isExists(addtype, m.getCfzc_addtype())) {
						continue;
					}
				}
				if (status != null) {
					if (!RegexUtil.isExists(status, m.getCfzc_statusString())) {
						continue;
					}
				}
				schfmList.add(m);
			}
		}
	}

	// 小信封
	@Command("msg")
	@NotifyChange("schfmList")
	public void msg(@BindingParam("id") int id,
			@BindingParam("addname") String addname) {
		LoginDal d = new LoginDal();

		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
		map.put("id", id);// 业务表id
		map.put("tablename", "CoHousingFundZbChange");// 业务表名
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
		schfmList = cfzb.getChfzListChange();
	}

	/**
	 * 专办员前到录入的信息页面
	 */
	@Command
	public void addZbInfo() {
		window = (Window) Executions.createComponents("House_Zb_Add_Check.zul",
				null, null);
		window.doModal();
	}

	/**
	 * 退回操作
	 * 
	 * 在申报未完成之前都可以退回到前道 退回状态改为4
	 */
	@Command
	@NotifyChange("schfmList")
	public void back(@BindingParam("a") CoHousingFundZbChangeModel m) {
		Map<String, CoHousingFundZbChangeModel> map = new HashMap<String, CoHousingFundZbChangeModel>();
		map.put("cfzc", m);
		window = (Window) Executions.createComponents(
				"/CoHousingFund/House_zb_back.zul", null, map);
		window.doModal();

		schfmList = cfzb.getChfzListChange();
	}

	/**
	 * 专办员后到 combobox 弹出操作页面
	 * 
	 * @param status
	 */
	@Command
	@NotifyChange("schfmList")
	public void contro(@BindingParam("a") CoHousingFundZbChangeModel m) {
		String url = "";
		Map<String, CoHousingFundZbChangeModel> map = new HashMap<String, CoHousingFundZbChangeModel>();
		map.put("cfzc", m);
		if (!"".equals(m.getCfzc_addtype())
				&& m.getCfzc_addtype().equals("新增专办员")) {
			url = "/CoHousingFund/House_Zb_Add_Check.zul";
		} else if (!"".equals(m.getCfzc_addtype())
				&& m.getCfzc_addtype().equals("注销专办员")) {
			url = "/CoHousingFund/Co_House_Zb_Del_Check.zul";
		} else if (!"".equals(m.getCfzc_addtype())
				&& m.getCfzc_addtype().equals("修改专办员信息")) {
			url = "/CoHousingFund/Co_House_Zb_ChangeCheck.zul";
		}
		if (!url.equals("")) {
			window = (Window) Executions.createComponents(url, null, map);
			window.doModal();
		}
		schfmList = cfzb.getChfzListChange();
	}

	/**
	 * 已经申报成功查看页面
	 */
	@Command
	public void querycompelet(@BindingParam("a") CoHousingFundZbChangeModel m) {
		Map<String, CoHousingFundZbChangeModel> map = new HashMap<String, CoHousingFundZbChangeModel>();
		map.put("cfzc", m);
		String url = "";
		if (!"".equals(m.getCfzc_addtype())
				&& m.getCfzc_addtype().equals("新增专办员")) {
			url = "/CoHousingFund/House_Zb_Add_Check.zul";
		} else if (!"".equals(m.getCfzc_addtype())
				&& m.getCfzc_addtype().equals("注销专办员")) {
			map.put("cfzc", m);
			url = "/CoHousingFund/Co_House_Zb_Del_Check.zul";
		} else if (!"".equals(m.getCfzc_addtype())
				&& m.getCfzc_addtype().equals("修改专办员信息")) {
			map.put("cfzc", m);
			url = "/CoHousingFund/Co_House_Zb_ChangeCheck.zul";
		}
		if (!url.equals("")) {
			window = (Window) Executions.createComponents(url, null, map);
			window.doModal();
		}
	}

	public List<CoHousingFundZbChangeModel> getSchfmList() {
		return schfmList;
	}

	public void setSchfmList(List<CoHousingFundZbChangeModel> schfmList) {
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

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	public List<CoHousingFundZbChangeModel> getChfmList() {
		return chfmList;
	}

	public void setChfmList(List<CoHousingFundZbChangeModel> chfmList) {
		this.chfmList = chfmList;
	}

}
