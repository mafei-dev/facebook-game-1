package com.game.demo;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
  @Author kalhara@bowsin
  @Created 1/10/2021 7:40 PM  
*/
public class ImageMain {
    public static void main(String[] args) throws IOException, TranscoderException {


        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get("E:\\KALHARA\\fb game\\test\\method-draw-image.svg"), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
            String my_name = contentBuilder.toString().replaceFirst("#name", "my name");
            InputStream targetStream = new ByteArrayInputStream(my_name.getBytes());
            TranscoderInput input_svg_image = new TranscoderInput(targetStream);
            //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
            OutputStream png_ostream = new FileOutputStream("chessboard.png");
            TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
            // Step-3: Create PNGTranscoder and define hints if required
            PNGTranscoder my_converter = new PNGTranscoder();
            // Step-4: Convert and Write output
            my_converter.transcode(input_svg_image, output_png_image);
            // Step 5- close / flush Output Stream
            png_ostream.flush();
            png_ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
