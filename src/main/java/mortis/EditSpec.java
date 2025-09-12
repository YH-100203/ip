package mortis;

public class EditSpec {
    private final String newDescription;
    private final String newBy;
    private final String newFrom;
    private final String newTo;

    public EditSpec(String newDescription, String newBy, String newFrom, String newTo) {
        this.newDescription = newDescription;
        this.newBy = newBy;
        this.newFrom = newFrom;
        this.newTo = newTo;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public String getNewBy() {
        return newBy;
    }

    public String getNewFrom() {
        return newFrom;
    }

    public String getNewTo() {
        return newTo;
    }
}

