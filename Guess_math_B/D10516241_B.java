import java.io.*;
import java.util.*;

class filter_c  //篩選
{
	int [] a = new int[4];
    static int [] num = new int[9876]; //設4位數為陣列
    
	int ans=0;//儲存猜數字答案(delete_math())
	
	public void Not_repeating()//把有重複的數字 設值為1 ， 不重複的值 設為0
	{
		for(int k=1023 ; k<num.length ; k++)
		{
			num[k]=0;
			a[0]= k/1000;
			a[1]= (k-a[0]*1000)/100;
			a[2]= (k-(a[0]*1000+a[1]*100))/10;
			a[3] = k%10;	
				
			for(int j=0 ; j<=a.length-2 ; j++)//重複為1
			{
				for(int i=j+1 ; i<=a.length-1 ; i++)
				{
					if(a[j]==a[i])
					{
						num[k]=1;
						break;
					}
				}
			}
		}
		
		//測試是否有把重複的值變為1
		/*
		for(int h=1023 ; h<4000 ; h++)
		{
			System.out.println("num["+h+"]="+num[h]);
		}
		*/
	}
	
	//將數字分割成陣列
	public int[] split(int val)	
    {
    	int [] a = new int[4];
    	a[0]=val/1000;
    	a[1]=(val-a[0]*1000)/100;
    	a[2]=(val-a[0]*1000-a[1]*100)/10;
    	a[3]=(val-a[0]*1000-a[1]*100-a[2]*10)/1;
    	return a;
    }
    
	//根據幾A幾B 刪除不可能的數字
	public void delete_math(int Guess_val ,int A ,int B)
	{
		int [] Guess_val_split  = new int [4]; //儲存 分割Guess亂數
		Guess_val_split = split(Guess_val);//分割分割Guess亂數	
			for(int k=1023 ; k<num.length ; k++)
			{
				if(num[k]!=1) //如果數字是不重複的
				{
					int num_a=0 , num_b=0;//紀錄 不重複的數和猜數字答案 幾A幾B
					int [] num_split = new int [4]; //分割不重複的數
    				num_split=split(k);//分割不重複的數
    						
    				for(int i=0;i<=3;i++)
					{
						for(int j=0;j<=3;j++)
						{
							if(Guess_val_split[i]==num_split[j]) //如果 不重複的數和猜數字答案 裡面數字符合 
							{
								if(i==j) //位置相同的話 a+1
								{
									num_a++;
									break;
								}
								num_b++; //位置不同 b+1
								break;
							}
						}
					}
					
					if(num_a == A && num_b == B)  //如果幾A幾B 與 答案的幾A幾B吻合的話
					{
						this.ans=k;
					}
					else
					{
						num[k] = 1;
					}
				}
			}
			
			//測試
			/*
			for(int h=1023 ; h<4000 ; h++)
			{
				System.out.println("num["+h+"]="+num[h]);
			}
			*/
		
		//System.out.println(num.length);//猜數字的答案
	}
}

class Answer_c extends Thread  //負責 設猜數字答案 和 回答結果
{
	filter_c filter = new filter_c();	//建立物件取得不重複的陣列
	int Guess_ans=0;//存放 猜數字的答案
	int Guess_val=0;//存Guess傳來亂數的值
	int A=0;
    int B=0;
	Queue <Integer> queue;//Queue存放Guess亂數
	int maxsize=0;
	
	public Answer_c(Queue<Integer> queue , int maxsize) //建構元 存預設值
	{
		this.queue = queue;
		this.maxsize = maxsize;
	}
	
	@Override
	public void run()
	{
			Answer();
			System.exit(0); //強迫離開
	}
	
	//設猜數字答案
	public void rand() 
	{
		int rand=0;
		filter.Not_repeating();//先篩選重複的數
		
		do
		{
			rand = (int)(Math.random()*8853+1023);	   
		}
		while(filter.num[rand]==1);
		
		this.Guess_ans = rand;
	}
	
	//回答結果
	public void Answer()
	{
		while(this.A !=4 )
		{
			synchronized (this.queue)//同步 讓執行緒不會互相執行而碰撞
			{
				while(queue.isEmpty())//如果queue是空值
				{
					try
					{
						System.out.println("等待Guess 猜答案");
						queue.wait();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				try
				{
					Thread.sleep(1000);
				}
				catch(Exception x){}
				this.Guess_val = queue.peek(); //存Guess傳來猜的值
				//System.out.println(this.Guess_val);
				AB();//顯示幾A幾B
				filter.delete_math(this.Guess_val,this.A ,this.B);//(篩選不符合的數字)	
				if(this.A != 4) //如果沒有4A的話 繼續喚醒執行續
				{
					queue.notifyAll();
				}
				else
				{
					//queue.notifyAll();
					System.out.println("Guess猜對答案為 :"+filter.ans);
				}
				
				queue.remove();
			}
		}
	}
	
	//判斷幾A幾B
	public void AB()
	{
		this.A=0;//清空AB
		this.B=0;
		int [] Guess_ans_split = new int [4];//存分解猜數字的答案
		int [] Guess_val_split = new int [4];//存分解Guess的數字
		Guess_ans_split=filter.split(this.Guess_ans); //分解猜數字的答案
		Guess_val_split=filter.split(this.Guess_val); //分解Guess的數字
		
		for(int j=0 ; j<4 ;j++) //判斷幾A幾B
		{
			for(int i=0 ; i<4 ;i++)
			{
				if(Guess_ans_split[j] == Guess_val_split[i] )
				{
					if(i==j)
					{
						this.A++;
					}
					else
					{
						this.B++;
					}			
				}
			}
		}
		System.out.println("A="+this.A +"B="+this.B);
		System.out.println();
	}
}


class Guess_c extends Thread   //負責猜數字
{
	int c=0;
	int rand =0;
	int maxsize=0;
	Queue <Integer> queue;//存放猜的亂數
	filter_c filter = new filter_c();	//建立物件取得不重複的陣列
	
	public Guess_c(Queue<Integer> queue , int maxsize)
	{
		this.queue = queue;
		this.maxsize = maxsize;
	}
	@Override
	public void run()
	{
		guess_rand();
	}
		
	public void guess_rand()//隨機猜不重複的亂數 猜答案
	{		
		while(true)
		{
			synchronized (this.queue)
			{
				while(queue.size() == maxsize)
				{
					try
					{
						System.out.println("等待Answer 回答結果");
						queue.wait();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				/*
				for(int h=1023 ; h<4000 ; h++)
				{
					System.out.println("num["+h+"]="+filter.num[h]);
				}
				*/
				do	//選出陣列不重複的數	 存到rand
				{
					rand = (int)(Math.random()*8853+1023);	   
				}
				while(filter.num[rand]==1);
				try
				{
					Thread.sleep(1000);
				}
				catch(Exception x){}
				System.out.println("Guess："+rand);
				queue.add(rand);
				queue.notifyAll();
			}	
		}		
	}	
}
public class D10516241_B
{
	public static void main (String args[])
	{
		int maxsize = 1; //判斷Queue是否有數字	
		Queue<Integer> queue = new LinkedList<Integer>(); //Queue儲存Guess猜的亂數
		Answer_c Answer = new Answer_c(queue , maxsize);	//負責回答結果 和 設亂數
		Guess_c  Guess  = new Guess_c (queue , maxsize);	//負責猜數字
		filter_c filter = new filter_c();	//建立物件取得不重複的陣列
		Answer.rand();//先設 猜數字答案
		filter.Not_repeating();
		System.out.println("要猜的數字答案："+Answer.Guess_ans);
		System.out.println("----------------------------");
		Guess.start();
		Answer.start();
		
	}
}

