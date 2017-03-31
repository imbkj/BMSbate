package bll.EmBaseCompact;

import java.text.SimpleDateFormat;
import java.util.Date;

import Model.EmBaseCompactModel;
import dal.EmBaseCompact.EmBaseCompactSA_AddDal;

public class EmBaseCompactSA_AddBll {
	private Integer y;
	private Integer y1;
	private Integer y2;
	private EmBaseCompactModel reg;
	private EmBaseCompactSA_AddDal data;
	private EmBaseCompactModel reg2;
	private EmBaseCompactSA_AddDal data2;
	private EmBaseCompactModel reg3;
	private EmBaseCompactSA_AddDal data3;

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}

	public void add_emcompact(String cid, String gid, String embase1,
			String embase2, String embase3, String embase5, String embase6,
			String embase8, String embase9, String embase10, String embase11,
			String embase12, String embase13, String embase14, String embase15,
			String embase16, String embase17, String embase18, String embase19,
			String embase20, String embase21) {

		reg = new EmBaseCompactModel(gid, cid, embase1, embase2, embase3,
				embase5, embase6, embase8, embase9, embase10, embase11,
				embase12, embase13, embase14, embase15, embase16, embase17,
				embase18, embase19, embase20, embase21, "", "0", "新签","");
	}

	// 签回劳动合同
	public void sign_emcompact(int ebcc_id, String embase1) {

		reg2 = new EmBaseCompactModel(ebcc_id, embase1);
	}

	public void filing_emcompact(int ebcc_id, String embase1) {

		reg3 = new EmBaseCompactModel(ebcc_id, embase1);
	}

	public int Dochek() {
		data = new EmBaseCompactSA_AddDal();

		int y = 0;
		try {
			y = data.add_emcompact(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public int Dochek2() {
		data2 = new EmBaseCompactSA_AddDal();

		int y1 = 0;
		try {
			y1 = data2.sign_emcompact(reg2);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y1;
	}

	public void setY1(Integer y1) {
		this.y1 = y1;
	}
	
	public int Dochek3() {
		data3 = new EmBaseCompactSA_AddDal();

		int y2 = 0;
		try {
			y2 = data3.filing_emcompact(reg3);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y2;
	}

	public void setY2(Integer y2) {
		this.y2 = y2;
	}
}
