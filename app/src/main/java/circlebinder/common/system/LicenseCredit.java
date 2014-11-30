package circlebinder.common.system;

final class LicenseCredit {

    private final String name;
    private final String author;
    private final int since;
    private final String licenseName;
    private final String licenseBody;

    LicenseCredit(String name, String author, int since, String licenseName, String licenseBody) {
        this.name = name;
        this.author = author;
        this.since = since;
        this.licenseName = licenseName;
        this.licenseBody = licenseBody;
    }

    String getName() {
        return name;
    }

    String getAuthor() {
        return author;
    }

    int getSince() {
        return since;
    }

    String getLicenseName() {
        return licenseName;
    }

    String getLicenseBody() {
        return licenseBody;
    }
}
