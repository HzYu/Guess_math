import java.io.*;


class Thread_c extends Thread 
{
	int id;//thread�s��
	int [] data = new int [40];  //��txt�̭����ƭ�
	int c=0; 
	public Thread_c(int no)
	{
		this.id = no;
	}
	
	public void txt_data(int [] data,int c) //�qmain�x�stxt�ƭ�
	{
		this.c= c;
		for(int i=0 ; i<c ;i++)
		{
			this.data[i] = data[i];
		}
	}
	
	public void run()
	{
		int id = this.id;  //thread�s��
		int c= this.c; //���X��txt�ƭ�
		int sum = 0;//�[�`
		
		for(int i=0 ; i<c ;i++)		
		{
			sum += this.data[id] ;
			id+=4*(i+1);
			if(id>c)
			{
				break;
			}
		}
		System.out.println("t["+this.id+"]"+":"+sum);
	}
	
}
class reader_c extends Thread //Ū��
{
	int [] data = new int [40]; //�إ߰}�C�stxt�Ʀr
	int c=0;//�p�ƥ�
	public void run() 
	{	
		try
		{
			FileReader fileReader = new FileReader("tmp\\f_data.txt"); //Ū�ɮ�
		    BufferedReader bufReader = new BufferedReader(fileReader); //�إ߽w�İ�
		    	    
		    while(bufReader.ready())//Ūtxt��
		    {
		    	data[c] = Integer.parseInt(bufReader.readLine());
		    //	System.out.println(data[c]);
		    	c++; //�p��txt�̭����X�����
		    }
	
		    fileReader.close();
		}
		catch(Exception e)
		{
		}	
	}

}

public class D10516241
{
	public static void main (String args[]) throws IOException
	{
		reader_c reader = new reader_c(); //�ŧiŪ�ɮת�����
		int [] data = new int [40];
		
		Thread_c [] t = new Thread_c [4];	//�ŧi����}�C
		for(int i=0 ; i<4 ; i++)
		{
			t[i] = new Thread_c(i); //�ŧi�C�Ӥ@���󪺰}�C
			
		//	System.out.println(t[i].id);
		}	
		reader.start();	
		try
		{
			reader.join();	//��Ū���ɮץ����� �A�h�����L�������
		}
		catch(Exception e){}
		
		
		for(int j=0 ; j<4 ; j++)
		{
			t[j].txt_data(reader.data,reader.c);
			t[j].start();
		}
	}
}
