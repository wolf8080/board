package board.board.mapper;

import board.board.dto.GuDto;
import board.board.dto.MapDto;
import board.board.dto.SiGunGuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapMapper {

    /**
     * 시공건 목록 조회
     * @param
     * @return
     * @throws Exception
     */
    List<MapDto> selectPosition(String type) throws Exception;

    /**
     * 시공건 입력
     * @param
     * @return
     * @throws Exception
     */

    void insertLocation(MapDto map) throws Exception;

}
