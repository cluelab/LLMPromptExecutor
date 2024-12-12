package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OpenAIEvaluatorAgent;
import org.json.JSONObject;

public class Gpt4oEvaluationAgent extends OpenAIEvaluatorAgent {
    public Gpt4oEvaluationAgent(JSONObject setttings) {
        super(setttings);
        setModel("gpt-4o");
    }
}
