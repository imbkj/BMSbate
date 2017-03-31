package Controller.Taskflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Util.UserInfo;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.SystemControl.EmBuCenter_SelectBll;
import bll.Taskflow.EmBaseMenulistBll;
import bll.Taskflow.EmBaseMenulistSelectBll;

public class EmBaseMenulistController {
	private Integer gid = 0;
	private Integer cid = 0;
	private Integer embaId = 0;
	// 当taprid不为空的时候就表示是在任务单中心打开业务中心，daid表示TaskBatch表的id,如果taprid为空则表示是在员工列表中打开业务中心，此时daid是从
	// 员工列表中传过来的gid
	private Integer daid = 0;
	// 从任务单中心传过来的任务单id
	private Integer taprid = 0;

	// 业务中心菜单查询的bll
	EmBuCenter_SelectBll bll = new EmBuCenter_SelectBll();
	// lists用于递归函数的时候存放数据（用于暂时存放）
	private List<EmbaseBusinessCenterModel> lists = new ArrayList<EmbaseBusinessCenterModel>();
	// 生成树的时候使用的菜单的list
	private List<EmbaseBusinessCenterModel> pubtreelist = new ArrayList<EmbaseBusinessCenterModel>();
	private String s = "";
	private Object did = Executions.getCurrent().getArg().get("daid");
	private Object tid =Executions.getCurrent().getArg().get("id");
	private EmBaseMenulistSelectBll selectbll = new EmBaseMenulistSelectBll();
	private EmbaseModel emmodel = new EmbaseModel();
	
	/*******************************分配业务***************************************/
	
	/* 必填字段列表 */
	private List<String> requiredList = new ArrayList<String>();
	private List<String> folkList;
	
	private Date birth = null;// 生日
	private Date graduation = null;// 毕业时间
	private PubCodeConversionModel dgModel = new PubCodeConversionModel();// 文化程度
	private String fileinclass = "";// 档案是否愿意转入中智托管
	private String filedebts = "";// 档案是否有欠费
	private String filehj = "";// 户口是否托管在人才
	private String sbcard = "";// 是否需要办理社保卡
	private String excompanystate = "";// 原单位是否已封存
	private Date housetime = null;// 入住时间
	private Date worktime = null;// 参加工作时间
	private Date sztime = null;// 来深日期
	private Date hjtime = null;// 户口进深日期
	private Date compactstart = null;// 劳动合同开始时间
	private Date compactend = null;// 劳动合同结束时间
	private Date companystart = null;// 本单位工作起始时间
	private Date bctime = null;// 体检时间
	private EmBcSetupModel bcsetupModel = new EmBcSetupModel();// 体检医院
	private List<EmbaseBusinessCenterModel> embulist=new ArrayList<EmbaseBusinessCenterModel>();
	/* 下拉列表绑定 */
	private List<PubCodeConversionModel> degreeList;
	private List<PubCodeConversionModel> partyList;
	private List<PubCodeConversionModel> titleList;
	private List<PubCodeConversionModel> housetypeList;
	private List<PubCodeConversionModel> houseclassList;
	private List<PubCodeConversionModel> skilllevelList;
	private List<PubCodeConversionModel> regtypeList;
	private List<EmBcSetupModel> bcsetupList;
	private List<EmBcSetupAddressModel> bcsetupaddList;
	private List<EmBcSetupAddressModel> bcsetupaddList1 = new ListModelList<EmBcSetupAddressModel>();
	// 日期格式
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/*******************************分配业务End************************************/
	
		
	
	public EmBaseMenulistController() {
		
		
		if (did != null) {
			daid = Integer.valueOf(did.toString());
		}

		if (tid != null) {
			taprid = Integer.valueOf(tid.toString());
		}

		// 当taprid不为空的时候就表示是从任务单中心打开页面
		if (taprid != null && taprid > 0) {
			// 根据穿过来的TaskBatch表的id查询员工的gid
			if (daid != null) {
				gid = bll.getgid(daid);
				embaId = bll.getEmbaseList(gid).get(0).getEmba_id();
					
				
			} else {
				s = "系统出错，请联系开发人员";
			}
		} else {
			// taprid为空的时候表示是从员工列表传过来的gid
			gid = daid;
			embaId = (Integer) Executions.getCurrent().getArg().get("embaId");
		}
		
		List<EmbaseModel> embaselist = selectbll
				.getEmbaseLoginInfo(gid);
		if (!embaselist.isEmpty()) {
			emmodel = embaselist.get(0);
			cid = emmodel.getCid();
		}
		
		/*******************************分配业务***************************************/
		
		EmbaseLogin_AddBll bllbase= new EmbaseLogin_AddBll();
		setDegreeList(new ListModelList<PubCodeConversionModel>(
				bllbase.getPubCodeList("文化程度")));
		degreeList.add(0, new PubCodeConversionModel());
		setPartyList(new ListModelList<PubCodeConversionModel>(
				bllbase.getPubCodeList("政治面貌")));
		partyList.add(0, new PubCodeConversionModel());
		setTitleList(new ListModelList<PubCodeConversionModel>(
				bllbase.getPubCodeList("职称")));
		titleList.add(0, new PubCodeConversionModel());
		setHousetypeList(new ListModelList<PubCodeConversionModel>(
				bllbase.getPubCodeList("住所类别")));
		housetypeList.add(0, new PubCodeConversionModel());
		setHouseclassList(new ListModelList<PubCodeConversionModel>(
				bllbase.getPubCodeList("居住方式")));
		houseclassList.add(0, new PubCodeConversionModel());
		setSkilllevelList(new ListModelList<PubCodeConversionModel>(
				bllbase.getPubCodeList("职业技能等级")));
		skilllevelList.add(0, new PubCodeConversionModel());
		setRegtypeList(new ListModelList<PubCodeConversionModel>(
				bllbase.getPubCodeList("就业类型")));
		regtypeList.add(0, new PubCodeConversionModel());
		setBcsetupList(new ListModelList<EmBcSetupModel>(
				bllbase.getBcSetupList()));
		bcsetupList.add(0, new EmBcSetupModel());
		setBcsetupaddList(new ListModelList<EmBcSetupAddressModel>(
				bllbase.getBcSetupAddressList()));
		setFolkList(new ListModelList<String>(bllbase.getFolkList()));
		folkList.add(0, "");
		fieldinit();
		
		/*******************************分配业务End************************************/
	}
	
	// 树的onCreate事件，用于生成树的时候生成树节点
		@Command
		public void oncreatetree(@BindingParam("tree") Tree tree,
				@BindingParam("refleshurl") Include refleshurl) {
			List<Integer> lt=new ArrayList<Integer>();
			// 菜单的查询条件
			String strs = "";
			strs = strs + " and emce_id in(select embu_id from EmbuGroupRel ";
			strs = strs
					+ " where rol_id in(select a.rol_id from logingroup a,Login b where a.log_id=b.log_id and log_name='"
					+ UserInfo.getUsername() + "'))";
			// 把操作者的菜单权限的所有菜单查出来
			pubtreelist = null;
			// List<EmbaseBusinessCenterModel> treelists=null;
			// pubtreelist = bll.getEmbaseBusinessCenterInfo(strs);
			// treelists=pubtreelist;
			// taprid为不空就表示是在任务单打开业务中心
			String strid = "";
			if (taprid != null && taprid > 0) {
				if (gid != null && gid > 0) {
					// 查询EmOnBoardList表获取索要打开的菜单id
					List<EmbaseBusinessCenterModel> olist = bll
							.getEmOnBoardList(gid);
					if (olist.size() > 0) {
						for (int j = 0; j < olist.size(); j++) {
							if (j == 0) {
								strid = olist.get(j).getEmce_id() + "";
							} else {
								strid = strid + "," + olist.get(j).getEmce_id()
										+ "";
							}
						}
					}
					// 调用test方法获取菜单
					// getlists(pubtreelist, olist);
					// 把暂时存储的id的lists的值赋给pubtreelist
					// if(lists.size()>0)
					// {
					// pubtreelist = lists;
					// }
				} else {
					s = "系统出错，请联系开发人员";
				}
			}
			if (s == "") {
				if (strid.equals("") || strid == "") {
					pubtreelist = bll.getEmbaseBusinessCenterInfo(strs);
				} else {
					strs=strs+"  and (emce_must<>3 or emce_must is null) ";
					pubtreelist = bll.getEmbaseBusinessCenterInfo(strs
							+ " and (emce_id in(" + strid + ") or emce_id in(select emce_pid from " +
									"EmbaseBusinessCenter where emce_id in("
							+ strid + ")) or emce_pid in("+ strid + "))");
				}
				lt=selectbll.getEmbaseMenuList(gid);
				// 如果pubtreelist不等于空则开始生成树的子节点
				if (!pubtreelist.isEmpty()) {
					Treechildren trc = new Treechildren();
					// 生成事件监控类，监控Treechildren的点击事件
//					trc.addEventListener("onClick", new MyListener(trc, refleshurl,
//							tree));
					trc.setParent(tree);
					for (int y = 0; y < pubtreelist.size(); y++) {
						EmbaseBusinessCenterModel model = pubtreelist.get(y);
						if (model.getEmce_pid() == 0) {
							// 生成一个树的子节点
							Treeitem newitem = new Treeitem();
							newitem.setParent(trc);
							newitem.setOpen(true);
							newitem.setLabel(model.getEmce_menuname());
							newitem.setId("id" + model.getEmce_id());
							newitem.setValue(model.getEmce_menuurl());
							newitem.setCheckable(false);
							// newitem.addEventListener("onClose", new
							// MyListener());
							// 调用递归函数
							AddTreeItem(newitem, model.getEmce_id(), pubtreelist,
									refleshurl, tree,lt);
						}
					}
				}
			} else {
				Messagebox.show(s, "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}

		// 定义递归函数AddTreeItem()
		private void AddTreeItem(Treeitem ptem, int parentID,
				List<EmbaseBusinessCenterModel> list, Include refleshurl, Tree tree,List<Integer> lt) {
			Treechildren newchild = new Treechildren();
			// 生成事件监控类，监控Treechildren的点击事件
			//newchild.addEventListener("onClick", new MyListener(newchild,
				//	refleshurl, tree));
			for (int i = 0; i < list.size(); i++) {
				EmbaseBusinessCenterModel model = list.get(i);
				if (model.getEmce_pid() == parentID) {
					newchild.setParent(ptem);
					Treeitem item = new Treeitem();
					item.setOpen(true);
					item.setParent(newchild);
					item.setId("id" + model.getEmce_id());
					item.setValue(model.getEmce_menuurl());
					Treerow tw = new Treerow();
					Treecell tl = new Treecell();
					tl.setLabel(model.getEmce_menuname());
					tl.setParent(tw);
					tw.setParent(item);
					for(int ij=0;ij<lt.size();ij++)
					{
						if(model.getEmce_id().equals(lt.get(ij)))
						{
							item.setSelected(true);
						}
					}
					// 函数递归
					AddTreeItem(item, model.getEmce_id(), list, refleshurl, tree,lt);
				}
			}
		}

		// Treechildren的点击事件监控类
		class MyListener implements org.zkoss.zk.ui.event.EventListener {
			Treechildren trc = null;
			Tree tree = null;
			Include refleshurl = null;

			public void onEvent(Event e) throws UiException {
				Treeitem item = tree.getSelectedItem();
				if (item != null) {
					if (item.isOpen()) {
						item.setOpen(false);
					} else {
						item.setOpen(true);
					}
				}
				if (item.getValue() != null && !item.getValue().equals("")
						&& item.getValue() != "") {
					refleshurl.setSrc("");
					refleshurl.setSrc(item.getValue() + "");
				}
			}

			// 类MyListener的构造函数
			public MyListener(Treechildren trc, Include refleshurl, Tree tree) {
				this.trc = trc;
				this.refleshurl = refleshurl;
				this.tree = tree;
			}
		}
		
		
		/*******************************分配业务***************************************/
		// "*"初始化
		@Command("LabelInit")
		public void LabelInit(@BindingParam("self") Label lbl) {
			try {
				if (requiredList.contains(lbl.getId())) {
					lbl.setVisible(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		// 特殊字段初始化
		public void fieldinit() {
			try {
				birth = emmodel.getEmba_birth() != null ? sdf.parse(emmodel.getEmba_birth())
						: null;
				graduation = emmodel.getEmba_graduation() != null ? sdf.parse(emmodel
						.getEmba_graduation()) : null;
				for (int i = 0; i < degreeList.size(); i++) {
					if (degreeList.get(i).getPcco_cn() != null) {
						if (degreeList.get(i).getPcco_cn()
								.equals(emmodel.getEmba_degree())) {
							dgModel = degreeList.get(i);
						}
					}
				}
				fileinclass = emmodel.getEmba_fileinclass() == 0 ? "否" : "是";
				filedebts = emmodel.getEmba_filedebts() == 0 ? "否" : "是";
				filehj = emmodel.getEmba_filehj() == 0 ? "否" : "是";
				sbcard = emmodel.getEmba_sbcard() == "否" ? "否" : "是";
				excompanystate = emmodel.getEmba_excompanystate() == 0 ? "否" : "是";
				housetime = emmodel.getEmba_housetime() != null ? sdf.parse(emmodel
						.getEmba_housetime()) : null;
				worktime = emmodel.getEmba_worktime() != null ? sdf.parse(emmodel
						.getEmba_worktime()) : null;
				sztime = emmodel.getEmba_sztime() != null ? sdf.parse(emmodel.getEmba_sztime())
						: null;
				hjtime = emmodel.getEmba_hjtime() != null ? sdf.parse(emmodel.getEmba_hjtime())
						: null;
				compactstart = emmodel.getEmba_compactstart() != null ? sdf.parse(emmodel
						.getEmba_compactstart()) : null;
				compactend = emmodel.getEmba_compactend() != null ? sdf.parse(emmodel
						.getEmba_compactend()) : null;
				companystart = emmodel.getEmba_companystart() != null ? sdf.parse(emmodel
						.getEmba_companystart()) : null;
				bctime = emmodel.getEmba_bctime() != null ? sdf.parse(emmodel.getEmba_bctime())
						: null;
				for (int i = 0; i < bcsetupList.size(); i++) {
					if (bcsetupList.get(i).getEbcs_hospital() != null) {
						if (bcsetupList.get(i).getEbcs_hospital()
								.equals(emmodel.getEmba_hospital())) {
							bcsetupModel = bcsetupList.get(i);
						}
					}
				}
			} catch (Exception e) {
				Messagebox.show("字段加载失败,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				System.out.println(e.toString());
			}
		}
		
		// 提交
		@Command("save")
		public void save(@BindingParam("win") Window win,@BindingParam("tree") Tree tree) throws Exception {
			String str=updateInfo();
			if(str.equals(""))
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show(str, "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		
		
		
		//下一步
		@Command
		public void submit(@BindingParam("win") Window win)
		{
			String str=updateInfo();
			String[] ss=new String[5];
			//如果提交成功则下一步
			if(str.equals(""))
			{
				EmBaseMenulistBll uppbll=new EmBaseMenulistBll();
				ss=uppbll.EmbaseMenuListUpdate(taprid, daid);
				if(ss[0]=="1")
				{
					Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					win.detach();
				}
				else
				{
					Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
			else
			{
				Messagebox.show(str, "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
			
		}

		
		/*******************************分配业务End************************************/

		//选择业务中心菜单的checkbox事件
		@Command
		public void embumenucheck(@BindingParam("tree") Tree tree){
			if (!embulist.isEmpty())
				embulist.clear();
			if(tree.getSelectedItem()!=null)
			{
				Set<Treeitem> i=tree.getSelectedItems();
				EmbaseBusinessCenterModel emb=new EmbaseBusinessCenterModel();
				emb.setEmce_id(3);
				embulist.add(emb);
				for(Treeitem t : i){
					t.setOpen(true);
					EmbaseBusinessCenterModel embumodel=new EmbaseBusinessCenterModel();
					//基本信息菜单
					
					String idstr=t.getId();
					int id = Integer.parseInt(idstr.substring(2));
					embumodel.setEmce_id(id);
					this.embulist.add(embumodel);
//					for(int jk=0;jk<pubtreelist.size();jk++)
//					{
//						if(pubtreelist.get(jk).getEmce_id().equals(id))
//						{
//							if(pubtreelist.get(jk).getEmce_pid()==0)
//							{
//								List<Component> li=t.getTreechildren().getChildren();
//								if(li.size()>0)
//								{
//									for(int ju=0;ju<li.size();ju++)
//									{
//										Treeitem tritem=(Treeitem)li.get(ju);
//										tritem.setSelected(true);
//									}
//								}
//							}
//						}
//					}
				}
			}
		}
		
		private String updateInfo()
		{
			String strs="";
			try {
				handle();
			} catch (Exception e) {
				// TODO: handle exception
				strs="数据处理失败,请联系IT部门!";
				System.out.println(e.toString());
			}
			try {
				// 修改
				EmBaseMenulistBll uppbll=new EmBaseMenulistBll();
				emmodel.setCid(cid);
				int kid = uppbll.EmbaseUpdate(emmodel);
				if(kid>0)
				{
					if(embulist.size()>0)
					{
						uppbll.EmbaseMenuListdelete(gid);
						for(int i=0;i<embulist.size();i++)
						{
							uppbll.EmbaseMenuListAdd(gid, embulist.get(i).getEmce_id());
						}
					}
				}
				else
				{
					strs="提交失败";
				}
				} catch (Exception e) {
					// TODO: handle exception
					strs="提交失败,请联系IT部门!";
					System.out.println(e.toString());
				}
			return strs;
		}
		
		// 处理特殊字段
				public void handle() {
					emmodel.setEmba_birth(birth != null ? sdf.format(birth) : null);
					emmodel.setEmba_graduation(graduation != null ? sdf.format(graduation) : null);
					emmodel.setEmba_degree(dgModel.getPcco_cn());
					emmodel.setEmba_fileinclass(fileinclass.equals("是") ? 1 : 0);
					emmodel.setEmba_filedebts(filedebts.equals("是") ? 1 : 0);
					emmodel.setEmba_filehj(filehj.equals("是") ? 1 : 0);
					emmodel.setEmba_sbcard(sbcard.equals("是") ?"是" : "否");
					emmodel.setEmba_excompanystate(excompanystate.equals("是") ? 1 : 0);
					emmodel.setEmba_housetime(housetime != null ? sdf.format(housetime) : null);
					emmodel.setEmba_worktime(worktime != null ? sdf.format(worktime) : null);
					emmodel.setEmba_sztime(sztime != null ? sdf.format(sztime) : null);
					emmodel.setEmba_hjtime(hjtime != null ? sdf.format(hjtime) : null);
					emmodel.setEmba_compactstart(compactstart != null ? sdf.format(compactstart)
							: null);
					emmodel.setEmba_compactend(compactend != null ? sdf.format(compactend) : null);
					emmodel.setEmba_companystart(companystart != null ? sdf.format(companystart)
							: null);
					emmodel.setEmba_bctime(bctime != null ? sdf.format(bctime) : null);
					emmodel.setEmba_hospital(bcsetupModel.getEbcs_hospital());
				}
		
		public Integer getGid() {
			return gid;
		}

		public void setGid(Integer gid) {
			this.gid = gid;
		}

		public Integer getCid() {
			return cid;
		}

		public void setCid(Integer cid) {
			this.cid = cid;
		}

		public Integer getEmbaId() {
			return embaId;
		}

		public void setEmbaId(Integer embaId) {
			this.embaId = embaId;
		}
		
		public List<String> getFolkList() {
			return folkList;
		}

		public void setFolkList(List<String> folkList) {
			this.folkList = folkList;
		}
		
		

		public Object getDid() {
			return did;
		}

		public void setDid(Object did) {
			this.did = did;
		}

		public EmbaseModel getEmmodel() {
			return emmodel;
		}

		public void setEmmodel(EmbaseModel emmodel) {
			this.emmodel = emmodel;
		}

		public Date getBirth() {
			return birth;
		}

		public void setBirth(Date birth) {
			this.birth = birth;
		}

		public Date getGraduation() {
			return graduation;
		}

		public void setGraduation(Date graduation) {
			this.graduation = graduation;
		}

		public PubCodeConversionModel getDgModel() {
			return dgModel;
		}

		public void setDgModel(PubCodeConversionModel dgModel) {
			this.dgModel = dgModel;
		}

		public String getFileinclass() {
			return fileinclass;
		}

		public void setFileinclass(String fileinclass) {
			this.fileinclass = fileinclass;
		}

		public String getFiledebts() {
			return filedebts;
		}

		public void setFiledebts(String filedebts) {
			this.filedebts = filedebts;
		}

		public String getFilehj() {
			return filehj;
		}

		public void setFilehj(String filehj) {
			this.filehj = filehj;
		}

		public String getSbcard() {
			return sbcard;
		}

		public void setSbcard(String sbcard) {
			this.sbcard = sbcard;
		}

		public String getExcompanystate() {
			return excompanystate;
		}

		public void setExcompanystate(String excompanystate) {
			this.excompanystate = excompanystate;
		}

		public Date getHousetime() {
			return housetime;
		}

		public void setHousetime(Date housetime) {
			this.housetime = housetime;
		}

		public Date getWorktime() {
			return worktime;
		}

		public void setWorktime(Date worktime) {
			this.worktime = worktime;
		}

		public Date getSztime() {
			return sztime;
		}

		public void setSztime(Date sztime) {
			this.sztime = sztime;
		}

		public Date getHjtime() {
			return hjtime;
		}

		public void setHjtime(Date hjtime) {
			this.hjtime = hjtime;
		}

		public Date getCompactstart() {
			return compactstart;
		}

		public void setCompactstart(Date compactstart) {
			this.compactstart = compactstart;
		}

		public Date getCompactend() {
			return compactend;
		}

		public void setCompactend(Date compactend) {
			this.compactend = compactend;
		}

		public Date getCompanystart() {
			return companystart;
		}

		public void setCompanystart(Date companystart) {
			this.companystart = companystart;
		}

		public Date getBctime() {
			return bctime;
		}

		public void setBctime(Date bctime) {
			this.bctime = bctime;
		}

		public EmBcSetupModel getBcsetupModel() {
			return bcsetupModel;
		}

		public void setBcsetupModel(EmBcSetupModel bcsetupModel) {
			this.bcsetupModel = bcsetupModel;
		}
		

		public List<EmbaseBusinessCenterModel> getPubtreelist() {
			return pubtreelist;
		}

		public void setPubtreelist(List<EmbaseBusinessCenterModel> pubtreelist) {
			this.pubtreelist = pubtreelist;
		}

		public List<String> getRequiredList() {
			return requiredList;
		}

		public void setRequiredList(List<String> requiredList) {
			this.requiredList = requiredList;
		}

		public List<PubCodeConversionModel> getDegreeList() {
			return degreeList;
		}

		public void setDegreeList(List<PubCodeConversionModel> degreeList) {
			this.degreeList = degreeList;
		}

		public List<PubCodeConversionModel> getPartyList() {
			return partyList;
		}

		public void setPartyList(List<PubCodeConversionModel> partyList) {
			this.partyList = partyList;
		}

		public List<PubCodeConversionModel> getTitleList() {
			return titleList;
		}

		public void setTitleList(List<PubCodeConversionModel> titleList) {
			this.titleList = titleList;
		}

		public List<PubCodeConversionModel> getHousetypeList() {
			return housetypeList;
		}

		public void setHousetypeList(List<PubCodeConversionModel> housetypeList) {
			this.housetypeList = housetypeList;
		}

		public List<PubCodeConversionModel> getHouseclassList() {
			return houseclassList;
		}

		public void setHouseclassList(List<PubCodeConversionModel> houseclassList) {
			this.houseclassList = houseclassList;
		}

		public List<PubCodeConversionModel> getSkilllevelList() {
			return skilllevelList;
		}

		public void setSkilllevelList(List<PubCodeConversionModel> skilllevelList) {
			this.skilllevelList = skilllevelList;
		}

		public List<PubCodeConversionModel> getRegtypeList() {
			return regtypeList;
		}

		public void setRegtypeList(List<PubCodeConversionModel> regtypeList) {
			this.regtypeList = regtypeList;
		}

		public List<EmBcSetupModel> getBcsetupList() {
			return bcsetupList;
		}

		public void setBcsetupList(List<EmBcSetupModel> bcsetupList) {
			this.bcsetupList = bcsetupList;
		}

		public List<EmBcSetupAddressModel> getBcsetupaddList() {
			return bcsetupaddList;
		}

		public void setBcsetupaddList(List<EmBcSetupAddressModel> bcsetupaddList) {
			this.bcsetupaddList = bcsetupaddList;
		}

		public List<EmBcSetupAddressModel> getBcsetupaddList1() {
			return bcsetupaddList1;
		}

		public void setBcsetupaddList1(List<EmBcSetupAddressModel> bcsetupaddList1) {
			this.bcsetupaddList1 = bcsetupaddList1;
		}

		// list1 是所有的菜單，list2是任務單傳過來的菜單
		private void getlists(List<EmbaseBusinessCenterModel> list1,
				List<EmbaseBusinessCenterModel> list2) {
			if (!lists.isEmpty())
				lists.clear();
			for (int i = 0; i < list2.size(); i++) {
				getlists2(list2.get(i).getEmce_id(), list1);
			}
		}

		// 递归函数，用于把传进来的菜单id的父菜单全部找出来
		private void getlists2(int id, List<EmbaseBusinessCenterModel> list) {
			for (int j = 0; j < list.size(); j++) {
				if (id == list.get(j).getEmce_id()) {
					lists.add(list.get(j));
					if (list.get(j).getEmce_pid() != 0) {
						getlists2(list.get(j).getEmce_pid(), list);
					}
				}
			}
		}

}
