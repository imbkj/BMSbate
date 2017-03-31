package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EmFinanceProductModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int efpr_id;
	private int efpr_efba_id;
	private String efpr_cfmb_number;
	private int gid;
	private String idcard;
	private int ownmonth;
	private int efpr_startownmonth;
	private int efpr_coco_id;
	private int efpr_cabc_id;
	private String efpr_cpac_name;
	private String efpr_copr_name;
	private int efpr_amount;
	private BigDecimal efpr_fee;
	private BigDecimal efpr_receivable;
	private BigDecimal efpr_paidin;
	private BigDecimal efpr_payout;
	private int efpr_iffirstpaidin;
	private String efpr_addtime;
	private int efpr_finalcheck;
	private String efpr_finalchecktime;
	private int efpr_state;
	private BigDecimal sumReceivable = BigDecimal.ZERO;
	private List<EmFinanceProductItemListModel> itemList;
	private String emba_name;

	public EmFinanceProductModel() {
		super();
	}

	// 插入应收至itemList;
	public void insertReceivableToItemList(String className, String name,
			BigDecimal receivable) {
		for (EmFinanceProductItemListModel m : itemList) {
			try {
				if (className != null) {
					if (className.equals(m.getClassName())
							&& name.equals(m.getName())) {
						m.setReceivable(receivable.add(m.getReceivable()));
						break;
					}
				} else {
					if (name.equals(m.getName())) {
						m.setReceivable(receivable.add(m.getReceivable()));
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 应收合计
	public void sumReceivable() {
		BigDecimal bd = BigDecimal.ZERO;
		for (EmFinanceProductItemListModel m : itemList) {
			bd = bd.add(m.getReceivable());
		}
		sumReceivable = bd;
	}

	// 插入ItemList(复制)
	public void insertItemList(List<EmFinanceProductItemListModel> itemList) {
		List<EmFinanceProductItemListModel> list = new ArrayList<EmFinanceProductItemListModel>();
		for (EmFinanceProductItemListModel m : itemList) {
			EmFinanceProductItemListModel newModel = new EmFinanceProductItemListModel();
			newModel.setClassName(m.getClassName());
			newModel.setName(m.getName());
			list.add(newModel);
		}
		this.itemList = list;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public int getEfpr_id() {
		return efpr_id;
	}

	public void setEfpr_id(int efpr_id) {
		this.efpr_id = efpr_id;
	}

	public int getEfpr_efba_id() {
		return efpr_efba_id;
	}

	public void setEfpr_efba_id(int efpr_efba_id) {
		this.efpr_efba_id = efpr_efba_id;
	}

	public String getEfpr_cfmb_number() {
		return efpr_cfmb_number;
	}

	public void setEfpr_cfmb_number(String efpr_cfmb_number) {
		this.efpr_cfmb_number = efpr_cfmb_number;
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

	public int getEfpr_startownmonth() {
		return efpr_startownmonth;
	}

	public void setEfpr_startownmonth(int efpr_startownmonth) {
		this.efpr_startownmonth = efpr_startownmonth;
	}

	public int getEfpr_coco_id() {
		return efpr_coco_id;
	}

	public void setEfpr_coco_id(int efpr_coco_id) {
		this.efpr_coco_id = efpr_coco_id;
	}

	public int getEfpr_cabc_id() {
		return efpr_cabc_id;
	}

	public void setEfpr_cabc_id(int efpr_cabc_id) {
		this.efpr_cabc_id = efpr_cabc_id;
	}

	public String getEfpr_cpac_name() {
		return efpr_cpac_name;
	}

	public void setEfpr_cpac_name(String efpr_cpac_name) {
		this.efpr_cpac_name = efpr_cpac_name;
	}

	public String getEfpr_copr_name() {
		return efpr_copr_name;
	}

	public void setEfpr_copr_name(String efpr_copr_name) {
		this.efpr_copr_name = efpr_copr_name;
	}

	public int getEfpr_amount() {
		return efpr_amount;
	}

	public void setEfpr_amount(int efpr_amount) {
		this.efpr_amount = efpr_amount;
	}

	public BigDecimal getEfpr_fee() {
		return efpr_fee;
	}

	public void setEfpr_fee(BigDecimal efpr_fee) {
		this.efpr_fee = efpr_fee;
	}

	public BigDecimal getEfpr_receivable() {
		return efpr_receivable;
	}

	public void setEfpr_receivable(BigDecimal efpr_receivable) {
		this.efpr_receivable = new BigDecimal(df.format(efpr_receivable));
	}

	public BigDecimal getEfpr_paidin() {
		return efpr_paidin;
	}

	public void setEfpr_paidin(BigDecimal efpr_paidin) {
		this.efpr_paidin = new BigDecimal(df.format(efpr_paidin));
	}

	public BigDecimal getEfpr_payout() {
		return efpr_payout;
	}

	public void setEfpr_payout(BigDecimal efpr_payout) {
		this.efpr_payout = new BigDecimal(df.format(efpr_payout));
	}

	public int getEfpr_iffirstpaidin() {
		return efpr_iffirstpaidin;
	}

	public void setEfpr_iffirstpaidin(int efpr_iffirstpaidin) {
		this.efpr_iffirstpaidin = efpr_iffirstpaidin;
	}

	public String getEfpr_addtime() {
		return efpr_addtime;
	}

	public void setEfpr_addtime(String efpr_addtime) {
		this.efpr_addtime = efpr_addtime;
	}

	public int getEfpr_finalcheck() {
		return efpr_finalcheck;
	}

	public void setEfpr_finalcheck(int efpr_finalcheck) {
		this.efpr_finalcheck = efpr_finalcheck;
	}

	public String getEfpr_finalchecktime() {
		return efpr_finalchecktime;
	}

	public void setEfpr_finalchecktime(String efpr_finalchecktime) {
		this.efpr_finalchecktime = efpr_finalchecktime;
	}

	public int getEfpr_state() {
		return efpr_state;
	}

	public void setEfpr_state(int efpr_state) {
		this.efpr_state = efpr_state;
	}

	public List<EmFinanceProductItemListModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<EmFinanceProductItemListModel> itemList) {
		this.itemList = itemList;
	}

	public BigDecimal getSumReceivable() {
		return sumReceivable;
	}

	public void setSumReceivable(BigDecimal sumReceivable) {
		this.sumReceivable = new BigDecimal(df.format(sumReceivable));
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

}
