package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.GeminiEvaluatorAgent;
import org.json.JSONObject;

public class GeminiPro15EvaluationAgent extends GeminiEvaluatorAgent {

    public GeminiPro15EvaluationAgent(JSONObject setttings) {
        super(setttings);
        this.setModel("gemini-pro");
    }
}
