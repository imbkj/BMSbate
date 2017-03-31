package bll.Archives;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.Archives.EmArchiveRemakDal;

import Model.EmArchiveRemarkModel;

public class ArchiveRemarkBll {
	
	public Integer addfeeRemark(){
		Integer i=0;
		
		return i;
	}
	
	public List<EmArchiveRemarkModel> readfeeRemark(Integer id){
		List<EmArchiveRemarkModel> list = new ListModelList<>();
		EmArchiveRemakDal dal = new EmArchiveRemakDal();
		EmArchiveRemarkModel em = new EmArchiveRemarkModel();
		em.setEare_tid(3);
		em.setEare_trid(id);
		list=dal.getList(em,"order by eare_addtime desc");
		return list;
	}
}
