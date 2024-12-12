package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OLLAMAEvaluatorAgent;
import org.json.JSONObject;

public class Llama3EvaluatorAgent extends OLLAMAEvaluatorAgent {

    public Llama3EvaluatorAgent(JSONObject setttings) {
        super(setttings);
        setModel("llama3");
    }

}
