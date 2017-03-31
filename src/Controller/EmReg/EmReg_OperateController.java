package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmReg.EmReg_ListDal;
import dal.EmReg.EmReg_OperateDal;

import bll.CoReg.CoReg_ListBll;
import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;

import Model.CoOnlineRegisterInfoModel;
import Model.EmRegistrationInfoModel;
import Util.UserInfo;

public class EmReg_OperateController {

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private Integer puzu_id = 0;
	private String timestr = "";
	private Date statetime = new Date();

	List<EmRegistrationInfoModel> list = new ListModelList<>();
	private Integer daid = 0;
	private Integer state = 0;
	private String ifbackdata;

	@SuppressWarnings("unchecked")
	public EmReg_OperateController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();

			try {
				daid = Integer.parseInt(Executions.getCurrent().getArg()
						.get("daid").toString());
			} catch (Exception e) {
				list = (List<EmRegistrationInfoModel>) Executions.getCurrent()
						.getArg().get("list");
				daid = list.get(0).getErin_id();
			}

			setErm(bll.getEmRegInfo(daid, ""));
			if (erm.getErin_state() == 9 || erm.getErin_state() == 10
					|| erm.getErin_state() == 11) {
				state = erm.getErin_laststate();
			} else {
				state = erm.getErin_state();
			}
			timestr();

			// 获取材料id
			CoOnlineRegisterInfoModel coregModel = new CoOnlineRegisterInfoModel();
			coregModel = new CoReg_ListBll().getCoregInfobyCid(erm.getCid());
			if (coregModel.getCori_id() != null) {
				if (erm.getErin_hjtype() != null
						&& !erm.getErin_hjtype().isEmpty()) {
					if (erm.getErin_hjtype().equals("本市城镇")) {
						puzu_id = coregModel.getCori_sz_puzu_id();
						EmReg_ListDal dl=new EmReg_ListDal();
						boolean flag=dl.isexistdata(puzu_id);
						if(!flag)
						{
							puzu_id=12;
						}
					} else {
						puzu_id = coregModel.getCori_wd_puzu_id();
						EmReg_ListDal dl=new EmReg_ListDal();
						boolean flag=dl.isexistdata(puzu_id);
						if(!flag)
						{
							puzu_id=22;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void timestr() {
		switch (state) {
		case 2:
			timestr = "人事签收材料日期";
			if (erm.getErin_hjtype().equals("本市城镇")) {
				puzu_id = erm.getCori_sz_puzu_id() == null ? 12 : erm
						.getCori_sz_puzu_id();
			} else {
				puzu_id = erm.getCori_wd_puzu_id() == null ? 22 : erm
						.getCori_wd_puzu_id();
			}
			break;
		case 3:
			timestr = "人事网上申报日期";
			break;
		case 4:
			if (erm.getZhtype() == 1) {
				timestr = "客服收需盖章表格日期";
				if (erm.getErin_hjtype().equals("本市城镇")) {
					puzu_id = erm.getCori_sz_puzu_id() == null ? 12 : erm
							.getCori_sz_puzu_id();
				} else {
					puzu_id = erm.getCori_wd_puzu_id() == null ? 22 : erm
							.getCori_wd_puzu_id();
				}
			} else {
				timestr = "政府部门办理完成日期";
			}
			break;
		case 5:
			timestr = "人事收已盖章表格日期";
			break;
		case 6:
			timestr = "政府部门办理完成日期";
			break;
		case 7:
			timestr = "员工领取就业登记证明日期";
			erm.setErin_get_people(erm.getEmba_name());
			break;
		default:
			break;
		}
	}

	/**
	 * 退回
	 * 
	 * @param m
	 * @param win
	 */
	@Command("back")
	public void back(@BindingParam("win") Window win) {
		String url = "/EmReg/EmReg_Back.zul";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", daid);
		map.put("flag", "1");
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		if(map.get("flag").equals("2"))
		{
			win.detach();
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 *            窗体id
	 * @param gd
	 *            材料grid
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			String strinfo = "";
			if (state == 6 || (state == 4 && erm.getZhtype() != 1)) {
				if (ifbackdata == null || ifbackdata.equals("")) {
					strinfo = "请选择需要是否退回材料";
				}
			}
			if (strinfo.equals("")) {
				// 如果为中智户，状态为已申报(4)，则跳过盖章(5)步骤,跳至待办理(6)
				if (erm.getZhtype() == 0 && erm.getErin_state() == 4) {
					erm.setErin_state(erm.getErin_state() + 3);
				} else {
					erm.setErin_state(state + 1);
				}

				erm.setErsr_addname(UserInfo.getUsername());
				erm.setErsr_statetime(new SimpleDateFormat("yyyy-MM-dd")
						.format(statetime));

				if (erm.getErin_state() == 7) {
					erm.setErin_reg_state(1);
				}

				if (state == 7) {
					Integer row = new EmReg_OperateDal().UpdateState(erm);
					if (row > 0) {
						Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					String[] str = new EmReg_OperateBll().UpdateState(erm, gd);
					if (str[0].equals("1")) {
						if (state == 6 || (state == 4 && erm.getZhtype() != 1)) {
							if (ifbackdata.equals("否")) {//如果不需要退回资料则结束流程
								new EmReg_OperateBll().stop(erm, Integer.parseInt(str[2]),0);
							}
							else
							{
								int row = new EmReg_OperateDal().updateerin(erm.getErin_id(),1);
							}
						}
						Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
			else
			{
				Messagebox.show(strinfo, "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public Integer getPuzu_id() {
		return puzu_id;
	}

	public void setPuzu_id(Integer puzu_id) {
		this.puzu_id = puzu_id;
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

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getIfbackdata() {
		return ifbackdata;
	}

	public void setIfbackdata(String ifbackdata) {
		this.ifbackdata = ifbackdata;
	}

}
