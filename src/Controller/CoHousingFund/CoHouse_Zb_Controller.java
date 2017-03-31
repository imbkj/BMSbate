package Controller.CoHousingFund;


import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;


/**
 * 专办员查询页面控制
 * @author Administrator
 *
 */
public class CoHouse_Zb_Controller {


	private String num;

	/**
	 * 加载数据
	 */
	public CoHouse_Zb_Controller() {
	}

	/**
	 * 搜索
	 */
	@Command
	public void search(){
		Executions.sendRedirect("CoHouse_ZB_Declare.zul");
	}
	
	@Command
	public void setNum(@BindingParam("name") String name,
			@BindingParam("nid") Textbox tb) {
		if (name.equals("高平")) {
			num = "362202198607070632DG";
			tb.setValue(num);
		} else if (name.equals("陈景桃")) {
			num = "44122919830601373XAC";
			tb.setValue(num);
		} else if (name.equals("江琦")) {
			num = "410203198203062520AM";
			tb.setValue(num);
		} else {
			num = "";
			tb.setValue(num);
		}
	}

	public String getNum() {
		return num;
	}


}
