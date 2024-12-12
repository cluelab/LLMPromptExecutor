package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OpenAIEvaluatorAgent;
import org.json.JSONObject;

public class Gpt35EvaluationAgent extends OpenAIEvaluatorAgent {
    public Gpt35EvaluationAgent(JSONObject setttings) {
        super(setttings);
        setModel("gpt-3.5-turbo");
    }
}
