package dal.EmSheBao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmSheBaoChangeModel;
import Util.UserInfo;
 
public class Emsc_backlistyfzxDal {
	private dbconn conn = new dbconn();

	/**
	 * 查询退回列表
	 * 
	 * 
	 */
 
	// 获取社保退回数据
	public List<EmSheBaoChangeModel> getEmscData(String where) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		EmSheBaoChangeModel m = null;
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from EmSheBaoChange a left join cobase b on a.CID=b.CID  ");
		 
		sql.append(" where (emsc_ifdeclare=3 or emsc_IfDeclare=9) ");
		sql.append(where);
		try {
			ResultSet rs = conn.GRS(sql.toString());
			while (rs.next()) {
				m = new EmSheBaoChangeModel();
				m.setId(rs.getInt("id"));
				m.setCid(rs.getInt("cid"));
				m.setGid(rs.getInt("gid"));
				m.setEmsc_company(rs.getString("coba_company"));
				m.setEmsc_client(rs.getString("coba_client"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setEmsc_formula(rs.getString("emsc_formula"));
				//m.setEmsc_company(rs.getString("emsc_company"));
				m.setEmsc_shortname(rs.getString("emsc_shortname"));
				m.setEmsc_name(rs.getString("emsc_name"));
				m.setEmsc_computerid(rs.getString("emsc_computerid"));
				m.setEmsc_idcard(rs.getString("emsc_idcard"));
				m.setEmsc_hj(rs.getString("emsc_hj"));
				m.setEmsc_radix(rs.getInt("emsc_radix"));
				m.setEmsc_folk(rs.getString("emsc_folk"));
				m.setEmsc_mobile(rs.getString("emsc_mobile"));
				m.setEmsc_worker(rs.getString("emsc_worker"));
				m.setEmsc_officialrank(rs.getString("emsc_officialrank"));
				m.setEmsc_hand(rs.getString("emsc_hand"));
				m.setEmsc_yl(rs.getString("emsc_yl"));
				m.setEmsc_gs(rs.getString("emsc_gs"));
				m.setEmsc_sye(rs.getString("emsc_sye"));
				m.setEmsc_syu(rs.getString("emsc_syu"));
				m.setEmsc_yltype(rs.getString("emsc_yltype"));
				m.setEmsc_house(rs.getString("emsc_house"));
				m.setEmsc_single(rs.getInt("emsc_single"));
				//m.setEmsc_client(rs.getString("emsc_client"));
				m.setEmsc_remark(rs.getString("emsc_remark"));
				m.setEmsc_change(rs.getString("emsc_change"));
				m.setEmsc_content(rs.getString("emsc_content"));
				m.setEmsc_declaretime(rs.getString("emsc_declaretime"));
				m.setEmsc_declarename(rs.getString("emsc_declarename"));
				m.setEmsc_addtime(rs.getString("emsc_addtime"));
				m.setEmsc_addname(rs.getString("emsc_addname"));
				m.setEmsc_chargeman(rs.getString("emsc_chargeman"));
				m.setEmsc_excelfile(rs.getString("emsc_excelfile"));
				m.setEmsc_iffifteen(rs.getString("emsc_iffifteen"));
				m.setEmsc_ifdeclare(rs.getString("emsc_ifdeclare"));
				m.setEmsc_ifinure(rs.getString("emsc_ifinure"));
				m.setEmsc_ifmodify(rs.getString("emsc_ifmodify"));
				m.setEmsc_ifsame(rs.getString("emsc_ifsame"));
				m.setEmsc_ifmsg(rs.getString("emsc_ifmsg"));
				m.setEmsc_flag(rs.getString("emsc_flag"));
				m.setEmsc_flagname(rs.getString("emsc_flagname"));
				m.setEmsc_ifwrong(rs.getInt("emsc_ifwrong"));
				m.setEmsc_confirmtime(rs.getString("emsc_confirmtime"));
				m.setEmsc_tid(rs.getString("emsc_tid"));
				m.setEmsc_stopreason(rs.getString("emsc_stopreason"));
				//m.setCoco_shebaoid(rs.getString("coco_shebaoid"));
				//m.setMsg_a(rs.getString("msg_a"));
				// m.setMsg_w(rs.getString("msg_w"));
				// m.setMsg_y(rs.getString("msg_y"));
				m.setEmsc_tapr_id(rs.getInt("emsc_tapr_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
