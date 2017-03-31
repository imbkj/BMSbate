package Controller.EmSalary;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmSalary.EmSalary_SalaryOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_UploadBatchAddController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());
	private List<EmSalaryBaseAddItemModel> itemList;
	private List<EmSalaryDataModel> salaryBaseList;
	private final String filename = ownmonth + "" + cid + "SalaryBatchAdd";
	private final String filetype = ".xls";
	private final String downfolder = "EmSalary/File/Download/SalaryBatchAdd/";
	private final int dataCell = 3;
	private final int dataRow = 3;
	private String absolutePath;
	private Media media;
	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
	private String uploadFlieName;
	private String usageType = "";

	public EmSalary_UploadBatchAddController() {
		salaryBaseList = bll.getEmSalaryBase(cid);
		setItemList();
		absolutePath = FileOperate.getAbsolutePath();
	}

	// 下载模板
	@Command("downloadTemplet")
	public void downloadTemplet() {
		String path = downfolder + filename + filetype;
		try {
			try {
				File f = new File(absolutePath + path);
				if (f.isFile()) {
					f.delete();
				}
				int i = createTemplet();
				if (i == 1) {
					FileOperate.download(path);
				} else {
					Messagebox.show("模板生成失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("模板生成出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 生成模板文件
	private int createTemplet() {
		String templet = "EmSalary/File/Templet/SalaryBatchAdd.xls";
		int success = 0;
		try {
			// 读取Excel模板
			Workbook wb = Workbook
					.getWorkbook(new File(absolutePath + templet));
			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(new File(
					absolutePath + downfolder + filename + filetype), wb);
			// 生成工作表
			WritableSheet sheet = wwb.getSheet(0);
			// 设置字体格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
			WritableCellFormat wcf = new WritableCellFormat(wf);
			// 插入第一行
			Label label = null;
			jxl.write.Number num = null;
			num = new jxl.write.Number(1, 0, ownmonth, wcf);
			sheet.addCell(num);
			label = new Label(4, 0, bll.getCoShortName(cid) + "数据导入模板", wcf);
			sheet.addCell(label);
			// 插入表头
			try {
				for (int i = 0; i < itemList.size(); i++) {
					label = new Label(i + dataCell, dataRow - 1, itemList
							.get(i).getCsii_item_name(), wcf);
					sheet.addCell(label);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 插入数据
			try {
				for (int i = 0; i < salaryBaseList.size(); i++) {
					num = new jxl.write.Number(0, dataRow + i, salaryBaseList
							.get(i).getGid());
					sheet.addCell(num);
					label = new Label(1, dataRow + i, salaryBaseList.get(i)
							.getName());
					sheet.addCell(label);
					label = new Label(2, dataRow + i, "");
					sheet.addCell(label);
					for (int j = 0; j < itemList.size(); j++) {
						num = new jxl.write.Number(j + dataCell, dataRow + i, 0);
						sheet.addCell(num);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			success = 1;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return success;
	}

	// 文件检查
	@Command("browse")
	@NotifyChange("uploadFlieName")
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		this.media = upEvent.getMedia();
		if ("xls".equals(media.getFormat())) {
			uploadFlieName = media.getName();
		} else {
			this.media = null;
			uploadFlieName = "";
			Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	// 提交并导入数据
	@Command("upload")
	public void upload(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (salaryBaseList.size() > 0) {
				if (usageType != null && !"".equals(usageType)) {
					if (this.media != null) {
						String uploadfolder = "EmSalary/File/Upload/SalaryBatchAdd/";
						String uploadName = mosaicFileName();
						// 上传文件至服务器
						if (FileOperate
								.upload(media, uploadfolder + uploadName)) {
							// 获取上传Excel的内容,并更新至数据库
							String[] message = getExcel(absolutePath
									+ uploadfolder + uploadName);
							if ("1".equals(message[0])
									|| "0".equals(message[0])) {
								Binder bind = (Binder) view.getParent()
										.getAttribute("binder");
								bind.postCommand("setSalaryList", null);
								// 弹出提示
								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.NONE);
								win.detach();
							} else {
								// 弹出提示
								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.ERROR);
							}
						} else {
							Messagebox.show("文件上传出错。", "操作提示", Messagebox.OK,
									Messagebox.NONE);
						}
					} else {
						Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
					}
				} else {
					Messagebox.show("请选择导入数据用途。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			} else {
				Messagebox.show("该公司无可导入的人员，无法批量操作（请查看该公司员工是否未分配报价单）。", "操作提示",
						Messagebox.OK, Messagebox.NONE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("数据导入出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + filename + filetype;
		return name;
	}

	// 获取上传Excel的内容,并更新至数据库
	private String[] getExcel(String filepath) {
		String[] message = new String[3];
		File file = null;
		Workbook wb = null;
		try {
			file = new File(filepath);
			// 读取Excel文件
			wb = Workbook.getWorkbook(file);
			// 读取工作表
			Sheet st = wb.getSheet(0);
			int rows = st.getRows();
			if (rows > 3) {
				// 核对Excel数据格式
				if (checkTh(st)) {
					// 存放数据至LIST
					List<EmSalaryDataModel> upSalList = getExcelData(st);
					if (upSalList.size() > 0) {
						// 更新至数据库
						message = UpdateToDb(upSalList);
					} else {
						message[0] = "2";
						message[1] = "未找到可导入的数据。";
					}
				} else {
					message[0] = "2";
					message[1] = "导入的Excel与模板的数据格式不一致。";
				}
			} else {
				message[0] = "2";
				message[1] = "导入的Excel并未找到工资数据。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "数据获取出错。";
		} finally {
			wb.close();
		}
		return message;
	}

	// 核对数据格式
	private boolean checkTh(Sheet st) {
		for (int i = 0; i < itemList.size(); i++) {
			try {
				EmSalaryBaseAddItemModel m = itemList.get(i);
				if (!m.getCsii_item_name().equals(
						st.getCell(i + dataCell, 2).getContents())) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	// 存放数据至LIST
	private List<EmSalaryDataModel> getExcelData(Sheet st) {
		List<EmSalaryDataModel> list = new ArrayList<EmSalaryDataModel>();
		EmSalaryDataModel salaryModel = null;
		try {
			String username = UserInfo.getUsername();
			// 遍历数据行
			for (int r = dataRow; r < st.getRows(); r++) {
				if (st.getCell(0, r).getContents() == null
						|| "".equals(st.getCell(0, r).getContents())) {
					break;
				}
				salaryModel = null;
				// 查找对应的Model
				for (EmSalaryDataModel m : salaryBaseList) {
					if (String.valueOf(m.getGid()).equals(
							st.getCell(0, r).getContents())
							&& m.getName().equals(
									st.getCell(1, r).getContents())) {
						salaryModel = (EmSalaryDataModel) m.clone();
						break;
					}
				}
				if (salaryModel != null) {
					// 遍历数据列
					for (int c = 0; c < itemList.size(); c++) {
						try {
							if (st.getCell(c + dataCell, r).getContents() != null) {
								setField(
										salaryModel,
										itemList.get(c).getCsii_col(),
										new BigDecimal(st.getCell(c + dataCell,
												r).getContents()));
							}
						} catch (Exception e) {

						}
					}
					salaryModel.setEsda_addname(username);
					salaryModel.setEsda_usage_typestr(usageType);
					salaryModel.setOwnmonth(ownmonth);
					list.add(salaryModel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 插入数据至数据库
	private String[] UpdateToDb(List<EmSalaryDataModel> upSalList) {
		String[] message = new String[3];
		try {
			EmSalary_SalaryOperateBll bll = new EmSalary_SalaryOperateBll();
			message = bll.AddSalary(upSalList, itemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	// 调用Set方法
	private void setField(Object obj, String fieldname, Object value) {
		try {
			Method method = obj.getClass().getMethod(setMethod(fieldname),
					BigDecimal.class);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据字段名获取SET方法名；
	private static String setMethod(String name) {
		return "set"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	private void setItemList() {
		this.itemList = (List<EmSalaryBaseAddItemModel>) Executions
				.getCurrent().getArg().get("itemList");
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

}
