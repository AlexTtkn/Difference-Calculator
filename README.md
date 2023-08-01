### Hexlet tests and linter status:
[![Actions Status](https://github.com/AlexTtkn/java-project-71/workflows/hexlet-check/badge.svg)](https://github.com/AlexTtkn/java-project-71/actions)
[![Java CI](https://github.com/AlexTtkn/java-project-71/actions/workflows/main.yml/badge.svg)](https://github.com/AlexTtkn/java-project-71/actions/workflows/main.yml)
[![Maintainability](https://api.codeclimate.com/v1/badges/5912a40cc4587cf4e195/maintainability)](https://codeclimate.com/github/AlexTtkn/java-project-71/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/5912a40cc4587cf4e195/test_coverage)](https://codeclimate.com/github/AlexTtkn/java-project-71/test_coverage)

## Description:
A **difference calculator** is a program that determines the differences between two sets of data. 
It is a frequently required function, and there are numerous online services accessible to perform this task, like [JSON Diff](http://www.jsondiff.com/).
Usually programs like this are used when running tests or automatically tracking changes in configuration files.

**Utility Features:**
- Support for different input formats: yaml and json.
- Generating a report in the form of plain text, stylish and json.

To install the program, you need to download the project, open the terminal, go to the "app" folder, and prescribe the path to compare the 2 files you selected.
For instance:

`./app path/to/file.yml another/path/file.json`

`./app -f json path/to/file.yml another/path/file.json`

`./app -f plain path/to/file.yml another/path/file.json`



## Examples
[![Json](https://i.postimg.cc/9Qjp4SwZ/Json.jpg)](https://asciinema.org/a/PK8gaBfYhOH05eqsCjFosQb3F)

[![Yaml](https://i.postimg.cc/g2kxBqf1/Yaml.jpg)](https://asciinema.org/a/c41LkJHRvcMkwbXVdk3u9SdOa)

