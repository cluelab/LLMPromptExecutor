package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.ClaudeEvaluatorAgent;
import org.json.JSONObject;

public class ClaudeSonnetEvaluatorAgent extends ClaudeEvaluatorAgent {


    public ClaudeSonnetEvaluatorAgent(JSONObject settings) {
        super(settings);
        this.setModel("claude-3-5-sonnet-20240620");
    }
}
