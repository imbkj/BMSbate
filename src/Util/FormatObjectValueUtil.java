package Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

public class FormatObjectValueUtil {
	public String getFieldValue(Object obj) {
		String value = "";
		if (obj != null) {
			if (obj.getClass().equals(Combobox.class)) {
				Combobox cb = (Combobox) obj;
				if (cb.getSelectedItem() != null
						&& !cb.getSelectedItem().getValue().toString()
								.equals("")) {

					value = cb.getSelectedItem().getValue().toString();
				}
			} else if (obj.getClass().equals(Comboitem.class)) {
				Comboitem ci = (Comboitem) obj;
				if (ci.getValue() != null
						&& !ci.getValue().toString().equals("")) {
					value = ci.getValue();
				}
			} else if (obj.getClass().equals(Date.class)) {
				Date date = (Date) obj;
				if (date != null) {
					value = DateFormat.getDateInstance().format(date);
				}
			} else if (obj.getClass().equals(Integer.class)) {
				Integer i = (Integer) obj;
				if (i != null) {
					value = i.toString();
				}
			} else if (obj.getClass().equals(String.class)) {
				String s = (String) obj;
				if (s != null) {
					value = s;
				}
			}

		}
		return value;
	}

	public String getFieldLabel(Object obj) {
		String value = "";

		if (obj != null) {

			if (obj.getClass().equals(Combobox.class)) {
				Combobox cb = (Combobox) obj;

				if (cb.getSelectedItem() != null
						&& !cb.getSelectedItem().getLabel().equals("")) {

					value = cb.getSelectedItem().getLabel();
				}
			} else if (obj.getClass().equals(Comboitem.class)) {
				Comboitem ci = (Comboitem) obj;
				if (ci.getLabel() != null && !ci.getLabel().equals("")) {
					value = ci.getLabel();
				}
			}else if (obj.getClass().equals(Date.class)) {
				Date date = (Date) obj;
				if (date != null) {
					value = getDateString(date);
				}
			}
		}
		return value;
	}

	public Date getDateValue(Date d) {
		Date date = d == null ? null : d;
		return date;
	}

	public String getDateString(Date d){
		String date="";
		if (d!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.format(d);
		}
		return date;
	}
	
}
