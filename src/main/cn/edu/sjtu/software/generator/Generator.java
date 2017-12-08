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
        g.readXLSFile();
    }

    public void readXLSFile() throws IOException{
        InputStream in = new FileInputStream("test.xlsx");
        Workbook wb = new XSSFWorkbook("test.xlsx");

        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(1); // 第二行
        Cell cell = row.getCell(0);
        System.out.println(cell);
    }

    public void generateJavaTest() throws IOException {
        String dir = "src/test/cn/edu/sjtu/software/";
        String filename = "GenTest";
        String exname = ".java";
        File f = new File(dir + filename + exname);
        f.createNewFile();
        FileWriter fw = new FileWriter(f);

        // Imports
        fw.write("package cn.edu.sjtu.software;\n\n");
        fw.write("import org.junit.*;\n");


        // Class initialize
        fw.write("public class " + filename + "{\n");




        // End
        fw.write("}\n");

        fw.flush();
        fw.close();
    }
}
