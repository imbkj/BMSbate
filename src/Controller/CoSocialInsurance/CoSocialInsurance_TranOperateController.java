package Controller.CoSocialInsurance;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.CoSocialInsurance.CoSocialInsurance_OperateDal;

import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.CoSocialInsurance.CoSocialInsurance_OperateBll;
import Model.CoBaseModel;
import Model.CoShebaoChangeModel;
import Model.PubAreaCoShebaoModel;
import Model.PubBankModel;
import Model.PubHealthModel;
import Model.PubIndustryModel;
import Util.FileOperate;
import Util.UserInfo;

public class CoSocialInsurance_TranOperateController {

	private CoShebaoChangeModel m = new CoShebaoChangeModel();
	Integer daid = 0;
	Media xlsMedia;
	Media imageMedia;
	private boolean ifxls;
	private boolean ifimg;
	private Integer state;

	// 下拉列表
	private List<CoBaseModel> cobaseList = new ListModelList<>();// 公司
	private CoBaseModel coBaseModel = new CoBaseModel();
	private List<PubIndustryModel> industryList = new ListModelList<>();// 经济类型
	private List<PubAreaCoShebaoModel> areaList;// 区域
	private PubAreaCoShebaoModel areaModel = new PubAreaCoShebaoModel();
	private List<PubAreaCoShebaoModel> townList;// 镇
	private List<PubAreaCoShebaoModel> townList1 = new ListModelList<>();
	private PubAreaCoShebaoModel townModel = new PubAreaCoShebaoModel();
	private List<PubAreaCoShebaoModel> streetList;// 街道
	private List<PubAreaCoShebaoModel> streetList1 = new ListModelList<>();
	private List<PubBankModel> bankList;
	private List<PubHealthModel> healthList;// 医疗机构

	private Double per1;
	private Double per2;
	private Date ukeytruetime, ukeyfailtime;
	private DecimalFormat df = new DecimalFormat(".##");
	
	public CoSocialInsurance_TranOperateController() {
		try {
			CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setM(bll.getCoShebaoChangeInfo(daid));
			if (m.getCsbc_state() == 5 || m.getCsbc_state() == 6) {
				setState(m.getCsbc_laststate());
			} else {
				setState(m.getCsbc_state());
			}
			setPer1(Double.parseDouble(df.format(m.getCsbc_unemployment_per() * 100)));
			setPer2(Double.parseDouble(df.format(m.getCsbc_business_per() * 100)));
			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void init() {
		if (m.getCsbc_xls() != null && !m.getCsbc_xls().isEmpty()) {
			setIfxls(true);
		}
		if (m.getCsbc_image() != null && !m.getCsbc_image().isEmpty()) {
			setIfimg(true);
		}
		if(m.getCosb_ukeytruetime()!=null&&!m.getCosb_ukeytruetime().equals(""))
		{
			ukeytruetime=SrtoDate(m.getCosb_ukeytruetime());
		}
		if(m.getCosb_ukeyfailtime()!=null&&!m.getCosb_ukeyfailtime().equals(""))
		{
			ukeyfailtime=SrtoDate(m.getCosb_ukeyfailtime());
		}
		fieldinit();
		comboinit();
	}

	/**
	 * 字段初始化
	 */
	public void fieldinit() {
		m.setCsbc_submission("网上申报");
		m.setCsbc_paytype("电脑托收");
	}

	/**
	 * 下拉列表初始化
	 */
	public void comboinit() {
		CoSocialInsurance_ListBll bll = new CoSocialInsurance_ListBll();

		setCobaseList(bll.getCobaList(null));
		setIndustryList(bll.getIndustryList());
		industryList.add(0, new PubIndustryModel());
		setAreaList(new ListModelList<>(bll.getAreaList()));
		areaList.add(0, new PubAreaCoShebaoModel());
		setTownList(new ListModelList<>(bll.getTownList()));
		setStreetList(new ListModelList<>(bll.getStreetList()));
		setBankList(new ListModelList<>(bll.getBankList()));
		bankList.add(0, new PubBankModel());
		setHealthList(new ListModelList<>(bll.getHealthList()));
		healthList.add(0, new PubHealthModel());

		if (m.getCsbc_sorarea() != null && !m.getCsbc_sorarea().isEmpty()) {
			for (PubAreaCoShebaoModel m : areaList) {
				if (this.m.getCsbc_sorarea().equals(m.getName())) {
					setAreaModel(m);
					break;
				}
			}
			areaChange();
		}
		if (m.getCsbc_town() != null && !m.getCsbc_town().isEmpty()) {
			for (PubAreaCoShebaoModel m : townList1) {
				if (this.m.getCsbc_town().equals(m.getName())) {
					setTownModel(m);
					break;
				}
			}
			townChange();
		}
	}

	/**
	 * 选择区域
	 */
	@Command("areaChange")
	@NotifyChange({ "townList1", "streetList1", "m", "townModel" })
	public void areaChange() {
		townList1.clear();
		if (areaModel.getId() != null) {
			for (PubAreaCoShebaoModel m : townList) {
				if (areaModel.getId().toString().equals(m.getA_id().toString())) {
					townList1.add(m);
				}
			}
		}
		if (townList1.size() == 0) {
			townList1.add(0, new PubAreaCoShebaoModel());
		}
		townModel = townList1.get(0);
		townChange();
	}

	/**
	 * 选择镇
	 */
	@Command("townChange")
	@NotifyChange({ "streetList1", "m" })
	public void townChange() {
		streetList1.clear();
		if (townModel.getId() != null) {
			for (PubAreaCoShebaoModel m : streetList) {
				if (townModel.getId().toString().equals(m.getT_id().toString())) {
					streetList1.add(m);
				}
			}
		}
		if (streetList1.size() == 0) {
			streetList1.add(new PubAreaCoShebaoModel());
		}
		this.m.setCsbc_street(streetList1.get(0).getName());
	}

	/**
	 * 下载已上传文件
	 * 
	 * @param type
	 *            xls:人员在册名单 img:公司信息截图
	 */
	@Command("download")
	public void download(@BindingParam("type") String type) {
		if (type.equals("xls")) {
			FileOperate.download("OfficeFile/DownLoad/CoSocialInsurance/"
					+ m.getCsbc_xls());
		} else if (type.equals("img")) {
			FileOperate.download("OfficeFile/DownLoad/CoSocialInsurance/"
					+ m.getCsbc_image());
		}
	}

	/**
	 * 获取上传文件
	 * 
	 * @param media
	 */
	@Command("browse")
	@NotifyChange({ "m" })
	public void browse(@BindingParam("media") Media media) {

		if (media.getFormat().equals("xls") || media.getFormat().equals("xlsx")) {
			if (!ifxls) {
				m.setCsbc_xls(media.getName());
				xlsMedia = media;
			}
		} else if (media instanceof Image) {
			if (!ifimg) {
				m.setCsbc_image(media.getName());
				imageMedia = media;
			}
		} else {
			Messagebox.show("文件类型错误,請檢查!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @param gd
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		flagA: {
			if (Messagebox.show("是否确定提交?", "CONFIRM", Messagebox.OK
					| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
				if (xlsMedia != null) {
					String xlsurl = "OfficeFile/DownLoad/CoSocialInsurance/"
							+ m.getCsbc_xls();
					try {
						FileOperate.upload(xlsMedia, xlsurl);
					} catch (Exception e) {
						Messagebox.show("上传人员在册名单失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						e.printStackTrace();
						break flagA;
					}
				}
				if (imageMedia != null) {
					String imgurl = "OfficeFile/DownLoad/CoSocialInsurance/"
							+ m.getCsbc_image();
					try {
						FileOperate.upload(imageMedia, imgurl);
					} catch (Exception e) {
						Messagebox.show("上传公司信息截图失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
						e.printStackTrace();
						break flagA;
					}
				}
			}

			try {
				fieldhandle();
				m.setCsbc_state(state + 1);
			} catch (Exception e) {
				Messagebox.show("数据处理出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
				break flagA;
			}
			if (per1 != null && per1 > 0) {
				m.setCsbc_unemployment_per(per1 / 100);
			}else {
				Messagebox.show("请输入失业比例!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				break flagA;
			}
			if (per2 != null && per2 > 0) {
				m.setCsbc_business_per(per2 / 100);
			}else {
				Messagebox.show("请输入工伤比例!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				break flagA;
			}

			try {
				String[] str = new CoSocialInsurance_OperateBll().UpdateState(
						m, new Grid());
				if (str[0].equals("1")) {
					if (ukeytruetime != null && !ukeytruetime.equals("")) {
						m.setCosb_ukeytruetime(datetostr(ukeytruetime));
					}
					if (ukeyfailtime != null && !ukeyfailtime.equals("")) {
						m.setCosb_ukeyfailtime(datetostr(ukeyfailtime));
					}
					new CoSocialInsurance_OperateDal().UpdateInfo(m);
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
			}
		}
	}

	@Command("save")
	public void save(@BindingParam("win") Window win) {
		flagA: {
			Integer row = 0;
			CoSocialInsurance_OperateBll bll = new CoSocialInsurance_OperateBll();
			if (xlsMedia != null) {
				String xlsurl = "OfficeFile/DownLoad/CoSocialInsurance/"
						+ m.getCsbc_xls();
				try {
					FileOperate.upload(xlsMedia, xlsurl);

					bll.InsertLog(daid, "待调入", "福利部上传人员在册名单", "csoiadd1",
							m.getCsbc_xls());
				} catch (Exception e) {
					Messagebox.show("上传人员在册名单失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					e.printStackTrace();
					break flagA;
				}
			}
			if (imageMedia != null) {
				String imgurl = "OfficeFile/DownLoad/CoSocialInsurance/"
						+ m.getCsbc_image();
				try {
					FileOperate.upload(imageMedia, imgurl);
					bll.InsertLog(daid, "待调入", "福利部上传公司信息截图", "csoiadd1",
							m.getCsbc_image());
				} catch (Exception e) {
					Messagebox.show("上传公司信息截图失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					e.printStackTrace();
					break flagA;
				}
			}
			if (xlsMedia != null && imageMedia != null) {
				bll.InsertLog(daid, "待调入", "福利部保存公司信息", "csoiadd1", "");
			}

			try {
				fieldhandle();
			} catch (Exception e) {
				Messagebox.show("数据处理出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				e.printStackTrace();
				break flagA;
			}
			
			if (per1 != null && per1 > 0) {
				m.setCsbc_unemployment_per(per1 / 100);
			}else {
				Messagebox.show("请输入失业比例!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				break flagA;
			}
			if (per2 != null && per2 > 0) {
				m.setCsbc_business_per(per2 / 100);
			}else {
				Messagebox.show("请输入工伤比例!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				break flagA;
			}
			row = new CoSocialInsurance_OperateDal().mod(m);

			if (row >= 1) {
				if (ukeytruetime != null && !ukeytruetime.equals("")) {
					m.setCosb_ukeytruetime(datetostr(ukeytruetime));
				}
				if (ukeyfailtime != null && !ukeyfailtime.equals("")) {
					m.setCosb_ukeyfailtime(datetostr(ukeyfailtime));
				}
				new CoSocialInsurance_OperateDal().UpdateInfo(m);
				Messagebox.show("保存成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("保存失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/**
	 * 数据处理
	 * 
	 */
	public void fieldhandle() {
		m.setCsbc_addname(UserInfo.getUsername());
		m.setCsbc_sorarea(areaModel.getName());
		m.setCsbc_town(townModel.getName());
		if (m.getCsbc_heaname() != null && !m.getCsbc_heaname().isEmpty()) {
			m.setCsbc_heaname(m.getCsbc_heaname().substring(1));
		}
		
	}

	public CoShebaoChangeModel getM() {
		return m;
	}

	public void setM(CoShebaoChangeModel m) {
		this.m = m;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public boolean isIfxls() {
		return ifxls;
	}

	public void setIfxls(boolean ifxls) {
		this.ifxls = ifxls;
	}

	public boolean isIfimg() {
		return ifimg;
	}

	public void setIfimg(boolean ifimg) {
		this.ifimg = ifimg;
	}

	public List<CoBaseModel> getCobaseList() {
		return cobaseList;
	}

	public void setCobaseList(List<CoBaseModel> cobaseList) {
		this.cobaseList = cobaseList;
	}

	public CoBaseModel getCoBaseModel() {
		return coBaseModel;
	}

	public void setCoBaseModel(CoBaseModel coBaseModel) {
		this.coBaseModel = coBaseModel;
	}

	public List<PubIndustryModel> getIndustryList() {
		return industryList;
	}

	public void setIndustryList(List<PubIndustryModel> industryList) {
		this.industryList = industryList;
	}

	public List<PubAreaCoShebaoModel> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<PubAreaCoShebaoModel> areaList) {
		this.areaList = areaList;
	}

	public PubAreaCoShebaoModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(PubAreaCoShebaoModel areaModel) {
		this.areaModel = areaModel;
	}

	public List<PubAreaCoShebaoModel> getTownList() {
		return townList;
	}

	public void setTownList(List<PubAreaCoShebaoModel> townList) {
		this.townList = townList;
	}

	public List<PubAreaCoShebaoModel> getTownList1() {
		return townList1;
	}

	public void setTownList1(List<PubAreaCoShebaoModel> townList1) {
		this.townList1 = townList1;
	}

	public PubAreaCoShebaoModel getTownModel() {
		return townModel;
	}

	public void setTownModel(PubAreaCoShebaoModel townModel) {
		this.townModel = townModel;
	}

	public List<PubAreaCoShebaoModel> getStreetList() {
		return streetList;
	}

	public void setStreetList(List<PubAreaCoShebaoModel> streetList) {
		this.streetList = streetList;
	}

	public List<PubAreaCoShebaoModel> getStreetList1() {
		return streetList1;
	}

	public void setStreetList1(List<PubAreaCoShebaoModel> streetList1) {
		this.streetList1 = streetList1;
	}

	public List<PubBankModel> getBankList() {
		return bankList;
	}

	public void setBankList(List<PubBankModel> bankList) {
		this.bankList = bankList;
	}

	public List<PubHealthModel> getHealthList() {
		return healthList;
	}

	public void setHealthList(List<PubHealthModel> healthList) {
		this.healthList = healthList;
	}

	public Double getPer1() {
		return per1;
	}

	public void setPer1(Double per1) {
		this.per1 = per1;
	}

	public Double getPer2() {
		return per2;
	}

	public void setPer2(Double per2) {
		this.per2 = per2;
	}

	public Date getUkeytruetime() {
		return ukeytruetime;
	}

	public void setUkeytruetime(Date ukeytruetime) {
		this.ukeytruetime = ukeytruetime;
	}

	public Date getUkeyfailtime() {
		return ukeyfailtime;
	}

	public void setUkeyfailtime(Date ukeyfailtime) {
		this.ukeyfailtime = ukeyfailtime;
	}
	private String datetostr(Date date) {
		String str = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (date != null) {
				str = format.format(date);
			}
		} catch (Exception e) {

		}
		return str;
	}
	
	private Date SrtoDate(String str) {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (str != null) {
				date = format.parse(str);
			}
		} catch (Exception e) {

		}
		return date;
	}
}
