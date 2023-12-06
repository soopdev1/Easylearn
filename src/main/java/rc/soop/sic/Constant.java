package rc.soop.sic;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
    public static final String PATTERNDATE7 = "yyyy-MM-dd'T'HH:mm";
    public static final String PATTERNDATE8 = "HH:mm";
    public static final String PATTERNDATE9 = "yyyy-MM-dd HH:mm";
    public static SimpleDateFormat sdf_PATTERNDATE4 = new SimpleDateFormat(PATTERNDATE4);
    public static SimpleDateFormat sdf_PATTERNDATE5 = new SimpleDateFormat(PATTERNDATE5);
    public static SimpleDateFormat sdf_PATTERNDATE6 = new SimpleDateFormat(PATTERNDATE6);
    public static SimpleDateFormat sdf_PATTERNDATE7 = new SimpleDateFormat(PATTERNDATE7);
    public static SimpleDateFormat sdf_PATTERNDATE8 = new SimpleDateFormat(PATTERNDATE8);
    public static SimpleDateFormat sdf_PATTERNDATE9 = new SimpleDateFormat(PATTERNDATE9);
    public static final String MIMEPDF = "application/pdf";
    public static final String EXTPDF = ".pdf";
    public static final String MIMEZIP = "application/zip";
    public static final String EXTZIP = ".zip";

    public static final ResourceBundle RB = ResourceBundle.getBundle("parameter.conf");
    public static final Logger LOGGER = createLog(NAMEAPP, RB.getString("path.log"), PATTERNDATE1);
    public static boolean ISDEMO = RB.getString("demo").equalsIgnoreCase("SI");

    public static String TINYMCEKEY = RB.getString("tinymce.key");

    public static ObjectMapper omSTANDARD = JsonMapper
            .builder()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .serializationInclusion(Include.NON_NULL).build();
}
