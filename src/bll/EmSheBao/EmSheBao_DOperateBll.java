/*
 * 创建人：林少斌
 * 创建时间：2014-1-2
 * 用途：社保变更数据处理Bll
 */
package bll.EmSheBao;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfOperateService;
import bll.EmSheBao.EmSheBao_DOperateImpl;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeForeignerModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoDeclareOKModel;
import Model.EmShebaoErrModel;
import Model.EmShebaoSZSIFileModel;
import Model.EmShebaoUpdateModel;
import Util.UserInfo;
import dal.EmSheBao.EmSheBao_DOperateDal;
import dal.EmSheBao.Emsi_OperateDal;

public class EmSheBao_DOperateBll {
	EmSheBao_DOperateDal dal = new EmSheBao_DOperateDal();

	// 社保变更数据申报
	public String[] declareSingle(int id, int emsc_tapr_id, String username,
			int cid) {
		String[] message = new String[5];
		try {
			EmSheBaoChangeModel m = new EmSheBaoChangeModel();
			m.setId(id);
			m.setEmsc_addname(username);

			Object[] obj = { "0", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new EmSheBao_DOperateImpl());
			message = wf.PassToNext(obj, emsc_tapr_id, username, "", cid, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保变更数据申报(非工作流)
	public String[] declareSingle(int id, String username) {
		String[] message = new String[5];
		int result = 0;
		try {
			EmSheBaoChangeModel m = new EmSheBaoChangeModel();
			m.setId(id);
			m.setEmsc_addname(username);

			result = dal.declareSingle(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作变更数据为“申报中”成功!";
				message[2] = String.valueOf(id);
				message[3] = "EmShebaoChange";
				message[4] = "社保申报中";
			} else {
				message[0] = "0";
				message[1] = "操作变更数据为“申报中”失败!";
			}
			return message;
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保变更数据申报(外籍人)
	public String[] declareForSingle(int id, int emsc_tapr_id, String username,
			int cid) {
		String[] message = new String[5];
		try {
			EmShebaoChangeForeignerModel m = new EmShebaoChangeForeignerModel();
			m.setId(id);
			m.setEmsc_addname(username);

			Object[] obj = { "0", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DForOperateImpl());
			message = wf.PassToNext(obj, emsc_tapr_id, username, "", 0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保交单变更数据申报(交单)
	public String[] declareSZSISingle(int id, int emsc_tapr_id,
			String username, int cid) {
		String[] message = new String[5];
		try {
			EmShebaoChangeSZSIModel m = new EmShebaoChangeSZSIModel();
			m.setEscs_id(id);
			m.setEscs_addname(username);

			Object[] obj = { "0", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DSZSIOperateImpl());
			message = wf.PassToNext(obj, emsc_tapr_id, username, "", cid, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保交单变更数据福利上传PDF文件
	public String[] declareSZSIUpload(int id, int emsc_tapr_id,
			String filename, int cid) {
		String[] message = new String[5];
		try {
			EmShebaoChangeSZSIModel m = new EmShebaoChangeSZSIModel();
			m.setEscs_id(id);
			m.setEscs_addname(UserInfo.getUsername());
			m.setEscs_uploadfile(filename);

			Object[] obj = { "2", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DSZSIOperateImpl());
			message = wf.PassToNext(obj, emsc_tapr_id, UserInfo.getUsername(),
					"", cid, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保交单变更数据客服下载PDF文件
	public String[] declareSZSIDownload(EmShebaoChangeSZSIModel m) {
		String[] message = new String[5];
		try {
			m.setEscs_addname(UserInfo.getUsername());

			Object[] obj = { "7", m.getEscs_id(), m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DSZSIOperateImpl());
			message = wf.PassToNext(obj, m.getEmsc_tapr_id(),
					UserInfo.getUsername(), "", m.getCid(), "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保变更数据申报(外籍人)
	public String[] declareForSingleSuccess(int id, int emsc_tapr_id,
			String computerid, String username) {
		String[] message = new String[5];
		try {
			EmShebaoChangeForeignerModel m = new EmShebaoChangeForeignerModel();
			m.setId(id);
			m.setEmsc_computerid(computerid);
			m.setEmsc_addname(username);

			Object[] obj = { "1", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DForOperateImpl());
			message = wf.PassToNext(obj, emsc_tapr_id, username, "", 0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保交单变更数据申报(交单)
	public String[] declareSZSISingleSuccess(int id, int emsc_tapr_id,
			String username) {
		String[] message = new String[5];
		try {
			EmShebaoChangeSZSIModel m = new EmShebaoChangeSZSIModel();
			m.setEscs_id(id);
			m.setEscs_addname(username);

			Object[] obj = { "1", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DSZSIOperateImpl());
			message = wf.PassToNext(obj, emsc_tapr_id, username, "", 0, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保变更数据申报(生成单项模板)
	public String[] declareSingleExcel(int id, int emsc_tapr_id,
			String username, String excelfile, int cid) {
		String[] message = new String[5];
		try {

			EmSheBaoChangeModel m = new EmSheBaoChangeModel();
			m.setId(id);
			m.setEmsc_excelfile(excelfile);
			m.setEmsc_addname(username);

			Object[] obj = { "1", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new EmSheBao_DOperateImpl());
			message = wf.PassToNext(obj, emsc_tapr_id, username, "", cid, "");
			System.out.println(emsc_tapr_id);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保外籍人新参保数据批量申报(外籍人)
	public String[] declareForSingleExcel(int id, int emsc_tapr_id,
			String username, String excelfile) {
		String[] message = new String[5];
		int result = 0;
		try {

			EmShebaoChangeForeignerModel m = new EmShebaoChangeForeignerModel();
			m.setId(id);
			m.setEmsc_excelfile(excelfile);
			m.setEmsc_addname(username);

			result = dal.declareForSingleExcel(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作外籍人新参保数据为 批量申报 成功!";
				message[2] = String.valueOf(id);
				message[3] = "EmShebaoChangeForeigner";
				message[4] = "社保外籍人新参保申报中";
			} else {
				message[0] = "0";
				message[1] = "操作外籍人新参保数据为 批量申报 失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 社保变更变更为已申报
	public String[] declareSuccess(int id, int emsc_tapr_id, String username,
			int cid) {
		String[] message = new String[5];
		try {

			EmShebaoDeclareOKModel m = new EmShebaoDeclareOKModel();
			m.setId(id);
			m.setEsdo_addname(username);

			Object[] obj = { "7", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new EmSheBao_DOperateImpl());
			message = wf.PassToNext(obj, emsc_tapr_id, username, "", cid, "");

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "申报社保，操作出错。";
		}
		return message;
	}

	// 撤销申报,返回上一步
	public String[] declareCancel(int id, int emsc_tapr_id, String username) {
		String[] message = new String[5];
		try {

			EmSheBaoChangeModel m = new EmSheBaoChangeModel();
			m.setId(id);
			m.setEmsc_addname(username);

			Object[] obj = { "2", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new EmSheBao_DOperateImpl());
			message = wf.ReturnToPrev(obj, emsc_tapr_id, username, "撤销社保申报");

		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "撤销社保申报，操作出错。";
		}
		return message;
	}

	// 退回申报,返回第一步
	public String[] declareReturn(int id, int emsc_tapr_id, String username) {
		String[] message = new String[5];
		try {

			EmSheBaoChangeModel m = new EmSheBaoChangeModel();
			m.setId(id);
			m.setEmsc_addname(username);

			Object[] obj = { "3", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new EmSheBao_DOperateImpl());
			message = wf.ReturnToN(obj, emsc_tapr_id, 1, username);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "退回社保申报，操作出错。";
		}
		return message;
	}

	// 变更数据状态为未申报,返回上一步
	public String[] declareFirstStep(int id, int emsc_tapr_id, String username) {
		String[] message = new String[5];
		try {

			EmSheBaoChangeModel m = new EmSheBaoChangeModel();
			m.setId(id);
			m.setEmsc_addname(username);

			Object[] obj = { "4", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new EmSheBao_DOperateImpl());
			message = wf
					.ReturnToPrev(obj, emsc_tapr_id, username, "变更社保申报为未申报");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "变更社保申报为未申报，操作出错。";
		}
		return message;
	}

	// 外籍人新参保退回申报,返回第一步(外籍人)
	public String[] declareForReturn(int id, int emsc_tapr_id, String username) {
		String[] message = new String[5];
		try {

			EmShebaoChangeForeignerModel m = new EmShebaoChangeForeignerModel();
			m.setId(id);
			m.setEmsc_addname(username);

			Object[] obj = { "3", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DForOperateImpl());
			message = wf.ReturnToN(obj, emsc_tapr_id, 1, username);
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "退回社保申报，操作出错。";
		}
		return message;
	}

	// 外籍人新参保数据状态为未申报,返回上一步(外籍人)
	public String[] declareForFirstStep(int id, int emsc_tapr_id,
			String username) {
		String[] message = new String[5];
		try {

			EmShebaoChangeForeignerModel m = new EmShebaoChangeForeignerModel();
			m.setId(id);
			m.setEmsc_addname(username);

			Object[] obj = { "4", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DForOperateImpl());
			message = wf
					.ReturnToPrev(obj, emsc_tapr_id, username, "变更社保申报为未申报");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "变更社保申报为未申报，操作出错。";
		}
		return message;
	}

	// 外籍人新参保撤销申报,返回第一步(外籍人)
	public String[] declareForCancel(int id, int emsc_tapr_id, String username) {
		String[] message = new String[5];
		try {

			EmShebaoChangeForeignerModel m = new EmShebaoChangeForeignerModel();
			m.setId(id);
			m.setEmsc_addname(username);

			Object[] obj = { "5", id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DForOperateImpl());
			message = wf.ReturnToPrev(obj, emsc_tapr_id, username, "撤销社保申报");

		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "撤销社保申报，操作出错。";
		}
		return message;
	}

	// 变更数据手动申报
	public String[] declareChangeState(int id, int emsc_tapr_id,
			String ifdeclare, String flag, String username, int cid,
			String reason) {
		String[] message = new String[5];
		String type = "";
		try {
			EmSheBaoChangeModel m = new EmSheBaoChangeModel();
			m.setId(id);
			m.setEmsc_ifdeclare(ifdeclare);
			m.setEmsc_flag(flag);
			m.setEmsc_addname(username);

			if (ifdeclare.equals("1") || ifdeclare.equals("2")) {
				type = "5";
			} else if (ifdeclare.equals("0") || ifdeclare.equals("3")) {
				type = "6";
			}

			Object[] obj = { type, id, m };

			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new EmSheBao_DOperateImpl());

			if (ifdeclare.equals("1") || ifdeclare.equals("2")) {// 变更为 申报中 或
																	// 已申报

				message = wf.PassToNext(obj, emsc_tapr_id, username, "", cid,
						"");
				/*
				 * } else if (ifdeclare.equals("0") || ifdeclare.equals("3"))
				 * {// 变更为 // 未申报 或 // 退回
				 * 
				 * message = wf.PassToNext(obj, emsc_tapr_id, username, "", cid,
				 * "");
				 */
			} else if (ifdeclare.equals("3")) {// 变更为
												// 未申报 或
												// 退回

				message = wf.ReturnToN(obj, emsc_tapr_id, 1, username, reason);
			} else if ("0".equals(ifdeclare)) {
				message = wf.ReturnToN(obj, emsc_tapr_id, 3, username, reason);
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保变更申报修改状态，操作出错。";
		}
		return message;
	}

	// 退回变更数据并终止任务单
	public String[] returnStopChange(EmSheBaoChangeModel m) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.returnStopChange(m);

			if (result == 1) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 交单变更数据手动申报
	public String[] declareSZSIChangeState(int id, int emsc_tapr_id,
			int ifdeclare, String flag, String username, int cid, String reason) {
		String[] message = new String[5];
		String type = "";
		try {
			EmShebaoChangeSZSIModel m = new EmShebaoChangeSZSIModel();
			m.setEscs_id(id);
			m.setEscs_ifdeclare(ifdeclare);
			m.setEscs_flag(flag);
			m.setEscs_addname(username);

			if (ifdeclare == 6) {
				type = "0";
			} else if (ifdeclare == 1) {
				type = "1";
			} else if (ifdeclare == 8) {
				type = "4";
			} else if (ifdeclare == 3) {
				type = "3";
			} else if (ifdeclare == 9) {// 返回中心收集材料
				type = "8";

				EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
				Emsi_OperateDal edal = new Emsi_OperateDal();
				int single = edal.getShebaoSingle(dsbll
						.getEmSCSZSIData(" AND escs_id=" + id, "").get(0)
						.getGid());
				m.setEscs_single(single);
				if (single == 1) {
					m.setEscs_ifdeclare(0);
				} else {
					m.setEscs_ifdeclare(7);
				}
			}

			Object[] obj = { type, id, m };

			// 执行工作流
			WfOperateService wf = new WfOperateImpl(
					new EmSheBao_DSZSIOperateImpl());

			if (ifdeclare == 1 || ifdeclare == 6) {// 变更为 申报中 或
													// 已申报
				message = wf.PassToNext(obj, emsc_tapr_id, username, "", cid,
						"");
			} else if (ifdeclare == 0 || ifdeclare == 3) {// 变更为
															// 未申报 或
															// 退回
				message = wf.ReturnToN(obj, emsc_tapr_id, 1, username, reason);
			} else if (ifdeclare == 9) {// 返回中心收集材料
				// message = wf.SkipToN(obj, emsc_tapr_id, 2, "系统", "",
				// m.getCid(), "");
				message = wf.ReturnToN(obj, emsc_tapr_id, 2, "系统", "");
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "社保交单变更申报修改状态，操作出错。";
		}
		return message;
	}

	// 社保变更数据添加备忘
	public String[] declareAddFlag(int id, String flag, String username) {
		String[] message = new String[3];
		int result = 0;
		try {
			EmSheBaoChangeModel m = new EmSheBaoChangeModel();
			m.setId(id);
			m.setEmsc_flag(flag);
			m.setEmsc_flagname(username);

			result = dal.declareAddFlag(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 社保交单变更数据添加备忘
	public String[] declareSZSIAddFlag(int id, String flag, String username) {
		String[] message = new String[3];
		int result = 0;
		try {
			EmShebaoChangeSZSIModel m = new EmShebaoChangeSZSIModel();
			m.setEscs_id(id);
			m.setEscs_flag(flag);
			m.setEscs_flagname(username);

			result = dal.declareSZSIAddFlag(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 插入社保已审核的文件
	public String[] uploadDeclareOK(EmShebaoDeclareOKModel m) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.uploadDeclareOK(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 插入社保已审核的文件
	public String[] updateDeclareOK(String filename) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.updateDeclareOK(filename);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	/*
	 * public String[] uploadDeclareOK(String sheetname, String filename, String
	 * ownmonth, String username, String fileAllname) { String[] message = new
	 * String[3]; int result = 0; try {
	 * 
	 * result = dal.uploadDeclareOK(sheetname, filename, ownmonth, username,
	 * fileAllname);
	 * 
	 * if (result == 0) { message[0] = "1"; message[1] = "操作成功!"; message[2] =
	 * String.valueOf(result); } else { message[0] = "0"; message[1] = "操作失败!";
	 * message[2] = "0"; } } catch (Exception e) { message[0] = "2"; message[1]
	 * = "操作出错。"; message[2] = "0"; } return message; }
	 */

	/*
	 * // 上传社保台账前数据(批量) public String[] uploadFinanceMonth(String sheetname,
	 * String filename, String ownmonth, String username, String fileAllname,
	 * String ifsingle, String shebaoid, String company) { String[] message =
	 * new String[3]; int result = 0; try {
	 * 
	 * result = dal.uploadFinanceMonth(sheetname, filename, ownmonth, username,
	 * fileAllname, ifsingle, shebaoid, company);
	 * 
	 * if (result == 0) { message[0] = "1"; message[1] = "操作成功!"; message[2] =
	 * String.valueOf(result); } else { message[0] = "0"; message[1] = "操作失败!";
	 * message[2] = "0"; } } catch (Exception e) { message[0] = "2"; message[1]
	 * = "操作出错。"; message[2] = "0"; } return message; }
	 */

	// 上传社保台账前数据
	public String[] uploadFinanceMonth(EmShebaoUpdateModel m, String filename) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.uploadFinanceMonth(m, filename);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 更新导入系统的社保台账前的文件的数据的医疗类型
	public int updateFinanceMonth(String filename) {
		int result;
		result = dal.updateFinanceMonth(filename);
		return result;
	}

	// 更新导入系统的社保台账前的文件的数据gid,cid
	public int updateFinanceMonthSP(String filename) {
		int result;
		result = dal.updateFinanceMonthSP(filename);
		return result;
	}

	// 导入台账后单位正常在册人员名单
	public String[] uploadFinanceSZSIFee(EmShebaoUpdateModel m, String filename) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.uploadFinanceSZSIFee(m, filename);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 导入台账后“五险合一”数据
	public String[] uploadFinanceSZSI(EmShebaoUpdateModel m, String filename) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.uploadFinanceSZSI(m, filename);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 导入台账后补交数据
	public String[] uploadFinanceSZSIBJ(EmShebaoBJModel m, String filename) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.uploadFinanceSZSIBJ(m, filename);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 更新导入系统的社保台账后的文件的数据gid,cid
	public int updateFinanceSP(String filename1, String filename2) {
		int result;
		result = dal.updateFinanceSP(filename1, filename2);
		return result;
	}

	// 记录台账后上传文件
	public int addShebaoSZSIFile(EmShebaoSZSIFileModel m) {
		int result;
		result = dal.addShebaoSZSIFile(m);
		return result;
	}

	/*
	 * // 上传社保台账后数据 public String[] uploadFinance(String sheetname1, String
	 * sheetname2, String filename1, String filename2, String ownmonth, String
	 * username, String fileAllname1, String fileAllname2, String ifsingle,
	 * String shebaoid, String company) { String[] message = new String[3]; int
	 * result = 0; try {
	 * 
	 * result = dal.uploadFinance(sheetname1, sheetname2, filename1, filename2,
	 * ownmonth, username, fileAllname1, fileAllname2, ifsingle, shebaoid,
	 * company);
	 * 
	 * if (result == 0) { message[0] = "1"; message[1] = "操作成功!"; message[2] =
	 * String.valueOf(result); } else { message[0] = "0"; message[1] = "操作失败!";
	 * message[2] = "0"; } } catch (Exception e) { message[0] = "2"; message[1]
	 * = "操作出错。"; message[2] = "0"; } return message; }
	 * 
	 * // 上传社保台账后补交数据 public String[] uploadFinanceSZSIBJ(String sheetname,
	 * String ownmonth, String ifsingle, String username, String filename,
	 * String fileAllname) { String[] message = new String[3]; int result = 0;
	 * try {
	 * 
	 * result = dal.uploadFinanceSZSIBJ(sheetname, ownmonth, ifsingle, username,
	 * filename, fileAllname);
	 * 
	 * if (result == 0) { message[0] = "1"; message[1] = "操作成功!"; message[2] =
	 * String.valueOf(result); } else { message[0] = "0"; message[1] = "操作失败!";
	 * message[2] = "0"; } } catch (Exception e) { message[0] = "2"; message[1]
	 * = "操作出错。"; message[2] = "0"; } return message; }
	 */

	// 删除所有台账前数据
	public String[] deleteSZSIMonth() {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.deleteSZSIMonth();

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 删除所有台账后数据
	public String[] deleteSZSI() {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.deleteSZSI();

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 删除台账前数据
	public String[] deleteSZSIMonth(String shebaoid) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.deleteSZSIMonth(shebaoid);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 删除台账后数据
	public String[] deleteSZSI(String shebaoid) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.deleteSZSI(shebaoid);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 台账前逻辑检查
	public String[] chkErrSZSIMonth(String username) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.chkErrSZSIMonth(username);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 台账后逻辑检查
	public String[] chkErrSZSI(String username) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.chkErrSZSI(username);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 删除台账前逻辑检查
	public String[] delErrSZSIMonth(String ownmonth) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.delErrSZSIMonth(ownmonth);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 删除台账后逻辑检查
	public String[] delErrSZSI(String ownmonth) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.delErrSZSI(ownmonth);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 删除社保台账后导入数据
	public boolean deleteSZSIFinance(int shebaoid) {
		boolean flag = false;
		if (dal.deleteSZSIFinance(shebaoid) > 0) {
			flag = true;
		}
		return flag;
	}

	// 删除社保台账前导入数据
	public boolean deleteSZSIFinanceMonth(int shebaoid) {
		boolean flag = false;
		if (dal.deleteSZSIFinanceMonth(shebaoid) > 0) {
			flag = true;
		}
		return flag;
	}

	// 根据社保编号检查是否已有上传的台账
	public boolean existsSZSIByShebaoID(int shebaoid) {
		return dal.existsSZSIByShebaoID(shebaoid);
	}

	// 处理逻辑检查数据(台后)
	public String[] declareSheBaoErr(EmShebaoErrModel m) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.declareSheBaoErr(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 处理逻辑检查数据(台前)
	public String[] declareSheBaoMonthErr(EmShebaoErrModel m) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.declareSheBaoMonthErr(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 处理调走数据
	public String[] updateSheBaoIfstop(Integer gid) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.updateSheBaoIfstop(gid);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 处理大学生医疗
	public String[] updateSheBaoyltype(Integer gid) {
		String[] message = new String[3];
		int result = 0;
		try {

			result = dal.updateSheBaoyltype(gid);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
			message[2] = "0";
		}
		return message;
	}
}
