import java.io.*;
import java.util.*;

public class D10516241_A
{
	public static void main (String args[])
	{
		int maxsize = 1; //�P�_Queue�O�_���Ʀr	
		Queue<Integer> XY_queue = new LinkedList<Integer>(); //X_Play�x�s���� �M �q��誺�ü�
		X_player_c X_player = new X_player_c(XY_queue, maxsize);	//�t�d�^�����G �M �]�ü�
		Y_player_c Y_player = new Y_player_c(XY_queue, maxsize);	//�t�d�^�����G �M �]�ü�
		
		filter_c filter = new filter_c();	//�إߪ�����o�����ƪ��}�C
		
		X_player.start();
		Y_player.start();
	
	//	filter.Not_repeating();
	}
}

class filter_c  //�z��
{
	static 	int A=0;
	static 	int B=0;
    static int [] num = new int[9876]; //�]4��Ƭ��}�C
    int ans=0;
	public void Not_repeating(int [] A_num)//�⦳���ƪ��Ʀr �]�Ȭ�1 �A �����ƪ��� �]��0
	{
		for(int k=1023 ; k<num.length ; k++)
		{
			int [] a = new int[4];
			a=split(k);

			for(int j=0 ; j<=a.length-2 ; j++)//���Ƭ�1
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
		//���լO�_���⭫�ƪ����ܬ�1
		for(int h=1023 ; h<4000 ; h++)
		{
			System.out.println("num["+h+"]="+num[h]);
		}
		*/
	}
	
	//XY���a���ת��ü�
	public int rand()
	{
		int rand;
		Not_repeating(num); //�z��X���ƪ��Ʀr
		do
		{
			rand = (int)(Math.random()*8853+1023);	   
		}
		while(num[rand]==1);
		return rand;
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
	
	//�P�_�XA�XB
	public void AB(int Ans , int Guess)
	{
	    this.A=0;//�M��AB
		this.B=0;
		int [] Ans_split = new int [4];//�s���ѵ��ת��Ʀr
		int [] Guess_split = new int [4];//�s���Ѳq���Ʀr
		
		Ans_split=split(Ans); //���ѵ��ת��Ʀr
		Guess_split=split(Guess); //���Ѳq���Ʀr
		
		for(int j=0 ; j<4 ;j++) //�P�_�XA�XB
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
	
	
	//�ھڴXA�XB �R�����i�઺�Ʀr
	public void delete_math(int XY_val ,int [] XY_num ,int A ,int B)
	{
		int [] XY_val_split  = new int [4]; //�x�s XY�q����
		XY_val_split = split(XY_val);//����XY�q����	
		
			for(int k=1023 ; k<XY_num.length ; k++)
			{
				if(XY_num[k]!=1) //�p�G�Ʀr�O�����ƪ�
				{
					int num_a=0 , num_b=0;//���� �����ƪ��ƩM�q�Ʀr���� �XA�XB
					
					int [] num_split = new int [4]; //���Τ����ƪ���
    				num_split=split(k);//���Τ����ƪ���
    						
    				for(int i=0;i<=3;i++)
					{
						for(int j=0;j<=3;j++)
						{
							if(XY_val_split[i]==num_split[j]) //�p�G �����ƪ��ƩM�q�Ʀr���� �̭��Ʀr�ŦX 
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
						XY_num[k] = 1;
					}
				}
			}
			
			//����
			/*
			for(int h=1023 ; h<3000 ; h++)
			{
				System.out.println("num["+h+"]="+XY_num[h]);
			}
			*/
		//System.out.println(num.length);//�q�Ʀr������
	}
	
	
}

class X_player_c extends Thread  //X���a
{
	filter_c filter = new filter_c();	//�إߪ�����o�����ƪ��}�C
	Queue <Integer> XY_queue;// �s�� �q���Ʀr
    static int [] Y_num = new int [9876];//�R��Y���i�઺�Ʀr
	int maxsize=0;
	int X_ans=0;
	int X_guess=0;
	int Y_guess=0;
	int c = 0; //�p�� �Ĥ@���إ�XY���a������ �ĤG�����q
	int four_A=0; //����4A;
	
	public X_player_c(Queue<Integer> XY_queue , int maxsize) //�غc�� �s�w�]��
	{
		this.XY_queue = XY_queue;
		this.maxsize = maxsize;
	}
	
	@Override
	public void run()
	{
		X_player();
		System.exit(0); //�j�����}
	}
	
	public void X_player()
	{
		while(true)//this.A !=4
		{
			synchronized (XY_queue)//�P�B ����������|���۰���ӸI��
			{	
				this.c++;  //�p�� �Ĥ@���إ�XY���a������ �ĤG�����q		
				while(XY_queue.size() == maxsize && this.c ==2)
				{
					try
					{
						System.out.println("����Y���a");
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
						
				if(this.c == 1) //�p�� �Ĥ@���إ�XY���a������ �ĤG�����q
				{	
					filter.Not_repeating(Y_num);//�z��XY_num�����ƪ���
					this.X_ans=filter.rand(); //X���a���������ƪ�����		
					XY_queue.add(this.X_ans); //��JX_queue�Ĥ@��
					System.out.println("X���a�����׬O�G"+this.X_ans);
					XY_queue.notifyAll();
				}
				else //�ĤG�� ���a���q
				{							
					//System.out.println("X_GUESS="+this.X_guess+",a"+filter.A + "B="+filter.B);
					//�M�����ƪ��Ʀr
					
					if(this.c >2)
					{
						filter.delete_math(this.X_guess , Y_num , filter.A , filter.B);	
						//���զ��L�R�����ŦX���Ʀr
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
															
					if(XY_queue.size() == 2) //�p�GQueue�̭�����Ӽƪ��� �R���Ĥ@��
					{
						XY_queue.poll(); //�R���Ĥ@�Ӫ��Ʀr	
						this.Y_guess = XY_queue.element();									
						filter.AB(this.X_ans,this.Y_guess);  //Y�^���XA�XB
						System.out.println("-------------------------------");
						XY_queue.remove();
					}
					if(filter.A != 4)
					{
						//X���a�q��
						this.X_guess=0;//���M���x�s�q�Ʀr���ܼ�
						do	//��X�}�C�����ƪ���	 �s��X_guess
						{
							this.X_guess = (int)(Math.random()*8853+1023);	   
						}
						while(Y_num[this.X_guess]==1);
						
						XY_queue.add(this.X_guess); //��JX_queue�Ĥ@��
										
						XY_queue.notifyAll();//�n�����Y���a
						
						//�T�{ queue �̭����Ʀr
						while(XY_queue.size() == maxsize)  //�q�o��}�l �p�G��Queue�w�g���ӼƦr
						{
							try
							{
								System.out.println("X���a");
								System.out.println("X���a�q�G" + this.X_guess);
								System.out.println("����Y���a�^�����G");
								XY_queue.wait();
							}
							catch (Exception x){}		
						}
					}
					else
					{
						System.out.println("-------------------------------");
						System.out.println("Y���a���");
						break;
					}	
				}															
			}
		}
	}
}

class Y_player_c extends Thread  //Y���a
{
	filter_c filter = new filter_c();	//�إߪ�����o�����ƪ��}�C
	Queue <Integer> XY_queue;// �s�� �q���Ʀr
	int [] X_num = new int [9876];//�R��X���i�઺�Ʀr
	int maxsize=0;
//	int X_ans=0;  //���oX������
	int Y_ans=0;
	int X_guess=0;
	int Y_guess=0;
	int c =0; //�p�� �Ĥ@���إ�XY���a������ �ĤG�����q
	int four_A=0; //����4A;
	int A=0;
	int B=0;
	
	public Y_player_c(Queue<Integer> XY_queue , int maxsize) //�غc�� �s�w�]��
	{
		this.XY_queue = XY_queue;
		this.maxsize = maxsize;
	}
	
	@Override
	public void run()
	{
		Y_player();
		System.exit(0); //�j�����}
	}
	
	public void Y_player()
	{
		while(true)//this.A !=4
		{
			synchronized (XY_queue)//�P�B ����������|���۰���ӸI��
			{
				this.c++;//�p�� �Ĥ@���إ�XY���a������ �ĤG�����q
	
				
				while(XY_queue.isEmpty() && this.c ==2)
				{
					try
					{
						System.out.println("-------------------------------");				
						System.out.println("����X���a�q����");
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
				
				if(this.c == 1) //�p�� �Ĥ@���إ�XY���a������ �ĤG�����q
				{	
					filter.Not_repeating(X_num);//�z��XY_num�����ƪ���
				//	this.X_ans = XY_queue.element(); //���oX������
					this.Y_ans=filter.rand(); //X���a���������ƪ�����		
					System.out.println("Y���a�����׬O�G"+this.Y_ans);
					XY_queue.remove();
					XY_queue.notifyAll();
				}	
				else//�ĤG���}�l ���a���q
				{				
					//�M�����ƪ��Ʀr
					if(this.c >2)
					{							
						filter.delete_math(this.Y_guess , X_num ,filter.A , filter.B);
					}
					
					this.Y_guess =0 ;//���M���x�s�q�Ʀr���ܼ�
					this.X_guess =XY_queue.element(); //���oX���a�q���ü�
					
					filter.AB(this.Y_ans , this.X_guess); //��X���a�^���XA�XB
					
					if(filter.A != 4)
					{
						do	//��X�}�C�����ƪ���	 �s��X_guess
						{
							this.Y_guess = (int)(Math.random()*8853+1023);	
						}
						while(X_num[this.Y_guess]==1);
						
						XY_queue.add(this.Y_guess); //��JX_queue�ĤG��
						
						//XY_queue.poll(); //�R���Ĥ@�Ӫ��Ʀr
						
						System.out.println("-------------------------------");
						System.out.println("Y���a");
						System.out.println("Y���a�q�G" + this.Y_guess);	
						XY_queue.notifyAll();	
						
						//�T�{ queue �̭����Ʀr
						while(XY_queue.size() == 2)
						{
							try
							{
								System.out.println("����X���a�^�����G");
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
							System.out.println("X���a���");
							break;
					}													
				}	
			}	
		}
	}
}


