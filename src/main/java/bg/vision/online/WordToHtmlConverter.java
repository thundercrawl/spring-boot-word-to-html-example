package bg.vision.online;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.ToXMLContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
/**
 *
 */
@Component
class WordToHtmlConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordToHtmlConverter.class);

    public String convertwordtoSimpleText()
    {

   	 try {
	    	ClassLoader classLoader = ConvertedDocumentDTO.class.getClassLoader();
	    	File file = new File(classLoader.getResource("testDocs/test1.docx").getFile());
	   XWPFDocument docx = new XWPFDocument(
	   new FileInputStream(file));
	   org.apache.log4j.Logger.getLogger(getClass()).info("convert docx file,name=test1.docx");
 
	   System.out.println("convert docx file to html start!");
      InputStream input =new FileInputStream(file);
      Parser parser = new OOXMLParser();

      StringWriter sw = new StringWriter();
      SAXTransformerFactory factory = (SAXTransformerFactory)
              SAXTransformerFactory.newInstance();
      TransformerHandler handler = factory.newTransformerHandler();
      handler.getTransformer().setOutputProperty(OutputKeys.ENCODING, "utf-8");
      handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
      handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
      handler.setResult(new StreamResult(sw));

      Metadata metadata = new Metadata();
      metadata.add(Metadata.CONTENT_TYPE, "text/html;charset=utf-8");
      parser.parse(input, handler, metadata, new ParseContext());
      StringBuilder sb = new StringBuilder();
      String[] metadataNames = metadata.names();
      
      for(String name : metadataNames) {
         sb.append(name+":="+metadata.get(name)+"\r\n");
      }
      return sb.toString();
  } catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SAXException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (TikaException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (TransformerConfigurationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  finally
  {}
		return null;
   	
   
    	
    }
    public String  viewDocHeaders(String docname)
    {
    	
         WordExtractor extractor = null;

         try {
        	 StringBuffer sb = new StringBuffer();
        	 ClassLoader classLoader = ConvertedDocumentDTO.class.getClassLoader();
 	    	File file = new File(classLoader.getResource("testDocs/"+docname).getFile());
 	    	XWPFDocument xdoc=new XWPFDocument(OPCPackage.open(file));
 	    	XWPFStyles styles=xdoc.getStyles();         
 	        List<XWPFParagraph> xwpfparagraphs =xdoc.getParagraphs();
 	      //  System.out.println();
 	        for(int i=0;i<xwpfparagraphs.size();i++)
 	        {
 	            System.out.println("paragraph style id "+(i+1)+":"+xwpfparagraphs.get(i).getStyleID());                         
 	            if(xwpfparagraphs.get(i).getStyleID()!=null)
 	            {
 	                String styleid=xwpfparagraphs.get(i).getStyleID();
 	                XWPFStyle style=styles.getStyle(styleid);
 	                if(style!=null)
 	                {
 	                   
 	                    if(style.getName().startsWith("heading"))
 	                    {
 	                    	System.out.println("Style name:"+style.getName()+" content="+xwpfparagraphs.get(i).getText());
 	                    	sb.append("stylename="+style.getName()+",content="+xwpfparagraphs.get(i).getText());
 	                    }
 	                }

 	            }
 	        }
             
          
             return sb.toString();
         } catch (Exception e) {
        	 e.printStackTrace();
         }
		return null;
    }
    public String  convertWordDocumentIntoHtml(String docname)
    {
    	 try {
	    	ClassLoader classLoader = ConvertedDocumentDTO.class.getClassLoader();
	    	File file = new File(classLoader.getResource("testDocs/"+docname).getFile());
	   XWPFDocument docx = new XWPFDocument(
	   new FileInputStream(file));
	  // org.apache.log4j.Logger.getLogger(getClass()).info("convert docx file,name=test1.docx");
  
	   System.out.println("convert docx file to html start!");
       InputStream input =new FileInputStream(file);
       AutoDetectParser parser = new AutoDetectParser();

       StringWriter sw = new StringWriter();
       SAXTransformerFactory factory = (SAXTransformerFactory)
               SAXTransformerFactory.newInstance();
       ContentHandler handler = new ToXMLContentHandler();
       

       Metadata metadata = new Metadata();
       metadata.add(Metadata.CONTENT_TYPE, "text/html;charset=utf-8");
       parser.parse(input, handler, metadata);
       
       return handler.toString();
   } catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SAXException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (TikaException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}   finally
   {}
		return null;
    	
    }
    /**
     * Converts a .docx document into HTML markup. This code
     * is based on <a href="http://stackoverflow.com/a/9053258/313554">this StackOverflow</a> answer.
     *
     * @param wordDocument  The converted .docx document.
     * @return
     */
    public ConvertedDocumentDTO convertWordDocumentIntoHtml(MultipartFile wordDocument) {
        LOGGER.info("Converting word document: {} into HTML", wordDocument.getOriginalFilename());
        try {
            InputStream input = wordDocument.getInputStream();
            Parser parser = new OOXMLParser();

            StringWriter sw = new StringWriter();
            SAXTransformerFactory factory = (SAXTransformerFactory)
                    SAXTransformerFactory.newInstance();
            TransformerHandler handler = factory.newTransformerHandler();
            handler.getTransformer().setOutputProperty(OutputKeys.ENCODING, "utf-8");
            handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
            handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            handler.setResult(new StreamResult(sw));

            Metadata metadata = new Metadata();
            
            parser.parse(input, handler, metadata, new ParseContext());
            metadata.set(Metadata.CONTENT_TYPE, "text/html;charset=utf-8");
            return new ConvertedDocumentDTO(wordDocument.getOriginalFilename(), sw.toString());
        }
        catch (IOException | SAXException | TransformerException | TikaException ex) {
            LOGGER.error("Conversion failed because an exception was thrown", ex);
            throw new DocumentConversionException(ex.getMessage(), ex);
        }
    }
}
