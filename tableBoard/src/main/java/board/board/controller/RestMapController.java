package board.board.controller;

import board.board.dto.GuDto;
import board.board.dto.MapDto;
import board.board.dto.SiGunGuDto;
import board.board.service.MapService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestMapController {

    @Autowired
    private MapService mapService;

    @Autowired
    MappingJackson2JsonView jsonView;

    @GetMapping(value = "/board/mapApi")
    public ModelAndView openLocationList(HttpServletRequest request) throws Exception {
        String type = request.getParameter("type");
        ModelAndView mv = new ModelAndView(jsonView);
        List<MapDto> list = mapService.selectPosition(type);
        mv.addObject("list", list);
        System.out.println(list);
        return mv;
    }

    @RequestMapping(value = "/board/mapWrite", method = RequestMethod.GET)
    public ModelAndView openLocationWrite() throws Exception {
        ModelAndView mv = new ModelAndView("/board/mapWrite");
        return mv;
    }


//    @RequestMapping(value = "/board/writeMap", method = RequestMethod.POST)
//    public ModelAndView insertLocation(MapDto map) throws Exception {
//        mapService.insertLocation(map);
//        ModelAndView mv = new ModelAndView("redirect:/board/mapApi");
//
//        return mv;
//    }
//
//    // 도로명 주소 좌표 전환 네이버 api
//    @GetMapping(value = "/board/searchRoadAddr")
//    public ModelAndView searchRoadAddr(String address) {
//        ModelAndView mv = new ModelAndView(jsonView);
//        StringBuilder html = new StringBuilder();
//        String url =  "https://openapi.naver.com/v3/map/geocode?query=" + address;
//        String clientId = "7o83deuk7v";
//        String clientSecret = "NBCvUnhlem2rO5dXWT937KfCo27atWvgFUUAvGbe";
//
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpGet request = new HttpGet(url);
//        request.addHeader("X-Naver-Client-Id", clientId);
//        request.addHeader("X-Naver_Client-Secret", clientSecret);
//        try {
//            HttpResponse response = client.execute(request);
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//
//            String current ="";
//            while ((current = reader.readLine())!=null) {
//                html.append(current);
//            }
//            reader.close();
//        }catch (ClientProtocolException e) {
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        mv.addObject("result", html.toString());
//        System.out.println(mv);
//    return mv;
//    }
}
