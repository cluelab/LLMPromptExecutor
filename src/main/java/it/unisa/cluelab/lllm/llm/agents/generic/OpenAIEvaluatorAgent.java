package it.unisa.cluelab.lllm.llm.agents.generic;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;
import it.unisa.cluelab.lllm.llm.LLMEvaluatorAgent;
import it.unisa.cluelab.lllm.llm.prompt.Prompt;
import it.unisa.cluelab.lllm.llm.prompt.PromptList;
import org.json.JSONObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class OpenAIEvaluatorAgent extends LLMEvaluatorAgent {

    private String model;
    private final String token;
    private final double temperature;
    private final int ctx;

    public OpenAIEvaluatorAgent(JSONObject settings) {
        super(settings);
        this.token = settings.getString("openai-token");
        this.temperature = settings.getFloat("temperature");
        this.ctx = settings.getInt("num_ctx");
    }

    public void setModel(String model) {
        this.model = model;
    }



    @Override
    public String evaluate(List<Prompt> prompts, String grid) {
        OpenAiService service = new OpenAiService(token, Duration.ofSeconds(90));

        FunctionExecutor functionExecutor = new FunctionExecutor(Collections.singletonList(ChatFunction.builder()
                .name("transpile")
                .description("Convert a string")
                .executor(Result.class, Result::getAnswer)
                .build()));

        List<ChatMessage> messages = new ArrayList<>();
        for (int i = 0; i < prompts.size(); i++) {
            if (prompts.get(i).getRole().equals(PromptList.SYSTEM)) {
                ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), prompts.get(i).getContent());
                messages.add(systemMessage);
            } else if (prompts.get(i).getRole().equals(PromptList.USER)) {
                ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), prompts.get(i).getContent());
                messages.add(firstMsg);
            } else {
                ChatMessage firstMsg = new ChatMessage(ChatMessageRole.ASSISTANT.value(), prompts.get(i).getContent());
                messages.add(firstMsg);
            }
        }


        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(this.model)
                .temperature(this.temperature)
                .messages(messages)
                .functions(functionExecutor.getFunctions())
                .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("auto"))
                .n(1)
                .maxTokens(this.ctx)
                .logitBias(new HashMap<>())
                .build();
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        messages.add(responseMessage); // don't forget to update the conversation with the latest response
        ChatFunctionCall functionCall = responseMessage.getFunctionCall();
        return responseMessage.getContent();
    }

    class Result {
        String answer;

        public String getAnswer() {
            return answer;
        }
    }
}
