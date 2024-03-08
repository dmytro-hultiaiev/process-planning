import java.util.ArrayList;

public class Main {
    private static final ArrayList<Process> interactive = new ArrayList<>();
    private static final ArrayList<Process> background = new ArrayList<>();
    private static int i = 0;
    private static int b = 0;
    private static double avgWait = 0.0;
    private static double avgOverall = 0.0;

    public static void print(){
        System.out.printf("| %-15s | %-20s | %-17s | %-17s | %-17s | %-17s | %-17s | \n", "Type", "Appearence Time", "Time Needed", "Start Time", "End Time", "Wait Time", "Execution Time");

        for(Process process : interactive){
            System.out.printf("| %-15s | %-20d | %-17d | %-17d | %-17d | %-17d | %-17d | \n", "Interactive", process.getTimeAppeared(), process.getTimeNeeded(), process.getTimeStart(), process.getTimeEnd(), process.getTimeWait(), process.getOverallTime());
        }
        for(Process process : background){
            System.out.printf("| %-15s | %-20d | %-17d | %-17d | %-17d | %-17d | %-17d | \n", "Background", process.getTimeAppeared(), process.getTimeNeeded(), process.getTimeStart(), process.getTimeEnd(), process.getTimeWait(), process.getOverallTime());
        }

        System.out.println("\nAverage waiting time: " + avgWait);
        System.out.println("Average overall time: " + avgOverall);
    }

    public static void main(String[] args) {
        interactive.add(new Process(3,20));
        interactive.add(new Process(18,25));
        background.add(new Process(0,54));
        background.add(new Process(2,32));

        int currentQuant = Math.min(interactive.get(0).getTimeAppeared(), background.get(0).getTimeAppeared());
        int interactiveQuants = 0;
        int backgroundQuants = 0;
        int quant = 5;
        double ratio = 0.8;
        boolean running = true;

        while (running) {
            if((interactiveQuants == 0) ||
                    ((double) interactiveQuants / (interactiveQuants + backgroundQuants) < ratio) ||
                    (b >= background.size())){
                boolean found = false;

                for (int j = i + 1; j < interactive.size(); j++) {
                    if (!interactive.get(j).isFinished() && interactive.get(j).getTimeAppeared() <= currentQuant){
                        found = true;
                        i = j;
                        break;
                    }
                }
                if (!found) {
                    for (int j = 0; j < Math.min(i + 1, interactive.size()); j++) {
                        if ((!interactive.get(j).isFinished()) && (interactive.get(j).getTimeAppeared() <= currentQuant)) {
                            found = true;
                            i = j;
                            break;
                        }
                    }
                }
                if(found){
                    int unused = interactive.get(i).execute(currentQuant, quant);
                    interactiveQuants += quant - unused;
                    currentQuant += quant - unused;
                    continue;
                }
            }
            if(b < background.size() && (currentQuant >= background.get(b).getTimeAppeared())){
                while(!background.get(b).isFinished()){
                    int unused = background.get(b).execute(currentQuant, quant);
                    backgroundQuants += quant - unused;
                    currentQuant += quant - unused;
                }
                b++;
                continue;
            }
            if(b >= background.size()){
                boolean finished = true;
                for(Process process : interactive){
                    if(!process.isFinished()){
                        finished = false;
                        break;
                    }
                }
                if (finished) {
                    running = false;
                    break;
                }
            }
        }

        for (Process process : interactive) {
            avgWait += process.getTimeWait();
            avgOverall += process.getOverallTime();
        }
        for (Process process : background) {
            avgWait += process.getTimeWait();
            avgOverall += process.getOverallTime();
        }

        int totalProcesses = interactive.size() + background.size();

        if (totalProcesses > 0) {
            avgWait /= totalProcesses;
            avgOverall /= totalProcesses;
        } else {
            avgWait = Double.POSITIVE_INFINITY;
            avgOverall = Double.POSITIVE_INFINITY;
        }

        print();
    }
}