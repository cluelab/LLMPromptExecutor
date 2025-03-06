package it.unisa.cluelab.lllm.llm.prompt;

import java.util.Scanner;

public class HumanInTheMiddle {

    private PromptList prompts;


    public HumanInTheMiddle() {
        prompts = new PromptList();
    }

    public void addHumanPrompt() throws Exception {
        Scanner in = new Scanner(System.in);
        String content = in.nextLine();
        prompts.addUserPrompt(content);
        in.close();
    }

    public void addAssistantPrompt(String content) throws Exception {
        prompts.addAssistantPrompt(content);
    }
}

