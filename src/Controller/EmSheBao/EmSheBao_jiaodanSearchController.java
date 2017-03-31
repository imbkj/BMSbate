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

public class EmSheBao_jiaodanSearchController {
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
	private String[] count = new String[11];

	public EmSheBao_jiaodanSearchController() {

		// 获取当月尚未申报完独立户数据
		ndscompanyList = dsbll.getSZSINDSCompany(nowmonth);
		// 统计
		count = dsbll.getSZSIChangeDataCount(nowmonth);
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
			ndscompanyList = dsbll.getSZSINDSCompany(s_ownmonth
					.getSelectedItem().getLabel());
			// 统计
			count = dsbll.getSZSIChangeDataCount(s_ownmonth.getSelectedItem()
					.getLabel());
		} else {
			// 弹出提示
			Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 查询
	@Command("search")
	public void search(
			@BindingParam("winDeclareSearch") Window winDeclareSearch,
			@BindingParam("s_ownmonth") Combobox s_ownmonth,
			@BindingParam("s_change") Combobox s_change,
			@BindingParam("s_state") Combobox s_state,
			@BindingParam("keyword") Textbox keyword,
			@BindingParam("cont") Combobox cont,
			@BindingParam("computerid") Checkbox computerid,
			@BindingParam("todayAdd") Checkbox todayAdd,
			@BindingParam("flag") Checkbox flag,
			@BindingParam("ifmsg") Checkbox ifmsg,
			@BindingParam("flag_f") Checkbox flag_f,
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
			@BindingParam("topnum") Combobox topnum) {

		String sql = "";
		String top = "";
		String order = "";

		int ifs = 0; // 判断是否选中特殊筛选
		int chkDec = 1; // 判断

		if (s_ownmonth.getSelectedItem() != null) {

			order = " order by escs_company,escs_name,escs_addtime";
			// 所属月份
			sql = sql + " AND sb.ownmonth="
					+ s_ownmonth.getSelectedItem().getLabel();

			// 变动情况
			if (!s_change.getSelectedItem().getLabel().equals("全部")) {
				sql = sql + " AND escs_change='"
						+ s_change.getSelectedItem().getLabel() + "'";
			}
			// 申报状态
			if (!s_state.getSelectedItem().getValue().equals("9")) {
				sql = sql + " AND escs_ifdeclare="
						+ s_state.getSelectedItem().getValue();
			}

			// 当月尚未申报完独立户
			if (scompany.getSelectedItem() != null) {
				sql = sql
						+ " and exists(select gid from CoGList where CoGList.gid=sb.gid and exists(select coli_id from CoOfferList where CoOfferList.coli_id=CoGList.cgli_coli_id and exists (SELECT COCO_ID FROM COCOMPACT WHERE COCOMPACT.coco_id=CoOfferList.coli_coco_id and COCO_STATE>1 AND coco_shebaoid='"
						+ scompany.getSelectedItem().getValue()
						+ "'))) and escs_single=1";
			}

			// 客服代表
			if (client.getSelectedItem() != null) {
				sql = sql + " and escs_client='"
						+ client.getSelectedItem().getLabel() + "'";
			}
			// 添加人
			if (addname.getSelectedItem() != null) {
				sql = sql + " and escs_addname='"
						+ addname.getSelectedItem().getLabel() + "'";
			}
			// 开户状态
			if (single.getSelectedItem() != null) {
				if (!"9".equals(single.getSelectedItem().getValue())
						&& !"".equals(single.getSelectedItem().getValue())) {
					sql = sql + " AND escs_single="
							+ single.getSelectedItem().getValue();
				}
			}
			// winDeclareSearch.detach();
			Map map = new HashMap();
			map.put("sql", sql);
			map.put("top", top);
			map.put("order", order);

			Window window = (Window) Executions.createComponents(
					"EmSheBao_ShebaojiaodanList.zul", null, map);
			window.doModal();
		} else {
			// 弹出提示
			Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public String[] getS_ownmonth() {
		return s_ownmonth;
	}

	public void setS_ownmonth(String[] s_ownmonth) {
		this.s_ownmonth = s_ownmonth;
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

	public String[] getCount() {
		return count;
	}

	public void setCount(String[] count) {
		this.count = count;
	}

	public String getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(String nowmonth) {
		this.nowmonth = nowmonth;
	}

}
