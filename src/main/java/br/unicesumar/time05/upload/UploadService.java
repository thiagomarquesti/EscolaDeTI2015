package br.unicesumar.time05.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.transaction.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class UploadService {

    public void upload(String baseedString, Long nomeArquivo) throws IOException {
        String stringCerta = baseedString.substring(baseedString.indexOf(",") + 1);
        byte[] decode = Base64.decodeBase64(stringCerta);
        File f = new File("imagens/" + nomeArquivo + ".jpg");
        
        FileOutputStream out = new FileOutputStream(f);
        out.write(decode);
        out.close();
    }

}
