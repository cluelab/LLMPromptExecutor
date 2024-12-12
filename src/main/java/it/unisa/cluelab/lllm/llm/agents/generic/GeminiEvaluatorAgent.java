package it.unisa.cluelab.lllm.llm.agents.generic;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.google.cloud.vertexai.generativeai.preview.ResponseHandler;
import it.unisa.cluelab.lllm.llm.LLMEvaluatorAgent;
import it.unisa.cluelab.lllm.llm.prompt.Prompt;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public abstract class GeminiEvaluatorAgent extends LLMEvaluatorAgent {

    private String model;
    private final String projectId;
    private final String projectLocation;
    public GeminiEvaluatorAgent(JSONObject settings) {
        super(settings);
        this.projectId = getSettingProperty("gemini-projectId");
        this.projectLocation = getSettingProperty("gemini-location");
    }

    public void setModel(String model) {
        this.model = model;
    }


    @Override
    public String evaluate(List<Prompt> prompts, String grid) throws IOException {
        try {
            VertexAI vertexAI = new VertexAI(this.projectId, this.projectLocation);
            GenerativeModel model = new GenerativeModel(this.model, vertexAI);
            GenerateContentResponse response = model.generateContent(grid.concat(""));
            return (ResponseHandler.getText(response));
        } catch (IOException e) {
            return "ERROR_EMPTY_EVAL";
        } catch (Exception e) {
            return "ERROR_GENERIC_" + e.getMessage();
        }
    }

}
