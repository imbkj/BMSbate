package Controller.Archives;

import impl.WorkflowCore.WfOperateImpl;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.Archives.Archive_ConfirmImpl;
import bll.Archives.EmArchive_SelectBll;
import bll.Archives.EmarchiveDatum_payImpl;

import Model.EmArchiveDatumModel;
import Model.EmGatheringModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmarchiveDatum_payController {
	List<EmArchiveDatumModel> eaList = new ListModelList<>();

	EmArchiveDatumModel eam = new EmArchiveDatumModel();
	EmGatheringModel egm = new EmGatheringModel();

	private Date ownmonth;
	private Integer id = 0;

	private EmArchive_SelectBll bll = new EmArchive_SelectBll();

	private Window win;

	public EmarchiveDatum_payController() {
		if (Executions.getCurrent().getArg().get("daid") != null) {
			id = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());
		}
		ownmonth = new Date();
		eaList = bll.getEmArchiveDatumInfo(" and a.eada_id=" + id);
		if (eaList.size() > 0) {
			eam = eaList.get(0);
		}
		egm.setEmgt_type("档案");
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	public void submit() {
		if (ownmonth == null || ownmonth.equals("")) {
			Messagebox.show("请输入所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (egm.getEmgt_paytype() == null || egm.getEmgt_paytype().equals("")) {
			Messagebox.show("请输入收费方式", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (egm.getEmgt_fee() == null || egm.getEmgt_fee().equals("")) {
			Messagebox.show("请输入收费金额", "提示", Messagebox.OK, Messagebox.ERROR);
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
							egm.setCid(eam.getCid());
							egm.setGid(eam.getGid());
							egm.setEmgt_addname(UserInfo.getUsername());
							egm.setOwnmonth(Integer.valueOf(DateStringChange
									.DatetoSting(ownmonth, "yyyyMM")));
							WfBusinessService wfbs = new EmarchiveDatum_payImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							Object[] obj = { "财务录入费用", egm, eam.getEada_id() };
							String[] str = wfs.PassToNext(obj,
									eam.getEada_tapr_id(),
									UserInfo.getUsername(), "", 0, "");
							if (str[0].equals("1")) {
								Messagebox.show("操作成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败", "提示", Messagebox.OK,
										Messagebox.ERROR);

							}
						}
					}
				});
	}

	public EmArchiveDatumModel getEam() {
		return eam;
	}

	public void setEam(EmArchiveDatumModel eam) {
		this.eam = eam;
	}

	public EmGatheringModel getEgm() {
		return egm;
	}

	public void setEgm(EmGatheringModel egm) {
		this.egm = egm;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

}
