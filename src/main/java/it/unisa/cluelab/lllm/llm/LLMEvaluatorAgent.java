package it.unisa.cluelab.lllm.llm;

import it.unisa.cluelab.lllm.llm.prompt.PromptList;
import org.json.JSONObject;

import java.io.IOException;

public abstract class LLMEvaluatorAgent<E> {

    private JSONObject settings;
    public LLMEvaluatorAgent(JSONObject settings) {
        this.settings = new JSONObject();
        if(settings != null) this.settings = settings;
    }
    public abstract E evaluate(PromptList prompts, String grid) throws IOException;


}
