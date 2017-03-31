package Controller.EmHouse;

import impl.MessageImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.MessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_EditImpl;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.SocialInsuranceClassInfoViewModel;
import Model.SysMessageModel;
import Model.loginroleModel;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.SocialInsuranceCalculator;
import Util.UserInfo;

public class Emhouse_ChangeInfoController {
	private EmHouseUpdateModel eum = new EmHouseUpdateModel();
	private List<EmHouseUpdateModel> list = new ListModelList<>();
	private List<Integer> ownmonthlist = new ListModelList<>();
	private List<EmHouseCpp> cpList = new ListModelList<>();
	private List<EmbaseModel> eblist = new ListModelList<>();
	private EmHouse_EditBll bll = new EmHouse_EditBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private Window win;
	private Integer dataId = 0;
	private Integer taprId = 0;
	private String companyid;
	private String cppName;
	private Double radix;
	private Double cpp;
	private Integer ownmonth;
	private boolean read = false;
	private boolean deadline = false;

	private Integer embaId = 0;
	private boolean close = false;
	private String errorMessage = "";

	public Emhouse_ChangeInfoController() {
		// 业务中心传入
		if (Executions.getCurrent().getArg().get("embaId") != null) {
			embaId = Integer.valueOf(Executions.getCurrent().getArg()
					.get("embaId").toString());
			setEblist(embaId);
			setList(eblist.get(0).getGid());
		}
		// 中心退回数据
		if (Executions.getCurrent().getArg().get("radix") != null) {
			if (Executions.getCurrent().getArg().get("gid") != null) {
				if (Executions.getCurrent().getArg().get("id") != null) {
					dataId = Integer.valueOf(Executions.getCurrent().getArg()
							.get("id").toString());
				}
				if (Executions.getCurrent().getArg().get("taprid") != null) {
					taprId = Integer.valueOf(Executions.getCurrent().getArg()
							.get("taprid").toString());
				}
				bll.updateData(Integer.valueOf(Executions.getCurrent().getArg()
						.get("gid").toString()));
				setList(Integer.valueOf(Executions.getCurrent().getArg()
						.get("gid").toString()));
			}
		}

		ownmonth = sbll.nowmonth();
		if (list.size() > 0) {
			deadline = bll.getlastDay(list.get(0).getGid(), list.get(0)
					.getEmhu_cpp());
			eum = list.get(0);
			eum.setOwnmonth(ownmonth);
			ownmonthlist.add(ownmonth);
			ownmonthlist.add(Integer.valueOf(DateStringChange
					.ownmonthAddoneMonth(ownmonth.toString())));
			companyid = eum.getEmhu_companyid();
			cppName = (int) (eum.getEmhu_cpp() * 100) + "%";
			radix = eum.getEmhu_radix();
			cpp = eum.getEmhu_cpp();
			setCpList(eum.getCid(), eum.getGid());

			if (Executions.getCurrent().getArg().get("radix") != null) {
				read = true;
				eum.setEmhu_radix(Double.valueOf(Executions.getCurrent()
						.getArg().get("radix").toString()));
			}

			if (companyid == null) {
				// Messagebox.show("该用户单位公积金账户信息不全!", "操作提示", Messagebox.OK,
				// Messagebox.ERROR);
				errorMessage = "该用户单位公积金账户信息不全!";
				close = true;
			} else {

				if (Integer.valueOf(DateStringChange.getOwnmonth()) > sbll
						.nowmonth()) {
					// Messagebox.show("当月公积金数据未更新.", "操作提示", Messagebox.OK,
					// Messagebox.ERROR);
					errorMessage = "当月公积金数据未更新!";
					close = true;
				} else {

					boolean b = sbll.gjjOnair(eum.getCid(), eum.getGid(),
							eum.getOwnmonth(), eum.getEmhu_single());
					if (b) {
						// Messagebox.show("当月不允许提交变更数据!", "操作提示",
						// Messagebox.OK,
						// Messagebox.ERROR);
						errorMessage = "当月不允许提交变更数据!";
						close = true;
					} else {
						boolean b2 = sbll.aduitData(eum.getGid(),
								eum.getOwnmonth());
						if (b2) {
							// Messagebox.show("员工有未处理变更数据!", "操作提示",
							// Messagebox.OK, Messagebox.ERROR);
							errorMessage = "员工有未处理变更数据!";
							close = true;
						}
						if (bll.houseChangeSP(eum)) {
							errorMessage = "员工已有未处理特殊变更!";
							close = true;
						}
					}
				}
			}
		} else {

			// Messagebox.show("员工未参保!", "操作提示", Messagebox.OK,
			// Messagebox.ERROR);
			errorMessage = "员工未参保!";
			close = true;
		}

	}

	@Command
	public void winC(@BindingParam("a") Window w) {
		win = w;
		if (close) {
			Messagebox.show(errorMessage, "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			RedirectUtil util = new RedirectUtil();
			util.refreshEntryThirdUrl("../EmHouse/Emhouse_Index.zul");
			util.refreshEmUrl("../EmHouse/Emhouse_Index.zul");
		}
	}

	@Command
	public void submit() {
		// Checkbox cbaduit = (Checkbox) winC.getFellow("aj");
		Combobox cp = (Combobox) win.getFellow("cpp");
		Textbox tb = (Textbox) win.getFellow("remark");

		if (eum.getEmhu_radix() <= 0) {
			Messagebox.show("基数不正确", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (cp.getSelectedItem() == null) {
			Messagebox.show("请选择比例", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			eum.setEmhu_cpp(Double.valueOf(cp.getSelectedItem().getValue()
					.toString()));
		}
		if (eum.getEmhu_cpp() > 0.12) {
			Messagebox.show("公积金比例范围是[0.05-0.12].", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (eum.getEmhu_radix().equals(radix) && eum.getEmhu_cpp().equals(cpp)) {
			Messagebox.show("请修改基数或者比例.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (tb.getValue().equals("")) {
			Messagebox
					.show("请输入申请原因.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			eum.setEmhu_remark(tb.getValue());
		}

		if (bll.houseChangeSP(eum)) {
			Messagebox.show("员工当月已有未处理特殊变更数据.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							WfBusinessService wfbs = new EmHouse_EditImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);

							SocialInsuranceCalculator sm = new SocialInsuranceCalculator();
							Integer id = sm.getSionId("深户员工", "深圳",
									"深圳中智经济技术合作有限公司");
							Date d = new Date();
							sm.setBcgjj(false);
							List<SocialInsuranceClassInfoViewModel> list2 = sm.getGjjItemFee(
									id, new BigDecimal(eum.getEmhu_radix()),
									new BigDecimal(eum.getEmhu_cpp()), d);
							BigDecimal fee = list2.get(0).getFee();
							BigDecimal radix = list2.get(0).getRadix();

							// fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP);
							radix = radix.setScale(2, BigDecimal.ROUND_HALF_UP);

							EmHouseChangeModel ecm = new EmHouseChangeModel();
							ecm.setGid(eum.getGid());
							ecm.setOwnmonth(eum.getOwnmonth());
							ecm.setEmhc_radix(radix.doubleValue());
							ecm.setEmhc_cpp(eum.getEmhu_cpp());
							ecm.setEmhc_ifdeclare(0);
							ecm.setEmhc_remark(eum.getEmhu_remark());
							ecm.setEmhc_addname(UserInfo.getUsername());
							Object[] obj = { "比例基数调整", ecm };
							String[] str = wfs.AddTaskToNext(obj,
									eum.getOwnmonth() + "员工公积金比例基数调整",
									eum.getEmhu_name() + "(" + eum.getGid()
											+ ")调整比例基数", 101,
									UserInfo.getUsername(), "", eum.getCid(),
									"");

							if (str[0].equals("1")) {
								EmHouseChangeModel em = new EmHouseChangeModel();
								em.setEmhc_id(dataId);
								em.setEmhc_tapr_id(taprId);
								Object[] obj2 = { "删除数据", em };
								String[] str2 = new String[5];
								if (em.getEmhc_tapr_id() != null) {
									str2 = wfs.StopTask(obj2,
											em.getEmhc_tapr_id(),
											UserInfo.getUsername(), "");
								} else {
									str2 = wfbs.StopOperate(obj2);
								}
								if (deadline) {
									email(list.get(0).getCid(), list.get(0).getGid(),
											list.get(0).getEmhu_company(),
											list.get(0).getEmhu_name());
								}
								bll.updateData(ecm.getGid());
								// bll.updateChangeAccount(Integer.valueOf(str[3]));
								Messagebox.show(str[1], "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);

								RedirectUtil util = new RedirectUtil();
								util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
							} else {
								Messagebox.show(str[1], "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}

						}
					}
				});

	}

	// 客服或中心在托收日3个工作日内提交数据时通知后道部门人员
	public void email(Integer cid, Integer gid, String company, String name) {
		// 参数解释，业务表名：tablename；业务表id:id
		MessageService msgservice = new MessageImpl("emhousechange", 0);

		String msgstr = "(" + cid.toString() + "," + gid.toString() + ")"
				+ company + name + "有公积金变更数据在托收日三个工作日内提交到后道.";

		String eTitle = "员工公积金数据操作提示";

		try {
			// 调用添加短信息方法

			List<loginroleModel> msglist = new ListModelList<>();
			msglist = bll.getuserlist("39,40,45,43");
			for (loginroleModel m : msglist) {
				SysMessageModel sysm = new SysMessageModel();
				sysm.setSyme_content(msgstr);// 短信内容
				sysm.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
				sysm.setSymr_name(m.getLog_name());// 收件人姓名
				sysm.setSymr_log_id(m.getLog_id());// 收件人姓名id
				sysm.setEmail(1);
				sysm.setEmailtitle(eTitle);
				msgservice.Add(sysm);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public EmHouseUpdateModel getEum() {
		return eum;
	}

	public void setEum(EmHouseUpdateModel eum) {
		this.eum = eum;
	}

	public List<EmHouseUpdateModel> getList() {
		return list;
	}

	public void setList(Integer gid) {
		this.list = bll.gethouseList(gid, null);
	}

	public List<EmHouseCpp> getCpList() {
		return cpList;
	}

	public void setCpList(Integer cid, Integer gid) {
		this.cpList = bll.housecppList(cid, gid);
	}

	public List<EmbaseModel> getEblist() {
		return eblist;
	}

	public void setEblist(Integer id) {
		this.eblist = bll.getEmbase(id);
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCppName() {
		return cppName;
	}

	public void setCppName(String cppName) {
		this.cppName = cppName;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public List<Integer> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<Integer> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

}
