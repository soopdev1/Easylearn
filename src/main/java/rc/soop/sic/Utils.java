/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic;

import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;
import org.apache.commons.lang3.RandomStringUtils;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import org.joda.time.DateTime;
import static rc.soop.sic.Constant.PATTERNDATE2;
import static rc.soop.sic.Constant.PATTERNDATE3;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.User;

/**
 *
 * @author raf
 */
public class Utils {

    public static String estraiEccezione(Exception ec1) {
        try {
            return ec1.getStackTrace()[0].getMethodName() + " - " + getStackTrace(ec1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ec1.getMessage();

    }

    public static Logger createLog(String appname, String folderini, String patterndatefolder) {
        Logger loggerbase = Logger.getLogger(appname);
        try {
            DateTime dt = new DateTime();
            String filename = appname + "-" + dt.toString(PATTERNDATE2) + ".log";
            File dirING = new File(folderini);
            dirING.mkdirs();
            if (patterndatefolder != null) {
                File dirINGNew = new File(dirING.getPath() + File.separator + dt.toString(patterndatefolder));
                dirINGNew.mkdirs();
                filename = dirINGNew.getPath() + File.separator + filename;
            } else {
                filename = dirING.getPath() + File.separator + filename;
            }
            Handler fileHandler = new FileHandler(filename);
            loggerbase.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            loggerbase.setLevel(Level.ALL);
        } catch (Exception ex) {
        }
        return loggerbase;
    }

    public static void redirect(HttpServletRequest request, HttpServletResponse response, String destination) throws ServletException, IOException {

        String domain = "";
        if (response.isCommitted()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(domain + destination);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(domain + destination);
        }

    }

    public static String getRequestValue(HttpServletRequest request, String fieldname) {
        String out = "";
        try {
            String[] v1 = request.getParameterValues(fieldname);
            if (v1 != null) {
                if (v1.length > 1) {
                    out = new GsonBuilder().create().toJson(v1);
                } else {
                    out = request.getParameter(fieldname);
                    if (out == null) {
                        out = "";
                    } else {
                        out = out.trim();
                    }
                }
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
            out = "";
        }
        return out;
    }

    public static String convmd5(String psw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(psw.getBytes());
            byte byteData[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString().trim();
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
            return "-";
        }
    }

    public static int checkSession(HttpSession session, HttpServletRequest request) {
        try {
            User us1 = (User) session.getAttribute("us_memory");
            if (us1 != null) {
                String uri = request.getRequestURI();
                String pageName = uri.substring(uri.lastIndexOf("/") + 1);
                EntityOp en1 = new EntityOp();
                boolean out = en1.pageaccess(String.valueOf(us1.getTipo()), pageName);
                en1.close();
                if (out) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }
        return -1;
    }

    public static void printRequest(HttpServletRequest request) throws ServletException, IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                Constant.LOGGER.log(Level.INFO, "NORMAL FIELD - {0} : {1}", new Object[]{paramName, new String(paramValue.getBytes(ISO_8859_1), UTF_8)});
            }
        }
        boolean isMultipart = isMultipartContent(request);
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        String fieldName = item.getFieldName();
                        String value = new String(item.getString().getBytes(ISO_8859_1), UTF_8);
                        Constant.LOGGER.log(Level.INFO, "MULTIPART FIELD - {0} : {1}", new Object[]{fieldName, value});
                    } else {
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getName();
                        Constant.LOGGER.log(Level.INFO, "MULTIPART FILE - {0} : {1}", new Object[]{fieldName, fieldValue});
                    }
                }
            } catch (Exception ex) {
                Constant.LOGGER.severe(estraiEccezione(ex));
            }
        }
    }

    public static int parseIntR(String value) {
        try {
            if (value.contains(".")) {
                StringTokenizer st = new StringTokenizer(value, ".");
                value = st.nextToken();
            }
            return Integer.parseInt(value);
        } catch (Exception e) {
        }
        return 0;
    }

    public static Long parseLongR(String value) {
        try {
            if (value.contains(".")) {
                StringTokenizer st = new StringTokenizer(value, ".");
                value = st.nextToken();
            }
            return Long.valueOf(value);
        } catch (Exception e) {
        }
        return 0L;
    }

    public static String generaId(int length) {
        String random = randomAlphanumeric(length - 15).trim();
        return new DateTime().toString(PATTERNDATE3) + random;
    }

    public static String generateIdentifier(int length) {
        return randomAlphabetic(length).trim();
    }

    public static String enc_string(String plainText) {
        try {

            return Base64.getUrlEncoder().encodeToString(plainText.getBytes());
//            String IV = "AAAAAAAAAAAAAAAA";
//            String _plaintText_16 = covertto16Byte(plainText);
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            SecretKeySpec key = new SecretKeySpec(Engine.getPath("pass.vers.1").getBytes("UTF-8"), "AES");
//            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
//            return new String(cipher.doFinal(_plaintText_16.getBytes("UTF-8")));
//
//            Key aesKey = new SecretKeySpec(Engine.getPath("pass.vers.1").getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            // encrypt the text
//            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
//            byte[] encrypted = cipher.doFinal(ing.getBytes());
//            byte[] encoded = Base64.getEncoder().encode(new String(encrypted).getBytes(StandardCharsets.UTF_8));
//            return new String(encoded);
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }
        return null;

//        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
//        textEncryptor.setPassword(Engine.getPath("pass.vers.1"));
//        return textEncryptor.encrypt(ing);
    }

    public static String dec_string(String ing) {
        try {

            return new String(Base64.getUrlDecoder().decode(ing));
//            String IV = "AAAAAAAAAAAAAAAA";
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            SecretKeySpec key = new SecretKeySpec(Engine.getPath("pass.vers.1").getBytes("UTF-8"), "AES");
//            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
//            return removePadding(new String(cipher.doFinal(ing.getBytes()), "UTF-8"));

//            Key aesKey = new SecretKeySpec(Engine.getPath("pass.vers.1").getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            cipher.init(Cipher.DECRYPT_MODE, aesKey);
//            byte[] decrypted = cipher.doFinal(ing.getBytes());
//            byte[] decoded = Base64.getDecoder().decode(new String(decrypted).getBytes(StandardCharsets.UTF_8));
//            return new String(decoded);
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }
        return null;
    }

    public static String getPagename(HttpServletRequest req) {
        try {
            return StringUtils.substringAfterLast(req.getRequestURI(), "/");
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }
        return "";
    }

    public static String getPercentuale(int totale, int percentuale) {
        try {
            double p1 = calcolaPercentuale(String.valueOf(totale), String.valueOf(percentuale));
            return roundDoubleandFormat(p1, 2);
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }
        return "0";
    }

    public static double calcolaPercentuale(String total, String perc) {
        try {
            double t1 = fd(total);
            double p1 = fd(perc);
            double r1 = t1 * p1 / 100.0;
            return r1;
        } catch (Exception ex) {
            Constant.LOGGER.severe(estraiEccezione(ex));
        }
        return 0.0;
    }

    private static String roundDoubleandFormat(double d, int scale) {
        return new DecimalFormat("###,###.00", DecimalFormatSymbols.getInstance(Locale.ITALIAN))
                .format(BigDecimal.valueOf(d).setScale(scale, ROUND_HALF_DOWN).doubleValue());
    }

    public static double fd(String si_t_old) {
        if (si_t_old == null) {
            return 0.0D;
        }
        double d1;
        si_t_old = si_t_old.replace(",", "").trim();
        try {
            d1 = Double.parseDouble(si_t_old);
        } catch (Exception e) {
            d1 = 0.0D;
        }
        return d1;
    }

    public static String formatDoubleforMysql(String value) {
        if (value == null) {
            return "0.00";
        }
        if (value.equals("-") || value.equals("")) {
            return "0.00";
        }
        String add = "";
        if (value.contains("-")) {
            add = "-";
            value = value.replaceAll("-", "").trim();
        }

        if (!value.equals("0.00")) {
            if (value.contains(",")) {
                value = value.replaceAll("\\.", "");
                value = value.replaceAll(",", ".");
            } else {
                value = value.replaceAll("\\.", "");

                return value + ".00";
            }
        }
        return add + value;

    }

//
//    
//    public static String dec_string(String ing){
//        
//    }
}
