package Controller.Embase;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.ListModelList;

import bll.Embase.Embase_AllinoneBll;

import Model.Allinone_ListModel;
import Model.EmBaseCompactListModel;

public class Embase_AllinoneController {
	/**
	 * 
	 */

	private int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());

	Embase_AllinoneBll bll = new Embase_AllinoneBll();

	Allinone_ListModel model = new Allinone_ListModel();

	private ListModelList<Allinone_ListModel> coglist_list;// 显示所选服务类型

	private ListModelList<Allinone_ListModel> sb_list;// 显示社保数据

	private ListModelList<Allinone_ListModel> house_list;// 显示住房数据

	private ListModelList<Allinone_ListModel> emcompact_list;// 显示劳动合同数据
	private ListModelList<EmBaseCompactListModel> emcompact;// 劳动合同未制作列表

	private ListModelList<Allinone_ListModel> wt_list;// 显示委托出数据

	private ListModelList<Allinone_ListModel> gz_list;// 显示工资数据
	private ListModelList<Allinone_ListModel> pay_list;// 个人支付数据

	private ListModelList<Allinone_ListModel> file_list;// 显示档案数据

	private ListModelList<Allinone_ListModel> tj_list;// 显示体检数据

	private ListModelList<Allinone_ListModel> card_list;// 显示社保卡数据

	private ListModelList<Allinone_ListModel> hj_list;// 显示户籍数据

	private ListModelList<Allinone_ListModel> gzk_list;// 显示公积金卡制卡数据

	private ListModelList<Allinone_ListModel> glk_list;// 显示公积金卡领卡数据

	private ListModelList<Allinone_ListModel> sy_list;// 显示商保数据

	@Init
	public void init() throws SQLException {
		List<Allinone_ListModel> list = bll.getall_state(gid);

		if (list.size() > 0) {
			model = list.get(0);
		}
		coglist_list = new ListModelList<Allinone_ListModel>(
				bll.getcoglist_list(gid));// 显示所选服务类型

		sb_list = new ListModelList<Allinone_ListModel>(bll.getsb_list(gid));// 显示社保数据

		house_list = new ListModelList<Allinone_ListModel>(
				bll.gethouse_list(gid));// 显示住房数据

		emcompact_list = new ListModelList<Allinone_ListModel>(
				bll.getemcompact_list(gid));// 显示劳动合同数据
		
		emcompact = new ListModelList<EmBaseCompactListModel>(
				bll.getemcompactlist(gid));// 劳动合同未制作列表

		wt_list = new ListModelList<Allinone_ListModel>(bll.getwt_list(gid));// 显示委托出数据

		gz_list = new ListModelList<Allinone_ListModel>(bll.getgz_list(gid));// 显示工资数据
		pay_list = new ListModelList<Allinone_ListModel>(bll.getpay_list(gid));// 显示个人支付数据

		file_list = new ListModelList<Allinone_ListModel>(bll.getfile_list(gid));// 显示档案数据

		tj_list = new ListModelList<Allinone_ListModel>(bll.gettj_list(gid));// 显示体检数据

		card_list = new ListModelList<Allinone_ListModel>(bll.getcard_list(gid));// 显示社保卡数据

		hj_list = new ListModelList<Allinone_ListModel>(bll.gethj_list(gid));// 显示户籍数据

		gzk_list = new ListModelList<Allinone_ListModel>(bll.getgzk_list(gid));// 显示公积金卡制卡数据

		glk_list = new ListModelList<Allinone_ListModel>(bll.getglk_list(gid));// 显示公积金卡领卡数据

		sy_list = new ListModelList<Allinone_ListModel>(bll.getsy_list(gid));// 显示商保数据
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public ListModelList<Allinone_ListModel> getCoglist_list() {
		return coglist_list;
	}

	public void setCoglist_list(ListModelList<Allinone_ListModel> coglist_list) {
		this.coglist_list = coglist_list;
	}

	public ListModelList<Allinone_ListModel> getSb_list() {
		return sb_list;
	}

	public void setSb_list(ListModelList<Allinone_ListModel> sb_list) {
		this.sb_list = sb_list;
	}

	public ListModelList<Allinone_ListModel> getHouse_list() {
		return house_list;
	}

	public void setHouse_list(ListModelList<Allinone_ListModel> house_list) {
		this.house_list = house_list;
	}

	public ListModelList<Allinone_ListModel> getEmcompact_list() {
		return emcompact_list;
	}

	public void setEmcompact_list(
			ListModelList<Allinone_ListModel> emcompact_list) {
		this.emcompact_list = emcompact_list;
	}

	public ListModelList<Allinone_ListModel> getWt_list() {
		return wt_list;
	}

	public void setWt_list(ListModelList<Allinone_ListModel> wt_list) {
		this.wt_list = wt_list;
	}

	public ListModelList<Allinone_ListModel> getGz_list() {
		return gz_list;
	}

	public void setGz_list(ListModelList<Allinone_ListModel> gz_list) {
		this.gz_list = gz_list;
	}

	public ListModelList<Allinone_ListModel> getFile_list() {
		return file_list;
	}

	public void setFile_list(ListModelList<Allinone_ListModel> file_list) {
		this.file_list = file_list;
	}

	public ListModelList<Allinone_ListModel> getTj_list() {
		return tj_list;
	}

	public void setTj_list(ListModelList<Allinone_ListModel> tj_list) {
		this.tj_list = tj_list;
	}

	public ListModelList<Allinone_ListModel> getCard_list() {
		return card_list;
	}

	public void setCard_list(ListModelList<Allinone_ListModel> card_list) {
		this.card_list = card_list;
	}

	public ListModelList<Allinone_ListModel> getHj_list() {
		return hj_list;
	}

	public void setHj_list(ListModelList<Allinone_ListModel> hj_list) {
		this.hj_list = hj_list;
	}

	public ListModelList<Allinone_ListModel> getGzk_list() {
		return gzk_list;
	}

	public void setGzk_list(ListModelList<Allinone_ListModel> gzk_list) {
		this.gzk_list = gzk_list;
	}

	public ListModelList<Allinone_ListModel> getGlk_list() {
		return glk_list;
	}

	public void setGlk_list(ListModelList<Allinone_ListModel> glk_list) {
		this.glk_list = glk_list;
	}

	public Allinone_ListModel getModel() {
		return model;
	}

	public void setModel(Allinone_ListModel model) {
		this.model = model;
	}

	public ListModelList<Allinone_ListModel> getSy_list() {
		return sy_list;
	}

	public void setSy_list(ListModelList<Allinone_ListModel> sy_list) {
		this.sy_list = sy_list;
	}

	public ListModelList<Allinone_ListModel> getPay_list() {
		return pay_list;
	}

	public void setPay_list(ListModelList<Allinone_ListModel> pay_list) {
		this.pay_list = pay_list;
	}

	public ListModelList<EmBaseCompactListModel> getEmcompact() {
		return emcompact;
	}

	public void setEmcompact(ListModelList<EmBaseCompactListModel> emcompact) {
		this.emcompact = emcompact;
	}

}
