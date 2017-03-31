package bll.Embase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Model.CoOfferListModel;
import Model.EmHouseBJModel;
import Model.EmShebaoBJModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Util.DateStringChange;
import Util.DateUtil;
import Util.MonthListUtil;

import dal.EmHouse.Emhouse_BjDal;
import dal.Embase.Embase_DimissionDal;

public class Embase_DimissionBll {
	private Embase_DimissionDal dal = new Embase_DimissionDal();

	// 获取智翼通接口预选的报价单项目表id
	public List<Integer> getCgliId(int gid) {
		return dal.getCgliId(gid);
	}

	// 获取智翼通接口预选的报价单项目表id
	public List<Integer> getCgliId2(int gid) {
		return dal.getCgliId2(gid);
	}

	// 获取智翼通接口预选的报价单项目表id(关联合同)
	public List<Integer> getCgliId3(int gid, String str) {
		return dal.getCgliId3(gid, str);
	}

	// 根据报价单表项目ID获取项目列表
	public List<CoOfferListModel> getCoofferlistByColiId(List<Integer> l) {
		List<CoOfferListModel> list = new ListModelList<CoOfferListModel>();
		try {

			list = dal.getCoofferlistByCglifId(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 员工离职
	public String[] emBaseDimission(EmbaseModel m) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.emBaseDimission(m);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作员工离职成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "员工离职";
			} else {
				message[0] = "0";
				message[1] = "操作员工离职失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "员工离职，操作出错。";
		}
		return message;
	}

	// 更新报价单终止收费日
	public String[] stopCoGlist(Integer gid, String cgli_id, String stopdate,
			String username) {
		String[] message = new String[5];
		int result = 0;
		try {
			result = dal.stopCoGlist(gid, cgli_id, stopdate, username);

			if (result == 0) {
				message[0] = "1";
				message[1] = "操作报价单项目终止成功!";
				message[2] = "";
				message[3] = "EmZYT";
				message[4] = "员工报价单项目终止";
			} else {
				message[0] = "0";
				message[1] = "操作报价单项目终止失败!";
			}
			return message;

		} catch (Exception e) {
			message[0] = "2";
			message[1] = "报价单项目终止，操作出错。";
		}
		return message;
	}

	// 获取员工档案的存档机构
	public String getArchiveType(Integer gid) {
		return dal.getArchiveType(gid);
	}

	// 获取员工档案存放机构
	public String getArchivePlace(Integer gid, Integer cid) {
		return dal.getArchivePlace(gid, cid);
	}

	// 获取对应客服信息
	public List<LoginModel> getClientInfo(Integer gid) {
		return dal.getClientInfo(gid);
	}

	// 社保停交所属月份下拉框数据
	public String[] getSBStopMonth(String[] month) {
		DateUtil du = new DateUtil();
		MonthListUtil mlu = new MonthListUtil();
		DateStringChange dsc = new DateStringChange();

		String[] sbMonth = null;
		if (month.length > 0) {
			sbMonth = new String[month.length];

			for (int i = 0; i < month.length; i++) {

				try {

					sbMonth[i] = dsc.DatetoSting(du.getLastDay(dsc
							.ownmonthTodate(mlu.getLastMonth(month[i]))),
							"yyyy-MM-dd");
				} catch (Exception e) {
					sbMonth[i] = "";
				}

			}
		}

		return sbMonth;
	}

	// 公积金停交所属月份下拉框数据
	public List<String> getGJJStopMonth(List<String> month) {
		DateUtil du = new DateUtil();
		MonthListUtil mlu = new MonthListUtil();
		DateStringChange dsc = new DateStringChange();

		List<String> gjjMonth = new ArrayList<>();
		if (month.size() > 0) {

			for (int i = 0; i < month.size(); i++) {
				try {

					gjjMonth.add(dsc.DatetoSting(du.getLastDay(dsc
							.ownmonthTodate(mlu.getLastMonth(month.get(i)))),
							"yyyy-MM-dd"));

				} catch (Exception e) {
					gjjMonth.add("");
				}

			}
		}

		return gjjMonth;
	}

	// 获取传入所属月份的上月最后一天，如：传入201505 返回 2015-04-30
	public String getLastMonthLastDay(String month) {
		DateUtil du = new DateUtil();
		MonthListUtil mlu = new MonthListUtil();
		DateStringChange dsc = new DateStringChange();

		String LMonthLDay = "";

		try {

			LMonthLDay = dsc.DatetoSting(
					du.getLastDay(dsc.ownmonthTodate(mlu.getLastMonth(month))),
					"yyyy-MM-dd");
		} catch (Exception e) {
			LMonthLDay = "";
		}

		return LMonthLDay;
	}

	// 获取公积金补缴月份
	public String[] getGjjBjOwnmonthList(String str) {
		List<EmHouseBJModel> list = new ListModelList<>();
		list = dal.getGjjBjOwnmonthList(str);

		String[] ownmonthStrings;
		ownmonthStrings = new String[list.size() + 1];

		ownmonthStrings[0] = "不退回数据且不终止任务单";

		if (list.size() > 0) {// 转换为数组
			for (int i = 0; i < list.size(); i++) {
				ownmonthStrings[i + 1] = String.valueOf(list.get(i)
						.getOwnmonth());
			}
		}

		return ownmonthStrings;
	}

	// 获取社保补缴月份
	public String[] getSBBjOwnmonthList(String str) {
		List<EmShebaoBJModel> list = new ListModelList<>();
		list = dal.getSBBjOwnmonthList(str);

		String[] ownmonthStrings;
		ownmonthStrings = new String[list.size() + 1];

		ownmonthStrings[0] = "不退回数据且不终止任务单";

		if (list.size() > 0) {// 转换为数组
			for (int i = 0; i < list.size(); i++) {
				ownmonthStrings[i + 1] = String.valueOf(list.get(i)
						.getOwnmonth());
			}
		}

		return ownmonthStrings;
	}

	//判断是否有住房公积金待确认数据
	public boolean chkGJJChange(Integer gid){
		boolean chk=false;
		chk=dal.chkGJJChange(gid);
		return chk;
	}
}
