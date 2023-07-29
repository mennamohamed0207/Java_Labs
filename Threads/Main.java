/***********************************Requirement description**********************************************/
// •	There is a bookstore that has different branches, each of them sell a number of books, these books are supplied by the Supplier. 
// •	A bookstore shouldn’t sell a book when the number of books is 0, it should block and notify the Supplier to provide more
// •	A supplier shouldn’t provide a book when the max count of books is reached, when it provides a book, it should tell stores that there are more books available.
// •	Modify the requirement_student.java code to reflect this behaviour.
// •	All threads should execute in parallel, you cannot allow a thread to stop another thread (should guarantee progress)
// •	Follow the 8 TODOs in the Code

import static java.lang.Thread.sleep;

/***********************************************************************************************/


class Threadsrequirement {
    public static void main(String... args) throws InterruptedException {
        BookStock b = new BookStock (10);

        //TODO-1: Create 4 threads,
        // 1 for Supplier
        // 3 for StoreBranches and Name them as the following: Giza branch, Cairo branch, Daqahley branch
        Thread Supp=new Thread(new Supplier(b));

        Thread Giza_branch =new Thread (new StoreBranch(b));
        Thread Cairo_branch =new Thread (new StoreBranch(b));
        Thread Daqahley_branch =new Thread (new StoreBranch(b));
        //System.out.println(b.getCount());
        // 3 for StoreBranches and Name them as the following: Giza branch, Cairo branch, Daqahley branch
        Supp.setName("Supplier");
        Giza_branch.setName("Giza_branch");
        Cairo_branch.setName("Cairo_branch");
        Daqahley_branch.setName("Daqahley_branch");
        //TODO-2: Run the 4 threads
        Supp.start();
        Giza_branch.start();
        Cairo_branch.start();
        Daqahley_branch.start();


    }
}

class BookStock {
    private int book_count=15;
    private int max;
    public BookStock  (int max) {
        this.max = max;
    }
    //this an extra function
    public int getmax()
    {
        return max;
    }
    public synchronized void produce(){

        book_count++;

    }

    public synchronized   void consume () {

        book_count--;


    }

    public int getCount () {
        return book_count;
    }
}

//TODO-3: should it implement or extend anything?
class Supplier implements Runnable{
    public void run()
    {
        doWork();
    }
    private BookStock b;

    public Supplier (BookStock b) {
        this.b = b;
    }
    //TODO-4:is a function missing ?
    public  void doWork () {

            while (true) {
                //TODO-5: how to make it stop producing when it reaches max, without adding extra sleeps or busy waiting ?
                synchronized (this.b) {

                    while (b.getCount() >= b.getmax()) {
                        try {
                            b.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    b.produce();

                    System.out.println(Thread.currentThread().getName() + " provided a book, total " + b.getCount());

                    b.notifyAll();
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "is awaken");
                }

            }
        }


}

//TODO-6: should it implement or extend anything?
class StoreBranch implements Runnable{
    public void run()
    {
        doWork();
    }
    private BookStock b;

    public StoreBranch (BookStock b) {
        this.b = b;
    }

    //TODO-7: is a function missing ?
    public void doWork () {

            while (true) {
                //TODO-8: how to make it stop consuming when the store is empty, without adding extra sleeps or busy waiting ?
                synchronized (this.b) {
                    while (b.getCount() == 0) {
                        try {
                            b.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    b.consume();
                    System.out.println(Thread.currentThread().getName() + " sold a book "+"and the count of book is "+b.getCount());

                    b.notifyAll();
                }

                try {
                    sleep(5000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //notifyAll();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "is awaken");
                }

            }
        }

}