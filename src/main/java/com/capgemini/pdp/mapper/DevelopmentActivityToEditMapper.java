package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.DevelopmentActivityEntity;
import com.capgemini.pdp.dto.DevelopmentActivityToEditDto;

public class DevelopmentActivityToEditMapper {
    private DevelopmentActivityToEditMapper() {
    }

    public static DevelopmentActivityToEditDto mapToDto(DevelopmentActivityEntity developmentActivityEntity) {
        DevelopmentActivityToEditDto developmentActivityToEditDto = new DevelopmentActivityToEditDto();
        developmentActivityToEditDto.setDevelopmentAction(developmentActivityEntity.getDevelopmentAction());
        developmentActivityToEditDto.setComment(developmentActivityEntity.getComment());
        return developmentActivityToEditDto;
    }
}
