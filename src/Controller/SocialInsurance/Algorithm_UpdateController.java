package Controller.SocialInsurance;

import impl.PubCityImpl;

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
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;
import Model.SocialInsuranceDecimalModel;

import Model.SocialInsuranceStandardModel;
import Util.UserInfo;
import bll.SocialInsurance.Algorithm_InfoBll;
import bll.SocialInsurance.Algorithm_OperateBll;

public class Algorithm_UpdateController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private final int sial_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("sial_id").toString());
	private ListModelList<SocialInsuranceStandardModel> sbStandardList;
	private ListModelList<SocialInsuranceStandardModel> gjjStandardList;
	private ListModelList<SocialInsuranceDecimalModel> decimalList;
	private SocialInsuranceAlgorithmViewModel saModel;
	private ListModelList<SocialInsuranceClassInfoViewModel> classList;
	private Algorithm_InfoBll bll;
	private int admin = 0;

	@Wire
	private Window winUpAlg;
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
	private Listbox docGrid;

	public Algorithm_UpdateController() {
		bll = new Algorithm_InfoBll();
		setClassList();
		setSaModel();
		setSbStandardList();
		setGjjStandardList();
		setDecimalList();
		testAdmin();

		Map map = new HashMap<>();
		map.put("pono_city", saModel.getCity());// 城市
		map.put("pono_agencies ", saModel.getCoab_name()); // 机构名称
		BindUtils.postGlobalCommand(null, null, "refreshlist", map);
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		gdInfo.setModel(classList);
	}

	// 更新算法
	@Listen("onClick = #btSubmit")
	public void upAl() {
		Algorithm_OperateBll bll = new Algorithm_OperateBll();
		if (dbExecDate.getValue() != null) {
			if (bll.existExecdateExceptSial(saModel.getSoin_id(),
					dbExecDate.getValue(), sial_id)) {
				if (bll.existChangeExecdate(saModel.getSoin_id(),
						dbExecDate.getValue())) {
					try {
						SocialInsuranceAlgorithmViewModel m = getBaseData();
						List<SocialInsuranceClassInfoViewModel> list = getInfoData();
						if (list != null) {

							// 调用更新方法
							String[] message = bll.upAlgorithmChangeToTask(m,
									list);
							if (message[0].equals("1")) {
								// 插入政策信息
								Pono_PubInfoAddController pd = new Pono_PubInfoAddController();
								pd.InfoAdd(docGrid,
										Integer.parseInt(message[3]), "字典库新增");
								// 弹出提示
								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.NONE);
								winUpAlg.detach();

							} else {
								// 弹出提示
								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("更新出错。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("该标准已存在相同开始执行年月的算法变更正在审核中，不可重复添加。", "操作提示",
							Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("该标准已存在相同开始执行年月的算法，无法再添加该月份的算法。", "操作提示",
						Messagebox.OK, Messagebox.INFORMATION);
			}
		} else {
			Messagebox.show("请输入开始执行年月。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	// 政策通知新增
	@Listen("onClick = #zcadd")
	public void zcadd() {
		CoAgencyBaseCityRelModel model = new CoAgencyBaseCityRelModel();
		PubCityImpl cityimpl = new PubCityImpl();
		Integer cityid = cityimpl.getCityId(model.getCity());
		model.setId(cityid);
		model.setCity(saModel.getCity());
		Map map = new HashMap();
		map.put("model", model);
		map.put("type", "政府通知");
		map.put("classname", model.getCity());
		Window window = (Window) Executions.createComponents(
				"/CoPolicyNotice/PoNo_InfoAdd.zul", null, map);
		window.doModal();

		Map map1 = new HashMap<>();
		map1.put("pono_city", saModel.getCity());// 城市
		map1.put("pono_agencies ", saModel.getCoab_name()); // 机构名称
		BindUtils.postGlobalCommand(null, null, "refreshlist", map1);

	}

	// 直接更新算法(隐藏功能)
	@Listen("onClick = #btSubmitOld")
	public void addAl() {
		Algorithm_OperateBll bll = new Algorithm_OperateBll();
		if (dbExecDate.getValue() != null) {
			try {
				SocialInsuranceAlgorithmViewModel m = getBaseData();
				List<SocialInsuranceClassInfoViewModel> list = getInfoData();

				// 调用新增方法
				String[] message = bll.upAlgorithm(m, list);
				if (message[0].equals("1")) {

					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					winUpAlg.detach();

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("更新出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请输入开始执行年月。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	// 获取基本信息
	private SocialInsuranceAlgorithmViewModel getBaseData() {
		String username = UserInfo.getUsername();
		SocialInsuranceAlgorithmViewModel m = new SocialInsuranceAlgorithmViewModel();
		m.setSoin_id(saModel.getSoin_id());
		m.setSial_id(sial_id);
		m.setCity(saModel.getCity());
		m.setCoab_name(saModel.getCoab_name());
		m.setSoin_cabc_id(saModel.getSoin_cabc_id());
		m.setSoin_title(saModel.getSoin_title());
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
		for (Object obj : row) {
			comp = (Row) obj;
			childSize = comp.getChildren().size();
			SocialInsuranceClassInfoViewModel m = new SocialInsuranceClassInfoViewModel();
			m.setSiai_id(((SocialInsuranceClassInfoViewModel) comp.getValue())
					.getSiai_id());
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
			// 设为0用于更新时新增,在存储过程获取该值
			m.setSiai_side_id(0);
			m.setSide_decimal(((Combobox) comp.getChildren().get(childSize - 2)
					.getChildren().get(0)).getValue());
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

	public int getAdmin() {
		return admin;
	}

	public ListModelList<SocialInsuranceClassInfoViewModel> getClassList() {
		return classList;
	}

	private void setClassList() {
		this.classList = new ListModelList<SocialInsuranceClassInfoViewModel>(
				bll.getSiAlInfo(sial_id));
	}

	public ListModelList<SocialInsuranceStandardModel> getSbStandardList() {
		return sbStandardList;
	}

	private void setSbStandardList() {
		this.sbStandardList = new ListModelList<SocialInsuranceStandardModel>(
				bll.getStandard(1));
		for (SocialInsuranceStandardModel m : sbStandardList) {
			if (m.getSist_id() == saModel.getSial_sb_standard()) {
				sbStandardList.addToSelection(m);
			}
		}
	}

	public ListModelList<SocialInsuranceStandardModel> getGjjStandardList() {
		return gjjStandardList;
	}

	private void setGjjStandardList() {
		this.gjjStandardList = new ListModelList<SocialInsuranceStandardModel>(
				bll.getStandard(2));
		for (SocialInsuranceStandardModel m : gjjStandardList) {
			if (m.getSist_id() == saModel.getSial_gjj_standard()) {
				gjjStandardList.addToSelection(m);
			}
		}
	}

	public ListModelList<SocialInsuranceDecimalModel> getDecimalList() {
		return decimalList;
	}

	private void setDecimalList() {
		this.decimalList = new ListModelList<SocialInsuranceDecimalModel>(
				bll.getDecimal());
	}

	public SocialInsuranceAlgorithmViewModel getSaModel() {
		return saModel;
	}

	private void setSaModel() {
		this.saModel = bll.getSiAlBase(sial_id);
	}

}
