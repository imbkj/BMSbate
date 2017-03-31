package Model;

import java.util.Date;
import java.util.List;

public class EmCommissionOutPayUpdateCRTModel {

	/* EmCommissionOutPayUpdateT表 */

	// ecut_id
	private Integer ecut_id;
	// ecut_name
	private String ecut_name;
	// ecut_state
	private Integer ecut_state;
	// ecut_addname
	private String ecut_addname;
	// ecut_addtime
	private Date ecut_addtime;
	// ecut_excelname
	private String ecut_excelname;
	// ecut_localexcelname
	private String ecut_localexcelname;
	// ecut_titlerow
	private Integer ecut_titlerow;

	/* EmCommissionOutPayUpdateC表 */

	// ecuc_id
	private Integer ecuc_id;
	// ecuc_ecut_id
	private Integer ecuc_ecut_id;
	// ecuc_ecpr_id
	private Integer ecuc_ecpr_id;
	// ecuc_excel_title
	private String ecuc_excel_title;
	private String ecuc_excel_title1;
	// ecuc_state
	private Integer ecuc_state;
	// ecuc_addname
	private String ecuc_addname;
	// ecuc_addtime
	private Date ecuc_addtime;

	/* EmCommissionOutPayUpdateR表 */

	// ecpr_id
	private Integer ecpr_id;
	// ecpr_ecpu_field
	private String ecpr_ecpu_field;
	// ecpr_sicl_id
	private Integer ecpr_sicl_id;
	// ecpr_ecpu_fieldstr
	private String ecpr_ecpu_fieldstr;
	// 数据类型
	private String ecpr_type;
	// ecpr_order
	private Integer ecpr_order;
	// ecpr_state
	private Integer ecpr_state;

	private List<EmCommissionOutPayUpdateCRTModel> cList;
	private List<EmCommissionOutPayUpdateCRTModel> fieldList;
	private EmCommissionOutPayUpdateCRTModel fieldModel;

	private String addtime1;
	private String statename;

	public final Integer getEcut_id() {
		return ecut_id;
	}

	public final String getEcut_name() {
		return ecut_name;
	}

	public final Integer getEcut_state() {
		return ecut_state;
	}

	public final String getEcut_addname() {
		return ecut_addname;
	}

	public final Date getEcut_addtime() {
		return ecut_addtime;
	}

	public final Integer getEcuc_id() {
		return ecuc_id;
	}

	public final Integer getEcuc_ecut_id() {
		return ecuc_ecut_id;
	}

	public final String getEcuc_excel_title() {
		return ecuc_excel_title;
	}

	public final Integer getEcuc_state() {
		return ecuc_state;
	}

	public final String getEcuc_addname() {
		return ecuc_addname;
	}

	public final Date getEcuc_addtime() {
		return ecuc_addtime;
	}

	public final Integer getEcpr_id() {
		return ecpr_id;
	}

	public final String getEcpr_ecpu_field() {
		return ecpr_ecpu_field;
	}

	public final Integer getEcpr_sicl_id() {
		return ecpr_sicl_id;
	}

	public final String getEcpr_ecpu_fieldstr() {
		return ecpr_ecpu_fieldstr;
	}

	public final void setEcut_id(Integer ecut_id) {
		this.ecut_id = ecut_id;
	}

	public final void setEcut_name(String ecut_name) {
		this.ecut_name = ecut_name;
	}

	public final void setEcut_state(Integer ecut_state) {
		this.ecut_state = ecut_state;
	}

	public final void setEcut_addname(String ecut_addname) {
		this.ecut_addname = ecut_addname;
	}

	public final void setEcut_addtime(Date ecut_addtime) {
		this.ecut_addtime = ecut_addtime;
	}

	public final void setEcuc_id(Integer ecuc_id) {
		this.ecuc_id = ecuc_id;
	}

	public final void setEcuc_ecut_id(Integer ecuc_ecut_id) {
		this.ecuc_ecut_id = ecuc_ecut_id;
	}

	public final void setEcuc_excel_title(String ecuc_excel_title) {
		this.ecuc_excel_title = ecuc_excel_title;
	}

	public final void setEcuc_state(Integer ecuc_state) {
		this.ecuc_state = ecuc_state;
	}

	public final void setEcuc_addname(String ecuc_addname) {
		this.ecuc_addname = ecuc_addname;
	}

	public final void setEcuc_addtime(Date ecuc_addtime) {
		this.ecuc_addtime = ecuc_addtime;
	}

	public final void setEcpr_id(Integer ecpr_id) {
		this.ecpr_id = ecpr_id;
	}

	public final void setEcpr_ecpu_field(String ecpr_ecpu_field) {
		this.ecpr_ecpu_field = ecpr_ecpu_field;
	}

	public final void setEcpr_sicl_id(Integer ecpr_sicl_id) {
		this.ecpr_sicl_id = ecpr_sicl_id;
	}

	public final void setEcpr_ecpu_fieldstr(String ecpr_ecpu_fieldstr) {
		this.ecpr_ecpu_fieldstr = ecpr_ecpu_fieldstr;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getcList() {
		return cList;
	}

	public void setcList(List<EmCommissionOutPayUpdateCRTModel> cList) {
		this.cList = cList;
	}

	public List<EmCommissionOutPayUpdateCRTModel> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<EmCommissionOutPayUpdateCRTModel> fieldList) {
		this.fieldList = fieldList;
	}

	public EmCommissionOutPayUpdateCRTModel getFieldModel() {
		return fieldModel;
	}

	public void setFieldModel(EmCommissionOutPayUpdateCRTModel fieldModel) {
		this.fieldModel = fieldModel;
	}

	public Integer getEcuc_ecpr_id() {
		return ecuc_ecpr_id;
	}

	public void setEcuc_ecpr_id(Integer ecuc_ecpr_id) {
		this.ecuc_ecpr_id = ecuc_ecpr_id;
	}

	public String getEcut_excelname() {
		return ecut_excelname;
	}

	public void setEcut_excelname(String ecut_excelname) {
		this.ecut_excelname = ecut_excelname;
	}

	public String getEcut_localexcelname() {
		return ecut_localexcelname;
	}

	public void setEcut_localexcelname(String ecut_localexcelname) {
		this.ecut_localexcelname = ecut_localexcelname;
	}

	public String getAddtime1() {
		return addtime1;
	}

	public void setAddtime1(String addtime1) {
		this.addtime1 = addtime1;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public Integer getEcut_titlerow() {
		return ecut_titlerow;
	}

	public void setEcut_titlerow(Integer ecut_titlerow) {
		this.ecut_titlerow = ecut_titlerow;
	}

	public String getEcuc_excel_title1() {
		return ecuc_excel_title1;
	}

	public void setEcuc_excel_title1(String ecuc_excel_title1) {
		this.ecuc_excel_title1 = ecuc_excel_title1;
	}

	public Integer getEcpr_order() {
		return ecpr_order;
	}

	public void setEcpr_order(Integer ecpr_order) {
		this.ecpr_order = ecpr_order;
	}

	public Integer getEcpr_state() {
		return ecpr_state;
	}

	public void setEcpr_state(Integer ecpr_state) {
		this.ecpr_state = ecpr_state;
	}

	public String getEcpr_type() {
		return ecpr_type;
	}

	public void setEcpr_type(String ecpr_type) {
		this.ecpr_type = ecpr_type;
	}
}
