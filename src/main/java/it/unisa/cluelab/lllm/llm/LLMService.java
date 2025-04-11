package it.unisa.cluelab.lllm.llm;

import it.unisa.cluelab.lllm.Evaluation;
import it.unisa.cluelab.lllm.llm.prompt.Prompt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LLMService {

    private List<LLMEvaluatorAgent> evaluationStrategies;

    public LLMService() {
        this.evaluationStrategies = new ArrayList<>();
    }

    public void addEvaluationStrategy(LLMEvaluatorAgent agent) {
        this.evaluationStrategies.add(agent);
    }

    public List<Evaluation> evaluate(List<Prompt> prompts, String grid) {
        List<Evaluation> results = new ArrayList<>();
        this.evaluationStrategies.forEach(agent -> {
            try {
                Evaluation evaluation = new Evaluation();
                evaluation.setLlmAgent(agent.getClass().getName());
                String evaluationResponse = (String) agent.evaluate(prompts, grid);
                if (evaluationResponse != null) {
                    String[] response = evaluationResponse.replaceAll("\n", "").split(";");
                    if (response.length == 2) {
                        evaluation.setGrade(response[0]);
                        evaluation.setComment(response[1]);
                    } else {
                        evaluation.setComment(evaluationResponse);
                    }
                }
                results.add(evaluation);
            } catch (IOException e) {
                Evaluation evaluation = new Evaluation();
                evaluation.setLlmAgent(agent.getClass().getName());
                evaluation.setComment(e.getMessage());
                results.add(evaluation);
                //throw new RuntimeException(e);
            }
        });
        /*for(LLMEvaluatorAgent agent : this.evaluationStrategies) {

        }*/
        return results;
    }

}
