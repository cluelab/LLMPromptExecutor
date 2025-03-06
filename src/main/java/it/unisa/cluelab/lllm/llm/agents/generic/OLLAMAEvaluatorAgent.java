package it.unisa.cluelab.lllm.llm.agents.generic;

import it.unisa.cluelab.lllm.llm.LLMEvaluatorAgent;
import it.unisa.cluelab.lllm.llm.prompt.Prompt;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class OLLAMAEvaluatorAgent extends LLMEvaluatorAgent {

    public static Logger logger = Logger.getLogger(OLLAMAEvaluatorAgent.class.getName());
    private final String urlLlama;
    private final int port;

    private final double temperature;
    private final int timeout;

    private final double ctx;
    private String model;

    private OkHttpClient client;

    private static final String endPointGenerate = "/api/chat";


    public OLLAMAEvaluatorAgent(JSONObject settings) {
        super(settings);
        this.urlLlama = settings.getString("ollama-url");
        this.port = settings.getInt("ollama-port");
        this.temperature = settings.getFloat("temperature");
        this.ctx = settings.getInt("num_ctx");
        if(settings.has("timeout") && !settings.isNull("timeout")) {
            timeout = settings.getInt("timeout");
        } else {
            timeout = 600;
        }
        initClient();
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String evaluate(List<Prompt> prompts, String grid) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject prompt = new JSONObject();
        JSONObject options = new JSONObject();
        prompt.put("model", this.model);
        options.put("temperature", this.temperature);
        options.put("num_ctx", this.ctx);
        //options.put("top_k", 40);
        //options.put("top_p", 0.5);
        prompt.put("options", options);
        prompt.put("system", prompts.get(0).getContent());
        prompt.put("stream", false);

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
                .url(this.urlLlama + ":" + this.port + endPointGenerate)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseJson = response.body().string();
            logger.info(responseJson);
            JSONObject resp = new JSONObject(responseJson);
            JSONObject message = (JSONObject) resp.get("message");
            return (String) message.get("content");

        }
        System.out.println("null-response");
        return "ERROR_EMPTY_EVAL";
    }

    public void initClient() {
        this.client = new OkHttpClient().newBuilder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }

}
