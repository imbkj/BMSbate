package Controller.EmBenefit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmBenefit_ModBll;

import Model.EmBenefitModel;
import Model.EmBenefitRelationModel;
import Util.UserInfo;

public class EmBenefit_ModController {
	private List<EmBenefitModel> list = new ListModelList<>();
	private List<EmBenefitModel> nameList = new ListModelList<>();
	private List<EmBenefitRelationModel> supList = new ListModelList<>();
	private EmBenefitModel ebm = new EmBenefitModel();
	private EmBenefit_ModBll bll = new EmBenefit_ModBll();
	private Window win = (Window) Path.getComponent("/winEwMod");
	private String username = UserInfo.getUsername();

	private Integer ID = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private Date startdate;
	private Date noticedate;
	private boolean dis = false;

	public EmBenefit_ModController() {

		setList(ID);

		ebm = list.get(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (ebm.getEmbf_start() != null && !ebm.getEmbf_start().equals("")) {

			try {
				startdate = sdf.parse(ebm.getEmbf_start());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (ebm.getEmbf_notice() != null && !ebm.getEmbf_notice().equals("")) {

			try {
				noticedate = sdf.parse(ebm.getEmbf_notice());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (ebm.getEmbf_type() != null) {

			ebm.setEmbf_type2(ebm.getEmbf_type().equals(1) ? "是" : "否");
		} else {
			ebm.setEmbf_type2("否");
		}
		supList=bll.getSupply(ebm.getEmbf_id());
		updateRound();
	}

	@Command
	@NotifyChange("dis")
	public void updateRound() {
		System.out.println(ebm.getEmbf_type2());
		if (ebm.getEmbf_type2() != null && ebm.getEmbf_type2().equals("是")) {
			dis = true;
		} else {
			dis = false;
		}
	}

	@Command
	@NotifyChange("supList")
	public void supply() {
		Map map = new HashMap<>();
		map.put("ebm", ebm);
		Window window = (Window) Executions.createComponents(
				"EmActy_SupplyList.zul", null, map);
		window.doModal();
		supList= bll.getSupply(ebm.getEmbf_id());
	}
	
	@Command
	@NotifyChange("supList")
	public void del(@BindingParam("a") EmBenefitRelationModel em){
		bll.updateRelation(em.getEbre_id());
		supList= bll.getSupply(ebm.getEmbf_id());
		
	}

	@Command("submit")
	public void submit() {

		Combobox cbName = (Combobox) win.getFellow("name");
		Datebox dbstart = (Datebox) win.getFellow("start");
		Datebox dbnotice = (Datebox) win.getFellow("notice");
		Combobox cbType = (Combobox) win.getFellow("aroundType");
		Intbox ibCycle = (Intbox) win.getFellow("cycle");
		Combobox cbUnit = (Combobox) win.getFellow("unit");
		Combobox cbmold = (Combobox) win.getFellow("mold");

		if (cbName.getSelectedItem() == null) {
			if (cbName.getValue().equals("")) {
				Messagebox.show("请输入福利项目名称", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				ebm.setEmbf_name(cbName.getValue());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (dbstart.getValue() != null
						&& !dbstart.getValue().equals("")) {

					ebm.setEmbf_start(sdf.format(dbstart.getValue()));
				} else {
					ebm.setEmbf_start("");
				}

				if (dbnotice.getValue() != null
						&& !dbnotice.getValue().equals("")) {

					ebm.setEmbf_notice(sdf.format(dbnotice.getValue()));
				} else {
					ebm.setEmbf_notice("");
				}

				ebm.setEmbf_type(cbType.getValue().equals("是") ? 1 : 0);
				ebm.setEmbf_cycle(ibCycle.getValue());
				ebm.setEmbf_unit(cbUnit.getValue());
				//ebm.setEmbf_mold(cbmold.getValue());
				ebm.setEmbf_modname(username);
				if (bll.mod(ebm) > 0) {
					Messagebox.show("修改成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("修改失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}

	}

	public List<EmBenefitModel> getList() {
		return list;
	}

	public void setList(Integer id) {
		this.list = bll.getList(id);
	}

	public EmBenefitModel getEbm() {
		return ebm;
	}

	public void setEbm(EmBenefitModel ebm) {
		this.ebm = ebm;
	}

	public List<EmBenefitModel> getNameList() {
		return nameList;
	}

	public void setNameList(String name, String name2) {
		this.nameList = bll.getNameList(name, name2);
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public boolean isDis() {
		return dis;
	}

	public void setDis(boolean dis) {
		this.dis = dis;
	}

	public Date getNoticedate() {
		return noticedate;
	}

	public void setNoticedate(Date noticedate) {
		this.noticedate = noticedate;
	}

	public List<EmBenefitRelationModel> getSupList() {
		return supList;
	}

	public void setSupList(List<EmBenefitRelationModel> supList) {
		this.supList = supList;
	}

}
