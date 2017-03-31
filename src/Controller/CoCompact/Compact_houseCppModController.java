package Controller.CoCompact;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.impl.BinderUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import bll.CoCompact.CoCompact_OperateBll;
import bll.CoCompact.Compact_houseCppBll;
import bll.CoCompact.Compact_houseCppImpl;
import bll.EmHouse.EmHouse_EditImpl;

import Model.CoCompactCppAduitModel;
import Model.CoCompactModel;
import Model.EmHouseCpp;
import Util.UserInfo;

public class Compact_houseCppModController {

	private Integer id = 0;
	private List<CoCompactModel> clist = new ListModelList<>();
	private List<EmHouseCpp> cplist = new ListModelList<>();
	private CoCompactModel ccm = new CoCompactModel();
	private CoCompactCppAduitModel cam = new CoCompactCppAduitModel();
	private Window win;

	private CoCompact_OperateBll bll = new CoCompact_OperateBll();

	public Compact_houseCppModController() {
		if (Executions.getCurrent().getArg().get("id") != null) {
			id = Integer.valueOf(Executions.getCurrent().getArg().get("id")
					.toString());
		}
		clist = bll.getInfoList(id);
		if (clist.size() > 0) {
			ccm = clist.get(0);
			if (ccm.getCoco_cpp() != null && !ccm.getCoco_cpp().equals("") && !ccm.getCoco_cpp().equals("浮动比例")) {
				ccm.setCoco_cpp((int) (Double.valueOf(ccm.getCoco_cpp()) * 100)
						+ "%");
			}
			cam.setCoca_cpp(ccm.getCoco_cpp());
			cam.setCid(ccm.getCid2());
			cam.setCoca_coco_id(ccm.getCoco_id());
			cam.setCoca_addname(UserInfo.getUsername());

		}
		cplist = bll.cppList(ccm.getCid2());
		for (EmHouseCpp m : cplist) {
			if (m.getCpName().equals(ccm.getCoco_cpp())) {
				ccm.setCoco_houseid(m.getCompanyid());
			}
		}
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	public void updateCpp(@BindingParam("a") Combobox cb) {

		if (cb.getSelectedItem() != null) {
			if (!cb.getSelectedItem().getLabel().equals("浮动比例")) {
				ccm.setCoco_houseid(cb.getSelectedItem().getValue().toString());
				
			} else {
				ccm.setCoco_houseid(null);
			}
		} else {
			ccm.setCoco_houseid(null);
		}
		cam.setCoca_houseid(ccm.getCoco_houseid());
		BindUtils.postNotifyChange(null, null, ccm, "coco_houseid");
	}

	@Command
	public void submit() {
		Combobox cb = (Combobox) win.getFellow("cpp");

		if (cb.getSelectedItem() != null) {
			String s = cb.getSelectedItem().getLabel();

			if (ccm.getCoco_cpp()!=null && ccm.getCoco_cpp().equals(s)) {
				Messagebox.show("请选择变更比例", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			cam.setCoca_cpp(cb.getSelectedItem().getLabel());
		} else {
			Messagebox.show("请选择变更比例", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;

		}

		if (cam.getCoca_remark() == null || cam.getCoca_remark().equals("")) {
			Messagebox.show("请输入变更原因", "操作提示", Messagebox.OK, Messagebox.ERROR);
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
							System.out.print(cam.getCoca_cpp());
							WfBusinessService wfbs = new Compact_houseCppImpl();
							WfOperateService wfs = new WfOperateImpl(wfbs);
							Object[] obj = { "新增", cam };
							String[] str = wfs.AddTaskToNext(
									obj,
									"合同公积金比例变更",
									ccm.getCompany() + "("
											+ ccm.getCoco_compactid()
											+ ")变更公积金比例", 116,
									UserInfo.getUsername(), "", ccm.getCid2(),
									"");
							if (str[0].equals("1")) {
								cam.setCoca_id(Integer.valueOf(str[3]
										.toString()));
								Object[] obj2 = { "跳过确认", cam };
								wfs.SkipToNext(obj2,
										Integer.valueOf(str[2].toString()),
										UserInfo.getUsername(), "", ccm.getCid2(), "");
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public CoCompactModel getCcm() {
		return ccm;
	}

	public void setCcm(CoCompactModel ccm) {
		this.ccm = ccm;
	}

	public List<EmHouseCpp> getCplist() {
		return cplist;
	}

	public void setCplist(List<EmHouseCpp> cplist) {
		this.cplist = cplist;
	}

	public CoCompactCppAduitModel getCam() {
		return cam;
	}

	public void setCam(CoCompactCppAduitModel cam) {
		this.cam = cam;
	}

}
