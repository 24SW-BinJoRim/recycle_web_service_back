/*
package com.sparta.board.controller;

import com.sparta.board.dto.TrashDto;
import com.sparta.board.entity.Trash;
import com.sparta.board.repository.TrashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class MapController {
    private final TrashRepository trashRepository;

    @ResponseBody
    @GetMapping("/trash/{page}/{perPage}")
    public TrashResponseDto<List<TrashDto>> GetRecommmendSuportConditionsRes(
            @PathVariable("page")int page, @PathVariable("perPage")int perPage
            @RequestParam int lat, @RequestParam int lng, @RequestParam int title, @RequestParam int detail, @RequestParam int type) {

        try{
            List<GetRecommendSupportConditionsRes> getRecommendSupportConditionsRes = supportConditionsProvider.getRecommendSupportConditions(page, perPage, age, income_range, gender, area, personalArray, householdslArray);
            return new TrashResponse<>(getRecommendSupportConditionsRes);
        }catch (BaseException exception){
            logger.error("Error", exception);
            return new TrashResponse<>((exception.getStatus()));
        }
    }
    }


}

 */
