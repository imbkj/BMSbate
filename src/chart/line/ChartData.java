package chart.line;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

public class ChartData {
	
	public static CategoryModel getModel() {
		CategoryModel model = new SimpleCategoryModel();
		String[] category = { "Predict", "Actual", "Establishment" };
		model.setValue(category[0], "Jan", new Integer(25));
		model.setValue(category[0], "Feb", new Integer(35));
		model.setValue(category[0], "Mar", new Integer(45));
		model.setValue(category[0], "Apr", new Integer(48));
		model.setValue(category[0], "May", new Integer(53));
		model.setValue(category[0], "Jun", new Integer(62));

		model.setValue(category[1], "Jan", new Integer(28));
		model.setValue(category[1], "Feb", new Integer(33));
		model.setValue(category[1], "Mar", new Integer(40));
		model.setValue(category[1], "Apr", new Integer(53));
		model.setValue(category[1], "May", new Integer(58));
		model.setValue(category[1], "Jun", new Integer(75));

		model.setValue(category[2], "Jan", new Integer(40));
		model.setValue(category[2], "Feb", new Integer(55));
		model.setValue(category[2], "Mar", new Integer(65));
		model.setValue(category[2], "Apr", new Integer(57));
		model.setValue(category[2], "May", new Integer(63));
		model.setValue(category[2], "Jun", new Integer(68));
		return model;
	}
}
