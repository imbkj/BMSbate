package Controller.EmCensus;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmCensus_SelectBll;

import Model.EmCensusModel;
import Model.EmHJBorrowCardModel;
import Model.EmbaseModel;
import Util.RedirectUtil;

public class EmCensus_BorrowCard_AddController {
	private Integer gid = (Integer) Executions.getCurrent().getArg().get("gid");
	private EmCensusModel mol = (EmCensusModel) Executions.getCurrent()
			.getArg().get("model");
	private EmCensus_SelectBll dbll = new EmCensus_SelectBll();
	private List<EmbaseModel> embaselist = dbll
			.getEmbaseInfo(" and gid=" + gid);
	private EmbaseModel embamodel = new EmbaseModel();
	private String hjno = dbll.getHjNoByGid(gid);
	private String familyname = "", wtidcard = "";
	private List<EmCensusModel> emlist = new ArrayList<EmCensusModel>();
	private EmCensusModel hjm=new EmCensusModel();

	// 创建窗口时查询是否已落户
	@Command
	public void ifok(@BindingParam("win") Window win) {
		EmCensus_SelectBll selbll = new EmCensus_SelectBll();
		List<EmCensusModel> emcensus = selbll
				.getEmCensusInfo(" and emhj_no is not null and a.gid=" + gid);
		if (emcensus.size() <= 0) {
			Messagebox.show("该员工还没有落户，不能借卡", "提示", Messagebox.OK,
					Messagebox.ERROR);
			RedirectUtil util = new RedirectUtil();
			util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
		} else {

		}
	}

	public EmCensus_BorrowCard_AddController() {
		if (embaselist.size() > 0) {
			embamodel = embaselist.get(0);
		}
	}

	// 控制家属是否可见
	@Command
	@NotifyChange({ "emlist", "familyname", "wtidcard","hjno" })
	public void ifvisible(@BindingParam("family") Row family,
			@BindingParam("val") String val) {
		if (val.equals("是") || val == "是") {
			EmCensus_SelectBll sebll = new EmCensus_SelectBll();
			family.setVisible(true);
			emlist = sebll.SelectEmCensusList(gid);
			if (emlist.size() > 0) {
				hjm=emlist.get(0);
				familyname =hjm.getEmhj_name();
				wtidcard = hjm.getEmhj_idcard();
				hjno=hjm.getEmhj_no();
			}
		} else {
			if (!emlist.isEmpty())
				emlist.clear();
			family.setVisible(false);
		}
		if (gid == null || gid.equals("")) {
			if (mol != null) {
				gid = mol.getGid();
			}
		}
	}

	// 户口卡借卡
	@Command
	public void EmHJBorrowCardAdd(@BindingParam("home") Checkbox home,
			@BindingParam("homecopy") Checkbox homecopy,
			@BindingParam("hjcard") Checkbox hjcard,
			@BindingParam("iffamily") String iffamily,
			@BindingParam("familyname") String familyname,
			@BindingParam("wtidcard") String wtidcard,
			@BindingParam("borrowreason") String borrowreason,
			@BindingParam("win") Window win) {
		EmCensus_SelectBll sebll = new EmCensus_SelectBll();
		if (!home.isChecked() && !homecopy.isChecked() && !hjcard.isChecked()) {
			Messagebox
					.show("至少选择一种借卡类型", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			if (hjcard.isChecked()) {
				// 查询员工是否已有户口卡借卡信息
				boolean flag = sebll.ifHKKIsOut(gid,hjno);
				if (flag) {
					Messagebox.show("该员工的户口卡已借出，不能重复借卡", "提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
			
			boolean hj_sy = false;
			EmHJBorrowCardModel model = new EmHJBorrowCardModel();
			model.setIffamily(false);
			model.setEhbc_syfy(0);
			model.setEhbc_sy(0);
			model.setEhbc_grhk(0);
			EmCensus_OperateBll bll = new EmCensus_OperateBll();
			String[] str = new String[5];

			EmCensusModel m = new EmCensusModel();
			List<EmCensusModel> l = sebll.getEmCensusInfo(" and gid=" + gid
					+ " and emhj_state =5");
			List<EmbaseModel> eml = sebll.getEmbaseInfo(" and gid=" + gid);
			EmbaseModel embamodel = new EmbaseModel();
			if (l.size() > 0) {
				m = l.get(0);
				model.setEmhj_name(m.getEmhj_name());
				model.setEhbc_tablechid(m.getEmhj_id());
			}
			if (eml.size() > 0) {
				embamodel = eml.get(0);
				model.setEmhj_name(embamodel.getEmba_name());
			}
			model.setEhbc_tableid(m.getEmhj_id());
			if (homecopy.isChecked()) {
				model.setEhbc_syfy(1);
			}
			if (home.isChecked()) {
				model.setEhbc_sy(1);
				/************* 查询该员工是否已借首页还没有还 ******************/
				hj_sy = dbll.ifSyIsOut(gid);
			}
			if (hjcard.isChecked()) {
				model.setEhbc_grhk(1);
			}
			if (iffamily.equals("是") || iffamily == "是") {
				if(familyname==null||familyname.equals(""))
				{
					Messagebox.show("请选择家属姓名", "提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
				model.setEhbc_tableid(hjm.getEmhj_id());
				model.setIffamily(true);
			}
			else
			{
				model.setGid(gid);
			}
			model.setEhbc_class(12);
			model.setEhbc_state(0);
			model.setEhbc_wtname(familyname);
			model.setEhbc_wtidcard(wtidcard);
			model.setEhbc_case(borrowreason);
			if (model.getEhbc_tableid() == null
					|| model.getEhbc_tableid().equals("")) {
				if (mol != null) {
					if (mol.getEmhj_id() > 0) {
						model.setEhbc_tableid(mol.getEmhj_id());
					}
				}
			}
			if (model.getEhbc_tableid() == null
					|| model.getEhbc_tableid().equals("")) {
				Messagebox.show("该员工还没有家属户口信息，不能借卡", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			// 如果借首页则先查询该员工是否已借首页还没有还
			else if (home.isChecked() && hj_sy) {
				Messagebox.show("该员工的户口本首页已借出", "提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				// 如果首页和户口卡一起借的话两种类型要单独一条数据
				if (home.isChecked() && hjcard.isChecked()) {
					// 先添加户口首页的数据
					model.setEhbc_sy(1);
					model.setEhbc_grhk(0);
					str = bll.EmHJBorrowCardAdd(model);
					// 当首页的借卡数据添加成功就添加户口卡的数据
					if (str[0] == "1") {
						model.setEhbc_sy(0);
						model.setEhbc_grhk(1);
						str = bll.EmHJBorrowCardAdd(model);
						if (str[0] == "1") {
							Messagebox.show("提交成功", "提示", Messagebox.OK,
									Messagebox.INFORMATION);
							RedirectUtil util = new RedirectUtil();
							util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
						} else {
							Messagebox.show(str[1], "提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show(str[1], "提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					if (home.isChecked()) {
						model.setEhbc_sy(1);
					}
					if (homecopy.isChecked()) {
						model.setEhbc_syfy(1);
					}
					if (hjcard.isChecked()) {
						model.setEhbc_grhk(1);
					}
					str = bll.EmHJBorrowCardAdd(model);
					if (str[0] == "1") {
						Messagebox.show("提交成功", "提示", Messagebox.OK,
								Messagebox.INFORMATION);
						RedirectUtil util = new RedirectUtil();
						util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
					} else {
						Messagebox.show(str[1], "提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		}
	}

	@Command
	@NotifyChange({ "familyname", "wtidcard","hjno" })
	public void family(@BindingParam("familyname") Combobox familycb) {
		if (familycb.getValue() != null) {
			hjm=familycb.getSelectedItem().getValue();
			familyname=hjm.getEmhj_name();
			wtidcard=hjm.getEmhj_idcard();
			hjno=hjm.getEmhj_no();
		}
	}

	public EmbaseModel getEmbamodel() {
		return embamodel;
	}

	public void setEmbamodel(EmbaseModel embamodel) {
		this.embamodel = embamodel;
	}

	public String getHjno() {
		return hjno;
	}

	public void setHjno(String hjno) {
		this.hjno = hjno;
	}

	public List<EmCensusModel> getEmlist() {
		return emlist;
	}

	public void setEmlist(List<EmCensusModel> emlist) {
		this.emlist = emlist;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public String getWtidcard() {
		return wtidcard;
	}

	public void setWtidcard(String wtidcard) {
		this.wtidcard = wtidcard;
	}

}
