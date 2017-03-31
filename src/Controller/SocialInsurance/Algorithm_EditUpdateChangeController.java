package Controller.SocialInsurance;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;
import Model.SocialInsuranceDecimalModel;
import Model.SocialInsuranceStandardModel;
import Util.UserInfo;
import bll.SocialInsurance.AlgorithmChangeBll;
import bll.SocialInsurance.Algorithm_ChangeOperateBll;
import bll.SocialInsurance.Algorithm_InfoBll;

public class Algorithm_EditUpdateChangeController extends
		SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	private final int sich_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("sich_id").toString());
	private final boolean submitVis = Boolean.valueOf(Executions.getCurrent()
			.getArg().get("submitVis").toString());
	private ListModelList<SocialInsuranceStandardModel> sbStandardList;
	private ListModelList<SocialInsuranceStandardModel> gjjStandardList;
	private ListModelList<SocialInsuranceDecimalModel> decimalList;
	private SocialInsuranceAlgorithmViewModel saModel;
	private ListModelList<SocialInsuranceClassInfoViewModel> classList;

	private Algorithm_InfoBll bll;
	private AlgorithmChangeBll chbll;
	@Wire
	private Window winEditUpAlg;
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

	public Algorithm_EditUpdateChangeController() {
		bll = new Algorithm_InfoBll();
		chbll = new AlgorithmChangeBll();
		saModel = chbll.getSiAlChange(sich_id);
		classList = new ListModelList<SocialInsuranceClassInfoViewModel>(
				chbll.getSiAlInfoChange(saModel.getSial_id()));
		setSbStandardList();
		setGjjStandardList();
		setDecimalList();
		getOldAlgorithm();
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		gdInfo.setModel(classList);
	}

	// 更新算法
	@Listen("onClick = #btSubmit")
	public void addAl() {
		Algorithm_ChangeOperateBll opbll = new Algorithm_ChangeOperateBll();
		if (dbExecDate.getValue() != null) {
			try {
				SocialInsuranceAlgorithmViewModel m = getBaseData();
				List<SocialInsuranceClassInfoViewModel> list = getInfoData();
				if (list != null) {
					// 调用新增方法
					String[] message = opbll.upAlgorithmChange(m, list);
					if (message[0].equals("1")) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						winEditUpAlg.detach();

					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
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
		m.setSial_id(saModel.getSial_id());
		m.setSoin_cabc_id(saModel.getSoin_cabc_id());
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
			// 设为0用于更新,在存储过程获取该值
			m.setSiai_side_id(0);
			m.setSide_decimal(((Combobox) comp.getChildren().get(childSize - 2)
					.getChildren().get(0)).getValue());
			m.setSiai_remark(((Textbox) comp.getChildren().get(childSize - 1)
					.getChildren().get(0)).getValue());
			list.add(m);
		}
		return list;
	}

	// 获取更新前的算法信息
	private void getOldAlgorithm() {
		try {
			if (saModel.getSich_change_type() == 2
					|| saModel.getSich_change_type() == 3) {
				Algorithm_InfoBll fuckbll = new Algorithm_InfoBll();
				saModel.setOm(fuckbll.getSiAlBase(saModel.getSich_sial_id()));
				for (SocialInsuranceClassInfoViewModel m : classList) {
					m.setOm(chbll.getOldSiAlInfoChange(
							saModel.getSich_sial_id(), m.getSicl_id()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListModelList<SocialInsuranceClassInfoViewModel> getClassList() {
		return classList;
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

	public boolean isSubmitVis() {
		return submitVis;
	}

	public int getSich_id() {
		return sich_id;
	}

}
