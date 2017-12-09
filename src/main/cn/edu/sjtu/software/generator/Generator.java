package cn.edu.sjtu.software.generator;

// Require Apache POI
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Generator {

    String function = "default";
    int numOfCases = 0;
    int numOfParams = 0;
    ArrayList<String> params = new ArrayList<String>();
    TestCase[] testCases;

    class TestCase {
        String name;
        String[] params;
        String expected;

        public TestCase(String name, String[] params, String expected) {
            this.name = name;
            this.params = params;
            this.expected = expected;
        }

        @Override
        public String toString() {
            return "TestCase{" +
                    "name='" + name + '\'' +
                    ", params=" + Arrays.toString(params) +
                    ", expected='" + expected + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException{
        Generator g = new Generator();
        g.readXLSFile("test.xlsx");
        g.generateJavaTest();
        Result r1 = g.runTests();
//        Result r2 = g.runTests();
//        Result r3 = g.runTests();
//        g.generateResultFile(r);
        List<Failure> failures = r1.getFailures();

    }
    //Also set class attributes;

    public void readXLSFile(String filePath) throws IOException{
        Workbook wb = new XSSFWorkbook("test.xlsx");
        // Workbook wb = new XSSFWorkbook(filepath);

        Sheet sheet = wb.getSheetAt(0);

        this.numOfCases = sheet.getRow(0).getLastCellNum() - 1; // 去掉第一个，与行不一样。

        testCases = new TestCase[numOfCases];

        this.function = sheet.getRow(0).getCell(0).getStringCellValue();
        this.numOfParams = 0;
        for(int i = 1;i<numOfCases;i++){
            if(sheet.getRow(i).getCell(0).getStringCellValue().equals("return")) break;
            else numOfParams++;
        } // Params

        for(int i = 0;i<numOfCases;i++) {
            String expectedValue = sheet.getRow(numOfParams + 1).getCell(i + 1).getStringCellValue();
            String[] params = new String[numOfParams];
            for(int j = 0;j<numOfParams;j++) {
                params[j] = sheet.getRow(j+1).getCell(i+1).getStringCellValue();
            }
            testCases[i] = new TestCase("test"+(i+1),params, expectedValue);
        }

    }

    public void generateResultFile(Map<String,Result> resultMap) throws IOException{
        File f = new File("result.xlsx");
        f.createNewFile();
        Workbook wb = new XSSFWorkbook("result.xlsx");
        Sheet sheet = wb.getSheetAt(0);
        sheet.getRow(0).getCell(0).setCellValue(this.function);
        int rowCur = 1;
        for(int i = 0;i<testCases.length;i++){

        }

    }


    public Result runTests() {
        JUnitCore core = new JUnitCore();
//        Result r = core.run(GenTest.class);
        Result r = null;
        return r;
    }

    /**
     * Must be called after readXLSFile
     * @throws IOException
     */
    public void generateJavaTest() throws IOException {
        String dir = "src/main/cn/edu/sjtu/software/";
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


        for(int i = 0;i<numOfCases;i++){
            String expectedValue = testCases[i].expected;

            fw.write("@Test public void test"+ (i+1) +"(){\n");
            fw.write("assertEquals(" + expectedValue + ",");
            fw.write(member+"."+function + "(");
            for(int j = 0;j<numOfParams;j++) {
                fw.write(testCases[i].params[j]);
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
