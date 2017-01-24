package apps.chandan.com.proximitysensor1.entities;

/**
 * Created by sitir on 19-01-2017.
 */

public class Watch {

    private Member source;
    private Member target;
    private boolean bidirectional;
    /**
     * refresh interval in seconds
     */
    private int refreshInterval=10;
    /**
     * distance measured in meters
     */
    private int distanceAllowed=10;
    private boolean tracking;
    private String statusMessage;

    public Watch(Member source, Member target){
        this.source=source;
        this.target=target;
    }

    public Member getSource() {
        return source;
    }

    public void setSource(Member source) {
        this.source = source;
    }

    public Member getTarget() {
        return target;
    }

    public void setTarget(Member target) {
        this.target = target;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public int getDistanceAllowed() {
        return distanceAllowed;
    }

    public void setDistanceAllowed(int distanceAllowed) {
        this.distanceAllowed = distanceAllowed;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    @Override
    public String toString() {
        return "Watch{" +
                "source=" + source +
                ", target=" + target +'}';
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int isInSafeZone(Member member){
        //TODO
        /**
         * calculate the allowed distance between 'this' and member objects and
         * return negative for not in safe zone, 0 for warning and positive for safezone.
         *
         */

        return 1;

    }
}
