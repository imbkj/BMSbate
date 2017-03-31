package Controller.Embase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import Controller.EmCensus.EmDh.emdh_remarkController;
import Model.EmbaseBusinessCenterModel;

public class EmBase_DistributeController {
	private Integer gid=97749;
	private EmbaseBusinessCenterModel ebcm = new EmbaseBusinessCenterModel();
	private List<EmbaseBusinessCenterModel> list = new ListModelList<>();
	
	public EmBase_DistributeController(){
		
	}
	
	@Command("submit")
	public void submit(){
		EmbaseBusinessCenterModel ebcm = new EmbaseBusinessCenterModel();
		ebcm.setEmce_id(6);
		list.add(ebcm);
		ebcm = new EmbaseBusinessCenterModel();
		ebcm.setEmce_id(24);
		ebcm.setEmce_pid(6);
		list.add(ebcm);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gid", gid);
		map.put("modellist", list);
		System.out.println(gid);
		System.out.println(ebcm);
		Window window = (Window) Executions.createComponents(
				"../SystemControl/EmBuCenterInfoList.zul", null, map);
		window.doModal();
	}
}
