package dal.EmCommissionOut;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmCommissionOutPayDifModel;
/**
 * 
 * @author 苏宏远
 * 2016-05-26
 */
public class EmCommissionOutPayDifDal {
private dbconn conn = new dbconn();
 //初始化按月份有差异的数据
 public void addEmCommissionOutPayDif(String ownmon,String ecop_cabc) throws Exception, SQLException{
	 CallableStatement c= conn.getcall("EmCommissionOutPayDif_Add_shy(?,?)");
	 c.setInt(1, Integer.parseInt(ownmon));
	 c.setInt(2, Integer.parseInt(ecop_cabc));
	 c.execute();
 }
//删除按月份有差异的数据
 public void deleteEmCommissionOutPayDif(String gid,String ecod_remark) throws Exception, SQLException{
     String sql="delete EmCommissionOutPayDif where 1=1";
     if(ecod_remark==null&&ecod_remark.equals("")){
    	 sql=sql+" and gid="+gid;
    	 conn.execQuery(sql);
     }else{
    	 
     }
 }
 
 //按月份，委托机构关联查找有差异的数据
 public List<EmCommissionOutPayDifModel> getEmCommissionOutPayDifList(String ownmon,String ecop_cabc,String coab_name) throws SQLException{
	 List<EmCommissionOutPayDifModel> list =new ArrayList<EmCommissionOutPayDifModel>();
	 String sql ="select ecod_id,(c.city)city,(c.coab_name)coab_name,(a.ownmonth) ownmonth,(a.gid) gid,a.cid,ecod_cabc_id,ecod_name,emba_idcard ecod_idcard,ecod_yl,ecod_jl"
      +",ecod_gs,ecod_syu,ecod_sye,ecod_house,ecod_file,ecod_fuwu,ecod_other,ecod_total,ecod_addtime"
	  +",ecod_addname,ecod_fstate,ecod_sstate,ecod_remark,ecod_pmonth,ecod_premark,coba_company,ecod_yl_st,ecod_jl_st,ecod_gs_st,ecod_syu_st,ecod_sye_st,ecod_house_st,ecod_file_st,ecod_fuwu_st,ecod_other_st from EmCommissionOutPayDif a "
	  + " left join EmCommissionOutPay b on a.ecod_cabc_id=b.ecop_cabc_id and a.gid=b.gid and a.ownmonth=b.ownmonth left join CoAgencyBaseCityRel_view c on c.cabc_id=b.ecop_cabc_id left join embase d on d.gid=a.gid left join cobase e on e.cid=d.cid "
	  + "where ecop_ecou_addtype='正常' ";
	  if(ownmon!=null && !ownmon.equals("")){
		  sql=sql+" and a.ownmonth ="+ownmon;
	  }
	  if(ecop_cabc!=null && !ecop_cabc.equals("")){
		  sql=sql+" and ecod_cabc_id ="+ecop_cabc;
	  }

	try {
		ResultSet rs=conn.GRS(sql);
		while(rs.next()){
			EmCommissionOutPayDifModel d=new EmCommissionOutPayDifModel();
			d.setEcod_id(rs.getString("ecod_id"));
			d.setCoab_name(rs.getString("coab_name"));
			d.setCid(rs.getString("cid"));
			d.setGid(rs.getString("gid"));
			d.setEcod_addname(rs.getString("ecod_addname"));
			d.setEcod_addtime(rs.getString("ecod_addtime"));
			d.setEcod_cabc_id(rs.getString("ecod_cabc_id"));
			d.setEcod_file(rs.getString("ecod_file"));
			d.setEcod_fstate(rs.getString("ecod_fstate"));
			d.setEcod_fuwu(rs.getString("ecod_fuwu"));
			d.setEcod_gs(rs.getString("ecod_gs"));
			d.setEcod_house(rs.getString("ecod_house"));
			d.setEcod_idcard(rs.getString("ecod_idcard"));
			d.setEcod_jl(rs.getString("ecod_jl"));
			d.setEcod_name(rs.getString("ecod_name"));
			d.setEcod_other(rs.getString("ecod_other"));
			d.setEcod_pmonth(rs.getString("ecod_pmonth"));
			d.setEcod_remark(rs.getString("ecod_remark"));
			d.setEcod_sstate(rs.getString("ecod_sstate"));
			d.setEcod_sye(rs.getString("ecod_sye"));
			d.setEcod_syu(rs.getString("ecod_syu"));
			d.setEcod_total(rs.getString("ecod_total"));
			d.setEcod_yl(rs.getString("ecod_yl"));
			d.setOwnmonth(rs.getString("ownmonth"));
			d.setCity(rs.getString("city"));
			d.setEcod_premark(rs.getString("ecod_premark"));
			d.setCoba_company(rs.getString("coba_company"));
			
			d.setEcod_fuwu_st(rs.getString("ecod_fuwu_st"));
			d.setEcod_gs_st(rs.getString("ecod_gs_st"));
			d.setEcod_house_st(rs.getString("ecod_house_st"));
			d.setEcod_jl_st(rs.getString("ecod_jl_st"));
			d.setEcod_sye_st(rs.getString("ecod_sye_st"));
			d.setEcod_syu_st(rs.getString("ecod_syu_st"));
			d.setEcod_yl_st(rs.getString("ecod_yl_st"));
			d.setEcod_file_st(rs.getString("ecod_file_st"));
			d.setEcod_other(rs.getString("ecod_other"));
			list.add(d);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		conn.Close();
	}
	return list;
 }
 //委托对帐未办反馈更新
 public Integer upEmCommissionOutPayDif(String ownmon,String ecop_cabc ,String ecod_remark,String yl,String sye,String gs,
		 String syu,String jl,String house,String other,String fuwu,String file){
	 CallableStatement c = conn.getcall("EmCommissionOutPayDif_Upd_shy(?,?,?,?,?,?,?,?,?,?,?,?)");
	 try {
		
		c.setInt(1, Integer.parseInt(ownmon));
		c.setInt(2, Integer.parseInt(ecop_cabc));
		c.setString(3, ecod_remark);
		c.setString(4, yl);
		c.setString(5, sye);
		c.setString(6, gs);
		c.setString(7, syu);
		c.setString(8, jl);
		c.setString(9, house);
		c.setString(10, other);
		c.setString(11, fuwu);
		c.setString(12, file);
		c.execute();
		return 1;
	} catch (NumberFormatException | SQLException e) {
		e.printStackTrace();
		System.out.println("错误信息：" + e.getMessage());
		return 0;
	}
 }
 
//委托对帐未办反馈添加
public Integer NewAddEmCommissionOutPayDif11(String ownmon,String ecop_cabc ,String cid){
	 CallableStatement c = conn.getcall("EmCommissionOutPayDif_Add_New_shy(?,?,?)");
	 try {
		c.setInt(1, Integer.parseInt(ownmon));
		c.setInt(2, Integer.parseInt(ecop_cabc));
		c.setInt(3, Integer.parseInt(cid));
		c.execute();
		return 1;
	} catch (NumberFormatException | SQLException e) {
		e.printStackTrace();
		System.out.println("错误信息：" + e.getMessage());
		return 0;
	}
}
}
