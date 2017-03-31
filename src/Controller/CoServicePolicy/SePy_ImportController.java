package Controller.CoServicePolicy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoServicePolicy.SePy_CityPolicyOperateBll;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

import Model.CoAgencyBaseCityRelModel;
import Model.CoServicePolicyModel;
import Model.CoServicePolicyTitleModel;
import Util.FileOperate;
import Util.UserInfo;

public class SePy_ImportController {
	private String filename;
	private InputStream inputStream = null;
	private Media media;
	private CoAgencyBaseCityRelModel citymodel = (CoAgencyBaseCityRelModel)Executions.getCurrent().getArg().get("model");

	@Command
	@NotifyChange("filename")
	public void onupload(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev) {
		media = null;
		inputStream = null;
		media = ev.getMedia();
		if (media != null) {
			if (media.getFormat().equals("xls") || media.getFormat() == "xls"
					|| media.getFormat().equals("xlsx")
					|| media.getFormat() == "xlsx") {
				try {
					this.filename = media.getName();
					this.inputStream = media.getStreamData();
				} catch (Exception e) {
					System.out.println("错误:" + e.getMessage());
				}
			} else {
				Messagebox.show("选择的文件格式不正确", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	@Command
	public void submit(@BindingParam("win") Window win) {
		if (filename != null && !filename.equals("")) {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String om = "" + month;
			if (month < 10) {
				om = "0" + month;
			}
			String strowm = year + "" + om;
			String realPath = "CoServicePolicy/file/uploadfile/";
			String filepath = FileOperate.getAbsolutePath() + realPath
					+ filename;
			SePy_CityPolicyOperateBll obll=new SePy_CityPolicyOperateBll();
			int id=0;
			if (inputStream != null && !inputStream.equals("")) {
				if (media != null) {
					boolean flag = FileOperate.upload(media, realPath
							+ filename);
					if (flag) {
						// 创建对Excel工作簿文件的引用
						HSSFWorkbook workbook;
						try {
							workbook = new HSSFWorkbook(new FileInputStream(
									filepath));
							// 创建对工作表的引用。
							HSSFSheet sheet = workbook.getSheetAt(0);
							HSSFRow row;
							for (int i = sheet.getFirstRowNum(); i < sheet
									.getPhysicalNumberOfRows()+1; i++) {
								if (i > 2) {
									row = sheet.getRow(i);
									if (row != null
											&& row.getCell(2) != null
											&& row.getCell(2)
													.getStringCellValue() != null
											&& !row.getCell(2)
													.getStringCellValue()
													.equals("")) {
										String item_id = row.getCell(2)
												.getStringCellValue();
										if (item_id != null
												&& !item_id.equals("")) {
											SePy_CityPolicySelectBll bll = new SePy_CityPolicySelectBll();
											CoServicePolicyTitleModel tm = bll
													.getPolicyTitleModel(Integer
															.parseInt(item_id));
											if (tm != null) {
												String content = "";
												if (row.getCell(4) != null
														&& row.getCell(4)
																.getStringCellValue() != null) {
													content = row
															.getCell(4)
															.getStringCellValue();
												}
												CoServicePolicyModel model = new CoServicePolicyModel();
												model.setOwnmonth(Integer
														.parseInt(strowm));
												model.setSypo_title(tm
														.getItem_title());
												model.setSypo_agencies(citymodel.getCoab_name());
												model.setSypo_city(citymodel.getCity());
												model.setSypo_type(tm
														.getNote_type());
												model.setSypo_addname(UserInfo
														.getUsername());
												model.setSypo_content(content);
												model.setSypo_cityid(citymodel.getId());
												model.setSypo_cabc_id(citymodel.getCabc_id());
												model.setSypo_item_id(Integer
														.parseInt(item_id));
												id=id+obll.CoServicePolicyAdd(model);
											}
										}

									}
								}
							}
							if(id>0)
							{
								Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
								win.detach();
							}
							else
							{
								Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			} else {
				Messagebox.show("请选择导入文件", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择导入文件", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
