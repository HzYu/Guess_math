//		Scanner sc = new Scanner(System.in);
//		n=sc.nextInt();
//		System.out.print(n);
import java.util.*;
public class Guess_math
{
    public static void main (String args[])
	{
		int [] a = new int[4];
		int [] num = new int[9876];
		int rand=0,x=0,y=0 ,ans=0, result=0;
		Scanner sc= new Scanner(System.in);
		
		Not_repeating(a,num);//不重複
		 
		System.out.print("輸入你想的數字：");
		ans=sc.nextInt();
		 
		 do 
		 {	 	 
			rand=rands(rand,num);//亂數		 
		
			
			System.out.println("\n電腦猜的數："+rand);
			System.out.print("A：");
			x=sc.nextInt();
			System.out.print("B：");
			y=sc.nextInt();
  
 			result=guess(x,y,rand,ans,num);
 			num[rand]= 1; //刪除自己本身
 			print(num,x);	
		}while(result!=4);		     //如果4A的話跳出迴圈   	
	}
		
	public static void Not_repeating(int a[] , int num[])//不重複
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
	}
	
	public static void print(int num[],int x)//輸出
	{
		int c=0,d=0;
		if(x!=4)
		{
			for(int i=1023 ; i<num.length ; i++)
			{
				if(num[i]==0)
				{
					d++;
					c++;
					System.out.printf("%5d",i);	
				}	
				if(c==10)
				{
					System.out.println();
					c=0;
				}	
			}
			if(d==0)
			{
					System.out.printf("數字輸入錯誤");	
			}
		}	
		else
		{
			System.out.println("恭喜猜對了");
		}
			
	}
			
	
	
	public static int rands( int rand,int num[])//亂數
	{
		do
		{
			//rand = (int)(Math.random()*(9876-1023+1)+1023);
			rand = (int)(Math.random()*8853+1023);
		   
		}
		while(num[rand]==1);
		return rand;
	}
	
	public static int guess (int x ,int y,int rand,int ans,int num[])
	{
		int guess_total = x+y;  //A+B猜對的數字有幾個
		int [] rand_g = new int [4];
		int [] num_g = new int [4];
		int [] ans_g = new int [4]; //想得值
		int [] ans_A = new int [4]; //A相對的值
		int o=0;
		int w=0; //取得一樣的值 
		int c=0; //計數
		//拆解你想的值
		ans_g[0]= ans/1000;
		ans_g[1]= (ans-ans_g[0]*1000)/100;
		ans_g[2]= (ans-(ans_g[0]*1000+ans_g[1]*100))/10;
		ans_g[3] = ans%10;
			
		//拆解亂數的數
		rand_g[0]= rand/1000;
		rand_g[1]= (rand-rand_g[0]*1000)/100;
		rand_g[2]= (rand-(rand_g[0]*1000+rand_g[1]*100))/10;
		rand_g[3] = rand%10;
		
		//拆解可能的值
		for(int j=1023 ; j<num.length ; j++)
		{	
			if(num[j]==0)
			{
				num_g[0]= j/1000;
				num_g[1]= (j-num_g[0]*1000)/100;
				num_g[2]= (j-(num_g[0]*1000+num_g[1]*100))/10;
				num_g[3] = j%10;
				
			c=0;//每換一個數字，c計數變0重新計數
			
			for(int k=0 ; k<4 ;k++)//猜的數字
			{
				for(int i=0 ; i<4 ;i++)//每組數字
				{
					if(rand_g[i] == num_g[k])//判斷每組數字和猜的數字有沒有相同，如果有c加1
					{
						c++;
					}
										
					if(	ans_g[i] == rand_g[i])
					{
						w= ans_g[i];
							if(x>0)
							{
								o=0;
								for(int s=0 ; s<4 ;s++)
								{
								if(num_g[s] != w)
								{
									o++;			
									if(o == 4)
									{
										num[j]= 1;
									}
								}
								}
							}
					}			
					
				}
			}
			
			
			
			if(x==2 && y==2)
			{
				for(int i=0 ; i<4 ;i++)
				{
					if(	ans_g[i] == rand_g[i])
					{
						if(num_g[i] != ans_g[i])
						{
							num[j]= 1;
						}
					}
				}
			}
			
			
			if(c!=guess_total)  //如果你的A+B總和和c不符合的話，把數字刪掉
			{
				num[j]= 1;
			}
			
			if(y==4)  //如果是4B的話，刪掉不可能的數
			{
				for(int i=0 ; i<4 ;i++)
				{
					if (rand_g[i] == num_g[i])
					{
							num[j]= 1;
					}
				}
			}
			
			}
		}		
		return x;		
	}
	
}