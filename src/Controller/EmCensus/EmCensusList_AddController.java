package Controller.EmCensus;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import Model.EmCensusModel;
import Model.EmDhModel;
import Model.EmbaseModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmCensus_SelectBll;

public class EmCensusList_AddController {
	private EmCensus_OperateBll bll = new EmCensus_OperateBll();
	private EmCensus_SelectBll dbll = new EmCensus_SelectBll();
	private EmbaseModel embamodel = (EmbaseModel) Executions.getCurrent()
			.getArg().get("model");
	private EmDhModel dhmodel = (EmDhModel) Executions.getCurrent().getArg()
			.get("dhmodel");
	private Integer gid = (Integer) Executions.getCurrent().getArg().get("gid");
	private List<EmbaseModel> embaselist = dbll
			.getEmbaseInfo(" and gid=" + gid);
	private boolean addressvis = false;
	private String address = "";

	public EmCensusList_AddController() {
		if (!embaselist.isEmpty()) {
			embamodel = embaselist.get(0);
		}
		if (dhmodel != null && dhmodel.getEmdh_zhtype() == null) {
			dhmodel.setEmdh_zhtype("");
		}
	}

	// 新增提交事件
	@Command
	public void addemCensus(@BindingParam("type") String type,
			@BindingParam("address") String address,
			@BindingParam("wt") String wt,
			@BindingParam("family") String family,
			@BindingParam("idcard") String idcard,
			@BindingParam("outcase") String outcase,
			@BindingParam("docGrid") Grid docGrid,
			@BindingParam("win") Window win,
			@BindingParam("remark") String remark,
			@BindingParam("addtype") String addtype) {
		if (type == null||type.equals("")) {
			Messagebox.show("请选择账户类型", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (type.equals("中智集体户")) {
			if (address == null || address.equals("")) {
				Messagebox.show("请选择派出所", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		if (wt == null || wt.equals("")) {
			Messagebox.show("请选择入户方式", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (wt.equals("出生入户")) {
			if (family == null || family.equals("")) {
				Messagebox.show("请填写家属姓名", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		if (wt.equals("夫妻投靠") || wt.equals("家属随迁")) {
			if (family == null || family.equals("")) {
				Messagebox.show("请填写家属姓名", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (idcard == null || idcard.equals("")) {
				Messagebox.show("请填写家属身份证号", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		
		int t = 0;
		String readidcard = embamodel.getEmba_idcard();
		EmCensusModel model = new EmCensusModel();
		if (wt.equals("随迁入户") || wt.equals("出生入户") || wt.equals("夫妻投靠")
				|| wt.equals("家属随迁")) {
			model.setEmhj_name(family);
			if (idcard != null && idcard != "" && !idcard.equals("")) {
				String str = ifexist(idcard);
				if (str != null && str != "" && !str.equals("")) {
					t = 1;
					Messagebox.show(str, "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					if (addtype.equals("1")) {
						win.detach();
					} else {
						RedirectUtil util = new RedirectUtil();
						util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
					}
				}
			} else {
				idcard = "请填写家属身份证号 ";
			}
			readidcard = idcard;
		} else {
			model.setEmhj_name(embamodel.getEmba_name());
		}

		if (t == 0) {
			try {
				Date now = new Date();
				model.setCoba_shortname(embamodel.getCoba_name());
				model.setGid(embamodel.getGid());
				model.setCid(embamodel.getCid());
				model.setEmhj_idcard(readidcard);
				model.setEmhj_addname(UserInfo.getUsername());
				model.setEmhj_addtime(now);
				model.setEmhj_type(type);
				model.setEmhj_in_class(wt);
				model.setEmhj_case(outcase);
				model.setEmhj_place(address);
				model.setEmhj_remark(remark);
				String[] msg = bll.EmCensusAdd(model);
				if (msg[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					if (addtype.equals("1")) {
						win.detach();
					} else {
						RedirectUtil util = new RedirectUtil();
						util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
					}
				} else {
					Messagebox.show(msg[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 账户类型选择事件
	@Command
	@NotifyChange({ "addressvis", "address" })
	public void changetype(@BindingParam("type") String type) {
		if (type != null && !type.equals("中智集体户")) {
			addressvis = true;
			address = "";
		} else {
			address = "中智园岭所";
			addressvis = false;
		}
	}

	@Command
	public void ifvisible(@BindingParam("val") final String val,
			@BindingParam("cb") Combobox cb, @BindingParam("rw") final Row rw,
			@BindingParam("lb") Label lb, @BindingParam("type") Combobox type,
			@BindingParam("address") Combobox address) {
		if (val.equals("随迁入户") || val == "随迁入户" || val.equals("出生入户")
				|| val == "出生入户" || val.equals("夫妻投靠") || val == "夫妻投靠"
				|| val.equals("家属随迁")) {
			/*************** 先查询是否有户籍信息 ********************/
			List<EmCensusModel> emhjlist = dbll.getEmCensusInfo(" and a.gid="
					+ gid);
			if (emhjlist.size() > 0) {
				EmCensusModel ecm = emhjlist.get(0);
				rw.setVisible(true);
				if (!val.equals("出生入户")) {
					lb.setVisible(true);
				} else {
					lb.setVisible(false);
				}
				type.setValue(ecm.getEmhj_type());
				type.setButtonVisible(false);
				type.setReadonly(true);
				address.setValue(ecm.getEmhj_place());
				address.setButtonVisible(false);
				address.setReadonly(true);
			} else {
				type.setButtonVisible(true);
				type.setReadonly(false);
				address.setButtonVisible(true);
				address.setReadonly(false);
				Messagebox.show("该员工还没有户籍信息", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				cb.setValue("");
			}
		} else {
			type.setButtonVisible(true);
			type.setReadonly(false);
			address.setButtonVisible(true);
			address.setReadonly(false);
			rw.setVisible(false);
		}
	}

	@Command
	public void checkifExist(@BindingParam("val") String val) {
		String str = ifexist(val);
		if (str != null && str != "" && !str.equals("")) {
			Messagebox.show(str, "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 根据身份证号码查询是否已经存在员工的户籍信息
	private String ifexist(String idcard) {
		String str = "";
		if (idcard != null && idcard != "" && !idcard.equals("")) {
			EmCensus_SelectBll embll = new EmCensus_SelectBll();
			String sqls = " and emhj_state=5 and  emhj_idcard='" + idcard + "'";
			List<EmCensusModel> cenlist = embll.getEmCensusInfo(sqls);
			if (!cenlist.isEmpty()) {
				str = "此员工已有户籍信息";
			}
		}
		return str;
	}

	public EmbaseModel getEmbamodel() {
		return embamodel;
	}

	public void setEmbamodel(EmbaseModel embamodel) {
		this.embamodel = embamodel;
	}

	public boolean isAddressvis() {
		return addressvis;
	}

	public void setAddressvis(boolean addressvis) {
		this.addressvis = addressvis;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public EmDhModel getDhmodel() {
		return dhmodel;
	}

	public void setDhmodel(EmDhModel dhmodel) {
		this.dhmodel = dhmodel;
	}

}
