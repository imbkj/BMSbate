package chart.line;

public class LineChartConfigVM {

	boolean threeD = false;
	boolean showLine = true;
	boolean showShape = true;
	int width = 2;

	public boolean isThreeD() {
		return threeD;
	}

	public void setThreeD(boolean threeD) {
		this.threeD = threeD;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isShowLine() {
		return showLine;
	}

	public void setShowLine(boolean showLine) {
		this.showLine = showLine;
	}

	public boolean isShowShape() {
		return showShape;
	}

	public void setShowShape(boolean showShape) {
		this.showShape = showShape;
	}

}
