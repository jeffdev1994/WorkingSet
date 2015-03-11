package jeffdev.workingset;

import java.util.ArrayList;

/**
 * Created by Jeff on 2015-03-10.
 */
//may need to add more to this if i decide to allow for deleting, or updating or both.
public final class allSetsStorage {

    private static ArrayList<doesSetStorage> totalworkout = new ArrayList<doesSetStorage>();

    private allSetsStorage(){}

    public static void addset(doesSetStorage set){
        totalworkout.add(set);
    }

    public static ArrayList<doesSetStorage> getlist(){
        return totalworkout;
    }


}
