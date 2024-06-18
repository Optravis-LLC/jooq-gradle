set dotenv-load

verify:
    ./gradlew check
    ./gradlew publishPlugins -Pversion=0.0.0 --validate-only
    ./gradlew -p example check

test:
    ./gradlew test
    ./gradlew -p example test

action-lint:
    actionlint -verbose

publish:
    ./gradlew publishPlugins -Pversion=$(cat version.txt | xargs)
