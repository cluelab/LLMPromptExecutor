package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OLLAMAEvaluatorAgent;
import org.json.JSONObject;

public class MistralEvaluatorAgent extends OLLAMAEvaluatorAgent {


    public MistralEvaluatorAgent(JSONObject setttings) {
        super(setttings);
        setModel("mistral");
    }

}
