package Controller.CoAgency;

import java.io.File;
import java.io.IOException;
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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import bll.CoAgency.BaseInfo_SelBll;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

import service.ExcelService;
import Controller.CoServicePolicy.ExcelImpl;
import Controller.CoServicePolicy.OutTempExcelImpl;
import Model.CoAgencyBaseCityRelModel;
import Model.CoServicePolicyTitleModel;
import Model.CoServicePolicyTypeModel;
import Util.plyUtil;

public class WtPolicy_ManageController {
	private int cabc_id = Integer.parseInt(Executions.getCurrent()
			.getParameter("cabc_id").toString());
	private SePy_CityPolicySelectBll bll = new SePy_CityPolicySelectBll();
	private List<CoServicePolicyTypeModel> list;
	private List<CoServicePolicyTypeModel> typelist;
	private List<CoServicePolicyTitleModel> titlelist = new ArrayList<CoServicePolicyTitleModel>();
	private String tiptxt;

	public WtPolicy_ManageController() {
		list = bll.getCoServicePolicyTypeLists(cabc_id, "");
		typelist = list;
		addTitlelist();
	}

	// 新增政策
	@Command
	@NotifyChange({ "list", "tiptxt", "titlelist" })
	public void addPolicy() {
		BaseInfo_SelBll selbll = new BaseInfo_SelBll();
		CoAgencyBaseCityRelModel citymodel = selbll.getCoabModel(cabc_id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("model", citymodel);
		map.put("type", "机构政策");
		map.put("classname", citymodel.getCoab_name());
		Window window = (Window) Executions.createComponents(
				"../CoServicePolicy/SePy_CityPolicyInfoAdd.zul", null, map);
		window.doModal();
		// 刷新页面
		list = bll.getCoServicePolicyTypeLists(cabc_id, "");
		typelist = list;
		addTitlelist();
	}

	// 选择类型事件
	@Command
	@NotifyChange({ "list", "tiptxt", "titlelist" })
	public void checktype(@BindingParam("lb") Listbox lb,
			@BindingParam("bd") Bandbox bd) {
		String selectedval = "";
		List<CoServicePolicyTypeModel> newlist = new ArrayList<CoServicePolicyTypeModel>();
		if (lb.getSelectedCount() > 0) {
			for (Listitem item : lb.getSelectedItems()) {
				CoServicePolicyTypeModel m = item.getValue();
				newlist.add(m);
				if (selectedval == null || selectedval.equals("")) {
					selectedval = item.getLabel();
				} else {
					selectedval = selectedval + "、" + item.getLabel();
				}
			}
			bd.setValue(selectedval);
			tiptxt = selectedval;
			list = newlist;
		} else {
			list = bll.getCoServicePolicyTypeLists(cabc_id, "");
			bd.setValue("");
		}
		addTitlelist();
	}

	@Command
	public void checktitle(@BindingParam("ck") Checkbox ck,
			@BindingParam("model") CoServicePolicyTypeModel tmodel) {
		System.out.println("class=" + ck.getParent().getClass());
		Cell parentcell = (Cell) ck.getParent();
		Row parentrw = (Row) parentcell.getParent();
		Cell twocell = (Cell) parentrw.getChildren().get(1);
		Grid gd = (Grid) twocell.getChildren().get(0);
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 0) != null) {
				Cell gdcell = (Cell) gd.getCell(i, 0);
				Checkbox cb = (Checkbox) gdcell.getChildren().get(0);
				cb.setChecked(ck.isChecked());
			}
		}
	}

	// 导出数据
	@Command
	public void Export(HttpServletResponse response, @BindingParam("gd") Grid gd)
			throws IOException {
		List<CoServicePolicyTypeModel> outlist = new ArrayList<CoServicePolicyTypeModel>();
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			String title = "";
			List<CoServicePolicyTitleModel> titlelist = new ArrayList<CoServicePolicyTitleModel>();
			CoServicePolicyTypeModel ml = new CoServicePolicyTypeModel();
			CoServicePolicyTypeModel model = new CoServicePolicyTypeModel();
			if (gd.getCell(i, 0) != null) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren() != null) {
					Label lb = (Label) cell.getChildren().get(0);
					if (lb.getValue() != null) {
						title = lb.getValue();
					}
				}
				if (cell.getChildren().get(1) != null) {
					Checkbox check = (Checkbox) cell.getChildren().get(1);
					if (check.getValue() != null) {
						model = check.getValue();
					}
				}
			}
			if (gd.getCell(i, 1) != null) {
				Cell cell = (Cell) gd.getCell(i, 1);
				if (cell.getChildren() != null) {
					Grid grid = (Grid) cell.getChildren().get(0);
					for (int y = 0; y < grid.getRows().getChildren().size(); y++) {
						if (grid.getCell(y, 0) != null) {
							Cell cl = (Cell) grid.getCell(y, 0);
							if (cl.getChildren() != null) {
								Checkbox ck = (Checkbox) cl.getChildren()
										.get(0);
								if (ck.isChecked()) {
									if (ck.getValue() != null) {
										CoServicePolicyTitleModel m = new CoServicePolicyTitleModel();
										m = ck.getValue();
										titlelist.add(m);
									}
								}
							}
						}
					}
				}
			}
			if (titlelist.size() > 0) {
				if (model != null) {
					ml = model;
				} else {
					ml.setNote_id(titlelist.get(0).getItem_type_id());
					ml.setNote_type(title);
				}
				ml.setTitlelist(titlelist);
				outlist.add(ml);
			}
		}
		if (outlist.size() <= 0) {
			outlist = list;
		}
		plyUtil ply = new plyUtil();
		String path = "/../../CoServicePolicy/file/downloadfile/";// 导出保存路径
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = sdf.format(date) + ".xls";// 定义导出文件名称
		// 获取绝对路径
		path = ply.getAbsolutePath(path, filename, this);
		// System.out.println(path);
		// 创建文件
		File file = new File(path);
		file.createNewFile();
		String sheetName = filename;// Excel表格名

		CoAgencyBaseCityRelModel coagmodel = new CoAgencyBaseCityRelModel();
		BaseInfo_SelBll coagbll = new BaseInfo_SelBll();
		coagmodel = coagbll.getCoabModel(cabc_id);
		// 定义表头
		String[] title = { "类型编号", "类型", "标题编号", "标题", "内容" };
		try {
			// 使用自己写的Excel导出实现类把数据写入Excel
			ExcelService exl = new ExcelImpl(file, sheetName, title, outlist,
					coagmodel);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Filedownload.save(file, "xls");// 导出Excel
		// file.delete();
	}

	// 导出数据
	@Command
	public void Export2(HttpServletResponse response,
			@BindingParam("gd") Grid gd) throws IOException {
		List<CoServicePolicyTypeModel> outlist = new ArrayList<CoServicePolicyTypeModel>();
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			String title = "";
			List<CoServicePolicyTitleModel> titlelist = new ArrayList<CoServicePolicyTitleModel>();
			CoServicePolicyTypeModel ml = new CoServicePolicyTypeModel();
			CoServicePolicyTypeModel model = new CoServicePolicyTypeModel();
			if (gd.getCell(i, 0) != null) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren() != null) {
					Checkbox check = (Checkbox) cell.getChildren().get(0);
					if (check.getValue() != null) {
						model = check.getValue();
					}
				}
				if (cell.getChildren().get(1) != null) {
					Label lb = (Label) cell.getChildren().get(1);
					if (lb.getValue() != null) {
						title = lb.getValue();
					}
				}
				
			}
			if (gd.getCell(i, 1) != null) {
				Cell cell = (Cell) gd.getCell(i, 1);
				if (cell.getChildren() != null) {
					Grid grid = (Grid) cell.getChildren().get(0);
					for (int y = 0; y < grid.getRows().getChildren().size(); y++) {
						if (grid.getCell(y, 0) != null) {
							Cell cl = (Cell) grid.getCell(y, 0);
							if (cl.getChildren() != null) {
								Checkbox ck = (Checkbox) cl.getChildren()
										.get(0);
								if (ck.isChecked()) {
									if (ck.getValue() != null) {
										CoServicePolicyTitleModel m = new CoServicePolicyTitleModel();
										m = ck.getValue();
										titlelist.add(m);
									}
								}
							}
						}
					}
				}
			}
			if (titlelist.size() > 0) {
				if (model != null) {
					ml = model;
				} else {
					ml.setNote_id(titlelist.get(0).getItem_type_id());
					ml.setNote_type(title);
				}
				ml.setTitlelist(titlelist);
				outlist.add(ml);
			}
		}
		if (outlist.size() <= 0) {
			outlist = list;
		}
		plyUtil ply = new plyUtil();
		String path = "/../../CoServicePolicy/file/downloadfile/";// 导出保存路径
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = sdf.format(date) + ".xls";// 定义导出文件名称
		// 获取绝对路径
		path = ply.getAbsolutePath(path, filename, this);
		// System.out.println(path);
		// 创建文件
		File file = new File(path);
		file.createNewFile();
		String sheetName = filename;// Excel表格名

		CoAgencyBaseCityRelModel coagmodel = new CoAgencyBaseCityRelModel();
		BaseInfo_SelBll coagbll = new BaseInfo_SelBll();
		coagmodel = coagbll.getCoabModel(cabc_id);
		try {
			// 使用自己写的Excel导出实现类把数据写入Excel
			ExcelService exl = new OutTempExcelImpl(file, sheetName, outlist,
					coagmodel);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Filedownload.save(file, "xls");// 导出Excel
		// file.delete();
	}

	// 弹出导入更新页面
	@Command
	@NotifyChange("list")
	public void importexcel() {
		Map map = new HashMap<>();
		BaseInfo_SelBll selbll = new BaseInfo_SelBll();
		CoAgencyBaseCityRelModel citymodel = selbll.getCoabModel(cabc_id);
		map.put("model", citymodel);
		Window window = (Window) Executions.createComponents(
				"../CoServicePolicy/SePy_Import.zul", null, map);
		window.doModal();
		list = bll.getCoServicePolicyTypeLists(cabc_id, "");
	}

	// 给titlelist赋值
	private void addTitlelist() {
		titlelist.clear();
		for (CoServicePolicyTypeModel ml : list) {
			titlelist.addAll(ml.getTitlelist());
		}
	}

	public List<CoServicePolicyTypeModel> getList() {
		return list;
	}

	public List<CoServicePolicyTypeModel> getTypelist() {
		return typelist;
	}

	public List<CoServicePolicyTitleModel> getTitlelist() {
		return titlelist;
	}

	public String getTiptxt() {
		return tiptxt;
	}

}
