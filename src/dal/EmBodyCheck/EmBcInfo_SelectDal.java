package dal.EmBodyCheck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmBcItemGroupModel;
import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmBodycheckCancelModel;
import Model.EmbaseModel;
import Model.embodycheckoperlogModel;
import Util.UserInfo;

public class EmBcInfo_SelectDal {
	// 查询员工体检信息
	public List<EmBodyCheckModel> getEmBodyCheckInfo(String str) {
		List<EmBodyCheckModel> list = new ArrayList<EmBodyCheckModel>();
		dbconn db = new dbconn();
		String sql = "select a.cid,a.gid,embc_id,ebcl_discount,symr_readstate,ebcl_items ebcl_itemsstr,coba_manager,ebcl_bookdate,ebcl_plandate,convert(varchar(10),isnull(ebcl_plandate,ebcl_bookdate),120)bookdate, embc_drawreportdate,"
				+ " embc_showreportdate,ebcl_area,embc_sex,"
				+ " embc_balancedate,convert(decimal(18,1),ebcl_charge) as ebcl_charge,"
				+ " embc_addtime,convert(varchar(10),embc_modtime,120) as embc_modtime,"
				+ " convert(decimal(18,1),embc_finalcharge) as embc_finalcharge, ebcl_plandate,"
				+ " ebcl_linkdate,embc_tapr_id,ebcl_clientbackcase,ebcl_clientbackname,ebcl_clientbacktime,"
				+ " ebcl_drawformdate,ebcs_tips,mnum,cosp_bcrp_postfee,cosp_bcrp_careinfo,"
				+ " ebcl_showformdate,ebcl_addname,"
				+ " ebcl_completedate,ebcl_addtime,"
				+ " ebcl_showreportdate,ebcl_itemgroup,"
				+ " ebcl_clientshowdate,ebcl_showclient,"
				+ " ebcl_showformpeople,ebcl_showreportpeople,ebcl_items,ebcl_remark,coba_company,coba_clientmanager,"
				+ " ebcl_balancedate,ebcl_finalcharge,ebcl_bcid,"
				+ "case ebcl_type when 0 then '单次体检' when 1 then '入职体检' when 2 then '年度体检'  end as ebcl_typename,"
				+ "ebcl_type,ebcs_hospital,ebcl_state,case ebcl_state when 0 then '已取消' when 1 then '待确认'  when 2 then '待申报'"
				+ " when 3 then '体检中' when 4 then '已体检' when 5 then '已体检' when 6 then '已报销' when 7 then '福利退回'"
				+ " when 8 then '报销处理' when 9 then '重新预约' when 10 then '预约中' when 11 then '已结算' when 12 then '已签收报告' when 13 then '取消中' when 14 then '已删除' when 15 then '中心退回' end as embc_statebname,ebsa_address,"
				+ "ebcl_bookmode,case ebcl_bookmode when 1 then '固定时间' else '不固定时间' end ebcl_bookmode2,ebcl_itemnums,ebcl_id,ebcl_backcase,ebcl_hospital,a.*,d.ebsa_doc,empType "
				+ "from EmBodyCheck a "
				+ " left join CoBaseServePromise prom on a.cid=prom.cid "
				+ "inner join EmBodyCheckList b on a.embc_id=b.ebcl_embc_id "
				+ "inner join EmBcSetup c on b.ebcl_hospital=c.ebcs_id "
				+ "left join EmBcSetupAddress d on b.ebcl_area=d.ebsa_id "
				+ "left join (select distinct a.gid,case coco_compacttype when 'FS' THEN '代理员工' when 'EFS' THEN '代理员工' when 'AF' then '派遣员工' when 'FS-2' THEN '派遣员工' when 'FS-4' then '派遣员工' else '' end empType"
				+ " from coglist a inner join CoOfferList b on a.cgli_coli_id=b.coli_id and b.coli_pclass='体检'"
				+ " inner join CoOffer c on b.coli_coof_id=c.coof_id"
				+ " inner join CoCompact d on coof_coco_id=d.coco_id"
				+ " where cgli_stopdate is null and coli_cpfc_name='元/月/人')cg on a.GID=cg.gid"
				+ " left join cobase coba on a.cid=coba.cid "
				+ " left join(select smwr_tid,COUNT(*) mnum from View_Message where smwr_table='EmBodyCheckList' group by smwr_tid) msgnum on b.ebcl_id=msgnum.smwr_tid "
				+ " left join(select smwr_tid, case when syme_log_id="
				+ UserInfo.getUserid()
				+ " then 2 when symr_log_id="
				+ UserInfo.getUserid()
				+ " then symr_readstate end symr_readstate from View_Message where"
				+ " EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message "
				+ " where syme_state=1 and (symr_log_id= "
				+ UserInfo.getUserid()
				+ " or syme_log_id= "
				+ UserInfo.getUserid()
				+ " ) and "
				+ " smwr_table='EmBodyCheckList' group by smwr_tid) msg "
				+ " where View_Message.syme_id=msg.syme_id)) mgs on b.ebcl_id=mgs.smwr_tid "
				+ "where 1=1 and ebcl_flag=1";
		sql = sql + str + " order by ebcl_bookdate desc,ebcl_state";
		System.out.println(sql);
		try {
			list = db.find(sql, EmBodyCheckModel.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工对应合同类型
	public List<EmBodyCheckModel> getEmpType(Integer cid) {
		List<EmBodyCheckModel> list = new ArrayList<EmBodyCheckModel>();
		dbconn db = new dbconn();
		String sql = "select case when MAX(coco_compacttype)!= min(coco_compacttype) then '多个合同' else case MAX(coco_compacttype) when 'FS' THEN '代理员工' when 'EFS' THEN '代理员工' when 'AF' then '派遣员工' when 'FS-2' THEN '派遣员工' when 'FS-4' then '派遣员工' else '' end end empType"
				+ " from embodycheck a inner join CoCompact b on a.CID=b.cid and coco_state>3 and coco_state!=9"
				+ " where a.cid=? and coco_compacttype!='CS'"
				+ " group by a.cid,a.gid";
		try {
			list = db.find(sql, EmBodyCheckModel.class, null, cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工体检信息(婚姻状况)
	public List<EmBodyCheckModel> getmarry(Integer gid) {
		List<EmBodyCheckModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select embc_marry from embodycheck where gid=? and embc_marry='已婚'";
		try {
			list = db.find(sql, EmBodyCheckModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询员工体检信息
	public List<EmBodyCheckModel> getEmBodyCheckList(EmBodyCheckModel em) {
		List<EmBodyCheckModel> list = new ArrayList<EmBodyCheckModel>();
		dbconn db = new dbconn();
		String sql = "select embc_id,a.CID,a.GID,embc_bcid,embc_company,embc_shortname,embc_spell,"
				+ "embc_name,embc_idcard,embc_sex,embc_age,embc_marry,embc_remark,embc_client,embc_addtime,"
				+ "embc_addname,embc_state,embc_modname,embc_modtime,embc_tapr_id,"
				+ "ebcl_id,ebcl_hospital,ebcl_area,ebcl_itemgroup,ebcl_items,ebcl_itemnums,ebcl_charge,"
				+ "ebcl_discount,ebcl_bookdate,ebcl_linkdate,ebcl_drawformdate,ebcl_showformdate,"
				+ "ebcl_showformpeople,ebcl_plandate,ebcl_wodate,ebcl_state,ebcl_type,ebcl_bx,ebcl_change,"
				+ "ebcl_special,ebcl_remark,ebcl_remark2,ebcl_exportnum,ebcl_filename,ebcl_addtime,ebcl_addname,"
				+ "ebcl_modtime,ebcl_modname,ebcl_completeDate,ebcl_clientshowdate,ebcl_showclient,"
				+ "ebcl_showreportdate,ebcl_showreportpeople,ebcl_finalcharge,ebcl_balancedate,ebcl_flag,"
				+ "ebcs_id,ebcs_hospital,ebcs_name,ebcs_hospital,ebcs_tips,ebcs_state,ebcs_pstate,ebsa_id,"
				+ "ebsa_address,ebcl_backcase "
				+ "from EmBodyCheck a "
				+ "inner join EmBodyCheckList b on a.embc_id=b.ebcl_embc_id "
				+ "inner join EmBcSetup c on b.ebcl_hospital=c.ebcs_id "
				+ "left join EmBcSetupAddress d on b.ebcl_area=d.ebsa_id "
				+ "where 1=1";

		try {
			list = db.find(sql, EmBodyCheckModel.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 导入体检号时获取体检信息
	public List<EmBodyCheckModel> getBcList(String idcard, String bcid) {
		List<EmBodyCheckModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select embc_id,ebcl_id,a.CID,GID,embc_bcid,coba_company,coba_client,embc_name,embc_idcard,c.ebcs_hospital,d.ebsa_address,"
				+ "ISNULL(ebcl_plandate,ebcl_bookdate)ebcl_plandate,"
				+ "case ebcl_state when 0 then '已取消' when 1 then '待确认'  when 2 then '待申报'"
				+ " when 3 then '体检中' when 4 then '已体检' when 5 then '已体检' when 6 then '已报销' when 7 then '福利退回'"
				+ " when 8 then '报销处理' when 9 then '重新预约' when 10 then '预约中' when 11 then '已结算' when 12 then '已签收报告' "
				+ " when 13 then '取消中' when 14 then '已删除' when 15 then '中心退回' end stateName,"
				+ " embc_tapr_id"
				+ " from embodycheck a "
				+ " inner join EmBodyCheckList b on a.embc_id=ebcl_embc_id and ebcl_flag=1"
				+ " inner join embcsetup c on b.ebcl_hospital=c.ebcs_id"
				+ " inner join EmBcSetupAddress d on b.ebcl_area=d.ebsa_id"
				+ " left join cobase e on a.CID=e.cid"
				+ " where (ebcl_state in(3,10) and embc_idcard =?) or ebcl_bcid=?";
		try {
			list = db.find(sql, EmBodyCheckModel.class, null, idcard, bcid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取单位员工信息
	public List<EmbaseModel> getEmBaseInfo(String str) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select emba_id,gid,a.cid,emba_name,emba_sex,emba_email,emba_mobile,emba_pinyin,emba_spell,"
				+ "case emba_marital when '未婚' then '未婚' when '初婚' then '已婚' when '离异' then '已婚' when '丧偶' then '已婚' when '已婚' then '已婚' when '再婚' then '已婚' end emba_marital"
				+ ",emba_state,emba_indate,emba_outdate,"
				+ " DATEDIFF(yy,emba_birth,GETDATE()) emba_age,emba_idcard,coba_company,coba_shortname,"
				+ "coba_client from embase a inner join cobase b on a.cid=b.cid where 1=1 ";
		sql = sql + str;
		try {
			list = db.find(sql, EmbaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取取消信息
	public List<EmBodycheckCancelModel> getEmBodycheckCancelList(String str) {
		List<EmBodycheckCancelModel> list = new ArrayList<EmBodycheckCancelModel>();
		dbconn db = new dbconn();
		String sql = "select * from EmBodycheckCancel where 1=1" + str;
		System.out.println(sql);
		try {
			list = db.find(sql, EmBodycheckCancelModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 导出联系医院名单时查询
	public List<EmBodyCheckModel> getEmBodyCheck(String str) {
		List<EmBodyCheckModel> list = new ArrayList<EmBodyCheckModel>();
		dbconn db = new dbconn();
		String sql = " select a.GID,a.CID,coba_shortname,ebcl_items ebcl_itemsstr,coba_client,coba_clientmanager,embc_name,"
				+ "emba_idcard,emba_sex, isnull(embc_age,'') embc_age,isnull(embc_marry,'')embc_marry,"
				+ "case when b.ebcl_itemgroup >0 then case when isnull(b.ebcl_charge,0)= "
				+ "isnull(gb.ebig_charge,0) then gb.ebig_name else gb.ebig_name +'自选项目' end "
				+ "end ebcl_itemgroup,ebcl_items,ISNULL(CONVERT(varchar(50), b.ebcl_discount), '') "
				+ "AS ebcl_discount,dbo.GetBcItype(ebcl_items,EBCL_HOSPITAL) sex,ebcl_plandate,"
				+ "dateadd(d,60,ebcl_plandate) ebcl_enddate,case when CHARINDEX('罗湖',d.ebsa_address)>0 "
				+ "then '罗湖' when CHARINDEX('福田',d.ebsa_address)>0 then case when  "
				+ "CHARINDEX('车公庙',d.ebsa_address)>0 then '福田(车公庙)' when CHARINDEX('市民中心',d.ebsa_address)>0 "
				+ "then '福田(市民中心)'  when CHARINDEX('莲花北',d.ebsa_address)>0 then '福田(莲花北)' "
				+ "when CHARINDEX('财富大厦',d.ebsa_address)>0 then '福田(中心区)' else '福田' end when "
				+ "CHARINDEX('南山', d.ebsa_address)>0 then case when CHARINDEX('美年广场',d.ebsa_address)>0 "
				+ "then '蛇口' when CHARINDEX('富诚大厦',d.ebsa_address)>0 then '南山科技园南区分院' when "
				+ "CHARINDEX('金融中心',d.ebsa_address)>0 then '南山科技园中区分院' else '南山' end else "
				+ "d.ebsa_address end ebsa_address,(isnull(convert(varchar(8000),embc_remark),'''')+"
				+ "ISNULL(CONVERT(VARCHAR(8000),ebcl_remark),'''')) ebcl_remark from  EmBodyCheck a "
				+ " inner join EmBodyCheckList b on a.embc_id=b.ebcl_embc_id left join EmBcItemGroup gb "
				+ "on b.ebcl_itemgroup=gb.ebig_id inner join EmBcSetup c on b.ebcl_hospital=c.ebcs_id "
				+ "left join EmBcSetupAddress d on b.ebcl_area=d.ebsa_id left join cobase co "
				+ "on a.CID=co.cid inner join EmBase eb on a.GID=eb.gid where 1=1 and ebcl_flag=1";
		sql = sql + str + " order by ebcl_state";
		try {
			list = db.find(sql, EmBodyCheckModel.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询操作记录
	public List<embodycheckoperlogModel> getEmBodyCheckLog(int ebcl_id) {
		List<embodycheckoperlogModel> list = new ArrayList<embodycheckoperlogModel>();
		dbconn db = new dbconn();
		String sql = "select * from embodycheckoperlogModel where bclg_ebcl_id="
				+ ebcl_id;
		try {
			list = db.find(sql, embodycheckoperlogModel.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据身份证号查询员工是否已有保健号
	public EmBodyCheckModel isHasEmbcId(String str) {
		EmBodyCheckModel m = new EmBodyCheckModel();
		boolean flag = false;
		String sql = "select top 1 case ebcl_state when 0 then '已取消' when 1 then '待确认' "
				+ " when 2 then '待申报' when 3 then '体检中' when 4 then '已体检' when 5 then '已体检' "
				+ " when 6 then '已报销' when 7 then '已退回' when 8 then '报销处理' when 9 then '重新预约' "
				+ " when 10 then '预约中' when 11 then '已结算' when 12 then '已签收报告' when 13 then "
				+ " '取消中' when 14 then '已删除' end as embc_statebname,* from EmBodyCheck a "
				+ " inner join EmBodyCheckList b on a.embc_id=b.ebcl_embc_id "
				+ " where ebcl_flag=1 " + str + " order by b.ebcl_addtime desc";
		dbconn db = new dbconn();
		System.out.println(sql);
		try {
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				m.setEbcl_id(rs.getInt("ebcl_id"));
				m.setEmbc_tapr_id(rs.getInt("embc_tapr_id"));
				m.setCid(rs.getInt("cid"));
				m.setEbcl_state(rs.getInt("ebcl_state"));
				m.setEmbc_id(rs.getInt("embc_id"));
				m.setEbcl_bcid(rs.getString("ebcl_bcid"));
				m.setEmbc_id(rs.getInt("embc_id"));
				m.setEmbc_statebname(rs.getString("embc_statebname"));
			}
		} catch (Exception e) {
		}
		return m;
	}

	// 检测状态是否一样
	public boolean ifStateSame(String idstr) {
		boolean flag = false;
		String sql = "select count(distinct(ebcl_state)) num from EmBodyCheckList where ebcl_id in("
				+ idstr + ")";
		dbconn db = new dbconn();
		Integer num = 0;
		try {
			ResultSet rs = db.GRS(sql);
			if (rs.next()) {
				num = num + rs.getInt("num");
			}
		} catch (Exception e) {

		}
		if (num == 1) {
			flag = true;
		}
		return flag;
	}

	// 查询状态id
	public Integer getStateId(String idstr) {
		String sql = "select distinct(ebcl_state) ebcl_state from EmBodyCheckList where ebcl_id in("
				+ idstr + ")";
		dbconn db = new dbconn();
		Integer ebcl_state = 0;
		try {
			ResultSet rs = db.GRS(sql);
			if (rs.next()) {
				ebcl_state = rs.getInt("ebcl_state");
			}
		} catch (Exception e) {

		}
		return ebcl_state;
	}

	public EmBcItemGroupModel getEmBcItemGroup(String group_id) {
		EmBcItemGroupModel m = new EmBcItemGroupModel();
		String sql = "select * from EmBcItemGroup where ebig_state=1 and ebig_id="
				+ group_id;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			if (rs.next()) {
				m.setEbig_name(rs.getString("ebig_name"));
			}
		} catch (Exception e) {

		}
		return m;
	}

	public EmBcItemGroupModel getgrouplist(String group_id) {
		EmBcItemGroupModel m = new EmBcItemGroupModel();
		String sql = "select ebit_id,ebig_name,ebit_name"
				+ " from EmbcIGList a"
				+ " inner join EmBcItemGroup b on a.eigl_ebig_id=b.ebig_id"
				+ " inner join EmBodyCheckItem c on a.eigl_ebit_id=c.ebit_id"
				+ " where ebig_id=?";
		dbconn db = new dbconn();
		try {
			m.setList(db.find(sql, EmBodyCheckItemModel.class, null, group_id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}

	public boolean getEmBcCancel(Integer ebcl_id) {
		boolean flag = false;
		String sql = "select * from EmBodycheckCancel where  emca_ebcl_id="
				+ ebcl_id;
		dbconn db = new dbconn();
		try {
			ResultSet rs = db.GRS(sql);
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	// 查询是否是已体检状态
	public Integer isSameChecked(String strid) {
		Integer flag = 0;
		String sql = "select distinct(ebcl_state) from EmBodyCheckList where ebcl_id in("
				+ strid + ")";
		dbconn db = new dbconn();
		Integer k = 0;
		Integer state = null;
		try {
			ResultSet rs = db.GRS(sql);
			if (rs.next()) {
				k = k + 1;
				state = rs.getInt("ebcl_state");
			}
		} catch (Exception e) {

		}
		if (k > 1) {
			flag = k;
		} else {
			if (!state.equals(4)) {
				flag = -1;
			}
		}
		return flag;
	}

	// 已婚女性体检套餐替换未婚女性体检套餐
	public List<EmBodyCheckItemModel> getItemID(String item) {
		List<EmBodyCheckItemModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select ISNULL(b.ebit_id,a.ebit_id)ebit_id,ISNULL(b.ebit_name,a.ebit_name)ebit_name"
				+ " from EmBodyCheckItem a"
				+ " left join EmBodyCheckItem b on a.ebit_hospital=b.ebit_hospital and a.ebit_sex=b.ebit_sex and a.ebit_marry=0 and b.ebit_marry=1 and a.ebit_package=b.ebit_package and b.ebit_package=1 and b.ebit_state=1"
				+ " and substring(a.ebit_name,1,CHARINDEX('-',a.ebit_name)-1)=substring(b.ebit_name,1,CHARINDEX('-',b.ebit_name)-1)"
				+ " where a.ebit_id in ("
				+ item
				+ ")"
				+ " order by a.ebit_package desc,ebit_name";
		try {
			list = db.find(sql, EmBodyCheckItemModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工是否有完成体检信息
	public List<EmBodyCheckModel> getInureBC(String idcard) {
		List<EmBodyCheckModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select a.cid,a.gid,embc_shortname,embc_name,embc_idcard,ebcs_hospital,ebsa_address,ISNULL(ebcl_plandate,ebcl_bookdate)bookdate"
				+ ",case ebcl_state when 0 then '已取消' when 1 then '待确认'  when 2 then '待申报'"
				+ " when 3 then '体检中' when 4 then '已体检' when 5 then '已体检' when 6 then '已报销' when 7 then '福利退回'"
				+ " when 8 then '报销处理' when 9 then '重新预约' when 10 then '预约中' when 11 then '已结算' when 12 then '已签收报告'"
				+ " when 13 then '取消中' when 14 then '已删除' when 15 then '中心退回' end embc_statebname"
				+ " from embodycheck a inner join EmBodyCheckList b on a.embc_id=b.ebcl_embc_id and ebcl_flag=1"
				+ " inner join embcsetup c on b.ebcl_hospital=c.ebcs_id"
				+ " inner join EmBcSetupAddress d on b.ebcl_area=d.ebsa_id"
				+ " where ebcl_state in (1,2,3,9,10,13) and embc_idcard=?";

		try {
			list = db.find(sql, EmBodyCheckModel.class, null, idcard);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
