import com.SimpleChat.SimpleChat.SimpleChatApplication;
import com.SimpleChat.SimpleChat.controller.MessageController;
import com.SimpleChat.SimpleChat.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = SimpleChatApplication.class)
class MessageControllerTest {

    @Autowired
    private MessageController messageController;

    private MockMvc mockMvc;

    private MessageService messageService; // Будем мокать вручную

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class); // Мокаем сервис вручную
        mockMvc = MockMvcBuilders.standaloneSetup(new MessageController(messageService)).build(); // Передаем мок
    }

    @Test
    void shouldCreateMessage() throws Exception {
        when(messageService.createMessage(any())).thenReturn(true);

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"Hello!\", \"sender\": \"User1\"}"))
                .andExpect(status().isOk());
    }
}
