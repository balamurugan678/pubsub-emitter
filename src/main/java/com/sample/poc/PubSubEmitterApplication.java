package com.sample.poc;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sample.poc.model.Family;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Bala on 30/06/2017.
 */
@SpringBootApplication
public class PubSubEmitterApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(PubSubEmitterApplication.class, args);

        /*JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        System.out.println(xmlMapper.readValue(IOUtils.toByteArray(PubSubEmitterApplication.class.getClassLoader().getResourceAsStream("family.xml")), Family.class));*/
    }
}
