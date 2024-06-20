set dotenv-load

verify:
    ./gradlew check
    ./gradlew -p examples/with_config check
    ./gradlew -p examples/default_config check

test:
    ./gradlew test
    ./gradlew -p example test

action-lint:
    actionlint -verbose

publish:
    ./gradlew publishPlugins -Pversion=$(cat version.txt | xargs)
