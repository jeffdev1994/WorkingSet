package jeffdev.workingset;

import java.util.ArrayList;

/**
 * Created by Jeff on 2015-03-10.
 */
//may need to add more to this if i decide to allow for deleting, or updating or both.
public final class allSetsStorage {

    private static ArrayList<doesSetStorage> totalworkout = new ArrayList<doesSetStorage>();

    private allSetsStorage(){}

    public static void deletesingle(String Ename, String weight, String reps){
        int weight2 = Integer.parseInt(weight);
        int reps2 = Integer.parseInt(reps);
        for(int i=0 ; i<totalworkout.size(); i++){
            //this will just delete the first one, if they have notes for a set, and want to delete that one, it wont
            //matter at this point
            if(totalworkout.get(i).Ename.equals(Ename) && totalworkout.get(i).weight == weight2 && totalworkout.get(i).reps == reps2 ){
                totalworkout.remove(i);
                return;
            }
        }

    }

    public static void addset(doesSetStorage set){
        totalworkout.add(set);
    }

    public static ArrayList<doesSetStorage> getlist(){
        return totalworkout;
    }

    public static void deleteall(){
        totalworkout.clear();
    }


}
