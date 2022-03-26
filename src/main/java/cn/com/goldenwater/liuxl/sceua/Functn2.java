package cn.com.goldenwater.liuxl.sceua;

/**
 * Created by 刘祥林 on 2022/3/26.
 * %  This is the Rosenbrock Function
 * %  Bound: X1=[-5,5], X2=[-2,8]
 * %  Global Optimum: 0,(1,1)
 */
public class Functn2 {
    public static double functn(int nopt, double[] x) {
        double x1 = x[0];
        double x2 = x[1];
        int a = 100;
//        System.out.println("这里作为单站预报" + BB.i ++);
        return a * Math.pow(x2 - Math.pow(x1, 2), 2) + Math.pow(1 - x1, 2);
    }
}
