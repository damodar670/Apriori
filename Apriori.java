import java.util.*;
import java.util.Map.Entry;
import java.io.*;
public class Apriori {
public static void main(String args[]) throws Exception
{
	//taking input from a file and storing it in a arraylist
	Scanner sc=new Scanner(System.in);
	File file=new File("C:\\Users\\LENOVO\\eclipse-workspace\\Apriori\\file1.txt");
	 Scanner s1=new Scanner(file);
	 ArrayList<String> trans = new ArrayList<>();
	 while (s1.hasNextLine()) { 
		 trans.add(s1.nextLine());
	     // System.out.println(s1.nextLine()); 
	 }
	 System.out.println("enter the minimum support");
	//converting into single items  and storing it in arraylist
	 int min_sup=sc.nextInt();
	ArrayList<String> trans1=new ArrayList<>();
	String str;
	for(int i=0;i<trans.size();i++) {
	   str=trans.get(i);
	   String[] arrOfstr=str.split(",");
	   for(String a:arrOfstr) {
		   if(!trans1.contains(a))
		   trans1.add(a);
	}
	   }
	 //System.out.println(trans1);
	//constructing HashMap
	HashMap<String,Integer> hmap=new HashMap<>();
	for(int i=0;i<trans1.size();i++) {
		hmap.put(trans1.get(i),0);
	}
	//System.out.println(hmap);
	//constructing hmap 
	String str1;
	for(int i=0;i<trans.size();i++) {
	     str1=trans.get(i);
	     String[] arrOfstr=str1.split(",");
	     for(String a:arrOfstr) {
	    	 int val=hmap.get(a);
	    	 val++;
	    	 hmap.put(a,val);
	    	 }}
	    //System.out.println(hmap);
	   //constructing c1 
	HashMap<String,Integer> c1=new HashMap<>();
	   c1.putAll(hmap);
	  // System.out.println(c1);
	   HashMap<String,Integer> l1=new HashMap<>();
	  
	   for (Map.Entry<String,Integer> entry : c1.entrySet()) {
			 if(entry.getValue()>=min_sup)
				 l1.put(entry.getKey(),entry.getValue());
			}
		        
	// System.out.println(c1);
	System.out.println("displaying l1");
	//System.out.println(l1);
	for (Map.Entry<String,Integer> entry : l1.entrySet()) {
		System.out.println(entry.getKey()+"  "+entry.getValue());
	}
	//join step and pruning and constructing c2 and l2 
	ArrayList<String>tran=new ArrayList<String>();
	for (Map.Entry<String,Integer> entry : l1.entrySet()) {
		tran.add(entry.getKey());
	}
	ArrayList<String>a2=new ArrayList<String>();
	HashMap<String,Integer> c2=new HashMap<String,Integer>();
	HashMap<String,Integer> l2=new HashMap<String,Integer>();
	//System.out.println(tran.size());
	for(int i=0;i<tran.size();i++) {
		for(int j=i+1;j<tran.size();j++) {
			a2.add(tran.get(i)+tran.get(j));
		}
	}
	Collections.sort(a2);
	//System.out.println(a2);
	  int val1=0;
	for(int i=0;i<a2.size();i++) {
		c2.put(a2.get(i),val1);
	}
	String str2;
	for(int i=0;i<trans.size();i++) {
		str2=trans.get(i);
		for(int j=0;j<a2.size();j++) {
			String m=a2.get(j);
			if(check1(str2,m)) {
				val1=c2.get(a2.get(j));
				val1++;
				c2.put(a2.get(j),val1);
			}}}
	//System.out.println(c2);
	  // System.out.println(c1);
	  for (Map.Entry<String,Integer> entry : c2.entrySet()) {
			 if(entry.getValue()>=min_sup)
				 l2.put(entry.getKey(),entry.getValue());
			}
	//System.out.println(c2);
	// l2.putAll(c2);
	 a2.clear();
	 System.out.println("displaying l2 ");
	 for (Map.Entry<String,Integer> entry :  l2.entrySet()) {
		  a2.add(entry.getKey());
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
	 Collections.sort(a2);
	 //constructing c3 and l3
	 ArrayList<String>tran3=new ArrayList<String>();
		for (Map.Entry<String,Integer> entry : l2.entrySet()) {
			tran3.add(entry.getKey());
		}
		ArrayList<String>a3=new ArrayList<String>();
		HashMap<String,Integer> c3=new HashMap<String,Integer>();
		HashMap<String,Integer> l3=new HashMap<String,Integer>();
		//System.out.println(tran.size());
		//join step 
		for(int i=0;i<tran3.size();i++) {
			for(int j=i+1;j<tran3.size();j++) {
				if(tran3.get(i).substring(0,tran3.get(i).length()-1).equals(tran3.get(j).substring(0,tran3.get(j).length()-1)))
				a3.add(tran3.get(i)+tran3.get(j).substring(1,tran3.get(j).length()));
			}
		}
		
		Collections.sort(a3);
		//System.out.println(a3);
		 int val2=0;
			for(int i=0;i<a3.size();i++) {
				c3.put(a3.get(i),val2);
			}
			//System.out.println(c3);
			//prune step 
			String str3;
			for(int i=0;i<trans.size();i++) {
				str3=trans.get(i);
				
				 String[] arrOfstr=str3.split(",");
				ArrayList<String> arr=new ArrayList<>();
				for(String a:arrOfstr) {
					arr.add(a);
				}
				//System.out.println(arr);
				for(int j=0;j<a3.size();j++) {
					String m=a3.get(j);
				     if(check1(str3,m))
				     {	 
				    	 if(prune(m,a2)) 
				    	 {
						val1=c3.get(a3.get(j));
						val1++;
						c3.put(a3.get(j),val1);
					}
				     }
				}
			}
			//System.out.println(c3);
				  for (Map.Entry<String,Integer> entry : c3.entrySet()) {
						 if(entry.getValue()>=min_sup)
							 l3.put(entry.getKey(),entry.getValue());
						}
			
			 //l3.putAll(c3);
			 a3.clear();
			 System.out.println("displaying l3 ");
			 for (Map.Entry<String,Integer> entry : l3.entrySet()) {
				  a3.add(entry.getKey());
					System.out.println(entry.getKey()+" "+entry.getValue());
				}
			 Collections.sort(a3);
			 generate(c3,l3,a3,min_sup,trans);
			 sc.close(); s1.close(); 			}

/*private static boolean check1(ArrayList<String> arr, String m) {
	//System.out.println(arr);
	
		for(int i=0;i<arr.size();i++) {
			//System.out.println(arr);
			for(int j=0;j<arr.get(j).length();j++) {
				if(!m.contains(Character.toString(arr.get(i).charAt(j))))
		             return false;}
	    } 
	return true;
} */
private static void generate(HashMap<String, Integer> c3, HashMap<String, Integer> l3, ArrayList<String> a3,
		int min_sup, ArrayList<String> trans) {
	       if(c3.isEmpty()) {
	    	   return ;
	       }
	       else {
	    	   ArrayList<String>tran4=new ArrayList<String>();
	   		for (Map.Entry<String,Integer> entry : l3.entrySet()) {
	   			tran4.add(entry.getKey());
	   		}
	   		ArrayList<String>a4=new ArrayList<String>();
	   		HashMap<String,Integer> c4=new HashMap<String,Integer>();
	   		HashMap<String,Integer> l4=new HashMap<String,Integer>();
	   		//System.out.println(tran4);
	   		//join step 
	   		for(int i=0;i<tran4.size();i++) {
	   			for(int j=i+1;j<tran4.size();j++) {
	   				if(tran4.get(i).substring(0,tran4.get(i).length()-1).equals(tran4.get(j).substring(0,tran4.get(j).length()-1)))
	   				a4.add(tran4.get(i)+tran4.get(j).substring(tran4.get(j).length()-1,tran4.get(j).length()));
	   			}
	   		}
	   		//System.out.println(a4);
	   		 int val2=0;
	   			for(int i=0;i<a4.size();i++) {
	   				c4.put(a4.get(i),val2);
	   			}
	   			//System.out.println(c4);
	   			//prune step 
	   			String str3;
	   			//System.out.println(a3);
	   			for(int i=0;i<trans.size();i++) {
					str3=trans.get(i);
					
					 String[] arrOfstr=str3.split(",");
					ArrayList<String> arr=new ArrayList<>();
					for(String a:arrOfstr) {
						arr.add(a);
					}
					for(int j=0;j<a4.size();j++) {
						String m=a4.get(j);
					     if(check1(str3,m)) {
					    	 if(prune(m,a3)) {
							val2=c3.get(a4.get(j));
							val2++;
							c3.put(a4.get(j),val2);
						}}}}for (Map.Entry<String,Integer> entry : c3.entrySet()) {
							 if(entry.getValue()>=min_sup)
								 l3.put(entry.getKey(),entry.getValue());
							}
				
	   					//System.out.println(c4);
	   			 //l4.putAll(c4);
	   			 a4.clear();
	   			 System.out.println("displaying l4");
	   			// System.out.println(l4);
	   			 for (Map.Entry<String,Integer> entry : l4.entrySet()) {
	   				  a4.add(entry.getKey());
	   					System.out.println(entry.getKey()+" "+entry.getValue());
	   				} 
	   			Collections.sort(a4);
	   			 generate(c4, l4, a4, min_sup,trans);  } 
   }

private static boolean check1(String str3, String m) {
	
    for(int j=0;j<m.length();j++) {
    	if(!str3.contains(Character.toString(m.charAt(j))))
    	{
    		return false;
    	}
    }
	return true;
}
private static boolean prune(String m, ArrayList<String> a2) {
	for(int i=0;i<m.length();i++) {
		String n=m.substring(0,i)+m.substring(i+1,m.length());
	    if(!a2.contains(n)) {
	    	return false;	
	    }
	}
	return true ;
}
}

