package jeffdev.workingset;

import java.io.Serializable;

/**
 * Created by Jeff on 2015-02-14.
 */
public class exerciseStorage implements Serializable {
    public String name;
    public String description;

    public exerciseStorage(String name, String description){
        this.name = name;
        this.description = description;
    }
    //supposed to be empty
    public exerciseStorage(){

    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
