package Controller.CoPolicyNotice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseModel;
import Model.CoPolicyNoticeFileModel;
import Model.CoPolicyNoticeModel;
import Model.PubProCityModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoAgencies.CoAg_CompactSelectBll;
import bll.CoPolicyNotice.PoNo_OperateBll;
import bll.CoPolicyNotice.PoNo_SelectBll;

public class PoNo_InfoUpdateController {
	private CoPolicyNoticeModel model = (CoPolicyNoticeModel) Executions
			.getCurrent().getArg().get("model");
	private CoAg_CompactSelectBll bll = new CoAg_CompactSelectBll();
	private List<CoAgencyBaseModel> coaglist = bll.getCoAgencyBaseList("");
	private List<PubProCityModel> citylist = bll.getCoPubProCityList("");
	private CoAgencyBaseModel coagmodel = new CoAgencyBaseModel();
	private List<CoPolicyNoticeFileModel> filelist = new ArrayList<CoPolicyNoticeFileModel>();
	private List<Media> mlist = new ArrayList<Media>();
	private boolean filegdvisible = false;// 控制已选择行的可见性
	private Date ownmonth, feedbackbydate;

	public PoNo_InfoUpdateController() {
		if (model.getOwnmonth() != null) {
			ownmonth = StringToDate2(model.getOwnmonth() + "");
		}
		if (model.getPono_feedbackbydate() != null) {
			feedbackbydate = StringToDate(model.getPono_feedbackbydate());
		}
		filelist = model.getFilelist();
		if (filelist != null && filelist.size() > 0) {
			filegdvisible = true;
		}
	}

	// 添加文件
	@Command
	@NotifyChange({ "filegdvisible", "filelist" })
	public void upfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev) {
		try {
			Media media = ev.getMedia();
			if (media != null) {
				String ss = isExistFile(media.getName());
				if (ss == "") {
					CoPolicyNoticeFileModel fl = new CoPolicyNoticeFileModel();
					Integer k = 0;
					// 检查是否已选择文件
					for (CoPolicyNoticeFileModel m : filelist) {
						if (m.getFile_title().equals(media.getName())) {
							k = 1;
							break;
						}
					}
					if (k == 0) {
						fl.setFile_title(media.getName());
						filelist.add(fl);
						mlist.add(media);
					} else {
						Messagebox.show("该文件已在已选列表中，不能重复选择", "提示",
								Messagebox.OK, Messagebox.ERROR);
					}
				} else {
					Messagebox.show(ss, "提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
			if (filelist.size() > 0) {
				filegdvisible = true;
			} else {
				filegdvisible = false;
			}
		} catch (Exception e) {
			Messagebox.show("请检查文件名是否含有特殊符号", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 删除已选择文件
	@Command
	@NotifyChange({ "filegdvisible", "filelist" })
	public void del(@BindingParam("model") CoPolicyNoticeFileModel fmodel) {
		try {
			if (fmodel != null && filelist.size() > 0) {
				if (filelist.contains(fmodel)) {
					filelist.remove(fmodel);
					for (Media m : mlist) {
						if (m.getName().equals(fmodel.getFile_title())) {
							mlist.remove(m);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
		}
		if (filelist.size() > 0) {
			filegdvisible = true;
		} else {
			filegdvisible = false;
		}
	}

	// 机构的选择事件
	@Command
	@NotifyChange("citylist")
	public void changecity(@BindingParam("cb") Combobox cb) {
		if (cb.getValue() != null && !cb.getValue().equals("")) {
			coagmodel = cb.getSelectedItem().getValue();
			citylist = bll.getCoPubProCityListById(coagmodel.getCoab_id());
		}
	}

	@Command
	public void summit(@BindingParam("gd") Grid gd,
			@BindingParam("win") Window win) {
		// 先把文件上传到服务器
		String strinfo = isEmploy();
		if (strinfo == "") {
			// 检查已选文件列表中是否还有文件类型没有选择
			strinfo = isSelectedType(gd);
			if (strinfo == "") {
				String username = UserInfo.getUsername();
				model.setPono_addname(username);
				PoNo_OperateBll obll = new PoNo_OperateBll();
				// 修改政策通知基本信息
				int id = obll.CoPolicyNoticeUpdate(model);
				Integer l = 0;
				if (id > 0) {
					// 先把政策通知文件的所有状态改为无效（0）
					int row = obll.updateCoPolicyNoticeFileState(model
							.getPono_id());
					// 添加政策通知文件信息
					for (int i = 0; i < filelist.size(); i++) {
						CoPolicyNoticeFileModel flm = filelist.get(i);
						flm.setFile_addname(username);
						flm.setFile_pono_id(model.getPono_id());
						l = l + obll.CoPolicyNoticeFileAdd(flm);
					}
					Messagebox.show("修改成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("修改失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show(strinfo, "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show(strinfo, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检查是否填写完必填项
	private String isEmploy() {
		String str = "";
		if (model.getPono_title() == null || model.getPono_title().equals("")) {
			str = "请填写标题";
		} else if (model.getPono_type() == null
				|| model.getPono_type().equals("")) {
			str = "请选择文件类型";
		} else if (ownmonth == null) {
			str = "请选择所属月份";
		} else {
			if (mlist.size() > 0) {
				Integer k = savefile();
				if (k < mlist.size()) {
					str = "文件上传失败";
				}
			}
			if (str == "") {
				String om = DateToStr2(ownmonth);
				if (om != null && !om.equals("")) {
					model.setOwnmonth(Integer.parseInt(om));
				}
				if (feedbackbydate != null) {
					model.setPono_feedbackbydate(DateToStr(feedbackbydate));
				}
			}
		}
		return str;
	}

	// 保存文件到服务器方法
	private int savefile() {
		Integer k = 0;
		String RelativePath = "CoPolicyNotice/file/uploadfile/";
		String realPath = FileOperate.getAbsolutePath() + RelativePath;
		if (filelist.size() > 0) {
			for (int i = 0; i < filelist.size(); i++) {
				CoPolicyNoticeFileModel ml = filelist.get(i);
				String file = realPath + ml.getFile_title();
				Media media = null;
				for (Media m : mlist) {
					if (m.getName().equals(ml.getFile_title())) {
						media = m;
						break;
					}
				}
				if (media != null) {
					String name = "note"
							+ UserInfo.getUserid()
							+ DateStringChange.DatetoSting(new Date(),
									"yyyyMMddhhmmss") + i + "."
							+ media.getFormat();

					boolean flag = FileOperate.upload(media, RelativePath
							+ name);
					if (flag) {
						k = k + 1;
						filelist.get(i).setFile_url(RelativePath + name);
					}
				}
			}
		}
		return k;
	}

	// 检查已选文件是否已经选择了类型
	private String isSelectedType(Grid gd) {
		String str = "";
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 1) != null) {
				Combobox cb = (Combobox) gd.getCell(i, 1).getChildren().get(0);
				if (cb.getValue() == null || cb.getValue().equals("")) {
					str = "选中的文件列表中还有文件类型没有选择";
					break;
				}
			}
		}
		return str;
	}

	// 查询是否已经有了改文件名
	private String isExistFile(String filename) {
		PoNo_SelectBll bl = new PoNo_SelectBll();
		String str = "";
		boolean flag = bl.isExistFile(filename);
		if (flag) {
			str = "已有该文件名，请修改文件名后再上传";
		}
		return str;
	}

	public List<CoAgencyBaseModel> getCoaglist() {
		return coaglist;
	}

	public void setCoaglist(List<CoAgencyBaseModel> coaglist) {
		this.coaglist = coaglist;
	}

	public List<PubProCityModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<PubProCityModel> citylist) {
		this.citylist = citylist;
	}

	public CoPolicyNoticeModel getModel() {
		return model;
	}

	public void setModel(CoPolicyNoticeModel model) {
		this.model = model;
	}

	public List<CoPolicyNoticeFileModel> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<CoPolicyNoticeFileModel> filelist) {
		this.filelist = filelist;
	}

	public boolean isFilegdvisible() {
		return filegdvisible;
	}

	public void setFilegdvisible(boolean filegdvisible) {
		this.filegdvisible = filegdvisible;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public Date getFeedbackbydate() {
		return feedbackbydate;
	}

	public void setFeedbackbydate(Date feedbackbydate) {
		this.feedbackbydate = feedbackbydate;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr2(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String str = "";
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param date
	 * @return str
	 */
	public static Date StringToDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (dateStr != null) {
				date = format.parse(dateStr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param date
	 * @return str
	 */
	public static Date StringToDate2(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Date date = null;
		try {
			if (dateStr != null) {
				date = format.parse(dateStr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
