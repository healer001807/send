package com.example.send.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;

/**
 * @author kw
 * @program send
 * @description
 * @create 2023 - 05 - 16 20:51
 **/
public class FileController {
    @GetMapping("/downLoad")
    public void downLoad(HttpServletResponse response) throws IOException {
        //
        String path = "/data/aa.csv"; //todo  将地址换成项目地址

        String path1 = this.getClass().getResource(path).getPath(); // todo 这行如果不能实现，则使用地址截取

        File file = new File(path1);
        if (!file.exists()) {
            file.mkdir();
        }
        OutputStream ops = null;
        InputStream is = null;
        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName()); // todo 这一句一定要加

            is = new FileInputStream(file);
            int len = 0;
            byte[] bytes = new byte[1024];

            ops = response.getOutputStream();
            while ((len = is.read(bytes)) != -1) {
                ops.write(bytes, 0, len);
            }
            ops.flush(); // 刷新缓冲区
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭流
            if (is != null) {
                is.close();
            }
            if (ops != null) {
                ops.close();
            }
        }
    }

    @GetMapping("/downLoadByCSVPrinter")
    public void downLoadByCSVPrinter() throws IOException {
        //
        String path = "/data/aa.csv"; //todo  将地址换成项目地址

        FileWriter fileWriter = new FileWriter(path);

        CSVPrinter csvPrinter = CSVFormat.DEFAULT.print(fileWriter);

        String str = "手机号";

        CSVFormat csvFormat = CSVFormat.newFormat(',').withQuote(null).withRecordSeparator("\r\n")
                .withIgnoreEmptyLines(true).withHeader(str);

        String filePath = this.getClass().getResource(path).getPath();
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);

        OutputStreamWriter ws = new OutputStreamWriter(fileOutputStream,"utf-8");

        byte[] bytes = {(byte) 0xef,(byte) 0xbb,(byte) 0xbf};

        fileOutputStream.write(bytes);

        csvPrinter = new CSVPrinter(ws,csvFormat);

        csvPrinter.flush();

        fileOutputStream.close();
        ws.close();
        csvPrinter.close();
    }
}
