package Controller.CoHousingFund;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoHousingFund.CoHousingFund_ListBll;

import Model.CoHousingFundChangeModel;
import Model.PubStateModel;
import Util.CoHousingFundUtil;
import Util.FileOperate;
import Util.RegexUtil;

public class CoHousingFund_qdOperateListController {

	private List<CoHousingFundChangeModel> chfcList;
	private List<CoHousingFundChangeModel> schfcList = new ListModelList<>();
	private List<PubStateModel> stateList = new ListModelList<>();

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String addtype = "";
	private String ownmonth = "";
	private String houseid = "";
	private String ispwd = "";
	private String state = "";

	public CoHousingFund_qdOperateListController() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 数据初始化
	 * 
	 */
	public void init() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		try {
			setChfcList(bll.getCoHoChangeList(""));
			setStateList(bll.getStateList(" and type='cogjj'"));
			stateList.add(0, null);

			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检索
	 * 
	 */
	@Command
	@NotifyChange({ "schfcList" })
	public void search() {
		try {
			if (chfcList != null && chfcList.size() > 0) {
				schfcList.clear();

				for (CoHousingFundChangeModel m : chfcList) {
					if (!cid.isEmpty()) {
						if (!RegexUtil.isExists(cid, m.getCid() + "")) {
							continue;
						}
					}
					if (!shortname.isEmpty()) {
						if (!RegexUtil.isExists(shortname,
								m.getCoba_shortname())) {
							continue;
						}
					}
					if (!addtype.isEmpty()) {
						if (!m.getChfc_addtype().equals(addtype)) {
							continue;
						}
					}
					if (ownmonth != null) {
						if (!RegexUtil.isExists(ownmonth, m.getOwnmonth() + "")) {
							continue;
						}
					}
					if (!houseid.isEmpty()) {
						if (!m.getChfc_houseid().equals(houseid)) {
							continue;
						}
					}
					if (!ispwd.isEmpty()) {
						if (!m.getIspwd().equals(ispwd)) {
							continue;
						}
					}
					if (!state.isEmpty()) {
						if (!m.getStatename().equals(state)) {
							continue;
						}
					}

					schfcList.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("查询出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 弹出窗口
	 * 
	 * @param url
	 *            窗口链接
	 * @param m
	 */
	@Command
	@NotifyChange({ "schfcList" })
	public void openwin(@BindingParam("label") String label,
			@BindingParam("each") CoHousingFundChangeModel m) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getChfc_id());

		String url = CoHousingFundUtil
				.getUrl(label.trim(), m.getChfc_addtype());

		try {
			if (!url.isEmpty()) {
				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();

				CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
				setChfcList(bll.getCoHoChangeList(""));
				search();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("弹出窗口出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}
	
	@Command
	public void down(@BindingParam("a") CoHousingFundChangeModel m){
		String absolutePath = FileOperate.getAbsolutePath();
		String uploadfolder = "./OfficeFile/UpLoad/CoHousingFund/";
		try {
			Filedownload.save(new File(absolutePath+uploadfolder+m.getChfc_file()), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<CoHousingFundChangeModel> getChfcList() {
		return chfcList;
	}

	public void setChfcList(List<CoHousingFundChangeModel> chfcList) {
		this.chfcList = chfcList;
	}

	public final String getCid() {
		return cid;
	}

	public final String getShortname() {
		return shortname;
	}

	public final String getAddtype() {
		return addtype;
	}

	public final String getOwnmonth() {
		return ownmonth;
	}

	public final String getHouseid() {
		return houseid;
	}

	public final String getIspwd() {
		return ispwd;
	}

	public final String getState() {
		return state;
	}

	public final void setCid(String cid) {
		this.cid = cid;
	}

	public final void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public final void setAddtype(String addtype) {
		this.addtype = addtype;
	}

	public final void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public final void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public final void setIspwd(String ispwd) {
		this.ispwd = ispwd;
	}

	public final void setState(String state) {
		this.state = state;
	}

	public List<CoHousingFundChangeModel> getSchfcList() {
		return schfcList;
	}

	public void setSchfcList(List<CoHousingFundChangeModel> schfcList) {
		this.schfcList = schfcList;
	}

	public List<PubStateModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<PubStateModel> stateList) {
		this.stateList = stateList;
	}
}
