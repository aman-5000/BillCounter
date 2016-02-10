package util;

import java.text.DecimalFormat;

/**
 * Created by DK on 1/9/2016.
 */
public class Utils
{
    public static  String formatNumber(int value)
    {
        DecimalFormat formatter=new DecimalFormat("#,###,###");
        String formatted=formatter.format(value);
        return formatted;
    }


}
