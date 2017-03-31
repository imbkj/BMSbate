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
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.CheckStringService;

import Model.EmZYTFeedbackFileModel;
import Model.EmZYTFeedbackModel;
import Model.EmZYTModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.MonthListUtil;
import Util.UserInfo;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.EmbaseListBll;

public class EmZYT_FeeListController {
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

	// 获取当前所属月份
	Date nowDate = new Date(); // 获取当前时间
	private String nowmonth = dsc.DatetoSting(nowDate, "yyyyMM");
	private Date d_nowmonth = dsc.StringtoDate(nowmonth, "yyyyMM");
	private String username = UserInfo.getUsername();
	private MonthListUtil mlu = new MonthListUtil();
	String[] smonth = mlu.getMonthList(false, nowmonth, "h", 7);

	private String sql = "";
	private String ownmonthString = nowmonth;

	private boolean chk_qx=false;
	public EmZYT_FeeListController() {
		cityList = sbll.getZYTCity(); // 委托城市下拉框
		sclassList = sbll.getSclass(); // 委托事件下拉框
		stateList = sbll.getState(3); // 数据状态下拉框
		emkeyTypeList = sbll.getEmkeyType(); // 员工查询类型下拉框
		clientList = sbll.getZYTClient();// 客服代表

		// 获取当月数据
		sql = " AND a.ownmonth=" + nowmonth;
		dataList = sbll.getEmZYTFeeList(sql, nowmonth,
				smonth[smonth.length - 1]);
		
		if (UserInfo.getUsername().equals("林少斌")) {
			chk_qx=true;
		}
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
			@BindingParam("cokey") Textbox cokey,
			@BindingParam("client") Combobox client,
			@BindingParam("s_cidgid") Combobox s_cidgid,
			@BindingParam("ifsame") Combobox ifsame,
			@BindingParam("chkstate") Combobox chkstate,
			@BindingParam("balance") Combobox balance) {

		try {
			ownmonthString = dsc.DatetoSting(ownmonth.getValue(), "yyyyMM");
		} catch (Exception e) {
			ownmonthString = "";
		}

		if (!"".equals(ownmonthString)) {
			// 员工查询
			sql = "";
			if (!"".equals(emkey.getValue())
					&& emkeytype.getSelectedItem() != null) {
				if (emkeytype.getSelectedItem().getValue().equals("姓名")) {
					sql = sql + " AND a.emzt_name LIKE ''%" + emkey.getValue()
							+ "%''";
				} else if (emkeytype.getSelectedItem().getValue()
						.equals("员工编号")) {
					sql = sql + " AND a.gid=" + emkey.getValue();
				} else if (emkeytype.getSelectedItem().getValue().equals("身份证")) {
					sql = sql + " AND a.emzt_idcard=''" + emkey.getValue() + "''";
				} else if (emkeytype.getSelectedItem().getValue()
						.equals("委托方雇员编号")) {
					sql = sql + " AND a.emzt_wtgid=''" + emkey.getValue() + "''";
				} else if (emkeytype.getSelectedItem().getValue()
						.equals("智翼通序号")) {
					sql = sql + " AND a.emzt_zytid=" + emkey.getValue();
				} else {
					// 弹出提示
					Messagebox.show("请选择“员工查询”类型！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				// 所属月份
				if (!"".equals(ownmonthString)) {
					sql = sql + " AND a.ownmonth=" + ownmonthString;
				}
				// 委托城市
				if (city.getSelectedItem() != null) {
					if (!"全部".equals(city.getSelectedItem().getLabel())) {
						sql = sql + " AND a.emzt_scity=''"
								+ city.getSelectedItem().getLabel() + "''";
					}
				}
				// 客服
				if (client.getSelectedItem() != null
						&& !"".equals(client.getSelectedItem().getLabel())) {
					sql = sql + " AND a.emzt_client=''"
							+ client.getSelectedItem().getLabel() + "''";
				}
				// 委托事件
				if (sclass.getSelectedItem() != null) {
					if (!"全部".equals(sclass.getSelectedItem().getLabel())) {
						sql = sql + " AND a.emzt_class=''"
								+ sclass.getSelectedItem().getLabel() + "''";
					}
				}
				// 处理状态
				if (state.getSelectedItem() != null) {
					if (!"全部".equals(state.getSelectedItem().getLabel())) {
						if (state.getSelectedItem().getValue().equals("2")) {
							sql = sql
									+ " AND a.emzt_state IN (0,2,4,5,6,7,8,9)";
						} else if (state.getSelectedItem().getValue()
								.equals("100")) {// 非退单非未处理数据
							sql = sql + " AND a.emzt_state not IN (3,11,10)";
						} else {
							sql = sql + " AND a.emzt_state="
									+ state.getSelectedItem().getValue();
						}
					}
				}

				if (chkstate.getSelectedItem() != null) {
					if ("未核对".equals(chkstate.getSelectedItem().getLabel())) {
						sql = sql + " AND a.emzt_chkstate=0";
					} else if ("已核对".equals(chkstate.getSelectedItem()
							.getLabel())) {
						sql = sql + " AND a.emzt_chkstate=1";
					}
				}
				if (balance.getSelectedItem() != null) {
					if ("有差额".equals(balance.getSelectedItem().getLabel())) {
						sql = sql + " AND a.balance!=0";
					} else if ("无差额".equals(balance.getSelectedItem()
							.getLabel())) {
						sql = sql + " AND a.balance=0";
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
					sql = sql + " AND a.emzt_uptime BETWEEN ''" + uptime_sString
							+ "'' AND DATEADD(d,1,''" + uptime_eString + "'')";
				}

				// 公司查询
				if (!"".equals(cokey.getValue())) {
					CheckStringService ch = new CheckStringImpl();
					if (ch.isNum(cokey.getValue())) {
						sql = sql + " AND a.cid=" + cokey.getValue();
					} else {
						sql = sql + " AND a.emzt_company LIKE ''%"
								+ cokey.getValue() + "%''";
					}
				}

				// 编号筛选
				if (s_cidgid.getSelectedItem() != null
						&& !"".equals(s_cidgid.getSelectedItem().getValue())) {
					int cidgid = Integer.parseInt(s_cidgid.getSelectedItem()
							.getValue().toString());
					if (cidgid == 1) {
						sql = sql + " AND a.gid is null";
					} else if (cidgid == 2) {
						sql = sql + " AND a.cid is null";
					} else if (cidgid == 3) {
						sql = sql + " AND (a.cid is null or a.gid is null)";
					} else if (cidgid == 4) {
						sql = sql + " AND a.cid is null AND a.gid is null";
					} else if (cidgid == 5) {
						sql = sql
								+ " AND a.cid is not null AND a.gid is not null";
					}
				}

				// 一个员工是否有多条委托单
				if (ifsame.getSelectedItem() != null
						&& !"全部".equals(ifsame.getSelectedItem().getLabel())) {
					if ("是".equals(ifsame.getSelectedItem().getLabel())) {
						sql = sql + " AND a.emzt_ifsame=1";
					} else if ("否".equals(ifsame.getSelectedItem().getLabel())) {
						sql = sql + " AND a.emzt_ifsame<>1";
					}
				}
			}
			dataList = sbll.getEmZYTFeeList(sql, ownmonthString,
					smonth[smonth.length - 1]);
		} else {
			if ("".equals(ownmonthString)) {
				Messagebox.show("请选择所属月份！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);

			}
		}
	}

	// 查询同一员工当月多条委托单数据
	@Command("searchIfsame")
	@NotifyChange({ "dataList" })
	public void searchIfsame(@BindingParam("emztM") EmZYTModel emztM) {
		sql = "";
		sql = sql + " AND emzt_ifsame=1 AND ownmonth="
				+ String.valueOf(emztM.getOwnmonth()) + " AND emzt_name='"
				+ emztM.getEmzt_name() + "'";
		dataList = sbll.getEmZYTFeeList(sql,
				String.valueOf(emztM.getOwnmonth()), smonth[smonth.length - 1]);
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

	// 表格每行checkbox全选(当前页)
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") Checkbox check,@BindingParam("a") Checkbox cka) {
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

	// 表格每行checkbox全选(所有页)
	@Command("gdallselect2")
	public void gdallselect2(@BindingParam("grid") Grid gd,
			@BindingParam("check") Checkbox check,@BindingParam("a") Checkbox cka) {
		
		if (check.getId().equals("cka2")) {

			for (int i = 0; i < dataList.size(); i++) {
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
	@Command("declareChkState")
	@NotifyChange("dataList")
	public void declareChkState(@BindingParam("state") int state) {
		String[] message;
		int j = 0;
		int k = 0;
		String msg;

		// 选中项目
		for(EmZYTModel m:dataList){
			try {

				if (m.isEmzt_ischecked()) {
					// 判断是否选中
					m.setEmzt_chkstate(state);

					// 选中几条数据
					k = k + 1;

					// 判断操作类型
					//if (state == 1) {
						message = obll.upEmZYTChkState(m);
					//} else {
					//	message = new String[1];
					//	message[0] = "0";
					//}

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

			dataList = sbll.getEmZYTFeeList(sql, ownmonthString,
					smonth[smonth.length - 1]);
			// 弹出提示
			Messagebox.show(msg, "操作提示", Messagebox.OK, Messagebox.INFORMATION);
		}

	}

	// 查询多个委托单
	@Command("detailAll")
	public void detailAll()
			throws InterruptedException {
		String emzt_id = "0";

		// 选中项目
		for(EmZYTModel m:dataList){
			try {
				if (m.isEmzt_ischecked()) {
					// 判断是否选中
					emzt_id = emzt_id
							+ ","
							+ String.valueOf(m
									.getId());
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

	// 导出Excel
	@Command("downfile")
	public void downfile() {
		try {
			List<EmZYTModel> fbDataList = new ArrayList<EmZYTModel>();

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

			// 选中项目
			for(EmZYTModel m:dataList){
				try {
					if (m.isEmzt_ischecked()) {
						// 判断是否选中,并赋值
						// Row row = (Row)
						// dataGrid.getRows().getChildren().get(i); // 获取Gird的行
						fbDataList.add(m);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (fbDataList.size() == 0) {// 如果等于0,则导出当前查询条件下的所有数据
				fbDataList = dataList;
			}

			if (fbDataList.size() > 0) {

				// 创建Excel
				WritableWorkbook wwb = Workbook.createWorkbook(new File(
						absolutePath + "EMZYT/File/Download/ChkData/"
								+ filename));
				// 生成工作表
				WritableSheet sheet = wwb.createSheet("Sheet1", 0);
				Label label = null;
				// 设置字体格式
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体
				WritableCellFormat wcf = new WritableCellFormat(wf);

				String ifsame;

				label = new jxl.write.Label(0, 0, "所属月份", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(1, 0, "公司编号", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(2, 0, "公司名称", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(3, 0, "客服", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(4, 0, "员工编号", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(5, 0, "姓名", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(6, 0, "身份证", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(7, 0, "事件", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(8, 0, "委托单日期", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(9, 0, "智翼通应收", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(10, 0, "系统应收", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(11, 0, "差额", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(12, 0, "状态", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(13, 0, "核对状态", wcf);
				sheet.addCell(label);
				label = new jxl.write.Label(14, 0, "有无重复", wcf);
				sheet.addCell(label);

				for (int i = 0; i < fbDataList.size(); i++) {
					label = new Label(0, i + dataRow, String.valueOf(fbDataList
							.get(i).getOwnmonth()));
					sheet.addCell(label);
					label = new Label(1, i + dataRow, String.valueOf(fbDataList
							.get(i).getCid()));
					sheet.addCell(label);
					label = new Label(2, i + dataRow, fbDataList.get(i)
							.getEmzt_company());
					sheet.addCell(label);
					label = new Label(3, i + dataRow, fbDataList.get(i)
							.getEmzt_client());
					sheet.addCell(label);
					label = new Label(4, i + dataRow, String.valueOf(fbDataList
							.get(i).getGid()));
					sheet.addCell(label);
					label = new Label(5, i + dataRow, fbDataList.get(i)
							.getEmzt_name());
					sheet.addCell(label);
					label = new Label(6, i + dataRow, fbDataList.get(i)
							.getEmzt_idcard());
					sheet.addCell(label);
					label = new Label(7, i + dataRow, fbDataList.get(i)
							.getEmzt_class());
					sheet.addCell(label);
					label = new Label(8, i + dataRow, fbDataList.get(i)
							.getEmzt_uptime());
					sheet.addCell(label);
					label = new Label(9, i + dataRow, String.valueOf(fbDataList
							.get(i).getEmzt_sbtotal()
							.add(fbDataList.get(i).getEmzt_housetotal())
							.add(fbDataList.get(i).getEmzt_sbbj())
							.add(fbDataList.get(i).getEmzt_housebj())));
					sheet.addCell(label);
					label = new Label(10, i + dataRow, String.valueOf(fbDataList
							.get(i).getEmfi_total()));
					sheet.addCell(label);
					label = new Label(11, i + dataRow,
							String.valueOf(fbDataList.get(i).getBalance()));
					sheet.addCell(label);
					label = new Label(12, i + dataRow, fbDataList.get(i)
							.getState());
					sheet.addCell(label);
					label = new Label(13, i + dataRow, fbDataList.get(i)
							.getChkstate());
					sheet.addCell(label);
					ifsame = "";
					if (fbDataList.get(i).getEmzt_ifsame() == 1) {
						ifsame = "有";
					}
					label = new Label(14, i + dataRow, ifsame);
					sheet.addCell(label);

				}

				// 写入数据
				wwb.write();
				// 关闭文件
				wwb.close();

				FileOperate.download("EMZYT/File/Download/ChkData/" + filename);
			} else {
				// 弹出提示
				Messagebox.show("导出有问题！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
		dataList = sbll.getEmZYTFeeList(sql, ownmonthString,
				smonth[smonth.length - 1]);
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

	public List getClientList() {
		return clientList;
	}

	public void setClientList(List clientList) {
		this.clientList = clientList;
	}

	public boolean isChk_qx() {
		return chk_qx;
	}

	public void setChk_qx(boolean chk_qx) {
		this.chk_qx = chk_qx;
	}

}
