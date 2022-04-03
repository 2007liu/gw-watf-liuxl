
package cn.com.goldenwater.liuxl.model.MSK;

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
public class MSK {

    static double C0;
    static double C1;
    static double C2;
    static double[] QX;

    public static int msk(String TEM) {
        try {
//		String TEM = "C:\\071051602\\D\\6010698011.tem";

//		TEM = "D:\\temp\\11.tem";
            int YEAR, MONTH, DAY, HOUR;
            // int byr,bmth,bday,bhr,eyr,emth,eday, ehr,Qnum,T;
            int MPDIM = 51;

            double X;
            int KK, MP;

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
            X = MapUtils.getDoubleValue(parMap, "X", 0);
            KK = MapUtils.getIntValue(parMap, "KK", 0);
            MP = MapUtils.getIntValue(parMap, "MP", 0);
//		X = -0.912;
//		KK = 6;
//		MP = 5;
            // 读取参数


            QX = new double[MP + 1 + 1];
            // OPEN(1,FILE=F_PAR)
            // OPEN(2,FILE=F_DIS)
            // OPEN(3,FILE=F_STS)
            // OPEN(4,FILE=F_STN)
            // OPEN(5,FILE=F_OUT)

            ArrayList<String> F2 = FileUtils.executeList(F_DIS);
//		int F2Size = F2.size();
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
            int Qnum = NumberUtils.toInt(F2Line1List[i1++]);
            int T = NumberUtils.toInt(F2Line1List[i1++]);

            F2Line1List = F2.get(1).trim().split("\\s+");
            for (int i2 = 1; i2 <= MP + 1; i2++) {
                QX[i2] = NumberUtils.toDouble(F2Line1List[6]);
            }

            StringBuilder sts = new StringBuilder();
            sts.append("马法演算初始流量").append("\r\n");
            sts.append("QX=");
            for (int i4 = 1; i4 <= MP + 1; i4++) {
//			sts.append("  ").append(QX[i4]);
                sts.append("  ").append(FormatUtils.formatNumber(QX[i4], "#0.0"));
            }
            FileUtils.writeFile(F_STS, sts.toString());


            double FKT = KK - KK * X + .5 * T;
            C0 = (.5 * T - KK * X) / FKT;
            C1 = (KK * X + .5 * T) / FKT;
            C2 = (KK - KK * X - .5 * T) / FKT;


            F2Line1List = F2.get(0).trim().split("\\s+"); //首行
            StringBuilder out = new StringBuilder();
            int i4 = 0;
            out.append(NumberUtils.toInt(F2Line1List[i4++])).append("  ").append(NumberUtils.toInt(F2Line1List[i4++])).append("  ").append(NumberUtils.toInt(F2Line1List[i4++])).append("  ").append(NumberUtils.toInt(F2Line1List[i4++])).append("  ");
            out.append(NumberUtils.toInt(F2Line1List[i4++])).append("  ").append(NumberUtils.toInt(F2Line1List[i4++])).append("  ").append(NumberUtils.toInt(F2Line1List[i4++])).append("  ").append(NumberUtils.toInt(F2Line1List[i4++])).append("  ");
            out.append(NumberUtils.toInt(F2Line1List[i4++])).append("  ");
            out.append(F2Line1List[i4++]).append("\r\n");

            for (int i3 = 1; i3 <= Qnum; i3++) {
                F2Line1List = F2.get(i3).trim().split("\\s+");
                double QT = NumberUtils.toDouble(F2Line1List[6]);
                double v = LCHCO(MPDIM, MP, QT);
//			System.out.println(FormatUtils.formatNumber(v, "#0.00"));
//			System.out.println(FormatUtils.formatNumber(v, "#0.00"));
                out.append(NumberUtils.toInt(F2Line1List[1])).append("  ");
                out.append(NumberUtils.toInt(F2Line1List[2])).append("  ");
                out.append(NumberUtils.toInt(F2Line1List[3])).append("  ");
                out.append(NumberUtils.toInt(F2Line1List[4])).append("  ");
                out.append(NumberUtils.toInt(F2Line1List[5])).append("  "); // 水位值不变
                out.append(FormatUtils.formatNumber(v, "#0.00")).append("\r\n");
            }

            //写入out文件中
            FileUtils.writeFile(F_OUT, out.toString());

            //写入F_STN
            StringBuilder stn = new StringBuilder();
            stn.append("马法演算初始流量").append("\r\n");
            stn.append("QX=");
            for (int i5 = 1; i5 <= MP + 1; i5++) {
//			stn.append("  ").append(QX[i5]);
                stn.append("  ").append(FormatUtils.formatNumber(QX[i5], "#0.0"));
            }
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
            QX[1] = RQ;
        } else {
            for (int j = 2; j <= IM; j++) {

                double Q1 = RQ;
                double Q2 = QX[j - 1];
                double Q3 = QX[j];

                QX[j - 1] = RQ;

                RQ = C0 * Q1 + C1 * Q2 + C2 * Q3;
            }
            QX[IM] = RQ;
        }
//		System.out.println(FormatUtils.formatNumber(RQ, "#0.00"));

        return RQ;
    }

    //测试
    public static void main(String[] args) {
//		MSK.msk("D:\\609541999\\D\\8011230011.tem");
        MSK.msk("C:\\Temp\\11.tem");
    }
}
