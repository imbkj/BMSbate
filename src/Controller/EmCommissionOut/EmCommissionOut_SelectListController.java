package Controller.EmCommissionOut;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutModel;
import Model.PubStateModel;
import Util.RegexUtil;
import Util.UserInfo;
import bll.EmCommissionOut.EmCommissionOutListBll;

public class EmCommissionOut_SelectListController {
	private List<EmCommissionOutModel> ecocList;
	private List<EmCommissionOutModel> secocList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> cityList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> coabList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> clientList = new ListModelList<>();
	private List<PubStateModel> stateList = new ListModelList<>();
	private List<EmCommissionOutChangeModel> countList = new ListModelList<>();

	String username = UserInfo.getUsername();
	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String gid = "";
	private String name = "";
	private String idcard = "";
	private String city = "";
	private String coabname = "";
	private String client = "";
	private String statename = "";

	public EmCommissionOut_SelectListController() {
		try {
			bind();
			search();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化绑定
	 * 
	 */
	public void bind() {
		EmCommissionOutListBll bll = new EmCommissionOutListBll();

		try {
			//setEcocList(bll.getEmCommOutList(""));
			setCityList(bll.getCityList("",""));
			cityList.add(0, null);
			setCoabList(bll.getCoagencyList());
			coabList.add(0, null);
			setClientList(bll.getClientList());
			clientList.add(0, null);

			setCountList(bll.getCountList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列表检索
	 * 
	 */
	@Command("search")
	@NotifyChange({ "secocList" })
	public void search() {
		secocList.clear();
		StringBuilder sqlwhere =new StringBuilder();
		
			if (!cid.isEmpty()) {
				
				sqlwhere.append(" and a.cid="+cid+""); 
			}
			if (!shortname.isEmpty()) {
				
				sqlwhere.append(" and c.coba_shortname like '%"+shortname+""); 
				sqlwhere.append("%' "); 
			}
			if (!gid.isEmpty()) {
				
				sqlwhere.append(" and a.gid="+gid+""); 
			}
			if (!name.isEmpty()) {
				
				sqlwhere.append(" and emba_name LIKE  '%"+name+""); 
				sqlwhere.append("%' "); 
			}
			if (!idcard.isEmpty()) {
			
				sqlwhere.append(" and ecou_idcard = '"+idcard+"'"); 
				 
			}
			if (!city.isEmpty()) {
				
				sqlwhere.append(" and d.city = '"+city+"'"); 
			}
			if (!coabname.isEmpty()) {
			
				
				sqlwhere.append("c.coba_company LIKE '%"+coabname+""); 
				sqlwhere.append("%' "); 
				 
			}
			if (!client.isEmpty()) {
				
				
				sqlwhere.append(" and c.coba_client = '"+client+"'"); 
			}
			if (!statename.isEmpty()) {
				
					 
				  
				if (statename.equals("在职")) {
					sqlwhere.append(" and ecou_state=1 "); 
				}
				if (statename.equals("离职")) {
					sqlwhere.append(" and ecou_state=0 "); 
				}
				if (statename.equals("退回")) {
					sqlwhere.append(" and ecou_state=3 "); 
				}
				 
			}
			//setEcocList();
			EmCommissionOutListBll bll = new EmCommissionOutListBll();
			secocList=bll.getEmCommOutList(sqlwhere.toString());
			
			//secocList.add(m);
		}


	/**
	 * 弹窗
	 * 
	 * @param m
	 * @param url
	 */
	@Command("openwin")
	public void detail(@BindingParam("each") EmCommissionOutModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getEcou_id());

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	public final List<EmCommissionOutModel> getEcocList() {
		return ecocList;
	}

	public final List<EmCommissionOutModel> getSecocList() {
		return secocList;
	}

	public final List<CoAgencyBaseCityRelViewModel> getCityList() {
		return cityList;
	}

	public final List<CoAgencyBaseCityRelViewModel> getCoabList() {
		return coabList;
	}

	public final List<EmCommissionOutChangeModel> getClientList() {
		return clientList;
	}

	public final List<PubStateModel> getStateList() {
		return stateList;
	}

	public final List<EmCommissionOutChangeModel> getCountList() {
		return countList;
	}

	public final String getCid() {
		return cid;
	}

	public final String getShortname() {
		return shortname;
	}

	public final String getGid() {
		return gid;
	}

	public final String getName() {
		return name;
	}

	public final String getIdcard() {
		return idcard;
	}

	public final String getCity() {
		return city;
	}

	public final String getCoabname() {
		return coabname;
	}

	public final String getClient() {
		return client;
	}

	public final String getStatename() {
		return statename;
	}

	public final void setEcocList(List<EmCommissionOutModel> ecocList) {
		this.ecocList = ecocList;
	}

	public final void setSecocList(List<EmCommissionOutModel> secocList) {
		this.secocList = secocList;
	}

	public final void setCityList(List<CoAgencyBaseCityRelViewModel> cityList) {
		this.cityList = cityList;
	}

	public final void setCoabList(List<CoAgencyBaseCityRelViewModel> coabList) {
		this.coabList = coabList;
	}

	public final void setClientList(List<EmCommissionOutChangeModel> clientList) {
		this.clientList = clientList;
	}

	public final void setStateList(List<PubStateModel> stateList) {
		this.stateList = stateList;
	}

	public final void setCountList(List<EmCommissionOutChangeModel> countList) {
		this.countList = countList;
	}

	public final void setCid(String cid) {
		this.cid = cid;
	}

	public final void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public final void setGid(String gid) {
		this.gid = gid;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public final void setCity(String city) {
		this.city = city;
	}

	public final void setCoabname(String coabname) {
		this.coabname = coabname;
	}

	public final void setClient(String client) {
		this.client = client;
	}

	public final void setStatename(String statename) {
		this.statename = statename;
	}
}
