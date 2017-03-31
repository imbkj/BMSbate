package Controller.CoReg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;

import service.ExcelService;

import bll.EmReg.EmReg_ListBll;

import Model.EmRegistrationInfoModel;
import Model.PubCodeConversionModel;
import Util.plyUtil;

public class EmReg_StopListController {
	private List<EmRegistrationInfoModel> list = new ArrayList<EmRegistrationInfoModel>();
	private EmReg_ListBll bll = new EmReg_ListBll();
	private List<PubCodeConversionModel> hjList = new ArrayList<PubCodeConversionModel>();// 户籍类型

	/************ 定义查询字段 ****************/
	private String company, emba_name, hj, state;

	public EmReg_StopListController() {
		list = bll.getEmRegList("");
		PubCodeConversionModel m = new PubCodeConversionModel();
		hjList.add(m);
		hjList.addAll(bll
				.getPubCodeList(" and pucl_id=16 and pcco_name='户籍类型'"));
		PubCodeConversionModel md = new PubCodeConversionModel();
		md.setPcco_cn("非深户");
		hjList.add(md);
	}

	@Command
	@NotifyChange("list")
	public void search() {
		String sql = "";
		if (company != null && !company.equals("")) {
			sql = sql + " and coba_shortname like '%" + company + "%'";
		}
		if (emba_name != null && !emba_name.equals("")) {
			sql = sql + " and emba_name like '%" + emba_name + "%'";
		}
		if (hj != null && !hj.equals("")) {
			if (hj.equals("非深户")) {
				sql = sql + " and erin_hjtype != '本市城镇'";
			} else {
				sql = sql + " and erin_hjtype like '%" + hj + "%'";
			}
		}
		if (state != null && !state.equals("")) {
			if (state.equals("待终止")) {
				sql = sql + " and erin_stop_state=1";
			} else if (state.equals("已终止")) {
				sql = sql + " and erin_stop_state=2";
			}
		}
		list = bll.getEmRegList(sql);
	}

	// 导出
	@Command
	public void Export(@BindingParam("gd") Grid gd) {
		List<EmRegistrationInfoModel> outlist= new ArrayList<EmRegistrationInfoModel>();
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getRows().getChildren().get(i) != null) {
				Cell cell = (Cell) gd.getCell(i, 12);
				Checkbox ck = (Checkbox) cell.getChildren().get(0);
				if(ck.isChecked())
				{
					EmRegistrationInfoModel m=ck.getValue();
					outlist.add(m);
				}
			}
		}
		outExcel(outlist);
	}
	
	private void outExcel(List<EmRegistrationInfoModel> outlist)
	{
		if(outlist.size()<=0)
		{
			outlist=list;
		}
		plyUtil ply = new plyUtil();
		String path = "/../../EmReg/downloadfile/";// 导出保存路径
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "员工就业终止" + sdf.format(date) + ".xls";// 定义导出文件名称
		// 获取绝对路径
		path = ply.getAbsolutePath(path, filename, this);
		// System.out.println(path);
		// 创建文件
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sheetName = "员工就业终止信息";// Excel表格名
		// 定义表头
		String[] title = { "序号", "单位编号", "单位名称", "员工编号", "员工姓名", "交接人",
				"户籍类型", "终止原因", "终止日期", "终止申请人", "操作终止日期", "状态" };
		try {
			// 使用自己写的Excel导出实现类把数据写入Excel
			ExcelService exl = new Controller.EmReg.ExcelImpl(file, sheetName,
					title, outlist);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			Filedownload.save(file, "xls");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox ck) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getRows().getChildren().get(i) != null) {
				Cell cell = (Cell) gd.getCell(i, 12);
				Checkbox cb = (Checkbox) cell.getChildren().get(0);
				cb.setChecked(ck.isChecked());
			}
		}
	}

	public List<EmRegistrationInfoModel> getList() {
		return list;
	}

	public void setList(List<EmRegistrationInfoModel> list) {
		this.list = list;
	}

	public List<PubCodeConversionModel> getHjList() {
		return hjList;
	}

	public void setHjList(List<PubCodeConversionModel> hjList) {
		this.hjList = hjList;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getHj() {
		return hj;
	}

	public void setHj(String hj) {
		this.hj = hj;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
