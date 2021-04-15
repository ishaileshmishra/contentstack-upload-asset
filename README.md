# contentstack-upload-asset

This repository helps you to create Demo project for uploading assets to contentstack


#### Put Keys in the main/resources/application.properties

- Put your api_key against the api_key
- Put your authtoken against the authtoken
- Put your host against the host

 #### provide the image file path location of file you have to upload.

    @GetMapping(value = "/upload")
    public HttpEntity assetUpload() {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("api_key", api_key);
            headers.set("authtoken", authtoken);
            headers.set("Content-Type", "multipart/form-data");

            //Step 1
            MultiValueMap<String, Object> payload = new LinkedMultiValueMap<>();
            payload.add("asset[upload]", getResource("path/to/file"));

            // Step 2
            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(payload, headers);
            DefaultUriBuilderFactory defaultUriTemplateHandler =
            new DefaultUriBuilderFactory("https://"+host+"/v3/");

            // Step 3
            RestTemplate restTemplate1 = new RestTemplateBuilder()
                .uriTemplateHandler(defaultUriTemplateHandler).build();
            ResponseEntity<Object> response = restTemplate1
                .postForEntity("assets?include_dimension=true", 
                 requestEntity, Object.class);
            return ResponseEntity.ok(response);

        } catch (RestClientResponseException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }