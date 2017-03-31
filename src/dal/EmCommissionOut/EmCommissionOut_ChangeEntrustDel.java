package dal.EmCommissionOut;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutChangeEntrustModel;
import Model.EmbaseGDModel;

public class EmCommissionOut_ChangeEntrustDel {
	// 获取委托服务项目费用列表数据
	public List<EmCommissionOutChangeEntrustModel> getEmCommissionOut_ChangeEntrustList(String str) throws SQLException{
		List<EmCommissionOutChangeEntrustModel> list = new ArrayList<>();
		dbconn db =new dbconn();
		String sql = "select top 10000 a.cid,a.gid,a.ecyc_cityid,a.ecyc_sb_base ,a.ecyc_house_cp ,a.ecyc_house_base ,b.coba_company ,b.coba_client, c.emba_name ,c.emba_idcard , e.city ,e.cabc_id, " +
				"case ecyc_state when 0 then '未采集' when  1 then '未提交' when 2 then '已提交项目部' when 3 then '已提交机构' when 4 then '已更新' " +
				"when  5 then '已完成'  end ecyc_state,aa.ecyt_startdate from EmCommissionYearChange a " +
				"	INNER JOIN EmCommissionyearchangetitle aa ON a.ecyt_id=aa.ecyt_id  " +
				 " inner join CoBase b on a.cid=b.CID inner join EmBase c on " +
				"a.gid=c.gid left join CoAgencyBaseCityRel_view e on a.ecyc_cityid= e.cabc_id  where 1=1" + str
				+ " ORDER BY aa.ecyt_startdate DESC  "  ;
		     System.out.println(sql);  
		    try {
		     /**try {
					ResultSet rs= db.GRS(sql);
					while(rs.next()){
						EmCommissionOutChangeEntrustModel e = new EmCommissionOutChangeEntrustModel();
						e.setCabc_id(rs.getInt("cabc_id"));
						e.setCid(rs.getInt("cid"));
						e.setCoba_company(rs.getString("coba_company"));
						e.setEcyc_cityid(rs.getInt("ecyc_cityid"));
						e.setEcyc_house_base(rs.getBigDecimal("ecyc_house_base"));
						e.setEcyc_house_cp(rs.getString("ecyc_house_cp"));
						e.setEcyc_yl_base(rs.getBigDecimal("ecyc_house_base"));
						e.setEmba_idcard(rs.getString("emba_idcard"));
						e.setEmba_name(rs.getString("emba_name"));
						e.setGid(rs.getInt("gid"));
						e.setEcyc_state(rs.getString("ecyc_state"));
						e.setCity(rs.getString("city"));
						list.add(e);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					db.Close();
				}*/
		    	 
		    	list = db.find(sql, EmCommissionOutChangeEntrustModel.class, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		
		return list;
	}
	// 获取委托地列表
	public List<EmCommissionOutChangeEntrustModel> getCityList() {
		List<EmCommissionOutChangeEntrustModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql ="select distinct  city  from EmCommissionYearChange a left join CoBase b on a.cid=b.CID left join EmBase c on a.gid=c.gid left join CoAgencyBaseCityRel_view e on a.ecyc_cityid= e.cabc_id  where 1=1 order by city";
		try {
			System.out.println(sql);
			list = db.find(sql, EmCommissionOutChangeEntrustModel.class,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// 获取客服列表
	public List<EmCommissionOutChangeEntrustModel> getCobaClientList(){
		List<EmCommissionOutChangeEntrustModel> list = new ArrayList<EmCommissionOutChangeEntrustModel>();
		dbconn db = new dbconn();
		String sql="select distinct  b.coba_client from EmCommissionYearChange a left join CoBase b on a.cid=b.CID left join EmBase c on a.gid=c.gid left join CoAgencyBaseCityRel_view e on a.ecyc_cityid= e.cabc_id  where 1=1  order by coba_client ";
         try {
			list = db.find(sql, EmCommissionOutChangeEntrustModel.class,
						null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return list;
	}
}
