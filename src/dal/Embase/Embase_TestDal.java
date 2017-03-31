package dal.Embase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Model.EmbaseModel;
import Util.Page;
import Util.UserInfo;
import Conn.dbconn;

/**
 * 描述：员工信息 -- 分页功能测试页面业务层
 * @author suhongyuan
 * @create 2016-06-15
 */
public class Embase_TestDal {
    private dbconn con=new dbconn(); 
    
   //获取当前页面记录
    public Page<EmbaseModel> pagingSearchEmbase(String key,Page<EmbaseModel> p){
     int page=p.getCurrentPageIndex()*p.getPageSize();
     List<EmbaseModel> embatestList = new ArrayList<EmbaseModel>();
     String sql="";
     sql="select top "+p.getPageSize()+" * from View_Embaselist where emba_id>"
     +" isnull((select max(emba_id)id from (select top "+page+" emba_id from View_Embaselist where "
     +" exists( select distinct cid from DataPopedom where log_id="+UserInfo.getUserid()+" and  Dat_edited=1 ) order by emba_id) a),0) " 
     +" and exists( select distinct cid from DataPopedom where log_id="+UserInfo.getUserid()+" and  Dat_edited=1 ) order by emba_id ";
     System.out.println(sql);
     try {
		  ResultSet rs=con.GRS(sql);
		  while(rs.next()){
			  embatestList.add(new EmbaseModel(rs.getInt("emba_id"), rs
						.getInt("gid"), rs.getInt("cid"), rs
						.getString("emba_name"), rs.getString("emba_spell"), rs
						.getString("emba_pinyin"), rs.getString("emba_sex"), rs
						.getString("emba_idcard"), rs
						.getString("emba_idcardclass"), rs
						.getString("emba_mobile"), rs.getString("emba_email"),
						rs.getInt("emba_state"), rs.getString("emba_wt"), rs
								.getString("coba_shortname"), rs
								.getString("coba_client"), rs
								.getString("sein_shebao"), rs
								.getString("sein_gjj"), rs
								.getString("sein_shangbao"), rs
								.getString("sein_wt"), rs
								.getString("sein_shebaob"), rs
								.getString("sein_gjjb"), rs
								.getString("sein_da"), rs.getString("sein_zj"),
						rs.getString("sein_ldyg"), rs.getString("sein_xc"), rs
								.getString("emba_statestr"),
						rs.getInt("empic"), rs.getInt("mobile")));
		  }
		  p.setDataList(embatestList);
		  return p;
	     } catch (Exception e) {
		      e.printStackTrace();
		     return null;
	     }
  	
    }
   
    // 获取总记录数
   public int total(){
	   int a=0;
	   String sql ="SELECT COUNT(*) as toal from  View_Embaselist where 1=1  and exists( select distinct cid from DataPopedom where log_id=184 and  Dat_edited=1 ) ";
	   try {
		ResultSet rs=con.GRS(sql);
		while(rs.next()){
			a=rs.getInt("toal");
		}
		System.out.println(a);
		return a;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return a;
	}
   }
}
