package cn.com.goldenwater.liuxl.model.P_RZHJR;

import cn.com.goldenwater.utils.FileUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;

/**
 * Created by liuxl.auxuan on 2019/4/20.
 */
public class P_RZHJR {

	static int N, M;

	public static void main(String[] args) {
		String TEM = "C:\\071051602\\D\\6010698011.tem";

		double RN1, RN2, RN3;
		int IY, IM, ID, IH;
		int EY, EM, ED, EH;
		int LENGTH;
		int[] TY = new int[1000];
		int[] TM = new int[1000];
		int[] TD = new int[1000];
		int[] TH = new int[1000];
		int[] iy = new int[1500];

		int FLAG, DT;
		int[] N_POINT = new int[100];

		double IMA;
		double[] RATIO = new double[12];

		double[] PA0 = new double[50];
		double[] P0 = new double[100];
		double[][] R0 = new double[100][50];
		double[] P = new double[1000];
		double[] RUNOFF = new double[1000];

		int[] im = new int[1500];
		int[] id = new int[1500];
		int[] ih = new int[1500];

		String TMP_STR, Fil_name, T_CHAR;

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
		RATIO = new double[] { 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9, 0.9 };// 12个月的土壤含水量日衰减系数KD//
		N = 9;// P～R曲线条数N//
		PA0 = new double[] { 0, 10, 20, 30, 40, 50, 60, 70, 80 };
		M = 21;// 每条P～R曲线的节点数M//
		N_POINT[1] = 1;
		P0[1] = 0;
		R0[1] = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		N_POINT[2] = 2;
		P0[1] = 10;
		R0[2] = new double[] { 0.7, 1.4, 2.1, 3.0, 4.1, 5.4, 6.8, 8.1, 9.5 };
		N_POINT[3] = 3;
		P0[1] = 20;
		R0[3] = new double[] { 0.9, 2.4, 4.0, 5.9, 8.0, 10.3, 12.9, 15.7, 18.8 };
		N_POINT[4] = 4;
		P0[1] = 30;
		R0[4] = new double[] { 1.4, 3.7, 6.5, 9.8, 13.0, 16.5, 20.3, 24.2, 28.4 };
		N_POINT[5] = 5;
		P0[1] = 40;
		R0[5] = new double[] { 2.3, 5.6, 9.6, 14.1, 18.8, 23.6, 28.5, 33.3, 38.4 };
		N_POINT[6] = 6;
		P0[1] = 50;
		R0[6] = new double[] { 3.6, 9.0, 14.6, 20.2, 25.8, 31.4, 37.0, 42.7, 48.5 };
		N_POINT[7] = 7;
		P0[1] = 60;
		R0[7] = new double[] { 5.5, 12.4, 19.3, 26.0, 32.6, 39.3, 45.7, 52.2, 58.6 };
		N_POINT[8] = 8;
		P0[1] = 70;
		R0[8] = new double[] { 8.8, 16.8, 24.6, 32.1, 39.6, 46.9, 54.2, 61.5, 68.8 };
		N_POINT[9] = 9;
		P0[1] = 80;
		R0[9] = new double[] { 13.2, 21.9, 30.2, 38.4, 46.6, 54.9, 63.1, 71.3, 79.0 };
		N_POINT[10] = 10;
		P0[1] = 90;
		R0[10] = new double[] { 17.5, 26.8, 36.0, 45.3, 54.6, 63.3, 72.1, 80.8, 89.0 };
		N_POINT[11] = 11;
		P0[1] = 100;
		R0[11] = new double[] { 23.4, 33.7, 43.5, 53.3, 62.6, 71.8, 81.0, 90.3, 99.0 };
		N_POINT[12] = 12;
		P0[1] = 110;
		R0[12] = new double[] { 30.9, 41.2, 51.5, 61.3, 71.0, 80.8, 90.6, 99.8, 109.0 };
		N_POINT[13] = 13;
		P0[1] = 120;
		R0[13] = new double[] { 39.1, 49.3, 59.5, 69.7, 79.9, 90.1, 99.7, 108.8, 119.0 };
		N_POINT[14] = 14;
		P0[1] = 130;
		R0[14] = new double[] { 47.4, 58.1, 68.8, 79.4, 89.6, 99.7, 109.8, 119.4, 129.0 };
		N_POINT[15] = 15;
		P0[1] = 140;
		R0[15] = new double[] { 57.3, 67.9, 78.5, 89.1, 99.7, 109.8, 119.9, 129.5, 139.0 };
		N_POINT[16] = 16;
		P0[1] = 150;
		R0[16] = new double[] { 67.6, 78.2, 88.8, 99.3, 109.9, 119.9, 130.0, 139.5, 149.0 };
		N_POINT[17] = 17;
		P0[1] = 160;
		R0[17] = new double[] { 77.9, 88.5, 99.0, 109.5, 120.0, 130.0, 140.0, 149.5, 159.0 };
		N_POINT[18] = 18;
		P0[1] = 170;
		R0[18] = new double[] { 88.2, 98.7, 109.2, 119.7, 130.2, 140.1, 150.1, 159.6, 169.0 };
		N_POINT[19] = 19;
		P0[1] = 180;
		R0[19] = new double[] { 98.4, 108.9, 119.3, 129.8, 140.3, 150.2, 160.2, 169.6, 179.0 };
		N_POINT[20] = 20;
		P0[1] = 190;
		R0[20] = new double[] { 108.6, 119.0, 129.5, 139.9, 150.4, 160.3, 170.2, 179.6, 189.0 };
		N_POINT[21] = 21;
		P0[1] = 200;
		R0[21] = new double[] { 118.8, 129.2, 139.6, 150.0, 160.5, 170.3, 180.2, 189.6, 199.0 };
		// 读取参数

		// 状态：
		double PA = 20;
		if (PA > IMA) {
			PA = IMA;
		}
		// 状态：
		// 写初始状态文件
		// OPEN(2,FILE=f_sts,STATUS="unknown")
		// WRITE(2,*)"前期土壤含水量"
		// WRITE(2,200)"PA=",IMA
		// CLOSE(2)
		// 写初始状态文件 //出错的话，写 WRITE(2,200)"PA=",10.0
		// 如果状态读取失败则设置为0.0

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

		// CALL PPAR(LENGTH,M,N,IMA,RATIO,DT,PA0, P0,R0,PA,TM,P,RUNOFF,FLAG)
		PPAR(LENGTH, M, N, IMA, RATIO, DT, PA0, P0, R0, PA, TM, P, RUNOFF, FLAG);

		// OPEN(14,FILE=f_OUT)
		System.out.println(IY + "\t" + IM + "\t" + ID + "\t" + IH + "\t" + EY + "\t" + EM + "\t" + ED + "\t" + EH + "\t"
				+ LENGTH + "\t" + DT + "\t" + 1);

		for (int I = 1; I <= LENGTH; I++) {
			System.out.println(TY[I] + "\t" + TM[I] + "\t" + TD[I] + "\t" + TH[I] + "\t" + RUNOFF[I]);
		}

		// 写入最终的状态stn
		// OPEN(5,FILE=f_stn,STATUS="unknown")
		// WRITE(5,*)"前期土壤含水量"
		// WRITE(5,200)"PA=",PA
		// CLOSE(5)
	}

	public static void PPAR(int LENGTH, int M, int N, double IMA, double[] RAT, int DELTAT, double[] PA0, double[] P0,
			double[][] R0, double PA, int[] TM, double[] P, double[] RUNOFF, int FLAG) {
		double PA_new = PA;
		if (FLAG == 1) {
			for (int I = 2; I <= LENGTH; I++) {
				P[I] += P[I - 1];
			}
		}
		for (int L = 1; L <= LENGTH; L++) {
			int MON_TMP = TM[L];
			double RATIO = RAT[MON_TMP];
			if (P[L] < 0) {
				P[L] = 0;
			}

			int N1 = 0; // init 0? todo
			// IF(PA.LT.PA0(1)) THEN
			if (PA < PA0[1]) {
				PA = PA0[1];
				N1 = 1;
				// DO 20 I=1,M-1
				for (int I = 1; I <= M - 1; I++) {
					if (P[L] >= P0[I] && P[L] <= P0[I + 1]) {
						RUNOFF[L] = R0[I][N1] + (P[L] - P0[I]) * (R0[I + 1][N1] - R0[I][N1]) / (P0[I + 1] - P0[I]);
					} else if (P[L] < P0[1]) {
						RUNOFF[L] = 0.0;
					} else if (P[L] > P0[M]) {
						RUNOFF[L] = R0[M][N1] + (P[L] - P0[M]) * (R0[M][N1] - R0[M - 1][N1]) / (P0[M] - P0[M - 1]);
					}
				}
			} else if (PA >= PA0[1] && PA <= PA0[N]) {

				for (int I = 1; I <= N - 1; I++) {
					if (PA >= PA0[I] && PA < PA0[I + 1]) {
						N1 = I;
					}
				}

				for (int I = 1; I <= M - 1; I++) {
					if (P[L] >= P0[I] && P[L] < P0[I + 1]) {
						double R1 = R0[I][N1] + (P[L] - P0[I]) * (R0[I + 1][N1] - R0[I][N1]) / (P0[I + 1] - P0[I]);
						double R2 = R0[I][N1 + 1]
								+ (P[L] - P0[I]) * (R0[I + 1][N1 + 1] - R0[I][N1 + 1]) / (P0[I + 1] - P0[I]);
						RUNOFF[L] = R1 + (PA - PA0[N1]) * (R2 - R1) / (PA0[N1 + 1] - PA0[N1]);
					} else if (P[L] >= P0[M]) {
						double R1 = R0[M][N1] + (P[L] - P0[M]) * (R0[M][N1] - R0[M - 1][N1]) / (P0[M] - P0[M - 1]);
						double R2 = R0[M][N1 + 1]
								+ (P[L] - P0[M]) * (R0[M][N1 + 1] - R0[M - 1][N1 + 1]) / (P0[M] - P0[M - 1]);
						RUNOFF[L] = R1 + (PA - PA0[N1]) * (R2 - R1) / (PA0[N1 + 1] - PA0[N1]);
					}
				}
			}

			if (FLAG == 1) {
				PA = Math.pow(RATIO, DELTAT / 24.) * (PA + P[L]);
				if (PA > IMA)
					PA = IMA;
			} else {
				PA_new = Math.pow(RATIO, DELTAT / 24.) * (PA_new + P[L]);
				if (PA_new > IMA)
					PA_new = IMA;
			}
		}

		if (FLAG == 1) {
			for (int I = LENGTH; I >= 2; I--) {
				RUNOFF[I] = RUNOFF[I] - RUNOFF[I - 1];
			}
			PA = PA_new;
			// ENDIF
		}
	}

}
