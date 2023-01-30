
package Modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conversor {
    
    private static final double valorDeEquivalenciaDeUnKiloByteAByte = 1024.00;
    
    public static double conversionDeBytesAkiloBytes(double bytes){
        return redondeo (bytes / valorDeEquivalenciaDeUnKiloByteAByte,2);
    }
    
    public static double redondeo(double numeroCompleto, int decimales) {
        return new BigDecimal(numeroCompleto).setScale(decimales, RoundingMode.HALF_EVEN).doubleValue();
    }
}
