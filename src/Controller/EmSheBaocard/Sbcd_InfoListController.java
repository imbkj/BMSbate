package Controller.EmSheBaocard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import Model.EmShebaoCardInfoModel;
import Util.UserInfo;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_InfoListController {
	private EmShebaoCardInfoSelectBll bll = new EmShebaoCardInfoSelectBll();
		private List<EmShebaoCardInfoModel> list = bll.getEmShebaoCardInfoList(" and c.coba_client='"+UserInfo.getUsername()+"'");
	private List<EmShebaoCardInfoModel> statelist = bll.getStateList("");
	private String gid, sbcd_name, idcard, sbcd_computerid, cid, company,
			sbcd_comno;
	private String blclass = "", cosp_card_caliname = "",
			cosp_bsal_caliname = "", branbank = "";
	private List<String> branbanklist = bll.getBankBranchInfoList();

	// 查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("sbcdstate") Combobox sbcdstate) {
		String sql = "";
		if (gid != null && !gid.equals("")) {
			sql = sql + " and a.gid=" + gid;
		}
		if (sbcd_name != null && !sbcd_name.equals("")) {
			sql = sql + " and sbcd_name='" + sbcd_name + "'";
		}
		if (idcard != null && !idcard.equals("")) {
			sql = sql + " and sbcd_idcard='" + idcard + "'";
		}
		if (sbcd_computerid != null && !sbcd_computerid.equals("")) {
			sql = sql + " and sbcd_computerid='" + sbcd_computerid + "'";
		}
		if (cid != null && !cid.equals("")) {
			sql = sql + " and a.cid='" + cid + "'";
		}
		if (company != null && !company.equals("")) {
			sql = sql + " and (coba_company='" + company
					+ "' or coba_shortname='" + company + "')";
		}
		if (sbcd_comno != null && !sbcd_comno.equals("")) {
			sql = sql + " and sbcd_sbnumber='" + sbcd_comno + "'";
		}
		if (sbcdstate.getValue() != null && !sbcdstate.getValue().equals("")) {
			sql = sql + " and sbcd_stateid='"
					+ sbcdstate.getSelectedItem().getValue() + "'";
		}
		if (blclass != null && !blclass.equals("")) {
			sql = sql + " and sbcd_content='" + blclass + "'";
		}
		if (cosp_card_caliname != null && !cosp_card_caliname.equals("")) {
			if (cosp_card_caliname.equals("客服")) {
				sql = sql + " and cosp_card_caliname='" + cosp_card_caliname
						+ "'";
			} else {
				sql = sql + " and cosp_card_caliname<>'客服'";
			}
		}
		if (cosp_bsal_caliname != null && !cosp_bsal_caliname.equals("")) {
			if (cosp_bsal_caliname.equals("客服")) {
				sql = sql + " and cosp_bsal_caliname='" + cosp_bsal_caliname
						+ "'";
			} else {
				sql = sql + " and cosp_bsal_caliname<>'客服'";
			}
		}
		if (branbank != null && !branbank.equals("")) {
			sql = sql + " and sbcd_upbankname='" + branbank + "'";
		}
		list = bll.getEmShebaoCardInfoList(sql);
	}

	// 打开详细
	@Command
	public void datail(@BindingParam("model") EmShebaoCardInfoModel model) {
		Map map = new HashMap<>();
		map.put("m", model);
		Window window = (Window) Executions.createComponents(
				"/EmSheBaocard/Sbcd_DetailInfo.zul", null, map);
		window.doModal();
	}

	public List<EmShebaoCardInfoModel> getList() {
		return list;
	}

	public void setList(List<EmShebaoCardInfoModel> list) {
		this.list = list;
	}

	public List<EmShebaoCardInfoModel> getStatelist() {
		return statelist;
	}

	public void setStatelist(List<EmShebaoCardInfoModel> statelist) {
		this.statelist = statelist;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getSbcd_name() {
		return sbcd_name;
	}

	public void setSbcd_name(String sbcd_name) {
		this.sbcd_name = sbcd_name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getSbcd_computerid() {
		return sbcd_computerid;
	}

	public void setSbcd_computerid(String sbcd_computerid) {
		this.sbcd_computerid = sbcd_computerid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSbcd_comno() {
		return sbcd_comno;
	}

	public void setSbcd_comno(String sbcd_comno) {
		this.sbcd_comno = sbcd_comno;
	}

	public List<String> getBranbanklist() {
		return branbanklist;
	}

	public void setBranbanklist(List<String> branbanklist) {
		this.branbanklist = branbanklist;
	}

	public String getBlclass() {
		return blclass;
	}

	public void setBlclass(String blclass) {
		this.blclass = blclass;
	}

	public String getCosp_card_caliname() {
		return cosp_card_caliname;
	}

	public void setCosp_card_caliname(String cosp_card_caliname) {
		this.cosp_card_caliname = cosp_card_caliname;
	}

	public String getCosp_bsal_caliname() {
		return cosp_bsal_caliname;
	}

	public void setCosp_bsal_caliname(String cosp_bsal_caliname) {
		this.cosp_bsal_caliname = cosp_bsal_caliname;
	}

	public String getBranbank() {
		return branbank;
	}

	public void setBranbank(String branbank) {
		this.branbank = branbank;
	}
}
