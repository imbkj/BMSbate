package Controller.EmCommissionOut;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutStandardModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmCommissionOut_OnetimefeeController {

	Integer gid = 0;
	private EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();
	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();

	private List<EmCommissionOutStandardModel> stardList;// 合同标准
	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();

	public EmCommissionOut_OnetimefeeController() {
		try {
			gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 页面初始化
	 * 
	 */
	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();
		try {
			setM(bll.getEmbase(gid));
			m.setEcoc_sb_em_sum(new BigDecimal(0));
			m.setEcoc_sb_co_sum(new BigDecimal(0));
			m.setEcoc_sb_sum(new BigDecimal(0));
			m.setEcoc_gjj_em_sum(new BigDecimal(0));
			m.setEcoc_gjj_co_sum(new BigDecimal(0));
			m.setEcoc_gjj_sum(new BigDecimal(0));
			m.setEcoc_sum(new BigDecimal(0));
			m.setEcoc_welfare_sum(new BigDecimal(0));
			m.setEcoc_sb_base(new BigDecimal(0));
			m.setEcoc_house_base(new BigDecimal(0));
			m.setEcoc_salary(new BigDecimal(0));
			m.setGid(gid);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strDate =DateStringChange.DatetoSting(new Date(), "yyyy-MM-01");
			Date date=sdf.parse(strDate);
			m.setTitle_date(date);
			
			// 获取服务项目
			setFeeList(bll.getNullSoClassList(0));

			// 其他
			EmCommissionOutFeeDetailChangeModel m1 = new EmCommissionOutFeeDetailChangeModel();
			m1.setEofc_name("其他费用");
			m1.setSicl_class("其他费用");
			m1.setEofc_month_sum(BigDecimal.ZERO);
			m1.setEofc_em_base(BigDecimal.ZERO);
			m1.setEofc_co_base(BigDecimal.ZERO);
			m1.setEofc_state(1);
			feeList.add(m1);

			setStardList(bll.getStardList(m.getCid(), 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 总计
	 * 
	 */
	@Command("calcsum")
	@NotifyChange({ "m" })
	public void calcsum() {
		m.setEcoc_sum(BigDecimal.ZERO);
		for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
			m.setEcoc_sum(m.getEcoc_sum().add(m1.getEofc_month_sum()));
		}
	}

	/**
	 * 提交
	 * 
	 */
	@Command("submit")
	@NotifyChange({ "m", "feeList", "stardList" })
	public void submit() {
		if (stardModel.getEcos_id() == null) {
			Messagebox
					.show("选择合同标准!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else if ("".equals(m.getEcoc_remark()) || m.getEcoc_remark() == null) {
			Messagebox.show("请填写备注!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

		else {

			EmCommissionOut_OperateBll bll = new EmCommissionOut_OperateBll();
			try {
				m.setEcoc_title_date(DateStringChange.DatetoSting(
						m.getTitle_date(), "yyyy-MM-dd"));
				m.setEcoc_ecos_id(stardModel.getEcos_id());
				m.setEcoc_addtype("一次性费用");
				m.setEcoc_type("ecocotf");
				m.setEcoc_addname(UserInfo.getUsername());
				m.setEcoc_state(1);
				m.setEcoc_laststate(0);
				m.setFeeList(feeList);

				String[] str = bll.onetimefee(m, m.getEmba_name());

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					init();
				} else if (str[0].equals("0")) {
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

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public List<EmCommissionOutStandardModel> getStardList() {
		return stardList;
	}

	public void setStardList(List<EmCommissionOutStandardModel> stardList) {
		this.stardList = stardList;
	}

	public EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}

	public void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}
}
