package Controller.EmCommissionOut;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import Util.UserInfo;



import Model.EmCommissionyearchangetitleModel;
import Util.RegexUtil;
import bll.EmCommissionOut.EmCommissionyearchangetitleBll;

public class EmCyearaddlistController  extends SelectorComposer<Component>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
	
	private List<EmCommissionyearchangetitleModel> emcomm;
	private List<EmCommissionyearchangetitleModel> semcomm =new ListModelList();
	private EmCommissionyearchangetitleBll bll =new EmCommissionyearchangetitleBll();
	
	private String city = "";
	private String jgname = "";
	 
	private EmCommissionyearchangetitleModel m;
	
	@Wire
	private Grid gridwin;
	@Wire
	private Columns gridcols;
	@Wire
	private Rows gridrows;

	
	
	@Wire
	private Checkbox editAll;	  //全选修改选项框
	@Wire
	private Button btplsh;
	
	private int t_id;
	private int d_id;
	//private StringBuilder strsqlinfo;
	
	public EmCyearaddlistController()
	{
		//节点ID
		t_id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());
	   // t_id=2;
		//数据ID
		d_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());
		//d_id=27;
		
		System.out.println(t_id);
		System.out.println(d_id);
		
		
		setEmcomm(new ListModelList<>(bll.getemcommtlist(0,d_id)));
		//System.out.println(emcomm);
		search();
	}
	
	

	// 检索
		@Command("search")
		@NotifyChange("semcomm")
		public void search() {
			semcomm.clear();

			for (EmCommissionyearchangetitleModel m : emcomm) {
				if (!city.isEmpty()) {
					if (!RegexUtil.isExists(city, m.getCity())) {
						continue;
					}
				}
				if (!jgname.isEmpty()) {
					if (!RegexUtil.isExists(jgname, m.getJgname())) {
						continue;
					}
				}
				semcomm.add(m);
				}
			
			
			//System.out.println(semcomm);
			}
		
		

		
	//全选功能
		
		 @Listen("onCheck = #editAll")
		   public void checkedi(CheckEvent e){
				 List row = gridwin.getRows().getChildren();
				    for(Object obj:row){
				      Row comp = (Row) obj;
				      Checkbox ck = (Checkbox) comp.getChildren().get(0);
				      ck.setChecked(e.isChecked());
				      if(ck.isChecked()){
				    //	  System.out.print(ck.getValue()+",");
				      }
				   }
			}

		 //审核
		 @Listen("onClick = #btplsh")
		 public void submit()
		 {
			 boolean rowcout=false;
			 String idcountstr="";
				try{
					
					//定义row 并将grid的行赋值到row	
					 List row = gridwin.getRows().getChildren();
					 //遍历row
					    for(Object obj:row){
					    		Row comp = (Row) obj;
						      //定义1个checkbox获取当前遍历行的控件
						      Checkbox checksel = (Checkbox) comp.getChildren().get(0);
						      if(checksel.isChecked())
						      {
						    	 // Integer.parseInt(checksel.getValue().toString());
						    	  idcountstr=idcountstr+checksel.getValue().toString()+",";
						      }
					    }
					  //  System.out.println(idcountstr.substring(0,idcountstr.length()-1));
					    //审核
					    rowcout= bll.adutingemcommlist(idcountstr.substring(0,idcountstr.length()-1), UserInfo.getUsername());
					    //判断是否任务单是否需要前行
					    int x=0;
					    x=bll.checktasktonext(d_id, 0);
					    if (x==0)
			    		{
					    	bll.passtonext("2",t_id, UserInfo.getUsername(), d_id);
			    		}
						if (rowcout) {
							Messagebox.show("审核成功!", "INFORMATION", Messagebox.OK,
									Messagebox.INFORMATION);
						} else {
							Messagebox.show("审核失败!", "ERROR", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
					catch(Exception e)
					{
//						System.out.println(e);
//						System.out.println(e);
						e.printStackTrace();
					}
		 }
	

 


	public int getD_id() {
			return d_id;
		}



		public void setD_id(int d_id) {
			this.d_id = d_id;
		}



	public List<EmCommissionyearchangetitleModel> getEmcomm() {
		return emcomm;
	}

	public void setEmcomm(List<EmCommissionyearchangetitleModel> emcomm) {
		this.emcomm = emcomm;
	}

	public List<EmCommissionyearchangetitleModel> getSemcomm() {
		return semcomm;
	}

	public void setSemcomm(List<EmCommissionyearchangetitleModel> semcomm) {
		this.semcomm = semcomm;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getJgname() {
		return jgname;
	}

	public void setJgname(String jgname) {
		this.jgname = jgname;
	}
}
