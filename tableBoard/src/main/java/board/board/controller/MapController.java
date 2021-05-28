package board.board.controller;

import board.board.dto.MapDto;
import board.board.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Controller
public class MapController {

    @Autowired
    private MapService mapService;

    @RequestMapping(value = "/board/mapApi", method = RequestMethod.GET)
    public ModelAndView openLocationList() throws Exception {
        ModelAndView mv = new ModelAndView("/board/mapApi");
//        List<MapDto> list =  mapService.selectPostion();
//        mv.addObject("list", list);
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
}
