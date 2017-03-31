package bll.EmSheBao;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeMAModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoUpdateModel;
import Model.SocialInsuranceClassInfoViewModel;
import Util.DateStringChange;
import Util.SocialInsuranceCalculator;
import Util.SocialInsuranceCalculator.calculationMethod;
import Util.SocialInsuranceCalculator.item;
import Util.UserInfo;
import dal.EmSheBao.Emsc_DeclareOperateDal;
import dal.EmSheBao.Emsi_OperateDal;
import dal.EmSheBao.Emsi_SelDal;
import dal.SocialInsurance.Algorithm_RegisteredDataDal;

public class Emsi_OperateBll {
	private Emsi_OperateDal dal = new Emsi_OperateDal();

	// 社保新增
	public String[] newin(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			// if (m.getIfdeclare() == 0)
			sumSbItemFee(m);
			// 业务插入数据库
			int changeid = dal.newin(m, 0);
			if (changeid > 0) {
				message = msiStartTask(m, "社保新增", changeid, 33);
			} else if (changeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的社保信息，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "社保新增，失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保新增，出错！";
		}
		return message;
	}

	// 编辑社保新增
	public String[] editNewin(EmShebaoUpdateModel m, int changeId, int tapr_id) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			// if (m.getIfdeclare() == 0)
			sumSbItemFee(m);
			if (tapr_id == 0 || m.getIfdeclare() == 4) {
				// 业务插入数据库
				int changeid = dal.newin(m, changeId);
				if (changeid > 0) {
					message = msiStartTask(m, "社保新增", changeid, 33);
				} else if (changeid == -1) {
					message[0] = "0";
					message[1] = "已有该员工的社保新增的变更信息，无法提交！";
				} else {
					message[0] = "0";
					message[1] = "社保新增，失败！";
				}
			} else {
				message = msiTaskToNext(m, "社保新增", changeId, tapr_id);
				message[3] = String.valueOf(changeId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "社保新增，出错！";
		}
		return message;
	}

	// 社保调入
	public String[] movein(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			// if (m.getIfdeclare() == 0)
			sumSbItemFee(m);
			// 业务插入数据库
			int changeid = dal.movein(m, 0);
			if (changeid > 0) {
				message = msiStartTask(m, "社保调入", changeid, 33);
			} else if (changeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的社保信息，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "社保调入失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保调入出错！";
		}
		return message;
	}

	// 编辑社保调入
	public String[] editMovein(EmShebaoUpdateModel m, int changeId, int tapr_id) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			sumSbItemFee(m);
			if (tapr_id == 0 || m.getIfdeclare() == 4) {
				// 业务插入数据库
				int changeid = dal.movein(m, changeId);
				if (changeid > 0) {

					message = msiStartTask(m, "社保调入", changeid, 33);

				} else if (changeid == -1) {
					message[0] = "0";
					message[1] = "已有该员工的社保调入的变更信息，无法提交！";
				} else {
					message[0] = "0";
					message[1] = "社保调入失败！";
				}
			} else {
				message = msiTaskToNext(m, "社保调入", changeId, tapr_id);
				message[3] = String.valueOf(changeId);
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保调入出错！";
		}
		return message;
	}

	// 社保调回
	public String[] moveback(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			sumSbItemFee(m);
			// 业务插入数据库
			int changeid = dal.moveback(m);
			if (changeid > 0) {
				message = msiStartTask(m, "社保调回", changeid, 38);
			} else if (changeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的社保信息，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "社保调回失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保调回出错！";
		}
		return message;
	}

	// 外籍人社保新增
	public String[] newinForeigner(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			sumSbItemFee(m);
			// 业务插入数据库
			int changeid = dal.newinForeigner(m);
			if (changeid > 0) {
				message = msiForeignerStartTask(m, "外籍人新参保", changeid, 39);
				message[3] = String.valueOf(changeid);
			} else if (changeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的社保信息，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "外籍人社保新增失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "外籍人社保新增出错！";
		}
		return message;
	}

	// 外籍人社保调入
	public String[] moveinForeigner(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			sumSbItemFee(m);
			// 业务插入数据库
			int changeid = dal.moveinForeigner(m);
			if (changeid > 0) {
				message = msiForeignerStartTask(m, "外籍人社保调入", changeid, 41);
				message[3] = String.valueOf(changeid);
			} else if (changeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的社保信息，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "外籍人社保调入失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "外籍人社保调入出错！";
		}
		return message;
	}

	// 外籍人社保调回
	public String[] movebackForeigner(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			sumSbItemFee(m);
			// 业务插入数据库
			int changeid = dal.movebackForeigner(m);
			if (changeid > 0) {
				message = msiForeignerStartTask(m, "外籍人社保调回", changeid, 42);
				message[3] = String.valueOf(changeid);
			} else if (changeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的社保信息，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "外籍人社保调回失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "外籍人社保调回出错！";
		}
		return message;
	}

	// 社保独立户接管
	public String[] takeOver(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			sumSbItemFee(m);
			// 业务插入数据库
			int changeid = dal.takeOver(m);
			if (changeid > 0) {
				message[0] = "1";
			} else if (changeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的社保变更信息，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "社保新增，失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保新增，出错！";
		}
		return message;
	}

	// 修改工资提交
	public String[] upSalary(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			if (m.getIfdeclare() == 0)
				sumSbItemFee(m);
			// 业务插入数据库
			int newChangeid = dal.upSalary(m, 0);
			if (newChangeid > 0) {
				message = msiStartTask(m, "修改工资", newChangeid, 44);
				message[3] = String.valueOf(newChangeid);
			} else if (newChangeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的修改工资变更，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "修改工资失败！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "修改工资出错！";
		}
		return message;
	}

	// 修改工资编辑
	public String[] editUpSalary(EmShebaoUpdateModel m, int changeId,
			int tapr_id) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			if (m.getIfdeclare() == 0)
				sumSbItemFee(m);
			if (tapr_id == 0 || m.getIfdeclare() == 4) {
				// 业务插入数据库
				int newChangeid = dal.upSalary(m, changeId);
				if (newChangeid > 0) {
					message = msiStartTask(m, "修改工资", newChangeid, 44);
					message[3] = String.valueOf(newChangeid);
				} else if (newChangeid == -1) {
					message[0] = "0";
					message[1] = "已有该员工的修改工资变更，无法提交！";
				} else {
					message[0] = "0";
					message[1] = "修改工资失败！";
				}
			} else {
				message = msiTaskToNext(m, "修改工资", changeId, tapr_id);
				message[3] = String.valueOf(changeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "修改工资出错！";
		}
		return message;
	}

	// 档案修改
	public String[] upFile(EmShebaoUpdateModel m, String con, int changeId) {
		String[] message = new String[5];
		try {
			// 计算社保各项目费用
			if (m.getIfdeclare() == 0)
				sumSbItemFee(m);
			// 业务插入数据库
			int newChangeid = dal.upFile(m, con, changeId);
			if (newChangeid > 0) {
				message = msiStartTask(m, "档案修改", newChangeid, 48);
				message[3] = String.valueOf(newChangeid);
			} else if (newChangeid == -1) {
				message[0] = "0";
				message[1] = "已有该员工的档案修改变更，无法提交！";
			} else {
				message[0] = "0";
				message[1] = "档案修改失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "档案修改出错！";
		}
		return message;
	}

	// 社保停交
	public String[] stop(EmShebaoUpdateModel m) {
		String[] message = new String[5];
		try {
			// 业务插入数据库
			int changeid = dal.stop(m, 0);
			if (changeid > 0) {
				message = msiStartTask(m, "社保停交", changeid, 45);
				message[3] = String.valueOf(changeid);
			} else if (changeid == -1) {
				message[0] = "0";
				message[1] = "该员工已有停交的申报，不可重复提交！";
			} else {
				message[0] = "0";
				message[1] = "社保停交失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保停交出错！";
		}
		return message;
	}

	// 社保停交编辑
	public String[] editStop(EmShebaoUpdateModel m, int changeId, int tapr_id) {
		String[] message = new String[5];
		try {
			if (tapr_id == 0 || m.getIfdeclare() == 4) {
				// 业务插入数据库
				int changeid = dal.stop(m, changeId);
				if (changeid > 0) {
					message = msiStartTask(m, "社保停交", changeid, 45);
					message[3] = String.valueOf(changeid);
				} else if (changeid == -1) {
					message[0] = "0";
					message[1] = "该员工已有停交的申报，不可重复提交！";
				} else {
					message[0] = "0";
					message[1] = "社保停交失败！";
				}
			} else {
				message = msiTaskToNext(m, "社保停交", changeId, tapr_id);
				message[3] = String.valueOf(changeId);
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保停交出错！";
		}
		return message;
	}

	// 特殊变更
	public String[] changeSZSI(EmShebaoChangeSZSIModel m) {
		String[] message = new String[5];
		try {
			// 启用任务单
			int single = dal.getShebaoSingle(m.getGid());
			int wfbu_id;
			if (single == 1) {
				wfbu_id = 117;
			} else {
				wfbu_id = 50;
			}
			WfBusinessService impl = new Emsi_ChangeSZSIImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { 0, m };
			message = wf.AddTaskToNext(
					obj,
					m.getOwnmonth() + "社保变更",
					"(" + m.getGid() + ")" + dal.getEmbaseName(m.getGid())
							+ m.getEscs_change(), wfbu_id, m.getEscs_addname(),
					m.getEscs_name() + "特殊变更", 0, "");

			// 如果是户籍变更，则首次提交跳过前面的步骤，直接到福利网上申报
			if ("变更户籍".equals(m.getEscs_change())) {
				m.setEscs_id(Integer.parseInt(message[3]));
				m.setEmsc_tapr_id(Integer.valueOf(message[2]));

				if (single == 1) {
					// 跳过3步，调到指定步骤，第6步
					Object[] obj1 = { 4, m };
					String[] str1 = wf.SkipToN(obj1,
							Integer.valueOf(message[2]), 6, "系统", "",
							m.getCid(), "");
				} else {
					// 跳过2步，调到指定步骤，第4步
					Object[] obj1 = { 4, m };
					String[] str1 = wf.SkipToN(obj1,
							Integer.valueOf(message[2]), 4, "系统", "",
							m.getCid(), "");
					/*
					 * String[] str1 = wf.SkipToNext(obj1,
					 * Integer.valueOf(message[2]), UserInfo.getUsername(), "",
					 * m.getCid(), "");
					 */
				}
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错！";
		}
		return message;
	}

	// 特殊变更重新发送
	public String[] changeSZSIResend(EmShebaoChangeSZSIModel m) {
		String[] message = new String[5];
		try {
			int single = dal.getShebaoSingle(m.getGid());
			if (single == 1) {
				m.setEscs_ifdeclare(0);
			} else {
				m.setEscs_ifdeclare(7);
			}

			// 启用任务单
			WfBusinessService impl = new Emsi_ChangeSZSIImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { 3, m };
			message = wf.PassToNext(obj, m.getEmsc_tapr_id(),
					UserInfo.getUsername(), "特殊变更重新发送", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错！";
		}
		return message;
	}

	// 特殊变更材料收集
	public String[] changeSZSIColData(EmShebaoChangeSZSIModel m) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new Emsi_ChangeSZSIImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { 1, m };
			message = wf.PassToNext(obj, m.getEmsc_tapr_id(),
					UserInfo.getUsername(), "特殊变更材料收集", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错！";
		}
		return message;
	}

	// 新增补缴
	public String[] addbj(EmShebaoBJModel m) {
		String[] message = new String[5];
		try {
			// 计算养老保险费用
			BigDecimal[] yl = sumYlFee(m.getEmsb_radix(), m.getOwnmonth(),
					m.getSoin_title());
			if (yl.length > 0) {
				m.setEmsb_totalcp(yl[0]);
				m.setEmsb_totalop(yl[1]);
				int single = dal.getShebaoSingle(m.getGid());
				boolean ifUkey = dal.getShebaoUkey(m.getGid());
				int diffownmonth = DateStringChange.diffOwnmonth(
						Integer.parseInt(DateStringChange.getOwnmonth()),
						m.getOwnmonth());
				int wfbu_id;

				if (m.isChk_jlbj() == false) {
					if ((single != 1 && diffownmonth <= 3)
							|| (diffownmonth <= 3 && ifUkey)) {
						if (m.getEmsb_ifdeclare() == 0)
							m.setEmsb_ifdeclare(8);
						wfbu_id = 51;
					} else {
						wfbu_id = 118;
					}
				} else {
					if (single != 1 && diffownmonth <= 3) {
						if (m.getEmsb_ifdeclare() == 0)
							m.setEmsb_ifdeclare(7);
						wfbu_id = 128;
					} else {
						wfbu_id = 118;
					}
				}

				// 业务处理数据插入数据库
				int changeid = dal.addbj(m);
				if (m.getEmsb_ifdeclare() == 4 && changeid > 0) {
					message[0] = "1";
					message[1] = "新增社保补缴成功!";
					message[3] = String.valueOf(changeid);
				} else if (changeid > 0) {
					message = msiBjStartTask(m, changeid, wfbu_id);
					message[3] = String.valueOf(changeid);
				} else if (changeid == -1) {
					message[0] = "0";
					message[1] = "当前员工已有该月的补缴数据，不可重复提交！";
				} else {
					message[0] = "0";
					message[1] = "新增社保补缴失败！";
				}
			} else {
				message[0] = "0";
				message[1] = "新增社保补缴失败,养老金计算有误！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增社保补缴出错！";
		}
		return message;
	}

	// 新增补缴(医疗)
	public String[] addJLbj(EmShebaoBJModel m) {
		String[] message = new String[5];
		try {
			// 计算养老保险费用
			BigDecimal[] jl = sumJlFee(m.getEmsb_radix(), m.getOwnmonth(),
					m.getSoin_title());
			if (jl.length > 0) {
				m.setEmsb_totalcp(jl[0]);
				m.setEmsb_totalop(jl[1]);

				int wfbu_id;
				int single = dal.getShebaoSingle(m.getGid());
				int diffownmonth = DateStringChange.diffOwnmonth(
						Integer.parseInt(DateStringChange.getOwnmonth()),
						m.getOwnmonth());
				if (single != 1 && diffownmonth <= 3) {
					if (m.getEmsb_ifdeclare() == 0)
						m.setEmsb_ifdeclare(7);
					wfbu_id = 127;
				} else {
					wfbu_id = 126;
				}

				// 业务处理数据插入数据库
				int changeid = dal.addJLbj(m);
				if (m.getEmsb_ifdeclare() == 4 && changeid > 0) {
					message[0] = "1";
					message[1] = "新增社保补缴成功!";
					message[3] = String.valueOf(changeid);
				} else if (changeid > 0) {
					message = msiJLBjStartTask(m, changeid, wfbu_id);
					message[3] = String.valueOf(changeid);
				} else if (changeid == -1) {
					message[0] = "0";
					message[1] = "当前员工已有该月的补缴数据，不可重复提交！";
				} else {
					message[0] = "0";
					message[1] = "新增社保补缴失败！";
				}
			} else {
				message[0] = "0";
				message[1] = "新增社保补缴失败,养老金计算有误！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增社保补缴出错！";
		}
		return message;
	}

	// 社保变更确认
	public String[] ChangeDeclare(List<EmSheBaoChangeModel> list,
			String username) {
		String[] message = new String[5];
		int sum = list.size();
		int success = 0;
		try {
			for (EmSheBaoChangeModel m : list) {
				// 社保变更确认启用任务单
				message = ChangeDeclareStartTask(m, username);
				if ("1".equals(message[0])) {
					success++;
				}
			}
			// 判断数据成功数
			if (sum == success) {
				message[0] = "1";
				message[1] = "确认社保变更成功!";
			} else {
				message[0] = "0";
				message[1] = "共确认社保变更数据：" + sum + "条，其中有：" + (sum - success)
						+ "条数据失败。";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "确认社保变更出错！";
		}
		return message;
	}

	// 社保补缴撤回
	public String[] revokeBj(int id, int tapr_id, String username) {
		String[] message = new String[5];
		try {
			WfOperateService wf = new WfOperateImpl(new Emsc_editBjImpl());
			Object[] obj = { "1", id, username };
			message = wf.RevokeToN(obj, tapr_id, 1, username);
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "撤回社保补缴，出错！";
		}
		return message;
	}

	// 社保变更撤回
	public String[] revokeChange(int id, int type, int tapr_id,
			String username, String emsc_change, int gid) {
		String[] message = new String[5];
		try {
			WfOperateService wf = null;
			String opTable = "";
			switch (type) {
			case 1:
				wf = new WfOperateImpl(new Emsc_editChangeImpl());
				opTable = "EmShebaochange";
				break;
			case 2:
				wf = new WfOperateImpl(new Emsc_editChangeSZSIImpl());
				opTable = "EmShebaoChangeSZSI";
				break;
			case 3:
				wf = new WfOperateImpl(new Emsc_editChangeForeignerImpl());
				opTable = "EmShebaoChangeForeigner";
				break;
			}
			Object[] obj = { "1", id, type, username, opTable };
			message = wf.RevokeToN(obj, tapr_id, 1, username);
			if ("1".equals(message[0])
					&& ("修改工资".equals(emsc_change) || "档案修改"
							.equals(emsc_change))) {
				sumShebaoUpdate(gid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "撤回社保变更出错！";
		}
		return message;
	}

	// 撤回社保变更任务单退回操作
	public String[] revokeChangeReturnOp(int id, int type, String username,
			String opTable) {
		String[] message = new String[5];
		try {
			int i = dal.revokeChange(id, type, username);
			if (i == 1) {
				message[0] = "1";
				message[1] = "撤回社保变更，操作成功。";
				message[2] = String.valueOf(id);
				message[3] = opTable;
				message[4] = "撤回社保变更。";
			} else {
				message[0] = "0";
				message[1] = "撤回社保变更，操作失败。";
				message[2] = String.valueOf(id);
				message[3] = opTable;
				message[4] = "撤回社保变更。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "撤回社保变更出错！";
		}
		return message;
	}

	// 重新计算并更新社保在册表
	public int sumShebaoUpdate(int gid) {
		int i = 0;
		try {
			Emsi_SelBll bll = new Emsi_SelBll();
			EmShebaoUpdateModel m = bll.getShebaoUpdateByGid(gid);
			// 计算社保各项目费用
			sumSbItemFee(m);
			// 更新数据库
			Algorithm_RegisteredDataDal updal = new Algorithm_RegisteredDataDal();
			updal.upLocalRegData(m);
			i = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 社保变更审核
	public String[] aduitPass(EmSheBaoChangeModel m, String username) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new Emsi_AduitPassImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { m.getId() };
			message = wf.PassToNext(obj, m.getEmsc_tapr_id(), username, "审核通过",
					0, null);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保审核出错！";
		}
		return message;

	}

	// 社保补缴删除
	public String[] DelBj(int id, int tapr_id, String username) {
		String[] message = new String[5];
		try {
			WfOperateService wf = new WfOperateImpl(new Emsc_editBjImpl());
			Object[] obj = { "1", id, username };
			message = wf.StopTask(obj, tapr_id, "系统", username + "删除社保补缴");
			// System.out.println(message[1]);
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除社保补缴，出错！";
		}
		return message;
	}

	// 社保补缴删除(医疗)
	public String[] DelBjJL(int id, int tapr_id, String username) {
		String[] message = new String[5];
		try {
			WfOperateService wf = new WfOperateImpl(new Emsc_editBjJLImpl());
			Object[] obj = { "1", id, username };
			message = wf.StopTask(obj, tapr_id, "系统", username + "删除社保补缴");
			// System.out.println(message[1]);
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除社保补缴，出错！";
		}
		return message;
	}

	// 删除补交(无任务单)
	public String[] DelBj(int id) {
		String[] message = new String[5];
		try {
			Emsi_OperateDal dal = new Emsi_OperateDal();
			int i = dal.DelBj(id);
			if (i == 1) {
				message[0] = "1";
				message[1] = "删除社保补缴，操作成功。";
				message[2] = String.valueOf(id);
				message[3] = "EmShebaoBJ";
				message[4] = "删除社保补缴。";
			} else {
				message[0] = "0";
				message[1] = "删除社保变更，操作失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除社保补缴，出错！";
		}
		return message;
	}

	// 删除医疗补交(无任务单)
	public String[] DelBjJL(int id) {
		String[] message = new String[5];
		try {
			Emsi_OperateDal dal = new Emsi_OperateDal();
			int i = dal.DelBjJL(id);
			if (i == 1) {
				message[0] = "1";
				message[1] = "删除社保医疗补缴，操作成功。";
				message[2] = String.valueOf(id);
				message[3] = "EmShebaoBJ";
				message[4] = "删除社保医疗补缴。";
			} else {
				message[0] = "0";
				message[1] = "删除社保医疗补交，操作失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除社保补缴，出错！";
		}
		return message;
	}

	// 社保变更删除
	public String[] DelChange(int id, int type, int tapr_id, String username) {
		String[] message = new String[5];
		try {
			if (tapr_id == 0) {
				int i = dal.DelChange(id, type, username);
				if (i == 1) {
					message[0] = "1";
					message[1] = "删除社保变更,成功。";
				} else {
					message[0] = "0";
					message[1] = "删除社保变更,失败。";
				}
			} else {
				WfOperateService wf = null;
				String opTable = "";
				switch (type) {
				case 1:
					wf = new WfOperateImpl(new Emsc_editChangeImpl());
					opTable = "EmShebaochange";
					break;
				case 2:
					wf = new WfOperateImpl(new Emsc_editChangeSZSIImpl());
					opTable = "EmShebaoChangeSZSI";
					break;
				case 3:
					wf = new WfOperateImpl(new Emsc_editChangeForeignerImpl());
					opTable = "EmShebaoChangeForeigner";
					break;
				}
				Object[] obj = { "1", id, type, username, opTable };
				message = wf.StopTask(obj, tapr_id, "系统", username
						+ "删除社保变更，终止任务单。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除社保变更,出错！";
		}
		return message;
	}

	// 社保变更删除任务单终止操作
	public String[] DelChangeStopTaskOp(int id, int type, String username,
			String opTable) {
		String[] message = new String[5];
		try {
			int i = dal.DelChange(id, type, username);
			if (i == 1) {
				message[0] = "1";
				message[1] = "删除社保变更，操作成功。";
				message[2] = String.valueOf(id);
				message[3] = opTable;
				message[4] = "删除社保变更。";
			} else {
				message[0] = "0";
				message[1] = "删除社保变更，操作失败。";
				message[2] = String.valueOf(id);
				message[3] = opTable;
				message[4] = "删除社保变更。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除社保变更出错！";
		}
		return message;
	}

	// 社保变更确认
	public String[] ChangDeclare(EmSheBaoChangeModel m, String username) {
		String[] message = new String[5];
		try {
			if (m.getChangetype() == 1) {
				message = ChangeDeclareStartTask(m, username);
			} else if (m.getChangetype() == 3) {
				message = msiForeignerStartTask(m, username);
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "确认变更出错！";
		}
		return message;
	}

	// 社保变更确认启用任务单
	public String[] ChangeDeclareStartTask(EmSheBaoChangeModel m,
			String username) {
		String[] message = new String[5];
		try {
			// 获取任务单业务ID
			int buid = getBusinessID(m.getEmsc_change());
			if (buid != 0) {
				// 业务处理数据插入数据库
				int i = dal.ChangeDeclare(m);
				if (i == 1) {
					// 启用任务单
					WfOperateService wf = new WfOperateImpl(
							new Emsi_OperateImpl());
					Object[] obj = { m.getId(), "社保变更确认" };

					message = wf
							.AddTaskToNext(
									obj,
									m.getOwnmonth() + "社保变更",
									dal.getEmbaseName(m.getGid()) + "社保"
											+ m.getEmsc_change(),
									buid,
									username,
									m.getEmsc_name() + "社保"
											+ m.getEmsc_change(), 0, "");
				} else {
					message[0] = "0";
					message[1] = "确认变更失败！";
				}
			} else {
				message[0] = "0";
				message[1] = "业务ID无法确认！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "确认变更出错！";
		}
		return message;
	}

	// 社保补缴确认启用任务单
	public String[] BjDeclareStartTask(EmShebaoBJModel m, String username) {
		String[] message = new String[5];
		try {
			// 启用任务单
			int single = dal.getShebaoSingle(m.getGid());
			boolean ifUkey = dal.getShebaoUkey(m.getGid());
			int diffownmonth = DateStringChange.diffOwnmonth(
					Integer.parseInt(DateStringChange.getOwnmonth()),
					m.getOwnmonth());
			boolean ifJLBJ = dal.getShebaoBJJL(m.getGid(), m.getOwnmonth(),
					m.getEmsb_startmonth());
			int wfbu_id;

			if (ifJLBJ == false) {// 判断是否有医疗补交
				if ((single != 1 && diffownmonth <= 3)
						|| (diffownmonth <= 3 && ifUkey)) {
					// if (m.getEmsb_ifdeclare() == 0)
					m.setEmsb_ifdeclare(8);
					wfbu_id = 51;
				} else {
					m.setEmsb_ifdeclare(0);
					wfbu_id = 118;
				}
			} else {
				if (single != 1 && diffownmonth <= 3) {
					// if (m.getEmsb_ifdeclare() == 0)
					m.setEmsb_ifdeclare(7);
					wfbu_id = 128;
				} else {
					m.setEmsb_ifdeclare(0);
					wfbu_id = 118;
				}
			}

			// 业务处理数据插入数据库
			int i = dal.BjDeclare(m, UserInfo.getUsername());
			if (i == 1) {
				WfOperateService wf = new WfOperateImpl(
						new Emsi_BjOperateImpl());
				Object[] obj = { m.getId(), "社保养老补缴确认" };
				message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保变更", "("
						+ m.getGid() + ")" + dal.getEmbaseName(m.getGid())
						+ "社保养老补缴", wfbu_id, username, m.getEmsb_name()
						+ "社保养老补缴", 0, "");
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "确认变更出错！";
		}
		return message;
	}

	// 社保补缴确认启用任务单(医疗)
	public String[] BjJLDeclareStartTask(EmShebaoBJModel m, String username) {
		String[] message = new String[5];
		try {
			// 启用任务单
			int wfbu_id;
			int diffownmonth = DateStringChange.diffOwnmonth(
					Integer.parseInt(DateStringChange.getOwnmonth()),
					m.getOwnmonth());
			if (m.getEmsb_single() != 1 && diffownmonth <= 3) {
				wfbu_id = 127;
			} else {
				wfbu_id = 126;
			}

			// 业务处理数据插入数据库
			int i = dal.BjJLDeclare(m, UserInfo.getUsername());
			if (i == 1) {
				WfOperateService wf = new WfOperateImpl(
						new Emsi_BjJLOperateImpl());
				Object[] obj = { m.getId(), "社保医疗补缴确认" };
				message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保变更", "("
						+ m.getGid() + ")" + dal.getEmbaseName(m.getGid())
						+ "社保医疗补缴", wfbu_id, username, m.getEmsb_name()
						+ "社保医疗补缴", 0, "");
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "确认变更出错！";
		}
		return message;
	}

	// 外籍人社保新增、调入、调回确认启用任务单
	private String[] msiForeignerStartTask(EmSheBaoChangeModel m,
			String username) {
		String[] message = new String[5];
		try {
			// 业务处理数据插入数据库
			int i = dal.ForeignerChangeDeclare(m);
			if (i == 1) {
				// 启用任务单
				WfBusinessService impl = new Emsi_ForeignerOpImpl();
				WfOperateService wf = new WfOperateImpl(impl);
				Object[] obj = { m.getId(), "外籍人社保" + m.getEmsc_change() };
				int buid = getForeignerBusinessID(m.getEmsc_change(),
						m.getEmsc_content());
				message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保变更", "("
						+ m.getGid() + ")" + m.getEmsc_name() + "外籍人新参保", buid,
						username, m.getEmsc_name() + "外籍人新参保", 0, "");
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "确认社保变更出错！";
		}
		return message;
	}

	// 获取任务单业务ID
	private int getBusinessID(String change) {
		int id = 0;
		switch (change) {
		case "新增":
			id = 32;
			break;
		case "调入":
			id = 33;
			break;
		case "停交":
			id = 45;
			break;
		case "修改工资":
			id = 44;
			break;
		case "档案修改":
			id = 48;
			break;
		}
		return id;
	}

	// 获取外籍人社保任务单业务ID
	private int getForeignerBusinessID(String change, String content) {
		int id = 0;
		switch (change) {
		case "新增":
			id = 39;
			break;
		case "调入":
			switch (content) {
			case "入职":
				id = 41;
				break;
			case "重新调回":
				id = 42;
				break;
			}
			break;
		}
		return id;
	}

	// 社保新增、调入、调回、修改工资、停交、档案修改启用任务单
	private String[] msiStartTask(EmShebaoUpdateModel m, String con,
			int changeid, int buid) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new Emsi_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { changeid, con };
			if (m.getIfdeclare() == 0) {
				message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保变更", "("
						+ m.getGid() + ")" + m.getEsiu_name() + con, buid,
						m.getEsiu_addname(), m.getEsiu_name() + con, 0, "");
			} else {
				message[0] = "1";
				message[1] = con + "成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = con + "出错！";
		}
		return message;
	}

	// 社保新增、调入、调回、修改工资、停交编辑任务单通过当前节点
	private String[] msiTaskToNext(EmShebaoUpdateModel m, String con,
			int changeid, int tapr_id) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new Emsc_editChangeImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { changeid, con, m };
			if (m.getIfdeclare() == 0) {
				message = wf.PassToNext(obj, tapr_id, UserInfo.getUsername(),
						m.getEsiu_remark(), 0, "");
			} else {
				message[0] = "1";
				message[1] = con + "成功";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = con + "出错！";
		}
		return message;
	}

	// 外籍人社保新增、调入、调回启用任务单
	private String[] msiForeignerStartTask(EmShebaoUpdateModel m, String con,
			int changeid, int buid) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new Emsi_ForeignerOpImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { changeid, con };
			if (m.getIfdeclare() == 7) {
				message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保变更", "("
						+ m.getGid() + ")" + m.getEsiu_name() + con, buid,
						m.getEsiu_addname(), m.getEsiu_name() + con, 0, "");
			} else {
				message[0] = "1";
				message[1] = con + "成功";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = con + "出错！";
		}
		return message;
	}

	// 社保补缴启用任务单
	private String[] msiBjStartTask(EmShebaoBJModel m, int changeid, int wfbu_id) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new Emsi_BjOperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { changeid, "新增社保补缴" };
			message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保变更",
					"(" + m.getGid() + ")" + dal.getEmbaseName(m.getGid())
							+ "社保补缴", wfbu_id, m.getEmsb_addname(),
					m.getEmsb_name() + "社保补缴", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增补缴出错";
		}
		return message;

	}

	// 社保补缴启用任务单(医疗)
	private String[] msiJLBjStartTask(EmShebaoBJModel m, int changeid,
			int wfbu_id) {
		String[] message = new String[5];
		try {
			// 启用任务单
			WfBusinessService impl = new Emsi_BjJLOperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { changeid, "新增社保医疗补缴" };
			message = wf.AddTaskToNext(obj, m.getOwnmonth() + "社保变更",
					"(" + m.getGid() + ")" + dal.getEmbaseName(m.getGid())
							+ "社保医疗补缴", wfbu_id, m.getEmsb_addname(),
					m.getEmsb_name() + "社保医疗补缴", 0, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "新增医疗补缴出错";
		}
		return message;

	}

	// 计算社保各项目费用
	public void sumSbItemFee(EmShebaoUpdateModel m) {
		SocialInsuranceCalculator si = new SocialInsuranceCalculator();
		try {
			// 设置算法计算项目
			if ("不参加".equals(m.getEsiu_yl())) {
				si.setYl(false);
			}
			if ("不参加".equals(m.getEsiu_gs())) {
				si.setGs(false);
			}
			if ("不参加".equals(m.getEsiu_sye())) {
				si.setSye(false);
			}
			if ("不参加".equals(m.getEsiu_syu())) {
				si.setSyu(false);
			}
			if ("不参加".equals(m.getEsiu_yltype())) {
				si.setJl(false);
			}
			// 设置执行时间
			Date date = DateStringChange.StringtoDate(m.getOwnmonth() + "01",
					"yyyyMMdd");
			// 获取算法ID
			int soin_id;
			if (!"".equals(m.getEmsf_soin_title())
					&& m.getEmsf_soin_title() != null) {
				soin_id = si.getSionId(m.getEmsf_soin_title(), "深圳",
						"深圳中智经济技术合作有限公司");
			} else {
				soin_id = si.getSionId("深户员工", "深圳", "深圳中智经济技术合作有限公司");
			}
			if (soin_id != 0) {
				// 获取工伤系数及失业下浮比例
				Emsi_SelDal selDal = new Emsi_SelDal();
				String[] proportion = selDal.getItemProportionByGid(m.getGid());
				if (proportion != null) {
					// 设置工伤公司缴交部分算法
					si.setCalculationMethod(item.gsCp,
							calculationMethod.replaceProprotion, proportion[0]);
					// 设置失业保险公司缴交部分算法
					si.setCalculationMethod(item.syeCp,
							calculationMethod.multiply, proportion[1]);
				}
				// 计算社保项目
				List<SocialInsuranceClassInfoViewModel> list = si.getSbItemFee(
						soin_id, new BigDecimal(m.getEsiu_radix()), date);
				setSbItemFee(m, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 社保项目赋值
	private void setSbItemFee(EmShebaoUpdateModel m,
			List<SocialInsuranceClassInfoViewModel> list) {
		try {
			for (SocialInsuranceClassInfoViewModel item : list) {
				switch (item.getSicl_name()) {
				case "养老保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_ylcp(item.getFee());
						break;
					case "个人":
						m.setEsiu_ylop(item.getFee());
						break;
					}
					break;
				case "医疗保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_jlcp(item.getFee());
						break;
					case "个人":
						m.setEsiu_jlop(item.getFee());
						break;
					}
					break;
				case "生育保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_syucp(item.getFee());
						break;
					case "个人":
						m.setEsiu_syuop(item.getFee());
						break;
					}
					break;
				case "工伤保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_gscp(item.getFee());
						break;
					case "个人":
						m.setEsiu_gsop(item.getFee());
						break;
					}
					break;
				case "失业保险":
					switch (item.getSicl_payunit()) {
					case "企业":
						m.setEsiu_syecp(item.getFee());
						break;
					case "个人":
						m.setEsiu_syeop(item.getFee());
						break;
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 计算养老保险缴交金额
	public BigDecimal[] sumYlFee(int sbbasic, int ownmonth, String soin_title) {
		SocialInsuranceCalculator si = new SocialInsuranceCalculator();
		// 设置执行时间
		Date date = DateStringChange.StringtoDate(ownmonth + "01", "yyyyMMdd");
		// 获取算法ID
		int soin_id = si.getSionId(soin_title, "深圳", "深圳中智经济技术合作有限公司");
		return si.getYlFee(soin_id, new BigDecimal(sbbasic), date);
	}

	// 计算医疗保险缴交金额
	public BigDecimal[] sumJlFee(int sbbasic, int ownmonth, String soin_title) {
		SocialInsuranceCalculator si = new SocialInsuranceCalculator();
		// 设置执行时间
		Date date = DateStringChange.StringtoDate(ownmonth + "01", "yyyyMMdd");
		// 获取算法ID
		int soin_id = si.getSionId(soin_title, "深圳", "深圳中智经济技术合作有限公司");
		return si.getJlFee(soin_id, new BigDecimal(sbbasic), date);
	}

	// 插入社保在册数据
	public boolean insertShebaoupdate(EmShebaoUpdateModel m) {
		return dal.insertShebaoupdate(m);
	}

	// 插入社保补缴数据（入职）
	public boolean insertShebaoBj(EmShebaoBJModel m) {
		return dal.insertShebaoBj(m);
	}

	// 插入社保医疗补缴数据（入职）
	public boolean insertShebaoBjJL(EmShebaoBJModel m) {
		return dal.insertShebaoBjJL(m);
	}

	// 删除在册数据
	public String[] delSheBaoUpdate(Integer gid) {
		String[] message = new String[5];
		try {
			int changeid = dal.delupdate(gid);
			if (changeid > 0) {
				message[0] = "1";
				message[1] = "删除社保在册数据，成功！";
			} else {
				message[0] = "0";
				message[1] = "删除社保在册数据，失败！";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "删除社保在册数据，出错！";
		}
		return message;
	}

	// 补缴申报修改状态
	public String[] BjDeclareUpState(EmShebaoBJModel m) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int i = dal.BjDeclareUpState(m, UserInfo.getUsername());
		if (i == 1) {
			message[0] = "1";
			message[1] = "社保补缴操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = m.getEmsb_ifdeclarestr();
		} else {
			message[0] = "0";
			message[1] = "社保补缴操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "社保补缴操作";
		}
		return message;
	}

	// 补缴申报修改状态(医疗)
	public String[] BjJLDeclareUpState(EmShebaoBJModel m) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int i = dal.BjJLDeclareUpState(m, UserInfo.getUsername());
		if (i == 1) {
			message[0] = "1";
			message[1] = "社保补缴操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = m.getEmsb_ifdeclarestr();
		} else {
			message[0] = "0";
			message[1] = "社保补缴操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "社保补缴操作";
		}
		return message;
	}

	// 新增生育津贴申请
	public String[] addEscmInfo(EmShebaoChangeMAModel m) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int i = dal.addEscmInfo(m);
		if (i > 0) {
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

	// 从新发送生育津贴申请
	public String[] reSendEscmInfo(EmShebaoChangeMAModel m) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		int i = dal.reSendEscmInfo(m);
		if (i > 0) {
			message[0] = "1";
			message[1] = "社保生育津贴重新发送操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴申请重新发送";
		} else {
			message[0] = "0";
			message[1] = "社保生育津贴重新发送操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeMA";
			message[4] = "社保生育津贴申请操作";
		}
		return message;
	}
}
