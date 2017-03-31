package Controller.Embase;

import impl.MessageImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.LoginDal;

import service.MessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.Embase.EmBase_OnBoardBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.Embase.Embase_OnBoardImpl;

import Model.CoBaseModel;
import Model.EmHouseCpp;
import Model.EmShebaoFormulaModel;
import Model.EmbaseModel;
import Model.Emcontactinfo;
import Model.SysMessageModel;
import Util.DateStringChange;
import Util.EmailUtil;
import Util.IdcardUtil;
import Util.TelUtil;
import Util.UserInfo;

public class Embase_confirmController {

	private Integer daid = 0;
	private Date birth = null;// 生日
	// 日期格式
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private List<String> folkList;
	private EmbaseModel ebm = new EmbaseModel();
	private EmShebaoFormulaModel formula;// 社保医疗类型信息
	private List<EmShebaoFormulaModel> formulaList;
	private List<EmHouseCpp> cpList;
	private List<EmbaseModel> emList = new ListModelList<>();
	private Integer emba_tapr_id;
	private Emcontactinfo emzt = new Emcontactinfo();
	private EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();

	private EmBase_OnBoardBll obll = new EmBase_OnBoardBll();

	private boolean gjj = false; // 返回是否有社保公积金
	private boolean sb = false;

	private Window ww;
	private String winId = "winAdd"
			+ java.util.concurrent.ThreadLocalRandom.current().nextLong(2000,
					2999);

	public Embase_confirmController() throws ParseException {
		if (Executions.getCurrent().getArg().get("daid") != null) {
			daid = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString()); // embaid
		} else if (Executions.getCurrent().getArg().get("ebm") != null) {
			EmbaseModel m = (EmbaseModel) Executions.getCurrent().getArg()
					.get("ebm");
			daid = m.getEmba_id();
		}

		setEmList(daid);

		if (emList.size() > 0) {
			ebm = emList.get(0);
			setEmzt(ebm.getGid());

			// 根据查询结果返回的行数确定是否显示
			int gjjCount = obll.isShowGjj(ebm.getGid());
			int sbCount = obll.isShowShebao(ebm.getGid());
			if (gjjCount > 0) {
				gjj = true;
			}
			if (sbCount > 0) {
				sb = true;
			}
			if ("是".equals(ebm.getEmba_emsb_foreigner())) {
				setFormulaList(1);
			} else {
				setFormulaList(0);
			}

			emba_tapr_id = emList.get(0).getEmba_tapr_id();
			ebm.setEmba_tapr_id(emba_tapr_id);
			Calendar calendar = new GregorianCalendar();
			Date nowDate = new Date(); // 获取当前时间
			calendar.setTime(nowDate);
			Integer nowmonth = Integer.valueOf(DateStringChange.DatetoSting(
					nowDate, "yyyyMM"));
			Integer ownmonth = bll.getownmonth2(emList.get(0).getGid());
			ownmonth = nowmonth > ownmonth ? nowmonth : ownmonth;
			setCpList(ebm.getCid(), ebm.getGid(), ownmonth);
			setFolkList();

			try {
				if (ebm.getEmba_birth() != null) {
					birth = sdf.parse(ebm.getEmba_birth());
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@Command
	public void WinD(@BindingParam("a") Window w) {
		ww = w;
	}

	// 模板选择
	@Command("selFormula")
	@NotifyChange("ebm")
	public void selFormula(@BindingParam("mod") Combobox cb) {
		if (cb.getSelectedItem() != null) {

			ebm.setEmba_sb_hj(((EmShebaoFormulaModel) cb.getSelectedItem()
					.getValue()).getEmsf_hj());

		}
	}

	@Command
	public void computerid_search() {
		try {
			if (ebm.getEmba_name() == null || ebm.getEmba_name().isEmpty()) {
				Messagebox.show("请输入姓名!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (ebm.getEmba_idcard() == null
					|| ebm.getEmba_idcard().isEmpty()) {
				Messagebox.show("请输入身份证号码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (!IdcardUtil.validateCard(ebm.getEmba_idcard())) {
				Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String url = "/Embase/Embase_Computerid_search.zul";
				String searurl = "http://dgciic:81/ComputeridSearch.aspx?idcard="
						+ ebm.getEmba_idcard();
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

	/**
	 * 检查邮箱真实性
	 * 
	 * @param email
	 */
	@Command
	public void checkEmail(@BindingParam("email") String email) {
		try {
			if (email != null && !email.isEmpty()) {
				if (!EmailUtil.checkEmail(email)) {
					Messagebox.show("邮箱不存在!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					Messagebox.show("邮箱真实存在!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查邮箱格式
	 * 
	 * @param email
	 */
	@Command
	@NotifyChange({ "ebm" })
	public void checkEmailSimple(@BindingParam("email") String email) {
		try {
			if (email != null && !email.isEmpty()) {
				if (!EmailUtil.checkEmailSimple(email)) {
					Messagebox.show("邮箱格式错误!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					ebm.setEmba_email(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证手机号码
	 * 
	 */
	@Command
	@NotifyChange({ "ebm" })
	public void checkMobile() {
		try {
			if (ebm.getEmba_mobile() != null && !ebm.getEmba_mobile().isEmpty()) {
				if (!TelUtil.isMobile(ebm.getEmba_mobile())) {
					Messagebox.show("手机号码格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					ebm.setEmba_mobile(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 外籍人参保
	 * 
	 * @param forei
	 */
	@Command
	@NotifyChange({ "formulaList" })
	public void isForeigner(@BindingParam("forei") String forei) {
		if (forei != null) {
			if ("是".equals(forei)) {
				setFormulaList(1);
			} else {
				setFormulaList(0);
			}
		}
	}

	/**
	 * 验证固定电话
	 * 
	 */
	@Command
	@NotifyChange({ "ebm" })
	public void checkPhone() {
		try {
			if (ebm.getEmba_phone() != null && !ebm.getEmba_phone().isEmpty()) {
				if (!TelUtil.isPhone(ebm.getEmba_phone())) {
					Messagebox.show("家庭电话格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					ebm.setEmba_phone(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过身份证获取生日，省份，性别等信息
	 * 
	 */
	@Command
	@NotifyChange({ "ebm", "birth" })
	public void idcardhandle() {
		try {
			System.out.print(ebm.getEmba_idcardclass());
			if (ebm.getEmba_idcardclass().equals("身份证")) {
				if (ebm.getEmba_idcard() != null
						&& !ebm.getEmba_idcard().isEmpty()) {

					// 检查身份证号码合法性
					if (IdcardUtil.validateCard(ebm.getEmba_idcard())) {
						String idCard = ebm.getEmba_idcard();

						birth = DateStringChange.StringtoDate(IdcardUtil
								.strtodateformat(IdcardUtil
										.getBirthByIdCard(idCard)),
								"yyyy-MM-dd");
						ebm.setEmba_sex(IdcardUtil.getGenderByIdCard(idCard));
						if (ebm.getEmba_native() == null
								|| ebm.getEmba_native().isEmpty()) {
							ebm.setEmba_native(IdcardUtil
									.getProvinceByIdCard(idCard));
						}
					} else {
						Messagebox.show("身份证不合法,请检查是否正确!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 上一步
	@Command
	public void back() {
		CoBaseModel cobaM = new CoBaseModel();
		cobaM.setCoba_company(ebm.getCoba_company());
		WfBusinessService wfbs = new Embase_OnBoardImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "返回报价单", ebm };
		String[] str = wfs.ReturnToPrev(obj, emba_tapr_id,
				UserInfo.getUsername(), "");
		if (str[0].equals("1")) {

			Map map = new HashMap();
			map.put("embaId", ebm.getEmba_id());
			map.put("cobaM", cobaM);
			Window window = (Window) Executions.createComponents(
					"../Embase/EmBase_Add.zul", null, map);
			window.doModal();
			ww.detach();
		} else {
			Messagebox.show("操作失败", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 下一步
	@Command
	public void submit() {
		Messagebox.show("确认变更数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							WfBusinessService wfbs = new Embase_OnBoardImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							ebm.setEmba_state(5);
							Object[] obj = { "确认信息", ebm };
							String[] str = wfs.PassToNext(obj, emba_tapr_id,
									UserInfo.getUsername(), "", ebm.getCid(), "");
							if (str[0].equals("1")) {
								// 发现财税时更新任务单
								obll.updateMission(ebm.getEmba_id(),
										ebm.getGid(),
										Integer.valueOf(str[2].toString()));
								ww.detach();
								Messagebox.show(str[1]);
								
//								//发送邮件至雇员服务中心
//								
//								  MessageService msgservice=new MessageImpl("embase",ebm.getEmba_id());
//							      LoginDal d =new LoginDal();
//								      SysMessageModel sysm =new SysMessageModel();
//							      String msgstr="公司:"+ebm.getCoba_company()+"的"+ebm.getEmba_name()+"入职退回已重新提交";
//							      sysm.setSyme_title(msgstr);
//							      sysm.setSyme_content(msgstr);//短信内容
//							      sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));//发件人id
//							      sysm.setSymr_name(ebm.getEmba_modname());//收件人姓名
//							      sysm.setSymr_log_id(d.getUserIDByname(ebm.getEmba_modname()));//;
//							      sysm.setEmail(1);
//							      sysm.setEmailtitle(msgstr);
//							      msgservice.Add(sysm);
									
								
							} else {
								Messagebox.show(str[1], "ERROR", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});

	}
	
	@Command
	public void cancel() {
		Messagebox.show("确认取消入职?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							if (obll.cancelOnboard(ebm.getEmba_id(),
									ebm.getGid(), ebm.getEmba_tapr_id())) {
								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								ww.detach();
							}
						}
					}
				});
	}

	public boolean isGjj() {
		return gjj;
	}

	public void setGjj(boolean gjj) {
		this.gjj = gjj;
	}

	public boolean isSb() {
		return sb;
	}

	public void setSb(boolean sb) {
		this.sb = sb;
	}

	public void setEmList(Integer id) {
		this.emList = bll.embaseinfo(id);
	}

	public EmbaseModel getEbm() {
		return ebm;
	}

	public void setEbm(EmbaseModel ebm) {
		this.ebm = ebm;
	}

	public Date getBirth() {
		return birth;
	}

	public Emcontactinfo getEmzt() {
		return emzt;
	}

	public void setEmzt(int id) {
		this.emzt = bll.getEmzt(id);
	}

	public List<EmShebaoFormulaModel> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(int ifForeign) {
		this.formulaList = bll.getFormula(ifForeign);
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public List<EmHouseCpp> getCpList() {
		return cpList;
	}

	public void setCpList(Integer cid, Integer gid, Integer ownmonth) {
		this.cpList = bll.getCpp(cid, gid, ownmonth);
	}

	public EmShebaoFormulaModel getFormula() {
		return formula;
	}

	public void setFormula(EmShebaoFormulaModel formula) {
		this.formula = formula;
	}

	public List<String> getFolkList() {
		return folkList;
	}

	public void setFolkList() {
		this.folkList = bll.getFolkList();
	}

	public String getWinId() {
		return winId;
	}

	public void setWinId(String winId) {
		this.winId = winId;
	}

}
