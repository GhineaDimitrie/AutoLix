package com.example.demo.controller;

import com.example.demo.dto.ChatRequest;
import com.example.demo.dto.ChatResponse;
import com.example.demo.dto.ChatTurn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private static final int MAX_HISTORY_TURNS = 20;

    private final ChatClient chatClient;

    public ChatController(ChatClient autolixChatClient) {
        this.chatClient = autolixChatClient;
    }

    @GetMapping("/chat")
    public String showChatPage() {
        return "chat";
    }

    @PostMapping("/chat/ask")
    @ResponseBody
    public ResponseEntity<ChatResponse> ask(@RequestBody ChatRequest request) {
        List<ChatTurn> messages = request.messages();

        if (messages == null || messages.isEmpty()) {
            return ResponseEntity.badRequest().body(new ChatResponse("Please type a question."));
        }

        ChatTurn last = messages.get(messages.size() - 1);
        if (!"user".equals(last.role()) || last.content() == null || last.content().isBlank()) {
            return ResponseEntity.badRequest().body(new ChatResponse("Please type a question."));
        }

        // Defensively cap history server-side - never trust the client to have done this itself.
        List<ChatTurn> trimmed = messages.size() > MAX_HISTORY_TURNS
                ? messages.subList(messages.size() - MAX_HISTORY_TURNS, messages.size())
                : messages;

        List<Message> springAiMessages = new ArrayList<>();
        for (ChatTurn turn : trimmed) {
            if ("user".equals(turn.role())) {
                springAiMessages.add(new UserMessage(turn.content()));
            } else if ("assistant".equals(turn.role())) {
                springAiMessages.add(new AssistantMessage(turn.content()));
            }
        }

        try {
            String reply = chatClient.prompt()
                    .messages(springAiMessages)
                    .call()
                    .content();
            return ResponseEntity.ok(new ChatResponse(reply));
        } catch (Exception ex) {
            log.error("Chat request failed", ex);
            String msg = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
            if (msg.contains("429") || msg.contains("rate limit") || msg.contains("resource_exhausted")) {
                return ResponseEntity.status(429).body(new ChatResponse(
                        "AutoLix's assistant is getting a lot of questions right now (free-tier limit reached) - please try again in a moment."));
            }
            return ResponseEntity.internalServerError().body(new ChatResponse(
                    "Sorry, something went wrong answering that. Please try again."));
        }
    }
}
