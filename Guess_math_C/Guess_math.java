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
		
		Not_repeating(a,num);//������
		 
		System.out.print("��J�A�Q���Ʀr�G");
		ans=sc.nextInt();
		 
		 do 
		 {	 	 
			rand=rands(rand,num);//�ü�		 
		
			
			System.out.println("\n�q���q���ơG"+rand);
			System.out.print("A�G");
			x=sc.nextInt();
			System.out.print("B�G");
			y=sc.nextInt();
  
 			result=guess(x,y,rand,ans,num);
 			num[rand]= 1; //�R���ۤv����
 			print(num,x);	
		}while(result!=4);		     //�p�G4A���ܸ��X�j��   	
	}
		
	public static void Not_repeating(int a[] , int num[])//������
	{
		for(int k=1023 ; k<num.length ; k++)
		{
			num[k]=0;
			a[0]= k/1000;
			a[1]= (k-a[0]*1000)/100;
			a[2]= (k-(a[0]*1000+a[1]*100))/10;
			a[3] = k%10;	
			
			for(int j=0 ; j<=a.length-2 ; j++)//���Ƭ�1
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
	
	public static void print(int num[],int x)//��X
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
					System.out.printf("�Ʀr��J���~");	
			}
		}	
		else
		{
			System.out.println("���߲q��F");
		}
			
	}
			
	
	
	public static int rands( int rand,int num[])//�ü�
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
		int guess_total = x+y;  //A+B�q�諸�Ʀr���X��
		int [] rand_g = new int [4];
		int [] num_g = new int [4];
		int [] ans_g = new int [4]; //�Q�o��
		int [] ans_A = new int [4]; //A�۹諸��
		int o=0;
		int w=0; //���o�@�˪��� 
		int c=0; //�p��
		//��ѧA�Q����
		ans_g[0]= ans/1000;
		ans_g[1]= (ans-ans_g[0]*1000)/100;
		ans_g[2]= (ans-(ans_g[0]*1000+ans_g[1]*100))/10;
		ans_g[3] = ans%10;
			
		//��Ѷüƪ���
		rand_g[0]= rand/1000;
		rand_g[1]= (rand-rand_g[0]*1000)/100;
		rand_g[2]= (rand-(rand_g[0]*1000+rand_g[1]*100))/10;
		rand_g[3] = rand%10;
		
		//��ѥi�઺��
		for(int j=1023 ; j<num.length ; j++)
		{	
			if(num[j]==0)
			{
				num_g[0]= j/1000;
				num_g[1]= (j-num_g[0]*1000)/100;
				num_g[2]= (j-(num_g[0]*1000+num_g[1]*100))/10;
				num_g[3] = j%10;
				
			c=0;//�C���@�ӼƦr�Ac�p����0���s�p��
			
			for(int k=0 ; k<4 ;k++)//�q���Ʀr
			{
				for(int i=0 ; i<4 ;i++)//�C�ռƦr
				{
					if(rand_g[i] == num_g[k])//�P�_�C�ռƦr�M�q���Ʀr���S���ۦP�A�p�G��c�[1
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
			
			
			if(c!=guess_total)  //�p�G�A��A+B�`�M�Mc���ŦX���ܡA��Ʀr�R��
			{
				num[j]= 1;
			}
			
			if(y==4)  //�p�G�O4B���ܡA�R�����i�઺��
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