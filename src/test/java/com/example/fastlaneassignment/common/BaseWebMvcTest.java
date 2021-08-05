package com.example.fastlaneassignment.common;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest
@ActiveProfiles("web-mvc-test")
@Disabled
public class BaseWebMvcTest {
}
