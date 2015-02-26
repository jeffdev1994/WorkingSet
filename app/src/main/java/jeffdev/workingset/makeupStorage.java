package jeffdev.workingset;

import java.io.Serializable;

/**
 * Created by Jeff on 2015-02-24.
 */
public class makeupStorage implements Serializable {

    public String Wname;
    public String Ename;

    public makeupStorage(String Ename, String Wname){
        this.Ename = Ename;
        this.Wname = Wname;
    }

    public makeupStorage(){

    }
}
