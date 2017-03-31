package dal.EmFinanceManage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmGatheringModel;
import Model.EmbaseModel;

public class emgt_selectDal {
	// 查询公积金领卡信息
	public List<EmGatheringModel> getEmGatheringList(String str){
		List<EmGatheringModel> list=new ArrayList<EmGatheringModel>();
		dbconn db = new dbconn();
		String sql="select convert(varchar(10),emgt_addtime,120) as emgt_addtime,b.gid,c.CID," +
				"coba_shortname,coba_company,emba_name,a.* from " +
				" EmGathering a,embase b,cobase c where a.gid=b.gid and a.cid=b.cid and b.cid=c.cid "+str;
				sql=sql+" order by a.emgt_addtime desc,a.gid";
		try {
			ResultSet rs=db.GRS(sql);
			while(rs.next())
			{
				EmGatheringModel model=new EmGatheringModel();
				model.setCid(rs.getInt("cid"));
				model.setGid(rs.getInt("gid"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setEmgt_addname(rs.getString("emgt_addname"));
				model.setEmgt_addtime(rs.getString("emgt_addtime"));
				model.setEmgt_fee(rs.getBigDecimal("emgt_fee"));
				model.setEmgt_id(rs.getInt("emgt_id"));
				model.setEmgt_paytype(rs.getString("emgt_paytype"));
				model.setEmgt_remark(rs.getString("emgt_remark"));
				model.setEmgt_type(rs.getString("emgt_type"));
				model.setOwnmonth(rs.getInt("ownmonth"));
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return list;
	}
	
	// 根据查询语句获取员工列表数据集
	public List<EmbaseModel> getembaList(String str) {
		List<EmbaseModel> embaList = new ArrayList<EmbaseModel>();
		try {
			ResultSet rs = getembase(str);
			while (rs.next()) {
				EmbaseModel model=new EmbaseModel();
				model.setEmba_id(rs.getInt("emba_id"));
				model.setGid(rs.getInt("gid"));
				model.setCid(rs.getInt("cid"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setEmba_spell(rs.getString("emba_spell"));
				model.setEmba_pinyin(rs.getString("emba_pinyin"));
				model.setEmba_sex(rs.getString("emba_sex"));
				model.setEmba_idcard(rs.getString("emba_idcard"));
				model.setEmba_idcardclass(rs.getString("emba_idcardclass"));
				model.setEmba_mobile( rs.getString("emba_mobile"));
				model.setEmba_email(rs.getString("emba_email"));
				model.setEmba_state(rs.getInt("emba_state"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setCoba_company(rs.getString("coba_company"));
				if(model.getEmba_state()==1)
				{
					model.setEmba_statestr("在职");
				}
				else
				{
					model.setEmba_statestr("离职");
				}
				embaList.add(model);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return embaList;
	}
	
	// 根据查询语句获取员工列表数据集
	private ResultSet getembase(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT top 500 emba_id,a.cid,b.coba_shortname,coba_company,a.gid,a.emba_name," +
				"emba_pinyin,emba_idcardclass,coba_client," +
				"a.emba_sex,b.coba_client,emba_mobile,emba_email,a.emba_wt,a.emba_state,a.emba_spell," +
				"a.emba_idcard from  embase a inner join cobase b on a.cid=b.cid where 1=1 ");
		sql.append(str);
		sql.append(" order by emba_state desc,a.cid,a.gid desc");
		dbconn db = new dbconn();
		rs = db.GRS(sql.toString());
		return rs;
	}
}
