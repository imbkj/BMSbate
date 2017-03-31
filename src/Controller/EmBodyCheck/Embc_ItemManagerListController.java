package Controller.EmBodyCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;
import Model.EmBcSetupModel;
import Model.EmBodyCheckModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.Embc_SetupSellectBll;
import bll.EmBodyCheck.ExcelImpl;
import bll.EmBodyCheck.ExcelImpls;
import bll.EmBodyCheck.OutExcelImpl;

public class Embc_ItemManagerListController {
	private EmBcInfo_SelectBll bll = new EmBcInfo_SelectBll();
	private Embc_SetupSellectBll setupbll = new Embc_SetupSellectBll();
	private String sqls = " and ebcl_state in(2,3,4,5,6,8,10,13) and ebcl_flag=1";
	private List<EmBodyCheckModel> bclist = new ListModelList<>();
	private List<EmBcSetupModel> setuplist = new ArrayList<EmBcSetupModel>();
	// 获取客服信息
	private CoLatencyClient_AddBll clientbll = new CoLatencyClient_AddBll();
	private List<String> clientlist = clientbll.getpidLoginlist();

	public Embc_ItemManagerListController() {
		String sql = " and ebcl_bookdate Between '" + changedate(new Date())
				+ "'" + " And '" + changedate(getNextWeek())
				+ "' and ebcl_state=2";
		bclist = bll.getEmBodyCheckInfo(sqls + sql);
		setuplist.add(new EmBcSetupModel());
		setuplist.addAll(setupbll.getEmBcSetupname(""));
	}

	@Command
	@NotifyChange("bclist")
	public void search(@BindingParam("company") String company,
			@BindingParam("name") String name,
			@BindingParam("setup") String setup,
			@BindingParam("items") String items,
			@BindingParam("bookdate") Date bookdate,
			@BindingParam("embctype") String embctype,
			@BindingParam("client") String client,
			@BindingParam("nowstate") String nowstate,
			@BindingParam("empType") String empType) {
		String sql = " and ebcl_flag=1";
		if (company != null && !company.equals("") && company != "") {
			sql = sql + " and embc_shortname like '%" + company + "%'";
		}
		if (name != null && !name.equals("") && name != "") {
			sql = sql + " and embc_name like '%" + name + "%'";
		}
		if (setup != null && !setup.equals("") && setup != "") {
			sql = sql + " and ebcs_hospital like '%" + setup + "%'";
		}
		if (items != null && !items.equals("") && items != "") {
			sql = sql + " and ebcl_items like '%" + items + "%'";
		}
		if (bookdate != null) {
			sql = sql
					+ " and convert(varchar(10),ebcl_bookdate,120)=convert(varchar(10),'"
					+ changedate(bookdate) + "',120)";
		}
		if (embctype != null && !embctype.equals("")) {
			sql = sql + " and ebcl_type='" + embctype + "'";
		}
		if (client != null && !client.equals("") && client != "") {
			sql = sql + " and embc_client ='" + client + "'";
		}
		if (nowstate != null && !nowstate.equals("100")) {
			if (nowstate != null && !nowstate.equals("") && nowstate != ""
					&& !nowstate.equals("-1") && nowstate != "-1") {
				if (nowstate.equals("4")) {
					sql = sql + " and ebcl_state in(4,5)";
				} else {
					sql = sql + " and ebcl_state =" + nowstate;
				}
			}
		} else {
			if (nowstate != null && nowstate.equals("100")) {
				sql = sql + " and ebcl_bookdate Between '"
						+ changedate(new Date()) + "'" + " And '"
						+ changedate(getNextWeek()) + "' and ebcl_state=2";
			}
		}

		if (empType != null && !empType.equals("")) {
			sql = sql + " and empType='" + empType + "'";
		}
		if (sql != null && !sql.equals("")) {
			sqls = sql;
		}
		bclist = bll.getEmBodyCheckInfo(sql);
	}

	// 获取一周后的日期
	private Date getNextWeek() {
		Date date = new Date();//
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		Date after7date = cal.getTime();
		return after7date;
	}

	// 弹出修改页面
	@Command
	@NotifyChange("bclist")
	public void openZUL(@BindingParam("model") EmBodyCheckModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		// map.put("daid", model.getEmbc_id());
		// map.put("id", model.getEmbc_tapr_id());
		Window window = (Window) Executions.createComponents(
				"EmBc_InfoUpdate.zul", null, map);
		window.doModal();
		if (map.get("flag") != null && map.get("flag") == "1") {
			bclist = bll.getEmBodyCheckInfo(sqls);
		}
	}

	// 弹出确认取消页面
	@Command
	@NotifyChange("bclist")
	public void cancelconfirm(@BindingParam("model") EmBodyCheckModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("daid", model.getEmbc_id());
		Window window = (Window) Executions.createComponents(
				"Embc_CancelConfirm.zul", null, map);
		window.doModal();
		if (map.get("flag") != null && map.get("flag") == "1") {
			bclist = bll.getEmBodyCheckInfo(sqls);
		}
	}

	// 弹出批量修改页面
	@Command
	@NotifyChange("bclist")
	public void edit(@BindingParam("gd") Grid gd) {
		String idstr = "";
		List<EmBodyCheckModel> selist = new ArrayList<EmBodyCheckModel>();
		for (EmBodyCheckModel m : bclist) {
			if (m.isChecked()) {
				selist.add(m);
				if (idstr == "") {
					idstr = m.getEbcl_id() + "";
				} else {
					idstr = idstr + "," + m.getEbcl_id();
				}
			}
		}
		if (idstr != null && !idstr.equals("")) {
			if (bll.ifStateSame(idstr)) {
				Map map = new HashMap<>();
				map.put("idstr", idstr);
				map.put("list", selist);
				Window window = (Window) Executions.createComponents(
						"EmBc_Update.zul", null, map);
				window.doModal();
				if (idstr != null && idstr.length() > 0) {
					bclist = bll.getEmBodyCheckInfo(" and ebcl_id in(" + idstr
							+ ")");
				} else {
					bclist = bll.getEmBodyCheckInfo(sqls);
				}
			} else {
				Messagebox.show("选择的数据状态不一样，不能做批量修改", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 打开详细
	@Command
	public void opendetail(@BindingParam("model") EmBodyCheckModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"Embc_DetailInfo.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange("bclist")
	public void sendmsg(@BindingParam("table") String table,
			@BindingParam("tid") String tid,
			@BindingParam("title") String title,
			@BindingParam("client") String client,
			@BindingParam("model") EmBodyCheckModel model) {
		String uname = "";
		if (!UserInfo.getUsername().equals(client)) {
			uname = client;
		}
		Map map = new HashMap<>();
		map.put("type", "体检");// 业务类型:来自WfClass的wfcl_name
		map.put("id", tid);// 业务表id
		map.put("tablename", table);// 业务表名
		map.put("msgname", uname);// 默认收件人,没有默认收件人则为空""
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(uname);
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		map.put("title", title);

		EmbaseModel embase = new EmbaseModel();
		embase.setGid(model.getGid());
		embase.setCid(model.getCid());
		embase.setCoba_company(model.getEmbc_shortname());
		embase.setEmba_worktime(model.getEbcl_bookdate());
		embase.setEmba_name(model.getEmbc_name());
		map.put("embase", embase);
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	// 导出报表
	@Command
	public void outexport() {
		String absolutePath = FileOperate.getAbsolutePath();
		List<EmBodyCheckModel> outlist = new ListModelList<>();
		for (EmBodyCheckModel em : bclist) {
			if (em.isChecked()) {
				if (em.getEmpType()==null || em.getEmpType().equals("")) {
					List<EmBodyCheckModel> el =bll.getEmpType(em.getCid());
					if (el.size()>0) {
						em.setEmpType(el.get(0).getEmpType());
					}
				}
				outlist.add(em);
			}
		}

		if (outlist.size() <= 0) {
			Messagebox.show("请选择需导出的员工", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			String path = absolutePath
					+ "OfficeFile/DownLoad/EmBodyCheck/ManagerFile/";
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String filename = "联系医院" + sdf.format(date) + ".xls";
			// 获取绝对路径
			// System.out.println(path);
			// 创建文件
			File file = new File(path + filename);
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sheetName = "体检信息";
			try {
				String[] title = { "序号", "公司编号", "员工编号", "客服", "客服助理", "公司简称",
						"姓名", "身份证号码", "体检编号", "医院", "体检项目", "费用", "折扣费用",
						"体检类型", "安排体检时间", "福利组领报告时间", "体检地址", "签收人", "签收时间",
						"结算时间", "员工类型" };

				ExcelService exl = new OutExcelImpl(file, sheetName, title,
						outlist);
				exl.writeExcel();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Filedownload.save(file, "xlsx");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// file.delete();
		}
	}

	// 导出联系医院时间
	@Command
	@NotifyChange("bclist")
	public void link(@BindingParam("gd") Grid gd) {
		String idstr = "";
		for (EmBodyCheckModel m : bclist) {
			if (m.isChecked()) {
				if (idstr == "") {
					idstr = m.getEbcl_id() + "";
				} else {
					idstr = idstr + "," + m.getEbcl_id();
				}
			}
		}
		if (idstr != null && !idstr.equals("")) {

		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 导出excel
	@Command("doExcel")
	@NotifyChange("bclist")
	public void doExcel(@BindingParam("gd") Grid gd) {
		String absolutePath = FileOperate.getAbsolutePath();
		List<EmBodyCheckModel> outlist = new ListModelList<>();
		String idstr = "";
		for (EmBodyCheckModel em : bclist) {
			if (em.isChecked()) {
				outlist.add(em);
				idstr = idstr + em.getEbcl_id() + ",";
			}
		}
		if (outlist.size() <= 0) {
			Messagebox.show("请选择需导出的员工", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {

			Integer k = 0;
			for (int i = 0; i < outlist.size(); i++) {
				EmBodyCheckModel model = outlist.get(i);
				if (model.getEbcl_linkdate() == null
						|| model.getEbcl_linkdate().equals("")
						|| model.getEbcl_state() == 2
						|| model.getEbcl_state() == 9) {
					String sql = ",ebcl_linkdate='" + changedate(new Date())
							+ "',ebcl_state=10";
					EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
					String[] str = new String[5];
					Integer km = 0;
					if (model.getEmbc_tapr_id() != null) {
						str = obll.EmBodyCheckEdit(model, sql, "3");
					} else {
						km = obll
								.updateEmbodyChecklist(model.getEbcl_id(), sql);

					}
					// String[] str = obll.EmBodyCheckEdit(model, sql, "3");
					if (str[0] == "1" || km > 0) {
						k = k + 1;
					}
				}
			}

			String path = absolutePath
					+ "OfficeFile/DownLoad/EmBodyCheck/ManagerFile/";
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String filename = "联系医院" + sdf.format(date) + ".xls";
			// 获取绝对路径
			// System.out.println(path);
			// 创建文件
			File file = new File(path + filename);
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sheetName = "体检信息";
			try {
				String[] title = { "过期时效", "通知医院时间", "公司编号", "客服", "姓名",
						"身份证号", "性别", "年龄", "婚姻状况", "体检组合", "体检项目", "适用范围",
						"预约体检时间", "体检地址", "折扣价" };
				ExcelService exl = new ExcelImpl(file, sheetName, title,
						outlist);
				exl.writeExcel();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Filedownload.save(file, "xlsx");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// file.delete();
		}
		if (idstr.length() > 0) {
			idstr = idstr.substring(0, idstr.length() - 1);
		}
		if (idstr != null && idstr.length() > 0) {
			bclist = bll.getEmBodyCheckInfo(" and ebcl_id in(" + idstr + ")");
		} else {
			bclist = bll.getEmBodyCheckInfo(sqls);
		}
	}

	// 导出介绍信
	@Command
	public void doDoc(@BindingParam("gd") Grid gd) {
		EmBodyCheckModel m = new EmBodyCheckModel();
		Integer k = 0;
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 12) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, 12).getChildren().get(0);
				if (cb.isChecked()) {
					k++;
					m = cb.getValue();
				}
			}
		}
		if (k == 0) {
			Messagebox.show("请选择一个员工", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (k > 1) {
			Messagebox
					.show("一次只能选择一个员工", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			// 查询是否有介绍信
			if (m.getEbsa_doc() != null && !m.getEbsa_doc().equals("")) {
				Map map = new HashMap<>();
				map.put("model", m);
				Window window = (Window) Executions.createComponents(
						"Embc_OutDoc.zul", null, map);
				window.doModal();
			} else {
				Messagebox.show("该员工选择该医院地址没有介绍信模板", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
	}

	// 导出后道签收报告名单
	@Command
	@NotifyChange("bclist")
	public void doreport(@BindingParam("gd") Grid gd) {
		String absolutePath = FileOperate.getAbsolutePath();
		List<EmBodyCheckModel> outlist = new ListModelList<>();
		for (EmBodyCheckModel em : bclist) {
			if (em.isChecked()) {
				outlist.add(em);
			}
		}

		if (outlist.size() <= 0) {
			Messagebox.show("请选择需导出的员工", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Integer k = 0;
			for (int i = 0; i < outlist.size(); i++) {
				EmBodyCheckModel model = outlist.get(i);
				if ((model.getEbcl_completedate() == null || model
						.getEbcl_completedate().equals(""))
						&& model.getEbcl_state().equals(3)) {
					String sql = "";
					sql = ",ebcl_state=4";
					sql = sql + ",ebcl_completedate=getdate()";
					model.setOcon("导出体检名单");

					EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
					String[] str = new String[5];
					Integer km = 0;
					if (model.getEmbc_tapr_id() != null) {
						str = obll.EmBodyCheckEdit(model, sql, "3");
					} else {
						km = obll
								.updateEmbodyChecklist(model.getEbcl_id(), sql);

					}
					if (str[0] == "1") {
						k = k + 1;
					} else if (km > 0) {
						k = k + 1;
					}
				}
			}

			String path = absolutePath
					+ "OfficeFile/DownLoad/EmBodyCheck/ManagerFile/";
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String filename = "签收报告" + sdf.format(date) + ".xls";
			// 获取绝对路径
			// System.out.println(path);
			// 创建文件
			File file = new File(path + filename);
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sheetName = "体检信息";
			String[] title = { "序号", "单位编号", "公司名称", "客服", "客服助理", "员工编号",
					"姓名", "身份证号", "体检编号", "体检医院", "体检项目", "体检价格", "体检地址",
					"安排体检时间", "状态" };
			try {
				ExcelService exl = new ExcelImpls(file, sheetName, title,
						outlist);
				exl.writeExcel();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Filedownload.save(file, "xlsx");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// file.delete();
		}
		bclist = bll.getEmBodyCheckInfo(sqls);
	}

	// 导入保健号
	// 导入数据
	@Command
	public void insertdata(@BindingParam("flag") String flag) {
		Map map = new HashMap<>();
		String title = "";
		String url = "Embc_InsertEmbcId.zul";
		if (flag != null) {
			if (flag.equals("1")) {
				title = "导入保健号名单";
				url = "Embc_InsertNumber.zul";
			} else if (flag.equals("2")) {
				title = "导入签收名单";
			} else if (flag.equals("3")) {
				title = "导入结算费用名单";
			}
		}
		if (title == null || title.equals("")) {
			title = "导入数据";
		}

		map.put("title", title);
		map.put("flag", flag);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	// 全选
	@Command
	public void checkall(@BindingParam("ck") Checkbox ck,
			@BindingParam("gd") Grid gd) {

		Integer n = gd.getActivePage() > 0 ? (gd.getActivePage()
				* gd.getPageSize() + gd.getPageSize()) : (bclist.size() < gd
				.getPageSize() ? bclist.size() : gd.getPageSize());

		for (int i = gd.getActivePage() * gd.getPageSize(); i < n; i++) {
			if (gd.getCell(i, 12) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, 12).getChildren().get(0);
				cb.setChecked(ck.isChecked());
				bclist.get(i).setChecked(ck.isChecked());
			}
		}
	}

	// 弹出退回员工体检页面
	@Command
	public void back(@BindingParam("model") EmBodyCheckModel model) {
		if (model.getEmbc_id() != null) {
			Map map = new HashMap<>();
			map.put("model", model);
			map.put("flag", "0");
			Window window = (Window) Executions.createComponents(
					"../EmBodyCheck/Embc_Back.zul", null, map);
			window.doModal();
		} else {
			Messagebox.show("该数据没有任务单，不能退回", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<EmBodyCheckModel> getBclist() {
		return bclist;
	}

	public void setBclist(List<EmBodyCheckModel> bclist) {
		this.bclist = bclist;
	}

	public List<EmBcSetupModel> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist(List<EmBcSetupModel> setuplist) {
		this.setuplist = setuplist;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}
}
