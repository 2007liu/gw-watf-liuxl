package cn.com.goldenwater.liuxl.model.BDSMS_3;

import cn.com.goldenwater.utils.FileUtils;
import cn.com.goldenwater.utils.FormatUtils;
import cn.com.goldenwater.utils.ModelUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by liuxl.auxuan on 2022/4/3.
 */
public class BDSMS_3 {

    static double B, C, WUM, WLM, WDM, WM, WMM;
    static double KG, KI, SMM, SM, EX, IM;
    static double PE;

    static double WUP;
    static double WLP;
    static double WDP;
    static double S;
    static double FR;

    public static int bdsms_3(String TEM) {
//		String TEM = null;
//		TEM = "D:\\619706144\\D\\8011230031.tem";

        try {


            int YEAR, MTH, DAY, HOUR, FLAG_E = 0;
            int byr, bmth, bday, bhr, eyr, emth, eday, ehr, Pnum, T, SourceN;

            double K, HGI, WUMx, WLMx;
            double[] RD = new double[100];
            double[] PED = new double[100];

            ArrayList<String> arrayList = FileUtils.executeList(TEM);
            int i = 0;
            String F_PAR = arrayList.get(i++);
            String F_STS = arrayList.get(i++);
            String F_DIS = arrayList.get(i++);
            String F_DOB = arrayList.get(i++);

            String F_RNP = arrayList.get(i++);
            String F_RNA = arrayList.get(i++);
            String F_RNO = arrayList.get(i++);

            String F_STN = arrayList.get(i++);
            String F_OUT = arrayList.get(i++);
            String F_EVP = arrayList.get(i++);

            // 读取参数
//		WM = 50.104;
//		WUMx = 0.010;
//		WLMx = 0.940;
//		K = 0.700;
//		B = 0.334;
//		C = 0.198;
//		IM = 0.036;
//		SM = 49.634;
//		EX = 1.856;
//		KG = 0.794;
//		KI = 0.103;

            // 从文件中读取参数
            Map<String, String> parMap = ModelUtil.readPar(F_PAR);
            // 从文件中读取参数
            ;
            WM = MapUtils.getDoubleValue(parMap, "WM", 0);
            WUMx = MapUtils.getDoubleValue(parMap, "WUMx", 0);
            WLMx = MapUtils.getDoubleValue(parMap, "WLMx", 0);
            K = MapUtils.getDoubleValue(parMap, "K", 0);
            B = MapUtils.getDoubleValue(parMap, "B", 0);
            C = MapUtils.getDoubleValue(parMap, "C", 0);
            IM = MapUtils.getDoubleValue(parMap, "IM", 0);
            SM = MapUtils.getDoubleValue(parMap, "SM", 0);
            EX = MapUtils.getDoubleValue(parMap, "EX", 0);
            KG = MapUtils.getDoubleValue(parMap, "KG", 0);
            KI = MapUtils.getDoubleValue(parMap, "KI", 0);

            double[] ES = new double[13];
//				ES = {0, 28.6, 36 ,39.7 ,104.9, 147.4, 144.5, 123.7 ,106.8, 112.7, 68.9 ,64.9, 40.7 };
            String[] es = MapUtils.getString(parMap, "ES", "").trim().split("\\s+");
            for (int i1 = 1; i1 < 13 && i1 <= es.length; i1++) {
                ES[i1] = NumberUtils.toDouble(es[i1 - 1], 0);
            }

            WUM = WUMx * WM;
            WLM = (1 - WUMx) * WLMx * WM;
            WDM = WM - WUM - WLM;

            // C 如ES=0,读取实时蒸发数据。
            if (ES[1] == 0.0 && ES[6] == 0.0) {
                FLAG_E = 1;
            }

//		WUP = 0d;// 0.000000E+00;
//		WLP = 17.207800;
//		WDP = 27.120630;
//		S = 1.788796E-05;
//		FR = 5.970329E-01;

            // 从文件中读取初始状态
            Map<String, String> stsMap = ModelUtil.readPar(F_STS);
//		WUP=    0.000000E+00;
//		WLP=       36.512030;
//		WDP=       35.000000;
//		S=    8.933024E-10;
//		FR=    4.553660E-01;
            WUP = MapUtils.getDoubleValue(stsMap, "WUP", 0);
            WLP = MapUtils.getDoubleValue(stsMap, "WLP", 0);
            WDP = MapUtils.getDoubleValue(stsMap, "WDP", 0);
            S = MapUtils.getDoubleValue(stsMap, "SP", 0); //注意字段
            FR = MapUtils.getDoubleValue(stsMap, "FRP", 0); //注意字段

            if (WUP > WUM) {
                WUP = WUM;
            }
            if (WUP < 0) {
                WUP = 0d;
            }
            if (WLP > WLM) {
                WLP = WLM;
            }
            if (WLP < 0) {
                WLP = 0d;
            }
            if (WDP > WDM) {
                WDP = WDM;
            }
            if (WDP < 0) {
                WDP = 0d;
            }
            double WP = WUP + WLP + WDP;
            // GOTO 16
            // 16 OPEN(3,FILE=F_RNA)
            // OPEN(4,FILE=F_RNO)
            // OPEN(5,FILE=F_STN)


            //读取文件错误时，设置默认值
//		StringBuilder sts = new StringBuilder();
//		sts.append("上层张力水含量\r\n");
//		sts.append("\r\n下层张力水含量\r\n");
//		sts.append("\r\n深层张力水含量\r\n");
//		sts.append("\r\n自由水水深\r\n");
//		sts.append("\r\n产流面积系数\r\n");


            // C 增加蒸发读取
            if (FLAG_E == 1) {
                // OPEN(6,FILE=F_EVP)
            }

            double DIV = 5.0;
            WMM = (1.0 + B) * WM / (1.0 - IM);
            SMM = (1.0 + EX) * SM;

            SourceN = 3;

            ArrayList<String> FEVP = FileUtils.executeList(F_EVP);
            ArrayList<String> FRNA = FileUtils.executeList(F_RNA);

            if (FLAG_E == 1) {
                // read F_EVP
                // 增加蒸发读取
                // OPEN(6,FILE=F_EVP)
                String[] F2Line1List = FEVP.get(0).trim().split("\\s+");
                int i1 = 0;
                byr = NumberUtils.toInt(F2Line1List[i1++]);
                bmth = NumberUtils.toInt(F2Line1List[i1++]);
                bday = NumberUtils.toInt(F2Line1List[i1++]);
                bhr = NumberUtils.toInt(F2Line1List[i1++]);
                eyr = NumberUtils.toInt(F2Line1List[i1++]);
                emth = NumberUtils.toInt(F2Line1List[i1++]);
                eday = NumberUtils.toInt(F2Line1List[i1++]);
                ehr = NumberUtils.toInt(F2Line1List[i1++]);
                Pnum = NumberUtils.toInt(F2Line1List[i1++]);
                T = NumberUtils.toInt(F2Line1List[i1++]);
            } else {
                // read F_RNA
                String[] F2Line1List = FRNA.get(0).trim().split("\\s+");
                int i1 = 0;
                byr = NumberUtils.toInt(F2Line1List[i1++]);
                bmth = NumberUtils.toInt(F2Line1List[i1++]);
                bday = NumberUtils.toInt(F2Line1List[i1++]);
                bhr = NumberUtils.toInt(F2Line1List[i1++]);
                eyr = NumberUtils.toInt(F2Line1List[i1++]);
                emth = NumberUtils.toInt(F2Line1List[i1++]);
                eday = NumberUtils.toInt(F2Line1List[i1++]);
                ehr = NumberUtils.toInt(F2Line1List[i1++]);
                Pnum = NumberUtils.toInt(F2Line1List[i1++]);
                T = NumberUtils.toInt(F2Line1List[i1++]);
            }

            if (KG + KI > 0.9) {
                double tmp = (KG + KI - 0.9) / (KG + KI);
                KG = KG - KG * tmp;
                KI = KI - KI * tmp;
            }

            HGI = (1. - Math.pow(1. - KG - KI, T / 24.)) / (KG + KI);
            KG = HGI * KG;
            KI = HGI * KI;

            // WRITE(4,21) byr,bmth,bday,bhr,eyr,emth,eday,ehr,Pnum,T,SourceN //F_RNO

            // 结果输出文件
            StringBuilder rno = new StringBuilder();
            rno.append(byr).append("  ").append(bmth).append("  ").append(bday).append("  ").append(bhr).append("  ");
            rno.append(eyr).append("  ").append(emth).append("  ").append(eday).append("  ").append(ehr).append("  ");
            rno.append(Pnum).append("  ").append(T).append("  ").append(SourceN);
//		System.out.println(byr + "\t" + bmth + "\t" + bday + "\t" + bhr + "\t" + eyr + "\t" + emth + "\t" + eday + "\t"
//				+ ehr + "\t" + Pnum + "\t" + T + "\t" + SourceN);
            W = WP;
            WU = WUP;
            WL = WLP;
            WD = WDP;

            for (int ii = 1; ii <= Pnum; ii++) {
                int IDAY = 31;

                String[] F2Line1List = FRNA.get(ii).trim().split("\\s+");
                YEAR = NumberUtils.toInt(F2Line1List[0]);
                MTH = NumberUtils.toInt(F2Line1List[1]);
                DAY = NumberUtils.toInt(F2Line1List[2]);
                HOUR = NumberUtils.toInt(F2Line1List[3]);
                double PP = NumberUtils.toDouble(F2Line1List[4]);

                double EE, EM;
                if (FLAG_E == 1) {

                    F2Line1List = FEVP.get(ii).trim().split("\\s+");
                    YEAR = NumberUtils.toInt(F2Line1List[0]);
                    MTH = NumberUtils.toInt(F2Line1List[1]);
                    DAY = NumberUtils.toInt(F2Line1List[2]);
                    HOUR = NumberUtils.toInt(F2Line1List[3]);
                    EE = NumberUtils.toInt(F2Line1List[4]);

                    EM = EE;
                } else {
                    if (MTH == 4 || MTH == 6 || MTH == 9 || MTH == 11) {
                        IDAY = 30;
                    } else if (MTH == 28) {
                        if ((YEAR % 4 == 0 && YEAR % 100 != 0) || (YEAR % 400 == 0)) {
                            IDAY = 29;
                        } else {
                            IDAY = 28;
                        }
                    } else {
                        IDAY = 31;
                    }
                    EM = ES[MTH] / (IDAY * 24.0 / T);
                }

                double EK = K * EM;
                PE = PP - EK;
                int ND;
                if (PE >= 2 * DIV) {
                    // GOTO 110
                    ND = (int) (PE / DIV);
                    for (int II = 1; II <= ND - 1; II++) {
                        PED[II] = DIV;
                    } // todo 反复执行五次吗？
                    PED[ND] = PE - (ND - 1) * DIV;
                } else {
                    ND = 1;
                    PED[1] = PE;
                    // GOTO 130;
                }
                // double[] y = YIELD1(EK, WP, WUP, WLP, WDP, RD, PED, ND);
                YIELD1(EK, RD, PED, ND);
                DIVI31(RD, PED, ND);
                rno.append("\r\n").append(YEAR).append("  ").append(MTH).append("  ").append(DAY).append("  ").append(HOUR); // 年月日时
                rno.append("  ").append(FormatUtils.formatNumber(RS, "#0.00")); //1层
                rno.append("  ").append(FormatUtils.formatNumber(RI, "#0.00")); //2层
                rno.append("  ").append(FormatUtils.formatNumber(RG, "#0.00")); //3层
//			System.out.println(
//					YEAR + "\t" + MTH + "\t" + DAY + "\t" + HOUR + "\t" + FormatUtils.formatNumber(RS, "#0.00") + "\t"
//							+ FormatUtils.formatNumber(RI, "#0.00") + "\t" + FormatUtils.formatNumber(RG, "#0.00"));
            }
            FileUtils.writeFile(F_RNO, rno.toString());

            StringBuilder stn = new StringBuilder();
            stn.append("上层张力水含量").append("\r\n").append("WUP= ").append(WUP).append("\r\n");
            stn.append("下层张力水含量").append("\r\n").append("WLP= ").append(WLP).append("\r\n"); // todo待核对
            stn.append("深层张力水含量").append("\r\n").append("WDP= ").append(WDP).append("\r\n");
            stn.append("自由水水深").append("\r\n").append("SP= ").append(S).append("\r\n");
            stn.append("产流面积系数").append("\r\n").append("FRP= ").append(FR);
            FileUtils.writeFile(F_STN, stn.toString());

//		System.out.println("WUP= " + WUP);
//		System.out.println("WLP=" + WLP);
//		System.out.println("WDP=" + WDP);
//		System.out.println("SP= " + S);
//		System.out.println("FRP= " + FR);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    static double W;
    static double WU;
    static double WL;
    static double WD;

    public static void YIELD1(double EK, double[] RD, double[] PED, int ND) {
        double ED = 0;
        double EU = 0;
        double EL = 0;
        double R = 0;
        double PEDS = 0;
        if (PE <= 0.0) {// GOTO 400
            R = 0;
            if (WU + PE < 0.0) // GOTO 500
            {
                EU = WU + EK + PE;
                WU = 0d;
                EL = (EK - EU) * WL / WLM;
                if (WL < C * WLM) {
                    EL = C * (EK - EU);
                }
                if (WL - EL < 0.0) // GOTO 580
                {
                    ED = EL - WL;
                    EL = WL;
                    WL = 0d;
                    WD = WD - ED;
                    W = WU + WL + WD;
                } else {
                    ED = 0;
                    WL = WL - EL;

                    // GOTO 480
                    W = WU + WL + WD;
                }
            } else {
                EU = EK;
                ED = 0;
                EL = 0;
                WU = WU + PE;

                // GOTO 480
                W = WU + WL + WD;
            }
        } else {
            double A;
            if (WM - W < 0.0001) {
                A = WMM;
            } else {
                A = WMM * (1. - Math.pow(1. - W / WM, 1. / (1. + B)));
            }
            R = 0.0;
            PEDS = 0.0;

            for (int I = 1; I <= ND; I++) {
                A = A + PED[I];
                PEDS = PEDS + PED[I];
                double RI = R;
                R = PEDS - WM + W;
                if (A < WMM) {
                    R = R + WM * Math.pow(1. - A / WMM, 1. + B);
                }
                RD[I] = R - RI;
            }
            // 200 EU=EK
            EU = EK;
            EL = 0;
            ED = 0;

            if (WU + PE - R <= WUM) // GOTO 380
            {
                WU = WU + PE - R;
                // GOTO 480
                W = WU + WL + WD;
            } else if (WU + WL + PE - R - WUM >= WLM) // GOTO 350
            {
                WU = WUM;
                WL = WLM;
                WD = W + PEDS - R - WU - WL;
                if (WD > WDM)
                    WD = WDM;

                // GOTO 480
                W = WU + WL + WD;
            } else {

                WL = WU + WL + PE - R - WUM;
                WU = WUM;
                // GOTO 480
                W = WU + WL + WD;
            }
        }
    }

    static double RS;
    static double RI;
    static double RG;

    public static void DIVI31(double[] RD, double[] PED, int ND) {
        double RR = 0;

        // double[] RD = new double[ND],PED = new double[ND];
        if (PE <= 0.0) {
            RS = 0.0;
            RG = S * KG * FR;
            RI = S * KI * FR;
            S = S * (1. - KG - KI);
        } else {

            double RB = IM * PE;

            double KID = (1. - Math.pow(1. - (KG + KI), 1. / ND)) / (KG + KI);
            double KGD = KID * KG;
            KID = KID * KI;

            RS = 0;
            RI = 0;
            RG = 0;
            for (int I = 1; I <= ND; I++) {
                double TD = RD[I] - IM * PED[I];
                double X = FR;
                FR = TD / PED[I];
                S = X * S / FR;
                if (S >= SM) {
                    // GOTO 5
                    RR = (PED[I] + S - SM) * FR;
                    // GOTO 20
                    RS = RR + RS;
                    S = PED[I] - RR / FR + S;
                    RG = S * KGD * FR + RG;
                    RI = S * KID * FR + RI;
                    S = S * (1. - KID - KGD);
                } else {
                    // AU=SMM*(1.-(1.-S/SM)**(1./(1.+EX)))
                    double AU = SMM * (1. - Math.pow(1. - S / SM, 1. / (1. + EX)));
                    if (AU + PED[I] < SMM) {
                        // GOTO 10
                        RR = (PED[I] - SM + S + SM * Math.pow(1. - (PED[I] + AU) / SMM, 1. + EX)) * FR;
                        // RR=(PED[I]-SM+S+SM*(1.-(PED[I]+AU)/SMM)**(1.+EX))*FR;
                    } else {
                        RR = (PED[I] + S - SM) * FR;
                    }
                    // GOTO 20
                    RS = RR + RS;
                    S = PED[I] - RR / FR + S;
                    RG = S * KGD * FR + RG;
                    RI = S * KID * FR + RI;
                    S = S * (1. - KID - KGD);
                }
            }

            RS = RS + RB;
        }
    }

    public static void main(String[] args) {
        String
                TEM = "D:\\0604\\619706144\\D\\8011230031.tem";
        TEM = "C:\\937579543\\D\\5010050021.tem";
        TEM = "D:\\619706144\\D\\8011230031.TEM";
        TEM = "C:\\664992343\\D\\6051115011.tem";

        BDSMS_3.bdsms_3(TEM);
    }
}
