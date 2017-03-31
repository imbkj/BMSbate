package Controller.EmSheBao;

import impl.SystemControl.Data_PopedomIpml;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
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

public class EmSheBao_DForeignerSearchController {
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

	public EmSheBao_DForeignerSearchController() {
		// 获取当月尚未申报完独立户数据
		ndscompanyList = dsbll.getForNDSCompany(nowmonth);
		// 统计
		count = dsbll.getForeignerDataCount(nowmonth);
		// 客服列表
		clientList = c_dps.getdepLoginlist();
		// 申报人列表
		dNameList = dn_dps.getroleLoginlist();
	}

	// 查询当月尚未申报完独立户数据
	@Command("ndsSearch")
	@NotifyChange("ndscompanyList")
	public void ndsSearch(@BindingParam("s_ownmonth") Combobox s_ownmonth) {
		if (s_ownmonth.getSelectedItem() != null) {
			ndscompanyList = dsbll.getForNDSCompany(s_ownmonth
					.getSelectedItem().getLabel());
			// 统计
			count = dsbll.getForeignerDataCount(s_ownmonth.getSelectedItem()
					.getLabel());
		} else {
			// 弹出提示
			Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 查询发放
	@Command("search")
	public void search(
			@BindingParam("winDeclareSearch") Window winDeclareSearch,
			@BindingParam("s_ownmonth") Combobox s_ownmonth,
			@BindingParam("s_change") Combobox s_change,
			@BindingParam("s_state") Combobox s_state,
			@BindingParam("keyword") Textbox keyword,
			@BindingParam("cont") Combobox cont,
			@BindingParam("flag") Checkbox flag,
			@BindingParam("scompany") Combobox scompany,
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
			@BindingParam("excelfile") Textbox excelfile,
			@BindingParam("shebaocoding") Textbox shebaocoding) {

		String sql = "";

		int chkDec = 1; // 判断

		// 判断是否员工查询
		if (!"".equals(keyword.getValue())) {
			if (s_ownmonth.getSelectedItem() != null) {
				// 所属月份
				sql = sql + " AND sb.ownmonth="
						+ s_ownmonth.getSelectedItem().getLabel();
			}
			if (cont.getSelectedItem().getLabel().equals("姓名")) {
				sql = sql + " AND emsc_name like '%" + keyword.getValue()
						+ "%'";
			} else if (cont.getSelectedItem().getLabel().equals("员工编号")) {
				sql = sql + " AND sb.gid=" + keyword.getValue();
			} else if (cont.getSelectedItem().getLabel().equals("身份证")) {
				sql = sql + " AND emsc_idcard='" + keyword.getValue() + "'";
			} else if (cont.getSelectedItem().getLabel().equals("电脑号")) {
				sql = sql + " AND emsc_computerid='" + keyword.getValue() + "'";
			} else if (cont.getSelectedItem().getLabel().equals("公司名称")) {
				sql = sql + " AND emsc_company like '%" + keyword.getValue()
						+ "%'";
			} else if (cont.getSelectedItem().getLabel().equals("公司编号")) {
				sql = sql + " AND sb.cid=" + keyword.getValue();
			}

			winDeclareSearch.detach();
			Map map = new HashMap();
			map.put("sql", sql);
			map.put("chkDec", 0);
			map.put("s_state", "");
			map.put("shebaocoding", "");
			map.put("single", "");
			map.put("shebaoId", "");
			Window window = (Window) Executions.createComponents(
					"EmSheBao_DForeignerList.zul", null, map);
			window.doModal();

		} else {

			if (s_ownmonth.getSelectedItem() != null) {

				// 所属月份
				sql = sql + " AND sb.ownmonth="
						+ s_ownmonth.getSelectedItem().getLabel();

				// 变动情况
				if (!s_change.getSelectedItem().getLabel().equals("全部")) {
					if (s_change.getSelectedItem().getLabel().equals("全部但不含新增")) {
						sql = sql + " AND emsc_change<>'新增'";
					} else {
						sql = sql + " AND emsc_change='"
								+ s_change.getSelectedItem().getLabel() + "'";
					}
				}
				// 申报状态
				if (!s_state.getSelectedItem().getValue().equals("10")) {
					if (!s_state.getSelectedItem().getValue().equals("3")) {
						sql = sql + " AND emsc_ifdeclare="
								+ s_state.getSelectedItem().getValue();
					} else {
						sql = sql + " AND emsc_ifdeclare in(3,9)";
					}
				}
				// 特殊筛选

				if (flag.isChecked()) {
					sql = sql + " AND  emsc_flag is not null";
				}

				// 当月尚未申报完独立户
				if (scompany.getSelectedItem() != null) {
					if (!"".equals(scompany.getSelectedItem().getValue())) {
						sql = sql
								//+ " and exists(select gid from CoGList where CoGList.gid=sb.gid and exists(select coli_id from CoOfferList where CoOfferList.coli_id=CoGList.cgli_coli_id and exists (SELECT COCO_ID FROM COCOMPACT WHERE COCOMPACT.coco_id=CoOfferList.coli_coco_id and COCO_STATE>1 AND coco_shebaoid='"
								+" and sbid.coco_shebaoID='"
								+ scompany.getSelectedItem().getValue()
								+ "' and emsc_single=1";

						shebaoId = scompany.getSelectedItem().getValue();
					}
				}

				// 客服代表
				if (client.getSelectedItem() != null) {
					sql = sql + " and emsc_client='"
							+ client.getSelectedItem().getLabel() + "'";
				}
				// 添加人
				if (addname.getSelectedItem() != null) {
					sql = sql + " and emsc_addname='"
							+ addname.getSelectedItem().getLabel() + "'";
				}
				// 开户状态
				if (!single.getSelectedItem().getValue().equals("9")) {
					sql = sql + " AND emsc_single="
							+ single.getSelectedItem().getValue();
				}
				// 申报人
				if (dname.getSelectedItem() != null) {
					sql = sql
							+ "  and sb.cid in(select cid from cobase where coba_shebaodeclare='"
							+ dname.getSelectedItem().getLabel() + "')'";
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

				// 社保局单位编号
				if (!"".equals(shebaocoding.getValue())) {
					sql = sql
							//+ " and exists(select gid from CoGList where CoGList.gid=sb.gid and exists(select coli_id from CoOfferList where CoOfferList.coli_id=CoGList.cgli_coli_id and exists (SELECT COCO_ID FROM COCOMPACT WHERE COCOMPACT.coco_id=CoOfferList.coli_coco_id and COCO_STATE>1 AND coco_shebaoid='"
							+" and sbid.coco_shebaoID='"
							+ shebaocoding.getValue()
							+ "' and emsc_single=1";

					shebaoId = shebaocoding.getValue();
				}

				// winDeclareSearch.detach();

				// 判断是否可以操作批量打印\批量申报
				if (s_state.getSelectedItem().getValue().equals("9")
						|| single.getSelectedItem().getValue().equals("9")
						|| s_ownmonth.getSelectedItem() == null) {
					chkDec = 0;
				}

				winDeclareSearch.detach();
				Map map = new HashMap();
				map.put("sql", sql);
				map.put("chkDec", chkDec);
				map.put("s_state", s_state.getSelectedItem().getValue());
				map.put("shebaocoding", shebaocoding.getValue());
				map.put("single", single.getSelectedItem().getValue());
				map.put("shebaoId", shebaoId);
				Window window = (Window) Executions.createComponents(
						"EmSheBao_DForeignerList.zul", null, map);
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
