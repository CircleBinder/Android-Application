package circlebinder.common.system;

final class OpenSourceLicenseCredit {

    private final String name;
    private final String author;
    private final int since;
    private final String licenseBody;

    OpenSourceLicenseCredit(String name, String author, int since, String licenseBody) {
        this.name = name;
        this.author = author;
        this.since = since;
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

    String getLicenseBody() {
        return licenseBody;
    }
}
