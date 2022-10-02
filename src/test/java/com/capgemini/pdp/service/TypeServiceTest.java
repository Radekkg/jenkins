package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.TypeEntity;
import com.capgemini.pdp.dto.TypeDto;
import com.capgemini.pdp.repository.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class TypeServiceTest {

    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TypeService typeService;

    @Test
    void shouldGetAllTypes() {
        //given

        //when
        List<TypeDto> typeDtolist = typeService.getAllTypes();
        //them
        assertEquals(typeRepository.findAll().size(), typeDtolist.size());
    }

    private TypeEntity createExampleType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setDescription("test");
        return typeEntity;
    }
}