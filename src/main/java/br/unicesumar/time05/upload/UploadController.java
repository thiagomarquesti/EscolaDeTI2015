package br.unicesumar.time05.upload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.stream.FileImageOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @RequestMapping(method = RequestMethod.POST)
    public void upload(@RequestBody String baseedString) throws IOException {
//        System.out.println(baseedString);
//        byte[] data = Base64.decodeBase64(baseedString);
//        try (OutputStream stream = new FileOutputStream("C:/Users/Renato/Pictures/abc.bmp")) {
//            stream.write(data);
//        }
        String stringCerta = baseedString.substring(baseedString.indexOf(",")+1);
        byte[] decode = Base64.decodeBase64(stringCerta);
        int i = 5;
        File f = new File("C:/Users/Renato/Pictures/" + i + ".jpg");
        FileOutputStream out = new FileOutputStream(f);
        for (int x = 0; x < decode.length; x++) {
            out.write(decode[x]);
        }
        out.close();
        System.out.println("passo");
    }

}
