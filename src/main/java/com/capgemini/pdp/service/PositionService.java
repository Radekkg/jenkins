package com.capgemini.pdp.service;

import com.capgemini.pdp.dto.PositionDto;

import java.util.List;

public interface PositionService {

    PositionDto getPositionById(Long id);

    PositionDto getPositionByName(String positionName);

    PositionDto updatePosition(PositionDto positionDto);

    List<PositionDto> getAllPositions();

    void archiveAllPositionsByCareerPathId(Long id);

    void archivePositionById(Long id);

    List<PositionDto> getAllPositionsFilteredByCareerPath(String careerPath);

    PositionDto addNewPositionToCareerPath(PositionDto positionDto);

}
