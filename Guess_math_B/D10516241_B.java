import java.io.*;
import java.util.*;

class filter_c  //�z��
{
	int [] a = new int[4];
    static int [] num = new int[9876]; //�]4��Ƭ��}�C
    
	int ans=0;//�x�s�q�Ʀr����(delete_math())
	
	public void Not_repeating()//�⦳���ƪ��Ʀr �]�Ȭ�1 �A �����ƪ��� �]��0
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
		
		//���լO�_���⭫�ƪ����ܬ�1
		/*
		for(int h=1023 ; h<4000 ; h++)
		{
			System.out.println("num["+h+"]="+num[h]);
		}
		*/
	}
	
	//�N�Ʀr���Φ��}�C
	public int[] split(int val)	
    {
    	int [] a = new int[4];
    	a[0]=val/1000;
    	a[1]=(val-a[0]*1000)/100;
    	a[2]=(val-a[0]*1000-a[1]*100)/10;
    	a[3]=(val-a[0]*1000-a[1]*100-a[2]*10)/1;
    	return a;
    }
    
	//�ھڴXA�XB �R�����i�઺�Ʀr
	public void delete_math(int Guess_val ,int A ,int B)
	{
		int [] Guess_val_split  = new int [4]; //�x�s ����Guess�ü�
		Guess_val_split = split(Guess_val);//���Τ���Guess�ü�	
			for(int k=1023 ; k<num.length ; k++)
			{
				if(num[k]!=1) //�p�G�Ʀr�O�����ƪ�
				{
					int num_a=0 , num_b=0;//���� �����ƪ��ƩM�q�Ʀr���� �XA�XB
					int [] num_split = new int [4]; //���Τ����ƪ���
    				num_split=split(k);//���Τ����ƪ���
    						
    				for(int i=0;i<=3;i++)
					{
						for(int j=0;j<=3;j++)
						{
							if(Guess_val_split[i]==num_split[j]) //�p�G �����ƪ��ƩM�q�Ʀr���� �̭��Ʀr�ŦX 
							{
								if(i==j) //��m�ۦP���� a+1
								{
									num_a++;
									break;
								}
								num_b++; //��m���P b+1
								break;
							}
						}
					}
					
					if(num_a == A && num_b == B)  //�p�G�XA�XB �P ���ת��XA�XB�k�X����
					{
						this.ans=k;
					}
					else
					{
						num[k] = 1;
					}
				}
			}
			
			//����
			/*
			for(int h=1023 ; h<4000 ; h++)
			{
				System.out.println("num["+h+"]="+num[h]);
			}
			*/
		
		//System.out.println(num.length);//�q�Ʀr������
	}
}

class Answer_c extends Thread  //�t�d �]�q�Ʀr���� �M �^�����G
{
	filter_c filter = new filter_c();	//�إߪ�����o�����ƪ��}�C
	int Guess_ans=0;//�s�� �q�Ʀr������
	int Guess_val=0;//�sGuess�ǨӶüƪ���
	int A=0;
    int B=0;
	Queue <Integer> queue;//Queue�s��Guess�ü�
	int maxsize=0;
	
	public Answer_c(Queue<Integer> queue , int maxsize) //�غc�� �s�w�]��
	{
		this.queue = queue;
		this.maxsize = maxsize;
	}
	
	@Override
	public void run()
	{
			Answer();
			System.exit(0); //�j�����}
	}
	
	//�]�q�Ʀr����
	public void rand() 
	{
		int rand=0;
		filter.Not_repeating();//���z�ﭫ�ƪ���
		
		do
		{
			rand = (int)(Math.random()*8853+1023);	   
		}
		while(filter.num[rand]==1);
		
		this.Guess_ans = rand;
	}
	
	//�^�����G
	public void Answer()
	{
		while(this.A !=4 )
		{
			synchronized (this.queue)//�P�B ����������|���۰���ӸI��
			{
				while(queue.isEmpty())//�p�Gqueue�O�ŭ�
				{
					try
					{
						System.out.println("����Guess �q����");
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
				this.Guess_val = queue.peek(); //�sGuess�ǨӲq����
				//System.out.println(this.Guess_val);
				AB();//��ܴXA�XB
				filter.delete_math(this.Guess_val,this.A ,this.B);//(�z�藍�ŦX���Ʀr)	
				if(this.A != 4) //�p�G�S��4A���� �~����������
				{
					queue.notifyAll();
				}
				else
				{
					//queue.notifyAll();
					System.out.println("Guess�q�ﵪ�׬� :"+filter.ans);
				}
				
				queue.remove();
			}
		}
	}
	
	//�P�_�XA�XB
	public void AB()
	{
		this.A=0;//�M��AB
		this.B=0;
		int [] Guess_ans_split = new int [4];//�s���Ѳq�Ʀr������
		int [] Guess_val_split = new int [4];//�s����Guess���Ʀr
		Guess_ans_split=filter.split(this.Guess_ans); //���Ѳq�Ʀr������
		Guess_val_split=filter.split(this.Guess_val); //����Guess���Ʀr
		
		for(int j=0 ; j<4 ;j++) //�P�_�XA�XB
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


class Guess_c extends Thread   //�t�d�q�Ʀr
{
	int c=0;
	int rand =0;
	int maxsize=0;
	Queue <Integer> queue;//�s��q���ü�
	filter_c filter = new filter_c();	//�إߪ�����o�����ƪ��}�C
	
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
		
	public void guess_rand()//�H���q�����ƪ��ü� �q����
	{		
		while(true)
		{
			synchronized (this.queue)
			{
				while(queue.size() == maxsize)
				{
					try
					{
						System.out.println("����Answer �^�����G");
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
				do	//��X�}�C�����ƪ���	 �s��rand
				{
					rand = (int)(Math.random()*8853+1023);	   
				}
				while(filter.num[rand]==1);
				try
				{
					Thread.sleep(1000);
				}
				catch(Exception x){}
				System.out.println("Guess�G"+rand);
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
		int maxsize = 1; //�P�_Queue�O�_���Ʀr	
		Queue<Integer> queue = new LinkedList<Integer>(); //Queue�x�sGuess�q���ü�
		Answer_c Answer = new Answer_c(queue , maxsize);	//�t�d�^�����G �M �]�ü�
		Guess_c  Guess  = new Guess_c (queue , maxsize);	//�t�d�q�Ʀr
		filter_c filter = new filter_c();	//�إߪ�����o�����ƪ��}�C
		Answer.rand();//���] �q�Ʀr����
		filter.Not_repeating();
		System.out.println("�n�q���Ʀr���סG"+Answer.Guess_ans);
		System.out.println("----------------------------");
		Guess.start();
		Answer.start();
		
	}
}

