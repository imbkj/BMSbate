package Controller.KnowledgeBase;

import java.io.IOException;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vlayout;

public class UploadFile_Controller extends SelectorComposer<Component> implements AfterCompose{
	@Wire
	private Vlayout flist;
	
	
	//上传政策指引文件
	@Listen("onUpload = #uploadfile")
	public void upload(Event event){
		 
		/*org.zkoss.util.media.Media media = event.getData();
        final Hlayout hl = new Hlayout();
        hl.setClass("newFile");
        hl.appendChild(new Label(media.getName()));
        String filename=media.getName();
        java.io.InputStream memberPhotoInputStream=media.getStreamData();
        String realPath="D:/workspace/BMSbeta/WebContent/KnowledgeBase/file/";
        java.io.File file=new java.io.File(realPath+filename);
        try {
			org.zkoss.io.Files.copy(file,memberPhotoInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        org.zkoss.io.Files.close(memberPhotoInputStream);
        A rm = new A("取消");
        rm.addEventListener(Events.ON_CLICK,new org.zkoss.zk.ui.event.EventListener(){
            public void onEvent(Event event) throws Exception {
                hl.detach();
            }
        });
        hl.appendChild(rm);
        flist.appendChild(hl);*/
	}


	@Override
	public void afterCompose() {
		// TODO Auto-generated method stub
		
	}
}
