package MPD.app.Model;

import java.util.ArrayList;

import MPD.app.Entry.MPD_Client_Entry;
import MPD.app.Entry.MPD_SpAdmin_Entry;
import MPD.sys.Core._Model;

public class Admin_Model extends _Model{
    public Admin_Model(){
    	
    	
    }
    public int keepAlive(String ip,String mark){
    	long currenttime=System.currentTimeMillis();
		   Object[] object={ip,currenttime,mark};
		   ArrayList<MPD_SpAdmin_Entry> b = (ArrayList<MPD_SpAdmin_Entry>) this.querry("select * from MPD_SPADMIN where ADMIN_NAME=?",mark,MPD_SpAdmin_Entry.class);
    	if (b==null||b.isEmpty())
    	{
    		return 0;
    	}
    	
    	   int i= this.update("update MPD_SPADMIN set ADMIN_IP=?,ADMIN_ALIVE=?where ADMIN_NAME=?",object);
		   return i;
    }
}
