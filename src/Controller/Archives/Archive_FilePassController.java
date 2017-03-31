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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmArchiveRemarkModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;

public class Archive_FilePassController {
	private List<EmArchiveRemarkModel> listr;
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid =null;
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private EmArchiveRemarkModel modelr = new EmArchiveRemarkModel();
	private EmArchiveDatumModel models = new EmArchiveDatumModel();
	private String remarks = "";
	private List<TaskProcessViewModel> tlist =new ArrayList<TaskProcessViewModel>();
	private TaskProcessViewModel tmodel = new TaskProcessViewModel();
	private CoLatencyClient_AddBll bll2 = new CoLatencyClient_AddBll();
	private List<String> modell = bll2.getLoginInfo();
	private String gid = Executions.getCurrent().getArg().get("gid") + "";
	private String cid = Executions.getCurrent().getArg().get("cid") + "";
	private List<String> loginlist = bll.getLoginInfo();
	private Date rddate = null;
	private String iffee, feeinfo;

	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	private List<EmbaseModel> embaselist = selectbll
			.getEmBaseInfo(" and emba_state=1 and a.gid=" + gid);

	private EmArchiveModel amodel = new EmArchiveModel();
	private List<EmArchiveModel> archivelist = new ArrayList<EmArchiveModel>();
	private EmbaseModel emmodel = new EmbaseModel();
	private boolean isback = false;

	public Archive_FilePassController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tperid=Executions.getCurrent().getArg().get("id").toString();
			tlist= bll.getLastId(tperid);
		}
		if (embaselist.size() > 0) {
			emmodel = embaselist.get(0);
			gid = emmodel.getGid() + "";
			cid = emmodel.getCid() + "";
		}
		if (id != null && id != "" && !id.equals("")) {
			models = bll.getEmArchiveDatumInfo(" and eada_id=" + id).get(0);
			gid = models.getGid() + "";
			cid = models.getCid() + "";
			listr = bll.getEmArchiveRemarkInfo(" and eare_trid=" + id
					+ " and eare_tid=1 order by eare_id desc");
			if (!listr.isEmpty()) {
				remarks = listr.get(0).getEare_content();
			}
			if (!tlist.isEmpty()) {
				tmodel = tlist.get(0);
			}
		}
		archivelist = bll.getEmArchiveInfo("  and emar_state=1 and a.gid=" + gid);
		if (archivelist.size() > 0) {
			amodel = archivelist.get(0);
			if (amodel.getEmar_rdate() != null
					&& !amodel.getEmar_rdate().equals("")) {
				rddate = StrToDate(amodel.getEmar_rdate());
			}
		}
		if (models.getEada_final() != null
				&& models.getEada_final().equals("已退回")) {
			isback = true;
		}
	}

	private void reUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
	}

	// 转正定级新增提交事件
	@Command
	public void summit(@BindingParam("ifhelp") final String ifhelp,
			@BindingParam("win") final Window win,
			@BindingParam("ifbuy") final String ifbuy,
			@BindingParam("ifown") final String ifown,
			@BindingParam("remark") final String remark,
			@BindingParam("yorn") final String yorn) {
		if (ifhelp == null || ifhelp.equals("") || ifhelp == "") {
			Messagebox.show("请选择是否需要协助确认转正资格。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (ifbuy == null || ifbuy.equals("") || ifbuy == "") {
			Messagebox.show("请选择是代购表格。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (ifown == null || ifown.equals("") || ifown == "") {
			Messagebox.show("请选择后续事宜是否雇员自行办理。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model = new EmArchiveDatumModel();
			EmbaseModel emmodel = bll.getEmbaseinfo(" and gid=" + gid);
			model.setGid(Integer.parseInt(gid));
			model.setCid(Integer.parseInt(cid));
			model.setEada_type(emmodel.getEmba_name() + "(" + emmodel.getGid()
					+ ")转正定级申请");
			model.setEada_zg(Integer.parseInt(ifbuy));
			model.setEada_bf(Integer.parseInt(ifbuy));
			model.setEada_dms(Integer.parseInt(ifown));
			model.setEada_addname(UserInfo.getUsername());
			String[] str = bll.EmArchiveFilePassAdd(model, remark, yorn);
			// 判断业务id是否为空
			if (str[0].equals("1")) {
				// 调用内联页方法submitDoc(Grid gd)
				// message = docOC.AddsubmitDoc(docGrid,str[3]);
				Messagebox.show(str[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				reUrl();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 转正定级确认提交事件
	@Command
	public void summitconfirm(@BindingParam("ifhelp") final String ifhelp,
			@BindingParam("win") final Window win,
			@BindingParam("ifbuy") final String ifbuy,
			@BindingParam("ifown") final String ifown,
			@BindingParam("remark") final String remark,
			@BindingParam("yorn") final String yorn) {
		if (ifhelp == null || ifhelp.equals("") || ifhelp == "") {
			Messagebox.show("请选择是否需要协助确认转正资格。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (ifbuy == null || ifbuy.equals("") || ifbuy == "") {
			Messagebox.show("请选择是代购表格。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (ifown == null || ifown.equals("") || ifown == "") {
			Messagebox.show("请选择后续事宜是否雇员自行办理。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model = new EmArchiveDatumModel();
			model.setGid(Integer.parseInt(gid));
			model.setCid(Integer.parseInt(cid));
			model.setEada_type(tmodel.getWfno_name());
			model.setEada_zg(Integer.parseInt(ifbuy));
			model.setEada_bf(Integer.parseInt(ifbuy));
			model.setEada_dms(Integer.parseInt(ifown));
			model.setEada_addname(UserInfo.getUsername());
			model.setEada_tapr_id(Integer.parseInt(tperid));
			model.setEada_id(Integer.parseInt(id));
			String sql = ",eada_final=1,eada_zg=" + ifhelp + ",eada_bf="
					+ ifbuy + ",eada_dms=" + ifown;
			String[] str = bll.EmArchivePositive(model, remark, yorn, sql);
			// 判断业务id是否为空
			if (str[0].equals("1")) {
				// 调用内联页方法submitDoc(Grid gd)
				// message = docOC.AddsubmitDoc(docGrid,str[3]);
				Messagebox.show(str[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				// Executions.sendRedirect("Archive_FileProve.zul");
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 转正定级交接表格提交事件
	@Command
	public void summithand(@BindingParam("docGrid") final Grid docGrid,
			@BindingParam("win") final Window win,
			@BindingParam("drawformdate") final Date drawformdate,
			@BindingParam("drawformpeople") final String drawformpeople,
			@BindingParam("rsetup") final String rsetup,
			@BindingParam("rdate") final Date rdate,
			@BindingParam("receiveform") final Date receiveform,
			@BindingParam("finishdate") final Date finishdate) {
		if (drawformdate == null) {
			Messagebox.show("请选择发出表格日期", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (drawformpeople == null || drawformpeople.equals("")
				|| drawformpeople == "") {
			Messagebox.show("请选择交接人", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		// else if(rsetup==null||rsetup.equals("")||rsetup=="")
		// {
		// Messagebox.show("请填写毕业生接收单位", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// }
		// else if(rdate==null)
		// {
		// Messagebox.show("请选择毕业生接收时间", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// }
		// else if(receiveform==null)
		// {
		// Messagebox.show("请选择回收表格日期", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// }
		// else if(finishdate==null)
		// {
		// Messagebox.show("请选择办理转正日期", "操作提示", Messagebox.OK,
		// Messagebox.ERROR);
		// }
		else {
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				String[] message = docOC.AddchkHaveTo(docGrid);
				// 判断材料必选项是否已选
				if (message[0].equals("1")) {
					EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
					EmArchiveDatumModel model = new EmArchiveDatumModel();
					model.setGid(Integer.parseInt(gid));
					model.setCid(Integer.parseInt(cid));
					model.setEada_type(tmodel.getWfno_name());
					model.setEada_tapr_id(Integer.parseInt(tperid));
					model.setEada_id(Integer.parseInt(id));
					model.setEada_addname(UserInfo.getUsername());
					String sql = ",eada_drawformdate='"
							+ getStringDate(drawformdate) + "'";
					sql = sql + ",eada_drawformpeople='" + drawformpeople + "'";
					sql = sql + ",eada_rsetup='" + rsetup + "'";
					sql = sql + ",eada_rdate='" + getStringDate(rdate) + "'";
					// sql=sql+",eada_returnformdate='"+getStringDate(receiveform)+"'";
					// sql=sql+",eada_transactdate='"+getStringDate(finishdate)+"'";
					String[] str = bll.EmArchivePositive(model, "", "1", sql);
					// 判断业务id是否为空
					if (str[0].equals("1")) {
						// 调用内联页方法submitDoc(Grid gd)
						message = docOC.AddsubmitDoc(docGrid, id);
						Messagebox.show(str[1], "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						// Executions.sendRedirect("Archive_FileProve.zul");
						win.detach();
					} else {
						Messagebox.show(str[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
			}
		}
	}

	// 转正定级财务结费提交事件
	@Command
	public void summitfee(@BindingParam("win") final Window win,
			@BindingParam("paydate") final Date paydate,
			@BindingParam("arrearageinfo") final String arrearageinfo) {
		if (paydate == null) {
			Messagebox.show("请选择费用结算日期", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model = new EmArchiveDatumModel();
			model.setGid(Integer.parseInt(gid));
			model.setCid(Integer.parseInt(cid));
			model.setEada_type("财务结算");
			model.setEada_tapr_id(Integer.parseInt(tperid));
			model.setEada_id(Integer.parseInt(id));
			model.setEada_addname(UserInfo.getUsername());
			String sql = ",eada_paydate='" + getStringDate(paydate) + "'";
			sql = sql + ",eada_arrearageinfo='" + arrearageinfo + "'";
			String[] str = bll.EmArchivePositive(model, "", "1", sql);
			// 判断业务id是否为空
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				// Executions.sendRedirect("Archive_FileProve.zul");
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 转正定级收回表格日期
	@Command
	public void summitback(@BindingParam("win") Window win,
			@BindingParam("docGrid") Grid docGrid,
			@BindingParam("receiveform") Date receiveform) {
		if (receiveform == null) {
			Messagebox.show("请选择回收表格日期", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model = new EmArchiveDatumModel();
			model.setGid(Integer.parseInt(gid));
			model.setCid(Integer.parseInt(cid));
			model.setEada_type("收回表格");
			model.setEada_tapr_id(Integer.parseInt(tperid));
			model.setEada_id(Integer.parseInt(id));
			model.setEada_addname(UserInfo.getUsername());
			String sql = ",eada_returnformdate='" + getStringDate(receiveform)
					+ "'";
			if (feeinfo != null && !feeinfo.equals("")) {
				if (iffee != null && iffee.equals("是")) {
					sql = sql + ",eada_arrearageinfo='" + feeinfo
							+ "',eada_iffee=1";
				}
			}
			String[] str = bll.EmArchivePositive(model, "", "1", sql);
			// 判断业务id是否为空
			if (str[0].equals("1")) {
				if (iffee != null && iffee.equals("否"))// 不要结算费用则跳过结算费用步骤
				{
					EmArchiveDatum_OperateBll bllo = new EmArchiveDatum_OperateBll();
					Integer tarpid = Integer.parseInt(str[2]);
					models.setEada_tapr_id(tarpid);
					bllo.skiptonext(models, "", tarpid);
				}
				Messagebox.show(str[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				// Executions.sendRedirect("Archive_FileProve.zul");
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 转正日期
	@Command
	public void summitend(@BindingParam("win") Window win,
			@BindingParam("finishdate") Date finishdate) {
		if (finishdate == null) {
			Messagebox.show("请选择转正日期", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model = new EmArchiveDatumModel();
			model.setGid(Integer.parseInt(gid));
			model.setCid(Integer.parseInt(cid));
			model.setEada_type("收回表格");
			model.setEada_tapr_id(Integer.parseInt(tperid));
			model.setEada_id(Integer.parseInt(id));
			model.setEada_addname(UserInfo.getUsername());
			String sql = ",eada_transactdate='" + getStringDate(finishdate)
					+ "',eada_final=3";
			String[] str = bll.EmArchivePositive(model, "", "1", sql);
			// 判断业务id是否为空
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				// Executions.sendRedirect("Archive_FileProve.zul");
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 退回
	@Command
	public void back(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("ta", tperid);
		map.put("model", models);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(
				"../Archives/Archive_back.zul", null, map);
		window.doModal();
		if (map.get("flag") == "1") {
			win.detach();
		}
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id.toString());
		map.put("typeid", "2");
		map.put("gid",gid.toString());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	// 财务退回
	@Command
	public void cwback(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("ta", tperid);
		map.put("model", models);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(
				"../Archives/Archive_backtopre.zul", null, map);
		window.doModal();
		if (map.get("flag") == "1") {
			win.detach();
		}
	}

	// 弹出个人收款页面
	@Command
	public void emfinace(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("gid", models.getGid());
		Window window = (Window) Executions.createComponents(
				"../EmFinanceManage/Emgt_EmFinaceList.zul", null, map);
		window.doModal();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTperid() {
		return tperid;
	}

	public void setTperid(String tperid) {
		this.tperid = tperid;
	}

	public EmArchiveRemarkModel getModelr() {
		return modelr;
	}

	public void setModelr(EmArchiveRemarkModel modelr) {
		this.modelr = modelr;
	}

	public EmArchiveDatumModel getModels() {
		return models;
	}

	public void setModels(EmArchiveDatumModel models) {
		this.models = models;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public TaskProcessViewModel getTmodel() {
		return tmodel;
	}

	public void setTmodel(TaskProcessViewModel tmodel) {
		this.tmodel = tmodel;
	}

	public List<String> getModell() {
		return modell;
	}

	public void setModell(List<String> modell) {
		this.modell = modell;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public EmArchiveModel getAmodel() {
		return amodel;
	}

	public void setAmodel(EmArchiveModel amodel) {
		this.amodel = amodel;
	}

	public Date getRddate() {
		return rddate;
	}

	public void setRddate(Date rddate) {
		this.rddate = rddate;
	}

	public String getIffee() {
		return iffee;
	}

	public void setIffee(String iffee) {
		this.iffee = iffee;
	}

	public String getFeeinfo() {
		return feeinfo;
	}

	public void setFeeinfo(String feeinfo) {
		this.feeinfo = feeinfo;
	}

	public boolean isIsback() {
		return isback;
	}

	public void setIsback(boolean isback) {
		this.isback = isback;
	}

	// 日期转字符串
	public static String getStringDate(Date date) {
		String dateString = "";
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			dateString = formatter.format(date);
		}
		return dateString;
	}

	// 字符串转日期
	public Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			// date = format.parse(str);
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
