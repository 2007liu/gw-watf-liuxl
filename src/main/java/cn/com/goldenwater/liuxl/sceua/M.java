package cn.com.goldenwater.liuxl.sceua;

import java.util.*;

/**
 * 数组操作工具类
 * Created by 刘祥林 on 2022/3/26.
 */
public class M {

    /**
     * 数组相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double[] subtract(double[] d1, double[] d2) {
        double[] r = null;
        if (d1.length == d2.length) {
            r = new double[d1.length];
            for (int i = 0; i < d1.length; i++) {
                r[i] = d1[i] - d2[i];
            }
        }
        return r;
    }
    public static double[] subtract(double[] d1, double d2) {
        double[] r = new double[d1.length];
        for (int i = 0; i < d1.length; i++) {
            r[i] = d1[i] - d2;
        }
        return r;
    }
    public static int[] subtract(int[] d1, int d2) {
        int[] r = new int[d1.length];
        for (int i = 0; i < d1.length; i++) {
            r[i] = d1[i] - d2;
        }
        return r;
    }

    public static double avg(double[] d) {
        if (d != null && d.length >= 1) {
            double sum = 0;
            for (double v : d) {
                sum += v;
            }
            return sum / d.length;
        } else {
            return 0;
        }
    }

    public static double avg(double[] d, int max) {
        if (d != null && d.length >= 1) {
            double sum = 0;
            for (int i = 0; i < d.length && i < max; i++) {
                sum += d[i];
            }
            return sum / d.length;
        } else {
            return 0;
        }
    }

    public static double[] avg(double[][] d, int max) {
        if (d != null && d.length >= 1) {
            int len = d[0].length;
            double[] res = new double[len];
            for (int j = 0; j < len &&  j < max; j++) {
                double sum = 0;
                for (int i = 0; i < d.length; i++) {
                    sum += d[i][j];
                }
                res[j] = sum / d.length;
            }
            return res;
        } else {
            return new double[0];
        }
    }

    /**
     * 数组相乘
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double[] multiply(double[] d1, double[] d2) {
        double[] r = null;
        if (d1.length == d2.length) {
            r = new double[d1.length];
            for (int i = 0; i < d1.length; i++) {
                r[i] = d1[i] * d2[i];
            }
        }
        return r;
    }
    public static int[] multiply(int[] d1, int[] d2) {
        int[] r = null;
        if (d1.length == d2.length) {
            r = new int[d1.length];
            for (int i = 0; i < d1.length; i++) {
                r[i] = d1[i] * d2[i];
            }
        }
        return r;
    }

    public static double[] multiply(double[] d1, double d2) {
        double[] r = new double[d1.length];
        for (int i = 0; i < d1.length; i++) {
            r[i] = d1[i] * d2;
        }
        return r;
    }

    public static int[] multiply(int[] d1, int d2) {
        int[] r = new int[d1.length];
        for (int i = 0; i < d1.length; i++) {
            r[i] = d1[i] * d2;
        }
        return r;
    }

    /**
     * 数组相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double[] add(double[] d1, double[] d2) {
        double[] r = null;
        if (d1.length == d2.length) {
            r = new double[d1.length];
            for (int i = 0; i < d1.length; i++) {
                r[i] = d1[i] + d2[i];
            }
        }
        return r;
    }

    public static double[] add(double[] d1, double d2) {
        double[] r = new double[d1.length];
        for (int i = 0; i < d1.length; i++) {
            r[i] = d1[i] + d2;
        }
        return r;
    }


    public static int[] add(int[] d1, int[] d2) {
        int[] r = null;
        if (d1.length == d2.length) {
            r = new int[d1.length];
            for (int i = 0; i < d1.length; i++) {
                r[i] = d1[i] + d2[i];
            }
        }
        return r;
    }

    public static int[] add(int[] d1, int d2) {
        int[] r = new int[d1.length];
        for (int i = 0; i < d1.length; i++) {
            r[i] = d1[i] + d2;
        }
        return r;
    }

    /**
     * 随机生成1*j 一维数组
     *
     * @param j
     * @return
     */
    public static double[] rand1(int j) {
        return rand(1, j)[0];
    }

    /**
     * 随机生成i*j
     *
     * @param j
     * @return
     */
    public static double[][] rand(int i, int j) {
        double[][] r = new double[i][j];
        for (int i1 = 0; i1 < i; i1++) {
            for (int i2 = 0; i2 < j; i2++) {
                r[i1][i2] = Math.random();
            }
        }
        return r;
    }


    public static double[] sort(double[] d) {
        double[] dd = Arrays.copyOf(d, d.length);
        Arrays.sort(dd);
        return dd;
    }

    /**
     * 排序
     *
     * @param d
     * @return r
     * r[0] 排序后的数组
     * r[1] 排序后的顺序值
     */
    public static Map sortReturnMap(double[] d) {
        Map map = new HashMap();
        Object[][] r = new Object[2][d.length];
        double[] dd = Arrays.copyOf(d, d.length);
        Arrays.sort(dd);
        map.put("dd", dd);

        // 在dd中找d的顺序
        int[] seq = new int[d.length];
        Map<Integer, Object> added = new HashMap<>();// 已添加过的顺序号，不能再添加到seq
        for (int i = 0; i < dd.length; i++) {
            double data = dd[i];
            for (int i1 = 0; i1 < d.length; i1++) {
                double v = d[i1];
                if (v == data && !added.containsKey(i1)) {
                    added.put(i1, null);
                    seq[i] = i1;
                    break;
                }
            }
        }
        map.put("seq", seq);

        return map;
    }

    /**
     * 根据序号返回数组
     *
     * @param d
     * @param seq
     * @return
     */
    public static double[][] getByIdx(double[][] d, int[] seq) {
        int len = seq.length;
        double[][] x = new double[len][];
        for (int i = 0; i < len; i++) {
            x[i] = d[seq[i]];
        }
        return x;
    }
    public static void setByIdx(double[][] d, int[] seq, double[][] data) {
        for (int i = 0; i < seq.length; i++) {
            d[seq[i]] = data[i];
        }
    }

    /**
     * 根据序号返回数组
     *
     * @param d
     * @param seq
     * @return
     */
    public static double[] getByIdx(double[] d, int[] seq) {
        int len = seq.length;
        double[] x = new double[len];
        for (int i = 0; i < len; i++) {
            x[i] = d[seq[i]];
        }
        return x;
    }

    public static void setByIdx(double[] d, int[] seq, double[] data) {
        for (int i = 0; i < seq.length; i++) {
            d[seq[i]] = data[i];
        }
    }

    /**
     * 行转列
     *
     * @param d
     * @param i
     * @return
     */
    public static double[] getListByIdx(double[][] d, int i) {
        int length = d.length;
        double[] r = new double[length];

        for (int i1 = 0; i1 < length; i1++) {
            r[i1] = d[i1][i];
        }
        return r;
    }

    /**
     * 找到<0的数据的索引
     * find([1, 0, 0, 2])
     *  1     4
     * @param d
     * @return
     */
    public static int[] findLE0(double[] d) {
        if(d == null || d.length == 0) {
            return new int[0];
        }

        List list = new ArrayList();
        for (int i = 0; i < d.length; i++) {
            if(d[i] < 0) {
                list.add(i);
            }
        }

        if (list.size() >= 1) {
            int[] res = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                res[i] = (int) list.get(i);
            }
            return res;
        }
        return new int[0];
    }

    /**
     * 找到不为0的数据的索引
     * find([1, 0, 0, 2])
     *  1     4
     * @param d
     * @return
     */
    public static int[] findNo0(int[] d) {
        if(d == null || d.length == 0) {
            return new int[0];
        }

        List list = new ArrayList();
        for (int i = 0; i < d.length; i++) {
            if(d[i] == 0) {
            }else {
                list.add(i);
            }
        }

        if (list.size() >= 1) {
            int[] res = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                res[i] = (int) list.get(i);
            }
            return res;
        }
        return new int[0];
    }

    /**
     * 返回d中等于data的为1，否则为0
     * @param d
     * @param data
     * @return
     */
    public static int[] findDataAs01(int[] d, int data, int maxlen) {
        if(d == null || d.length == 0) {
            return new int[0];
        }

        int[] idx = new int[maxlen];
        for (int i = 0; i < d.length && i < maxlen; i++) {
            if(data == d[i]) {
                idx[i] = 1;
            }else {
                idx[i] = 0;
            }
        }

        return idx;
    }

    /**
     * 标准平方差
     *
     * @param d
     */
    public static double std(double[] d) {
        if (d.length == 0) {
            return 0;
        }
        double avg = 0;
        double sum = 0;
        for (double v : d) {
            sum += v;
        }
        avg = sum / d.length;

        sum = 0;
        for (double v : d) {
            double sqrt = Math.pow(Math.abs(v - avg), 2);

            sum += sqrt;
        }

        return Math.sqrt(sum / (d.length - 1));
    }

    public static double gnrng(double[][] x, double[] bound) {

        double[] t1 = new double[bound.length];
        for (int i = 0; i < bound.length; i++) {
            double[] t2 = getListByIdx(x, i);
            double[] sorted = sort(t2);
            double v = Math.log((sorted[sorted.length - 1] - sorted[0]) / bound[i]);
            t1[i] = v;
        }

        return Math.exp(avg(t1));

    }


    public static String num2str(double... d) {
        String s = "";
        for (double v : d) {
            s += "\t" + v;
        }
        return s;
    }

    public static void main(String[] args) {
//        double[] dd = rand1(10);
//        Map sort = sort(dd);
//        System.out.println();
//        double[][] dd = rand(10, 2);
//        double[][]
//                d = new double[][]{
//                {1.16497922510141, -0.194163720679546},
//                {-1.71149384077242, 4.92301813788852},
//                {-0.327672374121693, 2.81040813671910},
//                {1.16584349245105, 4.33157762481439},
//                {-2.55605322195033, 0.413498680765507},
//                {-3.30965346112366, 4.65427889472539},
//                {-1.79296405836612, -4.34692895940828},
//                {4.46538364955475, -0.297001933351625},
//                {4.99992173630741, 3.68462211856834},
//                {4.52955383785514, -1.78864716868319}
//        };
//
////        System.out.println(std(getListByIdx(d, 0)));
////        System.out.println(std(getListByIdx(d, 1)));
//        double[] b = {10, 10};
//        System.out.println(gnrng(d, b));

        double[][] d = {{-1.5392, 0.8400}, {-0.3277, 2.8104}};
        double[] avg = avg(d, 2);
        for (double v : avg) {
            System.out.println(v);
        }

    }

}
