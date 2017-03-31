package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.ResponsibilityBookModel;
import Util.RegexUtil;
import bll.CoReg.CoReg_ResponseBookBll;

public class CoReg_ResponseBookQueryController {

	private List<ResponsibilityBookModel> rlist;
	private List<ResponsibilityBookModel> srlist = new ArrayList<ResponsibilityBookModel>();
	private CoReg_ResponseBookBll bll = new CoReg_ResponseBookBll();
	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String limit = "";
	private String addname = "";
	private Date addtime;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	

	public CoReg_ResponseBookQueryController() {
		init();
	}

	public void init() {
		rlist = bll.getRlist();
		search();
	}

	@Command
	@NotifyChange("srlist")
	public void search() {
		if(rlist.size() > 0){
			srlist.clear();
			for(ResponsibilityBookModel rm : rlist){
				if (!cid.isEmpty()) {
					if (!RegexUtil.isExists(cid, rm.getCid() + "")) {
						continue;
					}
				}
				if (!shortname.isEmpty()) {
					if (!RegexUtil.isExists(shortname, rm.getCoba_shortname())) {
						continue;
					}
				}
				if (!limit.isEmpty()) {
					if (!RegexUtil.isExists(limit, rm.getRebk_limit())) {
						continue;
					}
				}
				if (!addname.isEmpty()) {
					if (!RegexUtil.isExists(addname, rm.getRebk_addname())) {
						continue;
					}
				}
				if (addtime != null) {
					if (!RegexUtil.isExists(sdf.format(addtime),
							rm.getAddtime())) {
						continue;
					}
				}
				srlist.add(rm);
			}
		}
	}

	// 查看详情
	@Command
	public void more(@BindingParam("m") ResponsibilityBookModel m) {
		Map<String,ResponsibilityBookModel> map = new HashMap<String,ResponsibilityBookModel>();
		map.put("m", m);
		Window win = (Window) Executions.createComponents("CoReg_queryResbookInfo.zul", null, map);
		win.doModal();
	}

	
	
	public List<ResponsibilityBookModel> getSrlist() {
		return srlist;
	}

	public void setSrlist(List<ResponsibilityBookModel> srlist) {
		this.srlist = srlist;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public List<ResponsibilityBookModel> getRlist() {
		return rlist;
	}

	public void setRlist(List<ResponsibilityBookModel> rlist) {
		this.rlist = rlist;
	}

}
