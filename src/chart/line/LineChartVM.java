package chart.line;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.CategoryModel;

public class LineChartVM {

	CategoryModel model;
	LineChartEngine engine;
	String message;
	
	boolean threeD;

	@Init
	public void init() {
		engine = new LineChartEngine();
		model = ChartData.getModel();
	}

	public LineChartEngine getEngine() {
		return engine;
	}

	public CategoryModel getModel() {
		return model;
	}
	
	public String getMessage() {
		return message;
	}

	public boolean isThreeD() {
		return threeD;
	}

	@Command("showMessage") 
	@NotifyChange("message")
	public void onShowMessage(
			@BindingParam("msg") String message){
		this.message = message;
	}
	
	@GlobalCommand("configChanged") 
	@NotifyChange({"threeD","engine"})
	public void onConfigChanged(
			@BindingParam("threeD") Boolean threeD,
			@BindingParam("showLine") Boolean showLine,
			@BindingParam("showShape") Boolean showShape,
			@BindingParam("width") Integer width){
		if (threeD != null) {
			this.threeD = threeD;
		}

		if (showLine != null) {
			engine.setShowLine(showLine);
		}

		if (showShape != null) {
			engine.setShowShape(showShape);
		}

		if (width != null) {
			engine.setWidth(width);
		}
	}
}
