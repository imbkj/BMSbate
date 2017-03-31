package dal.EmHouseCard;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Conn.dbconn;
import Model.CoHousingFundModel;
import Model.EmHouseCardBackInfoModel;
import Model.EmHouseCardFailInfoModel;
import Model.EmHouseMakeCardInfoModel;
import Model.EmHouseTakeCardInfoModel;

public class EmHouse_TakeCardInfoSelectDal {
	// 查询公积金领卡信息
	public List<EmHouseTakeCardInfoModel> getEmhouseTakeCardInfo(String str) {
		List<EmHouseTakeCardInfoModel> list = new ArrayList<EmHouseTakeCardInfoModel>();
		dbconn db = new dbconn();
		String sql = "select  a.re_id,a.gid,isnull(picnum,0) picnum,username,gidnum,a.cid,cname,re_client,convert(varchar(10),re_ctime,120) as re_ctime,"
				+ "convert(varchar(10),re_apptime,120) as re_apptime,idcard,re_accounttype,re_type,re_gjjno,re_cardid,re_name,"
				+ "convert(varchar(10),re_time,120) as re_time,re_ifmessage,re_state,re_applycase,re_cardstate,re_band,re_cgjjno,"
				+ "re_failcase,pla_client,convert(varchar(10),pla_clienttime,120) as pla_clienttime,pla_clientassistant,"
				+ "convert(varchar(10),pla_clientassistanttime,120) as pla_clientassistanttime,pla_welfreassistant,"
				+ "convert(varchar(10),gjj_welfreassistanttime,120) as gjj_welfreassistanttime,pla_bankpeople,cosp_card_data_caliname,"
				+ "convert(varchar(10),pla_tobanktime,120) as pla_tobanktime,pla_fl,convert(varchar(10),pla_fltime,120) as pla_fltime,"
				+ "pla_receivename,convert(varchar(10),pla_receivetime,120) as pla_receivetime,pla_receliveclient,"
				+ "convert(varchar(10),pla_clientttime,120) as pla_clientttime,pla_taketype,otherdata,upbank,shortname,re_contactsate,"
				+ "dept,convert(varchar(10),addtime,120) as addtime,a.ownmonth,send_client,State_Name,State_Id,addname,isnull(num,0) num, "
				+ "Re_taprid,coba_client,coba_company,coba_shortname,cohf_banklk,re_docId,cosp_card_caliname,cosp_bsal_caliname from EmHouseTakeCardInfo a "
				+ " left join CoBaseServePromise prom on a.cid=prom.cid "
				+ " left join (select COUNT(*) picnum,gid from empic where empi_class like '%身份证%' group by gid) empc on a.Gid=empc.gid "
				+ " left join (select fail_reid,count(*) as num from EmHouseCardFailInfo group by fail_reid) e on a.re_id=e.fail_reid "
				+ " inner join (select COUNT(gid) gidnum,gid from EmHouseTakeCardInfo group by gid) g on a.Gid=g.gid "
				+ " inner join (select cohf_houseid,cohf_banklk,cohf_bankjc,cohf_single,cid  from CoHousingFund) d on a.re_cgjjno=d.cohf_houseid and (cohf_single=0 or (cohf_single=1 and a.cid=d.cid)) "
				+ " inner join MakeOrTakeCardState b on a.Re_State=b.State_Id"
				+ " inner join CoBase c on a.Cid=c.cid "
				+ " inner join View_loginrole dept on c.coba_client=dept.log_name"
				+ " where b.state_type=2 "
				+ str + " order by re_id desc";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseTakeCardInfoModel.class,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询领卡信息的所属月份或申报月份
	public List<String> getOwnmonthInfo(String searchinfo, String str,
			String getInfo) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String ownmon = "";
		if (month < 9) {
			month = month + 1;
			ownmon = year + "0" + month;
		} else {
			month = month + 1;
			ownmon = year + "" + month;
		}

		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		dbconn db = new dbconn();
		String sql = "select " + searchinfo
				+ " from EmHouseTakeCardInfo where 1=1 " + str;
		try {
			list.add("");
			list.add(ownmon);
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString(getInfo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询状态
	public List<EmHouseTakeCardInfoModel> getStateInfo(String str) {
		ResultSet rs = null;
		List<EmHouseTakeCardInfoModel> list = new ArrayList<EmHouseTakeCardInfoModel>();
		dbconn db = new dbconn();
		String sql = "select State_Name,State_Id from MakeOrTakeCardState where 1=1 "
				+ str + " order by orderid ";
		try {
			EmHouseTakeCardInfoModel model = new EmHouseTakeCardInfoModel();
			model.setState_Name("");
			model.setState_Id(0);
			list.add(model);
			rs = db.GRS(sql);
			while (rs.next()) {
				EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
				m.setState_Name(rs.getString("State_Name"));
				m.setState_Id(rs.getInt("State_Id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询状态
	public EmHouseTakeCardInfoModel getStateId(String str) {
		ResultSet rs = null;
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		dbconn db = new dbconn();
		String sql = "select State_Name,State_Id from MakeOrTakeCardState where 1=1 "
				+ str;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				m.setState_Name(rs.getString("State_Name"));
				m.setState_Id(rs.getInt("State_Id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 判断是否已存在该月份的领卡数据
	public boolean ifExist(String ownmonth, String no) {
		boolean flag = false;
		ResultSet rs = null;
		List<EmHouseTakeCardInfoModel> list = new ArrayList<EmHouseTakeCardInfoModel>();
		dbconn db = new dbconn();
		String sql = "select count(Re_CGjjNo) as ggnos from EmHouseTakeCardInfo where Re_CGjjNo='"
				+ no + "' and ownmonth=" + ownmonth;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				if (rs.getInt("ggnos") > 0) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	// 判断制卡信息中是否存在该员工的所属月份的制卡信息
	public EmHouseTakeCardInfoModel getInfo(String sfcard, String ownmonth,
			String str) {
		ResultSet rs = null;
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		dbconn db = new dbconn();
		String sql = "select top 1 a.gid,a.cid,a.cname,convert(varchar(10),gjj_tobanktime,120) as gjj_tobanktime,"
				+ "b.emhc_single,b.ownmonth,coba_client,coba_shortname,coba_company,cohf_banklk "
				+ "from emhousemakecardinfo a inner join CoBase c on a.Cid=c.cid, "
				+ "emhousechange b inner join CoHousingFund d on b.emhc_companyid=d.cohf_houseid"
				+ " where a.gid=b.gid and gjj_tobanktime<>'' "
				+ str
				+ " and a.idcard='"
				+ sfcard
				+ "' and a.ownmonth="
				+ ownmonth
				+ " order by id desc ";
		try {
			rs = db.GRS(sql);
			if (rs.next()) {
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setCname(rs.getString("cname"));
				m.setRe_apptime(rs.getString("gjj_tobanktime"));
				m.setRe_accounttype(changeacc(rs.getInt("emhc_single")));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCohf_banklk(rs.getString("cohf_banklk"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 查询员工的公积金申报信息
	public EmHouseTakeCardInfoModel getEmhouseCahneInfo(String str) {
		ResultSet rs = null;
		EmHouseTakeCardInfoModel m = new EmHouseTakeCardInfoModel();
		dbconn db = new dbconn();
		String sql = "select top 1 convert(varchar(10),Emhc_declareTime,120) as Emhc_declareTime,"
				+ " case emhc_change when '新增' then 1 when '调入' then 2 when '比例调整' then 3 "
				+ " when '基数调整' then 4 when '比例基数调整' then 5 when '年度调基' then 6 when '停交' "
				+ " then 7 end t_order,* "
				+ " from EmHouseChange a inner join CoBase b on a.cid=b.cid left join CoHousingFund d "
				+ " on a.emhc_companyid=d.cohf_houseid"
				+ " where 1=1 "
				+ str
				+ " order by t_order,emhc_id";
		try {
			rs = db.GRS(sql);
			if (rs.next()) {
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setCname(rs.getString("emhc_company"));
				m.setRe_apptime(rs.getString("Emhc_declareTime"));
				m.setRe_accounttype(changeacc(rs.getInt("emhc_single")));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCohf_banklk(rs.getString("cohf_houseid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// 统计数据
	public Integer[] getCountInfo() {
		ResultSet rs = null;
		dbconn db = new dbconn();
		Integer[] intinfo = new Integer[4];
		String sql = "select (select count(re_id) from EmHouseTakeCardInfo a,MakeOrTakeCardState b "
				+ "where a.Re_State=b.State_Id and b.state_type=2 and State_Name='中心核收') as zl,"
				+ "(select count(re_id) from EmHouseTakeCardInfo a,MakeOrTakeCardState b "
				+ "where a.Re_State=b.State_Id and b.state_type=2 and State_Name='福利核收') as fl,"
				+ "(select count(re_id) from EmHouseTakeCardInfo a,MakeOrTakeCardState b "
				+ "where a.Re_State=b.State_Id and b.state_type=2 and State_Name='已交银行') as yb,"
				+ "(select count(re_id) from EmHouseTakeCardInfo a,MakeOrTakeCardState b "
				+ "where a.Re_State=b.State_Id and b.state_type=2 and State_Name='福利领卡') as ft";
		try {
			rs = db.GRS(sql);
			if (rs.next()) {
				intinfo[0] = rs.getInt("zl");
				intinfo[1] = rs.getInt("fl");
				intinfo[2] = rs.getInt("yb");
				intinfo[3] = rs.getInt("ft");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return intinfo;
	}

	// 获取公积金卡退回信息
	public List<EmHouseCardBackInfoModel> getEmhouseCardBackInfo(String str) {
		List<EmHouseCardBackInfoModel> list = new ArrayList<EmHouseCardBackInfoModel>();
		dbconn db = new dbconn();
		String sql = "select back_id,back_cardId,back_name,back_time,back_case from EmHouseCardBackInfo where 1=1"
				+ str + " order by Back_id desc";
		try {
			list = db.find(sql, EmHouseCardBackInfoModel.class,
					dbconn.parseSmap(EmHouseCardBackInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取公积金领卡银行名称
	public List<String> getBankInfo() {
		List<String> list = new ArrayList<String>();
		String sql = "select distinct(cohf_banklk) as cohf_banklk from CoHousingFund";
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("cohf_banklk"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取备注
	public List<EmHouseCardFailInfoModel> getEmhouseRemarkCardInfo(String str) {
		List<EmHouseCardFailInfoModel> list = new ArrayList<EmHouseCardFailInfoModel>();
		dbconn db = new dbconn();
		String sql = "select convert(varchar(10),fail_addtime,120) as fail_addtime,"
				+ "* from EmHouseCardFailInfo where fail_content is not null "
				+ str;
		try {
			list = db.find(sql, EmHouseCardFailInfoModel.class,
					dbconn.parseSmap(EmHouseCardFailInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询清册
	public List<EmHouseTakeCardInfoModel> getOutEmhouseTakeCardInfo(String str) {
		List<EmHouseTakeCardInfoModel> list = new ArrayList<EmHouseTakeCardInfoModel>();
		dbconn db = new dbconn();
		String sql = "select a.gid,username,re_cgjjno,cname,re_gjjno,emhu_idcardclass,emba_sex,emhu_houseid,emhu_idcard,"
				+ " emba_idcard,emba_mobile,esiu_computerid,emhu_degree,re_cardid,"
				+ "emhu_title,emhu_mobile,a.ownmonth,emhu_radix,emhu_hj,emba_marital,emhu_wifename,"
				+ "emhu_wifeidcard from EmHouseTakeCardInfo a inner join  MakeOrTakeCardState b "
				+ "on a.Re_State=b.State_Id inner join embase eb on a.gid=eb.gid left join "
				+ "emhouseupdate hs on a.gid=hs.gid left join emshebaoupdate d on a.gid=d.gid "
				+ " where 1=1 " + str + " order by a.gid";
		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseTakeCardInfoModel.class,
					dbconn.parseSmap(EmHouseTakeCardInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private String changeacc(Integer ty) {
		String acc = "";
		if (ty == 0) {
			acc = "中智开户";
		} else if (ty == 1) {
			acc = "独立开户";
		} else if (ty == 2) {
			acc = "中智开户(委托)";
		}
		return acc;
	}

	// 查询单位公积金号是否相同
	public int isSameCompanyId(String strid) {
		String sql = "select count(distinct(Re_CGjjNo)) num from EmHouseTakeCardInfo where re_id in("
				+ strid + ")";
		ResultSet rs = null;
		dbconn db = new dbconn();
		int num = 0;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	// 查询单位公积金号
	public CoHousingFundModel getCompanyId(String strid) {
		CoHousingFundModel model = new CoHousingFundModel();
		String sql = " select distinct(cohf_houseid) cohf_houseid,cohf_company,cohf_banklk"
				+ " from CoHousingFund where cohf_houseid in(select distinct(Re_CGjjNo) "
				+ " from EmHouseTakeCardInfo  where re_id in(" + strid + "))";
		ResultSet rs = null;
		dbconn db = new dbconn();
		int k = 0;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setCohf_houseid(rs.getString("cohf_houseid"));
				model.setCohf_company(rs.getString("cohf_company"));
				model.setCohf_banklk(rs.getString("cohf_banklk"));
				k = k + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (k > 1) {
			model.setCohf_company("-1");
		}
		return model;
	}

	// 查询单位
	public CoHousingFundModel getCompany(String strid) {
		CoHousingFundModel model = new CoHousingFundModel();
		String sql = " select cohf_company"
				+ " from CoHousingFund where cohf_houseid in(select distinct(Re_CGjjNo) "
				+ " from EmHouseTakeCardInfo  where re_id in(" + strid + "))";
		ResultSet rs = null;
		dbconn db = new dbconn();
		int k = 0;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setCohf_company(rs.getString("cohf_company"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	// 查询单位
	public CoHousingFundModel getCompanyByCid(int cid) {
		CoHousingFundModel model = new CoHousingFundModel();
		String sql = " select cohf_company" + " from CoHousingFund where cid="
				+ cid;
		ResultSet rs = null;
		dbconn db = new dbconn();
		int k = 0;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setCohf_company(rs.getString("cohf_company"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	// 查询公积金单位数量
	public int getCompanyCount(String strid) {
		int num = 0;
		String sql = " select COUNT(*) num"
				+ " from CoHousingFund where cohf_houseid in(select distinct(Re_CGjjNo) "
				+ " from EmHouseTakeCardInfo where re_id in(" + strid + "))";
		ResultSet rs = null;
		dbconn db = new dbconn();
		int k = 0;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	// 查询是否所有的都是中智户
	public boolean iFCiicCompanyId(String strid) {
		String sql = "select * from CoHousingFund where cohf_houseid IN(select distinct(Re_CGjjNo) "
				+ "from EmHouseTakeCardInfo where re_id in(" + strid + "))";
		ResultSet rs = null;
		dbconn db = new dbconn();
		boolean flag = true;
		try {
			System.out.println(sql);
			rs = db.GRS(sql);
			while (rs.next()) {
				Integer cid = rs.getInt("cid");
				if (cid != null && cid > 0) {
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 查询是否所有的领卡银行都是招行
	public boolean iFZH(String strid) {
		String sql = "select distinct(cohf_banklk) cohf_banklk from CoHousingFund "
				+ "where cohf_houseid in(select distinct(Re_CGjjNo) "
				+ "from EmHouseTakeCardInfo where re_id in(" + strid + "))";
		ResultSet rs = null;
		dbconn db = new dbconn();
		boolean flag = true;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				String cohf_banklk = "";
				cohf_banklk = rs.getString("cohf_banklk");
				if (cohf_banklk != null && !cohf_banklk.contains("招行")) {
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 查询是否所有的领卡银行都是招行
	public boolean iFJH(String strid) {
		String sql = "select distinct(cohf_banklk) cohf_banklk from CoHousingFund "
				+ "where cohf_houseid in(select distinct(Re_CGjjNo) "
				+ "from EmHouseTakeCardInfo where re_id in(" + strid + "))";
		ResultSet rs = null;
		dbconn db = new dbconn();
		boolean flag = true;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				String cohf_banklk = "";
				cohf_banklk = rs.getString("cohf_banklk");
				if (cohf_banklk != null && !cohf_banklk.contains("建")) {
					flag = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 查询是否所有的领卡银行都是招行
	public boolean iFSameCompany(String strid) {
		String sql = "select distinct(cid) cid from EmHouseTakeCardInfo"
				+ "  where re_id in(" + strid + ")";
		ResultSet rs = null;
		dbconn db = new dbconn();
		boolean flag = true;
		Integer k = 0;
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				k = k + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (k > 1) {
			flag = false;
		}
		return flag;
	}

	// 根据gid查询员工状态
	public String getEmbaState(Integer gid) {
		String emba_state = "";
		String sql = "SELECT emba_statestr from View_Embaselist where gid="
				+ gid;
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				emba_state = rs.getString("emba_statestr");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emba_state;
	}

	// 查询员工手机号码、
	public String getEmba_mobile(int gid) {
		String sql = "select * from embase where gid=" + gid;
		dbconn db = new dbconn();
		String mobile = "";
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				mobile = rs.getString("emba_mobile");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mobile;
	}
}
