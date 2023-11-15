package rc.soop.sic;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import static rc.soop.sic.Utils.createLog;

/**
 *
 * @author raf
 */
public class Constant {

    public static final String NAMEAPP = "WeForm";
    public static final String VERSIONAPP = "Version 1.0 RC";
    public static final String PATTERNDATE1 = "yyyyMMdd";
    public static final String PATTERNDATE2 = "HHmmssSSS";
    public static final String PATTERNDATE3 = "yyMMddHHmmssSSS";
    public static final String PATTERNDATE4 = "dd/MM/yyyy";
    public static final String PATTERNDATE5 = "dd/MM/yyyy HH:mm:ss";
    public static final String PATTERNDATE6 = "yyyy-MM-dd";
    public static SimpleDateFormat sdf_PATTERNDATE4 = new SimpleDateFormat(PATTERNDATE4);
    public static SimpleDateFormat sdf_PATTERNDATE5 = new SimpleDateFormat(PATTERNDATE5);
    public static SimpleDateFormat sdf_PATTERNDATE6 = new SimpleDateFormat(PATTERNDATE6);
    public static final String MIMEPDF = "application/pdf";
    public static final String EXTPDF = ".pdf";

    public static final ResourceBundle RB = ResourceBundle.getBundle("parameter.conf");
    public static final Logger LOGGER = createLog(NAMEAPP, RB.getString("path.log"), PATTERNDATE1);
    public static boolean ISDEMO = RB.getString("demo").equalsIgnoreCase("SI");

    public static String TINYMCEKEY = RB.getString("tinymce.key");
}
