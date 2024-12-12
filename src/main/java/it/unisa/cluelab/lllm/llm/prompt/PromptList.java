package it.unisa.cluelab.lllm.llm.prompt;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class PromptList extends ArrayList<Prompt> {

    public static final String SYSTEM = "system";
    public static final String USER = "user";
    public static final String ASSISTANT = "assistant";

    public void addPrompt(String type, String content) throws Exception {
        if (size() == 0 && type.equals(SYSTEM)) {
            add(new Prompt(type, content));
        } else if (!get(size() - 1).getType().equals(type)) {
            add(new Prompt(type, content));
        } else {
            throw new Exception("wrong format of prompts");
        }
    }

    public void replaceTag(String tag, String value) {
        this.forEach(p -> p.setContent(p.getContent().replace(tag, value)));

    }

    public void exportToJson(String filename) throws FileNotFoundException {
        JSONArray messages = new JSONArray();

        for (int i = 0; i < size(); i++) {
            Prompt p = get(i);
            JSONObject message = new JSONObject();
            message.put("role", p.getType());
            message.put("content", p.getContent());
            messages.put(message);
        }
        file_put_contents(filename, messages.toString());

    }

    public void importFromJson(String filename) throws IOException {
        String json = file_get_contents(filename);
        JSONArray messages = new JSONArray(json);
        parseJSONContent(messages);
    }

    public void parseJSONContent(JSONArray messages) {
        clear();
        for (int i = 0; i < messages.length(); i++) {
            JSONObject m = messages.getJSONObject(i);
            Prompt p = new Prompt(m.getString("role"), m.getString("content"));
            add(p);
        }
    }

    public static void file_put_contents(String file_name, String content) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(file_name);
        out.println(content);
        out.flush();
        out.close();
    }

    private String file_get_contents(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder builder = new StringBuilder();
        String line;

        // For every line in the file, append it to the string builder
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();
        return builder.toString();
    }

}
