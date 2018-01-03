package cn.edu.sjtu.software.generator;

// Require Apache POI

import cn.edu.sjtu.software.GenTest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.tools.*;
import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Generator{
    URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});

    String function = "default";
    int numOfCases = 0;
    int numOfParams = 0;
    ArrayList<String> params = new ArrayList<String>();
    TestCase[] testCases;

    public Generator() throws MalformedURLException {
    }

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


    public static void main(String[] args) throws Exception{
        Generator g = new Generator();
        g.go();
    }

    public void go() throws Exception{
        readXLSFile("test.xlsx");
        generateJavaTest("CalRadius");

        Result r1 = runTests(GenTest.class);
        List<Failure> failures = r1.getFailures();
        Map<String,Result> resultMap = new HashMap<String,Result>();
        resultMap.put("actual",r1);

        String destPathStr = "src/main/cn/edu/sjtu/software/CalRadius.java";
        File dest = new File(destPathStr);
        Path destPath = dest.toPath();

        String mutationPath = "src/main/cn/edu/sjtu/software/mutation/";

        String[] muts = {"opr.mut","ret.mut","rel.mut","log.mut"};
        for(String mut: muts){
            File mutationFile = new File(mutationPath+"CalRadius.java."+mut);
            Path mutPath = mutationFile.toPath();
            Files.copy(mutPath,destPath, REPLACE_EXISTING);
            System.out.println("Testing "+mut);
            String calRadius = "src/main/cn/edu/sjtu/software/CalRadius.java";
            String gentest = "src/main/cn/edu/sjtu/software/GenTest.java";
            String circle = "src/main/cn/edu/sjtu/software/Circle.java";
            String point = "src/main/cn/edu/sjtu/software/Point.java";
            Class testclass = recompile(calRadius, gentest, circle, point);
            Result r = runTests(testclass);
            resultMap.put(mut, r);
        }

//        File mutation1 = new File(mutationPath+"CalRadius.java.opr.mut");
//        Path mutation1Path = mutation1.toPath();
//        Files.copy(mutation1Path, destPath, REPLACE_EXISTING);
//        Result r2 = g.runTests();
//        resultMap.put("opr.mut",r2);
//
//        File mutation2 = new File(mutationPath+"CalRadius.java.ret.mut");
//        Path mutation2Path = mutation2.toPath();
//        Files.copy(mutation2Path, destPath, REPLACE_EXISTING);
//        Result r3 = g.runTests();
//        resultMap.put("ret.mut",r3);

        File ori = new File(mutationPath+"CalRadius.java.ori");
        Path oriPath = ori.toPath();
        Files.copy(oriPath, destPath, REPLACE_EXISTING);


        generateResultFile(resultMap);
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
        for(int i = 1;i<200;i++){
            if(sheet.getRow(i).getCell(0).getStringCellValue().equals("return")) break;
            else numOfParams++;
        } // Params
        System.out.println("TestCases:"+numOfParams);

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
        XSSFWorkbook wb = new XSSFWorkbook();

        String sheetName = "Sheet1";//name of sheet

        XSSFSheet sheet = wb.createSheet(sheetName) ;
        Row head = sheet.createRow(0);
        head.createCell(0).setCellValue(function);
        Row params = sheet.createRow(1);
        params.createCell(0).setCellValue("Params");
        for(int i = 1;i<=numOfParams;i++) {
            params.createCell(i).setCellValue("Params"+i);
        }
        int rowCur = 2;
        for(int i = 0;i<numOfCases;i++) {
            Row input = sheet.createRow(rowCur++);
            input.createCell(0).setCellValue("Input");
            for(int j = 1;j<=numOfParams;j++) {
                input.createCell(j).setCellValue(testCases[i].params[j-1]);
            }
            sheet.createRow(rowCur++).createCell(0).setCellValue("Output");
            for(Map.Entry<String, Result> entry: resultMap.entrySet()){
                String key = entry.getKey();
                Row output = sheet.createRow(rowCur++);
                output.createCell(0).setCellValue(key);
                Result result = entry.getValue();
                String actual = testCases[i].expected;
                for(Failure failure: result.getFailures()){
                    try{
                        if(failure.getTestHeader().contains(testCases[i].name)){
                            System.out.println("detected:"+failure+"    "+testCases[i].name);
                            actual = failure.getMessage().replaceAll("expected:(.*)but was:","");
                        }
                    }catch(Exception e){
                        actual = "err";
                    }

                }
                output.createCell(1).setCellValue(actual);
            }
        }

        FileOutputStream fileOut = new FileOutputStream("result.xlsx");
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();

    }

    public Class recompile(String... str) throws Exception {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        List<String> optionList = new ArrayList<String>();
        optionList.add("-classpath");
        optionList.add(System.getProperty("java.class.path"));
        Iterable<? extends JavaFileObject> compilationUnit = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(str));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, optionList, null, compilationUnit);
        if(task.call()){
//            Reloader classLoader = new Reloader();
            Class<?> loadedClass = classLoader.loadClass("cn.edu.sjtu.software.CalRadius");
            classLoader.loadClass("cn.edu.sjtu.software.Point");
            Class<?> testClass = classLoader.loadClass("cn.edu.sjtu.software.GenTest");

            Thread.currentThread().setContextClassLoader(classLoader);
            return testClass;
        }
        return null;
    }
    static class Reloader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (name.startsWith("cn.edu.sjtu.software.generator")) {
                return getParent().loadClass(name);
            }
            else if (name.startsWith("cn.edu.sjtu.software")) {
                try {
                    InputStream is = Generator.class.getClassLoader().getResourceAsStream("cn/edu/sjtu/software/"+name.substring(21)+".class");
                    byte[] buf = new byte[10000];
                    int len = is.read(buf);
                    return defineClass(name, buf, 0, len);
                } catch (IOException e) {
                    throw new ClassNotFoundException("", e);
                }
            }

            return getParent().loadClass(name);
        }
    }


    public Result runTests(Class clazz) throws Exception {
        Class junitcore = classLoader.loadClass("org.junit.runner.JUnitCore");
        JUnitCore core = (JUnitCore)junitcore.newInstance();
        //        ClassLoader loader = Generator.class.getClassLoader();
//        Class clazz = loader.loadClass("cn.edu.sjtu.software.GenTest");
        Result r = core.run(clazz);
//        Result r = null;
        return r;
    }

    /**
     * Must be called after readXLSFile
     * @throws IOException
     */
    public void generateJavaTest(String testClassName) throws IOException {
        String dir = "src/main/cn/edu/sjtu/software/";
        String filename = "GenTest";
//        String testClassName = "CalRadius";
        String member = "calRadius";
        String exname = ".java";
        File f = new File(dir + filename + exname);
        FileWriter fw = new FileWriter(f);

        // Imports
        fw.write("package cn.edu.sjtu.software;\n\n");
        fw.write("import static org.junit.Assert.assertEquals;\n");
        fw.write("import org.junit.*;\n");
        fw.write("import org.junit.runner.RunWith;");
        fw.write("import cn.edu.sjtu.software.generator.ClasspathTestRunner;");
        fw.write("@RunWith(ClasspathTestRunner.class)");
        // Class initialize
        fw.write("public class " + filename + "{\n");

        fw.write("private " + testClassName + " " + member + "= new " + testClassName + "();\n");


        for(int i = 0;i<numOfCases;i++){
            String expectedValue = testCases[i].expected;

            fw.write("@Test public void test"+ (i+1) +"() throws Exception{\n");
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
