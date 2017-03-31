package dal.EmZYT;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmZYTFeedbackFileModel;
import Model.EmZYTFeedbackModel;
import Model.EmZYTModel;
import Model.EmbaseModel;
import Util.DateStringChange;

public class EmZYT_SelectDal {
	private dbconn conn = new dbconn();

	// 获取智翼通接口委托单数据
	public List<EmZYTModel> getEmZYTList(String str) {
		List<EmZYTModel> list = new ArrayList<EmZYTModel>();
		EmZYTModel m = null;
		/*String sql = "SELECT * FROM EmZYT_V WHERE ( CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid()
				+ "  and dat_selected=1 ) or cid is null)   "
				+ str
				+ " order by CHARINDEX(convert(varchar(5),emzt_state)+',','10,2,4,5,6,7,8,9,11,1,3,'),emzt_name,emzt_uptime desc";*/
		String sql = "SELECT * FROM EmZYT_V WHERE 1=1  "
				+ str
				+ " order by CHARINDEX(convert(varchar(5),emzt_state)+',','10,2,4,5,6,7,8,9,11,1,3,'),emzt_name,emzt_uptime desc";
		 System.out.println(sql);
		// 状态
		String state = "";
		String statecolor = "";
		String contactstate = "";
		String datastate = "";
		String outstate = "";
		String outstatecolor = "";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmZYTModel();
				m.setId(rs.getInt("id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmzt_zytid(rs.getString("emzt_zytid"));
				m.setEmzt_zgid(rs.getString("emzt_zgid"));
				m.setEmzt_zcid(rs.getString("emzt_zcid"));
				m.setEmzt_class(rs.getString("emzt_class"));
				m.setEmzt_state(rs.getInt("emzt_state"));
				m.setEmzt_ifsame(rs.getInt("emzt_ifsame"));
				m.setEmzt_uptime(rs.getString("emzt_uptime"));
				m.setEmzt_scompany(rs.getString("emzt_scompany"));
				m.setEmzt_sname(rs.getString("emzt_sname"));
				m.setEmzt_scity(rs.getString("emzt_scity"));
				m.setEmzt_rname(rs.getString("emzt_rname"));
				m.setEmzt_rcity(rs.getString("emzt_rcity"));
				m.setEmzt_rcompany(rs.getString("emzt_rcompany"));
				m.setEmzt_company(rs.getString("emzt_company"));
				m.setEmzt_client(rs.getString("emzt_client"));
				m.setEmzt_name(rs.getString("emzt_name"));
				m.setEmzt_idcard(rs.getString("emzt_idcard"));
				m.setEmzt_mobile(rs.getString("emzt_mobile"));
				m.setEmzt_yltotal(rs.getBigDecimal("emzt_yltotal"));
				m.setEmzt_syetotal(rs.getBigDecimal("emzt_syetotal"));
				m.setEmzt_gstotal(rs.getBigDecimal("emzt_gstotal"));
				m.setEmzt_syutotal(rs.getBigDecimal("emzt_syutotal"));
				m.setEmzt_jltotal(rs.getBigDecimal("emzt_jltotal"));
				m.setEmzt_housetotal(rs.getBigDecimal("emzt_housetotal"));
				m.setEmzt_zhtotal(rs.getBigDecimal("emzt_zhtotal"));
				m.setEmzt_bjtotal(rs.getBigDecimal("emzt_bjtotal"));
				m.setEmzt_sbtotal(rs.getBigDecimal("emzt_sbtotal"));
				m.setEmzt_elsefee(rs.getBigDecimal("emzt_elsefee"));
				m.setEmzt_sbchange(rs.getString("emzt_sbchange"));
				m.setEmzt_serverid(rs.getString("emzt_serverid"));
				m.setEmzt_servername(rs.getString("emzt_servername"));
				m.setEmzt_serverfee(rs.getString("emzt_serverfee"));
				m.setEmzt_servertotal(rs.getBigDecimal("emzt_servertotal"));
				m.setEmzt_serverchange(rs.getString("emzt_serverchange"));
				m.setEmzt_fee(rs.getBigDecimal("emzt_fee"));
				m.setEmzt_filefee(rs.getBigDecimal("emzt_filefee"));
				m.setEmzt_total(rs.getBigDecimal("emzt_total"));
				m.setEmzt_sbbj(rs.getBigDecimal("emzt_sbbj"));
				m.setEmzt_housebj(rs.getBigDecimal("emzt_housebj"));
				m.setEmzt_elsebj(rs.getBigDecimal("emzt_elsebj"));
				m.setEmzt_remark(rs.getString("emzt_remark"));
				m.setEmzt_ifinure(rs.getString("emzt_ifinure"));
				m.setEmzt_addtime(rs.getString("emzt_addtime"));
				m.setEmzt_addname(rs.getString("emzt_addname"));
				m.setEmzt_ifconfirm(rs.getString("emzt_ifconfirm"));
				m.setEmzt_confirmtime(rs.getString("emzt_confirmtime"));
				m.setEmzt_confirmname(rs.getString("emzt_confirmname"));
				m.setEmzt_city(rs.getString("emzt_city"));
				m.setEmzt_sbstand(rs.getString("emzt_sbstand"));
				m.setEmzt_sbstanename(rs.getString("emzt_sbstanename"));
				m.setEmzt_ylcp(rs.getBigDecimal("emzt_ylcp"));
				m.setEmzt_ylop(rs.getBigDecimal("emzt_ylop"));
				m.setEmzt_jlcp(rs.getBigDecimal("emzt_jlcp"));
				m.setEmzt_jlop(rs.getBigDecimal("emzt_jlop"));
				m.setEmzt_gscp(rs.getBigDecimal("emzt_gscp"));
				m.setEmzt_gsop(rs.getBigDecimal("emzt_gsop"));
				m.setEmzt_syecp(rs.getBigDecimal("emzt_syecp"));
				m.setEmzt_syeop(rs.getBigDecimal("emzt_syeop"));
				m.setEmzt_syucp(rs.getBigDecimal("emzt_syucp"));
				m.setEmzt_syuop(rs.getBigDecimal("emzt_syuop"));
				m.setEmzt_housecp(rs.getBigDecimal("emzt_housecp"));
				m.setEmzt_houseop(rs.getBigDecimal("emzt_houseop"));
				m.setEmzt_zhcp(rs.getBigDecimal("emzt_zhcp"));
				m.setEmzt_zhop(rs.getBigDecimal("emzt_zhop"));
				m.setEmzt_ylradix(rs.getString("emzt_ylradix"));
				m.setEmzt_syeradix(rs.getString("emzt_syeradix"));
				m.setEmzt_gsradix(rs.getString("emzt_gsradix"));
				m.setEmzt_syuradix(rs.getString("emzt_syuradix"));
				m.setEmzt_jlradix(rs.getString("emzt_jlradix"));
				m.setEmzt_houseradix(rs.getString("emzt_houseradix"));
				m.setEmzt_zhradix(rs.getString("emzt_zhradix"));
				m.setEmzt_bjradix(rs.getString("emzt_bjradix"));
				m.setEmzt_flfee(rs.getBigDecimal("emzt_flfee"));
				m.setEmzt_flcontent(rs.getString("emzt_flcontent"));
				m.setEmzt_filename(rs.getString("emzt_filename"));
				m.setEmzt_f_confirm(rs.getInt("emzt_f_confirm"));
				m.setEmzt_f_confirmtime(rs.getString("emzt_f_confirmtime"));
				m.setEmzt_f_confirmname(rs.getString("emzt_f_confirmname"));
				m.setEmzt_ylcpp(rs.getString("emzt_ylcpp"));
				m.setEmzt_ylopp(rs.getString("emzt_ylopp"));
				m.setEmzt_jlcpp(rs.getString("emzt_jlcpp"));
				m.setEmzt_jlopp(rs.getString("emzt_jlopp"));
				m.setEmzt_gscpp(rs.getString("emzt_gscpp"));
				m.setEmzt_gsopp(rs.getString("emzt_gsopp"));
				m.setEmzt_syecpp(rs.getString("emzt_syecpp"));
				m.setEmzt_syeopp(rs.getString("emzt_syeopp"));
				m.setEmzt_syucpp(rs.getString("emzt_syucpp"));
				m.setEmzt_syuopp(rs.getString("emzt_syuopp"));
				m.setEmzt_housecpp(rs.getString("emzt_housecpp"));
				m.setEmzt_houseopp(rs.getString("emzt_houseopp"));
				m.setEmzt_sbsingle(rs.getString("emzt_sbsingle"));
				m.setEmzt_housesingle(rs.getString("emzt_housesingle"));
				m.setEmzt_ylstart(rs.getString("emzt_ylstart"));
				m.setEmzt_ylstartBMS(rs.getString("emzt_ylstartBMS"));
				m.setEmzt_ylstop(rs.getString("emzt_ylstop"));
				m.setEmzt_ylstopBMS(rs.getString("emzt_ylstopBMS"));
				m.setEmzt_jlstart(rs.getString("emzt_jlstart"));
				m.setEmzt_jlstartBMS(rs.getString("emzt_jlstartBMS"));
				m.setEmzt_jlstop(rs.getString("emzt_jlstop"));
				m.setEmzt_jlstopBMS(rs.getString("emzt_jlstopBMS"));
				m.setEmzt_gsstart(rs.getString("emzt_gsstart"));
				m.setEmzt_gsstop(rs.getString("emzt_gsstop"));
				m.setEmzt_syestart(rs.getString("emzt_syestart"));
				m.setEmzt_syestop(rs.getString("emzt_syestop"));
				m.setEmzt_syustart(rs.getString("emzt_syustart"));
				m.setEmzt_syustop(rs.getString("emzt_syustop"));
				m.setEmzt_housestart(rs.getString("emzt_housestart"));
				m.setEmzt_housestop(rs.getString("emzt_housestop"));
				m.setEmzt_housestartBMS(rs.getString("emzt_housestartBMS"));
				m.setEmzt_housestopBMS(rs.getString("emzt_housestopBMS"));
				m.setEmzt_sbtitle(rs.getString("emzt_sbtitle"));
				m.setEmzt_housetitle(rs.getString("emzt_housetitle"));
				m.setEmzt_compactstart(rs.getString("emzt_compactstart"));
				m.setEmzt_compactstop(rs.getString("emzt_compactstop"));
				m.setEmzt_outdate(rs.getString("emzt_outdate"));
				m.setEmzt_flag(rs.getString("emzt_flag"));
				m.setEmzt_email(rs.getString("emzt_email"));
				m.setEmzt_spell(rs.getString("emzt_spell"));
				m.setEmzt_r_record(rs.getString("emzt_r_record"));
				m.setEmzt_declaretime(rs.getString("emzt_declaretime"));
				m.setEmzt_declarename(rs.getString("emzt_declarename"));
				m.setEmzt_phone(rs.getString("emzt_phone"));
				m.setEmzt_idcardclass(rs.getString("emzt_idcardclass"));
				m.setEmzt_bchousestart(rs.getString("emzt_bchousestart"));
				m.setEmzt_bchousestop(rs.getString("emzt_bchousestop"));
				m.setEmzt_bchouseradix(rs.getString("emzt_bchouseradix"));
				m.setEmzt_flstart(rs.getString("emzt_flstart"));
				m.setEmzt_flstop(rs.getString("emzt_flstop"));
				m.setEmzt_flfeeinfo(rs.getString("emzt_flfeeinfo"));
				m.setEmzt_elsefeestart(rs.getString("emzt_elsefeestart"));
				m.setEmzt_elsefeestop(rs.getString("emzt_elsefeestop"));
				m.setEmzt_feestart(rs.getString("emzt_feestart"));
				m.setEmzt_feestop(rs.getString("emzt_feestop"));
				m.setEmzt_filefeestart(rs.getString("emzt_filefeestart"));
				m.setEmzt_filefeestop(rs.getString("emzt_filefeestop"));
				m.setEmzt_managefee(rs.getBigDecimal("emzt_managefee"));
				m.setEmzt_cityremark(rs.getString("emzt_cityremark"));
				m.setEmzt_agreement(rs.getString("emzt_agreement"));
				m.setEmzt_iffile(rs.getString("emzt_iffile"));
				m.setEmzt_rdate(rs.getString("emzt_rdate"));
				m.setEmzt_ifsingle(rs.getString("emzt_ifsingle"));
				m.setEmzt_ifunlimited(rs.getString("emzt_ifunlimited"));
				m.setEmzt_outstate(rs.getInt("emzt_outstate"));
				m.setEmzt_ylradixBMS(rs.getString("emzt_ylradixBMS"));
				m.setEmzt_jlradixBMS(rs.getString("emzt_jlradixBMS"));
				m.setEmzt_houseradixBMS(rs.getString("emzt_houseradixBMS"));
				m.setEmzt_od_name(rs.getString("emzt_od_name"));
				m.setEmzt_od_time(rs.getString("emzt_od_time"));
				m.setEmzt_oc_name(rs.getString("emzt_oc_name"));
				m.setEmzt_oc_time(rs.getString("emzt_oc_time"));
				m.setEmzt_filesingle(rs.getString("emzt_filesingle"));
				m.setEmzt_rsingle(rs.getString("emzt_rsingle"));
				m.setEmzt_outfilename(rs.getString("emzt_outfilename"));
				m.setEmzt_indate(rs.getString("emzt_indate"));
				m.setEmzt_t_name(rs.getString("emzt_t_name"));
				m.setEmzt_t_idcard(rs.getString("emzt_t_idcard"));
				m.setEmzt_hjadd(rs.getString("emzt_hjadd"));
				m.setEmzt_education(rs.getString("emzt_education"));
				m.setEmzt_folk(rs.getString("emzt_folk"));
				m.setEmzt_hand(rs.getString("emzt_hand"));
				m.setEmzt_ifshebao(rs.getString("emzt_ifshebao"));
				m.setEmzt_computerid(rs.getString("emzt_computerid"));
				m.setEmzt_ifsbcard(rs.getString("emzt_ifsbcard"));
				m.setEmzt_ifhouse(rs.getString("emzt_ifhouse"));
				m.setEmzt_houseid(rs.getString("emzt_houseid"));
				m.setEmzt_marital(rs.getString("emzt_marital"));
				m.setEmzt_m_name(rs.getString("emzt_m_name"));
				m.setEmzt_m_idcard(rs.getString("emzt_m_idcard"));
				m.setEmzt_fileplace(rs.getString("emzt_fileplace"));
				m.setEmzt_ofileplace(rs.getString("emzt_ofileplace"));
				m.setEmzt_ifda(rs.getString("emzt_ifda"));
				m.setEmzt_ifowed(rs.getString("emzt_ifowed"));
				m.setEmzt_fileendmonth(rs.getInt("emzt_fileendmonth"));
				m.setEmzt_ifrc(rs.getString("emzt_ifrc"));
				m.setEmzt_iffileservice(rs.getString("emzt_iffileservice"));
				m.setEmzt_iffilechange(rs.getString("emzt_iffilechange"));
				m.setEmzt_nifc_reason(rs.getString("emzt_nifc_reason"));
				m.setEmzt_ifhouseseal(rs.getString("emzt_ifhouseseal"));
				m.setEmzt_contactstate(rs.getInt("emzt_contactstate"));
				m.setEmzt_datastate(rs.getInt("emzt_datastate"));
				m.setEmzt_contacttype(rs.getString("emzt_contacttype"));
				m.setEmzt_yl_outstate(rs.getInt("emzt_yl_outstate"));
				m.setEmzt_ylod_name(rs.getString("emzt_ylod_name"));
				m.setEmzt_ylod_time(rs.getString("emzt_ylod_time"));
				m.setEmzt_yloc_name(rs.getString("emzt_yloc_name"));
				m.setEmzt_yloc_time(rs.getString("emzt_yloc_time"));
				m.setEmzt_jl_outstate(rs.getInt("emzt_jl_outstate"));
				m.setEmzt_jlod_name(rs.getString("emzt_jlod_name"));
				m.setEmzt_jlod_time(rs.getString("emzt_jlod_time"));
				m.setEmzt_jloc_name(rs.getString("emzt_jloc_name"));
				m.setEmzt_jloc_time(rs.getString("emzt_jloc_time"));
				m.setEmzt_house_outstate(rs.getInt("emzt_house_outstate"));
				m.setEmzt_houseod_name(rs.getString("emzt_houseod_name"));
				m.setEmzt_houseod_time(rs.getString("emzt_houseod_time"));
				m.setEmzt_houseoc_name(rs.getString("emzt_houseoc_name"));
				m.setEmzt_houseoc_time(rs.getString("emzt_houseoc_time"));
				m.setEmzt_sbc_notice(rs.getString("emzt_sbc_notice"));
				m.setEmzt_data_notice(rs.getString("emzt_data_notice"));
				m.setEmzt_wtgid(rs.getString("emzt_wtgid"));
				m.setEmzt_title(rs.getString("emzt_title"));
				m.setEmzt_adtype(rs.getString("emzt_adtype"));
				m.setEmzt_tel(rs.getString("emzt_tel"));
				m.setEmzt_wtcid(rs.getString("emzt_wtcid"));
				m.setEmzt_outreason(rs.getString("emzt_outreason"));
				m.setEmzt_sex(rs.getString("emzt_sex"));
				m.setEmzt_sb_state(rs.getString("emzt_sb_state"));
				m.setEmzt_house_state(rs.getString("emzt_house_state"));
				m.setEmzt_iffeefile(rs.getString("emzt_iffeefile"));
				if (rs.getInt("emzt_state") == 10) {
					state = "未处理";
					statecolor = "#000";
				} else if (rs.getInt("emzt_state") == 1) {
					state = "已处理";
					statecolor = "#00F";
				} else if (rs.getInt("emzt_state") == 3) {
					state = "退单";
					statecolor = "#F00";
				} else if (rs.getInt("emzt_state") == 11) {
					state = "退单(待跟踪)";
					statecolor = "#F00";
				}
				// else if (rs.getInt("emzt_state") == 0) {
				// state = "未处理";
				// }
				else {
					state = "待处理";
					statecolor = "#090";
				}
				m.setState(state);
				m.setState_color(statecolor);

				if (rs.getInt("emzt_contactstate") == 1) {
					contactstate = "入职网";
				} else if (rs.getInt("emzt_contactstate") == 2) {
					contactstate = "入职网已更新";
				} else if (rs.getInt("emzt_contactstate") == 3) {
					contactstate = "部分确认";
				} else if (rs.getInt("emzt_contactstate") == 4) {
					contactstate = "联系完成";
				} else {
					contactstate = "未处理";
				}
				m.setContactstate(contactstate);

				if (rs.getInt("emzt_datastate") == 1) {
					datastate = "未提交";
				} else if (rs.getInt("emzt_datastate") == 2) {
					datastate = "部分提交";
				} else if (rs.getInt("emzt_datastate") == 3) {
					datastate = "资料齐全";
				} else {
					datastate = "未处理";
				}
				m.setDatastate(datastate);

				if (rs.getInt("emzt_outstate") == 1) {
					outstate = "已反馈";
					outstatecolor = "#00F";
				} else if (rs.getInt("emzt_outstate") == 2) {
					outstate = "反馈中";
					outstatecolor = "#090";
				} else if (rs.getInt("emzt_outstate") == 3) {
					outstate = "部分反馈";
					outstatecolor = "#00F";
				} else {
					outstate = "未反馈";
					outstatecolor = "#000";
				}
				m.setOutstate(outstate);
				m.setOutstate_color(outstatecolor);

				m.setEmzt_tapr_id(rs.getInt("emzt_tapr_id"));
				m.setEmba_id(rs.getInt("emba_id"));

				m.setHouse_cpp(rs.getString("emzt_housecpp"));
				m.setHouse_opp(rs.getString("emzt_houseopp"));

				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取智翼通接口委托单数据
	public List<EmZYTModel> getEmZYTFeeList(String str,String ownmonth,String smonth) {
		List<EmZYTModel> list = new ArrayList<EmZYTModel>();
		EmZYTModel m = null;
		String sql = "";

		sql="exec EmZYTFeeListSelect_P_lsb '"+str+"','"+ownmonth+"','"+smonth+"'";
		 System.out.println(sql);
		// 状态
		String state = "";
		String statecolor = "";
		String contactstate = "";
		String datastate = "";
		String outstate = "";
		String outstatecolor = "";
		String chkstate="";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmZYTModel();
				m.setId(rs.getInt("id"));
				m.setOwnmonth(rs.getInt("ownmonth"));
				m.setGid(rs.getInt("gid"));
				m.setCid(rs.getInt("cid"));
				m.setEmzt_zytid(rs.getString("emzt_zytid"));
				m.setEmzt_zgid(rs.getString("emzt_zgid"));
				m.setEmzt_zcid(rs.getString("emzt_zcid"));
				m.setEmzt_class(rs.getString("emzt_class"));
				m.setEmzt_state(rs.getInt("emzt_state"));
				m.setEmzt_ifsame(rs.getInt("emzt_ifsame"));
				m.setEmzt_uptime(rs.getString("emzt_uptime"));
				m.setEmzt_scompany(rs.getString("emzt_scompany"));
				m.setEmzt_sname(rs.getString("emzt_sname"));
				m.setEmzt_scity(rs.getString("emzt_scity"));
				m.setEmzt_rname(rs.getString("emzt_rname"));
				m.setEmzt_rcity(rs.getString("emzt_rcity"));
				m.setEmzt_rcompany(rs.getString("emzt_rcompany"));
				m.setEmzt_company(rs.getString("emzt_company"));
				m.setEmzt_client(rs.getString("emzt_client"));
				m.setEmzt_name(rs.getString("emzt_name"));
				m.setEmzt_idcard(rs.getString("emzt_idcard"));
				m.setEmzt_mobile(rs.getString("emzt_mobile"));
				m.setEmzt_yltotal(rs.getBigDecimal("emzt_yltotal"));
				m.setEmzt_syetotal(rs.getBigDecimal("emzt_syetotal"));
				m.setEmzt_gstotal(rs.getBigDecimal("emzt_gstotal"));
				m.setEmzt_syutotal(rs.getBigDecimal("emzt_syutotal"));
				m.setEmzt_jltotal(rs.getBigDecimal("emzt_jltotal"));
				m.setEmzt_housetotal(rs.getBigDecimal("emzt_housetotal"));
				m.setEmzt_zhtotal(rs.getBigDecimal("emzt_zhtotal"));
				m.setEmzt_bjtotal(rs.getBigDecimal("emzt_bjtotal"));
				m.setEmzt_sbtotal(rs.getBigDecimal("z_sbtotal"));
				m.setEmzt_elsefee(rs.getBigDecimal("emzt_elsefee"));
				m.setEmzt_sbchange(rs.getString("emzt_sbchange"));
				m.setEmzt_serverid(rs.getString("emzt_serverid"));
				m.setEmzt_servername(rs.getString("emzt_servername"));
				m.setEmzt_serverfee(rs.getString("emzt_serverfee"));
				m.setEmzt_servertotal(rs.getBigDecimal("emzt_servertotal"));
				m.setEmzt_serverchange(rs.getString("emzt_serverchange"));
				m.setEmzt_fee(rs.getBigDecimal("emzt_fee"));
				m.setEmzt_filefee(rs.getBigDecimal("emzt_filefee"));
				m.setEmzt_total(rs.getBigDecimal("emzt_total"));
				m.setEmzt_sbbj(rs.getBigDecimal("z_sbbjtotal"));
				m.setEmzt_housebj(rs.getBigDecimal("z_gjjbjtotal"));
				m.setEmzt_elsebj(rs.getBigDecimal("emzt_elsebj"));
				m.setEmzt_remark(rs.getString("emzt_remark"));
				m.setEmzt_ifinure(rs.getString("emzt_ifinure"));
				m.setEmzt_addtime(rs.getString("emzt_addtime"));
				m.setEmzt_addname(rs.getString("emzt_addname"));
				m.setEmzt_ifconfirm(rs.getString("emzt_ifconfirm"));
				m.setEmzt_confirmtime(rs.getString("emzt_confirmtime"));
				m.setEmzt_confirmname(rs.getString("emzt_confirmname"));
				m.setEmzt_city(rs.getString("emzt_city"));
				m.setEmzt_sbstand(rs.getString("emzt_sbstand"));
				m.setEmzt_sbstanename(rs.getString("emzt_sbstanename"));
				m.setEmzt_ylcp(rs.getBigDecimal("emzt_ylcp"));
				m.setEmzt_ylop(rs.getBigDecimal("emzt_ylop"));
				m.setEmzt_jlcp(rs.getBigDecimal("emzt_jlcp"));
				m.setEmzt_jlop(rs.getBigDecimal("emzt_jlop"));
				m.setEmzt_gscp(rs.getBigDecimal("emzt_gscp"));
				m.setEmzt_gsop(rs.getBigDecimal("emzt_gsop"));
				m.setEmzt_syecp(rs.getBigDecimal("emzt_syecp"));
				m.setEmzt_syeop(rs.getBigDecimal("emzt_syeop"));
				m.setEmzt_syucp(rs.getBigDecimal("emzt_syucp"));
				m.setEmzt_syuop(rs.getBigDecimal("emzt_syuop"));
				m.setEmzt_housecp(rs.getBigDecimal("emzt_housecp"));
				m.setEmzt_houseop(rs.getBigDecimal("emzt_houseop"));
				m.setEmzt_zhcp(rs.getBigDecimal("emzt_zhcp"));
				m.setEmzt_zhop(rs.getBigDecimal("emzt_zhop"));
				m.setEmzt_ylradix(rs.getString("emzt_ylradix"));
				m.setEmzt_syeradix(rs.getString("emzt_syeradix"));
				m.setEmzt_gsradix(rs.getString("emzt_gsradix"));
				m.setEmzt_syuradix(rs.getString("emzt_syuradix"));
				m.setEmzt_jlradix(rs.getString("emzt_jlradix"));
				m.setEmzt_houseradix(rs.getString("emzt_houseradix"));
				m.setEmzt_zhradix(rs.getString("emzt_zhradix"));
				m.setEmzt_bjradix(rs.getString("emzt_bjradix"));
				m.setEmzt_flfee(rs.getBigDecimal("emzt_flfee"));
				m.setEmzt_flcontent(rs.getString("emzt_flcontent"));
				m.setEmzt_filename(rs.getString("emzt_filename"));
				m.setEmzt_f_confirm(rs.getInt("emzt_f_confirm"));
				m.setEmzt_f_confirmtime(rs.getString("emzt_f_confirmtime"));
				m.setEmzt_f_confirmname(rs.getString("emzt_f_confirmname"));
				m.setEmzt_ylcpp(rs.getString("emzt_ylcpp"));
				m.setEmzt_ylopp(rs.getString("emzt_ylopp"));
				m.setEmzt_jlcpp(rs.getString("emzt_jlcpp"));
				m.setEmzt_jlopp(rs.getString("emzt_jlopp"));
				m.setEmzt_gscpp(rs.getString("emzt_gscpp"));
				m.setEmzt_gsopp(rs.getString("emzt_gsopp"));
				m.setEmzt_syecpp(rs.getString("emzt_syecpp"));
				m.setEmzt_syeopp(rs.getString("emzt_syeopp"));
				m.setEmzt_syucpp(rs.getString("emzt_syucpp"));
				m.setEmzt_syuopp(rs.getString("emzt_syuopp"));
				m.setEmzt_housecpp(rs.getString("emzt_housecpp"));
				m.setEmzt_houseopp(rs.getString("emzt_houseopp"));
				m.setEmzt_sbsingle(rs.getString("emzt_sbsingle"));
				m.setEmzt_housesingle(rs.getString("emzt_housesingle"));
				m.setEmzt_ylstart(rs.getString("emzt_ylstart"));
				m.setEmzt_ylstartBMS(rs.getString("emzt_ylstartBMS"));
				m.setEmzt_ylstop(rs.getString("emzt_ylstop"));
				m.setEmzt_ylstopBMS(rs.getString("emzt_ylstopBMS"));
				m.setEmzt_jlstart(rs.getString("emzt_jlstart"));
				m.setEmzt_jlstartBMS(rs.getString("emzt_jlstartBMS"));
				m.setEmzt_jlstop(rs.getString("emzt_jlstop"));
				m.setEmzt_jlstopBMS(rs.getString("emzt_jlstopBMS"));
				m.setEmzt_gsstart(rs.getString("emzt_gsstart"));
				m.setEmzt_gsstop(rs.getString("emzt_gsstop"));
				m.setEmzt_syestart(rs.getString("emzt_syestart"));
				m.setEmzt_syestop(rs.getString("emzt_syestop"));
				m.setEmzt_syustart(rs.getString("emzt_syustart"));
				m.setEmzt_syustop(rs.getString("emzt_syustop"));
				m.setEmzt_housestart(rs.getString("emzt_housestart"));
				m.setEmzt_housestop(rs.getString("emzt_housestop"));
				m.setEmzt_housestartBMS(rs.getString("emzt_housestartBMS"));
				m.setEmzt_housestopBMS(rs.getString("emzt_housestopBMS"));
				m.setEmzt_sbtitle(rs.getString("emzt_sbtitle"));
				m.setEmzt_housetitle(rs.getString("emzt_housetitle"));
				m.setEmzt_compactstart(rs.getString("emzt_compactstart"));
				m.setEmzt_compactstop(rs.getString("emzt_compactstop"));
				m.setEmzt_outdate(rs.getString("emzt_outdate"));
				m.setEmzt_flag(rs.getString("emzt_flag"));
				m.setEmzt_email(rs.getString("emzt_email"));
				m.setEmzt_spell(rs.getString("emzt_spell"));
				m.setEmzt_r_record(rs.getString("emzt_r_record"));
				m.setEmzt_declaretime(rs.getString("emzt_declaretime"));
				m.setEmzt_declarename(rs.getString("emzt_declarename"));
				m.setEmzt_phone(rs.getString("emzt_phone"));
				m.setEmzt_idcardclass(rs.getString("emzt_idcardclass"));
				m.setEmzt_bchousestart(rs.getString("emzt_bchousestart"));
				m.setEmzt_bchousestop(rs.getString("emzt_bchousestop"));
				m.setEmzt_bchouseradix(rs.getString("emzt_bchouseradix"));
				m.setEmzt_flstart(rs.getString("emzt_flstart"));
				m.setEmzt_flstop(rs.getString("emzt_flstop"));
				m.setEmzt_flfeeinfo(rs.getString("emzt_flfeeinfo"));
				m.setEmzt_elsefeestart(rs.getString("emzt_elsefeestart"));
				m.setEmzt_elsefeestop(rs.getString("emzt_elsefeestop"));
				m.setEmzt_feestart(rs.getString("emzt_feestart"));
				m.setEmzt_feestop(rs.getString("emzt_feestop"));
				m.setEmzt_filefeestart(rs.getString("emzt_filefeestart"));
				m.setEmzt_filefeestop(rs.getString("emzt_filefeestop"));
				m.setEmzt_managefee(rs.getBigDecimal("emzt_managefee"));
				m.setEmzt_cityremark(rs.getString("emzt_cityremark"));
				m.setEmzt_agreement(rs.getString("emzt_agreement"));
				m.setEmzt_iffile(rs.getString("emzt_iffile"));
				m.setEmzt_rdate(rs.getString("emzt_rdate"));
				m.setEmzt_ifsingle(rs.getString("emzt_ifsingle"));
				m.setEmzt_ifunlimited(rs.getString("emzt_ifunlimited"));
				m.setEmzt_outstate(rs.getInt("emzt_outstate"));
				m.setEmzt_ylradixBMS(rs.getString("emzt_ylradixBMS"));
				m.setEmzt_jlradixBMS(rs.getString("emzt_jlradixBMS"));
				m.setEmzt_houseradixBMS(rs.getString("emzt_houseradixBMS"));
				m.setEmzt_od_name(rs.getString("emzt_od_name"));
				m.setEmzt_od_time(rs.getString("emzt_od_time"));
				m.setEmzt_oc_name(rs.getString("emzt_oc_name"));
				m.setEmzt_oc_time(rs.getString("emzt_oc_time"));
				m.setEmzt_filesingle(rs.getString("emzt_filesingle"));
				m.setEmzt_rsingle(rs.getString("emzt_rsingle"));
				m.setEmzt_outfilename(rs.getString("emzt_outfilename"));
				m.setEmzt_indate(rs.getString("emzt_indate"));
				m.setEmzt_t_name(rs.getString("emzt_t_name"));
				m.setEmzt_t_idcard(rs.getString("emzt_t_idcard"));
				m.setEmzt_hjadd(rs.getString("emzt_hjadd"));
				m.setEmzt_education(rs.getString("emzt_education"));
				m.setEmzt_folk(rs.getString("emzt_folk"));
				m.setEmzt_hand(rs.getString("emzt_hand"));
				m.setEmzt_ifshebao(rs.getString("emzt_ifshebao"));
				m.setEmzt_computerid(rs.getString("emzt_computerid"));
				m.setEmzt_ifsbcard(rs.getString("emzt_ifsbcard"));
				m.setEmzt_ifhouse(rs.getString("emzt_ifhouse"));
				m.setEmzt_houseid(rs.getString("emzt_houseid"));
				m.setEmzt_marital(rs.getString("emzt_marital"));
				m.setEmzt_m_name(rs.getString("emzt_m_name"));
				m.setEmzt_m_idcard(rs.getString("emzt_m_idcard"));
				m.setEmzt_fileplace(rs.getString("emzt_fileplace"));
				m.setEmzt_ofileplace(rs.getString("emzt_ofileplace"));
				m.setEmzt_ifda(rs.getString("emzt_ifda"));
				m.setEmzt_ifowed(rs.getString("emzt_ifowed"));
				m.setEmzt_fileendmonth(rs.getInt("emzt_fileendmonth"));
				m.setEmzt_ifrc(rs.getString("emzt_ifrc"));
				m.setEmzt_iffileservice(rs.getString("emzt_iffileservice"));
				m.setEmzt_iffilechange(rs.getString("emzt_iffilechange"));
				m.setEmzt_nifc_reason(rs.getString("emzt_nifc_reason"));
				m.setEmzt_ifhouseseal(rs.getString("emzt_ifhouseseal"));
				m.setEmzt_contactstate(rs.getInt("emzt_contactstate"));
				m.setEmzt_datastate(rs.getInt("emzt_datastate"));
				m.setEmzt_contacttype(rs.getString("emzt_contacttype"));
				m.setEmzt_yl_outstate(rs.getInt("emzt_yl_outstate"));
				m.setEmzt_ylod_name(rs.getString("emzt_ylod_name"));
				m.setEmzt_ylod_time(rs.getString("emzt_ylod_time"));
				m.setEmzt_yloc_name(rs.getString("emzt_yloc_name"));
				m.setEmzt_yloc_time(rs.getString("emzt_yloc_time"));
				m.setEmzt_jl_outstate(rs.getInt("emzt_jl_outstate"));
				m.setEmzt_jlod_name(rs.getString("emzt_jlod_name"));
				m.setEmzt_jlod_time(rs.getString("emzt_jlod_time"));
				m.setEmzt_jloc_name(rs.getString("emzt_jloc_name"));
				m.setEmzt_jloc_time(rs.getString("emzt_jloc_time"));
				m.setEmzt_house_outstate(rs.getInt("emzt_house_outstate"));
				m.setEmzt_houseod_name(rs.getString("emzt_houseod_name"));
				m.setEmzt_houseod_time(rs.getString("emzt_houseod_time"));
				m.setEmzt_houseoc_name(rs.getString("emzt_houseoc_name"));
				m.setEmzt_houseoc_time(rs.getString("emzt_houseoc_time"));
				m.setEmzt_sbc_notice(rs.getString("emzt_sbc_notice"));
				m.setEmzt_data_notice(rs.getString("emzt_data_notice"));
				m.setEmzt_wtgid(rs.getString("emzt_wtgid"));
				m.setEmzt_title(rs.getString("emzt_title"));
				m.setEmzt_adtype(rs.getString("emzt_adtype"));
				m.setEmzt_tel(rs.getString("emzt_tel"));
				m.setEmzt_wtcid(rs.getString("emzt_wtcid"));
				m.setEmzt_outreason(rs.getString("emzt_outreason"));
				m.setEmzt_sex(rs.getString("emzt_sex"));
				m.setEmzt_sb_state(rs.getString("emzt_sb_state"));
				m.setEmzt_house_state(rs.getString("emzt_house_state"));
				m.setEmzt_iffeefile(rs.getString("emzt_iffeefile"));
				if (rs.getInt("emzt_state") == 10) {
					state = "未处理";
					statecolor = "#000";
				} else if (rs.getInt("emzt_state") == 1) {
					state = "已处理";
					statecolor = "#00F";
				} else if (rs.getInt("emzt_state") == 3) {
					state = "退单";
					statecolor = "#F00";
				} else if (rs.getInt("emzt_state") == 11) {
					state = "退单(待跟踪)";
					statecolor = "#F00";
				}
				// else if (rs.getInt("emzt_state") == 0) {
				// state = "未处理";
				// }
				else {
					state = "待处理";
					statecolor = "#090";
				}
				m.setState(state);
				m.setState_color(statecolor);

				if (rs.getInt("emzt_contactstate") == 1) {
					contactstate = "入职网";
				} else if (rs.getInt("emzt_contactstate") == 2) {
					contactstate = "入职网已更新";
				} else if (rs.getInt("emzt_contactstate") == 3) {
					contactstate = "部分确认";
				} else if (rs.getInt("emzt_contactstate") == 4) {
					contactstate = "联系完成";
				} else {
					contactstate = "未处理";
				}
				m.setContactstate(contactstate);

				if (rs.getInt("emzt_datastate") == 1) {
					datastate = "未提交";
				} else if (rs.getInt("emzt_datastate") == 2) {
					datastate = "部分提交";
				} else if (rs.getInt("emzt_datastate") == 3) {
					datastate = "资料齐全";
				} else {
					datastate = "未处理";
				}
				m.setDatastate(datastate);

				if (rs.getInt("emzt_outstate") == 1) {
					outstate = "已反馈";
					outstatecolor = "#00F";
				} else if (rs.getInt("emzt_outstate") == 2) {
					outstate = "反馈中";
					outstatecolor = "#090";
				} else if (rs.getInt("emzt_outstate") == 3) {
					outstate = "部分反馈";
					outstatecolor = "#00F";
				} else {
					outstate = "未反馈";
					outstatecolor = "#000";
				}
				m.setOutstate(outstate);
				m.setOutstate_color(outstatecolor);

				m.setEmzt_tapr_id(rs.getInt("emzt_tapr_id"));
				m.setEmba_id(rs.getInt("emba_id"));

				m.setHouse_cpp(rs.getString("emzt_housecpp"));
				m.setHouse_opp(rs.getString("emzt_houseopp"));
				m.setEmfi_total(rs.getBigDecimal("emfi_total"));
				m.setBalance(rs.getBigDecimal("balance"));
				m.setEmzt_chkstate(rs.getInt("emzt_chkstate"));
				if (rs.getInt("emzt_chkstate")==1) {
					m.setChkstate("已核对");
				}else{
					m.setChkstate("未核对");
				}
				
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取智翼通接口反馈数据
	public List<EmZYTFeedbackModel> getEmZYTFeedbackList(String str) {
		List<EmZYTFeedbackModel> list = new ArrayList<EmZYTFeedbackModel>();
		EmZYTFeedbackModel m = null;
		String sql = "SELECT * FROM EmZYTFeedback WHERE 1=1 " + str;
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmZYTFeedbackModel();
				m.setId(rs.getInt("id"));
				m.setEzfb_billmonth(rs.getString("ezfb_billmonth"));
				m.setEzfb_zytid(rs.getInt("ezfb_zytid"));
				m.setEzfb_listid(rs.getInt("ezfb_listid"));
				m.setEzfb_gid(rs.getString("ezfb_gid"));
				m.setEzfb_name(rs.getString("ezfb_name"));
				m.setEzfb_cid(rs.getString("ezfb_cid"));
				m.setEzfb_company(rs.getString("ezfb_company"));
				m.setEzfb_idcardclass(rs.getString("ezfb_idcardclass"));
				m.setEzfb_idcard(rs.getString("ezfb_idcard"));
				m.setEzfb_class(rs.getString("ezfb_class"));
				m.setEzfb_hbstate(rs.getString("ezfb_hbstate"));
				m.setEzfb_hbstart(rs.getString("ezfb_hbstart"));
				m.setEzfb_hbstop(rs.getString("ezfb_hbstop"));
				m.setEzfb_hbclass(rs.getString("ezfb_hbclass"));
				m.setEzfb_ylstate(rs.getString("ezfb_ylstate"));
				m.setEzfb_yl_cradix(rs.getString("ezfb_yl_cradix"));
				m.setEzfb_yl_oradix(rs.getString("ezfb_yl_oradix"));
				m.setEzfb_ylstart(rs.getString("ezfb_ylstart"));
				m.setEzfb_ylstop(rs.getString("ezfb_ylstop"));
				m.setEzfb_ylcity(rs.getString("ezfb_ylcity"));
				m.setEzfb_housestate(rs.getString("ezfb_housestate"));
				m.setEzfb_house_cradix(rs.getString("ezfb_house_cradix"));
				m.setEzfb_house_oradix(rs.getString("ezfb_house_oradix"));
				m.setEzfb_housecpp(rs.getString("ezfb_housecpp"));
				m.setEzfb_houseopp(rs.getString("ezfb_houseopp"));
				m.setEzfb_housestart(rs.getString("ezfb_housestart"));
				m.setEzfb_housestop(rs.getString("ezfb_housestop"));
				m.setEzfb_housecity(rs.getString("ezfb_housecity"));
				m.setEzfb_compactstate(rs.getString("ezfb_compactstate"));
				m.setEzfb_compactstart(rs.getString("ezfb_compactstart"));
				m.setEzfb_compactstop(rs.getString("ezfb_compactstop"));
				m.setEzfb_feestate(rs.getString("ezfb_feestate"));
				m.setEzfb_feestart(rs.getString("ezfb_feestart"));
				m.setEzfb_feestop(rs.getString("ezfb_feestop"));
				m.setEzfb_filestate(rs.getString("ezfb_filestate"));
				m.setEzfb_filestart(rs.getString("ezfb_filestart"));
				m.setEzfb_filestop(rs.getString("ezfb_filestop"));
				m.setEzfb_elsestate(rs.getString("ezfb_elsestate"));
				m.setEzfb_elsestart(rs.getString("ezfb_elsestart"));
				m.setEzfb_elsestop(rs.getString("ezfb_elsestop"));
				m.setEzfb_cremark(rs.getString("ezfb_cremark"));
				m.setEzfb_cremark_class(rs.getString("ezfb_cremark_class"));
				m.setEzfb_cremark_content(rs.getString("ezfb_cremark_content"));
				m.setEzfb_zytgid(rs.getString("ezfb_zytgid"));
				m.setEzfb_zytcid(rs.getString("ezfb_zytcid"));
				m.setEzfb_ct_gid(rs.getString("ezfb_ct_gid"));
				m.setEzfb_ct_cid(rs.getString("ezfb_ct_cid"));
				m.setEzfb_city(rs.getString("ezfb_city"));
				m.setEzfb_submittime(rs.getString("ezfb_submittime"));
				m.setEzfb_confirmtime(rs.getString("ezfb_confirmtime"));
				m.setEzfb_ylop_pm(rs.getString("ezfb_ylop_pm"));
				m.setEzfb_ylcp_pm(rs.getString("ezfb_ylcp_pm"));
				m.setEzfb_jlop_pm(rs.getString("ezfb_jlop_pm"));
				m.setEzfb_jlcp_pm(rs.getString("ezfb_jlcp_pm"));
				m.setEzfb_gsop_pm(rs.getString("ezfb_gsop_pm"));
				m.setEzfb_gscp_pm(rs.getString("ezfb_gscp_pm"));
				m.setEzfb_syuop_pm(rs.getString("ezfb_syuop_pm"));
				m.setEzfb_syucp_pm(rs.getString("ezfb_syucp_pm"));
				m.setEzfb_syeop_pm(rs.getString("ezfb_syeop_pm"));
				m.setEzfb_syecp_pm(rs.getString("ezfb_syecp_pm"));
				m.setEzfb_houseop_pm(rs.getString("ezfb_houseop_pm"));
				m.setEzfb_housecp_pm(rs.getString("ezfb_housecp_pm"));
				m.setEzfb_bchouseop_pm(rs.getString("ezfb_bchouseop_pm"));
				m.setEzfb_bchousecp_pm(rs.getString("ezfb_bchousecp_pm"));
				m.setEzfb_feetotal(rs.getString("ezfb_feetotal"));
				m.setEzfb_addname(rs.getString("ezfb_addname"));
				m.setEzfb_addtime(rs.getString("ezfb_addtime"));
				m.setEzfb_addfilename(rs.getString("ezfb_addfilename"));
				m.setEzfb_outname(rs.getString("ezfb_outname"));
				m.setEzfb_outtime(rs.getString("ezfb_outtime"));
				m.setEzfb_outfilename(rs.getString("ezfb_outfilename"));
				m.setEzfb_gsstate(rs.getString("ezfb_gsstate"));
				m.setEzfb_gs_cradix(rs.getString("ezfb_gs_cradix"));
				m.setEzfb_gs_oradix(rs.getString("ezfb_gs_oradix"));
				m.setEzfb_gsstart(rs.getString("ezfb_gsstart"));
				m.setEzfb_gsstop(rs.getString("ezfb_gsstop"));
				m.setEzfb_gscity(rs.getString("ezfb_gscity"));
				m.setEzfb_syustate(rs.getString(("ezfb_syustate")));
				m.setEzfb_syu_cradix(rs.getString("ezfb_syu_cradix"));
				m.setEzfb_syu_oradix(rs.getString("ezfb_syu_oradix"));
				m.setEzfb_syustart(rs.getString("ezfb_syustart"));
				m.setEzfb_syustop(rs.getString("ezfb_syustop"));
				m.setEzfb_syucity(rs.getString("ezfb_syucity"));
				m.setEzfb_bchousestate(rs.getString("ezfb_bchousestate"));
				m.setEzfb_bchouse_cradix(rs.getString("ezfb_bchouse_cradix"));
				m.setEzfb_bchouse_oradix(rs.getString("ezfb_bchouse_oradix"));
				m.setEzfb_bchousecpp(rs.getString("ezfb_bchousecpp"));
				m.setEzfb_bchouseopp(rs.getString("ezfb_bchouseopp"));
				m.setEzfb_bchousestart(rs.getString("ezfb_bchousestart"));
				m.setEzfb_bchousestop(rs.getString("ezfb_bchousestop"));
				m.setEzfb_bchousecity(rs.getString("ezfb_bchousecity"));
				m.setEzfb_sb_remark(rs.getString("ezfb_sb_remark"));
				m.setEzfb_house_remark(rs.getString("ezfb_house_remark"));
				m.setEzfb_hbstartBMS(rs.getString("ezfb_hbstartBMS"));
				m.setEzfb_hbstopBMS(rs.getString("ezfb_hbstopBMS"));
				m.setEzfb_ylstartBMS(rs.getString("ezfb_ylstartBMS"));
				m.setEzfb_ylstopBMS(rs.getString("ezfb_ylstopBMS"));
				m.setEzfb_housestartBMS(rs.getString("ezfb_housestartBMS"));
				m.setEzfb_housestopBMS(rs.getString("ezfb_housestopBMS"));
				m.setEzfb_compactstartBMS(rs.getString("ezfb_compactstartBMS"));
				m.setEzfb_compactstopBMS(rs.getString("ezfb_compactstopBMS"));
				m.setEzfb_feestartBMS(rs.getString("ezfb_feestartBMS"));
				m.setEzfb_feestopBMS(rs.getString("ezfb_feestopBMS"));
				m.setEzfb_filestartBMS(rs.getString("ezfb_filestartBMS"));
				m.setEzfb_filestopBMS(rs.getString("ezfb_filestopBMS"));
				m.setEzfb_elsestartBMS(rs.getString("ezfb_elsestartBMS"));
				m.setEzfb_elsestopBMS(rs.getString("ezfb_elsestopBMS"));
				m.setEzfb_ylradixBMS(rs.getString("ezfb_ylradixBMS"));
				m.setEzfb_jlradixBMS(rs.getString("ezfb_jlradixBMS"));
				m.setEzfb_houseradixBMS(rs.getString("ezfb_houseradixBMS"));
				m.setEzfb_yl_failed(rs.getString("ezfb_yl_failed"));
				m.setEzfb_jl_failed(rs.getString("ezfb_jl_failed"));
				m.setEzfb_house_failed(rs.getString("ezfb_house_failed"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取新增数据的报价单新增日期
	public String getEZCGAddtime(int id) {
		String sql = "SELECT TOP 1 ezcg_addtime FROM EmZYTCoGlist WHERE emzt_id="
				+ id;
		String ezcg_addtime = null;

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				ezcg_addtime = DateStringChange.DatetoSting(
						DateStringChange.StringtoDate(
								rs.getString("ezcg_addtime"), "yyyy-mm-dd"),
						"yyyy-mm-dd");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ezcg_addtime = "";
		}
		return ezcg_addtime;
	}

	public int[] getEmBaseNotInId(String idcard) {
		String sql = "SELECT TOP 1 emba_id,emba_tapr_id FROM EmBase WHERE emba_idcard='"
				+ idcard + "' ORDER BY emba_id DESC";
		int[] emba_id = new int[2];

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				emba_id[0] = rs.getInt("emba_id");
				emba_id[1] = rs.getInt("emba_tapr_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			emba_id = null;
		}
		return emba_id;
	}

	public List<Integer> getCoofId(Integer emzt_id) {
		String sql = "select distinct coli_coof_id from CoOfferList where coli_id in(select ezcg_coli_id from EmZYTCoGlist where ezcg_state=1 and emzt_id="
				+ emzt_id + ")";
		List<Integer> list = new ArrayList<Integer>();

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getInt("coli_coof_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Integer> getColiId(Integer emzt_id) {
		String sql = "select ezcg_coli_id from EmZYTCoGlist where ezcg_state=1 and emzt_id="
				+ emzt_id;
		List<Integer> list = new ArrayList<Integer>();

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getInt("ezcg_coli_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getZYTCity() {
		String sql = "SELECT DISTINCT emzt_scity FROM EmZYT ORDER BY emzt_scity";
		List<String> list = new ArrayList<String>();

		try {
			ResultSet rs = conn.GRS(sql);
			list.add("全部");
			while (rs.next()) {
				list.add(rs.getString("emzt_scity"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getZYTClient() {
		String sql = "SELECT DISTINCT emzt_client FROM EmZYT ORDER BY emzt_client";
		List<String> list = new ArrayList<String>();

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("emzt_client"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Integer getUploadCount(String filename) {
		Integer count = 0;
		String sql = "SELECT count(*)cou FROM EmZYT where emzt_filename='"
				+ filename + "'";
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				count = rs.getInt("cou");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<EmbaseModel> getEmbaseInfo(String str) {
		String sql = "select * from embase where 1=1 " + str
				+ " order by emba_id desc";
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		EmbaseModel m = null;
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmbaseModel();
				m.setEmba_id(rs.getInt("emba_id"));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取智翼通接口委托单数据
	public List<EmZYTFeedbackFileModel> getEmZYTFeedbackFileList(String str) {
		List<EmZYTFeedbackFileModel> list = new ArrayList<EmZYTFeedbackFileModel>();
		EmZYTFeedbackFileModel m = null;
		String sql = "SELECT TOP 100 * FROM EmZYTFeedbackFile WHERE 1=1 " + str
				+ " order by ezff_addtime DESC";

		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				m = new EmZYTFeedbackFileModel();
				m.setEzff_id(rs.getInt("ezff_id"));
				m.setEzff_filename(rs.getString("ezff_filename"));
				m.setEzff_fileurl(rs.getString("ezff_fileurl"));
				m.setEzff_addname(rs.getString("ezff_addname"));
				m.setEzff_addtime(rs.getString("ezff_addtime"));

				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 通过智翼通获取社保城市备注获取bms社保模板title
	public String getShebaoFormula(String zyt_title,Integer ifForeigner){
		String shebao_title="";
		String sql = "select * from emzytshebaoformula where ZYTformula='"+zyt_title+"' and ifforeigner="+ifForeigner;
		//System.out.println(sql);
		try {
			ResultSet rs = conn.GRS(sql);
			while (rs.next()) {
				shebao_title = rs.getString("BMSformula");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shebao_title;
	}
}
