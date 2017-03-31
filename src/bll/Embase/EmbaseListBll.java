package bll.Embase;

import impl.CheckStringImpl;

import java.util.Date;
import java.util.List;

import service.CheckStringService;

import Model.EmbaseModel;
import Model.embaseyfModel;
import Util.DateStringChange;
import Util.MonthListUtil;
import Util.UserInfo;
import dal.Embase.Embasedal;

public class EmbaseListBll {

	private static Embasedal emdal = new Embasedal();

	// 按条件查询员工列表
	public List<EmbaseModel> searchembaselist(String str) {
		String sql = checkEmbaseSearchstr(str);
		// System.out.println(sql);
		sql = sql
				+ " and CID in ( select distinct cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_selected=1 )";
		return emdal.getembaList(sql);
	}

	// 按条件查询员工列表
	public List<EmbaseModel> searchembaselistall(String str) {
		String sql = checkEmbaseSearchstr(str);
		// System.out.println(sql);
		sql = sql + " and  1=1 ";
		return emdal.getembaList(sql);
	}

	// 按条件查询员工管理列表
	public List<EmbaseModel> searchembaseeditlist(String str) {
		String sql = checkEmbaseSearchstr(str);
		sql = sql
				+ " and exists( select distinct cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 ) ";
		if (str!=null && !str.equals("")) {
			return emdal.searchembase(sql,true);
		}else {
			return emdal.searchembase(sql,false);

		}
		
	}

	// 按条件查询员工管理列表
	public List<EmbaseModel> searchembaseeditlistcid(String str, int cid) {
		String sql = checkEmbaseSearchstr(str);
		sql = sql
				+ " and cid= "
				+ cid
				+ " and CID in ( select distinct cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		// System.out.println(sql);
		return emdal.getembaList(sql);

	}

	// 按条件查询员工管理列表(雇员服务中心)
	public List<embaseyfModel> searchembaseedityflist(String sqrname,
			String cobasestring, String statestring, String lxstring,
			String lxstatestring, String lxwebstatestr, String clstatestring,
			String str, String ownmonth,String cozhlx,String assistant) {
		String sql = pjsqlwhere(sqrname, cobasestring, statestring, lxstring,
				lxstatestring, lxwebstatestr, clstatestring, str, ownmonth,cozhlx,assistant);
		sql = sql
				+ " and a.CID in ( select distinct cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		// System.out.println(sql);
		return emdal.getembaListyf(sql);

	}

	// 修改雇员服务中心任务单状态(雇员服务中心)
	public int changelxstate(int gid, String statestr, int type) {
		int state = 0;

		if (statestr.equals("未联系")) {
			state = 0;
		}
		if (statestr.equals("部分确认")) {
			state = 1;
		}

		if (statestr.equals("联系完成")) {
			state = 2;
		}

		if (statestr.equals("未提交")) {
			state = 0;
		}

		if (statestr.equals("部分提交")) {
			state = 1;
		}

		if (statestr.equals("提交完成")) {
			state = 2;
		}

		return emdal.Updatestate(gid, state, type);

	}

	// 员工列表查询拼接SQL 客服,公司信息,员工状态，联系方式，联系状态，材料状态,员工查询，服务中心文员
	private static String pjsqlwhere(String sqrname, String cobasestring,
			String statestring, String lxstring, String lxstatestring,
			String lxwebstatestr, String clstatestring, String str,
			String ownmonth,String cozhlx,String assistant) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();
		if (!sqrname.equals(""))// 客服
		{

			sql.append(" and (coba_client='");
			sql.append(sqrname);
			sql.append("')");
		}
		// if (!cobasestring.equals(""))//公司信息
		// {
		// sql.append(" and (coba_client='");
		// sql.append(sqrname);
		// sql.append("')");
		//
		// }
		if (!assistant.equals(""))// 服务中心负责人
		{

			sql.append(" and (coba_assistant='");
			sql.append(assistant);
			sql.append("')");
		}
		
		if (!statestring.equals(""))// 员工状态
		{
			int statestint = 0;
			if (statestring.equals("未处理")) {
				statestint = 5;
			}
			if (statestring.equals("退回")) {
				statestint = 3;
			}
			if (statestring.equals("已申报")) {
				statestint = 1;
			}

			sql.append(" and (emba_state=");
			sql.append(statestint);
			sql.append(")");

		}
		
		if (!cozhlx.equals(""))// 社保公积金独立户筛选
		{
	 
			 
			if (cozhlx.equals("社保独立户")) {
				sql.append(" and cosb_state=1 ");
			}
			if (cozhlx.equals("公积金独立户")) {
				sql.append(" and cohf_state=1 ");
			}
			if (cozhlx.equals("社保大户")) {
				sql.append(" and cosb_state is null ");
			}
			if (cozhlx.equals("公积金大户")) {
				sql.append(" and cohf_state is null ");
			}
		 

		}

		if (!ownmonth.equals(""))// 所属月份 取社保公积金最小的ownmonth,以前为null的插入进去
		{
			//所属月份判断
			
			//1、台帐月
			//社保月
			//公积金月

			sql.append(" and case when isnull(emba_emhb_ownmonth,0)> isnull(emba_emsb_ownmonth,0) AND emba_emsb_ownmonth IS NOT null   " +
					"then emba_emsb_ownmonth else case when emba_emhb_ownmonth>0 then emba_emhb_ownmonth else  " +
					"emba_emsb_ownmonth  end end =");
			sql.append(ownmonth);
		
		}

		if (!lxstring.equals(""))// 联系方式
		{
			sql.append(" and (cosp_enty_caliname like '%");
			sql.append(lxstring);
			sql.append("%')");
		}

		if (!lxstatestring.equals(""))// 联系状态
		{
			Integer lxstate = null;
			if (lxstatestring.equals("未联系")) {
				lxstate = 0;
			} else if (lxstatestring.equals("部分确认")) {
				lxstate = 1;
			} else if (lxstatestring.equals("联系完成")) {
				lxstate = 2;
			}

			sql.append(" and (emzt_contactstate=");
			sql.append(lxstate);
			sql.append(")");
		}

		if (!clstatestring.equals(""))// 材料状态
		{
			Integer clstate = null;
			if (clstatestring.equals("未提交")) {
				clstate = 0;
			} else if (clstatestring.equals("部分提交")) {
				clstate = 1;
			} else if (clstatestring.equals("提交完成")) {
				clstate = 2;
			}
			sql.append(" and (emzt_datastate='");
			sql.append(clstate);
			sql.append("')");
		}

		if (!lxwebstatestr.equals(""))//
		{
			Integer lxwebstates = null;
			if (lxwebstatestr.equals("未创建")) {
				lxwebstates = 0;
			} else if (lxwebstatestr.equals("未更新")) {
				lxwebstates = 1;
			} else if (lxwebstatestr.equals("部分更新")) {
				lxwebstates = 2;
			} else if (lxwebstatestr.equals("已更新")) {
				lxwebstates = 3;
			}

			sql.append(" and (emzt_contactstateweb='");
			sql.append(lxwebstates);
			sql.append("')");
		}

		if (!cobasestring.equals("") & cobasestring != null) {

			if (ch.isNum(cobasestring)) {

				if (Long.parseLong(cobasestring) < 9999
						& Long.parseLong(cobasestring) > 0) {
					sql.append(" and (a.cid=");
					sql.append(cobasestring.toString());
					sql.append(")");
				}

			} else if (ch.isLetter(cobasestring.replaceAll(" ", ""))) {
				String[] strletter;
				strletter = cobasestring.split(" ");
				if (strletter.length > 1) {
					sql.append(" and ( coba_namespell like '%");
					sql.append(strletter[0]);
					sql.append("%')");
				}
			} else {
				sql.append(" and (coba_company like '%");
				sql.append(cobasestring);
				sql.append("%')");
			}
		}

//		if (!str.equals("") & str != null) {
//
//			if (ch.isNum(str)) {
//
//				// 判断是电话号码还是gid
//				if (Long.parseLong(str) > Long.parseLong("10000000000")
//						& Long.parseLong(str) < Long.parseLong("20000000000")) {
//					sql.append(" and (emba_mobile='");
//					sql.append(str);
//					sql.append("')");
//				} else {
//					if (Long.parseLong(str) > 9999
//							& Long.parseLong(str) < 900000) {
//						sql.append(" and (a.gid=");
//						sql.append(str);
//						sql.append(")");
//					} else if (Long.parseLong(str) < 9999) {
//						sql.append(" and (a.cid=");
//						sql.append(str);
//						sql.append(")");
//					}
//					// 身份证号码
//					else if (Long.parseLong(str) >  Long.parseLong("60000000000")) {
//						sql.append(" and (a.emba_idcard='");
//						sql.append(str);
//						sql.append("')");
//					}
//
//				}
//
//			} else if (ch.isLetter(str.replaceAll(" ", ""))) {
//				String[] strletter;
//				strletter = str.split(" ");
//				if (strletter.length > 1) {
//					sql.append(" and (a.emba_spell like '%");
//					sql.append(strletter[0]);
//					sql.append("%' and coba_namespell like '%");
//					sql.append(strletter[1]);
//					sql.append("%')");
//				} else {
//					sql.append(" and (emba_spell like '%");
//					sql.append(strletter[0]);
//					sql.append("%')");
//				}
//			} else {
//				sql.append(" and (emba_name like '%");
//				sql.append(str);
//				sql.append("%')");
//			}
//		} else {
//			sql.append(" and 1=1");
//		}
		
		if (!str.equals("") & str != null) {

			if (ch.isNum(str)) {

				// 判断是电话号码还是gid
				if (Long.parseLong(str) > 100000000
						& Long.parseLong(str) < 200000000) {
					sql.append(" and (emba_mobile='");
					sql.append(str);
					sql.append("')");
				} else {
					if (Long.parseLong(str) > 9999
							& Long.parseLong(str) < 900000) {
						sql.append(" and (a.gid=");
						sql.append(str);
						sql.append(")");
					} else if (Long.parseLong(str) < 9999) {
						sql.append(" and (cid=");
						sql.append(str);
						sql.append(")");
					}
					// 身份证号码
					else if (Long.parseLong(str) > 600000000) {
						sql.append(" and (emba_idcard='");
						sql.append(str);
						sql.append("')");
					}

				}

			} else if (ch.isLetter(str.replaceAll(" ", ""))) {
				String[] strletter;
				strletter = str.split(" ");
				if (strletter.length > 1) {
					sql.append(" and ((emba_spell like '%");
					sql.append(strletter[0]);
					sql.append("%' and coba_namespell like '%");
					sql.append(strletter[1]);
					 
					sql.append("%') or emba_name like '%");
					sql.append(str);
					sql.append("%')");
					
					
				} else {
					sql.append(" and (emba_spell like '%");
					sql.append(strletter[0]);
					sql.append("%')");
				}
			} else {
				sql.append(" and (a.emba_name like '%");
				sql.append(str);
				sql.append("%' or ");
				sql.append("  a.emba_idcard like '%");
				sql.append(str);
				sql.append("%')");
			}
		} else {
			sql.append(" and 1=1");
		}

		return sql.toString();
	}

	// 按条件查询员工列表
	public List<Integer> datecount(String str) {
		String sql = checkEmbaseSearchstr(str);
		return emdal.getcountsum(sql);
	}

	// 按条件查询员工列表(综合性查询)
	public static String[] searchembaselists() {
		String[] dictionary = emdal.getembaList();
		return dictionary;
	}

	// 获取单个员工信息
	public EmbaseModel getEmbaseInfo(String str) {
		return emdal.getEmbaseInfo(str);
	}

	// 员工列表查询拼接SQL
	private static String checkEmbaseSearchstr(String str) {
		CheckStringService ch = new CheckStringImpl();
		StringBuilder sql = new StringBuilder();

		if (!str.equals("") & str != null) {

			if (ch.isNum(str)) {

				// 判断是电话号码还是gid
				if (Long.parseLong(str) > 100000000
						& Long.parseLong(str) < Long.parseLong("99999999999")) {
					sql.append(" and (emba_mobile='");
					sql.append(str);
					sql.append("')");
				} else {
					if (Long.parseLong(str) > 9999
							& Long.parseLong(str) < 900000) {
						sql.append(" and (gid=");
						sql.append(str);
						sql.append(")");
					} else if (Long.parseLong(str) < 9999) {
						sql.append(" and (cid=");
						sql.append(str);
						sql.append(")");
					}
					// 身份证号码
					else if (Long.parseLong(str) > 600000000) {
						sql.append(" and (emba_idcard='");
						sql.append(str);
						sql.append("')");
					}

				}

			} else if (ch.isLetter(str.replaceAll(" ", ""))) {
				String[] strletter;
				strletter = str.split(" ");
				if (strletter.length > 1) {
					sql.append(" and ((emba_spell like '%");
					sql.append(strletter[0]);
					sql.append("%' and coba_namespell like '%");
					sql.append(strletter[1]);
					 
					sql.append("%') or emba_name like '%");
					sql.append(str);
					sql.append("%')");
					
					
				} else {
					sql.append(" and (emba_spell like '%");
					sql.append(strletter[0]);
					sql.append("%')");
				}
			} else {
				sql.append(" and (emba_name like '%");
				sql.append(str);
				sql.append("%' or ");
				sql.append("  emba_idcard like '%");
				sql.append(str);
				sql.append("%')");
			}
		} else {
			sql.append(" ");
		}
		// 修改权限
		// sql.append(" and CID in ( select cid from DataPopedom where log_id="+UserInfo.getUserid()+" and  Dat_edited=1 )");

		return sql.toString();
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

	// 获取过去3个月的所属月份数组
	public String[] getLastOwnmonthByNowf(boolean exNow) {
		String[] month = null;
		try {
			String nowMonth = DateStringChange
					.DatetoSting(new Date(), "yyyyMM");
			month = MonthListUtil.getMonthList(exNow, nowMonth, "f", 3);
		} catch (Exception e) {

		}
		return month;
	}
	
	// 获取过去3个月的所属月份数组
	public String[] getOwnmonthlist(boolean exNow) {
		String[] month = null;
		try {
			String nowMonth = DateStringChange.ownmonthAdd(DateStringChange
					.DatetoSting(new Date(), "yyyyMM"),-2);
			
			month = MonthListUtil.getMonthList(exNow, nowMonth, "f", 6);
		} catch (Exception e) {

		}
		return month;
	}

	// 根据cid获取该公司的员工信息
	public List<EmbaseModel> getEmBaseListByCid(Integer cid) {
		return emdal.getEmBaseListByCid(cid);
	}

}
