package cn.com.goldenwater.liuxl.model.UH_B;

import cn.com.goldenwater.utils.FileUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;


/**
 * Created by liuxl.auxuan on 2019/4/20.
 */
public class UH_B {


	public static void main(String[] args) {
		String TEM = "C:\\071051602\\D\\6010698011.tem";

		int N, N_all, n_no, M;
		int[] NN = new int[20]; // 每条单位线条数
		double[] RNO = new double[1500];
		double[] QC = new double[1500];
		double[][] UH = new double[20][100]; // 单位线详细的数据
		double RN1, RN2, RN3;
		int Sy, Sm, Sd, Sh, Ey, Em, Ed, Eh, DT;
		int[] iy = new int[1500];
		int[] im = new int[1500];
		int[] id = new int[1500];
		int[] ih = new int[1500];

		String flag, T_CHAR;

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
		n_no = 2;// 选取所采用的单位线号//
		N_all = 3; // 单位线总数//
		// 18
		// 0 80 220 345 400 333 254 195 154 122 100 77 60 40 26 16 10 0

		NN[1] = 18;
		NN[2] = 18;
		NN[3] = 18;
		UH[1] = new double[] { 0, 80, 220, 345, 400, 333, 254, 195, 154, 122, 100, 77, 60, 40, 26, 16, 10, 0 };// 暴雨中心在上游
		UH[2] = new double[] { 0, 284, 618, 390, 280, 200, 160, 120, 95, 75, 60, 45, 35, 25, 20, 15, 10, 0 }; // 暴雨中心在中游
		UH[3] = new double[] { 0, 250, 873, 359, 240, 172, 127, 96, 75, 60, 48, 38, 30, 27, 21, 10, 6, 0 };// 暴雨中心在下游

		N = NN[n_no];// 第n_no条单位线，单位线的条数

		// 状态：
		// 1 流域基流
		// 2 Qbasic= 100
		double Qbasic = 100; // 流域基流

		// 如果状态读取失败则设置为0.0
		ArrayList<String> F2 = FileUtils.executeList(F_RNO);
		int F2Size = F2.size();
		String[] F2Line1List = F2.get(0).trim().split("\\s+");
		int i1 = 0;
		Sy = NumberUtils.toInt(F2Line1List[i1++]);
		Sm = NumberUtils.toInt(F2Line1List[i1++]);
		Sd = NumberUtils.toInt(F2Line1List[i1++]);
		Sh = NumberUtils.toInt(F2Line1List[i1++]);

		Ey = NumberUtils.toInt(F2Line1List[i1++]);
		Em = NumberUtils.toInt(F2Line1List[i1++]);
		Ed = NumberUtils.toInt(F2Line1List[i1++]);
		Eh = NumberUtils.toInt(F2Line1List[i1++]);

		M = NumberUtils.toInt(F2Line1List[i1++]);
		DT = NumberUtils.toInt(F2Line1List[i1++]);
		int N_RNO = NumberUtils.toInt(F2Line1List[i1++]);

		for (int ii = 1; ii <= M; ii++) {
			F2Line1List = F2.get(ii).trim().split("\\s+");
			String YEAR = F2Line1List[0];
			String MONTH = F2Line1List[1];
			String DAY = F2Line1List[2];
			String HOUR = F2Line1List[3];

			iy[ii] = NumberUtils.toInt(YEAR);
			im[ii] = NumberUtils.toInt(MONTH);
			id[ii] = NumberUtils.toInt(DAY);
			ih[ii] = NumberUtils.toInt(HOUR);

			RNO[ii] = 0;
			RNO[ii] = 0;
			for (int j = 1; j <= N_RNO; j++) {
				RNO[ii] += NumberUtils.toDouble(F2Line1List[3 + j]); // 累加
			}
		}

		for (int jj = 1; jj <= M + N - 1; jj++) {
			QC[jj] = 0.0;
		}

		for (int I = 1; I <= N; I++) {
			for (int J = 1; J <= M; J++) {
				int IJ1 = I + J - 1;
				QC[IJ1] = QC[IJ1] + 0.1 * RNO[J] * UH[n_no][I];
			}
		}

		// OPEN(14,FILE=f_OUT)
		System.out.println(Sy + "\t" + Sm + "\t" + Sd + "\t" + Sh + "\t" + Ey + "\t" + Em + "\t" + Ed + "\t" + Eh + "\t"
				+ M + 1 + "\t" + DT);
		System.out.println(Sy + "\t" + Sm + "\t" + Sd + "\t" + Sh + "\t00.00\t" + Qbasic);
		for (int I = 1; I <= M; I++) {
			System.out.println(iy[I] + "\t" + im[I] + "\t" + id[I] + "\t" + ih[I] + "\t00.00\t" + (QC[I] + Qbasic));
		}

		// 写入最终的状态stn
		// OPEN(5,FILE=f_stn,STATUS="unknown")
		// WRITE(5,*)"流域基流"
		// WRITE(5,113)"Qbasic=",Qbasic
		// CLOSE(5)
	}

}
