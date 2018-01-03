package cn.edu.sjtu.software;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.Class;                

import java.lang.reflect.Constructor; 
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Array;  
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
public class generateJuintTest {
	public File[] get()
	{	
		File f=new File(this.getClass().getResource("").getPath());
		File[]files=f.listFiles();
		return files;
	}
	
	@SuppressWarnings("null")
	public  List<String> generateJuintTest(String funcname,String classname,String[][] temp,ArrayList<String> varname,ArrayList<String> vartype) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, IOException
	{	
		//String path = System.getProperty("user.dir");
		//path=path+"\\" +funcname+Integer.toString(i)+".java";
		//File f=new File(path);
			//FileWriter fw=new FileWriter(f);
		List<String> returnvalue=new ArrayList<String>();
			Class c=Class.forName("cn.edu.sjtu.software."+classname);
			Object o=c.newInstance();
			Method[] mds =c.getDeclaredMethods();
			Method md=c.getEnclosingMethod();
			for (Method md0 : mds) {
	            if(md0.getName().equals(funcname))
	            {
	            	md=md0;
	            	break;
	            }
	        }
			md.setAccessible(true);
			
//			System.out.println(md.getParameterCount());
//			System.out.println(temp[0].length);
			for(int row=0;row<temp.length;row++)
				{	//Integer[] t=new Integer[] {temp[row]};
					ArrayList real=new ArrayList();
					for(int r=0;r<temp[0].length;r++)
					{	System.out.println(vartype.get(r));
						if(vartype.get(r).equals("int"))
						{
							System.out.println(temp[row][r]);
							real.add(Integer.parseInt(temp[row][r]));
						}
							
						else if(vartype.get(r).equals("double"))
							real.add(Double.parseDouble(temp[row][r]));
						else 
							real.add(temp[row][r]);
					}
					//returnvalue.add((String) md.invoke(o, (Object[])temp[row]));
					System.out.println("����ֵ������"+md.getReturnType());
					if(md.getReturnType()==new String().getClass())
					returnvalue.add((String)md.invoke(o, (Object[])real.toArray()));
					else
						returnvalue.add((String.valueOf(md.invoke(o, (Object[])real.toArray()))));
				}
 
		return returnvalue;
	}
	
}
