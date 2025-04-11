package it.unisa.cluelab.lllm.llm.agents.generic;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.FunctionExecutor;
import com.theokanning.openai.service.OpenAiService;
import it.unisa.cluelab.lllm.llm.LLMEvaluatorAgent;
import it.unisa.cluelab.lllm.llm.prompt.Prompt;
import it.unisa.cluelab.lllm.llm.prompt.PromptList;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class OpenAIEvaluatorAgent<E> extends LLMEvaluatorAgent<E> {

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

    public String functionName() {
        return "auto";
    }

    @Override
    public E evaluate(List<Prompt> prompts, String grid) {
        OpenAiService service = new OpenAiService(getToken(), Duration.ofSeconds(90));
        ChatMessage responseMessage = service.createChatCompletion(getChatCompletionRequest(prompts))
                .getChoices()
                .get(0)
                .getMessage();
        ChatFunctionCall functionCall = responseMessage.getFunctionCall();
        return (E) responseMessage.getContent();
    }

    public ChatCompletionRequest getChatCompletionRequest(List<Prompt> prompts) {
        return ChatCompletionRequest
                .builder()
                .model(this.model)
                .temperature(this.temperature)
                .messages(toChatMessages(prompts))
                .functions(getFunctionExecutor().getFunctions())
                .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of(functionName()))
                .n(1)
                .maxTokens(this.ctx)
                .logitBias(new HashMap<>())
                .build();
    }


    @NotNull
    public FunctionExecutor getFunctionExecutor() {
        return new FunctionExecutor(Collections.singletonList(ChatFunction.builder()
                .name("transpile")
                .description("Convert a string")
                .executor(Result.class, Result::getAnswer)
                .build()));
    }

    @NotNull
    private static List<ChatMessage> toChatMessages(List<Prompt> prompts) {
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
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getToken() {
        return token;
    }

    public String getModel() {
        return model;
    }

    public int getCtx() {
        return ctx;
    }

    class Result {
        String answer;

        public String getAnswer() {
            return answer;
        }
    }
}
