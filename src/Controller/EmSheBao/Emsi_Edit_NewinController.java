package Controller.EmSheBao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import Model.EmSheBaoChangeModel;
import Model.EmShebaoFormulaModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseGDModel;
import Model.EmbaseModel;
import Util.IdcardUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;
import bll.Embase.EmBase_gdBll;
import bll.Taskflow.Task_controlBll;

public class Emsi_Edit_NewinController {
	private final int changeId = Integer.parseInt(Executions.getCurrent()
			.getArg().get("daid").toString());
	private Emsi_SelBll bll = new Emsi_SelBll();
	private List<String> folkList;
	private List<EmShebaoFormulaModel> formulaList;
	private String[] ownmonthList;
	private boolean ifaud=false;
	private EmSheBaoChangeModel ecModel;
	private EmbaseModel emModel;
	private boolean ifStop;
	// 页面获取值
	private EmShebaoFormulaModel formula;
	private String ownmonth;
	private int radix;
	private String worker;
	private String folk;
	private String hand;
	private String mobile;
	private String remark;
	private String computerid;
	private boolean aud=false;
	private EmbaseGDModel egm = new EmbaseGDModel();

	public Emsi_Edit_NewinController() {
		ecModel = bll.getChangById(changeId);
		folkList = bll.getFolk();
		formulaList = bll.getFormula(0);
		ifStop = bll.ifStop();
		// 判断是否停止当月操作社保
		if (ifStop) {
			ownmonthList = bll.getOwnmonthByNow(false);
		} else {
			ownmonthList = bll.getOwnmonthByNow(true);
		}
		//ifaud = bll.ifAud();
		emModel = bll.getEmBase(ecModel.getGid());
		mobile = emModel.getEmba_mobile();
		ownmonth = String.valueOf(ecModel.getOwnmonth());
		worker = ecModel.getEmsc_worker();
		radix = ecModel.getEmsc_radix();
		hand = ecModel.getEmsc_hand();
		remark = ecModel.getEmsc_remark();
		folk = ecModel.getEmsc_folk();
		computerid = ecModel.getEmsc_computerid();
		//aud = ifaud;
		for (EmShebaoFormulaModel m : formulaList) {
			if (m.getId() == Integer.parseInt(ecModel.getEmsc_formula())) {
				formula = m;
				continue;
			}
		}
	}

	// 检测社保电脑号
	@Command
	public void computerid_search() {
		try {
			if (emModel.getEmba_name() == null
					|| emModel.getEmba_name().isEmpty()) {
				Messagebox.show("请输入姓名!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (emModel.getEmba_idcard() == null
					|| emModel.getEmba_idcard().isEmpty()) {
				Messagebox.show("请输入身份证号码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (!IdcardUtil.validateCard(emModel.getEmba_idcard())) {
				Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String url = "/Embase/Embase_Computerid_search.zul";
				String searurl = "http://dgciic:81/ComputeridSearch.aspx?idcard="
						+ emModel.getEmba_idcard();
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
	@NotifyChange("formula")
	public void selFormula(@BindingParam("mod") Combobox cb) {
		if (cb.getSelectedItem() != null) {
			formula = cb.getSelectedItem().getValue();
		}
	}

	// 所属月份改变判断数据是否需要审核
	@Command("ownmonthChange")
	@NotifyChange("aud")
	public void ownmonthChange() {
		if (bll.ifaud(ownmonth)) {
			aud = false;
		} else {
			aud = ifaud;
		}
	}

	// 提交
	@Command("newin")
	public void newin(@BindingParam("win") Window win,
			@BindingParam("rg") Radiogroup rg) {
	
		
		try {
			if (checkPage()) {
				EmShebaoUpdateModel m = new EmShebaoUpdateModel();
				m.setGid(ecModel.getGid());
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
				m.setEsiu_remark(remark);
			
//				if (aud) {
//					m.setIfdeclare(5);
//				} else {
					if (Integer.parseInt(UserInfo.getDepID().toString())==16 || Integer.parseInt(UserInfo.getDepID().toString())==8) //雇员服务中心重新提交
					{
						m.setIfdeclare(Integer.parseInt(rg.getSelectedItem()
								.getValue().toString()));
						m.setEsiu_addname(UserInfo.getUsername());
					}
					else //客服重新提交
					{
						 
						m.setIfdeclare(4);
						//m.setEsiu_addname(ecModel.getEmsc_addname());
						m.setEsiu_addname(UserInfo.getUsername());
					}
					
//				}

				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] message;
				if ("".equals(computerid) || computerid == null
						|| "0".equals(computerid)) {
					// 调用新增方法
					message = opbll.editNewin(m, changeId,
							ecModel.getEmsc_tapr_id());
				} else {
					if (computerid.trim().length() < 7
							|| computerid.trim().length() > 9) {
						Messagebox.show("个人电脑号长度应为7或9位!", "操作提示",
								Messagebox.OK, Messagebox.NONE);
						return;
					}
					m.setEsiu_computerid(computerid.trim());
					message = opbll.editMovein(m, changeId,
							ecModel.getEmsc_tapr_id());
				}
				if (message[0].equals("1")) {
					
					//客服提交更改节点权限
					if (Integer.parseInt(UserInfo.getDepID().toString())!=16 && Integer.parseInt(UserInfo.getDepID().toString())!=8)
					{
						Task_controlBll tbll =new Task_controlBll();
						
						if (tbll.setOpName(ecModel.getEmsc_tapr_id(),ecModel.getEmsc_addname())==1)
						{
							//清除embasegd 退回状态
							EmBase_gdBll gdbll = new EmBase_gdBll();	
						 
							gdbll.modinfo(5,ecModel.getId(),"");
							 
							// 弹出提示
							Messagebox.show(message[1], "操作提示", Messagebox.OK,
									Messagebox.NONE);
						}
						else
						{
							Messagebox.show("权限异常，请联系IT部", "操作提示", Messagebox.OK,
									Messagebox.NONE);
						}
						 
					}
					win.detach();
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		} else if (!checkOwnmonth()) {
			b = false;
			Messagebox.show("当前所属月份，已无法申报，请重新选择所属月份!", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		} else if (radix == 0) {
			b = false;
			Messagebox
					.show("请输入月工资总额!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (radix > 99999) {
			b = false;
			Messagebox.show("月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!", "操作提示",
					Messagebox.OK, Messagebox.NONE);
		} else if ((ecModel.getEmsc_computerid() == null || "".equals(ecModel
				.getEmsc_computerid())) && worker == null) {
			b = false;
			Messagebox.show("请选择职工性质!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if ((ecModel.getEmsc_computerid() == null || "".equals(ecModel
				.getEmsc_computerid())) && folk == null) {
			b = false;
			Messagebox.show("请选择民族!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if ((ecModel.getEmsc_computerid() == null || "".equals(ecModel
				.getEmsc_computerid())) && hand == null) {
			b = false;
			Messagebox.show("请选择利手!", "操作提示", Messagebox.OK, Messagebox.NONE);
		}else if ((ecModel.getEmsc_computerid() == null || "".equals(ecModel
				.getEmsc_computerid())) && mobile == null) {
			b = false;
			Messagebox
					.show("请输入参保人手机!", "操作提示", Messagebox.OK, Messagebox.NONE);
		} else if (remark == null && aud) {
			b = false;
			Messagebox.show("该变更需审核，请填写申请原因!", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
		return b;
	}

	// 检测所属月份是否可提交
	private boolean checkOwnmonth() {
		try {
			for (String o : ownmonthList) {
				if (o.equals(ownmonth))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<String> getFolkList() {
		return folkList;
	}

	public List<EmShebaoFormulaModel> getFormulaList() {
		return formulaList;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isAud() {
		return aud;
	}

	public void setAud(boolean aud) {
		this.aud = aud;
	}

	public boolean isIfStop() {
		return ifStop;
	}

	public EmSheBaoChangeModel getEcModel() {
		return ecModel;
	}

	public String getComputerid() {
		return computerid;
	}

	public void setComputerid(String computerid) {
		this.computerid = computerid;
	}

}
