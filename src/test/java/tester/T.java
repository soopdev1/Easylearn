/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tester;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.forms.PdfAcroForm;
import static com.itextpdf.forms.PdfAcroForm.getAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import static rc.soop.sic.Constant.LOGGER;
import static rc.soop.sic.Constant.PATTERNDATE3;
import static rc.soop.sic.Constant.PATTERNDATE4;
import static rc.soop.sic.Pdf.checkFirmaQRpdfA;
import static rc.soop.sic.Pdf.convertPDFA;
import static rc.soop.sic.Pdf.createDir;
import static rc.soop.sic.Pdf.printbarcode;
import static rc.soop.sic.Pdf.setFieldsValue;
import static rc.soop.sic.Utils.estraiEccezione;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.Path;

/**
 *
 * @author Raffaele
 */
public class T {

    

    public static void main(String[] args) {

//        EntityOp e = new EntityOp();
//        GENERADECRETOAPPROVATIVO(e.getEm().find(Istanza.class, 8L));

//        System.out.println("tester.T.main() " + checkFirmaQRpdfA("ISTANZA", "221211205027474SJYgJuJv14hz0QZ",
//                new File("D:\\SmartOOP\\RegioneSiciliaFormazione\\Template\\TMP\\ISTANZA_221211205027474SJYgJuJv14hz0QZ_221212223911748.IF_signed.pdf"),
//                "CSCRFL86E19C352O",
//                "20;0;60;60"));
//        
    }

}
