package com.b5m.service.www.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b5m.common.env.GlobalInfo;

public abstract class AbstractConsumerService<T> implements InitializingBean, ConsumerService<T> {

	protected LinkedBlockingQueue<MessageWrap<T>> blockingQueue;

	public abstract Integer getThreadPoolSize();

	public abstract Integer getBlockingQueueSize();

	public abstract void dealWith(MessageWrap<T> messageWrap);
	
	@Autowired
	@Qualifier("searchThreadPool")
	public ThreadPoolExecutor searchThreadPool;
	
	@Override
	public void addMessage(T t, MessageOp op) throws InterruptedException {
		blockingQueue.put(MessageWrap.newInstance(op, t));
	}

	public void start() {
		blockingQueue = new LinkedBlockingQueue<MessageWrap<T>>(getBlockingQueueSize());
		Integer threadPoolSize = getThreadPoolSize();
//		ExecutorService commentExecutorService = Executors.newFixedThreadPool(threadPoolSize);
		for (int i = 0; i < threadPoolSize; i++) {
			searchThreadPool.submit(new Consumer());
		}
	}

	protected Integer getSize(String key) {
		Integer threadSize = GlobalInfo.getInt(key);
		return threadSize;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		start();
	}

	class Consumer implements Runnable{
		
		@Override
		public void run() {
			while(true){
				try {
					MessageWrap<T> messageWrap = blockingQueue.take();
					dealWith(messageWrap);
				} catch (InterruptedException e) {
				}
			}
		}

	}
}
