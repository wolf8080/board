package board.board.controller;

import board.board.dto.GuDto;
import board.board.dto.MapDto;
import board.board.dto.SiGunGuDto;
import board.board.service.MapService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.influx.InfluxDbOkHttpClientBuilderProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
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
        return mv;
    }

    @RequestMapping(value = "/board/mapWrite", method = RequestMethod.GET)
    public ModelAndView openLocationWrite() throws Exception {
        ModelAndView mv = new ModelAndView("/board/mapWrite");
        return mv;
    }


    @RequestMapping(value = "/board/writeMap", method = RequestMethod.POST)
    public ModelAndView insertLocation(MapDto map) throws Exception {
        mapService.insertLocation(map);
        ModelAndView mv = new ModelAndView("redirect:/board/mapApi");

        return mv;
    }

    // 도로명 주소 좌표 전환 네이버 api
    @GetMapping(value = "/board/searchRoadAddr")
    public ModelAndView searchRoadAddr(String address) {
        ModelAndView mv = new ModelAndView(jsonView);
        String url =  "https://openapi.naver.com/v1/map/geocode?query=" + address;
        String clientId = "RxRJEixhGDa4hKjHEBJp";
        String clientSecret = "fXeI38i3HZSWmOTQQh0Q7Y7dWx67SIsC5PGlPmH5";



    return mv;


    }
}
