package dal.EmBaseCompact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmbaseContractDetailModel;
import Util.UserInfo;


public class EmbaseContractDetail_ShowDal {
	//获取公司列表
	public List<String> getCompanyList(){
		String sql=" select distinct co.coba_company "
                   +" FROM(select puof_type,puof_tid,puof_addname,puof_addtime,puof_state,puof_editname,puof_edittime,puof_url from  PubOffice   where puof_pute_id=3 AND puof_url LIKE '%EmbaseCompactEnd%') a  "
                   +" INNER JOIN (select  gid,ebcc_id,ebcc_change,ebcc_end_date FROM  EmBaseCompactChange where ebcc_change IN ('终止','解除')  ) b ON a.puof_tid=b.ebcc_id  " 
                   +" INNER JOIN (select gid,cid,emba_name,emba_idcard,emba_state  FROM embase) em on b.gid=em.gid "
                   +" INNER JOIN (select CID,coba_company,coba_client from CoBase ) co on em.cid=co.cid";
		List<String> list =new ArrayList<>();
		list.add("");
		dbconn con = new  dbconn();
		ResultSet rs;
		try {
			rs = con.GRS(sql);
			while(rs.next()){
               list.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//获取员工列表
	public List<String> getNameList(){
			String sql=" select distinct em.emba_name "
	                   +" FROM(select puof_type,puof_tid,puof_addname,puof_addtime,puof_state,puof_editname,puof_edittime,puof_url from  PubOffice   where puof_pute_id=3 AND puof_url LIKE '%EmbaseCompactEnd%') a  "
	                   +" INNER JOIN (select  gid,ebcc_id,ebcc_change,ebcc_end_date FROM  EmBaseCompactChange where ebcc_change IN ('终止','解除')  ) b ON a.puof_tid=b.ebcc_id  " 
	                   +" INNER JOIN (select gid,cid,emba_name,emba_idcard,emba_state  FROM embase) em on b.gid=em.gid "
	                   +" INNER JOIN (select CID,coba_company,coba_client from CoBase ) co on em.cid=co.cid";
			List<String> list =new ArrayList<>();
			list.add("");
			dbconn con = new  dbconn();
			ResultSet rs;
			try {
				rs = con.GRS(sql);
				while(rs.next()){
	               list.add(rs.getString(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
	//获取身份证列表
		public List<String> getIdcardList(){
			String sql=" select distinct em.emba_idcard "
	                   +" FROM(select puof_type,puof_tid,puof_addname,puof_addtime,puof_state,puof_editname,puof_edittime,puof_url from  PubOffice   where puof_pute_id=3 AND puof_url LIKE '%EmbaseCompactEnd%') a  "
	                   +" INNER JOIN (select  gid,ebcc_id,ebcc_change,ebcc_end_date FROM  EmBaseCompactChange where ebcc_change IN ('终止','解除')  ) b ON a.puof_tid=b.ebcc_id  " 
	                   +" INNER JOIN (select gid,cid,emba_name,emba_idcard,emba_state  FROM embase) em on b.gid=em.gid "
	                   +" INNER JOIN (select CID,coba_company,coba_client from CoBase ) co on em.cid=co.cid";
			List<String> list =new ArrayList<>();
			list.add("");
			dbconn con = new  dbconn();
			ResultSet rs;
			try {
				rs = con.GRS(sql);
				while(rs.next()){
	               list.add(rs.getString(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		//获取客服列表
		public List<String> getClientList(){
			String sql=" select distinct co.coba_client "
	                   +" FROM(select puof_type,puof_tid,puof_addname,puof_addtime,puof_state,puof_editname,puof_edittime,puof_url from  PubOffice   where puof_pute_id=3 AND puof_url LIKE '%EmbaseCompactEnd%') a  "
	                   +" INNER JOIN (select  gid,ebcc_id,ebcc_change,ebcc_end_date FROM  EmBaseCompactChange where ebcc_change IN ('终止','解除')  ) b ON a.puof_tid=b.ebcc_id  " 
	                   +" INNER JOIN (select gid,cid,emba_name,emba_idcard,emba_state  FROM embase) em on b.gid=em.gid "
	                   +" INNER JOIN (select CID,coba_company,coba_client from CoBase ) co on em.cid=co.cid";
			List<String> list =new ArrayList<>();
			list.add("");
			dbconn con = new  dbconn();
			ResultSet rs;
			try {
				rs = con.GRS(sql);
				while(rs.next()){
	               list.add(rs.getString(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		//获取添加人列表
		public List<String> getAddnameList(){
			String sql=" select distinct a.puof_addname "
	                   +" FROM(select puof_type,puof_tid,puof_addname,puof_addtime,puof_state,puof_editname,puof_edittime,puof_url from  PubOffice   where puof_pute_id=3 AND puof_url LIKE '%EmbaseCompactEnd%') a  "
	                   +" INNER JOIN (select  gid,ebcc_id,ebcc_change,ebcc_end_date FROM  EmBaseCompactChange where ebcc_change IN ('终止','解除')  ) b ON a.puof_tid=b.ebcc_id  " 
	                   +" INNER JOIN (select gid,cid,emba_name,emba_idcard,emba_state  FROM embase) em on b.gid=em.gid "
	                   +" INNER JOIN (select CID,coba_company,coba_client from CoBase ) co on em.cid=co.cid";
			List<String> list =new ArrayList<>();
			list.add("");
			dbconn con = new  dbconn();
			ResultSet rs;
			try {
				rs = con.GRS(sql);
				while(rs.next()){
	               list.add(rs.getString(1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
    //获取员工合同详情
	public List<EmbaseContractDetailModel> getEmbaseContractDetail(String strsql) throws SQLException{
		dbconn con = new  dbconn();
		List<EmbaseContractDetailModel> list = new ListModelList<EmbaseContractDetailModel>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select co.CID ,b.gid , co.coba_company ,em.emba_name ,"
                    +" em.emba_idcard ,co.coba_client , b.ebcc_end_date , a.puof_addname  ,"
                    +" a.puof_addtime , ebcc_change ,a.puof_state ,puof_editname , puof_edittime , puof_url,a.puof_id " 
                    +" FROM(select puof_id,puof_type,puof_tid,puof_addname,puof_addtime,puof_state,puof_editname,puof_edittime,puof_url from  PubOffice   where puof_pute_id=3 AND puof_url LIKE '%EmbaseCompactEnd%') a "
                    +" INNER JOIN (select  gid,ebcc_id,ebcc_change,ebcc_end_date FROM  EmBaseCompactChange where ebcc_change IN ('终止','解除')  ) b ON a.puof_tid=b.ebcc_id " 
                    +" INNER JOIN (select gid,cid,emba_name,emba_idcard,emba_state  FROM embase) em on b.gid=em.gid "
                    +" INNER JOIN (select CID,coba_company,coba_client from CoBase ) co on em.cid=co.cid ");
		sql.append(" where 1=1 "+strsql);
		sql.append(" ORDER BY a.puof_addname desc ");
		try {
			System.out.println(sql.toString());
			ResultSet rs= con.GRS(sql.toString());
			while(rs.next()){
				EmbaseContractDetailModel m =new EmbaseContractDetailModel();
                m.setCid(rs.getInt("cid"));
                m.setCoba_client(rs.getString("coba_client"));
                m.setCoba_company(rs.getString("coba_company"));
                m.setEbcc_change(rs.getString("ebcc_change"));
                m.setEbcc_end_date(rs.getString("ebcc_end_date"));
                m.setEmba_idcard(rs.getString("emba_idcard"));
                m.setEmba_name(rs.getString("emba_name"));
                m.setGid(rs.getInt("gid"));
                m.setPuof_addname(rs.getString("puof_addname"));
                m.setPuof_addtime(rs.getString("puof_addtime"));
                m.setPuof_editname(rs.getString("puof_editname"));
                m.setPuof_edittime(rs.getString("puof_edittime"));
                m.setPuof_state(rs.getString("puof_state"));
                m.setPuof_url(rs.getString("puof_url"));
                m.setPuof_id(rs.getInt("puof_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	
	//审核
    public int appCommont(int puof_id){
    	dbconn con = new  dbconn();
    	String username = UserInfo.getUsername();
    	String sql=" UPDATE PubOffice SET  puof_state = 1,puof_editname = ?,puof_edittime = GETDATE() WHERE  puof_id= ? ";
    	PreparedStatement p =con.getpre(sql);
    	try {
			p.setString(1, username);
			p.setInt(2, puof_id);
			int a= p.executeUpdate();
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
    }
}
