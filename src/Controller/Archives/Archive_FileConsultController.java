package Controller.Archives;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmHouseChangeModel;
import Model.EmbaseModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class Archive_FileConsultController extends SelectorComposer<Component> {
	EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	@Wire
	private Label name;
	@Wire
	private Label idcard;
	@Wire
	private Textbox reason;
	@Wire
	private Textbox remark;
	@Wire
	private Label eaid;
	@Wire
	private Grid docGrid;
	@Wire
	private Radiogroup yorn;
	@Wire
	private Window winadd;
	Integer tid = 0;
	private String gid = Executions.getCurrent().getArg().get("gid") + "";
	private String cid = Executions.getCurrent().getArg().get("cid") + "";
	List<EmbaseModel> embaselist = selectbll.getEmBaseInfo(" and gid=" + gid);
	EmbaseModel emmodel = new EmbaseModel();
	String info = "";

	public void doAfterCompose(Component comp) {
		try {
			super.doAfterCompose(comp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Listen("onCreate=#winadd")
	public void addWin() {
		{
			try {

				if (embaselist.size() > 0) {
					emmodel = embaselist.get(0);
					gid = emmodel.getGid() + "";
					name.setValue(emmodel.getEmba_name());
					idcard.setValue(emmodel.getEmba_idcard());
					cid = emmodel.getCid() + "";
				}
				// if(emmodel.getEmba_state()==1)
				// {
				if (gid != null && !gid.equals("")) {
					EmArchive_SelectBll bll = new EmArchive_SelectBll();
					int k = bll.getIfEmArchiveInfo(Integer.parseInt(gid));
					if (k <= 0) {
						info = "该员工没有档案信息，不能做任何操作";
						Messagebox.show(info, "提示", Messagebox.OK,
								Messagebox.ERROR);
						reUrl();
					}
				} else {
					info = "该员工没有档案信息，不能做任何操作";
					Messagebox
							.show(info, "提示", Messagebox.OK, Messagebox.ERROR);
					reUrl();
				}
			}
			// else
			/*
			 * { info="该员工已离职，不能做该操作"; Messagebox.show(info, "提示",
			 * Messagebox.OK, Messagebox.ERROR); reUrl(); } }
			 */
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void reUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
	}

	// 查借材料新增提交
	@Listen("onClick =#summit")
	public void summit() {
		if (info == "") {
			if (!reason.getValue().equals("") && reason.getValue() != "") {
				String info = isSelectedData(docGrid);
				if (info == "") {
					DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
					try {
						// 调用内联页方法chkHaveTo(Grid gd)
						String[] message = docOC.AddchkHaveTo(docGrid);
						EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
						// 判断材料必选项是否已选
						if (message[0].equals("1")) {
							EmArchiveDatumModel model = new EmArchiveDatumModel();
							EmbaseModel emmodel = bll.getEmbaseinfo(" and gid="
									+ gid);
							model.setGid(Integer.parseInt(gid));
							model.setCid(Integer.parseInt(cid));
							model.setEada_lendcause(reason.getValue());
							model.setEada_type(emmodel.getEmba_name() + "("
									+ emmodel.getGid() + ")查借材料申请");
							model.setEada_addname(UserInfo.getUsername());
							model.setEada_final(yorn.getSelectedItem()
									.getValue() + "");
							// 新增业务数据，并返回业务表ID
							String[] str = bll.EmArchiveAddData(model,
									remark.getValue(), yorn.getSelectedItem()
											.getValue() + "");
							// 判断业务id是否为空
							if (str[0].equals("1")) {
								// 调用内联页方法submitDoc(Grid gd)
								message = docOC
										.AddsubmitDocout(docGrid, str[3]);
								Messagebox.show("提交成功", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								reUrl();
							} else {
								Messagebox.show("提交失败", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						} else {
							Messagebox.show("有必选资料没有选", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} catch (Exception e) {
						System.out.println("错误：" + e.getMessage());
						Messagebox.show("操作失败。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox
							.show(info, "提示", Messagebox.OK, Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请填入借阅原因", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show(info, "提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 判断是否有勾选材料
	private String isSelectedData(Grid gd) {
		String str = "没有选择材料，不能提交";
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			Cell cl = (Cell) gd.getCell(i, 0);
			Checkbox cb = (Checkbox) cl.getChildren().get(0);
			if (cb.isChecked()) {
				str = "";
				break;
			}
		}
		return str;
	}

	// 查借材料新增更新提交
	@Listen("onClick =#summitupdate")
	public void summitupdate() {
		if (!reason.getValue().equals("") && reason.getValue() != "") {
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				// 调用内联页方法chkHaveTo(Grid gd)
				String[] message = docOC.AddchkHaveTo(docGrid);
				EmArchiveDatum_OperateBll bll = new EmArchiveDatum_OperateBll();
				// 判断材料必选项是否已选
				if (message[0].equals("1")) {
					EmArchiveDatumModel model = new EmArchiveDatumModel();
					model.setGid(Integer.parseInt(gid));
					model.setCid(Integer.parseInt(cid));
					model.setEada_id(Integer.parseInt(eaid.getValue()));
					model.setEada_lendcause(reason.getValue());
					model.setEada_type("确认数据");
					model.setEada_addname(UserInfo.getUsername());
					String sql = ",eada_lendcause=" + reason.getValue();
					// 新增业务数据，并返回业务表ID
					String[] str = bll.EmArchiveAddUpdate(model,
							remark.getValue(), yorn.getSelectedItem()
									.getValue() + "", sql);
					// 判断业务id是否为空
					if (str[0].equals("1")) {
						// 调用内联页方法submitDoc(Grid gd)
						message = docOC.AddsubmitDocout(docGrid,
								model.getEada_id() + "");
						Messagebox.show("提交成功", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						reUrl();
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
			Messagebox.show("请填入借阅原因", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

}
