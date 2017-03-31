package Controller.EmCensus;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Filedownload;

import Model.EmCensusModel;
import Model.sbTotalModel;
import Util.ExcelHelper;
import Util.FileOperate;
import Util.HssfExcelHelper;
import bll.EmCensus.EmCensus_SelectBll;

public class EmCensus_InfoSearchController {
	private String cid = "";
	private String hjno = "";
	private String company = "";
	private String inhjtype = "";
	private String hjaddress = "";
	private String embaclass = "员工姓名";
	private String embainfo = "";
	private EmCensus_SelectBll bll = new EmCensus_SelectBll();
	private String str = "";
	private List<EmCensusModel> emcensus = bll
			.getEmCensusInfo(" and emhj_state in(5,6)");

	// 查询
	@Command
	@NotifyChange("emcensus")
	public void search(@BindingParam("emstate") String emstate) {
		str = "";
		// 员工信息
		if (embaclass != null && !embaclass.equals("") && embaclass != "") {
			if (embainfo != null && !embainfo.equals("") && embainfo != "") {
				if (embaclass == "员工姓名" || embaclass.equals("员工姓名")) {
					str = str
							+ "and (emhj_name='"
							+ embainfo
							+ "' or emhj_id in(select emhj_pid from "
							+ "EmCensus where emhj_name='"
							+ embainfo
							+ "') or emhj_Pid in(select emhj_id "
							+ "from EmCensus where emhj_name='"
							+ embainfo
							+ "')"
							+ " or emhj_pid in(select emhj_pid from EmCensus where emhj_name='"
							+ embainfo + "'))";
				} else if (embaclass == "员工编号" || embaclass.equals("员工编号")) {
					str = str + " and a.gid=" + embainfo;
				} else if (embaclass == "身份证号" || embaclass.equals("身份证号")) {
					str = str
							+ " and (emhj_idcard='"
							+ embainfo
							+ "'"
							+ "or emhj_id in(select emhj_pid from EmCensus where "
							+ "emhj_idcard='"
							+ embainfo
							+ "') or emhj_Pid in(select emhj_id from "
							+ "EmCensus where emhj_idcard='"
							+ embainfo
							+ "')"
							+ " or emhj_pid in(select emhj_pid from EmCensus where emhj_idcard='"
							+ embainfo + "'))";
				}
			}
		}
		if (company != null && !company.equals("") && company != "") {
			str = str + " and (b.coba_company like '%" + company
					+ "%' or coba_shortname like '%" + company + "%')";
		}
		if (cid != null && !cid.equals("") && cid != "") {
			str = str + " and a.cid=" + cid;
		}
		if (emstate != null && !emstate.equals("") && emstate != ""
				&& emstate != "0" && !emstate.equals("0")) {
			str = str + " and emhj_state=" + emstate;
		}
		if (hjno != null && !hjno.equals("") && hjno != "") {
			str = str
					+ " and (emhj_no='"
					+ hjno
					+ "'  or emhj_id in(select emhj_pid from EmCensus "
					+ " where emhj_no='"
					+ hjno
					+ "') or emhj_Pid in(select emhj_id from EmCensus "
					+ " where emhj_no='"
					+ hjno
					+ "')"
					+ " or emhj_pid in(select emhj_pid from EmCensus where emhj_no='"
					+ hjno + "'))";
		}
		if (hjaddress != null && !hjaddress.equals("") && hjaddress != "") {
			str = str + " and emhj_place='" + hjaddress + "'";
		}
		if (hjaddress != null && !hjaddress.equals("") && hjaddress != "") {
			str = str + " and emhj_place='" + hjaddress + "'";
		}
		if (inhjtype != null && !inhjtype.equals("") && inhjtype != "") {
			str = str + " and emhj_in_class='" + inhjtype + "'";
		}
		emcensus = bll.getEmCensusInfo(str);
	}

	@Command
	public void export() {
		String absolutePath = FileOperate.getAbsolutePath();
		String path = absolutePath + "OfficeFile/DownLoad/EmCensus/Client/";
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "户口信息" + sdf.format(date) + ".xls";
		// 获取绝对路径
		// System.out.println(path);
		// 创建文件
		File file = new File(path + filename);
		ExcelHelper eh = HssfExcelHelper.getInstance(file);
		String[] titles = new String[] { "公司编号", "员工编号", "户口编号", "客服", "公司名称",
				"姓名", "身份证", "户口所在地", "入户时间", "入户方式", "状态" };
		String[] fieldNames = new String[] { "cid", "gid", "emhj_no",
				"coba_client", "coba_shortname", "emhj_name", "emhj_idcard",
				"emhj_place", "emhj_in_time", "emhj_in_class", "states" };
		try {
			eh.writeExcel(EmCensusModel.class, emcensus, fieldNames, titles);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Filedownload.save(file, "xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<EmCensusModel> getEmcensus() {
		return emcensus;
	}

	public void setEmcensus(List<EmCensusModel> emcensus) {
		this.emcensus = emcensus;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getHjno() {
		return hjno;
	}

	public void setHjno(String hjno) {
		this.hjno = hjno;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getInhjtype() {
		return inhjtype;
	}

	public void setInhjtype(String inhjtype) {
		this.inhjtype = inhjtype;
	}

	public String getEmbaclass() {
		return embaclass;
	}

	public void setEmbaclass(String embaclass) {
		this.embaclass = embaclass;
	}

	public String getEmbainfo() {
		return embainfo;
	}

	public void setEmbainfo(String embainfo) {
		this.embainfo = embainfo;
	}

	public String getHjaddress() {
		return hjaddress;
	}

	public void setHjaddress(String hjaddress) {
		this.hjaddress = hjaddress;
	}
}
