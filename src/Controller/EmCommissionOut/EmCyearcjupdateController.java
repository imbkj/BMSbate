package Controller.EmCommissionOut;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Column;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmSalary.EmSalary_SalaryOperateBll;
import Model.EmCommissionYearChangemModel;
import Model.EmCommissionyearchangetitleModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmCommissionOut.EmCmmissionyearchangeupdateBll;

public class EmCyearcjupdateController {

	/**
	 * @param args
	 */

	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());

	private final int d_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("d_id").toString());

	private final int t_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("t_id").toString());

	private Window wininfo = (Window) Executions.getCurrent().getArg()
			.get("wininfo");

	private final String nowstr = DateStringChange
			.Datestringnow("YYmmDDHHMMSS");

	private final String filename = nowstr + "" + cid + "wtjscj";
	private final String filetype = ".xls";

	private final String downfolder = "EmCommissionOut/File/Download/Wtjscj/";

	private final int dataCell = 0;
	private final int dataRow = 1;
	private String absolutePath;
	private List<EmCommissionYearChangemModel> ecycmodellist;
	private EmCommissionyearchangetitleModel model;
	private Media media;
	private String uploadFlieName;
	private EmCmmissionyearchangeupdateBll bll = new EmCmmissionyearchangeupdateBll();

	public EmCyearcjupdateController() {
		setModel();
		setEcycmodellist();
		absolutePath = FileOperate.getAbsolutePath();
	}

	// 下载模板
	@Command("downloadTemplet")
	public void downloadTemplet() {
		String path = downfolder + filename + filetype;
		// String path = downfolder;
		try {
			/*
			 * // 查找文件 检查是否存在模板文件 if (FileOperate.existFile(downfolder, filename
			 * + filetype)) { FileOperate.download(path); } else {
			 */
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
		String templet = "EmCommissionOut/File/Templet/wt_cj_Tp.xls";
		int success = 0;
		try {
			System.out.println(absolutePath + templet);
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
			// 插入表头
			
			int sb=0;
			int gjj=0;
			int bcgjj=0;
			
			
			try {
				int i = 6;
				
				if (model.getEcyt_ylao()+model.getEcyt_gshang()+model.getEcyt_yliao()+model.getEcyt_sye()+model.getEcyt_syu()>0)
				{
					i = i + 1;
					label = new Label(i, 0, "社保基数", wcf);
					sheet.addCell(label);
					sb=1;
				}
				


				if (model.getEcyt_gjj() == 1) {
					i = i + 1;
					label = new Label(i, 0, "住房基数", wcf);
					sheet.addCell(label);

					i = i + 1;
					label = new Label(i, 0, "住房企业比例", wcf);
					sheet.addCell(label);

					i = i + 1;
					label = new Label(i, 0, "住房个人比例", wcf);
					sheet.addCell(label);

					i = i + 1;
					label = new Label(i, 0, "住房缴存额", wcf);
					sheet.addCell(label);
					gjj=1;
				}

				if (model.getEcyt_bcgjj() == 1) {
					i = i + 1;
					label = new Label(i, 0, "补充住房基数", wcf);
					sheet.addCell(label);
					i = i + 1;
					label = new Label(i, 0, "补充住房企业比例", wcf);
					sheet.addCell(label);
					i = i + 1;
					label = new Label(i, 0, "补充住房个人比例", wcf);
					sheet.addCell(label);
					i = i + 1;
					label = new Label(i, 0, "补充住房缴存额", wcf);
					sheet.addCell(label);
					
					bcgjj=1;
				}

				label = new Label(i + 1, 0, "备注", wcf);
				sheet.addCell(label);
				if (model.getEcyt_gjj() == 1) {
					
					i = i + 1;
					label = new Label(i+1, 0, "原公司住房基数", wcf);
					sheet.addCell(label);
					
					i = i + 1;
					label = new Label(i+1, 0, "原个人住房基数", wcf);
					sheet.addCell(label);

					i = i + 1;
					label = new Label(i+1, 0, "原住房公司比例", wcf);
					sheet.addCell(label);

					i = i + 1;
					label = new Label(i+1, 0, "原住房个人比例", wcf);
					sheet.addCell(label);

				 
				}
				
				
				

				// 插入表数据
				for (int x = 0; x <= ecycmodellist.size()-1; x++) {
					label = new Label(0, x + 1, ecycmodellist.get(x).getCid()
							.toString(), wcf);
					sheet.addCell(label);
					label = new Label(1, x + 1, ecycmodellist.get(x)
							.getCoba_shortname(), wcf);
					sheet.addCell(label);
					label = new Label(2, x + 1, ecycmodellist.get(x).getGid()
							.toString(), wcf);
					sheet.addCell(label);
					label = new Label(3, x + 1, ecycmodellist.get(x)
							.getEmba_name(), wcf);
					sheet.addCell(label);
					label = new Label(4, x + 1, ecycmodellist.get(x)
							.getEmba_idcard(), wcf);
					sheet.addCell(label);
					label = new Label(5, x + 1, ecycmodellist.get(x).getEcyt_id().toString(), wcf);
					sheet.addCell(label);
					label = new Label(6, x + 1, ecycmodellist.get(x)
							.getCoab_name(), wcf);
					
					sheet.addCell(label);
					//有住房，有社保，有补充 9
					//有住房，有补充 ，无社保8
					//有社保，有住房 5
					//有社保，有补充 5
					//有住房，无补充 4
					//有补充，无住房 4
					
					if((sb+gjj+bcgjj)==3) //9
					{
						label = new Label(17, x + 1, ecycmodellist.get(x)
								.getEcyc_ycohouse_base().toString(), wcf);
						sheet.addCell(label);
						label = new Label(18, x + 1, ecycmodellist.get(x)
								.getEcyc_yemhouse_base().toString(), wcf);
						sheet.addCell(label);
						label = new Label(19, x + 1, ecycmodellist.get(x)
								.getEcyc_yhousecp(), wcf);
						sheet.addCell(label);
						label = new Label(20, x + 1, ecycmodellist.get(x)
								.getEcyc_yhouseop(), wcf);
						sheet.addCell(label);
					}
					
					if(sb==0 &(gjj+bcgjj)==1) //4
					{
						label = new Label(12, x + 1, ecycmodellist.get(x)
								.getEcyc_ycohouse_base().toString(), wcf);
						sheet.addCell(label);
						label = new Label(13, x + 1, ecycmodellist.get(x)
								.getEcyc_yemhouse_base().toString(), wcf);
						sheet.addCell(label);
						label = new Label(14, x + 1, ecycmodellist.get(x)
								.getEcyc_yhousecp(), wcf);
						sheet.addCell(label);
						label = new Label(15, x + 1, ecycmodellist.get(x)
								.getEcyc_yhouseop(), wcf);
						sheet.addCell(label);
					}
					
					if(sb==1 &(gjj+bcgjj)==1) //5
					{
						label = new Label(13, x + 1, ecycmodellist.get(x)
								.getEcyc_ycohouse_base().toString(), wcf);
						sheet.addCell(label);
						label = new Label(14, x + 1, ecycmodellist.get(x)
								.getEcyc_yemhouse_base().toString(), wcf);
						sheet.addCell(label);
						label = new Label(15, x + 1, ecycmodellist.get(x)
								.getEcyc_yhousecp(), wcf);
						sheet.addCell(label);
						label = new Label(16, x + 1, ecycmodellist.get(x)
								.getEcyc_yhouseop(), wcf);
						sheet.addCell(label);
					}
					if(sb==0 &(gjj+bcgjj)==2) //8
					{
						label = new Label(16, x + 1, ecycmodellist.get(x)
								.getEcyc_ycohouse_base().toString(), wcf);
						sheet.addCell(label);
						label = new Label(17, x + 1, ecycmodellist.get(x)
								.getEcyc_yemhouse_base().toString(), wcf);
						sheet.addCell(label);
						label = new Label(18, x + 1, ecycmodellist.get(x)
								.getEcyc_yhousecp(), wcf);
						sheet.addCell(label);
						label = new Label(19, x + 1, ecycmodellist.get(x)
								.getEcyc_yhouseop(), wcf);
						sheet.addCell(label);
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
			if (model != null) {
				if (this.media != null) {
					String uploadfolder = "EmCommissionOut/File/Upload/nt/";
					String uploadName = mosaicFileName();
					// 上传文件至服务器
					if (FileOperate.upload(media, uploadfolder + uploadName)) {
						String[] message = getExcel(absolutePath + uploadfolder
								+ uploadName);
						if (message[0].equals("1") || message[0].equals("0")) {
							// Binder bind = (Binder) view.getParent()
							// .getAttribute("binder");
							// bind.postCommand("setemcomm", null);

							// 弹出提示
							Messagebox.show(message[1], "操作提示", Messagebox.OK,
									Messagebox.NONE);

							view.detach();
							win.detach();
							wininfo.detach();

							Map<String, Object> map = new HashMap<String, Object>();
							map.put("daid", d_id);
							map.put("id", t_id);
							Window window = (Window) Executions
									.createComponents(
											"../EmCommissionOut/EmCyearaddinfolist.zul",
											null, map);
							window.doModal();

						} else {
							// 弹出提示
							Messagebox.show(message[1], "操作提示", Messagebox.OK,
									Messagebox.ERROR);
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
				Messagebox.show("该月份暂无可修改的工资数据，无需导入变动。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
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

	// 获取上传Excel的内容
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
			if (rows > 1) {

				message = getExcelData(st, filepath);

			} else {
				message[0] = "2";
				message[1] = "导入的Excel并未找到数据。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "数据获取出错。";
		} finally {
			wb.close();
			file.delete();
		}
		return message;
	}

	// // 核对数据格式
	// private boolean checkTh(Sheet st) {
	// for (int i = 0; i < ecycmodellist.size(); i++) {
	// try {
	// EmCommissionYearChangemModel m = ecycmodellist.get(i);
	// if (!m.getCsii_item_name().equals(
	// st.getCell(i + dataCell, 2).getContents())) {
	// return false;
	// }
	// } catch (Exception e) {
	// return false;
	// }
	// }
	// return true;
	// }
	
	
	private BigDecimal getBigDecimal(String Bdc)
	{
		System.out.println(Bdc);
		if (Bdc==null||Bdc.equals(""))
		{
		return BigDecimal.ZERO;
		}
		else
		{
			return new BigDecimal(Bdc);
		}
	}

	// 存放数据至LIST
	@SuppressWarnings("null")
	private String[] getExcelData(Sheet st, String Strpath) {
		StringBuilder upstrsql = new StringBuilder();
		String[] message = new String[3];
		int massgeint = 0;

		try {
			// 遍历数据行
			for (int r = dataRow; r < st.getRows(); r++) {
				upstrsql.delete(0, upstrsql.length());
				upstrsql.append("update EmCommissionYearChange set ecyc_excel=1,");
				int i = 6;
				
				
				if (model.getEcyt_ylao()+model.getEcyt_gshang()+model.getEcyt_yliao()+model.getEcyt_sye()+model.getEcyt_syu()>0)
				{
					i++;
					System.out.println(st.getCell(i, r).getContents().toString());
					upstrsql.append("ecyc_sb_base="
							+ getBigDecimal(st.getCell(i, r).getContents())
							+ ",");
				}
				
//				if (model.getEcyt_ylao() == 1) {
//					i++;
//					upstrsql.append("ecyc_yl_base="
//							+ new BigDecimal(st.getCell(i, r).getContents())
//							+ ",");
//				}
//
//				if (model.getEcyt_gshang() == 1) {
//					i = i + 1;
//					upstrsql.append("ecyc_gs_base="
//							+ new BigDecimal(st.getCell(i, r).getContents())
//							+ ",");
//
//				}
//
//				if (model.getEcyt_yliao() == 1) {
//					i = i + 1;
//					upstrsql.append("ecyc_jl_base="
//							+ new BigDecimal(st.getCell(i, r).getContents())
//							+ ",");
//
//				}
//
//				if (model.getEcyt_sye() == 1) {
//					i = i + 1;
//					upstrsql.append("ecyc_sye_base="
//							+ new BigDecimal(st.getCell(i, r).getContents())
//							+ ",");
//
//				}
//				if (model.getEcyt_syu() == 1) {
//					i = i + 1;
//					upstrsql.append("ecyc_syu_base="
//							+ new BigDecimal(st.getCell(i, r).getContents())
//							+ ",");
//				}

				if (model.getEcyt_gjj() == 1) {
					i = i + 1;
					upstrsql.append("ecyc_house_base="
							+ getBigDecimal(st.getCell(i, r).getContents())
							+ ",");

					i = i + 1;
					
					upstrsql.append("ecyc_house_cp="
							+ String.valueOf(st.getCell(i, r).getContents())
							+ ",");
					BigDecimal p1=getBigDecimal(st.getCell(i, r).getContents());

					i = i + 1;
					upstrsql.append("ecyc_house_op="
							+ String.valueOf(st.getCell(i, r).getContents())
							+ ",");
					BigDecimal p2=getBigDecimal(st.getCell(i, r).getContents());
					i = i + 1;
					if (st.getCell(i, r).getContents()!=null)
					{
					
					upstrsql.append("ecyc_house_hj="
							+ getBigDecimal(st.getCell(i, r).getContents())
							+ ",");
					}
					else
					{
						BigDecimal m1=getBigDecimal(st.getCell(i, r).getContents());
						BigDecimal house_hj= m1.multiply(p1.add(p2));
							//	 (getBigDecimal(st.getCell(i, r).getContents()+getBigDecimal(st.getCell(i, r).getContents())
						 
						upstrsql.append("ecyc_house_hj="
								+ house_hj
								+ ",");
					}
				}

				if (model.getEcyt_bcgjj() == 1) {
					i = i + 1;
					upstrsql.append("ecyc_bc_base="
							+ getBigDecimal(st.getCell(i, r).getContents())
							+ ",");

					i = i + 1;
					upstrsql.append("ecyc_bc_cp="
							+ String.valueOf(st.getCell(i, r).getContents())
							+ ",");
					BigDecimal pc1=getBigDecimal(st.getCell(i, r).getContents());
					i = i + 1;
					upstrsql.append("ecyc_bc_op="
							+ String.valueOf(st.getCell(i, r).getContents())
							+ ",");
					BigDecimal pc2=getBigDecimal(st.getCell(i, r).getContents());
					i = i + 1;
				
					if (st.getCell(i, r).getContents()!=null)
					{
					
						upstrsql.append("ecyc_bc_total="
								+ getBigDecimal(st.getCell(i, r).getContents())
								+ ",");
					}
					else
					{
						BigDecimal mc1=getBigDecimal(st.getCell(i, r).getContents());
						BigDecimal bc_total= mc1.multiply(pc1.add(pc2));
							//	 (getBigDecimal(st.getCell(i, r).getContents()+getBigDecimal(st.getCell(i, r).getContents())
						 
						upstrsql.append("ecyc_bc_total="
								+ bc_total
								+ ",");
					}
					
					
				}
				
				if (st.getCell(i, r).getContents()!=null)
				{
					i++;
					upstrsql.append("ecyc_remark='"
							+ String.valueOf(st.getCell(i, r).getContents())
							+ "',");
				}
				

				upstrsql.append("ecyc_excel_date='"
						+ DateStringChange.Datestringnow("yy-MM-dd hh:mm:ss")
						+ "',");
				upstrsql.append("ecyc_excel_file='" + Strpath + "',");
				upstrsql.append("ecyc_state=1, ");
				upstrsql.append("ecyc_modname='"+UserInfo.getUsername()+"',");
				upstrsql.append("ecyc_modtime=getdate() ");
				upstrsql.append("FROM EmCommissionYearChange a ,EmBase b  ");
				
				
				upstrsql.append("where ecyc_state=0 and  ecyc_city='"
						+ String.valueOf(st.getCell(6, r).getContents()) + "' "
						+ "and a.gid="
						+ Integer.parseInt(st.getCell(2, r).getContents()) + ""
						+ "and ecyt_id="
						+ Integer.parseInt(st.getCell(5, r).getContents()) + ""
						+ " and b.emba_idcard='"
						+ String.valueOf(st.getCell(4, r).getContents()) + "'");
 
				massgeint = massgeint + bll.updateyeardata(upstrsql.toString());

			}
			message[0] = "0";
			message[1] = "共更新" + (st.getRows() - 1) + "行，其中成功" + massgeint
					+ "行";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	public List<EmCommissionYearChangemModel> getEcycmodellist() {
		return ecycmodellist;
	}

	@SuppressWarnings("unchecked")
	public void setEcycmodellist() {
		this.ecycmodellist = (List<EmCommissionYearChangemModel>) Executions
				.getCurrent().getArg().get("ecycmodellist");
	}

	public EmCommissionyearchangetitleModel getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setModel() {
		this.model = (EmCommissionyearchangetitleModel) Executions.getCurrent()
				.getArg().get("model");
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public void setUploadFlieName(String uploadFlieName) {
		this.uploadFlieName = uploadFlieName;
	}

}
