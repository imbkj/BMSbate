package Controller.CoFinanceManage;

import impl.MessageImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
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
import dal.LoginDal;

import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.CoFinanceSortAccountssModel;
import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.SysMessageModel;

import Util.DateStringChange;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.CoFinanceManage.Cfma_CollectOperateBll;
import bll.CoFinanceManage.Cfma_SelBll;
import bll.EmFinanceManage.GatherInfoNewBll;

public class Cfma_CoGatheringController {
	private final String cid = Executions.getCurrent().getArg().get("cid")
			.toString();

	private final String company = Executions.getCurrent().getArg()
			.get("company").toString();

	private CoFinanceSortAccountssModel ssModel;

	private List<CoFinanceSortAccountssModel> ssModellist = new ArrayList<CoFinanceSortAccountssModel>(
			18);

	private CoFinanceCollectModel cfmodel = new CoFinanceCollectModel();
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
	private BigDecimal sumEx1;
	private String cfss_type = "";
	private Boolean kpfrist = false;
	private Boolean allin = false;

	private String message = "";
	private List<CoFinanceCollectModel> billNoList;
	private DecimalFormat df = new DecimalFormat("#.00");
	Cfma_CollectOperateBll ssbll = new Cfma_CollectOperateBll();
	Cfma_SelBll cbll = new Cfma_SelBll();
	CoBase_SelectBll cobasebll = new CoBase_SelectBll();

	private List<String> ownmonthList; // 所属月份
	private String ownmonth = "";

	public Cfma_CoGatheringController() {
		try {

			if (cid.equals(0) || cid.equals("")) {
				List<CoBaseModel> cobasemodellsit = new ArrayList<CoBaseModel>();

				cobasemodellsit = cobasebll
						.getCobaseinfo(" and coba_servicestate=1 and a.coba_company like '%"
								+ company + "%'");
				if (cobasemodellsit.size() == 0) {
					Messagebox.show("输入的公司名有误，系统无法查询到改公司，请核查！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);

					return;
				} else if (cobasemodellsit.size() > 1) {
					Messagebox.show("输入的公司名有误，系统查询到2个以上记录，请核查！", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					return;
				} else {
					cobasemodel = cobasemodellsit.get(0);
				}
			}

			else if (company.equals("")) {
				cobasemodel = cobasebll.getCobaseByCid(Integer.parseInt(cid));
			} else {
				cobasemodel = cobasebll.getCobaseByCid(Integer.parseInt(cid));
			}
			if (cobasemodel != null) {

				ownmonthList = cbll.getCoFinanceOwnmonth();
				username = UserInfo.getUsername();
				amount = BigDecimal.ZERO;
				sumEx = BigDecimal.ZERO;

				cfss_type = "非派遣";

				if (cbll.CoFinanceSortAccountssModellist(
						" and a.cid=" + cobasemodel.getCid().toString()).size() > 0) {
					ssModel = cbll.CoFinanceSortAccountssModellist(
							" and a.cid=" + cobasemodel.getCid().toString())
							.get(0);
					amount = ssModel.getCfco_TotalPaidIn();
					sumEx = ssModel.getCfta_Balance();
				}

			}

		} catch (Exception e) {
			throw e;
		}

	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// 提交收款
	@Command("SubmitCollectDis")
	public void SubmitCollectDis(@BindingParam("win") Window win,

	@ContextParam(ContextType.VIEW) Component view) {

		if (ownmonth.equals("") || cfss_type.equals("")) {
			Messagebox.show("月份或则收款类型不能为空！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			cfmodel.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			cfmodel.setCfco_TotalPaidIn(sumEx1);
			cfmodel.setOwnmonth(Integer.parseInt(ownmonth));
			cfmodel.setCfco_cfmb_number("0");
			cfmodel.setCfco_addname(UserInfo.getUsername());
			cfmodel.setCfco_remark(remark);

			ssModellist.clear();
			CoFinanceSortAccountssModel skModel = new CoFinanceSortAccountssModel();
			skModel.setCfss_cpac_name("服务费");
			skModel.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel.setCfss_Receivable(cfss_Receivable1);
			skModel.setCfss_state(1);
			skModel.setCfss_fpstate(0);
			skModel.setCfss_yystate(0);
			skModel.setCfss_fpfrist(kpfrist);
			skModel.setCfss_addname(UserInfo.getUsername());
			skModel.setOwnmonth(Integer.parseInt(ownmonth));
			skModel.setRemark(remark);
			skModel.setCfss_type(cfss_type);
			skModel.setCfss_allin(allin);
			ssModellist.add(skModel);

			CoFinanceSortAccountssModel skModel1 = new CoFinanceSortAccountssModel();
			skModel1.setCfss_cpac_name("档案保管费");
			skModel1.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel1.setCfss_Receivable(cfss_Receivable2);
			skModel1.setCfss_state(1);
			skModel1.setCfss_fpstate(0);
			skModel1.setCfss_yystate(0);
			skModel1.setCfss_fpfrist(kpfrist);
			skModel1.setCfss_addname(UserInfo.getUsername());
			skModel1.setOwnmonth(Integer.parseInt(ownmonth));
			skModel1.setRemark(remark);
			skModel1.setCfss_type(cfss_type);
			skModel1.setCfss_allin(allin);
			ssModellist.add(skModel1);

			CoFinanceSortAccountssModel skModel2 = new CoFinanceSortAccountssModel();
			skModel2.setCfss_cpac_name("社保费");
			skModel2.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel2.setCfss_Receivable(cfss_Receivable3);
			skModel2.setCfss_state(1);
			skModel2.setCfss_fpstate(0);
			skModel2.setCfss_yystate(0);
			skModel2.setCfss_fpfrist(kpfrist);
			skModel2.setCfss_addname(UserInfo.getUsername());
			skModel2.setOwnmonth(Integer.parseInt(ownmonth));
			skModel2.setRemark(remark);
			skModel2.setCfss_type(cfss_type);
			skModel2.setCfss_allin(allin);
			ssModellist.add(skModel2);

			CoFinanceSortAccountssModel skModel3 = new CoFinanceSortAccountssModel();
			skModel3.setCfss_cpac_name("活动费");
			skModel3.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel3.setCfss_Receivable(cfss_Receivable4);
			skModel3.setCfss_state(1);
			skModel3.setCfss_fpstate(0);
			skModel3.setCfss_yystate(0);
			skModel3.setCfss_fpfrist(kpfrist);
			skModel3.setCfss_addname(UserInfo.getUsername());
			skModel3.setOwnmonth(Integer.parseInt(ownmonth));
			skModel3.setRemark(remark);
			skModel3.setCfss_type(cfss_type);
			skModel3.setCfss_allin(allin);
			ssModellist.add(skModel3);

			CoFinanceSortAccountssModel skModel4 = new CoFinanceSortAccountssModel();
			skModel4.setCfss_cpac_name("体检费");
			skModel4.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel4.setCfss_Receivable(cfss_Receivable5);
			skModel4.setCfss_state(1);
			skModel4.setCfss_fpstate(0);
			skModel4.setCfss_yystate(0);
			skModel4.setCfss_fpfrist(kpfrist);
			skModel4.setCfss_addname(UserInfo.getUsername());
			skModel4.setOwnmonth(Integer.parseInt(ownmonth));
			skModel4.setRemark(remark);
			skModel4.setCfss_type(cfss_type);
			skModel4.setCfss_allin(allin);
			ssModellist.add(skModel4);

			CoFinanceSortAccountssModel skModel5 = new CoFinanceSortAccountssModel();
			skModel5.setCfss_cpac_name("商保费");
			skModel5.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel5.setCfss_Receivable(cfss_Receivable6);
			skModel5.setCfss_state(1);
			skModel5.setCfss_fpstate(0);
			skModel5.setCfss_yystate(0);
			skModel5.setCfss_fpfrist(kpfrist);
			skModel5.setCfss_addname(UserInfo.getUsername());
			skModel5.setOwnmonth(Integer.parseInt(ownmonth));
			skModel5.setRemark(remark);
			skModel5.setCfss_type(cfss_type);
			skModel5.setCfss_allin(allin);
			ssModellist.add(skModel5);

			CoFinanceSortAccountssModel skModel6 = new CoFinanceSortAccountssModel();
			skModel6.setCfss_cpac_name("书报费");
			skModel6.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel6.setCfss_Receivable(cfss_Receivable7);
			skModel6.setCfss_state(1);
			skModel6.setCfss_fpstate(0);
			skModel6.setCfss_yystate(0);
			skModel6.setCfss_fpfrist(kpfrist);
			skModel6.setCfss_addname(UserInfo.getUsername());
			skModel6.setOwnmonth(Integer.parseInt(ownmonth));
			skModel6.setRemark(remark);
			skModel6.setCfss_type(cfss_type);
			skModel6.setCfss_allin(allin);
			ssModellist.add(skModel6);

			CoFinanceSortAccountssModel skModel7 = new CoFinanceSortAccountssModel();
			skModel7.setCfss_cpac_name("税后工资");
			skModel7.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel7.setCfss_Receivable(cfss_Receivable8);
			skModel7.setCfss_state(1);
			skModel7.setCfss_fpstate(0);
			skModel7.setCfss_yystate(0);
			skModel7.setCfss_fpfrist(kpfrist);
			skModel7.setCfss_addname(UserInfo.getUsername());
			skModel7.setOwnmonth(Integer.parseInt(ownmonth));
			skModel7.setRemark(remark);
			skModel7.setCfss_type(cfss_type);
			skModel7.setCfss_allin(allin);
			ssModellist.add(skModel7);

			CoFinanceSortAccountssModel skModel8 = new CoFinanceSortAccountssModel();
			skModel8.setCfss_cpac_name("个调税");
			skModel8.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel8.setCfss_Receivable(cfss_Receivable9);
			skModel8.setCfss_state(1);
			skModel8.setCfss_fpstate(0);
			skModel8.setCfss_yystate(0);
			skModel8.setCfss_fpfrist(kpfrist);
			skModel8.setCfss_addname(UserInfo.getUsername());
			skModel8.setOwnmonth(Integer.parseInt(ownmonth));
			skModel8.setRemark(remark);
			skModel8.setCfss_type(cfss_type);
			skModel8.setCfss_allin(allin);
			ssModellist.add(skModel8);

			CoFinanceSortAccountssModel skModel9 = new CoFinanceSortAccountssModel();
			skModel9.setCfss_cpac_name("户口");
			skModel9.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel9.setCfss_Receivable(cfss_Receivable10);
			skModel9.setCfss_state(1);
			skModel9.setCfss_fpstate(0);
			skModel9.setCfss_yystate(0);
			skModel9.setCfss_fpfrist(kpfrist);
			skModel9.setCfss_addname(UserInfo.getUsername());
			skModel9.setOwnmonth(Integer.parseInt(ownmonth));
			skModel9.setRemark(remark);
			skModel9.setCfss_type(cfss_type);
			skModel9.setCfss_allin(allin);
			ssModellist.add(skModel9);

			CoFinanceSortAccountssModel skModel10 = new CoFinanceSortAccountssModel();
			skModel10.setCfss_cpac_name("财务服务费");
			skModel10.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel10.setCfss_Receivable(cfss_Receivable11);
			skModel10.setCfss_state(1);
			skModel10.setCfss_fpstate(0);
			skModel10.setCfss_yystate(0);
			skModel10.setCfss_fpfrist(kpfrist);
			skModel10.setCfss_addname(UserInfo.getUsername());
			skModel10.setOwnmonth(Integer.parseInt(ownmonth));
			skModel10.setRemark(remark);
			skModel10.setCfss_type(cfss_type);
			skModel10.setCfss_allin(allin);
			ssModellist.add(skModel10);

			CoFinanceSortAccountssModel skModel11 = new CoFinanceSortAccountssModel();
			skModel11.setCfss_cpac_name("商务服务费");
			skModel11.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel11.setCfss_Receivable(cfss_Receivable12);
			skModel11.setCfss_state(1);
			skModel11.setCfss_fpstate(0);
			skModel11.setCfss_yystate(0);
			skModel11.setCfss_fpfrist(kpfrist);
			skModel11.setCfss_addname(UserInfo.getUsername());
			skModel11.setOwnmonth(Integer.parseInt(ownmonth));
			skModel11.setRemark(remark);
			skModel11.setCfss_type(cfss_type);
			skModel11.setCfss_allin(allin);
			ssModellist.add(skModel11);

			CoFinanceSortAccountssModel skModel12 = new CoFinanceSortAccountssModel();
			skModel12.setCfss_cpac_name("招聘服务费");
			skModel12.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel12.setCfss_Receivable(cfss_Receivable13);
			skModel12.setCfss_state(1);
			skModel12.setCfss_fpstate(0);
			skModel12.setCfss_yystate(0);
			skModel12.setCfss_fpfrist(kpfrist);
			skModel12.setCfss_addname(UserInfo.getUsername());
			skModel12.setOwnmonth(Integer.parseInt(ownmonth));
			skModel12.setRemark(remark);
			skModel12.setCfss_type(cfss_type);
			skModel12.setCfss_allin(allin);
			ssModellist.add(skModel12);

			CoFinanceSortAccountssModel skModel13 = new CoFinanceSortAccountssModel();
			skModel13.setCfss_cpac_name("居住证");
			skModel13.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel13.setCfss_Receivable(cfss_Receivable14);
			skModel13.setCfss_state(1);
			skModel13.setCfss_fpstate(0);
			skModel13.setCfss_yystate(0);
			skModel13.setCfss_fpfrist(kpfrist);
			skModel13.setCfss_addname(UserInfo.getUsername());
			skModel13.setOwnmonth(Integer.parseInt(ownmonth));
			skModel13.setRemark(remark);
			skModel13.setCfss_type(cfss_type);
			skModel13.setCfss_allin(allin);
			ssModellist.add(skModel13);

			CoFinanceSortAccountssModel skModel14 = new CoFinanceSortAccountssModel();
			skModel14.setCfss_cpac_name("劳动保障卡");
			skModel14.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel14.setCfss_Receivable(cfss_Receivable15);
			skModel14.setCfss_state(1);
			skModel14.setCfss_fpstate(0);
			skModel14.setCfss_yystate(0);
			skModel14.setCfss_fpfrist(kpfrist);
			skModel14.setCfss_addname(UserInfo.getUsername());
			skModel14.setOwnmonth(Integer.parseInt(ownmonth));
			skModel14.setRemark(remark);
			skModel14.setCfss_type(cfss_type);
			skModel14.setCfss_allin(allin);
			ssModellist.add(skModel14);
			

			CoFinanceSortAccountssModel skModel15 = new CoFinanceSortAccountssModel();
			skModel15.setCfss_cpac_name("残保金");
			skModel15.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel15.setCfss_Receivable(cfss_Receivable16);
			skModel15.setCfss_state(1);
			skModel15.setCfss_fpstate(0);
			skModel15.setCfss_yystate(0);
			skModel15.setCfss_fpfrist(kpfrist);
			skModel15.setCfss_addname(UserInfo.getUsername());
			skModel15.setOwnmonth(Integer.parseInt(ownmonth));
			skModel15.setRemark(remark);
			skModel15.setCfss_type(cfss_type);
			skModel15.setCfss_allin(allin);
			ssModellist.add(skModel15);

			CoFinanceSortAccountssModel skModel16 = new CoFinanceSortAccountssModel();
			skModel16.setCfss_cpac_name("其它");
			skModel16.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel16.setCfss_Receivable(cfss_Receivable17);
			skModel16.setCfss_state(1);
			skModel16.setCfss_fpstate(0);
			skModel16.setCfss_yystate(0);
			skModel16.setCfss_fpfrist(kpfrist);
			skModel16.setCfss_addname(UserInfo.getUsername());
			skModel16.setOwnmonth(Integer.parseInt(ownmonth));
			skModel16.setRemark(remark);
			skModel16.setCfss_type(cfss_type);
			skModel16.setCfss_allin(allin);
			ssModellist.add(skModel16);

			CoFinanceSortAccountssModel skModel17 = new CoFinanceSortAccountssModel();
			skModel17.setCfss_cpac_name("住房公积金");
			skModel17.setCid(Integer.parseInt(cobasemodel.getCid().toString()));
			skModel17.setCfss_Receivable(cfss_Receivable18);
			skModel17.setCfss_state(1);
			skModel17.setCfss_fpstate(0);
			skModel17.setCfss_yystate(0);
			skModel17.setCfss_fpfrist(kpfrist);
			skModel17.setCfss_addname(UserInfo.getUsername());
			skModel17.setOwnmonth(Integer.parseInt(ownmonth));
			skModel17.setRemark(remark);
			skModel17.setCfss_type(cfss_type);
			skModel17.setCfss_allin(allin);
			ssModellist.add(skModel17);

			Cfma_CollectOperateBll opbll = new Cfma_CollectOperateBll();

			int i = opbll.addCollectToCompany(cfmodel.getCid(),
					cfmodel.getCfco_TotalPaidIn(), cfmodel.getCfco_addname(),
					remark, Integer.parseInt(ownmonth), kpfrist);
			if (!kpfrist) {
				if (i > 1) {

					// 发邮件和系统短信
					MessageService msgservice = new MessageImpl("cobase",
							cobasemodel.getCoba_id());
					LoginDal d = new LoginDal();
					SysMessageModel sysm = new SysMessageModel();
					String msgstr = "财务" + UserInfo.getUsername() + "收到公司编号:("
							+ cobasemodel.getCid() + ")"
							+ cobasemodel.getCoba_company() + "的" + "来款"
							+ cfmodel.getCfco_TotalPaidIn() + "元,请及时领款！";
					sysm.setSyme_title("领款通知");
					sysm.setSyme_content(msgstr);// 短信内容
					sysm.setSyme_log_id(d.getUserIDByname(UserInfo
							.getUsername()));// 发件人id
					sysm.setEmail(1);
					sysm.setEmailtitle(msgstr);

					if (!"6".equals(d.getdepIDByname(
							cobasemodel.getCoba_client()).toString())) {
						sysm.setSymr_name(cobasemodel.getCoba_client());// 收件人姓名
						sysm.setSymr_log_id(d.getUserIDByname(cobasemodel
								.getCoba_client()));// ;

						msgservice.Add(sysm);

						if (!"".equals(cobasemodel.getCoba_gzaddname())
								&& cfss_Receivable8.compareTo(BigDecimal.ZERO) > 0) {

							sysm.setSymr_name(cobasemodel.getCoba_gzaddname());// 收件人姓名
							sysm.setSymr_log_id(d.getUserIDByname(cobasemodel
									.getCoba_gzaddname()));// ;
							msgservice.Add(sysm);
						}

					} else {

						if (!"".equals(cobasemodel.getCoba_gzaddname())
								&& cfss_Receivable8.compareTo(BigDecimal.ZERO) > 0) {
							sysm.setSymr_name(cobasemodel.getCoba_gzaddname());// 收件人姓名
							sysm.setSymr_log_id(d.getUserIDByname(cobasemodel
									.getCoba_gzaddname()));// ;
							msgservice.Add(sysm);
						}
					}

				}
			}
			int re = 0;
			re = ssbll.addcogathering(ssModellist, i);
			if (re > 0) {

				// 计算并插入税金表
				GatherInfoNewBll ganewbll = new GatherInfoNewBll();
				ganewbll.insertCollectListssNew(String.valueOf(i));

				Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);

				win.detach();
			} else {
				Messagebox.show("添加失败！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
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
	@NotifyChange({ "sumEx1" })
	public void checksum() {

		sumEx1 = BigDecimal.ZERO;
		// sumEx=ssModel.getCfta_Balance();
		sumEx1 = cfss_Receivable1.add(cfss_Receivable2).add(cfss_Receivable3)
				.add(cfss_Receivable4).add(cfss_Receivable5)
				.add(cfss_Receivable6).add(cfss_Receivable7)
				.add(cfss_Receivable8).add(cfss_Receivable9)
				.add(cfss_Receivable10).add(cfss_Receivable11)
				.add(cfss_Receivable12).add(cfss_Receivable13)
				.add(cfss_Receivable14).add(cfss_Receivable15)
				.add(cfss_Receivable16).add(cfss_Receivable17)
				.add(cfss_Receivable18);

		// sumEx= sumEx.subtract(sumEx1);
		// if (sumEx.compareTo(BigDecimal.ZERO)==-1)
		// {
		// Messagebox.show("未分配额度不够，请核对！", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// }

	}

	public String getCompany() {
		return company;
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

	public String getCid() {
		return cid;
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
