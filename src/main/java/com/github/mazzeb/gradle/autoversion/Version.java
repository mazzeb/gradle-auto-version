package com.github.mazzeb.gradle.autoversion;

public class Version {
    private final Long major;
    private final Long minor;
    private final Long patch;
    private final boolean snapshot;

    private Version(Builder builder) {
        major = builder.major;
        minor = builder.minor;
        patch = builder.patch;
        snapshot = builder.snapshot;
    }

    public Long getMajor() {
        return major;
    }

    public Long getMinor() {
        return minor;
    }

    public Long getPatch() {
        return patch;
    }

    public boolean isSnapshot() {
        return snapshot;
    }

    public static Builder versionBuilder() {
        return new Builder();
    }

    public static Builder versionBuilder(Version copy) {
        Builder builder = new Builder();
        builder.major = copy.major;
        builder.minor = copy.minor;
        builder.patch = copy.patch;
        builder.snapshot = copy.snapshot;
        return builder;
    }

    @Override
    public String toString() {
        StringBuilder versionString = new StringBuilder()
                .append(major.toString())
                .append(".")
                .append(minor.toString())
                .append(".")
                .append(patch.toString());

        if (snapshot) {
            versionString.append("-SNAPSHOT");
        }

        return versionString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        if (snapshot != version.snapshot) return false;
        if (major != null ? !major.equals(version.major) : version.major != null) return false;
        if (minor != null ? !minor.equals(version.minor) : version.minor != null) return false;
        return patch != null ? patch.equals(version.patch) : version.patch == null;
    }

    @Override
    public int hashCode() {
        int result = major != null ? major.hashCode() : 0;
        result = 31 * result + (minor != null ? minor.hashCode() : 0);
        result = 31 * result + (patch != null ? patch.hashCode() : 0);
        result = 31 * result + (snapshot ? 1 : 0);
        return result;
    }

    public Version nextMajor() {
        return versionBuilder(this)
                .withMajor(major+1)
                .withMinor(0L)
                .withPatch(0L)
                .build();
    }

    public Version nextMinor() {
        return versionBuilder(this)
                .withMinor(minor+1)
                .withPatch(0L)
                .build();
    }

    public Version nextPatch() {
        return versionBuilder(this)
                .withPatch(patch+1)
                .build();
    }

    public Version asSnapshot() {
        return versionBuilder(this)
                .withSnapshot(true)
                .build();
    }

    public Version asRelease() {
        return versionBuilder(this)
                .withSnapshot(false)
                .build();
    }

    public static final class Builder {
        private Long major = 0L;
        private Long minor = 0L;
        private Long patch = 0L;
        private boolean snapshot = false;

        private Builder() {
        }

        public Builder withMajor(Long val) {
            major = val;
            return this;
        }

        public Builder withMinor(Long val) {
            minor = val;
            return this;
        }

        public Builder withPatch(Long val) {
            patch = val;
            return this;
        }

        public Builder withSnapshot(boolean val) {
            snapshot = val;
            return this;
        }

        public Version build() {
            return new Version(this);
        }
    }
}
