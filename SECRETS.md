# Secrets

## `GRADLE_PUBLISH_KEY` and `GRADLE_PUBLISH_SECRET`

Keys to publish to the gradle plugins portal

### Rotation procedure

1. Navigate to https://plugins.gradle.org/u/optravis
2. Log-in as support@optravis.com (Use the password from TPM)
3. Generate new keys
4. Set the `GRADLE_PUBLISH_KEY` and `GRADLE_PUBLISH_SECRET` github [actions secrets](https://github.com/Optravis-LLC/jooq-gradle/settings/secrets/actions) accordingly
5. Revoke any unsed keys from https://plugins.gradle.org/u/optravis
