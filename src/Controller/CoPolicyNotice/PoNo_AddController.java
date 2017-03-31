package Controller.CoPolicyNotice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.CoPolicyNoticeFileModel;
import Model.CoPolicyNoticeModel;
import Model.LoginModel;
import Model.PubProCityModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoAgencies.CoAg_CompactSelectBll;
import bll.CoAgency.WtAgency_DisCitySelBll;
import bll.CoPolicyNotice.PoNo_OperateBll;

public class PoNo_AddController {
	private WtAgency_DisCitySelBll blls;
	private List<CoAgencyBaseCityRelModel> cList;
	private CoAg_CompactSelectBll bll = new CoAg_CompactSelectBll();
	private List<CoAgencyBaseModel> coaglist = bll.getCoAgencyBaseList("");
	private List<PubProCityModel> citylist = bll.getCoPubProCityList("");
	private CoPolicyNoticeModel model = new CoPolicyNoticeModel();
	private CoAgencyBaseModel coagmodel = new CoAgencyBaseModel();
	private List<CoPolicyNoticeFileModel> filelist = new ArrayList<CoPolicyNoticeFileModel>();
	private List<Media> mlist = new ArrayList<Media>();
	private boolean filegdvisible = false, viableag = false;// 控制已选择行的可见性
	private Date ownmonth, feedbackbydate;
	private CoAgencyBaseCityRelModel citymodel=new CoAgencyBaseCityRelModel();
	private String classname="",type="";
	
	public PoNo_AddController(){
		blls = new WtAgency_DisCitySelBll();
		cList = blls.getCoAgencyBaseCityRelList();
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

	// 添加文件
	@Command
	@NotifyChange({ "filegdvisible", "filelist" })
	public void upfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev) {
		String RelativePath = "CoPolicyNotice/file/uploadfile/";
		Media media = ev.getMedia();
		if (media != null) {
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

				mlist.add(media);
				String realPath = FileOperate.getAbsolutePath() + RelativePath;
				String name = "note"
						+ UserInfo.getUserid()
						+ DateStringChange.DatetoSting(new Date(),
								"yyyyMMddhhmmss") + "." + media.getFormat();

				boolean flag = FileOperate.upload(media, RelativePath + name);
				if (flag) {
					fl.setFile_url(RelativePath + name);
				}
				filelist.add(fl);
			} else {
				Messagebox.show("该文件已在已选列表中，不能重复选择", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		if (filelist.size() > 0) {
			filegdvisible = true;
		} else {
			filegdvisible = false;
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

	// 提交
	@Command
	public void summit(@BindingParam("gd") Grid gd,
			@BindingParam("win") Window win) {
		if (model.getPono_type() != null && model.getPono_type().equals("政府通知")) {
			model.setPono_city(classname);
			model.setPono_cityid(citymodel.getId());
			model.setPono_agencies(null);
		} else {
			if (citymodel.getCabc_id() == null
					|| citymodel.getCabc_id().equals("")) {
				List<PubProCityModel> cl = bll.getCityList(" and name='"
						+ citymodel.getCity() + "' and coab_name='"
						+ model.getPono_agencies() + "'");
				if (cl.size() > 0) {
					citymodel.setCabc_id(cl.get(0).getCabc_id());
				}
			}
			model.setPono_cityid(citymodel.getId());
			model.setPono_cabc_id(citymodel.getCabc_id());
			model.setPono_city(citymodel.getCity());
		}
		// 先把文件上传到服务器
		String strinfo = isEmploy();
		if (strinfo == "") {
			// 检查已选文件列表中是否还有文件类型没有选择
			strinfo = isSelectedType(gd);
			if (strinfo == "") {
				String username = UserInfo.getUsername();
				model.setPono_addname(username);
				PoNo_OperateBll obll = new PoNo_OperateBll();
				// 添加政策通知基本信息
				int id = obll.CoPolicyNoticeAdd(model);
				Integer l = 0;
				if (id > 0) {
					// 添加政策通知文件信息
					for (int i = 0; i < filelist.size(); i++) {
						CoPolicyNoticeFileModel flm = filelist.get(i);
						flm.setFile_pono_id(id);
						flm.setFile_addname(username);
						l = l + obll.CoPolicyNoticeFileAdd(flm);
						if (l <= 0) {
							break;
						}
					}
					if (l < filelist.size()) {
						obll.updateCoPolicyNoticeFileState(id);
						obll.updateCoPolicyNoticeState(id);
						Messagebox.show("提交失败", "提示", Messagebox.OK,
								Messagebox.ERROR);
					} else {
						// Executions.sendRedirect("/CoPolicyNotice/PoNo_InfoAdd.zul");
						Messagebox.show("提交成功", "提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				} else {
					Messagebox.show("提交失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show(strinfo, "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show(strinfo, "提示", Messagebox.OK, Messagebox.ERROR);
		}
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

	// 检查是否填写完必填项
	private String isEmploy() {
		String str = "";
		if ((model.getPono_type() != null && model.getPono_type()
				.equals("机构通知"))
				&& (model.getPono_agencies() == null || model
						.getPono_agencies().equals(""))) {
			str = "请选择发文机构";
		} else if (model.getPono_city() == null
				|| model.getPono_city().equals("")) {
			str = "请选择城市";
		} else if (model.getPono_title() == null
				|| model.getPono_title().equals("")) {
			str = "请填写标题";
		}
		// else if(model.getPono_type()==null||model.getPono_type().equals(""))
		// {
		// str="请选择文件类型";
		// }
		else if (ownmonth == null) {
			str = "请选择所属月份";
		} else {
			Integer k = savefile();
			if (k < filelist.size()) {
				str = "文件上传失败";
			} else {
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

	// 检查已选文件是否已经选择了类型
	private String isSelectedType(Grid gd) {
		String str = "";
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 1) != null) {
				Combobox cb = (Combobox) gd.getCell(i, 1).getChildren().get(0);
				if (cb.getValue() == null || cb.getValue().equals("")) {
					str = "请选择文件类型。";
					break;
				}
			}
		}
		return str;
	}

	// 通知类型选择事件
	@Command
	@NotifyChange({ "viableag", "coaglist" })
	public void changetype() {
		if (model.getPono_type() != null && model.getPono_type().equals("机构通知")) {
			viableag = true;
			coaglist = bll.getCoAgBaseListByCity(citymodel.getCity());
		} else {
			viableag = false;
		}
	}

	// 打开发短信页面
	@Command
	public void sendsmg() {
		Map map = new HashMap<>();
		map.put("type", "");// 业务类型:来自WfClass的wfcl_name
		map.put("id", citymodel.getId());// 业务表id
		map.put("tablename", "CoPolicyNotice");// 业务表名
		map.put("msgname", "");// 默认收件人,没有默认收件人则为空""
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name("");
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		map.put("content", "");
		if (model.getPono_title() != null && !model.getPono_title().equals("")) {
			map.put("title", model.getPono_title());
		} else {
			map.put("title", classname + "政策通知新增");
		}

		Window window = (Window) Executions.createComponents(
				"../CoPolicyNotice/Pono_Message_Add.zul", null, map);
		window.doModal();
	}
	
	@Command
	public void selectcity(@BindingParam("cb") Combobox cb)
	{
		if(cb.getValue()!=null&&!cb.getValue().equals(""))
		{
			citymodel=cb.getSelectedItem().getValue();
			classname=citymodel.getCity();
		}
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isViableag() {
		return viableag;
	}

	public void setViableag(boolean viableag) {
		this.viableag = viableag;
	}

	public CoAgencyBaseCityRelModel getCitymodel() {
		return citymodel;
	}

	public void setCitymodel(CoAgencyBaseCityRelModel citymodel) {
		this.citymodel = citymodel;
	}
	
	

	public List<CoAgencyBaseCityRelModel> getcList() {
		return cList;
	}

	public void setcList(List<CoAgencyBaseCityRelModel> cList) {
		this.cList = cList;
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
}
