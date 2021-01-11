package com.game._1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game._1.model.Database;
import com.game._1.model.GenderFile;
import com.game._1.model.UserData;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/*
  @Author kalhara@bowsin
  @Created 1/10/2021 11:48 PM  
*/
@RestController
/*@CrossOrigin(origins = {"https://www.facebook.com/",
        "https://localhost:8081/",
        "http://localhost:8081/",
        "http://apps.noonmoons.com/",
        "https://apps.noonmoons.com/",
})*/
@CrossOrigin("*")
public class ImageController {

    public static Integer COUNT = 0;

    @RequestMapping(value = "/create-image", method = RequestMethod.POST)
    public ResponseEntity createImage(@RequestBody UserData userData) throws IOException, TranscoderException {
        System.out.println("userData = " + userData);
        System.out.println("count = " + COUNT);
        ObjectMapper objectMapper = new ObjectMapper();
        Resource database = new ClassPathResource("static/_1/database.json");
        Database data = objectMapper.readValue(database.getInputStream(), Database.class);
        GenderFile genderFile;
        Random rn = new Random();

        if (userData.getGender().toLowerCase().equals("male")) {
            if (COUNT % 5 == 0) {
                //1st
                genderFile = data.getMale().get(0);
            } else if (COUNT % 8 == 0) {
                //haven't//last one should be haven't
                genderFile = data.getMale().get(data.getMale().size() - 1);
            } else {
                int randomValue = rn.nextInt((data.getMale().size() - 1) - 0 + 1) + 0;
                genderFile = data.getMale().get(randomValue);
            }
        } else {
            if (COUNT % 5 == 0) {
                //1st
                genderFile = data.getFemale().get(0);
            } else if (COUNT % 10 == 0) {
                //haven't//last one should be haven't
                genderFile = data.getFemale().get(data.getFemale().size() - 1);
            } else {
                int randomValue = rn.nextInt((data.getFemale().size() - 1) - 0 + 1) + 0;
                genderFile = data.getFemale().get(randomValue);
            }
        }
        return new ResponseEntity(getBase64File(genderFile.getFileName(), userData, genderFile.getLevel()), HttpStatus.OK);
    }


    private Map getBase64File(String fileName, UserData userData, String level) throws IOException, TranscoderException {
        ResourceLoader resourceLoader = new FileSystemResourceLoader();
        String uuid = UUID.randomUUID().toString();
        String temp_file = "./tmp/" + uuid + ".jpg";
//        Resource resource1 = resourceLoader.getResource("classpath:static/_1/templates/" + fileName);
//        InputStream inputStream = classLoader.getResourceAsStream("static/_1/templates/" + fileName);

     /*   ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("fileTest.txt").getFile());
        String data = FileUtils.readFileToString(file, "UTF-8");
*/
        URL url = this.getClass().getClassLoader().getResource("static/_1/templates/" + fileName);
        String fileContentString = IOUtils.toString(url.openStream());

        /*StringBuilder contentBuilder = new StringBuilder();
            Stream<String> stream = Files.lines(resources.nextElement().getPath(),
                StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s));*/
        String my_name = fileContentString
                .replaceAll("#name", userData.getUsername())
                .replaceAll("#level", level);
        InputStream targetStream = new ByteArrayInputStream(my_name.getBytes());
        TranscoderInput input_svg_image = new TranscoderInput(targetStream);
        //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
        OutputStream png_ostream = new FileOutputStream(temp_file);
        TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);

        // create a JPEG transcoder
        JPEGTranscoder t = new JPEGTranscoder();
        // set the transcoding hints
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));

      /*  // Step-3: Create PNGTranscoder and define hints if required
        PNGTranscoder my_converter = new PNGTranscoder();*/
        // Step-4: Convert and Write output
        t.transcode(input_svg_image, output_png_image);
        // Step 5- close / flush Output Stream
        png_ostream.flush();
        png_ostream.close();
        byte[] fileContent = FileUtils.readFileToByteArray(new File(temp_file));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        deleteImage(temp_file);
        return Collections.singletonMap("data", "data:image/jpg;base64," + encodedString);
    }

    @Async
    public void deleteImage(String file) {
        COUNT++;
        new File(file).delete();
    }

}
