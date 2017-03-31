package dal.EmCommissionOut;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmCommissionOutimportModel;
import Model.EmbaseModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.wtoutFeeModel;
import R.RS;
import Util.DateStringChange;
import Util.UserInfo;

public class EmCommissionOutimportDal {
	//查询服务费名称列数据
	public List<String> getfeetitlelist() throws SQLException{
		List<String> list = new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct wtot_feetitle FROM View_wtservicestandard where 1=1 ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("wtot_feetitle"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//查询标准名列数据
	public List<String> gettitlelist() throws SQLException{
		List<String> list = new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct wtss_title FROM View_wtservicestandard where 1=1 ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("wtss_title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//查询城市列数据
	public List<String> getcitylist() throws SQLException{
		List<String> list = new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct city FROM View_wtservicestandard where 1=1 ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("city"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//查询机构名列列数据
	public List<String> getnamelist() throws SQLException{
		List<String> list = new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct coab_name FROM View_wtservicestandard where 1=1 ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("coab_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
//查询wtoutFee服务列数据
public List<wtoutFeeModel> getWtoutFeeList(String strsql) throws SQLException{
	List<wtoutFeeModel> list = new ListModelList<wtoutFeeModel>();
	dbconn con = new  dbconn();
	StringBuilder sql = new StringBuilder();
	sql.append("select cid, Wtot_feeid,cid,wtot_feetitle,wtss_title ,city,coab_name,wtot_fee FROM View_wtservicestandard where 1=1 and wtot_state=3 ");
	sql.append(strsql);
	try {
		System.out.println(sql.toString());
		ResultSet rs= con.GRS(sql.toString());
		while(rs.next()){
			wtoutFeeModel mm =new wtoutFeeModel();
			mm.setCid(rs.getInt("cid"));
			mm.setWtot_feeid(rs.getInt("Wtot_feeid"));
			mm.setCid(rs.getInt("cid"));
			mm.setWtot_feetitle(rs.getString("wtot_feetitle"));
			mm.setWtss_title(rs.getString("wtss_title"));
			mm.setCity(rs.getString("city"));
			mm.setCoab_name(rs.getString("coab_name"));
			mm.setWtot_fee(rs.getBigDecimal("wtot_fee"));
			list.add(mm);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		con.Close();
	}
	return list;
}



//查询当地标准名列数据
public List<String> getsoinlist() throws SQLException{
		List<String> list = new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct a.soin_title  FROM SocialInsurance a,CoAgencyBaseCityRel_view b where a.soin_cabc_id=b.cabc_id  ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("soin_title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//查询当地城市列数据
	public List<String> getsoincitylist() throws SQLException{
		List<String> list = new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct b.city  FROM SocialInsurance a,CoAgencyBaseCityRel_view b where a.soin_cabc_id=b.cabc_id ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("city"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//查询当地机构名列列数据
	public List<String> getsoinnamelist() throws SQLException{
		List<String> list = new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct b.coab_name FROM SocialInsurance a,CoAgencyBaseCityRel_view b where a.soin_cabc_id=b.cabc_id  ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				list.add(rs.getString("coab_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}

//查询当地标地服务SocialInsurance
public List<SocialInsuranceAlgorithmViewModel> getSocialInsurance(String strsql) throws SQLException{
	List<SocialInsuranceAlgorithmViewModel> list = new ListModelList<SocialInsuranceAlgorithmViewModel>();
	dbconn con = new  dbconn();
	StringBuilder sql = new StringBuilder();
	sql.append("select a.soin_id ,a.soin_title ,b.city ,b.coab_name ,'详细' as remark FROM SocialInsurance a,CoAgencyBaseCityRel_view b where a.soin_cabc_id=b.cabc_id and a.soin_state=1 ");
	sql.append(strsql);
	try {
		System.out.println(sql.toString());
		ResultSet rs= con.GRS(sql.toString());
		while(rs.next()){
			SocialInsuranceAlgorithmViewModel mm =new SocialInsuranceAlgorithmViewModel();
			mm.setSoin_id(rs.getInt("soin_id"));
			mm.setSoin_title(rs.getString("soin_title"));
			mm.setCity(rs.getString("city"));
			mm.setCoab_name(rs.getString("coab_name"));
			mm.setSial_sb_remark(rs.getString("remark"));
			list.add(mm);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		con.Close();
	}
	return list;
}
   //根据导入的execl 员工编号查询是否存在该员工信息
   public EmbaseModel findEmbaseInfo(String gid){
	   dbconn con = new  dbconn();
	   EmbaseModel em = new EmbaseModel();
	   String sql = " select * from  EmBase where gid="+gid;
	   try {
			System.out.println(sql);
			ResultSet rs= con.GRS(sql);
			while(rs.next()){
				em.setEmba_name(rs.getString("emba_name"));
				em.setEmba_idcard(rs.getString("emba_idcard"));
			}
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return em;
   }
   
   //批量导入委托外地批量新增表 excel 文件内容
   public int importEntrustInfo(EmCommissionOutimportModel m){
	   dbconn con = new  dbconn();
	   int row = 0;
 
	   String username = UserInfo.getUsername();
	   
	   //判断是否导入过
	   String strsqlexis="select count(*) count from EmCommissionOutimport where gid="+m.getGid();
	   ResultSet rs;
	try {
		rs = con.GRS(strsqlexis.toString());
		while(rs.next()){
			row=rs.getInt("count");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		//rs.next();
		
	   if (row==0)
	   {
	   String strsql=" INSERT INTO EmCommissionOutimport "
           +"(gid,ecou_soin_id,ecou_ecos_id,ecou_idcard,ecou_name,ecou_mobile,ecou_filestate,ecou_com_phone "
           +",ecou_compact_f,ecou_compact_l,ecou_salary,ecou_sb_base,ecou_house_base,ecou_state,ecou_client,ecou_addname "
           +",ecou_addtime,ecou_remark,ecou_yldate,ecou_yliaodate,ecou_dbdate,ecou_syudate,ecou_gsdate,ecou_sydate "
           +",ecou_zfdate,ecou_zfcp,ecou_zfop,ecou_bczfdate,ecou_bczfcp,ecou_bczfop,ecou_cbjdate,ecou_fwfdate "
           +",ecou_importflie,ecou_filedate)  VALUES "
           +" ('"+ m.getGid()+ "','"+ m.getEcou_soin_id()+ "','"+ m.getEcou_ecos_id()+ "','"+ m.getEcou_idcard()+ "','"+ m.getEcou_name()+ "' "
           +" ,'"+ m.getEcou_mobile()+ "','"+ m.getEcou_filestate()+ "' "
           +" ,'"+ m.getEcou_com_phone()+ "','"+ m.getEcou_compact_f()+ "','"+ m.getEcou_compact_l()+ "' "
           +" ,'"+ m.getEcou_salary()+ "','"+ m.getEcou_sb_base()+ "','"+ m.getEcou_house_base()+ "',1 "
           +" ,'"+ m.getEcou_client()+ "','"+ username+ "',GETDATE() "
           +" ,'"+ m.getEcou_remark()+ "',convert(datetime,'"+ m.getEcou_yldate()+ "',120),convert(datetime,'"+ m.getEcou_yliaodate()+ "',120),convert(datetime,'"+ m.getEcou_dbdate()+ "',120),convert(datetime,'"+ m.getEcou_syudate()+ "',120) "
           +" ,convert(datetime,'"+ m.getEcou_gsdate()+ "',120),convert(datetime,'"+ m.getEcou_sydate()+ "',120),convert(datetime,'"+ m.getEcou_zfdate()+ "',120),'"+ m.getEcou_zfcp()+ "','"+ m.getEcou_zfop()+ "'"
           +" ,convert(datetime,'"+ m.getEcou_bczfdate()+ "',120),'"+ m.getEcou_bczfcp()+ "','"+ m.getEcou_bczfop()+ "' "
           +" ,convert(datetime,'"+ m.getEcou_cbjdate()+ "',120),convert(datetime,'"+ m.getEcou_fwfdate()+ "',120),'"+ m.getEcou_importflie()+ "',convert(datetime,'"+ m.getEcou_filedate()+ "',120)) ";
	   try {
			if(strsql.contains("'null'")){
				strsql=strsql.replaceAll("'null'", "null");
			}
			System.out.println(strsql);
			row = con.execQuery(strsql);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				con.Close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	   }
	   else
	   {
		   row=0;
	   }
	  return row;
   }
   
   //查询excel导入的委托外地信息
   public List<EmCommissionOutimportModel> getEntrustInfo(String strsql) throws SQLException{
   	List<EmCommissionOutimportModel> list = new ListModelList<EmCommissionOutimportModel>();
   	dbconn con = new  dbconn();
   	StringBuilder sql = new StringBuilder();
   	sql.append(" select top 100* from EmCommissionOutimport where 1=1 and ecou_addname='"+UserInfo.getUsername()+"' and ecou_state in (0,1) ");
   	if(strsql==null){
		strsql="";
	}
   	sql.append(strsql);
   	try {
   		System.out.println(sql.toString());
   		ResultSet rs= con.GRS(sql.toString());
   		while(rs.next()){
   			EmCommissionOutimportModel m =new EmCommissionOutimportModel();
   			m.setEcou_addname(rs.getString("ecou_addname"));
   			m.setEcou_addtime(rs.getDate("ecou_addtime"));
   			m.setEcou_bczfcp(rs.getString("ecou_bczfcp"));
   			m.setEcou_bczfdate(rs.getString("ecou_bczfdate"));
   			m.setEcou_bczfop(rs.getString("ecou_bczfop"));
   			m.setEcou_cbjdate(rs.getString("ecou_cbjdate"));
   			m.setEcou_client(rs.getString("ecou_client"));
   			m.setEcou_com_phone(rs.getString("ecou_com_phone"));
   			m.setEcou_compact_f(DateStringChange.Stringformat(rs.getString("ecou_compact_f"),"yyyy-MM-dd"));
   			m.setEcou_compact_l(DateStringChange.Stringformat(rs.getString("ecou_compact_l"),"yyyy-MM-dd"));
   			m.setEcou_dbdate(rs.getString("ecou_dbdate"));
   			m.setEcou_ecos_id(rs.getInt("ecou_ecos_id"));
   			m.setEcou_email(rs.getString("ecou_email"));
   			m.setEcou_filestate(rs.getInt("ecou_filestate"));
   			m.setEcou_fwfdate(rs.getString("ecou_fwfdate"));
   			m.setEcou_gsdate(rs.getString("ecou_gsdate"));
   			m.setEcou_house_base(rs.getBigDecimal("ecou_house_base"));
   			m.setEcou_id(rs.getInt("ecou_id"));
   			m.setEcou_idcard(rs.getString("ecou_idcard"));
   			m.setEcou_importflie(rs.getString("ecou_importflie"));
   			m.setEcou_mobile(rs.getString("ecou_mobile"));
   			m.setEcou_name(rs.getString("ecou_name"));
   			m.setEcou_remark(rs.getString("ecou_remark"));
   			m.setEcou_salary(rs.getBigDecimal("ecou_salary"));
   			m.setEcou_sb_base(rs.getBigDecimal("ecou_sb_base"));
   			m.setEcou_soin_id(rs.getInt("ecou_soin_id"));
   			m.setEcou_state(rs.getInt("ecou_state"));
   			m.setEcou_sydate(rs.getString("ecou_sydate"));
   			m.setEcou_syudate(rs.getString("ecou_syudate"));
   			m.setEcou_title_date(rs.getDate("ecou_title_date"));
   			m.setEcou_yldate(rs.getString("ecou_yldate"));
   			m.setEcou_yliaodate(rs.getString("ecou_yliaodate"));
   			m.setEcou_zfcp(rs.getString("ecou_zfcp"));
   			m.setEcou_zfdate(rs.getString("ecou_zfdate"));
   			m.setEcou_zfop(rs.getString("ecou_zfop"));
   			m.setGid(rs.getInt("gid"));
   			m.setCid(rs.getInt("cid"));
   			list.add(m);
   		}
   	} catch (Exception e) {
   		e.printStackTrace();
   	}finally {
   		con.Close();
   	}
   	return list;
   }
   
   
   
   //获取导入表model
   public EmCommissionOutimportModel getEntrustInfomodel(int ecou_id) throws SQLException{
   	EmCommissionOutimportModel m = new EmCommissionOutimportModel();
   	dbconn con = new  dbconn();
   	StringBuilder sql = new StringBuilder();
   	sql.append(" select * from EmCommissionOutimport where ecou_id ="+ecou_id);
   
   	try {
   		System.out.println(sql.toString());
   		ResultSet rs= con.GRS(sql.toString());
   		while(rs.next()){
   			
   			m.setEcou_addname(rs.getString("ecou_addname"));
   			m.setEcou_addtime(rs.getDate("ecou_addtime"));
   			m.setEcou_bczfcp(rs.getString("ecou_bczfcp"));
   			m.setEcou_bczfdate(rs.getString("ecou_bczfdate"));
   			m.setEcou_bczfop(rs.getString("ecou_bczfop"));
   			m.setEcou_cbjdate(rs.getString("ecou_cbjdate"));
   			m.setEcou_client(rs.getString("ecou_client"));
   			m.setEcou_com_phone(rs.getString("ecou_com_phone"));
   			m.setEcou_compact_f(rs.getString("ecou_compact_f")!=null?rs.getString("ecou_compact_f").substring(0, rs.getString("ecou_compact_f").indexOf(" ")):rs.getString("ecou_compact_f"));
   			m.setEcou_compact_l(rs.getString("ecou_compact_l")!=null?rs.getString("ecou_compact_l").substring(0, rs.getString("ecou_compact_l").indexOf(" ")):rs.getString("ecou_compact_l"));
   			m.setEcou_dbdate(rs.getString("ecou_dbdate"));
   			m.setEcou_ecos_id(rs.getInt("ecou_ecos_id"));
   			m.setEcou_email(rs.getString("ecou_email"));
   			m.setEcou_filestate(rs.getInt("ecou_filestate"));
   			m.setEcou_fwfdate(rs.getString("ecou_fwfdate"));
   			m.setEcou_gsdate(rs.getString("ecou_gsdate"));
   			m.setEcou_house_base(rs.getBigDecimal("ecou_house_base"));
   			m.setEcou_id(rs.getInt("ecou_id"));
   			m.setEcou_idcard(rs.getString("ecou_idcard"));
   			m.setEcou_importflie(rs.getString("ecou_importflie"));
   			m.setEcou_mobile(rs.getString("ecou_mobile"));
   			m.setEcou_name(rs.getString("ecou_name"));
   			m.setEcou_remark(rs.getString("ecou_remark"));
   			m.setEcou_salary(rs.getBigDecimal("ecou_salary"));
   			m.setEcou_sb_base(rs.getBigDecimal("ecou_sb_base"));
   			m.setEcou_soin_id(rs.getInt("ecou_soin_id"));
   			m.setEcou_state(rs.getInt("ecou_state"));
   			m.setEcou_sydate(rs.getString("ecou_sydate"));
   			m.setEcou_syudate(rs.getString("ecou_syudate"));
   			m.setEcou_title_date(rs.getDate("ecou_title_date"));
   			m.setEcou_yldate(rs.getString("ecou_yldate"));
   			m.setEcou_yliaodate(rs.getString("ecou_yliaodate"));
   			m.setEcou_zfcp(rs.getString("ecou_zfcp"));
   			m.setEcou_zfdate(rs.getString("ecou_zfdate"));
   			m.setEcou_zfop(rs.getString("ecou_zfop"));
   			m.setEcou_filedate(rs.getString("ecou_filedate"));
   			m.setGid(rs.getInt("gid"));
   			m.setCid(rs.getInt("cid"));
   			
   		}
   	} catch (Exception e) {
   		e.printStackTrace();
   	}finally {
   		con.Close();
   	}
   	return m;
   }
   
   
   //删除委托外地记录
   public int deleteEntrustInfo(Integer ecou_id){
	   dbconn con = new  dbconn();
		String strsql="DELETE FROM EmCommissionOutimport WHERE  ecou_id='"+ecou_id+"'";
		int a=con.execQuery(strsql);
		return a;
   }
   //查询员工姓名列数据
   public List<String> getecounamelist() throws SQLException{
 		List<String> list = new ArrayList<>();
 		list.add("");
 		dbconn con = new  dbconn();
 		StringBuilder sql = new StringBuilder();
 		sql.append("select distinct ecou_name  FROM EmCommissionOutimport ");
 		try {
 			System.out.println(sql.toString());
 			ResultSet rs= con.GRS(sql.toString());
 			while(rs.next()){
 				list.add(rs.getString("ecou_name"));
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		}finally {
 			con.Close();
 		}
 		return list;
 	}
   
   //核查员工身份信息
   public String[] checkem(int ecou_id )
   { 
	   String[] str = new String[4];
	   str[0]="0";
	   
		dbconn con = new  dbconn();
			 
			String sql="  select  a.gid,b.cid wtcid,c.cid cocid,a.ecou_ecos_id,a.ecou_soin_id,a.ecou_name," +
					"b.wtss_archives,a.ecou_filestate   from EmCommissionOutimport a " +
					"INNER join  View_wtservicestandard b on a.ecou_ecos_id=b.Wtot_feeid INNER JOIN " +
					"embase c on a.gid=c.gid" +
					" where a.ecou_id='"+ecou_id+"'";
			try {
				ResultSet rs= con.GRS(sql.toString());
				//rs.next();
				while(rs.next()){
				
				if(!rs.getString("wtcid").equals(rs.getString("cocid")))
				{
					
							 str[0]="1";
							 str[1]= "服务费代码填写有误！";
							 str[3]= rs.getString("ecou_name");
				}
				if(rs.getString("ecou_filestate").equals("1")&&rs.getString("wtss_archives").equals("不保管"))
				{
					 str[0]="1";
					 str[1]= "该服务标准不能保管档案！";
					 str[3]= rs.getString("ecou_name");
				}
				
					 
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 
			}
			
			return  str;
	   
	  
	   
   }
   
   
 	//查询添加人列数据
 	public List<String> getaddnamelist() throws SQLException{
 			List<String> list = new ArrayList<>();
 			list.add("");
 			dbconn con = new  dbconn();
 			StringBuilder sql = new StringBuilder();
 			sql.append("select distinct ecou_addname  FROM EmCommissionOutimport ");
 			try {
 				System.out.println(sql.toString());
 				ResultSet rs= con.GRS(sql.toString());
 				while(rs.next()){
 					list.add(rs.getString("ecou_addname"));
 				}
 			} catch (Exception e) {
 				e.printStackTrace();
 			}finally {
 				con.Close();
 			}
 			return list;
 		}
   //更新委托外地记录
   public int updateEntrustInfo(EmCommissionOutimportModel mr){
	  dbconn con = new  dbconn();
	  String sql =" UPDATE EmCommissionOutimport SET [ecou_soin_id] = ?,[ecou_ecos_id] = ?,[ecou_idcard] = ?,[ecou_mobile] = ? "
      +" ,[ecou_filestate] = ?,[ecou_com_phone] = ?,[ecou_compact_f] = ?,[ecou_compact_l] = ?,[ecou_salary] = ?,[ecou_sb_base] = ? "
      +" ,[ecou_house_base] = ?,[ecou_client] = ?,[ecou_remark] = ?,[ecou_yldate] = ?,[ecou_yliaodate] = ?,[ecou_dbdate] = ?,[ecou_syudate] = ? "
      +" ,[ecou_gsdate] = ?,[ecou_sydate] = ?,[ecou_zfdate] = ?,[ecou_zfcp] = ?,[ecou_zfop] = ?,[ecou_bczfdate] = ?,[ecou_bczfcp] = ? "
      +" ,[ecou_bczfop] = ?,[ecou_cbjdate] = ?,[ecou_fwfdate] = ?,ecou_state=? WHERE ecou_id= ? ";
	  PreparedStatement p = con.getpre(sql);
	   try {
		   p.setInt(1, mr.getEcou_soin_id());
		   p.setInt(2, mr.getEcou_ecos_id());
	       p.setString(3, mr.getEcou_idcard());
	       p.setString(4, mr.getEcou_mobile());
	       p.setInt(5, mr.getEcou_filestate());
	       p.setString(6, mr.getEcou_com_phone());
	       p.setString(7, mr.getEcou_compact_f());
	       p.setString(8, mr.getEcou_compact_l());
	       p.setBigDecimal(9, mr.getEcou_salary());
	       p.setBigDecimal(10, mr.getEcou_sb_base());
	       p.setBigDecimal(11, mr.getEcou_house_base());
	       p.setString(12, mr.getEcou_client());
	       p.setString(13, mr.getEcou_remark());
	       p.setString(14, mr.getEcou_yldate());
	       p.setString(15, mr.getEcou_yliaodate());
	       p.setString(16, mr.getEcou_dbdate());
	       p.setString(17, mr.getEcou_syudate());
	       p.setString(18, mr.getEcou_gsdate());
	       p.setString(19, mr.getEcou_sydate());
	       p.setString(20, mr.getEcou_zfdate());
	       p.setString(21, mr.getEcou_zfcp());
	       p.setString(22, mr.getEcou_zfop());
	       p.setString(23, mr.getEcou_bczfdate());
	       p.setString(24, mr.getEcou_bczfcp());
	       p.setString(25, mr.getEcou_bczfop());
	       p.setString(26, mr.getEcou_cbjdate());
	       p.setString(27, mr.getEcou_fwfdate());
	       p.setInt(28, mr.getEcou_state());
	       p.setInt(29, mr.getEcou_id());
          int a= p.executeUpdate();
          return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	   
   }
}
