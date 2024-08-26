package wanted.media.post.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.yml")
class PostControllerTest {
    private MockMvc mockMvc;

    @Test
    void posts_list_성공() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .param("hashtag", "wanted")
                        .param("type", "FACEBOOK")
                        .param("orderBy", "createdAt")
                        .param("sortDirection", "ASC")
                        .param("search_by", "title")
                        .param("search", "판교")
                        .param("page", "0")
                        .param("page_count", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty()); // 응답이 비어있지 않음을 확인
    }

    @Test
    void search_by_성공() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .param("hashtag", "wanted")
                        .param("type", "FACEBOOK")
                        .param("orderBy", "createdAt")
                        .param("sortDirection", "ASC")
                        .param("search_by", "invalidSearchBy")
                        .param("page", "0")
                        .param("page_count", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // 잘못된 검색 조건에 대한 에러 확인
    }

    @Test
    void 유효하지_않은_page값() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .param("hashtag", "wanted")
                        .param("type", "FACEBOOK")
                        .param("orderBy", "createdAt")
                        .param("sortDirection", "ASC")
                        .param("search_by", "title")
                        .param("search", "판교")
                        .param("page", "-1") // 유효하지 않은 페이지 번호
                        .param("page_count", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // 페이지 값이 유효하지 않음을 확인
    }

    @Test
    void 유효하지_않은_page_count_값() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .param("hashtag", "wanted")
                        .param("type", "FACEBOOK")
                        .param("orderBy", "createdAt")
                        .param("sortDirection", "ASC")
                        .param("search_by", "title")
                        .param("search", "판교")
                        .param("page", "0")
                        .param("page_count", "-10") // 유효하지 않은 페이지 수
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // 페이지 수가 유효하지 않음을 확인
    }
    
    @Test
    void search_키워드가_없을_때() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .param("hashtag", "wanted")
                        .param("type", "FACEBOOK")
                        .param("orderBy", "createdAt")
                        .param("sortDirection", "ASC")
                        .param("search_by", "title")
                        .param("page", "0")
                        .param("page_count", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty()); // 응답이 비어있지 않음을 확인
    }
}
