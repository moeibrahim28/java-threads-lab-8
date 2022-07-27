import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args){
        Executor executor = Executors.newSingleThreadExecutor();
        List<Future> futureList = new ArrayList<>();
        for(int i=0; i<6; i++){
            Callable<Integer> integerCallable= new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Random random = new Random();
                    Integer num = random.nextInt();
                    return num;
                }

            } ;
            futureList.add(((ExecutorService) executor).submit(integerCallable));
//

        }
        futureList.get(3).cancel(true);
        futureList.get(4).cancel(true);

        System.out.println(futureList);
        System.out.println(countFinishedFutures(futureList));
    }
    public static int countFinishedFutures(List<Future> futures) {
        return (int) futures.stream().filter(obj -> !obj.isCancelled()).filter(nonCancelled -> nonCancelled.isDone()).count();
    }
}