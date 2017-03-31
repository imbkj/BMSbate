package Controller.EmSheBao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.EmShebaoFormulaModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Model.Emcontactinfo;
import Util.DateStringChange;
import Util.IdcardUtil;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;
import bll.Embase.Emba_Contactbll;
import bll.Embase.EmbaseLogin_AddBll;
import Model.emmonthModel;

public class Emshebao_NewinstepController {
	
	private emmonthModel emmonthmodel =new emmonthModel();
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());

	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("embaId").toString());
	


	private Emsi_SelBll bll = new Emsi_SelBll();
	private List<String> folkList;
	private List<EmShebaoFormulaModel> formulaList;
	private EmShebaoFormulaModel formulaModel;
	private String[] ownmonthList;
	private EmbaseModel emModel;
	private boolean ifStop;
	private boolean existsShebao = false;
	private String existsMessage;
	private Emcontactinfo emcont = new Emcontactinfo();
	private Emba_Contactbll ecbll = new Emba_Contactbll();
	// 页面获取值
	private EmShebaoFormulaModel formula;
	private String ownmonth;
	private int radix;
	private String worker;
	private String folk;
	private String hand;
	private String mobile;
	private String remark;
	EmbaseLogin_AddBll embll = new EmbaseLogin_AddBll();
	EmbaseModel emM = new EmbaseModel();
	EmbaseLogin_AddBll sbbll = new EmbaseLogin_AddBll();
	private String emba_turename;
	private String emba_tureidcard;

	public Emshebao_NewinstepController() {
		
		if (Executions.getCurrent().getArg().get("emmonthmodel") != null) {
			emmonthmodel = (emmonthModel) Executions.getCurrent().getArg()
					.get("emmonthmodel");
		}
		
		emM = embll.embaseinfo(id).get(0);

		if (checkInit())
			return;
		folkList = bll.getFolk();
		formulaList = bll.getFormula(emM.getEmba_formula());

		formulaModel = formulaList.get(0);
		formula = formulaList.get(0);
		ifStop = bll.ifStop();
		 
		if (emmonthmodel!=null)
		{
			ownmonth=emmonthmodel.getEmba_emsb_ownmonth().toString();
		}
		// 判断是否停止当月操作社保
		if (ifStop) {
			 
			ownmonthList = bll.getOwnmonthByNow(false);
			
		} else {
			 
			ownmonthList = bll.getOwnmonthByNow(true);
		}
		emModel = bll.getEmBase(gid);
		mobile = emModel.getEmba_mobile();
		folk = emModel.getEmba_folk();
		
	 
	 

		//ownmonth = ownmonthList[0];

		radix = (int) emM.getEmba_sb_radix().doubleValue();

		emcont = ecbll.getemcontactmodel(gid);
		
		if (!"".equals(emM.getEmba_hand()))
			
				{
				hand=emM.getEmba_hand();
				}
		if (!"".equals(emM.getEmba_computerid()))
			
		{
		hand=emM.getEmba_hand();
		}
		

		// 判断是否有退回修改后的新身份证和姓名
		if (!"".equals(emModel.getEmba_sbuname())) {
			emba_turename = emModel.getEmba_sbuname();
		} else {
			emba_turename = emModel.getEmba_name();
		}

		if (!"".equals(emModel.getEmba_sbidcard())) {
			emba_tureidcard = emModel.getEmba_sbidcard();
		} else {
			emba_tureidcard = emModel.getEmba_idcard();
		}

		if (emcont.getEmzt_folk() != null && !"".equals(emcont.getEmzt_folk())) {
			folk = emcont.getEmzt_folk();
		}
		if (emcont.getEmzt_hand() != null && !"".equals(emcont.getEmzt_hand())) {
			hand = emcont.getEmzt_hand();
		}
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
		return false;
	}

	// 获取embase的预填写信息
	private void loaddate(int gid) {
		// "非深户参加一档医疗"
		formulaList = bll.getFormula(0);

	}

	@Command
	public void computerid_search() {
		try {
			if (emM.getEmba_name() == null || emM.getEmba_name().isEmpty()) {
				Messagebox.show("请输入姓名!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (emM.getEmba_idcard() == null
					|| emM.getEmba_idcard().isEmpty()) {
				Messagebox.show("请输入身份证号码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (!IdcardUtil.validateCard(emM.getEmba_idcard())) {
				Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String url = "/Embase/Embase_Computerid_search.zul";
				String searurl = "http://dgciic:81/ComputeridSearch.aspx?idcard="
						+ emM.getEmba_idcard();
				Map<String, Object> map = new HashMap<>();
				map.put("url", searurl);

				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
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
			String nowMonth = DateStringChange.DatetoSting(new Date(), "yyyyMM");
			
			if (ifStop) {
				 if(emmonthmodel!=null && emmonthmodel.getEmba_emsb_ownmonth()<=Integer.parseInt(nowMonth))
				 {
					 Messagebox.show("已过社保的截单日，请退单或等待后道开放操作。","提示" , Messagebox.OK, Messagebox.ERROR);
					 return;
				 }
				
			} 
			else if(emmonthmodel!=null && emmonthmodel.getEmba_emsb_ownmonth()<sbbll.getSbUpdateOwnmonth())
			{
				 
				 Messagebox.show("已过社保的截单日，请退单或等待后道开放操作。","提示" , Messagebox.OK, Messagebox.ERROR);
				 return; 
			} 
			if (checkPage()) {
				EmShebaoUpdateModel m = new EmShebaoUpdateModel();
				m.setGid(gid);
				// m.setEsiu_name(emModel.getEmba_name());
				m.setEsiu_name(emba_turename);
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
				m.setEsiu_remark(remark);
				m.setEsiu_addname(UserInfo.getUsername());
				m.setEsiu_computerid(emM.getEmba_computerid());
				m.setIfdeclare(Integer.parseInt(rg.getSelectedItem().getValue()
						.toString()));

				// 调用新增方法
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message;

				if ("".equals(m.getEsiu_computerid())
						|| m.getEsiu_computerid() == null) {
					message = opbll.newin(m);
				} else {
					message = opbll.movein(m);
				}

				if (message[0].equals("1")) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					RedirectUtil util = new RedirectUtil();
					util.refreshEntryThirdUrl("/EmSheBao/Emsi_index.zul");

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
		} else if ((emM.getEmba_computerid() == null || "".equals(emM
				.getEmba_computerid())) && worker == null) {
			b = false;
			Messagebox.show("请选择职工性质!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if ((emM.getEmba_computerid() == null || "".equals(emM
				.getEmba_computerid())) && folk == null) {
			b = false;
			Messagebox.show("请选择民族!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if ((emM.getEmba_computerid() == null || "".equals(emM
				.getEmba_computerid())) && hand == null) {
			b = false;
			Messagebox.show("请选择利手!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if ((emM.getEmba_computerid() == null || "".equals(emM
				.getEmba_computerid())) && mobile == null) {
			b = false;
			Messagebox
					.show("请输入参保人手机!", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
		return b;
	}

	public String getEmba_tureidcard() {
		return emba_tureidcard;
	}

	public void setEmba_tureidcard(String emba_tureidcard) {
		this.emba_tureidcard = emba_tureidcard;
	}

	public String getEmba_turename() {
		return emba_turename;
	}

	public void setEmba_turename(String emba_turename) {
		this.emba_turename = emba_turename;
	}

	public EmbaseModel getEmM() {
		return emM;
	}

	public void setEmM(EmbaseModel emM) {
		this.emM = emM;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public boolean isExistsShebao() {
		return existsShebao;
	}

	public String getExistsMessage() {
		return existsMessage;
	}
}
