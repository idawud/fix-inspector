# FIX Inspector (IntelliJ Plugin)

This project is an IntelliJ IDEA plugin that previews raw FIX messages as a table with Tag | FieldName | Value | Description.

This repository has been migrated to Maven. The original Gradle files are retained as markers but are intentionally disabled to avoid accidental builds.

Prerequisites
- JDK 11 or 17 (set JAVA_HOME)
- IntelliJ IDEA (for development)
- Maven

Build & Run (Windows PowerShell)
- Build: `mvn -e -X -B clean package`
- Run tests: `mvn test`

Notes
- The plugin uses a bundled `resources/fix-spec.json` file for field metadata.
- Core parsing logic lives in `src/main/kotlin/com/fixinspector/core` and is pure Kotlin (no IDEA SDK) so you can run unit tests quickly.

Next steps
- Add plugin packaging or consider using Gradle + org.jetbrains.intellij plugin for IDE sandbox runs (recommended for plugin development).
- Add unit tests for parser/tokenizer/delimiter detection
