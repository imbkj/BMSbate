package Controller.SocialInsurance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.SocialInsurance.SocialEstimatesBll;

import Model.SocialInsuranceAlgorithmViewModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.RegexUtil;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class SocialEstimatesController {
	private Media media;
	private String absolutePath;
	private String uploadFlieName;
	private List<String> cityList;
	private List<SocialInsuranceAlgorithmViewModel> insuranceList;
	private List<String> cppList;
	private String insurance;
	private SocialEstimatesBll bll;
	private String coabName;
	private String cpp;

	public SocialEstimatesController() {
		bll = new SocialEstimatesBll();
		absolutePath = FileOperate.getAbsolutePath();
		cityList = bll.getInsuranceCityList();
	}

	// 下载测算结果
	@Command("downloadData")
	public void downloadData(
			@BindingParam("m") SocialInsuranceAlgorithmViewModel m) {
		try {
			if (checkPage()) {
				int soin_id = m.getSoin_id();
				String ownmonth = DateStringChange.getOwnmonth();
				String flieName = ownmonth
						+ soin_id
						+ "_"
						+ pinyin4jUtil
								.getPinYinHeadChar(UserInfo.getUsername())
						+ ".xls";
				List<String[]> dataList = getExcel(flieName);
				if (dataList.size() > 0) {
					if (bll.createEstimatesReport(dataList, soin_id, ownmonth,
							flieName, cpp, insurance)) {
						FileOperate.download("SocialInsurance/Download/"
								+ flieName);
						FileOperate.deleteFile("SocialInsurance/Download/"
								+ flieName);
					}
				}else{
					Messagebox.show("导入的文件，未找到有效数据。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 检查页面录入信息
	private boolean checkPage() {
		boolean bool = false;
		try {
			if (this.media == null) {
				Messagebox.show("请上传需要测算的数据。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else if (this.insurance == null || "".equals(this.insurance)) {
				Messagebox.show("请选择需要测算的算法。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else if (this.cpp == null || "".equals(this.cpp)) {
				Messagebox.show("请选择需要测算的公积金比例。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else {
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 按城市查询算法
	@Command("selInsurance")
	@NotifyChange({ "cppList", "coabName", "cpp", "insuranceList", "insurance" })
	public void selInsurance(@BindingParam("city") String city) {
		insuranceList = bll.getInsuranceList(city);
		insurance = "";
		cpp = "";
		coabName = "";
		cppList = null;
	}

	// 按算法查询公积金比例，以及机构
	@Command("selByInsurance")
	@NotifyChange({ "cppList", "coabName", "cpp" })
	public void selByInsurance(@BindingParam("cbInsurance") Combobox cbInsurance) {
		try {
			if (cbInsurance.getSelectedItem().getValue() != null) {
				SocialInsuranceAlgorithmViewModel m = cbInsurance
						.getSelectedItem().getValue();
				cppList = CppHandle(m.getGjj_proportion());
				coabName = m.getCoab_name();
				cpp = "";
			} else {
				cpp = "";
				coabName = "";
				cppList = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	// 下载模板
	@Command("downloadTemplet")
	public void downloadTemplet() {
		String path = "SocialInsurance/Templet/ImportSocialEstimatesTemplet.xls";
		try {
			File f = new File(absolutePath + path);
			if (f.isFile()) {
				FileOperate.download(path);
			} else {
				Messagebox.show("未找到模板文件。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 上传Excel并获取内容
	private List<String[]> getExcel(String filename) {
		List<String[]> list = new ArrayList<String[]>();
		String[] str;
		File file = null;
		Workbook wb = null;
		try {
			if (FileOperate.upload(this.media, "SocialInsurance/Upload/"
					+ filename)) {
				file = new File(absolutePath + "SocialInsurance/Upload/"
						+ filename);
				// 读取Excel文件
				wb = Workbook.getWorkbook(file);
				// 读取工作表
				Sheet st = wb.getSheet(0);
				int rows = st.getRows();
				if (rows > 1) {
					// 遍历数据行
					for (int r = 1; r < st.getRows(); r++) {
						if (st.getCell(0, r).getContents() != null
								&& !"".equals(st.getCell(0, r).getContents())) {
							if (st.getCell(1, r).getContents() != null
									&& !"".equals(st.getCell(1, r)
											.getContents())) {
								str = new String[7];
								str[0] = st.getCell(0, r).getContents();
								str[1] = st.getCell(1, r).getContents();
								for(int i=2;i<7;i++){
									if (st.getCell(i, r).getContents() != null
											&& !"".equals(st.getCell(i, r)
													.getContents())) {
										str[i] = st.getCell(i, r).getContents();
									}else{
										str[i] ="0";
									}
								}
								list.add(str);
							}
						} else {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wb.close();
		}
		return list;
	}

	/**
	 * 比例处理
	 * 
	 */
	private List<String> CppHandle(String cpp) {
		List<String> list = new ListModelList<>();
		if (cpp != null && !cpp.isEmpty()) {
			if (RegexUtil.isExists(",", cpp)) {
				String[] strings = cpp.split(",");
				for (String str : strings) {
					list.add(str);
				}
			} else if (RegexUtil.isExists("-", cpp)) {
				String[] strs = cpp.split("-");
				Integer step;
				String[] strs1 = strs[0].split("\\.");
				String[] strs2 = strs[1].split("\\.");
				if (strs1[1].length() > 1 || strs2[1].length() > 1) {
					step = 1;
				} else {
					step = 10;
				}
				for (Double i = Double.parseDouble(strs[0]) * 100; i <= Double
						.parseDouble(strs[1]) * 100; i = i + step) {
					list.add((i / 100) + "");
				}
			} else {
				list.add(cpp);
			}
		} else {
			list.add("");
		}
		return list;
	}

	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public List<String> getCityList() {
		return cityList;
	}

	public List<SocialInsuranceAlgorithmViewModel> getInsuranceList() {
		return insuranceList;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public List<String> getCppList() {
		return cppList;
	}

	public String getCoabName() {
		return coabName;
	}

	public String getCpp() {
		return cpp;
	}

	public void setCpp(String cpp) {
		this.cpp = cpp;
	}

}
