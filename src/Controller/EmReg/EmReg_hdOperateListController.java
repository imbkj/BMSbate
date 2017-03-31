package Controller.EmReg;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmReg.EmReg_ListBll;

import Model.EmRegistrationInfoModel;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;
import Util.plyUtil;

public class EmReg_hdOperateListController {

	private List<EmRegistrationInfoModel> emregList;
	private List<EmRegistrationInfoModel> semregList = new ListModelList<>();
	private List<EmRegistrationInfoModel> stateList = new ListModelList<>();

	String wherestr = " and type=1 and typename='后道状态' and state=1 and erin_stop_state=0";

	// 检索条件
	private String cid = "";
	private String gid = "";
	private String shortname = "";
	private String name = "";
	private String t_kind = "";
	private String statename = "";
	private Date addtime = null;
	private String is_sh = "";

	private boolean mult = true;
	private boolean opsDis = true;

	public EmReg_hdOperateListController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			setStateList(bll.getStateList(" and type=1 and typename='后道状态'"));

			setEmregList(new ListModelList<>(bll.getEmRegList(2, wherestr)));
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
			if (!statename.isEmpty()) {
				if (!RegexUtil.isExists(statename, m.getStatename())) {
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
		if (statename.isEmpty() || statename.equals("待交接")
				|| statename.equals("已申报")) {
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
		String url = "/SysMessage/SysMessage_Send.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cn", "就业登记");
		map.put("title", "就业登记");
		map.put("gid", m.getGid());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();
	}

	// 补充信息
	@Command("mod")
	@NotifyChange("semregList")
	public void mod(@BindingParam("daid") Integer daid) {
		String url = "EmReg_hd_Mod.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(2, wherestr)));
		search();
	}

	// 重新提交
	@Command("resubmit")
	@NotifyChange("semregList")
	public void resubmit(@BindingParam("each") EmRegistrationInfoModel m) {
		String url = "EmReg_Operate.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErin_id());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(2, wherestr)));
		search();
	}

	// 单条状态变更
	@Command("openwin")
	@NotifyChange("semregList")
	public void openwin(@BindingParam("each") EmRegistrationInfoModel m) {
		List<EmRegistrationInfoModel> list = new ListModelList<>();
		list.add(m);
		String url = "EmReg_Operate.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		EmReg_ListBll bll = new EmReg_ListBll();
		setEmregList(new ListModelList<>(bll.getEmRegList(2, wherestr)));
		search();
	}

	// 批量状态变更
	@Command("openwins")
	@NotifyChange("semregList")
	public void openwins(@BindingParam("set") Set<Listitem> set) {
		List<EmRegistrationInfoModel> list = new ListModelList<>();
		String url = "";
		for (Listitem it : set) {
			list.add((EmRegistrationInfoModel) it.getValue());
		}

		if (list.size() > 0) {
			// 根据所选数量判断是批量还是个人
			url = list.size() == 1 ? "EmReg_Operate.zul" : "EmReg_Operates.zul";

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			Window win = (Window) Executions.createComponents(url, null, map);
			win.doModal();

			EmReg_ListBll bll = new EmReg_ListBll();
			setEmregList(new ListModelList<>(bll.getEmRegList(2, wherestr)));
			search();
		} else {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 导出Excel
	@Command("ExportExcel")
	public void ExportExcel(@BindingParam("set") Set<Listitem> set) {

		// 将选中的set转换成list
		List<EmRegistrationInfoModel> excList = new ListModelList<>();
		for (Listitem it : set) {
			excList.add((EmRegistrationInfoModel) it.getValue());
		}

		if (excList.size() > 0) {

			try {
				String filename = "jy"
						+ new SimpleDateFormat("yyyyMMddHHmmssSSS")
								.format(new Date()) + UserInfo.getUserid()
						+ ".xls";
				File file = new File(new plyUtil().getAbsolutePath(
						"/../../EmReg/file", filename, this));
				try {
					file.createNewFile();
				} catch (Exception e) {
					Messagebox.show("生成文件出错,请再次点击生成!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}

				// 读取Excel模板
				Workbook wb = Workbook.getWorkbook(new File(new plyUtil()
						.getAbsolutePath("/../../EmReg/file", "jy.xls", this)));

				// 使用模板创建
				WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

				// 生成工作表,(name:First Sheet,参数0表示这是第一页)
				WritableSheet sheet = wwb.getSheet(0);

				// 开始写入内容
				for (int row = 4; row < excList.size() + 4; row++) {
					// 用model获取每一行数据
					EmRegistrationInfoModel m = excList.get(row - 4);

					// 将每列数据写入工作表中
					Label label = null;
					label = new Label(0, row, m.getErin_idcard());
					sheet.addCell(label);
					label = new Label(1, row, m.getEmba_name());
					sheet.addCell(label);
					label = new Label(2, row, m.getErin_former_name());
					sheet.addCell(label);
					label = new Label(3, row, m.getErin_educationcode());
					sheet.addCell(label);
					label = new Label(4, row, m.getErin_folkcode());
					sheet.addCell(label);
					label = new Label(5, row, m.getErin_partycode());
					sheet.addCell(label);
					label = new Label(6, row, m.getErin_maritalcode());
					sheet.addCell(label);
					label = new Label(7, row, m.getErin_computerid());
					sheet.addCell(label);
					label = new Label(8, row, m.getErin_hjtypecode());
					sheet.addCell(label);
					label = new Label(9, row, m.getErin_worktime());
					sheet.addCell(label);
					label = new Label(10, row, m.getErin_titlecode());
					sheet.addCell(label);
					label = new Label(11, row, m.getErin_compact_s_date());
					sheet.addCell(label);
					label = new Label(12, row, m.getErin_compact_e_date());
					sheet.addCell(label);
					label = new Label(13, row, m.getErin_regtypecode());
					sheet.addCell(label);
					label = new Label(14, row, m.getErin_salary() == null ? "0"
							: m.getErin_salary() + "");
					sheet.addCell(label);
					label = new Label(15, row, m.getErin_skilllevelcode());
					sheet.addCell(label);
					label = new Label(16, row, m.getErin_unit_b_date());
					sheet.addCell(label);
					label = new Label(17, row, m.getErin_photo_number());
					sheet.addCell(label);
					label = new Label(18, row, m.getErin_address_number());
					sheet.addCell(label);
					label = new Label(19, row, m.getErin_idcard_address());
					sheet.addCell(label);
					label = new Label(20, row, m.getErin_come_sz_date());
					sheet.addCell(label);
					label = new Label(21, row,
							m.getErin_house_class() == null ? "" : m
									.getErin_house_class() + "");
					sheet.addCell(label);
					label = new Label(22, row, m.getErin_r_date());
					sheet.addCell(label);
					label = new Label(23, row,
							m.getErin_house_mode() == null ? "" : m
									.getErin_house_mode() + "");
					sheet.addCell(label);
					label = new Label(24, row, m.getErin_phone());
					sheet.addCell(label);
					label = new Label(25, row, m.getErin_mobile());
					sheet.addCell(label);
					label = new Label(26, row, m.getErin_epname());
					sheet.addCell(label);
					label = new Label(27, row, m.getErin_epphone());
					sheet.addCell(label);
					label = new Label(28, row, m.getErin_epmobile());
					sheet.addCell(label);
					label = new Label(29, row, m.getErin_birthcontrol());
					sheet.addCell(label);
					label = new Label(31, row, m.getErin_remark());
					sheet.addCell(label);
					label = new Label(32, row,
							m.getErin_wcin_code() == null ? "" : m
									.getErin_wcin_code() + "");
					sheet.addCell(label);
				}

				// 写入数据
				wwb.write();
				// 关闭文件
				wwb.close();

				FileOperate.download("EmReg/file/" + filename);

			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
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

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public List<EmRegistrationInfoModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<EmRegistrationInfoModel> stateList) {
		this.stateList = stateList;
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
