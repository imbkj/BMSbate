package Model;

import java.util.Date;

public class View_CSII_CSIIDIModel {
	
	private int  csii_id;
		/// csii_col
	private String  csii_col;
			/// csii_item_name
	private String  csii_item_name;
			/// csii_sequence
	private int  csii_sequence;
			/// csii_csd_state
	private int  csii_csd_state;
			/// csii_fd_state
	private int  csii_fd_state;
			/// ciin_id
	private int  ciin_id;
			/// csii_c_connection
	private String  csii_c_connection;
			/// csii_addname
	private String  csii_addname;
			/// csii_addtime
	private Date  csii_addtime;
			/// csii_ifzero
	private int  csii_ifzero;
			/// csii_edit_state
	private int  csii_edit_state;
			/// csii_itemid
	private String  csii_itemid;
			/// cid
	private int  cid;
			/// ownmonth
	private int  ownmonth;
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
	public Date getCsii_addtime() {
		return csii_addtime;
	}
	public void setCsii_addtime(Date csii_addtime) {
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
	public View_CSII_CSIIDIModel() {
		super();
	}
	public View_CSII_CSIIDIModel(String csii_col, String csii_item_name,
			int csii_sequence, int csii_csd_state, int csii_fd_state, int cid,
			int ownmonth) {
		super();
		this.csii_col = csii_col;
		this.csii_item_name = csii_item_name;
		this.csii_sequence = csii_sequence;
		this.csii_csd_state = csii_csd_state;
		this.csii_fd_state = csii_fd_state;
		this.cid = cid;
		this.ownmonth = ownmonth;
	}
	
	

}
