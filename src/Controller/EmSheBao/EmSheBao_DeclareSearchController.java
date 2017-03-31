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

import bll.EmSheBao.EmSheBao_DSelectBll;

import Model.CoCompactModel;
import Model.LoginModel;
import Util.DateStringChange;
import Util.MonthListUtil;

public class EmSheBao_DeclareSearchController {
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
	// 所有独立开户公司
	private List<CoCompactModel> scompanyList;
	private List<LoginModel> clientList = new ArrayList<LoginModel>(); // 客服
	private List<LoginModel> dNameList = new ArrayList<LoginModel>(); // 申报人
	private DataPopedomService c_dps = new Data_PopedomIpml();
	private DataPopedomService dn_dps = new Data_PopedomIpml("", "社保");

	// 变动情况
	private List<String> sChangeList;

	// 统计数据的数组
	private String[] count = new String[24];

	// 社保局单位编号
	private String shebaoId = "";

	// 变动情况
	private String s_changeString = "全部";

	public EmSheBao_DeclareSearchController() {
		// 获取所有独立开户公司数据
		scompanyList = dsbll.getSCompany();
		// 获取当月尚未申报完独立户数据
		ndscompanyList = dsbll.getNDSCompany(nowmonth);
		// 统计
		count = dsbll.getChangeDataCount(nowmonth);
		// 客服列表
		clientList = c_dps.getdepLoginlist();
		// 申报人列表
		dNameList = dn_dps.getroleLoginlist();
		// 变动情况列表
		sChangeList = dsbll.getSChange("");
	}

	// 查询变动情况
	@Command("getSChange")
	@NotifyChange({ "sChangeList", "shebaoId", "s_changeString" })
	public void getSChange(@BindingParam("scompany") Combobox scompany,
			@BindingParam("s_ownmonth") Combobox s_ownmonth,
			@BindingParam("s_state") Combobox s_state) {
		if (s_state.getSelectedItem() != null
				&& scompany.getSelectedItem() != null
				&& s_ownmonth.getSelectedItem() != null) {
			String sql = "";
			sql = " AND ownmonth="
					+ s_ownmonth.getSelectedItem().getLabel()
					+ " AND exists(select gid from CoGList where CoGList.gid=EmShebaoChange.gid and exists(select coli_id from CoOfferList where CoOfferList.coli_id=CoGList.cgli_coli_id and exists(select coof_id from CoOffer where CoOffer.coof_id=CoOfferList.coli_coof_id and exists (SELECT COCO_ID FROM COCOMPACT WHERE COCOMPACT.coco_id=CoOffer.coof_coco_id and COCO_STATE>1 AND coco_shebaoid='"
					+ scompany.getSelectedItem().getValue()
					+ "')))) and emsc_single=1";

			if (!"9".equals(s_state.getSelectedItem().getValue())) {
				sql = sql + " and emsc_ifdeclare="
						+ s_state.getSelectedItem().getValue();
			}

			shebaoId = scompany.getSelectedItem().getValue();
			sChangeList = dsbll.getSChange(sql);
			s_changeString = "全部";
		}
	}

	// 查询当月尚未申报完独立户数据
	@Command("ndsSearch")
	@NotifyChange("ndscompanyList")
	public void ndsSearch(@BindingParam("s_ownmonth") Combobox s_ownmonth) {
		if (s_ownmonth.getSelectedItem() != null) {
			ndscompanyList = dsbll.getNDSCompany(s_ownmonth.getSelectedItem()
					.getLabel());
			// 统计
			count = dsbll.getChangeDataCount(s_ownmonth.getSelectedItem()
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
			@BindingParam("ifsame") Checkbox ifsame,
			@BindingParam("ifmodify") Checkbox ifmodify,
			@BindingParam("computerid") Checkbox computerid,
			@BindingParam("todayAdd") Checkbox todayAdd,
			@BindingParam("flag") Checkbox flag,
			@BindingParam("ifmsg") Checkbox ifmsg,
			@BindingParam("ifwrong") Checkbox ifwrong,
			@BindingParam("iffifteen") Checkbox iffifteen,
			@BindingParam("scompany") Combobox scompany,
			@BindingParam("allcompany") Combobox allcompany,
			// @BindingParam("client") Combobox client,
			// @BindingParam("addname") Combobox addname,
			@BindingParam("single") Combobox single,
			@BindingParam("dname") Combobox dname,
			@BindingParam("dmonth") Combobox dmonth,
			@BindingParam("dday") Combobox dday,
			@BindingParam("dbefore") Combobox dbefore,
			@BindingParam("amonth") Combobox amonth,
			@BindingParam("aday") Combobox aday,
			@BindingParam("abefore") Combobox abefore,
			@BindingParam("excelfile") Textbox excelfile,
			@BindingParam("shebaocoding") Textbox shebaocoding,
			@BindingParam("topnum") Combobox topnum) {

		String sql = "";
		String top = "";
		String order = "";
		String bjOwnmonth = nowmonth;

		int ifs = 0; // 判断是否选中特殊筛选
		int chkDec = 1; // 判断

		// 判断是否员工查询
		if (!"".equals(keyword.getValue())) {
			if (s_ownmonth.getSelectedItem() != null) {
				// 所属月份
				sql = sql + " AND sb.ownmonth="
						+ s_ownmonth.getSelectedItem().getLabel();
				// 补交数据统计月份
				bjOwnmonth = s_ownmonth.getSelectedItem().getLabel();
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

			order = " order by sb.ownmonth,emsc_name";

			winDeclareSearch.detach();
			Map map = new HashMap();
			map.put("sql", sql);
			map.put("top", top);
			map.put("order", order);
			map.put("bjOwnmonth", bjOwnmonth);
			map.put("ifs", 0);
			map.put("chkDec", 0);
			map.put("change", "");
			map.put("shebaocoding", "");
			map.put("single", "");
			map.put("shebaoId", "");
			Window window = (Window) Executions.createComponents(
					"EmSheBao_DeclareList.zul", null, map);
			window.doModal();

		} else {

			if (s_ownmonth.getSelectedItem() != null) {

				order = " order by emsc_shortname,emsc_name,emsc_addtime";
				// 所属月份
				sql = sql + " AND sb.ownmonth="
						+ s_ownmonth.getSelectedItem().getLabel();

				// 补交数据统计月份
				bjOwnmonth = s_ownmonth.getSelectedItem().getLabel();

				// 变动情况
				if (s_change.getSelectedItem() != null
						&& !s_change.getSelectedItem().getLabel().equals("全部")) {
					if (s_change.getSelectedItem().getLabel().equals("全部但不含新增")) {
						sql = sql + " AND emsc_change<>'新增'";
					} else {
						sql = sql + " AND emsc_change='"
								+ s_change.getSelectedItem().getLabel() + "'";
					}
				}
				// 申报状态
				if (!s_state.getSelectedItem().getValue().equals("9")) {
					sql = sql + " AND emsc_ifdeclare="
							+ s_state.getSelectedItem().getValue();
				}
				// 特殊筛选
				if (ifsame.isChecked()) {
					sql = sql + " AND  emsc_ifsame=1";

					order = " order by emsc_name,emsc_addtime";
					ifs = 1;
				}
				if (ifmodify.isChecked()) {
					sql = sql + " AND  emsc_ifmodify=1";
				}
				if (computerid.isChecked()) {
					sql = sql + " AND  emsc_computerid IS NULL";
				}
				if (todayAdd.isChecked()) {
					sql = sql
							+ " AND  DATEDIFF(d,emsc_confirmtime,GETDATE())=0";
				}
				if (flag.isChecked()) {
					sql = sql + " AND  emsc_flagname IS NOT NULL";
				}
				if (ifmsg.isChecked()) {
					sql = sql + " AND  emsc_ifmsg=1";
				}
				if (ifwrong.isChecked()) {
					sql = sql + " AND  emsc_ifwrong=1";
				}
				if (iffifteen.isChecked()) {
					sql = sql + " AND  emsc_iffifteen=1";
				}

				// 当月尚未申报完独立户
				if (scompany.getSelectedItem() != null) {
					if (!"".equals(scompany.getSelectedItem().getValue())) {
						sql = sql
								// +
								// " and exists(select gid from CoGList where CoGList.gid=sb.gid and exists(select coli_id from CoOfferList where CoOfferList.coli_id=CoGList.cgli_coli_id and exists (SELECT COCO_ID FROM COCOMPACT WHERE COCOMPACT.coco_id=CoOfferList.coli_coco_id and COCO_STATE>1 AND coco_shebaoid='"
								+ " and sbid.coco_shebaoID='"
								+ scompany.getSelectedItem().getValue()
								+ "' and emsc_single=1";

						shebaoId = scompany.getSelectedItem().getValue();
					}
				}
				// 所有独立开户公司
				else if (allcompany.getSelectedItem() != null) {
					if (!"".equals(allcompany.getSelectedItem().getValue())) {
						sql = sql
								// +
								// " and exists(select gid from CoGList where CoGList.gid=sb.gid and exists(select coli_id from CoOfferList where CoOfferList.coli_id=CoGList.cgli_coli_id and exists (SELECT COCO_ID FROM COCOMPACT WHERE COCOMPACT.coco_id=CoOfferList.coli_coco_id and COCO_STATE>1 AND coco_shebaoid='"
								+ " and sbid.coco_shebaoID='"
								+ allcompany.getSelectedItem().getValue()
								+ "' and emsc_single=1";

						shebaoId = allcompany.getSelectedItem().getValue();
					}
				}

				/*
				 * // 客服代表 if (client.getSelectedItem() != null) { sql = sql +
				 * " and emsc_client='" + client.getSelectedItem().getLabel() +
				 * "'"; } // 添加人 if (addname.getSelectedItem() != null) { sql =
				 * sql + " and emsc_addname='" +
				 * addname.getSelectedItem().getLabel() + "'"; }
				 */
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
							// +
							// " and exists(select gid from CoGList where CoGList.gid=sb.gid and exists(select coli_id from CoOfferList where CoOfferList.coli_id=CoGList.cgli_coli_id and exists (SELECT COCO_ID FROM COCOMPACT WHERE COCOMPACT.coco_id=CoOfferList.coli_coco_id and COCO_STATE>1 AND coco_shebaoid='"
							+ " and sbid.coco_shebaoID='"
							+ shebaocoding.getValue() + "' and emsc_single=1";

					shebaoId = shebaocoding.getValue();
				}

				// 申报文件名
				if (!"".equals(excelfile.getValue())) {
					sql = sql + " and emsc_excelfile='" + excelfile.getValue()
							+ "'";
				}

				// 结果集行数
				if (!topnum.getSelectedItem().getLabel().equals("全部")) {
					if (topnum.getSelectedItem().getLabel().equals("首100行")) {
						top = " TOP 100 ";
					} else if (topnum.getSelectedItem().getLabel()
							.equals("首200行")) {
						top = " TOP 200 ";
					} else if (topnum.getSelectedItem().getLabel()
							.equals("首300行")) {
						top = " TOP 300 ";
					} else if (topnum.getSelectedItem().getLabel()
							.equals("首400行")) {
						top = " TOP 400 ";
					} else if (topnum.getSelectedItem().getLabel()
							.equals("首500行")) {
						top = " TOP 500 ";
					}
				}

				// 判断是否可以操作生成模板
				if ((s_change.getSelectedItem() != null && s_change
						.getSelectedItem().getLabel().equals("全部"))
						|| single.getSelectedItem().getValue().equals("9")
						|| s_ownmonth.getSelectedItem() == null) {
					chkDec = 0;
				} else if ((single.getSelectedItem().getValue().equals("1") && scompany
						.getSelectedItem() == null)
						&& (s_change.getSelectedItem() != null && s_change
								.getSelectedItem().getLabel().equals("全部"))
						|| s_ownmonth.getSelectedItem() == null) {
					chkDec = 0;
				}

				winDeclareSearch.detach();
				Map map = new HashMap();
				map.put("sql", sql);
				map.put("top", top);
				map.put("order", order);
				map.put("bjOwnmonth", bjOwnmonth);
				map.put("ifs", ifs);
				map.put("chkDec", chkDec);
				if (s_change.getSelectedItem() != null) {
					map.put("change", s_change.getSelectedItem().getLabel());
				} else {
					map.put("change", "全部");
				}
				map.put("shebaocoding", shebaocoding.getValue());
				map.put("single", single.getSelectedItem().getValue());
				map.put("shebaoId", shebaoId);
				Window window = (Window) Executions.createComponents(
						"EmSheBao_DeclareList.zul", null, map);
				window.doModal();

			} else {
				// 弹出提示
				Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 上传文件
	@Command("upload")
	public void upload() {
		Window window = (Window) Executions.createComponents(
				"EmSheBao_DeclareUpload.zul", null, null);
		window.doModal();
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

	public List<CoCompactModel> getScompanyList() {
		return scompanyList;
	}

	public void setScompanyList(List<CoCompactModel> scompanyList) {
		this.scompanyList = scompanyList;
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

	public List<String> getsChangeList() {
		return sChangeList;
	}

	public void setsChangeList(List<String> sChangeList) {
		this.sChangeList = sChangeList;
	}

	public String getShebaoId() {
		return shebaoId;
	}

	public void setShebaoId(String shebaoId) {
		this.shebaoId = shebaoId;
	}

	public String getS_changeString() {
		return s_changeString;
	}

	public void setS_changeString(String s_changeString) {
		this.s_changeString = s_changeString;
	}

}
