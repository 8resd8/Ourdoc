package com.ssafy.ourdoc.ocr.service;

import com.ssafy.ourdoc.ocr.dto.HandOCRResponse;
import com.ssafy.ourdoc.ocr.exception.OCRFailException;
import com.ssafy.ourdoc.ocr.exception.OCRNoImageException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service
public class OCRService {

    @Value("${ocr.api-url}")
    private String apiURL;
    @Value("${ocr.secret-key}")
    private String secretKey;

    public HandOCRResponse handOCRConvert(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new OCRNoImageException("첨부된 이미지가 없습니다.");
        }

        try {
            // HTTP 연결
            HttpURLConnection con = getHttpURLConnection(multipartFile);

            // 네이버 클로바 응답코드 확인
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            // JSON 파싱 후 inferText 추출 images -> fields -> inferText
            JSONObject respJson = new JSONObject(response.toString());
            JSONArray imagesArr = respJson.getJSONArray("images");
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < imagesArr.length(); i++) {
                JSONObject imageObj = imagesArr.getJSONObject(i);

                // fields 배열이 있는 경우만 처리
                if (!imageObj.isNull("fields")) {
                    JSONArray fieldsArr = imageObj.getJSONArray("fields");

                    for (int j = 0; j < fieldsArr.length(); j++) {
                        JSONObject fieldObj = fieldsArr.getJSONObject(j);
                        if (!fieldObj.isNull("inferText")) {
                            String inferText = fieldObj.getString("inferText");
                            sb.append(inferText).append(" ");
                        }
                    }
                }
            }

            return new HandOCRResponse(sb.toString());
        } catch (Exception e) {
            throw new OCRFailException(e.getMessage());
        }
    }

    private HttpURLConnection getHttpURLConnection(MultipartFile multipartFile) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setReadTimeout(30000);
        con.setRequestMethod("POST");
        String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("X-OCR-SECRET", secretKey);

        // 2. JSON 파라미터 작성
        JSONObject json = new JSONObject();
        json.put("version", "V2");
        json.put("requestId", UUID.randomUUID().toString());
        json.put("timestamp", System.currentTimeMillis());
        JSONObject image = new JSONObject();
        image.put("format", getExtension(multipartFile));
        image.put("name", "demo");
        JSONArray images = new JSONArray();
        images.put(image);
        json.put("images", images);
        String postParams = json.toString();

        con.connect();

        // 3. 파일 변환 (MultipartFile -> File)
        File file = new File(System.getProperty("java.io.tmpdir"), multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        // 4. multipart/form-data 전송
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            writeMultiPart(wr, postParams, file, boundary);
        }
        return con;
    }

    private String getExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
    }

    // 멀티파트 작성 메서드
    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");
        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString.append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"").append(file.getName()).append("\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }
            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }
}
