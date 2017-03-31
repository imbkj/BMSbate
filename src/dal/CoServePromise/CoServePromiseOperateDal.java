package dal.CoServePromise;

import java.sql.Types;

import Conn.dbconn;
import Model.CoBaseServePromiseModel;

public class CoServePromiseOperateDal {
	//单位系统服务约定信息新增
	public Integer CoBaseServePromise_Add(CoBaseServePromiseModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
			.parseInt(db.callWithReturn(
			"{?=call CoBaseServePromise_Add_p_cyj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
			"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
			"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
			Types.INTEGER, m.getCid(),m.getCosp_coco_signtime(),m.getCosp_coco_signname(),m.getCosp_coco_signtype(),
			m.getCosp_coco_hiretype(),m.getCosp_coco_amount(),m.getCosp_coco_hbamount(),
			m.getCosp_coco_ortherfile(),m.getCosp_coco_datainfo(),m.getCosp_coco_careinfo(),
			m.getCosp_enty_caliname(),m.getCosp_enty_datainfo(),m.getCosp_enty_feepaytype(),
			m.getCosp_enty_careinfo(),m.getCosp_sbhs_caliname(),m.getCosp_sbhs_postfee(),
			m.getCosp_sbhs_careinfo(),m.getCosp_jbrg_caliname(),m.getCosp_jbrg_postfee(),
			m.getCosp_jbrg_careinfo(),m.getCosp_card_caliname(),m.getCosp_card_postfee(),
			m.getCosp_card_careinfo(),m.getCosp_bsal_caliname(),m.getCosp_bsal_postfee(),
			m.getCosp_bsal_careinfo(),m.getCosp_invo_caliname(),m.getCosp_invo_postfee(),
			m.getCosp_invo_careinfo(),m.getCosp_page_caliname(),m.getCosp_dali_caliname(),
			m.getCosp_dali_postfee(),m.getCosp_dali_careinfo(),m.getCosp_file_fix(),
			m.getCosp_prve_caliname(),m.getCosp_pive_postfee(),m.getCosp_prve_careinfo(),
			m.getCosp_sing_caliname(),m.getCosp_sing_careinfo(),m.getCosp_acnt_sbiess(),
			m.getCosp_acnt_sbukey(),m.getCosp_acnt_sbdata(),m.getCosp_acnt_housebank(),
			m.getCosp_acnt_houseukey(),m.getCosp_acnt_housepaytime(),m.getCosp_acnt_housedata(),
			m.getCosp_acnt_sbno(),m.getCosp_acnt_sbukeyreachtime(),m.getCosp_acnt_houseno(),
			m.getCosp_anct_housescale(),m.getCosp_anct_houseukeyreachtime(),m.getCosp_state(),
			m.getCosp_addname(),m.getCosp_bcrp_caliname(),m.getCosp_bcrp_postfee(),
			m.getCosp_bcrp_careinfo(),m.getCosp_jbrg_street(),m.getCosp_jbrg_ifdutybook(),
			m.getCosp_jbrg_nodutybookok(),m.getCosp_jbrp_datainfo(),m.getCosp_jbrg_data_caliname(),
			m.getCosp_sbhs_data_caliname(),m.getCosp_card_data_caliname(),m.getCosp_bcrp_bclinkname(),
			m.getCosp_bcrp_linknumber(),m.getCosp_bcrp_bclinkid(),m.getCosp_bcrp_caliid()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	//单位系统服务约定信息修改
	public Integer CoBaseServePromise_Update(CoBaseServePromiseModel m) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
			.parseInt(db.callWithReturn(
			"{?=call [CoBaseServePromise_Update_p_cyj](?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
			"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
			"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
			Types.INTEGER, m.getCid(),m.getCosp_coco_signtime(),m.getCosp_coco_signname(),m.getCosp_coco_signtype(),
			m.getCosp_coco_hiretype(),m.getCosp_coco_amount(),m.getCosp_coco_hbamount(),
			m.getCosp_coco_ortherfile(),m.getCosp_coco_datainfo(),m.getCosp_coco_careinfo(),
			m.getCosp_enty_caliname(),m.getCosp_enty_datainfo(),m.getCosp_enty_feepaytype(),
			m.getCosp_enty_careinfo(),m.getCosp_sbhs_caliname(),m.getCosp_sbhs_postfee(),
			m.getCosp_sbhs_careinfo(),m.getCosp_jbrg_caliname(),m.getCosp_jbrg_postfee(),
			m.getCosp_jbrg_careinfo(),m.getCosp_card_caliname(),m.getCosp_card_postfee(),
			m.getCosp_card_careinfo(),m.getCosp_bsal_caliname(),m.getCosp_bsal_postfee(),
			m.getCosp_bsal_careinfo(),m.getCosp_invo_caliname(),m.getCosp_invo_postfee(),
			m.getCosp_invo_careinfo(),m.getCosp_page_caliname(),m.getCosp_dali_caliname(),
			m.getCosp_dali_postfee(),m.getCosp_dali_careinfo(),m.getCosp_file_fix(),
			m.getCosp_prve_caliname(),m.getCosp_pive_postfee(),m.getCosp_prve_careinfo(),
			m.getCosp_sing_caliname(),m.getCosp_sing_careinfo(),m.getCosp_acnt_sbiess(),
			m.getCosp_acnt_sbukey(),m.getCosp_acnt_sbdata(),m.getCosp_acnt_housebank(),
			m.getCosp_acnt_houseukey(),m.getCosp_acnt_housepaytime(),m.getCosp_acnt_housedata(),
			m.getCosp_acnt_sbno(),m.getCosp_acnt_sbukeyreachtime(),m.getCosp_acnt_houseno(),
			m.getCosp_anct_housescale(),m.getCosp_anct_houseukeyreachtime(),m.getCosp_state(),
			m.getCosp_addname(),m.getCosp_bcrp_caliname(),m.getCosp_bcrp_postfee(),
			m.getCosp_bcrp_careinfo(),m.getCosp_jbrg_street(),m.getCosp_jbrg_ifdutybook(),
			m.getCosp_jbrg_nodutybookok(),m.getCosp_jbrp_datainfo(),m.getCosp_jbrg_data_caliname(),
			m.getCosp_sbhs_data_caliname(),m.getCosp_card_data_caliname(),m.getCosp_bcrp_bclinkname(),
			m.getCosp_bcrp_linknumber(),m.getCosp_bcrp_bclinkid(),m.getCosp_bcrp_caliid()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
}
