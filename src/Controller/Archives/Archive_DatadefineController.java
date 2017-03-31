package Controller.Archives;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.DocumentsInfoModel;
import Model.DocumentsSubmitInfoModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveRemarkModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

public class Archive_DatadefineController {
	@Wire
	private Textbox reason;
	@Wire
	private Textbox remark;
	@Wire
	private Grid docGrid;
	@Wire
	private Radiogroup yorn;
	Integer tid = 0;
	private int gid = 14652;
	private int cid = 1059;

	private List<EmArchiveRemarkModel> listr;
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid =null;
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private EmArchiveRemarkModel modelr = new EmArchiveRemarkModel();
	private List<EmArchiveDatumModel> list = bll
			.getEmArchiveDatumInfo(" and eada_id=" + id);
	private EmArchiveDatumModel models = new EmArchiveDatumModel();;
	private String remarks = "";
	private String ifend = "";
	private List<TaskProcessViewModel> tlist =new ArrayList<TaskProcessViewModel>();
	private TaskProcessViewModel tmodel = new TaskProcessViewModel();
	private List<String> modell = bll.getLoginInfo();

	public Archive_DatadefineController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tperid=Executions.getCurrent().getArg().get("id").toString();
			tlist = bll.getLastId(tperid);
		}
		if (list.size() > 0) {
			models = list.get(0);
			gid = models.getGid();
			cid = models.getCid();
			listr = bll.getEmArchiveRemarkInfo(" and eare_trid=" + id
					+ " and eare_tid=1 order by eare_id desc");
			if (!listr.isEmpty()) {
				remarks = listr.get(0).getEare_content();
			}
			if (!tlist.isEmpty()) {
				tmodel = tlist.get(0);
			}
		} else {
			Messagebox.show("没有找到该员工信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 提交更新——查借材料
	@Command
	public void summitupdate(@BindingParam("reason") final String reason,
			@BindingParam("remark") final String remark,
			@BindingParam("yorn") final Radio yorn,
			@BindingParam("gd") final Grid docgrid,
			@BindingParam("outdate") final Date outdate,
			@BindingParam("backdate") final Date backdate,
			@BindingParam("leanpeop") final String leanpeop,
			@BindingParam("windetial") final Window windetial,
			@BindingParam("returndate") final Date returndate) {
		EmArchiveDatumModel model = new EmArchiveDatumModel();
		model.setGid(gid);
		model.setCid(cid);
		model.setEada_id(Integer.parseInt(id));
		model.setEada_lendcause(reason);
		model.setEada_type("查借材料");
		model.setEada_remark(remarks);
		model.setEada_addname(UserInfo.getUsername());
		model.setEada_lenddate(outdate);
		model.setEada_returnarchivedate(backdate);
		model.setEada_tapr_id(models.getEada_tapr_id());
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

		// 判断状态,第二步——确认
		if (tmodel.getWfno_step() == 2) {
			if (!reason.equals("") && reason != "") {

				try {
					// 调用内联页方法chkHaveTo(Grid gd)
					String[] message = docOC.UpchkHaveTo(docgrid);
					EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
					// 判断材料必选项是否已选
					if (message[0].equals("1")) {
						String sql = ",eada_lendcause='" + reason
								+ "',eada_final=1";
						// 新增业务数据，并返回业务表ID
						String[] str = bll.EmArchiveAddUpdate(model, remark,
								yorn.getValue() + "", sql);
						// 判断业务id是否为空
						if (str[0].equals("1")) {
							// 调用内联页方法submitDoc(Grid gd)
							message = docOC.UpsubmitDoc(docgrid, id);
							Messagebox.show("提交成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							windetial.detach();
						} else {
							Messagebox.show("提交失败", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				} catch (Exception e) {
					System.out.println("错误：" + e.getMessage());
					Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请填入借阅原因", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		// 第三步——受理
		else if (tmodel.getWfno_step() == 3) {

			String sql = ",eada_final=2";
			updatestate(model, sql, windetial);
			windetial.detach();

		}
		// 第五步——借出材料
		else if (tmodel.getWfno_step() == 5) {
			if (outdate != null && !outdate.equals("")) {
				if (leanpeop != "" || !leanpeop.equals("")) {
					String sql = ",eada_lenddate='" + dateToString(outdate)
							+ "',eada_lendpeople='" + leanpeop + "'";
					try {
						docOC.UpsubmitDoc(docgrid, id);
						// updatestate(model,sql,windetial);
						EmArchiveDatum_OperateBll bl = new EmArchiveDatum_OperateBll();
						String[] str = bl.sendoutdata(model, sql, ifend);
						if (str[0] == "1") {
							Messagebox.show("提交成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							windetial.detach();
						} else {
							Messagebox.show(str[1], "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					windetial.detach();
				} else {
					Messagebox.show("请选择交接人", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请选择借出时间", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		// 第六步——前道归还材料
		else if (tmodel.getWfno_step() == 6) {
			if (returndate != null && !returndate.equals("")) {
				String sql = ",eada_final=3,eada_returndate='"
						+ dateToString(returndate) + "',eada_returnname='"
						+ UserInfo.getUsername() + "'";
				try {
					docOC.UpsubmitDoc(docgrid, id);
					updatestate(model, sql, windetial);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Messagebox.show("请选择归还时间", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		// 第六步——人事签收归还材料
		else if (tmodel.getWfno_step() == 7) {
			if (backdate != null && !backdate.equals("")) {
				String sql = ",eada_final=3,eada_returnarchivedate='"
						+ dateToString(backdate) + "'";
				try {
					docOC.UpsubmitDoc(docgrid, id);
					updatestate(model, sql, windetial);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Messagebox.show("请选择归还时间", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		// 第七步——结束
		else if (tmodel.getWfno_step() == 8) {
			String sql = ",eada_final=3";
			updatestate(model, sql, windetial);

		}
	}

	// 提交更新——开具证明
	@Command
	public void summitupdateprove(@BindingParam("reason") final String reason,
			@BindingParam("remark") final String remark,
			@BindingParam("yorn") final Radio yorn,
			@BindingParam("gd") final Grid docgrid,
			@BindingParam("win") final Window win) {
		EmArchiveDatumModel model = new EmArchiveDatumModel();
		model.setGid(gid);
		model.setCid(cid);
		model.setEada_id(Integer.parseInt(id));
		model.setEada_cause(reason);
		model.setEada_type("开具证明");
		model.setEada_remark(remarks);
		model.setEada_addname(UserInfo.getUsername());
		model.setEada_tapr_id(models.getEada_tapr_id());
		// 判断状态，第二步——确认
		if (tmodel.getWfno_step() == 2) {
			String orserdata = AddsubmitDoc(docgrid);
			if (orserdata != "" && !orserdata.equals("")) {
				if (!reason.equals("") && reason != "") {
					DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
					try {
						// 调用内联页方法chkHaveTo(Grid gd)
						String[] message = docOC.UpchkHaveTo(docgrid);
						EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
						// 判断材料必选项是否已选
						if (message[0].equals("1")) {
							String sql = ",eada_cause='" + reason
									+ "',eada_final=1,eada_orderdata='"
									+ orserdata + "'";
							// 新增业务数据，并返回业务表ID
							String[] str = bll.EmArchiveAddUpdate(model,
									remark, yorn.getValue() + "", sql);
							// 判断业务id是否为空
							if (str[0].equals("1")) {
								// 调用内联页方法submitDoc(Grid gd)
								message = docOC.UpsubmitDoc(docgrid, id);
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
						Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("请填入出具证明事由", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请选择证明类型", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		// 第三步——受理
		else if (tmodel.getWfno_step() == 3) {
			win.detach();
		}
		// 第四步——出具材料（开证明）
		else if (tmodel.getWfno_step() == 4) {
			win.detach();
		}
		// 第五步——结束
		else if (tmodel.getWfno_step() == 5) {
			String sql = ",eada_final=3";
			win.detach();

		}

	}

	private String[] updatestate(EmArchiveDatumModel model, String sql,
			Window windetial) {
		EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
		// 新增业务数据，并返回业务表ID
		String[] str = bll.Accepted(model, sql);
		// 判断业务id是否为空
		if (str[0].equals("1")) {
			// 调用内联页方法submitDoc(Grid gd)
			Messagebox.show("提交成功", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			windetial.detach();
		} else {
			Messagebox.show("提交失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
		return str;
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
		map.put("gid",models.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getIfend() {
		return ifend;
	}

	public void setIfend(String ifend) {
		this.ifend = ifend;
	}

	/**
	 * Date转String
	 * 
	 * @param count
	 * @return
	 */
	private String dateToString(Date date) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String str = formatDate.format(date);
		return str;
	}

	// 获取证明类型
	public String AddsubmitDoc(Grid gd) {
		DocumentsSubmitInfoModel mode = new DocumentsSubmitInfoModel();
		String str = "";
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0); // 获取checkbox
			Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
			if (ckb.isChecked()) {// 判断是否选中
				mode = (DocumentsSubmitInfoModel) row.getValue();
				if (str == "") {
					str = mode.getDoin_content();
				} else {
					str = str + "," + mode.getDoin_content();
				}
			}
		}
		return str;
	}

}
