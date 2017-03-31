/*
 * 创建人：林少斌
 * 创建时间：2013-12-5
 * 用途：工资项目算法视图CSII_CFDa_V
 */
package Model;

public class CoSalaryItemFormulaModel {
	private int csii_id;
	private String csii_col;
	private String csii_item_name;
	private int csii_sequence;
	private int csii_csd_state;
	private int csii_fd_state;
	private int ciin_id;
	private String csii_c_connection;
	private String csii_addname;
	private String csii_addtime;
	private int csii_ifzero;
	private int csii_edit_state;
	private String csii_itemid;
	private int cid;
	private int ownmonth;
	private String ciin_name;
	private String ciin_remark;
	private String cfda_id;
	private String cfin_id;
	private String cfda_formula;
	private String cfda_t_formula;
	private String cfda_range;
	private String cfda_t_range;
	private String cfda_sequence;
	private String csgi_content;
	private String chk_cfd;
	private String item_name;		//项目名称用[]括起来
	private String csii_tapr_id;
	private String csii_item_name2;
	private String item_name2;		//项目名称用[]括起来
	private String cfin_name;
	private String csia_attribute;
	private String csia_ms;
	private String csia_remark;
	private int csia_id;
	
	public CoSalaryItemFormulaModel() {
		super();
	}
	
	public CoSalaryItemFormulaModel(int csii_id, String csii_col,
			String csii_item_name, int csii_sequence, int csii_csd_state,
			int csii_fd_state, int ciin_id, String csii_c_connection,
			String csii_addname, String csii_addtime, int csii_ifzero,
			int csii_edit_state, String csii_itemid, int cid, int ownmonth,
			String ciin_name, String ciin_remark, String cfda_id,
			String cfin_id, String cfda_formula, String cfda_t_formula,
			String cfda_range, String cfda_t_range, String cfda_sequence,
			String csgi_content, String chk_cfd, String item_name,
			String csii_tapr_id, String csii_item_name2, String item_name2,
			String cfin_name, String csia_attribute, String csia_ms,
			String csia_remark, int csia_id) {
		super();
		this.csii_id = csii_id;
		this.csii_col = csii_col;
		this.csii_item_name = csii_item_name;
		this.csii_sequence = csii_sequence;
		this.csii_csd_state = csii_csd_state;
		this.csii_fd_state = csii_fd_state;
		this.ciin_id = ciin_id;
		this.csii_c_connection = csii_c_connection;
		this.csii_addname = csii_addname;
		this.csii_addtime = csii_addtime;
		this.csii_ifzero = csii_ifzero;
		this.csii_edit_state = csii_edit_state;
		this.csii_itemid = csii_itemid;
		this.cid = cid;
		this.ownmonth = ownmonth;
		this.ciin_name = ciin_name;
		this.ciin_remark = ciin_remark;
		this.cfda_id = cfda_id;
		this.cfin_id = cfin_id;
		this.cfda_formula = cfda_formula;
		this.cfda_t_formula = cfda_t_formula;
		this.cfda_range = cfda_range;
		this.cfda_t_range = cfda_t_range;
		this.cfda_sequence = cfda_sequence;
		this.csgi_content = csgi_content;
		this.chk_cfd = chk_cfd;
		this.item_name = item_name;
		this.csii_tapr_id = csii_tapr_id;
		this.csii_item_name2 = csii_item_name2;
		this.item_name2 = item_name2;
		this.cfin_name = cfin_name;
		this.csia_attribute = csia_attribute;
		this.csia_ms = csia_ms;
		this.csia_remark = csia_remark;
		this.csia_id = csia_id;
	}

	public String getCfin_name() {
		return cfin_name;
	}

	public void setCfin_name(String cfin_name) {
		this.cfin_name = cfin_name;
	}

	public String getCsia_attribute() {
		return csia_attribute;
	}

	public void setCsia_attribute(String csia_attribute) {
		this.csia_attribute = csia_attribute;
	}

	public String getCsia_ms() {
		return csia_ms;
	}

	public void setCsia_ms(String csia_ms) {
		this.csia_ms = csia_ms;
	}

	public String getCsia_remark() {
		return csia_remark;
	}

	public void setCsia_remark(String csia_remark) {
		this.csia_remark = csia_remark;
	}

	public int getCsia_id() {
		return csia_id;
	}

	public void setCsia_id(int csia_id) {
		this.csia_id = csia_id;
	}

	public String getCsii_tapr_id() {
		return csii_tapr_id;
	}

	public void setCsii_tapr_id(String csii_tapr_id) {
		this.csii_tapr_id = csii_tapr_id;
	}

	public String getItem_name() {
		return item_name;
	}


	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}


	public String getChk_cfd() {
		return chk_cfd;
	}

	public void setChk_cfd(String chk_cfd) {
		this.chk_cfd = chk_cfd;
	}

	public int getCsii_id() {
		return csii_id;
	}
	public void setCsii_id(int csii_id) {
		this.csii_id = csii_id;
	}
	public String getCsii_col() {
		return csii_col;
	}
	public void setCsii_col(String csii_col) {
		this.csii_col = csii_col;
	}
	public String getCsii_item_name() {
		return csii_item_name;
	}
	public void setCsii_item_name(String csii_item_name) {
		this.csii_item_name = csii_item_name;
	}
	public int getCsii_sequence() {
		return csii_sequence;
	}
	public void setCsii_sequence(int csii_sequence) {
		this.csii_sequence = csii_sequence;
	}
	public int getCsii_csd_state() {
		return csii_csd_state;
	}
	public void setCsii_csd_state(int csii_csd_state) {
		this.csii_csd_state = csii_csd_state;
	}
	public int getCsii_fd_state() {
		return csii_fd_state;
	}
	public void setCsii_fd_state(int csii_fd_state) {
		this.csii_fd_state = csii_fd_state;
	}
	public int getCiin_id() {
		return ciin_id;
	}
	public void setCiin_id(int ciin_id) {
		this.ciin_id = ciin_id;
	}
	public String getCsii_c_connection() {
		return csii_c_connection;
	}
	public void setCsii_c_connection(String csii_c_connection) {
		this.csii_c_connection = csii_c_connection;
	}
	public String getCsii_addname() {
		return csii_addname;
	}
	public void setCsii_addname(String csii_addname) {
		this.csii_addname = csii_addname;
	}
	public String getCsii_addtime() {
		return csii_addtime;
	}
	public void setCsii_addtime(String csii_addtime) {
		this.csii_addtime = csii_addtime;
	}
	public int getCsii_ifzero() {
		return csii_ifzero;
	}
	public void setCsii_ifzero(int csii_ifzero) {
		this.csii_ifzero = csii_ifzero;
	}
	public int getCsii_edit_state() {
		return csii_edit_state;
	}
	public void setCsii_edit_state(int csii_edit_state) {
		this.csii_edit_state = csii_edit_state;
	}
	public String getCsii_itemid() {
		return csii_itemid;
	}
	public void setCsii_itemid(String csii_itemid) {
		this.csii_itemid = csii_itemid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getOwnmonth() {
		return ownmonth;
	}
	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}
	public String getCiin_name() {
		return ciin_name;
	}
	public void setCiin_name(String ciin_name) {
		this.ciin_name = ciin_name;
	}
	public String getCiin_remark() {
		return ciin_remark;
	}
	public void setCiin_remark(String ciin_remark) {
		this.ciin_remark = ciin_remark;
	}
	public String getCfda_id() {
		return cfda_id;
	}
	public void setCfda_id(String cfda_id) {
		this.cfda_id = cfda_id;
	}
	public String getCfin_id() {
		return cfin_id;
	}
	public void setCfin_id(String cfin_id) {
		this.cfin_id = cfin_id;
	}
	public String getCfda_formula() {
		return cfda_formula;
	}
	public void setCfda_formula(String cfda_formula) {
		this.cfda_formula = cfda_formula;
	}
	public String getCfda_t_formula() {
		return cfda_t_formula;
	}
	public void setCfda_t_formula(String cfda_t_formula) {
		this.cfda_t_formula = cfda_t_formula;
	}
	public String getCfda_range() {
		return cfda_range;
	}
	public void setCfda_range(String cfda_range) {
		this.cfda_range = cfda_range;
	}
	public String getCfda_t_range() {
		return cfda_t_range;
	}
	public void setCfda_t_range(String cfda_t_range) {
		this.cfda_t_range = cfda_t_range;
	}
	public String getCfda_sequence() {
		return cfda_sequence;
	}
	public void setCfda_sequence(String cfda_sequence) {
		this.cfda_sequence = cfda_sequence;
	}
	public String getCsgi_content() {
		return csgi_content;
	}
	public void setCsgi_content(String csgi_content) {
		this.csgi_content = csgi_content;
	}




	public String getCsii_item_name2() {
		return csii_item_name2;
	}




	public void setCsii_item_name2(String csii_item_name2) {
		this.csii_item_name2 = csii_item_name2;
	}




	public String getItem_name2() {
		return item_name2;
	}




	public void setItem_name2(String item_name2) {
		this.item_name2 = item_name2;
	}
	
	

}
