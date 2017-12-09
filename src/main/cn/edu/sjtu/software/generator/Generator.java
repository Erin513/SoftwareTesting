package cn.edu.sjtu.software.generator;
// Require Apache POI
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Generator {

    public static void main(String[] args) throws IOException{
        Generator g = new Generator();
        g.generateJavaTest();
    }

    public Sheet readXLSFile() throws IOException{
        InputStream in = new FileInputStream("test.xlsx");
        Workbook wb = new XSSFWorkbook("test.xlsx");

        Sheet sheet = wb.getSheetAt(0);
//        System.out.println(sheet.getLastRowNum()); // 第一行是0
//        System.out.println(sheet.getRow(0).getLastCellNum()); // 第一列是1
//        System.out.println(sheet.getRow(0).getCell(0).getStringCellValue());
//        System.out.println(sheet.getRow(0).getCell(1).getStringCellValue());
//        System.out.println(sheet.getRow(1).getCell(1).getStringCellValue());
        return sheet;
    }

    public void generateJavaTest() throws IOException {
        String dir = "src/test/cn/edu/sjtu/software/";
        String filename = "GenTest";
        String testClassName = "CalRadius";
        String member = "calRadius";
        String exname = ".java";
        File f = new File(dir + filename + exname);
        FileWriter fw = new FileWriter(f);

        // Imports
        fw.write("package cn.edu.sjtu.software;\n\n");
        fw.write("import static org.junit.Assert.assertEquals;\n");
        fw.write("import org.junit.*;\n");

        // Class initialize
        fw.write("public class " + filename + "{\n");

        fw.write("private " + testClassName + " " + member + "= new " + testClassName + "();\n");

        // Test Method
        Sheet sheet = readXLSFile();

        int numOfCases = sheet.getRow(0).getLastCellNum() - 1; // 去掉第一个，与行不一样。
        String function = sheet.getRow(0).getCell(0).getStringCellValue();
        int numOfParams = 0;
        for(int i = 1;i<numOfCases;i++){
            if(sheet.getRow(i).getCell(0).getStringCellValue().equals("return")) break;
            else numOfParams++;
        } // Params
        System.out.println(numOfParams);
        for(int i = 0;i<numOfCases;i++){
            System.out.println(sheet.getRow(numOfParams+1).getCell(i+1));
            String expectedValue = sheet.getRow(numOfParams+1).getCell(i+1).getStringCellValue();

            fw.write("@Test public void test"+ (i+1) +"(){\n");
            fw.write("assertEquals(" + expectedValue + ",");
            fw.write(member+"."+function + "(");
            for(int j = 0;j<numOfParams;j++) {
                fw.write(sheet.getRow(j+1).getCell(i+1).getStringCellValue());
                if(j!=numOfParams-1) fw.write(",");
            }
            fw.write("));\n");
            fw.write("}\n\n");
        }

        // End
        fw.write("}\n");

        fw.flush();
        fw.close();
    }
}
