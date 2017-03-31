package Model;

public class CoCompactTemAddModel {

	/* 控制页面合同信息显示 */
	private boolean sbVis = false;// 社保
	private boolean gjjVis = false;// 公积金
	private boolean gzVis = false;// 代发工资
	private boolean gsVis = false;// 代报个税
	private boolean jyVis = false;// 就业登记
	private boolean groupVis = false;
	private boolean sbrowVis = false;
	private boolean gjjrowVis = false;

	public boolean isGroupVis() {
		return groupVis;
	}

	public void setGroupVis(boolean groupVis) {
		this.groupVis = groupVis;
	}

	public boolean isSbVis() {
		return sbVis;
	}

	public void setSbVis(boolean sbVis) {
		this.sbVis = sbVis;
	}

	public boolean isGjjVis() {
		return gjjVis;
	}

	public void setGjjVis(boolean gjjVis) {
		this.gjjVis = gjjVis;
	}

	public boolean isGzVis() {
		return gzVis;
	}

	public void setGzVis(boolean gzVis) {
		this.gzVis = gzVis;
	}

	public boolean isGsVis() {
		return gsVis;
	}

	public void setGsVis(boolean gsVis) {
		this.gsVis = gsVis;
	}

	public boolean isJyVis() {
		return jyVis;
	}

	public void setJyVis(boolean jyVis) {
		this.jyVis = jyVis;
	}

	public boolean isSbrowVis() {
		return sbrowVis;
	}

	public void setSbrowVis(boolean sbrowVis) {
		this.sbrowVis = sbrowVis;
	}

	public boolean isGjjrowVis() {
		return gjjrowVis;
	}

	public void setGjjrowVis(boolean gjjrowVis) {
		this.gjjrowVis = gjjrowVis;
	}
}
