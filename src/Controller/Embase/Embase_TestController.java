package Controller.Embase;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import Model.EmbaseModel;
import Util.Page;
import bll.Embase.Embase_TestBll;

/**
 * 员工信息 -- 分页功能测试页面 控制层
 * @author suhongyuan
 * @create 2016-06-15
 *
 */
public class Embase_TestController extends GenericForwardComposer{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private AnnotateDataBinder binder;
private Window embaseTestWindow;

@Wire  
private Listbox embaseTestListbox;  
@Wire  
private Paging embasePaging; 
@Wire
private Textbox search;
@Wire
private Button oc;

private String key;

private Embase_TestBll bll=new Embase_TestBll();


public void noCreate$embaseTestWindow(Event event) throws Exception{
	  binder =new AnnotateDataBinder(embaseTestWindow);
	  binder.loadAll();
}

public void doAfterCompose(Component component) throws Exception {  
    super.doAfterCompose(component);  
    if(search==null){
    	key=" ";
    }else{
    	key=search.getValue();
    }
    embasePaging.setTotalSize(bll.toalCount());
    final int PAGE_SIZE =embasePaging.getPageSize();
    Page<EmbaseModel> p= new Page<EmbaseModel>();
    p.setCurrentPageIndex(0);
    p.setPageSize(PAGE_SIZE);
    p.setTotalSize(embasePaging.getTotalSize());
    redraw(key, p);  
    
    embasePaging.addEventListener("onPaging", new EventListener() {  
        public void onEvent(Event event) {  
            PagingEvent pe = (PagingEvent) event;  
            int pgno = pe.getActivePage();// 页数(从零计算)  
            int start = pgno * PAGE_SIZE; 
            Page<EmbaseModel> p= new Page<EmbaseModel>();
            p.setCurrentPageIndex(pe.getActivePage());
            System.out.println(pe.getActivePage());
            p.setPageSize(PAGE_SIZE);
            p.setTotalSize(embasePaging.getTotalSize());  
            redraw(key, p);  
          
        }  
    });
}

@SuppressWarnings("unchecked")  
private void redraw(String key, Page<EmbaseModel> p) {  
	embaseTestListbox.getItems().clear();  
	Page<EmbaseModel> PageBean = bll.pagingSearchEmbase(key, p);  
    List<EmbaseModel> list =  PageBean.getDataList();
   for (EmbaseModel info : list) {  
        Listitem item = new Listitem();  
        item.setValue(info);  
        item.appendChild(new Listcell(info.getCoba_client()));  
        item.appendChild(new Listcell(info.getEmba_pinyin()));  
        item.appendChild(new Listcell(info.getEmba_name()));  
        embaseTestListbox.appendChild(item);  
    }  
}  

/**
 * 列表检索
 * @throws SQLException 
 * 
 */
public void onChange$search(){
	 if(search==null){
	    	key=" ";
	    }else{
	    	key=search.getValue();
	    }
	System.out.println(key);
	embasePaging.setTotalSize(bll.toalCount());
    final int PAGE_SIZE =embasePaging.getPageSize();
    Page<EmbaseModel> p= new Page<EmbaseModel>();
    p.setCurrentPageIndex(0);
    p.setPageSize(PAGE_SIZE);
    p.setTotalSize(embasePaging.getTotalSize());
    redraw(key, p);  
    
    embasePaging.addEventListener("onPaging", new EventListener() {  
        public void onEvent(Event event) {  
            PagingEvent pe = (PagingEvent) event;  
            int pgno = pe.getActivePage();// 页数(从零计算)  
            int start = pgno * PAGE_SIZE; 
            Page<EmbaseModel> p= new Page<EmbaseModel>();
            p.setCurrentPageIndex(pe.getActivePage());
            p.setPageSize(PAGE_SIZE);
            p.setTotalSize(embasePaging.getTotalSize());  
            redraw(key, p);  
          
        }  
    });
}

public void onClick$oc(){
	if(search==null){
    	key=" ";
    }else{
    	key=search.getValue();
    }
System.out.println(key);
}

public Embase_TestBll getBll() {
	return bll;
}

public void setBll(Embase_TestBll bll) {
	this.bll = bll;
}

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

public Paging getEmbasePaging() {
	return embasePaging;
}

public void setEmbasePaging(Paging embasePaging) {
	this.embasePaging = embasePaging;
}


}
