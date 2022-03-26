package cn.com.goldenwater.liuxl.sceua;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.com.goldenwater.liuxl.sceua.Functn2.functn;
import static cn.com.goldenwater.liuxl.sceua.M.*;

/**
 * Created by 刘祥林 on 2022/3/26.
 */
public class Sceua {
    List<Double> BESTF = new ArrayList<>();
    List<Double[]> BESTX = new ArrayList<>();
    List<Integer> ICALL = new ArrayList<>();
    double[][] PX;
    double[] PF;

    /**
     * @param x0     = the initial parameter array at the start;
     *               = the optimized parameter array at the end;
     *               f0 = the objective function value corresponding to the initial parameters
     *               = the objective function value corresponding to the optimized parameters
     * @param bl     = the lower bound of the parameters
     * @param bu     = the upper bound of the parameters
     * @param iseed  = the random seed number (for repetetive testing purpose)
     * @param iniflg = flag for initial parameter array (=1, included it in initial
     *               population; otherwise, not included)
     * @param ngs    = number of complexes (sub-populations)
     *               npg = number of members in a complex
     *               nps = number of members in a simplex
     *               nspl = number of evolution steps for each complex before shuffling
     *               mings = minimum number of complexes required during the optimization process
     * @param maxn   = maximum number of function evaluations allowed during optimization
     * @param kstop  = maximum number of evolution loops before convergency
     *               percento = the percentage change allowed in kstop loops before convergency
     */
    public BB sceua(double[] x0, double[] bl, double[] bu, int maxn, int kstop, double pcento, double peps, int ngs, int iseed, int iniflg) {
//        % Initialize SCE parameters:
        int nopt = x0.length;
        int npg = 2 * nopt + 1;
        int nps = nopt + 1;
        int nspl = npg;
        int mings = ngs;
        int npt = npg * ngs;

        double[] bound = subtract(bu, bl);

//% Create an initial population to fill array x(npt,nopt):
//        rand('seed', iseed);
        double[][] x = new double[npt][nopt]; // x(.,.) = coordinates of points in the population
        for (int i = 0; i < npt; i++) {
            x[i] = add(bl, multiply(rand1(nopt), bound));
        }

        if (iniflg == 1) {
            x[1] = x0;
        }

        int nloop = 0;
        int icall = 0;

        double[] xf = new double[npt]; // xf(.) = function values of x(.,.)
        for (int i = 0; i < npt; i++) {
            xf[i] = functn(nopt, x[i]); // 执行预报？ // todo
            icall = icall + 1;
        }
        double f0 = xf[0];

//        % Sort the population in order of increasing function values;
//[xf,idx]=sort(xf);
        Map sort = sortReturnMap(xf);
        xf = (double[]) sort.get("dd");
        int[] idx = (int[]) sort.get("seq");
        x = getByIdx(x, idx);


//        % Record the best and worst points;
        double[] bestx = x[0];
        double bestf = xf[0];
        double[] worstx = x[npt - 1];
        double worstf = xf[npt - 1];
        BESTF.add(bestf);
        Double[] xxx2 = new Double[bestx.length];
        for (int i = 0; i < bestx.length; i++) {
            xxx2[i] = bestx[i];
        }
        BESTX.add(xxx2);
        ICALL.add(icall);

        // todo

//% Compute the standard deviation for each parameter
//        xnstd=std(x);
        double[] xnstd = new double[x[0].length];
        for (int i = 0; i < xnstd.length; i++) {
            xnstd[i] = std(getListByIdx(x, i));
        }


//        % Computes the normalized geometric range of the parameters
//        gnrng=exp(mean(log((max(x)-min(x))./bound)));


        double gnrng = gnrng(x, bound);


        System.out.println("The Initial Loop: 0");
        System.out.println("BESTF  : " + num2str(bestf));
        System.out.println("BESTX  : " + num2str(bestx));
        System.out.println("WORSTF : " + num2str(worstf));
        System.out.println("WORSTX : " + num2str(worstx));
        System.out.println(" ");


//% Check for convergency;
        if (icall >= maxn) {
            System.out.println("*** OPTIMIZATION SEARCH TERMINATED BECAUSE THE LIMIT");
            System.out.println("ON THE MAXIMUM NUMBER OF TRIALS ");
            System.out.println(maxn);
            System.out.println("HAS BEEN EXCEEDED.  SEARCH WAS STOPPED AT TRIAL NUMBER:");
            System.out.println(icall);
            System.out.println("OF THE INITIAL LOOP!");
        }

        if (gnrng < peps) {
            System.out.println("THE POPULATION HAS CONVERGED TO A PRESPECIFIED SMALL PARAMETER SPACE");
        }

//        % Begin evolution loops:
        nloop = 0;
//        double[] criter = {};
        List<Double> criter = new ArrayList<Double>();
        double criter_change = 1e+5;

        while (icall < maxn && gnrng > peps && criter_change > pcento) {
            nloop = nloop + 1;
//            % Loop on complexes (sub-populations);
            for (int igs = 0; igs < ngs; igs++) {
//                % Partition the population into complexes (sub-populations);
                int[] k1 = new int[npg];
                int[] k2 = new int[npg];
                for (int i = 1; i <= npg; i++) {
//                    k1=1:npg;
//                    k2=(k1-1)*ngs+igs;
                    k1[i-1] = i;
                }
                k2 = add(multiply(subtract(k1, 1), ngs), igs+1);
                k1 = subtract(k1, 1);
                k2 = subtract(k2, 1);

//                cx(k1,:) = x(k2,:);
                double[][] cx = getByIdx(x, k2);
//                cf(k1) = xf(k2);
                double[] cf = getByIdx(xf, k2);

                for (int loop = 0; loop < nspl; loop++) {
//                    % Select simplex by sampling the complex according to a linear
//            % probability distribution
                    int[] lcs = new int[nps];
                    lcs[0] = 1;
                    for (int k3 = 1; k3 < nps; k3++) {
                        int lpos = 0;
                        for (int iter = 1; iter <= 1000; iter++) {
                            lpos = (int) (npg+0.5-Math.sqrt(Math.pow(npg+0.5,2) - npg*(npg+1)*Math.random()));
//                            idx=find(lcs(1:k3-1)==(int)lpos); if isempty(idx); break; end;
                            idx = findNo0(findDataAs01(lcs, lpos, k3));
                            if(idx.length == 0) {
                                break;
                            }
                        }
                        lcs[k3] = lpos;
                    }

//                    lcs=Masort(lcs);
                    Arrays.sort(lcs);

//            % Construct the simplex:
//                    s = zeros(nps,nopt);
//                    double[][] s = new double[nps][nopt];
//                    s=cx(lcs,:); sf = cf(lcs);
                    double[][] s = getByIdx(cx, lcs);
                    double[] sf = getByIdx(cf, lcs);

//            [snew,fnew,icall]=cceua(s,sf,bl,bu,icall,maxn);
                    Cceua.SFI sfi = new Cceua().cceua(s, sf, bl, bu, icall, maxn);

//            % Replace the worst point in Simplex with the new point:
//                    s(nps,:) = snew; sf(nps) = fnew;
                    s[nps - 1] = sfi.getSnew();
                    sf[nps - 1] = sfi.getFnew();
                    icall = sfi.icall;

//            % Replace the simplex into the complex;
//                    cx(lcs,:) = s;
                    setByIdx(cx, lcs, s);

//                    cf(lcs) = sf;
                    setByIdx(cf, lcs, sf);


//            % Sort the complex;
//            [cf,idx] = sort(cf); cx=cx(idx,:);
                    Map map = sortReturnMap(cf);
                    cf = (double[]) map.get("dd");
                    idx = (int[]) map.get("seq");

                    cx = getByIdx(cx, idx);
                }


//        % Replace the complex back into the population;
//                x(k2,:) = cx(k1,:);
                setByIdx(x, k2, getByIdx(cx, k1));
//                xf(k2) = cf(k1);
                setByIdx(xf, k2, getByIdx(cf, k1));
//                % End of Loop on Complex Evolution;
            }
//              % Shuffled the complexes;
//    [xf,idx] = sort(xf); x=x(idx,:);
            Map map = sortReturnMap(xf);
            xf = (double[]) map.get("dd");
            idx = (int[]) map.get("seq");
            PX=x; PF=xf;


//    % Record the best and worst points;
//            bestx=x(1,:); bestf=xf(1);
            bestx=x[0];
            bestf=xf[0];
//            worstx=x(npt,:); worstf=xf(npt);
            worstx=x[npt-1];
            worstf=xf[npt-1];
//            BESTX=[BESTX;bestx];
            Double[] xxx = new Double[bestx.length];
            for (int i = 0; i < bestx.length; i++) {
                xxx[i] = bestx[i];
            }
            BESTX.add(xxx);


//            BESTF=[BESTF;bestf];
            BESTF.add(bestf);

//            ICALL=[ICALL;icall];
            ICALL.add(icall);


//    % Compute the standard deviation for each parameter
//            xnstd=std(x);
            for (int i = 0; i < xnstd.length; i++) {
                xnstd[i] = std(getListByIdx(x, i));
            }


//    % Computes the normalized geometric range of the parameters
//            gnrng=exp(mean(log((max(x)-min(x))./bound)));
            gnrng = gnrng(x, bound);


            System.out.println("Evolution Loop: " + (nloop) + "  - Trial - " +  (icall));
            System.out.println("BESTF  : " +  num2str(bestf));
            System.out.println("BESTX  : " +  num2str(bestx));
            System.out.println("WORSTF : " +  num2str(worstf));
            System.out.println("WORSTX : " +  num2str(worstx));
            System.out.println("  ");


//    % Check for convergency;
            if (icall >= maxn) {
                System.out.println("'*** OPTIMIZATION SEARCH TERMINATED BECAUSE THE LIMIT");
                System.out.println("['ON THE MAXIMUM NUMBER OF TRIALS "+ (maxn) +" HAS BEEN EXCEEDED!'");
            }

            if( gnrng < peps) {
                System.out.println("THE POPULATION HAS CONVERGED TO A PRESPECIFIED SMALL PARAMETER SPACE");
            }

//            criter=[criter;bestf]; //
            criter.add(bestf);

            if (nloop >= kstop) {
                criter_change = Math.abs(criter.get(nloop-1) - criter.get(nloop - kstop)) * 100;
//                criter_change = criter_change / mean(abs(criter(nloop - kstop + 1:nloop)));
                double avg = 0;
                for (int i = nloop - kstop; i < nloop; i++) {
                    avg += Math.abs(criter.get(i));
                }
                avg = avg / kstop;
                criter_change = criter_change / avg;
                if (criter_change<pcento ) {
                    System.out.println("THE BEST POINT HAS IMPROVED IN LAST " + num2str(kstop) + " LOOPS BY LESS THAN THE THRESHOLD "+ num2str(pcento) +"%");
                    System.out.println("CONVERGENCY HAS ACHIEVED BASED ON OBJECTIVE FUNCTION CRITERIA!!!");
                }
            }

//            % End of the Outer Loops
        }

        System.out.println("SEARCH WAS STOPPED AT TRIAL NUMBER: " + (icall));
        System.out.println("NORMALIZED GEOMETRIC RANGE = " + num2str(gnrng));
        System.out.println("THE BEST POINT HAS IMPROVED IN LAST " + (kstop) + " LOOPS BY " + num2str(criter_change) + "%");

//% END of Subroutine sceua
        BB bb = new BB();
        bb.setBestf(bestf);
        bb.setBestx(bestx);
        return bb;
    }

    class BB {
        double[] bestx;
        double bestf;

        public double[] getBestx() {
            return bestx;
        }

        public void setBestx(double[] bestx) {
            this.bestx = bestx;
        }

        public double getBestf() {
            return bestf;
        }

        public void setBestf(double bestf) {
            this.bestf = bestf;
        }
    }



}


