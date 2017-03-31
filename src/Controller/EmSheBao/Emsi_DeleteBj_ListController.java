package Controller.EmSheBao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmSheBao.Emsi_OperateDal;

import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

import Model.EmShebaoBJModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.UserInfo;

public class Emsi_DeleteBj_ListController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll;
	private EmbaseModel emModel;
	private List<EmShebaoBJModel> bjList;

	public Emsi_DeleteBj_ListController() {
		bll = new Emsi_SelBll();
		emModel = bll.getEmBase(gid);
		bjList = bll.getBjListByGid(gid);
	}

	// 删除
	@Command("delBj")
	@NotifyChange("bjList")
	public void delBj(@BindingParam("bjM") EmShebaoBJModel bjM) {
		Emsi_OperateBll opbll = new Emsi_OperateBll();
		//try {
			if (Messagebox.show("确认删除该社保补缴信息吗？", "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				String[] message;

				// 获取医疗补交数据
				Emsi_OperateDal eDal = new Emsi_OperateDal();
				boolean ifJLBJ = eDal.getShebaoBJJL(bjM.getGid(),
						bjM.getOwnmonth(), bjM.getEmsb_startmonth());

				if (bjM.getEmsb_tapr_id() == 0) {// 无任务单
					if (ifJLBJ) {// 判断是否有医疗补交
						// 通过养老补交获取医疗补交数据
						EmShebaoBJModel jlM = new EmShebaoBJModel();
						Emsi_SelBll bll = new Emsi_SelBll();
						jlM = bll.getBjJLListByBJid(bjM.getId());
						opbll.DelBjJL(jlM.getId());
					}

					message = opbll.DelBj(bjM.getId());

				} else {
					if (ifJLBJ) {// 判断是否有医疗补交
						// 通过养老补交获取医疗补交数据
						EmShebaoBJModel jlM = new EmShebaoBJModel();
						Emsi_SelBll bll = new Emsi_SelBll();
						jlM = bll.getBjJLListByBJid(bjM.getId());

						opbll.DelBjJL(jlM.getId(), jlM.getEmsb_tapr_id(),
								UserInfo.getUsername());
					}
					message = opbll.DelBj(bjM.getId(), bjM.getEmsb_tapr_id(),
							UserInfo.getUsername());
				}

				if ("1".equals(message[0])) {
					Messagebox.show("删除社保补缴成功。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
					bjList = bll.getBjListByGid(gid);
				} else {
					Messagebox.show("删除社保补缴失败。", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				}
			}
		/*} catch (Exception e) {
			Messagebox.show("删除社保补缴出错", "操作提示", Messagebox.OK, Messagebox.NONE);
		}*/
	}

	// 确认
	@Command("confirmBj")
	@NotifyChange("bjList")
	public void confirmBj(@BindingParam("m") EmShebaoBJModel m) {
		if (Integer.parseInt(UserInfo.getDepID().toString()) != 16
				&& Integer.parseInt(UserInfo.getDepID().toString()) != 8) {
			Messagebox.show("请等待雇员服务中心处理.", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		} else {
			if (Messagebox.show("是否确认该社保补缴信息？", "操作提示", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				try {
					String message[];
					String message2[];
					String msg = "";
					Emsi_OperateBll opbll = new Emsi_OperateBll();

					Emsi_OperateDal eDal = new Emsi_OperateDal();
					boolean ifUkey = eDal.getShebaoUkey(m.getGid());
					int diffownmonth = DateStringChange.diffOwnmonth(
							Integer.parseInt(DateStringChange.getOwnmonth()),
							m.getOwnmonth());
					boolean ifJLBJ = eDal.getShebaoBJJL(m.getGid(),
							m.getOwnmonth(), m.getEmsb_startmonth());

					if (ifJLBJ == false) {
						if ((m.getEmsb_single() != 1 && diffownmonth <= 3)
								|| (diffownmonth <= 3 && ifUkey)) {
							m.setEmsb_ifdeclare(8);
						} else {
							m.setEmsb_ifdeclare(0);
						}
					} else {
						if (m.getEmsb_single() != 1 && diffownmonth <= 3) {
							m.setEmsb_ifdeclare(7);
						} else {
							m.setEmsb_ifdeclare(0);
						}
					}

					if (m.getEmsb_tapr_id() != 0) {

						Emsc_DeclareOperateBll eopbll = new Emsc_DeclareOperateBll();
						message = eopbll.BjDeclareUpState(m,
								UserInfo.getUsername());
					} else {
						message = opbll.BjDeclareStartTask(m,
								UserInfo.getUsername());

						// 新增医疗
						if (ifJLBJ == true && "1".equals(message[0])) {
							// 通过养老补交获取医疗补交数据
							EmShebaoBJModel jlM = new EmShebaoBJModel();

							jlM = bll.getBjJLListByBJid(m.getId());
							if (jlM.getEmsb_single() != 1 && diffownmonth <= 3) {
								jlM.setEmsb_ifdeclare(7);
							} else {
								jlM.setEmsb_ifdeclare(0);
							}

							message2 = opbll.BjJLDeclareStartTask(jlM,
									UserInfo.getUsername());
							if (!"1".equals(message2[0])) {
								msg = "，但是社保医疗数据确认失败，请联系IT部！";
							}
						}
					}

					if ("1".equals(message[0])) {
						// 弹出提示
						Messagebox.show(message[1] + msg, "操作提示",
								Messagebox.OK, Messagebox.NONE);
						bjList = bll.getBjListByGid(gid);
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} catch (Exception e) {
					Messagebox.show("确认社保补缴出错。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	// 重新发送
	@Command("resendBj")
	@NotifyChange("bjList")
	public void resendBj(@BindingParam("id") int id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("daid", String.valueOf(id));
		Window window = (Window) Executions.createComponents(
				"/EmSheBao/Emsi_BjReSend.zul", null, map);
		window.doModal();
		bjList = bll.getBjListByGid(gid);
	}

	// 撤回
	@Command("revokeBj")
	@NotifyChange("bjList")
	public void revokeBj(@BindingParam("id") int id,
			@BindingParam("taprid") int tapr_id) {
		if (Messagebox.show("是否撤回该社保补缴信息？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			try {
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String message[] = opbll.revokeBj(id, tapr_id,
						UserInfo.getUsername());
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					bjList = bll.getBjListByGid(gid);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("确认社保补缴出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public EmbaseModel getEmModel() {
		return emModel;
	}

	public List<EmShebaoBJModel> getBjList() {
		return bjList;
	}

}
