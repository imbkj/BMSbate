package Model;

import java.util.List;

public class CoshebaobankModel {
    /// bank_id
    private Integer  bank_id;
             /// bank_name
    private String  bank_name;
             /// bank_state
    private Integer  bank_state;
             /// bank_addname
    private String  bank_addname;
             /// bank_addtime
    private String  bank_addtime;
             /// 材料模块表pubzul的Id
    private Integer bank_doc_id;
    private List<EmshebaoCardBankDataInfoModel> list;
	public Integer getBank_id() {
		return bank_id;
	}
	public void setBank_id(Integer bank_id) {
		this.bank_id = bank_id;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public Integer getBank_state() {
		return bank_state;
	}
	public void setBank_state(Integer bank_state) {
		this.bank_state = bank_state;
	}
	public String getBank_addname() {
		return bank_addname;
	}
	public void setBank_addname(String bank_addname) {
		this.bank_addname = bank_addname;
	}
	public String getBank_addtime() {
		return bank_addtime;
	}
	public void setBank_addtime(String bank_addtime) {
		this.bank_addtime = bank_addtime;
	}
	public Integer getBank_doc_id() {
		return bank_doc_id;
	}
	public void setBank_doc_id(Integer bank_doc_id) {
		this.bank_doc_id = bank_doc_id;
	}
	public List<EmshebaoCardBankDataInfoModel> getList() {
		return list;
	}
	public void setList(List<EmshebaoCardBankDataInfoModel> list) {
		this.list = list;
	}
    
    
}
