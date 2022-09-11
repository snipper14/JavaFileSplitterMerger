/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splitbatchpdf;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 *
 * @author KAZUNGU
 */
public class SplitBatchPdf {

    ShredPdf shredPDF;
    String forSignSign = "C:\\Incotex\\Docs\\For Sign\\";
    String tempPath = "C:\\Incotex\\Docs\\For Batch\\temp\\";
    private String initialFile = "C:\\Incotex\\Docs\\For Batch\\sign.pdf";

    public SplitBatchPdf() {
        shredPDF = new ShredPdf();
    }

    public void processor() throws IOException {
        // Loading PDF
        createTempFolder();
        File pdffile
                = new File(initialFile);

        if (pdffile.exists()) {
            PDDocument document = Loader.loadPDF(pdffile);

            // Splitter Class
            Splitter splitting = new Splitter();

            // Splitting the pages into multiple PDFs
            List<PDDocument> Page = splitting.split(document);

            // Using a iterator to Traverse all pages
            Iterator<PDDocument> iteration
                    = Page.listIterator();

            // Saving each page as an individual document
            int j = 1;
            while (iteration.hasNext()) {
                PDDocument pd = iteration.next();
                pd.save(tempPath + "\\batch_"
                        + j++ + ".pdf");

            }

            document.close();
            moveFiles();
            pdffile.delete();
        }
    }

    public void moveFiles() {
        File folder = new File(tempPath);
        File[] listOfFiles = folder.listFiles();

        // if (folder.list().length > 1) {
        for (int e = 0; e < listOfFiles.length; e++) {
            File f = new File(folder + "/" + listOfFiles[e].getName());
            if (listOfFiles[e].getName().endsWith(".pdf")) {
                try {

                    DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssss");
                 
                //   String docName= shredPDF.getDocumentNo(f+"");
                    String dateName = df.format(new Date());
                    if (f.renameTo(new File(forSignSign    + listOfFiles[e].getName()))) {//f.renameTo(new File(forSignSign    + docName+".pdf"))
                        System.out.println("File is moved successful!");
                    } 
//                    else {
//                        if (f.renameTo(new File(forSignSign    + dateName+".pdf"))) {
//                              System.out.println("File  moved!");
//                        }else{
//                             f.delete();
//                              System.out.println("File is failed to move!");
//                        }
//                      
//                    }

                } catch (Exception p) {
                    p.printStackTrace();
                }

            }
        }

    }

   

    private void createTempFolder() {
        File folder = new File(tempPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }
    
      public void combine()
    {
        try
        {
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        String folder ="pdf";
        File _folder = new File(forSignSign);
        File[] filesInFolder;
        filesInFolder = _folder.listFiles();
        for (File string : filesInFolder)
        {
            System.out.println(string+">>>");
            mergePdf.addSource(string);    
        }
    mergePdf.setDestinationFileName(forSignSign+"\\Combined.pdf");
    mergePdf.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
   
        }
        catch(Exception e)
        {

        }  
    }}
