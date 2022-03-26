package cn.com.goldenwater.liuxl.sceua;

import static cn.com.goldenwater.liuxl.sceua.Functn2.functn;
import static cn.com.goldenwater.liuxl.sceua.M.*;

/**
 * Created by 刘祥林 on 2022/3/26.
 */
public class Cceua {
    /**
     * %  This is the subroutine for generating a new point in a simplex
     * %
     * %   s(.,.) = the sorted simplex in order of increasing function values
     * %   s(.) = function values in increasing order
     * %
     * % LIST OF LOCAL VARIABLES
     * %   sb(.) = the best point of the simplex
     * %   sw(.) = the worst point of the simplex
     * %   w2(.) = the second worst point of the simplex
     * %   fw = function value of the worst point
     * %   ce(.) = the centroid of the simplex excluding wo
     * %   snew(.) = new point generated from the simplex
     * %   iviol = flag indicating if constraints are violated
     * %         = 1 , yes
     * %         = 0 , no
     *
     * @param s
     * @param sf
     * @param bl
     * @param bu
     * @param icall
     * @param maxn
     * @return
     */
    public SFI cceua(double[][] s, double[] sf, double[] bl, double[] bu, int icall, int maxn) {
        SFI sfi = new SFI();
        int nps = s.length;
        int nopt = s[0].length;

        int n = nps;
        int m = nopt;
        double alpha = 1.0;
        double beta = 0.5;

//      % Assign the best and worst points:
        double[] sb = s[0];
        double fb = sf[0];
        double[] sw = s[n - 1];
        double fw = sf[n - 1];

//      % Compute the centroid of the simplex excluding the worst point:
//        ce=mean(s(1:n-1,:));
        double[] ce = avg(s, n - 1);

//        % Attempt a reflection point
//        snew = ce + alpha*(ce-sw);
        double[] snew = add(ce, multiply(subtract(ce, sw), alpha));

//        % Check if is outside the bounds:
        int ibound=0;
//        s1=snew-bl; idx=find(s1<0); if ~isempty(idx); ibound=1; end;
        double[] s1 = subtract(snew, bl);
        int[] idx = findLE0(s1);
        if(idx.length != 0) {
            ibound = 1;
        }

//        s1=bu-snew; idx=find(s1<0); if ~isempty(idx); ibound=2; end;
        s1 = subtract(bu, snew);
        idx = findLE0(s1);
        if(idx.length != 0) {
            ibound = 2;
        }

        if( ibound >=1) {
//            snew = bl + rand(1, nopt). * (bu - bl);
            snew = add(bl, multiply(rand1(nopt), subtract(bu, bl)));
        }

        double fnew = 0;
        fnew = functn(nopt,snew);// todo liuxl
        icall = icall + 1;

//% Reflection failed; now attempt a contraction point:
        if (fnew > fw) {
//            snew = sw + beta * (ce - sw);
            snew = add(sw, multiply(subtract(ce, sw), beta));
            fnew = functn(nopt, snew); // todo liuxl
            icall = icall + 1;

//%Both reflection and contraction have failed, attempt a random point;
            if( fnew > fw) {
//                snew = bl + rand(1, nopt). * (bu - bl);
                snew = add(bl, multiply(rand1(nopt), subtract(bu, bl)));
                fnew = functn(nopt, snew); // todo liuxl
                icall = icall + 1;
            }
        }

        sfi.setFnew(fnew);
        sfi.setIcall(icall);
        sfi.setSnew(snew);
//        % END OF CCE
        return sfi;
    }

    class SFI {
        double[] snew;
        double fnew;
        int icall;

        public double[] getSnew() {
            return snew;
        }

        public void setSnew(double[] snew) {
            this.snew = snew;
        }

        public double getFnew() {
            return fnew;
        }

        public void setFnew(double fnew) {
            this.fnew = fnew;
        }

        public int getIcall() {
            return icall;
        }

        public void setIcall(int icall) {
            this.icall = icall;
        }
    }

}
