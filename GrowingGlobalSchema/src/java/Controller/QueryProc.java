/**
 *
 * @author Mikayil
 */
package Controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;
import java.util.regex.*;

@ManagedBean(name = "query_proc")
@SessionScoped
public class QueryProc implements Serializable {

    // ^(?=.*\\bSELECT\\b)(?=.*\\bFROM\\b).*$ - might need later
    private  final String REGEX1 = "SELECT(.*)FROM";
    private  final String REGEX2 = "FROM(.*)";
    //private  final String INPUT  = "SELECT A FROM B";

    
    
    public void Input(String input)
    {
        TableGen tb = new TableGen();
        
        tb.setTableName(procesSelect(input));
        tb.setColName(procesFrom(input));   
        tb.createTable();
    }
    /*
        this method processes the part of the input between the Strings SELECT and FROM
    */
    private  List procesSelect(String input)
    {
        Pattern p = Pattern.compile(REGEX1);
        Matcher m = p.matcher(input); // get a matcher object

        List<String> matchS = new ArrayList<>();

        while (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                matchS.add(m.group(i));
            }
        }
        
        for(String a : matchS){
            System.out.println("S: "+a);
        }
        
        return matchS;

    }
    
    /*
        this method processes the part of the input that starts after the Strind FROM
    */
    private  List procesFrom(String input)
    {
        Pattern p = Pattern.compile(REGEX2);
        Matcher m = p.matcher(input); // get a matcher object

        List<String> matchF = new ArrayList<>();

        while (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                matchF.add(m.group(i));
            }
        }
        
        for(String a : matchF){
            System.out.println("F: "+a);
        }
        
        return matchF;

    }

    public static void main(String[] args) {
       
        /*List a = Input(INPUT);
        for (int i = 0; i < a.size(); i++) {
            System.out.println("a: " + i + ")  " + a.get(i));

        }*/
    }
}
