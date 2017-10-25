package cn.edu.sjtu.software;

public class DateUtil {

    public boolean isLeapYear(int year) {
        return false;
    }

    /**
     * Which week and which day in a week
     * @param year
     * @param month
     * @param day
     * @return an int array, first element is which week, second element is which day. null if wrong input.
     */
    public int[] weekInYear(int year, int month, int day) {
        return new int[2];
    }

    public boolean isValid(int year, int month, int day) {
        return true;
    }
}
