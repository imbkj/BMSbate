package Controller.SocialInsurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Controller.CoPolicyNotice.Pono_PubInfoAddController;
import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseCityRelViewModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;
import Model.SocialInsuranceDecimalModel;
import Model.SocialInsuranceStandardModel;
import Util.UserInfo;
import bll.SocialInsurance.Algorithm_InfoBll;
import bll.SocialInsurance.Algorithm_OperateBll;

public class Algorithm_AddController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	private final int cabc_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cabc_id").toString());
	private ListModelList<SocialInsuranceClassInfoViewModel> classList;
	private ListModelList<SocialInsuranceDecimalModel> decimalList;
	private ListModelList<SocialInsuranceStandardModel> sbStandardList;
	private ListModelList<SocialInsuranceStandardModel> gjjStandardList;
	private CoAgencyBaseCityRelViewModel baseCityModel;
	private Algorithm_InfoBll bll;
	private int admin = 0;

	@Wire
	private Window winAddAlg;
	@Wire
	private Grid gdInfo;
	@Wire
	private Textbox txtTitle;
	@Wire
	private Datebox dbExecDate;
	@Wire
	private Combobox cbSbStandard;
	@Wire
	private Combobox cbGjjStandard;
	@Wire
	private Textbox txtStandard;
	@Wire
	private Decimalbox dbAvgSalary;
	@Wire
	private Decimalbox dbLowestSalary;
	@Wire
	private Textbox txtCityRemark;
	@Wire
	private Textbox txtSbRemark;
	@Wire
	private Textbox txtGjjRemark;
	@Wire
	private Textbox txtCbRemark;
	@Wire 
	private Label zcadd;
	@Wire 
	private Label zcbrow;
	@Wire
	private Listbox docGrid;
	

	public Algorithm_AddController() {
		bll = new Algorithm_InfoBll();
		setClassList();
		setBaseCityModel();
		setDecimalList();
		setSbStandardList();
		setGjjStandardList();
		testAdmin();
		
		Map map = new HashMap<>();
		map.put("pono_city", baseCityModel.getCity());// 城市
		map.put("pono_agencies ", baseCityModel.getCoab_name()); // 机构名称
		BindUtils.postGlobalCommand(null, null, "refreshlist", map);

	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		gdInfo.setModel(classList);
	}

	// 新增
	@Listen("onClick = #btSubmit")
	public void add() {
		Algorithm_OperateBll bll = new Algorithm_OperateBll();
		if (txtTitle.getValue() != null) {
			if (dbExecDate.getValue() != null) {
				if (bll.existAlName(cabc_id, txtTitle.getValue())) {
					if (bll.existChangeAlName(cabc_id, txtTitle.getValue())) {
						try {
							SocialInsuranceAlgorithmViewModel m = getBaseData();
							List<SocialInsuranceClassInfoViewModel> list = getInfoData();

							// 调用新增方法
							String[] message = bll.addAlgorithmChangeToTask(m,
									list);
							
							if ("1".equals(message[0])) {
								//插入政策信息	
								Pono_PubInfoAddController pd = new Pono_PubInfoAddController();
								pd.InfoAdd(docGrid, Integer.parseInt(message[3]), "字典库新增");
								// 弹出提示
								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.NONE);
								winAddAlg.detach();

							} else {
								// 弹出提示
								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.ERROR);
							}
						} catch (Exception e) {
							e.printStackTrace();
							Messagebox.show("新增出错。", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show("已有该算法正在审核中，不可重复添加。", "操作提示",
								Messagebox.OK, Messagebox.INFORMATION);
					}
				} else {
					Messagebox.show("该算法描述已存在，不可重复添加。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("请输入开始执行年月。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} else {
			Messagebox.show("请输入算法描述。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}
	
	// 政策通知新增
	@Listen("onClick = #zcadd")
	public void zcadd() {
		CoAgencyBaseCityRelModel model=new CoAgencyBaseCityRelModel();
		
		model.setId(baseCityModel.getCoab_id());
		model.setCity(baseCityModel.getCity());
		Map map=new HashMap();
		map.put("model", model);
		map.put("type", "政府通知");
		map.put("classname", model.getCity());
		Window window=(Window)Executions.createComponents("/CoPolicyNotice/PoNo_InfoAdd.zul", null, map);
		window.doModal();
		
		Map map1 = new HashMap<>();
		map1.put("pono_city", baseCityModel.getCity());// 城市
		map1.put("pono_agencies ", baseCityModel.getCoab_name()); // 机构名称
		BindUtils.postGlobalCommand(null, null, "refreshlist", map1);
		
	}
	
	
	// 政策通知新增
	@Listen("onClick = #zcbrow")
	public void zcbrow() {
		
		
	}
	
	

	// 直接新增(隐藏功能)
	@Listen("onClick = #btSubmitOld")
	public void addAl() {
		Algorithm_OperateBll bll = new Algorithm_OperateBll();
		if (txtTitle.getValue() != null) {
			if (dbExecDate.getValue() != null) {
				if (bll.existAlName(cabc_id, txtTitle.getValue())) {
					if (bll.existChangeAlName(cabc_id, txtTitle.getValue())) {
						try {
							SocialInsuranceAlgorithmViewModel m = getBaseData();
							List<SocialInsuranceClassInfoViewModel> list = getInfoData();
							if (list != null) {
								// 调用新增方法
								String[] message = bll.addAlgorithm(m, list);
							
								
								if (message[0].equals("1")) {
									// 弹出提示
									Messagebox.show(message[1], "操作提示",
											Messagebox.OK, Messagebox.NONE);
									winAddAlg.detach();

								} else {
									// 弹出提示
									Messagebox.show(message[1], "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						} catch (Exception e) {
							Messagebox.show("新增出错。", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show("已有该算法正在审核中，不可重复添加。", "操作提示",
								Messagebox.OK, Messagebox.INFORMATION);
					}
				} else {
					Messagebox.show("该算法描述已存在，不可重复添加。", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("请输入开始执行年月。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} else {
			Messagebox.show("请输入算法描述。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}

	}

	// 获取基本信息
	private SocialInsuranceAlgorithmViewModel getBaseData() {
		String username = UserInfo.getUsername();
		SocialInsuranceAlgorithmViewModel m = new SocialInsuranceAlgorithmViewModel();
		m.setCity(baseCityModel.getCity());
		m.setCoab_name(baseCityModel.getCoab_name());
		m.setSoin_cabc_id(cabc_id);
		m.setSoin_title(txtTitle.getValue());
		m.setSial_execdate(dbExecDate.getValue());
		m.setSial_standard(txtStandard.getValue());
		if (cbSbStandard.getValue() == null) {
			m.setSial_sb_standard(1);
		} else {
			m.setSial_sb_standard((Integer) cbSbStandard.getSelectedItem()
					.getValue());
		}
		if (cbGjjStandard.getValue() == null) {
			m.setSial_gjj_standard(1);
		} else {
			m.setSial_gjj_standard((Integer) cbGjjStandard.getSelectedItem()
					.getValue());
		}
		m.setSial_avg_salary(dbAvgSalary.getValue());
		m.setSial_lowest_salary(dbLowestSalary.getValue());
		m.setSial_city_remark(txtCityRemark.getValue());
		m.setSial_sb_remark(txtSbRemark.getValue());
		m.setSial_gjj_remark(txtGjjRemark.getValue());
		m.setSial_cb_remark(txtCbRemark.getValue());
		m.setSial_addname(username);
		return m;
	}

	// 获取项目信息列表
	private List<SocialInsuranceClassInfoViewModel> getInfoData() {
		List<SocialInsuranceClassInfoViewModel> list = new ArrayList<SocialInsuranceClassInfoViewModel>();
		List<Component> row = gdInfo.getRows().getChildren();
		int childSize;
		Row comp;
		Integer side_id;
		for (Object obj : row) {
			comp = (Row) obj;
			childSize = comp.getChildren().size();
			SocialInsuranceClassInfoViewModel m = new SocialInsuranceClassInfoViewModel();
			m.setSicl_id(((SocialInsuranceClassInfoViewModel) comp.getValue())
					.getSicl_id());
			m.setSiai_basic_dd(((Decimalbox) comp.getChildren()
					.get(childSize - 8).getChildren().get(0)).getValue());
			m.setSiai_basic_ud(((Decimalbox) comp.getChildren()
					.get(childSize - 6).getChildren().get(0)).getValue());
			m.setSiai_deposit_dd(((Decimalbox) comp.getChildren()
					.get(childSize - 7).getChildren().get(0)).getValue());
			m.setSiai_deposit_ud(((Decimalbox) comp.getChildren()
					.get(childSize - 5).getChildren().get(0)).getValue());
			try {
				m.setSiai_proportion(((Textbox) comp.getChildren()
						.get(childSize - 4).getChildren().get(0)).getValue());
			} catch (Exception e) {
				((Textbox) comp.getChildren().get(childSize - 4).getChildren()
						.get(0)).focus();
				return null;
			}
			m.setSiai_algorithm(((Textbox) comp.getChildren()
					.get(childSize - 3).getChildren().get(0)).getValue());

			side_id = ((Combobox) comp.getChildren().get(childSize - 2)
					.getChildren().get(0)).getSelectedItem().getValue();
			if (side_id == null) {
				m.setSiai_side_id(1);
			} else {
				m.setSiai_side_id(side_id);
			}
			m.setSiai_remark(((Textbox) comp.getChildren().get(childSize - 1)
					.getChildren().get(0)).getValue());
			list.add(m);
		}
		return list;
	}

	// 测试权限
	private void testAdmin() {
		String username = UserInfo.getUsername();
		if ("李文洁".equals(username)) {
			this.admin = 1;
		}
	}

	public ListModelList<SocialInsuranceClassInfoViewModel> getClassList() {
		return classList;
	}

	private void setClassList() {
		this.classList = new ListModelList<SocialInsuranceClassInfoViewModel>(
				bll.getSiClass(cabc_id));
	}

	public ListModelList<SocialInsuranceDecimalModel> getDecimalList() {
		return decimalList;
	}

	private void setDecimalList() {
		this.decimalList = new ListModelList<SocialInsuranceDecimalModel>(
				bll.getDecimal());
	}

	public ListModelList<SocialInsuranceStandardModel> getSbStandardList() {
		return sbStandardList;
	}

	private void setSbStandardList() {
		this.sbStandardList = new ListModelList<SocialInsuranceStandardModel>(
				bll.getStandard(1));
	}

	public ListModelList<SocialInsuranceStandardModel> getGjjStandardList() {
		return gjjStandardList;
	}

	private void setGjjStandardList() {
		this.gjjStandardList = new ListModelList<SocialInsuranceStandardModel>(
				bll.getStandard(2));
	}

	public CoAgencyBaseCityRelViewModel getBaseCityModel() {
		return baseCityModel;
	}

	private void setBaseCityModel() {
		this.baseCityModel = bll.getBaseCityById(cabc_id);
	}

	public int getCabc_id() {
		return cabc_id;
	}

	public int getAdmin() {
		return admin;
	}

}
