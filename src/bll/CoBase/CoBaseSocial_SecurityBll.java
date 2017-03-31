package bll.CoBase;

import java.sql.SQLException;
import java.util.List;

import Model.CoBaseSocialSecurityModel;
import dal.CoBase.CoBaseSocial_SecurityDal;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-10
 * 商业保险-体记业务逻辑层
 */
public class CoBaseSocial_SecurityBll {
	CoBaseSocial_SecurityDal dal =new CoBaseSocial_SecurityDal();
	//当前用户多条件查询商业保险-体记录
	public List<CoBaseSocialSecurityModel> getCoBaseSocial_SecurityList(String strsql) throws SQLException{
		return dal.getCoBaseSocial_SecurityList(strsql);
	}
	//获取客服下拉列表数据
	public List<String> getCoba_assistantList() throws SQLException{
		return dal.getCoba_assistantList();
	}
	//根据coba_id 修改coba_assistant字段员工米名
	public int updateCoBase(CoBaseSocialSecurityModel mr){
		return dal.updateCoBase(mr);
	}
	
	//获取员服下拉列表数据
	public List<String> getCobaList() throws SQLException{
		return dal.getCobaList();
	}
}
