package Controller.CoFinanceManage;

import impl.MessageImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;

import service.MessageService;
import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceSortAccountssModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.CoFinanceManage.Cfma_CollectOperateBll;
import bll.CoFinanceManage.Cfma_SelBll;
import bll.EmFinanceManage.GatherInfoNewBll;
import dal.LoginDal;

public class Cfma_CoGatheringmodController {

	private String cfss_cfso_id = Executions.getCurrent().getArg()
			.get("cfss_cfso_id").toString();

	private List<CoFinanceSortAccountssModel> ssModellist1 = new ArrayList<CoFinanceSortAccountssModel>();
	private CoFinanceSortAccountssModel ssModel;

	// private List<CoFinanceSortAccountssModel> ssModellist= new
	// ArrayList<CoFinanceSortAccountssModel>(18);
	private CoBaseModel cobasemodel;

	private String username;
	private BigDecimal amount = BigDecimal.ZERO; // 总额
	private BigDecimal sumEx = BigDecimal.ZERO; // 未分配额

	private BigDecimal cfss_Receivable1 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable2 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable3 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable4 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable5 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable6 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable7 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable8 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable9 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable10 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable11 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable12 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable13 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable14 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable15 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable16 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable17 = BigDecimal.ZERO;
	private BigDecimal cfss_Receivable18 = BigDecimal.ZERO;
	private String remark;
	private BigDecimal sumEx1 = BigDecimal.ZERO;
	private BigDecimal summod = BigDecimal.ZERO;
	private int cid = 0;
	private String cfss_type = "";
	private Boolean kpfrist;
	private Boolean allin = false;

	private CoFinanceCollectModel cfmodel = new CoFinanceCollectModel();

	private String message = "";
	private List<CoFinanceCollectModel> billNoList;
	private DecimalFormat df = new DecimalFormat("#.00");
	Cfma_CollectOperateBll ssbll = new Cfma_CollectOperateBll();
	Cfma_SelBll cbll = new Cfma_SelBll();
	CoBase_SelectBll cobasebll = new CoBase_SelectBll();
	Cfma_CollectOperateBll opbll = new Cfma_CollectOperateBll();

	private List<String> ownmonthList; // 所属月份
	private String ownmonth;

	public Cfma_CoGatheringmodController() {

		ssModellist1 = cbll
				.CoFinanceSortAccountssModellist1(" and cfss_cfso_id= '"
						+ cfss_cfso_id + "'");

		remark = ssModellist1.get(0).getRemark();
		cid = ssModellist1.get(0).getCid();
		ownmonth = String.valueOf(ssModellist1.get(0).getOwnmonth());
		cfss_type = ssModellist1.get(0).getCfss_type();
		kpfrist = ssModellist1.get(0).getCfss_fpfrist();
		allin=ssModellist1.get(0).getCfss_allin();
		for (int i = 0; i < ssModellist1.size(); i++) {
			sumEx1 = sumEx1.add(ssModellist1.get(i).getCfss_Receivable());
		}

		cobasemodel = cobasebll.getCobaseByCid(cid);

		ownmonthList = cbll.getCoFinanceOwnmonth();
		username = UserInfo.getUsername();
		amount = BigDecimal.ZERO;
		sumEx = BigDecimal.ZERO;

		ssModel = cbll.CoFinanceSortAccountssModellist(" and a.cid=" + cid)
				.get(0);

		amount = ssModel.getCfco_TotalPaidIn();
		// sumEx=ssModel.getCfta_Balance();
		for (int i = 0; i < ssModellist1.size(); i++) {
			summod = summod.add(ssModellist1.get(i).getCfss_Receivable());
		}

	}

	// 提交收款
	@Command("SubmitCollectDis")
	public void SubmitCollectDis(@BindingParam("win") Window win,

	@ContextParam(ContextType.VIEW) Component view) {

		cfmodel.setCfco_id(Integer.parseInt(cfss_cfso_id));
		cfmodel.setCid(cid);
		cfmodel.setCfco_addname(UserInfo.getUsername());
		cfmodel.setCfco_TotalPaidIn(sumEx1);
		cfmodel.setCfco_remark(remark);
		cfmodel.setOwnmonth(Integer.parseInt(ownmonth));

		if (ssModellist1.get(0).getCfss_fpfrist() && !kpfrist) {

			// int y = opbll.addCollectToCompany(cfmodel.getCid(),
			// cfmodel.getCfco_TotalPaidIn(),cfmodel.getCfco_addname(),
			// remark,Integer.parseInt(ownmonth),kpfrist);
			// if (y>0)
			// {
			// 发邮件和系统短信
			MessageService msgservice = new MessageImpl("cobase",
					cobasemodel.getCoba_id());
			LoginDal d = new LoginDal();
			SysMessageModel sysm = new SysMessageModel();
			String msgstr = "财务" + UserInfo.getUsername() + "收到公司:"
					+ cobasemodel.getCoba_company() + "的" + "来款"
					+ cfmodel.getCfco_TotalPaidIn() + "元,请及时领款！";
			sysm.setSyme_title("领款通知");
			sysm.setSyme_content(msgstr);// 短信内容
			sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));// 发件人id
			sysm.setEmail(1);
			sysm.setEmailtitle(msgstr);

			if (!"6".equals(d.getdepIDByname(cobasemodel.getCoba_client())
					.toString())) {
				sysm.setSymr_name(cobasemodel.getCoba_client());// 收件人姓名
				sysm.setSymr_log_id(d.getUserIDByname(cobasemodel
						.getCoba_client()));// ;

				msgservice.Add(sysm);

				if (!"".equals(cobasemodel.getCoba_gzaddname())) {
					sysm.setSymr_name(cobasemodel.getCoba_gzaddname());// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname(cobasemodel
							.getCoba_gzaddname()));// ;
					msgservice.Add(sysm);
				}

			} else {

				if (!"".equals(cobasemodel.getCoba_gzaddname())) {
					sysm.setSymr_name(cobasemodel.getCoba_gzaddname());// 收件人姓名
					sysm.setSymr_log_id(d.getUserIDByname(cobasemodel
							.getCoba_gzaddname()));// ;
					msgservice.Add(sysm);
				}
			}
		}

		int i = opbll.updateCollectToCompany(cfmodel.getCfco_id(),
				cfmodel.getCid(), cfmodel.getCfco_TotalPaidIn(),
				cfmodel.getCfco_addname(), remark, cfmodel.getOwnmonth(),
				kpfrist);

		if (i > 0) {

			int re = 0;
			re = ssbll.modcogathering(ssModellist1, remark, ownmonth,
					cfss_type, kpfrist,allin);

			if (re > 0) {

				// 计算并插入税金表
				GatherInfoNewBll ganewbll = new GatherInfoNewBll();
				ganewbll.insertCollectListssNew(cfss_cfso_id);

				Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);

				win.detach();
			} else {
				Messagebox.show("添加失败！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("添加失败！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 格式化收款金额(只保留两位小数)
	@Command("formatAmount")
	@NotifyChange("amount")
	public void formatAmount() {
		amount = new BigDecimal(df.format(amount));
	}

	// 检查收款的总额是否合法
	private boolean checkAmount(Popup pop, Component com) {
		boolean bool = false;
		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			message = "您并未领取金额，无需确认。";
			BindUtils.postNotifyChange(null, null, this, "message");
			pop.open(com);
		} else {
			bool = true;
		}
		return bool;
	}

	@Command("checksum")
	@NotifyChange({ "sumEx", "sumEx1" })
	public void checksum() {

		sumEx1 = BigDecimal.ZERO;
		// sumEx=ssModel.getCfta_Balance().add(summod);

		for (int i = 0; i < ssModellist1.size(); i++) {
			sumEx1 = sumEx1.add(ssModellist1.get(i).getCfss_Receivable());
		}

		// sumEx= sumEx.subtract(sumEx1);
		// if (sumEx.compareTo(BigDecimal.ZERO)==-1)
		// {
		// Messagebox.show("未分配额度不够，请核对！", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// }
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CoFinanceSortAccountssModel getSsModel() {
		return ssModel;
	}

	public void setSsModel(CoFinanceSortAccountssModel ssModel) {
		this.ssModel = ssModel;
	}

	public BigDecimal getCfss_Receivable1() {
		return cfss_Receivable1;
	}

	public void setCfss_Receivable1(BigDecimal cfss_Receivable1) {
		this.cfss_Receivable1 = cfss_Receivable1;
	}

	public BigDecimal getCfss_Receivable2() {
		return cfss_Receivable2;
	}

	public void setCfss_Receivable2(BigDecimal cfss_Receivable2) {
		this.cfss_Receivable2 = cfss_Receivable2;
	}

	public BigDecimal getCfss_Receivable3() {
		return cfss_Receivable3;
	}

	public void setCfss_Receivable3(BigDecimal cfss_Receivable3) {
		this.cfss_Receivable3 = cfss_Receivable3;
	}

	public BigDecimal getCfss_Receivable4() {
		return cfss_Receivable4;
	}

	public void setCfss_Receivable4(BigDecimal cfss_Receivable4) {
		this.cfss_Receivable4 = cfss_Receivable4;
	}

	public BigDecimal getCfss_Receivable5() {
		return cfss_Receivable5;
	}

	public void setCfss_Receivable5(BigDecimal cfss_Receivable5) {
		this.cfss_Receivable5 = cfss_Receivable5;
	}

	public BigDecimal getCfss_Receivable6() {
		return cfss_Receivable6;
	}

	public void setCfss_Receivable6(BigDecimal cfss_Receivable6) {
		this.cfss_Receivable6 = cfss_Receivable6;
	}

	public BigDecimal getCfss_Receivable7() {
		return cfss_Receivable7;
	}

	public void setCfss_Receivable7(BigDecimal cfss_Receivable7) {
		this.cfss_Receivable7 = cfss_Receivable7;
	}

	public BigDecimal getCfss_Receivable8() {
		return cfss_Receivable8;
	}

	public void setCfss_Receivable8(BigDecimal cfss_Receivable8) {
		this.cfss_Receivable8 = cfss_Receivable8;
	}

	public BigDecimal getCfss_Receivable9() {
		return cfss_Receivable9;
	}

	public void setCfss_Receivable9(BigDecimal cfss_Receivable9) {
		this.cfss_Receivable9 = cfss_Receivable9;
	}

	public BigDecimal getCfss_Receivable10() {
		return cfss_Receivable10;
	}

	public void setCfss_Receivable10(BigDecimal cfss_Receivable10) {
		this.cfss_Receivable10 = cfss_Receivable10;
	}

	public BigDecimal getCfss_Receivable11() {
		return cfss_Receivable11;
	}

	public void setCfss_Receivable11(BigDecimal cfss_Receivable11) {
		this.cfss_Receivable11 = cfss_Receivable11;
	}

	public BigDecimal getCfss_Receivable12() {
		return cfss_Receivable12;
	}

	public void setCfss_Receivable12(BigDecimal cfss_Receivable12) {
		this.cfss_Receivable12 = cfss_Receivable12;
	}

	public BigDecimal getCfss_Receivable13() {
		return cfss_Receivable13;
	}

	public void setCfss_Receivable13(BigDecimal cfss_Receivable13) {
		this.cfss_Receivable13 = cfss_Receivable13;
	}

	public BigDecimal getCfss_Receivable14() {
		return cfss_Receivable14;
	}

	public void setCfss_Receivable14(BigDecimal cfss_Receivable14) {
		this.cfss_Receivable14 = cfss_Receivable14;
	}

	public BigDecimal getCfss_Receivable15() {
		return cfss_Receivable15;
	}

	public void setCfss_Receivable15(BigDecimal cfss_Receivable15) {
		this.cfss_Receivable15 = cfss_Receivable15;
	}

	public BigDecimal getCfss_Receivable16() {
		return cfss_Receivable16;
	}

	public BigDecimal getSumEx1() {
		return sumEx1;
	}

	public void setSumEx1(BigDecimal sumEx1) {
		this.sumEx1 = sumEx1;
	}

	public void setCfss_Receivable16(BigDecimal cfss_Receivable16) {
		this.cfss_Receivable16 = cfss_Receivable16;
	}

	public BigDecimal getCfss_Receivable17() {
		return cfss_Receivable17;
	}

	public void setCfss_Receivable17(BigDecimal cfss_Receivable17) {
		this.cfss_Receivable17 = cfss_Receivable17;
	}

	public BigDecimal getCfss_Receivable18() {
		return cfss_Receivable18;
	}

	public void setCfss_Receivable18(BigDecimal cfss_Receivable18) {
		this.cfss_Receivable18 = cfss_Receivable18;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public void setSumEx(BigDecimal sumEx) {
		this.sumEx = sumEx;
	}

	public List<CoFinanceCollectModel> getBillNoList() {
		return billNoList;
	}

	public void setBillNoList(List<CoFinanceCollectModel> billNoList) {
		this.billNoList = billNoList;
	}

	public String getUsername() {
		return username;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSumEx() {
		return sumEx;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<String> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public CoBaseModel getCobasemodel() {
		return cobasemodel;
	}

	public void setCobasemodel(CoBaseModel cobasemodel) {
		this.cobasemodel = cobasemodel;
	}

	public List<CoFinanceSortAccountssModel> getSsModellist1() {
		return ssModellist1;
	}

	public void setSsModellist1(List<CoFinanceSortAccountssModel> ssModellist1) {
		this.ssModellist1 = ssModellist1;
	}

	public String getCfss_type() {
		return cfss_type;
	}

	public void setCfss_type(String cfss_type) {
		this.cfss_type = cfss_type;
	}

	public Boolean getKpfrist() {
		return kpfrist;
	}

	public void setKpfrist(Boolean kpfrist) {
		this.kpfrist = kpfrist;
	}

	public Boolean getAllin() {
		return allin;
	}

	public void setAllin(Boolean allin) {
		this.allin = allin;
	}

}
