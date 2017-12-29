package cn.edu.sjtu.software;

public class ValidDate {
	public static boolean isLeap(int year) {//判断是否为闰年
	if (((year % 4 ==0) && (year % 100 != 0)) || (year % 400 ==0))
    	return true; 
	else 
    	return false;
	}
	public static boolean validDayRange(int day) {//判断Day的有效性
		if((day>=1)&&(day<=31)) {
			System.out.println("The day is valid day:"+"Day="+day);
			return true;
		} else {
			System.out.println("The day is INvalid day:"+"Day="+day);
			return false;
		}
	}
	public static boolean validMonthRange(int month) {//判断month的有效性
		if((month>=1)&&(month<=12)) {
			System.out.println("The month is valid month:"+"Month="+month);
			return true;
		} else {
			
			System.out.println("The month is INvalid day:"+"Month="+month);
		    return false;
			
		}
		
	}
	public static boolean validYearRange(int year) {//判断year的有效性
		if((year>=1500)&&(year<=2050)) {
			System.out.println("The year is valid year:"+"Year="+year);
			return true;
		} else {
			
			System.out.println("The year is INvalid year:"+"Year="+year);
			return false;
		}
		
	}
	 //判断年月日的组合是否有效（是否遗漏了30天，月份不能为1,3,5,7,8,10,12；28天2月不是平年）
	public static boolean validCombine(int day,int month,int year) {
		//如果天数是31天，但月份却是2、4、6、9、11，这是无效的
		if((day==31)&&((month==2)||(month==4)||(month==6)||(month==9)||(month==11))) {
			System.out.println("日期="+day+"不可能出现在"+month);
			return false;
		}
		//如果天数是30天，但月份却是2，这是无效的
		if((day==30)&&(month==2)) {
			System.out.println("日期="+day+"不可能出现在二月");
			return false;
		}
		//如果天数是29天，月份却是2，却不是闰年，这是无效的
		if((day==29)&&(month==2)&&(!isLeap(year))) {
			System.out.println("日期="+day+"不可能出现在二月");
			return false;
		}
			return true;
	}
	
    //桩函数
    /*
    public static boolean validDayRange_stub(int day){
   	if(day== 10 ){
        	
        	return true;
        }
    	
    	if(day== 10){
        	
        	return true;
        }
    	
    	if(day== 29 ){
        	
        	return true;
        }
    	
    	if(day== 10  ){
        	
        	return true;
        }
    	
    	if(day== 10){
        	
        	return true;
        }
    	
    	if(day== 35 ){
        	
        	return false;
        }
    	
    	if(day== 0 ){
        	
        	return false;
        }
    	
    	if(day== 2 ){
        	
        	return true;
        }
    	
    	return false;
    }
    public static boolean validMonthRange_stub(int month){
        
    	if( month== 9 ){
        	
        	return true;
        }
    	
    	if( month== 9 ){
        	
        	return true;
        }
    	
    	if( month==2){
        	
        	return true;
        }
    	
    	if(month== 15){
        	
        	return false;
        }
    	
    	if(month== 0 ){
        	
        	return false;
        }
    	
    	if(month== 9){
        	
        	return true;
        }
    	
    	if(month== 9){
        	
        	return true;
        }
    	
    	if(month== 8 ){
        	
        	return false;
        }
    	return false;
    }
    public static boolean validYearRange_stub(int year){
        
    	if( year== 2005 ){
        	
        	return true;
        }
    	
    	if( year==  2052){
        	
        	return false;
        }
    	
    	if( year== 1900 ){
        	
        	return true;
        }
    	
    	if( year== 2005 ){
        	
        	return true;
        }
    	
    	if( year== 2005 ){
        	
        	return true;
        }
    	
    	if( year== 2005 ){
        	
        	return true;
        }
    	
    	if( year== 2005 ){
        	
        	return true;
        }
    	
    	if( year==  2017){
        	
        	return true;
        }
    	
    	return false;
    }
    
    public static boolean validCombine_stub(int day,int month,int year){
        
    	if(day== 10 &&month== 9 &&year== 2005 ){
        	
        	return true;
        }
    	
    	if(day== 10 &&month== 9 &&year==  2052){
        	
        	return true;
        }
    	
    	if(day== 29 &&month==2  &&year== 1900 ){
        	
        	return false;
        }
    	
    	if(day== 10 &&month== 15 &&year== 2005 ){
        	
        	return true;
        }
    	
    	if(day== 10 &&month== 0 &&year== 2005 ){
        	
        	return true;
        }
    	
    	if(day== 35 &&month== 9 &&year== 2005 ){
        	
        	return true;
        }
    	
    	if(day== 0 &&month== 9 &&year== 2005 ){
        	
        	return true;
        }
    	
    	if(day== 2 &&month== 8 &&year==  2017){
        	
        	return true;
        }
    	
    	return false;
    }
    */
    //综合判断各函数的有效性
	/*
    public static boolean validate(int day,int month,int year) {
		
		if(!validDayRange_stub(day)) return false;
		if(!validMonthRange_stub(month)) return false;
		if(!validYearRange_stub(year)) return false;
		if(!validCombine_stub(day,month,year)) return false;
		return true;
	}
	*/
	public static boolean validate(int day,int month,int year) {
		
		if(!validDayRange(day)) return false;
		if(!validMonthRange(month)) return false;
		if(!validYearRange(year)) return false;
		if(!validCombine(day,month,year)) return false;
		return true;
	}
}
