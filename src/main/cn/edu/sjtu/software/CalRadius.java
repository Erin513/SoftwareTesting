package cn.edu.sjtu.software;

public class CalRadius {

	/**
	 * equal的桩函数
	 * @param num1
	 * @param num2
	 * @return
	 */
	/*
	public boolean equalStub(double num1, double num2){
		
		if(num1 == 4.0 && num2 == 12.0){
			return false;
		}
		
		if(num1 == 8.0 && num2 == 8.0){
			return true;
		}
		
		if(num1 == 4.0 && num2 == 8.0){
			return false;
		}
		
		if(num1 == 3.0 && num2 == 9.0){
			return false;
		}
		
		if(num1 == 5.0 && num2 == 7.0){
			return false;
		}
		
		if(num1 == 0.0 && num2 == 0.0){
			return true;
		}
		
		if(num1 == 3.0 && num2 == 3.0){
			return true;
		}
		if(num1 == 3.0 && num2 == 3.0000001){
			return true;
		}
		
		if(num1 == 3.0 && num2 == 2.9999999){
			return false;
		}
		
		if(num1 == 4.0 && num2 == 3.0){
			return false;
		}
		
		if(num1 == 3.5 && num2 == 4.5){
			return false;
		}
		
		
		return true;
	}
	*/
    /**
     * @param num1,num2
     */
    public boolean equal(double num1, double num2) {
    	
        if ((num1 - num2 > -0.000001) && (num1 - num2) < 0.000001) {
        	return true;
        }else {
        	return false;
        }
        	
    }

    public boolean isCollinear(Point p1, Point p2, Point p3) throws Exception {
    	
    	if(p1==null || p2==null || p3==null){
    		
    		throw new Exception();//如果输入为空，抛出异常
    	}
    	
        boolean boo = false;
        double len12 = Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
        double len13 = Math.sqrt((p1.x - p3.x) * (p1.x - p3.x) + (p1.y - p3.y) * (p1.y - p3.y));
        double len32 = Math.sqrt((p3.x - p2.x) * (p3.x - p2.x) + (p3.y - p2.y) * (p3.y - p2.y));
        
        /**
         * 此处使用了equal的转函数equalStub
         */
        if (equal(len12, len13 + len32) || equal(len13, len12 + len32) || equal(len32, len13 + len12)) {
        
        //if (equalStub(len12, len13 + len32) || equalStub(len13, len12 + len32) || equalStub(len32, len13 + len12)) {
        	
            boo = true;
        }
        return boo;
    }
    /**
     * isCollinear的桩函数
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    /*
    public boolean isCollinearStub(Point p1, Point p2, Point p3) {
        
    	if(p1.x==0.0 && p1.y==0.0 &&p2.x==0.0&&p2.y==4.0&&p3.x==0.0&&p3.y==8.0){
    		return true;
    	}
    	
    	if(p1.x==0.0 && p1.y==8.0 &&p2.x==0.0&&p2.y==8.0&&p3.x==0.0&&p3.y==8.0){
    		return true;
    	}
    	
    	if(p1.x==0.0 && p1.y==0.0 &&p2.x==0.0&&p2.y==4.0&&p3.x==3.0&&p3.y==0.0){
    		return false;
    	}
    	
    	if(p1.x==0.0 && p1.y==0.0 &&p2.x==3.0&&p2.y==0.0&&p3.x==0.0&&p3.y==4.0){
    		return false;
    	}
    	
    	return true;
    }

  */
    public Circle calRadius(Point p1, Point p2, Point p3) throws Exception {
    	
    	/** 对isCollinear函数进行打桩 */
        if (isCollinear(p1, p2, p3)/* isCollinearStub(p1, p2, p3) */== true) {
            System.out.println("三点共线");
            return null;
        } else {
            Point center = new Point(0, 0);
            double x1 = p1.x, y1 = p1.y;
            double x2 = p2.x, y2 = p2.y;
            double x3 = p3.x, y3 = p3.y;
            double a, b, c, d;
            a = (x1 * x1 + y1 * y1 - x2 * x2 - y2 * y2) * (x1 - x3) / 2;
            b = (x1 * x1 + y1 * y1 - x3 * x3 - y3 * y3) * (x1 - x2) / 2;
            c = (y1 - y2) * (x1 - x3);
            d = (y1 - y3) * (x1 - x2);
            center.y = (a - b) / (c - d);  //外心的纵坐标
            double e, f;
            if (x1 != x2) {   //防止出现两点的横坐标相等的情况
                e = (x1 * x1 + y1 * y1 - x2 * x2 - y2 * y2) / (2 * (x1 - x2));
                f = (y1 - y2) / (x1 - x2);
                center.x = e - f * center.y;  //外心的横坐标
            } else if (x1 != x3) {
                e = (x1 * x1 + y1 * y1 - x3 * x3 - y3 * y3) / (2 * (x1 - x3));
                f = (y1 - y3) / (x1 - x3);
                center.x = e - f * center.y;
            }

            System.out.println("外心为(" + center.x + "," + center.y + ")");
            double x0 = center.x, y0 = center.y;
            double radius = Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
            ;
            System.out.println("半径为" + radius);
            return new Circle(center, radius);
        }
    }

    /*public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入坐标");
        double a1, a2, a3, b1, b2, b3;
        a1 = scanner.nextDouble();
        b1 = scanner.nextDouble();
        a2 = scanner.nextDouble();
        b2 = scanner.nextDouble();
        a3 = scanner.nextDouble();
        b3 = scanner.nextDouble();
        Point p1 = new Point(a1, b1);
        Point p2 = new Point(a2, b2);
        Point p3 = new Point(a3, b3);
        CalRadius t = new CalRadius();

        t.calRadius(p1, p2, p3);

    }*/

}
