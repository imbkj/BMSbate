package Model;

import java.util.List;

public class EmSalaryHistoryDataModel {
	private int cid;
	private String coba_shortname;
	private int ownmonth;
	private String csre_csd_remark;
	private String csre_fd_remark;
	private List<EmSalaryDataModel> esdaList;
	private EmSalaryDataModel sumModel;
	private int esdaListSize;

	public EmSalaryHistoryDataModel() {
		super();
	}

	public void setEsdaListSize() {
		this.esdaListSize = esdaList.size();
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCoba_shortname() {
		return coba_shortname;
	}

	public void setCoba_shortname(String coba_shortname) {
		this.coba_shortname = coba_shortname;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCsre_csd_remark() {
		return csre_csd_remark;
	}

	public void setCsre_csd_remark(String csre_csd_remark) {
		this.csre_csd_remark = csre_csd_remark;
	}

	public String getCsre_fd_remark() {
		return csre_fd_remark;
	}

	public void setCsre_fd_remark(String csre_fd_remark) {
		this.csre_fd_remark = csre_fd_remark;
	}

	public List<EmSalaryDataModel> getEsdaList() {
		return esdaList;
	}

	public void setEsdaList(List<EmSalaryDataModel> esdaList) {
		this.esdaList = esdaList;
	}

	public EmSalaryDataModel getSumModel() {
		return sumModel;
	}

	public void setSumModel(EmSalaryDataModel sumModel) {
		this.sumModel = sumModel;
	}

	public int getEsdaListSize() {
		return esdaListSize;
	}

	public void setEsdaListSize(int esdaListSize) {
		this.esdaListSize = esdaListSize;
	}

}
