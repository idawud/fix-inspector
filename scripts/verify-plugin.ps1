param(
  [string]$zipPath = "target\fix-inspector-0.1.0-plugin.zip",
  [string]$jarPath = "target\fix-inspector-0.1.0.jar"
)

Write-Output "Verifying plugin package: $zipPath"
if (-not (Test-Path $zipPath)) { Write-Error "Plugin zip not found: $zipPath"; exit 2 }

$extractDir = "target\verify-extracted"
Remove-Item -Recurse -Force $extractDir -ErrorAction SilentlyContinue
Expand-Archive -Path $zipPath -DestinationPath $extractDir -Force

Write-Output "Listing top-level entries in plugin zip:"
Get-ChildItem $extractDir | Select-Object Name,FullName

Write-Output "Checking for META-INF/plugin.xml at zip root..."
if (Test-Path (Join-Path $extractDir "META-INF\plugin.xml")) { Write-Output "FOUND: META-INF/plugin.xml at zip root" } else { Write-Error "MISSING: META-INF/plugin.xml at zip root" }

Write-Output "Checking lib/ for plugin jar (will inspect jar for classes):"
$libJar = Get-ChildItem (Join-Path $extractDir "lib") -Filter "*-${env:version}.jar" -ErrorAction SilentlyContinue
$libFiles = Get-ChildItem (Join-Path $extractDir "lib") -Force -ErrorAction SilentlyContinue
$libFiles | Select-Object Name,FullName

# If jar not found by name fallback to any jar in lib
$jarToInspect = if ($libFiles) { $libFiles[0].FullName } else { $null }
if ($jarToInspect) {
  Write-Output "Inspecting jar: $jarToInspect"
  $jarZip = $jarToInspect -replace '\.jar$','.zip'
  Copy-Item $jarToInspect $jarZip -Force
  $jarExtract = "$extractDir\jar-extracted"
  Remove-Item -Recurse -Force $jarExtract -ErrorAction SilentlyContinue
  Expand-Archive -Path $jarZip -DestinationPath $jarExtract -Force
  Write-Output "Looking for com/fixinspector in jar-extracted:"
  Get-ChildItem $jarExtract -Recurse | Where-Object { $_.FullName -match 'com\\fixinspector' } | Select-Object FullName
} else {
  Write-Error "No jar found in lib/ to inspect"
}

