package cn.edu.sjtu.software;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ValidDateTest {
	@Before
	public void setUp() throws Exception {
		new ValidDate();
	}
	@After
	public void tearDown() throws Exception {}

	@Test
	public void testIsLeap() {
		System.out.println("isLeap:判断是否为闰年");
		boolean result1=ValidDate.isLeap(1600);
		boolean result2=ValidDate.isLeap(2016);
		boolean result3=ValidDate.isLeap(1900);
		boolean result4=ValidDate.isLeap(2049);
		boolean result5=ValidDate.isLeap(1499);
		boolean result6=ValidDate.isLeap(2051);
		assertEquals("第1条数据有问题",true,result1);
		assertEquals("第2条数据有问题",true,result2);
		assertEquals("第3条数据有问题",false,result3);
		assertEquals("第4条数据有问题",false,result4);
		assertEquals("第5条数据有问题",false,result5);
		assertEquals("第6条数据有问题",false,result6);
	}
	@Test
	public void testValidDayRange() {
		System.out.println("validDayRange:判断Day的有效性");
		boolean result1=ValidDate.validDayRange(-1);
		boolean result2=ValidDate.validDayRange(1);
		boolean result3=ValidDate.validDayRange(15);
		boolean result4=ValidDate.validDayRange(31);
		boolean result5=ValidDate.validDayRange(32);
		
		assertEquals("第1条数据有问题",false,result1);
		assertEquals("第2条数据有问题",true,result2);
		assertEquals("第3条数据有问题",true,result3);
		assertEquals("第4条数据有问题",true,result4);
		assertEquals("第5条数据有问题",false,result5);
	}
	@Test
	public void testValidMonthRange() {
		System.out.println("validMonthRange:判断month的有效性");
		boolean result1=ValidDate.validMonthRange(-1);
		boolean result2=ValidDate.validMonthRange(1);
		boolean result3=ValidDate.validMonthRange(8);
		boolean result4=ValidDate.validMonthRange(12);
		boolean result5=ValidDate.validMonthRange(13);
		assertEquals("第1条数据有问题",false,result1);
		assertEquals("第2条数据有问题",true,result2);
		assertEquals("第3条数据有问题",true,result3);
		assertEquals("第4条数据有问题",true,result4);
		assertEquals("第5条数据有问题",false,result5);
	}
	@Test
	public void testValidYearRange() {
		System.out.println("validYearRange:判断year的有效性");
		boolean result1=ValidDate.validYearRange(1499);
		boolean result2=ValidDate.validYearRange(1500);
		boolean result3=ValidDate.validYearRange(2016);
		boolean result4=ValidDate.validYearRange(2050);
		boolean result5=ValidDate.validYearRange(2051);
		assertEquals("第1条数据有问题",false,result1);
		assertEquals("第2条数据有问题",true,result2);
		assertEquals("第3条数据有问题",true,result3);
		assertEquals("第4条数据有问题",true,result4);
		assertEquals("第5条数据有问题",false,result5);
	}
	@Test
	public void testValidCombine() {
		System.out.println("validCombine:判断年月日的组合是否有效");
		boolean result1=ValidDate.validCombine(30,6,2016);
		boolean result2=ValidDate.validCombine(31,6,2016);
		boolean result3=ValidDate.validCombine(28,2,2016);
		boolean result4=ValidDate.validCombine(30,2,2016);
		boolean result5=ValidDate.validCombine(29,2,2016);
		boolean result6=ValidDate.validCombine(31,2,2017);
		boolean result7=ValidDate.validCombine(31,4,2017);
		boolean result8=ValidDate.validCombine(31,9,2017);
		boolean result9=ValidDate.validCombine(31,11,2017);
		boolean result10=ValidDate.validCombine(29,2,2017);
		boolean result11=ValidDate.validCombine(29,1,2016);
		boolean result12=ValidDate.validCombine(31,1,2016);
		
		assertEquals("第1条数据有问题",true,result1);
		assertEquals("第2条数据有问题",false,result2);
		assertEquals("第3条数据有问题",true,result3);
		assertEquals("第4条数据有问题",false,result4);
		assertEquals("第5条数据有问题",true,result5);
		assertEquals("第6条数据有问题",false,result6);
		assertEquals("第7条数据有问题",false,result7);
		assertEquals("第8条数据有问题",false,result8);
		assertEquals("第9条数据有问题",false,result9);
		assertEquals("第10条数据有问题",false,result10);
		assertEquals("第11条数据有问题",true,result11);
		assertEquals("第12条数据有问题",true,result12);
	}
	@Test
	public void testValidate() {
		System.out.println("validate:综合判断各函数的有效性");
		boolean result1=ValidDate.validate(10,9,2005);
		boolean result2=ValidDate.validate(10,9,2052);
		boolean result3=ValidDate.validate(29,2,1900);
		boolean result4=ValidDate.validate(10,15,2005);
		boolean result5=ValidDate.validate(10,0,2005);
		boolean result6=ValidDate.validate(35,9,2005);
		boolean result7=ValidDate.validate(0,9,2005);
		boolean result8=ValidDate.validate(2,28,2017);
		assertEquals("第1条数据有问题",true,result1);
		assertEquals("第2条数据有问题",false,result2);
		assertEquals("第3条数据有问题",false,result3);
		assertEquals("第4条数据有问题",false,result4);
		assertEquals("第5条数据有问题",false,result5);
		assertEquals("第6条数据有问题",false,result6);
		assertEquals("第7条数据有问题",false,result7);
		assertEquals("第8条数据有问题",false,result8);
	}
}
