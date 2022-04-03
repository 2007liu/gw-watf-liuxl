package cn.com.goldenwater.liuxl.model.P_RWLL;

import cn.com.goldenwater.utils.FileUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;

/**
 * Created by liuxl.auxuan on 2019/4/20.
 */
public class P_RWLL {

	static int N, M;

	public static void main(String[] args) {
		String TEM = "C:\\071051602\\D\\6010698011.tem";

		double RN1, RN2, RN3;
		int IY, IM, ID, IH;
		int EY, EM, ED, EH;
		int LENGTH;
		int[] TY = new int[45000];
		int[] TM = new int[45000];
		int[] TD = new int[45000];
		int[] TH = new int[45000];
		int[] iy = new int[1500];

		int FLAG, DT, N_POINT;

		double IMA, L31 = 0; //L31 todo Double是值引用
		double[] RAT = new double[12];

		String TMP_STR, Fil_name, T_CHAR;
		double[] P0 = new double[50];
		double[] R = new double[50];
		double[] P = new double[45000];
		double[] RUNOFF = new double[45000];
		double[] RUNOF = new double[45000];

		int[] im = new int[1500];
		int[] id = new int[1500];
		int[] ih = new int[1500];

		String Station;
		String F_PAR, F_STS, F_DIS, F_DOB, F_RNP, F_RNA, F_RNO, F_STN, F_OUT;

		ArrayList<String> arrayList = FileUtils.executeList(TEM);
		int i = 0;
		F_PAR = arrayList.get(i++);
		F_STS = arrayList.get(i++);
		F_DIS = arrayList.get(i++);
		F_DOB = arrayList.get(i++);
		F_RNP = arrayList.get(i++);
		F_RNA = arrayList.get(i++);
		F_RNO = arrayList.get(i++);
		F_STN = arrayList.get(i++);
		F_OUT = arrayList.get(i++);

		// 读取参数
		FLAG = 1;// 查曲线方式代码，等于1表示用累计雨量查曲线，不等于1表示用时段雨量查曲线//
		IMA = 80;// 土壤最大初损量Im//
		RAT = new double[] { 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9 };// 12个月的土壤含水量日衰减系数KD//
		// ?
		M = 9;
		P0 = new double[] { 0, 10, 20, 30, 40, 50, 60, 70, 80 };
		R = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		// ?
		// 读取参数

		// 读取状态：
		double PA0 = 20;
		// 读取状态：

		// 读取面雨量
		ArrayList<String> F2 = FileUtils.executeList(F_RNA);
		int F2Size = F2.size();
		String[] F2Line1List = F2.get(0).trim().split("\\s+");
		int i1 = 0;
		IY = NumberUtils.toInt(F2Line1List[i1++]);
		IM = NumberUtils.toInt(F2Line1List[i1++]);
		ID = NumberUtils.toInt(F2Line1List[i1++]);
		IH = NumberUtils.toInt(F2Line1List[i1++]);

		EY = NumberUtils.toInt(F2Line1List[i1++]);
		EM = NumberUtils.toInt(F2Line1List[i1++]);
		ED = NumberUtils.toInt(F2Line1List[i1++]);
		EH = NumberUtils.toInt(F2Line1List[i1++]);

		LENGTH = NumberUtils.toInt(F2Line1List[i1++]);
		DT = NumberUtils.toInt(F2Line1List[i1++]);

		System.out.println(IY + "\t" + IM + "\t" + ID + "\t" + IH + "\t" + EY + "\t" + EM + "\t" + ED + "\t" + EH + "\t"
				+ LENGTH + "\t" + DT + "\t" + 1);
		for (int ii = 1; ii <= LENGTH; ii++) {
			F2Line1List = F2.get(ii).trim().split("\\s+");
			String YEAR = F2Line1List[0];
			String MONTH = F2Line1List[1];
			String DAY = F2Line1List[2];
			String HOUR = F2Line1List[3];
			String _P = F2Line1List[4];

			TY[ii] = NumberUtils.toInt(YEAR);
			TM[ii] = NumberUtils.toInt(MONTH);
			TD[ii] = NumberUtils.toInt(DAY);
			TH[ii] = NumberUtils.toInt(HOUR);
			P[ii] = NumberUtils.toInt(_P);

		}

		double PA_new = PA0;
		// IF(FLAG.EQ.1) THEN //todo 202201229
		if (FLAG == 1) {

			for (int I = 1; I <= LENGTH; I++) {
				P[I] = P[I] + P[I - 1];
			}

			L3(PA0, M, P0, R, L31);
			RUNOF[0] = L31;
			P[0] = 0;
		}

		for (int L = 1; L <= LENGTH; L++) {
			int MON_TMP = TM[L];
			double RATIO = RAT[MON_TMP];
			if (P[L] < 0)
				P[L] = 0.0;

			if (FLAG != 1) {
				L3(PA0, M, P0, R, L31);
				RUNOFF[L - 1] = L31;

				L3(P[L] + PA0, M, P0, R, L31);
				PA0 = Math.pow(RATIO, DT / 24.) * (PA0 + P[L]);
				if (PA0 > IM)
					PA0 = IM;
				RUNOFF[L] = L31;

				RUNOFF[L] = RUNOFF[L] - RUNOFF[L - 1];
			}

			else {
				L3(P[L] + PA0, M, P0, R, L31);
				RUNOF[L] = L31;
				PA_new = Math.pow(RATIO, DT / 24.) * (PA_new + P[L]);
				if (PA_new > IM)
					PA_new = IM;
				RUNOFF[L] = RUNOF[L] - RUNOF[L - 1];
			}
			// WRITE(4,*)TY(L),TM(L),TD(L),TH(L),RUNOFF(L);}
			// WRITE(4,*)TY(L),TM(L),TD(L),TH(L),RUNOFF(L)

			System.out.println(TY[L] + "\t" + TM[L] + "\t" + TD[L] + "\t" + TH[L] + "\t" + RUNOFF[L]);
		}

		if (FLAG == 1) {
			PA0 = PA_new;
		}

		// 写入最终的状态stn
		// OPEN(5,FILE=f_stn,STATUS="unknown")
		// WRITE(5,*) '土壤前期含水量'
		// WRITE(5,*) 'PA0= ',PA0
		// CLOSE(5)
	}

	public static void L3(double x, int N, double[] X0, double[] Y0, double L31) {
		if (N > 2) {
			int N1;
			int N2;
			if (x < X0[1]) {
				x = X0[1];
			} else if (x > X0[N]) {
				x = X0[N];
			}
			int i = 2;
			while (x >= X0[i]) {
				i = i + 1;
			}

			if (i != N) {
				// GOTO 32
				if (i != 2) {
					N1 = i - 2;
					N2 = i + 1;
					// 38
				} else {
					N1 = 1;
					N2 = 3;
					// GOTO 38
				}
			} else {
				N1 = N - 2;
				N2 = N;
				// GOTO 38
			}

			// 38
			double L311 = 0.0;
			for (int j = N1; j <= N2; j++) {
				double P = 1.0;
				for (int k = N1; k <= N2; k++) {
					if (j - k != 0)
						P = P * (x - X0[k]) / (X0[j] - X0[k]);
				}
				L311 = L311 + P * Y0[j];
			}
			L31 = L311;
		} else {
			if (x < X0[1]) {
				L31 = Y0[1];
			} else if (x > X0[N]) {
				L31 = Y0[N];
			} else {
				L31 = Y0[1] + (x - X0[1]) * (Y0[N] - Y0[1]) / (X0[N] - X0[1]);
			}
		}
	}

}
