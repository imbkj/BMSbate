package dal.EmFinanceManage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.GatherInfoModel;
import Util.UserInfo;
/**
 * 
 * @author 苏宏远
 *
 */
public class GatherInfoNewDal {

	// 获取到cobase的信息
	public List<CoBaseModel> getCoinfoListssNew(String sqlstr) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		String sql="SELECT distinct(a.cid) cid,sum(cfss_Receivable) cfss_Receivable," +
				"coba_company,coba_shortname,coba_client,cfss_cfso_id," +
				"coba_ufclass,isnull(coba_ufid,'')coba_ufid,ownmonth,cfss_remark," +
				"case cfss_yystate when 0 then '未录' else '已录' end outstate,cfss_type, " +
				"CASE cfss_fpstate WHEN 0 THEN '未开票' ELSE '已开票'  end cfss_fpstate , "+
				" CONVERT(varchar(10),cf.cfss_addtime,120) "+
				" FROM cobase a inner join CoFinanceSortAccountssNew cf  on a.cid=cf.cid " +
				
				" where cfss_cfso_id is not null "+sqlstr;
		sql=sql+" group by a.cid,coba_company,coba_shortname,coba_client," +
				"cfss_cfso_id,coba_ufclass,cfss_yystate,cfss_fpstate,cfss_type,coba_ufid,ownmonth,cfss_remark ,CONVERT(varchar(10),cf.cfss_addtime,120)" +
				
				"order by CONVERT(varchar(10),cf.cfss_addtime,120)  desc, ownmonth";
		System.out.println(sql);
		try {
			dbconn db = new dbconn();
			PreparedStatement pstmt = db.getpre(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoBaseModel m = new CoBaseModel();
				m.setCid(rs.getInt("cid"));
				m.setCoba_company(rs.getString("coba_company"));
				m.setCoba_client(rs.getString("coba_client"));
				m.setCoba_ufclass(rs.getString("coba_ufclass"));
				m.setCoba_ufid(rs.getString("coba_ufid"));
				m.setCoba_shortname(rs.getString("coba_shortname"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setOutstate(rs.getString("outstate"));
				m.setCfss_cfso_id(rs.getString("cfss_cfso_id"));
				m.setCfss_fpstate(rs.getString("cfss_fpstate"));
				m.setCfss_type(rs.getString("cfss_type"));
				System.out.print("total="+rs.getBigDecimal("cfss_Receivable"));
				m.setTotal(rs.getBigDecimal("cfss_Receivable"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//查询明细
	public List<CoFinanceCollectModel> getCollectListssNew(int ownmonth,int cid,String cfss_cfso_id) {
		List<CoFinanceCollectModel> list=new ArrayList<CoFinanceCollectModel>();
		String sql="select CONVERT(varchar(10),cfss_addtime,120) cfss_addtime," +"* from CoFinanceSortAccountssNew a " +
				"where cfss_cfso_id is not null and ownmonth="+ownmonth+" and cid="+cid
				+" and cfss_cfso_id='"+cfss_cfso_id;
		sql=sql+"'  order by a.cfss_addtime desc";
		System.out.println(sql);
		try {
			dbconn db = new dbconn();
			PreparedStatement pstmt = db.getpre(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CoFinanceCollectModel m = new CoFinanceCollectModel();
				m.setCfss_id(rs.getInt("cfss_id"));
				m.setCfsa_cpac_name(rs.getString("cfss_cpac_name"));
				m.setCfsa_PaidIn(rs.getBigDecimal("cfss_Receivable"));
				m.setCfta_addtime(rs.getString("cfss_addtime"));
				m.setCfta_addname(rs.getString("cfss_addname"));
				m.setCfmb_remark(rs.getString("cfss_remark"));
				m.setCfss_cfso_id(rs.getString("cfss_cfso_id"));
				
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return list;
	}
	
	
	// 生成账单
		public void insertCollectListssNew(String cfss_cfso_id) {
			try {
				dbconn conn = new dbconn();
				CallableStatement c = conn
						.getcall("CoFinanceSortAccountssNew_Add_shy(?)");
				c.setString(1, cfss_cfso_id);
				 
				c.execute();
				 
			} catch (SQLException e) {
				e.printStackTrace();
				 
			}
		}
    // 更新新旧账单表开票状态
		public int upCfss_fpstate(String cfss_id,int cfss_fpstate){
			dbconn conn = new dbconn();
			try {
				CallableStatement c = conn
					.getcall("CoFinanceSortAccountssNew_Up_Cfss_fpstate_shy(?,?,?)");
				c.setString(1, cfss_id);
				c.setInt(2, cfss_fpstate);
				c.registerOutParameter(3, java.sql.Types.INTEGER);
				c.execute();
				return c.getInt(3);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}

}
