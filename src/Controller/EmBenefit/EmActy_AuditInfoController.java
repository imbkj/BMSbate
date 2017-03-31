package Controller.EmBenefit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmActyGiftBackInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmWelfareModel;
import Util.UserInfo;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmActy_NewOperateFactory;
import bll.EmBenefit.EmActy_NewOperateFactoryImpl;
import bll.EmBenefit.EmActy_NewOperateService;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmPay.EmPay_OperateBll;

public class EmActy_AuditInfoController {
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private EmActySuppilerGiftInfoModel model = new EmActySuppilerGiftInfoModel();
	private EmActy_GiftInfoSelectBll bll = new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> list = bll
			.getEmActyGiftInfo(" and gift_id=" + id);
	private String username = UserInfo.getUsername();
	private String sortid;
	private BigDecimal allpri = new BigDecimal(0.0);

	private EmBenefit_comitListBll bllss = new EmBenefit_comitListBll();
	private List<EmWelfareModel> listss = new ListModelList<>();
	private Map map = Executions.getCurrent().getArg();
	private List<EmWelfareModel> newlist = new ListModelList<EmWelfareModel>();

	public EmActy_AuditInfoController() {
		if (list.size() > 0) {
			model = list.get(0);
			sortid = model.getGift_sortid();
			// listss = bllss.getLists(" and emwf_state=4 and emwf_sortid='"
			// + sortid + "'");
			listss = bllss.getEmwfListAudit(sortid);
			if (listss.size() > 0) {
				for (int i = 0; i < listss.size(); i++) {
					EmWelfareModel lmodel = listss.get(i);
					if (lmodel.getProd_discountprice() != null) {
						allpri = allpri.add(lmodel.getProd_discountprice());
					}
/*					int ku = 0;
					for (EmWelfareModel newmodel : newlist) {
						if ((newmodel.getCid() == lmodel.getCid() || newmodel
								.getCid().equals(newmodel.getCid()))
								&& newmodel.getEmwf_producefo().equals(
										newmodel.getEmwf_producefo())) {
							ku = 1;
							break;
						}
					}					if (ku == 0) {
					lmodel.setOwnmonth(model.getOwnmonth());
						newlist.add(lmodel);
					}*/
				}
			}
		}
	}

	// 审核
	@Command
	public void AuditGift(@BindingParam("win") Window win,
			@BindingParam("dateval") Date dateval,
			@BindingParam("remark") String remark) {
		String strsql = "";
		EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
		boolean flag = obll.updateEmWelfareBySortid(sortid, 5);
		if (flag) {
			// 审核
			strsql = ",gift_AuditTime='" + datechange(dateval)
					+ "',gift_AuditName='" + UserInfo.getUsername() + "',"
					+ "gift_state=1,gift_remark='" + remark + "'";
			EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
			m.setGift_id(model.getGift_id());
			m.setGift_remark("财务审核");
			m.setGift_tarpid(model.getGift_tarpid());
			String[] str = new String[5];
			EmActy_NewOperateFactory factory=new EmActy_NewOperateFactoryImpl();
			EmActy_NewOperateService service=factory.operateFactory("下一步");
			str=service.edit(m, strsql);
			if (str[0] == "1") {
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			win.detach();
			Messagebox.show("更新失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 退回采购申请
	@Command
	public void returnGift(@BindingParam("win") Window win,
			@BindingParam("cause") String cause) {
		if (cause != null && !cause.equals("") && cause != "") {
			EmActyGiftBackInfoModel model = new EmActyGiftBackInfoModel();
			model.setGibk_cause(cause);
			model.setGtbk_giftid(id);
			model.setGtbk_name(UserInfo.getUsername());
			EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
			String[] str = obll.backEmActy_AuditGiftInfo(model, tapr_id);
			if (str[0] == "1") {
				Messagebox.show("退回成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("退回原因不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出退回页面
	@Command
	public void back(@BindingParam("win") Window win) {
		// win.detach();
		Map map = new HashMap<>();
		map.put("sortid", sortid);
		map.put("tarpid", tapr_id);
		map.put("id", id);
		map.put("flag", "1");
		Window window = (Window) Executions.createComponents(
				"../EmBenefit/EmActy_backInfo.zul", null, map);
		window.doModal();
		if (map.get("flag") == "2") {
			win.detach();
		}
	}

	// 打开公司收款页面
	@Command
	public void lookbill(@BindingParam("model") EmWelfareModel billmodel) {
		// String billno = bll.getBillByCid(billmodel.getCid(),
		// billmodel.getOwnmonth());
		// if (billno == null || billno.equals("")) {
		// Messagebox.show("没有找到该公司的台帐数据","提示", Messagebox.OK,
		// Messagebox.ERROR);
		// } else {
		// Map map = new HashMap<>();
		// map.put("billNo", billno);
		// Window window = (Window) Executions.createComponents(
		// "/CoFinanceManage/Cfma_MonthlyBillView.zul", null, map);
		// window.doModal();
		// }
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cid", 1121);
		map.put("ownmonth", billmodel.getOwnmonth());
		Window window = (Window) Executions.createComponents(
				"/CoFinanceManage/Cfma_CollectMain.zul", null, map);
		window.doModal();
	}

	public EmActySuppilerGiftInfoModel getModel() {
		return model;
	}

	public void setModel(EmActySuppilerGiftInfoModel model) {
		this.model = model;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<EmWelfareModel> getListss() {
		return listss;
	}

	public void setListss(List<EmWelfareModel> listss) {
		this.listss = listss;
	}

	public BigDecimal getAllpri() {
		return allpri;
	}

	public void setAllpri(BigDecimal allpri) {
		this.allpri = allpri;
	}

	public List<EmWelfareModel> getNewlist() {
		return newlist;
	}

	public void setNewlist(List<EmWelfareModel> newlist) {
		this.newlist = newlist;
	}

	private String datechange(Date d) {
		String date = "";
		if (d != null && !d.equals("")) {
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = time.format(d);
		}
		return date;
	}
}
