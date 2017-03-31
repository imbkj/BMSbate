package bll.EmSheBao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.CoBaseModel;
import Model.EmSheBaoChangeHjModel;
import Model.EmSheBaoChangeModel;
import Model.EmShebaoBJModel;
import Model.EmShebaoChangeSZSIModel;
import Model.EmShebaoFormulaModel;
import Model.EmShebaoSetupModel;
import Model.EmShebaoUpdateModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import dal.EmSheBao.Emsi_SelDal;

public class Emsi_SelBll {
	private Emsi_SelDal dal = new Emsi_SelDal();

	// 获取所有公司基本信息
	public List<CoBaseModel> getCoBase() {
		return dal.getCoBase();
	}

	// 根据Cid获取员工信息
	public List<EmbaseModel> getEmbaseByCid(int cid) {
		return dal.getEmbaseByCid(cid);
	}

	// 根据GID获取员工信息
	public EmbaseModel getEmBase(int gid) {
		return dal.getEmBase(gid);
	}

	// 根据GID获取员工补缴信息
	public EmbaseModel getEmBaseBj(int gid) {
		return dal.getEmBaseBj(gid);
	}

	// 获取民族列表
	public List<String> getFolk() {
		return dal.getFolk();
	}

	// 获取模板列表(传入是否为外籍人模板)
	public List<EmShebaoFormulaModel> getFormula(int ifForeign) {
		return dal.getFormula(ifForeign);
	}

	// 获取模板列表()
	public List<EmShebaoFormulaModel> getFormula(String ifForeign) {
		return dal.getFormula(ifForeign);
	}

	// 获取社保设置
	public EmShebaoSetupModel getSbSetup() {
		return dal.getSbSetup();
	}

	// 获取社保在册数据的最后所属月份
	public int getSbUpdateOwnmonth() {
		return dal.getSbUpdateOwnmonth();
	}

	// 获取未来6个月的所属月份数组
	public String[] getOwnmonthByNow(boolean exNow) {
		String[] month = null;
		try {
			String nowMonth = DateStringChange
					.DatetoSting(new Date(), "yyyyMM");
			month = MonthListUtil.getMonthList(exNow, nowMonth, "f", 6);
		} catch (Exception e) {

		}
		return month;
	}

	// 获取在册数据月份未来6个月的所属月份数组
	public String[] getOwnmonthByUpOwnmonth(String upOwnmonth, boolean exNow) {
		String[] month = null;
		try {
			month = MonthListUtil.getMonthList(exNow, upOwnmonth, "f", 6);
		} catch (Exception e) {

		}
		return month;
	}

	// 获取过去3个月的所属月份数组
	public String[] getLastOwnmonthByNow(boolean exNow) {
		String[] month = null;
		try {
			String nowMonth = DateStringChange
					.DatetoSting(new Date(), "yyyyMM");
			month = MonthListUtil.getMonthList(exNow, nowMonth, "h", 3);
		} catch (Exception e) {

		}
		return month;
	}

	// 获取过去6个月的所属月份数组
	public String[] getLastOwnmonthByNow1(boolean exNow) {
		String[] month = null;
		try {
			String nowMonth = DateStringChange
					.DatetoSting(new Date(), "yyyyMM");
			month = MonthListUtil.getMonthList(exNow, nowMonth, "h", 6);
		} catch (Exception e) {

		}
		return month;
	}

	// 判断当月是否可操作社保
	public boolean ifStop() {
		boolean b = false;
		try {
			// 获取当前所属月份
			Date nowDate = new Date(); // 获取当前时间
			DateStringChange dsc = new DateStringChange();
			int nowmonth = Integer.parseInt(dsc.DatetoSting(nowDate, "yyyyMM"));

			// 判断在册表月份是否小于等于当前月份
			if (nowmonth < dal.getSbUpdateOwnmonth()) {
				return true;
			} else {
				if (ifNowLessSbUpdateOwnmonth()) {
					int stopDay = getSbSetup().getLastday();
					if (compareNowDay(stopDay)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 判断当月是否可操作生育津贴申请
	public boolean ifMAStop() {
		boolean b = false;
		try {
			// 获取当前所属月份
			Date nowDate = new Date(); // 获取当前时间
			DateStringChange dsc = new DateStringChange();
			int nowmonth = Integer.parseInt(dsc.DatetoSting(nowDate, "yyyyMM"));

			// 判断在册表月份是否小于等于当前月份
			if (nowmonth < dal.getSbUpdateOwnmonth()) {
				return true;
			} else {
				if (ifNowLessSbUpdateOwnmonth()) {
					int stopDay = getSbSetup().getMalastday();
					if (compareNowDay(stopDay)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 判断社保操作是否需审核
	public boolean ifAud() {
		boolean b = false;
		try {
			if (ifNowLessSbUpdateOwnmonth()) {
				int audDay = getSbSetup().getAuditday();
				if (compareNowDay(audDay)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 判断所属月份是否大于当前月份
	public boolean ifaud(String ownmonth) {
		boolean b = false;
		try {
			int nowmonth = Integer.parseInt(DateStringChange.DatetoSting(
					new Date(), "yyyyMM"));
			if (Integer.parseInt(ownmonth) > nowmonth) {
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 判断社保在册的月份是否小于当前月份
	private boolean ifNowLessSbUpdateOwnmonth() {
		boolean b = false;
		try {
			int ownmonth = getSbUpdateOwnmonth();
			if (ownmonth != 0) {
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
				String nowStr = dateFormat.format(now);
				if (ownmonth <= Integer.parseInt(nowStr)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 与当前天数比较大小
	private boolean compareNowDay(int day) {
		boolean b = false;
		try {
			Calendar c = Calendar.getInstance();
			int now = c.get(Calendar.DATE);
			// 小于返回TRUE
			if (day < now) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	// 根据GID获取员工社保在册表信息
	public EmShebaoUpdateModel getShebaoUpdateByGid(int gid) {
		EmShebaoUpdateModel m = null;
		try {
			m = dal.getShebaoUpdateByGid(gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据cid,single获取社保账户银行信息
	public String[] getShebaoBank(Integer cid, Integer single) {
		String[] bankinfo = { "", "" };
		if (single == 1) {
			bankinfo = dal.getShebaoBank(cid);
		}
		return bankinfo;
	}

	// 根据GID获取员工社保在册表信息(不判断ifstop状态)
	public EmShebaoUpdateModel getShebaoUpdateByGid2(int gid) {
		EmShebaoUpdateModel m = null;
		try {
			m = dal.getShebaoUpdateByGid2(gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据GID获取员工最新的社保表信息
	public EmShebaoUpdateModel getShebaoByGid(int gid) {
		EmShebaoUpdateModel m = null;
		try {
			m = dal.getShebaoByGid(gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 根据GID获取员工社保所有变更信息
	public List<EmSheBaoChangeModel> getAllChangListByGid(int gid,
			String computerid) {
		return dal.getAllChangListByGid(gid, computerid);
	}

	// 根据主键ID获取社保变更表数据
	public EmSheBaoChangeModel getChangById(int id) {
		return dal.getChangById(id);
	}

	// 根据GID获取未处理的员工补缴信息
	public List<EmShebaoBJModel> getBjListByGid(int gid) {
		return dal.getBjListByGid(gid);
	}

	// 根据养老补交ID获取待确认的员工医疗补缴信息
	public EmShebaoBJModel getBjJLListByBJid(int id) {
		return dal.getBjJLListByBJid(id);
	}

	// 根据GID获取未处理完的员工社保变更信息
	public List<EmSheBaoChangeModel> getChangListByGid(int gid) {
		return dal.getChangListByGid(gid);
	}

	// 获取员工社保特殊变更信息
	public EmShebaoChangeSZSIModel getEmSCSZSIData(int escs_id) {
		return dal.getEmSCSZSIData(escs_id);
	}

	// 查询该员工是否分配“社会保险服务”项目
	public boolean existsCoOfferList(int gid) {
		return dal.existsCoOfferList(gid);
	}

	// 查询该员工是否已有社保信息
	public boolean existsShebao(int gid) {
		return dal.existsShebao(gid);
	}

	// 检查该电脑号或身份证号下是否有在册社保数据
	public String[] chkIfShebao(int gid) {
		return dal.chkIfShebao(gid);
	}

	// 查询该员工是否在职
	public boolean existsEmState(int gid) {
		return dal.existsEmState(gid);
	}

	// 查询该员工是否为外籍人
	public boolean existsForeigner(int gid) {
		return dal.existsForeigner(gid);
	}

	// 查询该员工社保是否被调走
	public boolean existsStop(int gid) {
		return dal.existsStop(gid);
	}

	// 检查变更表状态
	public int checkChangeDeclare(int id, int type) {
		return dal.checkChangeDeclare(id, type);
	}

	public List<EmSheBaoChangeHjModel> getEmSheBaoChangeHjList(Integer sbci_id) {
		return dal.getEmSheBaoChangeHjList(sbci_id);
	}

	public EmSheBaoChangeHjModel getEmSheBaoChangeHjModelInfo(Integer sbci_id) {
		EmSheBaoChangeHjModel model = new EmSheBaoChangeHjModel();
		List<EmSheBaoChangeHjModel> list = getEmSheBaoChangeHjList(sbci_id);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 胞胎数
	public List<String> getBriths() {
		List<String> list = null;
		list = new ArrayList<String>();
		list.add("单");
		list.add("双");
		for (int i = 3; i < 9; i++) {
			list.add(String.valueOf(i));
		}
		return list;
	}

	// 胞胎数
	public List<String[]> getDystociatype() {
		List<String[]> list = null;
		list = new ArrayList<String[]>();

		String[] str = new String[] { "1", "剖宫产" };
		list.add(str);

		str = new String[] { "2", "胎头吸引" };
		list.add(str);

		str = new String[] { "3", "胎头旋转" };
		list.add(str);

		str = new String[] { "4", "产钳助产" };
		list.add(str);

		str = new String[] { "5", "臀位助产" };
		list.add(str);
		return list;
	}

	// 根据身份证号和出院日期查询生育津贴数据
	public boolean chkEscmByEoD(String idcard, String endDate) {
		return dal.chkEscmByEoD(idcard, endDate);
	}
}
