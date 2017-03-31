package dal.EmCommissionOutNew;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Conn.dbconn;
import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutZYTModel;

public class EmCommissionOut_OveAutDal {
	// 获取委托城市
	public List<EmCommissionOutChangeModel> getcitylist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutChangeModel> list = new ArrayList<EmCommissionOutChangeModel>();
		String sqlstr = "select distinct wtss_city from EmCommissionOutChange a left join wtoutFee b on b.Wtot_feeid=a.ecoc_ecos_id where ecoc_state in (2,3) and wtss_city is not null order by wtss_city";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
				model.setCity(rs.getString("wtss_city"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托类型
	public List<EmCommissionOutChangeModel> gettypelist(String str) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutChangeModel> list = new ArrayList<EmCommissionOutChangeModel>();
		String sqlstr = "select distinct ecoc_addtype from EmCommissionOutChange a left join wtoutfee b on b.Wtot_feeid=a.ecoc_ecos_id where ecoc_state in (2,3)"+str;
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
				model.setEcoc_addtype(rs.getString("ecoc_addtype"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托机构
	public List<EmCommissionOutChangeModel> getwtjglist(String str) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutChangeModel> list = new ArrayList<EmCommissionOutChangeModel>();
		String sqlstr = "select distinct coab_shortname from EmCommissionOutChange a left join wtoutFee b on b.Wtot_feeid=a.ecoc_ecos_id left join CoAgencyBaseCityRel c on c.cabc_id=b.coab_id left join CoAgencyBase d on d.coab_id=c.cabc_coab_id where ecoc_state in (2,3)  and coab_shortname is not null"+str+"  order by coab_shortname";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
				model.setCoba_shortname(rs.getString("coab_shortname"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取客服名称
	public List<EmCommissionOutChangeModel> getclientlist(String str) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutChangeModel> list = new ArrayList<EmCommissionOutChangeModel>();
		String sqlstr = "select distinct coba_client from EmCommissionOutChange a left join cobase b on b.cid=a.cid left join wtoutfee c on c.Wtot_feeid=a.ecoc_ecos_id where ecoc_state in (2,3)"+str+"  order by coba_client";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
				model.setCoba_client(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取未确认数据
	public List<EmCommissionOutChangeModel> getwtout_aut_list(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutChangeModel> list = new ArrayList<EmCommissionOutChangeModel>();
		String sqlstr = "select coba_shortname,wtss_city,ecoc_addtype,emba_name,a.gid,a.cid,ecoc_id,ecoc_tapr_id,coba_client,ecoc_addtime,coab_shortname,case when ecoc_state=2 then '已发委托单' else '受托部分完成' end st_name from EmCommissionOutChange a left join cobase b on b.cid=a.cid left join wtoutFee c on c.Wtot_feeid=a.ecoc_ecos_id left join embase d on d.gid=a.gid left join CoAgencyBaseCityRel e on e.cabc_id=c.coab_id left join CoAgencyBase f on f.coab_id=e.cabc_coab_id where 1=1  "+str+" order by ecoc_addtime";
		try {
			System.out.print(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
				model.setCoba_shortname(rs.getString("coba_shortname"));
				model.setCity(rs.getString("wtss_city"));
				model.setEcoc_addtype(rs.getString("ecoc_addtype"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setGid(rs.getInt("gid"));
				model.setCid(rs.getInt("cid"));
				model.setEcoc_id(rs.getInt("ecoc_id"));
				model.setEcoc_tapr_id(rs.getInt("ecoc_tapr_id"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEcoc_addtime(rs.getDate("ecoc_addtime"));
				model.setEcoc_statestr(rs.getString("st_name"));
				model.setCoab_company(rs.getString("coab_shortname"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托出数据信息
	public List<EmCommissionOutZYTModel> getci_excel(String id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutZYTModel> list = new ArrayList<EmCommissionOutZYTModel>();
		String sqlstr = "select a.gid t1,emba_name t2,isnull(emba_sex,0) t3,isnull(ecoc_mobile,0) t4,ecoc_phone t5,a.cid t6,coba_company t7,ecoc_phone t8,ecoc_addtype t9,"
				+ "emba_idcardclass t10,emba_idcard t11,'' t12,'' t13,ecoc_email t14,'' t15,substring(replace(isnull(yl_date,''),'-','.'),1,7) T16,"
				+ "'0' T17,cast(ecoc_compact_f as varchar(20)) T18,cast(ecoc_compact_l as varchar(20)) T19,"
				+ "'' T20,ecoc_salary T21,'' T22,'' T23,e.city T24,'' T25,'' T26,'' T27,soin_title T28,'' T29,substring(replace(isnull(yl_date,''),'-','.'),1,7) T30,"
				+ "'0' T31,yl_co_base T32,yl_em_base T33,"
				+ "yl_co_total T34,yl_em_total T35,case when wtss_shebaoco='受托方账户' then '管理费' else '公司自付' end T36,case when wtss_shebaoco='受托方账户' then '管理费' else '个人自付' end T37,'' T38,substring(replace(isnull(jl_date,''),'-','.'),1,7) T39,'0' T40,jl_co_base T41,"
				+ "jl_em_base T42,jl_co_total T43,jl_em_total T44,case when wtss_shebaoco='受托方账户' then '管理费' else '公司自付' end T45,case when wtss_shebaoco='受托方账户' then '管理费' else '个人自付' end T46,'' T47,"
				+ "substring(replace(isnull(sye_date,''),'-','.'),1,7) T48,'0' T49,sye_co_base T50,sye_em_base T51,sye_co_total T52,sye_em_total T53,case when wtss_shebaoco='受托方账户' then '管理费' else '公司自付' end T54,"
				+ "case when wtss_shebaoco='受托方账户' then '管理费' else '个人自付' end T55,'' T56,substring(replace(isnull(gs_date,''),'-','.'),1,7) T57,'0' T58,gs_co_base T59,gs_em_base T60,gs_co_total T61,"
				+ "gs_em_total T62,case when wtss_shebaoco='受托方账户' then '管理费' else '公司自付' end T63,case when wtss_shebaoco='受托方账户' then '管理费' else '个人自付' end T64,'' T65,substring(replace(isnull(syu_date,''),'-','.'),1,7) T66,'0' T67,syu_co_base T68,syu_em_base T69,"
				+ "syu_co_total T70,syu_em_total T71,case when wtss_shebaoco='受托方账户' then '管理费' else '公司自付' end T72,case when wtss_shebaoco='受托方账户' then '管理费' else '个人自付' end T73,'' T74,substring(replace(isnull(house_date,''),'-','.'),1,7) T75,'0' T76,"
				+ "house_co_base T77,house_em_base T78,house_cp T79,house_op T80,house_co_total T81,house_em_total T82,case when wtss_gjjco='受托方账户' then '管理费' else '公司自付' end T83,case when wtss_gjjco='受托方账户' then '管理费' else '个人自付' end T84,'' T85,"
				+ "substring(replace(isnull(bc_date,''),'-','.'),1,7) T86,'0' T87,case when bc_co_total+bc_em_total=0 then 0 else bc_co_base end T88,case when bc_co_total+bc_em_total=0 then 0 else bc_em_base end T89,case when bc_co_total+bc_em_total=0 then '0' else bc_cp end T90,case when bc_co_total+bc_em_total=0 then '0' else bc_op end T91,"
				+ "bc_co_total T92,bc_em_total T93,case when wtss_gjjco='受托方账户' then '管理费' else '公司自付' end T94,case when wtss_gjjco='受托方账户' then '管理费' else '个人自付' end T95,substring(replace(isnull(fee_date,''),'-','.'),1,7) T96,'0' T97,'' T98,ecoc_welfare_sum T99,'' T100,"
				+ "'' T101,fee_total T102,substring(replace(isnull(file_date,''),'-','.'),1,7) T103,'0' T104,file_total T105,"
				+ "'0' T106,'0' T107,'' T108,'0' T109,d.coab_name T110,'' T111,isnull(ecoc_remark,'') T112,'' T113,'' T114,'' T115,'' T116,'' T117,'' T118,'' T119,"
				+ "'' T120,'' T121,'' T122,'' T123,'' T124,'' T125,'' T126,'' T127,'' T128,'' T129,'' T130,'' T131,'' T132,'' T133,"
				+ "'' T134,'' T135,'' T136,'' T137,'' T138,'' T139,'' T140,'' T141,'' T142,'' T143,'' T144,'' T145,'' T146,'' T147,"
				+ "'' T148,'' T149,'' T150,'' T151,'' T152,'' T153,'' T154,'' T155,'' T156,'' T157,'' T158,'' T159,'' T160,'' T161,"
				+ "'' T162,'' T163,'' T164,'' T165,'' T166,'' T167,'' T168,'' T169,'' T170,'' T171,'' T172,'' T173,'' T174,'' T175,"
				+ "'' T176,'' T177,'' T178,'' T179,'' T180,'' T181,'' T182,'' T183,'' T184,'' T185,'' T186,'' T187,'' T188,cast(round(ecoc_file_fee,2) as varchar(30)) T189,"
				+ "cast(round(ecoc_service_fee,2) as varchar(30)) T190,cast(round(ecoc_gjj_sum,2) as varchar(30)) T191,cast(round(ecoc_sum,2) as varchar(30)) T192,cast(round(ecoc_sb_sum,2) as varchar(30)) T193,cast(round(ecoc_sb_em_sum,2) as varchar(30)) T194,cast(round(ecoc_sb_co_sum,2) as varchar(30)) T195,cast(round(ecoc_house_base,2) as varchar(30)) T196,cast(round(ecoc_sb_base,2) as varchar(30)) T197,house_date T198,yl_date T199,d.cabc_id T200"
				+ " from EmCommissionOutChange a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecoc_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join PubState f on a.ecoc_state=f.stateid and a.ecoc_type=f.type"
				+ " left outer join SocialInsurance g on a.ecoc_soin_id=g.soin_id"
				+ " inner join (select eofc_ecoc_id,"
				+ "yl_date=max(case when eofc_name='养老保险' then eofc_start_date else '' end),"
				+ "yl_em_base=max(case when eofc_name='养老保险' then eofc_em_base else 0 end),"
				+ "yl_co_base=max(case when eofc_name='养老保险' then eofc_co_base else 0 end),"
				+ "yl_em_total=max(case when eofc_name='养老保险' then eofc_em_sum else 0 end),"
				+ "yl_co_total=max(case when eofc_name='养老保险' then eofc_co_sum else 0 end),"
				+ "gs_date=max(case when eofc_name='工伤保险' then eofc_start_date else '' end),"
				+ "gs_em_base=max(case when eofc_name='工伤保险' then eofc_em_base else 0 end),"
				+ "gs_co_base=max(case when eofc_name='工伤保险' then eofc_co_base else 0 end),"
				+ "gs_em_total=max(case when eofc_name='工伤保险' then eofc_em_sum else 0 end),"
				+ "gs_co_total=max(case when eofc_name='工伤保险' then eofc_co_sum else 0 end),"
				+ "jl_date=max(case when eofc_name='医疗保险' then eofc_start_date else '' end),"
				+ "jl_em_base=max(case when eofc_name='医疗保险' then eofc_em_base else 0 end),"
				+ "jl_co_base=max(case when eofc_name='医疗保险' then eofc_co_base else 0 end),"
				+ "jl_em_total=max(case when eofc_name='医疗保险' then eofc_em_sum else 0 end),"
				+ "jl_co_total=max(case when eofc_name='医疗保险' then eofc_co_sum else 0 end),"
				+ "sye_date=max(case when eofc_name='失业保险' then eofc_start_date else '' end),"
				+ "sye_em_base=max(case when eofc_name='失业保险' then eofc_em_base else 0 end),"
				+ "sye_co_base=max(case when eofc_name='失业保险' then eofc_co_base else 0 end),"
				+ "sye_em_total=max(case when eofc_name='失业保险' then eofc_em_sum else 0 end),"
				+ "sye_co_total=max(case when eofc_name='失业保险' then eofc_co_sum else 0 end),"
				+ "syu_date=max(case when eofc_name='生育保险' then eofc_start_date else '' end),"
				+ "syu_em_base=max(case when eofc_name='生育保险' then eofc_em_base else 0 end),"
				+ "syu_co_base=max(case when eofc_name='生育保险' then eofc_co_base else 0 end),"
				+ "syu_em_total=max(case when eofc_name='生育保险' then eofc_em_sum else 0 end),"
				+ "syu_co_total=max(case when eofc_name='生育保险' then eofc_co_sum else 0 end),"
				+ "house_date=max(case when eofc_name='住房公积金' then eofc_start_date else '' end),"
				+ "house_em_base=max(case when eofc_name='住房公积金' then eofc_em_base else 0 end),"
				+ "house_co_base=max(case when eofc_name='住房公积金' then eofc_co_base else 0 end),"
				+ "house_cp=max(case when eofc_name='住房公积金' then eofc_cp else '0' end),"
				+ "house_op=max(case when eofc_name='住房公积金' then eofc_op else '0' end),"
				+ "house_em_total=max(case when eofc_name='住房公积金' then eofc_em_sum else 0 end),"
				+ "house_co_total=max(case when eofc_name='住房公积金' then eofc_co_sum else 0 end),"
				+ "bc_date=max(case when eofc_name='补充公积金' then eofc_start_date else '' end),"
				+ "bc_em_base=max(case when eofc_name='补充公积金' then eofc_em_base else 0 end),"
				+ "bc_co_base=max(case when eofc_name='补充公积金' then eofc_co_base else 0 end),"
				+ "bc_cp=max(case when eofc_name='补充公积金' then eofc_cp else '0' end),"
				+ "bc_op=max(case when eofc_name='补充公积金' then eofc_op else '0' end),"
				+ "bc_em_total=max(case when eofc_name='补充公积金' then eofc_em_sum else 0 end),"
				+ "bc_co_total=max(case when eofc_name='补充公积金' then eofc_co_sum else 0 end),"
				+ "fee_date=max(case when eofc_name='服务费' then eofc_start_date else '' end),"
				+ "fee_total=max(case when eofc_name='服务费' then eofc_month_sum else 0 end),"
				+ "file_date=max(case when eofc_name='档案费' then eofc_start_date else '' end),"
				+ "file_total=max(case when eofc_name='档案费' then eofc_month_sum else 0 end)"
				+ " from EmCommissionOutFeeDetailChange group by eofc_ecoc_id) h on h.eofc_ecoc_id=a.ecoc_id"
				+ " where ecoc_id=" + id + " and typename='后道状态'";
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutZYTModel model = new EmCommissionOutZYTModel();
				model.setT1(rs.getString("t1"));
				model.setT2(rs.getString("t2"));
				model.setT3(rs.getString("t3"));
				model.setT4(rs.getString("t4"));
				model.setT5(rs.getString("t5"));
				model.setT6(rs.getString("t6"));
				model.setT7(rs.getString("t7"));
				model.setT8(rs.getString("t8"));
				model.setT9(rs.getString("t9"));
				model.setT10(rs.getString("t10"));
				model.setT11(rs.getString("t11"));
				model.setT12(rs.getString("t12"));
				model.setT13(rs.getString("t13"));
				model.setT14(rs.getString("t14"));
				model.setT15(rs.getString("t15"));
				model.setT16(rs.getString("t16"));
				model.setT17(rs.getString("t17"));
				model.setT18(rs.getString("t18"));
				model.setT19(rs.getString("t19"));
				model.setT20(rs.getString("t20"));
				model.setT21(rs.getString("t21"));
				model.setT22(rs.getString("t22"));
				model.setT23(rs.getString("t23"));
				model.setT24(rs.getString("t24"));
				model.setT25(rs.getString("t25"));
				model.setT26(rs.getString("t26"));
				model.setT27(rs.getString("t27"));
				model.setT28(rs.getString("t28"));
				model.setT29(rs.getString("t29"));
				model.setT30(rs.getString("t30"));
				model.setT31(rs.getString("t31"));
				model.setT32(rs.getString("t32"));
				model.setT33(rs.getString("t33"));
				model.setT34(rs.getString("t34"));
				model.setT35(rs.getString("t35"));
				model.setT36(rs.getString("t36"));
				model.setT37(rs.getString("t37"));
				model.setT38(rs.getString("t38"));
				model.setT39(rs.getString("t39"));
				model.setT40(rs.getString("t40"));
				model.setT41(rs.getString("t41"));
				model.setT42(rs.getString("t42"));
				model.setT43(rs.getString("t43"));
				model.setT44(rs.getString("t44"));
				model.setT45(rs.getString("t45"));
				model.setT46(rs.getString("t46"));
				model.setT47(rs.getString("t47"));
				model.setT48(rs.getString("t48"));
				model.setT49(rs.getString("t49"));
				model.setT50(rs.getString("t50"));
				model.setT51(rs.getString("t51"));
				model.setT52(rs.getString("t52"));
				model.setT53(rs.getString("t53"));
				model.setT54(rs.getString("t54"));
				model.setT55(rs.getString("t55"));
				model.setT56(rs.getString("t56"));
				model.setT57(rs.getString("t57"));
				model.setT58(rs.getString("t58"));
				model.setT59(rs.getString("t59"));
				model.setT60(rs.getString("t60"));
				model.setT61(rs.getString("t61"));
				model.setT62(rs.getString("t62"));
				model.setT63(rs.getString("t63"));
				model.setT64(rs.getString("t64"));
				model.setT65(rs.getString("t65"));
				model.setT66(rs.getString("t66"));
				model.setT67(rs.getString("t67"));
				model.setT68(rs.getString("t68"));
				model.setT69(rs.getString("t69"));
				model.setT70(rs.getString("t70"));
				model.setT71(rs.getString("t71"));
				model.setT72(rs.getString("t72"));
				model.setT73(rs.getString("t73"));
				model.setT74(rs.getString("t74"));
				model.setT75(rs.getString("t75"));
				model.setT76(rs.getString("t76"));
				model.setT77(rs.getString("t77"));
				model.setT78(rs.getString("t78"));
				model.setT79(rs.getString("t79"));
				model.setT80(rs.getString("t80"));
				model.setT81(rs.getString("t81"));
				model.setT82(rs.getString("t82"));
				model.setT83(rs.getString("t83"));
				model.setT84(rs.getString("t84"));
				model.setT85(rs.getString("t85"));
				model.setT86(rs.getString("t86"));
				model.setT87(rs.getString("t87"));
				model.setT88(rs.getString("t88"));
				model.setT89(rs.getString("t89"));
				model.setT90(rs.getString("t90"));
				model.setT91(rs.getString("t91"));
				model.setT92(rs.getString("t92"));
				model.setT93(rs.getString("t93"));
				model.setT94(rs.getString("t94"));
				model.setT95(rs.getString("t95"));
				model.setT96(rs.getString("t96"));
				model.setT97(rs.getString("t97"));
				model.setT98(rs.getString("t98"));
				model.setT99(rs.getString("t99"));
				model.setT100(rs.getString("t100"));
				model.setT101(rs.getString("t101"));
				model.setT102(rs.getString("t102"));
				model.setT103(rs.getString("t103"));
				model.setT104(rs.getString("t104"));
				model.setT105(rs.getString("t105"));
				model.setT106(rs.getString("t106"));
				model.setT107(rs.getString("t107"));
				model.setT108(rs.getString("t108"));
				model.setT109(rs.getString("t109"));
				model.setT110(rs.getString("t110"));
				model.setT111(rs.getString("t111"));
				model.setT112(rs.getString("t112"));
				model.setT113(rs.getString("t113"));
				model.setT114(rs.getString("t114"));
				model.setT115(rs.getString("t115"));
				model.setT116(rs.getString("t116"));
				model.setT117(rs.getString("t117"));
				model.setT118(rs.getString("t118"));
				model.setT119(rs.getString("t119"));
				model.setT120(rs.getString("t120"));
				model.setT121(rs.getString("t121"));
				model.setT122(rs.getString("t122"));
				model.setT123(rs.getString("t123"));
				model.setT124(rs.getString("t124"));
				model.setT125(rs.getString("t125"));
				model.setT126(rs.getString("t126"));
				model.setT127(rs.getString("t127"));
				model.setT128(rs.getString("t128"));
				model.setT129(rs.getString("t129"));
				model.setT130(rs.getString("t130"));
				model.setT131(rs.getString("t131"));
				model.setT132(rs.getString("t132"));
				model.setT133(rs.getString("t133"));
				model.setT134(rs.getString("t134"));
				model.setT135(rs.getString("t135"));
				model.setT136(rs.getString("t136"));
				model.setT137(rs.getString("t137"));
				model.setT138(rs.getString("t138"));
				model.setT139(rs.getString("t139"));
				model.setT140(rs.getString("t140"));
				model.setT141(rs.getString("t141"));
				model.setT142(rs.getString("t142"));
				model.setT143(rs.getString("t143"));
				model.setT144(rs.getString("t144"));
				model.setT145(rs.getString("t145"));
				model.setT146(rs.getString("t146"));
				model.setT147(rs.getString("t147"));
				model.setT148(rs.getString("t148"));
				model.setT149(rs.getString("t149"));
				model.setT150(rs.getString("t150"));
				model.setT151(rs.getString("t151"));
				model.setT152(rs.getString("t152"));
				model.setT153(rs.getString("t153"));
				model.setT154(rs.getString("t154"));
				model.setT155(rs.getString("t155"));
				model.setT156(rs.getString("t156"));
				model.setT157(rs.getString("t157"));
				model.setT158(rs.getString("t158"));
				model.setT159(rs.getString("t159"));
				model.setT160(rs.getString("t160"));
				model.setT161(rs.getString("t161"));
				model.setT162(rs.getString("t162"));
				model.setT163(rs.getString("t163"));
				model.setT164(rs.getString("t164"));
				model.setT165(rs.getString("t165"));
				model.setT166(rs.getString("t166"));
				model.setT167(rs.getString("t167"));
				model.setT168(rs.getString("t168"));
				model.setT169(rs.getString("t169"));
				model.setT170(rs.getString("t170"));
				model.setT171(rs.getString("t171"));
				model.setT172(rs.getString("t172"));
				model.setT173(rs.getString("t173"));
				model.setT174(rs.getString("t174"));
				model.setT175(rs.getString("t175"));
				model.setT176(rs.getString("t176"));
				model.setT177(rs.getString("t177"));
				model.setT178(rs.getString("t178"));
				model.setT179(rs.getString("t179"));
				model.setT180(rs.getString("t180"));
				model.setT181(rs.getString("t181"));
				model.setT182(rs.getString("t182"));
				model.setT183(rs.getString("t183"));
				model.setT184(rs.getString("t184"));
				model.setT185(rs.getString("t185"));
				model.setT186(rs.getString("t186"));
				model.setT187(rs.getString("t187"));
				model.setT188(rs.getString("t188"));
				model.setT189(rs.getString("t189"));
				model.setT190(rs.getString("t190"));
				model.setT191(rs.getString("t191"));
				model.setT192(rs.getString("t192"));
				model.setT193(rs.getString("t193"));
				model.setT194(rs.getString("t194"));
				model.setT195(rs.getString("t195"));
				model.setT196(rs.getString("t196"));
				model.setT197(rs.getString("t197"));
				model.setT198(rs.getString("t198"));
				model.setT199(rs.getString("t199"));
				model.setT200(rs.getString("t200"));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取委托出数据信息
	public List<EmCommissionOutZYTModel> getci_exceldim(String id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutZYTModel> list = new ArrayList<EmCommissionOutZYTModel>();
		String sqlstr = "select a.gid t1,emba_name t2,isnull(emba_sex,0) t3,isnull(ecoc_mobile,0) t4,ecoc_phone t5,a.cid t6,coba_company t7,ecoc_phone t8,'终止' t9,"
				+ "emba_idcardclass t10,emba_idcard t11,'' t12,'' t13,ecoc_email t14,cast(Convert(varchar(10),ecoc_stop_date,120) as varchar(20)) t15,'0' T16,"
				+ "substring(replace(isnull(yl_date,''),'-','.'),1,7) T17,cast(ecoc_compact_f as varchar(20)) T18,'' T19,"
				+ "'1' T20,'0' T21,'' T22,e.city T23,e.city T24,'0' T25,'' T26,'' T27,'0' T28,'0' T29,substring(replace(isnull(yl_date,''),'-','.'),1,7) T30,"
				+ "'0' T31,'0' T32,'管理费' T33,"
				+ "'管理费' T34,'0' T35,'0' T36,substring(replace(isnull(jl_date,''),'-','.'),1,7) T37,'0' T38,'0' T39,'0' T40,'0' T41,"
				+ "'管理费' T42,'管理费' T43,'0' T44,'0' T45,substring(replace(isnull(sye_date,''),'-','.'),1,7) T46,'0' T47,"
				+ "'0' T48,'0' T49,'0' T50,'管理费' T51,'管理费' T52,'0' T53,'0' T54,"
				+ "substring(replace(isnull(gs_date,''),'-','.'),1,7) T55,'0' T56,'0' T57,'0' T58,'0' T59,'管理费' T60,'管理费' T61,"
				+ "'0' T62,'0' T63,substring(replace(isnull(syu_date,''),'-','.'),1,7) T64,'0' T65,'0' T66,'0' T67,'0' T68,'管理费' T69,"
				+ "'管理费' T70,'0' T71,'0' T72,substring(replace(isnull(house_date,''),'-','.'),1,7) T73,'0' T74,'0' T75,'' T76,"
				+ "'' T77,'0' T78,'0' T79,'管理费' T80,'管理费' T81,'0' T82,'0' T83,substring(replace(isnull(bc_date,''),'-','.'),1,7) T84,'0' T85,"
				+ "'0' T86,'' T87,'' T88,'0' T89,'0' T90,'管理费' T91,"
				+ "'管理费' T92,'0' T93,'' T94,'0' T95,'0' T96,'0' T97,'0' T98,fee_total T99,'0' T100,"
				+ "substring(replace(isnull(file_date,''),'-','.'),1,7) T101,file_total T102,'0' T103,'0' T104,'0' T105,"
				+ "'' T106,'' T107,ecoc_stop_cause T108,ecoc_remark T109,'' T110,'' T111,'' T112,'' T113,'' T114,'' T115,'' T116,'' T117,'' T118,'' T119,"
				+ "'' T120,'' T121,'' T122,'' T123,'' T124,'' T125,'' T126,'' T127,'' T128,'' T129,'' T130,'' T131,'' T132,'' T133,"
				+ "'' T134,'' T135,'' T136,'' T137,'' T138,'' T139,'' T140,'' T141,'' T142,'' T143,'' T144,'' T145,'' T146,'' T147,"
				+ "'' T148,'' T149,'' T150,'' T151,'' T152,'' T153,'' T154,'' T155,'' T156,'' T157,'' T158,'' T159,'' T160,'' T161,"
				+ "'' T162,'' T163,'' T164,'' T165,'' T166,'' T167,'' T168,'' T169,'' T170,'' T171,'' T172,'' T173,'' T174,'' T175,"
				+ "'' T176,'' T177,'' T178,'' T179,'' T180,'' T181,'' T182,'' T183,'' T184,'' T185,'' T186,'' T187,'' T188,'' T189,"
				+ "'' T190,'' T191,'' T192,'' T193,'' T194,d.cabc_id T195,ecoc_remark T196,ecoc_stop_cause T197,house_date T198,yl_date T199,ecoc_stop_date T200"
				+ " from EmCommissionOutChange a inner join EmBase b on a.gid=b.gid"
				+ " inner join CoBase c on a.cid=c.CID"
				+ " inner join View_wtservicestandard d on a.ecoc_ecos_id=d.wtot_feeid"
				+ " inner join CoAgencyBaseCityRel_view e on d.cabc_id=e.cabc_id"
				+ " inner join PubState f on a.ecoc_state=f.stateid and a.ecoc_type=f.type"
				+ " left outer join SocialInsurance g on a.ecoc_soin_id=g.soin_id"
				+ " inner join (select eofc_ecoc_id,"
				+ "yl_date=max(case when eofc_name='养老保险' then eofc_stop_date else '' end),"
				+ "yl_em_base=max(case when eofc_name='养老保险' then eofc_em_base else 0 end),"
				+ "yl_co_base=max(case when eofc_name='养老保险' then eofc_co_base else 0 end),"
				+ "yl_em_total=max(case when eofc_name='养老保险' then eofc_em_sum else 0 end),"
				+ "yl_co_total=max(case when eofc_name='养老保险' then eofc_co_sum else 0 end),"
				+ "gs_date=max(case when eofc_name='工伤保险' then eofc_stop_date else '' end),"
				+ "gs_em_base=max(case when eofc_name='工伤保险' then eofc_em_base else 0 end),"
				+ "gs_co_base=max(case when eofc_name='工伤保险' then eofc_co_base else 0 end),"
				+ "gs_em_total=max(case when eofc_name='工伤保险' then eofc_em_sum else 0 end),"
				+ "gs_co_total=max(case when eofc_name='工伤保险' then eofc_co_sum else 0 end),"
				+ "jl_date=max(case when eofc_name='医疗保险' then eofc_stop_date else '' end),"
				+ "jl_em_base=max(case when eofc_name='医疗保险' then eofc_em_base else 0 end),"
				+ "jl_co_base=max(case when eofc_name='医疗保险' then eofc_co_base else 0 end),"
				+ "jl_em_total=max(case when eofc_name='医疗保险' then eofc_em_sum else 0 end),"
				+ "jl_co_total=max(case when eofc_name='医疗保险' then eofc_co_sum else 0 end),"
				+ "sye_date=max(case when eofc_name='失业保险' then eofc_stop_date else '' end),"
				+ "sye_em_base=max(case when eofc_name='失业保险' then eofc_em_base else 0 end),"
				+ "sye_co_base=max(case when eofc_name='失业保险' then eofc_co_base else 0 end),"
				+ "sye_em_total=max(case when eofc_name='失业保险' then eofc_em_sum else 0 end),"
				+ "sye_co_total=max(case when eofc_name='失业保险' then eofc_co_sum else 0 end),"
				+ "syu_date=max(case when eofc_name='生育保险' then eofc_stop_date else '' end),"
				+ "syu_em_base=max(case when eofc_name='生育保险' then eofc_em_base else 0 end),"
				+ "syu_co_base=max(case when eofc_name='生育保险' then eofc_co_base else 0 end),"
				+ "syu_em_total=max(case when eofc_name='生育保险' then eofc_em_sum else 0 end),"
				+ "syu_co_total=max(case when eofc_name='生育保险' then eofc_co_sum else 0 end),"
				+ "house_date=max(case when eofc_name='住房公积金' then eofc_stop_date else '' end),"
				+ "house_em_base=max(case when eofc_name='住房公积金' then eofc_em_base else 0 end),"
				+ "house_co_base=max(case when eofc_name='住房公积金' then eofc_co_base else 0 end),"
				+ "house_cp=max(case when eofc_name='住房公积金' then eofc_cp else '0' end),"
				+ "house_op=max(case when eofc_name='住房公积金' then eofc_op else '0' end),"
				+ "house_em_total=max(case when eofc_name='住房公积金' then eofc_em_sum else 0 end),"
				+ "house_co_total=max(case when eofc_name='住房公积金' then eofc_co_sum else 0 end),"
				+ "bc_date=max(case when eofc_name='补充公积金' then eofc_stop_date else '' end),"
				+ "bc_em_base=max(case when eofc_name='补充公积金' then eofc_em_base else 0 end),"
				+ "bc_co_base=max(case when eofc_name='补充公积金' then eofc_co_base else 0 end),"
				+ "bc_cp=max(case when eofc_name='补充公积金' then eofc_cp else '0' end),"
				+ "bc_op=max(case when eofc_name='补充公积金' then eofc_op else '0' end),"
				+ "bc_em_total=max(case when eofc_name='补充公积金' then eofc_em_sum else 0 end),"
				+ "bc_co_total=max(case when eofc_name='补充公积金' then eofc_co_sum else 0 end),"
				+ "fee_date=max(case when eofc_name='服务费' then eofc_stop_date else '' end),"
				+ "fee_total=max(case when eofc_name='服务费' then eofc_month_sum else 0 end),"
				+ "file_date=max(case when eofc_name='档案费' then eofc_stop_date else '' end),"
				+ "file_total=max(case when eofc_name='档案费' then eofc_month_sum else 0 end)"
				+ " from EmCommissionOutFeeDetailChange group by eofc_ecoc_id) h on h.eofc_ecoc_id=a.ecoc_id"
				+ " where ecoc_id=" + id + " and typename='后道状态'";
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutZYTModel model = new EmCommissionOutZYTModel();
				model.setT1(rs.getString("t1"));
				model.setT2(rs.getString("t2"));
				model.setT3(rs.getString("t3"));
				model.setT4(rs.getString("t4"));
				model.setT5(rs.getString("t5"));
				model.setT6(rs.getString("t6"));
				model.setT7(rs.getString("t7"));
				model.setT8(rs.getString("t8"));
				model.setT9(rs.getString("t9"));
				model.setT10(rs.getString("t10"));
				model.setT11(rs.getString("t11"));
				model.setT12(rs.getString("t12"));
				model.setT13(rs.getString("t13"));
				model.setT14(rs.getString("t14"));
				model.setT15(rs.getString("t15"));
				model.setT16(rs.getString("t16"));
				model.setT17(rs.getString("t17"));
				model.setT18(rs.getString("t18"));
				model.setT19(rs.getString("t19"));
				model.setT20(rs.getString("t20"));
				model.setT21(rs.getString("t21"));
				model.setT22(rs.getString("t22"));
				model.setT23(rs.getString("t23"));
				model.setT24(rs.getString("t24"));
				model.setT25(rs.getString("t25"));
				model.setT26(rs.getString("t26"));
				model.setT27(rs.getString("t27"));
				model.setT28(rs.getString("t28"));
				model.setT29(rs.getString("t29"));
				model.setT30(rs.getString("t30"));
				model.setT31(rs.getString("t31"));
				model.setT32(rs.getString("t32"));
				model.setT33(rs.getString("t33"));
				model.setT34(rs.getString("t34"));
				model.setT35(rs.getString("t35"));
				model.setT36(rs.getString("t36"));
				model.setT37(rs.getString("t37"));
				model.setT38(rs.getString("t38"));
				model.setT39(rs.getString("t39"));
				model.setT40(rs.getString("t40"));
				model.setT41(rs.getString("t41"));
				model.setT42(rs.getString("t42"));
				model.setT43(rs.getString("t43"));
				model.setT44(rs.getString("t44"));
				model.setT45(rs.getString("t45"));
				model.setT46(rs.getString("t46"));
				model.setT47(rs.getString("t47"));
				model.setT48(rs.getString("t48"));
				model.setT49(rs.getString("t49"));
				model.setT50(rs.getString("t50"));
				model.setT51(rs.getString("t51"));
				model.setT52(rs.getString("t52"));
				model.setT53(rs.getString("t53"));
				model.setT54(rs.getString("t54"));
				model.setT55(rs.getString("t55"));
				model.setT56(rs.getString("t56"));
				model.setT57(rs.getString("t57"));
				model.setT58(rs.getString("t58"));
				model.setT59(rs.getString("t59"));
				model.setT60(rs.getString("t60"));
				model.setT61(rs.getString("t61"));
				model.setT62(rs.getString("t62"));
				model.setT63(rs.getString("t63"));
				model.setT64(rs.getString("t64"));
				model.setT65(rs.getString("t65"));
				model.setT66(rs.getString("t66"));
				model.setT67(rs.getString("t67"));
				model.setT68(rs.getString("t68"));
				model.setT69(rs.getString("t69"));
				model.setT70(rs.getString("t70"));
				model.setT71(rs.getString("t71"));
				model.setT72(rs.getString("t72"));
				model.setT73(rs.getString("t73"));
				model.setT74(rs.getString("t74"));
				model.setT75(rs.getString("t75"));
				model.setT76(rs.getString("t76"));
				model.setT77(rs.getString("t77"));
				model.setT78(rs.getString("t78"));
				model.setT79(rs.getString("t79"));
				model.setT80(rs.getString("t80"));
				model.setT81(rs.getString("t81"));
				model.setT82(rs.getString("t82"));
				model.setT83(rs.getString("t83"));
				model.setT84(rs.getString("t84"));
				model.setT85(rs.getString("t85"));
				model.setT86(rs.getString("t86"));
				model.setT87(rs.getString("t87"));
				model.setT88(rs.getString("t88"));
				model.setT89(rs.getString("t89"));
				model.setT90(rs.getString("t90"));
				model.setT91(rs.getString("t91"));
				model.setT92(rs.getString("t92"));
				model.setT93(rs.getString("t93"));
				model.setT94(rs.getString("t94"));
				model.setT95(rs.getString("t95"));
				model.setT96(rs.getString("t96"));
				model.setT97(rs.getString("t97"));
				model.setT98(rs.getString("t98"));
				model.setT99(rs.getString("t99"));
				model.setT100(rs.getString("t100"));
				model.setT101(rs.getString("t101"));
				model.setT102(rs.getString("t102"));
				model.setT103(rs.getString("t103"));
				model.setT104(rs.getString("t104"));
				model.setT105(rs.getString("t105"));
				model.setT106(rs.getString("t106"));
				model.setT107(rs.getString("t107"));
				model.setT108(rs.getString("t108"));
				model.setT109(rs.getString("t109"));
				model.setT110(rs.getString("t110"));
				model.setT111(rs.getString("t111"));
				model.setT112(rs.getString("t112"));
				model.setT113(rs.getString("t113"));
				model.setT114(rs.getString("t114"));
				model.setT115(rs.getString("t115"));
				model.setT116(rs.getString("t116"));
				model.setT117(rs.getString("t117"));
				model.setT118(rs.getString("t118"));
				model.setT119(rs.getString("t119"));
				model.setT120(rs.getString("t120"));
				model.setT121(rs.getString("t121"));
				model.setT122(rs.getString("t122"));
				model.setT123(rs.getString("t123"));
				model.setT124(rs.getString("t124"));
				model.setT125(rs.getString("t125"));
				model.setT126(rs.getString("t126"));
				model.setT127(rs.getString("t127"));
				model.setT128(rs.getString("t128"));
				model.setT129(rs.getString("t129"));
				model.setT130(rs.getString("t130"));
				model.setT131(rs.getString("t131"));
				model.setT132(rs.getString("t132"));
				model.setT133(rs.getString("t133"));
				model.setT134(rs.getString("t134"));
				model.setT135(rs.getString("t135"));
				model.setT136(rs.getString("t136"));
				model.setT137(rs.getString("t137"));
				model.setT138(rs.getString("t138"));
				model.setT139(rs.getString("t139"));
				model.setT140(rs.getString("t140"));
				model.setT141(rs.getString("t141"));
				model.setT142(rs.getString("t142"));
				model.setT143(rs.getString("t143"));
				model.setT144(rs.getString("t144"));
				model.setT145(rs.getString("t145"));
				model.setT146(rs.getString("t146"));
				model.setT147(rs.getString("t147"));
				model.setT148(rs.getString("t148"));
				model.setT149(rs.getString("t149"));
				model.setT150(rs.getString("t150"));
				model.setT151(rs.getString("t151"));
				model.setT152(rs.getString("t152"));
				model.setT153(rs.getString("t153"));
				model.setT154(rs.getString("t154"));
				model.setT155(rs.getString("t155"));
				model.setT156(rs.getString("t156"));
				model.setT157(rs.getString("t157"));
				model.setT158(rs.getString("t158"));
				model.setT159(rs.getString("t159"));
				model.setT160(rs.getString("t160"));
				model.setT161(rs.getString("t161"));
				model.setT162(rs.getString("t162"));
				model.setT163(rs.getString("t163"));
				model.setT164(rs.getString("t164"));
				model.setT165(rs.getString("t165"));
				model.setT166(rs.getString("t166"));
				model.setT167(rs.getString("t167"));
				model.setT168(rs.getString("t168"));
				model.setT169(rs.getString("t169"));
				model.setT170(rs.getString("t170"));
				model.setT171(rs.getString("t171"));
				model.setT172(rs.getString("t172"));
				model.setT173(rs.getString("t173"));
				model.setT174(rs.getString("t174"));
				model.setT175(rs.getString("t175"));
				model.setT176(rs.getString("t176"));
				model.setT177(rs.getString("t177"));
				model.setT178(rs.getString("t178"));
				model.setT179(rs.getString("t179"));
				model.setT180(rs.getString("t180"));
				model.setT181(rs.getString("t181"));
				model.setT182(rs.getString("t182"));
				model.setT183(rs.getString("t183"));
				model.setT184(rs.getString("t184"));
				model.setT185(rs.getString("t185"));
				model.setT186(rs.getString("t186"));
				model.setT187(rs.getString("t187"));
				model.setT188(rs.getString("t188"));
				model.setT189(rs.getString("t189"));
				model.setT190(rs.getString("t190"));
				model.setT191(rs.getString("t191"));
				model.setT192(rs.getString("t192"));
				model.setT193(rs.getString("t193"));
				model.setT194(rs.getString("t194"));
				model.setT195(rs.getString("t195"));
				model.setT196(rs.getString("t196"));
				model.setT197(rs.getString("t197"));
				model.setT198(rs.getString("t198"));
				model.setT199(rs.getString("t199"));
				model.setT200(rs.getString("t200"));

				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	public List<CoAgencyLinkmanModel> getLinkManList(Integer cabc_id) {
		List<CoAgencyLinkmanModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "select cali_id,cali_datatype,cali_linkman,cali_name,cali_ename,"
				+ "cali_nickname,cali_job,cali_duty,cali_tel,cali_tel1,cali_tel2,cali_mobile,"
				+ "cali_mobile1,cali_mobile2,cali_fax,cali_email,cali_email1,cali_email2,cali_sex,"
				+ "cali_address,cali_birth,cali_folk,cali_origo,cali_hjaddress,cali_marriage,cali_height,"
				+ "cali_figure,cali_character,cali_weibo,cali_weixin,cali_qq,cali_degree,cali_school,"
				+ "cali_schoolcity,cali_specialty,cali_lastindustry,cali_lastworktime,cali_lastjob,"
				+ "cali_lastcompany,cali_lastcompanyAddress,cali_developmentPlan,cali_hobby,"
				+ "cali_hobbyActivities,cali_hobbyClub,cali_communityActivities,cali_religiousBelief,"
				+ "cali_conversationTopics,cali_hobbyFood,cali_diet,cali_ifOpInvitationMeals,"
				+ "cali_ifOpSengGift,cali_notTalkAbout,cali_attentionProblem,cali_personality,"
				+ "cali_remark,cali_vip,cali_addname,cali_modname,cali_modtime,"
				+ "cali_delname,cali_deltime,cali_delReason,cali_state from CoAgencyLinkman a"
				+ " inner join CoAgencyBaseLinkRel b on a.cali_id=b.cabl_cali_id"
				+ " where cabl_coab_id=(select cabc_coab_id"
				+ " from CoAgencyBaseCityRel where cabc_id=" + cabc_id + ")";

		try {
			System.out.println(sql);
			list = db.find(sql, CoAgencyLinkmanModel.class,
					dbconn.parseSmap(CoAgencyLinkmanModel.class));

			if (list.size() == 0) {
				list.add(new CoAgencyLinkmanModel());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取委托数据统计
	public EmCommissionOutChangeModel getwt_state_list()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		EmCommissionOutChangeModel list = new EmCommissionOutChangeModel();
		String sqlstr = "select (select COUNT(*) from EmCommissionOutChange where ecoc_state=2 and ecoc_addtype='新增') xa,(select COUNT(*) from EmCommissionOutChange where ecoc_state=2 and ecoc_addtype='离职') la,(select COUNT(*) from EmCommissionOutChange where ecoc_state=2 and ecoc_addtype='调整') ta,(select COUNT(*) from EmCommissionOutChange where ecoc_state=2 and ecoc_addtype='取消') qa,(select COUNT(*) from EmCommissionOutChange where ecoc_state=3 and ecoc_addtype='新增') cxa,(select COUNT(*) from EmCommissionOutChange where ecoc_state=3 and ecoc_addtype='离职') cla,(select COUNT(*) from EmCommissionOutChange where ecoc_state=3 and ecoc_addtype='调整') cta,(select COUNT(*) from EmCommissionOutChange where ecoc_state=3 and ecoc_addtype='取消') cqa";
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutChangeModel model = new EmCommissionOutChangeModel();
				list.setGid(rs.getInt("xa"));
				list.setCid(rs.getInt("la"));
				list.setEcoc_id(rs.getInt("ta"));
				list.setEcoc_ecou_id(rs.getInt("qa"));
				
				list.setC1(rs.getInt("cxa"));
				list.setC2(rs.getInt("cla"));
				list.setC3(rs.getInt("cta"));
				list.setC4(rs.getInt("cqa"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
}
