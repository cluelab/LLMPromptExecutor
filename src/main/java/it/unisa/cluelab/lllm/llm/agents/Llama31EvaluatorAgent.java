package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OLLAMAEvaluatorAgent;
import org.json.JSONObject;

public class Llama31EvaluatorAgent extends OLLAMAEvaluatorAgent<String> {

    public Llama31EvaluatorAgent(JSONObject setttings) {
        super(setttings);
        setModel("llama3.1");
    }

    @Override
    public String parse(String rawContent) {
        return rawContent;
    }
}
