package Controller.EmCensus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmArchivePaymentModel;
import Model.EmCensusModel;
import Model.EmDhModel;
import Model.EmHJBorrowCardModel;
import Model.LoginModel;
import Util.UserInfo;
import bll.Archives.Archive_FeeManagerBll;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.EmCensus.EmCensus_SelectBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class EmRs_FileServerInfoController {
	private String gid = Executions.getCurrent().getArg().get("gid").toString();
	private EmDh_SelectBll dhbll = new EmDh_SelectBll();
	private EmCensus_SelectBll hjbll = new EmCensus_SelectBll();
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveModel> archivelist = bll
			.getEmArchiveInfo(" and a.gid=" + gid);
	private List<EmCensusModel> hjlist = hjbll.getEmCensusInfo(" and a.gid="
			+ gid);
	private List<EmArchiveDatumModel> datumlist = bll
			.getEmArchiveDatumInfo(" and a.gid= " + gid);
	private List<EmDhModel> dhlist = dhbll.getEmDhInfo(" and a.gid=" + gid);
	private List<EmHJBorrowCardModel> borrowlist = hjbll
			.getEmHJBorrowCardInfo(" and b.gid=" + gid);
	private List<EmArchivePaymentModel> eaplist = new ListModelList<EmArchivePaymentModel>();// 档案续费
	private EmArchivePaymentModel em = new EmArchivePaymentModel();
	private Archive_FeeManagerBll feebll = new Archive_FeeManagerBll();

	public EmRs_FileServerInfoController() {

		if (gid != null) {
			em.setGid(Integer.parseInt(gid));
			eaplist = feebll.getBaseList(em);
		}
	}

	@Command
	@NotifyChange({ "datumlist", "eaplist" })
	public void sendmsg(@BindingParam("table") String table,
			@BindingParam("tid") String tid,
			@BindingParam("title") String title,
			@BindingParam("client") String client) {
		String uname = "";
		if (!UserInfo.getUsername().equals(client)) {
			uname = client;
		}
		Map map = new HashMap<>();
		map.put("type", "");// 业务类型:来自WfClass的wfcl_name
		map.put("id", tid);// 业务表id
		map.put("tablename", table);// 业务表名
		map.put("msgname", uname);// 默认收件人,没有默认收件人则为空""
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(uname);
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		map.put("title", title);
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		datumlist = bll.getEmArchiveDatumInfo(" and a.gid= " + gid);
		eaplist = feebll.getBaseList(em);
	}

	// 删除档案调出信息
	@Command
	@NotifyChange("datumlist")
	public void deleada(@BindingParam("model") final EmArchiveDatumModel model) {
		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							EmArchiveDatum_OperateBll dbll = new EmArchiveDatum_OperateBll();
							String[] str = dbll.delCheckOut(model);
							if (str[0] == "1") {
								Messagebox.show("删除成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								datumlist = bll
										.getEmArchiveDatumInfo(" and a.gid= "
												+ gid);
							} else {
								Messagebox.show("删除失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<EmCensusModel> getHjlist() {
		return hjlist;
	}

	public void setHjlist(List<EmCensusModel> hjlist) {
		this.hjlist = hjlist;
	}

	public List<EmArchiveModel> getArchivelist() {
		return archivelist;
	}

	public void setArchivelist(List<EmArchiveModel> archivelist) {
		this.archivelist = archivelist;
	}

	public List<EmArchiveDatumModel> getDatumlist() {
		return datumlist;
	}

	public void setDatumlist(List<EmArchiveDatumModel> datumlist) {
		this.datumlist = datumlist;
	}

	public List<EmDhModel> getDhlist() {
		return dhlist;
	}

	public void setDhlist(List<EmDhModel> dhlist) {
		this.dhlist = dhlist;
	}

	public List<EmHJBorrowCardModel> getBorrowlist() {
		return borrowlist;
	}

	public void setBorrowlist(List<EmHJBorrowCardModel> borrowlist) {
		this.borrowlist = borrowlist;
	}

	public List<EmArchivePaymentModel> getEaplist() {
		return eaplist;
	}

	public void setEaplist(List<EmArchivePaymentModel> eaplist) {
		this.eaplist = eaplist;
	}
}
