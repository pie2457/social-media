package wanted.media.post.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import wanted.media.post.dto.PostDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PostControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void posts_list_성공() {
        // When
        String url = "/api/posts?hashtag=wanted&type=FACEBOOK&orderBy=createdAt&sortDirection=ASC&search_by=title&search=판교&page=0&page_count=10";
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<PostDto> posts = responseEntity.getBody();
        assertThat(posts).isNotEmpty();
    }

    @Test
    public void search_by_성공() {
        String url = "/api/posts?hashtag=wanted&type=FACEBOOK&orderBy=createdAt&sortDirection=ASC&search_by=invalidSearchBy&page=0&page_count=10";
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void 유효하지_않은_page값() {
        String url = "/api/posts?hashtag=wanted&type=FACEBOOK&orderBy=createdAt&sortDirection=ASC&search_by=title&search=판교&page=-1&page_count=10";
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void 유효하지_않은_page_count_값() {
        String url = "/api/posts?hashtag=wanted&type=FACEBOOK&orderBy=createdAt&sortDirection=ASC&search_by=title&search=판교&page=0&page_count=-10";
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void search_키워드가_없을_때() {
        String url = "/api/posts?hashtag=wanted&type=FACEBOOK&orderBy=createdAt&sortDirection=ASC&search_by=title&page=0&page_count=10";
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
        
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<PostDto> posts = responseEntity.getBody();
        assertThat(posts).isNotEmpty();
    }
}