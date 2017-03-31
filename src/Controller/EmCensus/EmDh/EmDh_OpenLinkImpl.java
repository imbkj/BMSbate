package Controller.EmCensus.EmDh;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmDhModel;

public class EmDh_OpenLinkImpl implements EmDh_OpenZul{

	@Override
	public void open(EmDhModel m) {
		Map map = new HashMap<>();
		map.put("gid", m.getGid());
		Window window = (Window) Executions.createComponents(
				"../EmReg/EmReg_LinkAdd.zul", null, map);
		window.doModal();
	}

}
