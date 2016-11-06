
#!/bin/bash
cd server
while true; do
	cp ../target/DevathonProject-1.0-SNAPSHOT plugins/DevathonProject-1.0-SNAPSHOT
	java -jar spigot.jar
done

