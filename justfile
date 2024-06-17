set dotenv-load

verify:
    ./gradlew check

test:
    ./gradlew check

action-lint:
    actionlint -verbose

publish:
    ./gradlew publish -Pversion=$(cat version.txt | xargs)
