package com.capgemini.pdp.controller;

import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.CareerPathDto;
import com.capgemini.pdp.dto.CompetencyDto;
import com.capgemini.pdp.dto.PositionDto;
import com.capgemini.pdp.dto.TypeDto;
import com.capgemini.pdp.service.CareerPathService;
import com.capgemini.pdp.service.CompetencyService;
import com.capgemini.pdp.service.PositionService;
import com.capgemini.pdp.service.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/data")
public class DataController {
    private final CareerPathService careerPathService;
    private final CompetencyService competencyService;
    private final PositionService positionService;
    private final TypeService typeService;

    public DataController(CareerPathService careerPathService, CompetencyService competencyService, PositionService positionService, TypeService typeService) {
        this.careerPathService = careerPathService;
        this.competencyService = competencyService;
        this.positionService = positionService;
        this.typeService = typeService;
    }

    @GetMapping("/careerPath")
    public ResponseEntity<List<CareerPathDto>> getAllCareerPaths() {
        List<CareerPathDto> careerPaths = careerPathService.getAllCareerPaths();
        return ResponseEntity.status(HttpStatus.OK).body(careerPaths);
    }

    @GetMapping("/careerPath/{careerPathId}")
    public ResponseEntity<CareerPathDto> getCareerPathById(@PathVariable Long careerPathId) {
        CareerPathDto careerPath = careerPathService.getCareerPathById(careerPathId);
        return ResponseEntity.status(HttpStatus.OK).body(careerPath);
    }

    @GetMapping("/position")
    public ResponseEntity<List<PositionDto>> getAllPositions() {
        List<PositionDto> positions = positionService.getAllPositions();
        return ResponseEntity.status(HttpStatus.OK).body(positions);
    }

    @GetMapping("/position/{careerPath}")
    public ResponseEntity<List<PositionDto>> getAllPositionsFilteredByCareerPath(@PathVariable String careerPath) {
        List<PositionDto> positions = positionService.getAllPositionsFilteredByCareerPath(careerPath);
        return ResponseEntity.status(HttpStatus.OK).body(positions);
    }

    @GetMapping("/position/{positionId}")
    public ResponseEntity<PositionDto> getPositionById(@PathVariable Long positionId) {
        PositionDto position = positionService.getPositionById(positionId);
        return ResponseEntity.status(HttpStatus.OK).body(position);
    }

    @GetMapping("/competency")
    public ResponseEntity<List<CompetencyDto>> getCompetencies() {
        List<CompetencyDto> competencies = competencyService.getAllCompetencies();
        return ResponseEntity.status(HttpStatus.OK).body(competencies);
    }

    @GetMapping("/competency/{competencyId}")
    public ResponseEntity<CompetencyDto> getCompetencyById(@PathVariable Long competencyId) {
        CompetencyDto competency = competencyService.getCompetencyById(competencyId);
        return ResponseEntity.status(HttpStatus.OK).body(competency);
    }

    @GetMapping("/role")
    public ResponseEntity<List<String>> getAllRoles() {
        List<String> roles = Stream.of(UserRole.values())
                .map(UserRole::name)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @GetMapping("/type")
    public ResponseEntity<List<TypeDto>> getAllTypes() {
        List<TypeDto> typeDtos = typeService.getAllTypes();
        return ResponseEntity.status(HttpStatus.OK).body(typeDtos);
    }
}
