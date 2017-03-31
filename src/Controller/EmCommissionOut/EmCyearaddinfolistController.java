package Controller.EmCommissionOut;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmCommissionYearChangemModel;
import Model.EmCommissionyearchangetitleModel;
import Model.EmSalaryDataModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.RegexUtil;
import Util.UserInfo;
import bll.EmCommissionOut.EmCmmissionyearchangeupdateBll;
import bll.EmCommissionOut.EmCommissionyearchangetitleBll;
import bll.Embase.EmbaseListBll;
import Util.UserInfo;

public class EmCyearaddinfolistController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	private List<Integer> countdate;
	private String strwhere = "";
	public List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	EmbaseListBll embasebll = new EmbaseListBll();
	EmCommissionyearchangetitleBll bll = new EmCommissionyearchangetitleBll();
	private List<EmCommissionYearChangemModel> ecycmodellist;
	private List<EmCommissionyearchangetitleModel> emcomm;
	private List<EmCommissionYearChangemModel> ecycmodelliswin;
	private List<EmCommissionyearchangetitleModel> semcomm = new ListModelList();
	private EmCommissionyearchangetitleModel model;

	@Wire
	private Grid gridwin;
	@Wire
	private Columns gridcols;

	@Wire
	private Rows gridrows;

	@Wire
	private Checkbox editAll; // 全选修改选项框

	private int t_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private int d_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());

	private ArrayList<String> wtztlist = new ArrayList<String>();
	public String wtzt;
	public String cjxm = "本批年调项目有：";// 采集项目

	public EmCyearaddinfolistController() {

		// System.out.println(t_id);
		wtztlist.add(0, "未录入");
		wtztlist.add(1, "已录入");
		wtztlist.add(2, "已采集");

		// search();

		// setEmcomm(bll.getemcommtlist(1, d_id));

		setEcycmodellist(bll.searchembaselist(UserInfo.getUsername(), d_id));
		setModel(bll.getemcommtlist(1, d_id).get(0));
		ecycmodelliswin = ecycmodellist;

		if (model.getEcyt_ylao() == 1) {

			cjxm = cjxm + "养老,";
		}

		if (model.getEcyt_gshang() == 1) {
			cjxm = cjxm + "工伤,";
		}

		if (model.getEcyt_yliao() == 1) {
			cjxm = cjxm + "医疗,";
		}

		if (model.getEcyt_sye() == 1) {
			cjxm = cjxm + "失业,";
		}
		if (model.getEcyt_syu() == 1) {
			cjxm = cjxm + "生育,";
		}

		if (model.getEcyt_gjj() == 1) {
			cjxm = cjxm + "公积金,";
		}

		if (model.getEcyt_bcgjj() == 1) {
			cjxm = cjxm + "补充公积金,";
		}
		cjxm = cjxm.substring(0, cjxm.length() - 1);

	}

	@NotifyChange({ "emcomm", "ecycmodellist" })
	public void search() {
		System.out.println(d_id);
		setEmcomm(bll.getemcommtlist(1, d_id));
		setEcycmodellist(bll.searchembaselist(UserInfo.getUsername(), d_id));
		setModel(emcomm.get(0));
		ecycmodelliswin = ecycmodellist;

	}

	public void deletegrid(Grid salarygrid) {

		Rows rows = salarygrid.getRows();
		List<Component> rownull = rows.getChildren();
		for (int i = rownull.size() - 1; i >= 0; i--) {
			rows.removeChild(rownull.get(i));
		}

	}

	// 检索
	@Command("changeList")
	@NotifyChange("ecycmodelliswin")
	public void changeList(@BindingParam("ibGid") Intbox ibGid,
			@BindingParam("ibcid") Intbox ibcid,
			@BindingParam("cname") Textbox cname,
			@BindingParam("gname") Textbox gname,
			@BindingParam("gridwin") Grid gridcols,
			@BindingParam("ecycstate") Combobox ecycstate) throws Exception {
		Integer sgid = ibGid.getValue();
		Integer scid = ibcid.getValue();
		if (!"".equals(cname.getValue()) || !"".equals(cname.getValue())
				|| ibGid.getValue() != null || ibcid.getValue() != null
				|| !"".equals(wtzt)) {
			if (sgid == null) {
				sgid = 0;
			}
			if (scid == null) {
				scid = 0;
			}

			ecycmodelliswin = getNewList(sgid, cname.getValue(), scid,
					gname.getValue(), wtzt);

		} else {
			ecycmodelliswin = ecycmodellist;
		}

		deletegrid(gridcols);
		rowsInit(gridcols);

	}

	// 检索数据
	private List<EmCommissionYearChangemModel> getNewList(int gid,
			String cname, int cid, String gname, String state) {
		List<EmCommissionYearChangemModel> list = new ArrayList<EmCommissionYearChangemModel>();
		int i = 5;
		for (EmCommissionYearChangemModel m : ecycmodellist) {
			i = 5;
			if (gid == 0) {
				i = i - 1;
			} else if (gid == m.getGid()) {
				i = i - 1;
			}

			if (cid == 0) {
				i = i - 1;
			} else if (cid == m.getCid()) {
				i = i - 1;
			}

			try {
				if (cname == null || "".equals(cname)) {
					i = i - 1;
				} else if (RegexUtil.isExists(cname, m.getCoba_shortname())) {
					i = i - 1;
				}
			} catch (Exception e) {

			}

			try {
				if (gname == null || "".equals(gname)) {
					i = i - 1;
				} else if (RegexUtil.isExists(gname, m.getEmba_name())) {
					i = i - 1;
				}
			} catch (Exception e) {

			}

			try {
				if (state == null || "".equals(state)) {
					i = i - 1;
				} else if (RegexUtil.isExists(state, m.getEcyc_statestr())) {
					i = i - 1;
				}
			} catch (Exception e) {

			}

			System.out.println(i);

			if (i == 0) {
				list.add(m);
			}
		}
		return list;
	}

	// 生成表头
	@Command("colsInit")
	@NotifyChange("emcomm")
	public void colsInit(@BindingParam("self") Grid gridcols) throws Exception {

		Column col4 = new Column();
		col4.setParent(gridcols.getColumns());
		Label lab4 = new Label();
		lab4.setParent(col4);
		lab4.setValue("城市");
		// lab3.setStyle("cursor:pointer");
		col4.setWidth("60px");

		Column col41 = new Column();
		col41.setParent(gridcols.getColumns());
		Label lab41 = new Label();
		lab41.setParent(col41);
		lab41.setValue("机构");
		// lab3.setStyle("cursor:pointer");
		col41.setWidth("60px");

		if (model.getEcyt_ylao() + model.getEcyt_gshang()
				+ model.getEcyt_yliao() + model.getEcyt_sye()
				+ model.getEcyt_syu() > 0) {
			Column colylao = new Column();
			colylao.setParent(gridcols.getColumns());
			Label labylao = new Label();
			labylao.setParent(colylao);
			labylao.setValue("社保基数");
			// lab3.setStyle("cursor:pointer");
			colylao.setWidth("60px");
		}

		// if (model.getEcyt_ylao() == 1) {
		// Column colylao = new Column();
		// colylao.setParent(gridcols.getColumns());
		// Label labylao = new Label();
		// labylao.setParent(colylao);
		// labylao.setValue("养老");
		// // lab3.setStyle("cursor:pointer");
		// colylao.setWidth("60px");
		//
		// }
		//
		// if (model.getEcyt_gshang() == 1) {
		// Column colgshang = new Column();
		// colgshang.setParent(gridcols.getColumns());
		// Label labgshang = new Label();
		// labgshang.setParent(colgshang);
		// labgshang.setValue("工伤");
		// // lab3.setStyle("cursor:pointer");
		// colgshang.setWidth("60px");
		//
		// }
		//
		// if (model.getEcyt_yliao() == 1) {
		// Column colyliao = new Column();
		// colyliao.setParent(gridcols.getColumns());
		// Label labyliao = new Label();
		// labyliao.setParent(colyliao);
		// labyliao.setValue("医疗");
		// // lab3.setStyle("cursor:pointer");
		// colyliao.setWidth("60px");
		//
		// }
		//
		// if (model.getEcyt_sye() == 1) {
		// Column colsye = new Column();
		// colsye.setParent(gridcols.getColumns());
		// Label labsye = new Label();
		// labsye.setParent(colsye);
		// labsye.setValue("失业");
		// // lab3.setStyle("cursor:pointer");
		// colsye.setWidth("60px");
		//
		// }
		// if (model.getEcyt_syu() == 1) {
		// Column colsyu = new Column();
		// colsyu.setParent(gridcols.getColumns());
		// Label labsyu = new Label();
		// labsyu.setParent(colsyu);
		// labsyu.setValue("生育");
		// // lab3.setStyle("cursor:pointer");
		// colsyu.setWidth("60px");
		//
		// }

		// if (model.getEcyt_gjj()+model.getEcyt_bcgjj()>0)
		// {
		//
		// }

		if (model.getEcyt_gjj() == 1) {
			Column colgjj = new Column();
			colgjj.setParent(gridcols.getColumns());
			Label labgjj = new Label();
			labgjj.setParent(colgjj);
			labgjj.setValue("住房基数");
			// lab3.setStyle("cursor:pointer");
			colgjj.setWidth("60px");

			Column colgjj1 = new Column();
			colgjj1.setParent(gridcols.getColumns());
			Label labgjj1 = new Label();
			labgjj1.setParent(colgjj1);
			labgjj1.setValue("住房企业比例（格式0.00）");
			// lab3.setStyle("cursor:pointer");
			colgjj1.setWidth("100px");

			Column colgjj2 = new Column();
			colgjj2.setParent(gridcols.getColumns());
			Label labgjj2 = new Label();
			labgjj2.setParent(colgjj2);
			labgjj2.setValue("住房个人比例（格式0.00）");
			// lab3.setStyle("cursor:pointer");
			colgjj2.setWidth("100px");

			Column colgjj3 = new Column();
			colgjj3.setParent(gridcols.getColumns());
			Label labgjj3 = new Label();
			labgjj3.setParent(colgjj3);
			labgjj3.setValue("住房缴存额");
			// lab3.setStyle("cursor:pointer");
			colgjj3.setWidth("100px");

		}

		if (model.getEcyt_bcgjj() == 1) {
			Column colbcgjj = new Column();
			colbcgjj.setParent(gridcols.getColumns());
			Label labbcgjj = new Label();
			labbcgjj.setParent(colbcgjj);
			labbcgjj.setValue("补充住房");
			// lab3.setStyle("cursor:pointer");
			colbcgjj.setWidth("100px");

			Column colbcgjj1 = new Column();
			colbcgjj1.setParent(gridcols.getColumns());
			Label labbcgjj1 = new Label();
			labbcgjj1.setParent(colbcgjj1);
			labbcgjj1.setValue("补充住房企业比例（格式0.00）");

			colbcgjj1.setWidth("150px");

			Column colbcgjj2 = new Column();
			colbcgjj2.setParent(gridcols.getColumns());
			Label labbcgjj2 = new Label();
			labbcgjj2.setParent(colbcgjj2);
			labbcgjj2.setValue("补充住房个人比例（格式0.00）");
			// lab3.setStyle("cursor:pointer");
			colbcgjj2.setWidth("150px");

			Column colbcgjj3 = new Column();
			colbcgjj3.setParent(gridcols.getColumns());
			Label labbcgjj3 = new Label();
			labbcgjj3.setParent(colbcgjj3);
			labbcgjj3.setValue("补充住房缴存额");
			// lab3.setStyle("cursor:pointer");
			colbcgjj3.setWidth("150px");

		}

		Column col9 = new Column();
		col9.setParent(gridcols.getColumns());
		Label lab9 = new Label();
		lab9.setParent(col9);
		lab9.setValue("客服");

		col9.setWidth("60px");

		rowsInit(gridcols);
	}

	// 生成数据

	@SuppressWarnings("unchecked")
	public void rowsInit(Grid gridrows) throws Exception {

		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

		for (int m = 0; m < ecycmodelliswin.size(); m++) {

			Row gdr = new Row();
			gdr.setParent(gridrows.getRows());

			Checkbox cbone = new Checkbox();
			cbone.setParent(gdr);
			cbone.setValue(ecycmodelliswin.get(m).getEcyc_id());

			Label lab0 = new Label();

			lab0.setParent(gdr);
			lab0.setValue(ecycmodelliswin.get(m).getCid().toString());

			Label lab2 = new Label();
			lab2.setParent(gdr);
			lab2.setValue(ecycmodelliswin.get(m).getCoba_shortname().toString());

			Label lab1 = new Label();
			lab1.setParent(gdr);
			lab1.setValue(ecycmodelliswin.get(m).getGid().toString());
			Label lab3 = new Label();
			lab3.setParent(gdr);
			lab3.setValue(ecycmodelliswin.get(m).getEmba_name().toString());

			Cell cl = new Cell();
			cl.setParent(gdr);
			Hbox hb = new Hbox();
			hb.setParent(cl);
			Label lab81 = new Label();
			lab81.setParent(hb);
			lab81.setValue(ecycmodelliswin.get(m).getEcyc_statestr());
			lab81.setStyle("cursor:pointer");
			lab81.addEventListener(
					"onClick",
					new listener(ecycmodelliswin.get(m).getEcyc_id(),
							ecycmodelliswin.get(m).getEcyc_state(), gridrows, m));

			Label lab4 = new Label();
			lab4.setParent(gdr);
			lab4.setValue(ecycmodelliswin.get(m).getCity().toString());

			Label lab41 = new Label();
			lab41.setParent(gdr);
			lab41.setValue(ecycmodelliswin.get(m).getCoab_name().toString());

			if (model.getEcyt_ylao() + model.getEcyt_gshang()
					+ model.getEcyt_yliao() + model.getEcyt_sye()
					+ model.getEcyt_syu() > 0) {
				Decimalbox lab5 = new Decimalbox();
				lab5.setParent(gdr);
				lab5.setValue(df.format(
						ecycmodelliswin.get(m).getEcyc_sb_base()).toString());
				lab5.setInplace(true);
				lab5.addEventListener("onChange", new MyListenerChangestyle(
						lab5));
			}

			// if (model.getEcyt_ylao() == 1) {
			//
			// // Label lab5 = new Label();
			// Decimalbox lab5 = new Decimalbox();
			//
			// lab5.setParent(gdr);
			// lab5.setValue(df.format(
			// ecycmodelliswin.get(m).getEcyc_yl_base()).toString());
			// lab5.setInplace(true);
			// lab5.addEventListener("onChange", new MyListenerChangestyle(
			// lab5));
			//
			// }
			// if (model.getEcyt_gshang() == 1) {
			// Decimalbox lab6 = new Decimalbox();
			// lab6.setParent(gdr);
			// lab6.setValue(df.format(
			// ecycmodelliswin.get(m).getEcyc_gs_base()).toString());
			// lab6.setInplace(true);
			// lab6.addEventListener("onChange", new MyListenerChangestyle(
			// lab6));
			// }
			//
			// if (model.getEcyt_yliao() == 1) {
			// Decimalbox lab7 = new Decimalbox();
			// lab7.setParent(gdr);
			// lab7.setValue(df.format(
			// ecycmodelliswin.get(m).getEcyc_jl_base()).toString());
			//
			// lab7.setInplace(true);
			// lab7.addEventListener("onChange", new MyListenerChangestyle(
			// lab7));
			// }
			//
			// if (model.getEcyt_sye() == 1) {
			// Decimalbox lab8 = new Decimalbox();
			// lab8.setParent(gdr);
			// lab8.setValue(df.format(
			// ecycmodelliswin.get(m).getEcyc_sye_base()).toString());
			//
			// lab8.setInplace(true);
			// lab8.addEventListener("onChange", new MyListenerChangestyle(
			// lab8));
			// }
			// if (model.getEcyt_syu() == 1) {
			// Decimalbox lab9 = new Decimalbox();
			// lab9.setParent(gdr);
			// lab9.setValue(df.format(
			// ecycmodelliswin.get(m).getEcyc_syu_base()).toString());
			//
			// lab9.setInplace(true);
			// lab9.addEventListener("onChange", new MyListenerChangestyle(
			// lab9));
			// }

			if (model.getEcyt_gjj() == 1) {
				Decimalbox lab91 = new Decimalbox();
				lab91.setParent(gdr);
				lab91.setValue(df.format(
						ecycmodelliswin.get(m).getEcyc_house_base()).toString());
				lab91.setInplace(true);
				lab91.addEventListener("onChange", new MyListenerChangestyle(
						lab91));

				Decimalbox lab92 = new Decimalbox();
				lab92.setParent(gdr);
				lab92.setValue(ecycmodelliswin.get(m).getEcyc_house_cp());
				lab92.setInplace(true);
				lab92.addEventListener("onChange", new MyListenerChangestyle(
						lab92));

				Decimalbox lab93 = new Decimalbox();
				lab93.setParent(gdr);
				lab93.setValue(ecycmodelliswin.get(m).getEcyc_house_op());
				lab93.setInplace(true);
				lab93.addEventListener("onChange", new MyListenerChangestyle(
						lab93));
				// 缴存额

				Decimalbox lab94 = new Decimalbox();
				lab94.setParent(gdr);
				lab94.setValue(df.format(
						ecycmodelliswin.get(m).getEcyc_house_hj()).toString());
				lab94.setInplace(true);
				lab94.addEventListener("onChange", new MyListenerChangestyle(
						lab94));

			}

			if (model.getEcyt_bcgjj() == 1) {
				Decimalbox lab911 = new Decimalbox();
				lab911.setParent(gdr);
				lab911.setValue(df.format(
						ecycmodelliswin.get(m).getEcyc_bc_base()).toString());
				lab911.setInplace(true);
				lab911.addEventListener("onChange", new MyListenerChangestyle(
						lab911));

				Decimalbox lab921 = new Decimalbox();
				lab921.setParent(gdr);
				lab921.setValue(ecycmodelliswin.get(m).getEcyc_bc_cp());
				lab921.setInplace(true);
				lab921.addEventListener("onChange", new MyListenerChangestyle(
						lab921));

				Decimalbox lab931 = new Decimalbox();
				lab931.setParent(gdr);
				lab931.setValue(ecycmodelliswin.get(m).getEcyc_bc_op());
				lab931.setInplace(true);
				lab931.addEventListener("onChange", new MyListenerChangestyle(
						lab931));

				Decimalbox lab941 = new Decimalbox();
				lab941.setParent(gdr);
				lab941.setValue(df.format(
						ecycmodelliswin.get(m).getEcyc_bc_total()).toString());
				lab941.setInplace(true);
				lab941.addEventListener("onChange", new MyListenerChangestyle(
						lab941));

			}
			Label lab49 = new Label();
			lab49.setParent(gdr);
			lab49.setValue(ecycmodelliswin.get(m).getCoba_client().toString());

		}

	}

	// 全选功能

	@Listen("onCheck = #editAll")
	public void checkedi(CheckEvent e) {
		List row = gridwin.getRows().getChildren();
		for (Object obj : row) {
			Row comp = (Row) obj;
			Checkbox ck = (Checkbox) comp.getChildren().get(0);
			ck.setChecked(e.isChecked());
			if (ck.isChecked()) {
				System.out.print(ck.getValue() + ",");
			}
		}
	}

	// 弹出导入基数
	@Command("jscj")
	public void ChangeSalary(@BindingParam("win") Window win) {
		// win.detach();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("d_id", d_id);
		map.put("t_id", t_id);
		map.put("cid", 1033);
		map.put("model", model);
		map.put("wininfo", win);
		map.put("ecycmodellist", ecycmodelliswin);
		Window window = (Window) Executions.createComponents(
				"..\\EmCommissionOut\\EmCyearcjupdate.zul", null, map);
		window.doModal();

		search();

	}

	// 提交数据

	@Command("Upjscjlist")
	public void Upjscjlist(@BindingParam("gridwin") Grid gd) {
		// win.detach();
		EmCmmissionyearchangeupdateBll cbll = new EmCmmissionyearchangeupdateBll();
		int massgeint = 0;

		StringBuilder upstrsql = new StringBuilder();
		try {

			// 定义row 并将grid的行赋值到row
			List row = gd.getRows().getChildren();
			// 遍历row

			int Gindex = -1;
			for (Object obj : row) {
				Gindex++;
				Row comp = (Row) obj;
				// 定义1个checkbox获取当前遍历行的控件
				Checkbox checksel = (Checkbox) comp.getChildren().get(0);

				if (checksel.isChecked()) {

					int i = 7;

					upstrsql.append("update EmCommissionYearChange set ");

					if (model.getEcyt_ylao() + model.getEcyt_gshang()
							+ model.getEcyt_yliao() + model.getEcyt_sye()
							+ model.getEcyt_syu() > 0) {
						i++;
						System.out.println(gd.getCell(Gindex, i));
						upstrsql.append("ecyc_sb_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");
					}

					if (model.getEcyt_gjj() == 1) {
						i = i + 1;
						upstrsql.append("ecyc_house_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");

						i = i + 1;
						upstrsql.append("ecyc_house_cp="
								+ String.valueOf(((Decimalbox) gd.getCell(
										Gindex, i)).getValue()) + ",");

						i = i + 1;
						upstrsql.append("ecyc_house_op="
								+ String.valueOf(((Decimalbox) gd.getCell(
										Gindex, i)).getValue()) + ",");

						i = i + 1;
						upstrsql.append("ecyc_house_hj="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");
					}

					if (model.getEcyt_bcgjj() == 1) {
						i = i + 1;
						upstrsql.append("ecyc_bc_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");

						i = i + 1;
						upstrsql.append("ecyc_bc_cp="
								+ String.valueOf(((Decimalbox) gd.getCell(
										Gindex, i)).getValue()) + ",");

						i = i + 1;
						upstrsql.append("ecyc_bc_op="
								+ String.valueOf(((Decimalbox) gd.getCell(
										Gindex, i)).getValue()) + ",");

						i = i + 1;
						upstrsql.append("ecyc_bc_total="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");
					}
					upstrsql.append("ecyc_state=2, ");
					upstrsql.append("ecyc_modname='" + UserInfo.getUsername()
							+ "', ");
					upstrsql.append("ecyc_modtime=getdate() ");
					upstrsql.append("where  ecyc_id='"
							+ checksel.getValue().toString() + "'");
					// System.out.println(upstrsql.toString());
					massgeint = massgeint
							+ cbll.updateyeardata(upstrsql.toString());

					/*******************
					 * 提交后更新委托在册数据 会议中提出要求需要及时修改在册数据
					 *******************/
					// 1、获得在册数据model
					// 2、计算在册数据
					// 3、更新在册数据

				}

			}

			if (massgeint > 0) {
				Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);

				search();
				deletegrid(gd);
				rowsInit(gd);

			} else {
				Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		// bll.passtonext("3", t_id, UserInfo.getUsername(), d_id);

	}

	@Command("checkall")
	public void checkall(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox ck) {
		try {
			List row = gd.getRows().getChildren();
			for (Object obj : row) {
				Row comp = (Row) obj;
				Checkbox ckitem = (Checkbox) comp.getChildren().get(0);
				ckitem.setChecked(ck.isChecked());
				if (ck.isChecked()) {

				}

			}

		} catch (Exception e) {
			Messagebox.show("提交出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}

	// 审核
	@Listen("onClick = #btplsh")
	public void submit() {
		boolean rowcout = false;
		String idcountstr = "";
		try {

			// 定义row 并将grid的行赋值到row
			List row = gridwin.getRows().getChildren();
			// 遍历row
			for (Object obj : row) {

				Row comp = (Row) obj;
				// 定义1个checkbox获取当前遍历行的控件
				Checkbox checksel = (Checkbox) comp.getChildren().get(0);
				if (checksel.isChecked()) {
					// Integer.parseInt(checksel.getValue().toString());
					idcountstr = idcountstr + checksel.getValue().toString()
							+ ",";

				}
			}

			int x = 0;
			x = bll.checktasktonext(d_id, 0);

			if (x == 0) {

				bll.passtonext("2", t_id, UserInfo.getUsername(), d_id);
			}

			if (rowcout) {
				Messagebox.show("审核成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);

			} else {
				Messagebox.show("审核失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	// 点击采集

	@SuppressWarnings("rawtypes")
	class listener implements org.zkoss.zk.ui.event.EventListener {
		public int ecyc_id;
		public Grid gd;
		public String msgstr;
		public int Gindex;
		private int state;
		int massgeint;
		EmCmmissionyearchangeupdateBll cbll = new EmCmmissionyearchangeupdateBll();
		StringBuilder upstrsql = new StringBuilder();

		public listener(int ecyc_id, int ecyc_state, Grid gd, int m) {
			this.ecyc_id = ecyc_id;
			this.gd = gd;
			this.Gindex = m;
			this.state = ecyc_state;
		}

		@Override
		public void onEvent(Event arg0) throws Exception {
			// TODO Auto-generated method stub
			boolean row = false;

			if (state == 0) {
				msgstr = "是否单独采集该条数据？";
			} else {
				msgstr = "是否取消该条数据重新采集？";
			}

			if (Messagebox.show(msgstr, "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {

				if (state == 0) {
					// 采集单条数据

					int i = 7;

					upstrsql.append("update EmCommissionYearChange set ");

					if (model.getEcyt_ylao() == 1) {
						i++;
						upstrsql.append("ecyc_yl_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");
					}
					if (model.getEcyt_gshang() == 1)

					{
						i = i + 1;
						upstrsql.append("ecyc_gs_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");

					}

					if (model.getEcyt_yliao() == 1) {
						i = i + 1;
						upstrsql.append("ecyc_jl_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");

					}

					if (model.getEcyt_sye() == 1) {
						i = i + 1;
						upstrsql.append("ecyc_sye_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");

					}
					if (model.getEcyt_syu() == 1) {
						i = i + 1;
						upstrsql.append("ecyc_syu_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");
					}

					if (model.getEcyt_gjj() == 1) {
						i = i + 1;
						upstrsql.append("ecyc_house_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");

						i = i + 1;
						upstrsql.append("ecyc_house_cp="
								+ String.valueOf(((Decimalbox) gd.getCell(
										Gindex, i)).getValue()) + ",");

						i = i + 1;
						upstrsql.append("ecyc_house_op="
								+ String.valueOf(((Decimalbox) gd.getCell(
										Gindex, i)).getValue()) + ",");

						i = i + 1;
						upstrsql.append("ecyc_house_hj="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");
					}

					if (model.getEcyt_bcgjj() == 1) {
						i = i + 1;
						upstrsql.append("ecyc_bc_base="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");

						i = i + 1;
						upstrsql.append("ecyc_bc_cp="
								+ String.valueOf(((Decimalbox) gd.getCell(
										Gindex, i)).getValue()) + ",");

						i = i + 1;
						upstrsql.append("ecyc_bc_op="
								+ String.valueOf(((Decimalbox) gd.getCell(
										Gindex, i)).getValue()) + ",");

						i = i + 1;
						upstrsql.append("ecyc_bc_total="
								+ ((Decimalbox) gd.getCell(Gindex, i))
										.getValue() + ",");
					}
					upstrsql.append("ecyc_state=1, ");
					upstrsql.append("ecyc_modname='" + UserInfo.getUsername()
							+ "', ");
					upstrsql.append("ecyc_modtime=getdate() ");

					upstrsql.append("where  ecyc_id=" + ecyc_id + "");
					System.out.println(upstrsql.toString());
					massgeint = massgeint
							+ cbll.updateyeardata(upstrsql.toString());

				}
				// 执行确认方法
				row = bll.unlockedit(ecyc_id, state);
				search();
				deletegrid(gd);
				rowsInit(gd);

				if (row) {
					Messagebox.show("成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);

				} else {
					Messagebox.show("取消失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}

			}
		}
	}

	// 点击选择事件
	@SuppressWarnings("rawtypes")
	class MyListenerCheckall implements org.zkoss.zk.ui.event.EventListener {
		Grid gridcols;
		Checkbox chall;

		public MyListenerCheckall(Checkbox chall, Grid gridcols) {
			this.gridcols = gridcols;
			this.chall = chall;
		}

		@Override
		public void onEvent(Event arg0) {
			// TODO Auto-generated method stub
			// System.out.print(chall.isChecked());
			int msg = 0;
			String[] msg1 = null;
			int i = 0;
			int x = 0;
			try {
				List row = gridcols.getRows().getChildren();
				for (Object obj : row) {
					Row comp = (Row) obj;
					Checkbox ck = (Checkbox) comp.getChildren().get(
							gridcols.getColumns().getChildren().size() - 1);
					ck.setChecked(chall.isChecked());
					if (ck.isChecked()) {

					}
					// else {
					// Messagebox.show(msg1[1], "ERROR", Messagebox.OK,
					// Messagebox.ERROR);
					// }
				}

			} catch (Exception e) {
				Messagebox.show("提交出错,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				System.out.println(e.toString());
			}
		}
	}

	// 改变颜色
	@SuppressWarnings("rawtypes")
	class MyListenerChangestyle implements org.zkoss.zk.ui.event.EventListener {
		Decimalbox db;

		public MyListenerChangestyle(Decimalbox db) {
			this.db = db;

		}

		@Override
		public void onEvent(Event arg0) {

			try {
				((Checkbox) db.getParent().getChildren().get(0))
						.setChecked(true);
				db.setStyle("background-color:#FFCCFF;");

			} catch (Exception e) {
				Messagebox.show("出错,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				System.out.println(e.toString());
			}
		}
	}

	public String getCjxm() {
		return cjxm;
	}

	public void setCjxm(String cjxm) {
		this.cjxm = cjxm;
	}

	public List<EmCommissionYearChangemModel> getEcycmodellist() {
		return ecycmodellist;
	}

	public void setEcycmodellist(
			List<EmCommissionYearChangemModel> ecycmodellist) {
		this.ecycmodellist = ecycmodellist;
	}

	public List<EmCommissionyearchangetitleModel> getEmcomm() {
		return emcomm;
	}

	public void setEmcomm(List<EmCommissionyearchangetitleModel> emcomm) {
		this.emcomm = emcomm;
	}

	public List<EmCommissionyearchangetitleModel> getSemcomm() {
		return semcomm;
	}

	public void setSemcomm(List<EmCommissionyearchangetitleModel> semcomm) {
		this.semcomm = semcomm;
	}

	public EmCommissionyearchangetitleModel getModel() {
		return model;
	}

	public void setModel(EmCommissionyearchangetitleModel model) {
		this.model = model;
	}

	public Grid getGridwin() {
		return gridwin;
	}

	public void setGridwin(Grid gridwin) {
		this.gridwin = gridwin;
	}

	public Columns getGridcols() {
		return gridcols;
	}

	public void setGridcols(Columns gridcols) {
		this.gridcols = gridcols;
	}

	public Rows getGridrows() {
		return gridrows;
	}

	public void setGridrows(Rows gridrows) {
		this.gridrows = gridrows;
	}

	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public List<EmCommissionYearChangemModel> getEcycmodelliswin() {
		return ecycmodelliswin;
	}

	public void setEcycmodelliswin(
			List<EmCommissionYearChangemModel> ecycmodelliswin) {
		this.ecycmodelliswin = ecycmodelliswin;
	}

	public ArrayList<String> getWtztlist() {
		return wtztlist;
	}

	public void setWtztlist(ArrayList<String> wtztlist) {
		this.wtztlist = wtztlist;
	}

	public String getWtzt() {
		return wtzt;
	}

	public void setWtzt(String wtzt) {
		this.wtzt = wtzt;
	}

	public List<Integer> getCountdate() {
		return countdate;
	}

	public void setCountdate(List<Integer> countdate) {
		this.countdate = countdate;
	}

	public String getStrwhere() {
		return strwhere;
	}

	public void setStrwhere(String strwhere) {
		this.strwhere = strwhere;
	}

	public List<EmbaseModel> getEmbaselist() {
		return embaselist;
	}

	public void setEmbaselist(List<EmbaseModel> embaselist) {
		this.embaselist = embaselist;
	}
}
