package Editor.Project.dto;
// dto data Transfer Object , the purpose  dto carries data bw
//frontend , backend , websocket as the parcel carrying data
// this can be deleted but it provides easy and harder maintenance
public class CodeRequest {
    private String input;
    private String code;
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

