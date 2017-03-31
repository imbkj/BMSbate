package Controller.Archives;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import Model.EmArchiveModel;

public class EmArchive_DetailInfoController {

	private EmArchiveModel m = (EmArchiveModel) Executions.getCurrent()
			.getArg().get("model");

	@Command
	public void oncreate(@BindingParam("rows") Rows rows) {
		if (m.getEmar_szresume() == null) {
			m.setEmar_szresume(""); // 如果为null ，把委托人才的工作经历split
		}
		String str[] = m.getEmar_szresume().split(",.,");
		Date sd = null;
		Date ed = null;
		String[] ds = null;
		String[] dd = null;
		String[] nr = null;
		for (int i = 0; i < str.length; i++) {
			try {
				ds = str[i].split("至");
				if (ds[0] != null && !ds[0].equals("")) {
					sd = new SimpleDateFormat("yyyy-MM-dd").parse(ds[0]);
				}
				if (ds.length > 1) {
					dd = ds[1].split("在");
					if (dd[0] != null && !dd[0].equals("")) {
						ed = new SimpleDateFormat("yyyy-MM-dd").parse(dd[0]);
					}
					if (dd.length > 1) {
						nr = dd[1].split("担任");
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Row row = new Row();
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
			if (ds.length > 1) {
				t1.setValue(nr[0]);
			}
			Textbox t2 = new Textbox();
			t2.setMold("rounded");
			if (ds.length > 1) {
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
}
