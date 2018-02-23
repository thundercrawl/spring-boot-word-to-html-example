package bg.vision.online;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class DocumentInterceptor extends HandlerInterceptorAdapter  {

	private static final Logger logger = Logger.getLogger(DocumentInterceptor.class);

	//before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler)
	    throws Exception {

		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		if(request.getRequestURL().toString().contains("viewDocCount"))
		{
			logger.debug("hanlder is:="+handler.getClass());
			if( request.getAttribute("doccount")!=null)
			{
				int count = (int)request.getAttribute("doccount")+1;
				request.setAttribute("doccount", count);
			}
			else
			request.setAttribute("doccount", 1);
		}
		else
			logger.debug("failed to get the viewDocCount from url="+request.getRequestURL());
		logger.info("request is :="+request.getRequestURI());
		return true;
	}

	//after the handler is executed
	public void postHandle(
		HttpServletRequest request, HttpServletResponse response,
		Object handler, ModelAndView modelAndView)
		throws Exception {

		long startTime = (Long)request.getAttribute("startTime");

		long endTime = System.currentTimeMillis();

		long executeTime = endTime - startTime;
		
		
		//modified the exisitng modelAndView
		if(modelAndView!=null)
		modelAndView.addObject("executeTime",executeTime);
		else
			logger.info("model viewr is null, the request have no model setting");

		//log it
		if(logger.isDebugEnabled()){
		   logger.debug("[" + handler + "] executeTime : " + executeTime + "ms");
		}
	}
}
