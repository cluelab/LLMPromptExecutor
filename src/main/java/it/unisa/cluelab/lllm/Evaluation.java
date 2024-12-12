package it.unisa.cluelab.lllm;

public class Evaluation {

    private String llmAgent;
    private String grade;

    private String comment;



    public void setLlmAgent(String llmAgent) {
        this.llmAgent = llmAgent;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setComment(String comment) {
        this.comment = comment.replace("\n", "");
    }

    @Override
    public String toString() {
        return  llmAgent + "\";\"" + grade + "\";\"" + comment + "\"";
    }
}
