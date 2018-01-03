package cn.edu.sjtu.software;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class test {
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";

	/**
	 * 判断Excel的版本,获取Workbook
	 * @param in
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	//public static ArrayList<Integer> valueexpected=new ArrayList<Integer>();
	
	public static Workbook getWorkbok(InputStream in,File file) throws IOException{
		Workbook wb = null;
		if(file.getName().endsWith(EXCEL_XLS)){	 //Excel 2003
			wb = new HSSFWorkbook(in);
		}else if(file.getName().endsWith(EXCEL_XLSX)){	// Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}

	/**
	 * 判断文件是否是excel
	 * @throws Exception 
	 */
	public static void checkExcelVaild(File file) throws Exception{
		if(!file.exists()){
			throw new Exception("文件不存在");
		}
		if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
			throw new Exception("文件不是Excel");
		}
	}
	
	/**
	 * 读取Excel测试，兼容 Excel 2003/2007/2010
	 * @throws Exception 
	 */
	
	public static void main(String[] args) throws Exception ,NullPointerException{
			
			generateJuintTest gjt=new generateJuintTest();
			// 同时支持Excel 2003、2007
			File excelFile = new File("C:/test/testy.xlsx"); // 创建文件对象
			File excelfile=new File("C:/test/testresult.xlsx");
			
			FileInputStream is = new FileInputStream(excelFile); // 文件流
			checkExcelVaild(excelFile);
			Workbook workbook = getWorkbok(is,excelFile);
			Workbook workbookout =new XSSFWorkbook();
			
			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			
			int varnum=0;
			/**
			 * 设置当前excel中sheet的下标：0开始
			 */
			System.out.println("一共有表格"+sheetCount);
			for(int sc=0;sc<sheetCount;sc++)
			{
				Sheet sheet = workbook.getSheetAt(sc);	// 遍历第一个Sheet
				 ArrayList<List<String>> caselist=new ArrayList<List<String>>();
				int column=sheet.getRow(1).getPhysicalNumberOfCells()-2;
				for (int j=0;j<sheet.getPhysicalNumberOfRows();j++)//读取每一行
				{
					Row row=sheet.getRow(j);
					if(row.getCell(0).getStringCellValue().equals("return"))
					{
						varnum=j-1;
						break;
					}
				}
				System.out.println("参数个数为"+varnum);
				String[][] temp = new String[column][varnum];//用例矩阵
				ArrayList<String> varname=new ArrayList<String>();//参数名称数组
				ArrayList<String> vartype=new ArrayList<String>();//参数名称数组类型
				String funcname=sheet.getRow(0).getCell(0).getStringCellValue();
				String classname=sheet.getRow(0).getCell(1).getStringCellValue();
				for (int j=0;j<sheet.getPhysicalNumberOfRows();j++)//读取每一行
				{	System.out.println(j);
					Row row=sheet.getRow(j);
					System.out.println(row.getCell(0).getStringCellValue());
					if(row.getCell(0).getStringCellValue().equals("return"))
						{	
//							System.out.println("end");
//							for(int i=1;i<=column;i++)
//							{
//								row=sheet.getRow(i+j);
//								valueexpected.add((int)(row.getCell(0).getNumericCellValue()));
//							}
							break;
						}
					else 
						{	
						System.out.println("else");
							if(j>0)
								{
									varname.add(row.getCell(0).getStringCellValue());
									vartype.add(row.getCell(1).getStringCellValue());
									//System.out.println("变量名");
									for(int i=0;i<column;i++)
									{	if(row.getCell(i+2).getCellType()==Cell.CELL_TYPE_NUMERIC)
										temp[i][j-1]=String.valueOf((int)row.getCell(i+2).getNumericCellValue());
										else
											temp[i][j-1]=row.getCell(i+2).getStringCellValue();
										//System.out.println(temp[i][j]);
									}
								}
						}
					
			    }
				/**
				 * 开始循环源程序或者编译程序
				 */	 
				File[] files=gjt.get();	
				caselist.add(gjt.generateJuintTest(funcname, classname, temp,varname,vartype));//源程序
				for(int i=0;i<files.length;i++)
				{	
					if(files[i].getName().indexOf(classname+"_mu")!=-1)
					{
						System.out.println("成功测试一个文件 ");
						caselist.add(gjt.generateJuintTest(funcname, files[i].getName().split("\\.")[0], temp,varname,vartype));//编译程序
					//caselist.add(gjt.generateJuintTest(funcname, classname+"_mu"+Integer.toString(i), temp,varname,vartype));//编译程序
					}
					
				}
				/**
				 * 写入表格
				 */	
				Sheet sheetout =(Sheet)workbookout.createSheet();
				System.out.println("写入表格");
			
				Row row0=sheetout.createRow(0);
				System.out.println("创建第一行");
				Row row1=sheetout.createRow(1);
				System.out.println("创建第二行");
				row0.createCell(0).setCellValue(funcname);
				System.out.println("填写函数名");
				row0.createCell(1).setCellValue(classname);//第一行
				row1.createCell(0).setCellValue("输入参数名称");
				System.out.println("输入参数名称");
				for(int l=1;l<=varname.size();l++)
					{
						row1.createCell(l).setCellValue(varname.get(l-1));
						
					}
				row1.createCell(varname.size()+1).setCellValue("测试用例编号");
				for(int c=0;c<temp.length;c++)//用例数
					{
						Row row=sheetout.createRow(sheetout.getPhysicalNumberOfRows());
						row.createCell(0).setCellValue("输入值");
						for(int v=1;v<=varname.size();v++)
							row.createCell(v).setCellValue(temp[c][v-1]);
						row.createCell(varname.size()+1).setCellValue(c+1);
						for(int r=0;r<caselist.size();r++)
							{
								Row row2=sheetout.createRow(sheetout.getPhysicalNumberOfRows());
								if(r==0)
									{
										row2.createCell(0).setCellValue("源程序结果");
										row2.createCell(1).setCellValue(caselist.get(r).get(c));
									}
								else
								{
									row2.createCell(0).setCellValue("变异程序结果");
									row2.createCell(1).setCellValue(caselist.get(r).get(c));
								}
							}
					}
			}
			FileOutputStream os=new FileOutputStream(excelfile);
			workbookout.write(os);
			os.flush();
			os.close();
	}//main
	
}
