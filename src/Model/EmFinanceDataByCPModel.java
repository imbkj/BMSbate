package Model;

import java.math.BigDecimal;

public class EmFinanceDataByCPModel {
	private int gid,cid;
	private String emba_name;
	private String emba_idcard;
	private String itemname;
	private BigDecimal otherEx;
	private BigDecimal receivable;
	private BigDecimal[] bd =new BigDecimal[2];
	private Integer coco_id;
	private Integer cfmb_PersonnelConfirm;
	private Integer cabc_id;
	
	private BigDecimal efsb_ylao= BigDecimal.ZERO;
	private BigDecimal efsb_yliao= BigDecimal.ZERO;
	private BigDecimal efsb_syu= BigDecimal.ZERO;
	private BigDecimal efsb_sye= BigDecimal.ZERO;
	private BigDecimal efsb_gs= BigDecimal.ZERO;
	
	private String coba_company;
	private Integer ownmonth;

	public EmFinanceDataByCPModel() {
		super();
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public BigDecimal getOtherEx() {
		return otherEx;
	}

	public void setOtherEx(BigDecimal otherEx) {
		this.otherEx = otherEx;
	}

	public BigDecimal getReceivable() {
		return receivable;
	}

	public void setReceivable(BigDecimal receivable) {
		this.receivable = receivable;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public BigDecimal[] getBd() {
		return bd;
	}

	public void setBd(BigDecimal[] bd) {
		this.bd = bd;
	}

	public Integer getCoco_id() {
		return coco_id;
	}

	public void setCoco_id(Integer coco_id) {
		this.coco_id = coco_id;
	}

	public Integer getCfmb_PersonnelConfirm() {
		return cfmb_PersonnelConfirm;
	}

	public void setCfmb_PersonnelConfirm(Integer cfmb_PersonnelConfirm) {
		this.cfmb_PersonnelConfirm = cfmb_PersonnelConfirm;
	}

	public Integer getCabc_id() {
		return cabc_id;
	}

	public void setCabc_id(Integer cabc_id) {
		this.cabc_id = cabc_id;
	}

	public BigDecimal getEfsb_ylao() {
		return efsb_ylao;
	}

	public void setEfsb_ylao(BigDecimal efsb_ylao) {
		this.efsb_ylao = efsb_ylao;
	}

	public BigDecimal getEfsb_yliao() {
		return efsb_yliao;
	}

	public void setEfsb_yliao(BigDecimal efsb_yliao) {
		this.efsb_yliao = efsb_yliao;
	}

	public BigDecimal getEfsb_syu() {
		return efsb_syu;
	}

	public void setEfsb_syu(BigDecimal efsb_syu) {
		this.efsb_syu = efsb_syu;
	}

	public BigDecimal getEfsb_sye() {
		return efsb_sye;
	}

	public void setEfsb_sye(BigDecimal efsb_sye) {
		this.efsb_sye = efsb_sye;
	}

	public BigDecimal getEfsb_gs() {
		return efsb_gs;
	}

	public void setEfsb_gs(BigDecimal efsb_gs) {
		this.efsb_gs = efsb_gs;
	}
	
}
