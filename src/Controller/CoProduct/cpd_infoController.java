package Controller.CoProduct;

import impl.UserInfoImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.UserInfoService;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoProductModel;
import Model.PubProCityModel;
import Util.plyUtil;
import bll.CoProduct.cpd_ListBll;
import bll.CoProduct.cpd_addBll;

public class cpd_infoController {

	private ListModelList<String> standardlist;
	private ListModelList<String> standardlist1;
	private ListModelList<CoProductModel> eclasslist;
	private ListModelList<CoProductModel> eclasslist1;
	private String cityString;
	int copr_id = ((CoProductModel) Executions.getCurrent().getArg().get("cop"))
			.getCopr_id();
	CoProductModel cop = new CoProductModel();
	plyUtil ply = new plyUtil();
	cpd_ListBll bll = new cpd_ListBll();
	cpd_addBll bll1 = new cpd_addBll();

	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	private List<PubProCityModel> cityList;
	private PubProCityModel cityModel = new PubProCityModel();
	private List<CoAgencyBaseCityRelViewModel> coagList;
	private List<CoAgencyBaseCityRelViewModel> scoagList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel coagModel = new CoAgencyBaseCityRelViewModel();

	private Window mywin;

	private Textbox copr_content;// 描述
	private Chosenbox cpst_name;// 享受方式
	private Combobox city;// 适用地
	private Grid gd;

	// 初始化
	public cpd_infoController() {
		cop = (CoProductModel) Executions.getCurrent().getArg().get("cop");

		try {
			this.standardlist = new ListModelList<String>(bll1.getStandard());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.standardlist1 = new ListModelList<String>(
					bll.getStardBycoprid(copr_id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			setCityList(bll1.getcity());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (PubProCityModel m : cityList) {
			if (cop.getId() == m.getId()) {
				setCityModel(m);
			}
		}

		setCoagList(bll1.getCoagList());
		city_change();
		if (scoagList.size() > 0) {
			for (CoAgencyBaseCityRelViewModel m : scoagList) {
				if (cop.getCoab_id() == m.getCabc_coab_id()) {
					setCoagModel(m);
				}
			}
		}

		// 更新默认收费金额和默认收费单位列表
		this.setEclasslist(new ListModelList<CoProductModel>(bll1.getfeeClass(
				copr_id, coagModel.getCabc_id())));

	}

	@Command
	public void cw(@BindingParam("w") Window w) {
		mywin = w;
		copr_content = (Textbox) mywin.getFellow("copr_content");
		cpst_name = (Chosenbox) mywin.getFellow("cpst_name");
		// city = mywin.getFellow("city");
		gd = (Grid) mywin.getFellow("gd");
	}

	// 初始化享受方式
	@Command
	public void selectedstand() {
		for (String st : standardlist1) {
			if (standardlist.contains(st)) {
				standardlist.addToSelection(st);
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
			}else {
				setCoagModel(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command("cellclick")
	public void cellclick(@BindingParam("self") Cell cell) {
		Checkbox ckb = (Checkbox) cell.getChildren().get(0);
		ckb.setChecked(!ckb.isChecked());
	}

	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		/*
		 * for (int i = 0; i < ply.getGridCount(gd); i++) { Checkbox ckb =
		 * (Checkbox) gd.getCell(i, 0).getChildren().get(0);
		 * ckb.setChecked(check); }
		 */
		for (CoProductModel cm : eclasslist) {
			cm.setIfU(check);
			BindUtils.postNotifyChange(null, null, cm, "ifU");
		}
	}

	@Command("checkclick")
	public void checkclick(@BindingParam("self") Checkbox ckb) {
		// ckb.setChecked(!ckb.isChecked());
	}

	// 初始化收费单位与默认收费金额
	@Command("initrow")
	public void initrow(@BindingParam("self") Row row) throws Exception {
		Checkbox ckb = (Checkbox) row.getChildren().get(1).getChildren().get(0);
		List<CoProductModel> list = bll.getSingleEclassBycoprid(copr_id,
				Integer.parseInt(ckb.getValue().toString()));
		if (list.size() > 0) {
			/*
			 * ckb.setChecked(true); Doublebox db = (Doublebox)
			 * row.getChildren().get(2).getChildren() .get(0); Checkbox ckb1 =
			 * (Checkbox) row.getChildren().get(1).getChildren() .get(0);
			 * db.setValue(((CoProductModel)
			 * list.get(0)).getFee().doubleValue()); if (((CoProductModel)
			 * list.get(0)).getCpfr_lock() == 1) { ckb1.setChecked(true); } else
			 * { ckb1.setChecked(false); }
			 */
		}
	}

	// 提交修改
	@Command
	public void cpdMod(){
		boolean flag =false;
		
		if (coagModel==null) {
			Messagebox.show("请选择机构!", "操作提示",
					Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		// 获取享受方式
		Set cpst_nameSet = cpst_name.getSelectedObjects();
		String cpst_nameStr = ply.SetToString(cpst_nameSet);

		// 获取适用地
		// Set citySet = city.getSelectedObjects();
		// String cityStr = ply.SetToString(citySet);

		// 传值到CoProductModel
		cop.setCopr_id(copr_id);
		cop.setCopr_content(copr_content.getValue());
		cop.setCpst_name(cpst_nameStr);
		// cop.setCity(cityStr);
		cop.setCopr_cabc_id(coagModel.getCabc_id());
		cop.setCopr_addname(user.getUsername());
		for (CoProductModel cm : eclasslist) {
			if (cm.isIfU()) {
				flag=true;
			}
		}
		if (!flag) {
			Messagebox.show("请选择默认收费单位!", "操作提示",
					Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							Integer row = 0;
			
							// 调用bll的方法进行新增,返回行数
							cop = bll.cpdmod(cop);
							row = cop.getRow();
							if (row > 0) {
								// 删除费用关联
								bll.delFeeRelation(copr_id);
								row = 0;
								for (CoProductModel cm : eclasslist) {
									if (cm.isIfU()) {
										row=0;
										cop.setCopr_id(copr_id);
										cop.setCpfc_id(cm.getCpfc_id());
										cop.setFee(cm.getFee());
										cop.setCpfr_lock(cm.isCpfr_lock1() ? 1
												: 0);
										cop.setCopr_addname(user.getUsername());
										row = bll1.insertfee(cop);
										if (row<=0) {
											Messagebox.show("默认金额添加失败!", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
											return;
										}
									}
								}
								if (row>0) {
									Messagebox.show("修改成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									mywin.detach();
								}
							}else {
								Messagebox.show("修改失败!", "操作提示",
										Messagebox.OK,
										Messagebox.ERROR);
								return;
							}

							/*
							 * for (int i = 0; i < ply.getGridCount(gd); i++) {
							 * Cell cell = (Cell) gd.getCell(i, 1); Grid gd1 =
							 * (Grid) cell.getChildren().get(0);
							 * 
							 * cop.setCity(((Label) gd.getCell(i, 0)
							 * .getChildren().get(0)).getValue());
							 * cop.setCopr_addname(user.getUsername());
							 * cop.setCopr_id(copr_id); for (int j = 0; j <
							 * ply.getGridCount(gd1); j++) { Checkbox ckb =
							 * (Checkbox) gd1.getCell(j, 0)
							 * .getChildren().get(0); if (ckb.isChecked()) {
							 * 
							 * // 收费单位id cop.setCpfc_id(Integer.parseInt(ckb
							 * .getValue().toString())); // 默认金额 Doublebox db =
							 * (Doublebox) gd1 .getCell(j, 2).getChildren()
							 * .get(0); BigDecimal fee = BigDecimal.valueOf(db
							 * .getValue() == null ? 0 : db .getValue());
							 * cop.setFee(fee); Checkbox ckb1 = (Checkbox) gd1
							 * .getCell(j, 1).getChildren() .get(0); if
							 * (ckb1.isChecked()) { cop.setCpfr_lock(1); } else
							 * { cop.setCpfr_lock(0); } row +=
							 * bll1.insertfee(cop); flag = 1; } } }
							 */
						}
					}
				});
	}

	public ListModelList<String> getStandardlist() {
		return standardlist;
	}

	public void setStandardlist(ListModelList<String> standardlist) {
		this.standardlist = standardlist;
	}

	public ListModelList<String> getStandardlist1() {
		return standardlist1;
	}

	public void setStandardlist1(ListModelList<String> standardlist1) {
		this.standardlist1 = standardlist1;
	}

	public ListModelList<CoProductModel> getEclasslist() {
		return eclasslist;
	}

	public void setEclasslist(ListModelList<CoProductModel> eclasslist) {
		this.eclasslist = eclasslist;
	}

	public ListModelList<CoProductModel> getEclasslist1() {
		return eclasslist1;
	}

	public void setEclasslist1(ListModelList<CoProductModel> eclasslist1) {
		this.eclasslist1 = eclasslist1;
	}

	public String getCityString() {
		return cityString;
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
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
}
