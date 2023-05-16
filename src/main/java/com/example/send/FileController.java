package com.example.send;

import java.io.*;

/**
 * @author kw
 * @program send
 * @description
 * @create 2023 - 05 - 16 20:51
 **/
public class FileController {
    @GetMapping("/downLoad")
    public void downLoad(HttpServletResponse response) {
        //
        String path = "D:\\aa.csv";

        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        OutputStream outputStream = null;
        try {
            InputStream fileInputStream = new FileInputStream(file);
            int len = 0;
            byte[] bytes = new byte[1024];

            outputStream = response.getOutputStream();
            while ((len = fileInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
