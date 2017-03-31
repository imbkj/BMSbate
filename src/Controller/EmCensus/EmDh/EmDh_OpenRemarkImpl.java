package Controller.EmCensus.EmDh;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmDhModel;

public class EmDh_OpenRemarkImpl implements EmDh_OpenZul{

	@Override
	public void open(EmDhModel m) {
		Map map = new HashMap<>();
		map.put("daid", m.getId() + "");
		map.put("id", m.getEmdh_taprid() + "");
		map.put("model", m);
		map.put("gid", m.getGid());
		Window window = (Window) Executions.createComponents(
				"../EmCensus/Emdh_AddRemark.zul", null, map);
		window.doModal();
	}

}
