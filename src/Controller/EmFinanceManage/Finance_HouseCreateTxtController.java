package Controller.EmFinanceManage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import Model.SbGatherModel;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmFinanceManage.SbGatherBll;

public class Finance_HouseCreateTxtController {
	private String ownmonth = (String) Executions.getCurrent().getArg()
			.get("ownmonth");
	private String number = "";
	private List<SbGatherModel> list = new ArrayList<SbGatherModel>();
	private BigDecimal s[]=new BigDecimal[2];

	@Command
	public void submit(@BindingParam("makedate") Date makedate) {
		if (makedate == null) {
			Messagebox.show("填制时间不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (number == null || number.equals("")) {
			Messagebox.show("凭证号不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			SbGatherBll bll = new SbGatherBll();
			list = bll.getHhouseGatherList("", ownmonth, "0");
			s= bll.getHsTotalInfo(ownmonth);
			if (list.size() > 0) {
				String filePath = "/../../EmFinanceManage/downfile";
				plyUtil ply = new plyUtil();
				String path = ply.getAbsolutePath(filePath, "", this);
				String fileName = "HouseCreateTxt";
				File file = new File(path);
				if (!file.exists()) {
					file.mkdir();
					System.out.println("文件夹已创建");
				}
				// 定义文件名格式并创建
				File txtFile = null;
				try {
					// System.out.print("fileName="+fileName);
					txtFile = File.createTempFile(fileName, ".txt", new File(
							path));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 先读取原有文件内容，然后进行写入操作
				FileWriter writer = null;
				FileOutputStream outSTr = null;
				BufferedOutputStream Buff = null;
				try {
					writer = new FileWriter(txtFile, true);
					outSTr = new FileOutputStream(txtFile);

					Buff = new BufferedOutputStream(outSTr);
					StringBuffer write = new StringBuffer();
					String newline = "\r\n";

					/********************* 每个账单的第一行 *********************************/
					String firstline = "";
					firstline = "填制凭证,V800";
					String username = UserInfo.getUsername();
					String tline = dateToStr(makedate) + ",记," + number
							+ ",0,付" + ownmonth + "月住房公积金,10020104,0.00,"
							+ s[0] + ",,,," + username
							+ ",,,,,,,,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
					/******************************************************/
					write.append(firstline);
					write.append(newline);// 换行
					write.append(tline);
					write.append(newline);// 换行
					for (SbGatherModel m : list) {
						String ufclass=m.getCoba_hsufclass();
						if(ufclass==null)
						{
							ufclass="";
						}
						String ufid2=m.getCoba_ufid2();
						if(ufid2==null)
						{
							ufid2="";
						}
						String line = dateToStr(makedate) + ",记," + number
								+ ",0,付" + "" + ownmonth + "月住房公积金,"
								+ ufclass + "," + m.getHstotal()
								+ ",0.00,0,0,," + username + ",,,,,,"
								+ ufid2
								+ ",,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
						write.append(line);
						write.append(newline);// 换行
					}
					Buff.write(write.toString().getBytes("UTF-8"));
					/************************* 对txt文件写入内容End ************************************/
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							Buff.close();
							outSTr.close();
							writer.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				}
				try {
					Filedownload.save(txtFile, "txt");
					/*
					 * for (GatherInfoModel m : list) {
					 * bll.updateYyOutState(m.getCfsa_id()); }
					 */
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 导出txt
			} else {
				Messagebox.show("没有数据", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	private String dateToStr(Date date) {
		String datestr = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			try {
				datestr = format.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datestr;
	}
}
