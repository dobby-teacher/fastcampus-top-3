package com.fastcampus.restapidemo.domain.books.controller;

import com.fastcampus.restapidemo.RestDocsConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(RestDocsConfig.class)
@TestConstructor(autowireMode = ALL)
@SpringBootTest
public class BookControllerTest {
    private final MockMvc mockMvc;

    @Test
    void getBookById() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/books/1")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("getBookById",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("책 번호"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("타이틀"),
                                fieldWithPath("publisher").type(JsonFieldType.STRING).description("출판사"),
                                fieldWithPath("publishedDate").type(JsonFieldType.STRING).description("출판일시"),
                                fieldWithPath("authors").type(JsonFieldType.ARRAY).description("저자 리스트"),
                                fieldWithPath("authors.[].id").type(JsonFieldType.NUMBER).description("저자 아이디"),
                                fieldWithPath("authors.[].name").type(JsonFieldType.STRING).description("저자 이름"),
                                fieldWithPath("reviews").type(JsonFieldType.ARRAY).description("리뷰 리스트"),
                                fieldWithPath("reviews.[].id").type(JsonFieldType.NUMBER).description("리뷰 번호"),
                                fieldWithPath("reviews.[].content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("reviews.[].rating").type(JsonFieldType.NUMBER).description("리뷰 평점"),
                                fieldWithPath("reviews.[].createdDate").type(JsonFieldType.STRING).description("리뷰 일시")
                        )
                ))
                .andDo(print())
        ;
    }

    @Test
    void getBooks() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/books")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("getBooks",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("책 번호"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("타이틀"),
                                fieldWithPath("[].publisher").type(JsonFieldType.STRING).description("출판사"),
                                fieldWithPath("[].publishedDate").type(JsonFieldType.STRING).description("출판일시"),
                                fieldWithPath("[].authors").type(JsonFieldType.ARRAY).description("저자 리스트"),
                                fieldWithPath("[].authors.[].id").type(JsonFieldType.NUMBER).description("저자 아이디"),
                                fieldWithPath("[].authors.[].name").type(JsonFieldType.STRING).description("저자 이름"),
                                fieldWithPath("[].reviews").type(JsonFieldType.ARRAY).description("리뷰 리스트"),
                                fieldWithPath("[].reviews.[].id").type(JsonFieldType.NUMBER).description("리뷰 번호"),
                                fieldWithPath("[].reviews.[].content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("[].reviews.[].rating").type(JsonFieldType.NUMBER).description("리뷰 평점"),
                                fieldWithPath("[].reviews.[].createdDate").type(JsonFieldType.STRING).description("리뷰 일시")
                        )
                ))
                .andDo(print())
        ;
    }
}