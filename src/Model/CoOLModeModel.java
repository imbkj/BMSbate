package Model;

import java.math.BigDecimal;
import java.util.Date;

public class CoOLModeModel {
	// / colm_id
	private Integer colm_id;
	// / colm_coli_id
	private Integer colm_coli_id;
	// / colm_name
	private String colm_name;
	// / colm_startmonth
	private Integer colm_startmonth;
	// / colm_stopmonth
	private Integer colm_stopmonth;
	// / colm_fee
	private BigDecimal colm_fee;
	// / colm_type
	private String colm_type;
	// / colm_addtime
	private Date colm_addtime;
	// / colm_addname
	private String colm_addname;
	// / colm_stargivemonth
	private Integer colm_stargivemonth;
	// / colm_giveonemonth
	private Integer colm_giveonemonth;
	// / colm_endgivemonth
	private Integer colm_endgivemonth;
	private Integer colm_state;
	private String colm_enjoytype;
	private Integer colm_selectid;
	private Integer int11, int12, int21, int22, int23, int31, int32, int33,
			int41, int42, int43, int44, int51, int52, int53, int54, int55,
			int56, int57, int58;

	public Integer getColm_id() {
		return colm_id;
	}

	public void setColm_id(Integer colm_id) {
		this.colm_id = colm_id;
	}

	public Integer getColm_coli_id() {
		return colm_coli_id;
	}

	public void setColm_coli_id(Integer colm_coli_id) {
		this.colm_coli_id = colm_coli_id;
	}

	public String getColm_name() {
		return colm_name;
	}

	public void setColm_name(String colm_name) {
		this.colm_name = colm_name;
	}

	public Integer getColm_startmonth() {
		return colm_startmonth;
	}

	public void setColm_startmonth(Integer colm_startmonth) {
		this.colm_startmonth = colm_startmonth;
	}

	public Integer getColm_stopmonth() {
		return colm_stopmonth;
	}

	public void setColm_stopmonth(Integer colm_stopmonth) {
		this.colm_stopmonth = colm_stopmonth;
	}

	public BigDecimal getColm_fee() {
		colm_fee = colm_fee == null ? BigDecimal.ZERO : colm_fee.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		return colm_fee;
	}

	public void setColm_fee(BigDecimal colm_fee) {
		this.colm_fee = colm_fee;
	}

	public String getColm_type() {
		if (colm_type == null) {
			colm_type = "元";
		}
		return colm_type;
	}

	public void setColm_type(String colm_type) {
		if (colm_type == null) {
			colm_type = "元";
		}
		this.colm_type = colm_type;
	}

	public Date getColm_addtime() {
		return colm_addtime;
	}

	public void setColm_addtime(Date colm_addtime) {
		this.colm_addtime = colm_addtime;
	}

	public String getColm_addname() {
		return colm_addname;
	}

	public void setColm_addname(String colm_addname) {
		this.colm_addname = colm_addname;
	}

	public Integer getColm_stargivemonth() {
		return colm_stargivemonth;
	}

	public void setColm_stargivemonth(Integer colm_stargivemonth) {
		this.colm_stargivemonth = colm_stargivemonth;
	}

	public Integer getColm_giveonemonth() {
		return colm_giveonemonth;
	}

	public void setColm_giveonemonth(Integer colm_giveonemonth) {
		this.colm_giveonemonth = colm_giveonemonth;
	}

	public Integer getColm_endgivemonth() {
		return colm_endgivemonth;
	}

	public void setColm_endgivemonth(Integer colm_endgivemonth) {
		this.colm_endgivemonth = colm_endgivemonth;
	}

	public String getColm_enjoytype() {
		if (colm_enjoytype == null) {
			colm_enjoytype = "常规享受";
		}
		return colm_enjoytype;
	}

	public void setColm_enjoytype(String colm_enjoytype) {
		if (colm_enjoytype == null) {
			colm_enjoytype = "常规享受";
		}
		this.colm_enjoytype = colm_enjoytype;
	}

	public Integer getColm_selectid() {
		return colm_selectid;
	}

	public void setColm_selectid(Integer colm_selectid) {
		this.colm_selectid = colm_selectid;
	}

	public Integer getInt11() {
		return int11;
	}

	public void setInt11(Integer int11) {
		this.int11 = int11;
	}

	public Integer getInt12() {
		return int12;
	}

	public void setInt12(Integer int12) {
		this.int12 = int12;
	}

	public Integer getInt21() {
		return int21;
	}

	public void setInt21(Integer int21) {
		this.int21 = int21;
	}

	public Integer getInt22() {
		return int22;
	}

	public void setInt22(Integer int22) {
		this.int22 = int22;
	}

	public Integer getInt23() {
		return int23;
	}

	public void setInt23(Integer int23) {
		this.int23 = int23;
	}

	public Integer getInt31() {
		return int31;
	}

	public void setInt31(Integer int31) {
		this.int31 = int31;
	}

	public Integer getInt32() {
		return int32;
	}

	public void setInt32(Integer int32) {
		this.int32 = int32;
	}

	public Integer getInt33() {
		return int33;
	}

	public void setInt33(Integer int33) {
		this.int33 = int33;
	}

	public Integer getInt41() {
		return int41;
	}

	public void setInt41(Integer int41) {
		this.int41 = int41;
	}

	public Integer getInt42() {
		return int42;
	}

	public void setInt42(Integer int42) {
		this.int42 = int42;
	}

	public Integer getInt43() {
		return int43;
	}

	public void setInt43(Integer int43) {
		this.int43 = int43;
	}

	public Integer getInt44() {
		return int44;
	}

	public void setInt44(Integer int44) {
		this.int44 = int44;
	}

	public Integer getInt51() {
		return int51;
	}

	public void setInt51(Integer int51) {
		this.int51 = int51;
	}

	public Integer getInt52() {
		return int52;
	}

	public void setInt52(Integer int52) {
		this.int52 = int52;
	}

	public Integer getInt53() {
		return int53;
	}

	public void setInt53(Integer int53) {
		this.int53 = int53;
	}

	public Integer getInt54() {
		return int54;
	}

	public void setInt54(Integer int54) {
		this.int54 = int54;
	}

	public Integer getInt55() {
		return int55;
	}

	public void setInt55(Integer int55) {
		this.int55 = int55;
	}

	public Integer getInt56() {
		return int56;
	}

	public void setInt56(Integer int56) {
		this.int56 = int56;
	}

	public Integer getInt57() {
		return int57;
	}

	public void setInt57(Integer int57) {
		this.int57 = int57;
	}

	public Integer getInt58() {
		return int58;
	}

	public void setInt58(Integer int58) {
		this.int58 = int58;
	}

	public Integer getColm_state() {
		return colm_state;
	}

	public void setColm_state(Integer colm_state) {
		this.colm_state = colm_state;
	}

}
