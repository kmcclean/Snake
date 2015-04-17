import java.util.Timer;

/**
 * Created by Kevin on 4/16/2015.
 */
public class Clock {
    private Timer timer;

    public Clock(){
        this.timer = new Timer();
    }

    public void shutOffClock(){
        this.timer.cancel();
    }

    public void runfixedRate(GameClock clockTick, long clockInterval){
        this.timer.scheduleAtFixedRate(clockTick, 0, clockInterval);
    }

}
