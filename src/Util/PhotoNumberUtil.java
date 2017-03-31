package Util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PhotoNumberUtil {
	
	public static String getphotoNumber(String idcard) {
		String url = "http://203.91.45.154/query/queryApproveState.action?cardId="+idcard+"&queryType=imageNo";
		Document doc;
		String photoNumber="";
		try {
			doc = Jsoup.connect(url).get();
			Elements items = doc.getElementsByClass("tr_01");
			Element item=items.first();
			if(item.text()!=null&&!item.text().equals(""))
			{
				String photoNumberText=item.text();
				String a[] = photoNumberText.split("：");
				if(a.length>1)
				{
					photoNumber=a[1];
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return photoNumber;
	}
	
	//获取快递信息
	public static String getKuaidiList(String expr_company,String exprNumber) {
		String url = "http://m.kuaidi100.com/index_all.html?type=圆通快递&postid=710034515704#result";
		Document doc;
		String photoNumber="";
		try {
			doc = Jsoup.connect(url).get();
			Elements items = doc.select("tbody");
			Element item=items.first();
			if(item.text()!=null&&!item.text().equals(""))
			{
				String photoNumberText=item.text();
				String a[] = photoNumberText.split("：");
				if(a.length>1)
				{
					photoNumber=a[1];
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return photoNumber;
	}
	
	
}
