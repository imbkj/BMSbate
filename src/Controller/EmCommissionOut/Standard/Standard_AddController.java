package Controller.EmCommissionOut.Standard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.Standard.Standard_ListBll;
import bll.EmCommissionOut.Standard.Standard_OperateBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.CoOfferListModel;
import Model.EmCommissionOutStandardModel;
import Model.PubProCityModel;
import Util.UserInfo;

public class Standard_AddController {

	private EmCommissionOutStandardModel m = new EmCommissionOutStandardModel();
	private CoBaseModel com = new CoBaseModel();
	private boolean ltbmu = true;

	// 城市
	private ListModelList<PubProCityModel> cityList = new ListModelList<>();
	private PubProCityModel ppcModel = new PubProCityModel();

	// 默认委托机构
	private CoAgencyBaseCityRelViewModel cabm = new CoAgencyBaseCityRelViewModel();

	// 社保、公积金
	private List<String> sbzhtypeList = new ListModelList<>();
	private List<String> sbfeetypeList = new ListModelList<>();
	private List<String> gjjzhtypeList = new ListModelList<>();
	private List<String> gjjfeetypeList = new ListModelList<>();

	// 福利产品
	private List<CoOfferListModel> colList = new ListModelList<>();

	Integer cid = 0;

	public Standard_AddController() {
		try {
			Standard_ListBll bll = new Standard_ListBll();

			cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
					.toString());

			setCom(bll.getCobaInfo(cid));

			// 城市
			setCityList(bll.getCityList());

			// 社保、公积金
			sbzhtypeList.add("");
			sbzhtypeList.add("受托方账户");
			sbzhtypeList.add("独立账户");

			sbfeetypeList.add("");
			sbfeetypeList.add("委托对账");
			sbfeetypeList.add("单独支付");

			gjjzhtypeList.add("");
			gjjzhtypeList.add("受托方账户");
			gjjzhtypeList.add("独立账户");

			gjjfeetypeList.add("");
			gjjfeetypeList.add("委托对账");
			gjjfeetypeList.add("单独支付");

			cabm.setCoab_name("无");

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("listitemInit")
	public void listitemInit(@BindingParam("listitem") Listitem lt) {
		lt.setSelected(true);
	}

	@Command("cityOnChange")
	@NotifyChange({ "cabm", "colList" })
	public void cityOnChange() {
		Standard_ListBll bll = new Standard_ListBll();

		if (ppcModel != null) {
			setCabm(bll.getDefaultCoAgency(ppcModel.getId()));
			if (cabm.getCoab_name() == null || cabm.getCoab_name().isEmpty()) {
				cabm.setCoab_name("无");
			}
			if (cabm.getCoab_name().equals("无")) {
				setColList(bll.getCoOfferList(cid, 0, ppcModel.getId()));
			} else {
				setColList(bll.getCoOfferList(cid, cabm.getCabc_id(), 0));
			}
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("set") Set<Listitem> set) {
		if (m.getEcos_name() == null || m.getEcos_name().isEmpty()) {
			Messagebox.show("请输入名称!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else if (ppcModel == null) {
			Messagebox.show("请选择委托地区!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			try {
				handlefield();
				String[] str = new Standard_OperateBll().add(m,
						com.getCoba_shortname(), set);

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/**
	 * 字段处理
	 * 
	 */
	public void handlefield() {
		m.setOwnmonth(new SimpleDateFormat("yyyyMM").format(new Date()));
		m.setCid(cid);
		m.setEcos_addname(UserInfo.getUsername());
		m.setEcos_usestate(0);
		m.setEcos_laststate(1);

		// 如无默认机构，则走设置机构流程
		if (cabm.getCoab_name().equals("无")) {
			m.setEcos_state(1);
			// 记录城市id
			m.setEcos_cabc_id(ppcModel.getId());
		} else {
			m.setEcos_state(2);
			// 记录默认机构id
			m.setEcos_cabc_id(cabm.getCabc_id());
		}
	}

	public EmCommissionOutStandardModel getM() {
		return m;
	}

	public void setM(EmCommissionOutStandardModel m) {
		this.m = m;
	}

	public ListModelList<PubProCityModel> getCityList() {
		return cityList;
	}

	public void setCityList(ListModelList<PubProCityModel> cityList) {
		this.cityList = cityList;
	}

	public PubProCityModel getPpcModel() {
		return ppcModel;
	}

	public void setPpcModel(PubProCityModel ppcModel) {
		this.ppcModel = ppcModel;
	}

	public List<String> getSbzhtypeList() {
		return sbzhtypeList;
	}

	public void setSbzhtypeList(List<String> sbzhtypeList) {
		this.sbzhtypeList = sbzhtypeList;
	}

	public List<String> getSbfeetypeList() {
		return sbfeetypeList;
	}

	public void setSbfeetypeList(List<String> sbfeetypeList) {
		this.sbfeetypeList = sbfeetypeList;
	}

	public List<String> getGjjzhtypeList() {
		return gjjzhtypeList;
	}

	public void setGjjzhtypeList(List<String> gjjzhtypeList) {
		this.gjjzhtypeList = gjjzhtypeList;
	}

	public List<String> getGjjfeetypeList() {
		return gjjfeetypeList;
	}

	public void setGjjfeetypeList(List<String> gjjfeetypeList) {
		this.gjjfeetypeList = gjjfeetypeList;
	}

	public CoBaseModel getCom() {
		return com;
	}

	public void setCom(CoBaseModel com) {
		this.com = com;
	}

	public CoAgencyBaseCityRelViewModel getCabm() {
		return cabm;
	}

	public void setCabm(CoAgencyBaseCityRelViewModel cabm) {
		this.cabm = cabm;
	}

	public List<CoOfferListModel> getColList() {
		return colList;
	}

	public void setColList(List<CoOfferListModel> colList) {
		this.colList = colList;
	}

	public boolean isLtbmu() {
		return ltbmu;
	}

	public void setLtbmu(boolean ltbmu) {
		this.ltbmu = ltbmu;
	}

}
