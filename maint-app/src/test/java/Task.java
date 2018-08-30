

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task {

	//

	public static void main(String[] args) throws InterruptedException {
		/*OrderQueue queue = new OrderQueue();

		OrderAccept a = new OrderAccept(1L, 100L,false);
		queue.produce(a);
		OrderAccept b = new OrderAccept(2L, 100L,false);
		queue.produce(b);

		ExecutorService service = Executors.newCachedThreadPool();

		class Consumer implements Runnable {

			public void run() {

				try {

					while (true) {
						// 消费
						System.out.println("消费者准备消费: " + System.currentTimeMillis());
						OrderAccept oa = queue.consume();
						System.out.println("消费者消费完毕: " + oa);
					}

				} catch (InterruptedException ex) {

				}
			}

		}

		Consumer consumer = new Consumer();

		service.submit(consumer);
		OrderAccept c = new OrderAccept(3L, 100L,false);
		queue.produce(c);
		Thread.sleep(3000L);
		OrderAccept d = new OrderAccept(4L, 100L,false);
		queue.produce(d);*/
	}
}
