package javapro2;
import java.util.*;


//purpose of using hashing is that, the duplication of the id of people can be avoided
//and each person is given a separate id along with a guaranty of unique seat number

class functions
{
	Scanner sc=new Scanner(System.in);
	int value=0;
	String name="";
	//the map acts as the database for storing the information of the people
	//Here manual storage of information will be required
	TreeMap<Integer, String> mp = new TreeMap<Integer, String>(); 
	public void store_info(int n,int k)
	{
		for(int i=1;i<n+1;i++)
		{
			System.out.println("enter your name");
			name=sc.next();
			System.out.println("**Is the person a VIP?**");
			System.out.println("1.Yes"+"  "+"2.No");
			int m=sc.nextInt();
			if(m==1)
			{
				value=100+i;	//add 100 to differentiate between an ordinary and a VIP person.
				mp.put(value,name);
			}
			else if(m==2)
			{
				value=i;
				mp.put(value,name);
			}
			else
			{
				System.out.println("Please choose the correct answer");
				System.exit(0);
			}
		}
		
		for (Map.Entry<Integer,String> i : mp.entrySet()) {
			System.out.print(i.getKey()+" -> "+i.getValue());
			System.out.println();
		}
		
		//to count the number of people traveling in Economy class.
		int count_ordinary=0;
		for (Map.Entry<Integer,String> i : mp.entrySet()) {
		    if(i.getKey()<100)
		    {
		    	count_ordinary++;
		    }
		}
		
		//to count the number of people traveling in VIP class.
		int count_vip=0;
		for (Map.Entry<Integer,String> i : mp.entrySet()) {
		    if(i.getKey()>=100)
		    {
		    	count_vip++;
		    }
		}
		
		OHash o=new OHash(count_ordinary);
		VHash v=new VHash(count_vip);
		
		
		//for loop for sending the values to the hash set for o and v people to check for the ticket number
		for (Map.Entry<Integer,String> i : mp.entrySet())
		{
		    if(i.getKey()<100)
		    {
		    	o.insertoHash(i.getKey());
		    }
		    else
		    {
		    	v.insertvHash(i.getKey());
		    }
		}		
		
		//o.display_oHash();
		//System.out.println();
		//v.display_vHash();
		
		if(k<100)
		{
			o.search_oHash(k);
		}
		else
		{
			v.search_vHash(k);
		}
}
}

//class for performing the functions for ordinary people
class OHash
{
	Scanner sc=new Scanner(System.in);
	int count_o;
	int o_table_size;
	int[] o_arr;
	int prime;
	int curr_osize;
	OHash(int n)
	{
		count_o=n;
		//System.out.println(count_o);
		prime=7;
		curr_osize=0;
		o_table_size=count_o;
		o_arr=new int[o_table_size];
		for (int i=0; i<o_table_size; i++) 
            o_arr[i] = -1; 
	}
	public boolean is_ofull()
	{
		return (curr_osize==o_table_size);
	}
	public int ohash1(int key)
	{
		return (key%o_table_size);
	}
	public int ohash2(int key)
	{
		return (prime-(key%prime));
	}
	
	//to allot seat numbers based on the customerID provided to them
	public void insertoHash(int key) 
    { 
        // if hash table is full 
        if (is_ofull()) 
            return; 
  
        // get index from first hash 
        int index = ohash1(key); 
  
        // if collision occurs 
        if (o_arr[index] != -1) 
        { 
            // get index2 from second hash 
            int index2 = ohash2(key); 
            int i = 1; 
            while (true) 
            { 
                // get newIndex 
                int newIndex = (index + i * index2) % 
                                        o_table_size; 
  
                // if no collision occurs, store 
                // the key 
                if (o_arr[newIndex] == -1) 
                { 
                    o_arr[newIndex] = key; 
                    break; 
                } 
                i++; 
            } 
        } 
  
        // if no collision occurs 
        else
            o_arr[index] = key; 
        curr_osize++; 
    } 
	
	//to display the ordinary people
	public void display_oHash() 
    { 
		System.out.println("These are the ordinary people"); 
        for (int i = 0; i < o_table_size; i++) 
        { 
            if (o_arr[i] != -1) 
            {//System.out.println(i+"->"+o_arr[i]);
            	System.out.println("Id is"+" "+o_arr[i]+" and your seat number is "+i);
            }
            else
            	System.out.println(i); 
        } 
    }
	
	//to search if the ticket of the person is booked or not and allot seat number
	public void search_oHash(int k)
	{
		int i=0;
		for (i = 0; i < o_arr.length; i++) 
		{
			if(o_arr[i]==k)
			{
				System.out.println("Your identity is verified and your seat number in the ECONOMY SECTION is "+i);
				break;
			}
		}
		if(i==o_arr.length)
		{
			System.out.println("Your ticket is not booked!");
		}
	}

	
	
	
}

//class for performing the functions for vip people
class VHash
{
	Scanner sc=new Scanner(System.in);
	functions f=new functions();
	int count_v;
	int v_table_size;
	int[] v_arr;
	int prime;
	int curr_vsize;
	VHash(int n)
	{
		count_v=n;
		//System.out.println(count_o);
		prime=3;
		curr_vsize=0;
		v_table_size=count_v;
		v_arr=new int[v_table_size];
		for (int i=0; i<v_table_size; i++) 
            v_arr[i] = -1; 
	}
	public boolean is_vfull()
	{
		return (curr_vsize==v_table_size);
	}
	public int vhash1(int key)
	{
		return (key%v_table_size);
	}
	public int vhash2(int key)
	{
		return (prime-(key%prime));
	}
	
	//to allot seat numbers based on the customerID provided to them
	public void insertvHash(int key) 
    { 
        // if hash table is full 
        if (is_vfull()) 
            return; 
  
        // get index from first hash 
        int index = vhash1(key); 
  
        // if collision occurs 
        if (v_arr[index] != -1) 
        { 
            // get index2 from second hash 
            int index2 = vhash2(key); 
            int i = 1; 
            while (true) 
            { 
                // get newIndex 
                int newIndex = (index + i * index2) % 
                                        v_table_size; 
  
                // if no collision occurs, store 
                // the key 
                if (v_arr[newIndex] == -1) 
                { 
                    v_arr[newIndex] = key; 
                    break; 
                } 
                i++; 
            } 
        } 
  
        // if no collision occurs 
        else
            v_arr[index] = key; 
        curr_vsize++; 
    } 
	
	//to display the VIP people
	public void display_vHash() 
    { 
		System.out.println("These are the VIP people"); 
		
        for (int i = 0; i < v_table_size; i++) 
        { 
            if (v_arr[i] != -1) 
            {//System.out.println(i+"->"+v_arr[i]);
            	System.out.println("VIP id is"+" "+v_arr[i]+" and your seat number is "+i);
            }
            else
            	System.out.println(i); 
        } 
    }  
	
	//to search if the ticket of the person is booked or not and allot seat number
	public void search_vHash(int k)
	{
		int i=0;
		for (i = 0; i < v_arr.length; i++) 
		{
			if(v_arr[i]==k)
			{
				System.out.println("Your identity is verified and your seat number in the VIP SECTION is "+i);
				break;
			}
		}
		if(i==v_arr.length)
		{
			System.out.println("Your ticket is not booked");
		}
		
		
	}
}

public class Main {
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		//Storing the information of people who buy the tickets
		functions f=new functions();
		int id=0;
		
		//Aadhar Card Verification
		System.out.println("Enter your Aadhar Number:");
		String aadhar=sc.nextLine();
		int l=aadhar.length();
		
	   if(l!=12)
	   {
	      System.out.println("This is not a valid Aadhar number");
	      System.exit(0);
	   } 
	   else if(l==12)
	   {
	        for(int i=0;i<l;i++)
	         {

	           if((aadhar.charAt(i)>='A' && aadhar.charAt(i)<='Z')||(aadhar.charAt(i)>='a' && aadhar.charAt(i)<='z'))
	           {
	        	   System.out.println("This is not a valid Aadhar number");
	 		       System.exit(0);
	           }
	         }
	    }
	    else 
	    {
            System.out.println("Your Aadhar is verified "); 
	    }
	   
	    System.out.println("Enter your provided Id:");
	    id=sc.nextInt();
	    
	    //From here, it is like a back-end where we have the customer information stored.
	    //Just to show the usage of tree, manual storage is done which can be seen in the store_info() function.
	    System.out.println("Enter the capacity of the flight");
	    int n=sc.nextInt();
	    f.store_info(n,id);
	      
		}
	}
