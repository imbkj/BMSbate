package Controller.Archives;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.Archives.EmArchive_OperateBll;

import Model.CoLatencyClientModel;
import Model.EmArchiveModel;
import Util.FormatObjectValueUtil;

public class Archives_UpdateController extends SelectorComposer<Component> {
	@Wire
	private Textbox cid;// 公司ID
	@Wire
	private Textbox company;// 公司名称
	@Wire
	private Textbox gid;// 雇员id
	@Wire
	private Textbox name;// 员工Id
	@Wire
	private Textbox sex;
	@Wire
	private Textbox idcard;
	@Wire
	private Textbox hj;// 户籍
	@Wire
	private Combobox hjtype;// 户籍性质
	@Wire
	private Combobox zz;// 政治面貌
	@Wire
	private Combobox party;// 党籍
	@Wire
	private Datebox partytime;// 入党时间
	@Wire
	private Combobox degree;// 学历
	@Wire
	private Textbox school;// 毕业院校
	@Wire
	private Textbox specialty;// 所学专业
	@Wire
	private Datebox gradate;// 毕业时间
	@Wire
	private Textbox marrystate;// 婚姻状况
	@Wire
	private Textbox fertilitystate;// 生育状况
	@Wire
	private Datebox workdate;// 参加工作时间
	@Wire
	private Textbox caste;// 职称
	@Wire
	private Datebox castedate;// 职称评定时间
	@Wire
	private Combobox pfoldmode;// 入深方式
	@Wire
	private Textbox link;// 联系方式
	@Wire
	private Textbox grouptype;// 分组类别
	@Wire
	private Textbox client;// 客服
	@Wire
	private Combobox specialdata;// 特殊数据
	@Wire
	private Textbox address;// 户口所在地详细地址
	@Wire
	private Combobox archiveclass;// 档案类别
	@Wire
	private Combobox wtmodetype;// 原托管方式
	@Wire
	private Textbox archiveplace;// 档案存放机构
	@Wire
	private Textbox archivesource;// 档案何处转入
	@Wire
	private Combobox foldmode;// 档案转入方式
	@Wire
	private Datebox folddate;// 档案转入时间
	@Wire
	private Textbox foldreason;// 档案转入缘由
	@Wire
	private Textbox transferorderid;// 调令号
	@Wire
	private Textbox cardid;// 代理卡号
	@Wire
	private Textbox surrogate;// 人才机构代理号
	@Wire
	private Datebox promisesdate;// 承诺书签订日期
	@Wire
	private Datebox prodate;// 协议书书签订日期
	@Wire
	private Datebox firstdeadline;// 初次协议到期日
	@Wire
	private Datebox continuedeadline;// 续签协议到期日
	@Wire
	private Combobox colhj;// 户籍挂靠
	@Wire
	private Textbox removermode;// 调出方式
	@Wire
	private Textbox remorereason;// 调出原因
	@Wire
	private Datebox removedate;// 调出日期
	@Wire
	private Textbox addname;// 添加人
	@Wire
	private Textbox addtime;// 添加时间
	@Wire
	private Textbox removeplace;// 转往单位
	@Wire
	private Textbox szresume;// 在深工作经历
	@Wire
	private Window winupdate;
	@Wire
	private Rows rows;
	@Wire
	private Button jjj;
	@Wire
	private Button addrow;

	private EmArchiveModel m = (EmArchiveModel) Executions.getCurrent()
			.getArg().get("model");

	private EmArchiveModel frommodel = (EmArchiveModel) Executions.getCurrent()
			.getArg().get("model");
	private FormatObjectValueUtil fov = new FormatObjectValueUtil();
	private int count;
	private static int count1;

	// 重写组件初始化方法
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		if (frommodel.getEmar_partydate() != null) {
			partytime.setValue(StrToDate(frommodel.getEmar_partydate()));
		}
		if (frommodel.getEmar_gradate() != null) {
			gradate.setValue(StrToDate(frommodel.getEmar_gradate()));
		}
		if (frommodel.getEmar_workdate() != null) {
			workdate.setValue(StrToDate(frommodel.getEmar_workdate()));
		}
		if (frommodel.getEmar_casteassessdate() != null) {
			castedate.setValue(StrToDate(frommodel.getEmar_casteassessdate()));
		}
		if (frommodel.getEmar_archivefolddate() != null) {
			folddate.setValue(StrToDate(frommodel.getEmar_archivefolddate()));
		}
		if (frommodel.getEmar_promisesdate() != null) {
			promisesdate.setValue(StrToDate(frommodel.getEmar_promisesdate()));
		}
		if (frommodel.getEmar_prodate() != null) {
			prodate.setValue(StrToDate(frommodel.getEmar_prodate()));
		}
		if (frommodel.getEmar_firstdeadline() != null) {
			firstdeadline
					.setValue(StrToDate(frommodel.getEmar_firstdeadline()));
		}
		if (frommodel.getEmar_continuedeadline() != null) {
			continuedeadline.setValue(StrToDate(frommodel
					.getEmar_continuedeadline()));
		}
		if (frommodel.getEmar_archiveremovedate() != null) {
			removedate
					.setValue(StrToDate(frommodel.getEmar_archiveremovedate()));
		}
	}

	// 拼装string 到szresume
	public String setszresume(Rows rows) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rows.getChildren().size(); i++) {
			Row row = (Row) rows.getChildren().get(i);
			if (row.getId() != "") {
				for (int j = 0; j < count1; j++) {
					if (row.getId().equals(j + "k")) {
						Hbox h = (Hbox) row.getChildren().get(1).getChildren()
								.get(0);
						Datebox dbs = (Datebox) h.getChildren().get(0);
						sb.append(sdf.format(dbs.getValue()));
						sb.append("至");
						Datebox dbe = (Datebox) h.getChildren().get(2);
						if (dbe.getValue()!=null && !dbe.getValue().equals("")) {
							sb.append(sdf.format(dbe.getValue()));
						}else {
							sb.append("今");
						}
						
						sb.append("在");
						Textbox t1 = (Textbox) h.getChildren().get(4);
						sb.append(t1.getValue());
						sb.append("单位担任");
						Textbox t2 = (Textbox) h.getChildren().get(6);
						sb.append(t2.getValue());
						if (count1 != ++j) {
							sb.append(",.,");
						}
					}

				}
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	// 动态创建行
	@Listen("onClick =#addrow")
	public void createrow() {
		final Row row = new Row();
		row.setId(count1++ + "k");
		Cell cell1 = new Cell();
		Cell cell2 = new Cell();
		Hbox h = new Hbox();
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
		h.appendChild(dbs);
		h.appendChild(new Label(" 至 "));
		h.appendChild(dbe);
		h.appendChild(new Label(" 工作单位 "));
		h.appendChild(t1);
		h.appendChild(new Label(" 部门及职务 "));
		h.appendChild(t2);

		Button btn = new Button();
		btn.setLabel("-");
		btn.addEventListener(Events.ON_CLICK,
				new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						row.detach();
						count1--;
					}
				});
		h.appendChild(btn);
		cell2.appendChild(h);
		row.appendChild(cell1);
		row.appendChild(cell2);
		rows.appendChild(row);
	}

	@Listen("onCreate =#winupdate")
	public void oncreate() {
		if (m.getEmar_szresume() == null) {
			m.setEmar_szresume(""); // 如果为null ，把委托人才的工作经历split
		}
		m.setEmar_szresume(m.getEmar_szresume().replaceAll("null", ""));
		System.out.println(m.getEmar_szresume());
		String str2[] = m.getEmar_szresume().split(",.,");
		System.out.println(str2.length);
		Date sd = null;
		Date ed = null;
		String[] ds = null;
		String[] dd = null;
		String[] nr = null;
		for (int i = 0; i < str2.length; i++) {
			try {
				ds = str2[i].split("至");
				if (ds!=null&&ds.length>0&&ds[0] != null && !ds[0].equals("") && ds[0]!="null") {
					System.out.println(ds[0]);
					sd = new SimpleDateFormat("yyyy-MM-dd").parse(ds[0]);
				}
				if (ds!=null&&ds.length > 1) {
					dd = ds[1].split("在");
					if (dd!=null&&dd.length>0&&dd[0] != null && !dd[0].equals("")) {
						ed = new SimpleDateFormat("yyyy-MM-dd").parse(dd[0]);
					}
					if (dd!=null&&dd.length > 1) {
						nr = dd[1].split("单位担任");
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Row row = new Row();
			row.setId(count1++ + "k");
			Hbox h = new Hbox();
			Cell cell1 = new Cell();
			Cell cell2 = new Cell();
			cell2.setColspan(5);
			Label label = new Label();
			label.setValue("在深工作简历");
			cell1.appendChild(label);
			Datebox dbs = new Datebox();
			dbs.setMold("rounded");
			dbs.setFormat("yyyy-MM-dd");
			dbs.setValue(sd);
			Datebox dbe = new Datebox();
			dbe.setMold("rounded");
			dbe.setFormat("yyyy-MM-dd");
			dbe.setValue(ed);
			Textbox t1 = new Textbox();
			t1.setMold("rounded");
			if (nr!=null&&nr.length >0) {
				t1.setValue(nr[0]);
			}
			Textbox t2 = new Textbox();
			t2.setMold("rounded");
			if (nr!=null&&nr.length > 1) {
				t2.setValue(nr[1]);
			}
			h.appendChild(dbs);
			h.appendChild(new Label(" 至 "));
			h.appendChild(dbe);
			h.appendChild(new Label(" 工作单位 "));
			h.appendChild(t1);
			h.appendChild(new Label(" 部门及职务 "));
			h.appendChild(t2);
			cell2.appendChild(h);
			row.appendChild(cell1);
			row.appendChild(cell2);
			rows.appendChild(row);
		}

	}

	// 提交
	@Listen("onClick =#updatearc")
	public void updateArchives() {
		EmArchive_OperateBll bll = new EmArchive_OperateBll();
		EmArchiveModel model = new EmArchiveModel();
		model.setEmar_id(frommodel.getEmar_id());
		model.setEmar_censusregister(hj.getValue());
		if (hjtype.getValue() != null && hjtype.getValue() != ""
				&& !hjtype.getValue().equals("")) {
			if (hjtype.getValue() == "隶属中智" || hjtype.getValue().equals("隶属中智")) {
				model.setEmar_crbelongs(1);
			} else {
				model.setEmar_crbelongs(0);
			}
		}

		model.setEmar_idcard(idcard.getValue());
		model.setEmar_party(zz.getValue());
		if (party.getValue() != null && party.getValue() != ""
				&& !party.getValue().equals("")) {
			if (party.getValue() == "隶属中智" || party.getValue().equals("隶属中智")) {
				model.setEmar_partybelongs(1);
			} else {
				model.setEmar_partybelongs(0);
			}
		}

		if (partytime.getValue() != null) {
			model.setEmar_partydate(changedate(partytime.getValue()));
		}
		if (gradate.getValue() != null) {
			model.setEmar_gradate(changedate(gradate.getValue()));
		}
		if (workdate.getValue() != null) {
			model.setEmar_workdate(changedate(workdate.getValue()));
		}
		if (castedate.getValue() != null) {
			model.setEmar_casteassessdate(changedate(castedate.getValue()));
		}

		if (folddate.getValue() != null) {
			model.setEmar_archivefolddate(changedate(folddate.getValue()));
		}
		if (promisesdate.getValue() != null) {
			model.setEmar_promisesdate(changedate(promisesdate.getValue()));
		}
		if (prodate.getValue() != null) {
			model.setEmar_prodate(changedate(prodate.getValue()));
		}
		if (firstdeadline.getValue() != null) {
			model.setEmar_firstdeadline(changedate(firstdeadline.getValue()));
		}
		if (continuedeadline.getValue() != null) {
			model.setEmar_continuedeadline(changedate(continuedeadline
					.getValue()));
		}
		if (removedate.getValue() != null) {
			model.setEmar_archiveremovedate(changedate(removedate.getValue()));
		}

		model.setEmar_degree(degree.getValue());
		model.setEmar_school(school.getValue());
		model.setEmar_specialty(specialty.getValue());

		model.setEmar_marrystate(marrystate.getValue());
		model.setEmar_fertilitystate(fertilitystate.getValue());

		model.setEmar_caste(caste.getValue());

		model.setEmar_peoplefoldmode(pfoldmode.getValue());
		model.setEmar_link(link.getValue());

		// if (specialdata.getValue() != null && specialdata.getValue() != ""
		// && !specialdata.getValue().equals("")) {
		// if (specialdata.getValue() == "是"
		// || specialdata.getValue().equals("是")) {
		// model.setEmar_specialdata(1);
		// } else {
		// model.setEmar_specialdata(0);
		// }
		// }
		// model.setEmar_address(address.getValue());
		model.setEmar_archiveclass(archiveclass.getValue());
		// model.setEmar_wtmode(wtmodetype.getValue());
		model.setEmar_archiveplace(archiveplace.getValue());
		model.setEmar_archivesource(archivesource.getValue());
		model.setEmar_archivefoldreason(foldreason.getValue());
		model.setEmar_archivefoldmode(foldmode.getValue());

		model.setEmar_transferorderid(transferorderid.getValue());
		// model.setEmar_surrogatecardid(cardid.getValue());
		model.setEmar_surrogateid(surrogate.getValue());
		// if (colhj.getValue() != null && !colhj.getValue().equals("")) {
		// if (colhj.getValue().equals("是")) {
		// // model.setEmar_colhj(1);
		// } else {
		// // model.setEmar_colhj(0);
		// }
		// }
		if (model.getEmar_colhj() == null) {
			model.setEmar_colhj(0);
		}
		model.setEmar_archiveremovermode(removermode.getValue());
		model.setEmar_archiveremovereason(remorereason.getValue());

		model.setEmar_addname(addname.getValue());
		model.setEmar_archiveremoveplace(removeplace.getValue());
		boolean isErr = true; // 判断是否出现异常
		try {
			model.setEmar_szresume(setszresume(rows));
		} catch (Exception e) {
			//isErr = false;
			//Messagebox.show("工作时间不能为空", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		if (isErr) {
			int k = bll.updateEmArchive(model);
			if (k > 0) {
				Messagebox.show("修改成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				winupdate.detach();
			} else {
				Messagebox.show("修改失败", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		}
	}

	private String changedate(Date d) {
		String formatDate = "";
		if (d != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			formatDate = df.format(d);
		}
		return formatDate;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		try {
			if (str != null && !str.equals("")) {
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
