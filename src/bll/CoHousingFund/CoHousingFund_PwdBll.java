package bll.CoHousingFund;

import java.util.ArrayList;
import java.util.List;

import service.CoHousingFundPwdService;
import Model.CoHousingFundModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbModel;
import Util.SingletonCoHousingFundPwd;
import Util.SingletonCoHousingFundZb;

/**
 * 密钥专办员业务逻辑
 * 
 * @author Administrator
 * 
 */
public class CoHousingFund_PwdBll {

	private CoHousingFundPwdService service = SingletonCoHousingFundPwd
			.getInstance().getChpi();

	public int getcohfid(int cid) {
		return service.getcohfid(cid);
	}

	public boolean delPwd(int cfpc_id) {
		boolean flag = false;
		if (service.delPwdd(cfpc_id) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean UpdateLimit1(CoHousingFundPwdChangeModel m) {
		boolean flag = false;
		if (service.UpdateLimit(m) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean UpdateLimit(CoHousingFundPwdChangeModel m) {
		boolean flag = false;
		if (service.UpdateLimit(m) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean UpdatePwdZb(CoHousingFundPwdChangeModel m) {
		boolean flag = false;
		if (service.UpdatePwdZb(m) != 0) {
			flag = true;
		}
		return flag;
	}

	public CoHousingFundPwdModel getPwdByid(int chfp_id) {

		return service.getPwdByid(chfp_id);
	}

	public List<CoHousingFundModel> getChfzList() {
		List<CoHousingFundModel> list = SingletonCoHousingFundZb.getInstance()
				.getChzi().getCoHousingFundList();
		List<CoHousingFundModel> list1 = new ArrayList<CoHousingFundModel>();
		return list;
	}

	public List<CoHousingFundModel> getChfzListByCondition(String str,
			String status) {
		String condition = null;
		List<CoHousingFundModel> list1 = new ArrayList<CoHousingFundModel>();
		if (!"".equals(status) && status.equals("1")) {
			condition = " cohf_houseid like '%" + str + "%'";
		} else if (!"".equals(status) && status.equals("2")) {
			condition = " cid like '%" + str + "%'";
		} else if (!"".equals(status) && status.equals("3")) {
			condition = " cohf_company like '%" + str + "%'";
		}

		List<CoHousingFundModel> list = SingletonCoHousingFundZb.getInstance()
				.getChzi().getCoHousingFundListbyCondition(condition);
		for (int i = 0; i < list.size(); i++) {
			if (service.allPwd(list.get(i).getCohf_id()).size() != 0) {
				list1.add(list.get(i));
			}
		}
		return list1;
	}

	public CoHousingFundPwdChangeModel getPwdChangeByid(int cfpc_id) {
		CoHousingFundPwdChangeModel m = service.getPwdChangeByid(cfpc_id);
		m.setNumberAndName(m.getCfpc_zb_name() + m.getCfpc_zb_number());
		if (m.getFlag() == 1) {
			m.setBflag(true);
		} else {
			m.setBflag(false);
		}
		return m;

	}

	public List<CoHousingFundPwdChangeModel> queryPwd() {

		return service.queryPwd();
	}

	public List<CoHousingFundPwdChangeModel> queryPwdByCondition(
			String queryCondition, String status) {

		String condition = null;
		if (!"".equals(status) && status.equals("1")) {
			condition = " cohf_houseid like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("2")) {
			condition = " cid like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("3")) {
			condition = " cohf_company like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("4")) {
			if (queryCondition.equals("未申报")) {
				condition = " cfpc_state = " + 1;
			} else if (queryCondition.equals("申报中")) {
				condition = " cfpc_state = " + 2;
			} else if (queryCondition.equals("已申报")) {
				condition = " cfpc_state = " + 3;
			} else if (queryCondition.equals("退回")) {
				condition = " cfpc_state = " + 4;
			}

		}
		List<CoHousingFundPwdChangeModel> list = service
				.queryPwdByCondition(condition);
		return list;
	}

	public CoHousingFundPwdChangeModel getDate(int cfpc) {
		return service.getDate(cfpc);
	}

	public int resubmitPwdChange(CoHousingFundPwdChangeModel cfpm) {

		return service.resubmitPwdChange(cfpm);
	}

	public boolean changePwd(CoHousingFundPwdModel cfpm, int chfp_id) {
		boolean flag = false;
		if (service.changePwd(cfpm, chfp_id) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean changePwdc(CoHousingFundPwdModel cfpm, int chfp_id,
			int sbstate) {
		boolean flag = false;
		if (service.changePwdc(cfpm, chfp_id, sbstate) != 0) {
			flag = true;
		}
		return flag;
	}

	public List<CoHousingFundZbModel> notPwdZb(int cohf_id) {
		List<CoHousingFundZbModel> list = service.notPwdZb(cohf_id);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getChfz_name() != null
					&& !list.get(i).getChfz_name().equals("")
					&& list.get(i).getChfz_number() != null && !list.get(i).getChfz_number().equals("")) {
				list.get(i).setChfz_numberAndName(
						list.get(i).getChfz_name()
								+ list.get(i).getChfz_number());
				
			}

		}
		return list;
	}

	public boolean changeStatus(CoHousingFundPwdChangeModel cfcm) {
		boolean flag = false;
		if (service.changeStatus(cfcm) != 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 密钥续期
	 * 
	 * @return
	 */
	public boolean renewalPwd(CoHousingFundPwdChangeModel cfpc) {
		boolean flag = false;
		if (service.renewalPwd(cfpc) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean renewalPwdc(CoHousingFundPwdChangeModel cfpc) {
		boolean flag = false;
		if (service.renewalPwdc(cfpc) != 0) {
			flag = true;
		}
		return flag;
	}

	public List<CoHousingFundPwdModel> allPwd(int cohf_id) {
		List<CoHousingFundPwdModel> list = service.allPwd(cohf_id);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setChfp_numberAndName(
					list.get(i).getChfp_zb_name()
							+ list.get(i).getChfp_zb_number());
		}
		return list;
	}

	public boolean addPwd(CoHousingFundPwdChangeModel cfpc) {
		boolean flag = false;
		if (service.addPwd(cfpc) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean addPwdc(CoHousingFundPwdChangeModel cfpc) {
		boolean flag = false;
		if (service.addPwdc(cfpc) != 0) {
			flag = true;
		}
		return flag;
	}

	// 该单位的该专办员是否存在有效的密钥
	public boolean havePwd(int cohf_id, int addZbid) {
		boolean flag = false;
		if (service.havePwd(cohf_id, addZbid) == 0) {
			flag = true;
		}
		return flag;
	}

	public int addPwdChange(CoHousingFundPwdChangeModel cfpc, String sign) {
		return service.addPwdChange(cfpc, sign);
	}

	public List<CoHousingFundPwdChangeModel> getPwdResubmitList() {
		List<CoHousingFundPwdChangeModel> list = service.getPwdListChange();
		List<CoHousingFundPwdChangeModel> list1 = new ArrayList<CoHousingFundPwdChangeModel>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCfpc_state() == 4) {
				list.get(i).setCfpc_statusString("退回");
				list1.add(list.get(i));
			} else if (list.get(i).getCfpc_state() == 1) {
				list.get(i).setCfpc_statusString("未申报");
				list1.add(list.get(i));
			}
		}
		return list1;
	}

	public List<CoHousingFundPwdChangeModel> getPwdListChange() {
		List<CoHousingFundPwdChangeModel> list = service.getPwdListChange();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCfpc_state() == 4) {
				list.get(i).setCfpc_statusString("退回");
			} else if (list.get(i).getCfpc_state() == 1) {
				list.get(i).setCfpc_statusString("未申报");
			} else if (list.get(i).getCfpc_state() == 2) {
				list.get(i).setCfpc_statusString("申报中");
			} else if (list.get(i).getCfpc_state() == 3) {
				list.get(i).setCfpc_statusString("已申报");
			}
			
		}
		return list;
	}

	public List<CoHousingFundPwdChangeModel> getPwdListChangeByCondition(
			String queryCondition, String status) {
		String condition = null;
		if (!"".equals(status) && status.equals("1")) {
			condition = " cohf_houseid like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("2")) {
			condition = " cid like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("3")) {
			condition = " cohf_company like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("4")) {
			if (queryCondition.equals("未申报")) {
				condition = " cfpc_state = " + 1;
			} else if (queryCondition.equals("申报中")) {
				condition = " cfpc_state = " + 2;
			} else if (queryCondition.equals("已申报")) {
				condition = " cfpc_state = " + 3;
			} else if (queryCondition.equals("退回")) {
				condition = " cfpc_state = " + 4;
			}

		}

		List<CoHousingFundPwdChangeModel> list = service
				.getPwdListChangeByCondition(condition);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCfpc_state() == 4) {
				list.get(i).setCfpc_statusString("退回");
			} else if (list.get(i).getCfpc_state() == 1) {
				list.get(i).setCfpc_statusString("未申报");
			} else if (list.get(i).getCfpc_state() == 2) {
				list.get(i).setCfpc_statusString("申报中");
			} else if (list.get(i).getCfpc_state() == 3) {
				list.get(i).setCfpc_statusString("已申报");
			}
		}
		return list;
	}

	public List<CoHousingFundPwdModel> getPwdById(int cohf_id) {
		List<CoHousingFundPwdModel> list = service
				.getCoHousingFundPwdById(cohf_id);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setChfp_numberAndName(
					list.get(i).getChfp_zb_name()
							+ list.get(i).getChfp_zb_number());
		}
		return list;
	}

	public void addPassWord(CoHousingFundPwdModel p) {
		service.addPassWord(p);
	}
}
