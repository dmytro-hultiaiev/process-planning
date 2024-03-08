public class Process {
    private int timeAppeared;
    private int timeNeeded;
    private int timeStart;
    private int timeEnd;
    private int timeWait;
    private int timeUsed;
    private int timeLast;

    public Process(int time_appeared, int time_needed) {
        this.timeAppeared = time_appeared;
        this.timeNeeded = time_needed;
        this.timeStart = -1;
        this.timeEnd = -1;
        this.timeWait = 0;
        this.timeUsed = 0;
        this.timeLast = time_appeared;
    }
    public int getOverallTime(){
        return timeUsed + timeWait;
    }
    public boolean isFinished(){
        return timeEnd != -1;
    }
    public int execute(int currentQuant, int quant){
        if(timeStart == -1){
            timeStart = currentQuant;
        }

        timeWait += currentQuant - timeLast;
        timeLast = currentQuant;
        timeUsed += quant;

        if(timeNeeded <= timeUsed){
            int unused = timeUsed - timeNeeded;
            timeEnd = currentQuant + quant - unused;
            timeLast += quant - unused;
            return unused;
        }

        timeLast += quant;
        return 0;
    }
    public int getTimeAppeared() {
        return timeAppeared;
    }
    public void setTimeAppeared(int timeAppeared) {
        this.timeAppeared = timeAppeared;
    }
    public int getTimeNeeded() {
        return timeNeeded;
    }
    public void setTimeNeeded(int timeNeeded) {
        this.timeNeeded = timeNeeded;
    }
    public int getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }
    public int getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }
    public int getTimeWait() {
        return timeWait;
    }
    public void setTimeWait(int timeWait) {
        this.timeWait = timeWait;
    }
    public int getTimeUsed() {
        return timeUsed;
    }
    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }
    public int getTimeLast() {
        return timeLast;
    }
    public void setTimeLast(int timeLast) {
        this.timeLast = timeLast;
    }
}
