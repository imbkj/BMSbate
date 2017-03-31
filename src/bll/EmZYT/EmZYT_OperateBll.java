/*
 * 创建人：林少斌
 * 创建时间：2014-3-10
 * 用途：智翼通接口数据处理Bll
 */
package bll.EmZYT;

import bll.EmSheBao.Emsi_OperateBll;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.EmShebaoUpdateModel;
import Model.EmZYTFeedbackFileModel;
import Model.EmZYTFeedbackModel;
import Model.EmZYTModel;
import dal.EmZYT.EmZYT_OperateDal;

public class EmZYT_OperateBll {
	EmZYT_OperateDal dal = new EmZYT_OperateDal();

	// 导入委托单数据 sql语句直接获取excel数据插入
	public String[] uploadZYT(String sheetname, String filename,
			String ownmonth, String username, String fileAllname) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.uploadZYT(sheetname, filename, ownmonth, username,
					fileAllname);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通委托单上传成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通委托单上传";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通委托单上传失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通委托单上传，操作出错。";
		}
		return message;
	}

	// 导入委托单数据 java读取excel单条数据循环插入
	public String[] uploadZYTbyModel(EmZYTModel m) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.uploadZYTbyModel(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通委托单上传成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通委托单上传";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通委托单上传失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通委托单上传，操作出错。";
		}
		return message;
	}

	// 导入委托单数据后更新公司编号和员工编号
	public String[] updateUploadFile(EmZYTModel m) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.updateUploadFile(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通委托单上传成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通委托单上传";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通委托单上传失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通委托单上传，操作出错。";
		}
		return message;
	}

	// 导入委托单数据后更新补交费用字段
	public String[] updateUploadFileBJ(EmZYTModel m) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.updateUploadFileBJ(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通委托单上传成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通委托单上传";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通委托单上传失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通委托单上传，操作出错。";
		}
		return message;
	}

	// 导入委托单反馈数据 java读取excel单条数据循环插入
	public String[] uploadFeedbackByModel(EmZYTFeedbackModel m) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.uploadFeedbackByModel(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通委托单反馈文件上传成功!";
				message[2] = "";
				message[3] = "EmZYTFeedback";
				message[4] = "智翼通委托单反馈文件上传";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通委托单反馈文件上传失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通委托单反馈文件上传，操作出错。";
		}
		return message;
	}

	// 导入委托单反馈数据
	public String[] uploadFeedbackExcel(String sheetname, String filename,
			String username, String fileAllname) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.uploadFeedbackExcel(sheetname, filename, username,
					fileAllname);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通反馈文件上传成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通反馈文件上传";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通反馈文件上传失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通反馈文件上传，操作出错。";
		}
		return message;
	}

	// 更新反馈数据
	public String[] upFeedbackData(String str) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.upFeedbackData(str);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通反馈数据更新成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通反馈数据更新";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通反馈数据更新失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通反馈数据更新，操作出错。";
		}
		return message;
	}

	// 生成反馈文件后更新反馈数据
	public String[] upFeedbackDataADF(String str, String username,
			String filename) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.upFeedbackDataADF(str, username, filename);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通反馈数据更新成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通反馈数据更新";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通反馈数据更新失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通反馈数据更新，操作出错。";
		}
		return message;
	}

	// 更新反馈数据为已反馈
	public String[] upFBStateSuccess(String str, String username) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.upFBStateSuccess(str, username);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作智翼通反馈数据更新成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通反馈数据更新";
			} else {
				message[0] = "0";
				message[1] = "操作智翼通反馈数据更新失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通反馈数据更新，操作出错。";
		}
		return message;
	}

	// 获取委托单数据的任务单业务ID
	private int getEmZYTBusinessID(String emztClass) {
		int id = 0;
		switch (emztClass) {
		case "新增":
			id = 68;
			break;
		case "调整":
			id = 69;
			break;
		case "终止":
			id = 70;
			break;
		case "年度调整":
			id = 71;
			break;
		case "服务费调整":
			id = 72;
			break;
		case "一次性费用":
			id = 73;
			break;
		}
		return id;
	}

	// 新增委托单的任务单数据
	public String[] msiEmZYTStartTask(EmZYTModel m) {
		String[] message = new String[5];
		int wfbu_id = getEmZYTBusinessID(m.getEmzt_class()); // 业务流程id
		String con = "智翼通接口启用任务单";
		String wf_con = "数据id：" + String.valueOf(m.getId()) + con;
		try {
			// 启用任务单
			WfBusinessService impl = new EmZYT_OperateImpl();
			WfOperateService wf = new WfOperateImpl(impl);
			Object[] obj = { "0", m.getId(), con };

			message = wf.AddTaskToNext(obj, con, m.getEmzt_name() + con,
					wfbu_id, m.getEmzt_addname(), wf_con, 0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = con + "出错！";
		}
		return message;
	}

	// 新增数据选报价单
	public String[] declareEmZYTAddCoGList(EmZYTModel m) {
		String[] message = new String[5];
		// 调用报价单新增方法

		// 调用
		message = declareEmZYTUpdate(m);

		return message;
	}

	// 终止任务单数据(业务操作退单)
	public String[] declareEmZYTStop(EmZYTModel m) {
		String[] message = new String[5];
		// 调用状态变更方法
		message = declareEmZYTUpdate(m);

		// 取消任务单操作
		/*
		 * try { // 启用任务单 WfBusinessService impl = new EmZYT_OperateImpl();
		 * WfOperateService wf = new WfOperateImpl(impl); Object[] obj = { "3",
		 * m.getId(), m };
		 * 
		 * message = wf.StopTask(obj, m.getEmzt_tapr_id(),
		 * m.getEmzt_declarename(), "");
		 * 
		 * } catch (Exception e) { message[0] = "2"; message[1] = "智翼通接口操作出错！";
		 * }
		 */
		return message;
	}

	// 智翼通比对页面数据状态更新(业务操作退单)
	public String[] upEmZYTChkState(EmZYTModel m) {
		String[] message = new String[5];
		try {
			// 调用状态变更方法
			int result = 0;
			result = dal.upEmZYTChkState(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新核对状态成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新核对状态成功!";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新核对状态失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新核对状态出错！";
		}
		return message;

	}

	// 任务单返回第一步(业务操作退单(待跟踪))
	public String[] declareEmZYTReturn(EmZYTModel m) {
		String[] message = new String[5];
		// 调用状态变更方法
		message = declareEmZYTUpdate(m);

		// 取消任务单操作
		/*
		 * try { // 启用任务单 WfBusinessService impl = new EmZYT_OperateImpl();
		 * WfOperateService wf = new WfOperateImpl(impl); Object[] obj = { "2",
		 * m.getId(), m };
		 * 
		 * message = wf.ReturnToN(obj, m.getEmzt_tapr_id(), 1,
		 * m.getEmzt_declarename()); } catch (Exception e) { message[0] = "2";
		 * message[1] = "智翼通接口操作出错！"; }
		 */
		return message;
	}

	// 业务数据状态更新并且任务单更新到下一步
	public String[] declareEmZYTUpdate(EmZYTModel m) {
		String[] message = new String[5];
		int result = 0;
		result = dal.upEmZYTState(m);
		try {
			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口操作成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口操作成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口操作出错！";
		}

		// 取消任务单操作
		/*
		 * try { // 启用任务单 WfBusinessService impl = new EmZYT_OperateImpl();
		 * WfOperateService wf = new WfOperateImpl(impl); Object[] obj = { "1",
		 * m.getId(), m };
		 * 
		 * message = wf.PassToNext(obj, m.getEmzt_tapr_id(),
		 * m.getEmzt_declarename(), "", 0, "");
		 * 
		 * } catch (Exception e) { message[0] = "2"; message[1] = "智翼通接口操作出错！";
		 * }
		 */
		return message;
	}

	// 业务数据状态更新为“入职网”
	public String[] declareEmZYTUpdateRZW(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;
			int id = m.getId();
			result = dal.upEmZYTState(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新数据状态成功!";
				message[2] = String.valueOf(id);
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新数据状态成功!";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新数据状态失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新数据状态出错！";
		}
		return message;
	}

	// 补充编号
	public String[] upEmZYTCidGid(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;
			int id = m.getId();
			result = dal.upEmZYTCidGid(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口补充编号成功!";
				message[2] = String.valueOf(id);
				message[3] = "EmZYT";
				message[4] = "智翼通接口补充编号成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口补充编号失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口补充编号出错！";
		}
		return message;
	}

	// 确认调整单
	public String[] confirmAdjust(EmZYTModel m) {
		String[] message = new String[5];
		int result = 0;
		try {
			// 调用状态变更方法
			result = dal.upAdjust(m);
			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新数据状态成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新数据状态成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新数据状态失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新数据状态出错！";
		}
		// 取消任务单操作
		/*
		 * try { // 启用任务单 WfBusinessService impl = new EmZYT_OperateImpl();
		 * WfOperateService wf = new WfOperateImpl(impl); Object[] obj = { "4",
		 * m.getId(), m };
		 * 
		 * message = wf.PassToNext(obj, m.getEmzt_tapr_id(),
		 * m.getEmzt_declarename(), "", 0, "");
		 * 
		 * } catch (Exception e) { message[0] = "2"; message[1] = "智翼通接口操作出错！";
		 * }
		 */

		return message;
	}

	// 确认非调整单，非新增
	public String[] notConfirmAdjust(EmZYTModel m) {
		String[] message = new String[5];
		// 调用状态变更方法
		message = declareEmZYTUpdate(m);

		// 取消任务单操作
		/*
		 * try { // 启用任务单 WfBusinessService impl = new EmZYT_OperateImpl();
		 * WfOperateService wf = new WfOperateImpl(impl); Object[] obj = { "5",
		 * m.getId(), m };
		 * 
		 * message = wf.PassToNext(obj, m.getEmzt_tapr_id(),
		 * m.getEmzt_declarename(), "", 0, "");
		 * 
		 * } catch (Exception e) { message[0] = "2"; message[1] = "智翼通接口操作出错！";
		 * }
		 */

		return message;
	}

	// 选择报价单
	public String[] addEmZYTCoGlist(int emzt_id, String intime,
			String coliIdList, String contactType, String addname) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.addEmZYTCoGlist(emzt_id, intime, coliIdList,
					contactType, addname);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口选择报价单成功!";
				message[2] = String.valueOf(emzt_id);
				message[3] = "EmZYT";
				message[4] = "智翼通接口选择报价单成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口选择报价单失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口选择报价单出错！";
		}
		return message;
	}

	// 更新联系内容
	public String[] upEmZYTContact(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.upEmZYTContact(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新联系信息成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新联系信息成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新联系信息失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新联系信息出错！";
		}
		return message;
	}

	// 入职更新gid字段
	public String[] upEmZYTGid(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.upEmZYTGid(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新数据成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新数据成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新数据失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新数据状态出错！";
		}
		return message;
	}

	// 入职更新gid字段
	public String[] upEmZYTGid(int emba_id) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.upEmZYTGid(emba_id);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新数据成功!";
				message[2] = String.valueOf(emba_id);
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新数据成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新数据失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新数据状态出错！";
		}
		return message;
	}

	// 更新数据状态
	public String[] upEmZYTembaId(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.upEmZYTembaId(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新数据emba_id成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新数据emba_id成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新数据emba_id失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新数据emba_id出错！";
		}
		return message;
	}

	// 更新反馈数据实际日期
	public String[] upEmZYTFeedback(Integer ownmonth) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.upEmZYTFeedback(ownmonth);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新反馈数据实际日期成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新反馈数据实际日期成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新反馈数据实际日期失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新数据emba_id出错！";
		}
		return message;
	}

	// 更新数据状态
	public String[] upEmZYTState(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.upEmZYTState(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口数据操作成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口数据操作成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口数据操作失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口数据操作出错！";
		}
		return message;
	}

	// 更新调整类型
	public String[] upEmZYTAdType(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.upEmZYTAdType(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新委托事件成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新委托事件成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新委托事件失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新委托事件出错！";
		}
		return message;
	}

	// 更新所属月份
	public String[] upEmZYTOwnmonth(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;
			result = dal.upEmZYTOwnmonth(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口更新所属月份成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口更新所属月份成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口更新所属月份失败!";
			}

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口更新所属月份出错！";
		}
		return message;
	}

	// 社保基数调整
	public String[] upEmZYTSheBaoSalary(EmZYTModel m, EmShebaoUpdateModel sbm) {
		String[] message = new String[5];
		try {
			int result = 0;

			// 调用新增方法
			Emsi_OperateBll opbll = new Emsi_OperateBll();
			message = opbll.upSalary(sbm);

			if (message[0].equals("1")) {
				// 更新智翼通接口数据状态
				m.setEmzt_state(1);
				result = dal.upEmZYTState(m);
			} else {
				result = 1;
			}

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口社保基数调整成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口社保基数调整成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口社保基数调整失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口社保基数调整出错！";
		}
		return message;
	}

	// 服务费调整
	public String[] upEmZYTFeeAdjust(EmZYTModel m) {
		String[] message = new String[5];
		try {
			int result = 0;

			// 更新智翼通接口数据
			result = dal.upEmZYTFeeAdjust(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "智翼通接口服务费调整成功!";
				message[2] = String.valueOf(m.getId());
				message[3] = "EmZYT";
				message[4] = "智翼通接口服务费调整成功";
			} else {
				message[0] = "0";
				message[1] = "智翼通接口服务费调整失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "智翼通接口服务费调整出错！";
		}
		return message;
	}

	// 新增"生成反馈文件"记录
	public String[] addEmZYTFeedbackFile(EmZYTFeedbackFileModel m) {
		String[] message = new String[5];
		try {
			int result = 0;

			// 更新智翼通接口数据
			result = dal.addEmZYTFeedbackFile(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(m.getEzff_id());
				message[3] = "EmZYT";
				message[4] = "操作成功";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错！";
		}
		return message;
	}
}
