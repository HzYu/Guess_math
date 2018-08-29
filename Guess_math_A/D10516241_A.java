import java.io.*;
import java.util.*;

public class D10516241_A
{
	public static void main (String args[])
	{
		int maxsize = 1; //判斷Queue是否有數字	
		Queue<Integer> XY_queue = new LinkedList<Integer>(); //X_Play儲存答案 和 猜對方的亂數
		X_player_c X_player = new X_player_c(XY_queue, maxsize);	//負責回答結果 和 設亂數
		Y_player_c Y_player = new Y_player_c(XY_queue, maxsize);	//負責回答結果 和 設亂數
		
		filter_c filter = new filter_c();	//建立物件取得不重複的陣列
		
		X_player.start();
		Y_player.start();
	
	//	filter.Not_repeating();
	}
}

class filter_c  //篩選
{
	static 	int A=0;
	static 	int B=0;
    static int [] num = new int[9876]; //設4位數為陣列
    int ans=0;
	public void Not_repeating(int [] A_num)//把有重複的數字 設值為1 ， 不重複的值 設為0
	{
		for(int k=1023 ; k<num.length ; k++)
		{
			int [] a = new int[4];
			a=split(k);

			for(int j=0 ; j<=a.length-2 ; j++)//重複為1
			{
				for(int i=j+1 ; i<=a.length-1 ; i++)
				{
					if(a[j]==a[i])
					{
						A_num[k]=1;
						break;
					}
				}
			}
		}
	
		/*
		//測試是否有把重複的值變為1
		for(int h=1023 ; h<4000 ; h++)
		{
			System.out.println("num["+h+"]="+num[h]);
		}
		*/
	}
	
	//XY玩家答案的亂數
	public int rand()
	{
		int rand;
		Not_repeating(num); //篩選出重複的數字
		do
		{
			rand = (int)(Math.random()*8853+1023);	   
		}
		while(num[rand]==1);
		return rand;
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
	
	//判斷幾A幾B
	public void AB(int Ans , int Guess)
	{
	    this.A=0;//清空AB
		this.B=0;
		int [] Ans_split = new int [4];//存分解答案的數字
		int [] Guess_split = new int [4];//存分解猜的數字
		
		Ans_split=split(Ans); //分解答案的數字
		Guess_split=split(Guess); //分解猜的數字
		
		for(int j=0 ; j<4 ;j++) //判斷幾A幾B
		{
			for(int i=0 ; i<4 ;i++)
			{
				if(Ans_split[j] == Guess_split[i] )
				{
					if(i==j)
					{
						A++;
					}
					else
					{
						B++;
					}			
				}
			}
		}
		System.out.println("A="+A +"B="+B);
	}
	
	
	//根據幾A幾B 刪除不可能的數字
	public void delete_math(int XY_val ,int [] XY_num ,int A ,int B)
	{
		int [] XY_val_split  = new int [4]; //儲存 XY猜的數
		XY_val_split = split(XY_val);//分割XY猜的數	
		
			for(int k=1023 ; k<XY_num.length ; k++)
			{
				if(XY_num[k]!=1) //如果數字是不重複的
				{
					int num_a=0 , num_b=0;//紀錄 不重複的數和猜數字答案 幾A幾B
					
					int [] num_split = new int [4]; //分割不重複的數
    				num_split=split(k);//分割不重複的數
    						
    				for(int i=0;i<=3;i++)
					{
						for(int j=0;j<=3;j++)
						{
							if(XY_val_split[i]==num_split[j]) //如果 不重複的數和猜數字答案 裡面數字符合 
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
						XY_num[k] = 1;
					}
				}
			}
			
			//測試
			/*
			for(int h=1023 ; h<3000 ; h++)
			{
				System.out.println("num["+h+"]="+XY_num[h]);
			}
			*/
		//System.out.println(num.length);//猜數字的答案
	}
	
	
}

class X_player_c extends Thread  //X玩家
{
	filter_c filter = new filter_c();	//建立物件取得不重複的陣列
	Queue <Integer> XY_queue;// 存放 猜的數字
    static int [] Y_num = new int [9876];//刪掉Y不可能的數字
	int maxsize=0;
	int X_ans=0;
	int X_guess=0;
	int Y_guess=0;
	int c = 0; //計數 第一次建立XY玩家的答案 第二次互猜
	int four_A=0; //接收4A;
	
	public X_player_c(Queue<Integer> XY_queue , int maxsize) //建構元 存預設值
	{
		this.XY_queue = XY_queue;
		this.maxsize = maxsize;
	}
	
	@Override
	public void run()
	{
		X_player();
		System.exit(0); //強迫離開
	}
	
	public void X_player()
	{
		while(true)//this.A !=4
		{
			synchronized (XY_queue)//同步 讓執行緒不會互相執行而碰撞
			{	
				this.c++;  //計數 第一次建立XY玩家的答案 第二次互猜		
				while(XY_queue.size() == maxsize && this.c ==2)
				{
					try
					{
						System.out.println("等待Y玩家");
						XY_queue.wait();
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
						
				if(this.c == 1) //計數 第一次建立XY玩家的答案 第二次互猜
				{	
					filter.Not_repeating(Y_num);//篩選出Y_num不重複的數
					this.X_ans=filter.rand(); //X玩家取的不重複的答案		
					XY_queue.add(this.X_ans); //放入X_queue第一格
					System.out.println("X玩家的答案是："+this.X_ans);
					XY_queue.notifyAll();
				}
				else //第二次 玩家互猜
				{							
					//System.out.println("X_GUESS="+this.X_guess+",a"+filter.A + "B="+filter.B);
					//清除重複的數字
					
					if(this.c >2)
					{
						filter.delete_math(this.X_guess , Y_num , filter.A , filter.B);	
						//測試有無刪除不符合的數字
						/*	
						for(int h=1023 ; h<Y_num.length ; h++)
						{
							if(Y_num[h]==0)
							{
								System.out.println("num["+h+"]="+Y_num[h]);
							}
						}
						*/				
					}
															
					if(XY_queue.size() == 2) //如果Queue裡面有兩個數的話 刪掉第一個
					{
						XY_queue.poll(); //刪除第一個的數字	
						this.Y_guess = XY_queue.element();									
						filter.AB(this.X_ans,this.Y_guess);  //Y回答幾A幾B
						System.out.println("-------------------------------");
						XY_queue.remove();
					}
					if(filter.A != 4)
					{
						//X玩家猜測
						this.X_guess=0;//先清空儲存猜數字的變數
						do	//選出陣列不重複的數	 存到X_guess
						{
							this.X_guess = (int)(Math.random()*8853+1023);	   
						}
						while(Y_num[this.X_guess]==1);
						
						XY_queue.add(this.X_guess); //放入X_queue第一格
										
						XY_queue.notifyAll();//要先喚醒Y玩家
						
						//確認 queue 裡面有數字
						while(XY_queue.size() == maxsize)  //從這邊開始 如果我Queue已經有個數字
						{
							try
							{
								System.out.println("X玩家");
								System.out.println("X玩家猜：" + this.X_guess);
								System.out.println("等待Y玩家回答結果");
								XY_queue.wait();
							}
							catch (Exception x){}		
						}
					}
					else
					{
						System.out.println("-------------------------------");
						System.out.println("Y玩家獲勝");
						break;
					}	
				}															
			}
		}
	}
}

class Y_player_c extends Thread  //Y玩家
{
	filter_c filter = new filter_c();	//建立物件取得不重複的陣列
	Queue <Integer> XY_queue;// 存放 猜的數字
	int [] X_num = new int [9876];//刪掉X不可能的數字
	int maxsize=0;
//	int X_ans=0;  //取得X的答案
	int Y_ans=0;
	int X_guess=0;
	int Y_guess=0;
	int c =0; //計數 第一次建立XY玩家的答案 第二次互猜
	int four_A=0; //接收4A;
	int A=0;
	int B=0;
	
	public Y_player_c(Queue<Integer> XY_queue , int maxsize) //建構元 存預設值
	{
		this.XY_queue = XY_queue;
		this.maxsize = maxsize;
	}
	
	@Override
	public void run()
	{
		Y_player();
		System.exit(0); //強迫離開
	}
	
	public void Y_player()
	{
		while(true)//this.A !=4
		{
			synchronized (XY_queue)//同步 讓執行緒不會互相執行而碰撞
			{
				this.c++;//計數 第一次建立XY玩家的答案 第二次互猜
	
				
				while(XY_queue.isEmpty() && this.c ==2)
				{
					try
					{
						System.out.println("-------------------------------");				
						System.out.println("等待X玩家猜答案");
						System.out.println();
						XY_queue.wait();
					
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
				
				if(this.c == 1) //計數 第一次建立XY玩家的答案 第二次互猜
				{	
					filter.Not_repeating(X_num);//篩選出Y_num不重複的數
				//	this.X_ans = XY_queue.element(); //取得X的答案
					this.Y_ans=filter.rand(); //X玩家取的不重複的答案		
					System.out.println("Y玩家的答案是："+this.Y_ans);
					XY_queue.remove();
					XY_queue.notifyAll();
				}	
				else//第二次開始 玩家互猜
				{				
					//清除重複的數字
					if(this.c >2)
					{							
						filter.delete_math(this.Y_guess , X_num ,filter.A , filter.B);
					}
					
					this.Y_guess =0 ;//先清空儲存猜數字的變數
					this.X_guess =XY_queue.element(); //取得X玩家猜的亂數
					
					filter.AB(this.Y_ans , this.X_guess); //跟X玩家回答幾A幾B
					
					if(filter.A != 4)
					{
						do	//選出陣列不重複的數	 存到X_guess
						{
							this.Y_guess = (int)(Math.random()*8853+1023);	
						}
						while(X_num[this.Y_guess]==1);
						
						XY_queue.add(this.Y_guess); //放入X_queue第二格
						
						//XY_queue.poll(); //刪除第一個的數字
						
						System.out.println("-------------------------------");
						System.out.println("Y玩家");
						System.out.println("Y玩家猜：" + this.Y_guess);	
						XY_queue.notifyAll();	
						
						//確認 queue 裡面有數字
						while(XY_queue.size() == 2)
						{
							try
							{
								System.out.println("等待X玩家回答結果");
								XY_queue.wait();
							}
							catch (Exception x){}		
						}
						
						try
						{
							Thread.sleep(1000);
						}
						catch(Exception x){}					
					}
					else
					{
							System.out.println("-------------------------------");
							System.out.println("X玩家獲勝");
							break;
					}													
				}	
			}	
		}
	}
}


