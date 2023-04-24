/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic;

import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.List;
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
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
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

}