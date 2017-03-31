package Controller.Archives;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Button;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import bll.Archives.Archive_newBll;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmArchiveSetupModel;
import Model.PubCodeConversionModel;
import Util.FormatObjectValueUtil;
import Util.UserInfo;

public class Archive_newController {
	private EmArchiveModel eam = new EmArchiveModel();
	private EmArchiveDatumModel edm = new EmArchiveDatumModel();
	private List<PubCodeConversionModel> partyList = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> hjBelongs = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> partyBelongs = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> dpList = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> szincome = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> hkgk = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> marrystates = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> syzz = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> zc = new ListModelList<PubCodeConversionModel>();
	private List<PubCodeConversionModel> dadryy = new ListModelList<PubCodeConversionModel>();
	private List<EmArchiveSetupModel> eas;
	private Archive_newBll bll = new Archive_newBll();
	private FormatObjectValueUtil fov = new FormatObjectValueUtil();
	private boolean zzmanage = true;
	private boolean rcwt = true;
	private Date stdate;
	private Date edate;
	private String workdw;
	private String workzw;

	private Integer hb;
	private Integer pb;
	private Integer sp;
	private Integer hj;
	private Date gradate, inciicdate, workdate, casteassessdate,
			archivefolddate, promisesdate, prodate, firstdeadline,
			continuedeadline, partydate;

	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id =null;
	private String username = UserInfo.getUsername();
	SimpleDateFormat sdf = null;
	private static int count = 1;

	public Archive_newController() {
		if(Executions.getCurrent().getArg()
				.get("id")!=null)
		{
			tapr_id=Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd");

		edm = bll.getInfo(eadaId).get(0);
		setPartyList(9, "政治面貌");
		setHjBelongs(9, "户籍性质");
		setPartyBelongs(9, "政治面貌性质");
		setDpList(16, "文化程度");
		setSzincome(9, "入深方式");
		setHkgk(9, "户口挂靠");
		setMarrystates(16, "婚姻状况");
		setSyzz(16, "生育状况");
		setZc(9, "职称");
		setDadryy(9, "档案转入原由");

		eas = bll.getdaInfo();
		try {
			eam.setEmar_archivetype(edm.getEada_filetype());
			eam.setEmar_archivearea(edm.getEada_filearea());
			eam.setCid(edm.getCid());
			eam.setGid(edm.getGid());
			eam.setEmar_company(edm.getCoba_company());
			eam.setEmar_name(edm.getEada_name());
			eam.setEmar_sex(edm.getEmba_sex());
			eam.setEmar_idcard(edm.getEada_idcard());
			if (edm.getEmba_hjadd() != null) {
				eam.setEmar_censusregister(edm.getEmba_hjadd());
			}
			if (edm.getEmba_degree() != null) {
				eam.setEmar_degree(edm.getEmba_degree());
			}
			if (edm.getEmba_school() != null) {
				eam.setEmar_school(edm.getEmba_school());
			}

			if (edm.getEmba_specialty() != null) {
				eam.setEmar_specialty(edm.getEmba_specialty());
			}
			if (edm.getEmba_graduation() != null) {
				eam.setEmar_gradate(sdf.format(edm.getEmba_graduation()));
			}
			if (edm.getEmba_marital() != null) {
				eam.setEmar_marrystate(edm.getEmba_marital());
			}
			if (edm.getEmba_mobile() != null) {
				eam.setEmar_link(edm.getEmba_mobile());
			}
			if (edm.getEmba_indate() != null) {
				eam.setEmar_inciicdate(sdf.format(edm.getEmba_indate()));
			}

			if (edm.getEada_fileplace() != null) {
				eam.setEmar_archiveplace(edm.getEada_fileplace());
			}

			if (edm.getEada_wtmode() != null) {
				eam.setEmar_wtmode(edm.getEada_wtmode());
			}
			if (edm.getEada_colhj() != null) {
				eam.setEmar_colhj(edm.getEada_colhj());
				eam.setEmar_colhjname(edm.getEada_colhj() == 1 ? "是" : "否");
			}
			partydate = StrToDate(eam.getEmar_partydate());
			gradate = StrToDate(eam.getEmar_gradate());
			inciicdate = StrToDate(eam.getEmar_inciicdate());
			workdate = StrToDate(eam.getEmar_workdate());
			casteassessdate = StrToDate(eam.getEmar_casteassessdate());
			archivefolddate = StrToDate(eam.getEmar_archivefolddate());
			promisesdate = StrToDate(eam.getEmar_promisesdate());
			prodate = edm.getEmar_prodate();
			firstdeadline = StrToDate(eam.getEmar_firstdeadline());
			continuedeadline = StrToDate(eam.getEmar_continuedeadline());
			eam.setEmar_grouptype(bll.getDept(edm.getCoba_client()));

			eam.setEmar_client(edm.getCoba_client());

			if (eam.getEmar_archivetype().equals("中智保管")) {
				zzmanage = false;
				rcwt = true;
			} else if (eam.getEmar_archivetype().equals("委托人才")) {
				zzmanage = true;
				rcwt = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		initM();
	}
	
	private void initM()
	{
		EmArchiveModel earm=bll.getEmarchive(eam.getEmar_idcard(), eam.getEmar_archivetype());
		if(earm!=null&&earm.getEmar_id()!=null)
		{
			if(earm.getEmar_censusregister()!=null&&!earm.getEmar_censusregister().equals(""))
			{
				if(eam.getEmar_censusregister()==null||eam.getEmar_censusregister().equals(""))
				{
					eam.setEmar_censusregister(earm.getEmar_censusregister());
				}
			}

			if(earm.getEmar_party()!=null&&!earm.getEmar_party().equals(""))
			{
				if(eam.getEmar_party()==null||eam.getEmar_party().equals(""))
				{
					eam.setEmar_party(earm.getEmar_party());
				}
			}

			if(earm.getEmar_partydate()!=null&&!earm.getEmar_partydate().equals(""))
			{
				if(eam.getEmar_partydate()==null||eam.getEmar_partydate().equals(""))
				{
					eam.setEmar_partydate(earm.getEmar_partydate());
				}
			}

			if(earm.getEmar_degree()!=null&&!earm.getEmar_degree().equals(""))
			{
				if(eam.getEmar_degree()==null||eam.getEmar_degree().equals(""))
				{
					eam.setEmar_degree(earm.getEmar_degree());
				}
			}

			if(earm.getEmar_school()!=null&&!earm.getEmar_school().equals(""))
			{
				if(eam.getEmar_school()==null||eam.getEmar_school().equals(""))
				{
					eam.setEmar_school(earm.getEmar_school());
				}
			}
			
			if(earm.getEmar_specialty()!=null&&!earm.getEmar_specialty().equals(""))
			{
				if(eam.getEmar_specialty()==null||eam.getEmar_specialty().equals(""))
				{
					eam.setEmar_specialty(earm.getEmar_specialty());
				}
			}

			if(earm.getEmar_gradate()!=null&&!earm.getEmar_gradate().equals(""))
			{
				if(eam.getEmar_gradate()==null||eam.getEmar_gradate().equals(""))
				{
					eam.setEmar_gradate(earm.getEmar_gradate());
				}
			}

			if(earm.getEmar_marrystate()!=null&&!earm.getEmar_marrystate().equals(""))
			{
				if(eam.getEmar_marrystate()==null||eam.getEmar_marrystate().equals(""))
				{
					eam.setEmar_marrystate(earm.getEmar_marrystate());
				}
			}

			if(earm.getEmar_fertilitystate()!=null&&!earm.getEmar_fertilitystate().equals(""))
			{
				if(eam.getEmar_fertilitystate()==null||eam.getEmar_fertilitystate().equals(""))
				{
					eam.setEmar_fertilitystate(earm.getEmar_fertilitystate());
				}
			}

			if(earm.getEmar_workdate()!=null&&!earm.getEmar_workdate().equals(""))
			{
				if(eam.getEmar_workdate()==null||eam.getEmar_workdate().equals(""))
				{
					eam.setEmar_workdate(earm.getEmar_workdate());
				}
			}

			if(earm.getEmar_caste()!=null&&!earm.getEmar_caste().equals(""))
			{
				if(eam.getEmar_caste()==null||eam.getEmar_caste().equals(""))
				{
					eam.setEmar_caste(earm.getEmar_caste());
				}
			}

			if(earm.getEmar_peoplefoldmode()!=null&&!earm.getEmar_peoplefoldmode().equals(""))
			{
				if(eam.getEmar_peoplefoldmode()==null||eam.getEmar_peoplefoldmode().equals(""))
				{
					eam.setEmar_peoplefoldmode(earm.getEmar_peoplefoldmode());
				}
			}

			if(earm.getEmar_archiveclass()!=null&&!earm.getEmar_archiveclass().equals(""))
			{
				if(eam.getEmar_archiveclass()==null||eam.getEmar_archiveclass().equals(""))
				{
					eam.setEmar_archiveclass(earm.getEmar_archiveclass());
				}
			}

			if(earm.getEmar_archivefoldreason()!=null&&!earm.getEmar_archivefoldreason().equals(""))
			{
				if(eam.getEmar_archivefoldreason()==null||eam.getEmar_archivefoldreason().equals(""))
				{
					eam.setEmar_archivefoldreason(earm.getEmar_archivefoldreason());
				}
			}

			if(earm.getEmar_surrogateid()!=null&&!earm.getEmar_surrogateid().equals(""))
			{
				if(eam.getEmar_surrogateid()==null||eam.getEmar_surrogateid().equals(""))
				{
					eam.setEmar_surrogateid(earm.getEmar_surrogateid());
				}
			}

			if(earm.getEmar_archivesource()!=null&&!earm.getEmar_archivesource().equals(""))
			{
				if(eam.getEmar_archivesource()==null||eam.getEmar_archivesource().equals(""))
				{
					eam.setEmar_archivesource(earm.getEmar_archivesource());
				}
			}

			if(earm.getEmar_archivefoldmode()!=null&&!earm.getEmar_archivefoldmode().equals(""))
			{
				if(eam.getEmar_archivefoldmode()==null||eam.getEmar_archivefoldmode().equals(""))
				{
					eam.setEmar_archivefoldmode(earm.getEmar_archivefoldmode());
				}
			}
			
			if(earm.getEmar_archivefolddate()!=null&&!earm.getEmar_archivefolddate().equals(""))
			{
				if(eam.getEmar_archivefolddate()==null||eam.getEmar_archivefolddate().equals(""))
				{
					eam.setEmar_archivefolddate(earm.getEmar_archivefolddate());
					archivefolddate=StrToDate(earm.getEmar_archivefolddate());
				}
			}
			
			if(earm.getEmar_prodate()!=null&&!earm.getEmar_prodate().equals(""))
			{
				if(eam.getEmar_prodate()==null||eam.getEmar_prodate().equals(""))
				{
					eam.setEmar_prodate(earm.getEmar_prodate());
					archivefolddate=StrToDate(earm.getEmar_prodate());
				}
			}
			
			if(earm.getEmar_transferorderid()!=null&&!earm.getEmar_transferorderid().equals(""))
			{
				if(eam.getEmar_transferorderid()==null||eam.getEmar_transferorderid().equals(""))
				{
					eam.setEmar_transferorderid(earm.getEmar_transferorderid());
				}
			}
			
			if(earm.getEmar_archiveplace()!=null&&!earm.getEmar_archiveplace().equals(""))
			{
				if(eam.getEmar_archiveplace()==null||eam.getEmar_archiveplace().equals(""))
				{
					eam.setEmar_archiveplace(earm.getEmar_archiveplace());
				}
			}
			
			if(earm.getEmar_continuedeadline()!=null&&!earm.getEmar_continuedeadline().equals(""))
			{
				if(eam.getEmar_continuedeadline()==null||eam.getEmar_continuedeadline().equals(""))
				{
					eam.setEmar_continuedeadline(earm.getEmar_continuedeadline());
					continuedeadline=StrToDate(earm.getEmar_continuedeadline());
				}
			}
			
			if(earm.getEmar_firstdeadline()!=null&&!earm.getEmar_firstdeadline().equals(""))
			{
				if(eam.getEmar_firstdeadline()==null||eam.getEmar_firstdeadline().equals(""))
				{
					eam.setEmar_firstdeadline(earm.getEmar_firstdeadline());
					firstdeadline=StrToDate(earm.getEmar_firstdeadline());
				}
			}
		}
	}

	public String changeDate(Datebox db) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = db.getValue() == null ? null : sdf.format(db.getValue());
		return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (str != null && !str.equals("")) {
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("a") Combobox chb, @BindingParam("b") Combobox cpb,
			@BindingParam("c") Combobox csp, @BindingParam("d") Combobox colhj,
			@BindingParam("rows") Rows rows) {

		Datebox dbparty = (Datebox) win.getFellow("partydate");
		Datebox dbschool = (Datebox) win.getFellow("schooldate");
		Datebox dbwork = (Datebox) win.getFellow("workdate");
		Datebox dbcaste = (Datebox) win.getFellow("castedate");
		Datebox dbinciic = (Datebox) win.getFellow("inciicdate");
		Datebox dbfold = (Datebox) win.getFellow("folddate");
		Datebox dbpromise = (Datebox) win.getFellow("promisesdate");
		Datebox dbpro = (Datebox) win.getFellow("prodate");
		Datebox dbfirst = (Datebox) win.getFellow("firstdeadline");
		Datebox dbcontinue = (Datebox) win.getFellow("continuedeadline");

		if (chb.getSelectedItem() != null && !chb.getValue().equals("")) {
			eam.setEmar_crbelongs(Integer.valueOf(chb.getSelectedItem()
					.getValue().toString()));
		} else {
			eam.setEmar_crbelongs(0);
		}

		if (cpb.getSelectedItem() != null && !cpb.getValue().equals("")) {
			eam.setEmar_partybelongs(Integer.valueOf(cpb.getSelectedItem()
					.getValue().toString()));
		} else {
			eam.setEmar_partybelongs(0);
		}

		// if (csp.getSelectedItem() != null && !"".equals(csp.getValue())) {
		// eam.setEmar_specialdata(Integer.valueOf(csp.getSelectedItem()
		// .getValue().toString()));
		// } else {
		// eam.setEmar_specialdata(0);
		// }

		// if (colhj.getSelectedItem() != null && !"".equals(colhj.getValue()))
		// {
		// eam.setEmar_colhj(Integer.parseInt(colhj.getSelectedItem()
		// .getValue().toString()));
		// } else {
		// eam.setEmar_colhj(0);
		// }

		if (eam.getEmar_archivetype() == "委托人才") {
			if (eam.getEmar_archivearea() == null) {
				Messagebox.show("请选择档案类型", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		try {
			eam.setEmar_szresume(setszresume(rows));

			eam.setEmar_partydate(changeDate(dbparty));
			eam.setEmar_gradate(changeDate(dbschool));
			eam.setEmar_workdate(changeDate(dbwork));
			eam.setEmar_casteassessdate(changeDate(dbcaste));
			eam.setEmar_inciicdate(changeDate(dbinciic));
			eam.setEmar_archivefolddate(changeDate(dbfold));
			eam.setEmar_promisesdate(changeDate(dbpromise));
			eam.setEmar_prodate(changeDate(dbpro));
			eam.setEmar_firstdeadline(changeDate(dbfirst));
			eam.setEmar_continuedeadline(changeDate(dbcontinue));

			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {
								Integer i = bll.addfile(eam, eadaId, tapr_id,
										username);
								if (i > 0) {
									Messagebox.show("添加成功", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
								} else {
									Messagebox.show("添加失败", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
								}
								win.detach();
							}
						}
					});
		} catch (Exception e) {
			Messagebox.show("在深工作简历时间不能为空", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 拼装string 到szresume
	public String setszresume(Rows rows) throws Exception {
		String sdate = null;
		String enddate = null;
		if (stdate != null) {
			sdate = sdf.format(stdate);
		}
		if (edate != null) {
			enddate = sdf.format(edate);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(sdate + "至" + enddate + "在" + workdw + "单位担任" + workzw);
		if (count > 1) {
			for (int i = 0; i < rows.getChildren().size(); i++) {
				Row row = (Row) rows.getChildren().get(i);
				if (row.getId() != "") {
					for (int j = 0; j < count; j++) {
						if (row.getId().equals(j + "")) {
							Datebox dbs = (Datebox) row.getChildren().get(1)
									.getChildren().get(0);
							sb.append(",.," + sdf.format(dbs.getValue()));
							sb.append("至");
							Datebox dbe = (Datebox) row.getChildren().get(1)
									.getChildren().get(2);
							sb.append(sdf.format(dbe.getValue()));
							sb.append("在");
							Textbox t1 = (Textbox) row.getChildren().get(1)
									.getChildren().get(4);
							sb.append(t1.getValue());
							sb.append("单位担任");
							Textbox t2 = (Textbox) row.getChildren().get(1)
									.getChildren().get(6);
							sb.append(t2.getValue());
						}
					}
				}
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	// @Command
	// public void ss(@BindingParam("rows") Rows rows) {
	// try {
	// System.out.println(setszresume(rows));
	// System.out.println(2);
	// } catch (Exception e) {
	// Messagebox.show("时间不能为空", "操作提示", Messagebox.OK, Messagebox.ERROR);
	// }
	// System.out.println(1);
	// }

	// 动态创建行
	@Command
	public void createrow(@BindingParam("rows") Rows rows) {
		final Row row = new Row();
		row.setId(count + "");
		Cell cell1 = new Cell();
		Cell cell2 = new Cell();
		cell2.setColspan(5);
		Label label = new Label();
		label.setValue("在深工作简历" + count++);
		cell1.appendChild(label);
		Datebox dbs = new Datebox();
		dbs.setMold("rounded");
		dbs.setFormat("yyyy-MM-dd");
		Datebox dbe = new Datebox();
		dbe.setMold("rounded");
		dbe.setFormat("yyyy-MM-dd");
		Textbox t1 = new Textbox();
		t1.setMold("rounded");
		Textbox t2 = new Textbox();
		t2.setMold("rounded");
		cell2.appendChild(dbs);
		cell2.appendChild(new Label(" 至 "));
		cell2.appendChild(dbe);
		cell2.appendChild(new Label(" 工作单位 "));
		cell2.appendChild(t1);
		cell2.appendChild(new Label(" 部门及职务 "));
		cell2.appendChild(t2);
		Button btn = new Button();
		btn.setLabel("-");
		btn.addEventListener(Events.ON_CLICK,
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						row.detach();
						count--;
					}
				});
		cell2.appendChild(btn);
		row.appendChild(cell1);
		row.appendChild(cell2);
		rows.appendChild(row);
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", eadaId.toString());
		map.put("typeid", "1");
		map.put("gid",eam.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public List<EmArchiveSetupModel> getEas() {
		return eas;
	}

	public void setEas(List<EmArchiveSetupModel> eas) {
		this.eas = eas;
	}

	public List<PubCodeConversionModel> getSzincome() {
		return szincome;
	}

	public void setSzincome(Integer id, String name) {
		this.szincome = bll.getInfoList(id, name);
	}

	public EmArchiveModel getEam() {
		return eam;
	}

	public void setEam(EmArchiveModel eam) {
		this.eam = eam;
	}

	public List<PubCodeConversionModel> getPartyList() {
		return partyList;
	}

	public List<PubCodeConversionModel> getHkgk() {
		return hkgk;
	}

	public void setHkgk(Integer id, String name) {
		this.hkgk = bll.getInfoList(id, name);
	}

	public void setPartyList(Integer id, String name) {
		this.partyList = bll.getInfoList(id, name);
	}

	public List<PubCodeConversionModel> getHjBelongs() {
		return hjBelongs;
	}

	public void setHjBelongs(Integer id, String name) {
		this.hjBelongs = bll.getInfoList(id, name);
	}

	public List<PubCodeConversionModel> getPartyBelongs() {
		return partyBelongs;
	}

	public void setPartyBelongs(Integer id, String name) {
		this.partyBelongs = bll.getInfoList(id, name);
	}

	public List<PubCodeConversionModel> getZc() {
		return zc;
	}

	public void setZc(Integer id, String name) {
		this.zc = bll.getInfoList(id, name);
	}

	public List<PubCodeConversionModel> getSyzz() {
		return syzz;
	}

	public void setSyzz(Integer id, String name) {
		this.syzz = bll.getInfoList(id, name);
	}

	public List<PubCodeConversionModel> getDadryy() {
		return dadryy;
	}

	public void setDadryy(Integer id, String name) {
		this.dadryy = bll.getInfoList(id, name);
	}

	public List<PubCodeConversionModel> getMarrystates() {
		return marrystates;
	}

	public void setMarrystates(Integer id, String name) {
		this.marrystates = bll.getInfoList(id, name);
	}

	public List<PubCodeConversionModel> getDpList() {
		return dpList;
	}

	public void setDpList(Integer id, String name) {
		this.dpList = bll.getInfoList(id, name);
	}

	public Date getGradate() {
		return gradate;
	}

	public void setGradate(Date gradate) {
		this.gradate = gradate;
	}

	public Date getInciicdate() {
		return inciicdate;
	}

	public void setInciicdate(Date inciicdate) {
		this.inciicdate = inciicdate;
	}

	public Date getCasteassessdate() {
		return casteassessdate;
	}

	public void setCasteassessdate(Date casteassessdate) {
		this.casteassessdate = casteassessdate;
	}

	public Date getPromisesdate() {
		return promisesdate;
	}

	public void setPromisesdate(Date promisesdate) {
		this.promisesdate = promisesdate;
	}

	public Date getProdate() {
		return prodate;
	}

	public void setProdate(Date prodate) {
		this.prodate = prodate;
	}

	public Date getFirstdeadline() {
		return firstdeadline;
	}

	public void setFirstdeadline(Date firstdeadline) {
		this.firstdeadline = firstdeadline;
	}

	public Date getContinuedeadline() {
		return continuedeadline;
	}

	public void setContinuedeadline(Date continuedeadline) {
		this.continuedeadline = continuedeadline;
	}

	public Date getWorkdate() {
		return workdate;
	}

	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}

	public Date getArchivefolddate() {
		return archivefolddate;
	}

	public void setArchivefolddate(Date archivefolddate) {
		this.archivefolddate = archivefolddate;
	}

	public Date getPartydate() {
		return partydate;
	}

	public void setPartydate(Date partydate) {
		this.partydate = partydate;
	}

	public boolean isZzmanage() {
		return zzmanage;
	}

	public void setZzmanage(boolean zzmanage) {
		this.zzmanage = zzmanage;
	}

	public boolean isRcwt() {
		return rcwt;
	}

	public void setRcwt(boolean rcwt) {
		this.rcwt = rcwt;
	}

	public Date getStdate() {
		return stdate;
	}

	public void setStdate(Date stdate) {
		this.stdate = stdate;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	public String getWorkdw() {
		return workdw;
	}

	public void setWorkdw(String workdw) {
		this.workdw = workdw;
	}

	public String getWorkzw() {
		return workzw;
	}

	public void setWorkzw(String workzw) {
		this.workzw = workzw;
	}

}
