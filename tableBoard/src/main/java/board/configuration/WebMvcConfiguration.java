package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.LoggerInceptor;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

//LoggerInterceptor를 스프링 빈으로 등록
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	@Bean
	MappingJackson2JsonView jsonView(){
		return new MappingJackson2JsonView();
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInceptor()); //인터셉터 등록
		
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() throws Exception{
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8"); // 파일의 인코딩
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); // 파일의 크기
		return commonsMultipartResolver;
	}
}
