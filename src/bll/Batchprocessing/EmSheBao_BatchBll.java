package bll.Batchprocessing;

import java.util.List;

import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

import Model.EmShebaoChangeUploadModel;
import Model.EmShebaoUpdateModel;
import Util.IdcardUtil;
import Util.UserInfo;
import dal.Batchprocessing.EmSheBao_BatchDal;

public class EmSheBao_BatchBll {
	private EmSheBao_BatchDal dal;
	private Emsi_OperateBll opbll;

	public EmSheBao_BatchBll() {
		dal = new EmSheBao_BatchDal();
	}

	// 根据用户获取导入的社保数据(修改工资)
	public List<EmShebaoChangeUploadModel> getShebaoUploadSalaryUpdateByUser(
			String username) {
		return dal.getShebaoUploadSalaryUpdateByUser(username);
	}

	// 根据用户获取导入的错误提示(修改工资)
	public List<EmShebaoChangeUploadModel> getShebaoUploadSalaryUpdateErr(
			String username) {
		return dal.getShebaoUploadSalaryUpdateErr(username);
	}

	// 根据用户获取导入的社保数据(社保新增)
	public List<EmShebaoChangeUploadModel> getShebaoUploadAddByUser(
			String username) {
		return dal.getShebaoUploadAddByUser(username);
	}

	// 根据用户获取导入的错误提示(社保新增)
	public List<EmShebaoChangeUploadModel> getShebaoUploadAddErr(String username) {
		return dal.getShebaoUploadAddErr(username);
	}

	// 处理社保导入数据
	public String[] addDataToDb(List<EmShebaoChangeUploadModel> ciList,
			String username, String templetType, String uploadfilename) {
		int sum = ciList.size();
		String[] success;
		int emsu_type = 0;
		String[] message = new String[3];
		try {
			switch (templetType) {
			case "新增调入":
				emsu_type = 1;
				break;
			case "修改工资":
				emsu_type = 3;
				break;
			case "独立户接管":
				emsu_type = 6;
				break;
			default:
				break;
			}
			success = addBatchUpload(ciList, username, uploadfilename,
					emsu_type);
			if (Integer.parseInt(success[0]) == sum) {
				message[0] = "1";
				message[1] = "共导入数据" + sum + "条，全部成功。";
			} else {
				message[0] = "0";
				message[1] = "共导入数据" + sum + "条，其中有" + (sum - Integer.parseInt(success[0]))
						+ "条数据导入失败。名单为："+success[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "数据导入出错。";
		}
		return message;
	}

	// 处理申报社保数据
	public String[] handleShebao(List<EmShebaoChangeUploadModel> list) {
		int sum = 0;
		int success = 0;
		String[] message = new String[2];
		opbll = new Emsi_OperateBll();
		try {
			int opOwnmonth = getShebaoOpOwnmonth();
			for (EmShebaoChangeUploadModel m : list) {
				if (m.isCheck() && m.getEmsu_state() == 0) {
					sum++;
					if (m.getGid() != 0) {
						if (decalreShebao(m, opOwnmonth)) {
							success++;
						}
					} else {
						dal.upBatchUpload(m.getEmsu_id(), 0,
								"系统中未找到该员工，请核对其姓名与身份证是否一致。");
					}
				}
			}
			if (sum == 0) {
				message[0] = "0";
				message[1] = "请先勾选需要提交的数据。";
			} else if (sum == success) {
				message[0] = "1";
				message[1] = "共提交数据" + sum + "条，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共提交数据" + sum + "条，其中有" + (sum - success)
						+ "条数据提交失败。";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "处理数据时出错。";
		}
		return message;
	}

	// 申报社保数据
	private boolean decalreShebao(EmShebaoChangeUploadModel m, int opOwnmonth) {
		boolean bool = false;
		try {
			switch (m.getEmsu_type()) {
			case 1:
				bool = newin(m, opOwnmonth);
				break;
			case 2:
				bool = movein(m, opOwnmonth);
				break;
			case 3:
				// 修改工资
				bool = updateSalary(m, opOwnmonth);
				break;
			case 4:
				bool = newinForeigner(m, opOwnmonth);
				break;
			case 5:
				bool = moveinForeigner(m, opOwnmonth);
				break;
			case 6:
				bool = takeOver(m);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 社保独立户接管
	private boolean takeOver(EmShebaoChangeUploadModel m) {
		boolean bool = false;
		try {
			Emsi_SelBll bll = new Emsi_SelBll();
			if (!bll.existsEmState(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "该员工已离职，无法操作社保数据!");
				return false;
			} else if (bll.existsShebao(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "已有该员工社保信息，不能新增!");
				return false;
			} else if (!bll.existsCoOfferList(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"该员工未分配“社会保险服务”的报价单项目，请分配该项目后再操作此步骤！");
				return false;
			} else if (m.getOwnmonth() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写所属月份!");
				return false;
			} else if (m.getEmsu_computerid() == null
					|| "".equals(m.getEmsu_computerid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写社保电脑号!");
				return false;
			} else if (m.getEmsu_computerid().length() < 7
					|| m.getEmsu_computerid().length() > 9) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "个人电脑号长度应为7或9位!");
				return false;
			} else if (m.getEmsu_radix() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写工资基数!");
				return false;
			} else if (m.getEmsu_formula() == null
					|| "".equals(m.getEmsu_formula())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到对应的社保算法!");
				return false;
			} else {
				m.setEuModel(new EmShebaoUpdateModel());
				m.getEuModel().setGid(m.getGid());
				m.getEuModel().setEsiu_name(m.getEmsu_name());
				m.getEuModel().setEsiu_computerid(m.getEmsu_computerid());
				m.getEuModel().setFormulaid(m.getEmsu_formulaid());
				m.getEuModel().setEsiu_yl(m.getEmsu_YL());
				m.getEuModel().setEsiu_gs(m.getEmsu_GS());
				m.getEuModel().setEsiu_sye(m.getEmsu_Sye());
				m.getEuModel().setEsiu_syu(m.getEmsu_Syu());
				m.getEuModel().setEsiu_yltype(m.getEmsu_YLType());
				m.getEuModel().setEmsf_soin_title(m.getEmsu_formula());
				m.getEuModel().setOwnmonth(m.getOwnmonth());
				m.getEuModel().setEsiu_radix(m.getEmsu_radix());
				m.getEuModel().setMobile(m.getMobile());
				m.getEuModel().setEsiu_remark("独立户接管");
				m.getEuModel().setEsiu_addname(UserInfo.getUsername());
				m.getEuModel().setIfdeclare(1);
				String[] message = opbll.movein(m.getEuModel());
				if ("1".equals(message[0])) {
					dal.upBatchUpload(m.getEmsu_id(), 1, null);
					bool = true;
				} else {
					dal.upBatchUpload(m.getEmsu_id(), 0, message[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return bool;
	}

	// 外籍人社保调入
	private boolean moveinForeigner(EmShebaoChangeUploadModel m, int opOwnmonth) {
		boolean bool = false;
		try {
			Emsi_SelBll bll = new Emsi_SelBll();
			if (!bll.existsEmState(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "该员工已离职，无法操作社保数据!");
				return false;
			} else if (bll.existsShebao(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "已有该员工社保信息，不能新增!");
				return false;
			} else if (!bll.existsCoOfferList(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"该员工未分配“社会保险服务”的报价单项目，请分配该项目后再操作此步骤！");
				return false;
			} else if (!bll.existsForeigner(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "该员工非外籍人，不能做外籍人调入!");
				return false;
			} else if (m.getOwnmonth() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写所属月份!");
				return false;
			} else if (m.getOwnmonth() < opOwnmonth) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "当前月份已不能申报数据。");
				return false;
			} else if (m.getEmsu_computerid() == null
					|| "".equals(m.getEmsu_computerid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写社保电脑号!");
				return false;
			} else if (m.getEmsu_computerid().length() < 7
					|| m.getEmsu_computerid().length() > 9) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "个人电脑号长度应为7或9位!");
				return false;
			} else if (m.getEmsu_radix() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写工资基数!");
				return false;
			} else if (m.getEmsu_radix() > 99999) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!");
				return false;
			} else if (m.getMobile() == null || "".equals(m.getMobile())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到参保人手机号码!");
				return false;
			} else if (m.getEmsu_formula() == null
					|| "".equals(m.getEmsu_formula())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到对应的社保算法!");
				return false;
			} else {
				m.setEuModel(new EmShebaoUpdateModel());
				m.getEuModel().setGid(m.getGid());
				m.getEuModel().setEsiu_name(m.getEmsu_name());
				m.getEuModel().setEsiu_computerid(m.getEmsu_computerid());
				m.getEuModel().setFormulaid(m.getEmsu_formulaid());
				m.getEuModel().setEsiu_yl(m.getEmsu_YL());
				m.getEuModel().setEsiu_gs(m.getEmsu_GS());
				m.getEuModel().setEsiu_sye(m.getEmsu_Sye());
				m.getEuModel().setEsiu_syu(m.getEmsu_Syu());
				m.getEuModel().setEsiu_yltype(m.getEmsu_YLType());
				m.getEuModel().setEmsf_soin_title(m.getEmsu_formula());
				m.getEuModel().setOwnmonth(m.getOwnmonth());
				m.getEuModel().setEsiu_radix(m.getEmsu_radix());
				m.getEuModel().setMobile(m.getMobile());
				m.getEuModel().setEsiu_remark("批量导入");
				m.getEuModel().setEsiu_addname(UserInfo.getUsername());
				m.getEuModel().setIfdeclare(7);
				String[] message = opbll.moveinForeigner(m.getEuModel());
				if ("1".equals(message[0])) {
					dal.upBatchUpload(m.getEmsu_id(), 1, null);
					bool = true;
				} else {
					dal.upBatchUpload(m.getEmsu_id(), 0, message[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return bool;
	}

	// 外籍人社保新增
	private boolean newinForeigner(EmShebaoChangeUploadModel m, int opOwnmonth) {
		boolean bool = false;
		try {
			Emsi_SelBll bll = new Emsi_SelBll();
			if (!bll.existsEmState(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "该员工已离职，无法操作社保数据!");
				return false;
			} else if (bll.existsShebao(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "已有该员工社保信息，不能新增!");
				return false;
			} else if (!bll.existsCoOfferList(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"该员工未分配“社会保险服务”的报价单项目，请分配该项目后再操作此步骤！");
				return false;
			} else if (!bll.existsForeigner(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "该员工非外籍人，不能做外籍人新增!");
				return false;
			} else if (m.getOwnmonth() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写所属月份!");
				return false;
			} else if (m.getOwnmonth() < opOwnmonth) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "当前月份已不能申报数据。");
				return false;
			} else if (m.getEmsu_radix() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写工资基数!");
				return false;
			} else if (m.getEmsu_radix() > 99999) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!");
				return false;
			} else if (m.getEmsu_Worker() == null
					|| "".equals(m.getEmsu_Worker())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写职工性质!");
				return false;
			} else if (m.getEmsu_Folk() == null || "".equals(m.getEmsu_Folk())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写民族!");
				return false;
			} else if (m.getEmsu_Hand() == null || "".equals(m.getEmsu_Hand())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写利手!");
				return false;
			} else if (m.getMobile() == null || "".equals(m.getMobile())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到参保人手机号码!");
				return false;
			} else if (m.getEmsu_formula() == null
					|| "".equals(m.getEmsu_formula())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到对应的社保算法!");
				return false;
			} else {
				m.setEuModel(new EmShebaoUpdateModel());
				m.getEuModel().setGid(m.getGid());
				m.getEuModel().setEsiu_name(m.getEmsu_name());
				m.getEuModel().setFormulaid(m.getEmsu_formulaid());
				m.getEuModel().setEsiu_yl(m.getEmsu_YL());
				m.getEuModel().setEsiu_gs(m.getEmsu_GS());
				m.getEuModel().setEsiu_sye(m.getEmsu_Sye());
				m.getEuModel().setEsiu_syu(m.getEmsu_Syu());
				m.getEuModel().setEsiu_yltype(m.getEmsu_YLType());
				m.getEuModel().setEmsf_soin_title(m.getEmsu_formula());
				m.getEuModel().setOwnmonth(m.getOwnmonth());
				m.getEuModel().setEsiu_radix(m.getEmsu_radix());
				m.getEuModel().setFolk(m.getEmsu_Folk());
				m.getEuModel().setWorker(m.getEmsu_Worker());
				m.getEuModel().setHand(m.getEmsu_Hand());
				m.getEuModel().setMobile(m.getMobile());
				m.getEuModel().setEsiu_remark("批量导入");
				m.getEuModel().setEsiu_addname(UserInfo.getUsername());
				m.getEuModel().setIfdeclare(7);
				String[] message = opbll.newinForeigner(m.getEuModel());
				if ("1".equals(message[0])) {
					dal.upBatchUpload(m.getEmsu_id(), 1, null);
					bool = true;
				} else {
					dal.upBatchUpload(m.getEmsu_id(), 0, message[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return bool;
	}

	// 社保调入
	private boolean movein(EmShebaoChangeUploadModel m, int opOwnmonth) {
		boolean bool = false;
		try {
			Emsi_SelBll bll = new Emsi_SelBll();
			if (!bll.existsEmState(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "该员工已离职，无法操作社保数据!");
				return false;
			} else if (bll.existsShebao(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "已有该员工社保信息，不能新增!");
				return false;
			} else if (!bll.existsCoOfferList(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"该员工未分配“社会保险服务”的报价单项目，请分配该项目后再操作此步骤！");
				return false;
			} else if (m.getOwnmonth() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写所属月份!");
				return false;
			} else if (m.getOwnmonth() < opOwnmonth) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "当前月份已不能申报数据。");
				return false;
			} else if (m.getEmsu_computerid() == null
					|| "".equals(m.getEmsu_computerid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写社保电脑号!");
				return false;
			} else if (m.getEmsu_computerid().length() < 7
					|| m.getEmsu_computerid().length() > 9) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "个人电脑号长度应为7或9位!");
				return false;
			} else if (m.getEmsu_radix() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写工资基数!");
				return false;
			} else if (m.getEmsu_radix() > 99999) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!");
				return false;
			} else if (m.getMobile() == null || "".equals(m.getMobile())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到参保人手机号码!");
				return false;
			} else if (m.getEmsu_formula() == null
					|| "".equals(m.getEmsu_formula())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到对应的社保算法!");
				return false;
			} else {
				m.setEuModel(new EmShebaoUpdateModel());
				m.getEuModel().setGid(m.getGid());
				m.getEuModel().setEsiu_name(m.getEmsu_name());
				m.getEuModel().setEsiu_computerid(m.getEmsu_computerid());
				m.getEuModel().setFormulaid(m.getEmsu_formulaid());
				m.getEuModel().setEsiu_yl(m.getEmsu_YL());
				m.getEuModel().setEsiu_gs(m.getEmsu_GS());
				m.getEuModel().setEsiu_sye(m.getEmsu_Sye());
				m.getEuModel().setEsiu_syu(m.getEmsu_Syu());
				m.getEuModel().setEsiu_yltype(m.getEmsu_YLType());
				m.getEuModel().setEmsf_soin_title(m.getEmsu_formula());
				m.getEuModel().setOwnmonth(m.getOwnmonth());
				m.getEuModel().setEsiu_radix(m.getEmsu_radix());
				m.getEuModel().setMobile(m.getMobile());
				m.getEuModel().setEsiu_remark("批量导入");
				m.getEuModel().setEsiu_addname(UserInfo.getUsername());
				m.getEuModel().setIfdeclare(4);
				String[] message = opbll.movein(m.getEuModel());
				if ("1".equals(message[0])) {
					dal.upBatchUpload(m.getEmsu_id(), 1, null);
					bool = true;
				} else {
					dal.upBatchUpload(m.getEmsu_id(), 0, message[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return bool;
	}

	// 社保新增
	private boolean newin(EmShebaoChangeUploadModel m, int opOwnmonth) {
		boolean bool = false;
		try {
			Emsi_SelBll bll = new Emsi_SelBll();
			if (!bll.existsEmState(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "该员工已离职，无法操作社保数据!");
				return false;
			} else if (bll.existsShebao(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "已有该员工社保信息，不能新增!");
				return false;
			} else if (!bll.existsCoOfferList(m.getGid())) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"该员工未分配“社会保险服务”的报价单项目，请分配该项目后再操作此步骤！");
				return false;
			} else if (m.getOwnmonth() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写所属月份!");
				return false;
			} else if (m.getOwnmonth() < opOwnmonth) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "当前月份已不能申报数据。");
				return false;
			} else if (m.getEmsu_radix() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写工资基数!");
				return false;
			} else if (m.getEmsu_radix() > 99999) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!");
				return false;
			} else if (m.getEmsu_Worker() == null
					|| "".equals(m.getEmsu_Worker())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写职工性质!");
				return false;
			} else if (m.getEmsu_Folk() == null || "".equals(m.getEmsu_Folk())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写民族!");
				return false;
			} else if (m.getEmsu_Hand() == null || "".equals(m.getEmsu_Hand())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写利手!");
				return false;
			} else if (m.getMobile() == null || "".equals(m.getMobile())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到参保人手机号码!");
				return false;
			} else if (m.getEmsu_formula() == null
					|| "".equals(m.getEmsu_formula())) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到对应的社保算法!");
				return false;
			} else {
				m.setEuModel(new EmShebaoUpdateModel());
				m.getEuModel().setGid(m.getGid());
				m.getEuModel().setEsiu_name(m.getEmsu_name());
				m.getEuModel().setFormulaid(m.getEmsu_formulaid());
				m.getEuModel().setEsiu_yl(m.getEmsu_YL());
				m.getEuModel().setEsiu_gs(m.getEmsu_GS());
				m.getEuModel().setEsiu_sye(m.getEmsu_Sye());
				m.getEuModel().setEsiu_syu(m.getEmsu_Syu());
				m.getEuModel().setEsiu_yltype(m.getEmsu_YLType());
				m.getEuModel().setEmsf_soin_title(m.getEmsu_formula());
				m.getEuModel().setOwnmonth(m.getOwnmonth());
				m.getEuModel().setEsiu_radix(m.getEmsu_radix());
				m.getEuModel().setFolk(m.getEmsu_Folk());
				m.getEuModel().setWorker(m.getEmsu_Worker());
				m.getEuModel().setHand(m.getEmsu_Hand());
				m.getEuModel().setMobile(m.getMobile());
				m.getEuModel().setEsiu_remark("批量导入");
				m.getEuModel().setEsiu_addname(UserInfo.getUsername());
				m.getEuModel().setIfdeclare(4);
				String[] message = opbll.newin(m.getEuModel());
				if ("1".equals(message[0])) {
					dal.upBatchUpload(m.getEmsu_id(), 1, null);
					bool = true;
				} else {
					dal.upBatchUpload(m.getEmsu_id(), 0, message[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return bool;
	}

	// 修改工资
	private boolean updateSalary(EmShebaoChangeUploadModel m, int opOwnmonth) {
		boolean bool = false;
		try {
			IdcardUtil idu=new IdcardUtil();
			String emsu_idcard = "";// 在册数据身份证号
			String esiu_idcard = "";// 导入数据身份证号

			if (m.getEmsu_idcard().length() == 15) {
				emsu_idcard=idu.conver15CardTo18(m.getEmsu_idcard());
			} else {
				emsu_idcard = m.getEmsu_idcard();
			}

			if (m.getEsiu_idcard().length() == 15) {
				esiu_idcard=idu.conver15CardTo18(m.getEsiu_idcard());
			} else {
				esiu_idcard = m.getEsiu_idcard();
			}

			if (m.getEuModel() == null) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "未找到该员工的社保数据。");
				return false;
			} else if (m.getOwnmonth() < opOwnmonth) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "当前月份已不能申报数据。");
				return false;
			} else if (m.getOwnmonth() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请填写所属月份!");
				return false;
			} else if (m.getEmsu_radix() == m.getEuModel().getEsiu_radix()) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "您没有修改工资基数，请确认是否修改!");
				return false;
			} else if (m.getEmsu_radix() == 0) {
				dal.upBatchUpload(m.getEmsu_id(), 0, "请输入新的工资基数!");
				return false;
			} else if (m.getEmsu_radix() > 99999) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"月工资总额不能大于99999元,如需申报十万元以上基数,请联系福利组!");
				return false;
			} else if (!esiu_idcard.equals(emsu_idcard)) {
				dal.upBatchUpload(m.getEmsu_id(), 0,
						"身份证号与在册数据的身份证号不一致，请检查员工编号和身份证号是否有误!!");
				return false;

			} else {
				m.getEuModel().setOwnmonth(m.getOwnmonth());
				m.getEuModel().setEsiu_remark("批量导入");
				m.getEuModel().setEsiu_addname(m.getEmsu_addname());
				m.getEuModel().setEsiu_radix(m.getEmsu_radix());
				m.getEuModel().setIfdeclare(0);
				String[] message = opbll.upSalary(m.getEuModel());
				if ("1".equals(message[0])) {
					dal.upBatchUpload(m.getEmsu_id(), 1, null);
					bool = true;
				} else {
					dal.upBatchUpload(m.getEmsu_id(), 0, message[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// 删除社保导入的数据
	public String[] delBatchUpload(List<EmShebaoChangeUploadModel> list) {
		int sum = 0;
		int success = 0;
		int resutl;
		String falseList="";
		String[] message = new String[2];
		try {
			for (EmShebaoChangeUploadModel m : list) {
				if (m.isCheck() && m.getEmsu_state() == 0) {
					sum++;
					resutl=0;
					resutl=dal.delBatchUpload(m.getEmsu_id());
					success = success + resutl;
					
					if (resutl==0) {//失败反馈名单
						falseList=falseList+m.getEmsu_name()+"，";
					}
				}
			}
			if (sum == 0) {
				message[0] = "0";
				message[1] = "请先勾选需要删除的数据。";
			} else if (sum == success) {
				message[0] = "1";
				message[1] = "共删除数据" + sum + "条，全部成功。";
			} else {
				message[0] = "1";
				message[1] = "共删除数据" + sum + "条，其中有" + (sum - success)
						+ "条数据提交失败。名单为："+falseList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message[0] = "2";
			message[1] = "删除数据时出错。";
		}
		return message;
	}

	// 导入社保数据
	private String[] addBatchUpload(List<EmShebaoChangeUploadModel> ciList,
			String username, String uploadfilename, int emsu_type) {
		String[] result =new String[2];
		int success = 0;
		int up = 0;
		String falseList="";
		try {
			for (EmShebaoChangeUploadModel m : ciList) {
				try {
					up = dal.addBatchUpload(m, username, uploadfilename,
							emsu_type);
					if (up > 0) {
						success = success + 1;
					}else {
						falseList=falseList+"("+m.getGid()+")"+m.getEmsu_name()+"；";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			result[0]=String.valueOf(success);
			result[1]=falseList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 获取社保可操作月份
	private int getShebaoOpOwnmonth() {
		Emsi_SelBll selbll = new Emsi_SelBll();
		int ownmonth = 0;
		try {
			// 判断是否停止当月操作社保
			if (selbll.ifStop()) {
				ownmonth = Integer
						.parseInt((selbll.getOwnmonthByNow(false))[0]);
			} else {
				ownmonth = Integer.parseInt((selbll.getOwnmonthByNow(true))[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ownmonth;
	}
}
