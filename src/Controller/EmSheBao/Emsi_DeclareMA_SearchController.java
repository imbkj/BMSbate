package Controller.EmSheBao;

import impl.SystemControl.Data_PopedomIpml;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.DataPopedomService;
import Model.CoCompactModel;
import Model.LoginModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsi_DeclareMA_SearchController {
	MonthListUtil mlu = new MonthListUtil();
	private DateStringChange dsc = new DateStringChange();
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	// 所属月份下拉框
	private String[] s_ownmonth = mlu.getMonthList(true, nowmonth, 2, 9);
	// 当月尚未申报完独立户
	private List<CoCompactModel> ndscompanyList;
	private List<LoginModel> clientList = new ArrayList<LoginModel>(); // 客服
	private DataPopedomService c_dps = new Data_PopedomIpml();
	private DataPopedomService dn_dps = new Data_PopedomIpml("", "社保");
	private String client;

	public Emsi_DeclareMA_SearchController() {
		// 客服列表
		clientList = c_dps.getdepLoginlist();
		client = UserInfo.getUsername();
	}

	// 查询
	@Command("search")
	public void search(@BindingParam("win") Window win,
			@BindingParam("s_ownmonth") Combobox s_ownmonth,
			@BindingParam("s_state") Combobox s_state,
			@BindingParam("keyword") Textbox keyword,
			@BindingParam("cont") Combobox cont,
			@BindingParam("client") Combobox client,
			@BindingParam("addname") Combobox addname,
			@BindingParam("single") Combobox single,
			@BindingParam("dname") Combobox dname,
			@BindingParam("dmonth") Combobox dmonth,
			@BindingParam("dday") Combobox dday,
			@BindingParam("dbefore") Combobox dbefore,
			@BindingParam("amonth") Combobox amonth,
			@BindingParam("aday") Combobox aday,
			@BindingParam("abefore") Combobox abefore,
			@BindingParam("batchnum") Textbox batchnum) {

		String sql = "";

		// 判断是否员工查询
		if (!"".equals(keyword.getValue())) {
			if (s_ownmonth.getSelectedItem() != null) {

				if (!"全部".equals(s_ownmonth.getSelectedItem().getLabel())) {
					// 所属月份
					sql = sql + " AND sb.ownmonth="
							+ s_ownmonth.getSelectedItem().getLabel();
				}

			}
			if (cont.getSelectedItem().getLabel().equals("姓名")) {
				sql = sql + " AND escm_name like '%" + keyword.getValue()
						+ "%'";
			} else if (cont.getSelectedItem().getLabel().equals("员工编号")) {
				sql = sql + " AND sb.gid=" + keyword.getValue();
			} else if (cont.getSelectedItem().getLabel().equals("身份证")) {
				sql = sql + " AND escm_idcard='" + keyword.getValue() + "'";
			} else if (cont.getSelectedItem().getLabel().equals("电脑号")) {
				sql = sql + " AND escm_computerid='" + keyword.getValue() + "'";
			} else if (cont.getSelectedItem().getLabel().equals("公司名称")) {
				sql = sql + " AND escm_company like '%" + keyword.getValue()
						+ "%'";
			} else if (cont.getSelectedItem().getLabel().equals("公司编号")) {
				sql = sql + " AND sb.cid=" + keyword.getValue();
			}

			win.detach();
			Map map = new HashMap();
			map.put("sql", sql);
			map.put("s_state", "");
			map.put("shebaocoding", "");
			map.put("single", "");
			Window window = (Window) Executions.createComponents(
					"Emsi_DeclareMA_List.zul", null, map);
			window.doModal();

		} else {

			if (s_ownmonth.getSelectedItem() != null) {

				if (!"全部".equals(s_ownmonth.getSelectedItem().getLabel())) {
					// 所属月份
					sql = sql + " AND sb.ownmonth="
							+ s_ownmonth.getSelectedItem().getLabel();
				}

				// 申报状态
				if (!s_state.getSelectedItem().getValue().equals("10")) {
					if (!s_state.getSelectedItem().getValue().equals("3")) {
						sql = sql + " AND escm_ifdeclare="
								+ s_state.getSelectedItem().getValue();
					} else {
						sql = sql + " AND escm_ifdeclare in(3,9)";
					}
				}

				// 客服代表
				if (client.getSelectedItem() != null
						&& !"全部".equals(client.getSelectedItem().getLabel())) {
					if ("客户服务部".equals(client.getSelectedItem().getLabel())) {
						sql = sql + " and coba_client in(select log_name from login where dep_id=2)";
					}else if ("全国项目部".equals(client.getSelectedItem().getLabel())) {
						sql = sql + " and coba_client in(select log_name from login where dep_id=6)";
					}else {
						sql = sql + " and coba_client='"
								+ client.getSelectedItem().getLabel() + "'";
					}

				}

				// 开户状态
				if (!single.getSelectedItem().getValue().equals("9")) {
					sql = sql + " AND escm_single="
							+ single.getSelectedItem().getValue();
				}

				// 批次号
				if (!"".equals(batchnum.getValue())) {
					sql = sql + "  AND escm_batchnum='" + batchnum.getValue()
							+ "'";
				}

				// winDeclareSearch.detach();

				win.detach();
				Map map = new HashMap();
				map.put("sql", sql);
				map.put("s_state", s_state.getSelectedItem().getValue());
				map.put("single", single.getSelectedItem().getValue());
				Window window = (Window) Executions.createComponents(
						"Emsi_DeclareMA_List.zul", null, map);
				window.doModal();
			} else {
				// 弹出提示
				Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public String[] getS_ownmonth() {
		return s_ownmonth;
	}

	public void setS_ownmonth(String[] s_month) {
		this.s_ownmonth = s_ownmonth;
	}

	public String getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(String nowmonth) {
		this.nowmonth = nowmonth;
	}

	public List<CoCompactModel> getNdscompanyList() {
		return ndscompanyList;
	}

	public void setNdscompanyList(List<CoCompactModel> ndscompanyList) {
		this.ndscompanyList = ndscompanyList;
	}

	public List<LoginModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<LoginModel> clientList) {
		this.clientList = clientList;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

}
