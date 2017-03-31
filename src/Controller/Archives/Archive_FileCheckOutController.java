package Controller.Archives;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
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
import bll.Embase.EmbaseListBll;

public class Archive_FileCheckOutController {
	private List<EmArchiveRemarkModel> listr;
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid =null;
	private String gid = Executions.getCurrent().getArg().get("gid") + "";
	private String cid = Executions.getCurrent().getArg().get("cid") + "";
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private EmArchiveDatumModel models = new EmArchiveDatumModel();
	private String remarks = "";
	private List<EmArchiveModel> flist;
	private TaskProcessViewModel tmodel = new TaskProcessViewModel();
	private List<EmArchiveModel> fidlist;
	private EmArchiveModel fmodel = new EmArchiveModel();

	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	private List<EmbaseModel> embaselist = selectbll.getEmBaseInfo(" and gid="
			+ gid);
	private EmbaseModel emmodel = new EmbaseModel();
	private String username = UserInfo.getUsername();
	private String emar_fid = "", feeinfo;
	private boolean isback = false;

	@Command
	public void addwin(@BindingParam("win") Window win) {
		if (fidlist.size() <= 0) {
			Messagebox.show("该员工没有档案信息，不能做档案调出", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			reUrl();
		}
	}

	private void reUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
	}

	public Archive_FileCheckOutController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tperid=Executions.getCurrent().getArg().get("id").toString();
		}
		if (gid == null || gid.equals("null") || gid.equals("")) {
			List<EmArchiveDatumModel> elist = bll
					.getEmArchiveDatumInfo(" and eada_id=" + id);
			if (elist.size() > 0) {
				gid = elist.get(0).getGid().toString();
				cid = elist.get(0).getCid().toString();
				if (embaselist.size() <= 0) {
					embaselist = selectbll.getEmBaseInfo(" and gid=" + gid);
				}
			}
		}

		if (embaselist.size() > 0) {
			emmodel = embaselist.get(0);
			gid = emmodel.getGid() + "";
			cid = emmodel.getCid() + "";
		}

		if (id != null && id != "" && !id.equals("")) {
			List<TaskProcessViewModel> tlist = bll.getLastId(tperid);
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
			if (gid.equals("")) {

			}
		}
		fidlist = bll.getFidInfo(Integer.parseInt(gid));
		if (fidlist.size() == 1) {
			emar_fid = fidlist.get(0).getEmar_fid();
		}
		if (models.getEada_final() != null
				&& models.getEada_final().equals("已退回")) {
			isback = true;
		}
	}

	// 调出新增提交
	@Command
	public void summit(@BindingParam("charge") String charge,
			@BindingParam("win") Window win, @BindingParam("yorn") String yorn,
			@BindingParam("remark") String remark,
			@BindingParam("chargedate") Date chargedate,
			@BindingParam("checkoutdate") Date checkoutdate,
			@BindingParam("checkoutmode") String checkoutmode,
			@BindingParam("checkoutsetup") String checkoutsetup,
			@BindingParam("checkoutreason") String checkoutreason,
			@BindingParam("fid") Combobox fid) {
		if (fidlist.size() > 0) {
			if (fid.getValue() == null || fid.getValue().equals("")) {
				Messagebox.show("请选择档案号", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (chargedate == null) {
				Messagebox.show("请选择服务终止时间", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				flist = bll.getEmArchiveInfo(" and emar_state=1 and a.gid="
						+ gid);
				if (flist.size() > 0) {
					fmodel = flist.get(0);
					EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
					EmArchiveDatumModel model = new EmArchiveDatumModel();
					model.setGid(Integer.parseInt(gid));
					model.setCid(Integer.parseInt(cid));
					model.setEada_type("新增档案调出");
					model.setEada_addname(UserInfo.getUsername());
					model.setEada_final(yorn);
					model.setEada_charge(charge);
					model.setEada_chargedate(chargedate);
					model.setEada_checkoutmode(checkoutmode);
					model.setEada_checkoutreason(checkoutreason);
					model.setEada_checkoutdate(checkoutdate);
					model.setEada_checkoutsetup(checkoutsetup);
					if (fid.getValue() != null && !fid.getValue().equals("")) {
						if (fid.getSelectedItem() != null) {
							model.setEada_fid(fid.getSelectedItem().getValue()
									.toString());
						} else {
							model.setEada_fid(getFid(fid.getValue()));
							if (model.getEada_fid() == null
									|| model.getEada_fid().equals("")) {
								if (fidlist.size() > 0) {
									model.setEada_fid(fidlist.get(0)
											.getEmar_fid());
								} else {
									model.setEada_fid(fid.getValue());
								}
							}
						}
					} else {
						if (fidlist.size() > 0) {
							model.setEada_fid(fidlist.get(0).getEmar_fid());
						} else {
							model.setEada_fid("");
						}
					}
					if (model.getEada_fid() != null
							&& !model.getEada_fid().equals("")) {
						// 根据档案号查询档案是否已有调出信息
						Integer existOK = bll.ifExistCheckOutInfo(model
								.getEada_fid(),model.getGid());
						if(existOK>0)
						{
							Messagebox.show("该档案已有调出信息，不能再调出", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
							return;
						}
						// 新增业务数据，并返回业务表ID
						String[] str = blloper.EmArchiveCheckOut(model, remark,
								"0", fmodel);
						// 判断业务id是否为空
						if (str[0].equals("1")) {
							Messagebox.show("提交成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							reUrl();
						} else {
							Messagebox.show(str[1], "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
					else
					{
						Messagebox.show("请选择档案号", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("找不到员工档案信息", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} else {
			Messagebox.show("该员工没有档案信息，不能做档案调出", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			reUrl();
		}
	}

	// 调出新增确认提交
	@Command
	public void summitupdate(@BindingParam("charge") final String charge,
			@BindingParam("win") final Window win,
			@BindingParam("yorn") final String yorn,
			@BindingParam("remark") final String remark,
			@BindingParam("chargedate") final Date chargedate,
			@BindingParam("checkoutdate") final Date checkoutdate,
			@BindingParam("checkoutmode") final String checkoutmode,
			@BindingParam("checkoutsetup") final String checkoutsetup,
			@BindingParam("checkoutreason") final String checkoutreason) {
		/*
		 * if (chargedate == null) { Messagebox.show("请选择员工欠费起始时间", "操作提示",
		 * Messagebox.OK, Messagebox.ERROR); } else
		 */
		if (checkoutreason.equals("") || checkoutreason == "") {
			Messagebox.show("请填写调出原因", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
			EmArchiveDatumModel model = new EmArchiveDatumModel();
			model.setGid(Integer.parseInt(gid));
			model.setCid(Integer.parseInt(cid));
			model.setEada_id(Integer.parseInt(id));
			model.setEada_tapr_id(Integer.parseInt(tperid));
			model.setEada_type("数据确认");
			model.setEada_addname(UserInfo.getUsername());
			model.setEada_final(yorn);
			model.setEada_charge(charge);
			model.setEada_chargedate(chargedate);
			model.setEada_checkoutmode(checkoutmode);
			model.setEada_checkoutreason(checkoutreason);
			model.setEada_checkoutdate(checkoutdate);
			model.setEada_checkoutsetup(checkoutsetup);
			String sql = ",eada_charge='" + charge + "'";
			String sqls = ",eada_final=1";
			// 修改业务数据，并返回业务表ID
			String[] str = blloper.EmArchiveCheckOut(model, remark, yorn, sqls,
					0);
			// 判断业务id是否为空
			if (str[0].equals("1")) {
				Messagebox.show("提交成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 调出交接材料事件
	@Command
	public void summitdata(@BindingParam("win") final Window win,
			@BindingParam("docGrid") final Grid docGrid) {
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		try {
			// 调用内联页方法chkHaveTo(Grid gd)
			String[] message = docOC.AddchkHaveTo(docGrid);
			EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
			// 判断材料必选项是否已选
			if (message[0].equals("1")) {
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				model.setGid(Integer.parseInt(gid));
				model.setCid(Integer.parseInt(cid));
				model.setEada_type(tmodel.getWfno_name());
				model.setEada_addname(UserInfo.getUsername());
				model.setEada_tapr_id(Integer.parseInt(tperid));
				model.setEada_id(Integer.parseInt(id));
				// 修改业务数据，并返回业务表ID
				String[] str = blloper.EmArchiveCheckOut(model, "", "1", "", 0);
				// 判断业务id是否为空
				if (str[0].equals("1")) {
					// 调用内联页方法submitDoc(Grid gd)
					message = docOC.AddsubmitDoc(docGrid, str[2]);
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 调出财务结算事件
	@Command
	public void summitaccount(@BindingParam("win") final Window win,
			@BindingParam("ftime") final Date ftime,
			@BindingParam("remark") final String remark) {
		if (ftime != null) {
			try {
				EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				model.setGid(Integer.parseInt(gid));
				model.setCid(Integer.parseInt(cid));
				model.setEada_type(tmodel.getWfno_name());
				model.setEada_addname(UserInfo.getUsername());
				model.setEada_tapr_id(Integer.parseInt(tperid));
				model.setEada_id(Integer.parseInt(id));
				String sql = ",eada_paydate='" + getStringDate(ftime)
						+ "',eada_accountdate='" + getStringDate(new Date())
						+ "',eada_final=3";
				// 修改业务数据，并返回业务表ID
				String[] str = blloper.EmArchiveCheckOut(model, remark, "1",
						sql, 0);
				// 判断业务id是否为空
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择结算费用时间", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 中智档案调出结束
	@Command
	public void summitend(@BindingParam("win") final Window win,
			@BindingParam("wtmode") final String wtmode,
			@BindingParam("outtype") final String outtype,
			@BindingParam("outdate") final Date outdate,
			@BindingParam("outseasion") final String outseasion,
			@BindingParam("tocom") final String tocom) {
		if (outdate == null) {
			Messagebox.show("请选择调出时间", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else if (models.getEada_filetype().equals("中智保管") && outtype == ""
				&& outtype.equals("")) {
			Messagebox.show("请填写调出方式", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else if (tocom == "" && tocom.equals("")) {
			Messagebox.show("请填写调往单位", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			try {
				EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				model.setGid(Integer.parseInt(gid));
				model.setCid(Integer.parseInt(cid));
				model.setEada_type(tmodel.getWfno_name());
				model.setEada_addname(UserInfo.getUsername());
				model.setEada_tapr_id(Integer.parseInt(tperid));
				model.setEada_id(Integer.parseInt(id));
				model.setEada_wtmode(wtmode);
				model.setEada_checkoutmode(outtype);
				model.setEada_checkoutdate(timechange(outdate));
				model.setEada_checkoutreason(outseasion);
				model.setEada_checkoutsetup(tocom);
				model.setEada_final("3");
				// 修改业务数据，并返回业务表ID
				String[] str = blloper.EmArchiveCheckOutEnd(model);
				// 判断业务id是否为空
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 市内调出时缴费判断
	@Command
	public void summitifarrears(@BindingParam("win") final Window win,
			@BindingParam("starddate") final Date starddate,
			@BindingParam("enddate") final Date enddate,
			@BindingParam("remark") final String remark,
			@BindingParam("ifArrears") final String ifArrears) {
		if (ifArrears == null || ifArrears == "" || ifArrears.equals("")) {
			Messagebox.show("请选择是否欠费", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			int ifarrears = 0;
			if (ifArrears == "否" || ifArrears.equals("否")) {
				ifarrears = 1;
			}
			try {
				EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				model.setGid(Integer.parseInt(gid));
				model.setCid(Integer.parseInt(cid));
				model.setEada_type(tmodel.getWfno_name());
				model.setEada_addname(UserInfo.getUsername());
				model.setEada_tapr_id(Integer.parseInt(tperid));
				model.setEada_id(Integer.parseInt(id));
				// model.setEada_charge(accountinfo);
				model.setEada_ifArrears(ifarrears);
				String sql = ",eada_starddate='" + datetostr(starddate)
						+ "',eada_stopdate='" + datetostr(enddate)
						+ "',eada_accountdate='" + getStringDate(new Date())
						+ "',eada_ifArrears=" + ifarrears;
				if (feeinfo != null && !feeinfo.equals("")) {
					if (ifArrears != null && ifArrears.equals("是")) {
						sql = sql + ",eada_arrearageinfo='" + feeinfo
								+ "',eada_iffee=1";
					}
				}
				if (models.getEada_dnfee() != null) {
					sql = sql + ",eada_dnfee='" + models.getEada_dnfee() + "'";
				}
				if (models.getEada_hkfee() != null) {
					sql = sql + ",eada_hkfee='" + models.getEada_hkfee() + "'";
				}
				if (models.getEada_promisefee() != null) {
					sql = sql + ",eada_promisefee='"
							+ models.getEada_promisefee() + "'";
				}
				// 修改业务数据，并返回业务表ID
				String[] str = blloper.EmArchiveCheckOutifArrears(model,
						remark, "1", sql, 0);
				// 判断业务id是否为空
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 市内调出时财务确认
	@Command
	public void summitable(@BindingParam("win") final Window win,
			@BindingParam("ftime") final Date ftime,
			@BindingParam("remark") final String remark) {

		if (ftime == null) {
			Messagebox.show("请输入确认费用时间", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			try {
				EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
				EmArchiveDatumModel model = new EmArchiveDatumModel();
				model.setGid(Integer.parseInt(gid));
				model.setCid(Integer.parseInt(cid));
				model.setEada_type(tmodel.getWfno_name());
				model.setEada_addname(UserInfo.getUsername());
				model.setEada_tapr_id(Integer.parseInt(tperid));
				model.setEada_id(Integer.parseInt(id));
				String sql = ",eada_paydate='" + getStringDate(ftime)
						+ "',eada_accountdate='" + getStringDate(new Date())
						+ "'";
				// 修改业务数据，并返回业务表ID
				String[] str = blloper.EmArchiveCheckOut(model, remark, "1",
						sql, 0);
				// 判断业务id是否为空
				if (str[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				System.out.println("错误：" + e.getMessage());
				Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id.toString());
		map.put("typeid", "2");
		map.put("gid", gid+"");
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
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

	// 退回
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

	// 财务确认步骤退回到上一步
	@Command
	public void backpre(@BindingParam("win") final Window win) {
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

	public String getTperid() {
		return tperid;
	}

	public void setTperid(String tperid) {
		this.tperid = tperid;
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

	public List<EmArchiveModel> getFidlist() {
		return fidlist;
	}

	public void setFidlist(List<EmArchiveModel> fidlist) {
		this.fidlist = fidlist;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmar_fid() {
		return emar_fid;
	}

	public void setEmar_fid(String emar_fid) {
		this.emar_fid = emar_fid;
	}

	public boolean isIsback() {
		return isback;
	}

	public void setIsback(boolean isback) {
		this.isback = isback;
	}

	public String getFeeinfo() {
		return feeinfo;
	}

	public void setFeeinfo(String feeinfo) {
		this.feeinfo = feeinfo;
	}

	public static String getStringDate(Date d) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String datetostr(Date date) {
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			str = formatter.format(date);
		}
		return str;
	}

	// 时间格式转换
	private Date timechange(java.util.Date d) {
		Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}

	private String getFid(String emar_fid) {
		String fid = "";
		for (EmArchiveModel am : fidlist) {
			if (am.getEmar_fid().equals(emar_fid)) {
				fid = am.getEmar_fid();
			}
		}
		return fid;

	}
}
