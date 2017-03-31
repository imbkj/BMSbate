package bll.KnowledgeBase;

import java.util.List;

import Model.KnowledgeBaseModel;
import Model.KnowledgeBase_ContentModel;
import dal.KnowledgeBase.KnowledgeBase_Dal;

public class KnowledgeBase_Bll {
	KnowledgeBase_Dal dal=new KnowledgeBase_Dal();
	
	//根据pid获取字典库的类型信息
	public List<KnowledgeBaseModel> getKnowledgeBaseInfo(int pid)
	{
		return dal.getKnowledgeBaseInfo(pid);
	}
	
	//获取字典库的内容信息
	public List<KnowledgeBase_ContentModel> getKnowledgeBaseConInfo(String str)
	{
		return dal.getKnowledgeBaseConInfo(str);
	}
	
	//获取字典库的内容类型信息
	public List<KnowledgeBase_ContentModel> getKnowledgeBaseConClassInfo()
	{
		return dal.getKnowledgeBaseConClassInfo();
	}
	
	//获取字典库的内容添加人信息
	public List<KnowledgeBase_ContentModel> getKnowleBaseAddnameInfo()
	{
		return dal.getKnowleBaseAddnameInfo();
	}
	
	//添加知识库内容
	public int addKnowleBaseInfo(KnowledgeBase_ContentModel model)
	{
		return dal.addKnowleBaseInfo(model);
	}
	//更新知识库内容
	public int updateKnowleBaseInfo(KnowledgeBase_ContentModel model)
	{
		return dal.updateKnowleBaseInfo(model);
	}
}
