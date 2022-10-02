package com.capgemini.pdp.service;

import com.capgemini.pdp.dto.CompetencyDto;
import com.capgemini.pdp.dto.CompetencyForUserDto;

import java.util.List;

public interface CompetencyService {
     CompetencyDto addCompetency(CompetencyDto competencyDto);

    CompetencyDto getCompetencyById(Long id);

    CompetencyDto updateCompetency(CompetencyDto competencyDto);

    List<CompetencyDto> getAllCompetencies();

    List<CompetencyDto> getAllByPositionName(String positionName);

    void archiveAllCompetenciesByPositionId(Long id);

    void archiveCompetency(Long id);

    List<CompetencyForUserDto> getCompetenciesAvailableForUser(Long userId);
}
