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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.ExcelService;
import Model.EmBcSetupModel;
import Model.EmBodyCheckModel;
import Model.LoginModel;
import Util.DateUtil;
import Util.DateUtil2;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.Embc_SetupSellectBll;
import bll.EmBodyCheck.ExcelImpl;

public class EmBc_InfoListController {
	private EmBcInfo_SelectBll bll = new EmBcInfo_SelectBll();
	private Embc_SetupSellectBll setupbll = new Embc_SetupSellectBll();
	private String oldsql = "";
	private List<EmBodyCheckModel> bclist = new ListModelList<>();
	// 获取客服信息
	CoLatencyClient_AddBll clientbll = new CoLatencyClient_AddBll();
	private List<String> clientlist = clientbll.getLoginInfo();

	private List<EmBcSetupModel> setuplist = new ArrayList<EmBcSetupModel>();

	public EmBc_InfoListController() {
		oldsql=" and ebcl_bookdate Between '"
				+ changedate(new Date()) + "'" + " And '"
				+ changedate(getNextWeek()) + "' and ebcl_state!=9 and ebcl_flag=1";
		bclist=bll.getEmBodyCheckInfo(oldsql);
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
			@BindingParam("nowstate") String nowstate) {
		String sql = "";
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
						+ changedate(getNextWeek()) + "' and ebcl_state!=9";
			}
		}
		oldsql = sql + " and ebcl_flag=1";
		bclist = bll.getEmBodyCheckInfo(oldsql);
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
		Window window = (Window) Executions.createComponents(
				"EmBc_InfoEdit.zul", null, map);
		window.doModal();
		bclist = bll.getEmBodyCheckInfo(oldsql);
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
			@BindingParam("client") String client) {
		String uname = "";
		if (!UserInfo.getUsername().equals(client)) {
			uname = client;
		}
		Map map = new HashMap<>();
		map.put("type", "");// 业务类型:来自WfClass的wfcl_name
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
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	// 弹出取消预约页面
	@Command
	@NotifyChange("bclist")
	public void cancel(@BindingParam("model") EmBodyCheckModel model) {
		if (model.getEmbc_id() != null) {
			// if (model.getEmbc_tapr_id() != null
			// && !model.getEmbc_tapr_id().equals("")) {
			Map map = new HashMap<>();
			map.put("model", model);
			map.put("tarpid", model.getEmbc_tapr_id());
			map.put("flag", "0");
			Window window = (Window) Executions.createComponents(
					"../EmBodyCheck/Embc_Cancel.zul", null, map);
			window.doModal();
			if (map.get("flag").equals("1")) {
				bclist = bll.getEmBodyCheckInfo(oldsql);
			}
			// } else {
			// Messagebox.show("该数据没有任务单信息", "提示", Messagebox.OK,
			// Messagebox.ERROR);
			// }
		} else {
			Messagebox.show("没有该员工体检信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出重新预约页面
	@Command
	@NotifyChange("bclist")
	public void upagain(@BindingParam("model") EmBodyCheckModel model) {
		if (model.getEmbc_id() != null) {
			Map map = new HashMap<>();
			map.put("model", model);
			map.put("id", model.getEmbc_tapr_id());
			map.put("daid", model.getEmbc_id());
			Window window = (Window) Executions.createComponents(
					"../EmBodyCheck/Embc_InfoConfirm.zul", null, map);
			window.doModal();
			bclist = bll.getEmBodyCheckInfo(oldsql);
		} else {
			Messagebox.show("没有该员工体检信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 删除
	@Command
	@NotifyChange("bclist")
	public void del(@BindingParam("model") final EmBodyCheckModel model) {
		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							EmBcInfo_OperateBll delbll = new EmBcInfo_OperateBll();
							String sql = ",ebcl_flag=0,ebcl_state=14";
							String[] str = delbll.delBodyCheck(model, sql);
							if (str[0] == "1") {
								Messagebox.show("删除成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								bclist = bll.getEmBodyCheckInfo(oldsql);
							} else {
								Messagebox.show(str[1], "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	// 导出excel
	@Command("doExcel")
	public void doExcel(@BindingParam("gd") Grid gd) {
		String absolutePath = FileOperate.getAbsolutePath();
		List<EmBodyCheckModel> outlist = new ListModelList<>();
		for (EmBodyCheckModel em : bclist) {
			if (em.isChecked()) {
				outlist.add(em);
			}
		}
		if (outlist.size() <= 0) {
			outlist = bclist;
		}
		plyUtil ply = new plyUtil();
		String path = absolutePath
				+ "OfficeFile/DownLoad/EmBodyCheck/ClientFile/";
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "员工体检" + sdf.format(date) + ".xls";

		// 创建文件
		File file = new File(path + filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sheetName = "体检信息";

		String[] title = { "序号", "客服", "公司简称", "姓名", "身份证号", "性别", "年龄",
				"体检医院", "体检项目", "体检价格", "体检地址", "安排体检时间", "状态" };

		try {
			ExcelService exl = new ExcelImpl(file, sheetName, title, outlist);
			// exl.writeExcel();
			exl.exportExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			Filedownload.save(file, "xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 弹出重新预约页面——未体检前重新预约
	@Command
	public void Reschedule(@BindingParam("model") final EmBodyCheckModel model) {
		if (model.getEbcl_plandate()!=null && !model.getEbcl_plandate().equals("")) {
			if (DateUtil2.getOffsetDays(DateUtil2.parse(model.getEbcl_plandate(),"yyyy-MM-dd"),DateUtil2.parse("2017-1-1","yyyy-MM-dd"))>=0) {
				Messagebox.show("2017年前的体检数据不允许操作重新预约,请重新添加.", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("id", model.getEmbc_tapr_id());
		map.put("daid", model.getEmbc_id());
		Window window = (Window) Executions.createComponents(
				"../EmBodyCheck/Embc_Reschedule.zul", null, map);
		window.doModal();
		bclist = bll.getEmBodyCheckInfo(oldsql);
	}

	// 全选
	@Command
	public void checkall(@BindingParam("ck") Checkbox ck,
			@BindingParam("gd") Grid gd) {
		int n = 500;
		if (bclist.size() < 500) {
			n = bclist.size();
		}
		for (int i = 0; i < n; i++) {
			if (gd.getCell(i, 12) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, 12).getChildren().get(0);
				cb.setChecked(ck.isChecked());
			}
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}
}
