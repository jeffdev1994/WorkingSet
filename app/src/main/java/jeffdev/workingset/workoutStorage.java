package jeffdev.workingset;

import java.io.Serializable;

/**
 * Created by Jeff on 2015-03-14.
 */
public class workoutStorage implements Serializable {

    public String date;
    public String name;

    public workoutStorage(String name,String date){
        this.date = date;
        this.name = name;
    }
}
