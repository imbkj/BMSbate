package Controller.EmBenefit;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.EmActyWarehouseModel;
import Model.EmActyWarehouseNoteModel;
import bll.EmBenefit.EmBenefit_comitListBll;

public class EmActy_WarehouseNoteController {
	private EmActyWarehouseModel model = (EmActyWarehouseModel) Executions.getCurrent().getArg().get("model");
	private EmBenefit_comitListBll bll=new EmBenefit_comitListBll();
	private List<EmActyWarehouseNoteModel> buylist=bll.getEmActyWarehouseNote(" and  hsnt_content='采购' and hsnt_wase_id="+model.getWase_id());
	private List<EmActyWarehouseNoteModel> sendlist=bll.getEmActyWarehouseNote(" and  hsnt_content='发放' and hsnt_wase_id="+model.getWase_id());
	private List<EmActyWarehouseNoteModel> editlist=bll.getEmActyWarehouseNote(" and  hsnt_content='修改' and hsnt_wase_id="+model.getWase_id());

	
	
	private Integer buynum=0;
	private Integer sendnum=0;
	
	public EmActy_WarehouseNoteController()
	{
		for(EmActyWarehouseNoteModel m:buylist)
		{
			if(m.getHsnt_num()!=null)
			{
				buynum=buynum+m.getHsnt_num();
			}
		}
		for(EmActyWarehouseNoteModel m:sendlist)
		{
			if(m.getHsnt_num()!=null)
			{
				sendnum=sendnum+m.getHsnt_num();
			}
		}
	}
	public List<EmActyWarehouseNoteModel> getBuylist() {
		return buylist;
	}
	public void setBuylist(List<EmActyWarehouseNoteModel> buylist) {
		this.buylist = buylist;
	}
	public List<EmActyWarehouseNoteModel> getSendlist() {
		return sendlist;
	}
	public void setSendlist(List<EmActyWarehouseNoteModel> sendlist) {
		this.sendlist = sendlist;
	}
	public Integer getBuynum() {
		return buynum;
	}
	public void setBuynum(Integer buynum) {
		this.buynum = buynum;
	}
	public Integer getSendnum() {
		return sendnum;
	}
	public void setSendnum(Integer sendnum) {
		this.sendnum = sendnum;
	}
	public List<EmActyWarehouseNoteModel> getEditlist() {
		return editlist;
	}
	public void setEditlist(List<EmActyWarehouseNoteModel> editlist) {
		this.editlist = editlist;
	}
	
}
