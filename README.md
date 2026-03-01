# FIX Inspector (IntelliJ Plugin)

This project is an IntelliJ IDEA plugin that previews raw FIX messages as a table with Tag | FieldName | Value | Description.

Prerequisites
- JDK 11 or 17 (set JAVA_HOME)
- IntelliJ IDEA (for development)
- Gradle (or use the Gradle wrapper)

Build & Run (Windows PowerShell)
- Build: `./gradlew.bat build`
- Run IDE sandbox: `./gradlew.bat runIde`
- Run tests: `./gradlew.bat test`

Notes
- The plugin uses a bundled `resources/fix-spec.json` file for field metadata.
- Core parsing logic lives in `src/main/kotlin/com/fixinspector/core` and is pure Kotlin (no IDEA SDK) so you can run unit tests quickly.

Next steps
- Implement FileEditorProvider and preview UI
- Add settings and export/copy actions
- Add unit tests for parser/tokenizer/delimiter detection

