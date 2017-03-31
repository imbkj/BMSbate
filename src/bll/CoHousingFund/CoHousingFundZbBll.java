package bll.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import service.CoHousingFundZbService;

import Model.CoHousingFundModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.SingletonCoHousingFundPwd;
import Util.SingletonCoHousingFundZb;

public class CoHousingFundZbBll {

	private CoHousingFundZbService service = SingletonCoHousingFundZb
			.getInstance().getChzi();

	public CoHousingFundZbModel getZb(int chfz_id) {

		return service.getZb(chfz_id);
	}

	public boolean delSb(int cfzc_id) {
		boolean flag = false;
		if (service.delSb(cfzc_id) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean UpdateZb(int chfz_id, int cfzc_id) {
		boolean flag = false;
		if (service.UpdateZb(chfz_id, cfzc_id) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean updateCusZbChange(CoHousingFundZbChangeModel c) {
		boolean flag = false;
		if (service.updateCusZbChange(c) != 0) {
			flag = true;
		}
		return flag;
	}

	public CoHousingFundZbModel getNewZbInfo(int chfz_id) {
		return service.getNewZbInfo(chfz_id);
	}

	public CoHousingFundZbChangeModel ZbChangeInfo(int cfzc_id) {
		return service.ZbChangeInfo(cfzc_id);

	}

	public boolean resubmitZb(CoHousingFundZbChangeModel cfzc) {
		boolean flag = false;
		if (service.resubmitZb(cfzc) != 0) {
			flag = true;
		}
		return flag;
	}

	public CoHousingFundModel getCohf(int cohf_id) {
		return service.getCohf(cohf_id);
	}

	public List<CoHousingFundModel> queryZbByCondition(String queryCondition,
			String status) {

		String condition = null;
		if (!"".equals(status) && status.equals("1")) {
			condition = " cohf_houseid like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("2")) {
			condition = " cid like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("3")) {
			condition = " cohf_company like '%" + queryCondition + "%'";
		}
		List<CoHousingFundModel> list = service.queryZbByCondition(condition);
		return list;
	}

	public List<CoHousingFundModel> queryZbByC(String queryCondition,
			String status) {

		String condition = null;
		if (!"".equals(status) && status.equals("1")) {
			condition = " cohf_houseid like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("2")) {
			condition = " cid like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("3")) {
			condition = " cohf_company like '%" + queryCondition + "%'";
		} else if (!"".equals(status) && status.equals("4")) {
			if (queryCondition.equals("未申报")) {
				condition = " cfzc_state = " + 1;
			} else if (queryCondition.equals("申报中")) {
				condition = " cfzc_state = " + 2;
			} else if (queryCondition.equals("已申报")) {
				condition = " cfzc_state = " + 3;
			} else if (queryCondition.equals("退回")) {
				condition = " cfzc_state = " + 4;
			}

		}
		List<CoHousingFundModel> list = service.queryZbByC(condition);
		return list;
	}

	public List<CoHousingFundModel> queryZb() {
		return service.queryZb();

	}

	public List<CoHousingFundModel> queryZbC() {
		return service.queryZbC();

	}

	public CoHousingFundZbChangeModel addtype(int cfzc_id) {

		return service.addtype(cfzc_id);
	}

	public CoHousingFundZbModel getZbChangeInfo(int chfz_id) {

		return service.getZbChangeInfo(chfz_id);
	}

	/**
	 * 根据id查专办员
	 * 
	 * @return
	 */
	public CoHousingFundZbModel getCoHousingFundZbById(int chfz_id) {

		return service.getCoHousingFundZbById(chfz_id);
	}

	/**
	 * 专办员业务完成
	 * 
	 * @param r
	 */
	public boolean ZbCompelete(String r, int chfz_id) {
		boolean flag = false;
		if (service.ZbCompelete(r, chfz_id) != 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 后到修改专办员信息
	 * 
	 * @param chfz
	 * @return
	 */
	public boolean updateZb(CoHousingFundZbChangeModel chfz, int chfz_id) {

		return service.updateZb(chfz, chfz_id);
	}

	public boolean addZbc(CoHousingFundZbModel cfzm, int state) {
		boolean flag = false;
		if (service.addZbc(cfzm, state) != 0) {
			flag = true;
		}
		return flag;
	}
	
	public boolean addZbchange(CoHousingFundZbChangeModel cfzm, int state) {
		boolean flag = false;
		if (service.addZbChange(cfzm, state) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean addZb(CoHousingFundZbChangeModel cfzm) {
		boolean flag = false;
		if (service.addZb(cfzm) != 0) {
			flag = true;
		}
		return flag;
	}

	public List<CoHousingFundModel> getChfzList() {
		List<CoHousingFundModel> list = service.getCoHousingFundList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i < list.size(); i++) {
			if (SingletonCoHousingFundPwd.getInstance().getChpi()
					.allPwd(list.get(i).getCohf_id()).size() != 0) {
				list.get(i).setIspwd("有");
			} else {
				list.get(i).setIspwd("无");
			}

		}
		return list;
	}

	public List<CoHousingFundZbChangeModel> getResubmitList() {
		List<CoHousingFundZbChangeModel> list = service.getChfzListChange();
		List<CoHousingFundZbChangeModel> list1 = new ArrayList<CoHousingFundZbChangeModel>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCfzc_state() == 4) {
				list.get(i).setCfzc_statusString("退回");
				list1.add(list.get(i));
			} else if (list.get(i).getCfzc_state() == 1) {
				list.get(i).setCfzc_statusString("未申报");
				list1.add(list.get(i));
			}
		}
		return list1;
	}

	public List<CoHousingFundZbChangeModel> getChfzListChange() {
		List<CoHousingFundZbChangeModel> list = service.getChfzListChange();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCfzc_state() == 4) {
				list.get(i).setCfzc_statusString("退回");
			} else if (list.get(i).getCfzc_state() == 1) {
				list.get(i).setCfzc_statusString("未申报");
			} else if (list.get(i).getCfzc_state() == 2) {
				list.get(i).setCfzc_statusString("申报中");
			} else if (list.get(i).getCfzc_state() == 3) {
				list.get(i).setCfzc_statusString("已申报");
			}
		}

		return list;
	}

	public List<CoHousingFundZbChangeModel> getChfzListChangeByCondition(
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
				condition = " cfzc_state = " + 1;
			} else if (queryCondition.equals("申报中")) {
				condition = " cfzc_state = " + 2;
			} else if (queryCondition.equals("已申报")) {
				condition = " cfzc_state = " + 3;
			} else if (queryCondition.equals("退回")) {
				condition = " cfzc_state = " + 4;
			}

		}

		List<CoHousingFundZbChangeModel> list = service
				.getChfzListChangeByCondition(condition);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCfzc_state() == 0) {
				list.get(i).setCfzc_statusString("退回");
			} else if (list.get(i).getCfzc_state() == 1) {
				list.get(i).setCfzc_statusString("未申报");
			} else if (list.get(i).getCfzc_state() == 2) {
				list.get(i).setCfzc_statusString("申报中");
			} else if (list.get(i).getCfzc_state() == 3) {
				list.get(i).setCfzc_statusString("已申报");
			}
		}

		return list;

	}

	public List<CoHousingFundModel> getChfzListByCondition(String str,
			String status) {
		String condition = null;
		if (!"".equals(status) && status.equals("1")) {
			condition = " cohf_houseid like '%" + str + "%'";
		} else if (!"".equals(status) && status.equals("2")) {
			condition = " cid like '%" + str + "%'";
		} else if (!"".equals(status) && status.equals("3")) {
			condition = " cohf_company like '%" + str + "%'";
		}

		return service.getChfzListByCondition(condition);
	}

	public CoHousingFundModel getChfzListById(int cid) {
		return service.getCoHousingFundListById(cid);
	}

	public List<CoHousingFundZbModel> getZbById(int cohf_id) {

		return service.getZbListById(cohf_id);
	}

	public List<CoHousingFundPwdModel> getPwdById(int cohf_id) {

		return service.getPwdListById(cohf_id);
	}

	public boolean isHaveZb(String cohf_id) {
		int intcohf_id = Integer.valueOf(cohf_id);
		boolean flag = false;
		if (service.isHaveZb(intcohf_id) != 0) {
			flag = true;
		}
		return flag;
	}

	public int addZbChange(CoHousingFundZbChangeModel cfzc) {

		return service.addZbChange(cfzc);
	}

	public List<CoHousingFundZbModel> getZbListBycohf_id(int cohf_id) {

		List<CoHousingFundZbModel> list = service
				.getZbListBycohf_id(cohf_id);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setChfz_numberAndName(
					list.get(i).getChfz_name() + list.get(i).getChfz_number());
		}
		return list;
	}
	
	public List<CoHousingFundZbModel> getNotPwdZb(int cohf_id) {
		List<CoHousingFundZbModel> list = service
				.getZbListBycohf_id(cohf_id);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setChfz_numberAndName(
					list.get(i).getChfz_name() + list.get(i).getChfz_number());
		}
		return list;
	}
	/**
	 * 后到修改专办员申报状态
	 * 
	 * @param cohf_id
	 * @param r
	 * @return
	 */
	public boolean changeStatus(CoHousingFundZbChangeModel cfzc) {
		boolean flag = false;
		if (service.changeStatus(cfzc) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean isHavePwd(int cohf_id, int chfz_id) {
		boolean flag = false;
		if (service.isHavePwd(cohf_id, chfz_id) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean delZbc(CoHousingFundZbChangeModel cfzc) {
		boolean flag = false;
		if (service.delZbc(cfzc) != 0) {
			flag = true;
		}
		return flag;
	}

	public boolean delZb(CoHousingFundZbChangeModel cfzc) {
		boolean flag = false;
		if (service.delZb(cfzc) != 0) {
			flag = true;
		}
		return flag;
	}
}
