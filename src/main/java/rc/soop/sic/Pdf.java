/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.forms.PdfAcroForm;
import static com.itextpdf.forms.PdfAcroForm.getAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import static com.itextpdf.kernel.colors.ColorConstants.BLACK;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.signatures.PdfPKCS7;
import com.itextpdf.signatures.SignatureUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static java.lang.Math.toRadians;
import static java.lang.System.setProperty;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Paths.get;
import java.security.Principal;
import static java.security.Security.addProvider;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import static java.util.Calendar.getInstance;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.StringUtils.replace;
import org.apache.pdfbox.pdmodel.PDDocument;
import static org.apache.pdfbox.pdmodel.PDDocument.load;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.xmpbox.XMPMetadata;
import static org.apache.xmpbox.XMPMetadata.createXMPMetadata;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.xml.XmpSerializer;
import org.joda.time.DateTime;
import static rc.soop.sic.Constant.LOGGER;
import static rc.soop.sic.Constant.PATTERNDATE3;
import static rc.soop.sic.Constant.PATTERNDATE4;
import rc.soop.sic.jpa.Corso;
import rc.soop.sic.jpa.EntityOp;
import rc.soop.sic.jpa.Istanza;
import rc.soop.sic.jpa.Path;
import static org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory.createFromImage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Store;
import static rc.soop.sic.Utils.datemysqltoita;
import static rc.soop.sic.Utils.estraiEccezione;
import static rc.soop.sic.Utils.roundDoubleandFormat;
import rc.soop.sic.jpa.Calendario_Formativo;
import rc.soop.sic.jpa.TipoCorso;

/**
 *
 * @author Raffaele
 */
public class Pdf {

    public static void setFieldsValue(
            PdfAcroForm form,
            Map<String, PdfFormField> fields_list,
            String field_name,
            String field_value) {
        try {
            if (fields_list.get(field_name) != null) {
                if (field_value == null) {
                    field_value = "";
                }
                fields_list.get(field_name).setValue(field_value);
                form.partialFormFlattening(field_name);
            }
        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
        }
    }

    public static void printbarcode(BarcodeQRCode barcode, PdfDocument pdfDoc) {
        try {
            Rectangle rect = barcode.getBarcodeSize();
            PdfFormXObject formXObject = new PdfFormXObject(new Rectangle(rect.getWidth(), rect.getHeight() + 10));
            PdfCanvas pdfCanvas = new PdfCanvas(formXObject, pdfDoc);
            barcode.placeBarcode(pdfCanvas, BLACK);
            Image bCodeImage = new Image(formXObject);
            bCodeImage.setRotationAngle(toRadians(90));
            bCodeImage.setFixedPosition(25, 5);
            for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
                new Canvas(pdfDoc.getPage(i), pdfDoc.getDefaultPageSize()).add(bCodeImage);
            }
        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
        }
    }

    public static boolean checkPDF(File pdffile) {
        if (pdffile.exists()) {
            try {
                int pag;
                try (InputStream is = new FileInputStream(pdffile); PdfReader pdfReader = new PdfReader(is); PdfDocument pd = new PdfDocument(pdfReader)) {
                    pag = pd.getNumberOfPages();
                }
                return pag > 0;
            } catch (Exception ex) {
                LOGGER.severe(estraiEccezione(ex));
            }
        }
        return false;
    }

    public static void createDir(String path) {
        try {
            createDirectories(get(path));
        } catch (Exception e) {
        }
    }

    public static File convertPDFA(File pdf_ing, String nomepdf) {
        if (pdf_ing == null) {
            return null;
        }
        try {
            File pdfOutA = new File(replace(pdf_ing.getPath(), ".pdf", "_pdfA.pdf"));
            try (FileInputStream in = new FileInputStream(pdf_ing)) {
                setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
                try (PDDocument doc = PDDocument.load(pdf_ing)) {
                    int numPageTOT = 0;
                    Iterator<PDPage> it1 = doc.getPages().iterator();
                    while (it1.hasNext()) {
                        numPageTOT++;
                        it1.next();
                    }
                    PDPage page = new PDPage();
                    doc.setVersion(1.7f);
                    try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                        PDDocument docSource = PDDocument.load(in);
                        PDFRenderer pdfRenderer = new PDFRenderer(docSource);
                        for (int i = 0; i < numPageTOT; i++) {
                            BufferedImage imagePage = pdfRenderer.renderImageWithDPI(i, 200);
                            PDImageXObject pdfXOImage = createFromImage(doc, imagePage);
                            contents.drawImage(pdfXOImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
                        }
                    }
                    XMPMetadata xmp = createXMPMetadata();
                    PDDocumentCatalog catalogue = doc.getDocumentCatalog();
                    Calendar cal = getInstance();
                    DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
                    dc.addCreator("YISU");
                    dc.addDate(cal);
                    PDFAIdentificationSchema id = xmp.createAndAddPFAIdentificationSchema();
                    id.setPart(3);  //value => 2|3
                    id.setConformance("A"); // value => A|B|U
                    XmpSerializer serializer = new XmpSerializer();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    serializer.serialize(xmp, baos, true);
                    PDMetadata metadata = new PDMetadata(doc);
                    metadata.importXMPMetadata(baos.toByteArray());
                    catalogue.setMetadata(metadata);
                    InputStream colorProfile = new Pdf().getClass().getResourceAsStream("/sRGB.icc");
                    PDOutputIntent intent = new PDOutputIntent(doc, colorProfile);
                    intent.setInfo("sRGB IEC61966-2.1");
                    intent.setOutputCondition("sRGB IEC61966-2.1");
                    intent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
                    intent.setRegistryName("http://www.color.org");
                    catalogue.addOutputIntent(intent);
                    catalogue.setLanguage("it-IT");
                    PDViewerPreferences pdViewer = new PDViewerPreferences(page.getCOSObject());
                    pdViewer.setDisplayDocTitle(true);
                    catalogue.setViewerPreferences(pdViewer);
                    PDMarkInfo mark = new PDMarkInfo(); // new PDMarkInfo(page.getCOSObject());
                    PDStructureTreeRoot treeRoot = new PDStructureTreeRoot();
                    catalogue.setMarkInfo(mark);
                    catalogue.setStructureTreeRoot(treeRoot);
                    catalogue.getMarkInfo().setMarked(true);
                    PDDocumentInformation info = doc.getDocumentInformation();
                    info.setCreationDate(cal);
                    info.setModificationDate(cal);
                    info.setAuthor("SmartOOP1");
                    info.setProducer("SmartOOP2");
                    info.setCreator("SmartOOP3");
                    info.setTitle(nomepdf);
                    info.setSubject("PDF/A");
                    doc.save(pdfOutA);
                }
            }
            return pdfOutA;
        } catch (Exception ex) {
            LOGGER.severe(estraiEccezione(ex));
        }
        return null;
    }

    public static String checkFirmaQRpdfA(
            String TIPODOC,
            String CODICEDARICERCARE,
            File file,
            String cfuser,
            String qrcrop
    ) {
        if (TIPODOC.equalsIgnoreCase("ISTANZA")) { //  QR e FIRMA
            if (getExtension(file.getName()).endsWith("p7m")) {
                try {
                    SignedDoc dc = extractSignatureInformation_P7M(readFileToByteArray(file));
                    if (dc.isValido()) {
                        if (!dc.getCodicefiscale().toUpperCase().contains(cfuser.toUpperCase())) {
                            return "ERRORE NELL'UPLOAD - " + TIPODOC + " - CF NON CONFORME";
                        } else {
                            byte[] content = dc.getContenuto();
                            if (content == null) {
                                return "ERRORE NELL'UPLOAD - " + TIPODOC + " - CF NON CONFORME";
                            } else {
                                String esitoqr = verificaQR(TIPODOC, CODICEDARICERCARE, content, qrcrop);
                                if (!esitoqr.equals("OK")) {
                                    return "ERRORE NELL'UPLOAD - " + TIPODOC + " - " + esitoqr;
                                }
                            }
                        }
                    } else {
                        return "ERRORE NELL'UPLOAD - " + TIPODOC + " - " + dc.getErrore();
                    }
                } catch (Exception e) {
                    return "ERRORE NELL'UPLOAD - " + TIPODOC + " - " + estraiEccezione(e);
                }
            } else {
                try {
                    SignedDoc dc = extractSignatureInformation_PDF(readFileToByteArray(file));
                    if (dc.isValido()) {
                        if (!dc.getCodicefiscale().toUpperCase().contains(cfuser.toUpperCase())) {
                            return "ERRORE NELL'UPLOAD - " + TIPODOC + " - CF NON CONFORME";
                        } else {
                            byte[] content = dc.getContenuto();
                            if (content == null) {
                                return "ERRORE NELL'UPLOAD - " + TIPODOC + " - CF NON CONFORME";
                            } else {
                                String esitoqr = verificaQR(TIPODOC, CODICEDARICERCARE, content, qrcrop);
                                if (!esitoqr.equals("OK")) {
                                    return "ERRORE NELL'UPLOAD - " + TIPODOC + " - " + esitoqr;
                                }
                            }
                        }
                    } else {
                        return "ERRORE NELL'UPLOAD - " + TIPODOC + " - " + dc.getErrore();
                    }
                } catch (Exception e) {
                    return "ERRORE NELL'UPLOAD - " + TIPODOC + " - " + estraiEccezione(e);
                }
            }
        }
        return "OK";
    }

    private static String verificaQR(String TIPODOC, String CODICEDARICERCARE, byte[] content, String qrcrop) {
        String pdfa = "OK";
        if (TIPODOC.equalsIgnoreCase("ISTANZA")) {
            pdfa = verificaPDFA(content);
        }
        if (!pdfa.equals("OK")) {
            return pdfa;
        } else {
            String out = "KO";
            if (content != null) {
                try {
                    String qr;
                    try (InputStream is1 = new ByteArrayInputStream(content)) {
                        PDDocument doc = load(is1);
                        qr = estraiResult(doc, qrcrop, 0);
                    }
                    if (qr == null) {
                        return "ERRORE E1 - DOCUMENTO NON CORRISPONDE A QUANTO RICHIESTO.";
                    } else {
                        if (qr.contains(CODICEDARICERCARE.toUpperCase())) {
                            if (qr.contains(CODICEDARICERCARE.toUpperCase())) {
                                out = "OK";
                            } else {
                                out = "ERRORE E2 - DOCUMENTO NON CORRISPONDE A QUANTO RICHIESTO.";
                            }
                        } else {
                            return "ERRORE E3 - DOCUMENTO NON CORRISPONDE A QUANTO RICHIESTO.";
                        }
                    }
                } catch (Exception ex) {
                    if (ex.getMessage() == null) {
                        out = "ERRORE E4 - QR CODE ILLEGGIBILE";
                    } else {
                        out = "ERRORE E5 - " + estraiEccezione(ex);
                    }
                }
            }
            return out;
        }

    }

    private static String estraiResult(PDDocument doc, String qrcrop, int pag) {
        try {
            PDPage page = doc.getPage(pag);
            if (qrcrop == null) {
                page.setCropBox(new PDRectangle(20, 0, 60, 60));
            } else {
                String[] cropdim = qrcrop.split(";");
                page.setCropBox(new PDRectangle(
                        Integer.parseInt(cropdim[0]),
                        Integer.parseInt(cropdim[1]),
                        Integer.parseInt(cropdim[2]),
                        Integer.parseInt(cropdim[3]))
                );
            }
            PDFRenderer pr = new PDFRenderer(doc);
            BufferedImage bi = pr.renderImageWithDPI(pag, 400);
            int[] pixels = bi.getRGB(0, 0,
                    bi.getWidth(), bi.getHeight(),
                    null, 0, bi.getWidth());
            RGBLuminanceSource source = new RGBLuminanceSource(bi.getWidth(),
                    bi.getHeight(),
                    pixels);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Map<DecodeHintType, Object> decodeHints = new HashMap<>();
            decodeHints.put(
                    DecodeHintType.TRY_HARDER,
                    Boolean.TRUE
            );

//
            try {
                ImageIO.write(bi, "jpg", new File("D:\\mnt\\qr" + pag + ".png"));
            } catch (Exception e) {
            }
            Result result = new QRCodeReader().decode(bitmap, decodeHints);
            doc.close();
            String qr = result.getText().toUpperCase();
            return qr;
        } catch (Exception ex) {
            if (ex.getMessage() == null) {
                return estraiResult(doc, qrcrop, pag + 1);
            }
            ex.printStackTrace();
            return null;
        }
    }

    private static SignedDoc extractSignatureInformation_P7M(byte[] p7m_bytes) {
        SignedDoc doc = new SignedDoc();
        CMSSignedData cms;
        try {
            cms = new CMSSignedData(p7m_bytes);
        } catch (Exception e) {
            doc.setErrore("ERRORE NEL FILE - " + e.getMessage());
            return doc;
        }
        if (cms.getSignedContent() == null) {
            doc.setErrore("ERRORE NEL FILE - CONTENUTO ERRATO");
            return doc;
        }
        try {
            Store<X509CertificateHolder> store = cms.getCertificates();
            Collection<X509CertificateHolder> allCerts = store.getMatches(null);
            if (!allCerts.isEmpty()) {
                X509CertificateHolder x509h = allCerts.iterator().next();
                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                try (InputStream in = new ByteArrayInputStream(x509h.getEncoded())) {
                    X509Certificate cert = (X509Certificate) certFactory.generateCertificate(in);
                    Principal principal = cert.getSubjectDN();
                    try {
                        cert.checkValidity();
                        doc.setValido(true);
                    } catch (Exception e) {
                        doc.setValido(false);
                        doc.setErrore(e.getMessage());
                    }
                    if (doc.isValido()) {
//                        String cf = substringBefore(substringAfter(principal.getName(), "SERIALNUMBER="), ", GIVENNAME");
                        doc.setCodicefiscale(principal.getName().toUpperCase());
                        doc.setContenuto((byte[]) cms.getSignedContent().getContent());
                    } else {
                        if (new DateTime(cert.getNotAfter().getTime()).isBeforeNow()) {
                            doc.setErrore("CERTIFICATO SCADUTO IN DATA " + new DateTime(cert.getNotAfter().getTime()).toString("dd/MM/yyyy HH:mm:ss"));
                        }
                    }
                }
            } else {
                doc.setErrore("ERRORE NEL FILE - CERTIFICATI NON TROVATI");
                return doc;
            }
        } catch (Exception ex) {
            doc.setValido(false);
            doc.setErrore("ERRORE NEL FILE - " + ex.getMessage());
        }
        return doc;
    }

    private static SignedDoc extractSignatureInformation_PDF(byte[] pdf_bytes) {
        SignedDoc doc = new SignedDoc();
        try {
            BouncyCastleProvider provider = new BouncyCastleProvider();
            addProvider(provider);
            try (InputStream is = new ByteArrayInputStream(pdf_bytes); PdfReader read = new PdfReader(is); PdfDocument pdfDoc = new PdfDocument(read)) {
                AtomicInteger error = new AtomicInteger(0);
                SignatureUtil signatureUtil = new SignatureUtil(pdfDoc);
                List<String> li = signatureUtil.getSignatureNames();
                if (li.isEmpty()) {
                    doc.setErrore("ERRORE NEL FILE - NESSUNA FIRMA");
                } else {
                    li.forEach(name -> {
                        if (error.get() == 0) {
                            PdfPKCS7 signature1 = signatureUtil.readSignatureData(name);
                            if (signature1 != null) {
                                X509Certificate cert = signature1.getSigningCertificate();
                                try {
                                    boolean es = signature1.verifySignatureIntegrityAndAuthenticity();
                                    if (es) {
                                        Principal principal = cert.getSubjectDN();
                                        doc.setCodicefiscale(principal.getName().toUpperCase());
                                        doc.setContenuto(pdf_bytes);
                                        doc.setValido(true);
                                    } else {
                                        doc.setErrore("ERRORE NEL FILE - CERTIFICATO NON VALIDO");
                                        error.addAndGet(1);
                                        doc.setValido(false);
                                    }
                                } catch (Exception e) {
                                    doc.setValido(false);
                                    doc.setErrore("ERRORE NEL FILE - " + e.getMessage());
                                    error.addAndGet(1);
                                }
                            } else {
                                doc.setValido(false);
                                doc.setErrore("ERRORE NEL FILE - FIRMA NON VALDA");
                                error.addAndGet(1);
                            }
                        }
                    });
                }
            }
            return doc;
        } catch (Exception e) {
            doc.setErrore("ERRORE NEL FILE - " + e.getMessage());
            return doc;
        }
    }

    private static String verificaPDFA(byte[] content) {
        String out = "KO";
        try {
            setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
            if (content != null) {
                try (InputStream is1 = new ByteArrayInputStream(content); PDDocument doc = load(is1)) {
                    PDDocumentInformation info = doc.getDocumentInformation();
                    if (info.getSubject() != null) {
                        if (info.getSubject().equals("PDF/A")) {
                            out = "OK";
                        } else {
                            out = "ERRORE E1 NEL FILE - NO PDF/A";
                        }
                    } else {
                        out = "ERRORE E2 NEL FILE - NO PDF/A";
                    }
                }
            }
        } catch (Exception e) {
            out = "ERRORE E3 NEL FILE - " + estraiEccezione(e);
        }
        return out;

    }

    public static File GENERAISTANZA(Istanza ista1) {

        try {

            EntityOp e = new EntityOp();
            List<Corso> lista_corsi = e.getCorsiIstanza(ista1);
            Path pathtemp = e.getEm().find(Path.class, "path.temp");
            Path template_part1 = e.getEm().find(Path.class, "pdf.istanza.v1.p1");
            Path template_part2 = e.getEm().find(Path.class, "pdf.istanza.v1.p2");
            Path template_part3 = e.getEm().find(Path.class, "pdf.istanza.v1.p3");
            Path template_part4 = e.getEm().find(Path.class, "pdf.istanza.v1.p4");
            DateTime datael = new DateTime();

            createDir(pathtemp.getDescrizione());

            File pdfOut_part1 = new File(pathtemp.getDescrizione() + "ISTANZA" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".I1.pdf");
            File pdfOut_part2 = new File(pathtemp.getDescrizione() + "ISTANZA" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".I2.pdf");
            File pdfOut_part3 = new File(pathtemp.getDescrizione() + "ISTANZA" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".I3.pdf");
            File pdfOut_part4 = new File(pathtemp.getDescrizione() + "ISTANZA" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".I4.pdf");
            try (InputStream is = new FileInputStream(template_part1.getDescrizione()); PdfReader reader = new PdfReader(is); PdfWriter writer = new PdfWriter(pdfOut_part1); PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
                PdfAcroForm form = getAcroForm(pdfDoc, true);
                form.setGenerateAppearance(true);
                Map<String, PdfFormField> fields = form.getAllFormFields();

                setFieldsValue(form, fields, "Prot", ista1.getProtocollosoggetto());
                setFieldsValue(form, fields, "del", ista1.getProtocollosoggettodata());

                lista_corsi.forEach(co1 -> {

                    setFieldsValue(form, fields, "A_TIPOCORSO", co1.getSchedaattivita().getCertificazioneuscita().getNome());
                    setFieldsValue(form, fields, "A_EDIZIONI", String.valueOf(co1.getQuantitarichiesta()));
                    setFieldsValue(form, fields, "A_DURATASTAGE", String.valueOf(co1.getStageore()));
                    setFieldsValue(form, fields, "A_NUMEROGIORNI", String.valueOf(co1.getDuratagiorni()));
                    setFieldsValue(form, fields, "A_NUMEROALLIEVI", String.valueOf(co1.getNumeroallievi()));
                    setFieldsValue(form, fields, "A_TIPOCERT", co1.getSchedaattivita().getCertificazioneuscita().getNome());

                    setFieldsValue(form, fields, "A_SEDEINDIRIZZO", co1.getSedescelta().getIndirizzo());
                    setFieldsValue(form, fields, "A_SEDECAP", co1.getSedescelta().getCap());
                    setFieldsValue(form, fields, "A_SEDECITTA", co1.getSedescelta().getComune() + " (" + co1.getSedescelta().getProvincia() + ")");
                    setFieldsValue(form, fields, "A_TELEFONO", co1.getSoggetto().getTELEFONO());

                    //statiche
                    setFieldsValue(form, fields, "A_CODICEISTAT", "5.4.4.3.0 - addetti all'assistenza personale");
                    setFieldsValue(form, fields, "A_NOMECORSO", "Assistente all'autonomia ed alla comunicazione dei disabili");
                    setFieldsValue(form, fields, "A_LIVELLO", "EQF 4");
                    setFieldsValue(form, fields, "A_DURATAORE", "300");
                    setFieldsValue(form, fields, "A_REQUISITIMINIMI", "Livello minimo di Scolarità: Scuola secondaria di II grado/diploma professionale");

                });

                fields.forEach((t, u) -> {
                    form.partialFormFlattening(t);
                });
                form.flattenFields();
            }

            try (InputStream is2 = new FileInputStream(template_part2.getDescrizione()); PdfReader reader2 = new PdfReader(is2); PdfWriter writer2 = new PdfWriter(pdfOut_part2); PdfDocument pdfDoc2 = new PdfDocument(reader2, writer2)) {

                PdfAcroForm form2 = getAcroForm(pdfDoc2, true);
                form2.setGenerateAppearance(true);
                Map<String, PdfFormField> fields2 = form2.getAllFormFields();

                setFieldsValue(form2, fields2, "Titolo Conseguito", "Assistente all'autonomia ed alla comunicazione dei disabili");

                lista_corsi.forEach(co1 -> {
                    setFieldsValue(form2, fields2, "Tipo", co1.getSchedaattivita().getCertificazioneuscita().getNome());
                    setFieldsValue(form2, fields2, "A_EDIZIONI", String.valueOf(co1.getQuantitarichiesta()));
                    setFieldsValue(form2, fields2, "A_DURATASTAGE", String.valueOf(co1.getStageore()));
                    setFieldsValue(form2, fields2, "A_NUMEROGIORNI", String.valueOf(co1.getDuratagiorni()));
                    setFieldsValue(form2, fields2, "A_NUMEROALLIEVI", String.valueOf(co1.getNumeroallievi()));
                    setFieldsValue(form2, fields2, "eLearning", String.valueOf(co1.getElearningperc()) + " %");
                    //statiche
                    setFieldsValue(form2, fields2, "A_CODICEISTAT", "5.4.4.3.0 - addetti all'assistenza personale");
                    setFieldsValue(form2, fields2, "A_NOMECORSO", "Assistente all'autonomia ed alla comunicazione dei disabili");
                    setFieldsValue(form2, fields2, "A_LIVELLO", "EQF 4");
                    setFieldsValue(form2, fields2, "A_DURATAORE", "300");
                    setFieldsValue(form2, fields2, "A_REQUISITIMINIMI", "Livello minimo di Scolarità: Scuola secondaria di II grado/diploma professionale");
                });

                fields2.forEach((t, u) -> {
                    form2.partialFormFlattening(t);
                });

                form2.flattenFields();

            }

            try (InputStream is3 = new FileInputStream(template_part3.getDescrizione()); PdfReader reader3 = new PdfReader(is3); PdfWriter writer3 = new PdfWriter(pdfOut_part3); PdfDocument pdfDoc3 = new PdfDocument(reader3, writer3)) {

                PdfAcroForm form3 = getAcroForm(pdfDoc3, true);
                form3.setGenerateAppearance(true);
                Map<String, PdfFormField> fields3 = form3.getAllFormFields();

                setFieldsValue(form3, fields3, "CITTA", ista1.getSoggetto().getSedelegale().getComune());
                setFieldsValue(form3, fields3, "DATA", new DateTime().toString(PATTERNDATE4));

                fields3.forEach((t, u) -> {
                    form3.partialFormFlattening(t);
                });

                form3.flattenFields();

            }

            try (InputStream is4 = new FileInputStream(template_part4.getDescrizione()); PdfReader reader4 = new PdfReader(is4); PdfWriter writer4 = new PdfWriter(pdfOut_part4); PdfDocument pdfDoc4 = new PdfDocument(reader4, writer4)) {

                PdfAcroForm form4 = getAcroForm(pdfDoc4, true);
                form4.setGenerateAppearance(true);
                Map<String, PdfFormField> fields4 = form4.getAllFormFields();

                setFieldsValue(form4, fields4, "CITTA", ista1.getSoggetto().getSedelegale().getComune());
                setFieldsValue(form4, fields4, "DATA", new DateTime().toString(PATTERNDATE4));

                fields4.forEach((t, u) -> {
                    form4.partialFormFlattening(t);
                });

                form4.flattenFields();

            }

            File pdfOut_FINAL = new File(pathtemp.getDescrizione() + "ISTANZA" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".IF.pdf");
            try (PdfDocument pdf = new PdfDocument(new PdfWriter(pdfOut_FINAL))) {
                PdfMerger merger = new PdfMerger(pdf);
                PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(pdfOut_part1));
                merger.merge(firstSourcePdf, 1, firstSourcePdf.getNumberOfPages());
                PdfDocument secondSourcePdf = new PdfDocument(new PdfReader(pdfOut_part2));
                merger.merge(secondSourcePdf, 1, secondSourcePdf.getNumberOfPages());
                PdfDocument thSourcePdf = new PdfDocument(new PdfReader(pdfOut_part3));
                merger.merge(thSourcePdf, 1, thSourcePdf.getNumberOfPages());
                PdfDocument foSourcePdf = new PdfDocument(new PdfReader(pdfOut_part4));
                merger.merge(foSourcePdf, 1, foSourcePdf.getNumberOfPages());

                BarcodeQRCode barcode = new BarcodeQRCode(ista1.getCodiceistanza() + " / ISTANZA / "
                        + ista1.getSoggetto().getRap_cf() + "_" + datael.toString(PATTERNDATE3));
                printbarcode(barcode, pdf);

                firstSourcePdf.close();
                secondSourcePdf.close();
                thSourcePdf.close();
                foSourcePdf.close();
                merger.close();
            }

            pdfOut_part1.delete();
            pdfOut_part2.delete();
            pdfOut_part3.delete();
            pdfOut_part4.delete();

            try {
                File fdef = convertPDFA(pdfOut_FINAL, "ISTANZA " + ista1.getCodiceistanza());
                if (fdef != null) {
                    pdfOut_FINAL.delete();
                    return fdef;
                }
            } catch (Exception ex1) {
                LOGGER.severe(estraiEccezione(ex1));
            }

            return pdfOut_FINAL;
        } catch (Exception ex0) {
            LOGGER.severe(estraiEccezione(ex0));
        }
        return null;
    }

    private static final String NOMESERVIZIO = "Servizio 3 Sistema di Accreditamento della Formazione Professionale e Certificazione delle Competenze";
    
    private static final String VISTO1 = "il D.D.G. n. 1154 del 22 settembre 2022 con il quale il Dirigente Generale del Dipartimento della "
            + "Formazione Professionale ha conferito, alla Dott.ssa Maria Josè Verde, l’incarico di Dirigente "
            + "responsabile del Servizio 3 “Sistema di Accreditamento della Formazione Professionale e "
            + "Certificazione delle Competenze” con decorrenza 20 giugno 2022;";

    private static final String FUNZCARICA = "Il Funzionario Direttivo";
    private static final String FUNZNOME = "Giulio Giuliani";
    private static final String DIRCARICA = "IL DIRIGENTE DEL SERVIZIO";
    private static final String DIRNOME = "Maria Josè Verde";

    public static File GENERADECRETODDSFTO(EntityOp ep, Istanza ista1) {
        try {
            String DDS = ista1.getDecreto().split("§")[0];
            String DDSDATA = datemysqltoita(ista1.getDecreto().split("§")[1]);
            Path pathtemp = ep.getEm().find(Path.class, "path.temp");
            Path template_parte1 = ep.getEm().find(Path.class, "pdf.decreto.ok.p1");
            Path template_parte2 = ep.getEm().find(Path.class, "pdf.decreto.ok.p2");
            Path template_parte_corso = ep.getEm().find(Path.class, "pdf.decreto.ok.corso1");

            DateTime datael = new DateTime();

            createDir(pathtemp.getDescrizione());

            File pdfOut_part1 = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".D1.pdf");
            File pdfOut_part2 = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".D2.pdf");
            File pdfOut_part_corso = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".C1.pdf");

            try (InputStream is = new ByteArrayInputStream(Base64.decodeBase64(template_parte_corso.getDescrizione())); PdfReader reader = new PdfReader(is); PdfWriter writer = new PdfWriter(pdfOut_part_corso); PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
                PdfAcroForm form = getAcroForm(pdfDoc, true);
                form.setGenerateAppearance(true);
                Map<String, PdfFormField> fields = form.getAllFormFields();

                setFieldsValue(form, fields, "DDS", DDS);
                setFieldsValue(form, fields, "DDSDATA", DDSDATA);
                setFieldsValue(form, fields, "paginecorso", "PAGINA 5 DI 7");

                List<Corso> c1 = ep.getCorsiIstanza(ista1);
                AtomicInteger indice = new AtomicInteger(1);
                for (Corso c2 : c1) {
                    if (c2.getStatocorso().getCodicestatocorso().equals("24")) {
                        double t_comp_base = 0.0;
                        double t_aula_teorica = 0.0;
                        double t_aula_el = 0.0;
                        double t_aula_tecnope = 0.0;
                        double t_aula = 0.0;
                        double t_tot = 0.0;
                        List<Calendario_Formativo> calendar = ep.calendario_formativo_corso(c2);
                        for (Calendario_Formativo cal2 : calendar) {
                            if (cal2.getTipomodulo().equals("BASE")) {
                                t_comp_base = t_comp_base + cal2.getOre();
                            }
                            t_aula_teorica = t_aula_teorica + cal2.getOre_teorica_aula();
                            t_aula_el = t_aula_el + cal2.getOre_teorica_el();
                            t_aula_tecnope = t_aula_tecnope + cal2.getOre_tecnica_operativa();
                            t_aula = t_aula + cal2.getOre_aula();
                            t_tot = t_tot + cal2.getOre();
                        }

                        setFieldsValue(form, fields, indice.get() + "_codice", c2.getIdentificativocorso());
                        setFieldsValue(form, fields, indice.get() + "_nome", c2.getRepertorio().getDenominazione());

                        setFieldsValue(form, fields, indice.get() + "_sede", c2.getSedescelta().getComune() + ", " + c2.getSedescelta().getIndirizzo());
                        setFieldsValue(form, fields, indice.get() + "_accesso", c2.getSchedaattivita().getPrerequisiti());
                        setFieldsValue(form, fields, indice.get() + "_oreteoria", roundDoubleandFormat(t_aula_teorica, 2));
                        setFieldsValue(form, fields, indice.get() + "_orepratica", roundDoubleandFormat(t_aula_tecnope, 2));
                        setFieldsValue(form, fields, indice.get() + "_orestage", String.valueOf(c2.getStageore()));
                        setFieldsValue(form, fields, indice.get() + "_orefad", roundDoubleandFormat(t_aula_el, 2));
                        setFieldsValue(form, fields, indice.get() + "_altro", "");
                        setFieldsValue(form, fields, indice.get() + "_giorni", String.valueOf(c2.getDuratagiorni()));
                        setFieldsValue(form, fields, indice.get() + "_numallievi", String.valueOf(c2.getNumeroallievi()));

                    }
                }

                fields.forEach((t, u) -> {
                    form.partialFormFlattening(t);
                });
                form.flattenFields();

            }

            try (InputStream is = new ByteArrayInputStream(Base64.decodeBase64(template_parte1.getDescrizione())); PdfReader reader = new PdfReader(is); PdfWriter writer = new PdfWriter(pdfOut_part1); PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
                PdfAcroForm form = getAcroForm(pdfDoc, true);
                form.setGenerateAppearance(true);
                Map<String, PdfFormField> fields = form.getAllFormFields();

                String ogg = "Autorizzazione allo svolgimento dei percorsi formativi autofinanziati di cui all'istanza "
                        + "acquisita al protocollo xxxxx del xx/xx/xxxx- ente gestore " + ista1.getSoggetto().getRAGIONESOCIALE() + " con "
                        + "sede legale in " + ista1.getSoggetto().getSedelegale().getComune() + ", C.I.R. " + ista1.getSoggetto().getCIR() + ".";
                if (ista1.getTipologiapercorso().getTipocorso().equals(TipoCorso.FINANZIATO)) {
                    ogg = "Autorizzazione allo svolgimento dei percorsi formativi finanziati di cui all'istanza "
                            + "acquisita al protocollo xxxxx del xx/xx/xxxx- ente gestore " + ista1.getSoggetto().getRAGIONESOCIALE() + " con "
                            + "sede legale in " + ista1.getSoggetto().getSedelegale().getComune() + ", C.I.R. " + ista1.getSoggetto().getCIR() + ".";
                }

                String ART1 = "Su proposta del Dirigente responsabile del Servizio 3 \"Sistema di Accreditamento della Formazione "
                        + "Professionale e Certificazione delle Competenze\" si autorizza l'ente di formazione " + ista1.getSoggetto().getRAGIONESOCIALE()
                        + ", con sede legale in Santa " + ista1.getSoggetto().getSedelegale().getComune()
                        + ", accreditato con D.D.G. n° " + ista1.getSoggetto().getDDGNUMERO() + " del " + ista1.getSoggetto().getDDGNUMERO() + ", Partita IVA "
                        + ista1.getSoggetto().getPARTITAIVA() + ", C.I.R. " + ista1.getSoggetto().getCIR()
                        + ", ad attuare i sottostanti percorsi formativi autofinanziati, di seguito elencati:";

                if (ista1.getTipologiapercorso().getTipocorso().equals(TipoCorso.FINANZIATO)) {
                    ART1 = "Su proposta del Dirigente responsabile del Servizio 3 \"Sistema di Accreditamento della Formazione "
                            + "Professionale e Certificazione delle Competenze\" si autorizza l'ente di formazione " + ista1.getSoggetto().getRAGIONESOCIALE()
                            + ", con sede legale in Santa " + ista1.getSoggetto().getSedelegale().getComune()
                            + ", accreditato con D.D.G. n° " + ista1.getSoggetto().getDDGNUMERO() + " del " + ista1.getSoggetto().getDDGNUMERO() + ", Partita IVA "
                            + ista1.getSoggetto().getPARTITAIVA() + ", C.I.R. " + ista1.getSoggetto().getCIR()
                            + ", ad attuare i sottostanti percorsi formativi finanziati, di seguito elencati:";
                }

                setFieldsValue(form, fields, "NOMESERVIZIO", NOMESERVIZIO);
                setFieldsValue(form, fields, "DESCRIZIONE", ogg);
                setFieldsValue(form, fields, "DDS", DDS);
                setFieldsValue(form, fields, "DDSDATA", DDSDATA);
                setFieldsValue(form, fields, "pag1", "PAGINA 1 DI 7");
                setFieldsValue(form, fields, "pag2", "PAGINA 2 DI 7");
                setFieldsValue(form, fields, "pag3", "PAGINA 3 DI 7");
                setFieldsValue(form, fields, "pag4", "PAGINA 4 DI 7");
                setFieldsValue(form, fields, "VISTO1", VISTO1);
                setFieldsValue(form, fields, "ART1", ART1);

                fields.forEach((t, u) -> {
                    form.partialFormFlattening(t);
                });
                form.flattenFields();

//                BarcodeQRCode barcode = new BarcodeQRCode(ista1.getCodiceistanza() + " / DECRETOAPPROVATIVO / "
//                        + ista1.getSoggetto().getRap_cf() + "_" + datael.toString(PATTERNDATE3));
//                printbarcode(barcode, pdfDoc);
            }

            try (InputStream is = new ByteArrayInputStream(Base64.decodeBase64(template_parte2.getDescrizione())); PdfReader reader = new PdfReader(is); PdfWriter writer = new PdfWriter(pdfOut_part2); PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
                PdfAcroForm form = getAcroForm(pdfDoc, true);
                form.setGenerateAppearance(true);
                Map<String, PdfFormField> fields = form.getAllFormFields();

                setFieldsValue(form, fields, "NOMESERVIZIO", NOMESERVIZIO);
                setFieldsValue(form, fields, "RAGIONESOCIALE", ista1.getSoggetto().getRAGIONESOCIALE());
                setFieldsValue(form, fields, "PARTITAIVA", ista1.getSoggetto().getPARTITAIVA());
                setFieldsValue(form, fields, "CIR", ista1.getSoggetto().getCIR());

                setFieldsValue(form, fields, "DDS", DDS);
                setFieldsValue(form, fields, "DDSDATA", DDSDATA);
                setFieldsValue(form, fields, "pagA", "PAGINA 6 DI 7");
                setFieldsValue(form, fields, "pagB", "PAGINA 7 DI 7");

                setFieldsValue(form, fields, "DATA", ista1.getDatagestione().split(" ")[0]);
                setFieldsValue(form, fields, "funz1carica", FUNZCARICA);
                setFieldsValue(form, fields, "funz1nome", FUNZNOME);
                setFieldsValue(form, fields, "funz1fto", "F.TO");
                setFieldsValue(form, fields, "dir1carica", DIRCARICA);
                setFieldsValue(form, fields, "dir1nome", DIRNOME);
                setFieldsValue(form, fields, "dir1fto", "F.TO");

                fields.forEach((t, u) -> {
                    form.partialFormFlattening(t);
                });
                form.flattenFields();

            }

            File pdfOut_FINAL = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".F.pdf");

            try (PdfDocument pdf = new PdfDocument(new PdfWriter(pdfOut_FINAL))) {
                PdfMerger merger = new PdfMerger(pdf);
                PdfDocument parte1 = new PdfDocument(new PdfReader(pdfOut_part1));
                merger.merge(parte1, 1, parte1.getNumberOfPages());

                PdfDocument partecorsi = new PdfDocument(new PdfReader(pdfOut_part_corso));
                merger.merge(partecorsi, 1, partecorsi.getNumberOfPages());

                PdfDocument parte2 = new PdfDocument(new PdfReader(pdfOut_part2));
                merger.merge(parte2, 1, parte2.getNumberOfPages());

                parte1.close();
                parte2.close();
                partecorsi.close();
                merger.close();
                pdfOut_part1.delete();
                pdfOut_part2.delete();
                pdfOut_part_corso.delete();
            }

            try {
//                File fdef = convertPDFA(pdfOut_part1, "DECRETOAPPROVATIVO ISTANZA " + ista1.getCodiceistanza());
//                if (fdef != null) {
//                    pdfOut_part1.delete();
                System.out.println("tester.T.GENERADECRETOAPPROVATIVO() " + pdfOut_FINAL.getPath());
                return pdfOut_FINAL;
//                }
            } catch (Exception ex1) {
                LOGGER.severe(estraiEccezione(ex1));
            }

        } catch (Exception ex0) {
            LOGGER.severe(estraiEccezione(ex0));
        }
        return null;
    }

    public static File GENERADECRETOBASE(EntityOp ep, Istanza ista1) {
        try {

            Path pathtemp = ep.getEm().find(Path.class, "path.temp");
            Path template_parte1 = ep.getEm().find(Path.class, "pdf.decreto.ok.p1");
            Path template_parte2 = ep.getEm().find(Path.class, "pdf.decreto.ok.p2");
            Path template_parte_corso = ep.getEm().find(Path.class, "pdf.decreto.ok.corso1");

            DateTime datael = new DateTime();

            createDir(pathtemp.getDescrizione());

            File pdfOut_part1 = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".D1.pdf");
            File pdfOut_part2 = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".D2.pdf");
            File pdfOut_part_corso = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".C1.pdf");

            try (InputStream is = new ByteArrayInputStream(Base64.decodeBase64(template_parte_corso.getDescrizione())); PdfReader reader = new PdfReader(is); PdfWriter writer = new PdfWriter(pdfOut_part_corso); PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
                PdfAcroForm form = getAcroForm(pdfDoc, true);
                form.setGenerateAppearance(true);
                Map<String, PdfFormField> fields = form.getAllFormFields();

                setFieldsValue(form, fields, "DDS", "");
                setFieldsValue(form, fields, "DDDSDATA", "");
                setFieldsValue(form, fields, "paginecorso", "PAGINA 5 DI 7");

                List<Corso> c1 = ep.getCorsiIstanza(ista1);
                AtomicInteger indice = new AtomicInteger(1);
                for (Corso c2 : c1) {
                    if (c2.getStatocorso().getCodicestatocorso().equals("24")) {
                        double t_comp_base = 0.0;
                        double t_aula_teorica = 0.0;
                        double t_aula_el = 0.0;
                        double t_aula_tecnope = 0.0;
                        double t_aula = 0.0;
                        double t_tot = 0.0;
                        List<Calendario_Formativo> calendar = ep.calendario_formativo_corso(c2);
                        for (Calendario_Formativo cal2 : calendar) {
                            if (cal2.getTipomodulo().equals("BASE")) {
                                t_comp_base = t_comp_base + cal2.getOre();
                            }
                            t_aula_teorica = t_aula_teorica + cal2.getOre_teorica_aula();
                            t_aula_el = t_aula_el + cal2.getOre_teorica_el();
                            t_aula_tecnope = t_aula_tecnope + cal2.getOre_tecnica_operativa();
                            t_aula = t_aula + cal2.getOre_aula();
                            t_tot = t_tot + cal2.getOre();
                        }

                        setFieldsValue(form, fields, indice.get() + "_codice", c2.getIdentificativocorso());
                        setFieldsValue(form, fields, indice.get() + "_nome", c2.getRepertorio().getDenominazione());

                        setFieldsValue(form, fields, indice.get() + "_sede", c2.getSedescelta().getComune() + ", " + c2.getSedescelta().getIndirizzo());
                        setFieldsValue(form, fields, indice.get() + "_accesso", c2.getSchedaattivita().getPrerequisiti());
                        setFieldsValue(form, fields, indice.get() + "_oreteoria", roundDoubleandFormat(t_aula_teorica, 2));
                        setFieldsValue(form, fields, indice.get() + "_orepratica", roundDoubleandFormat(t_aula_tecnope, 2));
                        setFieldsValue(form, fields, indice.get() + "_orestage", String.valueOf(c2.getStageore()));
                        setFieldsValue(form, fields, indice.get() + "_orefad", roundDoubleandFormat(t_aula_el, 2));
                        setFieldsValue(form, fields, indice.get() + "_altro", "");
                        setFieldsValue(form, fields, indice.get() + "_giorni", String.valueOf(c2.getDuratagiorni()));
                        setFieldsValue(form, fields, indice.get() + "_numallievi", String.valueOf(c2.getNumeroallievi()));

                    }
                }

                fields.forEach((t, u) -> {
                    form.partialFormFlattening(t);
                });
                form.flattenFields();

            }

            try (InputStream is = new ByteArrayInputStream(Base64.decodeBase64(template_parte1.getDescrizione())); PdfReader reader = new PdfReader(is); PdfWriter writer = new PdfWriter(pdfOut_part1); PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
                PdfAcroForm form = getAcroForm(pdfDoc, true);
                form.setGenerateAppearance(true);
                Map<String, PdfFormField> fields = form.getAllFormFields();

                String ogg = "Autorizzazione allo svolgimento dei percorsi formativi autofinanziati di cui all'istanza "
                        + "acquisita al protocollo xxxxx del xx/xx/xxxx- ente gestore " + ista1.getSoggetto().getRAGIONESOCIALE() + " con "
                        + "sede legale in " + ista1.getSoggetto().getSedelegale().getComune() + ", C.I.R. " + ista1.getSoggetto().getCIR() + ".";
                if (ista1.getTipologiapercorso().getTipocorso().equals(TipoCorso.FINANZIATO)) {
                    ogg = "Autorizzazione allo svolgimento dei percorsi formativi finanziati di cui all'istanza "
                            + "acquisita al protocollo xxxxx del xx/xx/xxxx- ente gestore " + ista1.getSoggetto().getRAGIONESOCIALE() + " con "
                            + "sede legale in " + ista1.getSoggetto().getSedelegale().getComune() + ", C.I.R. " + ista1.getSoggetto().getCIR() + ".";
                }

                String ART1 = "Su proposta del Dirigente responsabile del Servizio 3 \"Sistema di Accreditamento della Formazione "
                        + "Professionale e Certificazione delle Competenze\" si autorizza l'ente di formazione " + ista1.getSoggetto().getRAGIONESOCIALE()
                        + ", con sede legale in Santa " + ista1.getSoggetto().getSedelegale().getComune()
                        + ", accreditato con D.D.G. n° " + ista1.getSoggetto().getDDGNUMERO() + " del " + ista1.getSoggetto().getDDGNUMERO() + ", Partita IVA "
                        + ista1.getSoggetto().getPARTITAIVA() + ", C.I.R. " + ista1.getSoggetto().getCIR()
                        + ", ad attuare i sottostanti percorsi formativi autofinanziati, di seguito elencati:";

                if (ista1.getTipologiapercorso().getTipocorso().equals(TipoCorso.FINANZIATO)) {
                    ART1 = "Su proposta del Dirigente responsabile del Servizio 3 \"Sistema di Accreditamento della Formazione "
                            + "Professionale e Certificazione delle Competenze\" si autorizza l'ente di formazione " + ista1.getSoggetto().getRAGIONESOCIALE()
                            + ", con sede legale in Santa " + ista1.getSoggetto().getSedelegale().getComune()
                            + ", accreditato con D.D.G. n° " + ista1.getSoggetto().getDDGNUMERO() + " del " + ista1.getSoggetto().getDDGNUMERO() + ", Partita IVA "
                            + ista1.getSoggetto().getPARTITAIVA() + ", C.I.R. " + ista1.getSoggetto().getCIR()
                            + ", ad attuare i sottostanti percorsi formativi finanziati, di seguito elencati:";
                }

                setFieldsValue(form, fields, "NOMESERVIZIO", NOMESERVIZIO);
                setFieldsValue(form, fields, "DESCRIZIONE", ogg);
                setFieldsValue(form, fields, "DDS", "");
                setFieldsValue(form, fields, "DDDSDATA", "");
                setFieldsValue(form, fields, "pag1", "PAGINA 1 DI 7");
                setFieldsValue(form, fields, "pag2", "PAGINA 2 DI 7");
                setFieldsValue(form, fields, "pag3", "PAGINA 3 DI 7");
                setFieldsValue(form, fields, "pag4", "PAGINA 4 DI 7");
                setFieldsValue(form, fields, "VISTO1", VISTO1);
                setFieldsValue(form, fields, "ART1", ART1);

                fields.forEach((t, u) -> {
                    form.partialFormFlattening(t);
                });
                form.flattenFields();

//                BarcodeQRCode barcode = new BarcodeQRCode(ista1.getCodiceistanza() + " / DECRETOAPPROVATIVO / "
//                        + ista1.getSoggetto().getRap_cf() + "_" + datael.toString(PATTERNDATE3));
//                printbarcode(barcode, pdfDoc);
            }

            try (InputStream is = new ByteArrayInputStream(Base64.decodeBase64(template_parte2.getDescrizione())); PdfReader reader = new PdfReader(is); PdfWriter writer = new PdfWriter(pdfOut_part2); PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
                PdfAcroForm form = getAcroForm(pdfDoc, true);
                form.setGenerateAppearance(true);
                Map<String, PdfFormField> fields = form.getAllFormFields();

                setFieldsValue(form, fields, "NOMESERVIZIO", NOMESERVIZIO);
                setFieldsValue(form, fields, "RAGIONESOCIALE", ista1.getSoggetto().getRAGIONESOCIALE());
                setFieldsValue(form, fields, "PARTITAIVA", ista1.getSoggetto().getPARTITAIVA());
                setFieldsValue(form, fields, "CIR", ista1.getSoggetto().getCIR());

                setFieldsValue(form, fields, "DDS", "");
                setFieldsValue(form, fields, "DDDSDATA", "");
                setFieldsValue(form, fields, "pagA", "PAGINA 6 DI 7");
                setFieldsValue(form, fields, "pagB", "PAGINA 7 DI 7");

                setFieldsValue(form, fields, "DATA", ista1.getDatagestione().split(" ")[0]);
                setFieldsValue(form, fields, "funz1carica", FUNZCARICA);
                setFieldsValue(form, fields, "funz1nome", FUNZNOME);
                setFieldsValue(form, fields, "funz1fto", "_____________________________");
                setFieldsValue(form, fields, "dir1carica", DIRCARICA);
                setFieldsValue(form, fields, "dir1nome", DIRNOME);
                setFieldsValue(form, fields, "dir1fto", "_____________________________");

                fields.forEach((t, u) -> {
                    form.partialFormFlattening(t);
                });
                form.flattenFields();

            }

            File pdfOut_FINAL = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".F.pdf");

            try (PdfDocument pdf = new PdfDocument(new PdfWriter(pdfOut_FINAL))) {
                PdfMerger merger = new PdfMerger(pdf);
                PdfDocument parte1 = new PdfDocument(new PdfReader(pdfOut_part1));
                merger.merge(parte1, 1, parte1.getNumberOfPages());

                PdfDocument partecorsi = new PdfDocument(new PdfReader(pdfOut_part_corso));
                merger.merge(partecorsi, 1, partecorsi.getNumberOfPages());

                PdfDocument parte2 = new PdfDocument(new PdfReader(pdfOut_part2));
                merger.merge(parte2, 1, parte2.getNumberOfPages());

                parte1.close();
                parte2.close();
                partecorsi.close();
                merger.close();
                pdfOut_part1.delete();
                pdfOut_part2.delete();
                pdfOut_part_corso.delete();
            }

            try {
//                File fdef = convertPDFA(pdfOut_part1, "DECRETOAPPROVATIVO ISTANZA " + ista1.getCodiceistanza());
//                if (fdef != null) {
//                    pdfOut_part1.delete();
                System.out.println("tester.T.GENERADECRETOAPPROVATIVO() " + pdfOut_FINAL.getPath());
                return pdfOut_FINAL;
//                }
            } catch (Exception ex1) {
                LOGGER.severe(estraiEccezione(ex1));
            }

        } catch (Exception ex0) {
            LOGGER.severe(estraiEccezione(ex0));
        }
        return null;
    }

    public static File GENERADECRETOAPPROVATIVO(Istanza ista1) {

        try {

            EntityOp e = new EntityOp();
            Path pathtemp = e.getEm().find(Path.class, "path.temp");
            Path template = e.getEm().find(Path.class, "pdf.decrok.v1");

            DateTime datael = new DateTime();

            createDir(pathtemp.getDescrizione());

            File pdfOut_part1 = new File(pathtemp.getDescrizione() + "DECRETO_APP_" + "_" + ista1.getCodiceistanza() + "_" + datael.toString(PATTERNDATE3) + ".D1.pdf");

            try (InputStream is = new FileInputStream(template.getDescrizione()); PdfReader reader = new PdfReader(is); PdfWriter writer = new PdfWriter(pdfOut_part1); PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
                PdfAcroForm form = getAcroForm(pdfDoc, true);
                form.setGenerateAppearance(true);
                Map<String, PdfFormField> fields = form.getAllFormFields();

                setFieldsValue(form, fields, "ddg", "187 DEL " + datael.toString(PATTERNDATE4));
                setFieldsValue(form, fields, "dataprot", datael.toString(PATTERNDATE4));
                setFieldsValue(form, fields, "nome", ista1.getSoggetto().getRAGIONESOCIALE());
                setFieldsValue(form, fields, "sedelegalecomune", ista1.getSoggetto().getSedelegale().getComune());
                setFieldsValue(form, fields, "cir", ista1.getSoggetto().getCIR());

                fields.forEach((t, u) -> {
                    form.partialFormFlattening(t);
                });
                form.flattenFields();

                BarcodeQRCode barcode = new BarcodeQRCode(ista1.getCodiceistanza() + " / DECRETOAPPROVATIVO / "
                        + ista1.getSoggetto().getRap_cf() + "_" + datael.toString(PATTERNDATE3));
                printbarcode(barcode, pdfDoc);

            }

            try {
                File fdef = convertPDFA(pdfOut_part1, "DECRETOAPPROVATIVO ISTANZA " + ista1.getCodiceistanza());
                if (fdef != null) {
                    pdfOut_part1.delete();
                    System.out.println("tester.T.GENERADECRETOAPPROVATIVO() " + fdef.getPath());
                    return fdef;
                }
            } catch (Exception ex1) {
                LOGGER.severe(estraiEccezione(ex1));
            }

        } catch (Exception ex0) {
            LOGGER.severe(estraiEccezione(ex0));
        }
        return null;
    }

}
