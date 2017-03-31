package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoOnlineRegisterInfoModel;
import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitChangeModel;
import Model.EmResidencePermitInfoModel;
import Util.UserInfo;
import bll.EmReg.EmReg_ListBll;
import bll.EmResidencePermit.Emrp_ChangeBll;
import bll.EmResidencePermit.Emrp_ListBll;

public class Emrp_ChangeAddController {
	// 页面传值
	private Integer gid = 0;
	private String role = "";// hd 后道 qd 前道

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();
	private EmResidencePermitInfoModel epm = new EmResidencePermitInfoModel();
	private List<EmResidencePermitInfoModel> hosList;

	// 特殊字段
	private String is_parttime;// 是否兼职
	private String if_unlimited;// 无固定劳动期限
	private String if_invoice;// 是否需要发票

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private EmResidencePermitInfoModel mm = new EmResidencePermitInfoModel();
	private Emrp_ListBll bl = new Emrp_ListBll();

	public Emrp_ChangeAddController() {
		try {
			Emrp_ListBll bll = new Emrp_ListBll();

			gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());
			mm = bll.getEmResidencePermitInfo(gid);
			setEpm(bll.getEmrpInfo(mm.getErpi_id()));
			setErm(new EmReg_ListBll().getEmRegInfo(epm.getErin_id(), ""));
			setHosList(bll.getStateRelList(" and typename='后道状态' and state=1",
					mm.getErpi_id()));
			if (mm.getErpi_id() != null) {
				fieldinit();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	//创建窗口时检测该员工是否可以办理居住证转换
	@Command
	public void createwin(@BindingParam("win") Window win) {
		String sqlstr = " and erpi_t_kind='新办'";
		EmResidencePermitInfoModel pmmodel = bl.getResidencePermit(gid, sqlstr);
		EmRegistrationInfoModel regmodel = bl.getEmRegistrationInfo(gid);
		if (regmodel.getErin_id() == null) {
			refreshEmUrl("该员工没有办理就业登记，不能做居住证转换!");
		} else {
			if (pmmodel.getErpi_id() == null) {
				refreshEmUrl("该员工没有居住证信息!");
			} else if (pmmodel.getErpi_state() < 10) {
				refreshEmUrl("该员工居住证新办还没有完成，不能转换!");
			}
		}
	}
	
	//页面跳转
	private void refreshEmUrl(String msg)
	{
		Messagebox.show(msg, "ERROR", Messagebox.OK,
				Messagebox.ERROR);
		Map map = new HashMap<>();
		map.put("url", "/EmHouseCard/CardInfoList.zul");// 跳转的页面连接
		BindUtils.postGlobalCommand(null, null, "refreshEmUrl", map);
	}

	// 居住证转换新增
	@Command()
	public void change(@BindingParam("win") Window win) {
		EmResidencePermitChangeModel m = new EmResidencePermitChangeModel();
		if (Messagebox.show("是否确认提交居住证转换?", "CONFIRM", Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {

			// 数据处理
			try {
				m.setGid(epm.getGid());
				m.setCid(epm.getCid());
				m.setOwnmonth(new SimpleDateFormat("yyyyMM").format(new Date()));
				m.setErpc_idcard(erm.getErin_idcard());
				m.setErpc_laststate(0);
				m.setErpc_state(1);
				m.setErpc_addname(UserInfo.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("数据处理出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

			try {
				String[] str = new Emrp_ChangeBll().changeadd(m,
						epm.getEmba_name());

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					Map map = new HashMap<>();
					map.put("url", "/EmHouseCard/CardInfoList.zul");// 跳转的页面连接
					BindUtils
							.postGlobalCommand(null, null, "refreshEmUrl", map);
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/**
	 * 特殊字段初始化
	 * 
	 */
	public void fieldinit() {
		try {
			if (erm.getErin_is_parttime() != null) {
				setIs_parttime(erm.getErin_is_parttime() == 1 ? "是" : "否");
			}
			if (erm.getErin_if_unlimited() != null) {
				setIf_unlimited(erm.getErin_if_unlimited() == 1 ? "是" : "否");
			}
			if (epm.getErpi_if_invoice() != null) {
				setIf_invoice(epm.getErpi_if_invoice() == 1 ? "是" : "否");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("初始化数据出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public EmResidencePermitInfoModel getEpm() {
		return epm;
	}

	public void setEpm(EmResidencePermitInfoModel epm) {
		this.epm = epm;
	}

	public String getIs_parttime() {
		return is_parttime;
	}

	public void setIs_parttime(String is_parttime) {
		this.is_parttime = is_parttime;
	}

	public String getIf_unlimited() {
		return if_unlimited;
	}

	public void setIf_unlimited(String if_unlimited) {
		this.if_unlimited = if_unlimited;
	}

	public String getIf_invoice() {
		return if_invoice;
	}

	public void setIf_invoice(String if_invoice) {
		this.if_invoice = if_invoice;
	}

	public List<EmResidencePermitInfoModel> getHosList() {
		return hosList;
	}

	public void setHosList(List<EmResidencePermitInfoModel> hosList) {
		this.hosList = hosList;
	}
}
