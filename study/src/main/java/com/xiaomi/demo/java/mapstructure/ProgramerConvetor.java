package com.xiaomi.demo.java.mapstructure;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/11
 */
@Mapper
public interface ProgramerConvetor {

    ProgramerConvetor INSTANCE = Mappers.getMapper(ProgramerConvetor.class);

    TestMapStructure.ProgramerDto toProgramerDto(TestMapStructure.Programer programer);
}
