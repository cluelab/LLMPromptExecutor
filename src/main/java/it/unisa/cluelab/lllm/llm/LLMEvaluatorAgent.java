package it.unisa.cluelab.lllm.llm;

import it.unisa.cluelab.lllm.llm.prompt.Prompt;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public abstract class LLMEvaluatorAgent {

    private JSONObject settings;
    public LLMEvaluatorAgent(JSONObject settings) {
        this.settings = new JSONObject();
        if(settings != null) this.settings = settings;
    }
    public abstract String evaluate(List<Prompt> prompts, String grid) throws IOException;

}
