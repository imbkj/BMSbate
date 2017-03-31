package Controller.EmFinanceManage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import bll.EmFinanceManage.SbGatherBll;

import Model.EmFinanceWtModel;
import Model.SbGatherModel;
import Util.UserInfo;
import Util.plyUtil;

public class Finance_SbCreateTxtController {
	private String ownmonth = (String) Executions.getCurrent().getArg()
			.get("ownmonth");
	private String number = "";
	private List<SbGatherModel> list = new ArrayList<SbGatherModel>();
	private BigDecimal s[] = new BigDecimal[2];
	private DecimalFormat myformat = new DecimalFormat("0.00");

	@Command
	public void submit(@BindingParam("makedate") Date makedate) {
		if (makedate == null) {
			Messagebox.show("填制时间不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		} else if (number == null || number.equals("")) {
			Messagebox.show("凭证号不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			SbGatherBll bll = new SbGatherBll();
			list = bll.getSbGatherList(" and z.ownmonth=" + ownmonth, ownmonth,
					"0");
			s = bll.getSbTotalInfo(ownmonth);
			if (list.size() > 0) {
				String filePath = "/../../EmFinanceManage/downfile";
				plyUtil ply = new plyUtil();
				String path = ply.getAbsolutePath(filePath, "", this);
				String fileName = "FinanceSZCreateTxt";
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
					String month=ownmonth.substring(ownmonth.length()-2,ownmonth.length());
					String tline = dateToStr(makedate) + ",记," + number
							+ ",0,付" + month + "月社保费,10020104,0.00 ," + myformat.format(s[0])
							+ " ,,,," + username
							+ ",,,,,,,,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
					/******************************************************/
					write.append(firstline);
					write.append(newline);// 换行
					write.append(tline);
					write.append(newline);// 换行
					List<SbGatherModel> nlist = Merger();
					for (SbGatherModel m : list) {
						String ufclass = m.getCoba_ufclass();
						if (ufclass == null) {
							ufclass = "";
						}
						String ufid2 = m.getCoba_ufid2();
						if (ufid2 == null) {
							ufid2 = "";
						}
						String line = dateToStr(makedate) + ",记," + number
								+ ",0,付" + "" + month + "月社保费," + ufclass
								+ "," + m.getSbktotal() + ",0.00,0,0,,"
								+ username + ",,,,,," + ufid2
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

	// 合并数据——相同用友编号的数据合并为一条
	private List<SbGatherModel> Merger() {
		List<SbGatherModel> nlist = new ArrayList<SbGatherModel>();
		for (SbGatherModel m : list) {
			Integer k = 0;
			if (m.getCoba_ufclass() != null) {
				for (SbGatherModel nm : nlist) {
					if (nm.getCoba_ufclass() != null) {
						if (nm.getCoba_ufclass().equals(m.getCoba_ufclass())) {
							BigDecimal total = nm.getSbktotal().add(
									m.getSbktotal());
							nm.setSbktotal(total);
							k = 1;
							break;
						}
					}
				}
			}
			if (k == 0) {
				nlist.add(m);
			}
		}
		return nlist;
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
