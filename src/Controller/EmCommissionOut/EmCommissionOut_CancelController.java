package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutStandardModel;
import Util.DateStringChange;
import Util.SocialInsuranceEmCommissionOut;
import Util.UserInfo;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

public class EmCommissionOut_CancelController {
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	private EmCommissionOutChangeModel m1 = new EmCommissionOutChangeModel();
	Integer daid = 0;

	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
	private List<EmCommissionOutChangeModel> titleList;// 当地标准
	private EmCommissionOutChangeModel titleModel;// 获取标准详细信息
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();

	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();

	public EmCommissionOut_CancelController() {
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
	 * 数据初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setM(bll.getEmCommOutChangeInfo(daid, ""));
			m1.setEcoc_addname(UserInfo.getUsername());
			m1.setEcoc_cancel_id(daid);
			m1.setEcoc_type(m.getEcoc_type());
			m1.setGid(m.getGid());
			m1.setCid(m.getCid());
			m1.setEcoc_addtype(m.getEcoc_addtype());
			m1.setEcoc_tapr_id(m.getEcoc_tapr_id());
			m1.setEcoc_id(daid);
			m1.setEcoc_title_date(DateStringChange.Datestringnow("yyyy-MM-dd"));
			m1.setEmba_name(m.getEmba_name());
			m1.setEcoc_client(m.getEcoc_client());
			m1.setEcoc_idcard(m.getEcoc_idcard());
			m1.setEcoc_ecos_id(m.getEcoc_ecos_id());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
		if (m1.getEcoc_cancel_cause() == null
				|| m1.getEcoc_cancel_cause().isEmpty()) {
			Messagebox.show("请输入取消原因!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else if (Messagebox.show("提交后，该任务单将被系统终止操作,且该单无法恢复,是否确认取消?",
				"CONFIRM", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION) == Messagebox.CANCEL) {
		} else {
			try {
				m1.setEcoc_cancel_date(DateStringChange.StringtoDate(
						m.getEcoc_title_date(), "yyyy-MM-dd"));
				m1.setEcoc_state(0);

				String[] str = bll.cancel(m1);

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

	public EmCommissionOutChangeModel getM() {
		return m;
	}

	public void setM(EmCommissionOutChangeModel m) {
		this.m = m;
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}

	public List<EmCommissionOutChangeModel> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<EmCommissionOutChangeModel> titleList) {
		this.titleList = titleList;
	}

	public EmCommissionOutChangeModel getTitleModel() {
		return titleModel;
	}

	public void setTitleModel(EmCommissionOutChangeModel titleModel) {
		this.titleModel = titleModel;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public EmCommissionOutChangeModel getM1() {
		return m1;
	}

	public void setM1(EmCommissionOutChangeModel m1) {
		this.m1 = m1;
	}
}
