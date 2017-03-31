package Controller.EmReg;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import dal.LoginDal;

import bll.EmReg.EmReg_ListBll;

import Controller.EmSheBaocard.CunExcelImpl;
import Controller.EmSheBaocard.newExcelImpl;
import Model.EmRegistrationInfoModel;
import Model.LoginModel;
import Util.FileOperate;
import Util.RegexUtil;
import Util.plyUtil;

public class EmReg_hdStopListController {
	private List<EmRegistrationInfoModel> emregList;
	private List<EmRegistrationInfoModel> semregList = new ListModelList<>();
	private List<EmRegistrationInfoModel> stopstateList = new ListModelList<>();

	String wherestr = " and type=1 and state=1 and erin_stop_state<>0";

	// 检索条件
	private String cid = "";
	private String gid = "";
	private String shortname = "";
	private String name = "";
	private String t_kind = "";
	private String stop_statename = "";
	private Date addtime = null;
	private String is_sh = "";

	private boolean mult = true;
	private boolean opsDis = true;

	public EmReg_hdStopListController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			setStopstateList(bll.getStopStateList());

			setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
			search();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("search")
	@NotifyChange({ "semregList", "opsDis" })
	public void search() {
		semregList.clear();

		for (EmRegistrationInfoModel m : emregList) {

			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
			if (!gid.isEmpty()) {
				if (!RegexUtil.isExists(gid, m.getGid() + "")) {
					continue;
				}
			}
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getEmba_name())) {
					continue;
				}
			}
			if (!stop_statename.isEmpty()) {
				if (!RegexUtil.isExists(stop_statename, m.getStop_statename())) {
					continue;
				}
			}
			if (addtime != null) {
				if (!RegexUtil.isExists(
						new SimpleDateFormat("yyyy-MM-dd").format(addtime),
						m.getErin_addtime())) {
					continue;
				}
			}
			if (!is_sh.isEmpty()) {
				if (!is_sh.equals(m.getIs_sh())) {
					continue;
				}
			}

			semregList.add(m);
		}
		if (stop_statename.equals("待终止")) {
			setOpsDis(true);
		} else {
			setOpsDis(false);
		}
	}

	// 详情
	@Command("detail")
	public void detail(@BindingParam("daid") Integer daid,
			@BindingParam("role") String role) {
		String url = "EmReg_Detail.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		map.put("role", role);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();
	}

	// 材料详情
	@Command("docdetail")
	public void docdetail(@BindingParam("daid") Integer daid) {
		String url = "EmReg_DocDetail.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();
	}

	// 系统短信
	@Command("sysmessage")
	public void sysmessage(@BindingParam("each") EmRegistrationInfoModel m) {
		String url = "../SysMessage/Message_Add.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "就业登记");// 业务类型:来自WfClass的wfcl_name
		map.put("id", m.getErin_id());// 业务表id
		map.put("tablename", "EmRegistrationInfo");// 业务表名
		map.put("title", m.getEmba_name() + "就业登记终止");
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel lom = new LoginModel();
		lom.setLog_name(m.getCoba_client());
		LoginDal d = new LoginDal();
		lom.setLog_id(d.getUserIDByname(m.getCoba_client()));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(lom);
		map.put("list", mlist);// 默认收件人list

		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();
	}

	// 申报终止
	@Command("stop")
	public void stop(@BindingParam("daid") Integer daid) {
		String url = "EmReg_Stop.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(1, wherestr)));
		search();
	}

	// 打开处理页面
	@Command
	public void deal(@BindingParam("model") EmRegistrationInfoModel model) {
		String url = "EmReg_StopDeal.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", model);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();
	}

	// 打开批量处理页面
	@Command
	public void openwins(@BindingParam("gd") Grid gd) {
		String url = "EmReg_StopDealAll.zul";
		List<EmRegistrationInfoModel> list = new ArrayList<EmRegistrationInfoModel>();
		Integer rownum = gd.getPageSize();
		for (int i = 0; i < rownum; i++) {
			if (gd.getCell(i, 11) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 11).getChildren().get(0);
				if (ck.isChecked()) {
					EmRegistrationInfoModel m = ck.getValue();
					list.add(m);
				}
			}
		}
		if (list.size() > 0) {
			Map map = new HashMap<>();
			map.put("list", list);
			Window win = (Window) Executions.createComponents(url, null, map);
			win.doModal();
		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 全选
	@Command
	public void checkall(@BindingParam("ck") Checkbox ck,
			@BindingParam("gd") Grid gd) {
		Integer rownum = gd.getPageSize();
		for (int i = 0; i < rownum; i++) {
			if (gd.getCell(i, 11) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, 11).getChildren().get(0);
				cb.setChecked(ck.isChecked());
			}
		}
	}

	// 批量生成上传文件
	@Command
	public void ExportExcel(HttpServletResponse response,
			@BindingParam("ltb") Grid gd) throws Exception {
		List<EmRegistrationInfoModel> list = new ArrayList<EmRegistrationInfoModel>();
		Integer rownum = gd.getPageSize();
		for (int i = 0; i < rownum; i++) {
			if (gd.getCell(i, 11) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, 11).getChildren().get(0);
				if (cb.isChecked()) {
					EmRegistrationInfoModel m = cb.getValue();
					list.add(m);
				}
			}
		}
		if (list.size() > 0) {
			getStopReson(list);
			plyUtil ply = new plyUtil();
			String path = "/../../EmReg/file/";
			String paths = "EmReg/downloadfile/";
			String absolutePath = FileOperate.getAbsolutePath();
			String filename = "eeri_stop_templet.xls";
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String newfilename = "就业终止"
					+ sdf.format(date) + ".xls";
			// 获取绝对路径
			String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径

			try {
				File f = new File(absolutePath + paths + newfilename);
				if (f.isFile()) {
					f.delete();
				}
				ExcelService exl = new Emreg_StopExportExcelImpl(solpath, absolutePath
					+ paths + newfilename, list);
				exl.writeExcel();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			FileOperate.download(paths + newfilename);

		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//获取终止原因
	private void getStopReson(List<EmRegistrationInfoModel> list)
	{
		for(EmRegistrationInfoModel m:list)
		{
			EmReg_ListBll sbll = new EmReg_ListBll();
			m.setErin_stop_reason(sbll.getStopreson(m.getErin_id()));
		}
	}

	public List<EmRegistrationInfoModel> getEmregList() {
		return emregList;
	}

	public void setEmregList(List<EmRegistrationInfoModel> emregList) {
		this.emregList = emregList;
	}

	public List<EmRegistrationInfoModel> getSemregList() {
		return semregList;
	}

	public void setSemregList(List<EmRegistrationInfoModel> semregList) {
		this.semregList = semregList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getT_kind() {
		return t_kind;
	}

	public void setT_kind(String t_kind) {
		this.t_kind = t_kind;
	}

	public String getStop_statename() {
		return stop_statename;
	}

	public void setStop_statename(String stop_statename) {
		this.stop_statename = stop_statename;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public List<EmRegistrationInfoModel> getStopstateList() {
		return stopstateList;
	}

	public void setStopstateList(List<EmRegistrationInfoModel> stopstateList) {
		this.stopstateList = stopstateList;
	}

	public String getIs_sh() {
		return is_sh;
	}

	public void setIs_sh(String is_sh) {
		this.is_sh = is_sh;
	}

	public boolean isMult() {
		return mult;
	}

	public void setMult(boolean mult) {
		this.mult = mult;
	}

	public boolean isOpsDis() {
		return opsDis;
	}

	public void setOpsDis(boolean opsDis) {
		this.opsDis = opsDis;
	}
}
