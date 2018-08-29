import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


class Product extends Thread{
Queue<Integer> queue;//���C�w�q�A�s��ƾ�
Queue<Integer> queue2;
 int maxSize=0,maxSize2=0;//�e�q�n��

public Product(Queue<Integer> queue, int maxSize, Queue<Integer> queue2, int maxSize2,String name) {//�Ыؽu�{��H�c�y���
 super(name);
 this.queue=queue;
  this.queue2=queue2;
 this.maxSize=maxSize;
 this.maxSize2=maxSize2;
}
//=======
@Override
 public void run() {
  produce1();  
 }
//======= 
 public void produce1()
 {
 	while(true){
   synchronized(queue){
    while(queue.size()==maxSize){
     try {
      System.out.println("G full");
      queue.wait();
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
    Random random=new Random();
    int a=random.nextInt(100);
    try{
    	Thread.sleep(1000);
    }
    catch (Exception x){
    }
    System.out.println(this.getName()+ " produces==> "+a);
    queue.add(a);
    queue.notifyAll();//���C���F�ɳq�����O�̽u�{�}�l����
   }
  }
 }
 //=======
 public void comsume2()
 {
 	while(true){
   synchronized (queue2) {
    while(queue2.isEmpty()){
     try {
      System.out.println("CCCCC  queue  EMPTY");
      queue2.wait();
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
    try{
    	Thread.sleep(1000);
    }
    catch (Exception x){
    }
    System.out.println(this.getName()+ " gets ==> "+queue2.remove());
    queue2.notifyAll();
   }
  }
 }
}


class Customer extends Thread{
 Queue<Integer> queue;//���C�w�q�A�s��ƾ�
Queue<Integer> queue2;
 int maxSize=0,maxSize2=0;//�e�q�n��
 public Customer(Queue<Integer> queue, int maxSize, Queue<Integer> queue2,int maxSize2,String name) {//�Ыؽu�{��H�c�y���
  super(name);
  this.queue=queue;
  this.queue2=queue2;
  this.maxSize=maxSize;
  this.maxSize2=maxSize2;
 }
 //=====
 @Override
 public void run() {
  comsume1();
 }
 //====
 public void comsume1()
 {
 	while(true){
   synchronized (queue) {
    while(queue.isEmpty()){
     try {
      System.out.println("EMPTY ==> Comsumer waiting for producer");
      queue.wait();
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
    try{
    	Thread.sleep(1000);
    }
    catch (Exception x){
    }
    System.out.println(this.getName()+ " getsetgfsdg ==> "+queue.remove());
    queue.notifyAll();
   }
  }
 }
 //======= 
 public void produce2()
 {
 	while(true){
   synchronized(queue2){
    while(queue2.size()==maxSize){
     try {
      System.out.println("Comsumer WAITs for producer getting  DATA");
      queue2.wait();
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
    Random random=new Random();
    int a=random.nextInt(100);
    try{
    	Thread.sleep(1000);
    }
    catch (Exception x){
    }
    System.out.println(this.getName()+ " produces  ==>  "+a);
    queue2.add(a);
    queue.notifyAll();//���C���F�ɳq�����O�̽u�{�}�l����
   }
  }
 }
//====== 
}
 class ProductOrCustomer {
public static void main(String[] args) {
 //�Ыؽu�{��H
 System.out.println("start-----");
 Queue<Integer> queue=new LinkedList<Integer>();
 Queue<Integer> queue2=new LinkedList<Integer>();
 int maxSize=1;
 int maxSize2=1;
 //int maxSize=5;
 Product productX=new Product(queue, maxSize,queue2, maxSize2, "product XX");
 
 Customer customerA=new Customer(queue, maxSize,queue2, maxSize2, "customer AA");
 
 productX.start();
 
 customerA.start();
 
 System.out.println("end-----");
}
}