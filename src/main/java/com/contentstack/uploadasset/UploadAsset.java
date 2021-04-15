package com.contentstack.uploadasset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringBootApplication
@RestController
@Controller
public class UploadAsset {

    public static void main(String[] args) {
        SpringApplication.run(UploadAsset.class, args);
    }

    private static FileSystemResource getResource(final String location) {
        return new FileSystemResource(location);
    }

    @GetMapping(value = "/upload")
    public HttpEntity testAssetUpload() {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("api_key", "put_your_api_key");
            headers.set("authtoken", "authtoken");
            headers.set("Content-Type", "multipart/form-data");

            //Step 1
            MultiValueMap<String, Object> payload = new LinkedMultiValueMap<>();
            payload.add("asset[upload]", getResource("put your file location path"));//loadResourceFile());

            // Step 2
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

            // Step 3
            DefaultUriBuilderFactory defaultUriTemplateHandler = new DefaultUriBuilderFactory("https://hostname/v3/");


            // Step 4
            RestTemplate restTemplate1 = new RestTemplateBuilder()
                    .uriTemplateHandler(defaultUriTemplateHandler)
                    .build();

            //Step 5
            ResponseEntity<Object> response = restTemplate1.postForEntity("assets?include_dimension=true", requestEntity, Object.class);

            System.out.println(response);
            return ResponseEntity.ok(response);
        } catch (RestClientResponseException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @Configuration
    @PropertySource("classpath:application.properties")
    public class KeyConfigProperties {
        //...
        @Value("${config.api_key}")
        private String api_key;

        @Value("${config.authtoken}")
        private String authtoken;

        @Value("${config.host}")
        private String host;
    }

}
