package Controller.SocialInsurance;

import impl.PubCityImpl;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Controller.CoPolicyNotice.Pono_PubInfoAddController;
import Model.CoAgencyBaseCityRelModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Util.UserInfo;
import bll.SocialInsurance.Algorithm_InfoBll;
import bll.SocialInsurance.Algorithm_OperateBll;

public class Algorithm_StopController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private final int sial_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("sial_id").toString());
	private SocialInsuranceAlgorithmViewModel saModel;
	private int admin = 0;

	@Wire
	private Textbox soin_delreason;
	@Wire
	private Window winAlgorithmStop;
	@Wire 
	private Label zcadd;
	@Wire
	private Listbox docGrid;

	public Algorithm_StopController() {
		testAdmin();
		Algorithm_InfoBll bll = new Algorithm_InfoBll();
		saModel = bll.getSiAlBase(sial_id);
		
		Map map = new HashMap<>();
		map.put("pono_city", saModel.getCity());// 城市
		map.put("pono_agencies ", saModel.getCoab_name()); // 机构名称
		BindUtils.postGlobalCommand(null, null, "refreshlist", map);
	}

	// 删除
	@Listen("onClick = #btSubmit")
	public void Delete() {
		try {
			Algorithm_OperateBll bll = new Algorithm_OperateBll();
		 
			if (bll.existChangeSoinId(saModel.getSoin_id())) {
				saModel.setSoin_delreason(soin_delreason.getValue());
				saModel.setSoin_delname(UserInfo.getUsername());
				// 调用禁用标准方法
				String[] message = bll.StopSiAlgorithmChangeToTask(saModel);
				if (message[0].equals("1")) {
					//插入政策信息	
					Pono_PubInfoAddController pd = new Pono_PubInfoAddController();
					pd.InfoAdd(docGrid, Integer.parseInt(message[3]), "字典库新增");
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					winAlgorithmStop.detach();

				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}else{
				Messagebox.show("该标准有未审核的变更，暂无法停用。", "操作提示", Messagebox.OK, Messagebox.NONE);
			}
			 
		 
		} catch (Exception e) {
			Messagebox.show("请填写停用原因。", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
	}
	
	// 政策通知新增
				@Listen("onClick = #zcadd")
				public void zcadd() {
					CoAgencyBaseCityRelModel model=new CoAgencyBaseCityRelModel();
					 PubCityImpl cityimpl = new PubCityImpl();
					Integer cityid=cityimpl.getCityId(model.getCity());
					model.setId(cityid);
					model.setCity(saModel.getCity());
					Map map=new HashMap();
					map.put("model", model);
					map.put("type", "政府通知");
					map.put("classname", model.getCity());
					Window window=(Window)Executions.createComponents("/CoPolicyNotice/PoNo_InfoAdd.zul", null, map);
					window.doModal();
					
					Map map1 = new HashMap<>();
					map1.put("pono_city", saModel.getCity());// 城市
					map1.put("pono_agencies ", saModel.getCoab_name()); // 机构名称
					BindUtils.postGlobalCommand(null, null, "refreshlist", map1);
					
				}


	// 直接删除(隐藏功能)
	@Listen("onClick = #btSubmitOld")
	public void DelAl() {
		try {
			Algorithm_OperateBll bll = new Algorithm_OperateBll();
			int result = bll.StopSiAlgorithm(sial_id,
					soin_delreason.getValue(), UserInfo.getUsername());
			if (result == 1) {
				Messagebox.show("停用算法成功。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				winAlgorithmStop.detach();
			} else {
				Messagebox.show("停用算法失败。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("停用算法出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 测试权限
	private void testAdmin() {
		String username = UserInfo.getUsername();
		if ("李文洁".equals(username)) {
			this.admin = 1;
		}
	}

	public int getAdmin() {
		return admin;
	}

}
