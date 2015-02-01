package Model;

/**
 *
 * @author pishiy
 */
public class TableProperty {
    
    public TableProperty(){
        
    }
    
    String FIELD;
    String TYPE;
    String NULL_;
    String KEY;
    String DEFAULT;
    String EXTRA;
       
    public String getFIELD() {
        return FIELD;
    }

    public void setFIELD(String FIELD) {
        this.FIELD = FIELD;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getNULL_() {
        return NULL_;
    }

    public void setNULL_(String NULL_) {
        this.NULL_ = NULL_;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }
}
