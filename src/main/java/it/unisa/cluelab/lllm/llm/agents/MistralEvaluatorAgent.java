package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OLLAMAEvaluatorAgent;
import org.json.JSONObject;

public class MistralEvaluatorAgent extends OLLAMAEvaluatorAgent<String> {


    public MistralEvaluatorAgent(JSONObject setttings) {
        super(setttings);
        setModel("mistral");
    }

    @Override
    public String parse(String rawContent) {
        return rawContent;
    }

}
