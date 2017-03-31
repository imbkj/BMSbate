package Controller.Embase;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Model.EmZYTModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Util.EmbaseFieldUtil;
import Util.UserInfo;
import bll.EmZYT.EmZYT_OperateBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.SystemControl.EmBuCenter_SelectBll;
import bll.Taskflow.EmBaseMenulistSelectBll;

public class Embase_InfoUpdateController {
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();

	// window的title属性
	private String win_title = "";

	private EmbaseModel m = new EmbaseModel();
	private EmBaseMenulistSelectBll selectbll = new EmBaseMenulistSelectBll();
	private Object did = Executions.getCurrent().getArg().get("daid");
	private Object tid = Executions.getCurrent().getArg().get("id");

	/* 必填字段列表 */
	private List<String> requiredList = new ArrayList<String>();

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
	private List<String> folkList;

	// 日期格式
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 特殊字段
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
	private EmBuCenter_SelectBll bll = new EmBuCenter_SelectBll();
	// 必填字段提示
	private String alarmStr = "";
	private String s = "";
	// 页面传值
	private int emba_id = 0;
	private EmZYTModel emztM;
	private Integer gid = 0;
	private Integer cid = 0;
	private Integer embaId = 0;
	private EmbaseModel emmodel = new EmbaseModel();
	// 当taprid不为空的时候就表示是在任务单中心打开业务中心，daid表示TaskBatch表的id,如果taprid为空则表示是在员工列表中打开业务中心，此时daid是从
	// 员工列表中传过来的gid
	private Integer daid = 0;
	// 从任务单中心传过来的任务单id
	private Integer taprid = 0;

	// 初始化
	public Embase_InfoUpdateController() {
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
			// taprid不为空的时候表示是从员工列表传过来的gid
			gid = daid;
			embaId = (Integer) Executions.getCurrent().getArg().get("embaId");
		}
		
		List<EmbaseModel> embaselist = selectbll.getEmbaseLoginInfo(gid);
		if (!embaselist.isEmpty()) {
			emmodel = embaselist.get(0);
			cid = emmodel.getCid();
		}
		try {
			EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();

			setDegreeList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("文化程度")));
			degreeList.add(0, new PubCodeConversionModel());
			setPartyList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("政治面貌")));
			partyList.add(0, new PubCodeConversionModel());
			setTitleList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("职称")));
			titleList.add(0, new PubCodeConversionModel());
			setHousetypeList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("住所类别")));
			housetypeList.add(0, new PubCodeConversionModel());
			setHouseclassList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("居住方式")));
			houseclassList.add(0, new PubCodeConversionModel());
			setSkilllevelList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("职业技能等级")));
			skilllevelList.add(0, new PubCodeConversionModel());
			setRegtypeList(new ListModelList<PubCodeConversionModel>(
					bll.getPubCodeList("就业类型")));
			regtypeList.add(0, new PubCodeConversionModel());
			setBcsetupList(new ListModelList<EmBcSetupModel>(
					bll.getBcSetupList()));
			bcsetupList.add(0, new EmBcSetupModel());
			setBcsetupaddList(new ListModelList<EmBcSetupAddressModel>(
					bll.getBcSetupAddressList()));
			setFolkList(new ListModelList<String>(bll.getFolkList()));
			folkList.add(0, "");
			// 获取window的title
			win_title = emba_id == 0 ? "员工信息新增" : "员工信息补充";

			// 获取必填字段
			requiredList = bll.getRequiredList(emba_id);

			// 获取员工信息
			if (emba_id != 0) {
				List<EmbaseModel> list = new ListModelList<EmbaseModel>(
						selectbll
						.getEmbaseLoginInfo(emba_id));
				m = list.get(0);
				fieldinit();
			}

			// 调用智翼通接口数据携带过来方法
			getEmZYTInfo();

		} catch (Exception e) {
			// TODO: handle exception
			Messagebox.show("页面加载失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	// 将智翼通接口数据携带过来
	public void getEmZYTInfo() {
		if (emztM != null) {
			// 姓名
			if (!"".equals(emztM.getEmzt_t_name())
					&& emztM.getEmzt_t_name() != null) {
				m.setEmba_name(emztM.getEmzt_t_name());
			} else {
				m.setEmba_name(emztM.getEmzt_name());
			}

			// 身份证
			if (!"".equals(emztM.getEmzt_t_idcard())
					&& emztM.getEmzt_t_idcard() != null) {
				m.setEmba_idcard(emztM.getEmzt_t_idcard());
			} else {
				m.setEmba_idcard(emztM.getEmzt_idcard());
			}

			m.setEmba_sex(emztM.getEmzt_sex()); // 性别
			m.setEmba_mobile(emztM.getEmzt_mobile()); // 手机
			m.setEmba_folk(emztM.getEmzt_folk()); // 民族
			m.setEmba_computerid(emztM.getEmzt_computerid()); // 社保电脑号
			m.setEmba_houseid(emztM.getEmzt_houseid()); // 公积金号
			m.setEmba_marital(emztM.getEmzt_marital()); // 婚姻状况
			m.setEmba_education(emztM.getEmzt_education()); // 学历
			m.setEmba_hjadd(emztM.getEmzt_hjadd()); // 户籍地址
			m.setEmba_fileplace(emztM.getEmzt_fileplace()
					+ emztM.getEmzt_ofileplace()); // 原档案存放地
			m.setEmba_email(emztM.getEmzt_email()); // email
			m.setEmba_zytid(emztM.getEmzt_zgid()); // 智翼通员工编号
			m.setEmba_zytwtgid(emztM.getEmzt_wtgid()); // 智翼通委托员工编号
		}
	}

	// 处理特殊字段
	public void handle() {
		m.setEmba_birth(birth != null ? sdf.format(birth) : null);
		m.setEmba_graduation(graduation != null ? sdf.format(graduation) : null);
		m.setEmba_degree(dgModel.getPcco_cn());
		m.setEmba_fileinclass(fileinclass.equals("是") ? 1 : 0);
		m.setEmba_filedebts(filedebts.equals("是") ? 1 : 0);
		m.setEmba_filehj(filehj.equals("是") ? 1 : 0);
		m.setEmba_sbcard(sbcard.equals("是") ? "1" : "0");
		m.setEmba_excompanystate(excompanystate.equals("是") ? 1 : 0);
		m.setEmba_housetime(housetime != null ? sdf.format(housetime) : null);
		m.setEmba_worktime(worktime != null ? sdf.format(worktime) : null);
		m.setEmba_sztime(sztime != null ? sdf.format(sztime) : null);
		m.setEmba_hjtime(hjtime != null ? sdf.format(hjtime) : null);
		m.setEmba_compactstart(compactstart != null ? sdf.format(compactstart)
				: null);
		m.setEmba_compactend(compactend != null ? sdf.format(compactend) : null);
		m.setEmba_companystart(companystart != null ? sdf.format(companystart)
				: null);
		m.setEmba_bctime(bctime != null ? sdf.format(bctime) : null);
		m.setEmba_hospital(bcsetupModel.getEbcs_hospital());
	}

	// 特殊字段初始化
	public void fieldinit() {
		try {
			birth = m.getEmba_birth() != null ? sdf.parse(m.getEmba_birth())
					: null;
			graduation = m.getEmba_graduation() != null ? sdf.parse(m
					.getEmba_graduation()) : null;
			for (int i = 0; i < degreeList.size(); i++) {
				if (degreeList.get(i).getPcco_cn() != null) {
					if (degreeList.get(i).getPcco_cn()
							.equals(m.getEmba_degree())) {
						dgModel = degreeList.get(i);
					}
				}
			}
			fileinclass = m.getEmba_fileinclass() == 0 ? "否" : "是";
			filedebts = m.getEmba_filedebts() == 0 ? "否" : "是";
			filehj = m.getEmba_filehj() == 0 ? "否" : "是";
			sbcard = m.getEmba_sbcard() == "0" ? "否" : "是";
			excompanystate = m.getEmba_excompanystate() == 0 ? "否" : "是";
			housetime = m.getEmba_housetime() != null ? sdf.parse(m
					.getEmba_housetime()) : null;
			worktime = m.getEmba_worktime() != null ? sdf.parse(m
					.getEmba_worktime()) : null;
			sztime = m.getEmba_sztime() != null ? sdf.parse(m.getEmba_sztime())
					: null;
			hjtime = m.getEmba_hjtime() != null ? sdf.parse(m.getEmba_hjtime())
					: null;
			compactstart = m.getEmba_compactstart() != null ? sdf.parse(m
					.getEmba_compactstart()) : null;
			compactend = m.getEmba_compactend() != null ? sdf.parse(m
					.getEmba_compactend()) : null;
			companystart = m.getEmba_companystart() != null ? sdf.parse(m
					.getEmba_companystart()) : null;
			bctime = m.getEmba_bctime() != null ? sdf.parse(m.getEmba_bctime())
					: null;
			for (int i = 0; i < bcsetupList.size(); i++) {
				if (bcsetupList.get(i).getEbcs_hospital() != null) {
					if (bcsetupList.get(i).getEbcs_hospital()
							.equals(m.getEmba_hospital())) {
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

	// 选择体检医院,级联绑定体检医院地址列表
	@Command("getbcadd")
	@NotifyChange({ "bcsetupaddList1", "m" })
	public void getbcadd() {
		bcsetupaddList1.clear();
		try {
			for (EmBcSetupAddressModel fm : bcsetupaddList) {
				if (bcsetupModel.getEbcs_id() == fm.getEbsa_ebcs_id()) {
					bcsetupaddList1.add(fm);
				}
			}
			if (bcsetupaddList1.size() > 0) {
				m.setEmba_bcaddress(bcsetupaddList1.get(0).getEbsa_address());
			} else {
				m.setEmba_bcaddress(null);
			}
		} catch (Exception e) {
			m.setEmba_bcaddress(null);
		}
	}

	// 必填字段检查
	public boolean check() throws Exception {
		boolean su = true;
		EmbaseFieldUtil efu = new EmbaseFieldUtil();
		try {
			for (int i = 0; i < requiredList.size(); i++) {
				String fname = requiredList.get(i);
				if (fname.equals("emba_degree")) {
					if (dgModel.getPcco_cn() == null
							|| dgModel.getPcco_cn().isEmpty()) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				} else if (fname.equals("emba_ep")) {
					if ((m.getEmba_epname() == null || m.getEmba_epname()
							.isEmpty())
							&& (m.getEmba_epmobile() == null || m
									.getEmba_epmobile().isEmpty())) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				} else if (fname.equals("emba_hospital")) {
					if (bcsetupModel.getEbcs_hospital() == null
							|| bcsetupModel.getEbcs_hospital().isEmpty()) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				} else if (fname.equals("emba_gzbank")) {
					if ((m.getEmba_gz_account() == null || m
							.getEmba_gz_account().isEmpty())
							|| (m.getEmba_gz_bank() == null || m
									.getEmba_gz_bank().isEmpty())
							|| (m.getEmba_writeoff_bank() == null || m
									.getEmba_writeoff_bank().isEmpty())
							|| (m.getEmba_writeoff_account() == null || m
									.getEmba_writeoff_account().isEmpty())) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				} else if (fname.equals("emba_sbbank")) {
					if ((m.getEmba_sy_account() == null || m
							.getEmba_sy_account().isEmpty())
							|| (m.getEmba_sy_bank() == null || m
									.getEmba_sy_bank().isEmpty())) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				} else if (fname.equals("emba_sbinfor")) {
					Object a = "";
					Object b = "";
					Object c = "";
					Object d = "";
					boolean su1 = false;
					for (int j = 1; j < 5; j++) {
						a = m.getClass()
								.getMethod(getMethod("emba_sbname" + j))
								.invoke(m);
						b = m.getClass().getMethod(getMethod("emba_sbage" + j))
								.invoke(m);
						c = m.getClass()
								.getMethod(getMethod("emba_sbidcard" + j))
								.invoke(m);
						d = m.getClass()
								.getMethod(getMethod("emba_sbrelation" + j))
								.invoke(m);
						if ((a == null || a.toString().isEmpty())
								|| (b == null || b.toString().isEmpty())
								&& (c == null || c.toString().isEmpty())
								&& (d == null || d.toString().isEmpty())) {
						} else {
							su1 = true;
							break;
						}
					}
					if (!su1) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				} else if (fname.equals("emba_workinfor")) {
					Object a = "";
					Object b = "";
					Object c = "";
					boolean su1 = true;
					for (int j = 1; j < 7; j++) {
						a = m.getClass()
								.getMethod(getMethod("emba_worktime" + j))
								.invoke(m);
						b = m.getClass()
								.getMethod(getMethod("emba_workcompany" + j))
								.invoke(m);
						c = m.getClass()
								.getMethod(getMethod("emba_workjob" + j))
								.invoke(m);
						if ((a == null || a.toString().isEmpty())
								&& (b == null || b.toString().isEmpty())
								&& (c == null || c.toString().isEmpty())) {
						} else {
							su1 = true;
							break;
						}
					}
					if (!su1) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				} else if (fname.equals("emba_family")) {
					Object a = "";
					Object b = "";
					Object c = "";
					Object d = "";
					Object e = "";
					boolean su1 = true;
					for (int j = 1; j < 7; j++) {
						a = m.getClass().getMethod(getMethod("emba_f" + j))
								.invoke(m);
						b = m.getClass().getMethod(getMethod("emba_fn" + j))
								.invoke(m);
						c = m.getClass().getMethod(getMethod("emba_fag" + j))
								.invoke(m);
						d = m.getClass().getMethod(getMethod("emba_fw" + j))
								.invoke(m);
						e = m.getClass().getMethod(getMethod("emba_fr" + j))
								.invoke(m);
						if ((a == null || a.toString().isEmpty())
								&& (b == null || b.toString().isEmpty())
								&& (c == null || c.toString().isEmpty())
								&& (d == null || d.toString().isEmpty())
								&& (e == null || e.toString().isEmpty())) {
						} else {
							su1 = true;
							break;
						}
					}
					if (!su1) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				} else {
					Method method = m.getClass().getMethod(getMethod(fname));
					String ftype = method.getReturnType().getSimpleName();
					if (ftype.equals("String")
							&& (method.invoke(m) == null || method.invoke(m)
									.toString().isEmpty())) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					} else if (ftype.equals("Integer")
							&& method.invoke(m) == null) {
						su = false;
						alarmStr = efu.getfield(fname) + "未填!";
						break;
					}
				}
			}
		} catch (Exception e) {
			su = false;
			Messagebox.show("必填项检查出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
		return su;
	}

	// 提交
	@Command("submit")
	public void submit(@BindingParam("win") Window win) throws Exception {

		EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();

		try {
			m.setEmba_addname(UserInfo.getUsername());

			handle();
		} catch (Exception e) {
			// TODO: handle exception
			Messagebox.show("数据处理失败,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
		if (!check()) {
			Messagebox.show(alarmStr, "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {
			try {

				// 新增
				if (emba_id == 0 && cid != 0) {
					m.setCid(cid);
					m.setEmba_state(1);
					String[] str = bll.EmbaseAdd(m);

					if (str[0].equals("1")) {

						// 智翼通接口数据处理
						if (emztM != null) {
							emztM.setEmzt_state(8);
							obll.upEmZYTState(emztM);
						}

						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
				// 补充
				else if (emba_id != 0 && cid == 0) {
					m.setEmba_id(emba_id);

					String[] str = bll.EmbaseloginUpdate(m,
							m.getEmba_tapr_id(), emba_id);

					if (str[0].equals("1")) {
						Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					} else if (str[1].equals("0")) {
						Messagebox.show(str[1], "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					Messagebox.show("数据出错,请联系IT部门!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}

			} catch (Exception e) {
				// TODO: handle exception
				Messagebox.show("提交失败,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				System.out.println(e.toString());
			}
		}
	}

	public static String getMethod(String name) {
		return "get"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	public String getWin_title() {
		return win_title;
	}

	public void setWin_title(String win_title) {
		this.win_title = win_title;
	}

	public EmbaseModel getM() {
		return m;
	}

	public void setM(EmbaseModel m) {
		this.m = m;
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

	public List<String> getFolkList() {
		return folkList;
	}

	public void setFolkList(List<String> folkList) {
		this.folkList = folkList;
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
}
