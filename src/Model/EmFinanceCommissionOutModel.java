package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EmFinanceCommissionOutModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int efco_id;
	private int efco_efba_id;
	private int efco_coco_id;
	private String efco_cfmb_number;
	private int gid;
	private int ownmonth;
	private int efco_cabc_id;
	private int efco_soin_id;
	private int efco_ecou_id;
	private int efco_ecos_shebao_feetype;
	private int efco_ecos_gjj_feetype;
	private String efco_ecou_addtype;
	private String efco_ecou_title_date;
	private BigDecimal efco_ecou_sb_base;
	private BigDecimal efco_ecou_house_base;
	private BigDecimal efco_ecou_sb_sum;
	private BigDecimal efco_ecou_gjj_sum;
	private BigDecimal efco_ecou_cb_sum;
	private BigDecimal efco_ecou_welfare_sum;
	private BigDecimal efco_ecou_service_fee;
	private BigDecimal efco_ecou_file_fee;
	private BigDecimal efco_receivable;
	private BigDecimal efco_paidin;
	private BigDecimal efco_payout;
	private int efco_iffirstpaidin;
	private String efco_addtime;
	private int efco_finalcheck;
	private String efco_finalchecktime;
	private int efco_state;
	private String soin_title;
	private List<EmFinanceCommissionOutDetailItemModel> detailItemList;
	private List<EmFinanceCommissionOutDetailItemModel> productItemList;
	private String emba_name;
	private String emba_idcard;

	public EmFinanceCommissionOutModel() {
		super();
	}

	// 插入应收至detailItemList;
	public void insertReceivableToDetailItemList(String name,
			BigDecimal embase, BigDecimal cobase, BigDecimal emsum,
			BigDecimal cosum) {
		for (EmFinanceCommissionOutDetailItemModel m : detailItemList) {
			try {
				if (name.equals(m.getItem())) {
					m.setBase(embase);
					m.setCoSum(cosum);
					m.setEmSum(emsum);
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 插入应收至productItemList;
	public void insertReceivableToProductItemList(String name,
			BigDecimal receivable) {
		
		for (EmFinanceCommissionOutDetailItemModel m : productItemList) {
			try {
				if (name.equals(m.getItem())) {
					m.setReceivable(receivable);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 插入detailItemList
	public void insertDetailItemList(
			List<EmFinanceCommissionOutDetailItemModel> list) {
		detailItemList = new ArrayList<EmFinanceCommissionOutDetailItemModel>();
		try {
			for (EmFinanceCommissionOutDetailItemModel m : list) {
				EmFinanceCommissionOutDetailItemModel newModel = new EmFinanceCommissionOutDetailItemModel();
				newModel.setItem(m.getItem());
				detailItemList.add(newModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入productItemList
	public void insertProductItemList(
			List<EmFinanceCommissionOutDetailItemModel> list) {
		productItemList = new ArrayList<EmFinanceCommissionOutDetailItemModel>();
		try {
			for (EmFinanceCommissionOutDetailItemModel m : list) {
				EmFinanceCommissionOutDetailItemModel newModel = new EmFinanceCommissionOutDetailItemModel();
				newModel.setItem(m.getItem());
				productItemList.add(newModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入社保比例至detailItemList
	public void insertProportionToDetailItemList(String item,
			String sicl_payunit, String siai_proportion) {
		for (EmFinanceCommissionOutDetailItemModel m : detailItemList) {
			if (item.equals(m.getItem())) {
				if ("1".equals(sicl_payunit)) {
					m.setCpp(siai_proportion);
				} else {
					m.setOpp(siai_proportion);
				}
				return;
			}
		}
	}

	public int getEfco_id() {
		return efco_id;
	}

	public void setEfco_id(int efco_id) {
		this.efco_id = efco_id;
	}

	public int getEfco_efba_id() {
		return efco_efba_id;
	}

	public void setEfco_efba_id(int efco_efba_id) {
		this.efco_efba_id = efco_efba_id;
	}

	public int getEfco_coco_id() {
		return efco_coco_id;
	}

	public void setEfco_coco_id(int efco_coco_id) {
		this.efco_coco_id = efco_coco_id;
	}

	public String getEfco_cfmb_number() {
		return efco_cfmb_number;
	}

	public void setEfco_cfmb_number(String efco_cfmb_number) {
		this.efco_cfmb_number = efco_cfmb_number;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public int getEfco_cabc_id() {
		return efco_cabc_id;
	}

	public void setEfco_cabc_id(int efco_cabc_id) {
		this.efco_cabc_id = efco_cabc_id;
	}

	public int getEfco_soin_id() {
		return efco_soin_id;
	}

	public void setEfco_soin_id(int efco_soin_id) {
		this.efco_soin_id = efco_soin_id;
	}

	public int getEfco_ecou_id() {
		return efco_ecou_id;
	}

	public void setEfco_ecou_id(int efco_ecou_id) {
		this.efco_ecou_id = efco_ecou_id;
	}

	public int getEfco_ecos_shebao_feetype() {
		return efco_ecos_shebao_feetype;
	}

	public void setEfco_ecos_shebao_feetype(int efco_ecos_shebao_feetype) {
		this.efco_ecos_shebao_feetype = efco_ecos_shebao_feetype;
	}

	public int getEfco_ecos_gjj_feetype() {
		return efco_ecos_gjj_feetype;
	}

	public void setEfco_ecos_gjj_feetype(int efco_ecos_gjj_feetype) {
		this.efco_ecos_gjj_feetype = efco_ecos_gjj_feetype;
	}

	public String getEfco_ecou_addtype() {
		return efco_ecou_addtype;
	}

	public void setEfco_ecou_addtype(String efco_ecou_addtype) {
		this.efco_ecou_addtype = efco_ecou_addtype;
	}

	public String getEfco_ecou_title_date() {
		return efco_ecou_title_date;
	}

	public void setEfco_ecou_title_date(String efco_ecou_title_date) {
		this.efco_ecou_title_date = efco_ecou_title_date;
	}

	public BigDecimal getEfco_ecou_sb_base() {
		return efco_ecou_sb_base;
	}

	public void setEfco_ecou_sb_base(BigDecimal efco_ecou_sb_base) {
		this.efco_ecou_sb_base = new BigDecimal(df.format(efco_ecou_sb_base));
	}

	public BigDecimal getEfco_ecou_house_base() {
		return efco_ecou_house_base;
	}

	public void setEfco_ecou_house_base(BigDecimal efco_ecou_house_base) {
		this.efco_ecou_house_base = new BigDecimal(df.format(efco_ecou_sb_sum));
	}

	public BigDecimal getEfco_ecou_sb_sum() {
		return efco_ecou_sb_sum;
	}

	public void setEfco_ecou_sb_sum(BigDecimal efco_ecou_sb_sum) {
		this.efco_ecou_sb_sum = new BigDecimal(df.format(efco_ecou_sb_sum));
	}

	public BigDecimal getEfco_ecou_gjj_sum() {
		return efco_ecou_gjj_sum;
	}

	public void setEfco_ecou_gjj_sum(BigDecimal efco_ecou_gjj_sum) {
		this.efco_ecou_gjj_sum = new BigDecimal(df.format(efco_ecou_gjj_sum));
	}

	public BigDecimal getEfco_ecou_cb_sum() {
		return efco_ecou_cb_sum;
	}

	public void setEfco_ecou_cb_sum(BigDecimal efco_ecou_cb_sum) {
		this.efco_ecou_cb_sum = new BigDecimal(df.format(efco_ecou_cb_sum));
	}

	public BigDecimal getEfco_ecou_welfare_sum() {
		return efco_ecou_welfare_sum;
	}

	public void setEfco_ecou_welfare_sum(BigDecimal efco_ecou_welfare_sum) {
		this.efco_ecou_welfare_sum = new BigDecimal(
				df.format(efco_ecou_welfare_sum));
	}

	public BigDecimal getEfco_ecou_service_fee() {
		return efco_ecou_service_fee;
	}

	public void setEfco_ecou_service_fee(BigDecimal efco_ecou_service_fee) {
		this.efco_ecou_service_fee = new BigDecimal(
				df.format(efco_ecou_service_fee));
	}

	public BigDecimal getEfco_ecou_file_fee() {
		return efco_ecou_file_fee;
	}

	public void setEfco_ecou_file_fee(BigDecimal efco_ecou_file_fee) {
		this.efco_ecou_file_fee = new BigDecimal(df.format(efco_ecou_file_fee));
	}

	public BigDecimal getEfco_receivable() {
		return efco_receivable;
	}

	public void setEfco_receivable(BigDecimal efco_receivable) {
		this.efco_receivable = new BigDecimal(df.format(efco_receivable));
	}

	public BigDecimal getEfco_paidin() {
		return efco_paidin;
	}

	public void setEfco_paidin(BigDecimal efco_paidin) {
		this.efco_paidin = new BigDecimal(df.format(efco_paidin));
	}

	public BigDecimal getEfco_payout() {
		return efco_payout;
	}

	public void setEfco_payout(BigDecimal efco_payout) {
		this.efco_payout = new BigDecimal(df.format(efco_payout));
	}

	public int getEfco_iffirstpaidin() {
		return efco_iffirstpaidin;
	}

	public void setEfco_iffirstpaidin(int efco_iffirstpaidin) {
		this.efco_iffirstpaidin = efco_iffirstpaidin;
	}

	public String getEfco_addtime() {
		return efco_addtime;
	}

	public void setEfco_addtime(String efco_addtime) {
		this.efco_addtime = efco_addtime;
	}

	public int getEfco_finalcheck() {
		return efco_finalcheck;
	}

	public void setEfco_finalcheck(int efco_finalcheck) {
		this.efco_finalcheck = efco_finalcheck;
	}

	public String getEfco_finalchecktime() {
		return efco_finalchecktime;
	}

	public void setEfco_finalchecktime(String efco_finalchecktime) {
		this.efco_finalchecktime = efco_finalchecktime;
	}

	public int getEfco_state() {
		return efco_state;
	}

	public void setEfco_state(int efco_state) {
		this.efco_state = efco_state;
	}

	public String getSoin_title() {
		return soin_title;
	}

	public void setSoin_title(String soin_title) {
		this.soin_title = soin_title;
	}

	public List<EmFinanceCommissionOutDetailItemModel> getDetailItemList() {
		return detailItemList;
	}

	public void setDetailItemList(
			List<EmFinanceCommissionOutDetailItemModel> detailItemList) {
		this.detailItemList = detailItemList;
	}

	public List<EmFinanceCommissionOutDetailItemModel> getProductItemList() {
		return productItemList;
	}

	public void setProductItemList(
			List<EmFinanceCommissionOutDetailItemModel> productItemList) {
		this.productItemList = productItemList;
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

}
