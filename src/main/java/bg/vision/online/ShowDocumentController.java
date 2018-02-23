package bg.vision.online;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShowDocumentController {

	@Autowired WordToHtmlConverter wordconverter;
	 @RequestMapping(value = "/viewDoc", method = RequestMethod.GET)
	 @ResponseBody
	  public String convertWordDocumentIntoHtmlDocument(@RequestParam(value = "doc", required = true)String docname) {
		 
		return wordconverter.convertWordDocumentIntoHtml(docname);
		 
	 }
	 @RequestMapping(value = "/viewDocMeta", method = RequestMethod.GET)
	 @ResponseBody
	  public String viewDocMeta() {
		 
		return wordconverter.convertwordtoSimpleText();
		 
	 }
	 
	 @RequestMapping(value = "/viewHtml", method = RequestMethod.GET)
	 @ResponseBody
	 public String simpleHtml()
	 {
		 return "<H1>check html</H1>";
		 
	 }
	 
	 @RequestMapping(value = "/viewDocHeaders", method = RequestMethod.GET)
	 @ResponseBody
	  public String viewDocHeaders(@RequestParam(value = "doc", required = true)String docname) {
		 
		return wordconverter.viewDocHeaders(docname);
		 
	 }
	 
	 @RequestMapping(value = "/viewDocCount", method = RequestMethod.GET)
	 @ResponseBody
	 public String showcount(HttpServletRequest request)
	 {
		 return "<H1>check html</H1>"+request.getAttribute("doccount");
		 
	 }
	 
	 
}
