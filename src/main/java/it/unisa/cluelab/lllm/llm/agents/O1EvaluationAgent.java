package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OpenAIEvaluatorAgent;
import org.json.JSONObject;

public class O1EvaluationAgent extends OpenAIEvaluatorAgent {
    public O1EvaluationAgent(JSONObject setttings) {
        super(setttings);
        setModel("o1-mini");
    }
}
