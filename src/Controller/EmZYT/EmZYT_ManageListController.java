package Controller.EmZYT;

import impl.CheckStringImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
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

import bll.CoBase.CoBase_SelectBll;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.EmbaseListBll;

import Model.CoBaseModel;
import Model.EmZYTFeedbackFileModel;
import Model.EmZYTFeedbackModel;
import Model.EmZYTModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;

public class EmZYT_ManageListController {
	private DateStringChange dsc = new DateStringChange();
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();

	private List cityList; // 委托城市
	private List<EmZYTModel> dataList;
	private List<EmZYTFeedbackModel> fbDataList;
	private List sclassList; // 委托事件
	private List stateList; // 委托处理状态
	private List emkeyTypeList; // 员工查询类型
	private List clientList; // 客服代表

	private boolean adType = true; // 是否可以操作调整类型选择

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	private Date d_nowmonth = dsc.StringtoDate(nowmonth, "yyyyMM");
	private String username = UserInfo.getUsername();

	private String sql = "";

	public EmZYT_ManageListController() throws Exception {
		cityList = sbll.getZYTCity(); // 委托城市下拉框
		sclassList = sbll.getSclass(); // 委托事件下拉框
		stateList = sbll.getState(1); // 数据状态下拉框
		emkeyTypeList = sbll.getEmkeyType(); // 员工查询类型下拉框
		clientList = sbll.getZYTClient();// 客服代表

		// 获取当月数据
		sql = " AND ownmonth=" + nowmonth;
		dataList = sbll.getEmZYTList(sql);
	}

	// 查询数据
	@Command("search")
	@NotifyChange({ "dataList", "adType" })
	public void search(@BindingParam("ownmonth") Datebox ownmonth,
			@BindingParam("city") Combobox city,
			@BindingParam("sclass") Combobox sclass,
			@BindingParam("state") Combobox state,
			@BindingParam("uptime_s") Datebox uptime_s,
			@BindingParam("uptime_e") Datebox uptime_e,
			@BindingParam("emkey") Textbox emkey,
			@BindingParam("emkeytype") Combobox emkeytype,
			@BindingParam("cokey") Textbox cokey,
			@BindingParam("client") Combobox client,
			@BindingParam("s_cidgid") Combobox s_cidgid,
			@BindingParam("ifsame") Combobox ifsame) {

		String ownmonthString;
		try {
			ownmonthString = dsc.DatetoSting(ownmonth.getValue(), "yyyyMM");
		} catch (Exception e) {
			ownmonthString = "";
		}

		// 员工查询
		sql = "";
		if (!"".equals(emkey.getValue()) && emkeytype.getSelectedItem() != null) {
			if (emkeytype.getSelectedItem().getValue().equals("姓名")) {
				sql = sql + " AND emzt_name LIKE '%" + emkey.getValue() + "%'";
			} else if (emkeytype.getSelectedItem().getValue().equals("员工编号")) {
				sql = sql + " AND gid=" + emkey.getValue();
			} else if (emkeytype.getSelectedItem().getValue().equals("身份证")) {
				sql = sql + " AND emzt_idcard='" + emkey.getValue() + "'";
			} else if (emkeytype.getSelectedItem().getValue().equals("委托方雇员编号")) {
				sql = sql + " AND emzt_wtgid='" + emkey.getValue() + "'";
			} else if (emkeytype.getSelectedItem().getValue().equals("智翼通序号")) {
				sql = sql + " AND emzt_zytid=" + emkey.getValue();
			} else {
				// 弹出提示
				Messagebox.show("请选择“员工查询”类型！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			// 所属月份
			if (!"".equals(ownmonthString)) {
				sql = sql + " AND ownmonth=" + ownmonthString;
			}
			// 委托城市
			if (city.getSelectedItem() != null) {
				if (!"全部".equals(city.getSelectedItem().getLabel())) {
					sql = sql + " AND emzt_scity='"
							+ city.getSelectedItem().getLabel() + "'";
				}
			}
			// 客服
			if (client.getSelectedItem() != null
					&& !"".equals(client.getSelectedItem().getLabel())) {
				sql = sql + " AND emzt_client='"
						+ client.getSelectedItem().getLabel() + "'";
			}
			// 委托事件
			if (sclass.getSelectedItem() != null) {
				if (!"全部".equals(sclass.getSelectedItem().getLabel())) {
					sql = sql + " AND emzt_class='"
							+ sclass.getSelectedItem().getLabel() + "'";
				}

				// 控制选择调整类型按钮
				if (sclass.getSelectedItem().getLabel().equals("调整")) {
					adType = false;
				} else {
					adType = true;
				}
			} else {
				adType = true;
			}
			// 处理状态
			if (state.getSelectedItem() != null) {
				if (!"全部".equals(state.getSelectedItem().getLabel())) {
					if (state.getSelectedItem().getValue().equals("2")) {
						sql = sql + " AND emzt_state IN (0,2,4,5,6,7,8,9)";
					} else {
						sql = sql + " AND emzt_state="
								+ state.getSelectedItem().getValue();
					}
				}
			}
			// 委托单日期范围
			String uptime_sString = "";
			try {
				if (uptime_s.getValue() != null) {
					uptime_sString = dsc.DatetoSting(uptime_s.getValue(),
							"yyyy-MM-dd");
				}
			} catch (Exception e) {
				uptime_sString = "";
			}
			String uptime_eString = "";
			try {
				if (uptime_e.getValue() != null) {
					uptime_eString = dsc.DatetoSting(uptime_e.getValue(),
							"yyyy-MM-dd");
				}
			} catch (Exception e) {
				uptime_eString = "";
			}
			if (!"".equals(uptime_sString) && !"".equals(uptime_eString)) {
				sql = sql + " AND emzt_uptime BETWEEN '" + uptime_sString
						+ "' AND DATEADD(d,1,'" + uptime_eString + "')";
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

			// 编号筛选
			if (s_cidgid.getSelectedItem() != null
					&& !"".equals(s_cidgid.getSelectedItem().getValue())) {
				int cidgid = Integer.parseInt(s_cidgid.getSelectedItem()
						.getValue().toString());
				if (cidgid == 1) {
					sql = sql + " AND gid is null";
				} else if (cidgid == 2) {
					sql = sql + " AND cid is null";
				} else if (cidgid == 3) {
					sql = sql + " AND (cid is null or gid is null)";
				} else if (cidgid == 4) {
					sql = sql + " AND cid is null AND gid is null";
				} else if (cidgid == 5) {
					sql = sql + " AND cid is not null AND gid is not null";
				}
			}

			// 一个员工是否有多条委托单
			if (ifsame.getSelectedItem() != null
					&& !"全部".equals(ifsame.getSelectedItem().getLabel())) {
				if ("是".equals(ifsame.getSelectedItem().getLabel())) {
					sql = sql + " AND emzt_ifsame=1";
				} else if ("否".equals(ifsame.getSelectedItem().getLabel())) {
					sql = sql + " AND emzt_ifsame<>1";
				}
			}
		}
		dataList = sbll.getEmZYTList(sql);
	}

	// 查询同一员工当月多条委托单数据
	@Command("searchIfsame")
	@NotifyChange({ "dataList" })
	public void searchIfsame(@BindingParam("emztM") EmZYTModel emztM) {
		sql = "";
		sql = sql + " AND emzt_ifsame=1 AND ownmonth="
				+ String.valueOf(emztM.getOwnmonth()) + " AND emzt_name='"
				+ emztM.getEmzt_name() + "'";
		dataList = sbll.getEmZYTList(sql);
	}

	// 委托单查询
	@Command("detail")
	public void detail(@BindingParam("emztM") EmZYTModel emztM)
			throws InterruptedException {
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
			@BindingParam("check") Checkbox check,
			@BindingParam("a") Checkbox cka) {

		if (check.getId().equals("cka")) {

			Integer pageSize = gd.getPageSize();
			Integer activePage = gd.getActivePage() + 1;
			Integer page = gd.getPageCount();
			Integer start = 0;
			Integer size = 0;

			if (dataList.size() < pageSize) {
				size = dataList.size();
			} else {
				start = gd.getActivePage() * pageSize;
				if (activePage.equals(page)) {
					if (dataList.size() < activePage * pageSize) {
						size = dataList.size();
					} else {
						size = activePage * pageSize;
					}
				} else {
					size = activePage * pageSize;
				}

			}

			for (int i = start; i < size; i++) {
				dataList.get(i).setEmzt_ischecked(cka.isChecked());
				BindUtils.postNotifyChange(null, null, dataList.get(i),
						"emzt_ischecked");
			}
		} else {
			if (!check.isChecked()) {
				cka.setChecked(false);
			}
		}
	}

	// 状态处理
	@Command("declareState")
	@NotifyChange("dataList")
	public void declareState(@BindingParam("state") int state) {
		String[] message;
		int j = 0;
		int k = 0;
		String msg;

		// 选中项目
		for (EmZYTModel m : dataList) {
			try {

				if (m.isEmzt_ischecked()) {

					String emzt_class = m.getEmzt_class();// 类型;
					int o_state = m.getEmzt_state();// 原状态;

					m.setEmzt_declarename(username);
					m.setEmzt_state(state);

					// 选中几条数据
					k = k + 1;

					// 判断操作类型
					if (state == 1) {
						// 新增且未选报价单的数据不能操作已处理
						/*
						 * if (o_state == 10 && "新增".equals(emzt_class)) {
						 * message = new String[1]; message[0] = "0"; } else {
						 */
						// 调用方法更新数据
						message = obll.declareEmZYTUpdate(m);
						// }
					} else if (state == 3 || state == 11) {
						// 调用方法更新数据
						message = obll.declareEmZYTStop(m);
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
			/*
			 * EventListener<ClickEvent> clickListener = new
			 * EventListener<Messagebox.ClickEvent>() { public void
			 * onEvent(ClickEvent event) throws Exception { if
			 * (Messagebox.Button.OK.equals(event.getButton())) { dataList =
			 * sbll.getEmZYTList(sql); } } };
			 */
			dataList = sbll.getEmZYTList(sql);
			// 弹出提示
			Messagebox.show(msg, "操作提示", Messagebox.OK, Messagebox.INFORMATION);
		}

	}

	// 服务费调整
	@Command("feeAdjust")
	@NotifyChange("dataList")
	public void feeAdjust() throws InterruptedException {
		int k = 0;
		int l_cid = 0;
		int chk = 0;
		String emzt_id = "0";

		// 选中项目
		for (EmZYTModel m : dataList) {
			try {

				if (m.isEmzt_ischecked()) {
					// 判断是否选中

					int id = m.getId();
					int gid = m.getGid();
					int cid = m.getCid();
					String emzt_class = m.getEmzt_class();
					int state = m.getEmzt_state();

					if (!"服务费调整".equals(emzt_class)) {
						chk = 1;
						break;
					}

					// 判断gid,cid是否都补充完整
					if (gid > 0 && cid > 0 && "服务费调整".equals(emzt_class)
							&& state == 10) {
						emzt_id = emzt_id + "," + String.valueOf(m.getId());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (chk == 0) {
			// 弹出操作页面
			String url;
			url = "EmZYT_FeeAdjust.zul";

			Map map = new HashMap();
			map.put("emzt_id", emzt_id);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			dataList = sbll.getEmZYTList(sql);

		} else { // 弹出提示
			Messagebox.show("只能操作服务费调整的数据！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 社保基数调整
	@Command("shebaoSalary")
	@NotifyChange("dataList")
	public void shebaoSalary() throws InterruptedException {
		int k = 0;
		int l_cid = 0;
		int chk = 0;
		String emzt_id = "0";

		// 选中项目
		for (EmZYTModel m : dataList) {
			try {

				if (m.isEmzt_ischecked()) {
					// 判断是否选中
					int id = m.getId();
					int gid = m.getGid();
					int cid = m.getCid();
					String emzt_class = m.getEmzt_class();
					int state = m.getEmzt_state();

					if (!"社保基数调整".equals(emzt_class)) {
						chk = 1;
						break;
					}

					// 判断gid,cid是否都补充完整
					if (gid > 0 && cid > 0 && "社保基数调整".equals(emzt_class)
							&& state == 10) {
						emzt_id = emzt_id + "," + String.valueOf(m.getId());
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (chk == 0) {
			// 弹出操作页面
			String url;
			url = "EmZYT_SheBaoSalaryAll.zul";

			Map map = new HashMap();
			map.put("emzt_id", emzt_id);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			dataList = sbll.getEmZYTList(sql);

		} else { // 弹出提示
			Messagebox.show("只能操作社保基数调整的数据！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 查询多个委托单
	@Command("detailAll")
	public void detailAll() throws InterruptedException {
		String emzt_id = "0";

		// 选中项目
		for (EmZYTModel m : dataList) {
			try {

				if (m.isEmzt_ischecked()) {
					// 判断是否选中
					emzt_id = emzt_id + "," + String.valueOf(m.getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 弹出操作页面
		String url;
		url = "EmZYT_DetailIndex.zul";

		Map map = new HashMap();
		map.put("emzt_id", emzt_id);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 补充编号
	@Command("upCidGid")
	@NotifyChange("dataList")
	public void upCidGid(@BindingParam("emztM") EmZYTModel emztM)
			throws InterruptedException {
		String url;
		url = "EmZYT_UpCidGid.zul";

		Map map = new HashMap();
		map.put("emztM", emztM);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		dataList = sbll.getEmZYTList(sql);

	}

	// 批量选择报价单
	@Command("addnewAll")
	@NotifyChange("dataList")
	public void addnewAll() throws InterruptedException {

		List<EmZYTModel> emztList = new ArrayList<EmZYTModel>();
		int k = 0;

		// 选中项目
		for (EmZYTModel m : dataList) {
			try {

				if (m.isEmzt_ischecked()) {

					int id = m.getId();
					String emzt_class = m.getEmzt_class();// 类型;
					int state = m.getEmzt_state();// 状态;

					// 选中几条数据
					k = k + 1;

					// 判断是否可以操作选择报价单
					if (emzt_class.equals("新增") && state == 10) {
						emztList.add(m);
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

			String url;
			url = "EmZYT_AddNewAll.zul";
			Map map = new HashMap();
			map.put("emztList", emztList);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			dataList = sbll.getEmZYTList(sql);
		}

	}

	// 选择报价单
	@Command("addNew")
	@NotifyChange("dataList")
	public void addNew(@BindingParam("emztM") EmZYTModel emztM,
			@ContextParam(ContextType.VIEW) Component view)
			throws InterruptedException {
		String url;
		Map map = new HashMap();

		if (emztM.getEmzt_class().equals("新增")) {
			// 获取emba_id
			Integer embaId;
			try {
				if (emztM.getEmba_id() != 0) {
					embaId = emztM.getEmba_id();
				} else {
					embaId = sbll
							.getEmbaseInfo(
									" AND emba_state in(2,3) AND emba_idcard='"
											+ emztM.getEmzt_idcard()
											+ "' AND cid=" + emztM.getCid())
							.get(0).getEmba_id();
				}
			} catch (Exception e) {
				embaId = 0;
			}
			emztM.setEmba_id(embaId);

			url = "EmZYT_ConfirmAdd.zul";
			CoBase_SelectBll sebll = new CoBase_SelectBll();
			CoBaseModel cobaM = new CoBaseModel();
			List<CoBaseModel> ListcoM = sebll.getshCobaseinfo(String
					.valueOf(emztM.getCid()));
			if (ListcoM.size() > 0) {
				cobaM = ListcoM.get(0);
			} else {
				// 弹出提示
				Messagebox.show("获取公司数据错误！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			map.put("cobaM", cobaM);
		} else if (emztM.getEmzt_class().equals("终止")) {
			url = "EmZYT_ConfirmDimission.zul";

		} else {
			url = "EmZYT_ConfirmAdjust.zul";
		}

		map.put("emztM", emztM);
		Window window = (Window) Executions.createComponents(url, view, map);
		window.doModal();
		dataList = sbll.getEmZYTList(sql);
	}

	// 选择调整类型
	@Command("adType")
	@NotifyChange("dataList")
	public void adType() throws InterruptedException {

		List<EmZYTModel> emztList = new ArrayList<EmZYTModel>();
		int k = 0;

		// 选中项目
		for (EmZYTModel m : dataList) {
			try {

				if (m.isEmzt_ischecked()) {
					// 判断是否选中
					int id = m.getId();
					String emzt_class = m.getEmzt_class();// 类型;
					int state = m.getEmzt_state();// 状态;

					// 选中几条数据
					k = k + 1;

					// 判断是否可以操作选择报价单
					// if (!"新增".equals(emzt_class) && !"终止".equals(emzt_class))
					// {
					emztList.add(m);
					// }

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (k == 0) {
			// 弹出提示
			Messagebox.show("请选择数据！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {

			String url;
			url = "EmZYT_AdType.zul";
			Map map = new HashMap();
			map.put("emztList", emztList);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			dataList = sbll.getEmZYTList(sql);
		}

	}

	// 打开业务中心
	@Command("openbucenter")
	public void openbucenter(@BindingParam("gid") Integer gid)
			throws InterruptedException {

		// 获取embaId
		Integer embaId;
		EmbaseListBll embasebll = new EmbaseListBll();
		embaId = embasebll.searchembaselist(String.valueOf(gid)).get(0)
				.getEmba_id();

		Map map = new HashMap<>();
		map.put("daid", gid);
		map.put("embaId", embaId);
		Window window = (Window) Executions.createComponents(
				"../SystemControl/EmBuCenterInfoList.zul", null, map);
		window.doModal();
	}

	// 生成反馈文件
	@Command("downfile")
	@NotifyChange({ "dataList" })
	public void downfile(@BindingParam("dataGrid") Grid dataGrid,
			@BindingParam("ownmonth") Datebox ownmonth) {
		try {

			// 更新反馈数据实际日期
			obll.upEmZYTFeedback(Integer.parseInt(dsc.DatetoSting(
					ownmonth.getValue(), "yyyyMM")));

			String filename = ""; // 文件名
			String absolutePath; // 服务器地址
			String filetype = ".xls"; // 文件类型
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);
			int dataCell = 0;
			int dataRow = 1;

			filename = nowtime + filetype;
			absolutePath = FileOperate.getAbsolutePath();

			int k = 0;
			String chk_strString = "0";
			// 选中项目
			for (EmZYTModel m : dataList) {
				try {

					if (m.isEmzt_ischecked()) {
						// 判断是否选中
						chk_strString = chk_strString + ","
								+ String.valueOf(m.getId());

						k = k + 1;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 拼接sql
			String fb_sql = "";
			if (k == 0) {
				fb_sql = "SELECT id FROM EmZYT WHERE emzt_outstate<>1" + sql;
			} else {
				fb_sql = "SELECT id FROM EmZYT WHERE id IN(" + chk_strString
						+ ")";
			}

			// 获取数据
			/*
			 * String fb_str =
			 * "SELECT EmZYT.id AS emzt_id,EmZYT.emzt_zytid,EmZYTFeedback.id AS ezfb_id,EmZYT.emzt_state FROM (SELECT id,emzt_zytid,emzt_state FROM EmZYT WHERE emzt_outstate<>1 AND emzt_state=1 AND emzt_class<>'终止' AND emzt_jlstartBMS IS NOT NULL AND emzt_ylstartBMS IS NOT NULL AND emzt_housestartBMS IS NOT NULL AND id IN("
			 * + fb_sql +
			 * ") UNION ALL SELECT id,emzt_zytid,emzt_state FROM EmZYT WHERE emzt_outstate<>1 AND emzt_state=1 AND emzt_class='终止' AND emzt_jlstopBMS IS NOT NULL AND emzt_ylstopBMS IS NOT NULL AND emzt_housestopBMS IS NOT NULL AND id IN("
			 * + fb_sql +
			 * "))EmZYT LEFT JOIN (SELECT ezfb_zytid,MAX(id)id FROM EmZYTFeedback GROUP BY ezfb_zytid)EmZYTFeedback ON EmZYT.emzt_zytid=EmZYTFeedback.ezfb_zytid WHERE EmZYTFeedback.id IS NOT NULL"
			 * ;
			 */

			String fb_str = "SELECT EmZYT.id AS emzt_id,EmZYT.emzt_zytid,EmZYTFeedback.id AS ezfb_id,EmZYT.emzt_state FROM (SELECT id,emzt_zytid,emzt_state FROM EmZYT WHERE emzt_outstate<>1 AND emzt_state=1 AND id IN("
					+ fb_sql
					+ ") )EmZYT LEFT JOIN (SELECT ezfb_zytid,MAX(id)id FROM EmZYTFeedback GROUP BY ezfb_zytid)EmZYTFeedback ON EmZYT.emzt_zytid=EmZYTFeedback.ezfb_zytid WHERE EmZYTFeedback.id IS NOT NULL";

			fbDataList = sbll
					.getEmZYTFeedbackList(" AND id IN(SELECT ezfb_id FROM ("
							+ fb_str + ")a)");

			if (fbDataList.size() > 0) {
				// 创建excel
				// 读取Excel模板
				Workbook wb = Workbook.getWorkbook(new File(absolutePath
						+ "EMZYT/File/Templet/feedback_templet.xls"));
				// 使用模板创建
				WritableWorkbook wwb = Workbook.createWorkbook(
						new File(absolutePath + "EMZYT/File/Download/FBData/"
								+ filename), wb);
				// 生成工作表
				WritableSheet sheet = wwb.getSheet(0);
				Label label = null;

				for (int i = 0; i < fbDataList.size(); i++) {
					label = new Label(0, i + dataRow, fbDataList.get(i)
							.getEzfb_billmonth());
					sheet.addCell(label);
					label = new Label(1, i + dataRow, String.valueOf(fbDataList
							.get(i).getEzfb_zytid()));
					sheet.addCell(label);
					label = new Label(2, i + dataRow, String.valueOf(fbDataList
							.get(i).getEzfb_listid()));
					sheet.addCell(label);
					label = new Label(3, i + dataRow, fbDataList.get(i)
							.getEzfb_gid());
					sheet.addCell(label);
					label = new Label(4, i + dataRow, fbDataList.get(i)
							.getEzfb_name());
					sheet.addCell(label);
					label = new Label(5, i + dataRow, fbDataList.get(i)
							.getEzfb_cid());
					sheet.addCell(label);
					label = new Label(6, i + dataRow, fbDataList.get(i)
							.getEzfb_company());
					sheet.addCell(label);
					label = new Label(7, i + dataRow, fbDataList.get(i)
							.getEzfb_idcardclass());
					sheet.addCell(label);
					label = new Label(8, i + dataRow, fbDataList.get(i)
							.getEzfb_idcard());
					sheet.addCell(label);
					label = new Label(9, i + dataRow, fbDataList.get(i)
							.getEzfb_class());
					sheet.addCell(label);
					label = new Label(10, i + dataRow, fbDataList.get(i)
							.getEzfb_hbstate());
					sheet.addCell(label);
					label = new Label(11, i + dataRow,
							changeZYTmonth(fbDataList.get(i)
									.getEzfb_hbstartBMS()));
					sheet.addCell(label);
					label = new Label(12, i + dataRow,
							changeZYTmonth(fbDataList.get(i)
									.getEzfb_hbstopBMS()));
					sheet.addCell(label);
					label = new Label(13, i + dataRow, fbDataList.get(i)
							.getEzfb_hbclass());
					sheet.addCell(label);
					label = new Label(14, i + dataRow, fbDataList.get(i)
							.getEzfb_ylstate());
					sheet.addCell(label);
					label = new Label(15, i + dataRow, fbDataList.get(i)
							.getEzfb_ylradixBMS());
					sheet.addCell(label);
					label = new Label(16, i + dataRow, fbDataList.get(i)
							.getEzfb_ylradixBMS());
					sheet.addCell(label);
					label = new Label(17, i + dataRow,
							changeZYTmonth(fbDataList.get(i)
									.getEzfb_ylstartBMS()));
					sheet.addCell(label);
					label = new Label(18, i + dataRow,
							changeZYTmonth(fbDataList.get(i)
									.getEzfb_ylstopBMS()));
					sheet.addCell(label);
					label = new Label(19, i + dataRow, fbDataList.get(i)
							.getEzfb_ylcity());
					sheet.addCell(label);
					label = new Label(20, i + dataRow, fbDataList.get(i)
							.getEzfb_ylop_pm());
					sheet.addCell(label);
					label = new Label(21, i + dataRow, fbDataList.get(i)
							.getEzfb_ylcp_pm());
					sheet.addCell(label);
					label = new Label(22, i + dataRow, fbDataList.get(i)
							.getEzfb_housestate());
					sheet.addCell(label);
					label = new Label(23, i + dataRow, fbDataList.get(i)
							.getEzfb_houseradixBMS());
					sheet.addCell(label);
					label = new Label(24, i + dataRow, fbDataList.get(i)
							.getEzfb_houseradixBMS());
					sheet.addCell(label);
					label = new Label(25, i + dataRow, fbDataList.get(i)
							.getEzfb_housecpp());
					sheet.addCell(label);
					label = new Label(26, i + dataRow, fbDataList.get(i)
							.getEzfb_houseopp());
					sheet.addCell(label);
					label = new Label(27, i + dataRow,
							changeZYTmonth(fbDataList.get(i)
									.getEzfb_housestartBMS()));
					sheet.addCell(label);
					label = new Label(28, i + dataRow,
							changeZYTmonth(fbDataList.get(i)
									.getEzfb_housestopBMS()));
					sheet.addCell(label);
					label = new Label(29, i + dataRow, fbDataList.get(i)
							.getEzfb_housecity());
					sheet.addCell(label);
					label = new Label(30, i + dataRow, fbDataList.get(i)
							.getEzfb_houseop_pm());
					sheet.addCell(label);
					label = new Label(31, i + dataRow, fbDataList.get(i)
							.getEzfb_housecp_pm());
					sheet.addCell(label);

					// 判断与养老还是公积金月份做比较
					if (Float.valueOf(changeZYTmonth(fbDataList.get(i)
							.getEzfb_ylstartBMS())) < Float
							.valueOf(changeZYTmonth(fbDataList.get(i)
									.getEzfb_housestartBMS()))) {

						label = new Label(32, i + dataRow, fbDataList.get(i)
								.getEzfb_ylstate());
						sheet.addCell(label);
						label = new Label(33, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_ylstartBMS()));
						sheet.addCell(label);

						label = new Label(35, i + dataRow, fbDataList.get(i)
								.getEzfb_ylstate());
						sheet.addCell(label);
						label = new Label(36, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_ylstartBMS()));
						sheet.addCell(label);

						label = new Label(38, i + dataRow, fbDataList.get(i)
								.getEzfb_ylstate());
						sheet.addCell(label);
						label = new Label(39, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_ylstartBMS()));
						sheet.addCell(label);

						label = new Label(41, i + dataRow, fbDataList.get(i)
								.getEzfb_ylstate());
						sheet.addCell(label);
						label = new Label(42, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_ylstartBMS()));
						sheet.addCell(label);

					} else {

						label = new Label(32, i + dataRow, fbDataList.get(i)
								.getEzfb_housestate());
						sheet.addCell(label);
						label = new Label(33, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_housestartBMS()));
						sheet.addCell(label);

						label = new Label(35, i + dataRow, fbDataList.get(i)
								.getEzfb_housestate());
						sheet.addCell(label);
						label = new Label(36, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_housestartBMS()));
						sheet.addCell(label);

						label = new Label(38, i + dataRow, fbDataList.get(i)
								.getEzfb_housestate());
						sheet.addCell(label);
						label = new Label(39, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_housestartBMS()));
						sheet.addCell(label);

						label = new Label(41, i + dataRow, fbDataList.get(i)
								.getEzfb_housestate());
						sheet.addCell(label);
						label = new Label(42, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_housestartBMS()));
						sheet.addCell(label);

					}

					// 判断与养老还是公积金月份做比较
					if (Float.valueOf(changeZYTmonth(fbDataList.get(i)
							.getEzfb_ylstopBMS())) > Float
							.valueOf(changeZYTmonth(fbDataList.get(i)
									.getEzfb_housestopBMS()))) {

						label = new Label(32, i + dataRow, fbDataList.get(i)
								.getEzfb_ylstate());
						sheet.addCell(label);
						label = new Label(34, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_ylstopBMS()));
						sheet.addCell(label);

						label = new Label(35, i + dataRow, fbDataList.get(i)
								.getEzfb_ylstate());
						sheet.addCell(label);
						label = new Label(37, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_ylstopBMS()));
						sheet.addCell(label);

						label = new Label(38, i + dataRow, fbDataList.get(i)
								.getEzfb_ylstate());
						sheet.addCell(label);
						label = new Label(40, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_ylstopBMS()));
						sheet.addCell(label);

						label = new Label(41, i + dataRow, fbDataList.get(i)
								.getEzfb_ylstate());
						sheet.addCell(label);
						label = new Label(43, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_ylstopBMS()));
						sheet.addCell(label);

					} else {

						label = new Label(32, i + dataRow, fbDataList.get(i)
								.getEzfb_housestate());
						sheet.addCell(label);
						label = new Label(34, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_housestopBMS()));
						sheet.addCell(label);

						label = new Label(35, i + dataRow, fbDataList.get(i)
								.getEzfb_housestate());
						sheet.addCell(label);
						label = new Label(37, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_housestopBMS()));
						sheet.addCell(label);

						label = new Label(38, i + dataRow, fbDataList.get(i)
								.getEzfb_housestate());
						sheet.addCell(label);
						label = new Label(40, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_housestopBMS()));
						sheet.addCell(label);

						label = new Label(41, i + dataRow, fbDataList.get(i)
								.getEzfb_housestate());
						sheet.addCell(label);
						label = new Label(43, i + dataRow,
								changeZYTmonth(fbDataList.get(i)
										.getEzfb_housestopBMS()));
						sheet.addCell(label);

					}

					label = new Label(44, i + dataRow, fbDataList.get(i)
							.getEzfb_cremark());
					sheet.addCell(label);
					label = new Label(45, i + dataRow, fbDataList.get(i)
							.getEzfb_cremark_class());
					sheet.addCell(label);
					label = new Label(46, i + dataRow, fbDataList.get(i)
							.getEzfb_cremark_content());
					sheet.addCell(label);
					label = new Label(47, i + dataRow, fbDataList.get(i)
							.getEzfb_zytgid());
					sheet.addCell(label);
					label = new Label(48, i + dataRow, fbDataList.get(i)
							.getEzfb_zytcid());
					sheet.addCell(label);
					label = new Label(49, i + dataRow, fbDataList.get(i)
							.getEzfb_ct_gid());
					sheet.addCell(label);
					label = new Label(50, i + dataRow, fbDataList.get(i)
							.getEzfb_ct_cid());
					sheet.addCell(label);
					label = new Label(51, i + dataRow, fbDataList.get(i)
							.getEzfb_city());
					sheet.addCell(label);
					label = new Label(52, i + dataRow, fbDataList.get(i)
							.getEzfb_submittime());
					sheet.addCell(label);
					label = new Label(53, i + dataRow, fbDataList.get(i)
							.getEzfb_feetotal());
					sheet.addCell(label);
					label = new Label(54, i + dataRow, fbDataList.get(i)
							.getEzfb_sb_remark());
					sheet.addCell(label);
					label = new Label(55, i + dataRow, fbDataList.get(i)
							.getEzfb_house_remark());
					sheet.addCell(label);

					label = new Label(56, i + dataRow, fbDataList.get(i)
							.getEzfb_qz_fee());
					sheet.addCell(label);
					label = new Label(57, i + dataRow, fbDataList.get(i)
							.getEzfb_qz_housecp());
					sheet.addCell(label);
					label = new Label(58, i + dataRow, fbDataList.get(i)
							.getEzfb_qz_houseop());
					sheet.addCell(label);
					label = new Label(59, i + dataRow, fbDataList.get(i)
							.getEzfb_qz_sbcp());
					sheet.addCell(label);
					label = new Label(60, i + dataRow, fbDataList.get(i)
							.getEzfb_qz_sbop());
					sheet.addCell(label);
					label = new Label(61, i + dataRow, fbDataList.get(i)
							.getEzfb_qz_file());
					sheet.addCell(label);
					label = new Label(62, i + dataRow, fbDataList.get(i)
							.getEzfb_qz_product());
					sheet.addCell(label);
					label = new Label(63, i + dataRow, fbDataList.get(i)
							.getEzfb_wt_agency());
					sheet.addCell(label);
					label = new Label(64, i + dataRow, fbDataList.get(i)
							.getEzfb_wtremark());
					sheet.addCell(label);

					// 系统反馈原因（非智翼通模板里的字段）
					label = new Label(65, i + dataRow, fbDataList.get(i)
							.getEzfb_yl_failed());
					sheet.addCell(label);
					label = new Label(66, i + dataRow, fbDataList.get(i)
							.getEzfb_house_failed());
					sheet.addCell(label);

				}

				// 写入数据
				wwb.write();
				// 关闭文件
				wwb.close();

				// 更新状态
				obll.upFeedbackDataADF(fb_str, filename, username);

				// 弹出下载页面
				String path = "EMZYT/File/Download/FBData/";

				// 记录生成的反馈文档
				EmZYTFeedbackFileModel ezffM = new EmZYTFeedbackFileModel();
				ezffM.setEzff_filename(filename);
				ezffM.setEzff_fileurl(path + filename);
				ezffM.setEzff_addname(username);

				// 新增数据
				obll.addEmZYTFeedbackFile(ezffM);

				Map map = new HashMap();
				map.put("filename", filename);
				map.put("path", path);
				Window window = (Window) Executions.createComponents(
						"EmZYT_ExcelOut.zul", null, map);
				window.doModal();

				// 刷新数据
				dataList = sbll.getEmZYTList(sql);
			} else {
				// 弹出提示
				Messagebox.show("无匹配数据可生成反馈文件！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 反馈状态处理
	@Command("feedbackState")
	@NotifyChange("dataList")
	public void feedbackState(@BindingParam("state") int state,
			@BindingParam("dataGrid") Grid dataGrid) {

		int k = 0;
		String chk_strString = "0";
		// 选中项目
		for (EmZYTModel m : dataList) {
			try {
				if (m.isEmzt_ischecked()) {
					// 判断是否选中
					chk_strString = chk_strString + ","
							+ String.valueOf(m.getId());

					k = k + 1;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 拼接sql
		String fb_sql = "";
		if (k == 0) {
			fb_sql = "SELECT id FROM EmZYT WHERE 1=1" + sql;
		} else {
			fb_sql = "SELECT id FROM EmZYT WHERE id IN(" + chk_strString + ")";
		}

		String fb_str = "SELECT id FROM EmZYT WHERE emzt_outstate=2 AND id IN ("
				+ fb_sql + ")";

		String[] message = obll.upFBStateSuccess(fb_str, username);

		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// 刷新数据
						dataList = sbll.getEmZYTList(sql);
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 转换月份显示格式
	public String changeZYTmonth(String month) {
		String result;

		if (!"0.00".equals(month) && month != null && !"".equals(month)) {
			String[] m = month.split("-");
			String mm = "00" + m[1];
			result = m[0] + "." + (mm).substring(mm.length() - 2, mm.length());
		} else {
			result = "0.00";
		}

		return result;
	}

	// 刷新账单列表
	@Command("refreshWin")
	public void refreshWin() {
		dataList = sbll.getEmZYTList(sql);
		BindUtils.postNotifyChange(null, null, this, "dataList");
	}

	public List<EmZYTModel> getDataList() {
		return dataList;
	}

	public void setDataList(List<EmZYTModel> dataList) {
		this.dataList = dataList;
	}

	public Date getD_nowmonth() {
		return d_nowmonth;
	}

	public void setD_nowmonth(Date d_nowmonth) {
		this.d_nowmonth = d_nowmonth;
	}

	public List getCityList() {
		return cityList;
	}

	public void setCityList(List cityList) {
		this.cityList = cityList;
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

	public boolean isAdType() {
		return adType;
	}

	public void setAdType(boolean adType) {
		this.adType = adType;
	}

	public List getClientList() {
		return clientList;
	}

	public void setClientList(List clientList) {
		this.clientList = clientList;
	}

}
