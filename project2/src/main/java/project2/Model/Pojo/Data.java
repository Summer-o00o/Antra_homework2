package project2.Model.Pojo;

public class Data {
    private String name;
    private Translation translations;

    public Translation getTranslations() {
        return translations;
    }

    public void setTranslations(Translation translation) {
        this.translations = translation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
