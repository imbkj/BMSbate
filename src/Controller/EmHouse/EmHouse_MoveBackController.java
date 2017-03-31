package Controller.EmHouse;

import impl.WorkflowCore.WfOperateImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Controller.EmSheBaocard.newExcelImpl;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmHouseUpdateModel;
import Model.PubCodeConversionModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmHouse.EmHouse_EditImpl;

public class EmHouse_MoveBackController {
	private EmHouseChangeModel ecm = new EmHouseChangeModel();
	private List<EmHouseCpp> cpList = new ListModelList<>();
	private List<PubCodeConversionModel> listPC = new ListModelList<>();
	private List<EmHouseUpdateModel> list = new ListModelList<>();

	private EmHouse_EditBll bll = new EmHouse_EditBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();

	private Integer gid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Integer cid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("cid").toString());

	private boolean Stop;

	private Window win;

	public EmHouse_MoveBackController() {
		list = bll.gethouseList(gid,null);
		if (list.size() > 0) {
			Stop = sbll.gjjOnair(list.get(0).getCid(), list.get(0).getGid(),
					list.get(0).getOwnmonth(), list.get(0).getEmhu_single());
			if (!Stop) {
				ecm.setCid(list.get(0).getCid());
				ecm.setGid(list.get(0).getGid());
				ecm.setOwnmonth(list.get(0).getOwnmonth());
				ecm.setEmhc_companyid(list.get(0).getEmhu_companyid());
				ecm.setEmhc_company(list.get(0).getEmhu_company());
				ecm.setEmhc_excompany(list.get(0).getEmhu_single().equals(1) ? list
						.get(0).getEmhu_company() : "深圳中智经济技术合作有限公司");
				ecm.setEmhc_excompanyid(list.get(0).getEmhu_companyid());
				ecm.setEmhc_shortname(list.get(0).getCoba_shortname());
				ecm.setEmhc_name(list.get(0).getEmhu_name());
				ecm.setEmhc_idcard(list.get(0).getEmhu_idcard());
				ecm.setEmhc_idcardclass(list.get(0).getEmhu_idcard());
				ecm.setEmhc_hj(list.get(0).getEmhu_hj());
				ecm.setEmhc_computerid(list.get(0).getEmhu_computerid());
				ecm.setEmhc_houseid(list.get(0).getEmhu_houseid());
				ecm.setEmhc_mobile(list.get(0).getEmhu_mobile());
				ecm.setEmhc_title(list.get(0).getEmhu_title());
				ecm.setEmhc_wifename(list.get(0).getEmhu_wifename());
				ecm.setEmhc_wifeidcard(list.get(0).getEmhu_wifeidcard());
				ecm.setEmhc_degree(list.get(0).getEmhu_degree());
				ecm.setEmhc_change("调入");
				ecm.setEmhc_radix(list.get(0).getEmhu_radix());
				ecm.setEmhc_cpp(list.get(0).getEmhu_cpp());
				ecm.setEmhc_opp(list.get(0).getEmhu_opp());
				ecm.setEmhc_cpp2((int) (list.get(0).getEmhu_cpp() * 100) + "%");
				ecm.setEmhc_opp2((int) (list.get(0).getEmhu_opp() * 100) + "%");
				ecm.setEmhc_single(list.get(0).getEmhu_single());
				ecm.setEmhc_ifdeclare(0);
				ecm.setEmhc_ifprogress(21);
				ecm.setEmhc_addname(UserInfo.getUsername());
				ecm.setEmhc_client(list.get(0).getCoba_client());
				setListPC();
			}
		}

	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		win = winD;
		if (Stop) {
			Messagebox.show("当月不允许提交数据.", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
			return;
		}
		if (list.size() > 0) {
			System.out.println(list.get(0).getEmhu_ifstop());
			if (list.get(0).getEmhu_ifstop().equals(0)) {
				Messagebox.show("该员工已有在册数据.", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
				return;
			} else if (list.get(0).getEmhu_ifstop().equals(2)) {
				Messagebox.show("该员工联网核查数据没通过.", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
				return;
			}
		} else {
			Messagebox.show("该员工没有在册数据.", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
			return;
		}

	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ecm.getGid() + "",
				"雇员服务中心");
	}

	@Command
	public void checkConfirm(@BindingParam("a") Checkbox ck) {
		if (ecm.getEmhc_companyid() == null) {
			ecm.setConfirm(true);
			ck.setChecked(true);
		} else {
			ecm.setConfirm(ck.isChecked());
		}

	}

	@Command("submit")
	public void submit() {

		if (ecm.getEmhc_houseid() == null) {
			Messagebox.show("请输入个人公积金编号!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (ecm.getEmhc_radix() == 0) {
			Messagebox
					.show("缴存金额不正确!", "操作提示", Messagebox.OK, Messagebox.ERROR);

		} else {

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
								Object[] obj = { "调回", ecm };
								Map<String, String> map = new HashMap();
								map.put("cid", ecm.getCid().toString());
								map.put("gid", ecm.getGid().toString());
								String[] str = wfs.AddTaskToNext(
										obj,
										"员工公积金变更",
										ecm.getOwnmonth() + ecm.getEmhc_name()
												+ "(" + ecm.getGid() + ")调回公积金",
										37, UserInfo.getUsername(), ecm.getEmhc_addname()+"添加数据",
										ecm.getCid(), "", map);
								Integer i = str[0].equals("1") ? 1 : 0;

								if (i > 0) {
									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);

									RedirectUtil util = new RedirectUtil();
									util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);

								}

							}
						}
					});

		}
	}

	public EmHouseChangeModel getEcm() {
		return ecm;
	}

	public void setEcm(EmHouseChangeModel ecm) {
		this.ecm = ecm;
	}

	public List<EmHouseCpp> getCpList() {
		return cpList;
	}

	public void setCpList(Integer cid, Integer gid) {
		this.cpList = bll.housecppList(cid, gid);
	}

	public List<PubCodeConversionModel> getListPC() {
		return listPC;
	}

	public void setListPC() {
		this.listPC = bll.getpcInfo();
	}

}
