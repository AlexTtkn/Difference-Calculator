run-dist:
	./build/install/app/bin/app

help:
	./build/install/app/bin/app -h

compare-json:
	./build/install/app/bin/app src/test/resources/testFile1.json src/test/resources/testFile2.json

build:
	./gradlew clean build

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

build-run: build run

.PHONY: build