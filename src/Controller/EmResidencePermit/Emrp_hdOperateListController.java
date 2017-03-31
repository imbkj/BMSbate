package Controller.EmResidencePermit;

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
import bll.EmResidencePermit.Emrp_ListBll;

import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitInfoModel;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;
import Util.plyUtil;

public class Emrp_hdOperateListController {
	private List<EmResidencePermitInfoModel> erpList;
	private List<EmResidencePermitInfoModel> serpList = new ListModelList<>();
	private List<EmResidencePermitInfoModel> stateList;

	String wherestr = " and type=1 and typename='后道状态' and state=1 order by erpi_id desc";

	// 检索条件
	private String cid = "";
	private String gid = "";
	private String shortname = "";
	private String name = "";
	private String t_kind = "";
	private String statename = "";
	private Date addtime = null;

	private final boolean mult = true;
	private boolean opsDis;

	public Emrp_hdOperateListController() {
		try {
			Emrp_ListBll bll = new Emrp_ListBll();
			setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
			search();
			setStateList(new ListModelList<>(
					bll.getStateList(" and typename='后道状态'")));
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("search")
	@NotifyChange({ "serpList", "opsDis" })
	public void search() {
		serpList.clear();

		for (EmResidencePermitInfoModel m : erpList) {

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
						m.getErpi_addtime())) {
					continue;
				}
			}

			serpList.add(m);
		}
		if (statename.equals("待上传") || statename.equals("待申报")
				|| statename.equals("待盖章") || statename.equals("待受理")
				|| statename.equals("已受理")) {
			setOpsDis(false);
		} else {
			setOpsDis(true);
		}
	}

	// 状态变更
	@Command("operate")
	@NotifyChange("serpList")
	public void operate(@BindingParam("each") EmResidencePermitInfoModel m) {
		String url = "Emrp_Operate.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
		search();
	}

	// 批量状态变更
	@Command("operates")
	@NotifyChange("serpList")
	public void operates(@BindingParam("set") Set<Listitem> set) {
		String url = "";
		Map<String, Object> map = new HashMap<String, Object>();
		List<EmResidencePermitInfoModel> list = new ListModelList<>();
		for (Listitem it : set) {
			list.add((EmResidencePermitInfoModel) it.getValue());
		}

		if (list.size() > 0) {
			if (list.size() == 1) {
				url = "Emrp_Operate.zul";
				map.put("daid", list.get(0).getErpi_id());
			} else {
				url = "Emrp_Operates.zul";
				map.put("list", list);
			}

			Window win = (Window) Executions.createComponents(url, null, map);
			win.doModal();

			Emrp_ListBll bll = new Emrp_ListBll();
			setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
			search();
		} else {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 补充信息
	@Command("mod")
	@NotifyChange("serpList")
	public void mod(@BindingParam("each") EmResidencePermitInfoModel m) {
		String url = "Emrp_hd_Mod.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
		search();
	}

	// 退回
	@Command("back")
	@NotifyChange("serpList")
	public void back(@BindingParam("each") EmResidencePermitInfoModel m) {
		String url = "Emrp_Back.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
		search();
	}

	// 详情
	@Command("detail")
	@NotifyChange("serpList")
	public void detail(@BindingParam("each") EmResidencePermitInfoModel m,
			@BindingParam("role") String role) {
		String url = "Emrp_Detail.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		map.put("role", role);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
		search();
	}

	// 材料详情
	@Command("docdetail")
	@NotifyChange("serpList")
	public void docdetail(@BindingParam("each") EmResidencePermitInfoModel m) {
		String url = "Emrp_DocDetail.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		map.put("gid", m.getGid());
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setErpList(new ListModelList<>(bll.getEmrpList(wherestr)));
		search();
	}

	// 导出Excel
	@Command("ExportExcel")
	public void ExportExcel(@BindingParam("set") Set<Listitem> set) {

		// 将选中的set转换成list
		List<EmResidencePermitInfoModel> emresList = new ListModelList<>();
		for (Listitem it : set) {
			emresList.add((EmResidencePermitInfoModel) it.getValue());
		}

		// 拼接sql条件语句
		String wherestr = "";
		for (EmResidencePermitInfoModel m : emresList) {
			wherestr += "," + m.getErin_id();
		}
		wherestr = wherestr.substring(1) + ")";
		wherestr = " and erin_id in(" + wherestr;

		// 获取就业登记数据
		List<EmRegistrationInfoModel> excList = new ListModelList<>(
				new EmReg_ListBll().getEmRegList(1, wherestr));

		if (excList.size() > 0) {

			try {
				String filename = "jzz"
						+ new SimpleDateFormat("yyyyMMddHHmmssSSS")
								.format(new Date()) + UserInfo.getUserid()
						+ ".xls";
				File file = new File(new plyUtil().getAbsolutePath(
						"/../../EmResidencePermit/file", filename, this));
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

				FileOperate.download("EmResidencePermit/file/" + filename);

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

	public List<EmResidencePermitInfoModel> getErpList() {
		return erpList;
	}

	public void setErpList(List<EmResidencePermitInfoModel> erpList) {
		this.erpList = erpList;
	}

	public List<EmResidencePermitInfoModel> getSerpList() {
		return serpList;
	}

	public void setSerpList(List<EmResidencePermitInfoModel> serpList) {
		this.serpList = serpList;
	}

	public List<EmResidencePermitInfoModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<EmResidencePermitInfoModel> stateList) {
		this.stateList = stateList;
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

	public boolean isOpsDis() {
		return opsDis;
	}

	public void setOpsDis(boolean opsDis) {
		this.opsDis = opsDis;
	}

	public boolean isMult() {
		return mult;
	}
}
