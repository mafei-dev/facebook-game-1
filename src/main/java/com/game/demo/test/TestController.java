package com.game.demo.test;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

/*
  @Author kalhara@bowsin
  @Created 1/9/2021 6:50 PM  
*/
//@RestController
//@CrossOrigin("*")
public class TestController {
/*

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
    public ResponseEntity getImageAsByteArray() throws IOException {
        System.out.println("TestController.getImageAsByteArray");
        Resource resource = new ClassPathResource("static/_1/templates/image.jpg");
        byte[] fileContent = FileUtils.readFileToByteArray(resource.getFile());
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity(Collections.singletonMap("data","data:image/jpg;base64," + encodedString), HttpStatus.OK);
    }
*/


}
