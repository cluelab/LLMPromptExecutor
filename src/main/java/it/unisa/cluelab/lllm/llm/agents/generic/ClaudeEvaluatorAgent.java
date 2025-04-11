package it.unisa.cluelab.lllm.llm.agents.generic;

import it.unisa.cluelab.lllm.llm.LLMEvaluatorAgent;
import it.unisa.cluelab.lllm.llm.prompt.Prompt;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class ClaudeEvaluatorAgent extends LLMEvaluatorAgent<String> {

    private final int maxTokens;
    private String model;

    private OkHttpClient client;

    private static final String endPointGenerate = "/v1/messages";
    private static final String urlClaude = "https://api.anthropic.com";
    private final String token;
    private final double temperature;

    public ClaudeEvaluatorAgent(JSONObject settings) {
        super(settings);
        this.token = settings.getString("claude-token");
        this.temperature = settings.getFloat("temperature");
        this.maxTokens = settings.getInt("num_ctx");
        initClient();
    }

    @Override
    public String evaluate(List<Prompt> prompts, String grid) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject prompt = new JSONObject();
        prompt.put("model", this.model);
        prompt.put("max_tokens", this.maxTokens);
        prompt.put("system", prompts.get(0).getContent());
        prompt.put("temperature", this.temperature);

        JSONArray messages = new JSONArray();
        prompt.put("messages", messages);


        for (int i = 1; i < prompts.size(); i++) {
            Prompt p = prompts.get(i);
            JSONObject message = new JSONObject();
            message.put("role", p.getRole());
            message.put("content", p.getContent());
            messages.put(message);
        }

        RequestBody body = RequestBody.create(mediaType, prompt.toString());

        Request request = new Request.Builder()
                .url(urlClaude + endPointGenerate)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", this.token)
                .addHeader("anthropic-version", "2023-06-01")
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            if(response.code() == 200) {
                String responseJson = response.body().string();
                response.close();
                JSONObject resp = new JSONObject(responseJson);
                JSONArray msgList = (JSONArray) resp.get("content");
                JSONObject msgContent = (JSONObject) msgList.get(0);

                return (String) msgContent.get("text");
            } else if(response.code() == 429) {
                response.close();
                return "ERROR_CLAUDE_RATE_LIMIT";
            } else {
                String responseJson = response.body().string();
                response.close();
                return responseJson.toUpperCase();
            }

        }
        response.close();
        return "ERROR_EMPTY_EVAL";
    }


    public void initClient() {
        this.client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public void setModel(String model) {
        this.model = model;
    }
}
