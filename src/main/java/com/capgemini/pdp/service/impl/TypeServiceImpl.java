package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.domain.TypeEntity;
import com.capgemini.pdp.dto.TypeDto;
import com.capgemini.pdp.mapper.TypeMapper;
import com.capgemini.pdp.repository.TypeRepository;
import com.capgemini.pdp.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<TypeDto> getAllTypes() {
        List<TypeEntity> typeEntities = typeRepository.findAll();
        return TypeMapper.mapToDtoList(typeEntities);
    }
}
