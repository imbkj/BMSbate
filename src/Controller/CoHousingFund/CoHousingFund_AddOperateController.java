package Controller.CoHousingFund;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.CoHousingFund.CoHousingFund_OperateDal;

import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;
import bll.EmHouse.EmHouse_EditBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundChangeModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbChangeModel;
import Model.PubGjBankModel;
import Util.DateStringChange;
import Util.EmailUtil;
import Util.UserInfo;

public class CoHousingFund_AddOperateController {

	private Integer daid;
	private Integer state;
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();
	private List<PubGjBankModel> gjbankList = new ListModelList<>();
	private List<CoHousingFundZbChangeModel> zbList = new ListModelList<>();
	private List<CoHousingFundPwdChangeModel> pwdList = new ListModelList<>();

	public CoHousingFund_AddOperateController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		try {
			chfcModel = bll.getCoHoChangeList(" and chfc_id=" + daid).get(0);

			chfcModel.setChfc_manstate("我司管理");
			if (chfcModel.getChfc_state().equals(4)
					|| chfcModel.getChfc_state().equals(5)) {
				state = chfcModel.getChfc_laststate();
			} else {
				state = chfcModel.getChfc_state();
			}

			setGjbankList(bll.getGjBankList());
			gjbankList.add(0, null);

			// 专办员
			setZbList(bll.getZbChangeList(" and cfzc_state=0 and cfzc_chfc_id="
					+ daid));
			chfcModel.setZb_count(zbList.size());
			for (int i = 0; i < 3 - chfcModel.getZb_count(); i++) {
				CoHousingFundZbChangeModel m = new CoHousingFundZbChangeModel();

				m.setCfzc_cohf_id(chfcModel.getChfc_cohf_id());
				m.setCfzc_chfc_id(chfcModel.getChfc_id());
				m.setCfzc_addtype("新增");
				m.setOwnmonth(Integer.parseInt(DateStringChange.DatetoSting(
						new Date(), "yyyyMM")));
				m.setCfzc_addname(UserInfo.getUsername());
				m.setCfzc_state(0);
				m.setCfzc_laststate(0);
				m.setCfzc_tzlstate(1);
				m.setCfzc_remark("与单位公积金账户同时新增，自动变为已申报状态");

				zbList.add(m);
			}

			// 密钥
			setPwdList(bll
					.getPwdChangeList(" and cfpc_state=0 and cfpc_chfc_id="
							+ daid));
			for (int i = 0; i < 1 - pwdList.size(); i++) {
				CoHousingFundPwdChangeModel m = new CoHousingFundPwdChangeModel();

				m.setCfpc_cohf_id(chfcModel.getChfc_cohf_id());
				m.setCfpc_chfc_id(chfcModel.getChfc_id());
				m.setCfpc_addtype("新增");
				m.setOwnmonth(Integer.parseInt(DateStringChange.DatetoSting(
						new Date(), "yyyyMM")));
				m.setCfpc_addname(UserInfo.getUsername());
				m.setCfpc_state(0);
				m.setCfpc_laststate(0);
				m.setCfpc_tzlstate(1);
				m.setCfpc_remark("与单位公积金账户同时新增，自动变为已申报状态");

				pwdList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择归集银行后，同步到领卡银行
	 * 
	 */
	@Command
	@NotifyChange({ "chfcModel" })
	public void gjbankchange() {
		chfcModel.setChfc_banklk(chfcModel.getChfc_bankgj());
	}

	/**
	 * 检查邮箱格式
	 * 
	 * @param email
	 */
	@Command
	public void checkEmailSimple(@BindingParam("email") String email) {
		try {
			if (email != null && !email.isEmpty()) {
				if (!EmailUtil.checkEmailSimple(email)) {
					Messagebox.show("邮箱格式错误!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 点击专办员序号，将此序号的专办员信息复制到密钥信息中
	 * 
	 * @param zbModel
	 */
	@Command
	@NotifyChange({ "pwdList" })
	public void checkzb(
			@BindingParam("zbModel") CoHousingFundZbChangeModel zbModel) {
		try {
			if (zbModel.getCfzc_name() != null
					&& zbModel.getCfzc_number() != null
					&& !zbModel.getCfzc_name().isEmpty()
					&& !zbModel.getCfzc_number().isEmpty()) {
				pwdList.get(0).setCfpc_zb_name(zbModel.getCfzc_name());
				pwdList.get(0).setCfpc_zb_number(zbModel.getCfzc_number());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退回
	 * 
	 * @param win
	 */
	@Command
	public void backdata(@BindingParam("win") final Window win) {
		Messagebox.show("确认退回数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							CoHousingFund_OperateBll bll = new CoHousingFund_OperateBll();
							chfcModel.setChfc_state(4);
							Integer i = bll.returnTocommit(chfcModel);
							if (i > 0) {
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							}
						}
					}
				});
	}

	@Command
	public void look() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", chfcModel.getChfc_cohf_id());
		map.put("cid", chfcModel.getCid());
		Window window = (Window) Executions.createComponents(
				"../CoHousingFund/CoHousingFund_Info.zul", null, map);
		window.doModal();
	}

	/**
	 * 暂存
	 * 
	 * @param win
	 */
	@Command
	public void save(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		CoHousingFund_OperateDal dal = new CoHousingFund_OperateDal();
		Integer row = 0;

		try {

			if (chfcModel.getChfc_state().equals(1)) {
				// 提交材料
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				String[] str = docOC.UpsubmitDoc(gd, chfcModel.getChfc_id()
						+ "");

				if (str[0].equals("1")) {
					Messagebox.show("保存成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else if (chfcModel.getChfc_state().equals(2)) {
				row = dal.cohousemod(chfcModel);

				if (chfcModel.getCid() != null
						&& !chfcModel.getCid().equals("")
						&& chfcModel.getChfc_houseid() != null
						&& !chfcModel.getChfc_houseid().equals("")
						&& chfcModel.getChfc_cpp() != null
						&& !chfcModel.getChfc_cpp().equals("")) {
					EmHouse_EditBll bll = new EmHouse_EditBll();
					bll.modHouseId(chfcModel.getCid(),
							chfcModel.getChfc_houseid(),
							chfcModel.getChfc_cpp());
				}
				if (row > 0) {
					Integer res = 0;
					// 删除暂存的专办员临时数据
					dal.DelZbTem(chfcModel.getChfc_id());
					// 过滤专办员列表
					zbList_Filter();
					// 新增专办员数据
					for (CoHousingFundZbChangeModel m : zbList) {
						if (dal.zbadd(m) > 0) {
							res++;
						}
					}

					if (res.equals(zbList.size())) {
						res = 0;
						// 删除暂存的密钥临时数据
						dal.DelPwdTem(chfcModel.getChfc_id());
						// 过滤密钥列表
						pwdList_Filter();
						// 新增密钥数据
						for (CoHousingFundPwdChangeModel m : pwdList) {
							if (dal.pwdadd(m) > 0) {
								res++;
							}
						}

						if (res.equals(pwdList.size())) {
							Messagebox.show("保存成功!", "INFORMATION",
									Messagebox.OK, Messagebox.INFORMATION);
							win.detach();
						} else {
							Messagebox.show("密钥数据保存失败!", "ERROR",
									Messagebox.OK, Messagebox.ERROR);
						}
					} else {
						Messagebox.show("专办员数据保存失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("账户数据保存失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		CoHousingFund_OperateBll bll = new CoHousingFund_OperateBll();

		try {
			state = state.equals(4) ? 2 : (state + 1);
			chfcModel.setChfc_state(state);
			chfcModel.setChfc_addname(UserInfo.getUsername());
			zbList_Filter();
			pwdList_Filter();
			chfcModel.setZbList(zbList);
			chfcModel.setPwdList(pwdList);

			String[] str = bll.updatestate(chfcModel, gd);
			if (str[0].equals("1")) {
				if (chfcModel.getCid() != null
						&& !chfcModel.getCid().equals("")
						&& ((chfcModel.getChfc_houseid() != null && !chfcModel
								.getChfc_houseid().equals("")) || (chfcModel
								.getChfc_cpp() != null && !chfcModel
								.getChfc_cpp().equals("")))) {
					EmHouse_EditBll ebll = new EmHouse_EditBll();
					ebll.modHouseId(chfcModel.getCid(),
							chfcModel.getChfc_houseid(),
							chfcModel.getChfc_cpp());
				}
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 过滤专办员列表
	 * 
	 */
	public void zbList_Filter() {
		for (int i = 0; i < zbList.size(); i++) {
			CoHousingFundZbChangeModel m = zbList.get(i);
			if (m.getCfzc_number() == null || m.getCfzc_number().isEmpty()
					|| m.getCfzc_name() == null || m.getCfzc_name().isEmpty()) {
				zbList.remove(m);
				i--;
			}
		}
	}

	/**
	 * 过滤密钥列表
	 * 
	 */
	public void pwdList_Filter() {
		for (int i = 0; i < pwdList.size(); i++) {
			CoHousingFundPwdChangeModel m = pwdList.get(i);
			if (m.getCfpc_zb_number() == null
					|| m.getCfpc_zb_number().isEmpty()
					|| m.getCfpc_zb_name() == null
					|| m.getCfpc_zb_name().isEmpty()) {
				pwdList.remove(m);
				i--;
			} else {
				if (m.getStartdate() != null) {
					m.setCfpc_startdate(DateStringChange.DatetoSting(
							m.getStartdate(), "yyyy-MM-dd"));
				}
				if (m.getEnddate() != null) {
					m.setCfpc_enddate(DateStringChange.DatetoSting(
							m.getEnddate(), "yyyy-MM-dd"));
				}
			}
		}
	}

	public CoHousingFundChangeModel getChfcModel() {
		return chfcModel;
	}

	public void setChfcModel(CoHousingFundChangeModel chfcModel) {
		this.chfcModel = chfcModel;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<PubGjBankModel> getGjbankList() {
		return gjbankList;
	}

	public void setGjbankList(List<PubGjBankModel> gjbankList) {
		this.gjbankList = gjbankList;
	}

	public List<CoHousingFundZbChangeModel> getZbList() {
		return zbList;
	}

	public void setZbList(List<CoHousingFundZbChangeModel> zbList) {
		this.zbList = zbList;
	}

	public List<CoHousingFundPwdChangeModel> getPwdList() {
		return pwdList;
	}

	public void setPwdList(List<CoHousingFundPwdChangeModel> pwdList) {
		this.pwdList = pwdList;
	}
}
