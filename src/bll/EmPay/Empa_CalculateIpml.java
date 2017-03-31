package bll.EmPay;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Empa_CalculateIpml implements EmPa_Calculate {
	private final BigDecimal[] becm1={new BigDecimal(0.03),new BigDecimal(0.1),new BigDecimal(0.2),
			new BigDecimal(0.25),new BigDecimal(0.3),new BigDecimal(0.35),new BigDecimal(0.45)};
	
	private final BigDecimal[] becm2={BigDecimal.ZERO,new BigDecimal(105),new BigDecimal(555),
			new BigDecimal(1005),new BigDecimal(2755),new BigDecimal(5505),new BigDecimal(13505)};
	@Override
	public BigDecimal Calculate(BigDecimal radix) {
		BigDecimal maxdecm=BigDecimal.ZERO;
		for(int i=0;i<becm1.length;i++)
		{
			BigDecimal decmal1=becm1[i];
			BigDecimal decmal2=becm2[i];
			BigDecimal decmalmul=decmal1.multiply(radix);//乘
			BigDecimal decmalsub=decmalmul.subtract(decmal2);//减
			if(maxdecm.compareTo(decmalsub)<0)
			{
				maxdecm=decmalsub;
			}
		}
		return maxdecm;
	}

}
