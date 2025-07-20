# 🛡️ karate-scanner

**Code quality and static analysis tool for Karate DSL.**  
Scans `.feature` files for common issues, enforces best practices, and integrates with **SonarQube** for visibility.

---

## 🔍 Why Karate Scanner?

Karate is a powerful testing framework — but `.feature` files often escape quality checks. `karate-scanner` fills that gap with:

- ✅ Static code analysis for `.feature` files
- ✅ Built-in quality rules (and extensible)
- ✅ CLI and Maven plugin support
- ✅ SonarQube-compatible reports
- ✅ Future support for JavaScript and Java glue static analysis

---

## 📦 Project Modules

```text
karate-scanner/
├── karate-scanner-core      → Quality rules engine & feature parser
├── karate-scanner-cli       → Command-line tool: `scan`
├── karate-scanner-maven     → Maven plugin: `<goal>scan</goal>`
├── karate-scanner-example   → Sample Karate project for testing
```

---

## 🚀 Usage

### 1. CLI

```bash
java -jar karate-scanner-cli.jar scan -f src/test/java
```

Generates:

- `target/quality-report.json` – raw issue list
- `target/sonar-issues.json` – SonarQube-compatible

### 2. Maven Plugin

```xml
<plugin>
  <groupId>io.github.jinsgeorge</groupId>
  <artifactId>karate-scanner-maven</artifactId>
  <version>1.0.0</version>
  <executions>
    <execution>
      <goals>
        <goal>scan</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

```bash
 mvn io.github.jinsgeorge:karate-scanner-maven-plugin:1.0.0:scan
```

---

## ✅ Built-in Quality Rules

| Rule                         | Description                                                                 | Severity  |
|------------------------------|-----------------------------------------------------------------------------|-----------|
| `print-usage`                | Warns if `print` is used instead of `karate.log()`                          | Minor     |
| `duplicate-step`             | Flags repeated steps in a scenario                                          | Minor     |
| `empty-scenario`             | Detects scenarios with no executable steps                                  | Major     |
| `duplicate-scenario-name`    | Detects reused `Scenario:` titles within the same file                      | Major     |
| `commented-out-code`         | Flags commented-out lines that resemble real steps                          | Minor     |
| `missing-tag`                | Warns if no `@tag` is used                                                  | Minor     |
| `missing-assertion`          | No `Then`, `Match`, or status check present                                 | Major     |
| `hardcoded-auth`             | Detects usage of hardcoded tokens/keys/passwords                            | Critical  |
| `incorrect-step-order`       | Flags `Then` before `When`, or broken Gherkin step ordering                 | Major     |
| `large-json-inline`          | Warns if inline JSON payloads are too large                                 | Minor     |
| `gherkin-structure`          | Enforces proper Given–When–Then format                                      | Minor     |

---

## 🔗 SonarQube Integration

Add this to your `sonar-project.properties`:

```properties
sonar.externalIssuesReportPaths=target/sonar-issues.json
```

---

## 🧪 Development & Testing

```bash
mvn clean install
java -jar karate-scanner-cli/target/karate-scanner-cli.jar scan -f karate-scanner-example/src/test/java
```

---

## 📜 License

Licensed under the **Apache 2.0 License**.  
Feel free to fork, extend, and contribute!

---

## ✨ Coming Soon

- [ ] IntelliJ plugin (`karate-pro`) with syntax highlighting and debugging
- [ ] Static analysis for Java glue / JS via embedded linters
- [ ] GitHub Action for CI integration
