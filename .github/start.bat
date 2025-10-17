echo === [Starting new app] ===
cd /d C:\root\hama\backend\target

for %%f in (*.jar) do (
    echo Starting %%f ...
    start "" cmd /c "java -jar \"%%f\" --server.port=8090"
)

exit /b