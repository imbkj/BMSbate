package dal.EmTax;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.PropersonTaxationModel;

public class PropersonTaxationDal {
	//获取客服列表
	public List<String> getClientList(){
			String sql="select log_name FROM Login where (dep_id=2 OR dep_id=13) and log_inure=1 order BY dep_id,log_id ";
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
	//获取大大类
	public List<PropersonTaxationModel> findBigPropersonTaxationList(Integer clientName) throws SQLException{
		List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
		dbconn con = new  dbconn();
		String strsql =" select coba_client,COUNT(*)n,SUM(num)m "
          +" from ( "
          +" select cid,COUNT(*)num,coba_client "
          +" from( "
          +" select coba_client,a.cid,a.gid "
          +" from embase a  "
          +" inner join cobase b on a.cid=b.CID "
          +" inner join coglist c on a.gid=c.gid "
          +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id "
          +" inner join CoOffer e on d.coli_coof_id=e.coof_id "
          +" inner join CoCompact f on e.coof_coco_id=f.coco_id "
          +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname "
          +" where emba_state=1 and cgli_stopdate is null AND cpct_type <>'财税' " +
          "  and coba_client in ( SELECT username FROM GetChildbase(" +clientName+
          ",5)) " 
         
          +" group by a.cid,a.gid,coba_client "
          +" )z "
          +" group by cid,coba_client "
          +" )y "
          +" group by coba_client "
          +" order by coba_client desc ";
		try {
			ResultSet rs= con.GRS(strsql);
			System.out.println(strsql);
			while(rs.next()){
				PropersonTaxationModel mm =new PropersonTaxationModel();
				mm.setCoba_client(rs.getString("coba_client"));
				mm.setM(rs.getInt("m"));
				mm.setN(rs.getInt("n"));
				list.add(mm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//获取大类
	public List<PropersonTaxationModel> findPropersonTaxationList(Integer clientName) throws SQLException{
		List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
		dbconn con = new  dbconn();
		String strsql =" select coba_client,cpct_type,COUNT(*)n,SUM(num)m "
          +" from ( "
          +" select cid,COUNT(*)num,cpct_type,coba_client "
          +" from( "
          +" select coba_client,a.cid,a.gid,cpct_type "
          +" from embase a  "
          +" inner join cobase b on a.cid=b.CID "
          +" inner join coglist c on a.gid=c.gid "
          +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id "
          +" inner join CoOffer e on d.coli_coof_id=e.coof_id "
          +" inner join CoCompact f on e.coof_coco_id=f.coco_id "
          +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname "
          +" where emba_state=1 and cgli_stopdate is null AND cpct_type <>'财税' " +
          "  and coba_client in ( SELECT username FROM GetChildbase(" +clientName+
          ",5)) " 
         
          +" group by a.cid,a.gid,cpct_type,coba_client "
          +" )z "
          +" group by cid,cpct_type,coba_client "
          +" )y "
          +" group by cpct_type,coba_client "
          +" order by coba_client,cpct_type desc ";
		try {
			ResultSet rs= con.GRS(strsql);
			System.out.println(strsql);
			while(rs.next()){
				PropersonTaxationModel mm =new PropersonTaxationModel();
				mm.setCoba_client(rs.getString("coba_client"));
				mm.setCpct_type(rs.getString("cpct_type"));
				mm.setM(rs.getInt("m"));
				mm.setN(rs.getInt("n"));
				list.add(mm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	
	//获取财务类数据
	public List<PropersonTaxationModel> findPropersonTaxationFinancialList(Integer clientName) throws SQLException{
		List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
		dbconn con = new  dbconn();
		String strsql =" select coba_gzaddname,cpct_type,COUNT(*)n,SUM(num)m "
                    +" from ( "
                    +" select cid,COUNT(*)num,cpct_type,coba_gzaddname "
                    +" from( "
                    +" select coba_gzaddname,a.cid,a.gid,cpct_type "
                    +" from embase a  "
                    +" inner join cobase b on a.cid=b.CID "
                    +" inner join coglist c on a.gid=c.gid "
                    +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id "
                    +" inner join CoOffer e on d.coli_coof_id=e.coof_id "
                    +" inner join CoCompact f on e.coof_coco_id=f.coco_id "
                    +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname "
                    +" where emba_state=1 and cgli_stopdate is null and cpct_type='财税' and b.coba_gzaddname in "
                    +" ( SELECT username FROM GetChildbase(" +clientName+
                     ",5)) " 
                    +" group by a.cid,a.gid,cpct_type,coba_gzaddname "
                    +" )z "
                    +" group by cid,cpct_type,coba_gzaddname "
                    +" )y "
                    +" group by cpct_type,coba_gzaddname "
                    +" order by coba_gzaddname, cpct_type desc ";
		try {
			ResultSet rs= con.GRS(strsql);
			System.out.println(strsql);
			while(rs.next()){
				PropersonTaxationModel mm =new PropersonTaxationModel();
				mm.setCoba_gzaddname(rs.getString("coba_gzaddname"));
				mm.setCpct_type(rs.getString("cpct_type"));
				mm.setM(rs.getInt("m"));
				mm.setN(rs.getInt("n"));
				list.add(mm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	
	//派遣明细
	public List<PropersonTaxationModel> findPropersonTaxationSendList(String clientName) throws SQLException{
			List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
			dbconn con = new  dbconn();
			String strsql =" select coba_client,coco_compacttype,COUNT(*)n,SUM(num)m "
                       +" from ( "
                       +" select cid,COUNT(*)num,coco_compacttype,coba_client "
                       +" from( "
                       +" select coba_client,a.cid,a.gid,coco_compacttype "
                       +" from embase a  "
                       +" inner join cobase b on a.cid=b.CID "
                       +" inner join coglist c on a.gid=c.gid "
                       +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id "
                       +" inner join CoOffer e on d.coli_coof_id=e.coof_id "
                       +" inner join CoCompact f on e.coof_coco_id=f.coco_id "
                       +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname "
                       +" where emba_state=1 and cgli_stopdate is null and cpct_type='派遣' and coba_client='"+clientName+"' "
                       +" group by a.cid,a.gid,coco_compacttype,coba_client "
                       +" )z "
                       +" group by cid,coco_compacttype,coba_client "
                       +" )y "
                       +" group by coco_compacttype,coba_client "
                       +" order by coco_compacttype,coba_client ";
			try {
				ResultSet rs= con.GRS(strsql);
				System.out.println(strsql);
				while(rs.next()){
					PropersonTaxationModel mm =new PropersonTaxationModel();
					mm.setCoba_client(rs.getString("coba_client"));
					mm.setCoco_compacttype(rs.getString("coco_compacttype"));
					mm.setM(rs.getInt("m"));
					mm.setN(rs.getInt("n"));
					list.add(mm);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				con.Close();
			}
			return list;
		}
		
		//代理明细
		public List<PropersonTaxationModel> findPropersonTaxationAgentList(String clientName) throws SQLException{
			List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
			dbconn con = new  dbconn();
			String strsql =" select coba_client,coco_compacttype,COUNT(*)n,SUM(num)m "
                    +" from ( "
                    +" select cid,COUNT(*)num,coco_compacttype,coba_client "
                    +" from( "
                    +" select coba_client,a.cid,a.gid,coco_compacttype "
                    +" from embase a  "
                    +" inner join cobase b on a.cid=b.CID "
                    +" inner join coglist c on a.gid=c.gid "
                    +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id "
                    +" inner join CoOffer e on d.coli_coof_id=e.coof_id "
                    +" inner join CoCompact f on e.coof_coco_id=f.coco_id "
                    +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname "
                    +" where emba_state=1 and cgli_stopdate is null and cpct_type='代理' and coba_client='"+clientName+"' "
                    +" group by a.cid,a.gid,coco_compacttype,coba_client "
                    +" )z "
                    +" group by cid,coco_compacttype,coba_client "
                    +" )y "
                    +" group by coco_compacttype,coba_client "
                    +" order by coco_compacttype,coba_client ";
			try { 
				ResultSet rs= con.GRS(strsql);
				System.out.println(strsql);
				while(rs.next()){
					PropersonTaxationModel mm =new PropersonTaxationModel();
					mm.setCoba_client(rs.getString("coba_client"));
					mm.setCoco_compacttype(rs.getString("coco_compacttype"));
					mm.setM(rs.getInt("m"));
					mm.setN(rs.getInt("n"));
					list.add(mm);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				con.Close();
			}
			return list;
		}
		
		//财税明细
		public List<PropersonTaxationModel> findPropersonTaxationTaxList(String clientName) throws SQLException{
				List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
				dbconn con = new  dbconn();
				String strsql ="select coba_gzaddname,cpct_type,COUNT(*)n,SUM(num)m "
                            +" from ( "
                            +" select cid,COUNT(*)num,cpct_type,coba_gzaddname "
                            +" from( "
                            +" select coba_gzaddname,a.cid,a.gid,cpct_type "
                            +" from embase a  "
                            +" inner join cobase b on a.cid=b.CID "
                            +" inner join coglist c on a.gid=c.gid "
                            +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id "
                            +" inner join CoOffer e on d.coli_coof_id=e.coof_id "
                            +" inner join CoCompact f on e.coof_coco_id=f.coco_id "
                            +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname "
                            +" where emba_state=1 and cgli_stopdate is null and cpct_type='财税' and b.coba_gzaddname='"+clientName+"' "
                            +" group by a.cid,a.gid,cpct_type,coba_gzaddname "
                            +" )z "
                            +" group by cid,cpct_type,coba_gzaddname "
                            +" )y "
                            +" group by cpct_type,coba_gzaddname "
                            +" order by cpct_type desc,coba_gzaddname ";

					try {
						ResultSet rs= con.GRS(strsql);
						System.out.println(strsql);
						while(rs.next()){
							PropersonTaxationModel mm =new PropersonTaxationModel();
							mm.setCpct_type(rs.getString("cpct_type"));
							mm.setCoba_gzaddname(rs.getString("coba_gzaddname"));
							mm.setM(rs.getInt("m"));
							mm.setN(rs.getInt("n"));
							list.add(mm);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						con.Close();
					}
					return list;
				}
	//委托外地总人数 
	public List<PropersonTaxationModel> getBigEntrustList(Integer clientName) throws SQLException{
		List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
		dbconn con = new  dbconn();
		String strSql=" select coba_client, COUNT(*)n,SUM(num)m  "
                +" from (  "
                +" select cid,COUNT(*)num,coba_client  "
               +"  from(  "
                +" select coba_client,a.cid,a.gid " 
                +" from embase a "  
                +"  INNER JOIN (SELECT  * FROM EmCommissionOut where ecou_addtype='正常' AND ecou_state<>3 "
                +" ) emcm on a.gid= emcm.gid "
                +" inner join cobase b on a.cid=b.CID  "
                +" inner join coglist c on a.gid=c.gid  "
                +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id  "
                +" inner join CoOffer e on d.coli_coof_id=e.coof_id " 
                +" inner join CoCompact f on e.coof_coco_id=f.coco_id  "
                +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname  "
                +" where  emba_state=1 and cgli_stopdate is null " 
                +" and coba_client in ( SELECT username FROM GetChildbase( "+clientName+" ,5)) "
                +" group by a.cid,a.gid,coba_client " 
                +" )z  "
                +" group by cid,coba_client  "
                +" )y  "
                +" group by  coba_client " 
                +" order by coba_client desc ";
		try {
			ResultSet rs= con.GRS(strSql);
			System.out.println(strSql);
			while(rs.next()){
				PropersonTaxationModel mm =new PropersonTaxationModel();
				mm.setCoba_client(rs.getString("coba_client"));
				mm.setM(rs.getInt("m"));
				mm.setN(rs.getInt("n"));
				list.add(mm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	
	//委托外地分人数 
		public List<PropersonTaxationModel> getEntrustList(Integer clientName) throws SQLException{
			List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
			dbconn con = new  dbconn();
			String strSql="select coba_client,cpct_type,COUNT(*)n,SUM(num)m  "
           +" from (  "
           +" select cid,COUNT(*)num,cpct_type,coba_client  "
           +" from(  "
           +" select coba_client,a.cid,a.gid,cpct_type  "
           +" from embase a "  
           +"  INNER JOIN (SELECT  * FROM EmCommissionOut where ecou_addtype='正常' AND ecou_state<>3 "
           +" ) emcm on a.gid= emcm.gid "
           +" inner join cobase b on a.cid=b.CID  "
           +" inner join coglist c on a.gid=c.gid  "
           +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id  "
           +" inner join CoOffer e on d.coli_coof_id=e.coof_id  "
           +" inner join CoCompact f on e.coof_coco_id=f.coco_id  "
           +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname  "
           +" where  emba_state=1 and cgli_stopdate is null  "
           +"  and coba_client in ( SELECT username FROM GetChildbase("+clientName+",5)) "  
           +" group by a.cid,a.gid,cpct_type,coba_client " 
           +" )z   "
           +"  group by cid,cpct_type,coba_client   "
           +" )y   "
           +" group by cpct_type,coba_client  " 
           +" order by coba_client,cpct_type desc  ";
			try {
				ResultSet rs= con.GRS(strSql);
				System.out.println(strSql);
				while(rs.next()){
					PropersonTaxationModel mm =new PropersonTaxationModel();
					mm.setCoba_client(rs.getString("coba_client"));
					mm.setCpct_type(rs.getString("cpct_type"));
					mm.setM(rs.getInt("m"));
					mm.setN(rs.getInt("n"));
					list.add(mm);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				con.Close();
			}
			return list;
		}
     //委托外地明细
		public List<PropersonTaxationModel> getEntrustDetailList(String clientName,String cpct_type) throws SQLException{
			List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
			dbconn con = new  dbconn();
			String strsql =" select coba_client,coco_compacttype,COUNT(*)n,SUM(num)m " 
           +" from (  "
           +" select cid,COUNT(*)num,coco_compacttype,coba_client " 
           +" from(  "
           +" select coba_client,a.cid,a.gid,coco_compacttype "
           +" from embase a  " 
           +" INNER JOIN (SELECT  * FROM EmCommissionOut where ecou_addtype='正常' AND ecou_state<>3 "
           +" ) emcm on a.gid= emcm.gid "
           +" inner join cobase b on a.cid=b.CID " 
           +" inner join coglist c on a.gid=c.gid " 
           +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id " 
           +" inner join CoOffer e on d.coli_coof_id=e.coof_id " 
           +" inner join CoCompact f on e.coof_coco_id=f.coco_id  "
           +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname " 
           +" where  emba_state=1 and cgli_stopdate is null " 
           +"  and coba_client='"+clientName+"' and  cpct_type='"+cpct_type+"' "
           +" group by a.cid,a.gid,coco_compacttype,coba_client " 
           +" )z "  
           +"  group by cid,coco_compacttype,coba_client "  
           +" )y  " 
           +" group by coco_compacttype,coba_client "  
           +" order by coba_client,coco_compacttype desc ";
			try {
				ResultSet rs= con.GRS(strsql);
				System.out.println(strsql);
				while(rs.next()){
					PropersonTaxationModel mm =new PropersonTaxationModel();
					mm.setCoba_client(rs.getString("coba_client"));
					mm.setCoco_compacttype(rs.getString("coco_compacttype"));
					mm.setM(rs.getInt("m"));
					mm.setN(rs.getInt("n"));
					list.add(mm);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				con.Close();
			}
			return list;
		}
  //外籍人汇总
	public List<PropersonTaxationModel> getForexList(Integer clientName) throws SQLException{
		List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
		dbconn con = new  dbconn();
		String strSql=" select coba_client,cpct_type,COUNT(*)n,SUM(num)m  "
              +" from (  "
              +" select cid,COUNT(*)num,cpct_type,coba_client " 
              +" from(  "
              +" select coba_client,a.cid,a.gid,cpct_type  "
              +" from embase a  " 
              +" inner join cobase b on a.cid=b.CID " 
              +" inner join coglist c on a.gid=c.gid  "
              +" inner join CoOfferList d on c.cgli_coli_id=d.coli_id  "
              +" inner join CoOffer e on d.coli_coof_id=e.coof_id " 
              +" inner join CoCompact f on e.coof_coco_id=f.coco_id  "
              +" inner join CopCompact g on f.coco_compacttype=g.cpct_shortname " 
              +" where  emba_state=1 and cgli_stopdate is null  and g.cpct_shortname='EFS' "
              +"  and coba_client in ( SELECT username FROM GetChildbase("+clientName+",5)) "  
              +"  group by a.cid,a.gid,cpct_type,coba_client " 
              +" )z " 
              +" group by cid,cpct_type,coba_client " 
              +" )y " 
              +" group by cpct_type,coba_client "   
              +" order by coba_client,cpct_type desc ";
		try {
			ResultSet rs= con.GRS(strSql);
			System.out.println(strSql);
			while(rs.next()){
				PropersonTaxationModel mm =new PropersonTaxationModel();
				mm.setCoba_client(rs.getString("coba_client"));
				mm.setCpct_type(rs.getString("cpct_type"));
				mm.setM(rs.getInt("m"));
				mm.setN(rs.getInt("n"));
				list.add(mm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			con.Close();
		}
		return list;
	}
	//外籍人详情
	public List<PropersonTaxationModel> getForexDetailList(String clientName) throws SQLException{
		List<PropersonTaxationModel> list = new ListModelList<PropersonTaxationModel>();
		dbconn con = new  dbconn();
		String strSql="select coba_client,coco_compacttype,COUNT(*)n,SUM(num)m from ( select cid,COUNT(*)num,coco_compacttype,coba_client from( select coba_client,a.cid,a.gid,coco_compacttype from embase a inner join cobase b on a.cid=b.CID inner join coglist c on a.gid=c.gid inner join CoOfferList d on c.cgli_coli_id=d.coli_id  inner join CoOffer e on d.coli_coof_id=e.coof_id inner join CoCompact f on e.coof_coco_id=f.coco_id inner join CopCompact g on f.coco_compacttype=g.cpct_shortname where  emba_state=1 and cgli_stopdate is null  and  g.cpct_shortname='EFS' and coba_client='"+clientName+"' group by a.cid,a.gid,coco_compacttype,coba_client )z group by cid,coco_compacttype,coba_client )y group by coco_compacttype,coba_client order by coba_client,coco_compacttype desc";
			try {
				ResultSet rs= con.GRS(strSql);
				System.out.println(strSql);
				while(rs.next()){
					PropersonTaxationModel mm =new PropersonTaxationModel();
					mm.setCoba_client(rs.getString("coba_client"));
					mm.setCoco_compacttype(rs.getString("coco_compacttype"));
					mm.setM(rs.getInt("m"));
					mm.setN(rs.getInt("n"));
					list.add(mm);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				con.Close();
			}
			return list;
	}
}
