package com.capgemini.pdp.service;

import com.capgemini.pdp.dto.CareerPathDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CareerPathService {

    CareerPathDto addCareerPath(CareerPathDto careerPathDto);

    CareerPathDto getCareerPathById(Long id);

    CareerPathDto getCareerPathByName(String careerPathName);

    CareerPathDto updateCareerPath(CareerPathDto careerPathDto);

    void archiveCareerPath(Long id);

    List<CareerPathDto> getAllCareerPaths();
}
