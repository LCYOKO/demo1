package com.xiaomi.demo.mapstructure;

import lombok.Data;
import org.junit.Test;

import java.util.Date;

/**
 * @Author: liuchiyun
 * @Date: 2023/12/11
 */
public class Main {

    @Test
    public void test1() {
        Programer programer = new Programer();
        programer.setName("lisi");
        programer.setBeDate(new Date());

        ProgramerDto programerDto = ProgramerConvetor.INSTANCE.toProgramerDto(programer);
        System.out.println(programer);
        System.out.println(programerDto);
    }

    @Data
    static class Programer {
        private String name;
        private String lang;
        private Double height;
        private Date beDate;
        private Address address;
        private String girlName;
        private String girlDes;
    }

    @Data
    static class ProgramerDto {
        private String name;
        private String proLang;
        private String height;
        private String beDate;
        private AddressDto address;

    }


    @Data
    static class Address {
        private String address;
    }


    @Data
    static class AddressDto {
        private String address;
    }
}
