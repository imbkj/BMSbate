package bll.EmSheBao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import dal.EmSheBao.Emsc_DeclareOperateDal;
import dal.EmSheBao.Emsi_OperateDal;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfOperateService;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeForeignerModel;
import Model.EmShebaoChangeMAModel;
import Model.EmShebaoSetupModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;

public class Emsc_DeclareOperateBll {
	// 社保补缴申报
	public String[] BjDeclare(List<EmShebaoBJModel> list, String username) {
		String[] message = new String[5];
		int sum = 0;
		try {
			for (EmShebaoBJModel m : list) {
				Object[] obj = { "0", m, username };
				WfOperateService wf = new WfOperateImpl(
						new Emsc_BjDeclareImpl());
				message = wf.PassToNext(obj, m.getEmsb_tapr_id(), username,
						"社保补缴申报", 0, "");
				if ("1".equals(message[0])) {

					// 更新相应的医疗补交数据状态
					Emsi_OperateDal eDal = new Emsi_OperateDal();
					boolean ifJLBJ = eDal.getShebaoBJJL(m.getGid(),
							m.getOwnmonth(), m.getEmsb_startmonth());
					if (ifJLBJ == true) {
						// 通过养老补交获取医疗补交数据
						EmShebaoBJModel jlM = new EmShebaoBJModel();
						Emsi_SelBll bll = new Emsi_SelBll();
						jlM = bll.getBjJLListByBJid(m.getId());
						jlM.setEmsb_dptime(m.getEmsb_dptime());

						// 更新医疗补交状态
						if (jlM.getEmsb_ifdeclare() != 6) {// 不能同时和养老补交一起更新成 已申报
															// 状态
							BjJLDeclare(jlM, UserInfo.getUsername());
						}

					}

					sum++;
				}
			}
			if (sum == list.size()) {
				message[0] = "1";
				message[1] = "社保补缴申报成功。";
			} else {
				sum = list.size() - sum;
				message[0] = "0";
				message[1] = "社保补缴申报共提交:" + list.size() + "条数据，其中有" + sum
						+ "条数据失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴申报出错。";
		}
		return message;
	}

	public String[] BjJLDeclare(EmShebaoBJModel m, String username) {
		String[] message = new String[5];
		try {
			Object[] obj = { "0", m, username };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjJLDeclareImpl());
			message = wf.PassToNext(obj, m.getEmsb_tapr_id(), username,
					"社保补缴申报", 0, "");
			if ("1".equals(message[0])) {

				message[0] = "1";
				message[1] = "社保医疗补缴申报成功。";
			} else {
				message[0] = "0";
				message[1] = "社保医疗补缴申报失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保医疗补缴申报出错。";
		}
		return message;
	}

	// 社保补缴编辑备注
	public int UpFlag(EmShebaoBJModel m, String username) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		return dal.UpFlag(m, username);
	}

	// 社保补缴编辑备注(医疗)
	public int UpJLFlag(EmShebaoBJModel m, String username) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		return dal.UpJLFlag(m, username);
	}

	// 外籍人社保变更编辑备注
	public int UpForeignerFlag(EmShebaoChangeForeignerModel m, String username) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		return dal.UpForeignerFlag(m, username);
	}

	// 社保生育津贴编辑备注
	public int UpChangeMaFlag(EmShebaoChangeMAModel m, String username) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		return dal.UpChangeMaFlag(m, username);
	}

	// 社保生育津贴上传申请表和批量表
	public int UpChangeMaAfBfFile(EmShebaoChangeMAModel m, Integer chk_af,
			Integer chk_bf) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		return dal.UpChangeMaAfBfFile(m, chk_af, chk_bf);
	}

	// 社保生育津贴编辑批次号
	public int UpChangeMaBatchNum(EmShebaoChangeMAModel m) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		return dal.UpChangeMaBatchNum(m);
	}

	// 社保生育津贴编辑金额
	public int UpChangeMaFee(EmShebaoChangeMAModel m) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		return dal.UpChangeMaFee(m);
	}

	// 社保补缴修改状态
	public String[] BjDeclareUpState(EmShebaoBJModel m, String username) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		try {
			Object[] obj = { "1", m, username };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjDeclareImpl());
			if (m.getEmsb_ifdeclare() == 3) {
				message = wf.ReturnToN(obj, m.getEmsb_tapr_id(), 1, username,
						m.getEmsb_reason());
			} else if (m.getEmsb_ifdeclare() == 4) {
				int i = dal.BjDeclareUpState(m, username);
				if (i == 1) {
					message[0] = "1";
					message[1] = "社保补缴操作成功。";
				} else {
					message[0] = "0";
					message[1] = "社保补缴操作失败。";
				}
			} else {
				message = wf.PassToNext(obj, m.getEmsb_tapr_id(), username, "",
						0, "");
			}
			if ("1".equals(message[0])) {
				message[0] = "1";
				message[1] = "社保补缴操作成功。";
			} else {

				message[0] = "0";
				message[1] = "社保补缴操作失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴操作出错。";
		}
		return message;
	}

	// 社保补缴修改状态(医疗)
	public String[] BjJLDeclareUpState(EmShebaoBJModel m, String username) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		try {
			Object[] obj = { "1", m, username };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjJLDeclareImpl());
			if (m.getEmsb_ifdeclare() == 3) {
				message = wf.ReturnToN(obj, m.getEmsb_tapr_id(), 1, username,
						m.getEmsb_reason());
			} else if (m.getEmsb_ifdeclare() == 4) {
				int i = dal.BjDeclareUpState(m, username);
				if (i == 1) {
					message[0] = "1";
					message[1] = "社保医疗补缴操作成功。";
				} else {
					message[0] = "0";
					message[1] = "社保医疗补缴操作失败。";
				}
			} else {
				message = wf.PassToNext(obj, m.getEmsb_tapr_id(), username, "",
						0, "");
			}
			if ("1".equals(message[0])) {
				message[0] = "1";
				message[1] = "社保医疗补缴操作成功。";
			} else {

				message[0] = "0";
				message[1] = "社保医疗补缴操作失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保医疗补缴操作出错。";
		}
		return message;
	}

	// 社保取消入职
	public String[] delEntryShebao(Integer gid) {
		String[] message = new String[2];
		try {
			EmSheBao_DSelectBll sbDSBll = new EmSheBao_DSelectBll();
			// 检查是否可以删除社保新增、调入、补交数据
			int chksbc = 0;
			int chksbf = 0;
			int chksbbj = 0;

			// 获取社保在册数据
			Emsi_SelBll sbubll = new Emsi_SelBll();
			EmShebaoUpdateModel upM;
			upM = sbubll.getShebaoUpdateByGid(gid);

			if (upM == null) {
				message[0] = "0";
				message[1] = "无社保在册数据，操作失败。";
			} else {
				// 获取最新月份的社保变更数据
				List<EmSheBaoChangeModel> sbList;
				sbList = sbDSBll.getEmSCData(
						" AND a.emsc_change in('调入','新增') AND a.gid="
								+ String.valueOf(gid) + " AND a.ownmonth="
								+ String.valueOf(upM.getOwnmonth()),
						" order by a.ownmonth");
				if (sbList.size() > 0) {
					EmSheBaoChangeModel sbM = sbList.get(0);
					if ("4".equals(sbM.getEmsc_ifdeclare())
							|| "0".equals(sbM.getEmsc_ifdeclare())) {
						// 调用删除方法
						Emsi_OperateBll opbll = new Emsi_OperateBll();
						String[] msg = opbll.DelChange(sbM.getId(), 1,
								sbM.getEmsc_tapr_id(), UserInfo.getUsername());

						if (!"1".equals(msg[0])) {
							chksbc = 1;
							message[1] = message[1] + "社保数据删除失败；";
						}
					} else {
						chksbc = 1;
					}

				}

				// 获取最新月份的外籍人社保变更信息
				List<EmShebaoChangeForeignerModel> sbfList;
				sbfList = sbDSBll.getEmSForCData(
						" AND a.emsc_change in('调入','新增') AND a.gid="
								+ String.valueOf(gid) + " AND a.ownmonth="
								+ String.valueOf(upM.getOwnmonth()),
						" order by a.ownmonth");
				if (sbfList.size() > 0) {
					EmShebaoChangeForeignerModel sbfM;
					sbfM = sbfList.get(0);
					if ("4".equals(sbfM.getEmsc_ifdeclare())
							|| "0".equals(sbfM.getEmsc_ifdeclare())) {
						// 调用删除方法
						Emsi_OperateBll opbll = new Emsi_OperateBll();
						String[] msg = opbll.DelChange(sbfM.getId(), 3,
								sbfM.getEmsc_tapr_id(), UserInfo.getUsername());

						if (!"1".equals(msg[0])) {
							chksbf = 1;
							message[1] = message[1] + "外籍人社保数据删除失败；";
						}
					} else {
						chksbf = 1;
					}
				}

				// 获取最新月份的社保补缴信息
				List<EmShebaoBJModel> sbbjList;
				sbbjList = sbDSBll.getEmSBjData(
						" AND a.gid=" + String.valueOf(gid)
								+ " AND a.ownmonth="
								+ String.valueOf(upM.getOwnmonth()),
						" order by a.ownmonth");
				if (sbbjList.size() > 0) {
					EmShebaoBJModel sbjM;
					for (int i = 0; i < sbbjList.size(); i++) {
						sbjM = sbbjList.get(i);
						if (sbjM.getEmsb_ifdeclare() == 0
								|| sbjM.getEmsb_ifdeclare() == 4) {
							// 调用删除方法
							String[] msg;
							Emsi_OperateBll opbll = new Emsi_OperateBll();

							// 获取医疗补交数据
							Emsi_OperateDal eDal = new Emsi_OperateDal();
							boolean ifJLBJ = eDal.getShebaoBJJL(sbjM.getGid(),
									sbjM.getOwnmonth(),
									sbjM.getEmsb_startmonth());

							if (sbjM.getEmsb_tapr_id() == 0) {// 无任务单
								// 医疗补交
								if (ifJLBJ == true) {// 先删除医疗再删除养老补交
									// 通过养老补交获取医疗补交数据
									EmShebaoBJModel jlM = new EmShebaoBJModel();
									Emsi_SelBll bll = new Emsi_SelBll();
									jlM = bll.getBjJLListByBJid(sbjM.getId());
									opbll.DelBjJL(jlM.getId());
								}

								msg = opbll.DelBj(sbjM.getId());

							} else {
								// 医疗补交
								if (ifJLBJ == true) {// 先删除医疗再删除养老补交
									// 通过养老补交获取医疗补交数据
									EmShebaoBJModel jlM = new EmShebaoBJModel();
									Emsi_SelBll bll = new Emsi_SelBll();
									jlM = bll.getBjJLListByBJid(sbjM.getId());

									opbll.DelBjJL(jlM.getId(),
											jlM.getEmsb_tapr_id(),
											UserInfo.getUsername());
								}

								msg = opbll.DelBj(sbjM.getId(),
										sbjM.getEmsb_tapr_id(),
										UserInfo.getUsername());

							}

							if (!"1".equals(msg[0])) {
								chksbbj = 1;
							}
						} else {
							chksbbj = 1;
						}
					}

					if (chksbbj == 1) {
						message[1] = message[1] + "社保补交数据删除失败；";
					}
				}

				// 删除在册数据
				int chksbu = 0;
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String[] msg = opbll.delSheBaoUpdate(gid);
				if (!"1".equals(msg[0])) {
					chksbu = 1;
					message[1] = message[1] + "社保在册数据删除失败；";
				}

				if (chksbc + chksbf + chksbbj + chksbu == 0) {
					message[0] = "1";
					message[1] = "操作成功。";
				} else {
					message[0] = "1";
					message[1] = "存在申报中的数据：" + message[1];
				}
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 社保养老补缴后道上传PDF文件
	public String[] BjDeclareUpload(EmShebaoBJModel m) {
		String[] message = new String[5];
		try {
			Object[] obj = { "2", m, UserInfo.getUsername() };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjDeclareImpl());
			message = wf.PassToNext(obj, m.getEmsb_tapr_id(),
					UserInfo.getUsername(), "", 0, "");

			if ("1".equals(message[0])) {
				message[0] = "1";
				message[1] = "社保补缴操作成功。";
			} else {

				message[0] = "0";
				message[1] = "社保补缴操作失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴操作出错。";
		}
		return message;
	}

	// 社保医疗补缴后道上传PDF文件
	public String[] BjJLDeclareUpload(EmShebaoBJModel m) {
		String[] message = new String[5];
		try {
			Object[] obj = { "2", m, UserInfo.getUsername() };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjJLDeclareImpl());
			message = wf.PassToNext(obj, m.getEmsb_tapr_id(),
					UserInfo.getUsername(), "", 0, "");

			if ("1".equals(message[0])) {
				message[0] = "1";
				message[1] = "社保补缴操作成功。";
			} else {

				message[0] = "0";
				message[1] = "社保补缴操作失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴操作出错。";
		}
		return message;
	}

	// 社保补缴客服下载PDF文件
	public String[] BjDeclareDownload(EmShebaoBJModel m) {
		String[] message = new String[5];
		try {
			Object[] obj = { "7", m, UserInfo.getUsername() };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjDeclareImpl());
			message = wf.PassToNext(obj, m.getEmsb_tapr_id(),
					UserInfo.getUsername(), "", 0, "");

			if ("1".equals(message[0])) {
				message[0] = "1";
				message[1] = "社保补缴操作成功。";
			} else {

				message[0] = "0";
				message[1] = "社保补缴操作失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴操作出错。";
		}
		return message;
	}

	// 社保补缴客服下载PDF文件(医疗)
	public String[] BjJLDeclareDownload(EmShebaoBJModel m) {
		String[] message = new String[5];
		try {
			Object[] obj = { "7", m, UserInfo.getUsername() };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjJLDeclareImpl());
			message = wf.PassToNext(obj, m.getEmsb_tapr_id(),
					UserInfo.getUsername(), "", 0, "");

			if ("1".equals(message[0])) {
				message[0] = "1";
				message[1] = "社保补缴操作成功。";
			} else {

				message[0] = "0";
				message[1] = "社保补缴操作失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴操作出错。";
		}
		return message;
	}

	// 社保补交退回并终止任务单
	public String[] BjDeclareUpState(EmShebaoBJModel m) {
		String[] message = new String[5];
		try {
			Object[] obj = { "1", m, m.getEmsb_addname() };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjDeclareImpl());

			message = wf.StopTask(obj, m.getEmsb_tapr_id(), "系统", "员工离职");

			if ("1".equals(message[0])) {
				message[0] = "1";
				message[1] = "社保补缴退回成功。";
			} else {
				message[0] = "0";
				message[1] = "社保补缴退回失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴退回出错。";
		}
		return message;
	}

	// 社保补交退回并终止任务单(医疗)
	public String[] BjJLDeclareUpState(EmShebaoBJModel m) {
		String[] message = new String[5];
		try {
			Object[] obj = { "1", m, m.getEmsb_addname() };
			WfOperateService wf = new WfOperateImpl(new Emsc_BjJLDeclareImpl());

			message = wf.StopTask(obj, m.getEmsb_tapr_id(), "系统", "员工离职");

			if ("1".equals(message[0])) {
				message[0] = "1";
				message[1] = "社保补缴退回成功。";
			} else {
				message[0] = "0";
				message[1] = "社保补缴退回失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保补缴退回出错。";
		}
		return message;
	}

	// 外籍人社保变更修改状态
	public String[] ForeignerDeclareUpState(EmShebaoChangeForeignerModel m,
			String username) {
		String[] message = new String[5];
		try {
			Object[] obj = { "0", m, username };
			WfOperateService wf = new WfOperateImpl(
					new Emsc_ForeignerDeclareImpl());
			if ("3".equals(m.getEmsc_ifdeclare())) {
				message = wf.ReturnToN(obj, m.getEmsc_tapr_id(), 1, username,
						m.getEmsc_reason());
			} else {
				message = wf.PassToNext(obj, m.getEmsc_tapr_id(), username, "",
						m.getCid(), "");
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "外籍人社保变更申报出错。";
		}
		return message;
	}

	// 社保生育津贴修改状态
	public String[] ChangeMADeclareUpState(EmShebaoChangeMAModel m) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int i = dal.declareChangeMAState(m);
		if (i == 0) {
			message[0] = "1";
			message[1] = "社保生育津贴申请操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴申请新增";
		} else {
			message[0] = "0";
			message[1] = "社保生育津贴申请操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴申请操作";
		}
		return message;
	}

	// 社保生育津贴决定书字段
	public String[] ChangeMAUploadDef(EmShebaoChangeMAModel m) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int i = dal.ChangeMAUploadDef(m);
		if (i == 1) {
			message[0] = "1";
			message[1] = "社保生育津贴决定书上传操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴决定书上传";
		} else {
			message[0] = "0";
			message[1] = "社保生育津贴决定书上传操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴决定书上传操作";
		}
		return message;
	}

	// 社保设置
	public int Setup(EmShebaoSetupModel m) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		return dal.Setup(m);
	}

	// 社保申报分配
	public int Assignment(Map<String, String> map) {
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int success = 0;
		try {
			Set<?> set = map.entrySet();
			Iterator<?> it = set.iterator();
			while (it.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<String, String> entry = (Entry<String, String>) it
						.next();
				success = success
						+ dal.Assignment(entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	// 社保生育津贴确认收款
	public String[] ChangeMAConfirmFee(EmShebaoChangeMAModel m) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int i = dal.ChangeMAConfirmFee(m);
		if (i == 1) {
			message[0] = "1";
			message[1] = "社保生育津贴确认收款操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴确认收款";
		} else {
			message[0] = "0";
			message[1] = "社保生育津贴确认收款操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴确认收款操作";
		}
		return message;
	}

	// 社保生育津贴完结
	public String[] ChangeMAFinish(EmShebaoChangeMAModel m) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int i = dal.ChangeMAFinish(m);
		if (i == 1) {
			message[0] = "1";
			message[1] = "社保生育津贴完结操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴完结";
		} else {
			message[0] = "0";
			message[1] = "社保生育津贴完结操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴完结操作";
		}
		return message;
	}
}
