package Controller.EmHouse;

import java.util.ArrayList;
import java.util.List;
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
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_addBll;
import Model.EmHouseChangeModel;
import Model.EmHouseCpp;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Util.CheckString;
import Util.UserInfo;

public class EmHouse_addnewController {
	private EmHouseChangeModel ecm = new EmHouseChangeModel();
	private List<EmHouseCpp> cpList = new ListModelList<>();
	private List<EmbaseModel> eblist = new ListModelList<>();
	private List<PubCodeConversionModel> listPC = new ListModelList<>();
	private List<String> ownmonthList = new ArrayList<>();
	private EmHouse_addBll bll = new EmHouse_addBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private CheckString cs = new CheckString();
	private Integer embaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("embaId").toString());

	// private Integer tapr_id =
	// Integer.valueOf(Executions.getCurrent().getArg().get("id").toString());
	private String username = UserInfo.getUsername();
	// private Integer embaId = 10017;

	// private Window win = (Window) Path.getComponent("/winadd");
	private Window winC;

	private String companyid;

	public EmHouse_addnewController() {
		
		setEblist(embaId);
		setOwnmonthList();
		setCpList(eblist.get(0).getCid(), eblist.get(0).getGid());
		setListPC();
		companyid = bll.getcompanyid(eblist.get(0).getGid());

		if (eblist.size() > 0) {
			
			ecm.setOwnmonth(Integer.valueOf(ownmonthList.get(0)));
			ecm.setCid(eblist.get(0).getCid());
			ecm.setGid(eblist.get(0).getGid());
			ecm.setEmhc_company(eblist.get(0).getCoba_company());
			ecm.setEmhc_shortname(eblist.get(0).getCoba_shortname());
			ecm.setEmhc_name(eblist.get(0).getEmba_name());
			ecm.setEmhc_companyid(companyid);
			ecm.setEmhc_mobile(eblist.get(0).getEmba_mobile());
			ecm.setEmhc_wifename(eblist.get(0).getEmba_wifename());
			ecm.setEmhc_wifeidcard(eblist.get(0).getEmba_wifeidcard());
			if (cpList.size() >0) {
				ecm.setEmhc_cpp2(cpList.get(0).getCpName());

			}
			ecm.setJudge(sbll.gjjaudit(
					ecm.getCid(),
					ecm.getGid(),
					Integer.valueOf(ownmonthList.get(1)),
					sbll.houseSingle(ecm.getGid(),
							Integer.valueOf(ownmonthList.get(1)))));

		}

	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		winC = winD;
		if (companyid == null) {
			Messagebox.show("该用户单位公积金账户信息不全!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			//winC.detach();
		}
		if (bll.houseupdate(eblist.get(0).getGid(),0)) {
			Messagebox.show("员工已参保!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			winC.detach();
		}
	}

	@Command("getcheck")
	public void getcheck(@BindingParam("a") Checkbox cb) {

		cb.setChecked(ecm.isJudge());

	}

	@Command("submit")
	public void submit() {

		Combobox cp = (Combobox) winC.getFellow("cpp");
		Combobox op = (Combobox) winC.getFellow("opp");
		Checkbox cka = (Checkbox) winC.getFellow("icheck");

		if (cp.getSelectedItem() == null) {
			Messagebox.show("请选择单位缴交比例!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		} else if (op.getSelectedItem() == null) {
			Messagebox.show("请选择个人缴交比例!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		} else if (ecm.getOwnmonth() == null) {
			Messagebox
					.show("请选择所属月份!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else if (ecm.getEmhc_radix() == 0) {
			Messagebox
					.show("缴存金额不正确!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else if (cka.isChecked() && ecm.getEmhc_remark() == null) {
			Messagebox
					.show("请填写申请原因!", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			if (cka.isChecked()) {
				ecm.setEmhc_ifdeclare(Integer
						.valueOf(cka.getValue().toString()));

			} else {
				/*
				Checkbox ckc = (Checkbox) winC.getFellow("iconfirm");
				if (ckc.isChecked()) {
					ecm.setEmhc_ifdeclare(Integer.valueOf(ckc.getValue()
							.toString()));

				} else {

					ecm.setEmhc_ifdeclare(0);
				}
				*/
				ecm.setEmhc_ifdeclare(0);
				
			}
			ecm.setEmhc_cpp(Double.valueOf(cp.getSelectedItem().getValue()
					.toString()));
			ecm.setEmhc_opp(Double.valueOf(op.getSelectedItem().getValue()
					.toString()));
			ecm.setEmhc_single(bll.getsingle(ecm.getGid()));
			ecm.setEmhc_addname(username);

			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {

								Integer i = bll.addEmhouse(ecm);

								if (i > 0) {
									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
								winC.detach();

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
		this.cpList = bll.getCpp(cid, gid,null);
	}

	public List<PubCodeConversionModel> getListPC() {
		return listPC;
	}

	public void setListPC() {
		this.listPC = bll.getpcInfo();
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList() {
		this.ownmonthList = bll.getOwnmonthList();
	}

	public List<EmbaseModel> getEblist() {
		return eblist;
	}

	public void setEblist(Integer id) {
		this.eblist = bll.getembaseInfo(id);
	}

}
