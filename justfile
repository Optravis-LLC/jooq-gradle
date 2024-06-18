set dotenv-load

verify:
    ./gradlew check
    ./gradlew -p example check

test:
    ./gradlew test
    ./gradlew -p example test

action-lint:
    actionlint -verbose

publish:
    ./gradlew publishPlugins -Pversion=$(cat version.txt | xargs)
