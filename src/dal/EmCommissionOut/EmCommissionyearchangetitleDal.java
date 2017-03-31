package dal.EmCommissionOut;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import Conn.dbconn;
import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionYearChangemModel;
import Model.EmCommissionyearchangetitleModel;
import Model.EmbaseModel;
import Util.DateStringChange;

public class EmCommissionyearchangetitleDal {

	/**
	 * @param args
	 */
	dbconn db = new dbconn();
	
	

	
	// 根据查询语句获取员工列表数据集
	private ResultSet getembase(String  str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select b.gid,b.emba_name,c.cid,c.coba_shortname,emba_wt,coab_name,city,coba_client");
		sql.append(",ecyc_bc_base,ecyc_bc_cp,ecyc_bc_op,ecyc_bc_total");	
		sql.append(",ecyc_house_pay,ecyc_house_cp,ecyc_house_op,ecyc_house_hj");	
		sql.append(",ecyc_yl_base");
		sql.append(",ecyc_jl_base");
		sql.append(",ecyc_sye_base");
		sql.append(",ecyc_syu_base");
		sql.append(" from EmCommissionYearChange  a left join (select gid,emba_name,cid,emba_wt from embase) b on a.gid=b.gid");
		sql.append(" left join (select cid,coba_shortname,coba_client  from CoBase )c on c.CID=b.cid ");
		sql.append(" left join (select cabc_id,coab_name,city from CoAgencyBaseCityRel_view )d on d.cabc_id=a.ecyc_cityid where 1=1");
		sql.append(str);
		sql.append(" order by coba_client,b.gid ");
		System.out.println(sql);
		rs = db.GRS(sql.toString());
		return rs;
	}
	
	//检查是否有未完结的年调
	public String[] checkdata(ArrayList<EmCommissionyearchangetitleModel> alist) throws Exception
	{
		String[] message = new String[2];
		StringBuilder str=new StringBuilder();
		StringBuilder sql = new StringBuilder();
		sql.append("select city,coab_name from  EmCommissionyearchangetitle a left join CoAgencyBaseCityRel_view b on a.coab_id=b.cabc_id where a.ecyt_state<3 and   a.coab_id in ");
		sql.append("(");
		for (EmCommissionyearchangetitleModel model : alist) {
			
			sql.append(model.getCoab_id());
			sql.append(",");
			//str.append(str);
		}
		sql.setLength(sql.length()-1);
		sql.append(")");
		ResultSet rs = db.GRS(sql.toString());
		 
		while (rs.next()) {
			str.append(rs.getString("city"));
			str.append("/");
			str.append(rs.getString("coab_name"));
			str.append(",");
			}
		
		if (str.length()>0)
		{
			str.setLength(str.length()-1);
			str.append("尚有未处理完的年调,请勿重复添加。");
			message[0]="1";
			message[1]=str.toString();
		}
		else 
		{
			
			message[0]="0";
			message[1]="通过";
		}
		return message;
	}

	// 年调数据添加

	public int changetitleadd(
			ArrayList<EmCommissionyearchangetitleModel> modellist, int taba_id) {
		// EmCommissionyearchangetitleModel model =new
		// EmCommissionyearchangetitleModel();
		String[] message = new String[5];
		int i = 0;
		try {
			for (EmCommissionyearchangetitleModel model : modellist) {

				CallableStatement c = db
						.getcall("EmCommissionyearchangetitleADD_p_zmj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				c.setString(1, model.getEcyt_monthsart());
				c.setString(2, model.getEcyt_monthend());
				c.setInt(3, model.getEcyt_state());
				c.setInt(4, model.getCoab_id());
				c.setInt(5, model.getEcyt_single());
				c.setString(6, model.getEcyt_addname());
				c.setInt(7, model.getEcyt_ylao());
				c.setInt(8, model.getEcyt_yliao());
				c.setInt(9, model.getEcyt_gshang());
				c.setInt(10, model.getEcyt_sye());
				c.setInt(11, model.getEcyt_gjj());
				c.setInt(12, model.getEcyt_bcgjj());
				c.setInt(13, model.getEcyt_syu());
				c.setString(14, model.getEcyt_remark());
				c.setInt(15, taba_id);
				c.setInt(16, model.getEcyt_lzcj());
				c.setString(17, model.getEcyt_lzstatedate());
				c.setString(18, model.getEcyt_lzenddate());
				c.setString(19,model.getEcyt_startdate());
				c.registerOutParameter(20, java.sql.Types.INTEGER);
				c.execute();
				i = i + c.getInt(20);
			}
			if (i > 0) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 2;
		}

	}
	
	//更新年调时间
	
	public int changetitleupdate(EmCommissionyearchangetitleModel model) throws SQLException
	{
//		try{
		CallableStatement c = db
				.getcall("EmCommissionyearchangetitleupdate_p_zmj(?,?,?,?,?,?,?" +
						",?,?,?)");
		
		c.setString(1,DateStringChange.DatetoSting(model.getEcyt_ylaotime(), "yyyy-MM-dd"));
		c.setString(2,DateStringChange.DatetoSting(model.getEcyt_syetime(),"yyyy-MM-dd"));
		c.setString(3,DateStringChange.DatetoSting(model.getEcyt_gshangtime(),"yyyy-MM-dd"));
		c.setString(4,DateStringChange.DatetoSting(model.getEcyt_yliaotime(),"yyyy-MM-dd"));
		c.setString(5,DateStringChange.DatetoSting(model.getEcyt_syutime(),"yyyy-MM-dd"));
		c.setString(6,DateStringChange.DatetoSting( model.getEcyt_gjjtime(),"yyyy-MM-dd"));
		c.setString(7,DateStringChange.DatetoSting(model.getEcyt_bcgjjtime(),"yyyy-MM-dd"));
		c.setString(8,model.getEcyt_modname());
		c.setInt(9, model.getEcyt_id());
		c.registerOutParameter(10, java.sql.Types.INTEGER);
		c.execute();
		 
		return c.getInt(10);
//		}
//		catch(Exception e)
//		{
//			return -1;
//		} 
	}
	
	
	//根据年调总表id获取明细表
	
	

	// 获取服务城市、委托机构主键ID
	public CoAgencyBaseCityRelViewModel getcabc_id(String city, String wtjgname) {
		CoAgencyBaseCityRelViewModel coagBaseList = new CoAgencyBaseCityRelViewModel();

		String sql = "select cabc_id,coab_id,coab_name,coab_namespell,coab_city,coab_setuptype,cabc_ifdefault from CoAgencyBaseCityRel_view where city=? and coab_name=?";

		PreparedStatement psmt = db.getpre(sql);
		try {
			psmt.setString(1, city);
			psmt.setString(2, wtjgname);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {

				coagBaseList.setCabc_id(rs.getInt("cabc_id"));
				coagBaseList.setCoab_id(rs.getInt("coab_id"));
				coagBaseList.setCoab_name(rs.getString("coab_name"));
				coagBaseList.setCoab_namespell(rs.getString("coab_namespell"));
				coagBaseList.setCoab_city(rs.getString("coab_city"));
				coagBaseList.setCoab_setuptype(rs.getString("coab_setuptype"));
//				coagBaseList.setCoab_hz(rs.getString("coab_hz"));
//				coagBaseList.setCoab_client(rs.getString("coab_client"));
				coagBaseList.setCabc_ifdefault(rs.getInt("cabc_ifdefault"));

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return coagBaseList;
	}
	
	
	// 获取主键ID查询城市，机构
		public CoAgencyBaseCityRelViewModel getcityname(int cabc_id) {
			CoAgencyBaseCityRelViewModel coagBaseList = new CoAgencyBaseCityRelViewModel();

			String sql = "select city,cabc_id,coab_id,coab_name,coab_namespell,coab_city,coab_setuptype,cabc_ifdefault from CoAgencyBaseCityRel_view where cabc_id=? ";

			PreparedStatement psmt = db.getpre(sql);
			try {
				psmt.setInt(1, cabc_id);
			//	psmt.setString(2, wtjgname);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {

					coagBaseList.setCabc_id(rs.getInt("cabc_id"));
					coagBaseList.setCoab_id(rs.getInt("coab_id"));
					coagBaseList.setCoab_name(rs.getString("coab_name"));
					coagBaseList.setCoab_namespell(rs.getString("coab_namespell"));
					coagBaseList.setCoab_city(rs.getString("city"));
					coagBaseList.setCoab_setuptype(rs.getString("coab_setuptype"));
//					coagBaseList.setCoab_hz(rs.getString("coab_hz"));
//					coagBaseList.setCoab_client(rs.getString("coab_client"));
					coagBaseList.setCabc_ifdefault(rs.getInt("cabc_ifdefault"));

				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return coagBaseList;
		}

	// 确认年调任务单插入TaskBatch表信息
	public int addTaskBatch(String username, String remark) {
		try {
			CallableStatement c = db.getcall("EmComm_ConfirmToTb_p_zmj(?,?,?)");
			c.setString(1, username);
			c.setString(2, remark);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 批量确认年调任务单插入TaskBatchRelBusiness表信息
	public int addTaskBatchRelBusiness(int tbrb_taba_id, int esda_id) {
		try {
			CallableStatement c = db
					.getcall("EmComm_ConfirmToTbrb_p_zmj(?,?,?)");
			c.setInt(1, tbrb_taba_id);
			c.setInt(2, esda_id);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 更新TaskBatch表流程ID
	public boolean upTaskBatchTaprId(int tapri_id, int dataid) {
		String sql = "update TaskBatch  set taba_tapr_id=? where taba_id=?";
		PreparedStatement psmt = db.getpre(sql);
		boolean b = false;
		try {
			psmt.setInt(1, tapri_id);
			psmt.setInt(2, dataid);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			b = false;
		}
		return b;
	}
	
	//修改为已提交状态
	
	// 更新TaskBatch表流程ID
	public boolean rebacknt(int ecyc_state, int ecyc_id) {
		String sql =  "update EmCommissionYearChange  set ecyc_state=?,ecyc_gxstate=0 where ecyc_id=?";
		PreparedStatement psmt = db.getpre(sql);
		boolean b = true;
		try {
			psmt.setInt(1, ecyc_state);
			psmt.setInt(2, ecyc_id);
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			b = false;
		}
		return b;
	}
	
	//撤销为可修改的状态
	public boolean updateemcomm(String ecyc_idcount,int state) throws Exception
	{
		
		String sql = "update EmCommissionYearChange  set ecyc_state="+state+" where ecyc_id in "+ecyc_idcount+" ";
		
		ResultSet rs = db.GRS(sql.toString());
		
		
	 
		return true;
		 
	}

	
	//撤销为可修改的状态
	public boolean unlockedit(int ecyc_id,int state)
	{
		
		String sql = "update EmCommissionYearChange  set ecyc_state=? where ecyc_id=?";
		PreparedStatement psmt = db.getpre(sql);
		boolean b = false;
		try {
			//非确认变确认
			if (state==0)
			{
			
			psmt.setInt(1, 1);
			psmt.setInt(2, ecyc_id);
		
			}
			//确认变取消确认
			else if (state==1)
			{
				psmt.setInt(1, 0);
				psmt.setInt(2, ecyc_id);
			}
			else
			{
				psmt.setInt(1,state);
				psmt.setInt(2,ecyc_id);
			}
			psmt.execute();
			b = true;
		} catch (SQLException e) {
			b = false;
		}
		return b;
	}

	// 查询年调总表列表--任务单
	public List<EmCommissionyearchangetitleModel> getemcommtlist(Object... objs) {
		List<EmCommissionyearchangetitleModel> emcommlist = new ArrayList<EmCommissionyearchangetitleModel>();
		dbconn db = new dbconn();
	
		String sql = "select a.ecyt_id,coab_id, case ecyt_state when 0 then '未审核' when 1 then '已审核' end as ecyt_statestr,"
				+"ecyt_ylao,ecyt_yliao,ecyt_gshang,ecyt_sye,ecyt_gjj,ecyt_bcgjj,ecyt_syu,ecyt_bcgjj,"
				+ "case ecyt_single when 1 then '独立户' when 2 then '大户' when 3 then '独立户及大户' end as ecyt_singlestr,"
				+ "case ecyt_ylao when 1 then '采集' when 0 then '不采集' end as ecyt_ylaostr,"
				+ "case ecyt_yliao when 1 then '采集' when 0 then '不采集' end as ecyt_yliaostr,"
				+ "case ecyt_gshang when 1 then '采集' when 0 then '不采集' end as ecyt_gshangstr,"
				+ "case ecyt_sye when 1 then '采集' when 0 then '不采集' end as ecyt_syestr,"
				+ "case ecyt_gjj when 1 then '采集' when 0 then '不采集' end as ecyt_gjjstr,"
				+ "case ecyt_bcgjj when 1 then '采集' when 0 then '不采集' end as ecyt_bcgjjstr,"
				+ "case ecyt_syu when 1 then '采集' when 0 then '不采集' end as ecyt_syustr, "
				+ "case ecyt_bcgjj when 1 then '采集' when 0 then '不采集' end as ecyt_ylaostr,"
				+ "convert(nvarchar(10),ecyt_monthsart,120) ecyt_monthsart,	convert(nvarchar(10),ecyt_monthend,120) ecyt_monthend,"
				+ "ecyt_addtime,ecyt_addname,ecyt_remark, b.city,b.coab_name jgname from EmCommissionyearchangetitle a "
				+ "inner join (select city,cabc_id,coab_name from CoAgencyBaseCityRel_view) b on a.coab_id=b.cabc_id "
				+ " where 1=1 and ecyt_state=? and ecyt_id in (select tbrb_data_id from TaskBatchRelBusiness where tbrb_taba_id = ?) ";
		System.out.println(sql);
		try {
			emcommlist = db.find(sql, EmCommissionyearchangetitleModel.class,
					dbconn.parseSmap(EmCommissionyearchangetitleModel.class),
					objs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emcommlist;
	}
	
	
	// 查询年调总表列表--全部数据
		public List<EmCommissionyearchangetitleModel> getemcommtlistsh(Object...objs) {
			List<EmCommissionyearchangetitleModel> emcommlist = new ArrayList<EmCommissionyearchangetitleModel>();
			dbconn db = new dbconn();
			
			String sql = "select a.ecyt_id,coab_id,ecyt_modtime,ecyt_modname,case ecyt_state when 0 then '未审核' when 1 then '已审核' when 3 then '已更新' end as ecyt_statestr,"
					+"ecyt_ylao,ecyt_yliao,ecyt_gshang,ecyt_sye,ecyt_gjj,ecyt_bcgjj,ecyt_syu,ecyt_bcgjj,"
					+ "case ecyt_single when 1 then '独立户' when 2 then '大户' when 3 then '独立户及大户' end as ecyt_singlestr,"
					+ "case ecyt_ylao when 1 then '采集' when 0 then '不采集' end as ecyt_ylaostr,"
					+ "case ecyt_yliao when 1 then '采集' when 0 then '不采集' end as ecyt_yliaostr,"
					+ "case ecyt_gshang when 1 then '采集' when 0 then '不采集' end as ecyt_gshangstr,"
					+ "case ecyt_sye when 1 then '采集' when 0 then '不采集' end as ecyt_syestr,"
					+ "case ecyt_gjj when 1 then '采集' when 0 then '不采集' end as ecyt_gjjstr,"
					+ "case ecyt_bcgjj when 1 then '采集' when 0 then '不采集' end as ecyt_bcgjjstr,"
					+ "case ecyt_syu when 1 then '采集' when 0 then '不采集' end as ecyt_syustr, "
					+ "case ecyt_bcgjj when 1 then '采集' when 0 then '不采集' end as ecyt_ylaostr,"
					+ "convert(nvarchar(10),ecyt_monthsart,120) ecyt_monthsart,	convert(nvarchar(10),ecyt_monthend,120) ecyt_monthend,convert(nvarchar(10),ecyt_startdate,120) ecyt_startdate,"
					+ "ecyt_addtime,ecyt_addname,ecyt_remark, cb.city,cb.coab_name jgname,taba_tapr_id from EmCommissionyearchangetitle a "
					+ "inner join (select city,cabc_id,coab_name from CoAgencyBaseCityRel_view) cb on a.coab_id=cb.cabc_id "
					+"inner join (select * from TaskBatchRelBusiness b inner join TaskBatch c on c.taba_id=b.tbrb_taba_id  where c.taba_class='nt') b"
					+ " on b.tbrb_data_id=a.ecyt_id "
					+" where 1=1 or ecyt_single<>?  "
					+" order by ecyt_addname,ecyt_addtime desc,ecyt_state  desc ";
			System.out.println(sql);
			try {
				emcommlist = db.find(sql, EmCommissionyearchangetitleModel.class,
						dbconn.parseSmap(EmCommissionyearchangetitleModel.class),objs
						);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return emcommlist;
		}
		
	
	
	
	// 查询年调总表列表--后道导出
		public List<EmCommissionyearchangetitleModel> getemcommtlistall(Object... objs) {
			List<EmCommissionyearchangetitleModel> emcommlist = new ArrayList<EmCommissionyearchangetitleModel>();
			dbconn db = new dbconn();
			String sql = "select ecyt_ylaotime,coab_id, ecyt_yliaotime,ecyt_gshangtime, ecyt_syetime,ecyt_gjjtime,ecyt_bcgjjtime,ecyt_syutime,ecyt_modtime,ecyt_modname," +
					" ecyt_id, case ecyt_state when 0 then '未审核' when 1 then '已审核' end as ecyt_statestr,"
					+"ecyt_ylao,ecyt_yliao,ecyt_gshang,ecyt_sye,ecyt_gjj,ecyt_bcgjj,ecyt_syu,ecyt_bcgjj,"
					+ "case ecyt_single when 1 then '独立户' when 2 then '大户' when 3 then '独立户及大户' end as ecyt_singlestr,"
					+ "case ecyt_ylao when 1 then '采集' when 0 then '不采集' end as ecyt_ylaostr,"
					+ "case ecyt_yliao when 1 then '采集' when 0 then '不采集' end as ecyt_yliaostr,"
					+ "case ecyt_gshang when 1 then '采集' when 0 then '不采集' end as ecyt_gshangstr,"
					+ "case ecyt_sye when 1 then '采集' when 0 then '不采集' end as ecyt_syestr,"
					+ "case ecyt_gjj when 1 then '采集' when 0 then '不采集' end as ecyt_gjjstr,"
					+ "case ecyt_bcgjj when 1 then '采集' when 0 then '不采集' end as ecyt_bcgjjstr,"
					+ "case ecyt_syu when 1 then '采集' when 0 then '不采集' end as ecyt_syustr, "
					+ "case ecyt_bcgjj when 1 then '采集' when 0 then '不采集' end as ecyt_ylaostr,"
					+ "convert(nvarchar(10),ecyt_monthsart,120) ecyt_monthsart,	convert(nvarchar(10),ecyt_monthend,120) ecyt_monthend,"
					+ "ecyt_addtime,ecyt_addname,ecyt_remark, b.city,b.coab_name jgname from EmCommissionyearchangetitle a "
					+ "inner join (select city,cabc_id,coab_name  from CoAgencyBaseCityRel_view) b on a.coab_id=b.cabc_id "
					+ " where 1=1 and ecyt_state=?  and city=? and  coab_name=? ";
			System.out.println(sql);
			try {
				emcommlist = db.find(sql, EmCommissionyearchangetitleModel.class,
						dbconn.parseSmap(EmCommissionyearchangetitleModel.class),
						objs);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return emcommlist;
		}

		
		// 查询年调总表列表--后道导出
				public List<EmCommissionyearchangetitleModel> getemcommtlistforid(Object... objs) {
					List<EmCommissionyearchangetitleModel> emcommlist = new ArrayList<EmCommissionyearchangetitleModel>();
					dbconn db = new dbconn();
					String sql = "select ecyt_ylaotime,coab_id, ecyt_yliaotime,ecyt_gshangtime, ecyt_syetime,ecyt_gjjtime,ecyt_bcgjjtime,ecyt_syutime,ecyt_modtime,ecyt_modname," +
							" ecyt_id, case ecyt_state when 0 then '未审核' when 1 then '已审核' end as ecyt_statestr,"
							+"ecyt_ylao,ecyt_yliao,ecyt_gshang,ecyt_sye,ecyt_gjj,ecyt_bcgjj,ecyt_syu,ecyt_bcgjj,"
							+ "case ecyt_single when 1 then '独立户' when 2 then '大户' when 3 then '独立户及大户' end as ecyt_singlestr,"
							+ "case ecyt_ylao when 1 then '采集' when 0 then '不采集' end as ecyt_ylaostr,"
							+ "case ecyt_yliao when 1 then '采集' when 0 then '不采集' end as ecyt_yliaostr,"
							+ "case ecyt_gshang when 1 then '采集' when 0 then '不采集' end as ecyt_gshangstr,"
							+ "case ecyt_sye when 1 then '采集' when 0 then '不采集' end as ecyt_syestr,"
							+ "case ecyt_gjj when 1 then '采集' when 0 then '不采集' end as ecyt_gjjstr,"
							+ "case ecyt_bcgjj when 1 then '采集' when 0 then '不采集' end as ecyt_bcgjjstr,"
							+ "case ecyt_syu when 1 then '采集' when 0 then '不采集' end as ecyt_syustr, "
							+ "case ecyt_bcgjj when 1 then '采集' when 0 then '不采集' end as ecyt_ylaostr,"
							+ "convert(nvarchar(10),ecyt_monthsart,120) ecyt_monthsart,	convert(nvarchar(10),ecyt_monthend,120) ecyt_monthend,"
							+ "ecyt_addtime,ecyt_addname,ecyt_remark, b.city,b.coab_name jgname from EmCommissionyearchangetitle a "
							+ "inner join (select city,cabc_id,coab_name  from CoAgencyBaseCityRel_view) b on a.coab_id=b.cabc_id "
							+ " where 1=1  and ecyt_id=? ";
					System.out.println(sql);
					try {
						emcommlist = db.find(sql, EmCommissionyearchangetitleModel.class,
								dbconn.parseSmap(EmCommissionyearchangetitleModel.class),
								objs);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					return emcommlist;
				}

		
	

	// 年调数据审核
	public int adutingemcommlist(String idcountstr,String username) throws SQLException {
//		String sql="update EmCommissionyearchangetitle set ecyt_state=1, ecyt_audtingname='"+username+"'," +
//				"ecyt_audtingtime='"+DateStringChange.Datestringnow("yyyy-MM-dd HH:mm:ss")+"' where  ecyt_id in ("+idcountstr+")";
//		System.out.println(sql);
//		boolean  b = false;
//		if ((db.execQuery(sql.toString())>0))
//				{
//					b=true;
//				}
//		return b;
		
		try {
			CallableStatement c = db
					.getcall("EmCommissionyearchangetitle_Up_p_zmj(?,?,?)");
			c.setString(1, idcountstr);
			c.setString(2, username);
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	
	}
	
	//检查是否进行下一步
	public int checktasktonext(int d_id,int state)
	{
		int i=0;
		String sql = "select COUNT(*) as count from EmCommissionyearchangetitle where 1=1 and ecyt_state="+state+"  and ecyt_id in (select tbrb_data_id from TaskBatchRelBusiness where tbrb_taba_id ="+d_id+")";
		System.out.println(sql);
		try {
			ResultSet rs = db.GRS(sql.toString());
			while (rs.next()) {
			i=rs.getInt("count");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return i;
	}

		
	// 获取年调员工列表--任务单
	public List<EmCommissionYearChangemModel> getembaList(Object... objs) {
		List<EmCommissionYearChangemModel> embaList = new ArrayList<EmCommissionYearChangemModel>();
		dbconn db = new dbconn();
	
		String sql = "select a.ecyt_id,ecyc_id,b.gid,b.emba_name,b.emba_idcard,c.cid,c.coba_shortname,coba_company,emba_wt,coab_name,coab_name,city,coba_client,"
				+"ecyc_state,case ecyc_state when 0 then '未录入' when 1 then '已录入' when 2 then '已采集' " +
				"when 3 then '已导出' when 4 then '已更新' when 5 then '已完成' end  as ecyc_statestr"
				+",ecyc_sb_base,ecyc_bc_base,ecyc_bc_cp,ecyc_bc_op,ecyc_bc_total"
				+ ",ecyc_house_base,ecyc_house_cp,ecyc_house_op,ecyc_house_hj"
				+ ",ecyc_yl_base,ecyc_jl_base,ecyc_gs_base,ecyc_sye_base,ecyc_syu_base,"
				 +" isnull(ecyc_ycohouse_base,0) ecyc_ycohouse_base,isnull(ecyc_yemhouse_base,0) ecyc_yemhouse_base,isnull(ecyc_yhouseop,0) ecyc_yhouseop," +
				 "isnull(ecyc_yhousecp,0) ecyc_yhousecp "
				+ " from EmCommissionYearChange  a inner join (select gid,emba_name,cid,emba_wt,emba_idcard from embase) b on a.gid=b.gid"
				+ " inner join (select cid,coba_shortname,coba_client,coba_company  from CoBase where coba_client=?)c on c.CID=b.cid"
				+ " inner join (select city,cabc_id,coab_name from CoAgencyBaseCityRel_view )d on d.cabc_id=a.ecyc_cityid "
				+" inner join (select * from  EmCommissionyearchangetitle aa inner join TaskBatchRelBusiness bb  on aa.ecyt_id=bb.tbrb_data_id where   tbrb_taba_id=?) e"
				+" on a.ecyt_id=e.ecyt_id"
				+ " where 1=1 ";
		
		for (int i = 0; i < objs.length; i++) {
			System.out.println(objs[i]);
		}
		System.out.println(sql);
		
		System.out.println(new Date());
		
		try {
			embaList = db.find(sql, EmCommissionYearChangemModel.class,
					dbconn.parseSmap(EmCommissionYearChangemModel.class),
					objs);
			System.out.println(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return embaList;
	}

	// 获取年调员工列表--后道
		public List<EmCommissionYearChangemModel> getembaListhd(Object... objs) {
			List<EmCommissionYearChangemModel> embaList = new ArrayList<EmCommissionYearChangemModel>();
			dbconn db = new dbconn();
			String sql = "select a.ecyt_id,ecyc_id,b.gid,b.emba_name,b.emba_idcard,c.cid,c.coba_shortname,c.coba_company,emba_wt,coab_name,coab_name,city,coba_client,"
					+"ecyc_state,case ecyc_state when 0 then '未录入' when 1 then '已录入' when 2 then '已采集' " +
				"when 3 then '已导出' when 4 then '已更新' when 5 then '已完成'  end as ecyc_statestr"
					+",ecyc_sb_base,ecyc_bc_base,ecyc_bc_cp,ecyc_bc_op,ecyc_bc_total"
					+ ",ecyc_house_base,ecyc_house_cp,ecyc_house_op,ecyc_house_hj"
					+ ",ecyc_yl_base,ecyc_jl_base,ecyc_gs_base,ecyc_sye_base,ecyc_syu_base "
					+",ecyc_yl_date,ecyc_sye_date,ecyc_gs_date,ecyc_jl_date,ecyc_syu_date,ecyc_house_date,ecyc_bc_date,ecyc_remark"
					+ " from EmCommissionYearChange  a left join (select gid,emba_name,cid,emba_wt,emba_idcard from embase) b on a.gid=b.gid"
					+ " left join (select cid,coba_shortname,coba_client,coba_company  from CoBase )c on c.CID=b.cid"
					+ " left join (select city,cabc_id,coab_name from CoAgencyBaseCityRel_view )d on d.cabc_id=a.ecyc_cityid "
					+ " where 1=1 and ecyc_state=? and  ecyt_id=?";
			System.out.println(sql);
			
			
			
			try {
				embaList = db.find(sql, EmCommissionYearChangemModel.class,
						dbconn.parseSmap(EmCommissionYearChangemModel.class),
						objs);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			return embaList;
		}
		
		// 获取年调员工列表--后道
				public List<EmCommissionYearChangemModel> getembaListhd(String strwhere) {
					List<EmCommissionYearChangemModel> embaList = new ArrayList<EmCommissionYearChangemModel>();
					dbconn db = new dbconn();
					String sql = "select a.ecyt_id,ecyc_id,b.gid,b.emba_name,b.emba_idcard,c.cid,c.coba_shortname,c.coba_company,emba_wt,coab_name,coab_name,city,coba_client,"
							+"ecyc_state,case ecyc_state when 0 then '未录入' when 1 then '已录入' when 2 then '已采集' " +
						"when 3 then '已导出' when 4 then '已更新' when 5 then '已完成'  end as ecyc_statestr"
							+",ecyc_sb_base,ecyc_bc_base,ecyc_bc_cp,ecyc_bc_op,ecyc_bc_total"
							+ ",ecyc_house_base,ecyc_house_cp,ecyc_house_op,ecyc_house_hj"
							+ ",ecyc_yl_base,ecyc_jl_base,ecyc_gs_base,ecyc_sye_base,ecyc_syu_base "
							+",ecyc_yl_date,ecyc_sye_date,ecyc_gs_date,ecyc_jl_date,ecyc_syu_date,ecyc_house_date,ecyc_bc_date,ecyc_remark"
							+ " from EmCommissionYearChange  a left join (select gid,emba_name,cid,emba_wt,emba_idcard from embase) b on a.gid=b.gid"
							+ " left join (select cid,coba_shortname,coba_client,coba_company  from CoBase )c on c.CID=b.cid"
							+ " left join (select city,cabc_id,coab_name from CoAgencyBaseCityRel_view )d on d.cabc_id=a.ecyc_cityid "
							+ " where 1=1   "+strwhere;
					System.out.println(sql);
					
					
					
					try {
						embaList = db.find(sql, EmCommissionYearChangemModel.class,
								dbconn.parseSmap(EmCommissionYearChangemModel.class),
								null);
						
					} catch (Exception e) {
						e.printStackTrace();
					}

					return embaList;
				}
		
		
		// 获取年调员工列表--后道
				public List<EmCommissionYearChangemModel> getembaListhdzd() {
					List<EmCommissionYearChangemModel> embaList = new ArrayList<EmCommissionYearChangemModel>();
					dbconn db = new dbconn();
					String sql = "select a.ecyt_id,ecyc_id,b.gid,b.emba_name,b.emba_idcard,c.cid,c.coba_shortname,c.coba_company,emba_wt,coab_name,city,coba_client,"
							+"ecyc_state,case ecyc_state when 0 then '未录入' when 1 then '已录入' when 2 then '已采集' " +
						"when 3 then '已导出' when 4 then '已更新' when 5 then '已完成'  end as ecyc_statestr"
							+",ecyc_sb_base,ecyc_bc_base,ecyc_bc_cp,ecyc_bc_op,ecyc_bc_total"
							+ ",ecyc_house_base,ecyc_house_cp,ecyc_house_op,ecyc_house_hj"
							+ ",ecyc_yl_base,ecyc_jl_base,ecyc_gs_base,ecyc_sye_base,ecyc_syu_base "
							+",ecyc_yl_date,ecyc_sye_date,ecyc_gs_date,ecyc_jl_date,ecyc_syu_date,ecyc_house_date,ecyc_bc_date,ecyc_remark,coab_id"
							+ " from EmCommissionYearChange  a "  
							+" inner JOIN  (select coab_id,ecyt_id from EmCommissionyearchangetitle where ecyt_startdate<=getdate())  et ON a.ecyt_id=et.ecyt_id "
							+" left join (select gid,emba_name,cid,emba_wt,emba_idcard from embase) b on a.gid=b.gid"
							+ " left join (select cid,coba_shortname,coba_client,coba_company  from CoBase )c on c.CID=b.cid"
							+ " left join (select city,cabc_id,coab_name from CoAgencyBaseCityRel_view )d on d.cabc_id=a.ecyc_cityid "
							+ " where 1=1 and ecyc_state>=2 and  ecyc_gxstate!=5 ";
					System.out.println(sql);
					
					
					
					try {
						embaList = db.find(sql, EmCommissionYearChangemModel.class,
								dbconn.parseSmap(EmCommissionYearChangemModel.class)
								);
						
					} catch (Exception e) {
						e.printStackTrace();
					}

					return embaList;
				}
		
	// 年调数据调整
	private int changetitleedit() {
		return 0;
	}
	// 年调数据删除
	private int changtitledele() {
		return 0;
	}
	// 年调数据查询
	private ArrayList<EmCommissionyearchangetitleModel> getchangetitlelist() {

		return null;
	}
}
