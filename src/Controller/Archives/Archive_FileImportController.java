package Controller.Archives;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.Archives.Archive_FileImportBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoglistModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveSetupModel;
import Model.EmbaseModel;
import Util.RedirectUtil;
import Util.DateStringChange;
import Util.StringFormat;
import Util.UserInfo;

public class Archive_FileImportController {
	private List<EmbaseModel> embaselist = new ListModelList<EmbaseModel>();
	private List<EmArchiveSetupModel> setuplist = new ListModelList<EmArchiveSetupModel>();
	private List<CoglistModel> itemlist = new ListModelList<CoglistModel>();
	private Archive_FileImportBll bll = new Archive_FileImportBll();

	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private String username = UserInfo.getUsername();
	private String userid = UserInfo.getUserid();
	private Integer gid = 0;
	private Integer cid = 0;
	private Integer dataId = 0;
	private Integer taprId = 0;

	private String company;
	private String name;
	private String idcard;
	private String client;
	private String fileplace;
	private String wtmode;
	private String hj;
	private String filedate;
	private String savefiledate;
	private String rsp;
	private String rsi;
	private String hjp;
	private String hji;
	private String charge;
	private String remark;
	private Date sdDate = null;
	private String sdDate2 = "19000101";
	private String backcase = "";
	private boolean save = false;
	private Window win;

	public Archive_FileImportController() {
		if (Executions.getCurrent().getArg().get("gid") != null) {
			gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString());
			setEmbaselist(gid);

			company = embaselist.get(0).getCoba_company();
			name = embaselist.get(0).getEmba_name();
			idcard = embaselist.get(0).getEmba_idcard();
			client = embaselist.get(0).getCoba_client();
			itemlist = bll.getItem(gid);
			if (itemlist.size() > 0) {
				if (itemlist.get(0).getCgli_startdate2() != null
						&& !itemlist.get(0).getCgli_startdate2().equals("")) {
					sdDate = DateStringChange.ownmonthTodate(itemlist.get(0)
							.getCgli_startdate2().toString());
					if (sdDate != null) {
						sdDate2 = DateStringChange.DatetoSting(sdDate,
								"yyyyMMdd");
					}

				}

				if (itemlist.get(0).getColi_rspaykind() != null
						&& !itemlist.get(0).getColi_rspaykind().equals("")) {
					rsp = itemlist.get(0).getColi_rspaykind();
				}

				if (itemlist.get(0).getColi_rsinvoice() != null
						&& !itemlist.get(0).getColi_rsinvoice().equals("")) {
					rsi = itemlist.get(0).getColi_rsinvoice();
				}
				if (itemlist.get(0).getColi_hjpaykind() != null
						&& !itemlist.get(0).getColi_hjpaykind().equals("")) {
					hjp = itemlist.get(0).getColi_hjpaykind();
				}
				if (itemlist.get(0).getColi_hjinvoice() != null
						&& !itemlist.get(0).getColi_hjinvoice().equals("")) {
					hji = itemlist.get(0).getColi_hjinvoice();
				}

			}
		}
		if (Executions.getCurrent().getArg().get("daid") != null) {
			dataId = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			taprId = Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());
			List<EmArchiveDatumModel> list = new ListModelList<>();
			list = bll.getealist(dataId);
			if (list.size() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				gid = list.get(0).getGid();
				cid = list.get(0).getCid();
				company = list.get(0).getCoba_company();
				name = list.get(0).getEada_name();
				idcard = list.get(0).getEada_idcard();
				client = list.get(0).getCoba_client();
				hj = list.get(0).getEada_hj();
				sdDate = list.get(0).getSavefiledate();
				fileplace = list.get(0).getEada_fileplace();
				backcase=list.get(0).getEada_backcase();
				rsp=list.get(0).getEada_rspaykind();
				rsi=list.get(0).getEada_rsinvoice();
				hjp=list.get(0).getEada_hjpaykind();
				hji=list.get(0).getEada_hjinvoice();
				charge=list.get(0).getEada_charge();
				remark=list.get(0).getEada_remark();

			}
			save = true;
		}

		setSetuplist();
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win=w;
		if (bll.importData(gid)) {
			Messagebox.show("已有未处理调入数据.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			w.detach();
			return;
		}
		/*
		 * if (itemlist.size() == 0) { Messagebox.show("员工未选择档案服务或服务未生效.",
		 * "操作提示", Messagebox.OK, Messagebox.ERROR); w.detach(); return; }
		 */
	}

	@Command("btnSubmit")
	public void btnSubmit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("a") Textbox tbidcard,
			@BindingParam("b") Combobox cbsetup,
			@BindingParam("j") Textbox tbcharge,
			@BindingParam("k") Textbox tbremark,
			@BindingParam("l") Datebox dbsavedate) {

		savefiledate = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (tbidcard.getValue() == null
				|| tbidcard.getValue().toString().equals("")) {
			Messagebox.show("请输入身份证号码!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		idcard = tbidcard.getValue();

		if (cbsetup.getValue() != null && !cbsetup.getValue().equals("")) {
			fileplace = cbsetup.getValue();
			fileplace = StringFormat.replaceSpace(fileplace);
			if (fileplace.equals("其他")) {
				Messagebox.show("不能选择其他,请输入现存档机构全称", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		if (hj == null || hj.equals("")) {
			Messagebox
					.show("请选择户籍性质.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (dbsavedate.getValue() != null && !dbsavedate.getValue().equals("")) {
			savefiledate = sdf.format(dbsavedate.getValue());
		}

		try {
			// 调用内联页方法chkHaveTo(Grid gd)
			// String[] message = docOC.AddchkHaveTo(gd);

			// 判断材料必选项是否已选
			// if (message[0].equals("1")) {

			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								// 新增业务数据，并返回业务表ID
								if (save) {
									EmArchiveDatumModel m = new EmArchiveDatumModel();
									m.setEada_id(dataId);
									m.setCid(cid);
									m.setGid(gid);
									m.setEada_tapr_id(taprId);
									m.setEada_idcard(idcard);
									m.setEada_hj(hj);
									m.setEada_savefiledate(filedate);
									m.setEada_rspaykind(rsp);
									m.setEada_rsinvoice(rsi);
									m.setEada_hjpaykind(hjp);
									m.setEada_hjinvoice(hji);
									m.setEada_charge(charge);
									m.setEada_remark(remark);
									m.setEada_fileplace(fileplace);
									m.setEada_client(client);
									m.setEada_final("1");
									Integer i = bll.passData(m);
									if (i > 0) {
										Messagebox.show("操作成功!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										win.detach();
									}else {
										Messagebox.show("操作失败!", "操作提示",
												Messagebox.OK,
												Messagebox.ERROR);
										
									}
								} else {
									Integer tid = bll.addFileImport(name, gid,
											idcard, fileplace, wtmode, hj,
											filedate, rsp, rsi, hjp, hji,
											charge, savefiledate, remark,
											username);

									// 判断业务id是否为空
									if (tid > 0) {
										// 调用内联页方法submitDoc(Grid gd)
										docOC.AddsubmitDoc(gd, tid.toString());
										Messagebox.show("添加成功!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										RedirectUtil util = new RedirectUtil();
										util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
									}else {
										Messagebox.show("操作失败!", "操作提示",
												Messagebox.OK,
												Messagebox.ERROR);
									}
								}
							}
						}
					});
			// }

		} catch (Exception e) {
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getFileplace() {
		return fileplace;
	}

	public void setFileplace(String fileplace) {
		this.fileplace = fileplace;
	}

	public List<EmbaseModel> getEmbaselist() {
		return embaselist;
	}

	public void setEmbaselist(Integer gid) {
		this.embaselist = bll.getEmbaseInfo(gid);
	}

	public List<EmArchiveSetupModel> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist() {
		this.setuplist = bll.getSetUp();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getSdDate() {
		return sdDate;
	}

	public void setSdDate(Date sdDate) {
		this.sdDate = sdDate;
	}

	public String getSdDate2() {
		return sdDate2;
	}

	public void setSdDate2(String sdDate2) {
		this.sdDate2 = sdDate2;
	}

	public String getRsp() {
		return rsp;
	}

	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public String getRsi() {
		return rsi;
	}

	public void setRsi(String rsi) {
		this.rsi = rsi;
	}

	public String getHjp() {
		return hjp;
	}

	public void setHjp(String hjp) {
		this.hjp = hjp;
	}

	public String getHji() {
		return hji;
	}

	public void setHji(String hji) {
		this.hji = hji;
	}

	public String getHj() {
		return hj;
	}

	public void setHj(String hj) {
		this.hj = hj;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

	public String getBackcase() {
		return backcase;
	}

	public void setBackcase(String backcase) {
		this.backcase = backcase;
	}

}
