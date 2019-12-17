package demo.stackoverflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.stackoverflow.auth.AuthenticationObject;
import demo.stackoverflow.config.GlobalAdvice;
import demo.stackoverflow.controller.QuestionsController;
import demo.stackoverflow.controller.requestmodel.QuestionRequest;
import demo.stackoverflow.model.Question;
import demo.stackoverflow.services.QuestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @InjectMocks
    private QuestionsController controller;

    @Mock
    private QuestionService service;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc=  MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalAdvice()).build();
        AuthenticationObject auth = new AuthenticationObject("U1");
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Test
    public void createQuestion() throws Exception {
        QuestionRequest request = new QuestionRequest();
        request.setQuestion("This is a test question");
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/api/v1/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(request));
        Mockito.doNothing().when(service).save(any());
        mockMvc.perform(builder).andExpect(status().isCreated());
    }
    @Test
    public void getAllQuestionWhenNoQuestions() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/api/v1/questions")
                        .contentType(MediaType.APPLICATION_JSON);
        Mockito.when(service.getAllQuestions()).thenReturn(Collections.emptyList());
        mockMvc.perform(builder).andExpect(status().isNoContent());
    }

    @Test
    public void getAllQuestionWhenThereAreQuestionsExist() throws Exception {
        Question request = new Question("A Question from DB", "U1");
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/api/v1/questions")
                        .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(service.getAllQuestions()).thenReturn(Collections.singletonList(request));
        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void updateQuestion() throws Exception {
        QuestionRequest request = new QuestionRequest();
        request.setQuestion("This is a test question");
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/api/v1/questions/questionId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(request));

        Mockito.doNothing().when(service).updateQuestion(anyString(), any());
        mockMvc.perform(builder).andExpect(status().isOk());
    }

    private byte[] objectToJson(Object object) {
        byte[] result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
