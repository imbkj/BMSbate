package Controller.CoBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;

import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoBase.CoBase_SelectBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoServiceRequestModel;

public class CoBase_DetailInfoController extends SelectorComposer<Component> {
	@Wire
	private Textbox singn;
	@Wire
	private Grid linkman;
	@Wire
	private Grid cocogrid;
	private String cid = (String) Executions.getCurrent().getArg().get("cid");
	private String str = " and a.cid=" + cid;
	private CoBase_SelectBll bll = new CoBase_SelectBll();
	private List<CoBaseModel> modellist = bll.getCobaseinfo(str);
	private CoBaseModel model = modellist.get(0);
	//获取合同信息
	private List<CoCompactModel> cobaseinfo;
	private ListModel<CoCompactModel> cocolist;

	// 获取公司联系人数据
	CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
	List<CoAgencyLinkmanModel> linkmanModel;
	ListModel<CoAgencyLinkmanModel> linklist;
	CoAgencyLinkmanModel linkmanM = new CoAgencyLinkmanModel();
	
	private CoServiceRequestSelectBll sbll=new CoServiceRequestSelectBll();
	private CoServiceRequestModel servicemodel=sbll.getRequestInfo(" and a.cid="+cid);

	// 重写组件初始化方法
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		if (model.getCoba_sign() == 1) {
			singn.setValue("是");
		} else {
			singn.setValue("否");
		}
		linkmanModel = lmBll.getLinkmanByCid(Integer.parseInt(cid));
		linklist = new ListModelList<CoAgencyLinkmanModel>(linkmanModel);
		linkman.setModel(linklist);
		
		//绑定合同信息
		cobaseinfo =bll.searchCoCoBase(" and a.cid="+cid);
		cocolist=new ListModelList<CoCompactModel>(cobaseinfo);
		cocogrid.setModel(cocolist);
	}
	
	//弹出公司合同详细信息
	public void openCocoDetail(String coco_id) {
		//专递cocoM
	
		CoCompactModel cocoM=new CoCompactModel();
		List<CoCompactModel> cobasein =bll.searchCoCoBase(" and coco_id="+coco_id);
		if(!cobasein.isEmpty())
		{
			cocoM=cobasein.get(0);
		}
		Map map = new HashMap();
	
		map.put("cocoM",cocoM);
		Window window = (Window)Executions.createComponents("/CoCompact/Compact_Detail.zul",null, map);
		window.doModal();
		
	}

	public CoBaseModel getModel() {
		return model;
	}

	public void setModel(CoBaseModel model) {
		this.model = model;
	}

	public CoAgencyLinkmanModel getLinkmanM() {
		return linkmanM;
	}

	public void setLinkmanM(CoAgencyLinkmanModel linkmanM) {
		this.linkmanM = linkmanM;
	}

	public CoServiceRequestModel getServicemodel() {
		return servicemodel;
	}

	public void setServicemodel(CoServiceRequestModel servicemodel) {
		this.servicemodel = servicemodel;
	}

}
