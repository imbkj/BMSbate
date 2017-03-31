package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EmShebaoUpdateModel {
	private final DecimalFormat df = new DecimalFormat("#.00");
	private int id;
	private int cid;
	private int gid;
	private int ownmonth;
	private String esiu_company;
	private String esiu_name;
	private String esiu_computerid;
	private String esiu_idcard;
	private String esiu_hj;
	private int esiu_radix;
	private String esiu_yl;
	private String esiu_gs;
	private String esiu_sye;
	private String esiu_syu;
	private String esiu_yltype;
	private String esiu_house;
	private BigDecimal esiu_ylcp;
	private BigDecimal esiu_ylop;
	private BigDecimal esiu_jlcp;
	private BigDecimal esiu_jlop;
	private BigDecimal esiu_syucp;
	private BigDecimal esiu_syuop;
	private BigDecimal esiu_syecp;
	private BigDecimal esiu_syeop;
	private BigDecimal esiu_gscp;
	private BigDecimal esiu_gsop;
	private BigDecimal esiu_housecp;
	private BigDecimal esiu_houseop;
	private BigDecimal esiu_totalcp;
	private BigDecimal esiu_totalop;
	private int esiu_ifinure;
	private String esiu_remark;
	private String esiu_addname;
	private String esiu_addtime;
	private int esiu_single;
	private String esiu_worker;
	private String esiu_officialrank;
	private String esiu_hand;
	private String esiu_folk;
	private int esiu_ifstop;
	private int esiu_ifsame;
	private String esiu_client;
	private String esiu_lbid;
	private int esiu_shebaoid;
	private String esiu_stopreason;
	private int formulaid;
	private String mobile;
	private String folk;
	private int ifdeclare;
	private String worker;
	private String hand;
	private String emsc_s8;
	private String emsf_soin_title;
	private String sex;
	private BigDecimal esiu_total;
	private String age;

	public EmShebaoUpdateModel() {
		super();
	}

	public EmShebaoUpdateModel(int id, int cid, int gid, int ownmonth,
			String esiu_company, String esiu_name, String esiu_computerid,
			String esiu_idcard, String esiu_hj, int esiu_radix, String esiu_yl,
			String esiu_gs, String esiu_sye, String esiu_syu,
			String esiu_yltype, String esiu_house, BigDecimal esiu_ylcp,
			BigDecimal esiu_ylop, BigDecimal esiu_jlcp, BigDecimal esiu_jlop,
			BigDecimal esiu_syucp, BigDecimal esiu_syuop,
			BigDecimal esiu_syecp, BigDecimal esiu_syeop, BigDecimal esiu_gscp,
			BigDecimal esiu_gsop, BigDecimal esiu_housecp,
			BigDecimal esiu_houseop, BigDecimal esiu_totalcp,
			BigDecimal esiu_totalop, int esiu_ifinure, String esiu_remark,
			String esiu_addname, String esiu_addtime, int esiu_single,
			String esiu_worker, String esiu_officialrank, String esiu_hand,
			String esiu_folk, int esiu_ifstop, int esiu_ifsame,
			String esiu_client, String esiu_lbid, int esiu_shebaoid,
			String esiu_stopreason) {
		super();
		this.id = id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.esiu_company = esiu_company;
		this.esiu_name = esiu_name;
		this.esiu_computerid = esiu_computerid;
		this.esiu_idcard = esiu_idcard;
		this.esiu_hj = esiu_hj;
		this.esiu_radix = esiu_radix;
		this.esiu_yl = esiu_yl;
		this.esiu_gs = esiu_gs;
		this.esiu_sye = esiu_sye;
		this.esiu_syu = esiu_syu;
		this.esiu_yltype = esiu_yltype;
		this.esiu_house = esiu_house;
		this.esiu_ylcp = new BigDecimal(df.format(esiu_ylcp));
		this.esiu_ylop = new BigDecimal(df.format(esiu_ylop));
		this.esiu_jlcp = new BigDecimal(df.format(esiu_jlcp));
		this.esiu_jlop = new BigDecimal(df.format(esiu_jlop));
		this.esiu_syucp = new BigDecimal(df.format(esiu_syucp));
		this.esiu_syuop = new BigDecimal(df.format(esiu_syuop));
		this.esiu_syecp = new BigDecimal(df.format(esiu_syecp));
		this.esiu_syeop = new BigDecimal(df.format(esiu_syeop));
		this.esiu_gscp = new BigDecimal(df.format(esiu_gscp));
		this.esiu_gsop = new BigDecimal(df.format(esiu_gsop));
		this.esiu_housecp = esiu_housecp;
		this.esiu_houseop = esiu_houseop;
		this.esiu_totalcp = new BigDecimal(df.format(esiu_totalcp));
		this.esiu_totalop = new BigDecimal(df.format(esiu_totalop));
		this.esiu_ifinure = esiu_ifinure;
		this.esiu_remark = esiu_remark;
		this.esiu_addname = esiu_addname;
		this.esiu_addtime = esiu_addtime;
		this.esiu_single = esiu_single;
		this.esiu_worker = esiu_worker;
		this.esiu_officialrank = esiu_officialrank;
		this.esiu_hand = esiu_hand;
		this.esiu_folk = esiu_folk;
		this.esiu_ifstop = esiu_ifstop;
		this.esiu_ifsame = esiu_ifsame;
		this.esiu_client = esiu_client;
		this.esiu_lbid = esiu_lbid;
		this.esiu_shebaoid = esiu_shebaoid;
		this.esiu_stopreason = esiu_stopreason;
	}

	public EmShebaoUpdateModel(int id, int cid, int gid, int ownmonth,
			String esiu_company, String esiu_name, String esiu_computerid,
			String esiu_idcard, String esiu_hj, int esiu_radix, String esiu_yl,
			String esiu_gs, String esiu_sye, String esiu_syu,
			String esiu_yltype, String esiu_house, BigDecimal esiu_ylcp,
			BigDecimal esiu_ylop, BigDecimal esiu_jlcp, BigDecimal esiu_jlop,
			BigDecimal esiu_syucp, BigDecimal esiu_syuop,
			BigDecimal esiu_syecp, BigDecimal esiu_syeop, BigDecimal esiu_gscp,
			BigDecimal esiu_gsop, BigDecimal esiu_housecp,
			BigDecimal esiu_houseop, BigDecimal esiu_totalcp,
			BigDecimal esiu_totalop, int esiu_ifinure, String esiu_remark,
			String esiu_addname, String esiu_addtime, int esiu_single,
			String esiu_worker, String esiu_officialrank, String esiu_hand,
			String esiu_folk, int esiu_ifstop, int esiu_ifsame,
			String esiu_client, String esiu_lbid, int esiu_shebaoid,
			String esiu_stopreason, String emsf_soin_title) {
		super();
		this.id = id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.esiu_company = esiu_company;
		this.esiu_name = esiu_name;
		this.esiu_computerid = esiu_computerid;
		this.esiu_idcard = esiu_idcard;
		this.esiu_hj = esiu_hj;
		this.esiu_radix = esiu_radix;
		this.esiu_yl = esiu_yl;
		this.esiu_gs = esiu_gs;
		this.esiu_sye = esiu_sye;
		this.esiu_syu = esiu_syu;
		this.esiu_yltype = esiu_yltype;
		this.esiu_house = esiu_house;
		this.esiu_ylcp = new BigDecimal(df.format(esiu_ylcp));
		this.esiu_ylop = new BigDecimal(df.format(esiu_ylop));
		this.esiu_jlcp = new BigDecimal(df.format(esiu_jlcp));
		this.esiu_jlop = new BigDecimal(df.format(esiu_jlop));
		this.esiu_syucp = new BigDecimal(df.format(esiu_syucp));
		this.esiu_syuop = new BigDecimal(df.format(esiu_syuop));
		this.esiu_syecp = new BigDecimal(df.format(esiu_syecp));
		this.esiu_syeop = new BigDecimal(df.format(esiu_syeop));
		this.esiu_gscp = new BigDecimal(df.format(esiu_gscp));
		this.esiu_gsop = new BigDecimal(df.format(esiu_gsop));
		this.esiu_housecp = esiu_housecp;
		this.esiu_houseop = esiu_houseop;
		this.esiu_totalcp = new BigDecimal(df.format(esiu_totalcp));
		this.esiu_totalop = new BigDecimal(df.format(esiu_totalop));
		this.esiu_ifinure = esiu_ifinure;
		this.esiu_remark = esiu_remark;
		this.esiu_addname = esiu_addname;
		this.esiu_addtime = esiu_addtime;
		this.esiu_single = esiu_single;
		this.esiu_worker = esiu_worker;
		this.esiu_officialrank = esiu_officialrank;
		this.esiu_hand = esiu_hand;
		this.esiu_folk = esiu_folk;
		this.esiu_ifstop = esiu_ifstop;
		this.esiu_ifsame = esiu_ifsame;
		this.esiu_client = esiu_client;
		this.esiu_lbid = esiu_lbid;
		this.esiu_shebaoid = esiu_shebaoid;
		this.esiu_stopreason = esiu_stopreason;
		this.emsf_soin_title = emsf_soin_title;
	}

	public EmShebaoUpdateModel(int id, int cid, int gid, int ownmonth,
			String esiu_company, String esiu_name, String esiu_computerid,
			String esiu_idcard, String esiu_hj, int esiu_radix, String esiu_yl,
			String esiu_gs, String esiu_sye, String esiu_syu,
			String esiu_yltype, String esiu_house, BigDecimal esiu_ylcp,
			BigDecimal esiu_ylop, BigDecimal esiu_jlcp, BigDecimal esiu_jlop,
			BigDecimal esiu_syucp, BigDecimal esiu_syuop,
			BigDecimal esiu_syecp, BigDecimal esiu_syeop, BigDecimal esiu_gscp,
			BigDecimal esiu_gsop, BigDecimal esiu_housecp,
			BigDecimal esiu_houseop, BigDecimal esiu_totalcp,
			BigDecimal esiu_totalop, int esiu_ifinure, String esiu_remark,
			String esiu_addname, String esiu_addtime, int esiu_single,
			String esiu_worker, String esiu_officialrank, String esiu_hand,
			String esiu_folk, int esiu_ifstop, int esiu_ifsame,
			String esiu_client, String esiu_lbid, int esiu_shebaoid,
			String esiu_stopreason, String emsf_soin_title, int formulaid) {
		super();
		this.id = id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.esiu_company = esiu_company;
		this.esiu_name = esiu_name;
		this.esiu_computerid = esiu_computerid;
		this.esiu_idcard = esiu_idcard;
		this.esiu_hj = esiu_hj;
		this.esiu_radix = esiu_radix;
		this.esiu_yl = esiu_yl;
		this.esiu_gs = esiu_gs;
		this.esiu_sye = esiu_sye;
		this.esiu_syu = esiu_syu;
		this.esiu_yltype = esiu_yltype;
		this.esiu_house = esiu_house;
		this.esiu_ylcp = new BigDecimal(df.format(esiu_ylcp));
		this.esiu_ylop = new BigDecimal(df.format(esiu_ylop));
		this.esiu_jlcp = new BigDecimal(df.format(esiu_jlcp));
		this.esiu_jlop = new BigDecimal(df.format(esiu_jlop));
		this.esiu_syucp = new BigDecimal(df.format(esiu_syucp));
		this.esiu_syuop = new BigDecimal(df.format(esiu_syuop));
		this.esiu_syecp = new BigDecimal(df.format(esiu_syecp));
		this.esiu_syeop = new BigDecimal(df.format(esiu_syeop));
		this.esiu_gscp = new BigDecimal(df.format(esiu_gscp));
		this.esiu_gsop = new BigDecimal(df.format(esiu_gsop));
		this.esiu_housecp = esiu_housecp;
		this.esiu_houseop = esiu_houseop;
		this.esiu_totalcp = new BigDecimal(df.format(esiu_totalcp));
		this.esiu_totalop = new BigDecimal(df.format(esiu_totalop));
		this.esiu_ifinure = esiu_ifinure;
		this.esiu_remark = esiu_remark;
		this.esiu_addname = esiu_addname;
		this.esiu_addtime = esiu_addtime;
		this.esiu_single = esiu_single;
		this.esiu_worker = esiu_worker;
		this.esiu_officialrank = esiu_officialrank;
		this.esiu_hand = esiu_hand;
		this.esiu_folk = esiu_folk;
		this.esiu_ifstop = esiu_ifstop;
		this.esiu_ifsame = esiu_ifsame;
		this.esiu_client = esiu_client;
		this.esiu_lbid = esiu_lbid;
		this.esiu_shebaoid = esiu_shebaoid;
		this.esiu_stopreason = esiu_stopreason;
		this.emsf_soin_title = emsf_soin_title;
		this.formulaid = formulaid;
	}

	public EmShebaoUpdateModel(int id, int cid, int gid, int ownmonth,
			String esiu_company, String esiu_name, String esiu_computerid,
			String esiu_idcard, String esiu_hj, int esiu_radix, String esiu_yl,
			String esiu_gs, String esiu_sye, String esiu_syu,
			String esiu_yltype, String esiu_house, BigDecimal esiu_ylcp,
			BigDecimal esiu_ylop, BigDecimal esiu_jlcp, BigDecimal esiu_jlop,
			BigDecimal esiu_syucp, BigDecimal esiu_syuop,
			BigDecimal esiu_syecp, BigDecimal esiu_syeop, BigDecimal esiu_gscp,
			BigDecimal esiu_gsop, BigDecimal esiu_housecp,
			BigDecimal esiu_houseop, BigDecimal esiu_totalcp,
			BigDecimal esiu_totalop, int esiu_ifinure, String esiu_remark,
			String esiu_addname, String esiu_addtime, int esiu_single,
			String esiu_worker, String esiu_officialrank, String esiu_hand,
			String esiu_folk, int esiu_ifstop, int esiu_ifsame,
			String esiu_client, String esiu_lbid, int esiu_shebaoid,
			String esiu_stopreason, String emsf_soin_title, int formulaid,
			String sex, String age,String mobile) {
		super();
		this.id = id;
		this.cid = cid;
		this.gid = gid;
		this.ownmonth = ownmonth;
		this.esiu_company = esiu_company;
		this.esiu_name = esiu_name;
		this.esiu_computerid = esiu_computerid;
		this.esiu_idcard = esiu_idcard;
		this.esiu_hj = esiu_hj;
		this.esiu_radix = esiu_radix;
		this.esiu_yl = esiu_yl;
		this.esiu_gs = esiu_gs;
		this.esiu_sye = esiu_sye;
		this.esiu_syu = esiu_syu;
		this.esiu_yltype = esiu_yltype;
		this.esiu_house = esiu_house;
		this.esiu_ylcp = new BigDecimal(df.format(esiu_ylcp));
		this.esiu_ylop = new BigDecimal(df.format(esiu_ylop));
		this.esiu_jlcp = new BigDecimal(df.format(esiu_jlcp));
		this.esiu_jlop = new BigDecimal(df.format(esiu_jlop));
		this.esiu_syucp = new BigDecimal(df.format(esiu_syucp));
		this.esiu_syuop = new BigDecimal(df.format(esiu_syuop));
		this.esiu_syecp = new BigDecimal(df.format(esiu_syecp));
		this.esiu_syeop = new BigDecimal(df.format(esiu_syeop));
		this.esiu_gscp = new BigDecimal(df.format(esiu_gscp));
		this.esiu_gsop = new BigDecimal(df.format(esiu_gsop));
		this.esiu_housecp = esiu_housecp;
		this.esiu_houseop = esiu_houseop;
		this.esiu_totalcp = new BigDecimal(df.format(esiu_totalcp));
		this.esiu_totalop = new BigDecimal(df.format(esiu_totalop));
		this.esiu_ifinure = esiu_ifinure;
		this.esiu_remark = esiu_remark;
		this.esiu_addname = esiu_addname;
		this.esiu_addtime = esiu_addtime;
		this.esiu_single = esiu_single;
		this.esiu_worker = esiu_worker;
		this.esiu_officialrank = esiu_officialrank;
		this.esiu_hand = esiu_hand;
		this.esiu_folk = esiu_folk;
		this.esiu_ifstop = esiu_ifstop;
		this.esiu_ifsame = esiu_ifsame;
		this.esiu_client = esiu_client;
		this.esiu_lbid = esiu_lbid;
		this.esiu_shebaoid = esiu_shebaoid;
		this.esiu_stopreason = esiu_stopreason;
		this.emsf_soin_title = emsf_soin_title;
		this.formulaid = formulaid;
		this.sex = sex;
		this.age = age;
		this.mobile=mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
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

	public String getEsiu_company() {
		return esiu_company;
	}

	public void setEsiu_company(String esiu_company) {
		this.esiu_company = esiu_company;
	}

	public String getEsiu_name() {
		return esiu_name;
	}

	public void setEsiu_name(String esiu_name) {
		this.esiu_name = esiu_name;
	}

	public String getEsiu_computerid() {
		return esiu_computerid;
	}

	public void setEsiu_computerid(String esiu_computerid) {
		this.esiu_computerid = esiu_computerid;
	}

	public String getEsiu_idcard() {
		return esiu_idcard;
	}

	public void setEsiu_idcard(String esiu_idcard) {
		this.esiu_idcard = esiu_idcard;
	}

	public String getEsiu_hj() {
		return esiu_hj;
	}

	public void setEsiu_hj(String esiu_hj) {
		this.esiu_hj = esiu_hj;
	}

	public int getEsiu_radix() {
		return esiu_radix;
	}

	public void setEsiu_radix(int esiu_radix) {
		this.esiu_radix = esiu_radix;
	}

	public String getEsiu_yl() {
		return esiu_yl;
	}

	public void setEsiu_yl(String esiu_yl) {
		this.esiu_yl = esiu_yl;
	}

	public String getEsiu_gs() {
		return esiu_gs;
	}

	public void setEsiu_gs(String esiu_gs) {
		this.esiu_gs = esiu_gs;
	}

	public String getEsiu_sye() {
		return esiu_sye;
	}

	public void setEsiu_sye(String esiu_sye) {
		this.esiu_sye = esiu_sye;
	}

	public String getEsiu_syu() {
		return esiu_syu;
	}

	public void setEsiu_syu(String esiu_syu) {
		this.esiu_syu = esiu_syu;
	}

	public String getEsiu_yltype() {
		return esiu_yltype;
	}

	public void setEsiu_yltype(String esiu_yltype) {
		this.esiu_yltype = esiu_yltype;
	}

	public String getEsiu_house() {
		return esiu_house;
	}

	public void setEsiu_house(String esiu_house) {
		this.esiu_house = esiu_house;
	}

	public BigDecimal getEsiu_ylcp() {
		return esiu_ylcp;
	}

	public void setEsiu_ylcp(BigDecimal esiu_ylcp) {
		this.esiu_ylcp = esiu_ylcp;
	}

	public BigDecimal getEsiu_ylop() {
		return esiu_ylop;
	}

	public void setEsiu_ylop(BigDecimal esiu_ylop) {
		this.esiu_ylop = esiu_ylop;
	}

	public BigDecimal getEsiu_jlcp() {
		return esiu_jlcp;
	}

	public void setEsiu_jlcp(BigDecimal esiu_jlcp) {
		this.esiu_jlcp = esiu_jlcp;
	}

	public BigDecimal getEsiu_jlop() {
		return esiu_jlop;
	}

	public void setEsiu_jlop(BigDecimal esiu_jlop) {
		this.esiu_jlop = esiu_jlop;
	}

	public BigDecimal getEsiu_syucp() {
		return esiu_syucp;
	}

	public void setEsiu_syucp(BigDecimal esiu_syucp) {
		this.esiu_syucp = esiu_syucp;
	}

	public BigDecimal getEsiu_syuop() {
		return esiu_syuop;
	}

	public void setEsiu_syuop(BigDecimal esiu_syuop) {
		this.esiu_syuop = esiu_syuop;
	}

	public BigDecimal getEsiu_syecp() {
		return esiu_syecp;
	}

	public void setEsiu_syecp(BigDecimal esiu_syecp) {
		this.esiu_syecp = esiu_syecp;
	}

	public BigDecimal getEsiu_syeop() {
		return esiu_syeop;
	}

	public void setEsiu_syeop(BigDecimal esiu_syeop) {
		this.esiu_syeop = esiu_syeop;
	}

	public BigDecimal getEsiu_gscp() {
		return esiu_gscp;
	}

	public void setEsiu_gscp(BigDecimal esiu_gscp) {
		this.esiu_gscp = esiu_gscp;
	}

	public BigDecimal getEsiu_gsop() {
		return esiu_gsop;
	}

	public void setEsiu_gsop(BigDecimal esiu_gsop) {
		this.esiu_gsop = esiu_gsop;
	}

	public BigDecimal getEsiu_housecp() {
		return esiu_housecp;
	}

	public void setEsiu_housecp(BigDecimal esiu_housecp) {
		this.esiu_housecp = esiu_housecp;
	}

	public BigDecimal getEsiu_houseop() {
		return esiu_houseop;
	}

	public void setEsiu_houseop(BigDecimal esiu_houseop) {
		this.esiu_houseop = esiu_houseop;
	}

	public BigDecimal getEsiu_totalcp() {
		return esiu_totalcp;
	}

	public void setEsiu_totalcp(BigDecimal esiu_totalcp) {
		this.esiu_totalcp = esiu_totalcp;
	}

	public BigDecimal getEsiu_totalop() {
		return esiu_totalop;
	}

	public void setEsiu_totalop(BigDecimal esiu_totalop) {
		this.esiu_totalop = esiu_totalop;
	}

	public int getEsiu_ifinure() {
		return esiu_ifinure;
	}

	public void setEsiu_ifinure(int esiu_ifinure) {
		this.esiu_ifinure = esiu_ifinure;
	}

	public String getEsiu_remark() {
		return esiu_remark;
	}

	public void setEsiu_remark(String esiu_remark) {
		this.esiu_remark = esiu_remark;
	}

	public String getEsiu_addname() {
		return esiu_addname;
	}

	public void setEsiu_addname(String esiu_addname) {
		this.esiu_addname = esiu_addname;
	}

	public String getEsiu_addtime() {
		return esiu_addtime;
	}

	public void setEsiu_addtime(String esiu_addtime) {
		this.esiu_addtime = esiu_addtime;
	}

	public int getEsiu_single() {
		return esiu_single;
	}

	public void setEsiu_single(int esiu_single) {
		this.esiu_single = esiu_single;
	}

	public String getEsiu_worker() {
		return esiu_worker;
	}

	public void setEsiu_worker(String esiu_worker) {
		this.esiu_worker = esiu_worker;
	}

	public String getEsiu_officialrank() {
		return esiu_officialrank;
	}

	public void setEsiu_officialrank(String esiu_officialrank) {
		this.esiu_officialrank = esiu_officialrank;
	}

	public String getEsiu_hand() {
		return esiu_hand;
	}

	public void setEsiu_hand(String esiu_hand) {
		this.esiu_hand = esiu_hand;
	}

	public String getEsiu_folk() {
		return esiu_folk;
	}

	public void setEsiu_folk(String esiu_folk) {
		this.esiu_folk = esiu_folk;
	}

	public int getEsiu_ifstop() {
		return esiu_ifstop;
	}

	public void setEsiu_ifstop(int esiu_ifstop) {
		this.esiu_ifstop = esiu_ifstop;
	}

	public int getEsiu_ifsame() {
		return esiu_ifsame;
	}

	public void setEsiu_ifsame(int esiu_ifsame) {
		this.esiu_ifsame = esiu_ifsame;
	}

	public String getEsiu_client() {
		return esiu_client;
	}

	public void setEsiu_client(String esiu_client) {
		this.esiu_client = esiu_client;
	}

	public String getEsiu_lbid() {
		return esiu_lbid;
	}

	public void setEsiu_lbid(String esiu_lbid) {
		this.esiu_lbid = esiu_lbid;
	}

	public int getEsiu_shebaoid() {
		return esiu_shebaoid;
	}

	public void setEsiu_shebaoid(int esiu_shebaoid) {
		this.esiu_shebaoid = esiu_shebaoid;
	}

	public String getEsiu_stopreason() {
		return esiu_stopreason;
	}

	public void setEsiu_stopreason(String esiu_stopreason) {
		this.esiu_stopreason = esiu_stopreason;
	}

	public int getFormulaid() {
		return formulaid;
	}

	public void setFormulaid(int formulaid) {
		this.formulaid = formulaid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFolk() {
		return folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public int getIfdeclare() {
		return ifdeclare;
	}

	public void setIfdeclare(int ifdeclare) {
		this.ifdeclare = ifdeclare;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public String getEmsc_s8() {
		return emsc_s8;
	}

	public void setEmsc_s8(String emsc_s8) {
		this.emsc_s8 = emsc_s8;
	}

	public String getEmsf_soin_title() {
		return emsf_soin_title;
	}

	public void setEmsf_soin_title(String emsf_soin_title) {
		this.emsf_soin_title = emsf_soin_title;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public BigDecimal getEsiu_total() {
		return esiu_total;
	}

	public void setEsiu_total(BigDecimal esiu_total) {
		this.esiu_total = esiu_total;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
