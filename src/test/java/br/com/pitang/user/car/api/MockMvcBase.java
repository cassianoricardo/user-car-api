package br.com.pitang.user.car.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public abstract class MockMvcBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    protected ResultActions performGet(String uri, Object ... uriVars) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.get(uri, uriVars).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performGet(String uri) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performPut(String uri, Object content, Object ... uriVars) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.put(uri, uriVars)
                .content(mapper.writeValueAsBytes(content)).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performDelete(String uri, Object ... uriVars) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.delete(uri, uriVars).contentType(MediaType.APPLICATION_JSON));
    }
    protected ResultActions performPost(String uri, Object content) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(mapper.writeValueAsBytes(content)).contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performMultipart(HttpMethod httpMethod, String uri, String fileName , byte[] file, Object ... uriVars) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.multipart(httpMethod, uri, uriVars).file(fileName,file)
                .contentType(MULTIPART_FORM_DATA_VALUE));
    }
}