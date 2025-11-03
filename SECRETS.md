# Secrets

## `GRADLE_PUBLISH_KEY` and `GRADLE_PUBLISH_SECRET`

Keys to publish to the gradle plugins portal

### Rotation procedure

1. Go to https://plugins.gradle.org
2. Log-in as support@optravis.com (Use the password from TPM)
3. Generate new keys
4. Set the `GRADLE_PUBLISH_KEY` and `GRADLE_PUBLISH_SECRET` github [actions secrets](https://github.com/Optravis-LLC/jooq-gradle/settings/secrets/actions) accordingly
5. Revoke any unsed keys the old keys from https://plugins.gradle.org
