package Controller.EmCommissionOut;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutPayUpdateCRTModel;
import Model.EmCommissionOutPayUpdateFeeDetailModel;
import Model.EmCommissionOutPayUpdateModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;

public class EmCommissionOutPayUpdate_ImportExcelController {

	private List<CoAgencyBaseCityRelViewModel> cityList;
	private List<CoAgencyBaseCityRelViewModel> coabList;
	private List<CoAgencyBaseCityRelViewModel> scoabList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel cityModel = null;
	private CoAgencyBaseCityRelViewModel coabModel = null;
	private Date ownmonth = null;
	private Integer titlerow = 1;
	private Integer oldtitlerow = 1;
	private Integer datarow = 2;
	private String excelname = "";
	private Media media = null;
	private List<EmCommissionOutPayUpdateCRTModel> temList;
	private EmCommissionOutPayUpdateCRTModel temModel = null;
	private List<EmCommissionOutPayUpdateCRTModel> fieldList = new ListModelList<>();
	private List<EmCommissionOutPayUpdateCRTModel> fieldList1 = new ListModelList<>();
	List<String> titleList;
	List<EmCommissionOutPayUpdateModel> puList = new ListModelList<>();

	private Integer errCount = 0;
	private String errString = "无数据";

	String localexcelname = "";
	String realfilename = "";

	private List<EmCommissionOutPayUpdateModel> payupList = new ListModelList<>();

	public EmCommissionOutPayUpdate_ImportExcelController() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 页面初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			setCityList(bll.getCoCityList());
			cityList.add(0, null);
			setCoabList(bll.getCoagList());
			setTemList(bll.getEmOutPayUpdateT(" and ecut_state=1"));
			temList.add(0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择城市，筛选出委托机构列表
	 * 
	 */
	@Command("citySelect")
	@NotifyChange({ "scoabList", "coabModel" })
	public void citySelect() {
		try {
			scoabList.clear();
			if (cityModel != null) {
				for (CoAgencyBaseCityRelViewModel m : coabList) {
					if (m != null) {
						if (cityModel.getCabc_ppc_id() != m.getCabc_ppc_id()) {
							continue;
						}
					}

					scoabList.add(m);
				}
				if (scoabList.size() > 0) {
					coabModel = scoabList.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传excel
	 * 
	 * @param media
	 */
	@SuppressWarnings("unchecked")
	@Command("browse")
	@NotifyChange({ "excelname", "fieldList", "errString" })
	public void browse(@BindingParam("media") Media media) {
		try {
			if (media.getFormat().equals("xls")) {
				setMedia(this.media);
				setExcelname(media.getName());

				// 上传excel文件
				try {
					localexcelname = "Pay"
							+ DateStringChange.Datestringnow("yyyyMMddhhmmss")
							+ UserInfo.getUserid() + ".xls";
					realfilename = "EmCommissionOut/File/Upload/Pay/"
							+ localexcelname;
					FileOperate.upload(media, realfilename);

				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("错误：" + e.toString(), "ERROR",
							Messagebox.OK, Messagebox.ERROR);
				}

				// 读取上传的excel文件
				File file = new File(getAbsolutePath() + realfilename);
				Object[] obj = plyUtil.readExcel(file, titlerow);
				if (obj[0].equals("0")) {
					Messagebox.show("错误：" + obj[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				} else if (obj[0].equals("1")) {
					titleList = new ListModelList<>((List<String>) obj[2]);
					if (temModel != null) {
						temSelect();
					}
				}
			} else if (media.getFormat().equals("xlsx")) {
				Messagebox.show("请将此excel文件另存为*.xls后，再尝试进行导入", "ERROR",
						Messagebox.OK, Messagebox.ERROR);
			} else {
				Messagebox.show("此文件不是excel,請檢查!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择模板
	 * 
	 */
	@Command("temSelect")
	@NotifyChange({ "fieldList", "errString" })
	public void temSelect() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			fieldList.clear();
			fieldList1.clear();
			errCount = 0;

			if (titleList != null) {
				// 建立临时titleList
				List<EmCommissionOutPayUpdateCRTModel> titleList1 = new ListModelList<>();
				if (titleList.size() > 0) {
					for (String str : titleList) {
						EmCommissionOutPayUpdateCRTModel m = new EmCommissionOutPayUpdateCRTModel();
						m.setEcuc_excel_title1(str);
						titleList1.add(m);
					}
				}

				// 上传的excel表头与所选模板表头进行匹配
				if (temModel != null) {
					setFieldList(bll.getEmOutPayUpdateC(" and ecuc_ecut_id="
							+ temModel.getEcut_id()));
					if (fieldList.size() > 0) {
						if (titleList1.size() > 0) {
							boolean flag = false;
							for (EmCommissionOutPayUpdateCRTModel m : titleList1) {
								for (EmCommissionOutPayUpdateCRTModel m1 : fieldList) {
									if (m.getEcuc_excel_title1().equals(
											m1.getEcuc_excel_title())) {
										if (m1.getEcuc_ecpr_id() == null) {
											errCount++;
										}
										m1.setEcuc_excel_title1(m
												.getEcuc_excel_title1());
										fieldList1.add(m1);
										flag = true;
										break;
									}
								}
								if (!flag) {
									fieldList1.add(m);
									errCount++;
								}

								flag = false;
							}
						}
					} else {
						errCount = null;
					}

					// 匹配结果
					if (errCount == null) {
						errString = "无数据";
					} else if (errCount > 0) {
						errString = "有" + errCount + "条无法匹配";
					} else {
						errString = "全部匹配成功";
					}
				} else {
					errString = "无数据";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Command("titlerowchange")
	@NotifyChange({ "fieldList", "errString" })
	public void titlerowchange() {
		while (true) {
			// 读取上传的excel文件表头
			File file = new File(getAbsolutePath() + realfilename);
			Object[] obj = plyUtil.readExcel(file, titlerow);

			if (obj[0].equals("0")) {
				Messagebox.show("错误：" + obj[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				titlerow = oldtitlerow;
			} else if (obj[0].equals("1")) {
				oldtitlerow = titlerow;
				titleList = new ListModelList<>((List<String>) obj[2]);

				// 调用匹配方法
				temSelect();

				break;
			}
		}
	}

	/**
	 * 项目绝对路径
	 * 
	 * @return
	 */

	public String getAbsolutePath() {
		return Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("/").replace("\\", "/");
	}

	/**
	 * 弹出模板新增、编辑页面
	 * 
	 */
	@Command("temOperate")
	@NotifyChange({ "temList", "fieldList", "errString" })
	public void temOperate(@BindingParam("win") Window win,
			@BindingParam("type") String type, @BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();

		flagA: {
			if (type.equals("模板新增")) {
				map.put("filename", realfilename);
				map.put("titlerow", titlerow);
				map.put("excelname", excelname);
			} else if (type.equals("模板编辑")) {
				if (temModel != null) {
					map.put("daid", temModel.getEcut_id());
				} else {
					Messagebox.show("请选择模板!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					break flagA;
				}
			}

			Window window = (Window) Executions
					.createComponents(url, null, map);
			win.setVisible(false);
			window.doModal();

			EmCommissionOutListBll bll = new EmCommissionOutListBll();
			setTemList(bll.getEmOutPayUpdateT(" and ecut_state=1"));
			temList.add(0, null);
			temSelect();
			win.setVisible(true);
		}
	}

	/**
	 * 弹出导入预览页面
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Command("preview")
	public void preview(@BindingParam("win") Window win) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (cityModel == null) {
			Messagebox.show("请选择委托城市!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (coabModel == null) {
			Messagebox.show("请选择委托机构!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (ownmonth == null) {
			Messagebox.show("请选择所属月份!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (realfilename.isEmpty()) {
			Messagebox.show("请选择导入文件!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			// 调用方法，将excel中的数据写入list
			File file = new File(getAbsolutePath() + realfilename);
			Object[] obj = new plyUtil().ReadEmOutPayExcel(file, datarow,
					fieldList1);
			
			if (obj[0].equals("1")) {
				puList = (List<EmCommissionOutPayUpdateModel>) obj[3];
				map.put("puList", puList);
				map.put("fieldList", fieldList1);
				map.put("ecut_id", temModel.getEcut_id());

				Window window = (Window) Executions
						.createComponents(
								"/EmCommissionOut/EmCommissionOutPayUpdate_ImportExcel_Preview.zul",
								null, map);
				win.setVisible(false);
				window.doModal();

				win.setVisible(true);
			} else {
				
				Messagebox.show("错误：" + obj[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@SuppressWarnings("unchecked")
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
			if (coabModel == null) {
				Messagebox.show("请选择委托机构!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (ownmonth == null) {
				Messagebox.show("请选择所属月份!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				flagA: {

					// 调用方法，将excel中的数据写入list
					File file = new File(getAbsolutePath() + realfilename);
					Object[] obj = new plyUtil().ReadEmOutPayExcel(file,
							datarow, fieldList1);

					if (obj[0].equals("0")) {
						Messagebox.show("错误:" + obj[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						break flagA;
					} else if (obj[0].equals("1")) {
						puList = (List<EmCommissionOutPayUpdateModel>) obj[3];
						Integer id = 0;
						Integer id1 = 0;
						Integer row = 0;
						boolean flag = true;
						// 将委托机构和所属月份写入支付列表,并将数据插入数据库
						for (EmCommissionOutPayUpdateModel m : puList) {
							System.out.println("xxxx");
							System.out.println(coabModel.getCabc_id());
							m.setEcpu_cabc_id(coabModel.getCabc_id());
							m.setOwnmonth(Integer.parseInt(DateStringChange
									.DatetoSting(ownmonth, "yyyyMM")));

							id = bll.EmOutPayUpdateAdd(m);
							if (id > 0) {
								for (EmCommissionOutPayUpdateFeeDetailModel fm : m
										.getFeeList()) {
									fm.setEpfd_ecpu_id(id);

									id1 = bll.EmOutPayUpdateFeeDetailAdd(fm);

									if (id1.equals(0)) {
										flag = false;
									}
								}
								if (flag) {
									row++;
								}
								flag = true;
							}
						}

						if (row > 0) {
							Messagebox.show("成功导入数据：" + row + "条",
									"INFORMATION", Messagebox.OK,
									Messagebox.INFORMATION);
							win.detach();
							Executions.sendRedirect("EmCommissionOutPay_AutList.zul");
						} else {
							Messagebox.show("导入失败!", "ERROR", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				}
			}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public final List<CoAgencyBaseCityRelViewModel> getCityList() {
		return cityList;
	}

	public final List<CoAgencyBaseCityRelViewModel> getCoabList() {
		return coabList;
	}

	public final void setCityList(List<CoAgencyBaseCityRelViewModel> cityList) {
		this.cityList = cityList;
	}

	public final void setCoabList(List<CoAgencyBaseCityRelViewModel> coabList) {
		this.coabList = coabList;
	}

	public List<CoAgencyBaseCityRelViewModel> getScoabList() {
		return scoabList;
	}

	public void setScoabList(List<CoAgencyBaseCityRelViewModel> scoabList) {
		this.scoabList = scoabList;
	}

	public CoAgencyBaseCityRelViewModel getCityModel() {
		return cityModel;
	}

	public void setCityModel(CoAgencyBaseCityRelViewModel cityModel) {
		this.cityModel = cityModel;
	}

	public CoAgencyBaseCityRelViewModel getCoabModel() {
		return coabModel;
	}

	public void setCoabModel(CoAgencyBaseCityRelViewModel coabModel) {
		this.coabModel = coabModel;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public Integer getTitlerow() {
		return titlerow;
	}

	public void setTitlerow(Integer titlerow) {
		this.titlerow = titlerow;
	}

	public Integer getDatarow() {
		return datarow;
	}

	public void setDatarow(Integer datarow) {
		this.datarow = datarow;
	}

	public String getExcelname() {
		return excelname;
	}

	public void setExcelname(String excelname) {
		this.excelname = excelname;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getTemList() {
		return temList;
	}

	public void setTemList(List<EmCommissionOutPayUpdateCRTModel> temList) {
		this.temList = temList;
	}

	public EmCommissionOutPayUpdateCRTModel getTemModel() {
		return temModel;
	}

	public void setTemModel(EmCommissionOutPayUpdateCRTModel temModel) {
		this.temModel = temModel;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<EmCommissionOutPayUpdateCRTModel> fieldList) {
		this.fieldList = fieldList;
	}

	public Integer getErrCount() {
		return errCount;
	}

	public void setErrCount(Integer errCount) {
		this.errCount = errCount;
	}

	public String getErrString() {
		return errString;
	}

	public void setErrString(String errString) {
		this.errString = errString;
	}

	public List<EmCommissionOutPayUpdateModel> getPayupList() {
		return payupList;
	}

	public void setPayupList(List<EmCommissionOutPayUpdateModel> payupList) {
		this.payupList = payupList;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getFieldList1() {
		return fieldList1;
	}

	public void setFieldList1(List<EmCommissionOutPayUpdateCRTModel> fieldList1) {
		this.fieldList1 = fieldList1;
	}
}
