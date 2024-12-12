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


    public String getSettingProperty(String key) {
        if(!this.settings.has(key)) return  ""; /** maybe it is better an exception **/
        return this.settings.getString(key);
    }
}
