// * This program implements the bounded buffer using Java synchronization.

public class BoundedBuffer implements Buffer {
	private static final int BUFFER_SIZE = 5;

	private int count; // number of items in the buffer

	private int in; // points to the next free position in the buffer

	private int out; // points to the next full position in the buffer

	private Object[] buffer;

	public BoundedBuffer() {
		count = 0;
		in = 0;
		out = 0;

		buffer = new Object[BUFFER_SIZE];
	}

	public synchronized void insert(Object item) {
		while (count == BUFFER_SIZE) {
			try { wait();} 
			catch (InterruptedException e) {}
		}
		++count;
		buffer[in] = item;
		in = (in + 1) % BUFFER_SIZE;

		System.out.println("Producer inserted " + item + " Buffer Size = "+ count);
		notify();
	}

	public synchronized Object remove() {
		Object item;

		while (count == 0) {
			try{ wait();} 
			catch(InterruptedException e){}
		}
		--count;
		item = buffer[out];
		out = (out + 1) % BUFFER_SIZE;

		System.out.println("Consumer removed " + item + " Buffer Size = "+ count);

		notify();
		return item;
	}
}
