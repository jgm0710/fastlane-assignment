package com.example.fastlaneassignment.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(value = {SpringExtension.class, RestDocumentationExtension.class, MockitoExtension.class})
@ActiveProfiles("web-mvc-test")
@Disabled
public abstract class BaseWebMvcTest {

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .apply(springSecurity())    //springSecurityFilter 를 타기 위해서 허용해줘야함. -> 없으면 filter를 타지 않음
                .alwaysDo(print())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    protected final String JSON_CONTENT_TYPE_DESCRIPTION = "application/json;charset=UTF-8";

    protected final String ACCESS_TOKEN_HEADER = "X-AUTH-TOKEN";

    protected final String ACCESS_TOKEN_HEADER_DESCRIPTION = "Access Token 기입";

    protected final String MOCK_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbklkIiwiaWQiOjEsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2MjgyMzI4MDQsImV4cCI6MTYyODIzMzEwNH0.6oLUhwOABXmeJxHZEsUuWVHnFUvsrHCI_3yaNS0kZsk";


}
