package Controller.Archives;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.DocumentsInfoModel;
import Model.EmArchiveDatumModel;
import Model.EmbaseModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;

public class Archive_FileProveController extends SelectorComposer<Component> {
	@Wire
	private Label name;
	@Wire
	private Label idcard;
	@Wire
	private Textbox reason;
	@Wire
	private Textbox remark;
	@Wire
	private Textbox content;
	@Wire
	private Label eaid;
	@Wire
	private Grid docGrid;
	@Wire
	private Radiogroup yorn;
	@Wire
	private Window winz;
	Integer tid = 0;
	private String gid = Executions.getCurrent().getArg().get("gid") + "";
	private String cid = Executions.getCurrent().getArg().get("cid") + "";
	EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	List<EmbaseModel> embaselist = selectbll
			.getEmBaseInfo(" and gid=" + gid);
	EmbaseModel emmodel = new EmbaseModel();

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

	}

	@Listen("onCreate=#winz")
	public void addWin() {
		{
			try {

				//if (emmodel.getEmba_state() == 1) {
					cid = emmodel.getCid() + "";
					if (emmodel.getEmba_name() != null) {
						name.setValue(emmodel.getEmba_name().toString());
					}
					if (emmodel.getEmba_idcard() != null) {
						idcard.setValue(emmodel.getEmba_idcard().toString());
					}
//				} else {
//					Messagebox.show("该员工已离职，不能做该操作", "提示", Messagebox.OK,
//							Messagebox.ERROR);
//					reUrl();
//				}

				if (gid != null && !gid.equals("")) {
					EmArchive_SelectBll bll = new EmArchive_SelectBll();
					int k = bll.getIfEmArchiveInfo(Integer.parseInt(gid));
					if (k <= 0) {
						Messagebox.show("该员工没有档案信息，不能做任何操作", "提示",
								Messagebox.OK, Messagebox.ERROR);
						reUrl();
					}
				} else {
					Messagebox.show("没有该员工信息，不能做任何操作", "提示", Messagebox.OK,
							Messagebox.ERROR);
					reUrl();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void reUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
	}

	public Archive_FileProveController() {
		if (embaselist.size() > 0) {
			emmodel = embaselist.get(0);
			gid = emmodel.getGid() + "";

		}
	}

	// 提交
	@Listen("onClick =#summit")
	public void summit() {
		String orserdata = AddsubmitDoc(docGrid);
		if (orserdata != "" && !orserdata.equals("")) {
			if (content.getValue() == null || content.getValue().equals("")) {
				Messagebox.show("请填入证明内容", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (reason.getValue() == null
					|| reason.getValue().equals("")) {
				Messagebox.show("请填写出具证明事由", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (!reason.getValue().equals("") && reason.getValue() != "") {
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
							if(cid!=null){
							model.setCid(Integer.parseInt(cid));
							}
							model.setEada_prove(content.getValue());
							model.setEada_cause(reason.getValue());
							model.setEada_type(emmodel.getEmba_name() + "("
									+ emmodel.getGid() + ")出具证明申请");
							model.setEada_addname(UserInfo.getUsername());
							model.setEada_final(yorn.getSelectedItem()
									.getValue() + "");
							model.setEada_orderdata(orserdata);
							// 新增业务数据，并返回业务表ID
							String[] str = bll.EmArchiveFileProveAdd(model,
									remark.getValue(), yorn.getSelectedItem()
											.getValue() + "");
							// 判断业务id是否为空
							if (str[0].equals("1")) {
								// 调用内联页方法submitDoc(Grid gd)
								message = docOC.AddsubmitDoc(docGrid, str[3]);
								Messagebox.show(str[1], "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								reUrl();
							} else {
								Messagebox.show(str[1], "操作提示", Messagebox.OK,
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
			}
		} else {
			Messagebox.show("请选择证明类型", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 获取证明类型
	public String AddsubmitDoc(Grid gd) {
		DocumentsInfoModel mode = new DocumentsInfoModel();
		String str = "";
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0); // 获取checkbox
			Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行
			if (ckb.isChecked()) {// 判断是否选中
				mode = (DocumentsInfoModel) row.getValue();
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
