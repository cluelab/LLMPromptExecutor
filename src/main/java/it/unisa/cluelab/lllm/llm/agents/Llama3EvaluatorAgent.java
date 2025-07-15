package it.unisa.cluelab.lllm.llm.agents;

import it.unisa.cluelab.lllm.llm.agents.generic.OLLAMAEvaluatorAgent;
import org.json.JSONObject;

public class Llama3EvaluatorAgent extends OLLAMAEvaluatorAgent<String> {

    public Llama3EvaluatorAgent(JSONObject setttings) {
        super(setttings);
        setModel("llama3");
    }

    @Override
    public String parse(String rawContent) {
        return rawContent;
    }
}
