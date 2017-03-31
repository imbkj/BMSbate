package dal;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.PubIndustryModel;

public class PubIndustryDal {
	
	//独立户读取行业信息
	public List<PubIndustryModel> getIndustrylist(){
		List<PubIndustryModel> list= new ListModelList<>();
		dbconn db =new dbconn();
		String sql="select a.id,type+convert(varchar(50),a.code)+' - '+a.name name" +
				" from PubIndustry a" +
				" inner join PubIndustryFirst b on a.f_id=b.id" +
				" order by type,code";
		try {
			list = db.find(sql, PubIndustryModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
