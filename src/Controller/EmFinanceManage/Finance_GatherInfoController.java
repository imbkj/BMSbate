package Controller.EmFinanceManage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;

import Model.CoBaseModel;
import Model.GatherInfoModel;
import Util.MonthListUtil;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmFinanceManage.GatherInfoBll;

public class Finance_GatherInfoController {
	private List<CoBaseModel> colist = new ArrayList<CoBaseModel>();
	private List<GatherInfoModel> list = new ArrayList<GatherInfoModel>();
	private GatherInfoBll bll = new GatherInfoBll();
	private String cidstr = "";

	public Finance_GatherInfoController() {
		if (Executions.getCurrent().getArg().get("list") != null) {
			colist = (List<CoBaseModel>) Executions.getCurrent().getArg()
					.get("list");
		}
		if (colist.size() > 0) {
			for (int i = 0; i < colist.size(); i++) {
				if (cidstr == null || cidstr.equals("")) {
					cidstr = cidstr + colist.get(i).getCid();
				} else {
					cidstr = cidstr + "," + colist.get(i).getCid();
				}
			}
		}
		String str = "";
		if (cidstr != null && !cidstr.equals("")) {
			str = " and c.cid in(" + cidstr + ")";
		}
		list = bll.getOwnmonthBillList(str);
	}

	@Command
	@NotifyChange("list")
	public void copylp(@BindingParam("gd") Grid gd,
			@BindingParam("rowIndex") Integer rowIndex) {
		Date makedate = list.get(rowIndex).getMakedate();
		for (int i = rowIndex; i < list.size(); i++) {
			list.get(i).setMakedate(makedate);
		}
	}

	@Command
	@NotifyChange("list")
	public void copynumber(@BindingParam("gd") Grid gd,
			@BindingParam("rowIndex") Integer rowIndex) {
		String number = list.get(rowIndex).getProofnumber();
		for (int i = rowIndex; i < list.size(); i++) {
			list.get(i).setProofnumber(number);
		}
	}

	@Command
	@NotifyChange("list")
	public void cancel(@BindingParam("model") GatherInfoModel model) {
		list.remove(model);
	}

	// 导出
	@Command
	public void downfile() {
		if (list.size() > 0) {
			String filePath = "/../../EmFinanceManage/downfile";
			plyUtil ply = new plyUtil();
			String path = ply.getAbsolutePath(filePath, "", this);
			String fileName = "FinanceGather";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdir();
				System.out.println("文件夹已创建");
			}
			// 定义文件名格式并创建
			File txtFile = null;
			try {
				// System.out.print("fileName="+fileName);
				txtFile = File.createTempFile(fileName, ".txt", new File(path));
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
				/************************* 对txt文件写入内容 ************************************/
				List<GatherInfoModel> numlist = getBillNumberList();

				for (GatherInfoModel ml : numlist) {
					String month = "";
					String ownmonthstr = ml.getOwnmonth().toString();
					if (ml.getCoco_gzmonth() != null) {
						MonthListUtil util = new MonthListUtil();
						if (ml.getCoco_gzmonth().equals("上月")) {
							ownmonthstr = util.getLastMonth(ownmonthstr);
						} else if (ml.getCoco_gzmonth().equals("次月")) {
							ownmonthstr = util.getNextMonth(ownmonthstr);
						}
					}
					if (ownmonthstr.length() >= 6) {
						month = ownmonthstr.substring(4, 6);
					}
					/********************* 每个账单的第一行 *********************************/
					String firstline ="";
					firstline="填制凭证,V800";
					String tline = dateToStr(ml.getMakedate()) + ",记,"
							+ ml.getProofnumber() + "" + ","
							+ ml.getProoffdnum() + ",收"
							+ ml.getCoba_shortname() + month + "月"
							+ ml.getCfsa_cpac_name() + ","
							+ ml.getSubjectnumber() + "," + ml.getCfsa_paidin()
							+ ",0.00,,,," + UserInfo.getUsername()
							+ ",,,,,,,,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
					/******************************************************/
					write.append(firstline);
					write.append(newline);// 换行
					write.append(tline);
					write.append(newline);// 换行
					for (GatherInfoModel m : list) {
						if (m.getCfsa_cfmb_number().equals(
								ml.getCfsa_cfmb_number())) {
							String line = "";
							line = dateToStr(ml.getMakedate())
									+ ",记,"
									+ m.getProofnumber()
									+ ""
									+ ","
									+ ml.getProoffdnum()
									+ ",收"
									+ m.getCoba_shortname()
									+ month
									+ "月"
									+ m.getCfsa_cpac_name()
									+ ","
									+ m.getSubjectnumber()
									+ ","
									+ m.getBorrowmoney()
									+ ","
									+ m.getLendmoney()
									+ ",,,,"
									+ UserInfo.getUsername()
									+ ",,,,,,,,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
							write.append(line);
							write.append(newline);// 换行
						}
					}
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
				for (GatherInfoModel m : list) {
					bll.updateYyOutState(m.getCfsa_id().toString(),m.getCid(),m.getOwnmonth());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 导出txt
		}else {
			Messagebox.show("没有数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void setList(List<GatherInfoModel> list) {
		this.list = list;
	}

	private Date strToDate(String str) {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (str != null && !str.equals("")) {
			try {
				date = format.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
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

	private List<GatherInfoModel> getBillNumberList() {
		List<GatherInfoModel> newlist = new ArrayList<GatherInfoModel>();
		List<String> slist = new ArrayList<String>();
		for (GatherInfoModel m : list) {
			String number = m.getCfsa_cfmb_number();
			if (!slist.contains(number)) {
				slist.add(number);
				GatherInfoModel ml = new GatherInfoModel();
				ml.setCfsa_cfmb_number(number);
				ml.setCfsa_cpac_name(m.getCfsa_cpac_name());
				ml.setOwnmonth(m.getOwnmonth());
				ml.setCoba_shortname(m.getCoba_shortname());
				ml.setMakedate(m.getMakedate());
				ml.setCfsa_paidin(m.getCfsa_paidin());
				ml.setSubjectnumber(m.getSubjectnumber());
				ml.setCpac_name(UserInfo.getUsername());
				ml.setProofnumber(m.getProofnumber());
				ml.setCoco_gzmonth(m.getCoco_gzmonth());
				ml.setProoffdnum(m.getProoffdnum());
				newlist.add(ml);
			} else {
				for (GatherInfoModel nm : newlist) {
					if (nm.getCfsa_cfmb_number().equals(number)) {
						nm.setCfsa_cpac_name(nm.getCfsa_cpac_name()
								+ m.getCfsa_cpac_name());
						nm.setCfsa_paidin(nm.getCfsa_paidin().add(
								m.getCfsa_paidin()));
					}
				}
			}
		}
		return newlist;
	}

	public List<GatherInfoModel> getList() {
		return list;
	}

}
