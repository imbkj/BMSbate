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
import bll.EmSheBao.EmSheBao_DSelectBll;

public class Emsc_DeclareMA_SearchController {
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
	private List<LoginModel> dNameList = new ArrayList<LoginModel>(); // 申报人
	private DataPopedomService c_dps = new Data_PopedomIpml();
	private DataPopedomService dn_dps = new Data_PopedomIpml("", "社保");

	// 统计数据的数组
	private String[] count = new String[2];
	private String shebaoId = "";

	public Emsc_DeclareMA_SearchController() {
		// 统计
		count = dsbll.getForeignerDataCount(nowmonth);
		// 客服列表
		clientList = c_dps.getdepLoginlist();
		// 申报人列表
		dNameList = dn_dps.getroleLoginlist();
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

		int chkDec = 1; // 判断

		// 判断是否员工查询
		if (!"".equals(keyword.getValue())) {
			if (s_ownmonth.getSelectedItem() != null) {
				// 所属月份
				if (!"全部".equals(s_ownmonth.getSelectedItem().getLabel())) {
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
			map.put("chkDec", 0);
			map.put("s_state", "");
			map.put("shebaocoding", "");
			map.put("single", "");
			map.put("shebaoId", "");
			Window window = (Window) Executions.createComponents(
					"Emsc_DeclareMA_List.zul", null, map);
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
				if (client.getSelectedItem() != null) {
					sql = sql + " and coba_client='"
							+ client.getSelectedItem().getLabel() + "'";
				}
				// 添加人
				if (addname.getSelectedItem() != null) {
					sql = sql + " and escm_addname='"
							+ addname.getSelectedItem().getLabel() + "'";
				}
				// 开户状态
				if (!single.getSelectedItem().getValue().equals("9")) {
					sql = sql + " AND escm_single="
							+ single.getSelectedItem().getValue();
				}
				// 申报人
				if (dname.getSelectedItem() != null) {
					sql = sql
							+ "  and sb.cid in(select cid from cobase where coba_shebaodeclare='"
							+ dname.getSelectedItem().getLabel() + "')'";
				}
				// 批次号
				if (!"".equals(batchnum.getValue())) {
					sql = sql + "  AND escm_batchnum='" + batchnum.getValue()
							+ "'";
				}

				// 申报时间
				if (dday.getSelectedItem() != null) {
					if ("之前".equals(dbefore.getSelectedItem().getValue())) {
						sql = sql + "and day(emsc_declaretime)<"
								+ dday.getSelectedItem().getLabel();
					} else if ("之后"
							.equals(dbefore.getSelectedItem().getValue())) {
						sql = sql + "and day(emsc_declaretime)>"
								+ dday.getSelectedItem().getLabel();
					} else if ("当日"
							.equals(dbefore.getSelectedItem().getValue())) {
						sql = sql + "and day(emsc_declaretime)="
								+ dday.getSelectedItem().getLabel();
					}
				}
				if (dmonth.getSelectedItem() != null) {
					sql = sql + "and month(emsc_declaretime)="
							+ dmonth.getSelectedItem().getLabel();
				}

				// 添加时间
				if (aday.getSelectedItem() != null) {
					if ("之前".equals(abefore.getSelectedItem().getValue())) {
						sql = sql + "and day(emsc_addtime)<"
								+ aday.getSelectedItem().getLabel();
						sql = sql + "and month(emsc_addtime)<="
								+ amonth.getSelectedItem().getLabel();
					} else if ("之后"
							.equals(abefore.getSelectedItem().getValue())) {
						sql = sql + "and day(emsc_addtime)>"
								+ aday.getSelectedItem().getLabel();
						sql = sql + "and month(emsc_addtime)>="
								+ amonth.getSelectedItem().getLabel();

					} else if ("当日"
							.equals(abefore.getSelectedItem().getValue())) {
						sql = sql + "and day(emsc_addtime)="
								+ aday.getSelectedItem().getLabel();
						sql = sql + "and month(emsc_addtime)="
								+ amonth.getSelectedItem().getLabel();

					}
				}

				// winDeclareSearch.detach();

				// 判断是否可以操作批量打印\批量申报
				if (s_state.getSelectedItem().getValue().equals("10")
						|| single.getSelectedItem().getValue().equals("9")
						|| s_ownmonth.getSelectedItem() == null) {
					chkDec = 0;
				}

				win.detach();
				Map map = new HashMap();
				map.put("sql", sql);
				map.put("chkDec", chkDec);
				map.put("s_state", s_state.getSelectedItem().getValue());
				map.put("single", single.getSelectedItem().getValue());
				map.put("shebaoId", shebaoId);
				Window window = (Window) Executions.createComponents(
						"Emsc_DeclareMA_List.zul", null, map);
				window.doModal();
			} else {
				// 弹出提示
				Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public String[] getCount() {
		return count;
	}

	public void setCount(String[] count) {
		this.count = count;
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

	public List<LoginModel> getdNameList() {
		return dNameList;
	}

	public void setdNameList(List<LoginModel> dNameList) {
		this.dNameList = dNameList;
	}

	public String getShebaoId() {
		return shebaoId;
	}

	public void setShebaoId(String shebaoId) {
		this.shebaoId = shebaoId;
	}

}
