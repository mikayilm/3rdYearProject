package Model;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author Mikayil
 */
public class DataTypeDict {
    
    private Dictionary dict = new Hashtable();
    
    public DataTypeDict(){
        
        dict.put("int", "INTEGER");
        dict.put("varchar", "STRING");
        dict.put("char", "CHARACTER");
        dict.put("double", "DOUBLE");
        
    }
    
    
    
    
}
