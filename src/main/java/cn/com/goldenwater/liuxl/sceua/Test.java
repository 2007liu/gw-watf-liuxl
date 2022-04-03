package cn.com.goldenwater.liuxl.sceua;

/**
 * Created by 刘祥林 on 2022/3/26.
 */
public class Test {
    public static void main(String[] args) {
        int ngs = 4;
        int fiunc = 2;
        double[] bl = {-5, -5};
        double[] bu = {5, 5};
        double[] x0 = {1, 1};
        int maxn = 10000;
        int kstop = 10;
        double pcento = 0.1;
        double peps = 0.001;
        int iseed = -1;
        int iniflg = 0;

        Sceua sceua = new Sceua();
        Sceua.BB bb = sceua.sceua(x0, bl, bu, maxn, kstop, pcento, peps, ngs, iseed, iniflg);
    }
}
