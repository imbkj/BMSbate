package Controller.EmSalary;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_SalarySelBll;

import Model.EmSalaryPayInfoModel;
import Util.CheckString;
import Util.DateStringChange;
import Util.RegexUtil;
import Util.UserInfo;

public class EmSalary_CompanyPayInfoController {
	private List<String> ownmonthList;
	private List<String> gzAddnameList;
	private String ownmonth;
	private String gzAddname;
	private String company;
	private EmSalary_SalarySelBll bll;
	private String dataOwnmonth = "";
	private List<EmSalaryPayInfoModel> infoList;
	private List<EmSalaryPayInfoModel> winInfoList;
	private String ifDiff;

	public EmSalary_CompanyPayInfoController() {
		bll = new EmSalary_SalarySelBll();
		ownmonth = DateStringChange.DatetoSting(new Date(), "yyyyMM");
		gzAddname = UserInfo.getUsername();
		company = "";
		ownmonthList = bll.getOwnmonth();
		gzAddnameList = bll.getGzUser();
	}

	// 弹出工资明细
	@Command("getinfo")
	public void getinfo(@BindingParam("model") EmSalaryPayInfoModel epmodel) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", epmodel.getCid());
		map.put("ownmonth", epmodel.getOwnmonth());
		Window window = new Window();
		window.detach();
		window = (Window) Executions.createComponents("EmSalary_InfoList.zul",
				null, map);
		window.doModal();

	}

	// 弹出工资收款明细
	@Command("getGatheringinfo")
	public void getGatheringinfo(
			@BindingParam("model") EmSalaryPayInfoModel epmodel) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", epmodel.getCid());
		map.put("ownmonth", epmodel.getOwnmonth());
		Window window = new Window();
		window.detach();
		window = (Window) Executions.createComponents("EmSalary_GatheringList.zul",
				null, map);
		window.doModal();

	}

	// 检索数据
	@Command("search")
	public void search() {
		try {
			//if (!dataOwnmonth.equals(ownmonth)) {
				infoList = bll.getEmSalaryPayInfo(Integer.parseInt(ownmonth));
				dataOwnmonth = ownmonth;
			//}

			// 检索公司信息
			if (company != null && !"".equals(company)) {
				winInfoList = searchCompany();
			} else {
				winInfoList = infoList;
			}

			// 检索薪酬负责人
			if (gzAddname != null && !"".equals(gzAddname)) {
				winInfoList = searchGzAddname();
			}

			// 检索是否有差额
			if ("是".equals(ifDiff) || "否".equals(ifDiff)) {
				winInfoList = searchIfDiff();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		BindUtils.postNotifyChange(null, null, this, "winInfoList");
	}

	// 检索公司信息
	private List<EmSalaryPayInfoModel> searchCompany() {
		List<EmSalaryPayInfoModel> list = new ArrayList<EmSalaryPayInfoModel>();
		if (CheckString.isNum(company)) {
			for (EmSalaryPayInfoModel m : infoList) {
				if (m.getCid() == Integer.parseInt(company)) {
					list.add(m);
					break;
				}
			}
		} else if (CheckString.isChinese(company)) {
			for (EmSalaryPayInfoModel m : infoList) {
				if (RegexUtil.isExists(company, m.getCoba_shortname())) {
					list.add(m);
				}
			}
		} else if (CheckString.isLetter(company)) {
			for (EmSalaryPayInfoModel m : infoList) {
				if (RegexUtil.isExists(company, m.getCoba_shortname())
						|| RegexUtil.isExists(company, m.getCoba_namespell())) {
					list.add(m);
				}
			}
		}
		return list;
	}

	// 检索薪酬负责人
	private List<EmSalaryPayInfoModel> searchGzAddname() {
		List<EmSalaryPayInfoModel> list = new ArrayList<EmSalaryPayInfoModel>();
		for (EmSalaryPayInfoModel m : winInfoList) {
			if (this.gzAddname.equals(m.getCoba_gzaddname())) {
				list.add(m);
			}
		}
		return list;
	}

	// 检索是否存在差额
	private List<EmSalaryPayInfoModel> searchIfDiff() {
		List<EmSalaryPayInfoModel> list = new ArrayList<EmSalaryPayInfoModel>();
		boolean bool = "是".equals(ifDiff) ? true : false;
		for (EmSalaryPayInfoModel m : winInfoList) {
			if (bool) {
				if (m.getEsda_pay().compareTo(m.getCfsa_PaidIn()) != 0)
					list.add(m);
			} else {
				if (m.getEsda_pay().compareTo(m.getCfsa_PaidIn()) == 0)
					list.add(m);
			}
		}
		return list;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getGzAddname() {
		return gzAddname;
	}

	public void setGzAddname(String gzAddname) {
		this.gzAddname = gzAddname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public List<String> getGzAddnameList() {
		return gzAddnameList;
	}

	public List<EmSalaryPayInfoModel> getWinInfoList() {
		return winInfoList;
	}

	public String getIfDiff() {
		return ifDiff;
	}

	public void setIfDiff(String ifDiff) {
		this.ifDiff = ifDiff;
	}

}
