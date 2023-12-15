package com.xiaomi.demo.mapstructure;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/11
 */
@Mapper
public interface ProgramerConvetor {

    ProgramerConvetor INSTANCE = Mappers.getMapper(ProgramerConvetor.class);

    Test.ProgramerDto toProgramerDto(Test.Programer programer);
}