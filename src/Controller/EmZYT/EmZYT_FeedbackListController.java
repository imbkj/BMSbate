package Controller.EmZYT;

import impl.CheckStringImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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

import Model.EmZYTFeedbackModel;
import Model.EmZYTModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;

public class EmZYT_FeedbackListController {
	private DateStringChange dsc = new DateStringChange();
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();

	private List cityList; // 委托城市
	private List<EmZYTModel> dataList;
	private List<EmZYTFeedbackModel> fbDataList;
	private List sclassList; // 委托事件
	private List stateList; // 委托处理状态
	private List emkeyTypeList; // 员工查询类型

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	private Date d_nowmonth = dsc.StringtoDate(nowmonth, "yyyyMM");
	private String username = UserInfo.getUsername();

	private String sql = "";

	public EmZYT_FeedbackListController() throws Exception {
		cityList = sbll.getCityName(); // 委托城市下拉框
		sclassList = sbll.getSclass(); // 委托事件下拉框
		stateList = sbll.getOutState(); // 数据状态下拉框
		emkeyTypeList = sbll.getEmkeyType(); // 员工查询类型下拉框

		// 获取当月数据
		sql = " AND emzt_state=1 AND ownmonth=" + nowmonth;
		dataList = sbll.getEmZYTList(sql);
	}

	// 查询数据
	@Command("search")
	@NotifyChange({ "dataList" })
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

			sql = " AND emzt_state=1";

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
				sql = sql + " AND emzt_outstate="
						+ state.getSelectedItem().getValue();
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

			dataList = sbll.getEmZYTList(sql);
		} else {
			// 弹出提示
			Messagebox.show("请选择“所属月份”！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 更新反馈数据
	@Command("upData")
	@NotifyChange({ "dataList" })
	public void upData(@BindingParam("dataGrid") final Grid dataGrid) {
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.OK.equals(event.getButton())) {

					int k = 0;
					String chk_strString = "0";
					// 选中项目
					for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {
						try {
							Checkbox ckb = (Checkbox) dataGrid.getCell(i, 15)
									.getChildren().get(0); // 获取checkbox
							if (ckb.isChecked()) {
								// 判断是否选中
								Row row = (Row) dataGrid.getRows()
										.getChildren().get(i); // 获取Gird的行

								chk_strString = chk_strString
										+ ","
										+ String.valueOf(((EmZYTModel) row
												.getValue()).getId());

								k = k + 1;
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					// 拼接sql
					String fb_sql = "";
					if (k == 0) {
						fb_sql = "SELECT id FROM EmZYT WHERE emzt_outstate<>1"
								+ sql;
					} else {
						fb_sql = "SELECT id FROM EmZYT WHERE id IN("
								+ chk_strString + ")";
					}

					// 更新方法
					String[] message = obll.upFeedbackData(fb_sql);

					if (message[0].equals("1")) {
						EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
							public void onEvent(ClickEvent event)
									throws Exception {
								if (Messagebox.Button.OK.equals(event
										.getButton())) {
								}
							}
						};
						// 弹出提示
						Messagebox
								.show(message[1],
										"操作提示",
										new Messagebox.Button[] { Messagebox.Button.OK },
										Messagebox.INFORMATION, clickListener);
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		};

		Messagebox.show("确认更新当前页面下所有数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION, clickListener);
	}

	// 生成反馈文件
	@Command("downfile")
	@NotifyChange({ "dataList" })
	public void downfile(@BindingParam("dataGrid") Grid dataGrid) {
		try {

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
			for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {
				try {
					Checkbox ckb = (Checkbox) dataGrid.getCell(i, 15)
							.getChildren().get(0); // 获取checkbox
					if (ckb.isChecked()) {
						// 判断是否选中
						Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行

						chk_strString = chk_strString
								+ ","
								+ String.valueOf(((EmZYTModel) row.getValue())
										.getId());

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
			String fb_str = "SELECT EmZYT.id AS emzt_id,EmZYT.emzt_zytid,EmZYTFeedback.id AS ezfb_id,EmZYT.emzt_state FROM (SELECT id,emzt_zytid,emzt_state FROM EmZYT WHERE emzt_outstate<>1 AND emzt_state=1 AND emzt_class<>'终止' AND emzt_jlstartBMS IS NOT NULL AND emzt_ylstartBMS IS NOT NULL AND emzt_housestartBMS IS NOT NULL AND id IN("
					+ fb_sql
					+ ") UNION ALL SELECT id,emzt_zytid,emzt_state FROM EmZYT WHERE emzt_outstate<>1 AND emzt_state=1 AND emzt_class='终止' AND emzt_jlstopBMS IS NOT NULL AND emzt_ylstopBMS IS NOT NULL AND emzt_housestopBMS IS NOT NULL AND id IN("
					+ fb_sql
					+ "))EmZYT LEFT JOIN (SELECT ezfb_zytid,MAX(id)id FROM EmZYTFeedback GROUP BY ezfb_zytid)EmZYTFeedback ON EmZYT.emzt_zytid=EmZYTFeedback.ezfb_zytid WHERE EmZYTFeedback.id IS NOT NULL";

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
				}

				// 写入数据
				wwb.write();
				// 关闭文件
				wwb.close();

				// 更新状态
				obll.upFeedbackDataADF(fb_str, filename, username);

				// 弹出下载页面
				String path = "EMZYT/File/Download/FBData/";

				Map map = new HashMap();
				map.put("filename", filename);
				map.put("path", path);
				Window window = (Window) Executions.createComponents(
						"EmZYT_ExcelOut.zul", null, map);
				window.doModal();

				// 刷新数据
				dataList = sbll.getEmZYTList(sql);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 状态处理
	@Command("feedbackState")
	@NotifyChange("dataList")
	public void feedbackState(@BindingParam("state") int state,
			@BindingParam("dataGrid") Grid dataGrid) {

		int k = 0;
		String chk_strString = "0";
		// 选中项目
		for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) dataGrid.getCell(i, 15).getChildren()
						.get(0); // 获取checkbox
				if (ckb.isChecked()) {
					// 判断是否选中
					Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行

					chk_strString = chk_strString
							+ ","
							+ String.valueOf(((EmZYTModel) row.getValue())
									.getId());

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

	// 表格每行checkbox全选
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				// 判断是否可以选中
				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行

				// 判断是否可以选中
				Checkbox ckb = (Checkbox) gd.getCell(i, 15).getChildren()
						.get(0);
				ckb.setChecked(check);

			} catch (Exception e) {
				e.printStackTrace();
			}
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
}
