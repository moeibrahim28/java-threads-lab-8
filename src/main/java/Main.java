import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Executor executor = Executors.newSingleThreadExecutor();
        List<Future> futureList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Callable<Integer> integerCallable = () -> {
                Random random = new Random();
                Integer num = random.nextInt();
                return num;
            };
            futureList.add(((ExecutorService) executor).submit(integerCallable));
//

        }
        futureList.get(3).cancel(true);
        futureList.get(4).cancel(true);


        for (Future future : futureList) {
            if (!future.isCancelled()) {
                future.get();
            }
        }
        System.out.println(futureList);
        System.out.println(countFinishedFutures(futureList));
    }

    public static int countFinishedFutures(List<Future> futures) {
        return (int) futures.stream().filter(obj -> !obj.isCancelled()).filter(nonCancelled -> nonCancelled.isDone()).count();
    }
}