package impl.SystemControl;

import service.PublicDataService;
import bll.SystemControl.Menu_RolePubBll;

public class Menu_PubImpl implements PublicDataService{
	private int meu_id;
	private int rol_id;
	private String str="";
	
	private Menu_RolePubBll bll=new Menu_RolePubBll();
	
	public Menu_PubImpl()
	{
	}
	
	public Menu_PubImpl(int rolid,int meuid)
	{
		this.meu_id=meuid;
		this.rol_id=rolid;
	}
	
	public Menu_PubImpl(String sql,int rol_id)
	{
		this.str=sql;
		this.rol_id=rol_id;
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		return bll.updateMenuPub(rol_id, meu_id);
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return bll.deleteMenuPub(str, rol_id);
	}

	@Override
	public int add() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int check() {
		// TODO Auto-generated method stub
		return 0;
	}
}
