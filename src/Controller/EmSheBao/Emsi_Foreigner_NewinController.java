package Controller.EmSheBao;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;
import Model.EmShebaoFormulaModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_Foreigner_NewinController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private List<String> folkList;
	private List<EmShebaoFormulaModel> formulaList;
	private EmShebaoFormulaModel formulaModel;
	private String[] ownmonthList;
	private boolean ifaud;
	private EmbaseModel emModel;
	private boolean ifStop;
	private boolean existsShebao = false;
	private String existsMessage;
	// 页面获取值
	private EmShebaoFormulaModel formula;
	private String ownmonth;
	private int radix;
	private String officialrank;
	private String worker;
	private String folk;
	private String hand;
	private String mobile;
	private String emsc_s8;

	public Emsi_Foreigner_NewinController() {
		if (checkInit())
			return;
		folkList = bll.getFolk();
		formulaList = bll.getFormula(1);
		ifStop = bll.ifStop();
		// 判断是否停止当月操作社保
		if (ifStop) {
			ownmonthList = bll.getOwnmonthByNow(false);
		} else {
			ownmonthList = bll.getOwnmonthByNow(true);
		}
		emModel = bll.getEmBase(gid);
		mobile = emModel.getEmba_mobile();
		folk = "汉族";

	}

	// 初始化检查
	private boolean checkInit() {
		if (bll.existsShebao(gid)) {
			existsShebao = true;
			existsMessage = "已有该员工社保信息，不能新增!";
			return existsShebao;
		}
		if (!bll.existsCoOfferList(gid)) {
			existsShebao = true;
			existsMessage = "该员工未分配“社会保险服务”的报价单项目，请分配该项目后再操作此步骤！";
			return existsShebao;
		}
		if (!bll.existsForeigner(gid)) {
			existsShebao = true;
			existsMessage = "该员工非外籍人，不能做外籍人新增!";
			return existsShebao;
		}
		return false;
	}

	// 模板选择
	@Command("selFormula")
	@NotifyChange("formulaModel")
	public void selFormula(@BindingParam("mod") Combobox cb) {
		if (cb.getSelectedItem() != null) {
			formulaModel = cb.getSelectedItem().getValue();
		}
	}

	// 提交
	@Command("newin")
	public void newin(@BindingParam("win") Window win,
			@BindingParam("rg") Radiogroup rg) {
		try {
			if (checkPage()) {
				EmShebaoUpdateModel m = new EmShebaoUpdateModel();
				m.setGid(gid);
				m.setEsiu_name(emModel.getEmba_name());
				m.setFormulaid(formula.getId());
				m.setEsiu_yl(formula.getEmsf_yl());
				m.setEsiu_gs(formula.getEmsf_gs());
				m.setEsiu_sye(formula.getEmsf_sye());
				m.setEsiu_syu(formula.getEmsf_syu());
				m.setEsiu_yltype(formula.getEmsf_yltype());
				m.setEmsf_soin_title(formula.getEmsf_soin_title());
				m.setOwnmonth(Integer.parseInt(ownmonth));
				m.setEsiu_radix(radix);
				m.setFolk(folk);
				m.setWorker(worker);
				m.setHand(hand);
				m.setMobile(mobile);
				m.setEsiu_officialrank(officialrank);
				m.setEsiu_addname(UserInfo.getUsername());
				m.setIfdeclare(Integer.parseInt(rg.getSelectedItem().getValue()
						.toString()));

				// 调用新增方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message = opbll.newinForeigner(m);
				if (message[0].equals("1")) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
					RedirectUtil util = new RedirectUtil();
					util.refreshEmUrl("/EmSheBao/Emsi_index.zul");// url为跳转页面连接
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			Messagebox.show("新增社保出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检测表单
	private boolean checkPage() {
		boolean b = true;
		if (formula == null) {
			b = false;
			Messagebox
					.show("请先选择社保模板!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (ownmonth == null) {
			b = false;
			Messagebox
					.show("请选择服务起始月!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (radix == 0) {
			b = false;
			Messagebox
					.show("请输入月工资总额!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (radix > 99999) {
			b = false;
			Messagebox.show("月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!", "操作提示",
					Messagebox.OK, Messagebox.NONE);
		} else if (worker == null) {
			b = false;
			Messagebox.show("请选择职工性质!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (officialrank == null) {
			b = false;
			Messagebox.show("请选择职别!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (folk == null) {
			b = false;
			Messagebox.show("请选择民族!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (hand == null) {
			b = false;
			Messagebox.show("请选择利手!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (mobile == null) {
			b = false;
			Messagebox
					.show("请输入参保人手机!", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
		return b;
	}

	public int getGid() {
		return gid;
	}

	public List<String> getFolkList() {
		return folkList;
	}

	public List<EmShebaoFormulaModel> getFormulaList() {
		return formulaList;
	}

	public EmShebaoFormulaModel getFormulaModel() {
		return formulaModel;
	}

	public String[] getOwnmonthList() {
		return ownmonthList;
	}

	public boolean isIfaud() {
		return ifaud;
	}

	public EmbaseModel getEmModel() {
		return emModel;
	}

	public EmShebaoFormulaModel getFormula() {
		return formula;
	}

	public void setFormula(EmShebaoFormulaModel formula) {
		this.formula = formula;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getRadix() {
		return radix;
	}

	public void setRadix(int radix) {
		this.radix = radix;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getFolk() {
		return folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public String getOfficialrank() {
		return officialrank;
	}

	public void setOfficialrank(String officialrank) {
		this.officialrank = officialrank;
	}

	public String getEmsc_s8() {
		return emsc_s8;
	}

	public void setEmsc_s8(String emsc_s8) {
		this.emsc_s8 = emsc_s8;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}

}
