package apps.chandan.com.proximitysensor1.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sitir on 19-01-2017.
 */

public class Member {

    private String displayName;
    private String email;
    Map<Member,Watch> watchMap = new HashMap<Member,Watch>();

    public Member(){}

    public Member(String displayName){this.displayName=displayName;}
    public Member(String displayName,String email){this.displayName=displayName;this.email=email;}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<Member, Watch> getWatchMap() {
        return watchMap;
    }

    public void setWatchMap(Map<Member, Watch> watchMap) {
        this.watchMap = watchMap;
    }

    @Override
    public String toString() {
        return  displayName +
                (email==null?"":", email='" + email + '\'' );
    }

    @Override
    public int hashCode() {
        //TODO change the imple
        return    displayName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //TODO change the imple
        if(!(obj instanceof  Member))
            return false;
        Member that = (Member) obj;
        return displayName.equals(that.displayName);
    }
}
