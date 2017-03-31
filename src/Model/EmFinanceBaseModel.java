package Model;

import java.util.ArrayList;
import java.util.List;

public class EmFinanceBaseModel {
	private int sein_id;
	private int gid;
	private String sein_shebao;
	private String sein_gjj;
	private String sein_shangbao;
	private String sein_wt;
	private String sein_shebaob;
	private String sein_gjjb;
	private String sein_da;
	private String sein_zj;
	private String sein_ldyg;
	private String sein_xc;
	private String sein_addname;
	private String sein_addtime;
	private List<EmFinanceProductItemListModel> emProductItemList;

	public EmFinanceBaseModel() {
		super();
	}

	public int getSein_id() {
		return sein_id;
	}

	public void setSein_id(int sein_id) {
		this.sein_id = sein_id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getSein_shebao() {
		return sein_shebao;
	}

	public void setSein_shebao(String sein_shebao) {
		this.sein_shebao = sein_shebao;
	}

	public String getSein_gjj() {
		return sein_gjj;
	}

	public void setSein_gjj(String sein_gjj) {
		this.sein_gjj = sein_gjj;
	}

	public String getSein_shangbao() {
		return sein_shangbao;
	}

	public void setSein_shangbao(String sein_shangbao) {
		this.sein_shangbao = sein_shangbao;
	}

	public String getSein_wt() {
		return sein_wt;
	}

	public void setSein_wt(String sein_wt) {
		this.sein_wt = sein_wt;
	}

	public String getSein_shebaob() {
		return sein_shebaob;
	}

	public void setSein_shebaob(String sein_shebaob) {
		this.sein_shebaob = sein_shebaob;
	}

	public String getSein_gjjb() {
		return sein_gjjb;
	}

	public void setSein_gjjb(String sein_gjjb) {
		this.sein_gjjb = sein_gjjb;
	}

	public String getSein_da() {
		return sein_da;
	}

	public void setSein_da(String sein_da) {
		this.sein_da = sein_da;
	}

	public String getSein_zj() {
		return sein_zj;
	}

	public void setSein_zj(String sein_zj) {
		this.sein_zj = sein_zj;
	}

	public String getSein_ldyg() {
		return sein_ldyg;
	}

	public void setSein_ldyg(String sein_ldyg) {
		this.sein_ldyg = sein_ldyg;
	}

	public String getSein_xc() {
		return sein_xc;
	}

	public void setSein_xc(String sein_xc) {
		this.sein_xc = sein_xc;
	}

	public String getSein_addname() {
		return sein_addname;
	}

	public void setSein_addname(String sein_addname) {
		this.sein_addname = sein_addname;
	}

	public String getSein_addtime() {
		return sein_addtime;
	}

	public void setSein_addtime(String sein_addtime) {
		this.sein_addtime = sein_addtime;
	}

	public List<EmFinanceProductItemListModel> getEmProductItemList() {
		return emProductItemList;
	}

	public void setEmProductItemList(
			List<EmFinanceProductItemListModel> emProductItemList) {
		this.emProductItemList = new ArrayList<EmFinanceProductItemListModel>(
				emProductItemList);
	}

}