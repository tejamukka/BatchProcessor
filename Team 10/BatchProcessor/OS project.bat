print "Performing cleanup"
pause
call cleanup.bat
print "Cleanup done. Starting project"
pause
java -jar myBatchProcessor.jar batch1.xml
pause
java -jar myBatchProcessor.jar batch2.xml
pause
java -jar myBatchProcessor.jar batch3.xml
pause
java -jar myBatchProcessor.jar batch4.xml
pause
java -jar myBatchProcessor.jar batch5.xml
pause