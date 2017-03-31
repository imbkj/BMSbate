package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmReg.EmReg_ListBll;
import bll.EmResidencePermit.Emrp_ListBll;
import bll.EmResidencePermit.Emrp_OperateBll;

import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitInfoModel;
import Model.PubCodeConversionModel;
import Util.UserInfo;

public class Emrp_OperateController {

	private EmResidencePermitInfoModel epm = new EmResidencePermitInfoModel();
	private Integer daid = 0;
	private String timestr = "";
	private Integer state = 0;
	private Date statetime = new Date();

	private List<PubCodeConversionModel> feeList;
	private PubCodeConversionModel feeM = new PubCodeConversionModel();
	private List<PubCodeConversionModel> tarList;
	private PubCodeConversionModel tarM = new PubCodeConversionModel();
	private boolean rwvis = false;
	private String nextstate = "";

	public Emrp_OperateController() {
		try {
			Emrp_ListBll bll = new Emrp_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			setEpm(bll.getEmrpInfo(daid));

			if (epm.getErpi_state()!=null&&(epm.getErpi_state() == 12 || epm.getErpi_state() == 13
					|| epm.getErpi_state() == 14)) {
				state = epm.getErpi_laststate();
			} else {
				state = epm.getErpi_state();
			}
			timestr();
			getComboList();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 获取下拉列表
	public void getComboList() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			setFeeList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=3 and pcco_name='收费状态'")));
			if (epm.getErpi_fee_state() != null) {
				for (PubCodeConversionModel m : feeList) {
					if (m.getPcco_code().equals(epm.getErpi_fee_state() + "")) {
						setFeeM(m);
						break;
					}
				}
			}

			setTarList(new ListModelList<>(
					bll.getPubCodeList(" and pucl_id=3 and pcco_name='发证对象'")));
			if (epm.getErpi_send_target() != null
					&& epm.getErpi_send_target() != 0) {
				for (PubCodeConversionModel m : tarList) {
					if (m.getPcco_code().equals(epm.getErpi_send_target() + "")) {
						setTarM(m);
						break;
					}
				}
			}
			tarList.add(0, new PubCodeConversionModel());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 退回
	@Command("back")
	public void back(@BindingParam("win") Window win) {
		String url = "../EmResidencePermit/Emrp_Back.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		if (map.get("flag").equals("1")) {
			win.detach();
		}
	}

	public void timestr() {
		switch (state) {
		case 2:
			setTimestr("人事签收材料日期");
			break;
		case 3:
			setTimestr("上传日期");
			break;
		case 4:
			setTimestr("人事网上申报日期");
			break;
		case 5:
			if (epm.getZhtype() == 1 && epm.getErpi_t_kind().equals("新办")) {
				setTimestr("客服收需盖章表格日期");
			} else {
				setTimestr("政府部门办理完成日期");
			}
			break;
		case 6:
			setTimestr("人事收已盖章表格日期");
			break;
		case 7:
			setTimestr("政府部门办理完成日期");
			break;
		case 8:
			setTimestr("人事领取居住证日期");
			break;
		case 9:
			setTimestr("客服取居住证日期");
			break;
		case 10:
			setTimestr("员工签收居住证日期");
			break;
		default:
			break;
		}
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		if (statetime == null) {
			Messagebox.show("请输入日期!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else if (epm.getErpi_state() == 10 && tarM.getPcco_code() == null) {
			Messagebox.show("请选择发证对象!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			try {
				if (epm.getErpi_state() == 12 || epm.getErpi_state() == 13
						|| epm.getErpi_state() == 14) {
					epm.setErpi_state(state + 1);
				} else if (state == 5
						&& (epm.getZhtype() == 0 || epm.getErpi_t_kind()
								.equals("补办"))) {
					epm.setErpi_state(state + 3);
				} else {
					epm.setErpi_state(state + 1);
				}

				if (epm.getErpi_state() == 11) {
					epm.setErpi_reg_state(1);
					if (tarM.getPcco_code() != null) {
						epm.setErpi_send_target(Integer.parseInt(tarM
								.getPcco_code()));
					}
					epm.setErpi_fee_state(Integer.parseInt(feeM.getPcco_code()));
				}

				epm.setEpsr_addname(UserInfo.getUsername());
				epm.setEpsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
						.format(statetime));

				String[] str = new Emrp_OperateBll().UpdateState(epm,
						new EmRegistrationInfoModel(), gd);

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 服务中心领卡
	@Command
	public void centertakecard(@BindingParam("win") Window win) {
		if (statetime == null) {
			Messagebox.show("请选择领卡日期!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			epm.setEpsr_addname(UserInfo.getUsername());
			epm.setEpsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
					.format(statetime));
			epm.setErpi_state(16);
			String[] str = new Emrp_OperateBll().UpdateState(epm,
					new EmRegistrationInfoModel(), null);

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 服务中心发卡
	@Command
	public void centersendcard(@BindingParam("win") Window win) {
		if (nextstate != null && !nextstate.equals("")) {
			if (nextstate.equals("发证给员工")) {
				if (tarM.getPcco_code() == null) {
					Messagebox.show("请选发证对象", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				} else if (feeM.getPcco_code() == null) {
					Messagebox.show("请选择收费状态", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					epm.setErpi_state(11);
					epm.setErpi_reg_state(1);
					if (tarM.getPcco_code() != null) {
						epm.setErpi_send_target(Integer.parseInt(tarM
								.getPcco_code()));
					}
					epm.setErpi_fee_state(Integer.parseInt(feeM.getPcco_code()));

					epm.setEpsr_addname(UserInfo.getUsername());
					epm.setEpsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
							.format(statetime));
					
					String[] str = new Emrp_OperateBll().CenterOverTask(epm,
							new EmRegistrationInfoModel());

					if (str[0].equals("1")) {
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show("提交成功", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				epm.setEpsr_addname(UserInfo.getUsername());
				epm.setEpsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date()));
				epm.setErpi_state(17);
				String[] str = new Emrp_OperateBll().UpdateState(epm,
						new EmRegistrationInfoModel(), null);

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} else {
			Messagebox.show("请选择下一步操作", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 服务中心选择下一步操作
	@Command
	@NotifyChange("rwvis")
	public void selectnext() {
		if (nextstate != null && !nextstate.equals("")) {
			if (nextstate.equals("发证给员工")) {
				rwvis = true;
			} else {
				rwvis = false;
			}
		} else {
			Messagebox.show("请选择下一步操作", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}
	
	//客服领卡
	@Command
	public void clienttakecard(@BindingParam("win") Window win)
	{
		if (statetime == null) {
			Messagebox.show("请选择领卡日期!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			epm.setEpsr_addname(UserInfo.getUsername());
			epm.setEpsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
					.format(statetime));
			epm.setErpi_state(10);
			String[] str = new Emrp_OperateBll().UpdateState(epm,
					new EmRegistrationInfoModel(), null);

			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public EmResidencePermitInfoModel getEpm() {
		return epm;
	}

	public void setEpm(EmResidencePermitInfoModel epm) {
		this.epm = epm;
	}

	public String getTimestr() {
		return timestr;
	}

	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}

	public Date getStatetime() {
		return statetime;
	}

	public void setStatetime(Date statetime) {
		this.statetime = statetime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public List<PubCodeConversionModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<PubCodeConversionModel> feeList) {
		this.feeList = feeList;
	}

	public List<PubCodeConversionModel> getTarList() {
		return tarList;
	}

	public void setTarList(List<PubCodeConversionModel> tarList) {
		this.tarList = tarList;
	}

	public PubCodeConversionModel getFeeM() {
		return feeM;
	}

	public void setFeeM(PubCodeConversionModel feeM) {
		this.feeM = feeM;
	}

	public PubCodeConversionModel getTarM() {
		return tarM;
	}

	public void setTarM(PubCodeConversionModel tarM) {
		this.tarM = tarM;
	}

	public boolean isRwvis() {
		return rwvis;
	}

	public void setRwvis(boolean rwvis) {
		this.rwvis = rwvis;
	}

	public String getNextstate() {
		return nextstate;
	}

	public void setNextstate(String nextstate) {
		this.nextstate = nextstate;
	}

}
