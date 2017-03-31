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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.lowagie.text.Row;

import dal.EmFinanceManage.GatherInfoDal;

import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.GatherInfoModel;
import Util.MonthListUtil;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmFinanceManage.GatherInfoBll;

public class Finance_GatherInfoListController {
	public static List<CoBaseModel> colist = new ArrayList<CoBaseModel>();
	private List<CoBaseModel> newcolist = new ArrayList<CoBaseModel>();
	private List<Map> listMap = new ArrayList<Map>();
	private GatherInfoBll bll = new GatherInfoBll();
	private BigDecimal total = BigDecimal.ZERO;
	private int firstOrder = 0;
	private int maxOrder = 0;
	private Date makeDate;
	private Integer firstNumber;
	private List<GatherInfoModel> list = new ArrayList<GatherInfoModel>();
	private String cidstr = "";
	private String str;

	public Finance_GatherInfoListController() {
		if (Executions.getCurrent().getArg().get("list") != null) {
			colist = (List<CoBaseModel>) Executions.getCurrent().getArg()
					.get("list");
			
			 
		}
		SumTotal();
	}

	// 计算总金额
	private void SumTotal() {
		total = BigDecimal.ZERO;
		for (CoBaseModel m : colist) {
			if (m != null) {
				if (m.getAmount() != null
						&& m.getAmount().getCfmb_TotalPaidIn() != null) {
					total = total.add(m.getAmount().getCfmb_TotalPaidIn());
				}
				m.setGroup_count(1);
				m.setGroup_firstrow(1);
				m.setGroup_order(0);
			}
		}
	}

	// 取消
	@Command
	@NotifyChange("colist")
	public void cancel(@BindingParam("model") CoBaseModel model) {
		colist.remove(model);
	}

	// 合并
/*	@Command
	@NotifyChange("colist")
	public void merge(@BindingParam("gd") Grid gd) {
		maxOrder = firstOrder;
		int firstRowNum = 0;
		int group_count = 0;
		if (!colist.isEmpty()) {
			colist.clear();
		}
		List<CoBaseModel> mList = new ArrayList<CoBaseModel>();
		int twoOrder = -1;
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 0) != null) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 0) {
					CoBaseModel m = (CoBaseModel) gd.getModel().getElementAt(i);
					if (gd.getCell(i, 0).getChildren().get(0).getClass()
							.toString().contains("Checkbox")) {
						Checkbox ck = (Checkbox) cell.getChildren().get(0);
						if (ck.isChecked()) {
							if (firstRowNum == 0 && m.getGroup_order() == 0) {
								firstOrder = firstOrder + 1;
								maxOrder = firstOrder;
								firstRowNum = i + 1;
								m.setGroup_firstrow(1);
							} else {
								if (firstRowNum == 0) {
									firstRowNum = i + 1;
									firstOrder = m.getGroup_order();
								}
								if (firstOrder == 0) {
									firstOrder = m.getGroup_order();
								}
								m.setGroup_firstrow(0);
							}
							if (m.getGroup_order() > 0) {
								twoOrder = m.getGroup_order();
							}
							m.setGroup_order(firstOrder);
							group_count++;
							mList.add(m);
						}
					} else {
						if (m.getGroup_order() == firstOrder && firstRowNum > 0) {
							group_count++;
							mList.add(m);
						} else if (twoOrder > 0
								&& m.getGroup_order() == twoOrder
								&& firstRowNum > 0) {
							m.setGroup_order(firstOrder);
							group_count++;
							mList.add(m);
						}
					}
					colist.add(m);
				}
			}
		}

		// 排序
		for (int i = 0; i < colist.size() - 1; i++) {
			for (int j = 0; j < colist.size() - 1 - i; j++) {
				if (colist.get(j + 1).getGroup_order() > colist.get(j)
						.getGroup_order()) {
					CoBaseModel cm = colist.get(j);
					colist.set(j, colist.get(j + 1));
					colist.set(j + 1, cm);
				}
			}
		}

		if (mList.size() > 0) {
			if (firstRowNum > 0) {
				firstRowNum--;
				Cell cell = (Cell) gd.getCell(firstRowNum, 0);
				Checkbox ck = (Checkbox) cell.getChildren().get(0);
				CoBaseModel m = ck.getValue();
				m.setGroup_count(group_count);
				m.setGroup_firstrow(1);
			}
			for (CoBaseModel m : mList) {

			}
		}

		// 把数据合并成一条数据
		GatherInfoModel gm = new GatherInfoModel();
		for (CoBaseModel mp : mList) {

		}

		firstOrder = maxOrder;
	}*/

	// 导出
	@Command
	public void downfile() {
		if (makeDate == null) {
			Messagebox.show("请选择制表时间", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (firstNumber == null || firstNumber.equals("")) {
			Messagebox.show("请填写起始凭证号", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (colist.size() > 0) {
			for (int i = 0; i < colist.size(); i++) {
				if (colist.get(i).getCoba_ufid() == null
						|| colist.get(i).getCoba_ufid().equals("")) {
					Messagebox.show(colist.get(i).getCoba_shortname()
							+ "的用友编号为空", "提示", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				if (cidstr == null || cidstr.equals("")) {
					cidstr = cidstr + colist.get(i).getCid();
				} else {
					cidstr = cidstr + "," + colist.get(i).getCid();
				}
			}
		}
		 
		list.clear();
		if (cidstr != null && !cidstr.equals("")) {
			str = " and c.cid in(" + cidstr + ")";
		}
		for (CoBaseModel com : colist) {
			String sql=" and ownmonth="+com.getOwnmonth()+" " +
					"and a.cid="+com.getCid()+" and cfss_cfso_id='"+com.getCfss_cfso_id()+"'";
			GatherInfoModel gm =bll.getOwnmonthBillListss2(sql);
			gm.setCid(com.getCid());
			gm.setCfss_cfso_id(com.getCfss_cfso_id());
			list.add(gm);
		}
		//list = bll.getOwnmonthBillListss(str);
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
				//String newline = "";
				
				/************************* 对txt文件写入内容 ************************************/
				//List<GatherInfoModel> numlist = getBillNumberList();
				// 第一行
				String firstline = "";
				firstline = "填制凭证,V800";
				write.append(firstline);
				write.append(newline);// 换行
				GatherInfoDal dal = new GatherInfoDal();
				
				for(GatherInfoModel ml:list)
				{
					String sql=" and ownmonth="+ml.getOwnmonth()+" " +
							"and a.cid="+ml.getCid()+" and cfss_cfso_id='"+ml.getCfss_cfso_id()+"'";
					List<GatherInfoModel> nlist=bll.getOwnmonthBillListssnew(sql);
					String capcname=mergeCapcName(nlist);
					String coco_gzmonth=bll.getCocompact(ml.getCid());
					String month = "";
					String ownmonthstr = ml.getOwnmonth().toString();
					if (coco_gzmonth != null&&!coco_gzmonth.equals("")) {
						MonthListUtil util = new MonthListUtil();
						if (coco_gzmonth.equals("上月")) {
							ownmonthstr = util.getLastMonth(ownmonthstr);
						} else if (coco_gzmonth.equals("次月")) {
							ownmonthstr = util.getNextMonth(ownmonthstr);
						}
					}
					if (ownmonthstr.length() >= 6) {
						month = ownmonthstr.substring(4, 6);
					}
					/********************* 每个账单的第一行 *********************************/
					capcname=capcname.replace("税金", "");
					String tline = dateToStr(makeDate) + ",记," + firstNumber
							+ "" + ",,收"
							+ ml.getCoba_shortname() + month + "月"
							+ capcname + ",10020101,"   
							+ ml.getSumpin() + ",0.00,,,,"
							+ UserInfo.getUsername()
							+ ",,,,,,,,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
					/******************************************************/
					write.append(tline);
					write.append(newline);// 换行
					String sjt="收";
					if (ml.getCoba_clientclass() != null
							 && ml.getCoba_clientclass().equals("AF")) {
						//AF开始 
						List<GatherInfoModel> fwlist = new ArrayList<GatherInfoModel>(); //服务费
						List<GatherInfoModel> cslist = new ArrayList<GatherInfoModel>();//代扣代缴
						List<GatherInfoModel> fllist = new ArrayList<GatherInfoModel>();//福利费
						String line = "";
						for (GatherInfoModel m : nlist) {
							  sjt="收";
							if (m.getCid().equals(
									ml.getCid())) {
								String cpac_name = m.getCfsa_cpac_name();
								if (cpac_name.equals("税金"))
								{
									cpac_name="增值税";
									sjt="计提";
								}
								if (cpac_name.contains("服务")
										|| cpac_name.contains("代理")||cpac_name.contains("增值税")
										|| cpac_name.equals("档案保管费")
										|| cpac_name.equals("户口")	
										) {
									if (m.getDepnumber() != null
											&& m.getDepnumber().equals("001")
											&& cpac_name.equals("服务费")) {
										m.setCfsa_cpac_name("服务费");
									}
									if (cpac_name.equals("档案保管费")
											|| cpac_name.equals("户口")
											 ) {
										m.setCfsa_cpac_name("福利费");
										switch (cpac_name) {
										case "档案保管费"://商保01、书报02、活动03、体检04、档案08
											m.setCw_id("08");
										break;
										case "户口"://商保01、书报02、活动03、体检04、档案08
											m.setCw_id("08");
										break;
										default:
											m.setCw_id("");
											break;
											
											
											
										}
									}
							
									
									fwlist.add(m);
								} else {
								
									if (cpac_name.equals("体检费")
											|| cpac_name.equals("商保费")
											|| cpac_name.equals("活动费")
											|| cpac_name.contains("书报")
										
											 ) {
										m.setCfsa_cpac_name("福利费");
										switch (cpac_name) {
										case "体检费"://商保01、书报02、活动03、体检04、档案08
												m.setCw_id("04");
											break;
										case "商保费"://商保01、书报02、活动03、体检04、档案08
											m.setCw_id("01");
										break;
										case "活动费"://商保01、书报02、活动03、体检04、档案08
											m.setCw_id("03");
										break;
										case "书报费"://商保01、书报02、活动03、体检04、档案08
											m.setCw_id("02");
										break;
										case "档案保管费"://商保01、书报02、活动03、体检04、档案08
											m.setCw_id("08");
										break;
										case "户口"://商保01、书报02、活动03、体检04、档案08
											m.setCw_id("08");
										break;
										default:
											m.setCw_id("");
											break;
											
										}
										fllist.add(m);
									}
									else
									{
										cslist.add(m);
									}
									 
									
								}
							}
						}
						BigDecimal cstotal = BigDecimal.ZERO;
						BigDecimal wftotal = BigDecimal.ZERO;
						String ufid="";
						for (GatherInfoModel fwm : fwlist) {
							 sjt="收";
							if ( fwm.getLendmoney().compareTo(BigDecimal.ZERO)==1)
							{
							String depnumber = "";
							if (fwm.getDepnumber() != null) {
								depnumber = fwm.getDepnumber();
							}
							wftotal = wftotal.add(fwm.getLendmoney());
							//String ufid="";
							if(fwm.getCoba_ufid()!=null)
							{
								ufid=fwm.getCoba_ufid().toString();
							}
							
							if (fwm.getCfsa_cpac_name().equals("税金"))
							{
								fwm.setCfsa_cpac_name("增值税");
								  sjt="计提";
								
							}
							
							line = dateToStr(makeDate)
									+ ",记,"
									+ firstNumber
									+ ","
									
									+ ","
									+ sjt
									+ fwm.getCoba_shortname()
									+ month
									+ "月"
									+ fwm.getCfsa_cpac_name()
									+ ","
									+ fwm.getSubjectnumber()
									+ ",0.00,"
									+ fwm.getLendmoney()
									+ ",,,,"
									+ UserInfo.getUsername()
									+ ",,,,"
									+ depnumber
									+ ",,"
									+ ufid
									+ ",,,"+fwm.getCw_id()+",,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
							write.append(line);
							write.append(newline);// 换行
						}}
						
						if (ml.getCfss_type().equals("非派遣"))
						{
							
						
							
							for (GatherInfoModel gm : fllist) {
								sjt="收";
								if (gm.getLendmoney().compareTo(BigDecimal.ZERO)==1)
								{
									if (gm.getCfsa_cpac_name().equals("税金"))
									{
										gm.setCfsa_cpac_name("增值税");
										sjt="计提";
										
									}
								line = dateToStr(makeDate)
										+ ",记,"
										+ firstNumber
										+ ",,"
										+ sjt
										+ gm.getCoba_shortname()
										+ month
										+ "月"
										+ gm.getCfsa_cpac_name()
										+ ","
										+ gm.getSubjectnumber()
										+ ",0.00,"
										+ gm.getLendmoney()
										+ ",,,,"
										+ UserInfo.getUsername()
										+ ",,,,,,"
										+ ufid
										+ ",,,"+gm.getCw_id()+",,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
								write.append(line);
								write.append(newline);// 换行
							}
						}
							
							
							
							if (cslist.size() > 0) {
								String cpacname = "";
								for (GatherInfoModel gm : cslist) {
									if (!cpacname.contains(gm.getCfsa_cpac_name())&& 
											!gm.getCfsa_cpac_name().equals("税金") &&
											gm.getLendmoney().compareTo(BigDecimal.ZERO)==1) {
										if (!gm.getCfsa_cpac_name().equals("福利费"))
										{
											cpacname = cpacname
													+ gm.getCfsa_cpac_name();
										}
										
									}
								}
							
								if(ml.getCoba_ufid()!=null)
								{
									ufid=ml.getCoba_ufid().toString();
								}
								//cstotal = ml.getCfsa_paidin().subtract(wftotal);
								cstotal=ml.getCfsa_paidin();
								if(cstotal.compareTo(BigDecimal.ZERO)==1)
										{
								line = dateToStr(makeDate)
										+ ",记,"
										+ firstNumber
										+ ",,收"
										+ ml.getCoba_shortname()
										+ month
										+ "月"
										+ cpacname
										+ ",60010107,0.00,"
										+ cstotal
										+ ",,,,"
										+ UserInfo.getUsername()
										+ ",,,,,,"
										+ ufid
										+ ",,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
								write.append(line);
								write.append(newline);// 换行
								
								firstNumber = firstNumber + 1;
								line = dateToStr(makeDate)
										+ ",记,"
										+ firstNumber
										+ ",,收"
										+ ml.getCoba_shortname()
										+ month
										+ "月"
										+ cpacname
										+ ",64010115,"+cstotal+",0.00,,,,"
										+ UserInfo.getUsername()
										+ ",,,,,,"
										+ ufid
										+ ",,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
								write.append(line);
								write.append(newline);// 换行
										}
						
							}
							for (GatherInfoModel gm : cslist) {
								sjt="收";
								if (gm.getLendmoney().compareTo(BigDecimal.ZERO)==1)
								{
									if (gm.getCfsa_cpac_name().equals("税金"))
									{
										gm.setCfsa_cpac_name("增值税");
										sjt="计提";
										
									}
								line = dateToStr(makeDate)
										+ ",记,"
										+ firstNumber
										+ ",,"
										+ sjt
										+ gm.getCoba_shortname()
										+ month
										+ "月"
										+ gm.getCfsa_cpac_name()
										+ ","
										+ gm.getSubjectnumber()
										+ ",0.00,"
										+ gm.getLendmoney()
										+ ",,,,"
										+ UserInfo.getUsername()
										+ ",,,,,,"
										+ ufid
										+ ",,,"+gm.getCw_id()+",,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
								write.append(line);
								write.append(newline);// 换行
							}
						}
							
						}
						else
						{
							if (cslist.size() > 0) {
								String cpacname = "";
								for (GatherInfoModel gm : cslist) {
									if (!cpacname.contains(gm.getCfsa_cpac_name())&& 
											!gm.getCfsa_cpac_name().equals("税金") &&
											gm.getLendmoney().compareTo(BigDecimal.ZERO)==1) {
										if (!gm.getCfsa_cpac_name().equals("福利费"))
										{
											cpacname = cpacname
													+ gm.getCfsa_cpac_name();
										}
										
									}
								}
								//String ufid="";
								if(ml.getCoba_ufid()!=null)
								{
									ufid=ml.getCoba_ufid().toString();
								}
								//cstotal = ml.getCfsa_paidin().subtract(wftotal);
								cstotal=ml.getCfsa_paidin();
								if(cstotal.compareTo(BigDecimal.ZERO)==1)
										{
								line = dateToStr(makeDate)
										+ ",记,"
										+ firstNumber
										+ ",,收"
										+ ml.getCoba_shortname()
										+ month
										+ "月"
										+ cpacname
										+ ",60010107,0.00,"
										+ cstotal
										+ ",,,,"
										+ UserInfo.getUsername()
										+ ",,,,,,"
										+ ufid
										+ ",,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
								write.append(line);
								write.append(newline);// 换行
								
								firstNumber = firstNumber + 1;
								line = dateToStr(makeDate)
										+ ",记,"
										+ firstNumber
										+ ",,收"
										+ ml.getCoba_shortname()
										+ month
										+ "月"
										+ cpacname
										+ ",64010115,"+cstotal+",0.00,,,,"
										+ UserInfo.getUsername()
										+ ",,,,,,"
										+ ufid
										+ ",,,,,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
								write.append(line);
								write.append(newline);// 换行
										}
								
								
								
								for (GatherInfoModel gm : fllist) {
									sjt="收";
									if (gm.getLendmoney().compareTo(BigDecimal.ZERO)==1)
									{
										if (gm.getCfsa_cpac_name().equals("税金"))
										{
											gm.setCfsa_cpac_name("增值税");
											sjt="计提";
											
										}
									line = dateToStr(makeDate)
											+ ",记,"
											+ firstNumber
											+ ",,"
											+ sjt
											+ gm.getCoba_shortname()
											+ month
											+ "月"
											+ gm.getCfsa_cpac_name()
											+ ","
											+ gm.getSubjectnumber()
											+ ",0.00,"
											+ gm.getLendmoney()
											+ ",,,,"
											+ UserInfo.getUsername()
											+ ",,,,,,"
											+ ufid
											+ ",,,"+gm.getCw_id()+",,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
									write.append(line);
									write.append(newline);// 换行
								}
							}
								
								
								for (GatherInfoModel gm : cslist) {
									sjt="收";
									if (gm.getLendmoney().compareTo(BigDecimal.ZERO)==1)
									{
										if (gm.getCfsa_cpac_name().equals("税金"))
										{
											gm.setCfsa_cpac_name("增值税");
											sjt="计提";
											
										}
									line = dateToStr(makeDate)
											+ ",记,"
											+ firstNumber
											+ ",,"
											+ sjt
											+ gm.getCoba_shortname()
											+ month
											+ "月"
											+ gm.getCfsa_cpac_name()
											+ ","
											+ gm.getSubjectnumber()
											+ ",0.00,"
											+ gm.getLendmoney()
											+ ",,,,"
											+ UserInfo.getUsername()
											+ ",,,,,,"
											+ ufid
											+ ",,,"+gm.getCw_id()+",,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
									write.append(line);
									write.append(newline);// 换行
								}
							}
							}
						}
							
				
						firstNumber++;
					
				//AF结束	
					} 
				//FS	
					else {
						for (GatherInfoModel m : nlist) {
							sjt="收";
							if (m.getLendmoney().compareTo(BigDecimal.ZERO)==1)
							{
							if (m.getCid().equals(
									ml.getCid())) {
								String line = "";
								String ufname="";
								String cpac_name = m.getCfsa_cpac_name();
								if (cpac_name.equals("税金"))
								{
									cpac_name="增值税";
									sjt="计提";
								}
								if (cpac_name != null && !cpac_name.equals("")) {
									if (m.getDepnumber() != null
											&& m.getDepnumber().equals("001")
											&& cpac_name.equals("服务费")) {
										cpac_name = "服务费";
									} else if (cpac_name.equals("体检费")
											|| cpac_name.equals("商保费")
											|| cpac_name.equals("活动费")
											|| cpac_name.contains("书报")
											|| cpac_name.equals("档案保管费")
											|| cpac_name.equals("户口")
										 ) {
										
									
										
										switch (cpac_name) {
										case "体检费"://商保01、书报02、活动03、体检04、档案08
											ufname="04";
											break;
										case "商保费"://商保01、书报02、活动03、体检04、档案08
											ufname="01";
										break;
										case "活动费"://商保01、书报02、活动03、体检04、档案08
											ufname="03";
										break;
										case "书报费"://商保01、书报02、活动03、体检04、档案08
											ufname="02";
										break;
										case "档案保管费"://商保01、书报02、活动03、体检04、档案08
											ufname="08";
										break;
										case "户口"://商保01、书报02、活动03、体检04、档案08
											ufname="08";
										break;
										default:
											ufname="";
											break;
										}
										
										cpac_name = "福利费";
									 
										
									}
								}
								String depnumber = "";
								if (m.getDepnumber() != null) {
									depnumber = m.getDepnumber();
								}
								String ufid="";
								if(m.getCoba_ufid()!=null)
								{
									ufid=m.getCoba_ufid().toString();
								}
								
//								if (m.getCfss_type().equals("派遣")&&cpac_name.equals("福利费"))
//								{
//									 
//									 
//									m.setSubjectnumber("224119");
//								}
								
								line = dateToStr(makeDate)
										+ ",记,"
										+ firstNumber
										+ ""
										+ ","
									
									 	+ ","
										+ sjt
										+ m.getCoba_shortname()
										+ month
										+ "月"
										+ cpac_name
										+ ","
										+ m.getSubjectnumber()
										+ ","
										+ m.getBorrowmoney()
										+ ","
										+ m.getLendmoney()
										+ ",,,,"
										+ UserInfo.getUsername()
										+ ",,,,"
										+ depnumber
										+ ",,"
										+ ufid
										+ ",,," 
										+ufname+
										",,,,,,,0,,0,,,,,,,,,0,0,0,0,0,,0,0,0,0";
								write.append(line);
								write.append(newline);// 换行
							}
						}
						}
						firstNumber++;
					}
					
				}
				Buff.write(write.toString().getBytes("UTF-8"));
				//Buff.write(write.toString().getBytes("gb3212"));
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
					bll.updateYyOutState(m.getCfss_cfso_id(),m.getCid(),m.getOwnmonth());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 导出txt
		} else {
			Messagebox.show("没有数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//
	public String mergeCapcName(List<GatherInfoModel> nlist)
	{
		String capc_name="";
		for(int i=0;i<nlist.size();i++)
		{
			GatherInfoModel m=nlist.get(i);
			String cpac_name = m.getCfsa_cpac_name();
		if (m.getCfsa_paidin().compareTo(BigDecimal.ZERO)==1)
		{
			if (cpac_name != null && !cpac_name.equals("")) {
				if (m.getDepnumber() != null
						&& m.getDepnumber().equals("001")
						&& cpac_name.equals("服务费")) {
					cpac_name = "服务费";
				} else if (cpac_name.equals("体检费")
						|| cpac_name.equals("商保费")
						|| cpac_name.equals("活动费")
						|| cpac_name.contains("书报")
						|| cpac_name.equals("档案保管费")) {
					cpac_name = "福利费";
	
					
				}
			
			}
			//服务费社保费公积金福利费工资个税
			
			if(!capc_name.contains(cpac_name)&&((cpac_name.equals("社保费")||cpac_name.equals("住房公积金")
					||cpac_name.equals("税后工资")||cpac_name.equals("个调税")||cpac_name.equals("服务费")||cpac_name.equals("福利费"))))
			{
				if(i==nlist.size()-1&&cpac_name!=null&&!cpac_name.equals(""))
				{
					cpac_name="及"+cpac_name;
				}
				capc_name=capc_name+cpac_name;
			
			}
			
			
		}
		}
		return capc_name;
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

	// 把账单号相同的数据合并在一起
	/*private List<GatherInfoModel> getBillNumberList() {
		List<GatherInfoModel> newlist = new ArrayList<GatherInfoModel>();
		List<String> slist = new ArrayList<String>();
		List<Integer> clist = new ArrayList<Integer>();
		for (GatherInfoModel m : list) {
			//String number = m.getCfsa_cfmb_number();// 账单号
			Integer cid = m.getCid();
			if (cid != null && cid > 0) {
				// if (!slist.contains(number)) {// 不存在账单号
				if (!clist.contains(cid)) {// 不存在CID
					clist.add(cid);
					String cpac_name = m.getCfsa_cpac_name();
					if (cpac_name != null && !cpac_name.equals("")) {
						if (m.getDepnumber() != null
								&& m.getDepnumber().equals("001")
								&& cpac_name.equals("服务费")) {
							cpac_name = "代理费";
						} else if (cpac_name.equals("体检费")
								|| cpac_name.equals("商保费")
								|| cpac_name.equals("活动费")
								|| cpac_name.contains("书报")
								|| cpac_name.equals("档案保管费")) {
							cpac_name = "福利费";
						}
					}
					GatherInfoModel ml = new GatherInfoModel();
					//ml.setCfsa_cfmb_number(number);
					ml.setCfsa_cpac_name(cpac_name);
					ml.setOwnmonth(m.getOwnmonth());
					ml.setCoba_shortname(m.getCoba_shortname());
					ml.setMakedate(m.getMakedate());
					ml.setCfsa_paidin(m.getCfsa_paidin());
					ml.setSubjectnumber(m.getSubjectnumber());
					ml.setCpac_name(UserInfo.getUsername());
					ml.setProofnumber(m.getProofnumber());
					ml.setCoco_gzmonth(m.getCoco_gzmonth());
					ml.setProoffdnum(m.getProoffdnum());
					ml.setCoba_clientclass(m.getCoba_clientclass());
					ml.setCoba_ufid(m.getCoba_ufid());
					ml.setCid(m.getCid());
					newlist.add(ml);
				} else {
					for (GatherInfoModel nm : newlist) {
						if (nm.getCid().equals(cid)) {
							String cpac_name = m.getCfsa_cpac_name();
							if (cpac_name != null && !cpac_name.equals("")) {
								if (m.getDepnumber() != null
										&& m.getDepnumber().equals("001")
										&& cpac_name.equals("服务费")) {
									cpac_name = "代理费";
								} else if (cpac_name.equals("体检费")
										|| cpac_name.equals("商保费")
										|| cpac_name.equals("活动费")
										|| cpac_name.contains("书报")
										|| cpac_name.equals("档案保管费")) {
									cpac_name = "福利费";
								}
							}
							if (!nm.getCfsa_cpac_name().contains(cpac_name)) {
								nm.setCfsa_cpac_name(nm.getCfsa_cpac_name()
										+ cpac_name);
							}
							nm.setCfsa_paidin(nm.getCfsa_paidin().add(
									m.getCfsa_paidin()));
						}
					}
				}
			}
		}
		return newlist;
	}*/

	// 弹出修改页面
	@Command
	@NotifyChange({"list","colist"})
	public void editUfid(@BindingParam("model") CoBaseModel model) {
		CoBaseModel modelold =new CoBaseModel();
		
		Map map = new HashMap<>();
		//map.put("cid", model.getCid());
		
		map.put("modelold", model);
		
		map.put("type", "1");
		Window window = (Window) Executions.createComponents(
				"Finance_GatherInfoEdit.zul", null, map);
		window.doModal();
		//newcolist.clear();
		
		 
//		for (CoBaseModel m : colist) {
//			if (m.getCid().equals(modelold.getCid()))
//			{
//				newcolist.add(modelold);
//				
//			}
//			newcolist.add(m);
//			
//		}
//		colist=newcolist;
		
		
	}

	public List<CoBaseModel> getColist() {
		return colist;
	}

	public void setColist(List<CoBaseModel> colist) {
		this.colist = colist;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public Integer getFirstNumber() {
		return firstNumber;
	}

	public void setFirstNumber(Integer firstNumber) {
		this.firstNumber = firstNumber;
	}

}
