package cn.com.goldenwater.liuxl.model.LAG_3;

import cn.com.goldenwater.utils.FileUtils;
import cn.com.goldenwater.utils.FormatUtils;
import cn.com.goldenwater.utils.ModelUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by liuxl.auxuan on 2019/4/20.
 */
public class LAG_3 {

    static double C0;
    static double C1;
    static double C2;
    static double[] QXSIG;

    public static int lag_3(String TEM) {
        try {
//		String TEM = null;
//		TEM = "D:\\619706144\\D\\8011230032.tem";
            int MPDIM = 100;
            int JDIM = 450;
            QXSIG = new double[MPDIM];

            double[] QSIG = new double[JDIM];

            String file_name;
            String T_CHAR;
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


            // 从文件中读取参数
            Map<String, String> parMap = ModelUtil.readPar(F_PAR);
            // 从文件中读取参数

            // 读取参数
            double F = MapUtils.getDoubleValue(parMap, "F", 0);
            double CI = MapUtils.getDoubleValue(parMap, "CI", 0);
            double CG = MapUtils.getDoubleValue(parMap, "CG", 0);
            double CS = MapUtils.getDoubleValue(parMap, "CS", 0);
            int LAG = MapUtils.getIntValue(parMap, "LAG", 0);
            double X = MapUtils.getDoubleValue(parMap, "X", 0);
            int KK = MapUtils.getIntValue(parMap, "KK", 0);
            int MP = MapUtils.getIntValue(parMap, "MP", 0);

            ArrayList<String> F2 = FileUtils.executeList(F_RNO);
            String[] F2Line1List = F2.get(0).trim().split("\\s+");
            int i1 = 0;
            int byr = NumberUtils.toInt(F2Line1List[i1++]);
            int bmth = NumberUtils.toInt(F2Line1List[i1++]);
            int bday = NumberUtils.toInt(F2Line1List[i1++]);
            int bhr = NumberUtils.toInt(F2Line1List[i1++]);
            int eyr = NumberUtils.toInt(F2Line1List[i1++]);
            int emth = NumberUtils.toInt(F2Line1List[i1++]);
            int eday = NumberUtils.toInt(F2Line1List[i1++]);
            int ehr = NumberUtils.toInt(F2Line1List[i1++]);
            int Pnum = NumberUtils.toInt(F2Line1List[i1++]);
            int T = NumberUtils.toInt(F2Line1List[i1++]);

            StringBuilder out = new StringBuilder();
            out.append(byr).append(" ").append(bmth).append(" ").append(bday).append(" ").append(bhr);
            out.append(eyr).append(" ").append(emth).append(" ").append(eday).append(" ").append(ehr);
            out.append(" ").append(Pnum + 1).append(" ").append(T);
            System.out.println(byr + "\t" + bmth + "\t" + bday + "\t" + bhr + "\t" + eyr + "\t" + emth + "\t" + eday + "\t"
                    + ehr + "\t" + Pnum + +1 + "\t" + T);

            double CP = F / T / 3.6;
            CG = Math.pow(CG, T / 24.);
            CI = Math.pow(CI, T / 24.);
            double FKT = KK - KK * X + .5 * T;
            C0 = (.5 * T - KK * X) / FKT;
            C1 = (KK * X + .5 * T) / FKT;
            C2 = (KK - KK * X - .5 * T) / FKT;


            double QSIG1;
            // 从文件中读取初始状态
            Map<String, String> stsMap = ModelUtil.readPar(F_STS);
            String[] qsig = MapUtils.getString(stsMap, "QSIG", "").trim().split("\\s+");
            if (LAG <= 1) {
//			QSIG[1] = 288.8;
                QSIG[1] = NumberUtils.toDouble(qsig[0], 0);
                QSIG1 = QSIG[1];
            } else {
                for (int i4 = 1; i4 <= LAG; i4++) {
                    // 数组形式，多状态值赋值情况
                    if (qsig.length >= i4) {// 数据长度判断
                        QSIG[i4] = NumberUtils.toDouble(qsig[i4 - 1]);
                    }
                }
                QSIG1 = QSIG[LAG];
            }
            // 读取状态值
//		QSIG[1] = 288.8;
//		if (LAG == 1) {
//			QXSIG[1] = 288.8;
//			QSIG1 = QSIG[1];
//		} else {
//			QXSIG[1] = 288.8;
//			QSIG1 = QSIG[LAG];
//		}
//		double QSP = 0.000000E+00;
//		double QIP = 7.775792;
//		double QGP = 266.221600;
            double QSP = MapUtils.getDoubleValue(stsMap, "QSP", 0);
            double QIP = MapUtils.getDoubleValue(stsMap, "QIP", 0);
            double QGP = MapUtils.getDoubleValue(stsMap, "QGP", 0);
            // 从文件中读取初始状态

            // if (LAG == 1) {
            //
            // if (LAG > 0) {
            // for (int I = 0; I < LAG; I++) {
            // QSIG[I] = 0.0;
            // }
            // }
            // for (int I = 0; I < MP + 1; I++) {
            // QXSIG[I] = 0.0;
            // }
            // QSP = 0.0;
            // QIP = 0.0;
            // QGP = 0.0;
            //


            //写STS初始状态文件
            StringBuilder sts = new StringBuilder();
            sts.append("初始总基流").append("\r\n");
            if (LAG == 0) {
                sts.append("QSIG=  ").append(QSIG1);
            } else {
                sts.append("QSIG=");
                for (int i4 = Pnum + 1; i4 <= Pnum + LAG; i4++) {
                    sts.append("  ").append(FormatUtils.formatNumber(QSIG[i4], "#0.0"));
                }
            }
            sts.append("\r\n马法初始流量").append("\r\n");
            sts.append("QXSIG=");
            for (int i4 = 1; i4 <= MP + 1; i4++) {
                sts.append("  ").append(FormatUtils.formatNumber(QXSIG[i4], "#0.0"));
            }
            sts.append("\r\n地表径流流量");
            sts.append("\r\n").append("QSP= ").append(QSP);
            sts.append("\r\n壤中流流量");
            sts.append("\r\n").append("QIP= ").append(QIP);
            sts.append("\r\n地下径流流量");
            sts.append("\r\n").append("QGP= ").append(QGP);


            for (int J = 1; J <= Pnum; J++) {
                F2Line1List = F2.get(J).trim().split("\\s+");
                String YEAR = F2Line1List[0];
                String MONTH = F2Line1List[1];
                String DAY = F2Line1List[2];
                String HOUR = F2Line1List[3];

                double RS = NumberUtils.toDouble(F2Line1List[4]);
                double RI = NumberUtils.toDouble(F2Line1List[5]);
                double RG = NumberUtils.toDouble(F2Line1List[6]);

                // double v = LCHCO(MPDIM, MP, QT);

                QGP = QGP * CG + RG * (1. - CG) * CP;
                QIP = QIP * CI + RI * (1. - CI) * CP;
                QSP = RS * CP;

                QSIG1 = QSIG1 * CS + (QSP + QIP + QGP) * (1 - CS);
                double QTSIG = QSIG1;

                LCHCO(MPDIM, MP, QTSIG);

                QSIG[J + LAG] = QTSIG;

                if (J == 1) {
                    out.append("\r\n").append(byr).append("  ").append(bmth).append("  ").append(bday).append("  ").append(bhr).append("  0.0  ").append(FormatUtils.formatNumber(QSIG[J], "#0.00"));
                }

                out.append("\r\n").append(YEAR).append("  ").append(MONTH).append("  ").append(DAY).append("  ").append(HOUR).append("  0.0  ").append(FormatUtils.formatNumber(QSIG[J], "#0.00"));
            }
            //写入out文件中
            FileUtils.writeFile(F_OUT, out.toString());

            //写入最终状态文件中 //todo check
            StringBuilder stn = new StringBuilder();
            stn.append("初始总基流\r\n");
            if (LAG == 0) {
                stn.append("QSIG=  ").append(FormatUtils.formatNumber(QSIG1, "#0.0"));
            } else {
                stn.append("QSIG=");
                for (int j = Pnum + 1; j <= Pnum + LAG; j++) {
                    stn.append("  ").append(FormatUtils.formatNumber(QSIG[j], "#0.0"));
                }
            }
            stn.append("\r\n马法初始流量");
            stn.append("\r\nQXSIG=");
            for (int j = 1; j <= MP + 1; j++) {
                stn.append("  ").append(FormatUtils.formatNumber(QXSIG[j], "#0.0"));
            }
            stn.append("\r\n地表径流流量");
            stn.append("\r\nQSP= ").append(FormatUtils.formatNumber(QSP, "#0.0"));
            stn.append("\r\n壤中流流量");
            stn.append("\r\nQIP= ").append(FormatUtils.formatNumber(QIP, "#0.0"));
            stn.append("\r\n地下径流流量");
            stn.append("\r\nQGP= ").append(FormatUtils.formatNumber(QGP, "#0.0"));
            FileUtils.writeFile(F_STN, stn.toString());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double LCHCO(int MPDIM, int MP, double RQ) {
        int IM = MP + 1;
        if (1 == IM) {
            QXSIG[1] = RQ;
        } else {
            for (int j = 2; j <= IM; j++) {

                double Q1 = RQ;
                double Q2 = QXSIG[j - 1];
                double Q3 = QXSIG[j];

                QXSIG[j - 1] = RQ;

                RQ = C0 * Q1 + C1 * Q2 + C2 * Q3;
            }
            QXSIG[IM] = RQ;
        }

        return RQ;
    }

    public static void main(String[] args) {
        String TEM = "D:\\619706144\\D\\8011230032.tem";
        TEM = "C:\\832423515\\D\\8152154012.tem";
        LAG_3.lag_3(TEM);
    }

}
