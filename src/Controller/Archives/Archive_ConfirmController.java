package Controller.Archives;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.Archives.Archive_ConfirmBll;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoglistModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Model.EmArchiveSetupModel;
import Model.LoginModel;
import Util.DateStringChange;
import Util.UserInfo;

public class Archive_ConfirmController {
	private EmArchiveDatumModel edm = new EmArchiveDatumModel();
	private Archive_ConfirmBll bll = new Archive_ConfirmBll();
	private List<EmArchiveSetupModel> setuplist = new ListModelList<EmArchiveSetupModel>();
	private List<EmArchiveLinkModel> ealist = new ListModelList<>();
	private List<CoglistModel> cglist = new ListModelList<>();
	private Integer embaId = null;
	private Integer eadaId = null;
	private Integer tapr_id = null;
	private String username = UserInfo.getUsername();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private Integer startdate;
	private Date savefiledate;
	private String savefiledate2;

	public Archive_ConfirmController() {

		if (Executions.getCurrent().getArg().get("embaId") != null) {
			embaId = Integer.valueOf(Executions.getCurrent().getArg()
					.get("embaId").toString());
			List<EmArchiveDatumModel> l = bll.getDatafileInfo(embaId);
			if (l.size() > 0) {
				edm = l.get(0);
				eadaId=l.get(0).getEada_id();
				tapr_id=l.get(0).getEada_tapr_id();
			}

		} else {
			eadaId = Integer.valueOf(Executions.getCurrent().getArg()
					.get("daid").toString());
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
			setEdm(eadaId);
		}

		setSetuplist();
		setCglist(edm.getGid());
		setEalist(eadaId);
		if (cglist.size() > 0) {
			startdate = cglist.get(0).getCgli_startdate();
		}

		if (edm.getSavefiledate() != null && !edm.getSavefiledate().equals("")) {
			savefiledate = edm.getSavefiledate();
			savefiledate2 = DateStringChange.DatetoSting(edm.getSavefiledate(),
					"yyyy-MM-dd");
		}
	}

	@Command("setPlace")
	public void setPlace(@BindingParam("a") EmArchiveSetupModel easModel,
			@BindingParam("b") Textbox tb) {
		if (easModel.getEase_name() == null
				|| easModel.getEase_name().equals("其他")) {
			//tb.setStyle("display:");
			//tb.setValue("");
		} else {
			tb.setStyle("display:none");
			tb.setValue(easModel.getEase_name());
		}
	}

	@Command("addlink")
	@NotifyChange("ealist")
	public void addlink() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("daid", eadaId);
		Window window = (Window) Executions.createComponents(
				"/Archives/Archive_addLink.zul", null, map);
		window.doModal();
		setEalist(eadaId);
	}
	
	@Command
	public void linkclient(){
		Map map = new HashMap<>();
		map.put("type", "员工档案");// 业务类型:来自WfClass的wfcl_name
		map.put("id", eadaId);// 业务表id
		map.put("tablename", "emarchivedatum");// 业务表名
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(edm.getCoba_client());
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("grid") final Grid gd, @BindingParam("a") Combobox cb) {

		try {

			// 调用内联页方法chkHaveTo(Grid gd)
			String[] message = docOC.UpchkHaveTo(gd);

			// 判断材料必选项是否已选
			if (message[0].equals("1")) {
				Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
						Messagebox.Button.OK, Messagebox.Button.CANCEL },
						Messagebox.QUESTION,
						new EventListener<Messagebox.ClickEvent>() {
							@Override
							public void onEvent(ClickEvent event)
									throws Exception {
								// TODO Auto-generated method stub

								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									if (savefiledate != null) {
										edm.setEada_savefiledate(DateToStr(savefiledate));
										edm.setSavefiledate(savefiledate);

										Integer i = bll.setConfirm(edm);

										if (i > 0) {
											docOC.UpsubmitDoc(gd,
													eadaId.toString());
											Messagebox.show("添加成功!", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
										} else {
											Messagebox.show("操作失败。", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
										win.detach();
									} else {
										Messagebox
												.show("请选择存档起始日期。", "操作提示",
														Messagebox.OK,
														Messagebox.ERROR);
									}
								}
							}
						});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EmArchiveDatumModel getEdm() {
		return edm;
	}

	public void setEdm(Integer id) {
		this.edm = bll.getEmArchiveDatumInfo(id).get(0);
	}

	public List<EmArchiveSetupModel> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist() {
		this.setuplist = bll.getSetUp();
	}

	public List<CoglistModel> getCglist() {
		return cglist;
	}

	public void setCglist(Integer gid) {
		this.cglist = bll.coglistInfo(gid);
	}

	public Integer getStartdate() {
		return startdate;
	}

	public void setStartdate(Integer startdate) {
		this.startdate = startdate;
	}

	public Date getSavefiledate() {
		return savefiledate;
	}

	public void setSavefiledate(Date savefiledate) {
		this.savefiledate = savefiledate;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date strtodate(String datestr) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime_2 = null;
		try {
			if (datestr != null && !datestr.equals("")) {
				currentTime_2 = formatter.parse(datestr);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static String DateToStr(Date currentTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = "";
		if (currentTime != null && !currentTime.equals("")) {
			dateString = formatter.format(currentTime);
		}
		return dateString;
	}

	public List<EmArchiveLinkModel> getEalist() {
		return ealist;
	}

	public void setEalist(Integer id) {
		this.ealist = bll.getLinkInfo(id);
	}

	public String getSavefiledate2() {
		return savefiledate2;
	}

	public void setSavefiledate2(String savefiledate2) {
		this.savefiledate2 = savefiledate2;
	}

}
