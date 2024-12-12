package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OLLAMAEvaluatorAgent;
import org.json.JSONObject;

public class Llama31EvaluatorAgent extends OLLAMAEvaluatorAgent {

    public Llama31EvaluatorAgent(JSONObject setttings) {
        super(setttings);
        setModel("llama3.1");
    }

}
