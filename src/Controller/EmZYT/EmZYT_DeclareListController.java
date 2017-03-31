package Controller.EmZYT;

import impl.CheckStringImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.CheckStringService;

import Model.EmZYTModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.EmbaseListBll;

public class EmZYT_DeclareListController {
	private DateStringChange dsc = new DateStringChange();
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();

	private List cityList; // 委托城市
	private List<EmZYTModel> dataList;
	private List sclassList; // 委托事件
	private List stateList; // 委托处理状态
	private List emkeyTypeList; // 员工查询类型

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	private Date d_nowmonth = dsc.StringtoDate(nowmonth, "yyyyMM");
	private String username = UserInfo.getUsername();

	private String sql = "";

	public EmZYT_DeclareListController() throws Exception {
		cityList = sbll.getCityName(); // 委托城市下拉框
		sclassList = sbll.getSclass(); // 委托事件下拉框
		stateList = sbll.getState(2); // 数据状态下拉框
		emkeyTypeList = sbll.getEmkeyType(); // 员工查询类型下拉框

		// 获取当月数据
		sql = " AND emzt_state<>10 AND ownmonth=" + nowmonth;
		dataList = sbll.getEmZYTList(sql);
	}

	// 查询数据
	@Command("search")
	@NotifyChange("dataList")
	public void search(@BindingParam("ownmonth") Datebox ownmonth,
			@BindingParam("city") Combobox city,
			@BindingParam("sclass") Combobox sclass,
			@BindingParam("state") Combobox state,
			@BindingParam("uptime_s") Datebox uptime_s,
			@BindingParam("uptime_e") Datebox uptime_e,
			@BindingParam("emkey") Textbox emkey,
			@BindingParam("emkeytype") Combobox emkeytype,
			@BindingParam("cokey") Textbox cokey) {

		String ownmonthString;
		try {
			ownmonthString = dsc.DatetoSting(ownmonth.getValue(), "yyyyMM");
		} catch (Exception e) {
			ownmonthString = "";
		}

		if (!"".equals(ownmonthString)) {

			sql = "";

			// 所属月份
			sql = sql + " AND ownmonth=" + ownmonthString;

			// 委托城市
			if (city.getSelectedItem() != null) {
				sql = sql + " AND emzt_scity='"
						+ city.getSelectedItem().getLabel() + "'";
			}
			// 委托事件
			if (sclass.getSelectedItem() != null) {
				sql = sql + " AND emzt_class='"
						+ sclass.getSelectedItem().getLabel() + "'";
			}
			// 处理状态
			if (state.getSelectedItem() != null) {
				if (state.getSelectedItem().getValue().equals("2")) {
					sql = sql + " AND emzt_state IN (0,2,4,5,6,7,8,9)";
				} else {
					sql = sql + " AND emzt_state="
							+ state.getSelectedItem().getValue();
				}
			}
			// 委托单日期范围
			String uptime_sString;
			try {
				uptime_sString = dsc.DatetoSting(uptime_s.getValue(),
						"yyyy-MM-dd");
			} catch (Exception e) {
				uptime_sString = "";
			}
			String uptime_eString;
			try {
				uptime_eString = dsc.DatetoSting(uptime_e.getValue(),
						"yyyy-MM-dd");
			} catch (Exception e) {
				uptime_eString = "";
			}
			if (!"".equals(uptime_sString) && !"".equals(uptime_eString)) {
				sql = sql + " AND emzt_uptime BETWEEN '" + uptime_sString
						+ "' AND DATEADD(d,1,'" + uptime_eString + "')";
			}

			// 员工查询
			if (!"".equals(emkey.getValue())
					&& emkeytype.getSelectedItem() != null) {
				if (emkeytype.getSelectedItem().getValue().equals("姓名")) {
					sql = sql + " AND emzt_name LIKE '%" + emkey.getValue()
							+ "%'";
				} else if (emkeytype.getSelectedItem().getValue()
						.equals("员工编号")) {
					sql = sql + " AND gid=" + emkey.getValue();
				} else if (emkeytype.getSelectedItem().getValue().equals("身份证")) {
					sql = sql + " AND emzt_idcard='" + emkey.getValue() + "'";
				} else if (emkeytype.getSelectedItem().getValue()
						.equals("委托方雇员编号")) {
					sql = sql + " AND emzt_wtgid='" + emkey.getValue() + "'";
				} else if (emkeytype.getSelectedItem().getValue()
						.equals("智翼通序号")) {
					sql = sql + " AND emzt_zytid=" + emkey.getValue();
				}
			}

			// 公司查询
			if (!"".equals(cokey.getValue())) {
				CheckStringService ch = new CheckStringImpl();
				if (ch.isNum(cokey.getValue())) {
					sql = sql + " AND cid=" + cokey.getValue();
				} else {
					sql = sql + " AND emzt_company LIKE '%" + cokey.getValue()
							+ "%'";
				}
			}

			sql = sql + " AND emzt_state<>10";
			dataList = sbll.getEmZYTList(sql);
		} else {
			// 弹出提示
			Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 委托单查询
	@Command("detail")
	public void detail(@BindingParam("emztM") EmZYTModel emztM) {
		String url;
		if (emztM.getEmzt_class().equals("终止")) {
			url = "EmZYT_DetailDimission.zul";
		} else {
			url = "EmZYT_DetailNew.zul";
		}

		Map map = new HashMap();
		map.put("emztM", emztM);
		map.put("way", 1);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 表格每行checkbox全选
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				// 判断是否可以选中
				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行

				// 判断是否可以选中
				Checkbox ckb = (Checkbox) gd.getCell(i, 21).getChildren()
						.get(0);
				ckb.setChecked(check);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 联系员工
	@Command("contact")
	@NotifyChange("dataList")
	public void contact(@BindingParam("emztM") EmZYTModel emztM) {
		String url;
		url = "EmZYT_DetailContact.zul";

		Map map = new HashMap();
		map.put("emztM", emztM);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		dataList = sbll.getEmZYTList(sql);
	}

	// 状态处理
	@Command("declareState")
	@NotifyChange("dataList")
	public void declareState(@BindingParam("state") int state,
			@BindingParam("dataGrid") Grid dataGrid) {
		String[] message;
		int j = 0;
		int k = 0;
		String msg;

		// 选中项目
		for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) dataGrid.getCell(i, 21).getChildren()
						.get(0); // 获取checkbox

				if (ckb.isChecked()) {
					// 判断是否选中
					Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行

					int id = ((EmZYTModel) row.getValue()).getId();
					int tapr_id = ((EmZYTModel) row.getValue())
							.getEmzt_tapr_id();
					String emzt_class = ((EmZYTModel) row.getValue())
							.getEmzt_class();// 原状态;
					int o_state = ((EmZYTModel) row.getValue()).getId();// 原状态;

					EmZYTModel m = new EmZYTModel();
					m.setId(id);
					m.setEmzt_tapr_id(tapr_id);
					m.setEmzt_declarename(username);
					m.setEmzt_state(state);

					// 选中几条数据
					k = k + 1;

					// 判断操作类型
					if (state == 1) {
						// 新增且未选报价单的数据不能操作已处理
						if (o_state == 10 && "新增".equals(emzt_class)) {
							message = new String[1];
							message[0] = "0";
						} else {
							// 调用方法更新数据
							message = obll.declareEmZYTUpdate(m);
						}
					} else if (state == 4 && "新增".equals(emzt_class)) {
						message = obll.declareEmZYTUpdateRZW(m);
					} else if (state == 3) {
						message = obll.declareEmZYTStop(m);
					} else if (state == 11) {
						message = obll.declareEmZYTReturn(m);
					} else {
						message = new String[1];
						message[0] = "0";
					}

					// 判断是否成功
					if (message[0].equals("1")) {
						j = j + 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (k == 0) {
			// 弹出提示
			Messagebox.show("请选择数据！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			if (j == k) {
				msg = "操作成功！";
			} else {
				msg = "部分数据操作不成功，请检查！";
			}
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						dataList = sbll.getEmZYTList(sql);
					}
				}
			};
			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		}

	}

	// 员工新增
	@Command("addnew")
	@NotifyChange("dataList")
	public void addnew(@BindingParam("emztM") EmZYTModel emztM) {
		String url;
		url = "../Embase/Embase_Addsec.zul";

		Map map = new HashMap();
		map.put("emztM", emztM);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		dataList = sbll.getEmZYTList(sql);
	}

	// 员工入职
	@Command("addOnBoard")
	@NotifyChange("dataList")
	public void addOnBoard(@BindingParam("emztM") EmZYTModel emztM) {
		String url;
		url = "../Taskflow/EmBaseMenulists.zul";

		// 通过智翼通接口数据获取EmBaseNotIn表id
		String idcard = "";
		if (!"".equals(emztM.getEmzt_t_idcard()) && emztM.getEmzt_t_idcard()!=null) {
			idcard = emztM.getEmzt_t_idcard();
		} else {
			idcard = emztM.getEmzt_idcard();
		}
		int[] did = sbll.getEmBaseNotInId(idcard);

		Map map = new HashMap();
		map.put("emztM", emztM);
		map.put("daid", did[0]);
		map.put("id", did[1]);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		dataList = sbll.getEmZYTList(sql);
	}

	// 打开业务中心
	@Command("openbucenter")
	public void openbucenter(@BindingParam("gid") Integer gid) {
		
		//获取embaId
		Integer embaId;
		EmbaseListBll embasebll = new EmbaseListBll();
		embaId=embasebll.searchembaselist(String.valueOf(gid)).get(0).getEmba_id();
		
		Map map = new HashMap<>();
		map.put("daid", gid);
		map.put("embaId", embaId);
		Window window = (Window) Executions.createComponents(
				"../SystemControl/EmBuCenterInfoList.zul", null, map);
		window.doModal();
	}

	public List getCityList() {
		return cityList;
	}

	public void setCityList(List cityList) {
		this.cityList = cityList;
	}

	public List<EmZYTModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmZYTModel> dataList) {
		this.dataList = dataList;
	}

	public List getSclassList() {
		return sclassList;
	}

	public void setSclassList(List sclassList) {
		this.sclassList = sclassList;
	}

	public List getStateList() {
		return stateList;
	}

	public void setStateList(List stateList) {
		this.stateList = stateList;
	}

	public List getEmkeyTypeList() {
		return emkeyTypeList;
	}

	public void setEmkeyTypeList(List emkeyTypeList) {
		this.emkeyTypeList = emkeyTypeList;
	}

	public String getNowmonth() {
		return nowmonth;
	}

	public void setNowmonth(String nowmonth) {
		this.nowmonth = nowmonth;
	}

	public Date getD_nowmonth() {
		return d_nowmonth;
	}

	public void setD_nowmonth(Date d_nowmonth) {
		this.d_nowmonth = d_nowmonth;
	}

}
