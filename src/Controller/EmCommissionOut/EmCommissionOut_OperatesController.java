package Controller.EmCommissionOut;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Util.DateStringChange;
import Util.DateUtil;

public class EmCommissionOut_OperatesController {

	private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
	private List<EmCommissionOutFeeDetailChangeModel> flList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> ecocList = new ListModelList<>();
	String daids = "";
	private String addtype = "";

	public EmCommissionOut_OperatesController() {
		try {
			daids = Executions.getCurrent().getArg().get("daids").toString();

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void init() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			setFeeList(bll.getNullSoClassList(0));
			setFlList(bll.getFeeDetailChangeFlList(" and eofc_ecoc_id in("
					+ daids + ")"));
			feeList.addAll(flList);
			setEcocList(bll.getEmCommOutChangeList(" and ecoc_id in(" + daids
					+ ")"));
			for (EmCommissionOutChangeModel m : ecocList) {
				m.setFeeList(bll.getFeeDetailChangeList(" and eofc_ecoc_id="
						+ m.getEcoc_id()));
			}
			setAddtype(ecocList.get(0).getEcoc_addtype());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 时间批量修改
	 * 
	 * @param date
	 */
	@Command("dateAll")
	@NotifyChange({ "feeList" })
	public void dateAll(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		if (sicl_class.equals("all")) {
			date = feeList.get(0).getTempDate();
			index = 0;
		}
		for (int i = index + 1; i < feeList.size(); i++) {
			if (feeList.get(i).getSicl_class().equals(sicl_class)
					|| sicl_class.equals("all")) {
				feeList.get(i).setTempDate(date);
			}
		}
	}

	@Command("dateAll1")
	@NotifyChange({ "feeList" })
	public void dateAll1(@BindingParam("date") Date date,
			@BindingParam("index") Integer index,
			@BindingParam("class") String sicl_class) {
		if (sicl_class.equals("all")) {
			date = feeList.get(0).getTempDate1();
			index = 0;
		}
		for (int i = index + 1; i < feeList.size(); i++) {
			if (feeList.get(i).getSicl_class().equals(sicl_class)
					|| sicl_class.equals("all")) {
				feeList.get(i).setTempDate1(date);
			}
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
		Integer count = 0;
		String[] str = new String[5];

		try {
			if (Messagebox.show("是否确定提交勾选的" + ecocList.size() + "条委托单?",
					"CONFIRM", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION) == Messagebox.OK) {
				for (EmCommissionOutChangeModel m : ecocList) {
					m.setEcoc_state(m.getEcoc_state() + 1);
					m.setType(m.getEcoc_type());
					m.setRemark("手动批量操作状态变更");

					for (EmCommissionOutFeeDetailChangeModel m1 : m
							.getFeeList()) {
						EmCommissionOutFeeDetailChangeModel m2 = feeListfind(m1);
						if (!m.getEcoc_addtype().equals("离职")) {
							if (m2.getTempDate() != null) {
								m1.setEofc_co_fstart_date(DateStringChange
										.DatetoSting(m2.getTempDate(),
												"yyyy-MM-01"));
							}
							if (m2.getTempDate1() != null) {
								m1.setEofc_em_fstart_date(DateStringChange
										.DatetoSting(m2.getTempDate1(),
												"yyyy-MM-01"));
							}
						} else {
							if (m2.getTempDate() != null) {
								m1.setEofc_co_fstop_date(DateStringChange
										.DatetoSting(DateUtil.getLastDay(m2
												.getTempDate()), "yyyy-MM-dd"));
							}
							if (m2.getTempDate1() != null) {
								m1.setEofc_em_fstop_date(DateStringChange
										.DatetoSting(DateUtil.getLastDay(m2
												.getTempDate1()), "yyyy-MM-dd"));
							}
						}
					}

					str = bll.updatestate(m, m.getFeeList());

					if (str[0].equals("1")) {
						count++;
					}
				}

				if (ecocList.size() == count) {
					Messagebox.show("全部提交成功\n共更新:" + count + "条委托单!",
							"INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				} else if (ecocList.size() - count > 0) {
					Messagebox.show("部分提交成功\n勾选了:" + ecocList.size()
							+ "条委托单,更新成功:" + count + "条委托单", "ERROR",
							Messagebox.OK, Messagebox.ERROR);
				} else {
					Messagebox.show("全部提交失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 搜索feeList
	 * 
	 * @param sicl_id
	 * @return
	 */
	public EmCommissionOutFeeDetailChangeModel feeListfind(
			EmCommissionOutFeeDetailChangeModel eofcM) {
		EmCommissionOutFeeDetailChangeModel m = new EmCommissionOutFeeDetailChangeModel();
		try {
			if (eofcM.getEofc_sicl_id() != null || eofcM.getEofc_sicl_id() != 0) {
				for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
					if (eofcM.getEofc_sicl_id().equals(m1.getEofc_sicl_id())) {
						m = m1;
						break;
					}
				}
			} else if (eofcM.getEofc_ecop_id() != null
					|| eofcM.getEofc_ecop_id() != 0) {
				for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
					if (eofcM.getEofc_ecop_id().equals(m1.getEofc_ecop_id())) {
						m = m1;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<EmCommissionOutFeeDetailChangeModel> feeList) {
		this.feeList = feeList;
	}

	public List<EmCommissionOutChangeModel> getEcocList() {
		return ecocList;
	}

	public void setEcocList(List<EmCommissionOutChangeModel> ecocList) {
		this.ecocList = ecocList;
	}

	public String getAddtype() {
		return addtype;
	}

	public void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public List<EmCommissionOutFeeDetailChangeModel> getFlList() {
		return flList;
	}

	public void setFlList(List<EmCommissionOutFeeDetailChangeModel> flList) {
		this.flList = flList;
	}
}
