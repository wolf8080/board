package board.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

//예외처리
@ControllerAdvice //예외처리 클래스 임을 알려줌
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class) // 해당 메서드에서 처리할 예외 지정
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		ModelAndView mv = new ModelAndView("/error/error_default"); // 예외발생 시 보여줄 화면 지정
		mv.addObject("exception", exception);
		
		log.error("exception", exception); // 에러 로그 출력
		
		return mv;
	}
	
}
