package Controller.CoProduct;

import impl.UserInfoImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.*;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;

import bll.CoProduct.cpd_addBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoPclassModel;
import Model.CoProductModel;
import Model.PubProCityModel;
import Util.plyUtil;

public class cpd_addController {

	cpd_addBll bll = new cpd_addBll();
	plyUtil ply = new plyUtil();
	private CoProductModel cop = new CoProductModel();
	private ListModelList<String> standardlist = new ListModelList<>();
	private ListModelList<CoPclassModel> classlist;
	private ListModelList<CoPclassModel> sclasslist = new ListModelList<>();
	private CoPclassModel classModel = new CoPclassModel();
	private ListModelList<CoProductModel> eclasslist;

	private List<PubProCityModel> cityList;
	private PubProCityModel cityModel = new PubProCityModel();
	private List<CoAgencyBaseCityRelViewModel> coagList;
	private List<CoAgencyBaseCityRelViewModel> scoagList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel coagModel = new CoAgencyBaseCityRelViewModel();

	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	// 初始化
	@Init
	public void init() throws SQLException {
		classlist = new ListModelList<CoPclassModel>(bll.getclass());
		this.setEclasslist(new ListModelList<CoProductModel>(bll.geteclass()));
		setCityList(bll.getcity());
		setCoagList(bll.getCoagList());
		if (cityList.size() > 0) {
			for (PubProCityModel m1 : cityList) {
				if (m1.getName().equals("(s)深圳")) {
					setCityModel(m1);
					break;
				}
			}
			city_change();
		}
		cop.setCopr_type("服务产品");
		class_change(null);
	}

	/**
	 * 城市变更
	 * 
	 */
	@Command("city_change")
	@NotifyChange({ "scoagList", "coagModel" })
	public void city_change() {
		try {
			scoagList.clear();

			for (CoAgencyBaseCityRelViewModel m1 : coagList) {
				if (m1.getCabc_ppc_id() == cityModel.getId()) {
					scoagList.add(m1);
				}
			}
			if (scoagList.size() > 0) {
				setCoagModel(scoagList.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 筛选产品类型列表
	 * 
	 */
	@Command("class_change")
	@NotifyChange({ "sclasslist", "standardlist" })
	public void class_change(@BindingParam("cpst_name") Chosenbox chosenbox) {
		if (!cop.getCopr_type().isEmpty()) {
			sclasslist.clear();
			for (CoPclassModel m : classlist) {
				if (!cop.getCopr_type().equals(m.getCopc_type1())) {
					continue;
				}

				sclasslist.add(m);
			}
			if (sclasslist.size() > 0) {
				classModel = sclasslist.get(0);
				getstalist(chosenbox);
			}
		}
	}

	/**
	 * 获取享受方式列表
	 * 
	 */
	@Command("getstalist")
	@NotifyChange({ "standardlist" })
	public void getstalist(@BindingParam("cpst_name") Chosenbox chosenbox) {
		if (classModel != null) {
			setStandardlist(new ListModelList<>(bll.getStandardList1(classModel
					.getCopc_id())));
			if (chosenbox != null) {
				chosenbox.clearSelection();
			}
		}
	}

	// chosenbox全选
	@Command("allselect")
	public void allselect(@BindingParam("select") boolean select) {
		if (select) {
			for (int i = 0; i < standardlist.getSize(); i++) {
				this.standardlist.addToSelection(this.standardlist.get(i));
			}
		} else {
			this.standardlist.clearSelection();
		}
	}

	// 点击cell,改变checkbox是否选中
	@Command("cellclick")
	@NotifyChange({ "eclasslist" })
	public void cellclick(@BindingParam("each") CoProductModel m1) {
		m1.setIfU(!m1.isIfU());
	}

	// 表格每行checkbox全选
	@Command("gdallselect")
	@NotifyChange({ "eclasslist" })
	public void gdallselect(@BindingParam("check") boolean check) {
		for (CoProductModel m1 : eclasslist) {
			m1.setIfU(check);
		}
	}

	// 提交新增
	@Command("submit")
	@NotifyChange({ "cop", "eclasslist" })
	public void submit(@BindingParam("cpst_name") final Chosenbox cpst_name) {

		try {
			int flag = 0;

			for (CoProductModel m1 : eclasslist) {
				if (m1.isIfU()) {
					flag = 1;
					break;
				}
			}

			// 获取享受方式
			Set<Object> cpst_nameSet = cpst_name.getSelectedObjects();
			String cpst_nameStr = ply.SetToString(cpst_nameSet);

			// 传值到CoProductModel
			cop.setCopr_state(1);
			cop.setCopr_copc_id(classModel.getCopc_id());
			cop.setCopr_addname(user.getUsername());
			cop.setCpst_name(cpst_nameStr);
			cop.setCopr_cabc_id(coagModel.getCabc_id());

			// 输入信息判断
			if (cop.getCopr_name() == null || cop.getCopr_name().isEmpty()) {
				Messagebox.show("请输入产品名称!", "输入信息错误", Messagebox.OK,
						Messagebox.ERROR);
			} else if (bll.Exist(cop)) {
				Messagebox.show("该委托机构已存在此产品!", "输入信息错误", Messagebox.OK,
						Messagebox.ERROR);
			} else if (classModel.getCopc_id() == 0) {
				Messagebox.show("请选择产品类型!", "输入信息错误", Messagebox.OK,
						Messagebox.ERROR);
			} else if (flag == 0) {
				Messagebox.show("请至少选择一个收费单位!", "输入信息错误", Messagebox.OK,
						Messagebox.ERROR);
			} else {

				// 调用bll的方法进行新增
				String[] str = bll.cpdadd(cop, eclasslist);

				// 根据行数判断，给出提示
				if (str[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								after_submit();
								cpst_name.clearSelection();
							}
						}
					};
					Messagebox.show(str[1], "INFORMATION",
							new Messagebox.Button[] { Messagebox.Button.OK },
							Messagebox.INFORMATION, clickListener);
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void after_submit() {
		cop.setCopr_name("");
		cop.setCopr_content("");
		cop.setCopr_type("服务产品");
		class_change(null);
		for (CoProductModel m1 : eclasslist) {
			m1.setIfU(false);
			m1.setCpfr_lock1(false);
			m1.setFee(BigDecimal.ZERO);
		}
		standardlist.clear();
	}

	public ListModelList<String> getStandardlist() {
		return standardlist;
	}

	public void setStandardlist(ListModelList<String> standardlist) {
		this.standardlist = standardlist;
	}

	public ListModelList<CoPclassModel> getClasslist() {
		return classlist;
	}

	public void setClasslist(ListModelList<CoPclassModel> classlist) {
		this.classlist = classlist;
	}

	public ListModelList<CoProductModel> getEclasslist() {
		return eclasslist;
	}

	public void setEclasslist(ListModelList<CoProductModel> eclasslist) {
		this.eclasslist = eclasslist;
	}

	public CoProductModel getCop() {
		return cop;
	}

	public void setCop(CoProductModel cop) {
		this.cop = cop;
	}

	public List<PubProCityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<PubProCityModel> cityList) {
		this.cityList = cityList;
	}

	public PubProCityModel getCityModel() {
		return cityModel;
	}

	public void setCityModel(PubProCityModel cityModel) {
		this.cityModel = cityModel;
	}

	public List<CoAgencyBaseCityRelViewModel> getCoagList() {
		return coagList;
	}

	public void setCoagList(List<CoAgencyBaseCityRelViewModel> coagList) {
		this.coagList = coagList;
	}

	public List<CoAgencyBaseCityRelViewModel> getScoagList() {
		return scoagList;
	}

	public void setScoagList(List<CoAgencyBaseCityRelViewModel> scoagList) {
		this.scoagList = scoagList;
	}

	public CoAgencyBaseCityRelViewModel getCoagModel() {
		return coagModel;
	}

	public void setCoagModel(CoAgencyBaseCityRelViewModel coagModel) {
		this.coagModel = coagModel;
	}

	public CoPclassModel getClassModel() {
		return classModel;
	}

	public void setClassModel(CoPclassModel classModel) {
		this.classModel = classModel;
	}

	public ListModelList<CoPclassModel> getSclasslist() {
		return sclasslist;
	}

	public void setSclasslist(ListModelList<CoPclassModel> sclasslist) {
		this.sclasslist = sclasslist;
	}

}
