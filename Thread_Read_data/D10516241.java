import java.io.*;


class Thread_c extends Thread 
{
	int id;//thread編號
	int [] data = new int [40];  //放txt裡面的數值
	int c=0; 
	public Thread_c(int no)
	{
		this.id = no;
	}
	
	public void txt_data(int [] data,int c) //從main儲存txt數值
	{
		this.c= c;
		for(int i=0 ; i<c ;i++)
		{
			this.data[i] = data[i];
		}
	}
	
	public void run()
	{
		int id = this.id;  //thread編號
		int c= this.c; //有幾筆txt數值
		int sum = 0;//加總
		
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
class reader_c extends Thread //讀檔
{
	int [] data = new int [40]; //建立陣列存txt數字
	int c=0;//計數用
	public void run() 
	{	
		try
		{
			FileReader fileReader = new FileReader("tmp\\f_data.txt"); //讀檔案
		    BufferedReader bufReader = new BufferedReader(fileReader); //建立緩衝區
		    	    
		    while(bufReader.ready())//讀txt檔
		    {
		    	data[c] = Integer.parseInt(bufReader.readLine());
		    //	System.out.println(data[c]);
		    	c++; //計數txt裡面有幾筆資料
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
		reader_c reader = new reader_c(); //宣告讀檔案的物件
		int [] data = new int [40];
		
		Thread_c [] t = new Thread_c [4];	//宣告物件陣列
		for(int i=0 ; i<4 ; i++)
		{
			t[i] = new Thread_c(i); //宣告每個一物件的陣列
			
		//	System.out.println(t[i].id);
		}	
		reader.start();	
		try
		{
			reader.join();	//讓讀取檔案先執行 再去執行其他的執行緒
		}
		catch(Exception e){}
		
		
		for(int j=0 ; j<4 ; j++)
		{
			t[j].txt_data(reader.data,reader.c);
			t[j].start();
		}
	}
}
