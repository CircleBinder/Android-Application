package circlebinder.common.event;

public final class GenreBuilder {

    int id;
    String name;

    public Genre build() {
        return new Genre(this);
    }

    public GenreBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public GenreBuilder setName(String name) {
        this.name = name;
        return this;
    }
}
