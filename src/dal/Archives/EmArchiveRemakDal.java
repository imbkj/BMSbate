package dal.Archives;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmArchivePaymentModel;
import Model.EmArchiveRemarkModel;
import Util.UserInfo;

public class EmArchiveRemakDal {

	//查询续费备注信息
	public List<EmArchiveRemarkModel> getList(EmArchiveRemarkModel em,String order) {
		List<EmArchiveRemarkModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select eare_id,eare_tid,eare_trid,eare_content,eare_addtime,eare_addname,eare_state "
				+ "from EmArchiveRemark " + "where 1=1";
		if (em.getEare_tid() != null) {
			if (!em.getEare_tid().equals("")) {
				sql = sql + " and eare_tid=" + em.getEare_tid();
			}
		}

		if (em.getEare_trid() != null) {
			if (!em.getEare_trid().equals("")) {
				sql = sql + " and eare_trid=" + em.getEare_trid();
			}
		}

		if (em.getEare_state() != null) {
			if (!em.getEare_state().equals("")) {
				sql = sql + " and eare_state=" + em.getEare_state();
			}
		}
		
		if (order!=null) {
			if (!order.equals("")) {
				sql=sql+order;
			}
		}
		try {
			list = db.find(sql, EmArchiveRemarkModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	//添加备注
	public Integer add(Integer tid,Integer trid,String content,int gid){
		Integer i=0;
		dbconn db = new dbconn();
		
		String sql="insert into EmArchiveRemark(eare_tid,eare_trid,eare_content,eare_addtime,eare_addname,eare_state,gid)" +
				"values(?,?,?,getdate(),?,?,?)";
		try {
			i=db.updatePrepareSQL(sql,tid, trid,content,UserInfo.getUsername(),1,gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
}
