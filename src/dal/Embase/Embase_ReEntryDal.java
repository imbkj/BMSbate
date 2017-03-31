package dal.Embase;

import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmArchiveDatumModel;
import Model.EmBaseCompactChangeModel;
import Model.EmComInsuranceChangeModel;
import Model.EmCommissionOutChangeModel;
import Model.EmHouseChangeModel;
import Model.EmSheBaoChangeModel;
import Model.EmbaseModel;
import Util.UserInfo;

public class Embase_ReEntryDal {
	
	//查询员工信息
	public List<EmbaseModel> getEmbaseList(String str)
	{
		List<EmbaseModel> list=new ArrayList<EmbaseModel>();
		dbconn db=new dbconn();
		String sql="select emba_id,gid,a.cid,emba_type,emba_name,emba_englishname,emba_spell," +
				"emba_pinyin,emba_sex,emba_idcard,emba_idcardclass,emba_hjadd,emba_native,emba_hjtype," +
				"emba_nationality,emba_folk,convert(varchar(10),emba_birth,120) as emba_birth,emba_marital," +
				"emba_phone,emba_mobile,emba_address,emba_email,emba_state,emba_wt,coba_shortname,coba_client," +
				"coba_company," +
				"convert(varchar(10),emba_outdate,120) as emba_outdate from EmBase a,CoBase b where a.cid=b.CID"+str;
		try{
			list = db
				.find(sql, EmbaseModel.class, dbconn.parseSmap(
					EmbaseModel.class));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	//查询员工社保停缴信息
	public List<EmSheBaoChangeModel> getSheBaoInfo(String str)
	{
		List<EmSheBaoChangeModel> list=new ArrayList<EmSheBaoChangeModel>();
		String sql="select id,gid,cid,ownmonth,emsc_company,emsc_shortname,emsc_name,emsc_computerid,isnull(b.symr_readstate,-1) readstate," +
				"emsc_idcard,emsc_hj,emsc_change,convert(varchar(2),emsc_ifdeclare,120) as emsc_ifdeclare,emsc_addname,emsc_tapr_id," +
				"convert(varchar(10),emsc_addtime,120) as emsc_addtime from EmShebaoChange a left join " +
				"(select smwr_tid, case when syme_log_id="+UserInfo.getUserid()+" then 2 when symr_log_id="+UserInfo.getUserid()+
				" then symr_readstate end symr_readstate from View_Message where EXISTS(select " +
				"syme_id from (select smwr_tid,MAX(syme_id)syme_id from View_Message where " +
				"syme_state=1 and (symr_log_id= "+UserInfo.getUserid()+" or syme_log_id= "+UserInfo.getUserid()+") and " +
				"smwr_table='EmShebaoChange' group by smwr_tid)msg where " +
				"View_Message.syme_id=msg.syme_id)) b on a.ID=b.smwr_tid " +
				"where emsc_change='停交' and emsc_content='离职' "+str;
		dbconn db=new dbconn();
		try{
			list = db
				.find(sql, EmSheBaoChangeModel.class, dbconn.parseSmap(
						EmSheBaoChangeModel.class));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	//查询员工公积金停缴信息
	public List<EmHouseChangeModel> getEmhouseInfo(String str)
	{
		List<EmHouseChangeModel> list=new ArrayList<EmHouseChangeModel>();
		String sql="select emhc_id,gid,cid,ownmonth,emhc_startmonth,emhc_companyid,emhc_excompany,isnull(b.symr_readstate,-1) readstate," +
				"emhc_excompany,emhc_company,emhc_shortname,emhc_name,emhc_idcard,emhc_idcardclass," +
				"emhc_hj,emhc_computerid,emhc_houseid,emhc_single,emhc_ifdeclare,emhc_addname," +
				"convert(varchar(10),emhc_addtime,120) as emhc_addtime,emhc_tapr_id,emhc_change" +
				" from EmHouseChange a left join (select smwr_tid, case when syme_log_id="+UserInfo.getUserid()+
				"then 2 when symr_log_id="+UserInfo.getUserid()+" then symr_readstate end symr_readstate from View_Message " +
				"where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from " +
				"View_Message where syme_state=1 and (symr_log_id= "+UserInfo.getUserid()+" or syme_log_id= "+UserInfo.getUserid()+") and " +
				"smwr_table='EmHouseChange' group by smwr_tid) msg where View_Message.syme_id=msg.syme_id)) b " +
				"on a.emhc_id=b.smwr_tid where emhc_change='停交'"+str;
		dbconn db=new dbconn();
		try{
			list = db
				.find(sql, EmHouseChangeModel.class, dbconn.parseSmap(
						EmHouseChangeModel.class));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	//查询员工档案调出信息
	public List<EmArchiveDatumModel> getEmArchiveDatumInfo(String str)
	{
		List<EmArchiveDatumModel> list=new ArrayList<EmArchiveDatumModel>();
		String sql="select eada_id,cid,gid,ownmonth,eada_name,eada_filetype,eada_idcard," +
				"eada_tapr_id,convert(varchar(2),eada_final,120) as eada_final,eada_addname," +
				"convert(varchar(10),eada_addtime,120) as eada_addtime,isnull(b.symr_readstate,-1) readstate " +
				"from EmArchiveDatum a left join (select smwr_tid, case when syme_log_id="+UserInfo.getUserid()+
				"then 2 when symr_log_id="+UserInfo.getUserid()+" then symr_readstate end symr_readstate from View_Message " +
				"where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from " +
				"View_Message where syme_state=1 and (symr_log_id= "+UserInfo.getUserid()+" or syme_log_id= "+UserInfo.getUserid()+") and " +
				"smwr_table='EmArchiveDatum' group by smwr_tid) msg where View_Message.syme_id=msg.syme_id)) b " +
				"on a.eada_id=b.smwr_tid  where eada_type=5 "+str;//eada_type=5表示调出；eada_final=1表示待处理
		dbconn db=new dbconn();
		try{
			list = db
				.find(sql, EmArchiveDatumModel.class, dbconn.parseSmap(
						EmArchiveDatumModel.class));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	//查询商保停缴数据
	public List<EmComInsuranceChangeModel> getEmComInsuranceChangeInfo(String str)
	{
		List<EmComInsuranceChangeModel> list=new ArrayList<EmComInsuranceChangeModel>();
		String sql="select ecic_id,ecic_inid,gid,cid,ecic_insurant,ecic_idcard,ecic_sex,isnull(b.symr_readstate,-1) readstate," +
				"ecic_work_city,ecic_castsort,ecic_buy_count,ecic_state,ecic_state2,ecic_addname," +
				"convert(varchar(10),ecic_addtime,120) as ecic_addtime,ecic_tapr_id" +
				" from EmComInsuranceChange a left join (select smwr_tid, case when syme_log_id="+UserInfo.getUserid()+
				"then 2 when symr_log_id="+UserInfo.getUserid()+" then symr_readstate end symr_readstate from View_Message " +
				"where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from " +
				"View_Message where syme_state=1 and (symr_log_id= "+UserInfo.getUserid()+" or syme_log_id= "+UserInfo.getUserid()+") and " +
				"smwr_table='EmComInsuranceChange' group by smwr_tid) msg where View_Message.syme_id=msg.syme_id)) b " +
				"on a.ecic_id=b.smwr_tid where ecic_state=2"+str;//ecic_state=2表示停缴
		dbconn db=new dbconn();
		try{
			list = db
			.find(sql, EmComInsuranceChangeModel.class, dbconn.parseSmap(
					EmComInsuranceChangeModel.class));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	//查询委托外地停缴数据
	public List<EmCommissionOutChangeModel> getEmCommissionOutChangeInfo(String str)
	{
		List<EmCommissionOutChangeModel> list=new ArrayList<EmCommissionOutChangeModel>();
		String sql="select ecoc_id,gid,cid,ecoc_ecou_id,ecoc_soin_id,ecoc_ecos_id,ecoc_addtype," +
				"ecoc_type,ecoc_idcard,ecoc_state,ecoc_addname,isnull(b.symr_readstate,-1) readstate," +
				"convert(varchar(10),ecoc_addtime,120) as addtime,ecoc_tapr_id " +
				"from EmCommissionOutChange a left join (select smwr_tid, case when syme_log_id="+UserInfo.getUserid()+
				"then 2 when symr_log_id="+UserInfo.getUserid()+" then symr_readstate end symr_readstate from View_Message " +
				"where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from " +
				"View_Message where syme_state=1 and (symr_log_id= "+UserInfo.getUserid()+" or syme_log_id= "+UserInfo.getUserid()+") and " +
				"smwr_table='EmCommissionOutChange' group by smwr_tid) msg where View_Message.syme_id=msg.syme_id)) b " +
				"on a.ecoc_id=b.smwr_tid   where ecoc_addtype='离职'"+str;//ecic_state=2表示停缴

		dbconn db=new dbconn();
		try{
			list = db
			.find(sql, EmCommissionOutChangeModel.class, dbconn.parseSmap(
					EmCommissionOutChangeModel.class));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	//查询劳动合同终止数据
	public List<EmBaseCompactChangeModel> getEmBaseCompactChangeInfo(String str)
	{
		List<EmBaseCompactChangeModel> list=new ArrayList<EmBaseCompactChangeModel>();
		String sql="select ebcc_id,ebcc_ebco_id,gid,cid,ebcc_work_place,ebcc_state,ebcc_change,isnull(b.symr_readstate,-1) readstate," +
				"ebcc_addname,convert(varchar(10),ebcc_addtime,120) as ebcc_addtime,ebcc_tapr_id " +
				"from EmBaseCompactChange a left join (select smwr_tid, case when syme_log_id="+UserInfo.getUserid()+
				"then 2 when symr_log_id="+UserInfo.getUserid()+" then symr_readstate end symr_readstate from View_Message " +
				"where EXISTS(select syme_id from (select smwr_tid,MAX(syme_id)syme_id from " +
				"View_Message where syme_state=1 and (symr_log_id= "+UserInfo.getUserid()+" or syme_log_id= "+UserInfo.getUserid()+") and " +
				"smwr_table='EmBaseCompactChange' group by smwr_tid) msg where View_Message.syme_id=msg.syme_id)) b " +
				"on a.ebcc_id=b.smwr_tid " +
				" where (ebcc_change='终止' or ebcc_change='解约') "+str;//ecic_state=2表示停缴

		dbconn db=new dbconn();
		try{
			list = db
			.find(sql, EmBaseCompactChangeModel.class, dbconn.parseSmap(
					EmBaseCompactChangeModel.class));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return list;
	}
}
